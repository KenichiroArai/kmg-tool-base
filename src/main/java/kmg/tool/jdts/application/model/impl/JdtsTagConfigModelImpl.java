package kmg.tool.jdts.application.model.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import kmg.core.infrastructure.model.val.KmgValDataModel;
import kmg.core.infrastructure.model.val.KmgValsModel;
import kmg.core.infrastructure.model.val.impl.KmgValDataModelImpl;
import kmg.core.infrastructure.model.val.impl.KmgValsModelImpl;
import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.cmn.infrastructure.type.msg.KmgToolValMsgTypes;
import kmg.tool.jdts.application.model.JdtsLocationConfigModel;
import kmg.tool.jdts.application.model.JdtsTagConfigModel;
import kmg.tool.jdts.application.types.JdtsConfigKeyTypes;
import kmg.tool.jdts.application.types.JdtsInsertPositionTypes;
import kmg.tool.jdts.application.types.JdtsOverwriteTypes;

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
    private KmgJavadocTagTypes tag;

    /** タグ名 */
    private String tagName;

    /** タグの指定値 */
    private String tagValue;

    /** タグの説明 */
    private String tagDescription;

    /** 配置場所の設定 */
    private JdtsLocationConfigModel location;

    /** タグの挿入位置 */
    private JdtsInsertPositionTypes insertPosition;

    /** 上書き設定 */
    private JdtsOverwriteTypes overwrite;

    /**
     * コンストラクタ<br>
     *
     * @param tagConfig
     *                  タグ設定
     *
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    public JdtsTagConfigModelImpl(final Map<String, Object> tagConfig) throws KmgToolValException {

        final KmgValsModel valsModel = new KmgValsModelImpl();

        /* 基本項目の設定 */
        final KmgValsModel basicItemsModel = this.setupBasicItems(tagConfig);
        valsModel.merge(basicItemsModel);

        /* 配置場所の設定 */
        final KmgValsModel locationModel = this.setupLocation(tagConfig);
        valsModel.merge(locationModel);

        /* 挿入位置の設定 */
        final KmgValsModel insertPositionModel = this.setupInsertPosition(tagConfig);
        valsModel.merge(insertPositionModel);

        /* 上書きの設定 */
        final KmgValsModel overwriteModel = this.setupOverwrite(tagConfig);
        valsModel.merge(overwriteModel);

        if (valsModel.isNotEmpty()) {

            throw new KmgToolValException(valsModel);

        }

    }

    /**
     * タグの挿入位置を返す<br>
     *
     * @return タグの挿入位置
     */
    @Override
    public JdtsInsertPositionTypes getInsertPosition() {

        final JdtsInsertPositionTypes result = this.insertPosition;
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
    public JdtsOverwriteTypes getOverwrite() {

        final JdtsOverwriteTypes result = this.overwrite;
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
                final List<JavaClassificationTypes> locations = this.tag.getLocations();
                // 全ての場所に配置可能か、または特定の場所に配置可能かをチェック
                yield locations.contains(JavaClassificationTypes.NONE) || locations.contains(javaClassification);

            }

            case MANUAL:
            /* 手動モード */ {

                // 対象要素に指定された要素と一致するかチェック
                yield this.location.getTargetElements().contains(javaClassification);

            }

        };

        return result;

    }

    /**
     * 基本項目の設定<br>
     *
     * @param tagConfig
     *                  タグ設定
     *
     * @return バリデーションモデル
     */
    private KmgValsModel setupBasicItems(final Map<String, Object> tagConfig) {

        final KmgValsModel result = new KmgValsModelImpl();

        /* タグ名とタグ */
        // タグ名
        this.tagName = (String) tagConfig.get(JdtsConfigKeyTypes.TAG_NAME.get());

        if (KmgString.isEmpty(this.tagName)) {

            final KmgToolValMsgTypes valMsgTypes  = KmgToolValMsgTypes.KMGTOOL_VAL33005;
            final Object[]           valMsgArgs   = {
                JdtsConfigKeyTypes.TAG_NAME.getDisplayName(), JdtsConfigKeyTypes.TAG_NAME.getKey(),
            };
            final KmgValDataModel    valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
            result.addData(valDataModel);

        }

        // タグ
        this.tag = KmgJavadocTagTypes.getEnum(this.tagName);

        if (this.tag == KmgJavadocTagTypes.NONE) {

            final KmgToolValMsgTypes valMsgTypes  = KmgToolValMsgTypes.KMGTOOL_VAL33006;
            final Object[]           valMsgArgs   = {
                JdtsConfigKeyTypes.TAG_NAME.getDisplayName(), this.tag,
            };
            final KmgValDataModel    valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
            result.addData(valDataModel);

        }

        /* タグの値 */
        this.tagValue = (String) tagConfig.get(JdtsConfigKeyTypes.TAG_VALUE.get());

        if (KmgString.isEmpty(this.tagValue)) {

            final KmgToolValMsgTypes valMsgTypes  = KmgToolValMsgTypes.KMGTOOL_VAL33007;
            final Object[]           valMsgArgs   = {
                JdtsConfigKeyTypes.TAG_VALUE.getDisplayName(), JdtsConfigKeyTypes.TAG_VALUE.getKey(),
            };
            final KmgValDataModel    valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
            result.addData(valDataModel);

        }

        /* タグの説明 */
        this.tagDescription = Optional.ofNullable(tagConfig.get(JdtsConfigKeyTypes.TAG_DESCRIPTION.get()))
            .map(Object::toString).orElse(KmgString.EMPTY);

        return result;

    }

    /**
     * 挿入位置の設定<br>
     *
     * @param tagConfig
     *                  タグ設定
     *
     * @return バリデーションモデル
     */
    private KmgValsModel setupInsertPosition(final Map<String, Object> tagConfig) {

        final KmgValsModel result = new KmgValsModelImpl();

        this.insertPosition
            = JdtsInsertPositionTypes.getEnum((String) tagConfig.get(JdtsConfigKeyTypes.INSERT_POSITION.get()));

        if (this.insertPosition == JdtsInsertPositionTypes.NONE) {

            final KmgToolValMsgTypes valMsgTypes  = KmgToolValMsgTypes.KMGTOOL_VAL33008;
            final Object[]           valMsgArgs   = {
                JdtsConfigKeyTypes.INSERT_POSITION.getDisplayName(), this.insertPosition,
            };
            final KmgValDataModel    valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
            result.addData(valDataModel);

        }

        return result;

    }

    /**
     * 配置場所の設定<br>
     *
     * @param tagConfig
     *                  タグ設定
     *
     * @return バリデーションモデル
     */
    private KmgValsModel setupLocation(final Map<String, Object> tagConfig) {

        final KmgValsModel result = new KmgValsModelImpl();

        try {

            final ObjectMapper        mapper      = new ObjectMapper(new YAMLFactory());
            final Map<String, Object> locationMap = mapper
                .convertValue(tagConfig.get(JdtsConfigKeyTypes.LOCATION.get()), Map.class);

            // 配置場所の設定の生成
            this.location = new JdtsLocationConfigModelImpl(locationMap);

        } catch (final KmgToolValException e) {

            result.merge(e.getValidationsModel());

        }

        return result;

    }

    /**
     * 上書き設定<br>
     *
     * @param tagConfig
     *                  タグ設定
     *
     * @return バリデーションモデル
     */
    private KmgValsModel setupOverwrite(final Map<String, Object> tagConfig) {

        final KmgValsModel result = new KmgValsModelImpl();

        this.overwrite = JdtsOverwriteTypes.getEnum((String) tagConfig.get(JdtsConfigKeyTypes.OVERWRITE.get()));

        if (this.overwrite == JdtsOverwriteTypes.NONE) {

            final KmgToolValMsgTypes valMsgTypes  = KmgToolValMsgTypes.KMGTOOL_VAL33009;
            final Object[]           valMsgArgs   = {
                JdtsConfigKeyTypes.OVERWRITE.getDisplayName(), this.overwrite,
            };
            final KmgValDataModel    valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
            result.addData(valDataModel);

        }

        return result;

    }

}
