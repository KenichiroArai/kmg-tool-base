package kmg.tool.application.logic.io;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * <h2>列挙型からcase文作成ロジックインタフェース</h2>
 * <p>
 * 列挙型の定義からswitch-case文を自動生成するためのロジックインタフェースです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
public interface Enum2SwitchCaseMakingLogic extends Closeable {

    /**
     * 項目を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean addItemToCsvRows() throws KmgToolException;

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean addItemNameToCsvRows() throws KmgToolException;

    /**
     * 書き込み対象に行を追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean addOneLineOfDataToCsvRows() throws KmgToolException;

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
     * リソースをクローズする。
     *
     * @throws IOException
     *                     入出力例外
     */
    @Override
    void close() throws IOException;

    /**
     * 列挙型定義から項目と項目名に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean convertEnumDefinition() throws KmgToolException;

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
     * 項目を返す。
     *
     * @return 項目
     */
    String getItem();

    /**
     * 項目名を返す。
     *
     * @return 項目名
     */
    String getItemName();

    /**
     * 読み込んだ１行データを返す。
     *
     * @return 読み込んだ１行データ
     */
    String getLineOfDataRead();

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
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean initialize(Path inputPath, Path outputPath) throws KmgToolException;

    /**
     * 1行データを読み込む。
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean readOneLineOfData() throws KmgToolException;

    /**
     * CSVファイルに書き込む。<br>
     * <p>
     * 入力ファイルからCSV形式に変換してCSVファイルに出力する。
     * </p>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean writeCsvFile() throws KmgToolException;
}
