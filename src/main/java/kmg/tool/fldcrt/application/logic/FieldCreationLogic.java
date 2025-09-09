package kmg.tool.fldcrt.application.logic;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.iito.domain.logic.IctoOneLinePatternLogic;

/**
 * フィールド作成ロジックインターフェース
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface FieldCreationLogic extends IctoOneLinePatternLogic {

    /**
     * コメントを書き込み対象に追加する
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addCommentToRows() throws KmgToolMsgException;

    /**
     * フィールドを書き込み対象に追加する
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addFieldToRows() throws KmgToolMsgException;

    /**
     * 型を書き込み対象に追加する
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addTypeToRows() throws KmgToolMsgException;

    /**
     * フィールドデータを変換する
     *
     * @since 0.1.0
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean convertFields() throws KmgToolMsgException;

    /**
     * コメントを返す
     *
     * @since 0.1.0
     *
     * @return コメント
     */
    String getComment();

    /**
     * フィールドを返す
     *
     * @since 0.1.0
     *
     * @return フィールド
     */
    String getField();

    /**
     * 型を返す
     *
     * @since 0.1.0
     *
     * @return 型
     */
    String getType();
}
