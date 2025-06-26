package kmg.tool.application.logic.two2one.dtc.impl;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.logic.two2one.dtc.MessageTypesCreationLogic;
import kmg.tool.application.types.two2one.MessageTypesRegexGroupTypes;
import kmg.tool.domain.logic.two2one.AbstractIctoOneLinePatternLogic;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;

/**
 * <h2>メッセージの種類作成ロジック実装クラス</h2>
 * <p>
 * メッセージの種類を定義するYMLファイルを自動生成するためのロジック実装クラスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Service
public class MessageTypesCreationLogicImpl extends AbstractIctoOneLinePatternLogic
    implements MessageTypesCreationLogic {

    /** メッセージの種類の分割数 */
    private static final int MESSAGE_TYPE_SPLIT_COUNT = 2;

    /** 項目 */
    private String item;

    /** 項目名 */
    private String itemName;

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean addItemNameToRows() throws KmgToolMsgException {

        boolean result = false;

        if (this.itemName == null) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN32000;
            final Object[]           messageArgs  = {};
            throw new KmgToolMsgException(messageTypes, messageArgs);

        }

        super.addRow(this.itemName);
        result = true;

        return result;

    }

    /**
     * 項目を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean addItemToRows() throws KmgToolMsgException {

        boolean result = false;

        if (this.item == null) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN32004;
            final Object[]           messageArgs  = {};
            throw new KmgToolMsgException(messageTypes, messageArgs);

        }

        super.addRow(this.item);
        result = true;

        return result;

    }

    /**
     * メッセージの種類定義から項目と項目名に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean convertMessageTypesDefinition() throws KmgToolMsgException {

        boolean result = false;

        // 項目と項目名に分ける（例：KMGTOOL_GEN32000=メッセージの種類が指定されていません。）
        final String[] inputDatas = KmgDelimiterTypes.HALF_EQUAL.split(this.getConvertedLine(),
            MessageTypesCreationLogicImpl.MESSAGE_TYPE_SPLIT_COUNT);

        // 項目と項目名に分かれないか
        if (inputDatas.length != MessageTypesCreationLogicImpl.MESSAGE_TYPE_SPLIT_COUNT) {
            // 分かれない場合

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN32005;
            final Object[]           messageArgs  = {
                this.getNowLineNumber(), this.getLineOfDataRead(),
            };
            throw new KmgToolMsgException(messageTypes, messageArgs);

        }

        // 項目と項目名に設定
        this.item = inputDatas[MessageTypesRegexGroupTypes.MESSAGE_TYPE_ITEM.getGroupIndex()]; // 項目
        this.itemName = inputDatas[MessageTypesRegexGroupTypes.MESSAGE_TYPE_ITEM_NAME.getGroupIndex()]; // 項目名

        result = true;
        return result;

    }

    /**
     * 項目を返す。
     *
     * @return 項目
     */
    @Override
    public String getItem() {

        final String result = this.item;
        return result;

    }

    /**
     * 項目名を返す。
     *
     * @return 項目名
     */
    @Override
    public String getItemName() {

        final String result = this.itemName;
        return result;

    }

}
