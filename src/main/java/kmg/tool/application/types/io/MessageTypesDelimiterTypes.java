package kmg.tool.application.types.io;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.common.KmgComTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;

/**
 * メッセージの種類用区切り文字の種類<br>
 * <p>
 * メッセージの種類作成ツールで使用する区切り文字と分割数を管理する列挙型です。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public enum MessageTypesDelimiterTypes implements KmgComTypes<String> {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @since 0.1.0
     */
    NONE("指定無し", "None", "指定無し", null, -1),

    /**
     * メッセージの種類パターン：区切り文字
     * <p>
     * 例：KMGTOOL_GEN32000=メッセージの種類が指定されていません。
     * </p>
     *
     * @since 0.1.0
     */
    MESSAGE_TYPE_DELIMITER("メッセージの種類パターン：区切り文字", "messageTypeDelimiter", "メッセージの種類パターンの区切り文字",
        KmgDelimiterTypes.HALF_EQUAL, 2),

    /* 定義：終了 */
    ;

    /**
     * 種類のマップ
     *
     * @since 0.1.0
     */
    private static final Map<String, MessageTypesDelimiterTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final MessageTypesDelimiterTypes type : MessageTypesDelimiterTypes.values()) {

            MessageTypesDelimiterTypes.VALUES_MAP.put(type.get(), type);

        }

    }

    /**
     * 表示名
     *
     * @since 0.1.0
     */
    private final String displayName;

    /**
     * キー
     *
     * @since 0.1.0
     */
    private final String key;

    /**
     * 詳細情報
     *
     * @since 0.1.0
     */
    private final String detail;

    /**
     * 区切り文字の種類
     *
     * @since 0.1.0
     */
    private final KmgDelimiterTypes delimiterType;

    /**
     * 分割数
     *
     * @since 0.1.0
     */
    private final int splitCount;

    /**
     * デフォルトの種類を返す<br>
     *
     * @since 0.1.0
     *
     * @return デフォルト値
     */
    public static MessageTypesDelimiterTypes getDefault() {

        final MessageTypesDelimiterTypes result = NONE;
        return result;

    }

    /**
     * キーに該当する種類を返す<br>
     * <p>
     * 但し、キーが存在しない場合は、指定無し（NONE）を返す。
     * </p>
     *
     * @since 0.1.0
     *
     * @param key
     *            キー
     *
     * @return 種類。指定無し（NONE）：キーが存在しない場合。
     */
    public static MessageTypesDelimiterTypes getEnum(final String key) {

        MessageTypesDelimiterTypes result = MessageTypesDelimiterTypes.VALUES_MAP.get(key);

        if (result == null) {

            result = NONE;

        }
        return result;

    }

    /**
     * 初期値の種類を返す<br>
     *
     * @since 0.1.0
     *
     * @return 初期値
     */
    public static MessageTypesDelimiterTypes getInitValue() {

        final MessageTypesDelimiterTypes result = NONE;
        return result;

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param displayName
     *                      表示名
     * @param key
     *                      キー
     * @param detail
     *                      詳細情報
     * @param delimiterType
     *                      区切り文字の種類
     * @param splitCount
     *                      分割数
     */
    MessageTypesDelimiterTypes(final String displayName, final String key, final String detail,
        final KmgDelimiterTypes delimiterType, final int splitCount) {

        this.displayName = displayName;
        this.key = key;
        this.detail = detail;
        this.delimiterType = delimiterType;
        this.splitCount = splitCount;

    }

    /**
     * キーを返す。<br>
     *
     * @since 0.1.0
     *
     * @return キー
     *
     * @see #getKey()
     */
    @Override
    public String get() {

        final String result = this.getKey();
        return result;

    }

    /**
     * 区切り文字の種類を返す。<br>
     *
     * @since 0.1.0
     *
     * @return 区切り文字の種類
     */
    public KmgDelimiterTypes getDelimiterType() {

        final KmgDelimiterTypes result = this.delimiterType;
        return result;

    }

    /**
     * 詳細情報を返す。<br>
     *
     * @since 0.1.0
     *
     * @return 詳細情報
     */
    @Override
    public String getDetail() {

        final String result = this.detail;
        return result;

    }

    /**
     * 表示名を返す。<br>
     * <p>
     * 識別するための表示名を返す。
     * </p>
     *
     * @since 0.1.0
     *
     * @return 表示名
     */
    @Override
    public String getDisplayName() {

        final String result = this.displayName;
        return result;

    }

    /**
     * キーを返す。<br>
     *
     * @since 0.1.0
     *
     * @return キー
     */
    @Override
    public String getKey() {

        final String result = this.key;
        return result;

    }

    /**
     * 分割数を返す。<br>
     *
     * @since 0.1.0
     *
     * @return 分割数
     */
    public int getSplitCount() {

        final int result = this.splitCount;
        return result;

    }

    /**
     * キーを返す。<br>
     *
     * @since 0.1.0
     *
     * @return キー
     *
     * @see #getKey()
     */
    @Override
    public String toString() {

        final String result = this.getKey();
        return result;

    }
}
