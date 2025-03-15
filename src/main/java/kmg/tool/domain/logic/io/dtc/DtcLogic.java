package kmg.tool.domain.logic.io.dtc;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * テンプレートの動的変換ロジックインタフェース<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 *
 * @author KenichiroArai
 */
public interface DtcLogic extends Closeable {

    /**
     * 読み込み中のデータをクリアする。
     *
     * @return true：成功、false：失敗
     */
    boolean clearReadingData();

    /**
     * リソースをクローズする。
     *
     * @throws IOException
     *                     入出力例外
     */
    @Override
    void close() throws IOException;

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
     * YAMLデータを返す。
     *
     * @return YAMLデータ
     */
    Map<String, Object> getYamlData();

    /**
     * 初期化する
     *
     * @param inputPath
     *                     入力ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     * @param outputPath
     *                     出力ファイルパス
     *
     * @throws KmgToolException
     *                          KMGツール例外
     *
     * @return true：成功、false：失敗
     */
    boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath) throws KmgToolException;

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
    boolean loadCsvPlaceholderDefinitions() throws KmgToolException;

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
    boolean loadDerivedPlaceholderDefinitions() throws KmgToolException;

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
    boolean loadTemplate() throws KmgToolException;

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
    boolean loadTemplateContent() throws KmgToolException;

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
    void processInputAndGenerateOutput() throws KmgToolException;

    /**
     * 1行データを読み込む。
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean readOneLineOfData() throws KmgToolException;
}
