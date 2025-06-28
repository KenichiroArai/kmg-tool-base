package kmg.tool.dtc.domain.model;

/**
 * テンプレートの動的変換モデル<br>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 */
public interface DtcTransformModel {

    /**
     * 指定された変換処理を適用する<br>
     */
    void apply();

    /**
     * 元の値を返す<br>
     *
     * @return 元の値
     */
    String getOriginalValue();

    /**
     * 変換後の値を返す<br>
     *
     * @return 変換後の値
     */
    String getTransformedValue();
}
