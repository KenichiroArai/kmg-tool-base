package kmg.tool.base.cmn.infrastructure.exception;

import kmg.core.infrastructure.model.val.KmgValsModel;
import kmg.fund.infrastructure.exception.KmgFundValException;

/**
 * KMGツールベースバリデーション例外<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.4
 *
 * @version 0.2.4
 */
public class KmgToolBaseValException extends KmgFundValException {

    /**
     * デフォルトシリアルバージョンＵＩＤ
     *
     * @since 0.2.4
     */
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ<br>
     *
     * @since 0.2.4
     *
     * @param validationsModel
     *                         KMGバリデーション集合モデル
     */
    public KmgToolBaseValException(final KmgValsModel validationsModel) {

        this(validationsModel, null);

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.2.4
     *
     * @param validationsModel
     *                         KMGバリデーション集合モデル
     * @param cause
     *                         原因
     */
    public KmgToolBaseValException(final KmgValsModel validationsModel, final Throwable cause) {

        super(validationsModel, cause);

    }

}
