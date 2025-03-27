package kmg.tool.application.service;

import java.nio.file.Path;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加サービスインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface JavadocAppenderService {

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

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean process() throws KmgToolException;

}
