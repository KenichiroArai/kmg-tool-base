package kmg.tool.jdts.application.service;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.jdts.application.model.JdtsCodeModel;
import kmg.tool.jdts.application.model.JdtsConfigsModel;

/**
 * Javadocタグ設定の入出力サービスインタフェース<br>
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
public interface JdtsReplService {

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
     * 置換後のコードを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 置換後のコード
     */
    String getReplaceCode();

    /**
     * 合計置換数を返す。
     *
     * @return 合計置換数
     */
    long getTotalReplaceCount();

    /**
     * 初期化する
     *
     * @param jdtsConfigsModel
     *                         Javadocタグ設定の構成モデル
     * @param jdtsCodeModel
     *                         Javadocタグ設定のコードモデル
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(JdtsConfigsModel jdtsConfigsModel, JdtsCodeModel jdtsCodeModel) throws KmgToolMsgException;

    /**
     * Javadocを置換する。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean replace() throws KmgToolMsgException;
}
