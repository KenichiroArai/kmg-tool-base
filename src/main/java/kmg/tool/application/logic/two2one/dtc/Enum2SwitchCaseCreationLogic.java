package kmg.tool.application.logic.two2one.dtc;

import kmg.tool.domain.logic.two2one.IctoOneLinePatternLogic;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * <h2>列挙型からcase文作成ロジックインタフェース</h2>
 * <p>
 * 列挙型の定義からswitch-case文を自動生成するためのロジックインタフェースです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
public interface Enum2SwitchCaseCreationLogic extends IctoOneLinePatternLogic {

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addItemNameToCsvRows() throws KmgToolMsgException;

    /**
     * 項目を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addItemToCsvRows() throws KmgToolMsgException;

    /**
     * 列挙型定義から項目と項目名に変換する。
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
