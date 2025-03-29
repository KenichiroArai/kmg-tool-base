package kmg.tool.application.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加ロジックインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface JavadocAppenderLogic {

    /**
     * タグマップを作成する<br>
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    void createTagMap() throws KmgToolException;

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
     *                           Javaファイル
     * @param fileContentBuilder
     *                           ファイル内容ビルダー
     * @param insertAtTop
     *                           タグを先頭に挿入するかどうか
     *
     * @throws IOException
     *                     入出力例外
     *
     * @return ファイル内容
     */
    String getNewJavaFile(final Path javaFile, final StringBuilder fileContentBuilder, final boolean insertAtTop)
        throws IOException;

    /**
     * タグマップを取得する<br>
     *
     * @return タグマップ
     */
    Map<String, String> getTagMap();

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param targetPath
     *                     対象ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean initialize(final Path targetPath, final Path templatePath) throws KmgToolException;
}
