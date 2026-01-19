package kmg.tool.base.iito.domain.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseLogMsgTypes;

/**
 * 入力、中間、テンプレート、出力の1行パターンの抽象クラス<br>
 * 「Iito」→「InputIntermediateTemplateOutput」の略。
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
public abstract class AbstractIctoOneLinePatternLogic implements IctoOneLinePatternLogic {

    /**
     * ロガー
     *
     * @since 0.2.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @since 0.2.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * 入力ファイルパス
     *
     * @since 0.2.0
     */
    private Path inputPath;

    /**
     * 出力ファイルパス
     *
     * @since 0.2.0
     */
    private Path outputPath;

    /**
     * 入力ファイルのBufferedReader
     *
     * @since 0.2.0
     */
    private BufferedReader reader;

    /**
     * 出力ファイルのBufferedReader
     *
     * @since 0.2.0
     */
    private BufferedWriter writer;

    /**
     * 読み込んだ１行データ
     *
     * @since 0.2.0
     */
    private String lineOfDataRead;

    /**
     * 変換後の1行データ
     *
     * @since 0.2.0
     */
    private String convertedLine;

    /**
     * 出力ファイルの区切り文字
     *
     * @since 0.2.0
     */
    private KmgDelimiterTypes outputDelimiter;

    /**
     * 現在の行番号
     *
     * @since 0.2.0
     */
    private int nowLineNumber;

    /**
     * 書き込み対象の行リスト
     *
     * @since 0.2.0
     */
    private final List<List<String>> rows;

    /**
     * デフォルトコンストラクタ
     *
     * @since 0.2.0
     */
    public AbstractIctoOneLinePatternLogic() {

        this(LoggerFactory.getLogger(AbstractIctoOneLinePatternLogic.class));

        this.outputDelimiter = KmgDelimiterTypes.COMMA;

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param logger
     *               ロガー
     */
    protected AbstractIctoOneLinePatternLogic(final Logger logger) {

        this.logger = logger;
        this.rows = new ArrayList<>();

    }

    /**
     * 書き込み対象に行を追加する。
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean addOneLineOfDataToRows() {

        boolean result = false;

        final List<String> newRow = new ArrayList<>();
        this.rows.add(newRow);

        result = true;
        return result;

    }

    /**
     * 処理中のデータをクリアする。
     *
     * @since 0.2.0
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
     * 書き込み対象の行データのリストをクリアする。
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean clearRows() {

        boolean result = false;

        this.rows.clear();

        result = true;
        return result;

    }

    /**
     * リソースをクローズする。
     *
     * @since 0.2.0
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
     *
     * @since 0.2.0
     */
    @Override
    public String getConvertedLine() {

        final String result = this.convertedLine;
        return result;

    }

    /**
     * 読み込んだ１行データを返す。
     *
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @return 現在の行番号
     */
    @Override
    public int getNowLineNumber() {

        final int result = this.nowLineNumber;
        return result;

    }

    /**
     * 出力ファイルの区切り文字を返す。<br>
     *
     * @since 0.2.2
     *
     * @return 出力ファイルの区切り文字
     */
    @Override
    public KmgDelimiterTypes getOutputDelimiter() {

        final KmgDelimiterTypes result = this.outputDelimiter;
        return result;

    }

    /**
     * 書き込み対象の行データのリストを返す。
     *
     * @since 0.2.0
     *
     * @return 書き込み対象の行データのリスト
     */
    @Override
    public List<List<String>> getRows() {

        final List<List<String>> result = this.rows;
        return result;

    }

    /**
     * 初期化する。
     *
     * @since 0.2.4
     *
     * @param inputPath
     *                   入力ファイルパス
     * @param outputPath
     *                   出力ファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path outputPath) throws KmgToolBaseMsgException {

        final boolean result = this.initialize(inputPath, outputPath, KmgDelimiterTypes.COMMA);
        return result;

    }

    /**
     * 初期化する。<br>
     * <p>
     * 出力ファイルの区切り文字を指定して初期化します。
     * </p>
     *
     * @since 0.2.4
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
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path outputPath, final KmgDelimiterTypes outputDelimiter)
        throws KmgToolBaseMsgException {

        boolean result;

        /* パラメータのチェック */
        if (outputDelimiter == null) {
            // NULLの場合

            final KmgToolBaseGenMsgTypes messageTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN07007;
            final Object[]           messageArgs  = {};
            throw new KmgToolBaseMsgException(messageTypes, messageArgs);

        }

        if (outputDelimiter == KmgDelimiterTypes.NONE) {
            // NONEの場合

            final KmgToolBaseGenMsgTypes messageTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN07008;
            final Object[]           messageArgs  = {};
            throw new KmgToolBaseMsgException(messageTypes, messageArgs);

        }

        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.outputDelimiter = outputDelimiter;

        /* データのクリア */
        this.clearProcessingData();

        this.clearRows();

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
     * @since 0.2.4
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean readOneLineOfData() throws KmgToolBaseMsgException {

        boolean result = false;

        try {

            // 1行読み込み
            this.lineOfDataRead = this.reader.readLine();

            this.nowLineNumber++;

        } catch (final IOException e) {

            final KmgToolBaseGenMsgTypes messageTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN07000;
            final Object[]           messageArgs  = {};
            throw new KmgToolBaseMsgException(messageTypes, messageArgs, e);

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
     * 出力ファイルに書き込む。<br>
     * <p>
     * 入力ファイルから指定の形式に変換して中間ファイルに出力する。
     * </p>
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean writeIntermediateFile() throws KmgToolBaseMsgException {

        boolean result = false;

        /* 書き込み処理 */
        for (final List<String> row : this.rows) {

            try {

                final String writeRow = this.outputDelimiter.join(row);
                this.writer.write(writeRow);
                this.writer.write(System.lineSeparator());

            } catch (final IOException e) {

                final KmgToolBaseGenMsgTypes messageTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN07001;
                final Object[]           messageArgs  = {
                    this.outputPath.toString()
                };
                throw new KmgToolBaseMsgException(messageTypes, messageArgs, e);

            }

        }

        /* バッファに残っているデータをファイルに書き込む */
        try {

            this.writer.flush();

        } catch (final IOException e) {

            final KmgToolBaseGenMsgTypes messageTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN07002;
            final Object[]           messageArgs  = {
                this.outputPath.toString()
            };
            throw new KmgToolBaseMsgException(messageTypes, messageArgs, e);

        }

        result = true;
        return result;

    }

    /**
     * 書き込み対象のデータの最後のリストにデータを追加する。
     *
     * @since 0.2.4
     *
     * @param data
     *             データ
     *
     * @return true：追加成功、false：追加失敗
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    protected boolean addRow(final String data) throws KmgToolBaseMsgException {

        boolean result;

        final List<String> row;

        try {

            row = this.rows.getLast();

        } catch (final NoSuchElementException e) {

            final KmgToolBaseGenMsgTypes messageTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN07005;
            final Object[]           messageArgs  = {};
            throw new KmgToolBaseMsgException(messageTypes, messageArgs, e);

        }
        result = row.add(data);

        return result;

    }

    /**
     * 行から対象に該当する部分を置換値に置換する。
     *
     * @since 0.2.0
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

        if (this.convertedLine == null) {

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
     * @since 0.2.0
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

            final KmgToolBaseLogMsgTypes logMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG07000;
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
     * @since 0.2.0
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

            final KmgToolBaseLogMsgTypes logMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG07001;
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
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("resource")
    private void openInputFile() throws KmgToolBaseMsgException {

        try {

            this.reader = Files.newBufferedReader(this.inputPath);

        } catch (final IOException e) {

            final KmgToolBaseGenMsgTypes messageTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN07003;
            final Object[]           messageArgs  = {
                this.inputPath.toString()
            };
            throw new KmgToolBaseMsgException(messageTypes, messageArgs, e);

        }

    }

    /**
     * 出力ファイルを開く
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("resource")
    private void openOutputFile() throws KmgToolBaseMsgException {

        try {

            this.writer = Files.newBufferedWriter(this.outputPath);

        } catch (final IOException e) {

            final KmgToolBaseGenMsgTypes messageTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN07004;
            final Object[]           messageArgs  = {
                this.outputPath.toString()
            };
            throw new KmgToolBaseMsgException(messageTypes, messageArgs, e);

        }

    }
}
