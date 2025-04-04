package kmg.tool.application.model.jda.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kmg.core.infrastructure.types.KmgJavadocLocationTypes;
import kmg.tool.application.model.jda.JdaLocationConfigModel;
import kmg.tool.application.types.JdaLocationModeTypes;

/**
 * Javadoc追加のタグの配置場所設定モデル<br>
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
public class JdaLocationConfigModelImpl implements JdaLocationConfigModel {

    /** 配置方法 */
    private final JdaLocationModeTypes mode;

    /** 対象要素の種類（手動モード時のみ使用） */
    private final List<KmgJavadocLocationTypes> targetElements;

    /**
     * コンストラクタ<br>
     *
     * @param locationMap
     *                    配置場所の設定マップ
     */
    @SuppressWarnings("unchecked")
    public JdaLocationConfigModelImpl(final Map<String, Object> locationMap) {

        /* 配置方法の設定 */
        this.mode = JdaLocationModeTypes.getEnum((String) locationMap.get("mode"));

        /* 対象要素の種類の設定 */
        final List<String> targetElementsKeys = (List<String>) locationMap.get("targetElements");
        this.targetElements = new ArrayList<>();

        if (targetElementsKeys != null) {

            for (final String key : targetElementsKeys) {

                final KmgJavadocLocationTypes type = KmgJavadocLocationTypes.getEnum(key);

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
    public String getMode() {

        final String result = this.mode.getKey();
        return result;

    }

    /**
     * 対象要素の種類を返す<br>
     *
     * @return 対象要素の種類
     */
    @Override
    public List<String> getTargetElements() {

        final List<String> result = new ArrayList<>();

        for (final KmgJavadocLocationTypes element : this.targetElements) {

            result.add(element.getKey());

        }
        return result;

    }
}
