package kmg.tool.application.logic;

import java.io.IOException;
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
     * 対象のJavaファイルリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 対象のJavaファイルリスト
     */
    List<Path> getJavaFileList();

    /**
     * 新しいJavaファイルを返す。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @param javaFile
     *                    Javaファイル
     * @param insertAtTop
     *                    タグを先頭に挿入するかどうか
     *
     * @throws IOException
     *                     入出力例外
     *
     * @return ファイル内容
     */
    String getNewJavaFile(final Path javaFile, final boolean insertAtTop) throws IOException;

    /**
     * タグマップを取得する<br>
     *
     * @return タグマップ
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
}
