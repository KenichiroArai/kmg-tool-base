package kmg.tool.base.dtc.domain.model;

/**
 * テンプレートの動的変換モデル<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface DtcTransformModel {

    /**
     * 指定された変換処理を適用する<br>
     *
     * @since 0.2.0
     */
    void apply();

    /**
     * 元の値を返す<br>
     *
     * @since 0.2.0
     *
     * @return 元の値
     */
    String getOriginalValue();

    /**
     * 変換後の値を返す<br>
     *
     * @since 0.2.0
     *
     * @return 変換後の値
     */
    String getTransformedValue();
}
