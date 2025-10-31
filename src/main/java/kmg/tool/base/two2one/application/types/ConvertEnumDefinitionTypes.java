package kmg.tool.base.two2one.application.types;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.cmn.KmgCmnTypes;

/**
 * 列挙型定義変換の種類<br>
 * <p>
 * 列挙型定義変換ツールで使用する正規表現パターンのグループインデックスを管理する列挙型です。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@SuppressWarnings("nls")
public enum ConvertEnumDefinitionTypes implements KmgCmnTypes<String> {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @since 0.2.0
     */
    NONE("指定無し", "None", "指定無し", -1),

    /**
     * 列挙型定義パターン：フルマッチ
     *
     * @since 0.2.0
     */
    ENUM_DEFINITION_FULL_MATCH("列挙型定義パターン：フルマッチ", "enumDefinitionFullMatch", "列挙型定義パターンの全体マッチ", 0),

    /**
     * 列挙型定義パターン：定数名
     *
     * @since 0.2.0
     */
    ENUM_DEFINITION_CONSTANT_NAME("列挙型定義パターン：定数名", "enumDefinitionConstantName", "列挙型定義パターンの定数名部分", 1),

    /**
     * 列挙型定義パターン：表示名
     *
     * @since 0.2.0
     */
    ENUM_DEFINITION_DISPLAY_NAME("列挙型定義パターン：表示名", "enumDefinitionDisplayName", "列挙型定義パターンの表示名部分", 2),

    /**
     * 列挙型定義パターン：キー
     *
     * @since 0.2.0
     */
    ENUM_DEFINITION_KEY("列挙型定義パターン：キー", "enumDefinitionKey", "列挙型定義パターンのキー部分", 3),

    /**
     * 列挙型定義パターン：詳細
     *
     * @since 0.2.0
     */
    ENUM_DEFINITION_DETAIL("列挙型定義パターン：詳細", "enumDefinitionDetail", "列挙型定義パターンの詳細部分", 4),

    /* 定義：終了 */
    ;

    /**
     * 種類のマップ
     *
     * @since 0.2.0
     */
    private static final Map<String, ConvertEnumDefinitionTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final ConvertEnumDefinitionTypes type : ConvertEnumDefinitionTypes.values()) {

            ConvertEnumDefinitionTypes.VALUES_MAP.put(type.get(), type);

        }

    }

    /**
     * 表示名
     *
     * @since 0.2.0
     */
    private final String displayName;

    /**
     * キー
     *
     * @since 0.2.0
     */
    private final String key;

    /**
     * 詳細情報
     *
     * @since 0.2.0
     */
    private final String detail;

    /**
     * グループインデックス
     *
     * @since 0.2.0
     */
    private final int groupIndex;

    /**
     * デフォルトの種類を返す<br>
     *
     * @since 0.2.0
     *
     * @return デフォルト値
     */
    public static ConvertEnumDefinitionTypes getDefault() {

        final ConvertEnumDefinitionTypes result = NONE;
        return result;

    }

    /**
     * キーに該当する種類を返す<br>
     * <p>
     * 但し、キーが存在しない場合は、指定無し（NONE）を返す。
     * </p>
     *
     * @since 0.2.0
     *
     * @param key
     *            キー
     *
     * @return 種類。指定無し（NONE）：キーが存在しない場合。
     */
    public static ConvertEnumDefinitionTypes getEnum(final String key) {

        ConvertEnumDefinitionTypes result = ConvertEnumDefinitionTypes.VALUES_MAP.get(key);

        if (result == null) {

            result = NONE;

        }
        return result;

    }

    /**
     * 初期値の種類を返す<br>
     *
     * @since 0.2.0
     *
     * @return 初期値
     */
    public static ConvertEnumDefinitionTypes getInitValue() {

        final ConvertEnumDefinitionTypes result = NONE;
        return result;

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.2.0
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
    ConvertEnumDefinitionTypes(final String displayName, final String key, final String detail, final int groupIndex) {

        this.displayName = displayName;
        this.key = key;
        this.detail = detail;
        this.groupIndex = groupIndex;

    }

    /**
     * キーを返す。<br>
     *
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.0
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
