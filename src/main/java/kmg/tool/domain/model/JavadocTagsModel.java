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
public interface JavadocTagsModel {

    /**
     * 説明を返す<br>
     *
     * @return 説明
     */
    String getDescription();

    /**
     * 元のJavadocを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 元のJavadoc
     */
    String getJavadoc();

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
