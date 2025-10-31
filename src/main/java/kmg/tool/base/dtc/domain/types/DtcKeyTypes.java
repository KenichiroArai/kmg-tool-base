package kmg.tool.base.dtc.domain.types;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.cmn.KmgCmnTypes;

/**
 * テンプレートの動的変換のキーの種類<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@SuppressWarnings("nls")
public enum DtcKeyTypes implements KmgCmnTypes<String> {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @since 0.2.0
     */
    NONE("指定無し", "None", "指定無し"),

    /**
     * 中間プレースホルダー定義のキー
     *
     * @since 0.2.0
     */
    INTERMEDIATE_PLACEHOLDERS("中間プレースホルダー定義", "intermediatePlaceholders", "中間から直接取得するプレースホルダー定義のキー"),

    /**
     * 派生プレースホルダー定義のキー
     *
     * @since 0.2.0
     */
    DERIVED_PLACEHOLDERS("派生プレースホルダー定義", "derivedPlaceholders", "他のプレースホルダーから派生するプレースホルダー定義のキー"),

    /**
     * 表示名のキー
     *
     * @since 0.2.0
     */
    DISPLAY_NAME("表示名", "displayName", "表示名のキー"),

    /**
     * 置換パターンのキー
     *
     * @since 0.2.0
     */
    REPLACEMENT_PATTERN("置換パターン", "replacementPattern", "置換パターンのキー"),

    /**
     * ソースキーのキー
     *
     * @since 0.2.0
     */
    SOURCE_KEY("ソースキー", "sourceKey", "変換元となるプレースホルダーのキー"),

    /**
     * 変換処理のキー
     *
     * @since 0.2.0
     */
    TRANSFORMATION("変換処理", "transformation", "変換処理のキー"),

    /**
     * テンプレート内容のキー
     *
     * @since 0.2.0
     */
    TEMPLATE_CONTENT("テンプレート内容", "templateContent", "テンプレート内容のキー"),

    /* 定義：終了 */
    ;

    /**
     * 種類のマップ
     *
     * @since 0.2.0
     */
    private static final Map<String, DtcKeyTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final DtcKeyTypes type : DtcKeyTypes.values()) {

            DtcKeyTypes.VALUES_MAP.put(type.get(), type);

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
     * デフォルトの種類を返す<br>
     *
     * @since 0.2.0
     *
     * @return デフォルト値
     */
    public static DtcKeyTypes getDefault() {

        final DtcKeyTypes result = NONE;
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
    public static DtcKeyTypes getEnum(final String key) {

        DtcKeyTypes result = DtcKeyTypes.VALUES_MAP.get(key);

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
    public static DtcKeyTypes getInitValue() {

        final DtcKeyTypes result = NONE;
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
     */
    DtcKeyTypes(final String displayName, final String key, final String detail) {

        this.displayName = displayName;
        this.key = key;
        this.detail = detail;

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
