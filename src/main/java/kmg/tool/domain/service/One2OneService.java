package kmg.tool.domain.service;

import java.nio.file.Path;

import kmg.core.infrastructure.exception.KmgToolException;

/**
 * 1入力ファイルから1出力ファイルへの変換ツールサービスインタフェース<br>
 */
public interface One2OneService extends IoService {

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
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                   入力ファイルパス
     * @param outputPath
     *                   出力ファイルパス
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean initialize(Path inputPath, Path outputPath) throws KmgToolException;
}
