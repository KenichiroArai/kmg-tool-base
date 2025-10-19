package kmg.tool.base.jdoc.domain.types;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.cmn.KmgCmnTypes;

/**
 * Javadocグループインデックスの種類<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@SuppressWarnings("nls")
public enum JavadocGroupIndexTypes implements KmgCmnTypes<Integer> {

    /* 定義：開始 */

    /**
     * タグ全体のグループインデックス
     *
     * @since 0.2.0
     */
    WHOLE("タグ全体", 0, "タグ全体のグループインデックス"),

    /**
     * タグ名のグループインデックス
     *
     * @since 0.2.0
     */
    TAG_NAME("タグ名", 1, "タグ名のグループインデックス"),

    /**
     * タグ値のグループインデックス
     *
     * @since 0.2.0
     */
    VALUE("タグ値", 2, "タグ値のグループインデックス"),

    /**
     * タグ説明のグループインデックス
     *
     * @since 0.2.0
     */
    DESCRIPTION("タグ説明", 3, "タグ説明のグループインデックス"),

    /* 定義：終了 */
    ;

    /**
     * 種類のマップ
     *
     * @since 0.2.0
     */
    private static final Map<Integer, JavadocGroupIndexTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final JavadocGroupIndexTypes type : JavadocGroupIndexTypes.values()) {

            JavadocGroupIndexTypes.VALUES_MAP.put(type.get(), type);

        }

    }

    /**
     * 表示名
     *
     * @since 0.2.0
     */
    private final String displayName;

    /**
     * キー
     *
     * @since 0.2.0
     */
    private final Integer key;

    /**
     * 詳細情報
     *
     * @since 0.2.0
     */
    private final String detail;

    /**
     * コンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param displayName
     *                    表示名
     * @param key
     *                    キー
     * @param detail
     *                    詳細情報
     */
    JavadocGroupIndexTypes(final String displayName, final Integer key, final String detail) {

        this.displayName = displayName;
        this.key = key;
        this.detail = detail;

    }

    /**
     * キーを返す。<br>
     *
     * @since 0.2.0
     *
     * @return キー
     *
     * @see #getKey()
     */
    @Override
    public Integer get() {

        final Integer result = this.getKey();
        return result;

    }

    /**
     * 詳細情報を返す。<br>
     *
     * @since 0.2.0
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
     *
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @return キー
     */
    @Override
    public Integer getKey() {

        final Integer result = this.key;
        return result;

    }

    /**
     * キーを返す。<br>
     *
     * @since 0.2.0
     *
     * @return キー
     *
     * @see #getKey()
     */
    @Override
    public String toString() {

        final String result = String.valueOf(this.getKey());
        return result;

    }
}
