package kmg.tool.base.dtc.domain.model.impl;

import kmg.tool.base.dtc.domain.model.DtcDerivedPlaceholderModel;
import kmg.tool.base.dtc.domain.types.DtcTransformTypes;

/**
 * テンプレートの動的変換派生プレースホルダー定義モデル実装<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public class DtcDerivedPlaceholderModelImpl implements DtcDerivedPlaceholderModel {

    /**
     * 表示名
     *
     * @since 0.2.0
     */
    private final String displayName;

    /**
     * 置換パターン
     *
     * @since 0.2.0
     */
    private final String replacementPattern;

    /**
     * ソースキー
     *
     * @since 0.2.0
     */
    private final String sourceKey;

    /**
     * 変換処理の種類
     *
     * @since 0.2.0
     */
    private final DtcTransformTypes transformTypes;

    /**
     * コンストラクタ
     *
     * @since 0.2.0
     *
     * @param displayName
     *                           表示名
     * @param replacementPattern
     *                           置換パターン
     * @param sourceKey
     *                           ソースキー
     * @param transformTypes
     *                           変換処理の種類
     */
    public DtcDerivedPlaceholderModelImpl(final String displayName, final String replacementPattern,
        final String sourceKey, final DtcTransformTypes transformTypes) {

        this.displayName = displayName;
        this.replacementPattern = replacementPattern;
        this.sourceKey = sourceKey;
        this.transformTypes = transformTypes;

    }

    /**
     * 表示名を返す
     *
     * @since 0.2.0
     *
     * @return 表示名
     */
    @Override
    public String getDisplayName() {

        final String result = this.displayName;
        return result;

    }

    /**
     * 置換パターンを返す
     *
     * @since 0.2.0
     *
     * @return 置換パターン
     */
    @Override
    public String getReplacementPattern() {

        final String result = this.replacementPattern;
        return result;

    }

    /**
     * ソースキーを返す
     *
     * @since 0.2.0
     *
     * @return ソースキー
     */
    @Override
    public String getSourceKey() {

        final String result = this.sourceKey;
        return result;

    }

    /**
     * 変換処理の種類を返す
     *
     * @since 0.2.0
     *
     * @return 変換処理の種類
     */
    @Override
    public DtcTransformTypes getTransformationTypes() {

        final DtcTransformTypes result = this.transformTypes;
        return result;

    }

}
