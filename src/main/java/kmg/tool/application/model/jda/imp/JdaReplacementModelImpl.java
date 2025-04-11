package kmg.tool.application.model.jda.imp;

import java.util.Arrays;
import java.util.UUID;

import org.apache.maven.artifact.versioning.ComparableVersion;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.model.jda.JdaReplacementModel;
import kmg.tool.application.model.jda.JdaTagConfigModel;
import kmg.tool.application.model.jda.JdtsConfigsModel;
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

    /** 元のコードブロック */
    private final String srcCodeBloc;

    /** 元のJavadocモデル */
    private JavadocModel srcJavadocModel;

    /** 元のJava区分 */
    private JavaClassificationTypes srcJavaClassification;

    /** avadocタグ設定の構成モデル */
    private final JdtsConfigsModel jdtsConfigurationsModel;

    /** 置換用識別子 */
    private final UUID identifier;

    /** 置換後のJavadoc */
    private String replacedJavadoc;

    /**
     * 最終的なJavadocを構築する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param editingJavadoc
     *                        編集中のJavadoc
     * @param headTagsBuilder
     *                        先頭タグビルダー
     * @param tailTagsBuilder
     *                        末尾タグビルダー
     *
     * @return 構築されたJavadoc
     */
    private static String buildFinalJavadoc(final String editingJavadoc, final StringBuilder headTagsBuilder,
        final StringBuilder tailTagsBuilder) {

        String result;

        final StringBuilder finalJavadocBuilder = new StringBuilder(editingJavadoc);

        /* 先頭のタグを追加 */
        if (headTagsBuilder.length() > 0) {

            // TODO KenichiroArai 2025/04/09 ハードコード
            final int firstAtPos = finalJavadocBuilder.indexOf("* @");

            if (firstAtPos > -1) {

                finalJavadocBuilder.insert(firstAtPos - 1, headTagsBuilder.toString());

            } else {

                finalJavadocBuilder.append(headTagsBuilder);

            }

        }

        /* 末尾のタグを追加 */
        if (tailTagsBuilder.length() > 0) {

            finalJavadocBuilder.append(tailTagsBuilder);

        }

        result = finalJavadocBuilder.toString();
        return result;

    }

    /**
     * 既存のタグを更新する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param editingJavadoc
     *                                編集中のJavadoc
     * @param jdaTagConfigModel
     *                                Javadocタグ設定モデル
     * @param existingJavadocTagModel
     *                                既存のJavadocタグモデル
     *
     * @return 更新後のJavadoc
     */
    private static String updateExistingTag(final String editingJavadoc, final JdaTagConfigModel jdaTagConfigModel,
        final JavadocTagModel existingJavadocTagModel) {

        String result = null;

        switch (jdaTagConfigModel.getOverwrite()) {

            case NONE:
                /* 指定無し */
            case NEVER:
                /* 上書きしない */

                result = editingJavadoc;
                return result;

            case ALWAYS:
                /* 常に上書き */

                break;

            case IF_LOWER:
                /* 既存のバージョンより小さい場合のみ上書き */

                if (!jdaTagConfigModel.getTag().isVersionValue()) {

                    break;

                }

                final ComparableVersion srcVer = new ComparableVersion(existingJavadocTagModel.getTag().get());
                final ComparableVersion destVer = new ComparableVersion(jdaTagConfigModel.getTagValue());

                if (srcVer.compareTo(destVer) <= 0) {

                    result = editingJavadoc;
                    return result;

                }

                break;

        }

        // TODO KenichiroArai 2025/04/09 ハードコード
        final String newTag = String.format(" * %s %s %s", jdaTagConfigModel.getTag().getKey(),
            jdaTagConfigModel.getTagValue(), jdaTagConfigModel.getTagDescription());

        result = editingJavadoc.replace(existingJavadocTagModel.getTargetStr(), newTag);

        return result;

    }

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
        final JdtsConfigsModel jdtsConfigurationsModel) {

        this.srcJavadoc = srcJavadoc;
        this.srcCodeBloc = srcCodeBloc;
        this.identifier = UUID.randomUUID();
        this.jdtsConfigurationsModel = jdtsConfigurationsModel;

    }

    /**
     * 置換後のJavadocを作成する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
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
        this.srcJavadocModel = new JavadocModelImpl(this.srcJavadoc);
        final StringBuilder headTagsBuilder = new StringBuilder();
        final StringBuilder tailTagsBuilder = new StringBuilder();

        /* Javadoc追加のタグ設定を基準に、Javadocを更新 */
        final String processedJavadoc = this.processJavadocTags(headTagsBuilder, tailTagsBuilder);

        /* 最終的な結果を組み立てる */
        this.replacedJavadoc
            = JdaReplacementModelImpl.buildFinalJavadoc(processedJavadoc, headTagsBuilder, tailTagsBuilder);

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

    /**
     * 既存のJavadocタグを検索する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param jdaTagConfigModel
     *                          Javadocタグ設定モデル
     *
     * @return 既存のJavadocタグモデル。存在しない場合はnull
     */
    private JavadocTagModel findExistingJavadocTag(final JdaTagConfigModel jdaTagConfigModel) {

        JavadocTagModel result = null;

        for (final JavadocTagModel srcJavadocTagModel : this.srcJavadocModel.getJavadocTagsModel()
            .getJavadocTagModelList()) {

            if (srcJavadocTagModel.getTag() != jdaTagConfigModel.getTag()) {

                continue;

            }

            result = srcJavadocTagModel;
            return result;

        }

        return result;

    }

    /**
     * Javadocタグを処理する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param headTagsBuilder
     *                        先頭タグビルダー
     * @param tailTagsBuilder
     *                        末尾タグビルダー
     *
     * @return 処理後のJavadoc
     */
    private String processJavadocTags(final StringBuilder headTagsBuilder, final StringBuilder tailTagsBuilder) {

        /* 戻り値 */
        String result = null;

        // 編集中のJavadoc
        String editingJavadoc = this.srcJavadoc;

        /* Javadoc追加のタグ設定を基準に、Javadocを更新 */
        for (final JdaTagConfigModel jdaTagConfigModel : this.jdtsConfigurationsModel.getJdaTagConfigModels()) {

            /* 元のJavadocにJavadoc追加のタグ設定のタグがあるか取得 */
            final JavadocTagModel existingJavadocTagModel = this.findExistingJavadocTag(jdaTagConfigModel);

            if (existingJavadocTagModel == null) {

                // タグが存在しない場合
                this.processNewTag(jdaTagConfigModel, headTagsBuilder, tailTagsBuilder);
                continue;

            }

            /* 誤配置時の削除処理 */
            final boolean isRemoveTag = jdaTagConfigModel.getLocation().isRemoveIfMisplaced()
                && !jdaTagConfigModel.isProperlyPlaced(this.srcJavaClassification);

            if (isRemoveTag) {

                // 誤配置のタグを削除する

                editingJavadoc = editingJavadoc.replace(existingJavadocTagModel.getTargetStr(), KmgString.EMPTY);

                continue;

            }

            /* タグの更新処理 */
            editingJavadoc
                = JdaReplacementModelImpl.updateExistingTag(editingJavadoc, jdaTagConfigModel, existingJavadocTagModel);

        }

        result = editingJavadoc;
        return result;

    }

    /**
     * 新しいタグを処理する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param jdaTagConfigModel
     *                          Javadocタグ設定モデル
     * @param headTagsBuilder
     *                          先頭タグビルダー
     * @param tailTagsBuilder
     *                          末尾タグビルダー
     */
    private void processNewTag(final JdaTagConfigModel jdaTagConfigModel, final StringBuilder headTagsBuilder,
        final StringBuilder tailTagsBuilder) {

        // タグの配置がJava区分に一致しないか
        if (!jdaTagConfigModel.isProperlyPlaced(this.srcJavaClassification)) {

            // 一致しない場合

            // タグを追加しない

            return;

        }

        // 新しいタグを作成
        // TODO KenichiroArai 2025/04/09 ハードコード
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

    }

}
