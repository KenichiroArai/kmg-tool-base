package kmg.tool.jdts.application.model;

import java.util.List;
import java.util.UUID;

import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.jdoc.domain.model.JavadocModel;

/**
 * Javadocタグ設定のブロックモデルインタフェース<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JdtsBlockModel {

    /**
     * アノテーションリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return アノテーションリスト
     */
    List<String> getAnnotations();

    /**
     * 区分を返す<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 区分
     */
    JavaClassificationTypes getClassification();

    /**
     * 要素名を返す<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 要素名
     */
    String getElementName();

    /**
     * 識別子を返す<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 識別子
     */
    UUID getId();

    /**
     * Javadocモデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return Javadocモデル
     */
    JavadocModel getJavadocModel();

    /**
     * オリジナルブロックを返す<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return オリジナルブロック
     */
    String getOrgBlock();

    /**
     * 解析する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean parse() throws KmgToolMsgException;

}
