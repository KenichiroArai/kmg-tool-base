package kmg.tool.application.types;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.common.KmgComTypes;

/**
 * Javadoc追加のタグの上書きの種類<br>
 * <p>
 * Jdaは、JavadocAppenderの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public enum JdaOverwriteTypes implements KmgComTypes<String> {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", "None", "指定無し"),

    /** 上書きしない */
    NEVER("上書きしない", "never", "上書きしない（既存が存在しない場合のみ追加する）"),

    /** 常に上書き */
    ALWAYS("常に上書き", "always", "常に上書き"),

    /** 既存のバージョンより小さい場合のみ上書き */
    IF_LOWER("既存のバージョンより小さい場合のみ上書き", "ifLower", "既存のバージョンより小さい場合のみ上書き"),

    /* 定義：終了 */
    ;

    /** 種類のマップ */
    private static final Map<String, JdaOverwriteTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final JdaOverwriteTypes type : JdaOverwriteTypes.values()) {

            JdaOverwriteTypes.VALUES_MAP.put(type.get(), type);

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
    public static JdaOverwriteTypes getDefault() {

        final JdaOverwriteTypes result = NONE;
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
    public static JdaOverwriteTypes getEnum(final String key) {

        JdaOverwriteTypes result = JdaOverwriteTypes.VALUES_MAP.get(key);

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
    public static JdaOverwriteTypes getInitValue() {

        final JdaOverwriteTypes result = NONE;
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
    JdaOverwriteTypes(final String displayName, final String key, final String detail) {

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
