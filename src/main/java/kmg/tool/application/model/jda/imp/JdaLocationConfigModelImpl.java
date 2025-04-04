package kmg.tool.application.model.jda.imp;

import java.util.List;

import kmg.tool.application.model.jda.JdaLocationConfigModel;

/**
 * Javadocタグの配置場所設定モデル<br>
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
    private final String mode;

    /** 対象要素の種類（手動モード時のみ使用） */
    private final List<String> targetElements;

    /**
     * コンストラクタ<br>
     *
     * @param mode
     *                       配置方法
     * @param targetElements
     *                       対象要素の種類
     */
    public JdaLocationConfigModelImpl(final String mode, final List<String> targetElements) {

        this.mode = mode;
        this.targetElements = targetElements;

    }

    /**
     * 配置方法を返す<br>
     *
     * @return 配置方法
     */
    @Override
    public String getMode() {

        final String result = this.mode;
        return result;

    }

    /**
     * 対象要素の種類を返す<br>
     *
     * @return 対象要素の種類
     */
    @Override
    public List<String> getTargetElements() {

        final List<String> result = this.targetElements;
        return result;

    }
}
