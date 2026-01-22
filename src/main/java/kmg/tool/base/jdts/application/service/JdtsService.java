package kmg.tool.base.jdts.application.service;

import java.nio.file.Path;

import kmg.fund.infrastructure.exception.KmgFundMsgException;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseValException;

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
 * @version 0.2.4
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
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @param targetPath
     *                       対象ファイルパス
     * @param definitionPath
     *                       定義ファイルのパス
     *
     * @throws KmgFundMsgException
     *                                 KMG基盤メッセージ例外
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    boolean initialize(final Path targetPath, final Path definitionPath)
        throws KmgFundMsgException, KmgToolBaseMsgException;

    /**
     * 処理する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgFundMsgException
     *                                 KMG基盤メッセージ例外
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     * @throws KmgToolBaseValException
     *                                 KMGツールバリデーション例外
     */
    boolean process() throws KmgFundMsgException, KmgToolBaseMsgException, KmgToolBaseValException;

}
