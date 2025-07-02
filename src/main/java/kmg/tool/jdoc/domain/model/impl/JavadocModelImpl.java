package kmg.tool.jdoc.domain.model.impl;

import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.jdoc.domain.model.JavadocModel;
import kmg.tool.jdoc.domain.model.JavadocTagsModel;

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
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    public JavadocModelImpl(final String javadoc) throws KmgToolMsgException {

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
