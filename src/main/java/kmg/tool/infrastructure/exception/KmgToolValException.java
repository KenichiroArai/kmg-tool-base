package kmg.tool.infrastructure.exception;

import kmg.core.infrastructure.model.val.KmgValsModel;
import kmg.fund.infrastructure.exception.KmgFundValException;

/**
 * KMGツールバリデーション例外<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class KmgToolValException extends KmgFundValException {

    /**
     * デフォルトシリアルバージョンＵＩＤ
     *
     * @since 0.1.0
     */
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param validationsModel
     *                         KMGバリデーション集合モデル
     */
    public KmgToolValException(final KmgValsModel validationsModel) {

        this(validationsModel, null);

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param validationsModel
     *                         KMGバリデーション集合モデル
     * @param cause
     *                         原因
     */
    public KmgToolValException(final KmgValsModel validationsModel, final Throwable cause) {

        super(validationsModel, cause);

    }

}
