package kmg.tool.application.service;

import java.nio.file.Path;

/**
 * シンプル1入力ファイルから1出力ファイルへの変換ツールサービスインタフェース<br>
 */
public interface SimpleOne2OneService {

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
     * @param inputPath
     *                   入力ファイルパス
     * @param outputPath
     *                   出力ファイルパス
     */
    void initialize(Path inputPath, Path outputPath);

    /**
     * 処理する
     */
    void process();

}
