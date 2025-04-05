package kmg.tool.domain.model.impl;

import java.util.ArrayList;
import java.util.List;

import kmg.tool.domain.model.JavadocTagsModel;
import kmg.tool.domain.model.JavadocTagModel;

/**
 * Javadocタグ一覧情報<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JavadocTagsModelImpl implements JavadocTagsModel {

    /** Javadocタグモデルのリスト */
    private List<JavadocTagModel> javadocTagModelList;

    /**
     * デフォルトコンストラクタ<br>
     */
    public JavadocTagsModelImpl() {

        this.javadocTagModelList = new ArrayList<>();

    }

    /**
     * コンストラクタ<br>
     *
     * @param javadocTagModelList
     *                            Javadocタグモデルのリスト
     */
    public JavadocTagsModelImpl(final List<JavadocTagModel> javadocTagModelList) {

        this.javadocTagModelList = javadocTagModelList;

    }

    /**
     * Javadocタグモデルのリストを返す<br>
     *
     * @return Javadocタグモデルのリスト
     */
    @Override
    public List<JavadocTagModel> getJavadocTagModelList() {

        final List<JavadocTagModel> result = this.javadocTagModelList;
        return result;

    }

    /**
     * Javadocタグモデルのリストを設定する<br>
     *
     * @param javadocTagModelList
     *                            Javadocタグモデルのリスト
     */
    @Override
    public void setJavadocTagModelList(final List<JavadocTagModel> javadocTagModelList) {

        this.javadocTagModelList = javadocTagModelList;

    }

}
