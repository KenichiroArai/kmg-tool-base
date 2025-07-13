package kmg.tool.iito.domain.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kmg.core.infrastructure.utils.KmgPathUtils;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.dtc.domain.service.DtcService;

/**
 * 入力、中間、テンプレート、出力の処理サービス抽象クラス<br>
 * 「Iito」→「InputIntermediateTemplateOutput」の略。
 */
public abstract class AbstractIitoProcessorService implements IitoProcessorService {

    /** 一時中間ファイルのサフィックスと拡張子 */
    private static final String TEMP_INTERMEDIATE_FILE_SUFFIX_EXTENSION = "Temp.tmp"; //$NON-NLS-1$

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

    /** 入力ファイルパス */
    private Path inputPath;

    /** テンプレートファイルパス */
    private Path templatePath;

    /** 出力ファイルパス */
    private Path outputPath;

    /** 中間ファイルパス */
    private Path intermediatePath;

    /** テンプレートの動的変換サービス */
    @Autowired
    private DtcService dtcService;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     */
    public AbstractIitoProcessorService() {

        this(LoggerFactory.getLogger(AbstractIitoProcessorService.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *               ロガー
     */
    protected AbstractIitoProcessorService(final Logger logger) {

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
     * 中間ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 中間ファイルパス
     */
    public Path getIntermediatePath() {

        final Path result = this.intermediatePath;
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
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                     入力ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     * @param outputPath
     *                     出力ファイルパス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath)
        throws KmgToolMsgException {

        boolean result = false;

        this.inputPath = inputPath;
        this.templatePath = templatePath;
        this.outputPath = outputPath;

        // 一時ファイルの作成
        this.intermediatePath = this.createTempntermediateFile();

        result = true;
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

        /* 中間ファイルに書き込む */

        try {

            final KmgToolLogMsgTypes startLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG12004;
            final Object[]           startLogMsgArgs  = {};
            final String             startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes,
                startLogMsgArgs);
            this.logger.debug(startLogMsg);

            result = this.writeIntermediateFile();

        } finally {

            final KmgToolLogMsgTypes endLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG12006;
            final Object[]           endLogMsgArgs  = {};
            final String             endLogMsg      = this.messageSource.getLogMessage(endLogMsgTypes, endLogMsgArgs);
            this.logger.debug(endLogMsg);

        }

        /* テンプレートの動的変換サービスで出力ファイルに出力する */
        result = this.dtcService.initialize(this.getIntermediatePath(), this.templatePath, this.outputPath);
        result = this.dtcService.process();

        return result;

    }

    /**
     * 一時的な中間ファイルを作成する。
     *
     * @return 中間ファイルパス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    protected Path createTempntermediateFile() throws KmgToolMsgException {

        Path result = null;

        final String intermediateFileNameOnly = KmgPathUtils.getFileNameOnly(this.getInputPath());
        final String suffixExtension          = AbstractIitoProcessorService.TEMP_INTERMEDIATE_FILE_SUFFIX_EXTENSION;

        try {

            result = Files.createTempFile(intermediateFileNameOnly, suffixExtension);
            result.toFile().deleteOnExit();

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genMsgType = KmgToolGenMsgTypes.KMGTOOL_GEN07006;
            final Object[]           getMsgArgs = {
                intermediateFileNameOnly, suffixExtension,
            };
            throw new KmgToolMsgException(genMsgType, getMsgArgs, e);

        }

        return result;

    }

    /**
     * 中間ファイルに書き込む。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    protected abstract boolean writeIntermediateFile() throws KmgToolMsgException;

}
