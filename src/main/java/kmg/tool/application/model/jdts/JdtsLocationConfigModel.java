package kmg.tool.application.model.jdts;

import java.util.List;

import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.tool.application.types.jdts.JdtsLocationModeTypes;

/**
 * Javadocタグ設定の配置場所設定インタフェース<br>
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
public interface JdtsLocationConfigModel {

    /**
     * 配置方法を返す<br>
     *
     * @return 配置方法
     */
    JdtsLocationModeTypes getMode();

    /**
     * 対象要素の種類を返す<br>
     *
     * @return 対象要素の種類
     */
    List<JavaClassificationTypes> getTargetElements();

    /**
     * 誤配置時に削除するかどうかを返す<br>
     *
     * @return true：削除する、false：削除しない
     */
    boolean isRemoveIfMisplaced();
}
