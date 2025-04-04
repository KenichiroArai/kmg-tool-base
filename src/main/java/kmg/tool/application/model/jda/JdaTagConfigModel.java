package kmg.tool.application.model.jda;

import java.util.Map;

import kmg.core.infrastructure.types.KmgJavadocTagTypes;

/**
 * Javadoc追加のタグ設定モデル<br>
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
public interface JdaTagConfigModel {

    /**
     * タグの挿入位置を返す<br>
     *
     * @return タグの挿入位置
     */
    String getInsertPosition();

    /**
     * 配置場所の設定を返す<br>
     *
     * @return 配置場所の設定
     */
    Map<String, Object> getLocation();

    /**
     * 配置場所のモードを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 配置場所のモード
     */
    String getLocationMode();

    /**
     * 上書き設定を返す<br>
     *
     * @return 上書き設定
     */
    String getOverwrite();

    /**
     * タグを返す<br>
     *
     * @return タグ
     */
    KmgJavadocTagTypes getTag();

    /**
     * タグの説明を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return タグの説明
     */
    String getTagDescription();

    /**
     * タグ名を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
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
}
