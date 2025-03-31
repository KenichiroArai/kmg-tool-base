package kmg.tool.domain.types;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.common.KmgComTypes;

/**
 * Java区分の種類<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.2.0
 */
public enum JavaClassificationTypes implements KmgComTypes<String> {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    NONE("指定無し", "None", "指定無し", null),

    /**
     * クラス
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    CLASS("クラス", "class", "クラス", "^\\s*class\\s+\\w+.*"),

    /**
     * インターフェース
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    INTERFACE("インターフェース", "interface", "インターフェース", "^\\s*interface\\s+\\w+.*"),

    /**
     * 列挙型
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    ENUM("列挙型", "enum", "列挙型", "^\\s*enum\\s+\\w+.*"),

    /**
     * アノテーション
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    ANNOTATION("アノテーション", "annotation", "アノテーション", "^\\s*@\\w+.*"),

    /**
     * フィールド
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    FIELD("フィールド", "field", "フィールド", "^\\s*\\w+\\s+\\w+.*"),

    /**
     * メソッド
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    METHOD("メソッド", "method", "メソッド", "^\\s*\\w+\\s+\\w+\\(.*\\).*"),

    /* 定義：終了 */
    ;

    /**
     * 種類のマップ
     *
     * @since 0.1.0
     */
    private static final Map<String, JavaClassificationTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final JavaClassificationTypes type : JavaClassificationTypes.values()) {

            JavaClassificationTypes.VALUES_MAP.put(type.get(), type);

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
     * 区分判定パターン
     *
     * @since 0.2.0
     */
    private final String classificationPattern;

    /**
     * デフォルトの種類を返す<br>
     *
     * @since 0.1.0
     *
     * @return デフォルト値
     */
    public static JavaClassificationTypes getDefault() {

        final JavaClassificationTypes result = NONE;
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
    public static JavaClassificationTypes getEnum(final String key) {

        JavaClassificationTypes result = JavaClassificationTypes.VALUES_MAP.get(key);

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
    public static JavaClassificationTypes getInitValue() {

        final JavaClassificationTypes result = NONE;
        return result;

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param displayName
     *                              表示名
     * @param key
     *                              キー
     * @param detail
     *                              詳細情報
     * @param classificationPattern
     *                              区分判定パターン
     */
    JavaClassificationTypes(final String displayName, final String key, final String detail,
        final String classificationPattern) {

        this.displayName = displayName;
        this.key = key;
        this.detail = detail;
        this.classificationPattern = classificationPattern;

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
     * 区分判定パターンを返す。<br>
     *
     * @since 0.2.0
     *
     * @return 区分判定パターン
     */
    public String getClassificationPattern() {

        final String result = this.classificationPattern;
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
