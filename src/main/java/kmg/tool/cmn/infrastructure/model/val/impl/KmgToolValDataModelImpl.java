package kmg.tool.cmn.infrastructure.model.val.impl;

import kmg.core.infrastructure.model.val.impl.KmgValDataModelImpl;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.msg.KmgToolCmnValMsgTypes;

/**
 * KMGツールバリデーションデータモデル<br>
 * <p>
 * Valは、Validationの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class KmgToolValDataModelImpl extends KmgValDataModelImpl {

    /**
     * KMGメッセージリソース
     *
     * @since 0.1.0
     */
    private KmgMessageSource messageSource;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param messageTypes
     *                     メッセージの種類
     * @param messageArgs
     *                     メッセージの引数
     */
    public KmgToolValDataModelImpl(final KmgToolCmnValMsgTypes messageTypes, final Object[] messageArgs) {

        super(messageTypes, messageArgs);

    }

    /**
     * メッセージを作成して返す。
     *
     * @since 0.1.0
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
