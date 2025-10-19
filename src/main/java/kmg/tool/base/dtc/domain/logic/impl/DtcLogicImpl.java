package kmg.tool.base.dtc.domain.logic.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.fund.infrastructure.exception.KmgFundMsgException;
import kmg.fund.infrastructure.utils.KmgYamlUtils;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.dtc.domain.logic.DtcLogic;
import kmg.tool.base.dtc.domain.model.DtcDerivedPlaceholderModel;
import kmg.tool.base.dtc.domain.model.DtcTransformModel;
import kmg.tool.base.dtc.domain.model.impl.DtcDerivedPlaceholderModelImpl;
import kmg.tool.base.dtc.domain.model.impl.DtcTransformModelImpl;
import kmg.tool.base.dtc.domain.types.DtcKeyTypes;
import kmg.tool.base.dtc.domain.types.DtcTransformTypes;

/**
 * テンプレートの動的変換ロジック実装<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 * <p>
 * このクラスはテンプレートファイルを入力ファイルのデータに基づいて動的に変換するロジックを実装します。 中間データとテンプレートを組み合わせて、動的なコンテンツ生成を行います。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@Service
public class DtcLogicImpl implements DtcLogic {

    /**
     * 入力ファイルパス
     *
     * @since 0.2.0
     */
    private Path inputPath;

    /**
     * テンプレートファイルパス
     *
     * @since 0.2.0
     */
    private Path templatePath;

    /**
     * 出力ファイルパス
     *
     * @since 0.2.0
     */
    private Path outputPath;

    /**
     * 入力ファイルのBufferedReader
     *
     * @since 0.2.0
     */
    private BufferedReader reader;

    /**
     * 出力ファイルのBufferedWriter
     *
     * @since 0.2.0
     */
    private BufferedWriter writer;

    /**
     * 読み込んだ1行データ
     *
     * @since 0.2.0
     */
    private String lineOfDataRead;

    /**
     * 変換後の1行データ
     *
     * @since 0.2.0
     */
    private String convertedLine;

    /**
     * 中間行の区切り文字
     *
     * @since 0.2.0
     */
    private KmgDelimiterTypes intermediateDelimiter;

    /**
     * 中間プレースホルダーの定義マップ
     *
     * @since 0.2.0
     */
    private final Map<String, String> intermediatePlaceholderMap;

    /**
     * 派生プレースホルダーの定義リスト
     *
     * @since 0.2.0
     */
    private final List<DtcDerivedPlaceholderModel> derivedPlaceholders;

    /**
     * テンプレートの内容
     *
     * @since 0.2.0
     */
    private String templateContent;

    /**
     * 1件分の内容
     *
     * @since 0.2.0
     */
    private String contentsOfOneItem;

    /**
     * 出力バッファの内容
     *
     * @since 0.2.0
     */
    private final StringBuilder outputBufferContent;

    /**
     * デフォルトコンストラクタ
     *
     * @since 0.2.0
     */
    public DtcLogicImpl() {

        this.intermediatePlaceholderMap = new HashMap<>();
        this.derivedPlaceholders = new ArrayList<>();
        this.outputBufferContent = new StringBuilder();

    }

    /**
     * 出力バッファに追加する<br>
     * <p>
     * 現在の1件分の内容（contentsOfOneItem）を出力バッファに追加します。 変換処理が完了したテンプレートの内容をバッファに蓄積するために使用します。
     * </p>
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外 - バッファ操作中にエラーが発生した場合
     */
    @Override
    public boolean addOutputBufferContent() throws KmgToolMsgException {

        boolean result = false;

        this.outputBufferContent.append(this.contentsOfOneItem);

        result = true;
        return result;

    }

    /**
     * 入力ファイルからテンプレートに基づいて変換する。<br>
     * <p>
     * 現在読み込まれている入力ファイルの1行データに対して、テンプレートを適用し変換処理を行います。 中間プレースホルダーと派生プレースホルダーの両方を処理します。
     * </p>
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外 - 変換処理中にエラーが発生した場合
     */
    @Override
    public void applyTemplateToInputFile() throws KmgToolMsgException {

        /* 1件分の内容をコンテンツの内容で初期化 */
        this.contentsOfOneItem = this.templateContent;

        /* 中間値を一時保存するマップ */
        final Map<String, String> intermediateValues = new HashMap<>();

        /* 中間プレースホルダーを処理 */
        this.processPlaceholders(intermediateValues);

        /* 派生プレースホルダーを処理 */
        this.processDerivedPlaceholders(intermediateValues);

    }

    /**
     * 出力バッファコンテンツをクリアする<br>
     * <p>
     * 出力バッファの内容を空にします。新しい出力処理を開始する前に使用します。
     * </p>
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外 - バッファクリア中にエラーが発生した場合
     */
    @Override
    public void clearOutputBufferContent() throws KmgToolMsgException {

        this.outputBufferContent.setLength(0);

    }

    /**
     * 読み込み中のデータをクリアする。<br>
     * <p>
     * 現在読み込まれているデータや変換状態をすべてクリアします。 新しい処理を開始する前に内部状態をリセットするために使用します。
     * </p>
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外 - データクリア中にエラーが発生した場合
     */
    @Override
    public boolean clearReadingData() throws KmgToolMsgException {

        boolean result = false;

        this.lineOfDataRead = null;
        this.convertedLine = null;
        this.templateContent = null;
        this.intermediatePlaceholderMap.clear();
        this.derivedPlaceholders.clear();
        this.contentsOfOneItem = null;
        this.clearOutputBufferContent();

        result = true;
        return result;

    }

    /**
     * リソースをクローズする。<br>
     * <p>
     * 入力ファイルリーダーと出力ファイルライターをクローズします。 リソースリークを防ぐために使用します。
     * </p>
     *
     * @since 0.2.0
     *
     * @throws IOException
     *                     入出力例外 - ファイルクローズ中にエラーが発生した場合
     */
    @Override
    public void close() throws IOException {

        this.closeReader();
        this.closeWriter();

    }

    /**
     * 1件分の内容を返す<br>
     * <p>
     * 現在処理中の1件分のテンプレート変換後の内容を返します。
     * </p>
     *
     * @since 0.2.0
     *
     * @return 1件分の内容 - テンプレート変換後のコンテンツ
     */
    @Override
    public String getContentsOfOneItem() {

        final String result = this.contentsOfOneItem;
        return result;

    }

    /**
     * 入力ファイルパスを返す<br>
     * <p>
     * 現在処理中の入力ファイルのパスを返します。
     * </p>
     *
     * @since 0.2.0
     *
     * @return 入力ファイルパス - 処理対象の入力ファイルのパス
     */
    @Override
    public Path getInputPath() {

        final Path result = this.inputPath;
        return result;

    }

    /**
     * 出力ファイルパスを返す<br>
     * <p>
     * 現在処理中の出力ファイルのパスを返します。
     * </p>
     *
     * @since 0.2.0
     *
     * @return 出力ファイルパス - 処理結果を書き込む出力ファイルのパス
     */
    @Override
    public Path getOutputPath() {

        final Path result = this.outputPath;
        return result;

    }

    /**
     * テンプレートファイルパスを返す<br>
     * <p>
     * 現在処理中のテンプレートファイルのパスを返します。
     * </p>
     *
     * @since 0.2.0
     *
     * @return テンプレートファイルパス - 変換に使用するテンプレートファイルのパス
     */
    @Override
    public Path getTemplatePath() {

        final Path result = this.templatePath;
        return result;

    }

    /**
     * 初期化する<br>
     * <p>
     * 入力ファイル、テンプレートファイル、出力ファイルのパスを設定し、 処理に必要なリソースを初期化します。入力ファイルと出力ファイルを開きます。
     * </p>
     *
     * @since 0.2.0
     *
     * @param inputPath
     *                     入力ファイルパス - 処理対象の中間データファイルのパス
     * @param templatePath
     *                     テンプレートファイルパス - 変換に使用するテンプレート定義ファイルのパス
     * @param outputPath
     *                     出力ファイルパス - 変換結果を書き込むファイルのパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外 - 初期化処理中にエラーが発生した場合
     */
    @Override
    @SuppressWarnings("hiding")
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath)
        throws KmgToolMsgException {

        final boolean result = this.initialize(inputPath, templatePath, outputPath, KmgDelimiterTypes.COMMA);
        return result;

    }

    /**
     * 初期化する
     *
     * @since 0.2.0
     *
     * @param inputPath
     *                              入力ファイルパス
     * @param templatePath
     *                              テンプレートファイルパス
     * @param outputPath
     *                              出力ファイルパス
     * @param intermediateDelimiter
     *                              中間行の区切り文字
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     *
     * @return true：成功、false：失敗
     */
    @Override
    @SuppressWarnings("hiding")
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath,
        final KmgDelimiterTypes intermediateDelimiter) throws KmgToolMsgException {

        boolean result = false;

        this.inputPath = inputPath;
        this.templatePath = templatePath;
        this.outputPath = outputPath;
        this.intermediateDelimiter = intermediateDelimiter;

        /* データのクリア */
        this.clearReadingData();

        /* ファイルを開く */

        // 入力ファイルを開く
        this.openInputFile();

        // 出力ファイルを開く
        this.openOutputFile();

        result = true;
        return result;

    }

    /**
     * テンプレートファイルを読み込む<br>
     * <p>
     * YAMLフォーマットのテンプレート定義ファイルを読み込み、 テンプレートコンテンツ、中間プレースホルダー定義、派生プレースホルダー定義を取得します。
     * </p>
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             テンプレートの読み込みに失敗した場合 - ファイルが存在しない、フォーマットが不正など
     */
    @Override
    public boolean loadTemplate() throws KmgToolMsgException {

        boolean result = false;

        /* YAMLファイルを読み込む */
        final Map<String, Object> yamlData;

        try {

            yamlData = KmgYamlUtils.load(this.getTemplatePath());

        } catch (final KmgFundMsgException e) {

            final KmgToolGenMsgTypes genMsgType = KmgToolGenMsgTypes.KMGTOOL_GEN03000;
            final Object[]           genMsgArgs = {
                this.templatePath.toString()
            };
            throw new KmgToolMsgException(genMsgType, genMsgArgs, e);

        }

        /* テンプレートコンテンツを読み込む */
        this.loadTemplateContent(yamlData);

        /* プレースホルダー定義の取得 */

        // 中間プレースホルダー定義を読み込む
        this.loadIntermediatePlaceholderDefinitions(yamlData);

        // 派生プレースホルダー定義を読み込む
        this.loadDerivedPlaceholderDefinitions(yamlData);

        result = true;
        return result;

    }

    /**
     * 1行データを読み込む。<br>
     * <p>
     * 入力ファイルから1行のデータを読み込みます。 読み込んだデータは内部変数に保存され、後続の処理で使用されます。
     * </p>
     *
     * @since 0.2.0
     *
     * @return true：データあり、false：データなし（ファイル終端に達した場合）
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外 - 読み込み処理中にエラーが発生した場合
     */
    @Override
    public boolean readOneLineOfData() throws KmgToolMsgException {

        boolean result = false;

        try {

            // 1行読み込み
            this.lineOfDataRead = this.reader.readLine();
            this.convertedLine = this.lineOfDataRead;

            // ファイルの終わりに達したか
            if (this.lineOfDataRead == null) {
                // 達した場合

                return result;

            }

        } catch (final IOException e) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN03001;
            final Object[]           messageArgs  = {
                this.inputPath.toString(),
            };
            throw new KmgToolMsgException(messageTypes, messageArgs, e);

        }

        result = true;
        return result;

    }

    /**
     * 出力バッファを書き込む<br>
     * <p>
     * 出力バッファに蓄積された内容を出力ファイルに書き込みます。 書き込み後は自動的に改行が追加されます。
     * </p>
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外 - 書き込み処理中にエラーが発生した場合
     */
    @Override
    public boolean writeOutputBuffer() throws KmgToolMsgException {

        boolean result = false;

        try {

            this.writer.write(this.outputBufferContent.toString());
            this.writer.newLine();

        } catch (final IOException e) {

            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN03002;
            final Object[]           messageArgs = {
                this.inputPath.toString(), this.templatePath.toString(), this.outputPath.toString(),
            };
            throw new KmgToolMsgException(msgType, messageArgs, e);

        }

        result = true;
        return result;

    }

    /**
     * 入力ファイルのリーダーをクローズする<br>
     * <p>
     * 入力ファイルのBufferedReaderをクローズします。 リーダーがnullの場合は何もしません。
     * </p>
     *
     * @since 0.2.0
     *
     * @throws IOException
     *                     入出力例外 - ファイルクローズ中にエラーが発生した場合
     */
    private void closeReader() throws IOException {

        if (this.reader == null) {

            return;

        }

        this.reader.close();
        this.reader = null;

    }

    /**
     * 出力ファイルのライターをクローズする<br>
     * <p>
     * 出力ファイルのBufferedWriterをクローズします。 ライターがnullの場合は何もしません。
     * </p>
     *
     * @since 0.2.0
     *
     * @throws IOException
     *                     入出力例外 - ファイルクローズ中にエラーが発生した場合
     */
    private void closeWriter() throws IOException {

        if (this.writer == null) {

            return;

        }

        this.writer.close();
        this.writer = null;

    }

    /**
     * 派生プレースホルダー定義を読み込む<br>
     * <p>
     * YAMLデータから派生プレースホルダー定義を読み込み、内部リストに格納します。 派生プレースホルダー定義がない場合は何もしません。
     * </p>
     *
     * @since 0.2.0
     *
     * @param yamlData
     *                 YAMLデータ - テンプレート定義ファイルから読み込まれたデータ
     *
     * @return true：成功、false：失敗（派生プレースホルダー定義がない場合）
     *
     * @throws KmgToolMsgException
     *                             テンプレートの読み込みに失敗した場合 - YAMLデータの形式が不正など
     */
    private boolean loadDerivedPlaceholderDefinitions(final Map<String, Object> yamlData) throws KmgToolMsgException {

        boolean result = false;

        // 派生プレースホルダー定義を取得する
        @SuppressWarnings("unchecked")
        final List<Map<String, String>> derivedPlaceholdersTmp
            = (List<Map<String, String>>) yamlData.get(DtcKeyTypes.DERIVED_PLACEHOLDERS.getKey());

        if (derivedPlaceholdersTmp == null) {

            return result;

        }

        for (final Map<String, String> placeholderMap : derivedPlaceholdersTmp) {

            final String            displayName        = placeholderMap.get(DtcKeyTypes.DISPLAY_NAME.getKey());
            final String            replacementPattern = placeholderMap.get(DtcKeyTypes.REPLACEMENT_PATTERN.getKey());
            final String            sourceKey          = placeholderMap.get(DtcKeyTypes.SOURCE_KEY.getKey());
            final DtcTransformTypes transformTypes     = DtcTransformTypes
                .getEnum(placeholderMap.get(DtcKeyTypes.TRANSFORMATION.getKey()));

            final DtcDerivedPlaceholderModel derivedPlaceholder
                = new DtcDerivedPlaceholderModelImpl(displayName, replacementPattern, sourceKey, transformTypes);
            this.derivedPlaceholders.add(derivedPlaceholder);

        }

        result = true;
        return result;

    }

    /**
     * 中間プレースホルダー定義を読み込む<br>
     * <p>
     * YAMLデータから中間プレースホルダー定義を読み込み、内部マップに格納します。 プレースホルダー定義がない場合は何もしません。
     * </p>
     *
     * @since 0.2.0
     *
     * @param yamlData
     *                 YAMLデータ - テンプレート定義ファイルから読み込まれたデータ
     *
     * @return true：成功、false：失敗（プレースホルダー定義がない場合）
     *
     * @throws KmgToolMsgException
     *                             テンプレートの読み込みに失敗した場合 - YAMLデータの形式が不正など
     */
    private boolean loadIntermediatePlaceholderDefinitions(final Map<String, Object> yamlData)
        throws KmgToolMsgException {

        boolean result = false;

        // 中間プレースホルダー定義を取得する
        @SuppressWarnings("unchecked")
        final List<Map<String, String>> intermediatePlaceholders
            = (List<Map<String, String>>) yamlData.get(DtcKeyTypes.INTERMEDIATE_PLACEHOLDERS.getKey());

        if (intermediatePlaceholders == null) {

            return result;

        }

        for (final Map<String, String> placeholderMap : intermediatePlaceholders) {

            this.intermediatePlaceholderMap.put(placeholderMap.get(DtcKeyTypes.DISPLAY_NAME.getKey()),
                placeholderMap.get(DtcKeyTypes.REPLACEMENT_PATTERN.getKey()));

        }

        result = true;
        return result;

    }

    /**
     * テンプレートコンテンツを読み込む<br>
     * <p>
     * YAMLデータからテンプレートコンテンツを読み込み、内部変数に格納します。 テンプレートコンテンツは変換処理の基本となるテキストです。
     * </p>
     *
     * @since 0.2.0
     *
     * @param yamlData
     *                 YAMLデータ - テンプレート定義ファイルから読み込まれたデータ
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             テンプレートコンテンツの読み込みに失敗した場合 - YAMLデータの形式が不正など
     */
    private boolean loadTemplateContent(final Map<String, Object> yamlData) throws KmgToolMsgException {

        boolean result = false;

        this.templateContent = (String) yamlData.get(DtcKeyTypes.TEMPLATE_CONTENT.getKey());

        result = true;
        return result;

    }

    /**
     * 入力ファイルを開く<br>
     * <p>
     * 指定された入力ファイルパスからBufferedReaderを作成し、内部変数に格納します。 入力ファイルが存在しない場合や読み込めない場合は例外をスローします。
     * </p>
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外 - ファイルオープン中にエラーが発生した場合
     */
    @SuppressWarnings("resource")
    private void openInputFile() throws KmgToolMsgException {

        try {

            this.reader = Files.newBufferedReader(this.inputPath);

        } catch (final IOException e) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN03003;
            final Object[]           messageArgs  = {
                this.inputPath.toString()
            };
            throw new KmgToolMsgException(messageTypes, messageArgs, e);

        }

    }

    /**
     * 出力ファイルを開く<br>
     * <p>
     * 指定された出力ファイルパスからBufferedWriterを作成し、内部変数に格納します。 出力ファイルが作成できない場合や書き込めない場合は例外をスローします。
     * </p>
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外 - ファイルオープン中にエラーが発生した場合
     */
    @SuppressWarnings("resource")
    private void openOutputFile() throws KmgToolMsgException {

        try {

            this.writer = Files.newBufferedWriter(this.outputPath);

        } catch (final IOException e) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN03004;
            final Object[]           messageArgs  = {
                this.outputPath.toString()
            };
            throw new KmgToolMsgException(messageTypes, messageArgs, e);

        }

    }

    /**
     * 派生プレースホルダーを処理する<br>
     * <p>
     * 中間プレースホルダー処理で得られた値を元に、派生プレースホルダーの変換処理を行います。 各派生プレースホルダーに対して、指定された変換タイプに基づいて値を変換し、 テンプレート内の対応するパターンを置換します。
     * </p>
     *
     * @since 0.2.0
     *
     * @param intermediateValues
     *                           中間値を保存するマップ - キーはプレースホルダー名、値は中間から読み取った値
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外 - 変換処理中にエラーが発生した場合
     */
    private void processDerivedPlaceholders(final Map<String, String> intermediateValues) throws KmgToolMsgException {

        for (final DtcDerivedPlaceholderModel derivedPlaceholder : this.derivedPlaceholders) {

            final String sourceValue = intermediateValues.get(derivedPlaceholder.getSourceKey());

            if (sourceValue == null) {

                continue;

            }

            // 変換処理を適用
            final DtcTransformModel dtcTransformModel
                = new DtcTransformModelImpl(sourceValue, derivedPlaceholder.getTransformationTypes());
            dtcTransformModel.apply();

            // テンプレートを置換
            this.contentsOfOneItem = this.contentsOfOneItem.replace(derivedPlaceholder.getReplacementPattern(),
                dtcTransformModel.getTransformedValue());

        }

    }

    /**
     * 中間プレースホルダーを処理する<br>
     * <p>
     * 現在読み込まれている中間行データを解析し、中間プレースホルダーを対応する値で置換します。 置換された値は中間値を保存するマップに保存され、後続の派生プレースホルダー処理で使用されます。
     * </p>
     *
     * @since 0.2.0
     *
     * @param intermediateValues
     *                           中間値を保存するマップ - キーはプレースホルダー名、値は中間から読み取った値
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外 - 中間行の列数が不足している場合など
     */
    private void processPlaceholders(final Map<String, String> intermediateValues) throws KmgToolMsgException {

        /* 置換前の準備 */

        // 中間行に分割
        final String[] intermediateLine = this.intermediateDelimiter.split(this.convertedLine);

        // 中間プレースホルダーのキー配列
        final String[] intermediatePlaceholderKeys = this.intermediatePlaceholderMap.keySet().toArray(new String[0]);

        // 中間プレースホルダーのパターン配列
        final String[] intermediatePlaceholderPatterns
            = this.intermediatePlaceholderMap.values().toArray(new String[0]);

        /* 各中間プレースホルダーを対応する値で置換 */
        for (int i = 0; i < intermediatePlaceholderKeys.length; i++) {

            final String key     = intermediatePlaceholderKeys[i];
            final String pattern = intermediatePlaceholderPatterns[i];
            String       value;

            try {

                value = intermediateLine[i];

            } catch (final ArrayIndexOutOfBoundsException e) {

                final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN03005;
                final Object[]           messageArgs  = {
                    this.inputPath.toString(), key, i + 1,
                };
                throw new KmgToolMsgException(messageTypes, messageArgs, e);

            }

            // 値を保存
            intermediateValues.put(key, value);

            // テンプレートを置換
            this.contentsOfOneItem = this.contentsOfOneItem.replace(pattern, value);

        }

    }

}
