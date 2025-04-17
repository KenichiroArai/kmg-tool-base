package kmg.tool.application.model.jdts.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.model.jdts.JdtsBlockModel;
import kmg.tool.domain.model.JavadocModel;
import kmg.tool.domain.model.impl.JavadocModelImpl;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグ設定のブロックモデルインタフェース<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JdtsBlockModelImpl implements JdtsBlockModel {

    /** 識別子 */
    private final UUID id;

    /** オリジナルブロック */
    private final String orgBlock;

    /** Javadocモデル */
    private JavadocModel javadocModel;

    /** アノテーションリスト */
    private final List<String> annotations;

    /** コードブロック */
    private String codeBlock;

    /** Java区分 */
    private JavaClassificationTypes javaClassification;

    /**
     * コンストラクタ
     *
     * @param block
     *              ブロック。「/**」で分割されたブロック。
     */
    public JdtsBlockModelImpl(final String block) {

        this.orgBlock = block;

        this.id = UUID.randomUUID();

        this.annotations = new ArrayList<>();

        this.javaClassification = JavaClassificationTypes.NONE;

    }

    /**
     * アノテーションリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return アノテーションリスト
     */
    @Override
    public List<String> getAnnotations() {

        final List<String> result = this.annotations;
        return result;

    }

    /**
     * 識別子を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 識別子
     */
    @Override
    public UUID getId() {

        final UUID result = this.id;
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
     * Javadocモデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocモデル
     */
    @Override
    public JavadocModel getJavadocModel() {

        final JavadocModel result = this.javadocModel;
        return result;

    }

    /**
     * オリジナルブロックを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return オリジナルブロック
     */
    @Override
    public String getOrgBlock() {

        final String result = this.orgBlock;
        return result;

    }

    // TODO KenichiroArai 2025/04/13 メソッド名を見直す
    /**
     * 解析する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean parse() throws KmgToolException {

        boolean result = false;

        // 「*/」でJavadocとCodeのブラックに分ける
        // TODO KenichiroArai 2025/04/03 ハードコード
        final String[] javadocCodeBlock = this.orgBlock.split(String.format("%s\\s+", Pattern.quote("*/")), 2);

        // Javadocモデルに変換する
        this.javadocModel = new JavadocModelImpl(javadocCodeBlock[0]);

        // TODO KenichiroArai 2025/04/09 コードを精査する

        // アノテーションと元のコードを分割
        final String[] codeLines = javadocCodeBlock[1].split("\\R");

        final StringBuilder wkCodeBlock = new StringBuilder();

        // TODO KenichiroArai 2025/04/12 不要では？
        boolean isCodeSection = false;

        for (final String line : codeLines) {

            final String trimmedLine = line.trim();

            if (trimmedLine.isEmpty()) {

                continue;

            }

            if (!isCodeSection) {

                if (trimmedLine.startsWith("@")) {

                    this.annotations.add(trimmedLine);

                } else {

                    isCodeSection = true;
                    wkCodeBlock.append(line).append(System.lineSeparator());

                }

            } else {

                wkCodeBlock.append(line).append(System.lineSeparator());

            }

        }

        this.codeBlock = wkCodeBlock.toString();

        /* Java区分を特定する */
        this.specifyJavaClassification();

        result = true;
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
    private boolean specifyJavaClassification() throws KmgToolException {

        boolean result = false;

        // コードを行ごとに取得する。空行は除外する。
        final String[] codeLines = Arrays.stream(KmgDelimiterTypes.LINE_SEPARATOR.split(this.codeBlock))
            .filter(KmgString::isNotBlank).toArray(String[]::new);

        for (final String codeLine : codeLines) {

            // Java区分を判別
            this.javaClassification = JavaClassificationTypes.identify(codeLine);

            // TODO KenichiroArai 2025/04/17 Java区分のキーワードを取得する。クラスならクラス名、フィールドならフィールド名のように。

            // Javadoc対象外か
            if (this.javaClassification.isNotJavadocTarget()) {
                // 対象外の場合

                continue;

            }

            result = true;
            break;

        }

        return result;

    }

}
