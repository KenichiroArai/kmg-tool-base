package kmg.tool.application.logic.io;

import kmg.tool.domain.logic.io.IctoOneLinePatternLogic;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * フィールド作成ロジックインターフェース
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
public interface FieldCreationLogic extends IctoOneLinePatternLogic {

    /**
     * コメントを書き込み対象に追加する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean addCommentToCsvRows() throws KmgToolException;

    /**
     * フィールドを書き込み対象に追加する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean addFieldToCsvRows() throws KmgToolException;

    /**
     * 型を書き込み対象に追加する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean addTypeToCsvRows() throws KmgToolException;

    /**
     * フィールドデータを変換する
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean convertFields() throws KmgToolException;

    /**
     * コメントを返す
     *
     * @return コメント
     */
    String getComment();

    /**
     * フィールドを返す
     *
     * @return フィールド
     */
    String getField();

    /**
     * 型を返す
     *
     * @return 型
     */
    String getType();
}
