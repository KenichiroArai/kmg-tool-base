package kmg.tool.base.val.model.impl;

import kmg.core.infrastructure.model.val.impl.KmgValDataModelImpl;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.base.cmn.infrastructure.msg.KmgToolBaseCmnValMsg;

/**
 * KMGツールベースバリデーションデータモデル<br>
 * <p>
 * Valは、Validationの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.4
 *
 * @version 0.2.4
 */
public class KmgToolBaseValDataModelImpl extends KmgValDataModelImpl {

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
     * @param messageArgs
     *                     メッセージの引数
     */
    public KmgToolBaseValDataModelImpl(final KmgToolBaseCmnValMsg messageTypes, final Object[] messageArgs) {

        super(messageTypes, messageArgs);

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
