package kmg.tool.application.logic.jdts;

import kmg.tool.application.model.jdts.JdtsBlockModel;
import kmg.tool.application.model.jdts.JdtsConfigsModel;
import kmg.tool.application.model.jdts.JdtsTagConfigModel;
import kmg.tool.domain.model.JavadocTagModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグ設定のブロック置換ロジックインタフェース<br>
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
public interface JdtsBlockReplLogic {

    /**
     * 最終的なJavadocを構築する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     */
    boolean buildFinalJavadoc();

    /**
     * 構成モデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 構成モデル
     */
    JdtsConfigsModel getConfigsModel();

    /**
     * 現在の元のJavadocタグを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 現在の元のJavadocタグ
     */
    JavadocTagModel getCurrentSrcJavadocTag();

    /**
     * 現在のタグ構成モデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 現在のタグ構成モデル
     */
    JdtsTagConfigModel getCurrentTagConfigModel();

    /**
     * 置換後のJavadocブロックを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 置換後のJavadocブロック
     */
    String getReplacedJavadocBlock();

    /**
     * 現在のタグが存在するか確認する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：存在する場合、false：存在しない場合
     */
    boolean hasExistingTag();

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
    boolean initialize(final JdtsConfigsModel configsModel, final JdtsBlockModel srcBlockModel) throws KmgToolException;

    /**
     * 次のJavadocタグに進む<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：次のタグがある場合、false：次のタグがない場合
     */
    boolean nextTag();

    /**
     * 新しいタグを処理する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：新しいタグを追加する、false：タグを追加しない
     */
    boolean processNewTag();

    /**
     * 現在のタグを削除する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     */
    boolean removeCurrentTag();

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
    boolean removeCurrentTagOnError();

    /**
     * 現在のタグを更新する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     */
    boolean updateCurrentTag();
}
