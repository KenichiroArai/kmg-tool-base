package kmg.tool.base.msgtpcrt.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseLogMsgTypes;
import kmg.tool.base.iito.domain.service.AbstractIitoProcessorService;
import kmg.tool.base.msgtpcrt.application.logic.MessageTypesCreationLogic;
import kmg.tool.base.msgtpcrt.service.MessageTypesCreationService;

/**
 * <h2>メッセージの種類作成サービス実装クラス</h2>
 * <p>
 * メッセージの種類を定義するYMLファイルを自動生成するためのサービス実装クラスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
@Service
public class MessageTypesCreationServiceImpl extends AbstractIitoProcessorService
    implements MessageTypesCreationService {

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
     * メッセージの種類作成ロジック
     *
     * @since 0.2.0
     */
    @Autowired
    private MessageTypesCreationLogic messageTypesCreationLogic;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public MessageTypesCreationServiceImpl() {

        this(LoggerFactory.getLogger(MessageTypesCreationServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param logger
     *               ロガー
     */
    protected MessageTypesCreationServiceImpl(final Logger logger) {

        this.logger = logger;

    }

    /**
     * 中間ファイルの区切り文字を返す。<br>
     * <p>
     * AbstractIitoProcessorServiceのgetIntermediateDelimiter()を実装します。
     * </p>
     *
     * @since 0.2.4
     *
     * @return 中間ファイルの区切り文字
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    @Override
    protected KmgDelimiterTypes getIntermediateDelimiter() throws KmgToolBaseMsgException {

        final KmgDelimiterTypes result = this.messageTypesCreationLogic.getOutputDelimiter();
        return result;

    }

    /**
     * 中間ファイルに書き込む。<br>
     * <p>
     * 入力ファイルから中間形式に変換して中間ファイルに出力する。
     * </p>
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    @Override
    protected boolean writeIntermediateFile() throws KmgToolBaseMsgException {

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
     * @since 0.2.0
     */
    private void clearAndPrepareNextLine() {

        // 書き込み対象の行のリストをクリアする
        this.messageTypesCreationLogic.clearRows();

        // 処理中のデータをクリアする
        this.messageTypesCreationLogic.clearProcessingData();

        /* 書き込み対象に行を追加する */
        this.messageTypesCreationLogic.addOneLineOfDataToRows();

    }

    /**
     * メッセージの種類作成ロジックをクローズする。
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    private void closeMessageTypesCreationLogic() throws KmgToolBaseMsgException {

        try {

            this.messageTypesCreationLogic.close();

        } catch (final IOException e) {

            final KmgToolBaseGenMsgTypes genMsgTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN14003;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolBaseMsgException(genMsgTypes, genMsgArgs, e);

        }

    }

    /**
     * カラムを処理する。
     *
     * @since 0.2.4
     *
     * @return true：処理成功、false：処理スキップ
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    private boolean processColumns() throws KmgToolBaseMsgException {

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

        } catch (final KmgToolBaseMsgException e) {

            final KmgToolBaseLogMsgTypes logMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG14001;
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
     * @since 0.2.4
     *
     * @return true：読み込み成功、false：読み込み終了
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    private boolean readOneLineData() throws KmgToolBaseMsgException {

        boolean result = false;

        try {

            result = this.messageTypesCreationLogic.readOneLineOfData();

        } catch (final KmgToolBaseMsgException e) {

            final KmgToolBaseLogMsgTypes logMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG14002;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        return result;

    }

    /**
     * 中間ファイルに行を書き込む。
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    private void writeIntermediateFileLine() throws KmgToolBaseMsgException {

        try {

            this.messageTypesCreationLogic.writeIntermediateFile();

        } catch (final KmgToolBaseMsgException e) {

            final KmgToolBaseLogMsgTypes logMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG14003;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        final KmgToolBaseLogMsgTypes logMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG14004;
        final Object[]               logMsgArgs  = {
            this.messageTypesCreationLogic.getItem(), this.messageTypesCreationLogic.getItemName(),
        };
        final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }

}
