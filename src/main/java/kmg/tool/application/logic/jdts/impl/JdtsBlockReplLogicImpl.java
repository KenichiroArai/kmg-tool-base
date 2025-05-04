package kmg.tool.application.logic.jdts.impl;

import java.util.Iterator;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.tool.application.logic.jdts.JdtsBlockReplLogic;
import kmg.tool.application.model.jdts.JdtsBlockModel;
import kmg.tool.application.model.jdts.JdtsConfigsModel;
import kmg.tool.application.model.jdts.JdtsTagConfigModel;
import kmg.tool.application.types.JdaInsertPositionTypes;
import kmg.tool.domain.model.JavadocTagModel;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * Javadocタグ設定のブロック置換ロジック<br>
 * <p>
 * このクラスは、Javadocタグの設定と置換に関する操作を定義します。 主な機能は以下の通りです：
 * </p>
 * <ul>
 * <li>既存のJavadocタグの検出と管理</li>
 * <li>新規タグの追加と位置指定</li>
 * <li>既存タグの置換と削除</li>
 * <li>タグの配置位置の制御</li>
 * <li>バージョン比較に基づくタグの更新</li>
 * </ul>
 * <p>
 * 略語の説明：
 * <ul>
 * <li>Jdts: JavadocTagSetterの略</li>
 * <li>Repl: Replacementの略</li>
 * </ul>
 * </p>
 * <p>
 * このクラスは{@link JdtsBlockReplLogic}インタフェースを実装し、 Javadocタグの操作に関するすべてのメソッドを提供します。
 * </p>
 *
 * @author KenichiroArai
 *
 * @version 0.1.0
 *
 * @since 0.1.0
 */
@Service
public class JdtsBlockReplLogicImpl implements JdtsBlockReplLogic {

    /** Javadocタグの開始文字列 */
    private static final String JAVADOC_TAG_START = "* @";

    /** タグのフォーマット */
    private static final String TAG_FORMAT = " * %s %s %s";

    /** 構成モデル */
    private JdtsConfigsModel configsModel;

    /** 元のブロックモデル */
    private JdtsBlockModel srcBlockModel;

    /** 先頭タグの位置オフセット */
    private int headTagPosOffset;

    /** タグ構成のイテレータ */
    private Iterator<JdtsTagConfigModel> tagConfigIterator;

    /** 現在のタグ構成モデル */
    private JdtsTagConfigModel currentTagConfigModel;

    /** 現在の元のJavadocタグ */
    private JavadocTagModel currentSrcJavadocTag;

    /** 設定するタグの内容 */
    private String tagContentToApply;

    /** 置換後のJavadocブロック */
    private StringBuilder replacedJavadocBlock;

    /**
     * 新しいタグを作成して指定位置に追加する<br>
     * <p>
     * タグ構成モデルの情報（タグ、タグ値、タグ説明）を使用して新しいタグを作成し、 挿入位置の設定に基づいて以下のいずれかの位置に追加します：
     * </p>
     * <ul>
     * <li>先頭（BEGINNING）：先頭タグに追加</li>
     * <li>指定無し（NONE）：末尾タグに追加</li>
     * <li>末尾（END）：末尾タグに追加</li>
     * <li>現在の位置を維持（PRESERVE）：末尾タグに追加</li>
     * </ul>
     * <p>
     * このメソッドは、新しいタグの作成と追加を一度の操作で行います。 タグの内容は現在のタグ構成モデルから取得されます。
     * </p>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    @Override
    public void addNewTagByPosition() {

        /* 新しいタグの生成 */
        this.tagContentToApply = this.createTagContent();

        /* タグの挿入位置に基づく処理 */
        switch (this.currentTagConfigModel.getInsertPosition()) {

            case BEGINNING:
                /* Javadocタグの先頭 */

                // 先頭タグの位置が特定されているか確認
                if (this.headTagPosOffset > -1) {

                    // 特定されている場合は指定位置に挿入
                    this.replacedJavadocBlock.insert(this.headTagPosOffset,
                        KmgString.concat(this.tagContentToApply, KmgString.LINE_SEPARATOR));

                } else {

                    // 特定されていない場合は末尾に追加
                    this.replacedJavadocBlock.append(this.tagContentToApply);

                }
                break;

            case NONE:
                /* 指定無し */
            case END:
                /* Javadocタグの末尾 */
            case PRESERVE:
                /* 現在の位置を維持（末尾に追加） */
                this.replacedJavadocBlock.append(this.tagContentToApply);
                break;

        }

        /* 先頭タグの位置オフセットを更新 */
        this.headTagPosOffset += this.tagContentToApply.length();

    }

    /**
     * 構成モデルを返す<br>
     * <p>
     * 現在の設定で使用されている構成モデルを返します。 構成モデルには、タグの設定情報やその他の設定が含まれています。
     * </p>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 構成モデル - 現在の設定で使用されている構成モデル
     */
    @Override
    public JdtsConfigsModel getConfigsModel() {

        final JdtsConfigsModel result = this.configsModel;
        return result;

    }

    /**
     * 現在の元のJavadocタグを返す<br>
     * <p>
     * 現在処理中の元のJavadocタグを返します。 このタグは、置換や更新の対象となるタグです。
     * </p>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 現在の元のJavadocタグ - 処理対象の元のJavadocタグ
     */
    @Override
    public JavadocTagModel getCurrentSrcJavadocTag() {

        final JavadocTagModel result = this.currentSrcJavadocTag;
        return result;

    }

    /**
     * 現在のタグ構成モデルを返す<br>
     * <p>
     * 現在処理中のタグ構成モデルを返します。 このモデルには、タグの設定情報や配置ルールが含まれています。
     * </p>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 現在のタグ構成モデル - 処理中のタグ構成情報
     */
    @Override
    public JdtsTagConfigModel getCurrentTagConfigModel() {

        final JdtsTagConfigModel result = this.currentTagConfigModel;
        return result;

    }

    /**
     * 置換後のJavadocブロックを返す<br>
     * <p>
     * タグの追加、更新、削除などの操作が適用された後の Javadocブロックの内容を返します。
     * </p>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 置換後のJavadocブロック - 更新された完全なJavadocブロックの文字列
     */
    @Override
    public String getReplacedJavadocBlock() {

        final String result = this.replacedJavadocBlock.toString();
        return result;

    }

    /**
     * 設定するタグの内容を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 設定するタグの内容
     */
    @Override
    public String getTagContentToApply() {

        final String result = this.tagContentToApply;
        return result;

    }

    /**
     * 元のブロックモデルに構成モデルのタグが存在するか<br>
     * <p>
     * 現在のタグ構成モデルで指定されているタグが、 元のJavadocブロック内に存在するかどうかを確認します。
     * </p>
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
     * 初期化する<br>
     * <p>
     * ブロック置換ロジックの初期状態を設定します。 構成モデルと元のブロックモデルを設定し、 内部状態を初期化します。
     * </p>
     *
     * @param configsModel
     *                      構成モデル - タグの設定情報を含むモデル
     * @param srcBlockModel
     *                      元のブロックモデル - 置換対象のJavadocブロック情報
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外 - 初期化中にエラーが発生した場合
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final JdtsConfigsModel configsModel, final JdtsBlockModel srcBlockModel)
        throws KmgToolMsgException {

        boolean result = false;

        /* 初期パラメータの設定 */
        this.configsModel = configsModel;
        this.srcBlockModel = srcBlockModel;

        /* タグ設定のイテレータを初期化 */
        this.tagConfigIterator = this.configsModel.getJdaTagConfigModels().iterator();
        this.nextTag();

        /* Javadocブロックの初期化 */
        // 元のJavadocを置換用バッファにコピー
        this.replacedJavadocBlock = new StringBuilder(this.srcBlockModel.getJavadocModel().getSrcJavadoc());

        /* 先頭タグの位置を特定 */
        this.headTagPosOffset = this.replacedJavadocBlock.indexOf(JdtsBlockReplLogicImpl.JAVADOC_TAG_START);

        result = true;
        return result;

    }

    /**
     * 次のJavadocタグに進む<br>
     * <p>
     * タグ構成モデルの次のタグに移動し、 対応する元のJavadocタグを設定します。 すべてのタグを処理し終えた場合はfalseを返します。
     * </p>
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

        /* 次のタグが存在するか確認 */
        if (!this.tagConfigIterator.hasNext()) {

            // 次のタグが存在しない場合、現在のタグをクリア
            this.currentTagConfigModel = null;
            this.currentSrcJavadocTag = null;
            return result;

        }

        /* 次のタグを設定 */
        this.currentTagConfigModel = this.tagConfigIterator.next();
        // 対応する元のJavadocタグを検索して設定
        this.currentSrcJavadocTag
            = this.srcBlockModel.getJavadocModel().getJavadocTagsModel().findByTag(this.currentTagConfigModel.getTag());

        result = true;
        return result;

    }

    /**
     * 現在のタグを削除する<br>
     * <p>
     * 現在処理中のJavadocタグを元のブロックから削除します。 タグが存在しない場合は何も行わずfalseを返します。
     * </p>
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

        /* 現在のタグの存在確認 */
        if (this.currentSrcJavadocTag == null) {

            // タグが存在しない場合は処理を終了
            return result;

        }

        /* タグの削除処理 */
        // タグの開始位置と終了位置を特定
        final int startIndex = this.replacedJavadocBlock.indexOf(this.currentSrcJavadocTag.getTargetStr());
        final int endIndex   = startIndex + this.currentSrcJavadocTag.getTargetStr().length();
        // 指定範囲のタグを削除
        this.replacedJavadocBlock.delete(startIndex, endIndex);

        result = true;
        return result;

    }

    /**
     * 誤配置時に現在のタグを削除します。<br>
     * <p>
     * 以下の条件を満たす場合、現在のタグを削除します：
     * </p>
     * <ol>
     * <li>「誤配置時に削除する」が指定されている</li>
     * <li>「タグの配置がJava区分」に一致していない</li>
     * </ol>
     * <p>
     * このメソッドは、タグの配置ルールに従って不適切な位置にあるタグを 自動的に削除するために使用されます。
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

        // 「タグの配置が区分」に一致しているか
        if (this.currentTagConfigModel.isProperlyPlaced(this.srcBlockModel.getClassification())) {
            // 一致している場合

            return result;

        }

        /* 現在のタグを削除する */
        result = this.removeCurrentTag();
        return result;

    }

    /**
     * 既存のタグを置換する<br>
     * <p>
     * 現在のJavadocタグの内容を新しいタグの内容で置換します。 置換は以下の手順で行われます：
     * </p>
     * <ol>
     * <li>既存のタグの開始位置を特定</li>
     * <li>既存のタグの終了位置を計算</li>
     * <li>新しいタグの内容で置換</li>
     * </ol>
     * <p>
     * 置換は、タグの値とタグの説明を含む完全な置換となります。
     * </p>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：置換成功、false：置換失敗
     */
    @Override
    public boolean replaceExistingTag() {

        boolean result = false;

        /* 既存タグの位置特定 */
        final int startIdx = this.replacedJavadocBlock.indexOf(this.currentSrcJavadocTag.getTargetStr());

        // 既存のタグが見つからない場合は処理を終了
        if (startIdx == -1) {

            return result;

        }

        /* 置換範囲の計算 */
        final int endIdx = startIdx + this.currentSrcJavadocTag.getTargetStr().length();

        /* 新しいタグ内容の生成と置換 */
        this.tagContentToApply = this.createTagContent();
        this.replacedJavadocBlock.replace(startIdx, endIdx, this.tagContentToApply);

        result = true;
        return result;

    }

    /**
     * タグを指定位置に再配置する<br>
     * <p>
     * タグの位置が指定されている場合（BEGINNING、END）、 タグを削除して指定位置に再配置します。 位置指定がない場合（NONE、PRESERVE）は何も行いません。
     * </p>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：再配置成功、false：再配置不要または失敗
     */
    @Override
    public boolean repositionTagIfNeeded() {

        boolean result = false;

        final JdaInsertPositionTypes position = this.currentTagConfigModel.getInsertPosition();

        // 位置指定がNONEまたはPRESERVEの場合は再配置不要
        if ((position == JdaInsertPositionTypes.NONE) || (position == JdaInsertPositionTypes.PRESERVE)) {

            return result;

        }

        // 現在のタグを削除
        if (!this.removeCurrentTag()) {

            return result;

        }

        // 指定位置に新しいタグを追加
        this.addNewTagByPosition();

        result = true;
        return result;

    }

    /**
     * 新しいタグを追加すべきか判断する<br>
     * <p>
     * 現在のタグ構成モデルと元のブロックモデルの状態に基づいて、 新しいタグを追加すべきかどうかを判断します。 この判断は、タグの配置ルールやJavaの区分に基づいて行われます。
     * </p>
     *
     * @return true：追加する、false：追加しない
     */
    @Override
    public boolean shouldAddNewTag() {

        final boolean result = this.currentTagConfigModel.isProperlyPlaced(this.srcBlockModel.getClassification());
        return result;

    }

    /**
     * タグを上書きすべきか判断する<br>
     * <p>
     * 現在のタグ構成モデルの設定に基づいて、 既存のタグを上書きすべきかどうかを判断します。 判断基準には以下が含まれます：
     * </p>
     * <ul>
     * <li>上書き設定（NONE, NEVER, ALWAYS, IF_LOWER）</li>
     * <li>バージョン比較（IF_LOWERの場合）</li>
     * </ul>
     *
     * @return true：上書きする、false：上書きしない
     */
    @Override
    public boolean shouldOverwriteTag() {

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

    /**
     * タグの内容を作成する<br>
     * <p>
     * 現在のタグ構成モデルの情報を使用して、 タグの内容を生成します。<br>
     * 生成される内容には、タグ、タグ値、タグの説明が含まれます。
     * </p>
     *
     * @return タグの内容 - フォーマットされたタグの文字列
     */
    private String createTagContent() {

        /* タグ内容の生成 */
        final String result
            = String.format(JdtsBlockReplLogicImpl.TAG_FORMAT, this.currentTagConfigModel.getTag().getKey(),
                this.currentTagConfigModel.getTagValue(), this.currentTagConfigModel.getTagDescription());
        return result;

    }

    /**
     * バージョン比較に基づいて上書きすべきか判断する<br>
     * <p>
     * 現在のタグと新しいタグのバージョンを比較し、 上書きが必要かどうかを判断します。 バージョンタグでない場合は常にtrueを返します。
     * </p>
     *
     * @return true：上書きする、false：上書きしない
     */
    private boolean shouldOverwriteBasedOnVersion() {

        boolean result = false;

        /* バージョンタグの確認 */
        // バージョンタグでない場合は上書きを許可
        if (!this.currentTagConfigModel.getTag().isVersionValue()) {

            result = true;
            return result;

        }

        /* バージョン比較処理 */
        // 既存バージョンと新規バージョンをComparableVersionに変換
        final ComparableVersion srcVer  = new ComparableVersion(this.currentSrcJavadocTag.getTag().get());
        final ComparableVersion destVer = new ComparableVersion(this.currentTagConfigModel.getTagValue());

        // 既存のバージョンと新規バージョンを比較
        // 既存のバージョンが新規バージョンより大きい場合に上書きを許可
        result = srcVer.compareTo(destVer) > 0;

        return result;

    }

}
