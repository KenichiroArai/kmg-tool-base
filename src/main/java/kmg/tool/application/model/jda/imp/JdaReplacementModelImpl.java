package kmg.tool.application.model.jda.imp;

import java.util.Arrays;
import java.util.UUID;

import org.apache.maven.artifact.versioning.ComparableVersion;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.model.jda.JdaReplacementModel;
import kmg.tool.application.model.jda.JdaTagConfigModel;
import kmg.tool.application.model.jda.JdaTagsModel;
import kmg.tool.domain.model.JavadocModel;
import kmg.tool.domain.model.JavadocTagModel;
import kmg.tool.domain.model.impl.JavadocModelImpl;
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

    /** 元のコード */
    private final String srcCode;

    /** 置換用識別子 */
    private final UUID identifier;

    /** Java区分 */
    private JavaClassificationTypes javaClassification;

    /** 置換後のJavadoc */
    private String replacedJavadoc;

    /** 元のJavadocモデル */
    private JavadocModel srcJavadocModel;

    /** Javadoc追加のタグモデル */
    private final JdaTagsModel jdaTagsModel;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @param srcJavadoc
     *                     元のJavadoc
     * @param srcCode
     *                     元のコード
     * @param jdaTagsModel
     *                     Javadocタグモデル
     */
    public JdaReplacementModelImpl(final String srcJavadoc, final String srcCode, final JdaTagsModel jdaTagsModel) {

        this.srcJavadoc = srcJavadoc;
        this.srcCode = srcCode;
        this.identifier = UUID.randomUUID();
        this.jdaTagsModel = jdaTagsModel;

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

        // TODO KenichiroArai 2025/04/07 実装中

        /* 準備 */
        // 元のJavadocモデルの作成
        this.srcJavadocModel = new JavadocModelImpl(this.srcJavadoc);

        // タグの追加・更新処理
        final StringBuilder replacedJavadocBuilder = new StringBuilder(this.srcJavadoc);

        // 先頭に追加するタグを収集
        final StringBuilder headTagsBuilder = new StringBuilder();

        // 末尾に追加するタグを収集
        final StringBuilder tailTagsBuilder = new StringBuilder();

        /* Javadoc追加のタグ設定を基準に、Javadocを更新 */
        for (final JdaTagConfigModel jdaTagConfigModel : this.jdaTagsModel.getJdaTagConfigModels()) {

            /* 元のJavadocにJavadoc追加のタグ設定のタグがあるか取得 */
            JavadocTagModel existingJavadocTagModel = null;

            for (final JavadocTagModel srcJavadocTagModel : this.srcJavadocModel.getJavadocTagsModel()
                .getJavadocTagModelList()) {

                if (srcJavadocTagModel.getTag() != jdaTagConfigModel.getTag()) {

                    continue;

                }
                existingJavadocTagModel = srcJavadocTagModel;
                break;

            }

            if (existingJavadocTagModel == null) {

                // タグが存在しない場合

                // タグの配置がJava区分に一致しないか
                if (!jdaTagConfigModel.isProperlyPlaced(this.javaClassification)) {

                    // 一致しない場合

                    // 次のタグへ
                    continue;

                }

                // 一致する場合

                // 新しいタグを作成
                // TODO KenichiroArai 2025/04/08 ハードコード
                final String newTag = String.format(" * %s %s %s%n", jdaTagConfigModel.getTag().getKey(),
                    jdaTagConfigModel.getTagValue(), jdaTagConfigModel.getTagDescription());

                // 挿入位置に応じてタグを振り分け
                switch (jdaTagConfigModel.getInsertPosition()) {

                    case BEGINNING:
                        /* ファイルの先頭 */

                        headTagsBuilder.append(newTag);

                        break;

                    case NONE:
                        /* 指定無し */
                    case END:
                        /* ファイルの末尾 */
                    case PRESERVE:
                        /* 現在の位置を維持 */

                        tailTagsBuilder.append(newTag);

                        break;

                }

                // タグを追加したので次へ進む
                continue;

            }

            // 誤配置時に削除するか
            if (jdaTagConfigModel.getLocation().isRemoveIfMisplaced()) {

                // 削除する場合

                // タグの配置がJava区分に一致しないか
                if (!jdaTagConfigModel.isProperlyPlaced(this.javaClassification)) {

                    // 一致しない場合

                    // 一致しない場合は、誤配置のため、元のJavadocに設定されていても削除する。

                    String javadoc = replacedJavadocBuilder.toString();
                    javadoc = javadoc.replace(existingJavadocTagModel.getTargetStr(), KmgString.EMPTY);
                    replacedJavadocBuilder.setLength(0);
                    replacedJavadocBuilder.append(javadoc);

                    continue;

                }

            }

            /* タグの上書き判定 */
            switch (jdaTagConfigModel.getOverwrite()) {

                case NONE:
                    /* 指定無し */
                case NEVER:
                    /* 上書きしない */

                    continue;

                case ALWAYS:
                    /* 常に上書き */

                    break;

                case IF_LOWER:
                    /* 既存のバージョンより小さい場合のみ上書き */

                    if (jdaTagConfigModel.getTag().isVersionValue()) {

                        final ComparableVersion srcVer  = new ComparableVersion(existingJavadocTagModel.getTag().get());
                        final ComparableVersion destVer = new ComparableVersion(jdaTagConfigModel.getTagValue());

                        if (srcVer.compareTo(destVer) <= 0) {

                            continue;

                        }

                    }
                    break;

            }

            /* タグの更新 */
            final String newTag  = String.format(" * @%s %s %s", jdaTagConfigModel.getTag().getKey(),
                jdaTagConfigModel.getTagValue(), jdaTagConfigModel.getTagDescription());
            String       javadoc = replacedJavadocBuilder.toString();
            javadoc = javadoc.replace(existingJavadocTagModel.getTargetStr(), newTag);
            replacedJavadocBuilder.setLength(0);
            replacedJavadocBuilder.append(javadoc);

        }

        /* 先頭のタグを追加 */
        if (headTagsBuilder.length() > 0) {

            // 最初の「@」の位置を探す
            final int firstAtPos = replacedJavadocBuilder.indexOf("@");

            if (firstAtPos != -1) {

                replacedJavadocBuilder.insert(firstAtPos, headTagsBuilder.toString());

            } else {
                // 「@」が見つからなければ末尾に追加
                replacedJavadocBuilder.append(headTagsBuilder);
            }

        }

        /* 末尾のタグを追加 */
        if (tailTagsBuilder.length() > 0) {

            replacedJavadocBuilder.append(tailTagsBuilder);

        }

        this.replacedJavadoc = replacedJavadocBuilder.toString();
        // TODO KenichiroArai 2025/04/06 デバッグログ
        System.out.println(this.replacedJavadoc);
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
    public String getSrcCode() {

        final String result = this.srcCode;
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
        this.javaClassification = JavaClassificationTypes.NONE;

        /* コードを行ごとに確認する */

        // コードを行ごとに取得する。空行は除外する。
        final String[] codeLines = Arrays.stream(KmgDelimiterTypes.LINE_SEPARATOR.split(this.srcCode))
            .filter(KmgString::isNotBlank).toArray(String[]::new);

        for (final String codeLine : codeLines) {

            // Java区分を判別
            this.javaClassification = JavaClassificationTypes.identify(codeLine);

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
