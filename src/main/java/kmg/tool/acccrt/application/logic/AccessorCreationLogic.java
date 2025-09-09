package kmg.tool.acccrt.application.logic;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.iito.domain.logic.IctoOneLinePatternLogic;

/**
 * アクセサ作成ロジックインタフェース<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface AccessorCreationLogic extends IctoOneLinePatternLogic {

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addItemToRows() throws KmgToolMsgException;

    /**
     * Javadocコメントを書き込み対象に追加する。
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addJavadocCommentToRows() throws KmgToolMsgException;

    /**
     * 型情報を書き込み対象に追加する。
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
     * フィールド宣言から型、項目名、先頭大文字項目に変換する。
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
     * Javadocの変換を行う。
     *
     * @since 0.1.0
     *
     * @return true：変換あり、false：変換なし
     */
    boolean convertJavadoc();

    /**
     * 項目名返す。
     *
     * @since 0.1.0
     *
     * @return 項目名
     */
    String getItem();

    /**
     * Javadocコメントを返す。
     *
     * @since 0.1.0
     *
     * @return Javadocコメント
     */
    String getJavadocComment();

    /**
     * 型を返す。
     *
     * @since 0.1.0
     *
     * @return 型
     */
    String getTyep();

    /**
     * Javadocの解析中かを返す。
     *
     * @since 0.1.0
     *
     * @return true：Javadoc解析中、false：Javadoc解析外
     */
    boolean isInJavadocParsing();

    /**
     * 不要な修飾子を削除する。
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     */
    boolean removeModifier();

}
