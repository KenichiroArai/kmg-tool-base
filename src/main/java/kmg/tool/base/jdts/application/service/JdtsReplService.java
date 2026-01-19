package kmg.tool.base.jdts.application.service;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.jdts.application.model.JdtsCodeModel;
import kmg.tool.base.jdts.application.model.JdtsConfigsModel;

/**
 * Javadocタグ設定の入出力サービスインタフェース<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * Replは、Replacementの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
public interface JdtsReplService {

    /**
     * Javadocタグ設定の構成モデルを返す<br>
     *
     * @since 0.2.0
     *
     * @return Javadocタグ設定の構成モデル
     */
    JdtsConfigsModel getJdtsConfigsModel();

    /**
     * 置換後のコードを返す<br>
     *
     * @since 0.2.0
     *
     * @return 置換後のコード
     */
    String getReplaceCode();

    /**
     * 合計置換数を返す。
     *
     * @since 0.2.0
     *
     * @return 合計置換数
     */
    long getTotalReplaceCount();

    /**
     * 初期化する
     *
     * @since 0.2.4
     *
     * @param jdtsConfigsModel
     *                         Javadocタグ設定の構成モデル
     * @param jdtsCodeModel
     *                         Javadocタグ設定のコードモデル
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(JdtsConfigsModel jdtsConfigsModel, JdtsCodeModel jdtsCodeModel) throws KmgToolBaseMsgException;

    /**
     * Javadocを置換する。<br>
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    boolean replace() throws KmgToolBaseMsgException;
}
