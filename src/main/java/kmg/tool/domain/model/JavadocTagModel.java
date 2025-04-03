package kmg.tool.domain.model;

/**
 * Javadocタグモデル<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JavadocTagModel {

    /**
     * 説明を返す<br>
     *
     * @return 説明
     */
    String getDescription();

    /**
     * タグを返す<br>
     *
     * @return タグ
     */
    String getTag();

    /**
     * 指定値を返す<br>
     *
     * @return 指定値
     */
    String getValue();

}
