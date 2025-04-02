package kmg.tool.domain.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kmg.tool.domain.model.JavadocTagConfigModel;
import kmg.tool.domain.model.JavadocTagsModel;

/**
 * Javadocタグモデル実装<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JavadocTagsModelImpl implements JavadocTagsModel {

    /** Javadocタグ設定モデルのリスト */
    private List<JavadocTagConfigModel> javadocTagConfigModels;

    /**
     * デフォルトコンストラクタ<br>
     */
    public JavadocTagsModelImpl() {

        this.javadocTagConfigModels = new ArrayList<>();

    }

    /**
     * コンストラクタ<br>
     *
     * @param yamlData
     *                 YAMLデータ
     */
    @SuppressWarnings("unchecked")
    public JavadocTagsModelImpl(final Map<String, Object> yamlData) {

        this();

        if (yamlData == null) {

            return;

        }

        /* javadocTagsセクションの取得 */
        final List<Map<String, Object>> javadocTags = (List<Map<String, Object>>) yamlData.get("javadocTags");

        if ((javadocTags == null) || javadocTags.isEmpty()) {

            return;

        }

        /* 各タグ設定の処理 */
        for (final Map<String, Object> tagConfig : javadocTags) {

            if (tagConfig == null) {

                continue;

            }

            /* モデルの作成と追加 */
            final JavadocTagConfigModel model = new JavadocTagConfigModelImpl(tagConfig);
            this.javadocTagConfigModels.add(model);

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JavadocTagConfigModel> getJavadocTagConfigModels() {

        final List<JavadocTagConfigModel> result = this.javadocTagConfigModels;
        return result;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setJavadocTagConfigModels(final List<JavadocTagConfigModel> javadocTagConfigModels) {

        this.javadocTagConfigModels = javadocTagConfigModels;

    }
}
