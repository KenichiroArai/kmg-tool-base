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

    /** 置換後のJavadocブロック */
    private String replacedJavadocBlock;

    /** 先頭タグ */
    private final StringBuilder headTags;

    /** 末尾タグ */
    private final StringBuilder tailTags;

    /**
     * デフォルトコンストラクタ
     */
    public JdtsBlockReplLogicImpl() {

        this.headTags = new StringBuilder();
        this.tailTags = new StringBuilder();

    }

    /**
     * 最終的なJavadocを構築する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean buildFinalJavadoc() {

        boolean result;

        final StringBuilder finalJavadocBuilder = new StringBuilder(this.replacedJavadocBlock);

        /* 先頭のタグを追加 */
        if (this.headTags.length() > 0) {

            // TODO KenichiroArai 2025/04/09 ハードコード
            final int firstAtPos = finalJavadocBuilder.indexOf("* @");

            if (firstAtPos > -1) {

                finalJavadocBuilder.insert(firstAtPos - 1, this.headTags.toString());

            } else {

                finalJavadocBuilder.append(this.headTags);

            }

        }

        /* 末尾のタグを追加 */
        if (this.tailTags.length() > 0) {

            finalJavadocBuilder.append(this.tailTags);

        }

        this.replacedJavadocBlock = finalJavadocBuilder.toString();

        result = true;
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
     * 置換後のJavadocブロックを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 置換後のJavadocブロック
     */
    @Override
    public String getReplacedJavadocBlock() {

        final String result = this.replacedJavadocBlock;
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

        this.headTags.setLength(0);
        this.tailTags.setLength(0);

        result = true;
        return result;

    }

    /**
     * Javadocタグを処理する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean processJavadocTags() {

        boolean result = false;

        // 編集中のJavadoc
        this.replacedJavadocBlock = this.jdtsBlockModel.getJavadocModel().getSrcJavadoc();

        /* Javadoc追加のタグ設定を基準に、Javadocを更新 */
        for (final JdaTagConfigModel jdaTagConfigModel : this.jdtsConfigsModel.getJdaTagConfigModels()) {

            /* 元のJavadocにJavadoc追加のタグ設定のタグがあるか取得 */
            final JavadocTagModel existingJavadocTagModel
                = this.jdtsBlockModel.getJavadocModel().getJavadocTagsModel().findByJdaTagConfig(jdaTagConfigModel);

            if (existingJavadocTagModel == null) {

                // タグが存在しない場合
                this.processNewTag(jdaTagConfigModel);
                continue;

            }

            /* 誤配置時の削除処理 */
            final boolean isRemoveTag = jdaTagConfigModel.getLocation().isRemoveIfMisplaced()
                && !jdaTagConfigModel.isProperlyPlaced(this.jdtsBlockModel.getJavaClassification());

            if (isRemoveTag) {

                // 誤配置のタグを削除する

                this.replacedJavadocBlock
                    = this.replacedJavadocBlock.replace(existingJavadocTagModel.getTargetStr(), KmgString.EMPTY);

                continue;

            }

            /* タグの更新処理 */
            this.updateExistingTag(jdaTagConfigModel, existingJavadocTagModel);

        }

        result = true;
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
     */
    private void processNewTag(final JdaTagConfigModel jdaTagConfigModel) {

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

                this.headTags.append(newTag);

                break;

            case NONE:
                /* 指定無し */
            case END:
                /* ファイルの末尾 */
            case PRESERVE:
                /* 現在の位置を維持 */

                this.tailTags.append(newTag);

                break;

        }

    }

    /**
     * 既存のタグを更新する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param jdaTagConfigModel
     *                                Javadocタグ設定モデル
     * @param existingJavadocTagModel
     *                                既存のJavadocタグモデル
     */
    private void updateExistingTag(final JdaTagConfigModel jdaTagConfigModel,
        final JavadocTagModel existingJavadocTagModel) {

        switch (jdaTagConfigModel.getOverwrite()) {

            case NONE:
                /* 指定無し */
            case NEVER:
                /* 上書きしない */

                return;

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

                    return;

                }

                break;

        }

        // TODO KenichiroArai 2025/04/09 ハードコード
        final String newTag = String.format(" * %s %s %s", jdaTagConfigModel.getTag().getKey(),
            jdaTagConfigModel.getTagValue(), jdaTagConfigModel.getTagDescription());

        this.replacedJavadocBlock = this.replacedJavadocBlock.replace(existingJavadocTagModel.getTargetStr(), newTag);

    }
}
