package kmg.tool.domain.types;

import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.common.KmgComTypes;

/**
 * Javadocグループインデックスの種類<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public enum JavadocGroupIndexTypes implements KmgComTypes<Integer> {

    /* 定義：開始 */

    /** タグ全体のグループインデックス */
    WHOLE("タグ全体", 0, "タグ全体のグループインデックス"),

    /** タグ名のグループインデックス */
    TAG_NAME("タグ名", 1, "タグ名のグループインデックス"),

    /** タグ値のグループインデックス */
    VALUE("タグ値", 2, "タグ値のグループインデックス"),

    /** タグ説明のグループインデックス */
    DESCRIPTION("タグ説明", 3, "タグ説明のグループインデックス"),

    /* 定義：終了 */
    ;

    /** 種類のマップ */
    private static final Map<Integer, JavadocGroupIndexTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final JavadocGroupIndexTypes type : JavadocGroupIndexTypes.values()) {

            JavadocGroupIndexTypes.VALUES_MAP.put(type.get(), type);

        }

    }

    /** 表示名 */
    private final String displayName;

    /** キー */
    private final Integer key;

    /** 詳細情報 */
    private final String detail;

    /**
     * コンストラクタ<br>
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
