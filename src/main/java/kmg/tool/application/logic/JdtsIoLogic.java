package kmg.tool.application.logic;

import java.nio.file.Path;
import java.util.List;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグ設定の入出力ロジックインタフェース<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JdtsIoLogic {

    /**
     * 現在のJavaファイルパスを返す。
     *
     * @return 現在のJavaファイルパス
     */
    Path getCurrentJavaFilePath();

    /**
     * 現在の読込んだ内容を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 現在の読込んだ内容
     */
    String getCurrentReadContent();

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
     * 初期化する
     *
     * @param targetPath
     *                   対象ファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean initialize(final Path targetPath) throws KmgToolException;

    /**
     * ロードする。
     * <p>
     * 対象ファイルパスから対象となるJavaファイルをリストにロードする。
     * </p>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean load() throws KmgToolException;

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
     * 内容を読込む。
     * <p>
     * 現在のファイルを読み込む。
     * </p>
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean read() throws KmgToolException;

    /**
     * 内容を書き込む
     *
     * @param contents
     *                 内容
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean write(String contents) throws KmgToolException;

}
