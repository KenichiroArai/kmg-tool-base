package kmg.tool.application.model.jda.imp;

import java.util.Map;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.application.model.jda.JdaLocationConfigModel;
import kmg.tool.application.model.jda.JdaTagConfigModel;
import kmg.tool.application.types.JdaInsertPositionTypes;
import kmg.tool.application.types.JdaOverwriteTypes;

/**
 * Javadoc追加のタグ設定モデル実装<br>
 * <p>
 * Jdaは、JavadocAppenderの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JdaTagConfigModelImpl implements JdaTagConfigModel {

    /** タグ */
    private final KmgJavadocTagTypes tag;

    /** タグ名 */
    private final String tagName;

    /** タグの指定値 */
    private final String tagValue;

    /** タグの説明 */
    private final String tagDescription;

    /** 配置場所の設定 */
    private final JdaLocationConfigModel location;

    /** タグの挿入位置 */
    private final JdaInsertPositionTypes insertPosition;

    /** 上書き設定 */
    private final JdaOverwriteTypes overwrite;

    /**
     * コンストラクタ<br>
     *
     * @param tagConfig
     *                  タグ設定
     */
    @SuppressWarnings("unchecked")
    public JdaTagConfigModelImpl(final Map<String, Object> tagConfig) {

        // TODO KenichiroArai 2025/04/02 ハードコード
        this.tagName = (String) tagConfig.get("tagName");
        this.tag = KmgJavadocTagTypes.getEnum(KmgString.concat("@", this.tagName));
        this.tagValue = (String) tagConfig.get("tagValue");
        this.tagDescription = (String) tagConfig.get("tagDescription");

        final Map<String, Object> locationMap = (Map<String, Object>) tagConfig.get("location");

        // 配置場所の設定の生成
        this.location = new JdaLocationConfigModelImpl(locationMap);

        this.insertPosition = JdaInsertPositionTypes.getEnum((String) tagConfig.get("insertPosition"));
        this.overwrite = JdaOverwriteTypes.getEnum((String) tagConfig.get("overwrite"));

    }

    /**
     * タグの挿入位置を返す<br>
     *
     * @return タグの挿入位置
     */
    @Override
    public String getInsertPosition() {

        final String result = this.insertPosition.getKey();
        return result;

    }

    /**
     * 配置場所の設定を返す<br>
     *
     * @return 配置場所の設定
     */
    @Override
    public JdaLocationConfigModel getLocation() {

        final JdaLocationConfigModel result = this.location;
        return result;

    }

    /**
     * 配置場所のモードを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 配置場所のモード
     */
    @Override
    public String getLocationMode() {

        final String result = this.location.getMode();
        return result;

    }

    /**
     * 上書き設定を返す<br>
     *
     * @return 上書き設定
     */
    @Override
    public String getOverwrite() {

        final String result = this.overwrite.getKey();
        return result;

    }

    /**
     * タグを返す<br>
     *
     * @return タグ
     */
    @Override
    public KmgJavadocTagTypes getTag() {

        final KmgJavadocTagTypes result = this.tag;
        return result;

    }

    /**
     * タグの説明を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return タグの説明
     */
    @Override
    public String getTagDescription() {

        final String result = this.tagDescription;
        return result;

    }

    /**
     * タグ名を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return タグ名
     */
    @Override
    public String getTagName() {

        final String result = this.tagName;
        return result;

    }

    /**
     * タグの指定値を返す<br>
     *
     * @return タグの指定値
     */
    @Override
    public String getTagValue() {

        final String result = this.tagValue;
        return result;

    }
}
