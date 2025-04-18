package kmg.tool.application.logic.jdts.impl;

import java.util.Iterator;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.springframework.stereotype.Service;

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
    private StringBuilder replacedJavadocBlock;

    /** 先頭タグの位置オフセット */
    private int headTagPosOffset;

    /** タグ構成のイテレータ */
    private Iterator<JdtsTagConfigModel> tagConfigIterator;

    /** 現在のタグ構成モデル */
    private JdtsTagConfigModel currentTagConfigModel;

    /** 現在の元のJavadocタグ */
    private JavadocTagModel currentSrcJavadocTag;

    /**
     * 新しいタグを作成して指定位置に追加する<br>
     * <p>
     * タグ構成モデルの情報（タグ、タグ値、タグ説明）を使用して新しいタグを作成し、 挿入位置の設定に基づいて以下のいずれかの位置に追加します：
     * <ul>
     * <li>先頭（BEGINNING）：先頭タグに追加</li>
     * <li>指定無し（NONE）：末尾タグに追加</li>
     * <li>末尾（END）：末尾タグに追加</li>
     * <li>現在の位置を維持（PRESERVE）：末尾タグに追加</li>
     * </ul>
     * </p>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    @Override
    public void addNewTagByPosition() {

        // 新しいタグを作成
        final String newTag = this.createNewTagContent();

        switch (this.currentTagConfigModel.getInsertPosition()) {

            case BEGINNING:
                /* Javadocタグの先頭 */

                if (this.headTagPosOffset > -1) {

                    this.replacedJavadocBlock.insert(this.headTagPosOffset, newTag);

                } else {

                    this.replacedJavadocBlock.append(newTag);

                }

                break;

            case NONE:
                /* 指定無し */
            case END:
                /* Javadocタグの末尾 */
            case PRESERVE:
                /* 現在の位置を維持 */

                this.replacedJavadocBlock.append(newTag);

                break;

        }

        this.headTagPosOffset += newTag.length();

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

        final String result = this.replacedJavadocBlock.toString();
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

        this.tagConfigIterator = this.configsModel.getJdaTagConfigModels().iterator();
        this.nextTag();

        // 元のJavadocを置換するため、初期値として設定する
        this.replacedJavadocBlock = new StringBuilder(this.srcBlockModel.getJavadocModel().getSrcJavadoc());

        // TODO KenichiroArai 2025/04/09 ハードコード
        this.headTagPosOffset = this.replacedJavadocBlock.indexOf("* @");

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

        final int startIndex = this.replacedJavadocBlock.indexOf(this.currentSrcJavadocTag.getTargetStr());
        final int endIndex   = startIndex + this.currentSrcJavadocTag.getTargetStr().length();
        this.replacedJavadocBlock.delete(startIndex, endIndex);

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
     * 新しいタグを追加すべきか判断する<br>
     *
     * @return true：追加する、false：追加しない
     */
    @Override
    public boolean shouldAddNewTag() {

        final boolean result = this.currentTagConfigModel.isProperlyPlaced(this.srcBlockModel.getJavaClassification());
        return result;

    }

    /**
     * 現在のタグを更新する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：更新した場合、false：更新していない場合
     */
    @Override
    public boolean updateCurrentTag() {

        boolean result = false;

        // タグを上書きしないか
        if (!this.shouldOverwriteTag()) {
            // 上書きしない場合

            return result;

        }

        // 既存のタグを置換する
        result = this.replaceExistingTag();

        return result;

    }

    /**
     * 新しいタグの内容を作成する<br>
     *
     * @return 新しいタグの内容
     */
    private String createNewTagContent() {

        final String result = String.format(" * %s %s %s%n", this.currentTagConfigModel.getTag().getKey(),
            this.currentTagConfigModel.getTagValue(), this.currentTagConfigModel.getTagDescription());
        return result;

    }

    /**
     * 置換用タグの内容を作成する<br>
     *
     * @return 置換用タグの内容
     */
    private String createReplacementTagContent() {

        final String result = String.format(" * %s %s %s", this.currentTagConfigModel.getTag().getKey(),
            this.currentTagConfigModel.getTagValue(), this.currentTagConfigModel.getTagDescription());
        return result;

    }

    /**
     * 既存のタグを置換する<br>
     *
     * @return true：置換成功、false：置換失敗
     */
    private boolean replaceExistingTag() {

        boolean result = false;

        final int startIdx = this.replacedJavadocBlock.indexOf(this.currentSrcJavadocTag.getTargetStr());

        if (startIdx == -1) {

            return result;

        }

        final int endIdx = startIdx + this.currentSrcJavadocTag.getTargetStr().length();

        final String replacementTag = this.createReplacementTagContent();

        this.replacedJavadocBlock.replace(startIdx, endIdx, replacementTag);

        result = true;
        return result;

    }

    /**
     * バージョン比較に基づいて上書きすべきか判断する<br>
     *
     * @return true：上書きする、false：上書きしない
     */
    private boolean shouldOverwriteBasedOnVersion() {

        boolean result = false;

        // バージョンのタグではないか
        if (!this.currentTagConfigModel.getTag().isVersionValue()) {
            // タグでない場合

            result = true;
            return result;

        }

        /* バージョン比較 */

        final ComparableVersion srcVer  = new ComparableVersion(this.currentSrcJavadocTag.getTag().get());
        final ComparableVersion destVer = new ComparableVersion(this.currentTagConfigModel.getTagValue());

        // 既存のバージョンより小さいければ上書きする
        result = srcVer.compareTo(destVer) > 0;

        return result;

    }

    /**
     * タグを上書きすべきか判断する<br>
     *
     * @return true：上書きする、false：上書きしない
     */
    private boolean shouldOverwriteTag() {

        boolean result = false;

        /* 上書き設定 */
        switch (this.currentTagConfigModel.getOverwrite()) {

            case NONE:
                /* 指定無し */
            case NEVER:
                /* 上書きしない */
                return result;

            case ALWAYS:
                /* 常に上書き */
                result = true;
                break;

            case IF_LOWER:
                /* 既存のバージョンより小さい場合のみ上書き */

                result = this.shouldOverwriteBasedOnVersion();

                break;

        }

        return result;

    }

}
