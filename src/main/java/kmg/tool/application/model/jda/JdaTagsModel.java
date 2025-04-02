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
     * Javadoc追加のタグ設定モデルのリストを返す<br>
     *
     * @return Javadoc追加のタグ設定モデルのリスト
     */
    List<JdaTagConfigModel> getJavadocAppenderTagConfigModels();

    /**
     * Javadoc追加のタグ設定モデルのリストを設定する<br>
     *
     * @param jdaTagConfigModels
     *                           Javadoc追加のタグ設定モデルのリスト
     */
    void setJavadocAppenderTagConfigModels(List<JdaTagConfigModel> jdaTagConfigModels);

}
