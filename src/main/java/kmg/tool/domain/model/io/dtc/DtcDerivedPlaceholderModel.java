package kmg.tool.domain.model.io.dtc;

/**
 * テンプレートの動的変換派生プレースホルダー定義モデル<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 */
public class DtcDerivedPlaceholderModel {

    /**
     * 表示名
     */
    private final String displayName;

    /**
     * 置換パターン
     */
    private final String replacementPattern;

    /**
     * ソースキー
     */
    private final String sourceKey;

    /**
     * 変換処理
     */
    private final String transformation;

    /**
     * コンストラクタ
     *
     * @param displayName
     *                           表示名
     * @param replacementPattern
     *                           置換パターン
     * @param sourceKey
     *                           ソースキー
     * @param transformation
     *                           変換処理
     */
    public DtcDerivedPlaceholderModel(final String displayName, final String replacementPattern, final String sourceKey,
        final String transformation) {

        this.displayName = displayName;
        this.replacementPattern = replacementPattern;
        this.sourceKey = sourceKey;
        this.transformation = transformation;

    }

    /**
     * 表示名を返す
     *
     * @return 表示名
     */
    public String getDisplayName() {

        final String result = this.displayName;
        return result;

    }

    /**
     * 置換パターンを返す
     *
     * @return 置換パターン
     */
    public String getReplacementPattern() {

        final String result = this.replacementPattern;
        return result;

    }

    /**
     * ソースキーを返す
     *
     * @return ソースキー
     */
    public String getSourceKey() {

        final String result = this.sourceKey;
        return result;

    }

    /**
     * 変換処理を返す
     *
     * @return 変換処理
     */
    public String getTransformation() {

        final String result = this.transformation;
        return result;

    }

}
