package kmg.tool.application.model.jdts.imp;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgJavadocLocationTypes;
import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.application.model.jdts.JdtsLocationConfigModel;
import kmg.tool.application.model.jdts.JdtsTagConfigModel;
import kmg.tool.application.types.JdaInsertPositionTypes;
import kmg.tool.application.types.JdaOverwriteTypes;

/**
 * Javadocタグ設定のタグ構成モデル<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JdtsTagConfigModelImpl implements JdtsTagConfigModel {

    /** タグ */
    private final KmgJavadocTagTypes tag;

    /** タグ名 */
    private final String tagName;

    /** タグの指定値 */
    private final String tagValue;

    /** タグの説明 */
    private final String tagDescription;

    /** 配置場所の設定 */
    private final JdtsLocationConfigModel location;

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
    public JdtsTagConfigModelImpl(final Map<String, Object> tagConfig) {

        // TODO KenichiroArai 2025/04/25 【優先度：低】：ハードコード
        this.tagName = (String) tagConfig.get("tagName");
        this.tag = KmgJavadocTagTypes.getEnum(KmgString.concat("@", this.tagName));
        this.tagValue = (String) tagConfig.get("tagValue");
        this.tagDescription
            = Optional.ofNullable(tagConfig.get("tagDescription")).map(Object::toString).orElse(KmgString.EMPTY);

        final Map<String, Object> locationMap = (Map<String, Object>) tagConfig.get("location");

        // 配置場所の設定の生成
        this.location = new JdtsLocationConfigModelImpl(locationMap);

        this.insertPosition = JdaInsertPositionTypes.getEnum((String) tagConfig.get("insertPosition"));
        this.overwrite = JdaOverwriteTypes.getEnum((String) tagConfig.get("overwrite"));

    }

    /**
     * タグの挿入位置を返す<br>
     *
     * @return タグの挿入位置
     */
    @Override
    public JdaInsertPositionTypes getInsertPosition() {

        final JdaInsertPositionTypes result = this.insertPosition;
        return result;

    }

    /**
     * 配置場所の設定を返す<br>
     *
     * @return 配置場所の設定
     */
    @Override
    public JdtsLocationConfigModel getLocation() {

        final JdtsLocationConfigModel result = this.location;
        return result;

    }

    /**
     * 上書き設定を返す<br>
     *
     * @return 上書き設定
     */
    @Override
    public JdaOverwriteTypes getOverwrite() {

        final JdaOverwriteTypes result = this.overwrite;
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

    /**
     * タグの配置がJava区分に一致するか<br>
     *
     * @param javaClassification
     *                           Java区分
     *
     * @return true：一致する、false：一致しない
     */
    @Override
    public boolean isProperlyPlaced(final JavaClassificationTypes javaClassification) {

        boolean result = false;

        /* 配置方法による判断 */
        result = switch (this.location.getMode()) {

            case NONE:
                /* 指定無し */
                yield false;

            case COMPLIANT:
            /* 準拠モード */ {

                // タグの設定可能な場所のリストを取得
                final List<KmgJavadocLocationTypes> locations = this.tag.getLocations();
                // Java区分に対応するJavadoc配置場所の種類を取得
                final KmgJavadocLocationTypes targetLocation
                    = KmgJavadocLocationTypes.fromJavaClassification(javaClassification);
                // 全ての場所に配置可能か、または特定の場所に配置可能かをチェック
                yield locations.contains(KmgJavadocLocationTypes.ALL) || locations.contains(targetLocation);

            }

            case MANUAL:
            /* 手動モード */ {

                // Java区分に対応するJavadoc配置場所の種類を取得
                final KmgJavadocLocationTypes manualTargetLocation
                    = KmgJavadocLocationTypes.fromJavaClassification(javaClassification);
                // targetElementsに指定された要素と一致するかチェック
                yield this.location.getTargetElements().contains(manualTargetLocation.getKey());

            }

        };

        return result;

    }

}
