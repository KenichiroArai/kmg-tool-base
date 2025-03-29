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
     * タグマップを取得する<br>
     *
     * @return タグマップ
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    Map<String, String> getTagMap() throws KmgToolException;

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
