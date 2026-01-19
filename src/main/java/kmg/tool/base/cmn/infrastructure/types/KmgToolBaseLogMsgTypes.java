package kmg.tool.base.cmn.infrastructure.types;

import java.util.HashMap;
import java.util.Map;

import kmg.tool.base.cmn.infrastructure.msg.KmgToolBaseCmnLogMsg;

/**
 * KMGツールベースログメッセージの種類<br>
 * <p>
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
public enum KmgToolBaseLogMsgTypes implements KmgToolBaseCmnLogMsg {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @since 0.2.4
     */
    NONE("指定無し"),

    /**
     * 中間ファイルに書き込み中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG01000("中間ファイルに書き込み中にエラーが発生しました。"),

    /**
     * 中間ファイルに書き込み完了。名称=[{0}]、 項目名=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG01001("中間ファイルに書き込み完了。名称=[{0}]、 項目名=[{1}]"),

    /**
     * クリア処理中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG01002("クリア処理中にエラーが発生しました。"),

    /**
     * カラムの追加中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG01003("カラムの追加中にエラーが発生しました。"),

    /**
     * 1行データの読み込み中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG01004("1行データの読み込み中にエラーが発生しました。"),

    /**
     * テンプレートの動的変換処理を開始します。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG03000("テンプレートの動的変換処理を開始します。"),

    /**
     * テンプレートの動的変換処理を終了します。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG03001("テンプレートの動的変換処理を終了します。"),

    /**
     * クリア処理中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG04000("クリア処理中にエラーが発生しました。"),

    /**
     * カラムの追加中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG04001("カラムの追加中にエラーが発生しました。"),

    /**
     * 1行データの読み込み中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG04002("1行データの読み込み中にエラーが発生しました。"),

    /**
     * 中間ファイルに書き込み中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG04003("中間ファイルに書き込み中にエラーが発生しました。"),

    /**
     * 中間ファイルに書き込み完了。名称=[{0}]、 項目名=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG04004("中間ファイルに書き込み完了。名称=[{0}]、 項目名=[{1}]"),

    /**
     * クリア処理中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG05000("クリア処理中にエラーが発生しました。"),

    /**
     * カラムの追加中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG05001("カラムの追加中にエラーが発生しました。"),

    /**
     * 1行データの読み込み中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG05002("1行データの読み込み中にエラーが発生しました。"),

    /**
     * 中間ファイルに書き込み中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG05003("中間ファイルに書き込み中にエラーが発生しました。"),

    /**
     * 中間ファイルに書き込み完了。コメント=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG05004("中間ファイルに書き込み完了。コメント=[{0}]"),

    /**
     * リーダーリソースのクローズ処理中にエラーが発生しました。入力ファイルパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG07000("リーダーリソースのクローズ処理中にエラーが発生しました。入力ファイルパス=[{0}]"),

    /**
     * ライターリソースのクローズ処理中にエラーが発生しました。出力ファイルパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG07001("ライターリソースのクローズ処理中にエラーが発生しました。出力ファイルパス=[{0}]"),

    /**
     * 中間ファイルに書き込む処理を開始します。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG07002("中間ファイルに書き込む処理を開始します。"),

    /**
     * 中間ファイルに書き込む処理を終了します。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG07003("中間ファイルに書き込む処理を終了します。"),

    /**
     * 挿入SQL出力に失敗しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG10000("挿入SQL出力に失敗しました。"),

    /**
     * 削除した行数=[{0}] Javadoc行の削除が完了しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG12000("削除した行数=[{0}] Javadoc行の削除が完了しました。"),

    /**
     * Javadocタグ設定の対象外です。識別子=[{0}]、オリジナルブロック=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG13000("Javadocタグ設定の対象外です。識別子=[{0}]、オリジナルブロック=[{1}]"),

    /**
     * タグ存在しないため、タグを追加しました。追加先の区分：[{0}]、 追加先の要素名：[{1}]、 追加したタグ：[{2}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG13001("タグ存在しないため、タグを追加しました。追加先の区分：[{0}]、 追加先の要素名：[{1}]、 追加したタグ：[{2}]"),

    /**
     * タグを削除します。区分：[{0}]、 要素名：[{1}]、 元の対象行:[{2}]、 元のタグ:[{3}]、 元の指定値:[{4}]、 元の説明:[{5}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG13002("タグを削除します。区分：[{0}]、 要素名：[{1}]、 元の対象行:[{2}]、 元のタグ:[{3}]、 元の指定値:[{4}]、 元の説明:[{5}]"),

    /**
     * タグの位置を変更します。区分：[{0}]、 要素名：[{1}]、 元の対象行:[{2}]、 元のタグ:[{3}]、 元の指定値:[{4}]、 元の説明:[{5}]、 変更後のタグの内容:[{6}]、 変更後のタグ:[{7}]、
     * 変更後の指定値:[{8}]、 変更後の説明:[{9}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG13003(
        "タグの位置を変更します。区分：[{0}]、 要素名：[{1}]、 元の対象行:[{2}]、 元のタグ:[{3}]、 元の指定値:[{4}]、 元の説明:[{5}]、 変更後のタグの内容:[{6}]、 変更後のタグ:[{7}]、 変更後の指定値:[{8}]、 変更後の説明:[{9}]"),

    /**
     * タグを置換します。区分：[{0}]、 要素名：[{1}]、 元の対象行:[{2}]、 元のタグ:[{3}]、 元の指定値:[{4}]、 元の説明:[{5}]、 置換後のタグの内容:[{6}]、 置換後のタグ:[{7}]、
     * 置換後の指定値:[{8}]、 置換後の説明:[{9}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG13004(
        "タグを置換します。区分：[{0}]、 要素名：[{1}]、 元の対象行:[{2}]、 元のタグ:[{3}]、 元の指定値:[{4}]、 元の説明:[{5}]、 置換後のタグの内容:[{6}]、 置換後のタグ:[{7}]、 置換後の指定値:[{8}]、 置換後の説明:[{9}]"),

    /**
     * Javadocタグ設定処理を開始します。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG13005("Javadocタグ設定処理を開始します。"),

    /**
     * Javadocタグ設定処理を終了します。読み込みファイル数:[{0}]、合計置換数:[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG13006("Javadocタグ設定処理を終了します。読み込みファイル数:[{0}]、合計置換数:[{1}]"),

    /**
     * 対象のファイルの処理を開始します。対象のファイルのパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG13007("対象のファイルの処理を開始します。対象のファイルのパス=[{0}]"),

    /**
     * 対象のファイルの処理を終了します。対象のファイルのパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG13008("対象のファイルの処理を終了します。対象のファイルのパス=[{0}]"),

    /**
     * クリア処理中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG14000("クリア処理中にエラーが発生しました。"),

    /**
     * カラムの追加中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG14001("カラムの追加中にエラーが発生しました。"),

    /**
     * 1行データの読み込み中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG14002("1行データの読み込み中にエラーが発生しました。"),

    /**
     * 中間ファイルに書き込み中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG14003("中間ファイルに書き込み中にエラーが発生しました。"),

    /**
     * 中間ファイルに書き込み完了。項目=[{0}]、項目名=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG14004("中間ファイルに書き込み完了。項目=[{0}]、項目名=[{1}]"),

    /**
     * 初期化の失敗
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG15000("初期化の失敗"),

    /**
     * 初期化で例外が発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG15001("初期化で例外が発生しました。"),

    /**
     * 初期化の失敗
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG17000("初期化の失敗"),

    /**
     * マッピング変換処理を開始します。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG19000("マッピング変換処理を開始します。"),

    /**
     * マッピング変換処理を終了します。読み込みファイル数:[{0}]、合計置換数:[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_LOG19001("マッピング変換処理を終了します。読み込みファイル数:[{0}]、合計置換数:[{1}]"),

    /* 定義：終了 */
    ;

    /**
     * 種類のマップ
     *
     * @since 0.2.4
     */
    private static final Map<String, KmgToolBaseLogMsgTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final KmgToolBaseLogMsgTypes type : KmgToolBaseLogMsgTypes.values()) {

            KmgToolBaseLogMsgTypes.VALUES_MAP.put(type.get(), type);

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
    public static KmgToolBaseLogMsgTypes getDefault() {

        final KmgToolBaseLogMsgTypes result = NONE;
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
    public static KmgToolBaseLogMsgTypes getEnum(final String key) {

        KmgToolBaseLogMsgTypes result = KmgToolBaseLogMsgTypes.VALUES_MAP.get(key);

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
    public static KmgToolBaseLogMsgTypes getInitValue() {

        final KmgToolBaseLogMsgTypes result = NONE;
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
    KmgToolBaseLogMsgTypes(final String displayName) {

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
