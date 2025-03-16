package kmg.tool.domain.logic.io.dtc.impl;

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
import kmg.foundation.infrastructure.exception.KmgFundException;
import kmg.foundation.infrastructure.utils.KmgYamlUtils;
import kmg.tool.domain.logic.io.dtc.DtcLogic;
import kmg.tool.domain.model.io.dtc.DtcDerivedPlaceholderModel;
import kmg.tool.domain.model.io.dtc.DtcTransformModel;
import kmg.tool.domain.model.io.dtc.impl.DtcDerivedPlaceholderModelImpl;
import kmg.tool.domain.model.io.dtc.impl.DtcTransformModelImpl;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.domain.types.io.dtc.DtcKeyTypes;
import kmg.tool.domain.types.io.dtc.DtcTransformTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * テンプレートの動的変換ロジック実装<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 *
 * @author KenichiroArai
 */
@Service
public class DtcLogicImpl implements DtcLogic {

    /** 入力ファイルパス */
    private Path inputPath;

    /** テンプレートファイルパス */
    private Path templatePath;

    /** 出力ファイルパス */
    private Path outputPath;

    /** 入力ファイルのBufferedReader */
    private BufferedReader reader;

    /** 出力ファイルのBufferedWriter */
    private BufferedWriter writer;

    /** 読み込んだ1行データ */
    private String lineOfDataRead;

    /** 変換後の1行データ */
    private String convertedLine;

    /** CSVプレースホルダーの定義マップ */
    private final Map<String, String> csvPlaceholderMap;

    /** 派生プレースホルダーの定義リスト */
    private final List<DtcDerivedPlaceholderModel> derivedPlaceholders;

    /** テンプレートの内容 */
    private String templateContent;

    /** 1件分の内容 */
    private String contentsOfOneItem;

    /** 出力バッファの内容 */
    private final StringBuilder outputBufferContent;

    /**
     * デフォルトコンストラクタ
     */
    public DtcLogicImpl() {

        this.csvPlaceholderMap = new HashMap<>();
        this.derivedPlaceholders = new ArrayList<>();
        this.outputBufferContent = new StringBuilder();

    }

    /**
     * 出力バッファに追加する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean addOutputBufferContent() throws KmgToolException {

        boolean result = false;

        this.outputBufferContent.append(this.contentsOfOneItem);

        result = true;
        return result;

    }

    /**
     * 入力ファイルからテンプレートに基づいて変換する。
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public void applyTemplateToInputFile() throws KmgToolException {

        this.contentsOfOneItem = this.templateContent;

        // CSV値を一時保存するマップ
        final Map<String, String> csvValues = new HashMap<>();

        // CSVプレースホルダーを処理
        this.processCsvPlaceholders(csvValues);

        // 派生プレースホルダーを処理
        this.processDerivedPlaceholders(csvValues);

    }

    /**
     * 出力バッファコンテンツをクリアする
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public void clearOutputBufferContent() throws KmgToolException {

        this.outputBufferContent.setLength(0);

    }

    /**
     * 読み込み中のデータをクリアする。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean clearReadingData() throws KmgToolException {

        boolean result = false;

        this.lineOfDataRead = null;
        this.convertedLine = null;
        this.templateContent = null;
        this.csvPlaceholderMap.clear();
        this.derivedPlaceholders.clear();
        this.contentsOfOneItem = null;
        this.clearOutputBufferContent();

        result = true;
        return result;

    }

    /**
     * リソースをクローズする。
     *
     * @throws IOException
     *                     入出力例外
     */
    @Override
    public void close() throws IOException {

        this.closeReader();
        this.closeWriter();

    }

    /**
     * 1件分の内容を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 1件分の内容
     */
    @Override
    public String getContentsOfOneItem() {

        final String result = this.contentsOfOneItem;
        return result;

    }

    /**
     * 入力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 入力ファイルパス
     */
    @Override
    public Path getInputPath() {

        final Path result = this.inputPath;
        return result;

    }

    /**
     * 出力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 出力ファイルパス
     */
    @Override
    public Path getOutputPath() {

        final Path result = this.outputPath;
        return result;

    }

    /**
     * テンプレートファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return テンプレートファイルパス
     */
    @Override
    public Path getTemplatePath() {

        final Path result = this.templatePath;
        return result;

    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param inputPath
     *                     入力ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     * @param outputPath
     *                     出力ファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    @SuppressWarnings("hiding")
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath)
        throws KmgToolException {

        boolean result = false;

        this.inputPath = inputPath;
        this.templatePath = templatePath;
        this.outputPath = outputPath;

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
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          テンプレートの読み込みに失敗した場合
     */
    @Override
    public boolean loadTemplate() throws KmgToolException {

        boolean result = false;

        /* YAMLファイルを読み込む */
        final Map<String, Object> yamlData;

        try {

            yamlData = KmgYamlUtils.load(this.getTemplatePath());

        } catch (final KmgFundException e) {

            final KmgToolGenMessageTypes genMsgType = KmgToolGenMessageTypes.KMGTOOL_GEN12001;
            final Object[]               genMsgArgs = {
                this.templatePath.toString()
            };
            throw new KmgToolException(genMsgType, genMsgArgs, e);

        }

        /* テンプレートコンテンツを読み込む */
        this.loadTemplateContent(yamlData);

        /* プレースホルダー定義の取得 */

        // CSVプレースホルダー定義を読み込む
        this.loadCsvPlaceholderDefinitions(yamlData);

        // 派生プレースホルダー定義を読み込む
        this.loadDerivedPlaceholderDefinitions(yamlData);

        result = true;
        return result;

    }

    /**
     * 1行データを読み込む。
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean readOneLineOfData() throws KmgToolException {

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

            // TODO KenichiroArai 2025/03/16 例外処理
            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs  = {};
            throw new KmgToolException(messageTypes, messageArgs, e);

        }

        result = true;
        return result;

    }

    /**
     * 出力バッファを書き込む
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean writeOutputBuffer() throws KmgToolException {

        boolean result = false;

        try {

            this.writer.write(this.outputBufferContent.toString());
            this.writer.newLine();

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/16 例外処理のメッセージを変更する
            final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.KMGTOOL_GEN12003;
            final Object[]               messageArgs = {
                this.inputPath.toString(), this.templatePath.toString(), this.outputPath.toString(),
            };
            throw new KmgToolException(msgType, messageArgs, e);

        }

        result = true;
        return result;

    }

    /**
     * 入力ファイルのリーダーをクローズする<br>
     *
     * @throws IOException
     *                     入出力例外
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
     *
     * @throws IOException
     *                     入出力例外
     */
    private void closeWriter() throws IOException {

        if (this.writer == null) {

            return;

        }

        this.writer.close();
        this.writer = null;

    }

    /**
     * CSVプレースホルダー定義を読み込む<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @param yamlData
     *                 YAMLデータ
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          テンプレートの読み込みに失敗した場合
     */
    private boolean loadCsvPlaceholderDefinitions(final Map<String, Object> yamlData) throws KmgToolException {

        boolean result = false;

        // CSVプレースホルダー定義を取得する
        @SuppressWarnings("unchecked")
        final List<Map<String, String>> csvPlaceholders
            = (List<Map<String, String>>) yamlData.get(DtcKeyTypes.CSV_PLACEHOLDERS.getKey());

        if (csvPlaceholders == null) {

            return result;

        }

        for (final Map<String, String> placeholderMap : csvPlaceholders) {

            this.csvPlaceholderMap.put(placeholderMap.get(DtcKeyTypes.DISPLAY_NAME.getKey()),
                placeholderMap.get(DtcKeyTypes.REPLACEMENT_PATTERN.getKey()));

        }

        result = true;
        return result;

    }

    /**
     * 派生プレースホルダー定義を読み込む<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @param yamlData
     *                 YAMLデータ
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          テンプレートの読み込みに失敗した場合
     */
    private boolean loadDerivedPlaceholderDefinitions(final Map<String, Object> yamlData) throws KmgToolException {

        boolean result = false;

        // 派生プレースホルダー定義を取得する
        @SuppressWarnings("unchecked")
        final List<Map<String, String>> derivedPlaceholdersTmp
            = (List<Map<String, String>>) yamlData.get(DtcKeyTypes.DERIVED_PLACEHOLDERS.getKey());

        if (derivedPlaceholdersTmp == null) {

            return result;

        }

        for (final Map<String, String> placeholderMap : derivedPlaceholdersTmp) {

            final String displayName        = placeholderMap.get(DtcKeyTypes.DISPLAY_NAME.getKey());
            final String replacementPattern = placeholderMap.get(DtcKeyTypes.REPLACEMENT_PATTERN.getKey());
            final String sourceKey          = placeholderMap.get(DtcKeyTypes.SOURCE_KEY.getKey());
            final String transformation     = placeholderMap.get(DtcKeyTypes.TRANSFORMATION.getKey());

            final DtcDerivedPlaceholderModel derivedPlaceholder
                = new DtcDerivedPlaceholderModelImpl(displayName, replacementPattern, sourceKey, transformation);
            this.derivedPlaceholders.add(derivedPlaceholder);

        }

        result = true;
        return result;

    }

    /**
     * テンプレートコンテンツを読み込む<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @param yamlData
     *                 YAMLデータ
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          テンプレートコンテンツの読み込みに失敗した場合
     */
    private boolean loadTemplateContent(final Map<String, Object> yamlData) throws KmgToolException {

        boolean result = false;

        this.templateContent = (String) yamlData.get(DtcKeyTypes.TEMPLATE_CONTENT.getKey());

        result = true;
        return result;

    }

    /**
     * 入力ファイルを開く<br>
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("resource")
    private void openInputFile() throws KmgToolException {

        try {

            this.reader = Files.newBufferedReader(this.inputPath);

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/15 例外処理
            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs  = {
                this.inputPath.toString()
            };
            throw new KmgToolException(messageTypes, messageArgs, e);

        }

    }

    /**
     * 出力ファイルを開く
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("resource")
    private void openOutputFile() throws KmgToolException {

        try {

            this.writer = Files.newBufferedWriter(this.outputPath);

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/15 例外処理
            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs  = {
                this.outputPath.toString()
            };
            throw new KmgToolException(messageTypes, messageArgs, e);

        }

    }

    /**
     * CSVプレースホルダーを処理する<br>
     *
     * @param csvValues
     *                  CSV値を保存するマップ
     */
    private void processCsvPlaceholders(final Map<String, String> csvValues) {

        final String[] csvLine = KmgDelimiterTypes.COMMA.split(this.convertedLine);

        // CSVプレースホルダーのキー配列
        final String[] csvPlaceholderKeys = this.csvPlaceholderMap.keySet().toArray(new String[0]);

        // CSVプレースホルダーのパターン配列
        final String[] csvPlaceholderPatterns = this.csvPlaceholderMap.values().toArray(new String[0]);

        // 各CSVプレースホルダーを対応する値で置換
        for (int i = 0; i < csvPlaceholderPatterns.length; i++) {

            if (i >= csvLine.length) {

                continue;

            }

            final String key     = csvPlaceholderKeys[i];
            final String pattern = csvPlaceholderPatterns[i];
            final String value   = csvLine[i];

            // 値を保存
            csvValues.put(key, value);

            // テンプレートを置換
            this.contentsOfOneItem = this.contentsOfOneItem.replace(pattern, value);

        }

    }

    /**
     * 派生プレースホルダーを処理する<br>
     *
     * @param csvValues
     *                  CSV値を保存するマップ
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private void processDerivedPlaceholders(final Map<String, String> csvValues) throws KmgToolException {

        // 派生プレースホルダーを処理
        for (final DtcDerivedPlaceholderModel derivedPlaceholder : this.derivedPlaceholders) {

            final String sourceValue = csvValues.get(derivedPlaceholder.getSourceKey());

            if (sourceValue == null) {

                continue;

            }

            // テンプレートの動的変換変換処理変換
            final DtcTransformTypes transformationType
                = DtcTransformTypes.getEnum(derivedPlaceholder.getTransformation());

            // 変換処理を適用
            final DtcTransformModel dtcTransformModel = new DtcTransformModelImpl(sourceValue, transformationType);
            dtcTransformModel.apply();

            // テンプレートを置換
            this.contentsOfOneItem = this.contentsOfOneItem.replace(derivedPlaceholder.getReplacementPattern(),
                dtcTransformModel.getTransformedValue());

        }

    }

}
