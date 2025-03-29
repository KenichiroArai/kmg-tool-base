package kmg.tool.application.logic;

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
