package kmg.tool.application.logic.io.impl;

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
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.application.logic.io.MessageTypesCreationLogic;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * <h2>メッセージの種類作成ロジック実装クラス</h2>
 * <p>
 * メッセージの種類を定義するYMLファイルを自動生成するためのロジック実装クラスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Service
public class MessageTypesCreationLogicImpl implements MessageTypesCreationLogic {

    /**
     * ロガー
     *
     * @since 1.0.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @since 1.0.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /** 入力ファイルパス */
    private Path inputPath;

    /** 出力ファイルパス */
    private Path outputPath;

    /** 入力ファイルのBufferedReader */
    private BufferedReader reader;

    /** 出力ファイルのBufferedWriter */
    private BufferedWriter writer;

    /** 読み込んだ１行データ */
    private String lineOfDataRead;

    /** 変換後の1行データ */
    private String convertedLine;

    /** 項目 */
    private String item;

    /** 項目名 */
    private String itemName;

    /** 書き込み対象のCSVデータのリスト */
    private final List<List<String>> csvRows;

    /**
     * デフォルトコンストラクタ
     */
    public MessageTypesCreationLogicImpl() {

        this(LoggerFactory.getLogger(MessageTypesCreationLogicImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 1.0.0
     *
     * @param logger
     *               ロガー
     */
    protected MessageTypesCreationLogicImpl(final Logger logger) {

        this.logger = logger;
        this.csvRows = new ArrayList<>();

    }

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean addItemNameToCsvRows() throws KmgToolException {

        boolean result = false;

        if (this.itemName == null) {

            // TODO KenichiroArai 2025/03/18 メッセージ
            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs  = {};
            throw new KmgToolException(messageTypes, messageArgs);

        }

        final List<String> row = this.csvRows.getLast();
        row.add(this.itemName);
        result = true;

        return result;

    }

    /**
     * 項目を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean addItemToCsvRows() throws KmgToolException {

        boolean result = false;

        if (this.item == null) {

            // TODO KenichiroArai 2025/03/18 メッセージ
            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs  = {};
            throw new KmgToolException(messageTypes, messageArgs);

        }

        final List<String> row = this.csvRows.getLast();
        row.add(this.item);
        result = true;

        return result;

    }

    /**
     * 書き込み対象に行を追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean addOneLineOfDataToCsvRows() throws KmgToolException {

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
        this.item = null;
        this.itemName = null;

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
     * メッセージの種類定義から項目と項目名に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean convertMessageTypesDefinition() throws KmgToolException {

        boolean result = false;

        // TODO KenichiroArai 2025/03/19 ハードコード

        // 項目と項目名に分ける
        final String[] inputDatas = KmgDelimiterTypes.HALF_EQUAL.split(this.convertedLine, 2);

        // 項目と項目名に分かれないか
        if (inputDatas.length > 2) {
            // 分かれない場合

            // TODO KenichiroArai 2025/03/19 例外処理
            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs  = {};
            throw new KmgToolException(messageTypes, messageArgs);

        }

        // 項目と項目名に設定
        // TODO KenichiroArai 2025/03/19 ハードコード
        this.item = inputDatas[0]; // 項目
        this.itemName = inputDatas[1]; // 項目名

        result = true;
        return result;

    }

    /**
     * 変換後の1行データを返す。
     *
     * @return 変換後の1行データ
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
     * 項目を返す。
     *
     * @return 項目
     */
    @Override
    public String getItem() {

        final String result = this.item;
        return result;

    }

    /**
     * 項目名を返す。
     *
     * @return 項目名
     */
    @Override
    public String getItemName() {

        final String result = this.itemName;
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
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path outputPath) throws KmgToolException {

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
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean readOneLineOfData() throws KmgToolException {

        boolean result = false;

        try {

            this.lineOfDataRead = this.reader.readLine();

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/18 メッセージ
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

        this.convertedLine = this.lineOfDataRead;

        // 読み込んだデータがないか
        if (this.lineOfDataRead == null) {
            // 読み込んだデータがない場合

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
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean writeCsvFile() throws KmgToolException {

        boolean result = false;

        /* CSVの書き込み処理 */
        for (final List<String> csvRow : this.csvRows) {

            try {

                final String csvLine = KmgDelimiterTypes.COMMA.join(csvRow);
                this.writer.write(csvLine);
                this.writer.write(System.lineSeparator());

            } catch (final IOException e) {

                // TODO KenichiroArai 2025/03/19 メッセージ
                final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
                final Object[]               messageArgs  = {
                    this.outputPath.toString()
                };
                throw new KmgToolException(messageTypes, messageArgs, e);

            }

        }

        /* バッファに残っているデータをファイルに書き込む */
        try {

            this.writer.flush();

        } catch (final IOException e) {

            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs  = {
                this.outputPath.toString()
            };
            // TODO KenichiroArai 2025/03/19 メッセージ
            throw new KmgToolException(messageTypes, messageArgs, e);

        }

        result = true;
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

            // TODO KenichiroArai 2025/03/19 ログ
            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.NONE;
            final Object[]               logMsgArgs  = {
                this.inputPath.toString(),
            };
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
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

            // TODO KenichiroArai 2025/03/19 ログ
            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.NONE;
            final Object[]               logMsgArgs  = {
                this.outputPath.toString(),
            };
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

    }

    /**
     * 入力ファイルを開く
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("resource")
    private void openInputFile() throws KmgToolException {

        try {

            this.reader = Files.newBufferedReader(this.inputPath);

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/19 例外処理
            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs  = {
                this.inputPath.toString()
            };
            throw new KmgToolException(messageTypes, messageArgs, e);

        }

    }

    /**
     * 出力ファイルを開く
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("resource")
    private void openOutputFile() throws KmgToolException {

        try {

            this.writer = Files.newBufferedWriter(this.outputPath);

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/19 例外処理
            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs  = {
                this.outputPath.toString()
            };
            throw new KmgToolException(messageTypes, messageArgs, e);

        }

    }
}
