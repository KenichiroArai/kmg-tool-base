package kmg.tool.application.logic.impl;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.tool.application.logic.JdtsBlockReplLogic;
import kmg.tool.application.model.jda.JdaTagConfigModel;
import kmg.tool.application.model.jda.JdtsBlockModel;
import kmg.tool.application.model.jda.JdtsConfigsModel;
import kmg.tool.domain.model.JavadocTagModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグ設定のブロック置換ロジック<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * Replは、Replacementの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@Service
public class JdtsBlockReplLogicImpl implements JdtsBlockReplLogic {

    /** Javadocタグ設定の構成モデル */
    private JdtsConfigsModel jdtsConfigsModel;

    /** Javadocタグ設定のブロックモデル */
    private JdtsBlockModel jdtsBlockModel;

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

    // TODO KenichiroArai 2025/04/13 メソッド名を見直す
    /**
     * 置換後のJavadocブロックを作成する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 置換後のJavadocブロック
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public String createReplacedJavadoc() throws KmgToolException {

        String result;

        /* 準備 */
        final StringBuilder headTagsBuilder = new StringBuilder();
        final StringBuilder tailTagsBuilder = new StringBuilder();

        /* Javadoc追加のタグ設定を基準に、Javadocを更新 */
        final String processedJavadoc = this.processJavadocTags(headTagsBuilder, tailTagsBuilder);

        /* 最終的な結果を組み立てる */
        result = JdtsBlockReplLogicImpl.buildFinalJavadoc(processedJavadoc, headTagsBuilder, tailTagsBuilder);

        return result;

    }

    /**
     * Javadocタグ設定の構成モデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocタグ設定の構成モデル
     */
    @Override
    public JdtsConfigsModel getJdtsConfigsModel() {

        final JdtsConfigsModel result = this.jdtsConfigsModel;
        return result;

    }

    /**
     * 初期化する
     *
     * @param jdtsConfigsModel
     *                         Javadocタグ設定の構成モデル
     * @param jdtsBlockModel
     *                         Javadocタグ設定のブロックモデル
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final JdtsConfigsModel jdtsConfigsModel, final JdtsBlockModel jdtsBlockModel)
        throws KmgToolException {

        boolean result = false;

        this.jdtsConfigsModel = jdtsConfigsModel;
        this.jdtsBlockModel = jdtsBlockModel;

        result = true;
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
     *                          Javadoc追加のタグ設定モデル
     *
     * @return 既存のJavadocタグモデル。存在しない場合はnull
     */
    private JavadocTagModel findExistingJavadocTag(final JdaTagConfigModel jdaTagConfigModel) {

        JavadocTagModel result = null;

        for (final JavadocTagModel srcJavadocTagModel : this.jdtsBlockModel.getJavadocModel().getJavadocTagsModel()
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

        String result = null;

        // 編集中のJavadoc
        String editingJavadoc = this.jdtsBlockModel.getJavadocModel().getSrcJavadoc();

        /* Javadoc追加のタグ設定を基準に、Javadocを更新 */
        for (final JdaTagConfigModel jdaTagConfigModel : this.jdtsConfigsModel.getJdaTagConfigModels()) {

            /* 元のJavadocにJavadoc追加のタグ設定のタグがあるか取得 */
            final JavadocTagModel existingJavadocTagModel = this.findExistingJavadocTag(jdaTagConfigModel);

            if (existingJavadocTagModel == null) {

                // タグが存在しない場合
                this.processNewTag(jdaTagConfigModel, headTagsBuilder, tailTagsBuilder);
                continue;

            }

            /* 誤配置時の削除処理 */
            final boolean isRemoveTag = jdaTagConfigModel.getLocation().isRemoveIfMisplaced()
                && !jdaTagConfigModel.isProperlyPlaced(this.jdtsBlockModel.getJavaClassification());

            if (isRemoveTag) {

                // 誤配置のタグを削除する

                editingJavadoc = editingJavadoc.replace(existingJavadocTagModel.getTargetStr(), KmgString.EMPTY);

                continue;

            }

            /* タグの更新処理 */
            editingJavadoc
                = JdtsBlockReplLogicImpl.updateExistingTag(editingJavadoc, jdaTagConfigModel, existingJavadocTagModel);

        }

        result = editingJavadoc;
        return result;

    }

    /**
     * 新しいタグを処理する<br>
     *
     * @author KenichiroArai
     *
     * @param jdaTagConfigModel
     *                          Javadoc追加のタグ設定モデル
     *
     * @since 0.1.0
     *
     * @param headTagsBuilder
     *                        先頭タグビルダー
     * @param tailTagsBuilder
     *                        末尾タグビルダー
     */
    private void processNewTag(final JdaTagConfigModel jdaTagConfigModel, final StringBuilder headTagsBuilder,
        final StringBuilder tailTagsBuilder) {

        // タグの配置がJava区分に一致しないか
        if (!jdaTagConfigModel.isProperlyPlaced(this.jdtsBlockModel.getJavaClassification())) {

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
