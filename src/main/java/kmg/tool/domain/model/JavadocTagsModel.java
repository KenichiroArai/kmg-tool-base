package kmg.tool.domain.model;

import java.util.List;

/**
 * Javadocタグモデル<br>
 * YAMLファイルのjavadocTagsセクションを表現するモデル
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JavadocTagsModel {

    /**
     * Javadocタグ設定モデルのリストを返す<br>
     *
     * @return Javadocタグ設定モデルのリスト
     */
    List<JavadocTagConfigModel> getJavadocTagConfigModels();

    /**
     * Javadocタグ設定モデルのリストを設定する<br>
     *
     * @param javadocTagConfigModels
     *                               Javadocタグ設定モデルのリスト
     */
    void setJavadocTagConfigModels(List<JavadocTagConfigModel> javadocTagConfigModels);

}
