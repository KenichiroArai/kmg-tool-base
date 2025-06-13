package kmg.tool.domain.service.io.dtc.impl;

import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.domain.logic.two2one.dtc.DtcLogic;
import kmg.tool.domain.service.io.dtc.DtcService;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;
import kmg.tool.infrastructure.type.msg.KmgToolLogMsgTypes;

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
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath)
        throws KmgToolMsgException {

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
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean process() throws KmgToolMsgException {

        boolean result = false;

        final KmgToolLogMsgTypes startLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG12000;
        final Object[]           startLogMsgArgs  = {};
        final String             startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes, startLogMsgArgs);
        this.logger.debug(startLogMsg);

        try {

            /* テンプレートの動的変換ロジックの初期化 */
            this.dtcLogic.initialize(this.getInputPath(), this.getTemplatePath(), this.getOutputPath());

            /* テンプレートの読み込む */
            this.dtcLogic.loadTemplate();

            /* 入力ファイルの処理と出力 */

            do {

                /* 1行データを読み込む */
                final boolean isRead = this.dtcLogic.readOneLineOfData();

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

        } catch (final KmgToolMsgException e) {

            throw e;

        } finally {

            try {

                /* テンプレートの動的変換ロジックのクローズ処理 */
                this.closeDtcLogic();

            } finally {

                final KmgToolLogMsgTypes endLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG12002;
                final Object[]           endLogMsgArgs  = {};
                final String             endLogMsg      = this.messageSource.getLogMessage(endLogMsgTypes,
                    endLogMsgArgs);
                this.logger.debug(endLogMsg);

            }

        }

        return result;

    }

    /**
     * テンプレートの動的変換ロジックをクローズする。
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void closeDtcLogic() throws KmgToolMsgException {

        try {

            this.dtcLogic.close();

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN12002;
            final Object[]           genMsgArgs  = {};
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

        }

    }

}
