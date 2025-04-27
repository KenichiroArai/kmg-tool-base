package kmg.tool.application.model.jdts.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.tool.application.model.jdts.JdtsLocationConfigModel;
import kmg.tool.application.types.JdaLocationModeTypes;

/**
 * Javadocタグ設定の配置場所設定<br>
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
public class JdtsLocationConfigModelImpl implements JdtsLocationConfigModel {

    /** 配置方法 */
    private final JdaLocationModeTypes mode;

    /** 誤配置時に削除するかどうか */
    private final boolean removeIfMisplaced;

    /** 対象要素の種類（手動モード時のみ使用） */
    private final List<JavaClassificationTypes> targetElements;

    /**
     * コンストラクタ<br>
     *
     * @param locationMap
     *                    配置場所の設定マップ
     */
    @SuppressWarnings("unchecked")
    public JdtsLocationConfigModelImpl(final Map<String, Object> locationMap) {

        /* 配置方法の設定 */
        this.mode = JdaLocationModeTypes.getEnum((String) locationMap.get("mode"));

        /** 誤配置時に削除するかどうかの設定 */
        this.removeIfMisplaced = Boolean.parseBoolean(String.valueOf(locationMap.get("removeIfMisplaced")));

        /* 対象要素の種類の設定 */
        final List<String> targetElementsKeys = (List<String>) locationMap.get("targetElements");
        this.targetElements = new ArrayList<>();

        if (targetElementsKeys != null) {

            for (final String key : targetElementsKeys) {

                final JavaClassificationTypes type = JavaClassificationTypes.getEnum(key);

                this.targetElements.add(type);

            }

        }

    }

    /**
     * 配置方法を返す<br>
     *
     * @return 配置方法
     */
    @Override
    public JdaLocationModeTypes getMode() {

        final JdaLocationModeTypes result = this.mode;
        return result;

    }

    /**
     * 対象要素の種類を返す<br>
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
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：削除する、false：削除しない
     */
    @Override
    public boolean isRemoveIfMisplaced() {

        final boolean result = this.removeIfMisplaced;
        return result;

    }
}
