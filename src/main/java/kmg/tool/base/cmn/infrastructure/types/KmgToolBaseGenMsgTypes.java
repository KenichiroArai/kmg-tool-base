package kmg.tool.base.cmn.infrastructure.types;

import java.util.HashMap;
import java.util.Map;

import kmg.tool.base.cmn.infrastructure.msg.KmgToolBaseCmnExcMsg;
import kmg.tool.base.cmn.infrastructure.msg.KmgToolBaseCmnGenMsg;

/**
 * KMGツールベース一般メッセージの種類<br>
 * <p>
 * Genは、Generalの略。<br>
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
public enum KmgToolBaseGenMsgTypes implements KmgToolBaseCmnGenMsg, KmgToolBaseCmnExcMsg {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @since 0.2.4
     */
    NONE("指定無し"),

    /**
     * アクセサ作成ロジックをクローズ中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN01000("アクセサ作成ロジックをクローズ中にエラーが発生しました。"),

    /**
     * 項目名がnullです。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN01001("項目名がnullです。"),

    /**
     * Javadocコメントがnullです。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN01002("Javadocコメントがnullです。"),

    /**
     * 型情報がnullです。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN01003("型情報がnullです。"),

    /**
     * テンプレートファイルをYAML形式で読み込むことに失敗しました。テンプレートパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN03000("テンプレートファイルをYAML形式で読み込むことに失敗しました。テンプレートパス=[{0}]"),

    /**
     * 入力ファイルの読み込み中にエラーが発生しました。入力ファイルパス：[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN03001("入力ファイルの読み込み中にエラーが発生しました。入力ファイルパス：[{0}]"),

    /**
     * 出力バッファの書き込み中にエラーが発生しました。入力ファイルパス：[{0}]、 テンプレートパス：[{1}]、 出力ファイルパス=[{2}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN03002("出力バッファの書き込み中にエラーが発生しました。入力ファイルパス：[{0}]、 テンプレートパス：[{1}]、 出力ファイルパス=[{2}]"),

    /**
     * 入力ファイルを開くことができませんでした。入力ファイルパス：[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN03003("入力ファイルを開くことができませんでした。入力ファイルパス：[{0}]"),

    /**
     * 出力ファイルを開くことができませんでした。出力ファイルパス：[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN03004("出力ファイルを開くことができませんでした。出力ファイルパス：[{0}]"),

    /**
     * 中間の列が不足しています。入力ファイルパス: [{0}]、 プレースホルダーキー: [{1}]、 列: [{2}] 番目
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN03005("中間の列が不足しています。入力ファイルパス: [{0}]、 プレースホルダーキー: [{1}]、 列: [{2}] 番目"),

    /**
     * テンプレートの動的変換ロジックをクローズ中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN03006("テンプレートの動的変換ロジックをクローズ中にエラーが発生しました。"),

    /**
     * 項目名がnullです。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN04000("項目名がnullです。"),

    /**
     * 項目名がnullです。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN04001("項目名がnullです。"),

    /**
     * 列挙型からcase文作成ロジックをクローズ中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN04002("列挙型からcase文作成ロジックをクローズ中にエラーが発生しました。"),

    /**
     * コメントがnullです。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN05000("コメントがnullです。"),

    /**
     * フィールドがnullです。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN05001("フィールドがnullです。"),

    /**
     * 型がnullです。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN05002("型がnullです。"),

    /**
     * フィールド作成ロジックをクローズ中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN05003("フィールド作成ロジックをクローズ中にエラーが発生しました。"),

    /**
     * 1行読み込みに失敗しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN07000("1行読み込みに失敗しました。"),

    /**
     * 中間データの書き込みに失敗しました。出力ファイルパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN07001("中間データの書き込みに失敗しました。出力ファイルパス=[{0}]"),

    /**
     * ファイルのフラッシュに失敗しました。出力ファイルパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN07002("ファイルのフラッシュに失敗しました。出力ファイルパス=[{0}]"),

    /**
     * 入力ファイルを開くのに失敗しました。入力ファイルパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN07003("入力ファイルを開くのに失敗しました。入力ファイルパス=[{0}]"),

    /**
     * 出力ファイルを開くのに失敗しました。出力ファイルパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN07004("出力ファイルを開くのに失敗しました。出力ファイルパス=[{0}]"),

    /**
     * 書き込み対象の中間データの最後がリストに存在しません。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN07005("書き込み対象の中間データの最後がリストに存在しません。"),

    /**
     * 一時的な中間ファイルの作成に失敗しました。中間ファイル名のみ=[{0}]、サフィックスと拡張子=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN07006("一時的な中間ファイルの作成に失敗しました。中間ファイル名のみ=[{0}]、サフィックスと拡張子=[{1}]"),

    /**
     * 入力、中間、テンプレート、出力の1行パターンの抽象クラスの初期処理で出力ファイルの区切り文字が「null」です。
     *
     * @since 0.2.2
     */
    KMGTOOLBASE_GEN07007("入力、中間、テンプレート、出力の1行パターンの抽象クラスの初期処理で出力ファイルの区切り文字が「null」です。"),

    /**
     * 入力、中間、テンプレート、出力の1行パターンの抽象クラスの初期処理で出力ファイルの区切り文字が「NONE」です。
     *
     * @since 0.2.2
     */
    KMGTOOLBASE_GEN07008("入力、中間、テンプレート、出力の1行パターンの抽象クラスの初期処理で出力ファイルの区切り文字が「NONE」です。"),

    /**
     * 入力ファイルパスがnullです。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN08000("入力ファイルパスがnullです。"),

    /**
     * 入力パスファイルが存在しません。入力ファイルパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN08001("入力パスファイルが存在しません。入力ファイルパス=[{0}]"),

    /**
     * 入力ファイルの読み込みに失敗しました。入力ファイルパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN08002("入力ファイルの読み込みに失敗しました。入力ファイルパス=[{0}]"),

    /**
     * 出力ファイルのディレクトリの作成に失敗しました。出力ファイルパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN10000("出力ファイルのディレクトリの作成に失敗しました。出力ファイルパス=[{0}]"),

    /**
     * 暗号化されたファイルです。入力ファイルのパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN10001("暗号化されたファイルです。入力ファイルのパス=[{0}]"),

    /**
     * 入力ファイルのパスの読み込みに失敗しました。入力ファイルのパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN10002("入力ファイルのパスの読み込みに失敗しました。入力ファイルのパス=[{0}]"),

    /**
     * 出力ファイルへの書き込みに失敗しました。出力ファイルパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN10003("出力ファイルへの書き込みに失敗しました。出力ファイルパス=[{0}]"),

    /**
     * ワークブックが空です。入力ファイルのパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN10004("ワークブックが空です。入力ファイルのパス=[{0}]"),

    /**
     * ワークブックの読み込みに失敗しました。入力ファイルのパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN10005("ワークブックの読み込みに失敗しました。入力ファイルのパス=[{0}]"),

    /**
     * 入力シートはnullです。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN10006("入力シートはnullです。"),

    /**
     * ファイルの書き込み中にエラーが発生しました。ファイル=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN12000("ファイルの書き込み中にエラーが発生しました。ファイル=[{0}]"),

    /**
     * ファイルの読み込み中にエラーが発生しました。ファイル=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN12001("ファイルの読み込み中にエラーが発生しました。ファイル=[{0}]"),

    /**
     * 入力ファイルの読み込み中にエラーが発生しました。ファイル=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN12002("入力ファイルの読み込み中にエラーが発生しました。ファイル=[{0}]"),

    /**
     * 定義ファイルの読み込みに失敗しました。定義ファイル=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN13003("定義ファイルの読み込みに失敗しました。定義ファイル=[{0}]"),

    /**
     * 項目名がnullです。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN14000("項目名がnullです。"),

    /**
     * 項目がnullです。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN14001("項目がnullです。"),

    /**
     * 項目と項目名に分かれていません。「項目=項目名」の設定にしてください。行番号=[{0}]、行データ=[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN14002("項目と項目名に分かれていません。「項目=項目名」の設定にしてください。行番号=[{0}]、行データ=[{1}]"),

    /**
     * メッセージの種類作成ロジックをクローズ中にエラーが発生しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN14003("メッセージの種類作成ロジックをクローズ中にエラーが発生しました。"),

    /**
     * ファイル処理に失敗しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN15000("ファイル処理に失敗しました。"),

    /**
     * ファイル処理に失敗しました。
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN16000("ファイル処理に失敗しました。"),

    /**
     * テンプレートファイルの取得に失敗しました。テンプレートファイルパス=[{0}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN16001("テンプレートファイルの取得に失敗しました。テンプレートファイルパス=[{0}]"),

    /**
     * 対象値からUUIDへの置換数とUUIDから置換値への置換数が一致しません。対象値からUUIDへの置換数：[{0}]、UUIDから置換値への置換数：[{1}]
     *
     * @since 0.2.4
     */
    KMGTOOLBASE_GEN19004("対象値からUUIDへの置換数とUUIDから置換値への置換数が一致しません。対象値からUUIDへの置換数：[{0}]、UUIDから置換値への置換数：[{1}]"),

    /* 定義：終了 */

    ;

    /**
     * 種類のマップ
     *
     * @since 0.2.4
     */
    private static final Map<String, KmgToolBaseGenMsgTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final KmgToolBaseGenMsgTypes type : KmgToolBaseGenMsgTypes.values()) {

            KmgToolBaseGenMsgTypes.VALUES_MAP.put(type.get(), type);

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
    public static KmgToolBaseGenMsgTypes getDefault() {

        final KmgToolBaseGenMsgTypes result = NONE;
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
    public static KmgToolBaseGenMsgTypes getEnum(final String key) {

        KmgToolBaseGenMsgTypes result = KmgToolBaseGenMsgTypes.VALUES_MAP.get(key);

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
    public static KmgToolBaseGenMsgTypes getInitValue() {

        final KmgToolBaseGenMsgTypes result = NONE;
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
    KmgToolBaseGenMsgTypes(final String displayName) {

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
