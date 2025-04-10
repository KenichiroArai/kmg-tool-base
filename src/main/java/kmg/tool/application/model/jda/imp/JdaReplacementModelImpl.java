package kmg.tool.application.model.jda.imp;

import java.util.Arrays;
import java.util.UUID;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.logic.JavadocReplacementLogic;
import kmg.tool.application.logic.impl.JavadocReplacementLogicImpl;
import kmg.tool.application.model.jda.JdaReplacementModel;
import kmg.tool.application.model.jda.JdtsConfigurationsModel;
import kmg.tool.domain.model.JavadocModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加の置換モデル<br>
 * <p>
 * Jdaは、JavadocAppenderの略。
 * </p>
 *
 * @author KenichiroArai
 */
public class JdaReplacementModelImpl implements JdaReplacementModel {

    /** 元のJavadoc */
    private final String srcJavadoc;

    /** 元のコードブロック */
    private final String srcCodeBloc;

    /** 元のJavadocモデル */
    private JavadocModel srcJavadocModel;

    /** 元のJava区分 */
    private JavaClassificationTypes srcJavaClassification;

    /** avadocタグ設定の構成モデル */
    private final JdtsConfigurationsModel jdtsConfigurationsModel;

    /** 置換用識別子 */
    private final UUID identifier;

    /** 置換後のJavadoc */
    private String replacedJavadoc;

    /** Javadoc置換ロジック */
    private final JavadocReplacementLogic javadocReplacementLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @param srcJavadoc
     *                                元のJavadoc
     * @param srcCodeBloc
     *                                元のコードブロック
     * @param jdtsConfigurationsModel
     *                                Javadocタグ設定の構成モデル
     */
    public JdaReplacementModelImpl(final String srcJavadoc, final String srcCodeBloc,
        final JdtsConfigurationsModel jdtsConfigurationsModel) {

        this.srcJavadoc = srcJavadoc;
        this.srcCodeBloc = srcCodeBloc;
        this.identifier = UUID.randomUUID();
        this.jdtsConfigurationsModel = jdtsConfigurationsModel;

        // TODO KenichiroArai 2025/04/10 どのようにするかを考える
        this.javadocReplacementLogic = new JavadocReplacementLogicImpl();

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

        /* 準備 */
        this.javadocReplacementLogic.initialize(this.srcJavadoc, this.jdtsConfigurationsModel,
            this.srcJavaClassification);

        /* Javadoc置換ロジックを実行 */
        result = this.javadocReplacementLogic.createReplacedJavadoc();

        this.replacedJavadoc = this.javadocReplacementLogic.getReplacedJavadoc();

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
     * 元のコードブロックを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 元のコードブロック
     */
    @Override
    public String getSrcCodeBloc() {

        final String result = this.srcCodeBloc;
        return result;

    }

    /**
     * 元のJava区分を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 元のJava区分
     */
    @Override
    public JavaClassificationTypes getSrcJavaClassification() {

        final JavaClassificationTypes result = this.srcJavaClassification;
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
    public String getSrcJavadoc() {

        final String result = this.srcJavadoc;
        return result;

    }

    /**
     * 元のJavadocモデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 元のJavadocモデル
     */
    @Override
    public JavadocModel getSrcJavadocModel() {

        final JavadocModel result = this.srcJavadocModel;
        return result;

    }

    /**
     * Java区分を特定する<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return true：区分が特定できた、false：区分がNONE
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean specifyJavaClassification() throws KmgToolException {

        boolean result = false;

        /* Java区分の初期化 */
        this.srcJavaClassification = JavaClassificationTypes.NONE;

        /* コードを行ごとに確認する */

        // コードを行ごとに取得する。空行は除外する。
        final String[] codeLines = Arrays.stream(KmgDelimiterTypes.LINE_SEPARATOR.split(this.srcCodeBloc))
            .filter(KmgString::isNotBlank).toArray(String[]::new);

        for (final String codeLine : codeLines) {

            // Java区分を判別
            this.srcJavaClassification = JavaClassificationTypes.identify(codeLine);

            // Javadoc対象外か
            if (this.srcJavaClassification.isNotJavadocTarget()) {
                // 対象外の場合

                continue;

            }

            result = true;
            break;

        }

        return result;

    }

}
