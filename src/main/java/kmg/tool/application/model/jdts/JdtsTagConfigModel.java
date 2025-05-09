package kmg.tool.application.model.jdts;

import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.application.types.jdts.JdtsInsertPositionTypes;
import kmg.tool.application.types.jdts.JdtsOverwriteTypes;

/**
 * Javadocタグ設定のタグ構成モデルインターフェース<br>
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
public interface JdtsTagConfigModel {

    /**
     * タグの挿入位置を返す<br>
     *
     * @return タグの挿入位置
     */
    JdtsInsertPositionTypes getInsertPosition();

    /**
     * 配置場所の設定を返す<br>
     *
     * @return 配置場所の設定
     */
    JdtsLocationConfigModel getLocation();

    /**
     * 上書き設定を返す<br>
     *
     * @return 上書き設定
     */
    JdtsOverwriteTypes getOverwrite();

    /**
     * タグを返す<br>
     *
     * @return タグ
     */
    KmgJavadocTagTypes getTag();

    /**
     * タグの説明を返す<br>
     *
     * @return タグの説明
     */
    String getTagDescription();

    /**
     * タグ名を返す<br>
     *
     * @return タグ名
     */
    String getTagName();

    /**
     * タグの指定値を返す<br>
     *
     * @return タグの指定値
     */
    String getTagValue();

    /**
     * タグの配置がJava区分に一致するか<br>
     *
     * @param javaClassification
     *                           Java区分
     *
     * @return true：一致する、false：一致しない
     */
    boolean isProperlyPlaced(JavaClassificationTypes javaClassification);
}
