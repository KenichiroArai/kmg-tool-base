package kmg.tool.e2scc.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.e2scc.application.logic.Enum2SwitchCaseCreationLogic;
import kmg.tool.e2scc.service.Enum2SwitchCaseCreationService;
import kmg.tool.iito.domain.service.AbstractIitoProcessorService;

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
public class Enum2SwitchCaseCreationServiceImpl extends AbstractIitoProcessorService
    implements Enum2SwitchCaseCreationService {

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
    private Enum2SwitchCaseCreationLogic enum2SwitchCaseMakingLogic;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 1.0.0
     */
    public Enum2SwitchCaseCreationServiceImpl() {

        this(LoggerFactory.getLogger(Enum2SwitchCaseCreationServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 1.0.0
     *
     * @param logger
     *               ロガー
     */
    protected Enum2SwitchCaseCreationServiceImpl(final Logger logger) {

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

            /* 列挙型からcase文作成ロジックの初期化 */
            this.enum2SwitchCaseMakingLogic.initialize(this.getInputPath(), this.getIntermediatePath());

            /* 書き込み対象に行を追加する */
            this.enum2SwitchCaseMakingLogic.addOneLineOfDataToRows();

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

            /* 列挙型からcase文作成ロジックのクローズ処理 */
            this.closeEnum2SwitchCaseCreationLogic();

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
            this.enum2SwitchCaseMakingLogic.clearRows();

            // 処理中のデータをクリアする
            this.enum2SwitchCaseMakingLogic.clearProcessingData();

            /* 書き込み対象に行を追加する */
            this.enum2SwitchCaseMakingLogic.addOneLineOfDataToRows();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG32005;
            final Object[]           logMsgArgs  = {};
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

    }

    /**
     * 列挙型からcase文作成ロジックをクローズする。
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void closeEnum2SwitchCaseCreationLogic() throws KmgToolMsgException {

        try {

            this.enum2SwitchCaseMakingLogic.close();

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN04002;
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

            // 列挙型定義から項目と項目名に変換する
            final boolean isConvertEnumDefinition = this.enum2SwitchCaseMakingLogic.convertEnumDefinition();

            if (!isConvertEnumDefinition) {

                return result;

            }

            // 項目を書き込み対象に追加する
            this.enum2SwitchCaseMakingLogic.addItemToRows();

            // 項目名を書き込み対象に追加する
            this.enum2SwitchCaseMakingLogic.addItemNameToRows();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG31006;
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

            result = this.enum2SwitchCaseMakingLogic.readOneLineOfData();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG31007;
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

            this.enum2SwitchCaseMakingLogic.writeIntermediateFile();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG31010;
            final Object[]           logMsgArgs  = {};
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);
            throw e;

        }

        final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG31011;
        final Object[]           logMsgArgs  = {
            this.enum2SwitchCaseMakingLogic.getItem(), this.enum2SwitchCaseMakingLogic.getItemName(),
        };
        final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }
}
