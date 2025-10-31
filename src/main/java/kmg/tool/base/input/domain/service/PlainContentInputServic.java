package kmg.tool.base.input.domain.service;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;

/**
 * プレーンコンテンツ入力サービスインタフェース<br>
 * <p>
 * 入力ファイルパスを読み込み、プレーンコンテンツを返すサービスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface PlainContentInputServic extends InputService {

    /**
     * 入力内容を返す
     *
     * @since 0.2.0
     *
     * @return 入力内容
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    String getContent() throws KmgToolMsgException;

}
