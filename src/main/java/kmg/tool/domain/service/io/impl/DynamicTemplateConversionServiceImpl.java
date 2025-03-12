package kmg.tool.domain.service.io.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.domain.service.io.DynamicTemplateConversionService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * テンプレートの動的変換サービス
 */
@Service
public class DynamicTemplateConversionServiceImpl implements DynamicTemplateConversionService {

    /** 入力ファイルパス */
    private Path inputPath;

    /** テンプレートファイルパス */
    private Path templatePath;

    /** 出力ファイルパス */
    private Path outputPath;

    /**
     * YAMLデータからプレースホルダー定義を抽出する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @param yamlData
     *                 解析されたYAMLデータ
     *
     * @return プレースホルダーの定義マップ
     */
    private static Map<String, String> extractPlaceholderDefinitions(final Map<String, Object> yamlData) {

        final Map<String, String> result = new LinkedHashMap<>();

        // プレースホルダー定義を取得する
        @SuppressWarnings("unchecked") // TODO KenichiroArai 2025/03/12 型変化の対応
        final List<Map<String, String>> placeholderDefinitions
            = (List<Map<String, String>>) yamlData.get("placeholderDefinitions");

        // 表示名と置換パターンのマッピングを作成

        for (final Map<String, String> placeholderMap : placeholderDefinitions) {

            result.put(placeholderMap.get("displayName"), placeholderMap.get("replacementPattern"));

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

        /* プレースホルダー定義の取得と変換 */
        final Map<String, String> columnMappings  = DynamicTemplateConversionServiceImpl
            .extractPlaceholderDefinitions(yamlData);
        final String              templateContent = (String) yamlData.get("templateContent");

        /* 入力ファイルの処理と出力 */
        this.processInputAndGenerateOutput(columnMappings, templateContent);

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

        final Yaml yaml = new Yaml();

        try {

            result = yaml.load(Files.newInputStream(this.getTemplatePath()));

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/12 例外
            final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs = {
                this.templatePath.toString()
            };
            throw new KmgToolException(msgType, messageArgs, e);

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
     * @param columnMappings
     *                        プレースホルダーの定義マップ
     * @param templateContent
     *                        テンプレートの内容
     *
     * @throws KmgToolException
     *                          入出力処理に失敗した場合
     */
    private void processInputAndGenerateOutput(final Map<String, String> columnMappings, final String templateContent)
        throws KmgToolException {

        try (final BufferedReader brInput = Files.newBufferedReader(this.getInputPath());
            final BufferedWriter bwOutput = Files.newBufferedWriter(this.getOutputPath())) {

            String line;
            // プレースホルダーのキー配列を取得
            final String[] keyArrays = columnMappings.values().toArray(new String[0]);

            // 入力ファイルを1行ずつ処理
            while ((line = brInput.readLine()) != null) {

                String         out     = templateContent;
                final String[] csvLine = KmgDelimiterTypes.COMMA.split(line);

                // 各プレースホルダーを対応する値で置換
                for (int i = 0; i < csvLine.length; i++) {

                    final String key   = keyArrays[i];
                    final String value = csvLine[i];
                    out = out.replace(key, value);

                }

                // 変換結果を出力
                bwOutput.write(out);
                bwOutput.newLine();

            }

        } catch (final IOException e) {

            final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs = {
                this.templatePath.toString()
            };
            throw new KmgToolException(msgType, messageArgs, e);

        }

    }

}
