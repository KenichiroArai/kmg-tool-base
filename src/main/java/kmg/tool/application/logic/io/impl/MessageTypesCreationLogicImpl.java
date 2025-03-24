package kmg.tool.application.logic.io.impl;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.logic.io.MessageTypesCreationLogic;
import kmg.tool.domain.logic.io.AbstractIctoOneLinePatternLogic;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

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

    /** 項目 */
    private String item;

    /** 項目名 */
    private String itemName;

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean addItemNameToCsvRows() throws KmgToolException {

        boolean result = false;

        if (this.itemName == null) {

            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.KMGTOOL_GEN32000;
            final Object[]               messageArgs  = {};
            throw new KmgToolException(messageTypes, messageArgs);

        }

        super.addCsvRow(this.itemName);
        result = true;

        return result;

    }

    /**
     * 項目を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean addItemToCsvRows() throws KmgToolException {

        boolean result = false;

        if (this.item == null) {

            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.KMGTOOL_GEN32004;
            final Object[]               messageArgs  = {};
            throw new KmgToolException(messageTypes, messageArgs);

        }

        super.addCsvRow(this.item);
        result = true;

        return result;

    }

    /**
     * メッセージの種類定義から項目と項目名に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean convertMessageTypesDefinition() throws KmgToolException {

        boolean result = false;

        // TODO KenichiroArai 2025/03/24 ハードコード

        // 項目と項目名に分ける
        final String[] inputDatas = KmgDelimiterTypes.HALF_EQUAL.split(this.getConvertedLine(), 2);

        // 項目と項目名に分かれないか
        if (inputDatas.length > 2) {
            // 分かれない場合

            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.KMGTOOL_GEN32005;
            final Object[]               messageArgs  = {
                this.getNowLineNumber(), this.getLineOfDataRead(),
            };
            throw new KmgToolException(messageTypes, messageArgs);

        }

        // 項目と項目名に設定
        // TODO KenichiroArai 2025/03/19 ハードコード
        this.item = inputDatas[0]; // 項目
        this.itemName = inputDatas[1]; // 項目名

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
