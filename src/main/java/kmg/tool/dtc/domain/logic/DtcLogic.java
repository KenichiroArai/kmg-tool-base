package kmg.tool.dtc.domain.logic;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;

/**
 * テンプレートの動的変換ロジックインタフェース<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 *
 * @author KenichiroArai
 */
public interface DtcLogic extends Closeable {

    /**
     * 出力バッファに追加する
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
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @throws KmgToolMsgException
     *                             入出力処理に失敗した場合
     */
    void applyTemplateToInputFile() throws KmgToolMsgException;

    /**
     * 出力バッファコンテンツをクリアする
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    void clearOutputBufferContent() throws KmgToolMsgException;

    /**
     * 読み込み中のデータをクリアする。
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
     * @throws IOException
     *                     入出力例外
     */
    @Override
    void close() throws IOException;

    /**
     * 1件分の内容を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 1件分の内容
     */
    String getContentsOfOneItem();

    /**
     * 入力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 入力ファイルパス
     */
    Path getInputPath();

    /**
     * 出力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 出力ファイルパス
     */
    Path getOutputPath();

    /**
     * テンプレートファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return テンプレートファイルパス
     */
    Path getTemplatePath();

    /**
     * 初期化する
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
     * @author KenichiroArai
     *
     * @sine 1.0.0
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
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean readOneLineOfData() throws KmgToolMsgException;

    /**
     * 出力バッファを書き込む
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean writeOutputBuffer() throws KmgToolMsgException;
}
