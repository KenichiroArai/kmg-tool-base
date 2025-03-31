package kmg.tool.domain.model.impl;

import java.util.UUID;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.domain.model.JavadocReplacementModel;
import kmg.tool.domain.types.JavaClassificationTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc置換モデル<br>
 *
 * @author KenichiroArai
 */
public class JavadocReplacementModelImpl implements JavadocReplacementModel {

    /** 元のJavadoc */
    private final String sourceJavadoc;

    /** 元のコード */
    private final String sourceCode;

    /** 置換用識別子 */
    private final UUID identifier;

    /** Java区分 */
    private JavaClassificationTypes javaClassification;

    /** 置換後のJavadoc */
    private String replacedJavadoc;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @param sourceJavadoc
     *                      元のJavadoc
     * @param sourceCode
     *                      元のコード
     */
    public JavadocReplacementModelImpl(final String sourceJavadoc, final String sourceCode) {

        this.sourceJavadoc = sourceJavadoc;
        this.sourceCode = sourceCode;
        this.identifier = UUID.randomUUID();

    }

    /**
     * 置換後のJavadocを作成する。<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean createReplacedJavadoc() throws KmgToolException {

        boolean result = false;

        // TODO KenichiroArai 2025/03/30 実装中
        this.replacedJavadoc = "aaaaaa";

        result = true;
        return result;

    }

    /**
     * 置換用識別子を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 置換用識別子
     */
    @Override
    public UUID getIdentifier() {

        final UUID result = this.identifier;
        return result;

    }

    /**
     * Java区分を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Java区分
     */
    @Override
    public JavaClassificationTypes getJavaClassification() {

        final JavaClassificationTypes result = this.javaClassification;
        return result;

    }

    /**
     * 置換後のJavadocを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 置換後のJavadoc
     */
    @Override
    public String getReplacedJavadoc() {

        final String result = this.replacedJavadoc;
        return result;

    }

    /**
     * 元のコードを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 元のコード
     */
    @Override
    public String getSourceCode() {

        final String result = this.sourceCode;
        return result;

    }

    /**
     * 元のJavadocを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 元のJavadoc
     */
    @Override
    public String getSourceJavadoc() {

        final String result = this.sourceJavadoc;
        return result;

    }

    /**
     * Java区分を特定する<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean specifyJavaClassification() throws KmgToolException {

        boolean result = false;

        final String[] codeLines = KmgDelimiterTypes.LINE_SEPARATOR.split(this.sourceCode);

        for (final String codeLine : codeLines) {

            // TODO KenichiroArai 2025/03/30 ハードコード

            // コード行の先頭が「@」始まりか
            if (codeLine.matches("^\\s*@.*")) {
                // 始まりの場合

                // 次の行へ
                continue;

            }

            // TODO KenichiroArai 2025/03/30 判別をもっと詳細にする
            if (codeLine.matches("^\\s*class\\s+\\w+.*")) {

                // TODO KenichiroArai 2025/03/30 トレース
                System.out.println("クラス");
                this.javaClassification = JavaClassificationTypes.CLASS;
                break;

            }

            // TODO KenichiroArai 2025/03/30 未実装

        }

        result = true;
        return result;

    }

}
