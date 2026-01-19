package kmg.tool.base.acccrt.application.logic;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.iito.domain.logic.IctoOneLinePatternLogic;

/**
 * アクセサ作成ロジックインタフェース<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
public interface AccessorCreationLogic extends IctoOneLinePatternLogic {

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addItemToRows() throws KmgToolBaseMsgException;

    /**
     * Javadocコメントを書き込み対象に追加する。
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addJavadocCommentToRows() throws KmgToolBaseMsgException;

    /**
     * 型情報を書き込み対象に追加する。
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addTypeToRows() throws KmgToolBaseMsgException;

    /**
     * フィールド宣言から型、項目名、先頭大文字項目に変換する。
     *
     * @since 0.2.4
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    boolean convertFields() throws KmgToolBaseMsgException;

    /**
     * Javadocの変換を行う。
     *
     * @since 0.2.0
     *
     * @return true：変換あり、false：変換なし
     */
    boolean convertJavadoc();

    /**
     * 項目名返す。
     *
     * @since 0.2.0
     *
     * @return 項目名
     */
    String getItem();

    /**
     * Javadocコメントを返す。
     *
     * @since 0.2.0
     *
     * @return Javadocコメント
     */
    String getJavadocComment();

    /**
     * 型を返す。
     *
     * @since 0.2.0
     *
     * @return 型
     */
    String getTyep();

    /**
     * Javadocの解析中かを返す。
     *
     * @since 0.2.0
     *
     * @return true：Javadoc解析中、false：Javadoc解析外
     */
    boolean isInJavadocParsing();

    /**
     * 不要な修飾子を削除する。
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     */
    boolean removeModifier();

}
