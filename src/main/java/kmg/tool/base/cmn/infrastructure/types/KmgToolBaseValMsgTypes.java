package kmg.tool.base.cmn.infrastructure.types;

import java.util.HashMap;
import java.util.Map;

import kmg.tool.base.cmn.infrastructure.msg.KmgToolBaseCmnExcMsg;
import kmg.tool.base.cmn.infrastructure.msg.KmgToolBaseCmnValMsg;

/**
 * KMGツールベースバリデーションメッセージの種類<br>
 * <p>
 * Valは、Validationの略。<br>
 * Msgは、Messageの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.4
 *
 * @version 0.2.4
 */
@SuppressWarnings("nls")
public enum KmgToolBaseValMsgTypes implements KmgToolBaseCmnValMsg, KmgToolBaseCmnExcMsg {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @since 0.2.4
     */
    NONE("指定無し"),

    /**
     * YAMLデータが空です。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_VAL13000("YAMLデータが空です。"),

    /**
     * {0}が空ありません。Javadocタグ設定の構成のキーワード=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_VAL13001("{0}が空ありません。Javadocタグ設定の構成のキーワード=[{1}]"),

    /**
     * [{0}]は、[{1}]の場合のみ指定可能です。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_VAL13002("[{0}]は、[{1}]の場合のみ指定可能です。"),

    /**
     * [{0}]が指定可能な値ではありません。[{0}]=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_VAL13003("[{0}]が指定可能な値ではありません。[{0}]=[{1}]"),

    /**
     * [{0}]は対象要素を指定してください。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_VAL13004("[{0}]は対象要素を指定してください。"),

    /**
     * [{0}]が指定されていません。[{0}]のキーワード=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_VAL13005("[{0}]が指定されていません。[{0}]のキーワード=[{1}]"),

    /**
     * [{0}]が存在しません。指定された[{0}]=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_VAL13006("[{0}]が存在しません。指定された[{0}]=[{1}]"),

    /**
     * [{0}]が指定されいません。[{0}]のキーワード=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_VAL13007("[{0}]が指定されいません。[{0}]のキーワード=[{1}]"),

    /**
     * [{0}]が正しく設定されていません。[{0}]=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_VAL13008("[{0}]が正しく設定されていません。[{0}]=[{1}]"),

    /**
     * [{0}]が正しく設定されていません。[{0}]=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_VAL13009("[{0}]が正しく設定されていません。[{0}]=[{1}]"),

    /**
     * Java区分から要素名が取得できません。コード行=[{0}]、Java区分=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_VAL13010("Java区分から要素名が取得できません。コード行=[{0}]、Java区分=[{1}]"),

    /* 定義：終了 */

    ;

    /**
     * 種類のマップ
     *
     * @since 0.2.4
     */
    private static final Map<String, KmgToolBaseValMsgTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final KmgToolBaseValMsgTypes type : KmgToolBaseValMsgTypes.values()) {

            KmgToolBaseValMsgTypes.VALUES_MAP.put(type.get(), type);

        }

    }

    /**
     * 表示名
     *
     * @since 0.2.4
     */
    private final String displayName;

    /**
     * メッセージのキー
     *
     * @since 0.2.4
     */
    private final String key;

    /**
     * メッセージの値
     *
     * @since 0.2.4
     */
    private final String value;

    /**
     * 詳細情報
     *
     * @since 0.2.4
     */
    private final String detail;

    /**
     * デフォルトの種類を返す<br>
     *
     * @since 0.2.4
     *
     * @return デフォルト値
     */
    public static KmgToolBaseValMsgTypes getDefault() {

        final KmgToolBaseValMsgTypes result = NONE;
        return result;

    }

    /**
     * キーに該当する種類を返す<br>
     * <p>
     * 但し、キーが存在しない場合は、指定無し（NONE）を返す。
     * </p>
     *
     * @since 0.2.4
     *
     * @param key
     *            キー
     *
     * @return 種類。指定無し（NONE）：キーが存在しない場合。
     */
    public static KmgToolBaseValMsgTypes getEnum(final String key) {

        KmgToolBaseValMsgTypes result = KmgToolBaseValMsgTypes.VALUES_MAP.get(key);

        if (result == null) {

            result = NONE;

        }
        return result;

    }

    /**
     * 初期値の種類を返す<br>
     *
     * @since 0.2.4
     *
     * @return 初期値
     */
    public static KmgToolBaseValMsgTypes getInitValue() {

        final KmgToolBaseValMsgTypes result = NONE;
        return result;

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.2.4
     *
     * @param displayName
     *                    表示名
     */
    KmgToolBaseValMsgTypes(final String displayName) {

        this.displayName = displayName;
        this.key = super.name();
        this.value = displayName;
        this.detail = displayName;

    }

    /**
     * メッセージのキーを返す。<br>
     *
     * @since 0.2.4
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
     * @since 0.2.4
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
     * @since 0.2.4
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
     * @since 0.2.4
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
     * @since 0.2.4
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
     * @since 0.2.4
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
     * @since 0.2.4
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
