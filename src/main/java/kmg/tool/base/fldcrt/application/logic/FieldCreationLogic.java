package kmg.tool.base.fldcrt.application.logic;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.iito.domain.logic.IctoOneLinePatternLogic;

/**
 * フィールド作成ロジックインターフェース
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
public interface FieldCreationLogic extends IctoOneLinePatternLogic {

    /**
     * コメントを書き込み対象に追加する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    boolean addCommentToRows() throws KmgToolBaseMsgException;

    /**
     * フィールドを書き込み対象に追加する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    boolean addFieldToRows() throws KmgToolBaseMsgException;

    /**
     * 型を書き込み対象に追加する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    boolean addTypeToRows() throws KmgToolBaseMsgException;

    /**
     * フィールドデータを変換する
     *
     * @since 0.2.4
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    boolean convertFields() throws KmgToolBaseMsgException;

    /**
     * コメントを返す
     *
     * @since 0.2.0
     *
     * @return コメント
     */
    String getComment();

    /**
     * フィールドを返す
     *
     * @since 0.2.0
     *
     * @return フィールド
     */
    String getField();

    /**
     * 型を返す
     *
     * @since 0.2.0
     *
     * @return 型
     */
    String getType();
}
