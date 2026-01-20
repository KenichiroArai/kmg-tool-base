package kmg.tool.base.msgtpcrt.application.logic;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.iito.domain.logic.IctoOneLinePatternLogic;

/**
 * <h2>メッセージの種類作成ロジックインタフェース</h2>
 * <p>
 * メッセージの種類を定義するYMLファイルを自動生成するためのロジックインタフェースです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
public interface MessageTypesCreationLogic extends IctoOneLinePatternLogic {

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    boolean addItemNameToRows() throws KmgToolBaseMsgException;

    /**
     * 項目を書き込み対象に追加する。
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    boolean addItemToRows() throws KmgToolBaseMsgException;

    /**
     * メッセージの種類定義から項目と項目名に変換する。
     *
     * @since 0.2.4
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    boolean convertMessageTypesDefinition() throws KmgToolBaseMsgException;

    /**
     * 項目を返す。
     *
     * @since 0.2.0
     *
     * @return 項目
     */
    String getItem();

    /**
     * 項目名を返す。
     *
     * @since 0.2.0
     *
     * @return 項目名
     */
    String getItemName();

}
