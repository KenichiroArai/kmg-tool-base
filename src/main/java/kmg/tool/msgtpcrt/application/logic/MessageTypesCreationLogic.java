package kmg.tool.msgtpcrt.application.logic;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.two2one.domain.logic.IctoOneLinePatternLogic;

/**
 * <h2>メッセージの種類作成ロジックインタフェース</h2>
 * <p>
 * メッセージの種類を定義するYMLファイルを自動生成するためのロジックインタフェースです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
public interface MessageTypesCreationLogic extends IctoOneLinePatternLogic {

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addItemNameToRows() throws KmgToolMsgException;

    /**
     * 項目を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addItemToRows() throws KmgToolMsgException;

    /**
     * メッセージの種類定義から項目と項目名に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean convertMessageTypesDefinition() throws KmgToolMsgException;

    /**
     * 項目を返す。
     *
     * @return 項目
     */
    String getItem();

    /**
     * 項目名を返す。
     *
     * @return 項目名
     */
    String getItemName();

}
