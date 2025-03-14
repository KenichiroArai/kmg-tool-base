package kmg.tool.domain.model.io.dtc;

import kmg.core.infrastructure.type.KmgString;
import kmg.tool.domain.types.io.dtc.DtcTransformTypes;

/**
 * テンプレートの動的変換モデル<br>
 * <p>
 * 文字列変換処理の各種メソッドを提供します。
 * </p>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 */
public class DtcTransformModel {

    /** 元の値 */
    private final String originalValue;

    /** 変換後の値 */
    private String transformedValue;

    /**
     * コンストラクタ<br>
     *
     * @param value
     *              元の値
     */
    public DtcTransformModel(final String value) {

        if (value == null) {

            this.originalValue = KmgString.EMPTY;
            this.transformedValue = KmgString.EMPTY;
            return;

        }

        this.originalValue = value;
        this.transformedValue = value;

    }

    /**
     * 指定された変換処理を適用する<br>
     *
     * @param dtcTransformTypes
     *                          テンプレートの動的変換変換処理の種類
     */
    public void apply(final DtcTransformTypes dtcTransformTypes) {

        switch (dtcTransformTypes) {

            case NONE          -> this.transformedValue = this.none();
            case CAPITALIZE    -> this.transformedValue = this.capitalize();
            case TO_UPPER_CASE -> this.transformedValue = this.toUpperCase();
            case TO_LOWER_CASE -> this.transformedValue = this.toLowerCase();

        }

    }

    /**
     * 元の値を返す<br>
     *
     * @return 元の値
     */
    public String getOriginalValue() {

        String result;

        result = this.originalValue;
        return result;

    }

    /**
     * 変換後の値を返す<br>
     *
     * @return 変換後の値
     */
    public String getTransformedValue() {

        String result;

        result = this.transformedValue;
        return result;

    }

    /**
     * 文字列の最初の文字を大文字に変換<br>
     *
     * @return 最初の文字を大文字に変換した値
     */
    private String capitalize() {

        String result;

        if (this.originalValue.isEmpty()) {

            result = this.originalValue;
            return result;

        }

        result = KmgString.capitalize(this.originalValue);
        return result;

    }

    /**
     * 変換なし<br>
     * 元の値をそのまま返す
     *
     * @return 元の値
     */
    private String none() {

        String result;

        result = this.originalValue;
        return result;

    }

    /**
     * すべて小文字に変換<br>
     *
     * @return すべて小文字に変換した値
     */
    private String toLowerCase() {

        String result;

        result = this.originalValue.toLowerCase();
        return result;

    }

    /**
     * すべて大文字に変換<br>
     *
     * @return すべて大文字に変換した値
     */
    private String toUpperCase() {

        String result;

        result = this.originalValue.toUpperCase();
        return result;

    }
}
