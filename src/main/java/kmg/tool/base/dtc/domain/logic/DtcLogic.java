package kmg.tool.base.dtc.domain.logic;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;

/**
 * テンプレートの動的変換ロジックインタフェース<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface DtcLogic extends Closeable {

    /**
     * 出力バッファに追加する
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addOutputBufferContent() throws KmgToolMsgException;

    /**
     * 入力ファイルからテンプレートに基づいて変換する。
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             入出力処理に失敗した場合
     */
    void applyTemplateToInputFile() throws KmgToolMsgException;

    /**
     * 出力バッファコンテンツをクリアする
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    void clearOutputBufferContent() throws KmgToolMsgException;

    /**
     * 読み込み中のデータをクリアする。
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean clearReadingData() throws KmgToolMsgException;

    /**
     * リソースをクローズする。
     *
     * @since 0.2.0
     *
     * @throws IOException
     *                     入出力例外
     */
    @Override
    void close() throws IOException;

    /**
     * 1件分の内容を返す<br>
     *
     * @since 0.2.0
     *
     * @return 1件分の内容
     */
    String getContentsOfOneItem();

    /**
     * 入力ファイルパスを返す<br>
     *
     * @since 0.2.0
     *
     * @return 入力ファイルパス
     */
    Path getInputPath();

    /**
     * 出力ファイルパスを返す<br>
     *
     * @since 0.2.0
     *
     * @return 出力ファイルパス
     */
    Path getOutputPath();

    /**
     * テンプレートファイルパスを返す<br>
     *
     * @since 0.2.0
     *
     * @return テンプレートファイルパス
     */
    Path getTemplatePath();

    /**
     * 初期化する
     *
     * @since 0.2.0
     *
     * @param inputPath
     *                     入力ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     * @param outputPath
     *                     出力ファイルパス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     *
     * @return true：成功、false：失敗
     */
    boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath) throws KmgToolMsgException;

    /**
     * 初期化する
     *
     * @since 0.2.0
     *
     * @param inputPath
     *                              入力ファイルパス
     * @param templatePath
     *                              テンプレートファイルパス
     * @param outputPath
     *                              出力ファイルパス
     * @param intermediateDelimiter
     *                              中間行の区切り文字
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     *
     * @return true：成功、false：失敗
     */
    boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath,
        KmgDelimiterTypes intermediateDelimiter) throws KmgToolMsgException;

    /**
     * テンプレートファイルを読み込む<br>
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             テンプレートの読み込みに失敗した場合
     */
    boolean loadTemplate() throws KmgToolMsgException;

    /**
     * 1行データを読み込む。
     *
     * @since 0.2.0
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean readOneLineOfData() throws KmgToolMsgException;

    /**
     * 出力バッファを書き込む
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean writeOutputBuffer() throws KmgToolMsgException;
}
