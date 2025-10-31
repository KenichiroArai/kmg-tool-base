package kmg.tool.base.jdoc.domain.model.impl;

import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.base.jdoc.domain.model.JavadocTagModel;

/**
 * Javadocタグ情報<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public class JavadocTagModelImpl implements JavadocTagModel {

    /**
     * 対象文字列
     *
     * @since 0.2.0
     */
    private final String targetStr;

    /**
     * タグ
     *
     * @since 0.2.0
     */
    private final KmgJavadocTagTypes tag;

    /**
     * 指定値
     *
     * @since 0.2.0
     */
    private final String value;

    /**
     * 説明
     *
     * @since 0.2.0
     */
    private final String description;

    /**
     * コンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param targetStr
     *                    対象文字列
     * @param tag
     *                    タグ
     * @param value
     *                    指定値
     * @param description
     *                    説明
     */
    public JavadocTagModelImpl(final String targetStr, final KmgJavadocTagTypes tag, final String value,
        final String description) {

        this.targetStr = targetStr;
        this.tag = tag;
        this.value = value;
        this.description = description;

    }

    /**
     * 説明を返す<br>
     *
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @return タグ
     */
    @Override
    public KmgJavadocTagTypes getTag() {

        final KmgJavadocTagTypes result = this.tag;
        return result;

    }

    /**
     * 対象文字列を返す<br>
     *
     * @since 0.2.0
     *
     * @return 対象文字列
     */
    @Override
    public String getTargetStr() {

        final String result = this.targetStr;
        return result;

    }

    /**
     * 指定値を返す<br>
     *
     * @since 0.2.0
     *
     * @return 指定値
     */
    @Override
    public String getValue() {

        final String result = this.value;
        return result;

    }
}
