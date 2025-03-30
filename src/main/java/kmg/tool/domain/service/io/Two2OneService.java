package kmg.tool.domain.service.io;

import java.nio.file.Path;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * シンプル2入力ファイルから1出力ファイルへの変換ツールサービスインタフェース<br>
 */
public interface Two2OneService extends IoService {

    /**
     * テンプレートファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return テンプレートファイルパス
     */
    Path getTemplatePath();

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                     入力ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     * @param outputPath
     *                     出力ファイルパス
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath) throws KmgToolException;
}
