package kmg.tool.base.jdts.application.model;

import java.util.List;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;

/**
 * Javadocタグ設定のコードモデルインタフェース<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
public interface JdtsCodeModel {

    /**
     * Javadocタグ設定のブロックモデルリストを返す<br>
     *
     * @since 0.2.0
     *
     * @return Javadocタグ設定のブロックモデルリスト
     */
    List<JdtsBlockModel> getJdtsBlockModels();

    /**
     * オリジナルコードを返す<br>
     *
     * @since 0.2.0
     *
     * @return オリジナルコード
     */
    String getOrgCode();

    /**
     * 解析する
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    void parse() throws KmgToolBaseMsgException;

}
