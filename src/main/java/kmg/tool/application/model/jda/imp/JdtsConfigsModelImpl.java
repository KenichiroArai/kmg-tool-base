package kmg.tool.application.model.jda.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kmg.tool.application.model.jda.JdaTagConfigModel;
import kmg.tool.application.model.jda.JdtsConfigsModel;

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
    private List<JdaTagConfigModel> jdaTagConfigModels;

    /**
     * コンストラクタ<br>
     *
     * @param yamlData
     *                 YAMLデータ
     */
    @SuppressWarnings("unchecked")
    public JdtsConfigsModelImpl(final Map<String, Object> yamlData) {

        this.jdaTagConfigModels = new ArrayList<>();

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
            final JdaTagConfigModel model = new JdaTagConfigModelImpl(tagConfig);
            this.jdaTagConfigModels.add(model);

        }

    }

    /**
     * Javadoc追加のタグ設定モデルのリストを返す<br>
     *
     * @return Javadoc追加のタグ設定モデルのリスト
     */
    @Override
    public List<JdaTagConfigModel> getJdaTagConfigModels() {

        final List<JdaTagConfigModel> result = this.jdaTagConfigModels;
        return result;

    }

    /**
     * Javadoc追加のタグ設定モデルのリストを設定する<br>
     *
     * @param jdaTagConfigModels
     *                           Javadoc追加のタグ設定モデルのリスト
     */
    @Override
    public void setJdaTagConfigModels(final List<JdaTagConfigModel> jdaTagConfigModels) {

        this.jdaTagConfigModels = jdaTagConfigModels;

    }

}
