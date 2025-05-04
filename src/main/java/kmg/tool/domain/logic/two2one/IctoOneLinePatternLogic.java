package kmg.tool.domain.logic.two2one;

import java.io.Closeable;
import java.nio.file.Path;
import java.util.List;

import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * 入力、CSV、テンプレート、出力の1行パターンインタフェース
 */
public interface IctoOneLinePatternLogic extends Closeable {

    /**
     * 書き込み対象に行を追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    boolean addOneLineOfDataToCsvRows() throws KmgToolMsgException;

    /**
     * 書き込み対象のCSVデータのリストをクリアする。
     *
     * @return true：成功、false：失敗
     */
    boolean clearCsvRows();

    /**
     * 処理中のデータをクリアする。
     *
     * @return true：成功、false：失敗
     */
    boolean clearProcessingData();

    /**
     * 変換後の1行データを返す。
     *
     * @return 変換後の1行データ
     */
    String getConvertedLine();

    /**
     * 書き込み対象のCSVデータのリストを返す。
     *
     * @return 書き込み対象のCSVデータのリスト
     */
    List<List<String>> getCsvRows();

    /**
     * 読み込んだ１行データを返す。
     *
     * @return 読み込んだ１行データ
     */
    String getLineOfDataRead();

    /**
     * 現在の行番号を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 現在の行番号
     */
    int getNowLineNumber();

    /**
     * 初期化する。
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                   入力ファイルパス
     * @param outputPath
     *                   出力ファイルパス
     *
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    boolean initialize(Path inputPath, Path outputPath) throws KmgToolMsgException;

    /**
     * 1行データを読み込む。
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    boolean readOneLineOfData() throws KmgToolMsgException;

    /**
     * CSVファイルに書き込む。<br>
     * <p>
     * 入力ファイルからCSV形式に変換してCSVファイルに出力する。
     * </p>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    boolean writeCsvFile() throws KmgToolMsgException;
}
