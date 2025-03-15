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

    /** YAMLデータ */
    private final Map<String, Object> yamlData;

    /** CSVプレースホルダーの定義マップ */
    private final Map<String, String> csvPlaceholderMap;

    /** 派生プレースホルダーの定義リスト */
    private final List<DtcDerivedPlaceholderModel> derivedPlaceholders;

    /** テンプレートの内容 */
    private String templateContent;

    /**
     * デフォルトコンストラクタ
     */
    public DtcLogicImpl() {

        this.yamlData = new HashMap<>();
        this.csvPlaceholderMap = new HashMap<>();
        this.derivedPlaceholders = new ArrayList<>();

    }

    /**
     * 読み込み処理をクリアする。
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean clearReadProcess() {

        boolean result = false;

        this.yamlData.clear();
        this.templateContent = null;
        this.csvPlaceholderMap.clear();
        this.derivedPlaceholders.clear();

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
     * YAMLデータを返す。
     *
     * @return YAMLデータ
     */
    @Override
    public Map<String, Object> getYamlData() {

        final Map<String, Object> result = this.yamlData;
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

        /* 読み込みと書き込みのインスタンス変数の初期化 */
        this.clearReadProcess();

        /* ファイルを開く */

        // 入力ファイルを開く
        this.openInputFile();

        // 出力ファイルを開く
        this.openOutputFile();

        result = true;
        return result;

    }

    /**
     * CSVプレースホルダー定義を読み込む<br>
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
    public boolean loadCsvPlaceholderDefinitions() throws KmgToolException {

        boolean result = false;

        // CSVプレースホルダー定義を取得する
        @SuppressWarnings("unchecked")
        final List<Map<String, String>> csvPlaceholders
            = (List<Map<String, String>>) this.yamlData.get(DtcKeyTypes.CSV_PLACEHOLDERS.getKey());

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
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          テンプレートの読み込みに失敗した場合
     */
    @Override
    public boolean loadDerivedPlaceholderDefinitions() throws KmgToolException {

        boolean result = false;

        // 派生プレースホルダー定義を取得する
        @SuppressWarnings("unchecked")
        final List<Map<String, String>> derivedPlaceholdersTmp
            = (List<Map<String, String>>) this.yamlData.get(DtcKeyTypes.DERIVED_PLACEHOLDERS.getKey());

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

        try {

            this.yamlData.putAll(KmgYamlUtils.load(this.getTemplatePath()));

        } catch (final KmgFundException e) {

            final KmgToolGenMessageTypes genMsgType = KmgToolGenMessageTypes.KMGTOOL_GEN12001;
            final Object[]               genMsgArgs = {
                this.templatePath.toString()
            };
            throw new KmgToolException(genMsgType, genMsgArgs, e);

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
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          テンプレートコンテンツの読み込みに失敗した場合
     */
    @Override
    public boolean loadTemplateContent() throws KmgToolException {

        boolean result = false;

        this.templateContent = (String) this.yamlData.get(DtcKeyTypes.TEMPLATE_CONTENT.getKey());

        result = true;
        return result;

    }

    /**
     * 入力ファイルを処理し、テンプレートに基づいて出力を生成する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @throws KmgToolException
     *                          入出力処理に失敗した場合
     */
    @Override
    public void processInputAndGenerateOutput() throws KmgToolException {

        try {

            // 入力ファイルを1行ずつ処理
            this.processInputFile();

        } catch (final IOException e) {

            final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.KMGTOOL_GEN12003;
            final Object[]               messageArgs = {
                this.inputPath.toString(), this.templatePath.toString(), this.outputPath.toString(),
            };
            throw new KmgToolException(msgType, messageArgs, e);

        } finally {

            try {

                // リソースをクローズ
                this.close();

            } catch (final IOException e) {

                // TODO KenichiroArai 2025/03/15 例外処理
            }

        }

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
     * @param template
     *                  テンプレート
     * @param csvLine
     *                  CSV行データ
     * @param csvValues
     *                  CSV値を保存するマップ
     *
     * @return 処理後のテンプレート
     */
    private String processCsvPlaceholders(final String template, final String[] csvLine,
        final Map<String, String> csvValues) {

        String result = template;

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
            result = result.replace(pattern, value);

        }

        return result;

    }

    /**
     * 派生プレースホルダーを処理する<br>
     *
     * @param template
     *                  テンプレート
     * @param csvValues
     *                  CSV値を保存するマップ
     *
     * @return 処理後のテンプレート
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private String processDerivedPlaceholders(final String template, final Map<String, String> csvValues)
        throws KmgToolException {

        String result = template;

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
            result
                = result.replace(derivedPlaceholder.getReplacementPattern(), dtcTransformModel.getTransformedValue());

        }

        return result;

    }

    /**
     * 入力ファイルを1行ずつ処理する<br>
     *
     * @throws IOException
     *                          入出力例外
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private void processInputFile() throws IOException, KmgToolException {

        String line;

        // 入力ファイルを1行ずつ処理
        while ((line = this.reader.readLine()) != null) {

            // 1行を処理して出力
            final String processedLine = this.processLine(line);
            this.writer.write(processedLine);
            this.writer.newLine();

        }

    }

    /**
     * 1行のデータを処理する<br>
     *
     * @param line
     *             処理する行
     *
     * @return 処理後の行
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private String processLine(final String line) throws KmgToolException {

        String         result  = this.templateContent;
        final String[] csvLine = KmgDelimiterTypes.COMMA.split(line);

        // CSV値を一時保存するマップ
        final Map<String, String> csvValues = new HashMap<>();

        // CSVプレースホルダーを処理
        result = this.processCsvPlaceholders(result, csvLine, csvValues);

        // 派生プレースホルダーを処理
        result = this.processDerivedPlaceholders(result, csvValues);

        return result;

    }
}
