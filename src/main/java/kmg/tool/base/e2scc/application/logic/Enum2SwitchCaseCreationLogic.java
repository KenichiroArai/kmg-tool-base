package kmg.tool.base.e2scc.application.logic;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.iito.domain.logic.IctoOneLinePatternLogic;

/**
 * <h2>列挙型からcase文作成ロジックインタフェース</h2>
 * <p>
 * 列挙型の定義からswitch-case文を自動生成するためのロジックインタフェースです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface Enum2SwitchCaseCreationLogic extends IctoOneLinePatternLogic {

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addItemToRows() throws KmgToolMsgException;

    /**
     * 列挙型定義から項目と項目名に変換する。
     *
     * @since 0.2.0
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean convertEnumDefinition() throws KmgToolMsgException;

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
