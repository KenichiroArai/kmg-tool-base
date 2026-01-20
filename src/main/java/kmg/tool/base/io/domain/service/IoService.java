package kmg.tool.base.io.domain.service;

import java.nio.file.Path;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;

/**
 * 入出力サービスインタフェース
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
public interface IoService {

    /**
     * 入力ファイルパスを返す<br>
     *
     * @since 0.2.0
     *
     * @return 入力ファイルパス
     */
    Path getInputPath();

    /**
     * 出力ファイルパスを返す<br>
     *
     * @since 0.2.0
     *
     * @return 出力ファイルパス
     */
    Path getOutputPath();

    /**
     * 処理する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    boolean process() throws KmgToolBaseMsgException;

}
