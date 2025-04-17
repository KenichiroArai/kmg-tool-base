package kmg.tool.application.model.jdts.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kmg.tool.application.model.jdts.JdtsConfigsModel;
import kmg.tool.application.model.jdts.JdtsTagConfigModel;

/**
 * Javadocタグ設定の構成モデル<br>
 * YAMLデータのJdtsConfigurationsセクションを表現するモデル
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
public class JdtsConfigsModelImpl implements JdtsConfigsModel {

    /** Javadoc追加のタグ設定モデルのリスト */
    private List<JdtsTagConfigModel> jdtsTagConfigModels;

    /**
     * コンストラクタ<br>
     *
     * @param yamlData
     *                 YAMLデータ
     */
    @SuppressWarnings("unchecked")
    public JdtsConfigsModelImpl(final Map<String, Object> yamlData) {

        this.jdtsTagConfigModels = new ArrayList<>();

        if (yamlData == null) {

            return;

        }

        /* javadocTagsセクションの取得 */
        // TODO KenichiroArai 2025/04/10 ハードコード
        final List<Map<String, Object>> javadocTags = (List<Map<String, Object>>) yamlData.get("JdtsConfigs");

        if ((javadocTags == null) || javadocTags.isEmpty()) {

            return;

        }

        /* 各タグ設定の処理 */
        for (final Map<String, Object> tagConfig : javadocTags) {

            if (tagConfig == null) {

                continue;

            }

            /* モデルの作成と追加 */
            final JdtsTagConfigModel model = new JdtsTagConfigModelImpl(tagConfig);
            this.jdtsTagConfigModels.add(model);

        }

    }

    /**
     * Javadocタグ設定のタグ構成モデルのリストを返す<br>
     *
     * @return Javadocタグ設定のタグ構成モデルのリスト
     */
    @Override
    public List<JdtsTagConfigModel> getJdaTagConfigModels() {

        final List<JdtsTagConfigModel> result = this.jdtsTagConfigModels;
        return result;

    }

}
