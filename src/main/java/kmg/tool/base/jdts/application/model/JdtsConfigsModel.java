package kmg.tool.base.jdts.application.model;

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
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface JdtsConfigsModel {

    /**
     * Javadocタグ設定のタグ構成モデルのリストを返す<br>
     *
     * @since 0.2.0
     *
     * @return Javadoc追加のタグ設定モデルのリスト
     */
    List<JdtsTagConfigModel> getJdaTagConfigModels();

}
