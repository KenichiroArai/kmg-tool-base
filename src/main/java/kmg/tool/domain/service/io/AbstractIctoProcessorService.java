package kmg.tool.domain.service.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kmg.core.infrastructure.utils.KmgPathUtils;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.domain.service.IctoProcessorService;
import kmg.tool.domain.service.io.dtc.DtcService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * 入力、CSV、テンプレート、出力の処理サービス抽象クラス<br>
 * 「Icto」→「InputCsvTemplateOutput」の略。
 */
public abstract class AbstractIctoProcessorService implements IctoProcessorService {

    /** 一時CSVファイルのサフィックスと拡張子 */
    private static final String TEMP_CSV_FILE_SUFFIX_EXTENSION = "Temp.csv";

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

    /** CSVファイルパス */
    private Path csvPath;

    /** テンプレートの動的変換サービス */
    @Autowired
    private DtcService dtcService;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     */
    public AbstractIctoProcessorService() {

        this(LoggerFactory.getLogger(AbstractIctoProcessorService.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *               ロガー
     */
    protected AbstractIctoProcessorService(final Logger logger) {

        this.logger = logger;

    }

    /**
     * CSVファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return CSVファイルパス
     */
    public Path getCsvPath() {

        final Path result = this.csvPath;
        return result;

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
        this.csvPath = this.createTempCsvFile();

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

        /* CSVファイルに書き込む */

        try {

            final KmgToolLogMessageTypes startLogMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG12004;
            final Object[]               startLogMsgArgs  = {};
            final String                 startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes,
                startLogMsgArgs);
            this.logger.debug(startLogMsg);

            result = this.writeCsvFile();

        } finally {

            final KmgToolLogMessageTypes endLogMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG12006;
            final Object[]               endLogMsgArgs  = {};
            final String                 endLogMsg      = this.messageSource.getLogMessage(endLogMsgTypes,
                endLogMsgArgs);
            this.logger.debug(endLogMsg);

        }

        /* テンプレートの動的変換サービスで出力ファイルに出力する */
        result = this.dtcService.initialize(this.getCsvPath(), this.templatePath, this.outputPath);
        result = this.dtcService.process();

        return result;

    }

    /**
     * 一時的なCSVファイルを作成する。
     *
     * @return CSVファイルパス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    protected Path createTempCsvFile() throws KmgToolMsgException {

        Path result = null;

        final String csvFileNameOnly = KmgPathUtils.getFileNameOnly(this.getInputPath());
        final String suffixExtension = AbstractIctoProcessorService.TEMP_CSV_FILE_SUFFIX_EXTENSION;

        try {

            result = Files.createTempFile(csvFileNameOnly, suffixExtension);
            result.toFile().deleteOnExit();

        } catch (final IOException e) {

            final KmgToolGenMessageTypes genMsgType = KmgToolGenMessageTypes.KMGTOOL_GEN12000;
            final Object[]               getMsgArgs = {
                csvFileNameOnly, suffixExtension,
            };
            throw new KmgToolMsgException(genMsgType, getMsgArgs, e);

        }

        return result;

    }

    /**
     * CSVファイルに書き込む。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    protected abstract boolean writeCsvFile() throws KmgToolMsgException;

}
