package kmg.tool.cmn.infrastructure.common.msg;

import kmg.fund.infrastructure.common.msg.KmgFundComExcMsgTypes;

// TODO KenichiroArai 2025/07/02 ComをCmnに変更する。

/**
 * KMG 共通例外メッセージの種類のインタフェース
 * <p>
 * Cmnは、Commonの略。<br>
 * Excは、Exceptionの略。<br>
 * Msgは、Messageの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface KmgToolComExcMsgTypes extends KmgToolComMsgTypes, KmgFundComExcMsgTypes {

    // 処理なし

}
