package kmg.tool.domain.logic.two2one;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;
import kmg.tool.infrastructure.type.msg.KmgToolLogMsgTypes;

/**
 * 入力、CSV、テンプレート、出力の1行パターンの抽象クラス
 */
public abstract class AbstractIctoOneLinePatternLogic implements IctoOneLinePatternLogic {

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @since 0.1.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /** 入力ファイルパス */
    private Path inputPath;

    /** 出力ファイルパス */
    private Path outputPath;

    /** 入力ファイルのBufferedReader */
    private BufferedReader reader;

    /** 出力ファイルのBufferedReader */
    private BufferedWriter writer;

    /** 読み込んだ１行データ */
    private String lineOfDataRead;

    /** 変換後の1行データ */
    private String convertedLine;

    /** 現在の行番号 */
    private int nowLineNumber;

    // TODO KenichiroArai 2025/06/15 現状はcsvだけではなく区切り文字が指定できるため、変数名はlineRowsの方がいい。
    /** 書き込み対象のCSVデータのリスト */
    private final List<List<String>> csvRows;

    /**
     * デフォルトコンストラクタ
     */
    public AbstractIctoOneLinePatternLogic() {

        this(LoggerFactory.getLogger(AbstractIctoOneLinePatternLogic.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *               ロガー
     */
    protected AbstractIctoOneLinePatternLogic(final Logger logger) {

        this.logger = logger;
        this.csvRows = new ArrayList<>();

    }

    /**
     * 書き込み対象に行を追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean addOneLineOfDataToCsvRows() throws KmgToolMsgException {

        boolean result = false;

        final List<String> newRow = new ArrayList<>();
        this.csvRows.add(newRow);

        result = true;
        return result;

    }

    /**
     * 書き込み対象のCSVデータのリストをクリアする。
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean clearCsvRows() {

        boolean result = false;

        this.csvRows.clear();

        result = true;
        return result;

    }

    /**
     * 処理中のデータをクリアする。
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean clearProcessingData() {

        boolean result = false;

        this.lineOfDataRead = null;
        this.convertedLine = null;
        this.nowLineNumber = 0;

        result = true;
        return result;

    }

    /**
     * リソースをクローズする。
     *
     * @throws IOException
     *                     入出力例外
     */
    @Override
    public void close() throws IOException {

        this.closeReader();
        this.closeWriter();

    }

    /**
     * 変換後の1行データを返す。
     */
    @Override
    public String getConvertedLine() {

        final String result = this.convertedLine;
        return result;

    }

    /**
     * 書き込み対象のCSVデータのリストを返す。
     *
     * @return 書き込み対象のCSVデータのリスト
     */
    @Override
    public List<List<String>> getCsvRows() {

        final List<List<String>> result = this.csvRows;
        return result;

    }

    /**
     * 読み込んだ１行データを返す。
     *
     * @return 読み込んだ１行データ
     */
    @Override
    public String getLineOfDataRead() {

        final String result = this.lineOfDataRead;
        return result;

    }

    /**
     * 現在の行番号を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 現在の行番号
     */
    @Override
    public int getNowLineNumber() {

        final int result = this.nowLineNumber;
        return result;

    }

    /**
     * 初期化する。
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
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path outputPath) throws KmgToolMsgException {

        boolean result = false;

        this.inputPath = inputPath;
        this.outputPath = outputPath;

        /* データのクリア */
        this.clearProcessingData();

        this.clearCsvRows();

        /* ファイルを開く */

        // 入力ファイルを開く
        this.openInputFile();

        // 出力ファイルを開く
        this.openOutputFile();

        result = true;
        return result;

    }

    /**
     * 1行データを読み込む。
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean readOneLineOfData() throws KmgToolMsgException {

        boolean result = false;

        try {

            // 1行読み込み
            this.lineOfDataRead = this.reader.readLine();

            this.nowLineNumber++;

        } catch (final IOException e) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN31007;
            final Object[]           messageArgs  = {};
            throw new KmgToolMsgException(messageTypes, messageArgs, e);

        }

        this.convertedLine = this.lineOfDataRead;

        // ファイルの終わりに達したか
        if (this.lineOfDataRead == null) {
            // 達した場合

            return result;

        }

        result = true;
        return result;

    }

    /**
     * CSVファイルに書き込む。<br>
     * <p>
     * 入力ファイルからCSV形式に変換してCSVファイルに出力する。
     * </p>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean writeCsvFile() throws KmgToolMsgException {

        boolean result = false;

        /* CSVの書き込み処理 */
        for (final List<String> csvRow : this.csvRows) {

            try {

                final String csvLine = KmgDelimiterTypes.COMMA.join(csvRow);
                this.writer.write(csvLine);
                this.writer.write(System.lineSeparator());

            } catch (final IOException e) {

                final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN13005;
                final Object[]           messageArgs  = {
                    this.outputPath.toString()
                };
                throw new KmgToolMsgException(messageTypes, messageArgs, e);

            }

        }

        /* バッファに残っているデータをファイルに書き込む */
        try {

            this.writer.flush();

        } catch (final IOException e) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN13006;
            final Object[]           messageArgs  = {
                this.outputPath.toString()
            };
            throw new KmgToolMsgException(messageTypes, messageArgs, e);

        }

        result = true;
        return result;

    }

    /**
     * 書き込み対象のCSVデータの最後のリストにCSVデータを追加する。
     *
     * @param csvRow
     *               CSVデータ
     *
     * @return true：追加成功、false：追加失敗
     */
    protected boolean addCsvRow(final String csvRow) {

        boolean result;

        final List<String> row = this.csvRows.getLast();
        result = row.add(csvRow);

        return result;

    }

    /**
     * 行から対象に該当する部分を置換値に置換する。
     *
     * @param target
     *                    対象
     * @param replacement
     *                    置換値
     *
     * @return true：変更あり、false：変更なし、対象、置換値がnull
     */
    protected boolean replaceInLine(final String target, final String replacement) {

        boolean result = false;

        if (target == null) {

            return result;

        }

        if (replacement == null) {

            return result;

        }

        final String before = this.convertedLine;
        this.convertedLine = this.convertedLine.replace(target, replacement);
        result = !before.equals(this.convertedLine);

        return result;

    }

    /**
     * リーダーリソースをクローズする。
     *
     * @throws IOException
     *                     入出力例外
     */
    private void closeReader() throws IOException {

        if (this.reader == null) {

            return;

        }

        try {

            this.reader.close();

        } catch (final IOException e) {

            this.reader = null;

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG13000;
            final Object[]           logMsgArgs  = {
                this.inputPath.toString(),
            };
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

    }

    /**
     * ライターリソースをクローズする。
     *
     * @throws IOException
     *                     入出力例外
     */
    private void closeWriter() throws IOException {

        if (this.writer == null) {

            return;

        }

        try {

            this.writer.close();

        } catch (final IOException e) {

            this.writer = null;

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG13001;
            final Object[]           logMsgArgs  = {
                this.outputPath.toString(),
            };
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

    }

    /**
     * 入力ファイルを開く
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("resource")
    private void openInputFile() throws KmgToolMsgException {

        try {

            this.reader = Files.newBufferedReader(this.inputPath);

        } catch (final IOException e) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN13007;
            final Object[]           messageArgs  = {
                this.inputPath.toString()
            };
            throw new KmgToolMsgException(messageTypes, messageArgs, e);

        }

    }

    /**
     * 出力ファイルを開く
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("resource")
    private void openOutputFile() throws KmgToolMsgException {

        try {

            this.writer = Files.newBufferedWriter(this.outputPath);

        } catch (final IOException e) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN13008;
            final Object[]           messageArgs  = {
                this.outputPath.toString()
            };
            throw new KmgToolMsgException(messageTypes, messageArgs, e);

        }

    }
}
