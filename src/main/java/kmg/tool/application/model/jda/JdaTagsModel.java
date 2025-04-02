package kmg.tool.application.model.jda;

import java.util.List;

/**
 * Javadoc追加のタグモデル<br>
 * YAMLファイルのjavadocTagsセクションを表現するモデル
 * <p>
 * Jdaは、JavadocAppenderの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JdaTagsModel {

    /**
     * 説明を返す<br>
     *
     * @return 説明
     */
    String getDescription();

    /**
     * Javadoc追加のタグ設定モデルのリストを返す<br>
     *
     * @return Javadoc追加のタグ設定モデルのリスト
     */
    List<JdaTagConfigModel> getJavadocAppenderTagConfigModels();

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
     * Javadoc追加のタグ設定モデルのリストを設定する<br>
     *
     * @param jdaTagConfigModels
     *                           Javadoc追加のタグ設定モデルのリスト
     */
    void setJavadocAppenderTagConfigModels(List<JdaTagConfigModel> jdaTagConfigModels);

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
