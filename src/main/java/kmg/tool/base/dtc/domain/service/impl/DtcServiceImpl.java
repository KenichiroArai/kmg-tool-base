package kmg.tool.base.dtc.domain.service.impl;

import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseLogMsgTypes;
import kmg.tool.base.dtc.domain.logic.DtcLogic;
import kmg.tool.base.dtc.domain.service.DtcService;

/**
 * テンプレートの動的変換サービス<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
@Service
public class DtcServiceImpl implements DtcService {

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
     * テンプレートの動的変換ロジック
     *
     * @since 0.2.0
     */
    @Autowired
    private DtcLogic dtcLogic;

    /**
     * 中間ファイルの区切り文字
     *
     * @since 0.2.2
     */
    private KmgDelimiterTypes intermediateDelimiter;

    /**
     * 入力ファイルパス
     *
     * @since 0.2.0
     */
    private Path inputPath;

    /**
     * テンプレートファイルパス
     *
     * @since 0.2.0
     */
    private Path templatePath;

    /**
     * 出力ファイルパス
     *
     * @since 0.2.0
     */
    private Path outputPath;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public DtcServiceImpl() {

        this(LoggerFactory.getLogger(DtcServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.4
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
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath)
        throws KmgToolBaseMsgException {

        final boolean result = true;

        this.inputPath = inputPath;
        this.templatePath = templatePath;
        this.outputPath = outputPath;

        return result;

    }

    /**
     * 初期化する<br>
     * <p>
     * 中間ファイルの区切り文字を指定して初期化します。
     * </p>
     *
     * @since 0.2.4
     *
     * @param inputPath
     *                              入力ファイルパス（中間ファイルパス）
     * @param templatePath
     *                              テンプレートファイルパス
     * @param outputPath
     *                              出力ファイルパス
     * @param intermediateDelimiter
     *                              中間ファイルの区切り文字
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath,
        final KmgDelimiterTypes intermediateDelimiter) throws KmgToolBaseMsgException {

        final boolean result = this.initialize(inputPath, templatePath, outputPath);
        this.intermediateDelimiter = intermediateDelimiter;
        return result;

    }

    /**
     * 処理する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    @Override
    public boolean process() throws KmgToolBaseMsgException {

        boolean result = false;

        final KmgToolBaseLogMsgTypes startLogMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG03000;
        final Object[]               startLogMsgArgs  = {};
        final String                 startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes,
            startLogMsgArgs);
        this.logger.debug(startLogMsg);

        try {

            /* テンプレートの動的変換ロジックの初期化 */
            if (this.intermediateDelimiter != null) {
                // 区切り文字が指定されている場合

                this.dtcLogic.initialize(this.getInputPath(), this.getTemplatePath(), this.getOutputPath(),
                    this.intermediateDelimiter);

            } else {
                // 区切り文字が指定されていない場合（デフォルト）

                this.dtcLogic.initialize(this.getInputPath(), this.getTemplatePath(), this.getOutputPath());

            }

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

        } finally {

            try {

                /* テンプレートの動的変換ロジックのクローズ処理 */
                this.closeDtcLogic();

            } finally {

                final KmgToolBaseLogMsgTypes endLogMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG03001;
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
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    private void closeDtcLogic() throws KmgToolBaseMsgException {

        try {

            this.dtcLogic.close();

        } catch (final IOException e) {

            final KmgToolBaseGenMsgTypes genMsgTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN03006;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolBaseMsgException(genMsgTypes, genMsgArgs, e);

        }

    }

}
