package kmg.tool.application.model;

import java.util.Map;

/**
 * Javadoc追加のタグ設定モデル<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JavadocAppenderTagConfigModel {

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
     * タグ名を返す<br>
     *
     * @return タグ名
     */
    String getName();

    /**
     * 上書き設定を返す<br>
     *
     * @return 上書き設定
     */
    String getOverwrite();

    /**
     * タグの値を返す<br>
     *
     * @return タグの値
     */
    String getText();
}
