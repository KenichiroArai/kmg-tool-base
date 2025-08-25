package kmg.tool.io.domain.service;

import java.nio.file.Path;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;

/**
 * 入出力サービスインタフェース
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface IoService {

    /**
     * 入力ファイルパスを返す<br>
     *
     * @since 0.1.0
     *
     * @return 入力ファイルパス
     */
    Path getInputPath();

    /**
     * 出力ファイルパスを返す<br>
     *
     * @since 0.1.0
     *
     * @return 出力ファイルパス
     */
    Path getOutputPath();

    /**
     * 処理する
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean process() throws KmgToolMsgException;

}
