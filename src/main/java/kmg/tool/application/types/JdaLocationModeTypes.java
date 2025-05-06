package kmg.tool.application.types;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.common.KmgComTypes;

/**
 * Javadocタグ設定の配置モードの種類<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public enum JdaLocationModeTypes implements KmgComTypes<String> {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", "None", "指定無し"),

    /** 準拠モード */
    COMPLIANT("準拠モード", "compliant", "クラス、インタフェース、列挙型などの直前に自動で配置"),

    /** 手動モード */
    MANUAL("手動モード", "manual", "targetElementsで指定した要素の直前に配置"),

    /* 定義：終了 */
    ;

    /** 種類のマップ */
    private static final Map<String, JdaLocationModeTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final JdaLocationModeTypes type : JdaLocationModeTypes.values()) {

            JdaLocationModeTypes.VALUES_MAP.put(type.get(), type);

        }

    }

    /** 表示名 */
    private final String displayName;

    /** キー */
    private final String key;

    /** 詳細情報 */
    private final String detail;

    /**
     * デフォルトの種類を返す<br>
     *
     * @return デフォルト値
     */
    public static JdaLocationModeTypes getDefault() {

        final JdaLocationModeTypes result = NONE;
        return result;

    }

    /**
     * キーに該当する種類を返す<br>
     * <p>
     * 但し、キーが存在しない場合は、指定無し（NONE）を返す。
     * </p>
     *
     * @param key
     *            キー
     *
     * @return 種類。指定無し（NONE）：キーが存在しない場合。
     */
    public static JdaLocationModeTypes getEnum(final String key) {

        JdaLocationModeTypes result = JdaLocationModeTypes.VALUES_MAP.get(key);

        if (result == null) {

            result = NONE;

        }
        return result;

    }

    /**
     * 初期値の種類を返す<br>
     *
     * @return 初期値
     */
    public static JdaLocationModeTypes getInitValue() {

        final JdaLocationModeTypes result = NONE;
        return result;

    }

    /**
     * コンストラクタ<br>
     *
     * @param displayName
     *                    表示名
     * @param key
     *                    キー
     * @param detail
     *                    詳細情報
     */
    JdaLocationModeTypes(final String displayName, final String key, final String detail) {

        this.displayName = displayName;
        this.key = key;
        this.detail = detail;

    }

    /**
     * キーを返す。<br>
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
