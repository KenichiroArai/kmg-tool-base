package kmg.tool.application.model.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kmg.tool.application.model.JavadocAppenderTagsModel;
import kmg.tool.application.model.JavadocTagConfigModel;

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
public class JavadocAppenderTagsModelImpl implements JavadocAppenderTagsModel {

    /** タグ */
    private String tag;

    /** 指定値 */
    private String value;

    /** 説明 */
    private String description;

    /** Javadocタグ設定モデルのリスト */
    private List<JavadocTagConfigModel> javadocTagConfigModels;

    /**
     * デフォルトコンストラクタ<br>
     */
    public JavadocAppenderTagsModelImpl() {

        this.javadocTagConfigModels = new ArrayList<>();

    }

    /**
     * コンストラクタ<br>
     *
     * @param yamlData
     *                 YAMLデータ
     */
    @SuppressWarnings("unchecked")
    public JavadocAppenderTagsModelImpl(final Map<String, Object> yamlData) {

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
    public String getDescription() {

        final String result = this.description;
        return result;

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
    public String getTag() {

        final String result = this.tag;
        return result;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {

        final String result = this.value;
        return result;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDescription(final String description) {

        this.description = description;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setJavadocTagConfigModels(final List<JavadocTagConfigModel> javadocTagConfigModels) {

        this.javadocTagConfigModels = javadocTagConfigModels;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTag(final String tag) {

        this.tag = tag;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(final String value) {

        this.value = value;

    }
}
