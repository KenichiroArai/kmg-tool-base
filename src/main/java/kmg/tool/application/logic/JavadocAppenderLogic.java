package kmg.tool.application.logic;

import kmg.tool.application.model.jda.JdtsConfigsModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加ロジックインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface JavadocAppenderLogic {

    /**
     * 合計行数を返す。
     *
     * @return 合計行数
     */
    long getTotalRows();

    /**
     * 内容を置換した値を返す。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @param contents
     *                         内容
     * @param jdtsConfigsModel
     *                         Javadocタグ設定の構成モデル
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    String replace(final String contents, JdtsConfigsModel jdtsConfigsModel) throws KmgToolException;

}
