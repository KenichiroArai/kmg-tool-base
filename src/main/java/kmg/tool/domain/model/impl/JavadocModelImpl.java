package kmg.tool.domain.model.impl;

import kmg.tool.domain.model.JavadocModel;
import kmg.tool.domain.model.JavadocTagsModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocモデル<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JavadocModelImpl implements JavadocModel {

    /** 元のJavadoc */
    private final String srcJavadoc;

    /** Javadocタグ一覧情報 */
    private final JavadocTagsModel javadocTagsModel;

    /**
     * コンストラクタ<br>
     *
     * @param javadoc
     *                Javadoc
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    public JavadocModelImpl(final String javadoc) throws KmgToolException {

        this.srcJavadoc = javadoc;
        this.javadocTagsModel = new JavadocTagsModelImpl(javadoc);

    }

    /**
     * Javadocタグ一覧情報を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
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
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 元のJavadoc
     */
    @Override
    public String getSrcJavadoc() {

        final String result = this.srcJavadoc;
        return result;

    }
}
