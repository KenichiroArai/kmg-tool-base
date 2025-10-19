package kmg.tool.base.fldcrt.application.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.fldcrt.application.logic.FieldCreationLogic;
import kmg.tool.base.fldcrt.application.service.FieldCreationService;
import kmg.tool.base.iito.domain.service.AbstractIitoProcessorService;

/**
 * フィールド作成サービス実装クラス
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@Service
public class FieldCreationServiceImpl extends AbstractIitoProcessorService implements FieldCreationService {

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
     * フィールド作成ロジック
     *
     * @since 0.2.0
     */
    @Autowired
    private FieldCreationLogic fieldCreationLogic;

    /**
     * デフォルトコンストラクタ
     *
     * @since 0.2.0
     */
    public FieldCreationServiceImpl() {

        this(LoggerFactory.getLogger(FieldCreationServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して初期化するコンストラクタ
     *
     * @since 0.2.0
     *
     * @param logger
     *               ロガー
     */
    protected FieldCreationServiceImpl(final Logger logger) {

        this.logger = logger;

    }

    /**
     * {@inheritDoc}
     *
     * @since 0.2.0
     */
    @Override
    protected boolean writeIntermediateFile() throws KmgToolMsgException {

        boolean result = false;

        try {

            /* フィールド作成ロジックの初期化 */
            this.fieldCreationLogic.initialize(this.getInputPath(), this.getIntermediatePath());

            /* 書き込み対象に行を追加する */
            this.fieldCreationLogic.addOneLineOfDataToRows();

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

            /* フィールド作成ロジックのクローズ処理 */
            this.closeFieldCreationLogic();

        }

        return result;

    }

    /**
     * データをクリアして次の行の準備をする
     *
     * @since 0.2.0
     */
    private void clearAndPrepareNextLine() {

        // 書き込み対象の行のリストをクリアする
        this.fieldCreationLogic.clearRows();

        // 処理中のデータをクリアする
        this.fieldCreationLogic.clearProcessingData();

        /* 書き込み対象に行を追加する */
        this.fieldCreationLogic.addOneLineOfDataToRows();

    }

    /**
     * フィールド作成ロジックをクローズする
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void closeFieldCreationLogic() throws KmgToolMsgException {

        try {

            this.fieldCreationLogic.close();

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN05003;
            final Object[]           genMsgArgs  = {};
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

        }

    }

    /**
     * カラムを処理する
     *
     * @since 0.2.0
     *
     * @return true：処理成功、false：処理スキップ
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean processColumns() throws KmgToolMsgException {

        boolean result = false;

        try {

            /* フィールドデータを変換する */
            final boolean isConverted = this.fieldCreationLogic.convertFields();

            if (!isConverted) {

                return false;

            }

            /* 各カラムを追加する */
            this.fieldCreationLogic.addCommentToRows();
            this.fieldCreationLogic.addFieldToRows();
            this.fieldCreationLogic.addTypeToRows();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG05001;
            final Object[]           logMsgArgs  = {};
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        result = true;
        return result;

    }

    /**
     * 1行データを読み込む
     *
     * @since 0.2.0
     *
     * @return true：読み込み成功、false：読み込み終了
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean readOneLineData() throws KmgToolMsgException {

        boolean result = false;

        try {

            result = this.fieldCreationLogic.readOneLineOfData();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG05002;
            final Object[]           logMsgArgs  = {};
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        return result;

    }

    /**
     * 中間ファイルに行を書き込む
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void writeIntermediateFileLine() throws KmgToolMsgException {

        try {

            this.fieldCreationLogic.writeIntermediateFile();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG05003;
            final Object[]           logMsgArgs  = {};
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);
            throw e;

        }

        final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG05004;
        final Object[]           logMsgArgs  = {
            this.fieldCreationLogic.getComment(),
        };
        final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }
}
