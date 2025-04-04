package kmg.tool.application.model.jda;

import java.util.List;

/**
 * Javadocタグの配置場所設定インタフェース<br>
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
public interface JdaLocationConfigModel {

    /**
     * 配置方法を返す<br>
     *
     * @return 配置方法
     */
    String getMode();

    /**
     * 対象要素の種類を返す<br>
     *
     * @return 対象要素の種類
     */
    List<String> getTargetElements();
}
