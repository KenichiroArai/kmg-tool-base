package kmg.tool.application.logic;

import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.tool.application.model.jda.JdtsConfigsModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc置換ロジックインターフェース<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 */
public interface JavadocReplacementLogic {

    /**
     * 置換後のJavadocを作成する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean createReplacedJavadoc() throws KmgToolException;

    /**
     * 置換後のJavadocを取得する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 置換後のJavadoc
     */
    String getReplacedJavadoc();

    /**
     * 元のJava区分を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 元のJava区分
     */
    JavaClassificationTypes getSrcJavaClassification();

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param srcJavadoc
     *                                元のJavadoc
     * @param jdtsConfigurationsModel
     *                                Javadoc追加のタグモデル
     * @param srcJavaClassification
     *                                元のJava区分
     */
    void initialize(final String srcJavadoc, final JdtsConfigsModel jdtsConfigurationsModel,
        final JavaClassificationTypes srcJavaClassification);
}
