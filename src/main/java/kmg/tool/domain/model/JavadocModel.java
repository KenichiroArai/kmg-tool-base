package kmg.tool.domain.model;

import java.util.List;

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
     * Javadocタグモデルのリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocタグモデルのリスト
     */
    List<JavadocTagModel> getJavadocTagModelList();

    /**
     * 元のJavadocを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 元のJavadoc
     */
    String getSourceJavadoc();

}
