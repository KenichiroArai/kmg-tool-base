package kmg.tool.application.logic.impl;

import org.apache.maven.artifact.versioning.ComparableVersion;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.tool.application.logic.JavadocReplacementLogic;
import kmg.tool.application.model.jda.JdaTagConfigModel;
import kmg.tool.application.model.jda.JdtsConfigurationsModel;
import kmg.tool.domain.model.JavadocModel;
import kmg.tool.domain.model.JavadocTagModel;
import kmg.tool.domain.model.impl.JavadocModelImpl;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc置換ロジック実装クラス<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 */
public class JavadocReplacementLogicImpl implements JavadocReplacementLogic {

    // TODO KenichiroArai 2025/04/09 クラス名を変更する

    /** 元のJavadoc */
    private String srcJavadoc;

    /** 元のJava区分 */
    private JavaClassificationTypes srcJavaClassification;

    /** 元のJavadocモデル */
    private JavadocModel srcJavadocModel;

    /** 置換後のJavadoc */
    private String replacedJavadoc;

    /** Javadocタグ設定の構成モデル */
    private JdtsConfigurationsModel jdtsConfigurationsModel;

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
     * デフォルトコンストラクタ
     */
    public JavadocReplacementLogicImpl() {

        // 処理なし
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
            = JavadocReplacementLogicImpl.buildFinalJavadoc(processedJavadoc, headTagsBuilder, tailTagsBuilder);

        result = true;
        return result;

    }

    /**
     * 置換後のJavadocを取得する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 置換後のJavadoc
     */
    @Override
    public String getReplacedJavadoc() {

        final String result = this.replacedJavadoc;
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
     * 初期化する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param srcJavadoc
     *                                元のJavadoc
     * @param jdtsConfigurationsModel
     *                                Javadocタグ設定の構成モデル
     * @param srcJavaClassification
     *                                元のJava区分
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final String srcJavadoc, final JdtsConfigurationsModel jdtsConfigurationsModel,
        final JavaClassificationTypes srcJavaClassification) {

        this.srcJavadoc = srcJavadoc;
        this.jdtsConfigurationsModel = jdtsConfigurationsModel;
        this.srcJavaClassification = srcJavaClassification;

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
            editingJavadoc = JavadocReplacementLogicImpl.updateExistingTag(editingJavadoc, jdaTagConfigModel,
                existingJavadocTagModel);

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
