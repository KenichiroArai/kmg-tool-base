package kmg.tool.base.mptf.application.service;

import java.nio.file.Path;
import java.util.Map;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolValException;

/**
 * マッピング変換サービスインタフェース<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
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
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @param targetPath
     *                                             対象ファイルパス
     * @param targetValueToReplacementValueMapping
     *                                             対象値と置換値のマッピング
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(final Path targetPath, Map<String, String> targetValueToReplacementValueMapping)
        throws KmgToolMsgException;

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
