package kmg.tool.domain.types;

import java.util.HashMap;
import java.util.Map;

import kmg.tool.infrastructure.common.KmgToolComLogMessageTypes;

/**
 * KMGツールログメッセージの種類<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public enum KmgToolLogMessageTypes implements KmgToolComLogMessageTypes {

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
     * テンプレートの動的変換処理を開始します。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG12000("テンプレートの動的変換処理を開始します。"),

    /**
     * テンプレートの動的変換処理を終了します。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG12002("テンプレートの動的変換処理を終了します。"),

    /**
     * CSVファイルに書き込む処理を開始します。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG12004("CSVファイルに書き込む処理を開始します。"),

    /**
     * CSVファイルに書き込む処理を終了します。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG12006("CSVファイルに書き込む処理を終了します。"),

    /**
     * リーダーリソースのクローズ処理中にエラーが発生しました。入力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG13000("リーダーリソースのクローズ処理中にエラーが発生しました。入力ファイルパス=[{0}]"),

    /**
     * ライターリソースのクローズ処理中にエラーが発生しました。出力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG13001("ライターリソースのクローズ処理中にエラーが発生しました。出力ファイルパス=[{0}]"),

    /**
     * クリア処理中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31000("クリア処理中にエラーが発生しました。"),

    /**
     * CSVファイルに書き込み中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31001("CSVファイルに書き込み中にエラーが発生しました。"),

    /**
     * CSVファイルに書き込み完了。名称=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31002("CSVファイルに書き込み完了。名称=[{0}]"),

    /**
     * クリア処理中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31003("クリア処理中にエラーが発生しました。"),

    /**
     * カラムの追加中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31004("カラムの追加中にエラーが発生しました。"),

    /**
     * 1行データの読み込み中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31005("1行データの読み込み中にエラーが発生しました。"),

    /**
     * カラムの追加中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31006("カラムの追加中にエラーが発生しました。"),

    /**
     * 1行データの読み込み中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31007("1行データの読み込み中にエラーが発生しました。"),

    /**
     * カラムの追加中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31008("カラムの追加中にエラーが発生しました。"),

    /**
     * 1行データの読み込み中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31009("1行データの読み込み中にエラーが発生しました。"),

    /**
     * CSVファイルに書き込み中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31010("CSVファイルに書き込み中にエラーが発生しました。"),

    /**
     * CSVファイルに書き込み完了。名称=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31011("CSVファイルに書き込み完了。名称=[{0}]"),

    /**
     * CSVファイルに書き込み中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31012("CSVファイルに書き込み中にエラーが発生しました。"),

    /**
     * クリア処理中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31013("クリア処理中にエラーが発生しました。"),

    /**
     * カラムの追加中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31014("カラムの追加中にエラーが発生しました。"),

    /**
     * 1行データの読み込み中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31015("1行データの読み込み中にエラーが発生しました。"),

    /**
     * CSVファイルに書き込み中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31016("CSVファイルに書き込み中にエラーが発生しました。"),

    /**
     * CSVファイルに書き込み完了。項目=[{0}]、項目名=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31017("CSVファイルに書き込み完了。項目=[{0}]、項目名=[{1}]"),

    /**
     * CSVファイルに書き込み完了。コメント=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG31018("CSVファイルに書き込み完了。コメント=[{0}]"),

    /**
     * クリア処理中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG32005("クリア処理中にエラーが発生しました。"),

    /**
     * 初期化の失敗
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG41000("初期化の失敗"),

    /**
     * 初期化の失敗
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG41001("初期化の失敗"),

    /**
     * 初期化で例外が発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG41002("初期化で例外が発生しました。"),

    /**
     * 実行中にKMGツール例外が発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG41003("実行中にKMGツール例外が発生しました。"),

    /**
     * 実行中に想定外の例外が発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_LOG41004("実行中に想定外の例外が発生しました。"),

    /* 定義：終了 */
    ;

    /**
     * 種類のマップ
     *
     * @since 0.1.0
     */
    private static final Map<String, KmgToolLogMessageTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final KmgToolLogMessageTypes type : KmgToolLogMessageTypes.values()) {

            KmgToolLogMessageTypes.VALUES_MAP.put(type.get(), type);

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
    public static KmgToolLogMessageTypes getDefault() {

        final KmgToolLogMessageTypes result = NONE;
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
    public static KmgToolLogMessageTypes getEnum(final String key) {

        KmgToolLogMessageTypes result = KmgToolLogMessageTypes.VALUES_MAP.get(key);

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
    public static KmgToolLogMessageTypes getInitValue() {

        final KmgToolLogMessageTypes result = NONE;
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
    KmgToolLogMessageTypes(final String displayName) {

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
