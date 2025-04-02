package kmg.tool.application.model.jda.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kmg.tool.application.model.jda.JdaTagConfigModel;
import kmg.tool.application.model.jda.JdaTagsModel;

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
public class JdaTagsModelImpl implements JdaTagsModel {

    /** タグ */
    private String tag;

    /** 指定値 */
    private String value;

    /** 説明 */
    private String description;

    /** Javadoc追加のタグ設定モデルのリスト */
    private List<JdaTagConfigModel> jdaTagConfigModels;

    /**
     * デフォルトコンストラクタ<br>
     */
    public JdaTagsModelImpl() {

        this.jdaTagConfigModels = new ArrayList<>();

    }

    /**
     * コンストラクタ<br>
     *
     * @param yamlData
     *                 YAMLデータ
     */
    @SuppressWarnings("unchecked")
    public JdaTagsModelImpl(final Map<String, Object> yamlData) {

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
            final JdaTagConfigModel model = new JdaTagConfigModelImpl(tagConfig);
            this.jdaTagConfigModels.add(model);

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
    public List<JdaTagConfigModel> getJavadocAppenderTagConfigModels() {

        final List<JdaTagConfigModel> result = this.jdaTagConfigModels;
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
     * @param jdaTagConfigModels
     *                           Javadoc追加のタグ設定モデルのリスト
     */
    @Override
    public void setJavadocAppenderTagConfigModels(final List<JdaTagConfigModel> jdaTagConfigModels) {

        this.jdaTagConfigModels = jdaTagConfigModels;

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
