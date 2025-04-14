package kmg.tool.application.logic.impl;

import java.util.Iterator;

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

    /** タグイテレータ */
    private Iterator<JdaTagConfigModel> tagIterator;

    /** 現在のタグ */
    private JdaTagConfigModel currentTag;

    /** 現在の既存タグ */
    private JavadocTagModel currentExistingTag;

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
     * 次のJavadocタグを取得する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 次のJavadocタグ設定モデル、存在しない場合はnull
     */
    @Override
    public JdaTagConfigModel getNextTag() {

        JdaTagConfigModel result = null;

        if (!this.tagIterator.hasNext()) {

            return result;

        }

        this.currentTag = this.tagIterator.next();
        this.currentExistingTag
            = this.jdtsBlockModel.getJavadocModel().getJavadocTagsModel().findByTag(this.currentTag.getTag());
        result = this.currentTag;

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
     * 現在のタグが存在するか確認する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：存在する場合、false：存在しない場合
     */
    @Override
    public boolean hasExistingTag() {

        boolean result = this.currentExistingTag != null;
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

        this.tagIterator = this.jdtsConfigsModel.getJdaTagConfigModels().iterator();
        this.currentTag = null;
        this.currentExistingTag = null;

        // 編集中のJavadoc
        this.replacedJavadocBlock = this.jdtsBlockModel.getJavadocModel().getSrcJavadoc();

        result = true;
        return result;

    }

    /**
     * 新しいタグを処理する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param tag
     *            Javadoc追加のタグ設定モデル
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean processNewTag(final JdaTagConfigModel tag) {

        boolean result = false;

        // タグの配置がJava区分に一致しないか
        if (!tag.isProperlyPlaced(this.jdtsBlockModel.getJavaClassification())) {

            // 一致しない場合
            // タグを追加しない
            return result;

        }

        // 新しいタグを作成
        // TODO KenichiroArai 2025/04/09 ハードコード
        final String newTag
            = String.format(" * %s %s %s%n", tag.getTag().getKey(), tag.getTagValue(), tag.getTagDescription());

        // 挿入位置に応じてタグを振り分け
        switch (tag.getInsertPosition()) {

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

        result = true;
        return result;

    }

    /**
     * 現在のタグを削除する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean removeCurrentTag() {

        boolean result = false;

        if (this.currentExistingTag == null) {

            return result;

        }

        this.replacedJavadocBlock
            = this.replacedJavadocBlock.replace(this.currentExistingTag.getTargetStr(), KmgString.EMPTY);
        result = true;

        return result;

    }

    /**
     * 現在のタグを更新する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean updateCurrentTag() {

        boolean result = false;

        if ((this.currentTag == null) || (this.currentExistingTag == null)) {

            return result;

        }

        this.updateExistingTag(this.currentTag, this.currentExistingTag);
        result = true;

        return result;

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
