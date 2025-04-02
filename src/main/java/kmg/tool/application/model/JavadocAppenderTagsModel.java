package kmg.tool.application.model;

import java.util.List;

/**
 * Javadoc追加のタグモデル<br>
 * YAMLファイルのjavadocTagsセクションを表現するモデル
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JavadocAppenderTagsModel {

    /**
     * 説明を返す<br>
     *
     * @return 説明
     */
    String getDescription();

    /**
     * Javadocタグ設定モデルのリストを返す<br>
     *
     * @return Javadocタグ設定モデルのリスト
     */
    List<JavadocTagConfigModel> getJavadocTagConfigModels();

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

    /**
     * 説明を設定する<br>
     *
     * @param description
     *                    説明
     */
    void setDescription(String description);

    /**
     * Javadocタグ設定モデルのリストを設定する<br>
     *
     * @param javadocTagConfigModels
     *                               Javadocタグ設定モデルのリスト
     */
    void setJavadocTagConfigModels(List<JavadocTagConfigModel> javadocTagConfigModels);

    /**
     * タグを設定する<br>
     *
     * @param tag
     *            タグ
     */
    void setTag(String tag);

    /**
     * 指定値を設定する<br>
     *
     * @param value
     *              指定値
     */
    void setValue(String value);

}
