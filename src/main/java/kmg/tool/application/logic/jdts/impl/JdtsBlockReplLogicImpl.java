package kmg.tool.application.logic.jdts.impl;

import java.util.Iterator;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.tool.application.logic.jdts.JdtsBlockReplLogic;
import kmg.tool.application.model.jdts.JdtsBlockModel;
import kmg.tool.application.model.jdts.JdtsConfigsModel;
import kmg.tool.application.model.jdts.JdtsTagConfigModel;
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

    /** 構成モデル */
    private JdtsConfigsModel configsModel;

    /** 元のブロックモデル */
    private JdtsBlockModel srcBlockModel;

    /** 置換後のJavadocブロック */
    private String replacedJavadocBlock;

    /** 先頭タグ */
    private final StringBuilder headTags;

    /** 末尾タグ */
    private final StringBuilder tailTags;

    /** タグ構成のイテレータ */
    private Iterator<JdtsTagConfigModel> tagConfigIterator;

    /** 現在のタグ構成モデル */
    private JdtsTagConfigModel currentTagConfigModel;

    /** 現在の元のJavadocタグ */
    private JavadocTagModel currentSrcJavadocTag;

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
     * 構成モデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 構成モデル
     */
    @Override
    public JdtsConfigsModel getConfigsModel() {

        final JdtsConfigsModel result = this.configsModel;
        return result;

    }

    /**
     * 現在の元のJavadocタグを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 現在の元のJavadocタグ
     */
    @Override
    public JavadocTagModel getCurrentSrcJavadocTag() {

        final JavadocTagModel result = this.currentSrcJavadocTag;
        return result;

    }

    /**
     * 現在のタグ構成モデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 現在のタグ構成モデル
     */
    @Override
    public JdtsTagConfigModel getCurrentTagConfigModel() {

        final JdtsTagConfigModel result = this.currentTagConfigModel;
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
     * 元のブロックモデルに構成モデルのタグが存在するか<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：存在する場合、false：存在しない場合
     */
    @Override
    public boolean hasExistingTag() {

        final boolean result = this.currentSrcJavadocTag != null;
        return result;

    }

    /**
     * 初期化する
     *
     * @param configsModel
     *                      構成モデル
     * @param srcBlockModel
     *                      元のブロックモデル
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final JdtsConfigsModel configsModel, final JdtsBlockModel srcBlockModel)
        throws KmgToolException {

        boolean result = false;

        this.configsModel = configsModel;
        this.srcBlockModel = srcBlockModel;

        this.headTags.setLength(0);
        this.tailTags.setLength(0);

        this.tagConfigIterator = this.configsModel.getJdaTagConfigModels().iterator();
        this.nextTag();

        // 編集中のJavadoc
        this.replacedJavadocBlock = this.srcBlockModel.getJavadocModel().getSrcJavadoc();

        result = true;
        return result;

    }

    /**
     * 次のJavadocタグに進む<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：次のタグがある場合、false：次のタグがない場合
     */
    @Override
    public boolean nextTag() {

        boolean result = false;

        if (!this.tagConfigIterator.hasNext()) {

            this.currentTagConfigModel = null;
            this.currentSrcJavadocTag = null;
            return result;

        }

        this.currentTagConfigModel = this.tagConfigIterator.next();
        this.currentSrcJavadocTag
            = this.srcBlockModel.getJavadocModel().getJavadocTagsModel().findByTag(this.currentTagConfigModel.getTag());

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
     * @return true：新しいタグを追加する、false：タグを追加しない
     */
    @Override
    public boolean processNewTag() {

        boolean result = false;

        // タグの配置がJava区分に一致しないか
        if (!this.currentTagConfigModel.isProperlyPlaced(this.srcBlockModel.getJavaClassification())) {

            // 一致しない場合
            // タグを追加しない
            return result;

        }

        // 新しいタグを作成
        // TODO KenichiroArai 2025/04/09 ハードコード
        final String newTag = String.format(" * %s %s %s%n", this.currentTagConfigModel.getTag().getKey(),
            this.currentTagConfigModel.getTagValue(), this.currentTagConfigModel.getTagDescription());

        // 挿入位置に応じてタグを振り分け
        switch (this.currentTagConfigModel.getInsertPosition()) {

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

        if (this.currentSrcJavadocTag == null) {

            return result;

        }

        this.replacedJavadocBlock
            = this.replacedJavadocBlock.replace(this.currentSrcJavadocTag.getTargetStr(), KmgString.EMPTY);
        result = true;

        return result;

    }

    /**
     * 誤配置時に現在のタグを削除します。<br>
     * <p>
     * 以下の条件を満たす場合、現在のタグを削除します。
     * <ol>
     * <li>「誤配置時に削除する」が指定されている</li>
     * <li>「タグの配置がJava区分」に一致していない</li>
     * </ol>
     * </p>
     *
     * @return true：タグを削除した場合、false：タグを削除しなかった場合
     */
    @Override
    public boolean removeCurrentTagOnError() {

        boolean result = false;

        /* 削除するか判断する */

        // 「誤配置時に削除する」が指定されていないか
        if (!this.currentTagConfigModel.getLocation().isRemoveIfMisplaced()) {
            // 指定されていない場合

            return result;

        }

        // 「タグの配置がJava区分」に一致しているか
        if (this.currentTagConfigModel.isProperlyPlaced(this.srcBlockModel.getJavaClassification())) {
            // 一致している場合

            return result;

        }

        /* 現在のタグを削除する */
        result = this.removeCurrentTag();
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

        if ((this.currentTagConfigModel == null) || (this.currentSrcJavadocTag == null)) {

            return result;

        }

        this.updateExistingTag(this.currentTagConfigModel, this.currentSrcJavadocTag);
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
     * @param jdtsTagConfigModel
     *                                Javadocタグ設定モデル
     * @param existingJavadocTagModel
     *                                既存のJavadocタグモデル
     */
    private void updateExistingTag(final JdtsTagConfigModel jdtsTagConfigModel,
        final JavadocTagModel existingJavadocTagModel) {

        switch (jdtsTagConfigModel.getOverwrite()) {

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
                if (!jdtsTagConfigModel.getTag().isVersionValue()) {

                    break;

                }

                final ComparableVersion srcVer = new ComparableVersion(existingJavadocTagModel.getTag().get());
                final ComparableVersion destVer = new ComparableVersion(jdtsTagConfigModel.getTagValue());

                if (srcVer.compareTo(destVer) <= 0) {

                    return;

                }

                break;

        }

        // TODO KenichiroArai 2025/04/09 ハードコード
        final String newTag = String.format(" * %s %s %s", jdtsTagConfigModel.getTag().getKey(),
            jdtsTagConfigModel.getTagValue(), jdtsTagConfigModel.getTagDescription());

        this.replacedJavadocBlock = this.replacedJavadocBlock.replace(existingJavadocTagModel.getTargetStr(), newTag);

    }
}
