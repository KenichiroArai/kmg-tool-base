package kmg.tool.application.model.jda;

import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.application.types.JdaInsertPositionTypes;
import kmg.tool.application.types.JdaOverwriteTypes;

/**
 * Javadoc追加のタグ設定モデルインターフェース<br>
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
    JdaInsertPositionTypes getInsertPosition();

    /**
     * 配置場所の設定を返す<br>
     *
     * @return 配置場所の設定
     */
    JdaLocationConfigModel getLocation();

    /**
     * 上書き設定を返す<br>
     *
     * @return 上書き設定
     */
    JdaOverwriteTypes getOverwrite();

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
     * 誤配置時に削除するかどうかを返す<br>
     *
     * @return true：削除する、false：削除しない
     */
    boolean isRemoveIfMisplaced();
}
