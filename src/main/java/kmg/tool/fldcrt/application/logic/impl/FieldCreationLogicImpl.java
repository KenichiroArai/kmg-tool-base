package kmg.tool.fldcrt.application.logic.impl;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDbDataTypeTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.domain.logic.two2one.AbstractIctoOneLinePatternLogic;
import kmg.tool.fldcrt.application.logic.FieldCreationLogic;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;

/**
 * フィールド作成ロジック実装クラス データベースのフィールド定義から、Javaのフィールド定義を生成するためのロジッククラスです。 入力されたフィールド定義（コメント、フィールド名、データ型）を解析し、
 * Javaのフィールド定義に変換する機能を提供します。
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Service
public class FieldCreationLogicImpl extends AbstractIctoOneLinePatternLogic implements FieldCreationLogic {

    /** フィールド定義の最小要素数（コメント、フィールド名、データ型の3要素） */
    private static final int FIELD_DEFINITION_MIN_LENGTH = 3;

    /** コメントのインデックス（フィールド定義の1番目の要素） */
    private static final int COMMENT_INDEX = 0;

    /** フィールド名のインデックス（フィールド定義の2番目の要素） */
    private static final int FIELD_NAME_INDEX = 1;

    /** データ型のインデックス（フィールド定義の3番目の要素） */
    private static final int DATA_TYPE_INDEX = 2;

    /** 完全修飾名を削除する正規表現パターン（例：java.lang.String → String） */
    private static final String REMOVE_PACKAGE_NAME_PATTERN = "(\\w+\\.)+"; //$NON-NLS-1$

    /** フィールドのコメント */
    private String comment;

    /** フィールド名 */
    private String field;

    /** フィールドの型 */
    private String type;

    /**
     * コメントを書き込み対象に追加する。 保持しているコメントを書き込み対象の中間の行に追加します。 コメントが設定されていない場合は例外をスローします。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             コメントが設定されていない場合
     */
    @Override
    public boolean addCommentToRows() throws KmgToolMsgException {

        boolean result = false;

        /* コメントの存在チェック */
        if (this.comment == null) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN32006;
            final Object[]           messageArgs  = {};
            throw new KmgToolMsgException(messageTypes, messageArgs);

        }

        /* コメントの追加 */
        super.addRow(this.comment);
        result = true;

        return result;

    }

    /**
     * フィールドを書き込み対象に追加する。 保持しているフィールド名を書き込み対象の中間の行に追加します。 フィールド名が設定されていない場合は例外をスローします。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             フィールド名が設定されていない場合
     */
    @Override
    public boolean addFieldToRows() throws KmgToolMsgException {

        boolean result = false;

        /* フィールド名の存在チェック */
        if (this.field == null) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN32007;
            final Object[]           messageArgs  = {};
            throw new KmgToolMsgException(messageTypes, messageArgs);

        }

        /* フィールド名の追加 */
        super.addRow(this.field);
        result = true;

        return result;

    }

    /**
     * 型を書き込み対象に追加する。 保持している型情報を書き込み対象の中間の行に追加します。 型情報が設定されていない場合は例外をスローします。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             型情報が設定されていない場合
     */
    @Override
    public boolean addTypeToRows() throws KmgToolMsgException {

        boolean result = false;

        /* 型情報の存在チェック */
        if (this.type == null) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN32008;
            final Object[]           messageArgs  = {};
            throw new KmgToolMsgException(messageTypes, messageArgs);

        }

        /* 型情報の追加 */
        super.addRow(this.type);
        result = true;

        return result;

    }

    /**
     * フィールド宣言からコメント、フィールド、型に変換する。<br>
     * <p>
     * 入力された行データをコメント、フィールド名、型情報に分解して保持します。 フィールド名はキャメルケースに変換され、型情報はパッケージ名が除去されます。
     * </p>
     *
     * @return true：変換成功、false：変換失敗（入力データが不正な場合）
     *
     * @throws KmgToolMsgException
     *                             データ変換時にエラーが発生した場合
     */
    @Override
    public boolean convertFields() throws KmgToolMsgException {

        boolean result = false;

        /* 入力データの取得 */
        final String line = this.getLineOfDataRead();

        if (line == null) {

            return result;

        }

        /* 入力データの分解 */
        final String[] inputDatas = KmgDelimiterTypes.SERIES_HALF_SPACE.split(line);

        if (inputDatas.length < FieldCreationLogicImpl.FIELD_DEFINITION_MIN_LENGTH) {

            return result;

        }

        /* データの設定 */
        this.comment = inputDatas[FieldCreationLogicImpl.COMMENT_INDEX];
        this.field = new KmgString(inputDatas[FieldCreationLogicImpl.FIELD_NAME_INDEX]).toCamelCase();
        final String dbDataType = inputDatas[FieldCreationLogicImpl.DATA_TYPE_INDEX];

        /* 型情報の変換 */
        final KmgDbDataTypeTypes dbDataTypeTypes = KmgDbDataTypeTypes.getEnum(dbDataType);

        if (dbDataTypeTypes == KmgDbDataTypeTypes.NONE) {

            this.type = dbDataType;

        } else {

            this.type = dbDataTypeTypes.getType().getTypeName()
                .replaceAll(FieldCreationLogicImpl.REMOVE_PACKAGE_NAME_PATTERN, KmgString.EMPTY);

        }

        result = true;
        return result;

    }

    /**
     * コメントを返す。 保持しているフィールドのコメントを返します。
     *
     * @return コメント。取得できない場合は、null
     */
    @Override
    public String getComment() {

        final String result = this.comment;
        return result;

    }

    /**
     * フィールドを返す。 保持しているフィールド名を返します。
     *
     * @return フィールド名。取得できない場合は、null
     */
    @Override
    public String getField() {

        final String result = this.field;
        return result;

    }

    /**
     * 型を返す。 保持しているフィールドの型情報を返します。
     *
     * @return 型情報。取得できない場合は、null
     */
    @Override
    public String getType() {

        final String result = this.type;
        return result;

    }
}
