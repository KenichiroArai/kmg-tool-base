package kmg.tool.base.jdts.application.model;

import java.util.List;

import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.tool.base.jdts.application.types.JdtsLocationModeTypes;

/**
 * Javadocタグ設定の配置場所設定インタフェース<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface JdtsLocationConfigModel {

    /**
     * 配置方法を返す<br>
     *
     * @since 0.2.0
     *
     * @return 配置方法
     */
    JdtsLocationModeTypes getMode();

    /**
     * 対象要素の種類を返す<br>
     *
     * @since 0.2.0
     *
     * @return 対象要素の種類
     */
    List<JavaClassificationTypes> getTargetElements();

    /**
     * 誤配置時に削除するかどうかを返す<br>
     *
     * @since 0.2.0
     *
     * @return true：削除する、false：削除しない
     */
    boolean isRemoveIfMisplaced();
}
