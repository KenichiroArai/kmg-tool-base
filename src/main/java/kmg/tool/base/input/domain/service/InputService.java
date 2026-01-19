package kmg.tool.base.input.domain.service;

import java.nio.file.Path;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;

/**
 * 入力サービスインタフェース
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
public interface InputService {

    /**
     * 入力ファイルパスを返す<br>
     *
     * @since 0.2.0
     *
     * @return 入力ファイルパス
     */
    Path getInputPath();

    /**
     * 初期化する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                  入力ファイルパス
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(final Path inputPath) throws KmgToolBaseMsgException;

    /**
     * 処理する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    boolean process() throws KmgToolBaseMsgException;

}
