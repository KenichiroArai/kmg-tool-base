package kmg.tool.base.one2one.domain.service;

import java.nio.file.Path;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.io.domain.service.IoService;

/**
 * 1入力ファイルから1出力ファイルへの変換ツールサービスインタフェース<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface One2OneService extends IoService {

    /**
     * 初期化する
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                   入力ファイルパス
     * @param outputPath
     *                   出力ファイルパス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(Path inputPath, Path outputPath) throws KmgToolMsgException;
}
