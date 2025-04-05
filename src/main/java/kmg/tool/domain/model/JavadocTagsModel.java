package kmg.tool.domain.model;

import java.util.List;

/**
 * Javadocタグ一覧情報<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JavadocTagsModel {

    /**
     * Javadocタグモデルのリストを返す<br>
     *
     * @return Javadocタグモデルのリスト
     */
    List<JavadocTagModel> getJavadocTagModelList();

    /**
     * Javadocタグモデルのリストを設定する<br>
     *
     * @param javadocTagModelList
     *                            Javadocタグモデルのリスト
     */
    void setJavadocTagModelList(List<JavadocTagModel> javadocTagModelList);

}
