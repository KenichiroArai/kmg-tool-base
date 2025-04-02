package kmg.tool.domain.model.impl;

import java.util.Map;

import kmg.tool.domain.model.JavadocTagConfigModel;

/**
 * Javadocタグ設定モデル実装<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JavadocTagConfigModelImpl implements JavadocTagConfigModel {

    /** タグ名 */
    private String name;

    /** タグの値 */
    private String text;

    /** 配置場所の設定 */
    private Map<String, Object> location;

    /** タグの挿入位置 */
    private String insertPosition;

    /** 上書き設定 */
    private String overwrite;

    /**
     * デフォルトコンストラクタ<br>
     */
    public JavadocTagConfigModelImpl() {

        // 処理なし
    }

    /**
     * コンストラクタ<br>
     *
     * @param tagConfig
     *                  タグ設定
     */
    @SuppressWarnings("unchecked")
    public JavadocTagConfigModelImpl(final Map<String, Object> tagConfig) {

        // TODO KenichiroArai 2025/04/02 ハードコード
        this.name = (String) tagConfig.get("name");
        this.text = (String) tagConfig.get("text");
        this.location = (Map<String, Object>) tagConfig.get("location");
        this.insertPosition = (String) tagConfig.get("insertPosition");
        this.overwrite = (String) tagConfig.get("overwrite");

    }

    /**
     * タグの挿入位置を返す<br>
     *
     * @return タグの挿入位置
     */
    @Override
    public String getInsertPosition() {

        final String result = this.insertPosition;
        return result;

    }

    /**
     * 配置場所の設定を返す<br>
     *
     * @return 配置場所の設定
     */
    @Override
    public Map<String, Object> getLocation() {

        final Map<String, Object> result = this.location;
        return result;

    }

    /**
     * タグ名を返す<br>
     *
     * @return タグ名
     */
    @Override
    public String getName() {

        final String result = this.name;
        return result;

    }

    /**
     * 上書き設定を返す<br>
     *
     * @return 上書き設定
     */
    @Override
    public String getOverwrite() {

        final String result = this.overwrite;
        return result;

    }

    /**
     * タグの値を返す<br>
     *
     * @return タグの値
     */
    @Override
    public String getText() {

        final String result = this.text;
        return result;

    }
}
