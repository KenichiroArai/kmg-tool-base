package kmg.tool.application.service.io.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.foundation.infrastructure.context.KmgMessageSource;
import kmg.tool.application.logic.io.Enum2SwitchCaseMakingLogic;
import kmg.tool.application.service.io.Enum2SwitchCaseMakingService;
import kmg.tool.domain.service.io.AbstractIctoProcessorService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * <h2>列挙型からcase文作成サービス実装クラス</h2>
 * <p>
 * 列挙型の定義からswitch-case文を自動生成するためのサービス実装クラスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Service
public class Enum2SwitchCaseMakingServiceImpl extends AbstractIctoProcessorService
    implements Enum2SwitchCaseMakingService {

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

    /** 列挙型からcase文作成ロジック */
    @Autowired
    private Enum2SwitchCaseMakingLogic enum2SwitchCaseMakingLogic;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 1.0.0
     */
    public Enum2SwitchCaseMakingServiceImpl() {

        this(LoggerFactory.getLogger(Enum2SwitchCaseMakingServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 1.0.0
     *
     * @param logger
     *               ロガー
     */
    protected Enum2SwitchCaseMakingServiceImpl(final Logger logger) {

        this.logger = logger;

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
    protected boolean writeCsvFile() throws KmgToolException {

        boolean result = false;

        // TODO KenichiroArai 2025/03/18 ログ - CSVファイルに書き込む処理を開始します。
        final KmgToolLogMessageTypes startLogMsgTypes = KmgToolLogMessageTypes.NONE;
        final Object[]               startLogMsgArgs  = {};
        final String                 startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes,
            startLogMsgArgs);
        this.logger.debug(startLogMsg);

        try {

            /* 列挙型からcase文作成ロジックの初期化 */
            this.enum2SwitchCaseMakingLogic.initialize(this.getInputPath(), this.getCsvPath());

            /* 書き込み対象に行を追加する */
            this.enum2SwitchCaseMakingLogic.addOneLineOfDataToCsvRows();

            do {

                /* 1行データを読み込む */
                final boolean isRead = this.readOneLineData();

                if (!isRead) {

                    break;

                }

                /* カラムを追加する */
                final boolean isProcessed = this.processColumns();

                if (!isProcessed) {

                    continue;

                }

                /* CSVファイルに行を書き込む */
                this.writeCsvFileLine();

                /* クリア処理 */
                this.clearAndPrepareNextLine();

            } while (true);

            result = true;

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/18 ログ - CSVファイルの書き込み処理中にエラーが発生しました。出力ファイルパス:[{0}]
            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.NONE;
            final Object[]               logMsgArgs  = {
                this.getOutputPath().toString(),
            };
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        } finally {

            try {

                /* 列挙型からcase文作成ロジックのクローズ処理 */
                this.closeEnum2SwitchCaseMakingLogic();

            } finally {

                // TODO KenichiroArai 2025/03/18 ログ - CSVファイルに書き込む処理を終了します。
                final KmgToolLogMessageTypes endLogMsgTypes = KmgToolLogMessageTypes.NONE;
                final Object[]               endLogMsgArgs  = {};
                final String                 endLogMsg      = this.messageSource.getLogMessage(endLogMsgTypes,
                    endLogMsgArgs);
                this.logger.debug(endLogMsg);

            }

        }

        return result;

    }

    /**
     * データをクリアして次の行の準備をする。
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private void clearAndPrepareNextLine() throws KmgToolException {

        try {

            // 書き込み対象のCSVデータのリストをクリアする
            this.enum2SwitchCaseMakingLogic.clearCsvRows();

            // 処理中のデータをクリアする
            this.enum2SwitchCaseMakingLogic.clearProcessingData();

            /* 書き込み対象に行を追加する */
            this.enum2SwitchCaseMakingLogic.addOneLineOfDataToCsvRows();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/18 ログ - クリア処理中にエラーが発生しました。
            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.NONE;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

    }

    /**
     * 列挙型からcase文作成ロジックをクローズする。
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private void closeEnum2SwitchCaseMakingLogic() throws KmgToolException {

        try {

            this.enum2SwitchCaseMakingLogic.close();

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/18 ログ - 列挙型からcase文作成ロジックをクローズ中にエラーが発生しました。
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

    }

    /**
     * カラムを処理する。
     *
     * @return true：処理成功、false：処理スキップ
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private boolean processColumns() throws KmgToolException {

        boolean result = false;

        try {

            // 列挙型定義から項目と項目名に変換する
            final boolean isConvertEnumDefinition = this.enum2SwitchCaseMakingLogic.convertEnumDefinition();

            if (!isConvertEnumDefinition) {

                return result;

            }

            // 項目を書き込み対象に追加する
            this.enum2SwitchCaseMakingLogic.addItemToCsvRows();

            // 項目名を書き込み対象に追加する
            this.enum2SwitchCaseMakingLogic.addItemNameToCsvRows();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/18 ログ - カラムの追加中にエラーが発生しました。
            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.NONE;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        result = true;
        return result;

    }

    /**
     * 1行データを読み込む。
     *
     * @return true：読み込み成功、false：読み込み終了
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private boolean readOneLineData() throws KmgToolException {

        boolean result = false;

        try {

            result = this.enum2SwitchCaseMakingLogic.readOneLineOfData();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/18 ログ - 1行データの読み込み中にエラーが発生しました。
            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.NONE;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        return result;

    }

    /**
     * CSVファイルに行を書き込む。
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private void writeCsvFileLine() throws KmgToolException {

        try {

            this.enum2SwitchCaseMakingLogic.writeCsvFile();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/18 ログ - CSVファイルに書き込み中にエラーが発生しました。
            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.NONE;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);
            throw e;

        }

        // TODO KenichiroArai 2025/03/18 ログ - CSVファイルに書き込み完了。名称=[{0}], 項目名=[{1}]
        final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.NONE;
        final Object[]               logMsgArgs  = {
            this.enum2SwitchCaseMakingLogic.getItem(), this.enum2SwitchCaseMakingLogic.getItemName(),
        };
        final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }
}
