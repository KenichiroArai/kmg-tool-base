package kmg.tool.dtc.domain.types;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.cmn.KmgCmnTypes;
import kmg.core.infrastructure.type.KmgString;

/**
 * テンプレートの動的変換変換処理の種類<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SuppressWarnings("nls")
public enum DtcTransformTypes implements KmgCmnTypes<String> {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @since 0.1.0
     */
    NONE("指定無し", "none", "指定無し"),

    /**
     * 文字列の最初の文字を大文字に変換
     *
     * @since 0.1.0
     */
    CAPITALIZE("文字列の最初の文字を大文字に変換", "capitalize", "文字列の最初の文字を大文字に変換する"),

    /**
     * すべて大文字に変換
     *
     * @since 0.1.0
     */
    TO_UPPER_CASE("すべて大文字に変換", "toUpperCase", "文字列のすべての文字を大文字に変換する"),

    /**
     * すべて小文字に変換
     *
     * @since 0.1.0
     */
    TO_LOWER_CASE("すべて小文字に変換", "toLowerCase", "文字列のすべての文字を小文字に変換する"),

    /* 定義：終了 */
    ;

    /**
     * 種類のマップ
     *
     * @since 0.1.0
     */
    private static final Map<String, DtcTransformTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final DtcTransformTypes type : DtcTransformTypes.values()) {

            DtcTransformTypes.VALUES_MAP.put(type.get(), type);

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
    public static DtcTransformTypes getDefault() {

        final DtcTransformTypes result = NONE;
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
    public static DtcTransformTypes getEnum(final String key) {

        DtcTransformTypes result = DtcTransformTypes.VALUES_MAP.get(key);

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
    public static DtcTransformTypes getInitValue() {

        final DtcTransformTypes result = NONE;
        return result;

    }

    /**
     * 文字列の最初の文字を大文字に変換<br>
     *
     * @since 0.1.0
     *
     * @param value
     *              変換対象の文字列
     *
     * @return 最初の文字を大文字に変換した値
     */
    private static String capitalize(final String value) {

        if (value.isEmpty()) {

            return value;

        }

        final String result = KmgString.capitalize(value);
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
    DtcTransformTypes(final String displayName, final String key, final String detail) {

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

    /**
     * 指定された文字列を変換する<br>
     *
     * @since 0.1.0
     *
     * @param value
     *              変換対象の文字列
     *
     * @return 変換後の文字列
     */
    public String transform(final String value) {

        String result = value;

        if (KmgString.isEmpty(result)) {

            return result;

        }

        result = switch (this) {

            case NONE          -> value;
            case CAPITALIZE    -> DtcTransformTypes.capitalize(value);
            case TO_UPPER_CASE -> value.toUpperCase();
            case TO_LOWER_CASE -> value.toLowerCase();

        };

        return result;

    }
}
