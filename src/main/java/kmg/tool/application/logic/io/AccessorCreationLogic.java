package kmg.tool.application.logic.io;

/**
 * アクセサ作成ロジックインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface AccessorCreationLogic {

    /**
     * Javadocコメントを返す。
     *
     * @param line
     *             ファイル1行データ
     *
     * @return Javadocコメント
     */
    String getJavadocComment(String line);

}
