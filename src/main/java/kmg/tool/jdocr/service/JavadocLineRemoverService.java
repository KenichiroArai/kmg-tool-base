package kmg.tool.jdocr.service;

import java.nio.file.Path;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;

/**
 * Javadoc行削除サービスインタフェース<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JavadocLineRemoverService {

    /**
     * 初期化する
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                  入力ファイルのパス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(final Path inputPath) throws KmgToolMsgException;

    /**
     * 処理する
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean process() throws KmgToolMsgException;
}
