package kmg.tool.domain.model.impl;

import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.domain.model.JavadocTagModel;

/**
 * Javadocタグモデル<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JavadocTagModelImpl implements JavadocTagModel {

    /** 対象文字列 */
    private final String targetStr;

    /** タグ */
    private final KmgJavadocTagTypes tag;

    /** 指定値 */
    private final String value;

    /** 説明 */
    private final String description;

    /**
     * コンストラクタ<br>
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
    public KmgJavadocTagTypes getTag() {

        final KmgJavadocTagTypes result = this.tag;
        return result;

    }

    /**
     * 対象文字列を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
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
     * @return 指定値
     */
    @Override
    public String getValue() {

        final String result = this.value;
        return result;

    }
}
