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

    /** フィールド定義の最小要素数 */
    private static final int FIELD_DEFINITION_MIN_LENGTH = 3;

    /** コメントのインデックス */
    private static final int COMMENT_INDEX = 0;

    /** フィールド名のインデックス */
    private static final int FIELD_NAME_INDEX = 1;

    /** データ型のインデックス */
    private static final int DATA_TYPE_INDEX = 2;

    /** 完全修飾名を削除する正規表現パターン */
    private static final String REMOVE_PACKAGE_NAME_PATTERN = "(\\w+\\.)+";

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

            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.KMGTOOL_GEN32006;
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

            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.KMGTOOL_GEN32007;
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

            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.KMGTOOL_GEN32008;
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

        final String[] inputDatas = KmgDelimiterTypes.SERIES_HALF_SPACE.split(line);

        if (inputDatas.length < FieldCreationLogicImpl.FIELD_DEFINITION_MIN_LENGTH) {

            return result;

        }

        this.comment = inputDatas[FieldCreationLogicImpl.COMMENT_INDEX];
        this.field = new KmgString(inputDatas[FieldCreationLogicImpl.FIELD_NAME_INDEX]).toCamelCase();
        final String dbDataType = inputDatas[FieldCreationLogicImpl.DATA_TYPE_INDEX];

        final KmgDbDataTypeTypes dbDataTypeTypes = KmgDbDataTypeTypes.getEnum(dbDataType);

        if (dbDataTypeTypes == null) {

            this.type = dbDataType;

        } else {

            this.type = dbDataTypeTypes.getType().getTypeName()
                .replaceAll(FieldCreationLogicImpl.REMOVE_PACKAGE_NAME_PATTERN, KmgString.EMPTY);

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
