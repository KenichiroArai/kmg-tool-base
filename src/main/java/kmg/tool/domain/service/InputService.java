package kmg.tool.domain.service;

import java.nio.file.Path;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * 入力サービスインタフェース
 */
public interface InputService {

    /**
     * 入力内容を返す
     *
     * @return 入力内容
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    String getContent() throws KmgToolException;

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
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                  入力ファイルパス
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean initialize(final Path inputPath) throws KmgToolException;

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean process() throws KmgToolException;

}
