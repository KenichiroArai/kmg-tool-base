package kmg.tool.base.jdoc.domain.model;

/**
 * Javadocモデル<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface JavadocModel {

    /**
     * Javadocタグ一覧情報を返す<br>
     *
     * @since 0.2.0
     *
     * @return Javadocタグ一覧情報
     */
    JavadocTagsModel getJavadocTagsModel();

    /**
     * 元のJavadocを返す<br>
     *
     * @since 0.2.0
     *
     * @return 元のJavadoc
     */
    String getSrcJavadoc();

}
