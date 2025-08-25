package kmg.tool.jdoc.domain.model;

/**
 * Javadocモデル<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JavadocModel {

    /**
     * Javadocタグ一覧情報を返す<br>
     *
     * @since 0.1.0
     *
     * @return Javadocタグ一覧情報
     */
    JavadocTagsModel getJavadocTagsModel();

    /**
     * 元のJavadocを返す<br>
     *
     * @since 0.1.0
     *
     * @return 元のJavadoc
     */
    String getSrcJavadoc();

}
