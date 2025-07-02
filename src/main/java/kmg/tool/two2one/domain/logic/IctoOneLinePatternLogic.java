package kmg.tool.two2one.domain.logic;

import java.io.Closeable;
import java.nio.file.Path;
import java.util.List;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;

/**
 * 入力、中間、テンプレート、出力の1行パターンインタフェース
 */
public interface IctoOneLinePatternLogic extends Closeable {

    /**
     * 書き込み対象に行を追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addOneLineOfDataToRows() throws KmgToolMsgException;

    /**
     * 処理中のデータをクリアする。
     *
     * @return true：成功、false：失敗
     */
    boolean clearProcessingData();

    /**
     * 書き込み対象の行データのリストをクリアする。
     *
     * @return true：成功、false：失敗
     */
    boolean clearRows();

    /**
     * 変換後の1行データを返す。
     *
     * @return 変換後の1行データ
     */
    String getConvertedLine();

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
     * 書き込み対象の行データのリストを返す。
     *
     * @return 書き込み対象の行データのリスト
     */
    List<List<String>> getRows();

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
     *                             KMGツールメッセージ例外
     */
    boolean initialize(Path inputPath, Path outputPath) throws KmgToolMsgException;

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
     * 中間ファイルに書き込む。<br>
     * <p>
     * 入力ファイルから指定の形式に変換して中間ファイルに出力する。
     * </p>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean writeIntermediateFile() throws KmgToolMsgException;
}
