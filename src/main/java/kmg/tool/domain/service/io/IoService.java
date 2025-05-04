package kmg.tool.domain.service.io;

import java.nio.file.Path;

import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * 入出力サービスインタフェース
 */
public interface IoService {

    /**
     * 入力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 入力ファイルパス
     */
    Path getInputPath();

    /**
     * 出力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 出力ファイルパス
     */
    Path getOutputPath();

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    boolean process() throws KmgToolMsgException;

}
