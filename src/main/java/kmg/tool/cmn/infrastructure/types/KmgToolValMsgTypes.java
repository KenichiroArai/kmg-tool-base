package kmg.tool.cmn.infrastructure.types;

import java.util.HashMap;
import java.util.Map;

import kmg.tool.cmn.infrastructure.msg.KmgToolCmnExcMsg;
import kmg.tool.cmn.infrastructure.msg.KmgToolCmnValMsg;

/**
 * KMGツールバリデーションメッセージの種類<br>
 * <p>
 * Valは、Validationの略。<br>
 * Msgは、Messageの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SuppressWarnings("nls")
public enum KmgToolValMsgTypes implements KmgToolCmnValMsg, KmgToolCmnExcMsg {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    NONE("指定無し"),

    /**
     * YAMLデータが空です。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_VAL13000("YAMLデータが空です。"),

    /**
     * {0}が空ありません。Javadocタグ設定の構成のキーワード=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_VAL13001("{0}が空ありません。Javadocタグ設定の構成のキーワード=[{1}]"),

    /**
     * [{0}]は、[{1}]の場合のみ指定可能です。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_VAL13002("[{0}]は、[{1}]の場合のみ指定可能です。"),

    /**
     * [{0}]が指定可能な値ではありません。[{0}]=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_VAL13003("[{0}]が指定可能な値ではありません。[{0}]=[{1}]"),

    /**
     * [{0}]は対象要素を指定してください。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_VAL13004("[{0}]は対象要素を指定してください。"),

    /**
     * [{0}]が指定されていません。[{0}]のキーワード=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_VAL13005("[{0}]が指定されていません。[{0}]のキーワード=[{1}]"),

    /**
     * [{0}]が存在しません。指定された[{0}]=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_VAL13006("[{0}]が存在しません。指定された[{0}]=[{1}]"),

    /**
     * [{0}]が指定されいません。[{0}]のキーワード=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_VAL13007("[{0}]が指定されいません。[{0}]のキーワード=[{1}]"),

    /**
     * [{0}]が正しく設定されていません。[{0}]=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_VAL13008("[{0}]が正しく設定されていません。[{0}]=[{1}]"),

    /**
     * [{0}]が正しく設定されていません。[{0}]=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_VAL13009("[{0}]が正しく設定されていません。[{0}]=[{1}]"),

    /* 定義：終了 */

    ;

    /**
     * 種類のマップ
     *
     * @since 0.1.0
     */
    private static final Map<String, KmgToolValMsgTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final KmgToolValMsgTypes type : KmgToolValMsgTypes.values()) {

            KmgToolValMsgTypes.VALUES_MAP.put(type.get(), type);

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
    public static KmgToolValMsgTypes getDefault() {

        final KmgToolValMsgTypes result = NONE;
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
    public static KmgToolValMsgTypes getEnum(final String key) {

        KmgToolValMsgTypes result = KmgToolValMsgTypes.VALUES_MAP.get(key);

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
    public static KmgToolValMsgTypes getInitValue() {

        final KmgToolValMsgTypes result = NONE;
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
    KmgToolValMsgTypes(final String displayName) {

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
