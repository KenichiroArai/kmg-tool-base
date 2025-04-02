package kmg.tool.application.logic;

import java.nio.file.Path;
import java.util.List;

import kmg.tool.application.model.JavadocAppenderTagsModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加ロジックインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface JavadocAppenderLogic {

    /**
     * Javadocタグモデルを作成する<br>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean createJavadocTagsModel() throws KmgToolException;

    /**
     * 対象のJavaファイル
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean createJavaFileList() throws KmgToolException;

    /**
     * 現在の書き込みするファイルの中身を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 現在の書き込みするファイルの中身
     */
    String getCurrentContentsOfFileToWrite();

    /**
     * 現在のJavaファイルパスを返す。
     *
     * @return 現在のJavaファイルパス
     */
    Path getCurrentJavaFilePath();

    /**
     * Javadoc追加のタグモデルを取得する<br>
     *
     * @return Javadocタグモデル
     */
    JavadocAppenderTagsModel getJavadocAppenderTagsModel();

    /**
     * 対象のJavaファイルパスのリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @sine 0.1.0
     *
     * @return 対象のJavaファイルリスト
     */
    List<Path> getJavaFilePathList();

    /**
     * 対象ファイルパス
     *
     * @return 対象ファイルパス
     */
    Path getTargetPath();

    /**
     * テンプレートファイルパス
     *
     * @return テンプレートファイルパス
     */
    Path getTemplatePath();

    /**
     * 合計行数を返す。
     *
     * @return 合計行数
     */
    long getTotalRows();

    /**
     * 初期化する
     *
     * @param targetPath
     *                     対象ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean initialize(final Path targetPath, final Path templatePath) throws KmgToolException;

    /**
     * 次のJavaファイルに進む。
     *
     * @return true：ファイルあり、false:ファイルなし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean nextJavaFile() throws KmgToolException;

    /**
     * 現在のJavaファイルにJavadocを設定する。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @param insertAtTop
     *                    タグを先頭に挿入するかどうか
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean setJavadoc(final boolean insertAtTop) throws KmgToolException;

    /**
     * 現在のJavaファイルに書き込む
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean writeCurrentJavaFile() throws KmgToolException;
}
