package kmg.tool.application.model.javadocappender.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kmg.tool.application.model.javadocappender.JavadocAppenderTagConfigModel;
import kmg.tool.application.model.javadocappender.JavadocAppenderTagsModel;

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

    /** Javadoc追加のタグ設定モデルのリスト */
    private List<JavadocAppenderTagConfigModel> javadocAppenderTagConfigModels;

    /**
     * デフォルトコンストラクタ<br>
     */
    public JavadocAppenderTagsModelImpl() {

        this.javadocAppenderTagConfigModels = new ArrayList<>();

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
            final JavadocAppenderTagConfigModel model = new JavadocAppenderTagConfigModelImpl(tagConfig);
            this.javadocAppenderTagConfigModels.add(model);

        }

    }

    /**
     * 説明を返す<br>
     *
     * @return 説明
     */
    @Override
    public String getDescription() {

        final String result = this.description;
        return result;

    }

    /**
     * Javadoc追加のタグ設定モデルのリストを返す<br>
     *
     * @return Javadoc追加のタグ設定モデルのリスト
     */
    @Override
    public List<JavadocAppenderTagConfigModel> getJavadocAppenderTagConfigModels() {

        final List<JavadocAppenderTagConfigModel> result = this.javadocAppenderTagConfigModels;
        return result;

    }

    /**
     * タグを返す<br>
     *
     * @return タグ
     */
    @Override
    public String getTag() {

        final String result = this.tag;
        return result;

    }

    /**
     * 指定値を返す<br>
     *
     * @return 指定値
     */
    @Override
    public String getValue() {

        final String result = this.value;
        return result;

    }

    /**
     * 説明を設定する<br>
     *
     * @param description
     *                    説明
     */
    @Override
    public void setDescription(final String description) {

        this.description = description;

    }

    /**
     * Javadoc追加のタグ設定モデルのリストを設定する<br>
     *
     * @param javadocAppenderTagConfigModels
     *                                       Javadoc追加のタグ設定モデルのリスト
     */
    @Override
    public void setJavadocAppenderTagConfigModels(
        final List<JavadocAppenderTagConfigModel> javadocAppenderTagConfigModels) {

        this.javadocAppenderTagConfigModels = javadocAppenderTagConfigModels;

    }

    /**
     * タグを設定する<br>
     *
     * @param tag
     *            タグ
     */
    @Override
    public void setTag(final String tag) {

        this.tag = tag;

    }

    /**
     * 指定値を設定する<br>
     *
     * @param value
     *              指定値
     */
    @Override
    public void setValue(final String value) {

        this.value = value;

    }
}
