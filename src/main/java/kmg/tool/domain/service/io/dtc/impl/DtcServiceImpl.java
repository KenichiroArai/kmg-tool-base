package kmg.tool.domain.service.io.dtc.impl;

import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.foundation.infrastructure.context.KmgMessageSource;
import kmg.tool.domain.logic.io.dtc.DtcLogic;
import kmg.tool.domain.service.io.dtc.DtcService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * テンプレートの動的変換サービス<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 *
 * @author KenichiroArai
 */
@Service
public class DtcServiceImpl implements DtcService {

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

    /** テンプレートの動的変換ロジック */
    @Autowired
    private DtcLogic dtcLogic;

    /** 入力ファイルパス */
    private Path inputPath;

    /** テンプレートファイルパス */
    private Path templatePath;

    /** 出力ファイルパス */
    private Path outputPath;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     */
    public DtcServiceImpl() {

        this(LoggerFactory.getLogger(DtcServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *               ロガー
     */
    protected DtcServiceImpl(final Logger logger) {

        this.logger = logger;

    }

    /**
     * 入力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 入力ファイルパス
     */
    @Override
    public Path getInputPath() {

        final Path result = this.inputPath;
        return result;

    }

    /**
     * 出力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 出力ファイルパス
     */
    @Override
    public Path getOutputPath() {

        final Path result = this.outputPath;
        return result;

    }

    /**
     * テンプレートファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return テンプレートファイルパス
     */
    @Override
    public Path getTemplatePath() {

        final Path result = this.templatePath;
        return result;

    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param inputPath
     *                     入力ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     * @param outputPath
     *                     出力ファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath)
        throws KmgToolException {

        final boolean result = true;

        this.inputPath = inputPath;
        this.templatePath = templatePath;
        this.outputPath = outputPath;

        return result;

    }

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean process() throws KmgToolException {

        boolean result = false;

        final KmgToolLogMessageTypes startLogMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG12000;
        final Object[]               startLogMsgArgs  = {};
        final String                 startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes,
            startLogMsgArgs);
        this.logger.debug(startLogMsg);

        try {

            /* テンプレートの動的変換ロジックの初期化 */
            this.dtcLogic.initialize(this.getInputPath(), this.getTemplatePath(), this.getOutputPath());

            /* テンプレートの読み込む */
            this.dtcLogic.loadTemplate();

            /* 入力ファイルの処理と出力 */

            do {

                /* 1行データを読み込む */
                final boolean isRead = this.readOneLineData();

                if (!isRead) {

                    break;

                }

                /* 入力ファイルからテンプレートに基づいて変換する */
                this.dtcLogic.applyTemplateToInputFile();

                /* 出力バッファに追加する */
                this.dtcLogic.addOutputBufferContent();

                /* 出力バッファを書き込む */
                this.dtcLogic.writeOutputBuffer();

                /* 出力バッファをクリアする */
                this.dtcLogic.clearOutputBufferContent();

            } while (true);

            result = true;

        } catch (final KmgToolException e) {

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG12001;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        } finally {

            try {

                /* テンプレートの動的変換ロジックのクローズ処理 */
                this.closeDtcLogic();

            } finally {

                final KmgToolLogMessageTypes endLogMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG12002;
                final Object[]               endLogMsgArgs  = {};
                final String                 endLogMsg      = this.messageSource.getLogMessage(endLogMsgTypes,
                    endLogMsgArgs);
                this.logger.debug(endLogMsg);

            }

        }

        return result;

    }

    /**
     * テンプレートの動的変換ロジックをクローズする。
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private void closeDtcLogic() throws KmgToolException {

        try {

            this.dtcLogic.close();

        } catch (final IOException e) {

            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.KMGTOOL_GEN12002;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

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

            result = this.dtcLogic.readOneLineOfData();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/16 ログ
            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.NONE;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        return result;

    }
}
