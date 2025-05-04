package kmg.tool.application.logic.two2one.dtc;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import kmg.tool.domain.logic.two2one.IctoOneLinePatternLogic;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

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
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    boolean addItemNameToCsvRows() throws KmgToolMsgException;

    /**
     * 項目を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    boolean addItemToCsvRows() throws KmgToolMsgException;

    /**
     * 書き込み対象に行を追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    @Override
    boolean addOneLineOfDataToCsvRows() throws KmgToolMsgException;

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
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    boolean convertMessageTypesDefinition() throws KmgToolMsgException;

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
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    @Override
    boolean initialize(Path inputPath, Path outputPath) throws KmgToolMsgException;

    /**
     * 1行データを読み込む。
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    @Override
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
    @Override
    boolean writeCsvFile() throws KmgToolMsgException;
}
