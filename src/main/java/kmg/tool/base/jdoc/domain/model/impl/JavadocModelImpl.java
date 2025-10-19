package kmg.tool.base.jdoc.domain.model.impl;

import kmg.tool.base.jdoc.domain.model.JavadocModel;
import kmg.tool.base.jdoc.domain.model.JavadocTagsModel;

/**
 * Javadocモデル<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public class JavadocModelImpl implements JavadocModel {

    /**
     * 元のJavadoc
     *
     * @since 0.2.0
     */
    private final String srcJavadoc;

    /**
     * Javadocタグ一覧情報
     *
     * @since 0.2.0
     */
    private final JavadocTagsModel javadocTagsModel;

    /**
     * コンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param javadoc
     *                Javadoc
     */
    public JavadocModelImpl(final String javadoc) {

        this.srcJavadoc = javadoc;
        this.javadocTagsModel = new JavadocTagsModelImpl(javadoc);

    }

    /**
     * Javadocタグ一覧情報を返す<br>
     *
     * @since 0.2.0
     *
     * @return Javadocタグ一覧情報
     */
    @Override
    public JavadocTagsModel getJavadocTagsModel() {

        final JavadocTagsModel result = this.javadocTagsModel;
        return result;

    }

    /**
     * 元のJavadocを返す<br>
     *
     * @since 0.2.0
     *
     * @return 元のJavadoc
     */
    @Override
    public String getSrcJavadoc() {

        final String result = this.srcJavadoc;
        return result;

    }
}
