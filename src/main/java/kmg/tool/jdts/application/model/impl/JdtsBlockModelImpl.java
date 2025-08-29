package kmg.tool.jdts.application.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.tool.jdoc.domain.model.JavadocModel;
import kmg.tool.jdoc.domain.model.impl.JavadocModelImpl;
import kmg.tool.jdts.application.model.JdtsBlockModel;

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

    /**
     * Javadocブロック終了文字列
     *
     * @since 0.1.0
     */
    private static final String JAVADOC_END = "*/"; //$NON-NLS-1$

    /**
     * アノテーション開始文字
     *
     * @since 0.1.0
     */
    private static final String ANNOTATION_START = "@"; //$NON-NLS-1$

    /**
     * 改行文字の正規表現
     *
     * @since 0.1.0
     */
    private static final String LINE_SEPARATOR_REGEX = "\\R"; //$NON-NLS-1$

    /**
     * Javadocブロック分割用の正規表現
     *
     * @since 0.1.0
     */
    private static final String JAVADOC_BLOCK_SPLIT_REGEX
        = String.format("%s\\s+", Pattern.quote(JdtsBlockModelImpl.JAVADOC_END)); //$NON-NLS-1$

    /**
     * アノテーション複数行開始文字列
     *
     * @since 0.1.0
     */
    private static final String ANNOTATION_MULTILINE_START = "{"; //$NON-NLS-1$

    /**
     * アノテーション複数行終了文字列
     *
     * @since 0.1.0
     */
    private static final String ANNOTATION_MULTILINE_END = "})"; //$NON-NLS-1$

    /**
     * 識別子
     *
     * @since 0.1.0
     */
    private final UUID id;

    /**
     * オリジナルブロック
     *
     * @since 0.1.0
     */
    private final String orgBlock;

    /**
     * Javadocモデル
     *
     * @since 0.1.0
     */
    private JavadocModel javadocModel;

    /**
     * アノテーションリスト
     *
     * @since 0.1.0
     */
    private final List<String> annotations;

    /**
     * コードブロック
     *
     * @since 0.1.0
     */
    private String codeBlock;

    /**
     * 区分
     *
     * @since 0.1.0
     */
    private JavaClassificationTypes classification;

    /**
     * 要素名
     *
     * @since 0.1.0
     */
    private String elementName;

    /**
     * Javadocが文字列中かを段階的にチェックする<br>
     *
     * @since 0.1.0
     *
     * @param codeBlock
     *                  コードブロック
     *
     * @return true：文字列中、false：文字列外
     */
    private static boolean isJavadocInString(final String codeBlock) {

        boolean result = false;

        // TODO KenichiroArai 2025/08/30 ハードコード対応

        // 1. 「"」があるかチェックし、splitで分割
        final String[] parts = codeBlock.split("\"", 2);

        // 「"」がない場合は文字列外
        if (parts.length <= 1) {

            return result;

        }

        // 2. 分割後の部分をチェック
        for (int i = 1; i < parts.length; i++) {

            final String part = parts[i];

            // セミコロンで終わるか
            final Pattern semicolonPattern = Pattern.compile("^;");

            if (semicolonPattern.matcher(part).find()) {
                // セミコロンで終わる場合

                result = true;
                return result;

            }

            // テキストブロック対応：複数のダブルクォートで終わるか
            final Pattern textBlockPattern = Pattern.compile("^\"+\\s*;");

            if (textBlockPattern.matcher(part).find()) {
                // 終わる場合

                result = true;
                return result;

            }

        }

        return result;

    }

    /**
     * コンストラクタ
     *
     * @since 0.1.0
     *
     * @param block
     *              ブロック。「/**」で分割されたブロック。
     */
    public JdtsBlockModelImpl(final String block) {

        this.orgBlock = block;

        this.id = UUID.randomUUID();

        this.annotations = new ArrayList<>();

        this.classification = JavaClassificationTypes.NONE;

    }

    /**
     * アノテーションリストを返す<br>
     *
     * @since 0.1.0
     *
     * @return アノテーションリスト
     */
    @Override
    public List<String> getAnnotations() {

        final List<String> result = this.annotations;
        return result;

    }

    /**
     * 区分を返す<br>
     *
     * @since 0.1.0
     *
     * @return 区分
     */
    @Override
    public JavaClassificationTypes getClassification() {

        final JavaClassificationTypes result = this.classification;
        return result;

    }

    /**
     * 要素名を返す<br>
     *
     * @since 0.1.0
     *
     * @return 要素名
     */
    @Override
    public String getElementName() {

        final String result = this.elementName;
        return result;

    }

    /**
     * 識別子を返す<br>
     *
     * @since 0.1.0
     *
     * @return 識別子
     */
    @Override
    public UUID getId() {

        final UUID result = this.id;
        return result;

    }

    /**
     * Javadocモデルを返す<br>
     *
     * @since 0.1.0
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
     * @since 0.1.0
     *
     * @return オリジナルブロック
     */
    @Override
    public String getOrgBlock() {

        final String result = this.orgBlock;
        return result;

    }

    /**
     * 解析する
     *
     * @since 0.1.0
     *
     * @return true：Javadocタグ設定のブロックモデルの対象である、false：Javadocタグ設定のブロックモデルの対象外である
     */
    @Override
    public boolean parse() {

        boolean result = false;

        /* オリジナルブロックをJavadocとコードブロックに分ける */

        // 「*/」でJavadocとCodeのブロックに分ける
        final String[] javadocCodeBlock = this.orgBlock.split(JdtsBlockModelImpl.JAVADOC_BLOCK_SPLIT_REGEX, 2);

        // 「*/」がないか
        if (javadocCodeBlock.length < 2) {
            // ない場合

            // 対象外とする
            return result;

        }

        // Javadocが文字列中か（段階的にチェック）
        final boolean isJavadocInString = JdtsBlockModelImpl.isJavadocInString(javadocCodeBlock[1]);

        if (isJavadocInString) {
            // 文字列中の場合

            // 対象外とする
            return result;

        }

        /* Javadoc部分をJavadocモデルに変換する */
        this.javadocModel = new JavadocModelImpl(javadocCodeBlock[0]);

        /* コード行の配列にする */
        final String[] codeLines = javadocCodeBlock[1].split(JdtsBlockModelImpl.LINE_SEPARATOR_REGEX);

        /* アノテーションを設定する */

        // アノテーションの設定値が複数行か
        boolean isAnnotationValueMultiline = false;

        // アノテーション複数行
        final StringBuilder annotationMultiline = new StringBuilder();

        // コードセクションの開始
        int codeSectionStartIdx = 0;

        for (int i = 0; i < codeLines.length; i++) {

            final String line = codeLines[i];

            if (KmgString.isBlank(line)) {

                continue;

            }

            final String trimmedLine = line.trim();

            // アノテーション開始文字か
            if (!trimmedLine.startsWith(JdtsBlockModelImpl.ANNOTATION_START)) {
                // 開始文字ではない場合

                // アノテーションの設定値が複数行か
                if (isAnnotationValueMultiline) {
                    // 複数行の場合

                    annotationMultiline.append(KmgString.LINE_SEPARATOR);
                    annotationMultiline.append(trimmedLine);

                    // アノテーションの設定値が複数行の終了か
                    if (trimmedLine.endsWith(JdtsBlockModelImpl.ANNOTATION_MULTILINE_END)) {
                        // 終了の場合

                        this.annotations.add(annotationMultiline.toString());

                        isAnnotationValueMultiline = false;

                    }

                    continue;

                }

                codeSectionStartIdx = i;

                break;

            }

            // アノテーションの設定値が複数行の開始か
            if (trimmedLine.endsWith(JdtsBlockModelImpl.ANNOTATION_MULTILINE_START)) {
                // 開始の場合

                isAnnotationValueMultiline = true;

                annotationMultiline.append(trimmedLine);

                continue;

            }

            this.annotations.add(trimmedLine);

        }

        /* コードブロックを設定する */
        final StringBuilder wkCodeBlock = new StringBuilder();

        for (int i = codeSectionStartIdx; i < codeLines.length; i++) {

            final String line = codeLines[i];

            if (KmgString.isBlank(line)) {

                continue;

            }

            final String trimmedLine = line.trim();
            wkCodeBlock.append(trimmedLine).append(System.lineSeparator());

        }

        this.codeBlock = wkCodeBlock.toString();

        /* 区分を特定する */
        this.specifyClassification();

        result = true;
        return result;

    }

    /**
     * 区分を特定する<br>
     *
     * @since 0.1.0
     *
     * @return true：区分が特定できた、false：区分がNONE
     */
    private boolean specifyClassification() {

        boolean result = false;

        // Java区分を判別
        this.classification = JavaClassificationTypes.identify(this.codeBlock);

        // Javadoc対象外か
        if (this.classification.isNotJavadocTarget()) {
            // 対象外の場合

            return result;

        }

        // 要素名を取得
        this.elementName = this.classification.getElementName(this.codeBlock);

        result = true;

        return result;

    }

}
