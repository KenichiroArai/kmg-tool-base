package kmg.tool.domain.types;

import java.util.HashMap;
import java.util.Map;

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
public enum KmgToolGenMessageTypes implements KmgToolComGenMessageTypes {

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
     * 一時的なCSVファイルの作成に失敗しました。CSVファイル名のみ=[{0}]、サフィックスと拡張子=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN12000("一時的なCSVファイルの作成に失敗しました。CSVファイル名のみ=[{0}]、サフィックスと拡張子=[{1}]"),

    /**
     * ファイル処理に失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31000("ファイル処理に失敗しました。"),

    /**
     * ファイル処理に失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31001("ファイル処理に失敗しました。"),

    /**
     * テンプレートファイルの取得に失敗しました。テンプレートファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31002("テンプレートファイルの取得に失敗しました。テンプレートファイルパス=[{0}]"),

    /**
     * リソースのクローズ処理中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31003("リソースのクローズ処理中にエラーが発生しました。"),

    /**
     * 先頭大文字項目がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32000("KMGTOOL_GEN32000"),

    /**
     * 項目名がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32001("KMGTOOL_GEN32001"),

    /**
     * Javadocコメントがnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32002("KMGTOOL_GEN32002"),

    /**
     * 型情報がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32003("KMGTOOL_GEN32003"),

    /**
     * 入力ファイルを開くのに失敗しました。入力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32004("KMGTOOL_GEN32004"),

    /**
     * 出力ファイルを開くのに失敗しました。出力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32005("KMGTOOL_GEN32005"),

    /**
     * 1行読み込みに失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32006("KMGTOOL_GEN32006"),

    /**
     * CSVデータの書き込みに失敗しました。出力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32007("KMGTOOL_GEN32007"),

    /**
     * ファイルのフラッシュに失敗しました。出力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32008("KMGTOOL_GEN32008"),

    /**
     * 失敗
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN41000("失敗"),

    /**
     * 成功
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN41001("成功"),

    /**
     * 例外発生
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN41002("例外発生"),

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
