package kmg.tool.application.logic;

import kmg.tool.application.model.jda.JdtsBlockModel;
import kmg.tool.application.model.jda.JdtsConfigsModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグ設定の入出力ロジックインタフェース<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * Replは、Replacementの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JdtsReplLogic {

    /**
     * 置換後のJavadocブロックを作成する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 置換後のJavadocブロック
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    String createReplacedJavadoc() throws KmgToolException;

    /**
     * Javadocタグ設定の構成モデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocタグ設定の構成モデル
     */
    JdtsConfigsModel getJdtsConfigsModel();

    /**
     * 初期化する
     *
     * @param jdtsConfigsModel
     *                         Javadocタグ設定の構成モデル
     * @param jdtsBlockModel
     *                         Javadocタグ設定のブロックモデル
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean initialize(JdtsConfigsModel jdtsConfigsModel, JdtsBlockModel jdtsBlockModel) throws KmgToolException;

}
