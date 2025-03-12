package kmg.tool.domain.service.io.impl;

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
import kmg.tool.domain.service.io.DynamicTemplateConversionService;
import kmg.tool.domain.types.DynamicTemplateConversionKeyTypes;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * テンプレートの動的変換サービス
 */
@Service
public class DynamicTemplateConversionServiceImpl implements DynamicTemplateConversionService {

    /**
     * 派生プレースホルダー定義を保持するクラス
     */
    private static class DerivedPlaceholder {

        /**
         * 表示名
         */
        private final String displayName;

        /**
         * 置換パターン
         */
        private final String replacementPattern;

        /**
         * ソースキー
         */
        private final String sourceKey;

        /**
         * 変換処理
         */
        private final String transformation;

        /**
         * コンストラクタ
         *
         * @param displayName
         *                           表示名
         * @param replacementPattern
         *                           置換パターン
         * @param sourceKey
         *                           ソースキー
         * @param transformation
         *                           変換処理
         */
        public DerivedPlaceholder(final String displayName, final String replacementPattern, final String sourceKey,
            final String transformation) {

            this.displayName = displayName;
            this.replacementPattern = replacementPattern;
            this.sourceKey = sourceKey;
            this.transformation = transformation;

        }

        /**
         * 表示名を返す
         *
         * @return 表示名
         */
        public String getDisplayName() {

            return this.displayName;

        }

        /**
         * 置換パターンを返す
         *
         * @return 置換パターン
         */
        public String getReplacementPattern() {

            return this.replacementPattern;

        }

        /**
         * ソースキーを返す
         *
         * @return ソースキー
         */
        public String getSourceKey() {

            return this.sourceKey;

        }

        /**
         * 変換処理を返す
         *
         * @return 変換処理
         */
        public String getTransformation() {

            return this.transformation;

        }
    }

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
     *                       元の値
     * @param transformation
     *                       適用する変換処理
     *
     * @return 変換後の値
     */
    private static String applyTransformation(final String value, final String transformation) {

        String result = KmgString.EMPTY;

        if (value == null) {

            return result;

        }

        switch (transformation) {

            case "capitalizeFirst":
                // 最初の文字を大文字に変換
                if (!value.isEmpty()) {

                    result = Character.toUpperCase(value.charAt(0)) + (value.length() > 1 ? value.substring(1) : "");

                } else {

                    result = value;

                }
                break;

            case "toUpperCase":
                // すべて大文字に変換
                result = value.toUpperCase();
                break;

            case "toLowerCase":
                // すべて小文字に変換
                result = value.toLowerCase();
                break;

            // 他の変換処理をここに追加可能
            default:
                result = value;
                break;

        }

        return result;

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
    private static Map<String, String> extractCsvPlaceholderDefinitions(final Map<String, Object> yamlData) {

        final Map<String, String> result = new LinkedHashMap<>();

        // CSVプレースホルダー定義を取得する
        @SuppressWarnings("unchecked")
        final List<Map<String, String>> csvPlaceholders
            = (List<Map<String, String>>) yamlData.get(DynamicTemplateConversionKeyTypes.CSV_PLACEHOLDERS.getKey());

        if (csvPlaceholders == null) {

            return result;

        }

        for (final Map<String, String> placeholderMap : csvPlaceholders) {

            result.put(placeholderMap.get(DynamicTemplateConversionKeyTypes.DISPLAY_NAME.getKey()),
                placeholderMap.get(DynamicTemplateConversionKeyTypes.REPLACEMENT_PATTERN.getKey()));

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
    private static List<DerivedPlaceholder> extractDerivedPlaceholderDefinitions(final Map<String, Object> yamlData) {

        final List<DerivedPlaceholder> result = new ArrayList<>();

        // 派生プレースホルダー定義を取得する
        @SuppressWarnings("unchecked")
        final List<Map<String, String>> derivedPlaceholders
            = (List<Map<String, String>>) yamlData.get(DynamicTemplateConversionKeyTypes.DERIVED_PLACEHOLDERS.getKey());

        if (derivedPlaceholders == null) {

            return result;

        }

        for (final Map<String, String> placeholderMap : derivedPlaceholders) {

            final String displayName        = placeholderMap
                .get(DynamicTemplateConversionKeyTypes.DISPLAY_NAME.getKey());
            final String replacementPattern = placeholderMap
                .get(DynamicTemplateConversionKeyTypes.REPLACEMENT_PATTERN.getKey());
            final String sourceKey          = placeholderMap.get(DynamicTemplateConversionKeyTypes.SOURCE_KEY.getKey());
            final String transformation     = placeholderMap
                .get(DynamicTemplateConversionKeyTypes.TRANSFORMATION.getKey());

            final DerivedPlaceholder derivedPlaceholder
                = new DerivedPlaceholder(displayName, replacementPattern, sourceKey, transformation);
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
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath) {

        final boolean result = true;

        this.inputPath = inputPath;
        this.templatePath = templatePath;
        this.outputPath = outputPath;

        return result;

    }

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean process() throws KmgToolException {

        boolean result = false;

        /* テンプレートの読み込みと解析 */
        final Map<String, Object> yamlData = this.loadAndParseTemplate();

        /* プレースホルダー定義の取得 */
        final Map<String, String>      csvPlaceholderMap   = DynamicTemplateConversionServiceImpl
            .extractCsvPlaceholderDefinitions(yamlData);
        final List<DerivedPlaceholder> derivedPlaceholders = DynamicTemplateConversionServiceImpl
            .extractDerivedPlaceholderDefinitions(yamlData);
        final String                   templateContent     = (String) yamlData
            .get(DynamicTemplateConversionKeyTypes.TEMPLATE_CONTENT.getKey());

        /* 入力ファイルの処理と出力 */
        this.processInputAndGenerateOutput(csvPlaceholderMap, derivedPlaceholders, templateContent);

        result = true;
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
    private Map<String, Object> loadAndParseTemplate() throws KmgToolException {

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
    private void processInputAndGenerateOutput(final Map<String, String> csvPlaceholderMap,
        final List<DerivedPlaceholder> derivedPlaceholders, final String templateContent) throws KmgToolException {

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
                for (final DerivedPlaceholder derivedPlaceholder : derivedPlaceholders) {

                    final String sourceValue = csvValues.get(derivedPlaceholder.getSourceKey());

                    if (sourceValue == null) {

                        continue;

                    }

                    // 変換処理を適用
                    final String derivedValue = DynamicTemplateConversionServiceImpl.applyTransformation(sourceValue,
                        derivedPlaceholder.getTransformation());

                    // テンプレートを置換
                    out = out.replace(derivedPlaceholder.getReplacementPattern(), derivedValue);

                }

                // 変換結果を出力
                bwOutput.write(out);
                bwOutput.newLine();

            }

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/12 例外
            final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs = {
                this.templatePath.toString()
            };
            throw new KmgToolException(msgType, messageArgs, e);

        }

    }

}
