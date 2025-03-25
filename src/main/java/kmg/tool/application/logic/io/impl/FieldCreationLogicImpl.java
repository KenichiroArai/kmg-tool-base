package kmg.tool.application.logic.io.impl;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDbDataTypeTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.logic.io.FieldCreationLogic;
import kmg.tool.domain.logic.io.AbstractIctoOneLinePatternLogic;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * フィールド作成ロジック実装クラス
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Service
public class FieldCreationLogicImpl extends AbstractIctoOneLinePatternLogic implements FieldCreationLogic {

    /** コメント */
    private String comment;

    /** フィールド */
    private String field;

    /** 型 */
    private String type;

    /**
     * コメントを書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean addCommentToCsvRows() throws KmgToolException {

        boolean result = false;

        if (this.comment == null) {

            // TODO KenichiroArai 2025/03/25 メッセージ コメントがnullです。
            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs  = {};
            throw new KmgToolException(messageTypes, messageArgs);

        }

        super.addCsvRow(this.comment);
        result = true;

        return result;

    }

    /**
     * フィールドを書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean addFieldToCsvRows() throws KmgToolException {

        boolean result = false;

        if (this.field == null) {

            // TODO KenichiroArai 2025/03/25 メッセージ フィールドがnullです。
            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs  = {};
            throw new KmgToolException(messageTypes, messageArgs);

        }

        super.addCsvRow(this.field);
        result = true;

        return result;

    }

    /**
     * 型を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean addTypeToCsvRows() throws KmgToolException {

        boolean result = false;

        if (this.type == null) {

            // TODO KenichiroArai 2025/03/25 メッセージ 型がnullです。
            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs  = {};
            throw new KmgToolException(messageTypes, messageArgs);

        }

        super.addCsvRow(this.type);
        result = true;

        return result;

    }

    /**
     * フィールド宣言からコメント、フィールド、型に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean convertFields() throws KmgToolException {

        boolean result = false;

        final String line = this.getLineOfDataRead();

        if (line == null) {

            return result;

        }

        // TODO KenichiroArai 2025/03/25 ハードコード

        final String[] inputDatas = KmgDelimiterTypes.SERIES_HALF_SPACE.split(line);

        if (inputDatas.length < 3) {

            return result;

        }

        int dataIdx = 0;
        this.comment = inputDatas[dataIdx];
        dataIdx++;
        this.field = new KmgString(inputDatas[dataIdx]).toCamelCase();
        dataIdx++;
        final String dbDataType = inputDatas[dataIdx];
        dataIdx++;

        final KmgDbDataTypeTypes dbDataTypeTypes = KmgDbDataTypeTypes.getEnum(dbDataType);

        if (dbDataTypeTypes == null) {

            this.type = dbDataType;

        } else {

            this.type = dbDataTypeTypes.getType().getTypeName().replaceAll("(\\w+\\.)+", KmgString.EMPTY);

        }

        result = true;
        return result;

    }

    /**
     * コメントを返す。
     *
     * @return コメント。取得できない場合は、null
     */
    @Override
    public String getComment() {

        final String result = this.comment;
        return result;

    }

    /**
     * フィールドを返す。
     *
     * @return フィールド。取得できない場合は、null
     */
    @Override
    public String getField() {

        final String result = this.field;
        return result;

    }

    /**
     * 型を返す。
     *
     * @return 型。取得できない場合は、null
     */
    @Override
    public String getType() {

        final String result = this.type;
        return result;

    }
}
