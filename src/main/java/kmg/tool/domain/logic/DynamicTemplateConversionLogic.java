package kmg.tool.domain.logic;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import kmg.tool.domain.model.DtcDerivedPlaceholderModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * テンプレートの動的変換ロジックインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface DynamicTemplateConversionLogic extends Closeable {

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
    Path getInputPath();

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
    Path getOutputPath();

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
    Path getTemplatePath();

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
    boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath);

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
    Map<String, Object> loadAndParseTemplate() throws KmgToolException;

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
    Map<String, String> extractCsvPlaceholderDefinitions(final Map<String, Object> yamlData);

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
    List<DtcDerivedPlaceholderModel> extractDerivedPlaceholderDefinitions(final Map<String, Object> yamlData);

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
    void processInputAndGenerateOutput(final Map<String, String> csvPlaceholderMap,
        final List<DtcDerivedPlaceholderModel> derivedPlaceholders, final String templateContent) throws KmgToolException;

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
    String applyTransformation(final String value, final String transformation);

    /**
     * リソースをクローズする。
     *
     * @throws IOException
     *                     入出力例外
     */
    @Override
    void close() throws IOException;
}
