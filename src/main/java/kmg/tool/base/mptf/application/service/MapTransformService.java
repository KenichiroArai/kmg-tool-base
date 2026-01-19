package kmg.tool.base.mptf.application.service;

import java.nio.file.Path;
import java.util.Map;

import kmg.fund.infrastructure.exception.KmgFundMsgException;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseValException;

/**
 * マッピング変換サービスインタフェース<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
public interface MapTransformService {

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
     *                                             対象ファイルパス
     * @param targetValueToReplacementValueMapping
     *                                             対象値と置換値のマッピング
     *
     * @throws KmgFundMsgException
     *                             KMG基盤メッセージ例外
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(final Path targetPath, Map<String, String> targetValueToReplacementValueMapping)
        throws KmgFundMsgException, KmgToolBaseMsgException;

    /**
     * 処理する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgFundMsgException
     *                             KMG基盤メッセージ例外
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     * @throws KmgToolBaseValException
     *                             KMGツールバリデーション例外
     */
    boolean process() throws KmgFundMsgException, KmgToolBaseMsgException, KmgToolBaseValException;

}
