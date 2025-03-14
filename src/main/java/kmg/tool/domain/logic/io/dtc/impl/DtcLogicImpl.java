package kmg.tool.domain.logic.io.dtc.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.foundation.infrastructure.exception.KmgFundException;
import kmg.foundation.infrastructure.utils.KmgYamlUtils;
import kmg.tool.domain.logic.io.dtc.DtcLogic;
import kmg.tool.domain.model.io.dtc.DtcDerivedPlaceholderModel;
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

    /**
     * 指定された変換処理を値に適用する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @param value
     *                          元の値
     * @param dtcTransformTypes
     *                          テンプレートの動的変換変換処理の種類
     *
     * @return 変換後の値
     */
    @Override
    public String applyTransformation(final String value, final DtcTransformTypes dtcTransformTypes) {

        String result = KmgString.EMPTY;

        if (value == null) {

            return result;

        }

        switch (dtcTransformTypes) {

            case NONE:
                result = value;
                break;

            case CAPITALIZE:
                // 文字列の最初の文字を大文字に変換
                if (!value.isEmpty()) {

                    result = KmgString.capitalize(value);

                } else {

                    result = value;

                }
                break;

            case TO_UPPER_CASE:
                // すべて大文字に変換
                result = value.toUpperCase();
                break;

            case TO_LOWER_CASE:
                // すべて小文字に変換
                result = value.toLowerCase();
                break;

        }

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

        // 処理なし
    }

    /**
     * YAMLデータからCSVプレースホルダー定義を抽出する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @param yamlData
     *                 解析されたYAMLデータ
     *
     * @return CSVプレースホルダーの定義マップ
     */
    @Override
    public Map<String, String> extractCsvPlaceholderDefinitions(final Map<String, Object> yamlData) {

        final Map<String, String> result = new LinkedHashMap<>();

        // CSVプレースホルダー定義を取得する
        @SuppressWarnings("unchecked")
        final List<Map<String, String>> csvPlaceholders
            = (List<Map<String, String>>) yamlData.get(DtcKeyTypes.CSV_PLACEHOLDERS.getKey());

        if (csvPlaceholders == null) {

            return result;

        }

        for (final Map<String, String> placeholderMap : csvPlaceholders) {

            result.put(placeholderMap.get(DtcKeyTypes.DISPLAY_NAME.getKey()),
                placeholderMap.get(DtcKeyTypes.REPLACEMENT_PATTERN.getKey()));

        }

        return result;

    }

    /**
     * YAMLデータから派生プレースホルダー定義を抽出する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @param yamlData
     *                 解析されたYAMLデータ
     *
     * @return 派生プレースホルダーの定義リスト
     */
    @Override
    public List<DtcDerivedPlaceholderModel> extractDerivedPlaceholderDefinitions(final Map<String, Object> yamlData) {

        final List<DtcDerivedPlaceholderModel> result = new ArrayList<>();

        // 派生プレースホルダー定義を取得する
        @SuppressWarnings("unchecked")
        final List<Map<String, String>> derivedPlaceholders
            = (List<Map<String, String>>) yamlData.get(DtcKeyTypes.DERIVED_PLACEHOLDERS.getKey());

        if (derivedPlaceholders == null) {

            return result;

        }

        for (final Map<String, String> placeholderMap : derivedPlaceholders) {

            final String displayName        = placeholderMap.get(DtcKeyTypes.DISPLAY_NAME.getKey());
            final String replacementPattern = placeholderMap.get(DtcKeyTypes.REPLACEMENT_PATTERN.getKey());
            final String sourceKey          = placeholderMap.get(DtcKeyTypes.SOURCE_KEY.getKey());
            final String transformation     = placeholderMap.get(DtcKeyTypes.TRANSFORMATION.getKey());

            final DtcDerivedPlaceholderModel derivedPlaceholder
                = new DtcDerivedPlaceholderModel(displayName, replacementPattern, sourceKey, transformation);
            result.add(derivedPlaceholder);

        }

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
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                     入力ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     * @param outputPath
     *                     出力ファイルパス
     */
    @Override
    @SuppressWarnings("hiding")
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath) {

        final boolean result = true;

        this.inputPath = inputPath;
        this.templatePath = templatePath;
        this.outputPath = outputPath;

        return result;

    }

    /**
     * テンプレートファイルを読み込みYAMLとして解析する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @return 解析されたYAMLデータ
     *
     * @throws KmgToolException
     *                          テンプレートの読み込みに失敗した場合
     */
    @Override
    public Map<String, Object> loadAndParseTemplate() throws KmgToolException {

        Map<String, Object> result = null;

        try {

            result = KmgYamlUtils.load(this.getTemplatePath());

        } catch (final KmgFundException e) {

            final KmgToolGenMessageTypes genMsgType = KmgToolGenMessageTypes.KMGTOOL_GEN12001;
            final Object[]               genMsgArgs = {
                this.templatePath.toString()
            };
            throw new KmgToolException(genMsgType, genMsgArgs, e);

        }

        return result;

    }

    /**
     * 入力ファイルを処理し、テンプレートに基づいて出力を生成する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @param csvPlaceholderMap
     *                            CSVプレースホルダーの定義マップ
     * @param derivedPlaceholders
     *                            派生プレースホルダーの定義リスト
     * @param templateContent
     *                            テンプレートの内容
     *
     * @throws KmgToolException
     *                          入出力処理に失敗した場合
     */
    @Override
    public void processInputAndGenerateOutput(final Map<String, String> csvPlaceholderMap,
        final List<DtcDerivedPlaceholderModel> derivedPlaceholders, final String templateContent)
        throws KmgToolException {

        // CSVプレースホルダーのキー配列を取得
        final String[] csvPlaceholderKeys     = csvPlaceholderMap.keySet().toArray(new String[0]);
        final String[] csvPlaceholderPatterns = csvPlaceholderMap.values().toArray(new String[0]);

        try (final BufferedReader brInput = Files.newBufferedReader(this.getInputPath());
            final BufferedWriter bwOutput = Files.newBufferedWriter(this.getOutputPath())) {

            String line;

            // 入力ファイルを1行ずつ処理
            while ((line = brInput.readLine()) != null) {

                String         out     = templateContent;
                final String[] csvLine = KmgDelimiterTypes.COMMA.split(line);

                // CSV値を一時保存するマップ
                final Map<String, String> csvValues = new HashMap<>();

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
                    out = out.replace(pattern, value);

                }

                // 派生プレースホルダーを処理
                for (final DtcDerivedPlaceholderModel derivedPlaceholder : derivedPlaceholders) {

                    final String sourceValue = csvValues.get(derivedPlaceholder.getSourceKey());

                    if (sourceValue == null) {

                        continue;

                    }

                    // テンプレートの動的変換変換処理変換
                    final DtcTransformTypes transformationType
                        = DtcTransformTypes.getEnum(derivedPlaceholder.getTransformation());

                    // 変換処理を適用
                    final String derivedValue = this.applyTransformation(sourceValue, transformationType);

                    // テンプレートを置換
                    out = out.replace(derivedPlaceholder.getReplacementPattern(), derivedValue);

                }

                // 変換結果を出力
                bwOutput.write(out);
                bwOutput.newLine();

            }

        } catch (final IOException e) {

            final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.KMGTOOL_GEN12003;
            final Object[]               messageArgs = {
                this.inputPath.toString(), this.templatePath.toString(), this.outputPath.toString(),
            };
            throw new KmgToolException(msgType, messageArgs, e);

        }

    }
}
