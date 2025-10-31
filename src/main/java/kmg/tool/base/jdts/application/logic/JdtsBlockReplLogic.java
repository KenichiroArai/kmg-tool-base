package kmg.tool.base.jdts.application.logic;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.jdoc.domain.model.JavadocTagModel;
import kmg.tool.base.jdts.application.model.JdtsBlockModel;
import kmg.tool.base.jdts.application.model.JdtsConfigsModel;
import kmg.tool.base.jdts.application.model.JdtsTagConfigModel;

/**
 * Javadocタグ設定のブロック置換ロジックインタフェース<br>
 * <p>
 * このインタフェースは、Javadocタグの設定と置換に関する操作を定義します。 主な機能は以下の通りです：
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
 * </p>
 * <ul>
 * <li>Jdts: JavadocTagSetterの略</li>
 * <li>Repl: Replacementの略</li>
 * </ul>
 * <p>
 * このインタフェースを実装するクラスは、Javadocタグの操作に関する すべてのメソッドを提供する必要があります。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface JdtsBlockReplLogic {

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
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     */
    boolean addNewTagByPosition();

    /**
     * 構成モデルを返す<br>
     * <p>
     * 現在の設定で使用されている構成モデルを返します。 構成モデルには、タグの設定情報やその他の設定が含まれています。
     * </p>
     *
     * @since 0.2.0
     *
     * @return 構成モデル - 現在の設定で使用されている構成モデル
     */
    JdtsConfigsModel getConfigsModel();

    /**
     * 現在の元のJavadocタグを返す<br>
     * <p>
     * 現在処理中の元のJavadocタグを返します。 このタグは、置換や更新の対象となるタグです。
     * </p>
     *
     * @since 0.2.0
     *
     * @return 現在の元のJavadocタグ - 処理対象の元のJavadocタグ
     */
    JavadocTagModel getCurrentSrcJavadocTag();

    /**
     * 現在のタグ構成モデルを返す<br>
     * <p>
     * 現在処理中のタグ構成モデルを返します。 このモデルには、タグの設定情報や配置ルールが含まれています。
     * </p>
     *
     * @since 0.2.0
     *
     * @return 現在のタグ構成モデル - 処理中のタグ構成情報
     */
    JdtsTagConfigModel getCurrentTagConfigModel();

    /**
     * 置換後のJavadocブロックを返す<br>
     * <p>
     * タグの追加、更新、削除などの操作が適用された後の Javadocブロックの内容を返します。
     * </p>
     *
     * @since 0.2.0
     *
     * @return 置換後のJavadocブロック - 更新された完全なJavadocブロックの文字列
     */
    String getReplacedJavadocBlock();

    /**
     * 設定するタグの内容を返す<br>
     *
     * @since 0.2.0
     *
     * @return 設定するタグの内容
     */
    String getTagContentToApply();

    /**
     * 元のブロックモデルに構成モデルのタグが存在するか<br>
     * <p>
     * 現在のタグ構成モデルで指定されているタグが、 元のJavadocブロック内に存在するかどうかを確認します。
     * </p>
     *
     * @since 0.2.0
     *
     * @return true：存在する場合、false：存在しない場合
     */
    boolean hasExistingTag();

    /**
     * 初期化する<br>
     * <p>
     * ブロック置換ロジックの初期状態を設定します。 構成モデルと元のブロックモデルを設定し、 内部状態を初期化します。
     * </p>
     *
     * @since 0.2.0
     *
     * @param configsModel
     *                      構成モデル - タグの設定情報を含むモデル
     * @param srcBlockModel
     *                      元のブロックモデル - 置換対象のJavadocブロック情報
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外 - 初期化中にエラーが発生した場合
     */
    boolean initialize(final JdtsConfigsModel configsModel, final JdtsBlockModel srcBlockModel)
        throws KmgToolMsgException;

    /**
     * 次のJavadocタグに進む<br>
     * <p>
     * タグ構成モデルの次のタグに移動し、 対応する元のJavadocタグを設定します。 すべてのタグを処理し終えた場合はfalseを返します。
     * </p>
     *
     * @since 0.2.0
     *
     * @return true：次のタグがある場合、false：次のタグがない場合
     */
    boolean nextTag();

    /**
     * 現在のタグを削除する<br>
     * <p>
     * 現在処理中のJavadocタグを元のブロックから削除します。 タグが存在しない場合は何も行わずfalseを返します。
     * </p>
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     */
    boolean removeCurrentTag();

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
     * @since 0.2.0
     *
     * @return true：タグを削除した場合、false：タグを削除しなかった場合
     */
    boolean removeCurrentTagOnError();

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
     * @since 0.2.0
     *
     * @return true：置換成功、false：置換失敗
     */
    boolean replaceExistingTag();

    /**
     * タグを指定位置に再配置する<br>
     * <p>
     * タグの位置が指定されている場合（BEGINNING、END）、 タグを削除して指定位置に再配置します。 位置指定がない場合（NONE、PRESERVE）は何も行いません。
     * </p>
     *
     * @since 0.2.0
     *
     * @return true：再配置成功、false：再配置不要または失敗
     */
    boolean repositionTagIfNeeded();

    /**
     * 新しいタグを追加すべきか判断する<br>
     * <p>
     * 現在のタグ構成モデルと元のブロックモデルの状態に基づいて、 新しいタグを追加すべきかどうかを判断します。 この判断は、タグの配置ルールやJavaの区分に基づいて行われます。
     * </p>
     *
     * @since 0.2.0
     *
     * @return true：追加する、false：追加しない
     */
    boolean shouldAddNewTag();

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
     * @since 0.2.0
     *
     * @return true：上書きする、false：上書きしない
     */
    boolean shouldOverwriteTag();
}
