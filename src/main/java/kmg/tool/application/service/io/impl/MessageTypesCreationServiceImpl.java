package kmg.tool.application.service.io.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.application.logic.io.MessageTypesCreationLogic;
import kmg.tool.application.service.io.MessageTypesCreationService;
import kmg.tool.domain.service.io.AbstractIctoProcessorService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * <h2>メッセージの種類作成サービス実装クラス</h2>
 * <p>
 * メッセージの種類を定義するYMLファイルを自動生成するためのサービス実装クラスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Service
public class MessageTypesCreationServiceImpl extends AbstractIctoProcessorService
    implements MessageTypesCreationService {

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

    /** メッセージの種類作成ロジック */
    @Autowired
    private MessageTypesCreationLogic messageTypesCreationLogic;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 1.0.0
     */
    public MessageTypesCreationServiceImpl() {

        this(LoggerFactory.getLogger(MessageTypesCreationServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 1.0.0
     *
     * @param logger
     *               ロガー
     */
    protected MessageTypesCreationServiceImpl(final Logger logger) {

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

        final KmgToolLogMessageTypes startLogMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31010;
        final Object[]               startLogMsgArgs  = {};
        final String                 startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes,
            startLogMsgArgs);
        this.logger.debug(startLogMsg);

        try {

            /* メッセージの種類作成ロジックを初期化 */
            this.messageTypesCreationLogic.initialize(this.getInputPath(), this.getCsvPath());

            /* 書き込み対象に行を追加する */
            this.messageTypesCreationLogic.addOneLineOfDataToCsvRows();

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

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31011;
            final Object[]               logMsgArgs  = {
                this.getOutputPath().toString(),
            };
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        } finally {

            try {

                /* メッセージの種類作成ロジックをクローズ処理 */
                this.closeMessageTypesCreationLogic();

            } finally {

                final KmgToolLogMessageTypes endLogMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31012;
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
            this.messageTypesCreationLogic.clearCsvRows();

            // 処理中のデータをクリアする
            this.messageTypesCreationLogic.clearProcessingData();

            /* 書き込み対象に行を追加する */
            this.messageTypesCreationLogic.addOneLineOfDataToCsvRows();

        } catch (final KmgToolException e) {

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31013;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

    }

    /**
     * メッセージの種類作成ロジックをクローズする。
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private void closeMessageTypesCreationLogic() throws KmgToolException {

        try {

            this.messageTypesCreationLogic.close();

        } catch (final IOException e) {

            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.KMGTOOL_GEN31010;
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

            // メッセージの種類定義から項目と項目名に変換する
            final boolean isConvertMessageTypesDefinition
                = this.messageTypesCreationLogic.convertMessageTypesDefinition();

            if (!isConvertMessageTypesDefinition) {

                return result;

            }

            // 項目を書き込み対象に追加する
            this.messageTypesCreationLogic.addItemToCsvRows();

            // 項目名を書き込み対象に追加する
            this.messageTypesCreationLogic.addItemNameToCsvRows();

        } catch (final KmgToolException e) {

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31014;
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

            result = this.messageTypesCreationLogic.readOneLineOfData();

        } catch (final KmgToolException e) {

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31015;
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

            this.messageTypesCreationLogic.writeCsvFile();

        } catch (final KmgToolException e) {

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31016;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31017;
        final Object[]               logMsgArgs  = {
            this.messageTypesCreationLogic.getItem(), this.messageTypesCreationLogic.getItemName(),
        };
        final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }

}
