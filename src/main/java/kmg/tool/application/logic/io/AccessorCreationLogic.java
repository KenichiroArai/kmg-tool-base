package kmg.tool.application.logic.io;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * アクセサ作成ロジックインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface AccessorCreationLogic {

    /**
     * フィールド宣言から型、項目名、先頭大文字項目に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean convertFields() throws KmgToolException;

    /**
     * Javadocコメントに変換する。
     *
     * @return true：変換あり、false：変換なし
     */
    boolean convertJavadocComment();

    /**
     * 先頭大文字項目返す。
     *
     * @return capitalizedItem 先頭大文字項目
     */
    String getCapitalizedItem();

    /**
     * 変換後の1行データを返す。
     *
     * @return 変換後の1行データ
     */
    String getConvertedLine();

    /**
     * 項目名返す。
     *
     * @return item 項目名
     */
    String getItem();

    /**
     * Javadocコメントを返す。
     *
     * @return Javadocコメント
     */
    String getJavadocComment();

    /**
     * 1行データを返す。
     *
     * @return 1行データ
     */
    String getLine();

    /**
     * 型を返す。
     *
     * @return tyep 型
     */
    String getTyep();

    /**
     * 初期化する。
     *
     * @param line
     *             1行データ
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean initialize(String line) throws KmgToolException;

    /**
     * 不要な修飾子を削除する。
     *
     * @return true：成功、false：失敗
     */
    boolean removeModifier();

}
