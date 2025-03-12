package kmg.tool.domain.types;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.common.KmgComTypes;

/**
 * テンプレートの動的変換のキーの種類<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public enum DynamicTemplateConversionKeyTypes implements KmgComTypes<String> {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    NONE("指定無し", "None", "指定無し"),

    /** CSVプレースホルダー定義のキー */
    CSV_PLACEHOLDERS("CSVプレースホルダー定義", "csvPlaceholders", "CSVから直接取得するプレースホルダー定義のキー"),

    /** 派生プレースホルダー定義のキー */
    DERIVED_PLACEHOLDERS("派生プレースホルダー定義", "derivedPlaceholders", "他のプレースホルダーから派生するプレースホルダー定義のキー"),

    /** 表示名のキー */
    DISPLAY_NAME("表示名", "displayName", "表示名のキー"),

    /** 置換パターンのキー */
    REPLACEMENT_PATTERN("置換パターン", "replacementPattern", "置換パターンのキー"),

    /** ソースキーのキー */
    SOURCE_KEY("ソースキー", "sourceKey", "変換元となるプレースホルダーのキー"),

    /** 変換処理のキー */
    TRANSFORMATION("変換処理", "transformation", "変換処理のキー"),

    /** テンプレート内容のキー */
    TEMPLATE_CONTENT("テンプレート内容", "templateContent", "テンプレート内容のキー"),

    /* 定義：終了 */
    ;

    /**
     * 種類のマップ
     *
     * @since 0.1.0
     */
    private static final Map<String, DynamicTemplateConversionKeyTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final DynamicTemplateConversionKeyTypes type : DynamicTemplateConversionKeyTypes.values()) {

            DynamicTemplateConversionKeyTypes.VALUES_MAP.put(type.get(), type);

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
     * デフォルトの種類を返す<br>
     *
     * @since 0.1.0
     *
     * @return デフォルト値
     */
    public static DynamicTemplateConversionKeyTypes getDefault() {

        final DynamicTemplateConversionKeyTypes result = NONE;
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
    public static DynamicTemplateConversionKeyTypes getEnum(final String key) {

        DynamicTemplateConversionKeyTypes result = DynamicTemplateConversionKeyTypes.VALUES_MAP.get(key);

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
    public static DynamicTemplateConversionKeyTypes getInitValue() {

        final DynamicTemplateConversionKeyTypes result = NONE;
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
     */
    DynamicTemplateConversionKeyTypes(final String displayName, final String key, final String detail) {

        this.displayName = displayName;
        this.key = key;
        this.detail = detail;

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
