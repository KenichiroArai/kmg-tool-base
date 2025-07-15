package kmg.tool.two2one.application.types;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.cmn.KmgCmnTypes;

/**
 * メッセージの種類作成用正規表現グループの種類<br>
 * <p>
 * アメッセージの種類作成ツールで使用する正規表現パターンのグループインデックスを管理する列挙型です。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SuppressWarnings("nls")
public enum MessageTypesRegexGroupTypes implements KmgCmnTypes<String> {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @since 0.1.0
     */
    NONE("指定無し", "None", "指定無し", -1),

    /**
     * メッセージの種類パターン：フルマッチ
     *
     * @since 0.1.0
     */
    MESSAGE_TYPE_FULL_MATCH("メッセージの種類パターン：フルマッチ", "messageTypeFullMatch", "メッセージの種類パターンの全体マッチ", 0),

    /**
     * メッセージの種類パターン：項目
     * <p>
     * 例：KMGTOOL_GEN14000
     * </p>
     *
     * @since 0.1.0
     */
    MESSAGE_TYPE_ITEM("メッセージの種類パターン：項目", "messageTypeItem", "メッセージの種類パターンの項目部分", 0),

    /**
     * メッセージの種類パターン：項目名
     * <p>
     * 例：メッセージの種類が指定されていません。
     * </p>
     *
     * @since 0.1.0
     */
    MESSAGE_TYPE_ITEM_NAME("メッセージの種類パターン：項目名", "messageTypeItemName", "メッセージの種類パターンの項目名部分", 1),

    /* 定義：終了 */
    ;

    /**
     * 種類のマップ
     *
     * @since 0.1.0
     */
    private static final Map<String, MessageTypesRegexGroupTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final MessageTypesRegexGroupTypes type : MessageTypesRegexGroupTypes.values()) {

            MessageTypesRegexGroupTypes.VALUES_MAP.put(type.get(), type);

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
     * グループインデックス
     *
     * @since 0.1.0
     */
    private final int groupIndex;

    /**
     * デフォルトの種類を返す<br>
     *
     * @since 0.1.0
     *
     * @return デフォルト値
     */
    public static MessageTypesRegexGroupTypes getDefault() {

        final MessageTypesRegexGroupTypes result = NONE;
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
    public static MessageTypesRegexGroupTypes getEnum(final String key) {

        MessageTypesRegexGroupTypes result = MessageTypesRegexGroupTypes.VALUES_MAP.get(key);

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
    public static MessageTypesRegexGroupTypes getInitValue() {

        final MessageTypesRegexGroupTypes result = NONE;
        return result;

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param displayName
     *                    表示名
     * @param key
     *                    キー
     * @param detail
     *                    詳細情報
     * @param groupIndex
     *                    グループインデックス
     */
    MessageTypesRegexGroupTypes(final String displayName, final String key, final String detail, final int groupIndex) {

        this.displayName = displayName;
        this.key = key;
        this.detail = detail;
        this.groupIndex = groupIndex;

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
     * グループインデックスを返す。<br>
     *
     * @since 0.1.0
     *
     * @return グループインデックス
     */
    public int getGroupIndex() {

        final int result = this.groupIndex;
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
