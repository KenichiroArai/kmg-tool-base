package kmg.tool.domain.model.impl;

import java.util.List;

import kmg.tool.domain.model.JavadocModel;
import kmg.tool.domain.model.JavadocTagModel;
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
    private final String sourceJavadoc;

    /** Javadocタグモデルのリスト */
    private final List<JavadocTagModel> javadocTagModelList;

    /**
     * コンストラクタ<br>
     *
     * @param sourceJavadoc
     *                      Javadoc
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    public JavadocModelImpl(final String sourceJavadoc) throws KmgToolException {

        this.sourceJavadoc = sourceJavadoc;
        final JavadocTagsModelImpl javadocTagsModel = new JavadocTagsModelImpl(sourceJavadoc);
        this.javadocTagModelList = javadocTagsModel.getJavadocTagModelList();

    }

    /**
     * Javadocタグモデルのリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocタグモデルのリスト
     */
    @Override
    public List<JavadocTagModel> getJavadocTagModelList() {

        final List<JavadocTagModel> result = this.javadocTagModelList;
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
    public String getSourceJavadoc() {

        final String result = this.sourceJavadoc;
        return result;

    }
}
