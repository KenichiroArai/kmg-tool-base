package kmg.tool.domain.model.impl;

import kmg.tool.domain.model.JavadocTagsModel;

/**
 * Javadocタグモデル<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JavadocTagModelImpl implements JavadocTagsModel {

    /** タグ */
    private String tag;

    /** 指定値 */
    private String value;

    /** 説明 */
    private String description;

    /**
     * コンストラクタ<br>
     *
     * @param tag
     *                    タグ
     * @param value
     *                    指定値
     * @param description
     *                    説明
     */
    public JavadocTagModelImpl(String tag, String value, String description) {

        this.tag = tag;
        this.value = value;
        this.description = description;

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
}
