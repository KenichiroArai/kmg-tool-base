package kmg.tool.domain.types;

import java.util.HashMap;
import java.util.Map;

import kmg.tool.infrastructure.common.KmgToolComExcMessageTypes;
import kmg.tool.infrastructure.common.KmgToolComGenMessageTypes;

/**
 * KMGツール一般メッセージの種類<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public enum KmgToolGenMessageTypes implements KmgToolComGenMessageTypes, KmgToolComExcMessageTypes {

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
    NONE("指定無し"),

    /**
     * KMGTOOL_GEN12000
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN12000("KMGTOOL_GEN12000"),

    /**
     * KMGTOOL_GEN12001
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN12001("KMGTOOL_GEN12001"),

    /**
     * KMGTOOL_GEN12002
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN12002("KMGTOOL_GEN12002"),

    /**
     * KMGTOOL_GEN12003
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN12003("KMGTOOL_GEN12003"),

    /**
     * KMGTOOL_GEN13000
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13000("KMGTOOL_GEN13000"),

    /**
     * KMGTOOL_GEN13001
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13001("KMGTOOL_GEN13001"),

    /**
     * KMGTOOL_GEN13002
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13002("KMGTOOL_GEN13002"),

    /**
     * KMGTOOL_GEN13003
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13003("KMGTOOL_GEN13003"),

    /**
     * KMGTOOL_GEN13004
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13004("KMGTOOL_GEN13004"),

    /**
     * KMGTOOL_GEN31000
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31000("KMGTOOL_GEN31000"),

    /**
     * KMGTOOL_GEN31001
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31001("KMGTOOL_GEN31001"),

    /**
     * KMGTOOL_GEN31002
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31002("KMGTOOL_GEN31002"),

    /**
     * KMGTOOL_GEN31003
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31003("KMGTOOL_GEN31003"),

    /**
     * KMGTOOL_GEN31004
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31004("KMGTOOL_GEN31004"),

    /**
     * KMGTOOL_GEN31005
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31005("KMGTOOL_GEN31005"),

    /**
     * KMGTOOL_GEN31006
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31006("KMGTOOL_GEN31006"),

    /**
     * KMGTOOL_GEN31007
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31007("KMGTOOL_GEN31007"),

    /**
     * KMGTOOL_GEN31008
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31008("KMGTOOL_GEN31008"),

    /**
     * KMGTOOL_GEN31009
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31009("KMGTOOL_GEN31009"),

    /**
     * KMGTOOL_GEN32000
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32000("KMGTOOL_GEN32000"),

    /**
     * KMGTOOL_GEN32001
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32001("KMGTOOL_GEN32001"),

    /**
     * KMGTOOL_GEN32002
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32002("KMGTOOL_GEN32002"),

    /**
     * KMGTOOL_GEN32003
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32003("KMGTOOL_GEN32003"),

    /**
     * KMGTOOL_GEN32004
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32004("KMGTOOL_GEN32004"),

    /**
     * KMGTOOL_GEN32005
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32005("KMGTOOL_GEN32005"),

    /**
     * KMGTOOL_GEN32009
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32009("KMGTOOL_GEN32009"),

    /**
     * KMGTOOL_GEN32010
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32010("KMGTOOL_GEN32010"),

    /**
     * KMGTOOL_GEN41000
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN41000("KMGTOOL_GEN41000"),

    /**
     * KMGTOOL_GEN41001
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN41001("KMGTOOL_GEN41001"),

    /**
     * KMGTOOL_GEN41002
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN41002("KMGTOOL_GEN41002"),

    /* 定義：終了 */

    ;

    /**
     * 種類のマップ
     *
     * @since 0.1.0
     */
    private static final Map<String, KmgToolGenMessageTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final KmgToolGenMessageTypes type : KmgToolGenMessageTypes.values()) {

            KmgToolGenMessageTypes.VALUES_MAP.put(type.get(), type);

        }

    }

    /**
     * 表示名
     *
     * @since 0.1.0
     */
    private final String displayName;

    /**
     * メッセージのキー
     *
     * @since 0.1.0
     */
    private final String key;

    /**
     * メッセージの値
     *
     * @since 0.1.0
     */
    private final String value;

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
    public static KmgToolGenMessageTypes getDefault() {

        final KmgToolGenMessageTypes result = NONE;
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
    public static KmgToolGenMessageTypes getEnum(final String key) {

        KmgToolGenMessageTypes result = KmgToolGenMessageTypes.VALUES_MAP.get(key);

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
    public static KmgToolGenMessageTypes getInitValue() {

        final KmgToolGenMessageTypes result = NONE;
        return result;

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param displayName
     *                    表示名
     */
    KmgToolGenMessageTypes(final String displayName) {

        this.displayName = displayName;
        this.key = super.name();
        this.value = displayName;
        this.detail = displayName;

    }

    /**
     * メッセージのキーを返す。<br>
     *
     * @since 0.1.0
     *
     * @return メッセージのキー
     *
     * @see #getKey()
     */
    @Override
    public String get() {

        final String result = this.getKey();
        return result;

    }

    /**
     * メッセージのキーを返す。<br>
     *
     * @since 0.1.0
     *
     * @return メッセージのキー
     *
     * @see #getKey()
     */
    @Override
    public String getCode() {

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
     * メッセージのキーを返す。<br>
     *
     * @since 0.1.0
     *
     * @return メッセージのキー
     */
    @Override
    public String getKey() {

        final String result = this.key;
        return result;

    }

    /**
     * メッセージの値を返す。
     *
     * @since 0.1.0
     *
     * @return メッセージの値
     */
    @Override
    public String getValue() {

        final String result = this.value;
        return result;

    }

    /**
     * メッセージのキーを返す。<br>
     *
     * @since 0.1.0
     *
     * @return メッセージのキー
     *
     * @see #getKey()
     */
    @Override
    public String toString() {

        final String result = this.getKey();
        return result;

    }

}
