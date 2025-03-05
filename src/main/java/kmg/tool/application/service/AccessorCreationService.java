package kmg.tool.application.service;

import kmg.tool.domain.service.IoService;

/**
 * アクセサ作成サービスインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface AccessorCreationService extends IoService {

    /**
     * 初期化する
     *
     * @param inputPath
     *                     入力ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     * @param outputPath
     *                     出力ファイルパス
     * 
     * @return true：成功、false：失敗
     */
    boolean initialize(final java.nio.file.Path inputPath, final java.nio.file.Path templatePath,
        final java.nio.file.Path outputPath);
}
