package kmg.tool.base.one2one.domain.service;

import java.nio.file.Path;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.io.domain.service.IoService;

/**
 * 1入力ファイルから1出力ファイルへの変換ツールサービスインタフェース<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
public interface One2OneService extends IoService {

    /**
     * 初期化する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                   入力ファイルパス
     * @param outputPath
     *                   出力ファイルパス
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    boolean initialize(Path inputPath, Path outputPath) throws KmgToolBaseMsgException;
}
