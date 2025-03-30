package kmg.tool.domain.service.io;

import java.nio.file.Path;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * 1入力ファイルから1出力ファイルへの変換ツールサービスインタフェース<br>
 */
public interface One2OneService extends IoService {

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
