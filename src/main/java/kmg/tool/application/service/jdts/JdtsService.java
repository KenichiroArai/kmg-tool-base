package kmg.tool.application.service.jdts;

import java.nio.file.Path;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグ設定サービスインタフェース<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 */
public interface JdtsService {

    /**
     * 定義ファイルのパスを返す。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return 定義ファイルのパス
     */
    Path getDefinitionPath();

    /**
     * 対象ファイルパス
     *
     * @return 対象ファイルパス
     */
    Path getTargetPath();

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
