package kmg.tool.application.model.jda;

import java.util.List;

/**
 * Javadocタグ設定の構成モデルインタフェース<br>
 * YAMLファイルのJdtsConfigurationsセクションを表現するモデル
 * <p>
 * Jdtsは、JavadocTagSetterの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JdtsConfigsModel {

    /**
     * Javadoc追加のタグ設定モデルのリストを返す<br>
     *
     * @return Javadoc追加のタグ設定モデルのリスト
     */
    List<JdaTagConfigModel> getJdaTagConfigModels();

    /**
     * Javadoc追加のタグ設定モデルのリストを設定する<br>
     *
     * @param jdaTagConfigModels
     *                           Javadoc追加のタグ設定モデルのリスト
     */
    void setJdaTagConfigModels(List<JdaTagConfigModel> jdaTagConfigModels);

}
