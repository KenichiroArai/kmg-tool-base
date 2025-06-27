package kmg.tool.accessorcreation.application.types;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.common.KmgComTypes;

/**
 * アクセサ作成用正規表現グループの種類<br>
 * <p>
 * アクセサ作成ツールで使用する正規表現パターンのグループインデックスを管理する列挙型です。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SuppressWarnings("nls")
public enum AccessorRegexGroupTypes implements KmgComTypes<String> {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @since 0.1.0
     */
    NONE("指定無し", "None", "指定無し", -1),

    /**
     * privateフィールドパターン：フルマッチ
     *
     * @since 0.1.0
     */
    PRIVATE_FIELD_FULL_MATCH("privateフィールドパターン：フルマッチ", "privateFieldFullMatch", "privateフィールドパターンの全体マッチ", 0),

    /**
     * privateフィールドパターン：型
     *
     * @since 0.1.0
     */
    PRIVATE_FIELD_TYPE("privateフィールドパターン：型", "privateFieldType", "privateフィールドパターンの型部分", 1),

    /**
     * privateフィールドパターン：型（内部グループ）
     *
     * @since 0.1.0
     */
    PRIVATE_FIELD_TYPE_INNER("privateフィールドパターン：型（内部グループ）", "privateFieldTypeInner", "privateフィールドパターンの型部分の内部グループ", 2),

    /**
     * privateフィールドパターン：項目名
     *
     * @since 0.1.0
     */
    PRIVATE_FIELD_ITEM_NAME("privateフィールドパターン：項目名", "privateFieldItemName", "privateフィールドパターンの項目名部分", 3),

    /**
     * 1行Javadocパターン：フルマッチ
     *
     * @since 0.1.0
     */
    SINGLE_LINE_JAVADOC_FULL_MATCH("1行Javadocパターン：フルマッチ", "singleLineJavadocFullMatch", "1行Javadocパターンの全体マッチ", 0),

    /**
     * 1行Javadocパターン：コメント内容
     *
     * @since 0.1.0
     */
    SINGLE_LINE_JAVADOC_COMMENT("1行Javadocパターン：コメント内容", "singleLineJavadocComment", "1行Javadocパターンのコメント内容部分", 1),

    /**
     * 複数行Javadocパターン：フルマッチ
     *
     * @since 0.1.0
     */
    MULTI_LINE_JAVADOC_FULL_MATCH("複数行Javadocパターン：フルマッチ", "multiLineJavadocFullMatch", "複数行Javadocパターンの全体マッチ", 0),

    /**
     * 複数行Javadocパターン：コメント内容
     *
     * @since 0.1.0
     */
    MULTI_LINE_JAVADOC_COMMENT("複数行Javadocパターン：コメント内容", "multiLineJavadocComment", "複数行Javadocパターンのコメント内容部分", 1),

    /* 定義：終了 */
    ;

    /**
     * 種類のマップ
     *
     * @since 0.1.0
     */
    private static final Map<String, AccessorRegexGroupTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final AccessorRegexGroupTypes type : AccessorRegexGroupTypes.values()) {

            AccessorRegexGroupTypes.VALUES_MAP.put(type.get(), type);

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
    public static AccessorRegexGroupTypes getDefault() {

        final AccessorRegexGroupTypes result = NONE;
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
    public static AccessorRegexGroupTypes getEnum(final String key) {

        AccessorRegexGroupTypes result = AccessorRegexGroupTypes.VALUES_MAP.get(key);

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
    public static AccessorRegexGroupTypes getInitValue() {

        final AccessorRegexGroupTypes result = NONE;
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
    AccessorRegexGroupTypes(final String displayName, final String key, final String detail, final int groupIndex) {

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
