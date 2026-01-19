package kmg.tool.base.jdts.application.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import kmg.core.infrastructure.model.val.KmgValDataModel;
import kmg.core.infrastructure.model.val.KmgValsModel;
import kmg.core.infrastructure.model.val.impl.KmgValDataModelImpl;
import kmg.core.infrastructure.model.val.impl.KmgValsModelImpl;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseValException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseValMsgTypes;
import kmg.tool.base.jdts.application.model.JdtsLocationConfigModel;
import kmg.tool.base.jdts.application.types.JdtsConfigKeyTypes;
import kmg.tool.base.jdts.application.types.JdtsLocationModeTypes;

/**
 * Javadocタグ設定の配置場所設定<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
public class JdtsLocationConfigModelImpl implements JdtsLocationConfigModel {

    /**
     * 配置方法
     *
     * @since 0.2.0
     */
    private final JdtsLocationModeTypes mode;

    /**
     * 誤配置時に削除するかどうか
     *
     * @since 0.2.0
     */
    private final boolean removeIfMisplaced;

    /**
     * 対象要素の種類（手動モード時のみ使用）
     *
     * @since 0.2.0
     */
    private final List<JavaClassificationTypes> targetElements;

    /**
     * コンストラクタ<br>
     *
     * @since 0.2.4
     *
     * @param locationMap
     *                    配置場所の設定マップ
     *
     * @throws KmgToolBaseValException
     *                             KMGツールバリデーション例外
     */
    public JdtsLocationConfigModelImpl(final Map<String, Object> locationMap) throws KmgToolBaseValException {

        final KmgValsModel valsModel = new KmgValsModelImpl();

        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        /* 配置方法の設定 */
        this.mode = JdtsLocationModeTypes.getEnum((String) locationMap.get(JdtsConfigKeyTypes.MODE.get()));

        /* 誤配置時に削除するかどうかの設定 */
        this.removeIfMisplaced
            = Boolean.parseBoolean(String.valueOf(locationMap.get(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get())));

        /* 対象要素の種類の設定 */
        final List<String> targetElementsKeys
            = mapper.convertValue(locationMap.get(JdtsConfigKeyTypes.TARGET_ELEMENTS.get()), List.class);
        this.targetElements = new ArrayList<>();

        if (targetElementsKeys != null) {

            if (this.mode != JdtsLocationModeTypes.MANUAL) {

                final KmgToolBaseValMsgTypes valMsgTypes  = KmgToolBaseValMsgTypes.KMGTOOLBASE_VAL13002;
                final Object[]           valMsgArgs   = {
                    JdtsConfigKeyTypes.TARGET_ELEMENTS.getDisplayName(), JdtsLocationModeTypes.MANUAL.getDisplayName(),
                };
                final KmgValDataModel    valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
                valsModel.addData(valDataModel);

            }

            for (final String key : targetElementsKeys) {

                final JavaClassificationTypes type = JavaClassificationTypes.getEnum(key);

                if (type == JavaClassificationTypes.NONE) {

                    final KmgToolBaseValMsgTypes valMsgTypes  = KmgToolBaseValMsgTypes.KMGTOOLBASE_VAL13003;
                    final Object[]           valMsgArgs   = {
                        JdtsConfigKeyTypes.TARGET_ELEMENTS.getDisplayName(),
                    };
                    final KmgValDataModel    valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
                    valsModel.addData(valDataModel);

                }

                this.targetElements.add(type);

            }

        } else if (this.mode == JdtsLocationModeTypes.MANUAL) {

            final KmgToolBaseValMsgTypes valMsgTypes  = KmgToolBaseValMsgTypes.KMGTOOLBASE_VAL13004;
            final Object[]           valMsgArgs   = {
                JdtsLocationModeTypes.MANUAL.getDisplayName(), JdtsConfigKeyTypes.TARGET_ELEMENTS.getDisplayName(),
            };
            final KmgValDataModel    valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
            valsModel.addData(valDataModel);

        }

        /* バリデーションをマージする */
        if (valsModel.isNotEmpty()) {

            throw new KmgToolBaseValException(valsModel);

        }

    }

    /**
     * 配置方法を返す<br>
     *
     * @since 0.2.0
     *
     * @return 配置方法
     */
    @Override
    public JdtsLocationModeTypes getMode() {

        final JdtsLocationModeTypes result = this.mode;
        return result;

    }

    /**
     * 対象要素の種類を返す<br>
     *
     * @since 0.2.0
     *
     * @return 対象要素の種類
     */
    @Override
    public List<JavaClassificationTypes> getTargetElements() {

        final List<JavaClassificationTypes> result = this.targetElements;
        return result;

    }

    /**
     * 誤配置時に削除するかどうかを返す<br>
     *
     * @since 0.2.0
     *
     * @return true：削除する、false：削除しない
     */
    @Override
    public boolean isRemoveIfMisplaced() {

        final boolean result = this.removeIfMisplaced;
        return result;

    }
}
