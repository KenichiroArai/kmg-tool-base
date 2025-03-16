package kmg.tool.domain.model.io.dtc;

import kmg.tool.domain.types.io.dtc.DtcTransformTypes;

/**
 * テンプレートの動的変換派生プレースホルダー定義モデル<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 */
public interface DtcDerivedPlaceholderModel {

    /**
     * 表示名を返す
     *
     * @return 表示名
     */
    String getDisplayName();

    /**
     * 置換パターンを返す
     *
     * @return 置換パターン
     */
    String getReplacementPattern();

    /**
     * ソースキーを返す
     *
     * @return ソースキー
     */
    String getSourceKey();

    /**
     * 変換処理の種類を返す
     *
     * @return 換処理の種類
     */
    DtcTransformTypes getTransformationTypes();

}
