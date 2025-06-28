package kmg.tool.jdts.application.types;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.common.KmgComTypes;

/**
 * KMGテンプレートの種類<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SuppressWarnings("nls")
public enum JdtsConfigKeyTypes implements KmgComTypes<String> {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    NONE("指定無し", "None", "指定無し"),

    /**
     * Javadocタグ設定の構成
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    JDTS_CONFIGS("Javadocタグ設定の構成", "JdtsConfigs", "Javadocタグ設定の構成"),

    /**
     * タグ名
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    TAG_NAME("タグ名", "tagName", "タグ名"),

    /**
     * タグ値
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    TAG_VALUE("タグ値", "tagValue", "タグ値"),

    /**
     * タグの説明
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    TAG_DESCRIPTION("タグの説明", "tagDescription", "タグの説明"),

    /**
     * 配置設定
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    LOCATION("配置設定", "location", "配置設定"),

    /**
     * 配置方法
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    MODE("配置方法", "mode", "配置方法"),

    /**
     * 誤配置時に削除するかどうか
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    REMOVE_IF_MISPLACED("誤配置時に削除するかどうか", "removeIfMisplaced", "指定された場所以外にタグが存在する場合に削除するかどうか"),

    /**
     * 対象要素
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    TARGET_ELEMENTS("対象要素", "targetElements", "手動モードの場合の対象要素"),

    /**
     * 挿入位置
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    INSERT_POSITION("挿入位置", "insertPosition", "挿入位置"),

    /**
     * 上書き設定
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    OVERWRITE("上書き設定", "overwrite", "上書き設定"),

    /* 定義：終了 */
    ;

    /**
     * 種類のマップ
     *
     * @since 0.1.0
     */
    private static final Map<String, JdtsConfigKeyTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final JdtsConfigKeyTypes type : JdtsConfigKeyTypes.values()) {

            JdtsConfigKeyTypes.VALUES_MAP.put(type.get(), type);

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
    public static JdtsConfigKeyTypes getDefault() {

        final JdtsConfigKeyTypes result = NONE;
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
    public static JdtsConfigKeyTypes getEnum(final String key) {

        JdtsConfigKeyTypes result = JdtsConfigKeyTypes.VALUES_MAP.get(key);

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
    public static JdtsConfigKeyTypes getInitValue() {

        final JdtsConfigKeyTypes result = NONE;
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
    JdtsConfigKeyTypes(final String displayName, final String key, final String detail) {

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
