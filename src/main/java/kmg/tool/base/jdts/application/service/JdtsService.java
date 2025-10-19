package kmg.tool.base.jdts.application.service;

import java.nio.file.Path;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolValException;

/**
 * Javadocタグ設定サービスインタフェース<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface JdtsService {

    /**
     * 定義ファイルのパスを返す。
     *
     * @since 0.2.0
     *
     * @return 定義ファイルのパス
     */
    Path getDefinitionPath();

    /**
     * 対象ファイルパス
     *
     * @since 0.2.0
     *
     * @return 対象ファイルパス
     */
    Path getTargetPath();

    /**
     * 初期化する
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @param targetPath
     *                     対象ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(final Path targetPath, final Path templatePath) throws KmgToolMsgException;

    /**
     * 処理する
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    boolean process() throws KmgToolMsgException, KmgToolValException;

}
