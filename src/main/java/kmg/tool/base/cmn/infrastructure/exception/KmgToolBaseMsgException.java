package kmg.tool.base.cmn.infrastructure.exception;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.fund.infrastructure.exception.KmgFundMsgException;
import kmg.tool.base.cmn.infrastructure.msg.KmgToolBaseCmnExcMsg;

/**
 * KMGツールベースメッセージ例外<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.4
 *
 * @version 0.2.4
 */
public class KmgToolBaseMsgException extends KmgFundMsgException {

    /**
     * デフォルトシリアルバージョンＵＩＤ
     *
     * @since 0.2.4
     */
    private static final long serialVersionUID = 1L;

    /**
     * KMGメッセージリソース
     *
     * @since 0.2.4
     */
    private KmgMessageSource messageSource;

    /**
     * コンストラクタ<br>
     *
     * @since 0.2.4
     *
     * @param messageTypes
     *                     メッセージの種類
     */
    public KmgToolBaseMsgException(final KmgToolBaseCmnExcMsg messageTypes) {

        this(messageTypes, null, null);

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.2.4
     *
     * @param messageTypes
     *                     メッセージの種類
     * @param messageArgs
     *                     メッセージの引数
     */
    public KmgToolBaseMsgException(final KmgToolBaseCmnExcMsg messageTypes, final Object[] messageArgs) {

        this(messageTypes, messageArgs, null);

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.2.4
     *
     * @param messageTypes
     *                     メッセージの種類
     * @param messageArgs
     *                     メッセージの引数
     * @param cause
     *                     原因
     */
    public KmgToolBaseMsgException(final KmgToolBaseCmnExcMsg messageTypes, final Object[] messageArgs,
        final Throwable cause) {

        super(messageTypes, messageArgs, cause);

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.2.4
     *
     * @param messageTypes
     *                     メッセージの種類
     * @param cause
     *                     原因
     */
    public KmgToolBaseMsgException(final KmgToolBaseCmnExcMsg messageTypes, final Throwable cause) {

        this(messageTypes, null, cause);

    }

    /**
     * メッセージを作成して返す。
     *
     * @since 0.2.4
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
     *
     * @since 0.2.4
     */
    @Override
    protected void createMessageSource() {

        this.messageSource = SpringApplicationContextHelper.getBean(KmgMessageSource.class);

    }

}
