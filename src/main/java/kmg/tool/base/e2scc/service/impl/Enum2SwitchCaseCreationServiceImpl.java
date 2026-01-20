package kmg.tool.base.e2scc.service.impl;

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
import kmg.tool.base.e2scc.application.logic.Enum2SwitchCaseCreationLogic;
import kmg.tool.base.e2scc.service.Enum2SwitchCaseCreationService;
import kmg.tool.base.iito.domain.service.AbstractIitoProcessorService;

/**
 * <h2>列挙型からcase文作成サービス実装クラス</h2>
 * <p>
 * 列挙型の定義からswitch-case文を自動生成するためのサービス実装クラスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
@Service
public class Enum2SwitchCaseCreationServiceImpl extends AbstractIitoProcessorService
    implements Enum2SwitchCaseCreationService {

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
     * 列挙型からcase文作成ロジック
     *
     * @since 0.2.0
     */
    @Autowired
    private Enum2SwitchCaseCreationLogic enum2SwitchCaseMakingLogic;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public Enum2SwitchCaseCreationServiceImpl() {

        this(LoggerFactory.getLogger(Enum2SwitchCaseCreationServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param logger
     *               ロガー
     */
    protected Enum2SwitchCaseCreationServiceImpl(final Logger logger) {

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

        final KmgDelimiterTypes result = this.enum2SwitchCaseMakingLogic.getOutputDelimiter();
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
     * @since 0.2.0
     */
    private void clearAndPrepareNextLine() {

        // 書き込み対象の行のリストをクリアする
        this.enum2SwitchCaseMakingLogic.clearRows();

        // 処理中のデータをクリアする
        this.enum2SwitchCaseMakingLogic.clearProcessingData();

        /* 書き込み対象に行を追加する */
        this.enum2SwitchCaseMakingLogic.addOneLineOfDataToRows();

    }

    /**
     * 列挙型からcase文作成ロジックをクローズする。
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    private void closeEnum2SwitchCaseCreationLogic() throws KmgToolBaseMsgException {

        try {

            this.enum2SwitchCaseMakingLogic.close();

        } catch (final IOException e) {

            final KmgToolBaseGenMsgTypes genMsgTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN04002;
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

            // 列挙型定義から項目と項目名に変換する
            final boolean isConvertEnumDefinition = this.enum2SwitchCaseMakingLogic.convertEnumDefinition();

            if (!isConvertEnumDefinition) {

                return result;

            }

            // 項目を書き込み対象に追加する
            this.enum2SwitchCaseMakingLogic.addItemToRows();

            // 項目名を書き込み対象に追加する
            this.enum2SwitchCaseMakingLogic.addItemNameToRows();

        } catch (final KmgToolBaseMsgException e) {

            final KmgToolBaseLogMsgTypes logMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG04001;
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

            result = this.enum2SwitchCaseMakingLogic.readOneLineOfData();

        } catch (final KmgToolBaseMsgException e) {

            final KmgToolBaseLogMsgTypes logMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG04002;
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

            this.enum2SwitchCaseMakingLogic.writeIntermediateFile();

        } catch (final KmgToolBaseMsgException e) {

            final KmgToolBaseLogMsgTypes logMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG04003;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);
            throw e;

        }

        final KmgToolBaseLogMsgTypes logMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG04004;
        final Object[]               logMsgArgs  = {
            this.enum2SwitchCaseMakingLogic.getItem(), this.enum2SwitchCaseMakingLogic.getItemName(),
        };
        final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }
}
