package kmg.tool.base.msgtpcrt.application.logic.impl;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.iito.domain.logic.AbstractIctoOneLinePatternLogic;
import kmg.tool.base.msgtpcrt.application.logic.MessageTypesCreationLogic;
import kmg.tool.base.two2one.application.types.MessageTypesRegexGroupTypes;

/**
 * <h2>メッセージの種類作成ロジック実装クラス</h2>
 * <p>
 * メッセージの種類を定義するYMLファイルを自動生成するためのロジック実装クラスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@Service
public class MessageTypesCreationLogicImpl extends AbstractIctoOneLinePatternLogic
    implements MessageTypesCreationLogic {

    /**
     * メッセージの種類の分割数
     *
     * @since 0.2.0
     */
    private static final int MESSAGE_TYPE_SPLIT_COUNT = 2;

    /**
     * 項目
     *
     * @since 0.2.0
     */
    private String item;

    /**
     * 項目名
     *
     * @since 0.2.0
     */
    private String itemName;

    /**
     * デフォルトコンストラクタ
     *
     * @since 0.2.0
     */
    public MessageTypesCreationLogicImpl() {

        // 処理なし
    }

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @since 0.2.0
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

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN14000;
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
     * @since 0.2.0
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

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN14001;
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
     * @since 0.2.0
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean convertMessageTypesDefinition() throws KmgToolMsgException {

        boolean result = false;

        // 項目と項目名に分ける（例：KMGTOOL_GEN14000=メッセージの種類が指定されていません。）
        final String[] inputDatas = KmgDelimiterTypes.HALF_EQUAL.split(this.getConvertedLine(),
            MessageTypesCreationLogicImpl.MESSAGE_TYPE_SPLIT_COUNT);

        // 項目と項目名に分かれないか
        if (inputDatas.length != MessageTypesCreationLogicImpl.MESSAGE_TYPE_SPLIT_COUNT) {
            // 分かれない場合

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN14002;
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
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @return 項目名
     */
    @Override
    public String getItemName() {

        final String result = this.itemName;
        return result;

    }

}
