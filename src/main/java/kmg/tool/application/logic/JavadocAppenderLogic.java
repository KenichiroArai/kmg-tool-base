package kmg.tool.application.logic;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加ロジックインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface JavadocAppenderLogic {

    /**
     * 対象のJavaファイル
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean createJavaFileList() throws KmgToolException;

    /**
     * タグマップを作成する<br>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean createTagMap() throws KmgToolException;

    /**
     * 現在のJavaファイルパスを返す。
     *
     * @return 現在のJavaファイルパス
     */
    Path getCurrentJavaFilePath();

    /**
     * 対象のJavaファイルパスのリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @sine 0.1.0
     *
     * @return 対象のJavaファイルリスト
     */
    List<Path> getJavaFilePathList();

    /**
     * タグのマップを取得する<br>
     *
     * @return タグのマップ
     */
    Map<String, String> getTagMap();

    /**
     * 対象ファイルパス
     *
     * @return 対象ファイルパス
     */
    Path getTargetPath();

    /**
     * テンプレートファイルパス
     *
     * @return テンプレートファイルパス
     */
    Path getTemplatePath();

    /**
     * 合計行数を返す。
     *
     * @return 合計行数
     */
    long getTotalRows();

    /**
     * 初期化する
     *
     * @param targetPath
     *                     対象ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean initialize(final Path targetPath, final Path templatePath) throws KmgToolException;

    /**
     * 次のJavaファイルに進む。
     *
     * @return true：ファイルあり、false:ファイルなし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean nextJavaFile() throws KmgToolException;

    /**
     * 現在のJavaファイルにJavadocを設定する。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @param insertAtTop
     *                    タグを先頭に挿入するかどうか
     *
     * @throws KmgToolException
     *                          KMGツール例外
     *
     * @return ファイル内容
     */
    String setJavadoc(final boolean insertAtTop) throws KmgToolException;
}
