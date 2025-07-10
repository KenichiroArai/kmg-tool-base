package kmg.tool.mptf.application.service;

import java.nio.file.Path;
import java.util.Map;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;

/**
 * マッピング変換サービスインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface MapTransformService {

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
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    boolean process() throws KmgToolMsgException, KmgToolValException;

}
