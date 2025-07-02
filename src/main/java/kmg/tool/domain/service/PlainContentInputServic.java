package kmg.tool.domain.service;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;

/**
 * プレーンコンテンツ入力サービスインタフェース<br>
 * <p>
 * 入力ファイルパスを読み込み、プレーンコンテンツを返すサービスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
public interface PlainContentInputServic extends InputService {

    /**
     * 入力内容を返す
     *
     * @return 入力内容
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    String getContent() throws KmgToolMsgException;

}
