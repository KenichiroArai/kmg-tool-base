package kmg.tool.cmn.infrastructure.exception;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.fund.infrastructure.exception.KmgFundMsgException;
import kmg.tool.cmn.infrastructure.msg.KmgToolCmnExcMsg;

/**
 * KMGツールメッセージ例外<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class KmgToolMsgException extends KmgFundMsgException {

    /**
     * デフォルトシリアルバージョンＵＩＤ
     *
     * @since 0.1.0
     */
    private static final long serialVersionUID = 1L;

    /**
     * KMGメッセージリソース
     *
     * @since 0.1.0
     */
    private KmgMessageSource messageSource;

    /**
     * コンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param messageTypes
     *                     メッセージの種類
     */
    public KmgToolMsgException(final KmgToolCmnExcMsg messageTypes) {

        this(messageTypes, null, null);

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param messageTypes
     *                     メッセージの種類
     * @param messageArgs
     *                     メッセージの引数
     */
    public KmgToolMsgException(final KmgToolCmnExcMsg messageTypes, final Object[] messageArgs) {

        this(messageTypes, messageArgs, null);

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param messageTypes
     *                     メッセージの種類
     * @param messageArgs
     *                     メッセージの引数
     * @param cause
     *                     原因
     */
    public KmgToolMsgException(final KmgToolCmnExcMsg messageTypes, final Object[] messageArgs,
        final Throwable cause) {

        super(messageTypes, messageArgs, cause);

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param messageTypes
     *                     メッセージの種類
     * @param cause
     *                     原因
     */
    public KmgToolMsgException(final KmgToolCmnExcMsg messageTypes, final Throwable cause) {

        this(messageTypes, null, cause);

    }

    /**
     * メッセージを作成して返す。
     *
     * @return メッセージ
     */
    @Override
    protected String createMessage() {

        final String result = this.messageSource.getExcMessage(this.getMessageTypes(), this.getMessageArgs());
        return result;

    }

    /**
     * メッセージソースを作成する。
     */
    @Override
    protected void createMessageSource() {

        this.messageSource = SpringApplicationContextHelper.getBean(KmgMessageSource.class);

    }

}
