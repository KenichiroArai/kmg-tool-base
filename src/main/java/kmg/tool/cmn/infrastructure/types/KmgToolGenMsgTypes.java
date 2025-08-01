package kmg.tool.cmn.infrastructure.types;

import java.util.HashMap;
import java.util.Map;

import kmg.tool.cmn.infrastructure.msg.KmgToolCmnExcMsg;
import kmg.tool.cmn.infrastructure.msg.KmgToolCmnGenMsg;

/**
 * KMGツール一般メッセージの種類<br>
 * <p>
 * Genは、Generalの略。<br>
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
public enum KmgToolGenMsgTypes implements KmgToolCmnGenMsg, KmgToolCmnExcMsg {

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
     * アクセサ作成ロジックをクローズ中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN01000("アクセサ作成ロジックをクローズ中にエラーが発生しました。"),

    /**
     * 項目名がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN01001("項目名がnullです。"),

    /**
     * Javadocコメントがnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN01002("Javadocコメントがnullです。"),

    /**
     * 型情報がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN01003("型情報がnullです。"),

    /**
     * テンプレートファイルをYAML形式で読み込むことに失敗しました。テンプレートパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN03000("テンプレートファイルをYAML形式で読み込むことに失敗しました。テンプレートパス=[{0}]"),

    /**
     * 入力ファイルの読み込み中にエラーが発生しました。入力ファイルパス：[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN03001("入力ファイルの読み込み中にエラーが発生しました。入力ファイルパス：[{0}]"),

    /**
     * 出力バッファの書き込み中にエラーが発生しました。入力ファイルパス：[{0}]、 テンプレートパス：[{1}]、 出力ファイルパス=[{2}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN03002("出力バッファの書き込み中にエラーが発生しました。入力ファイルパス：[{0}]、 テンプレートパス：[{1}]、 出力ファイルパス=[{2}]"),

    /**
     * 入力ファイルを開くことができませんでした。入力ファイルパス：[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN03003("入力ファイルを開くことができませんでした。入力ファイルパス：[{0}]"),

    /**
     * 出力ファイルを開くことができませんでした。出力ファイルパス：[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN03004("出力ファイルを開くことができませんでした。出力ファイルパス：[{0}]"),

    /**
     * 中間の列が不足しています。入力ファイルパス: [{0}]、 プレースホルダーキー: [{1}]、 列: [{2}] 番目
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN03005("中間の列が不足しています。入力ファイルパス: [{0}]、 プレースホルダーキー: [{1}]、 列: [{2}] 番目"),

    /**
     * テンプレートの動的変換ロジックをクローズ中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN03006("テンプレートの動的変換ロジックをクローズ中にエラーが発生しました。"),

    /**
     * 項目名がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN04000("項目名がnullです。"),

    /**
     * 項目名がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN04001("項目名がnullです。"),

    /**
     * 列挙型からcase文作成ロジックをクローズ中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN04002("列挙型からcase文作成ロジックをクローズ中にエラーが発生しました。"),

    /**
     * コメントがnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN05000("コメントがnullです。"),

    /**
     * フィールドがnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN05001("フィールドがnullです。"),

    /**
     * 型がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN05002("型がnullです。"),

    /**
     * フィールド作成ロジックをクローズ中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN05003("フィールド作成ロジックをクローズ中にエラーが発生しました。"),

    /**
     * 1行読み込みに失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN07000("1行読み込みに失敗しました。"),

    /**
     * 中間データの書き込みに失敗しました。出力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN07001("中間データの書き込みに失敗しました。出力ファイルパス=[{0}]"),

    /**
     * ファイルのフラッシュに失敗しました。出力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN07002("ファイルのフラッシュに失敗しました。出力ファイルパス=[{0}]"),

    /**
     * 入力ファイルを開くのに失敗しました。入力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN07003("入力ファイルを開くのに失敗しました。入力ファイルパス=[{0}]"),

    /**
     * 出力ファイルを開くのに失敗しました。出力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN07004("出力ファイルを開くのに失敗しました。出力ファイルパス=[{0}]"),

    /**
     * 書き込み対象の中間データの最後がリストに存在しません。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN07005("書き込み対象の中間データの最後がリストに存在しません。"),

    /**
     * 一時的な中間ファイルの作成に失敗しました。中間ファイル名のみ=[{0}]、サフィックスと拡張子=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN07006("一時的な中間ファイルの作成に失敗しました。中間ファイル名のみ=[{0}]、サフィックスと拡張子=[{1}]"),

    /**
     * 入力ファイルパスがnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN08000("入力ファイルパスがnullです。"),

    /**
     * 入力パスファイルが存在しません。入力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN08001("入力パスファイルが存在しません。入力ファイルパス=[{0}]"),

    /**
     * 入力ファイルの読み込みに失敗しました。入力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN08002("入力ファイルの読み込みに失敗しました。入力ファイルパス=[{0}]"),

    /**
     * 失敗
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN09000("失敗"),

    /**
     * 成功
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN09001("成功"),

    /**
     * 例外発生
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN09002("例外発生"),

    /**
     * 出力ファイルのディレクトリの作成に失敗しました。出力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN10000("出力ファイルのディレクトリの作成に失敗しました。出力ファイルパス=[{0}]"),

    /**
     * 暗号化されたファイルです。入力ファイルのパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN10001("暗号化されたファイルです。入力ファイルのパス=[{0}]"),

    /**
     * 入力ファイルのパスの読み込みに失敗しました。入力ファイルのパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN10002("入力ファイルのパスの読み込みに失敗しました。入力ファイルのパス=[{0}]"),

    /**
     * 出力ファイルへの書き込みに失敗しました。出力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN10003("出力ファイルへの書き込みに失敗しました。出力ファイルパス=[{0}]"),

    /**
     * ワークブックが空です。入力ファイルのパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN10004("ワークブックが空です。入力ファイルのパス=[{0}]"),

    /**
     * ワークブックの読み込みに失敗しました。入力ファイルのパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN10005("ワークブックの読み込みに失敗しました。入力ファイルのパス=[{0}]"),

    /**
     * 入力シートはnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN10006("入力シートはnullです。"),

    /**
     * ファイルの書き込み中にエラーが発生しました。ファイル=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN12000("ファイルの書き込み中にエラーが発生しました。ファイル=[{0}]"),

    /**
     * ファイルの読み込み中にエラーが発生しました。ファイル=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN12001("ファイルの読み込み中にエラーが発生しました。ファイル=[{0}]"),

    /**
     * 入力ファイルの読み込み中にエラーが発生しました。ファイル=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN12002("入力ファイルの読み込み中にエラーが発生しました。ファイル=[{0}]"),

    /**
     * Javadoc行削除の初期化に失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN12003("Javadoc行削除の初期化に失敗しました。"),

    /**
     * Javadoc行削除が正常に完了しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN12004("Javadoc行削除が正常に完了しました。"),

    /**
     * Javadoc行削除中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN12005("Javadoc行削除中にエラーが発生しました。"),

    /**
     * Javadocタグ設定で現在のファイルに内容を書き込み中に例外が発生しました。現在のファイルパス=[{0}]、書き込む内容=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN13000("Javadocタグ設定で現在のファイルに内容を書き込み中に例外が発生しました。現在のファイルパス=[{0}]、書き込む内容=[{1}]"),

    /**
     * Javadocタグ設定で現在のファイルをロード中に例外が発生しました。現在のファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN13001("Javadocタグ設定で現在のファイルをロード中に例外が発生しました。現在のファイルパス=[{0}]"),

    /**
     * Javadocタグ設定で対象ファイルをロード中に例外が発生しました。対象ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN13002("Javadocタグ設定で対象ファイルをロード中に例外が発生しました。対象ファイルパス=[{0}]"),

    /**
     * 定義ファイルの読み込みに失敗しました。定義ファイル=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN13003("定義ファイルの読み込みに失敗しました。定義ファイル=[{0}]"),

    /**
     * 入力ファイルから対象パスを設定に失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN13004("入力ファイルから対象パスを設定に失敗しました。"),

    /**
     * 実行が成功しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN13005("実行が成功しました。"),

    /**
     * 実行中に例外が発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN13006("実行中に例外が発生しました。"),

    /**
     * バリデーションエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN13007("バリデーションエラーが発生しました。"),

    /**
     * 実行時例外が発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN13008("実行時例外が発生しました。"),

    /**
     * 項目名がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN14000("項目名がnullです。"),

    /**
     * 項目がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN14001("項目がnullです。"),

    /**
     * 項目と項目名に分かれていません。「項目=項目名」の設定にしてください。行番号=[{0}]、行データ=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN14002("項目と項目名に分かれていません。「項目=項目名」の設定にしてください。行番号=[{0}]、行データ=[{1}]"),

    /**
     * メッセージの種類作成ロジックをクローズ中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN14003("メッセージの種類作成ロジックをクローズ中にエラーが発生しました。"),

    /**
     * ファイル処理に失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN15000("ファイル処理に失敗しました。"),

    /**
     * ファイル処理に失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN16000("ファイル処理に失敗しました。"),

    /**
     * テンプレートファイルの取得に失敗しました。テンプレートファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN16001("テンプレートファイルの取得に失敗しました。テンプレートファイルパス=[{0}]"),

    /**
     * 入力ファイルから対象パスを設定に失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN19000("入力ファイルから対象パスを設定に失敗しました。"),

    /**
     * 実行が成功しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN19001("実行が成功しました。"),

    /**
     * 実行中に例外が発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN19002("実行中に例外が発生しました。"),

    /**
     * バリデーションエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN19003("バリデーションエラーが発生しました。"),

    /**
     * 対象値からUUIDへの置換数とUUIDから置換値への置換数が一致しません。対象値からUUIDへの置換数：[{0}]、UUIDから置換値への置換数：[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    KMGTOOL_GEN19004("対象値からUUIDへの置換数とUUIDから置換値への置換数が一致しません。対象値からUUIDへの置換数：[{0}]、UUIDから置換値への置換数：[{1}]"),

    /* 定義：終了 */

    ;

    /**
     * 種類のマップ
     *
     * @since 0.1.0
     */
    private static final Map<String, KmgToolGenMsgTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final KmgToolGenMsgTypes type : KmgToolGenMsgTypes.values()) {

            KmgToolGenMsgTypes.VALUES_MAP.put(type.get(), type);

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
    public static KmgToolGenMsgTypes getDefault() {

        final KmgToolGenMsgTypes result = NONE;
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
    public static KmgToolGenMsgTypes getEnum(final String key) {

        KmgToolGenMsgTypes result = KmgToolGenMsgTypes.VALUES_MAP.get(key);

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
    public static KmgToolGenMsgTypes getInitValue() {

        final KmgToolGenMsgTypes result = NONE;
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
    KmgToolGenMsgTypes(final String displayName) {

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
