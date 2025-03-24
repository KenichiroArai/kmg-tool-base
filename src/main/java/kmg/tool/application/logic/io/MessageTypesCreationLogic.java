package kmg.tool.application.logic.io;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import kmg.tool.domain.logic.io.IctoOneLinePatternLogic;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * <h2>メッセージの種類作成ロジックインタフェース</h2>
 * <p>
 * メッセージの種類を定義するYMLファイルを自動生成するためのロジックインタフェースです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
public interface MessageTypesCreationLogic extends IctoOneLinePatternLogic {

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
     * 項目を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean addItemToCsvRows() throws KmgToolException;

    /**
     * 書き込み対象に行を追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    boolean addOneLineOfDataToCsvRows() throws KmgToolException;

    /**
     * 書き込み対象のCSVデータのリストをクリアする。
     *
     * @return true：成功、false：失敗
     */
    @Override
    boolean clearCsvRows();

    /**
     * 処理中のデータをクリアする。
     *
     * @return true：成功、false：失敗
     */
    @Override
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
     * メッセージの種類定義から項目と項目名に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean convertMessageTypesDefinition() throws KmgToolException;

    /**
     * 変換後の1行データを返す。
     *
     * @return 変換後の1行データ
     */
    @Override
    String getConvertedLine();

    /**
     * 書き込み対象のCSVデータのリストを返す。
     *
     * @return 書き込み対象のCSVデータのリスト
     */
    @Override
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
    @Override
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
    @Override
    boolean initialize(Path inputPath, Path outputPath) throws KmgToolException;

    /**
     * 1行データを読み込む。
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
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
    @Override
    boolean writeCsvFile() throws KmgToolException;
}
