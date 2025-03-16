package kmg.tool.application.logic.io;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * アクセサ作成ロジックインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface AccessorCreationLogic extends Closeable {

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean addItemToCsvRows() throws KmgToolException;

    /**
     * Javadocコメントを書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean addJavadocCommentToCsvRows() throws KmgToolException;

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
     * 型情報を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean addTypeToCsvRows() throws KmgToolException;

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
     * フィールド宣言から型、項目名、先頭大文字項目に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean convertFields() throws KmgToolException;

    /**
     * Javadocの変換を行う。
     *
     * @return true：変換あり、false：変換なし
     */
    boolean convertJavadoc();

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
     * 項目名返す。
     *
     * @return 項目名
     */
    String getItem();

    /**
     * Javadocコメントを返す。
     *
     * @return Javadocコメント
     */
    String getJavadocComment();

    /**
     * 読み込んだ１行データを返す。
     *
     * @return 読み込んだ１行データ
     */
    String getLineOfDataRead();

    /**
     * 型を返す。
     *
     * @return 型
     */
    String getTyep();

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
     * Javadocの解析中かを返す。
     *
     * @return true：Javadoc解析中、false：Javadoc解析外
     */
    boolean isInJavadocParsing();

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
     * 不要な修飾子を削除する。
     *
     * @return true：成功、false：失敗
     */
    boolean removeModifier();

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
