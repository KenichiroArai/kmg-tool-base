package kmg.tool.base.io.domain.logic;

import java.nio.file.Path;
import java.util.List;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;

/**
 * ファイルイテレーターロジックインタフェース<br>
 * <p>
 * ディレクトリ内のファイルを順次処理するためのイテレーター機能を提供します。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.2
 *
 * @version 0.2.2
 */
public interface FileIteratorLogic {

    /**
     * 現在のファイルパスを返す。
     *
     * @since 0.2.2
     *
     * @return 現在のファイルパス
     */
    Path getCurrentFilePath();

    /**
     * ファイルパスのリストを返す<br>
     *
     * @since 0.2.2
     *
     * @return ファイルのパス
     */
    List<Path> getFilePathList();

    /**
     * 読込んだ内容を返す<br>
     *
     * @since 0.2.2
     *
     * @return 読込んだ内容
     */
    String getReadContent();

    /**
     * 対象ファイルパス
     *
     * @since 0.2.2
     *
     * @return 対象ファイルパス
     */
    Path getTargetPath();

    /**
     * 初期化する
     * <p>
     * 拡張子が指定されない場合は、デフォルトで".java"を対象とする。
     * </p>
     *
     * @since 0.2.2
     *
     * @param targetPath
     *                   対象ファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(final Path targetPath) throws KmgToolMsgException;

    /**
     * 初期化する
     * <p>
     * 拡張子を指定して初期化する。fileExtensionがnullの場合は全ファイルを対象とする。
     * </p>
     *
     * @since 0.2.2
     *
     * @param targetPath
     *                      対象ファイルパス
     * @param fileExtension
     *                      対象ファイルの拡張子（nullの場合は全ファイルを対象）
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(final Path targetPath, final String fileExtension) throws KmgToolMsgException;

    /**
     * ロードする。
     * <p>
     * 対象ファイルパスから対象となるファイルをリストにロードする。 拡張子が指定されている場合は、その拡張子のファイルのみを対象とする。 拡張子がnullの場合は、全ファイルを対象とする。
     * </p>
     *
     * @since 0.2.2
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean load() throws KmgToolMsgException;

    /**
     * 内容を読み込む。
     *
     * @since 0.2.2
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean loadContent() throws KmgToolMsgException;

    /**
     * 次のファイルに進む。
     *
     * @since 0.2.2
     *
     * @return true：ファイルあり、false:ファイルなし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean nextFile() throws KmgToolMsgException;

    /**
     * ファイルインデックスを初期化する。
     *
     * @since 0.2.2
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean resetFileIndex() throws KmgToolMsgException;

    /**
     * 書き込む内容を設定する。
     *
     * @since 0.2.2
     *
     * @param content
     *                内容
     */
    void setWriteContent(String content);

    /**
     * 内容を書き込む
     *
     * @since 0.2.2
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean writeContent() throws KmgToolMsgException;

}
