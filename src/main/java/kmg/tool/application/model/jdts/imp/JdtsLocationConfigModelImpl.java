package kmg.tool.application.model.jdts.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.tool.application.model.jdts.JdtsLocationConfigModel;
import kmg.tool.application.types.JdtsLocationModeTypes;

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
    private final JdtsLocationModeTypes mode;

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
    public JdtsLocationConfigModelImpl(final Map<String, Object> locationMap) {

        // TODO KenichiroArai 2025/05/02 ハードコード

        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        /* 配置方法の設定 */
        this.mode = JdtsLocationModeTypes.getEnum((String) locationMap.get("mode"));

        /** 誤配置時に削除するかどうかの設定 */
        this.removeIfMisplaced = Boolean.parseBoolean(String.valueOf(locationMap.get("removeIfMisplaced")));

        /* 対象要素の種類の設定 */
        final List<String> targetElementsKeys = mapper.convertValue(locationMap.get("targetElements"), List.class);
        this.targetElements = new ArrayList<>();

        if (targetElementsKeys != null) {

            if (this.mode != JdtsLocationModeTypes.MANUAL) {

                // TODO KenichiroArai 2025/05/02 例外処理
                return;

            }

            for (final String key : targetElementsKeys) {

                final JavaClassificationTypes type = JavaClassificationTypes.getEnum(key);

                this.targetElements.add(type);

            }

        } else if (this.mode == JdtsLocationModeTypes.MANUAL) {

            // TODO KenichiroArai 2025/05/02 例外処理
        }

    }

    /**
     * 配置方法を返す<br>
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
