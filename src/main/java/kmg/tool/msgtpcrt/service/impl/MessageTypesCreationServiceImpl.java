package kmg.tool.msgtpcrt.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.iito.domain.service.AbstractIitoProcessorService;
import kmg.tool.msgtpcrt.application.logic.MessageTypesCreationLogic;
import kmg.tool.msgtpcrt.service.MessageTypesCreationService;

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
public class MessageTypesCreationServiceImpl extends AbstractIitoProcessorService
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
     * 中間ファイルに書き込む。<br>
     * <p>
     * 入力ファイルから中間形式に変換して中間ファイルに出力する。
     * </p>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    protected boolean writeIntermediateFile() throws KmgToolMsgException {

        boolean result = false;

        try {

            /* メッセージの種類作成ロジックを初期化 */
            this.messageTypesCreationLogic.initialize(this.getInputPath(), this.getIntermediatePath());

            /* 書き込み対象に行を追加する */
            this.messageTypesCreationLogic.addOneLineOfDataToRows();

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

                /* 中間ファイルに行を書き込む */
                this.writeIntermediateFileLine();

                /* クリア処理 */
                this.clearAndPrepareNextLine();

            } while (true);

            result = true;

        } finally {

            /* メッセージの種類作成ロジックをクローズ処理 */
            this.closeMessageTypesCreationLogic();

        }

        return result;

    }

    /**
     * データをクリアして次の行の準備をする。
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void clearAndPrepareNextLine() throws KmgToolMsgException {

        try {

            // 書き込み対象の行のリストをクリアする
            this.messageTypesCreationLogic.clearRows();

            // 処理中のデータをクリアする
            this.messageTypesCreationLogic.clearProcessingData();

            /* 書き込み対象に行を追加する */
            this.messageTypesCreationLogic.addOneLineOfDataToRows();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG14000;
            final Object[]           logMsgArgs  = {};
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

    }

    /**
     * メッセージの種類作成ロジックをクローズする。
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void closeMessageTypesCreationLogic() throws KmgToolMsgException {

        try {

            this.messageTypesCreationLogic.close();

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN14003;
            final Object[]           genMsgArgs  = {};
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

        }

    }

    /**
     * カラムを処理する。
     *
     * @return true：処理成功、false：処理スキップ
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean processColumns() throws KmgToolMsgException {

        boolean result = false;

        try {

            // メッセージの種類定義から項目と項目名に変換する
            final boolean isConvertMessageTypesDefinition
                = this.messageTypesCreationLogic.convertMessageTypesDefinition();

            if (!isConvertMessageTypesDefinition) {

                return result;

            }

            // 項目を書き込み対象に追加する
            this.messageTypesCreationLogic.addItemToRows();

            // 項目名を書き込み対象に追加する
            this.messageTypesCreationLogic.addItemNameToRows();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG14001;
            final Object[]           logMsgArgs  = {};
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
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
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean readOneLineData() throws KmgToolMsgException {

        boolean result = false;

        try {

            result = this.messageTypesCreationLogic.readOneLineOfData();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG14002;
            final Object[]           logMsgArgs  = {};
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        return result;

    }

    /**
     * 中間ファイルに行を書き込む。
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void writeIntermediateFileLine() throws KmgToolMsgException {

        try {

            this.messageTypesCreationLogic.writeIntermediateFile();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG14003;
            final Object[]           logMsgArgs  = {};
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG14004;
        final Object[]           logMsgArgs  = {
            this.messageTypesCreationLogic.getItem(), this.messageTypesCreationLogic.getItemName(),
        };
        final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }

}
