package kmg.tool.base.iito.domain.logic;

import java.io.Closeable;
import java.nio.file.Path;
import java.util.List;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;

/**
 * 入力、中間、テンプレート、出力の1行パターンインタフェース
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.2
 */
public interface IctoOneLinePatternLogic extends Closeable {

    /**
     * 書き込み対象に行を追加する。
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     */
    boolean addOneLineOfDataToRows();

    /**
     * 処理中のデータをクリアする。
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     */
    boolean clearProcessingData();

    /**
     * 書き込み対象の行データのリストをクリアする。
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     */
    boolean clearRows();

    /**
     * 変換後の1行データを返す。
     *
     * @since 0.2.0
     *
     * @return 変換後の1行データ
     */
    String getConvertedLine();

    /**
     * 読み込んだ１行データを返す。
     *
     * @since 0.2.0
     *
     * @return 読み込んだ１行データ
     */
    String getLineOfDataRead();

    /**
     * 現在の行番号を返す<br>
     *
     * @since 0.2.0
     *
     * @return 現在の行番号
     */
    int getNowLineNumber();

    /**
     * 出力ファイルの区切り文字を返す。<br>
     *
     * @since 0.2.2
     *
     * @return 出力ファイルの区切り文字
     */
    KmgDelimiterTypes getOutputDelimiter();

    /**
     * 書き込み対象の行データのリストを返す。
     *
     * @since 0.2.0
     *
     * @return 書き込み対象の行データのリスト
     */
    List<List<String>> getRows();

    /**
     * 初期化する。
     *
     * @since 0.2.0
     *
     * @param inputPath
     *                   入力ファイルパス
     * @param outputPath
     *                   出力ファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(Path inputPath, Path outputPath) throws KmgToolMsgException;

    /**
     * 初期化する。<br>
     * <p>
     * 出力ファイルの区切り文字を指定して初期化します。
     * </p>
     *
     * @since 0.2.2
     *
     * @param inputPath
     *                        入力ファイルパス
     * @param outputPath
     *                        出力ファイルパス
     * @param outputDelimiter
     *                        出力ファイルの区切り文字
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(Path inputPath, Path outputPath, KmgDelimiterTypes outputDelimiter) throws KmgToolMsgException;

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
     * 中間ファイルに書き込む。<br>
     * <p>
     * 入力ファイルから指定の形式に変換して中間ファイルに出力する。
     * </p>
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean writeIntermediateFile() throws KmgToolMsgException;
}
