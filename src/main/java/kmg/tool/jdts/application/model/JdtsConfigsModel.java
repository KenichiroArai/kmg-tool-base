package kmg.tool.jdts.application.model;

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
     * Javadocタグ設定のタグ構成モデルのリストを返す<br>
     *
     * @return Javadoc追加のタグ設定モデルのリスト
     */
    List<JdtsTagConfigModel> getJdaTagConfigModels();

}
