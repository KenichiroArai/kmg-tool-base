package kmg.tool.application.service;

import java.nio.file.Path;

import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * Javadoc行削除サービスインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface JavadocLineRemoverService {

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                   入力ファイルのパス
     * @param targetPath
     *                   対象ファイルパス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(final Path inputPath, final Path targetPath) throws KmgToolMsgException;

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean process() throws KmgToolMsgException;
}
