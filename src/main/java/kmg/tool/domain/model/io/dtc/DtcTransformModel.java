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
    private final String value;

    /**
     * コンストラクタ<br>
     *
     * @param value
     *              元の値
     */
    public DtcTransformModel(final String value) {

        if (value == null) {

            this.value = KmgString.EMPTY;
            return;

        }

        this.value = value;

    }

    /**
     * 指定された変換処理を適用する<br>
     *
     * @param dtcTransformTypes
     *                          テンプレートの動的変換変換処理の種類
     *
     * @return 変換後の値
     */
    public String apply(final DtcTransformTypes dtcTransformTypes) {

        String result = KmgString.EMPTY;

        result = switch (dtcTransformTypes) {

            case NONE          -> this.none();
            case CAPITALIZE    -> this.capitalize();
            case TO_UPPER_CASE -> this.toUpperCase();
            case TO_LOWER_CASE -> this.toLowerCase();

        };

        return result;

    }

    /**
     * 文字列の最初の文字を大文字に変換<br>
     *
     * @return 最初の文字を大文字に変換した値
     */
    public String capitalize() {

        String result;

        if (this.value.isEmpty()) {

            result = this.value;
            return result;

        }

        result = KmgString.capitalize(this.value);
        return result;

    }

    /**
     * 元の値を返す<br>
     *
     * @return 元の値
     */
    public String getValue() {

        String result;

        result = this.value;
        return result;

    }

    /**
     * 変換なし<br>
     * 元の値をそのまま返す
     *
     * @return 元の値
     */
    public String none() {

        String result;

        result = this.value;
        return result;

    }

    /**
     * すべて小文字に変換<br>
     *
     * @return すべて小文字に変換した値
     */
    public String toLowerCase() {

        String result;

        result = this.value.toLowerCase();
        return result;

    }

    /**
     * すべて大文字に変換<br>
     *
     * @return すべて大文字に変換した値
     */
    public String toUpperCase() {

        String result;

        result = this.value.toUpperCase();
        return result;

    }
}
