package kmg.tool.base.jdoc.domain.model;

import kmg.core.infrastructure.types.KmgJavadocTagTypes;

/**
 * Javadocタグ情報<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface JavadocTagModel {

    /**
     * 説明を返す<br>
     *
     * @since 0.2.0
     *
     * @return 説明
     */
    String getDescription();

    /**
     * タグを返す<br>
     *
     * @since 0.2.0
     *
     * @return タグ
     */
    KmgJavadocTagTypes getTag();

    /**
     * 対象文字列を返す<br>
     *
     * @since 0.2.0
     *
     * @return 対象文字列
     */
    String getTargetStr();

    /**
     * 指定値を返す<br>
     *
     * @since 0.2.0
     *
     * @return 指定値
     */
    String getValue();

}
