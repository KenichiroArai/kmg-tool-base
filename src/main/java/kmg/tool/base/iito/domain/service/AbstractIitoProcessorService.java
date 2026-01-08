package kmg.tool.base.iito.domain.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.core.infrastructure.utils.KmgPathUtils;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.dtc.domain.service.DtcService;

/**
 * 入力、中間、テンプレート、出力の処理サービス抽象クラス<br>
 * 「Iito」→「InputIntermediateTemplateOutput」の略。
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.2
 */
public abstract class AbstractIitoProcessorService implements IitoProcessorService {

    /**
     * 一時中間ファイルのサフィックスと拡張子
     *
     * @since 0.2.0
     */
    private static final String TEMP_INTERMEDIATE_FILE_SUFFIX_EXTENSION = "Temp.tmp"; //$NON-NLS-1$

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
     * 中間ファイルパス
     *
     * @since 0.2.0
     */
    private Path intermediatePath;

    /**
     * 一時中間ファイルのサフィックスと拡張子
     *
     * @since 0.2.2
     */
    private String tempIntermediateFileSuffixExtension;

    /**
     * テンプレートの動的変換サービス
     *
     * @since 0.2.0
     */
    @Autowired
    private DtcService dtcService;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public AbstractIitoProcessorService() {

        this(LoggerFactory.getLogger(AbstractIitoProcessorService.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
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
     * 中間ファイルパスを返す<br>
     *
     * @since 0.2.0
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
     * 初期化する
     *
     * @since 0.2.0
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

        return this.initialize(inputPath, templatePath, outputPath, null);

    }

    /**
     * 初期化する
     *
     * @since 0.2.2
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                                            入力ファイルパス
     * @param templatePath
     *                                            テンプレートファイルパス
     * @param outputPath
     *                                            出力ファイルパス
     * @param tempIntermediateFileSuffixExtension
     *                                            一時中間ファイルのサフィックスと拡張子（nullの場合はデフォルト値を使用）
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath,
        final String tempIntermediateFileSuffixExtension) throws KmgToolMsgException {

        boolean result = false;

        this.inputPath = inputPath;
        this.templatePath = templatePath;
        this.outputPath = outputPath;

        // 一時中間ファイルのサフィックスと拡張子を設定（nullの場合はデフォルト値を使用）
        if (tempIntermediateFileSuffixExtension == null) {

            this.tempIntermediateFileSuffixExtension
                = AbstractIitoProcessorService.TEMP_INTERMEDIATE_FILE_SUFFIX_EXTENSION;

        } else {

            this.tempIntermediateFileSuffixExtension = tempIntermediateFileSuffixExtension;

        }

        // 一時ファイルの作成
        this.intermediatePath = this.createTempntermediateFile();

        result = true;
        return result;

    }

    /**
     * 処理する
     *
     * @since 0.2.0
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

            final KmgToolLogMsgTypes startLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG07002;
            final Object[]           startLogMsgArgs  = {};
            final String             startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes,
                startLogMsgArgs);
            this.logger.debug(startLogMsg);

            result = this.writeIntermediateFile();

        } finally {

            final KmgToolLogMsgTypes endLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG07003;
            final Object[]           endLogMsgArgs  = {};
            final String             endLogMsg      = this.messageSource.getLogMessage(endLogMsgTypes, endLogMsgArgs);
            this.logger.debug(endLogMsg);

        }

        /* テンプレートの動的変換サービスで出力ファイルに出力する */
        final KmgDelimiterTypes intermediateDelimiter = this.getIntermediateDelimiter();
        result = this.dtcService.initialize(this.getIntermediatePath(), this.templatePath, this.outputPath,
            intermediateDelimiter);
        result = this.dtcService.process();

        return result;

    }

    /**
     * 一時的な中間ファイルを作成する。
     *
     * @since 0.2.0
     *
     * @return 中間ファイルパス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    protected Path createTempntermediateFile() throws KmgToolMsgException {

        Path result = null;

        final String intermediateFileNameOnly = KmgPathUtils.getFileNameOnly(this.getInputPath());
        // tempIntermediateFileSuffixExtensionがnullの場合はデフォルト値を使用
        final String suffixExtension
            = (this.tempIntermediateFileSuffixExtension != null) ? this.tempIntermediateFileSuffixExtension
                : AbstractIitoProcessorService.TEMP_INTERMEDIATE_FILE_SUFFIX_EXTENSION;

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
     * 中間ファイルの区切り文字を返す。<br>
     * <p>
     * 中間ファイルの書き込み時に使用した区切り文字を返します。
     * </p>
     *
     * @since 0.2.2
     *
     * @return 中間ファイルの区切り文字
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    protected abstract KmgDelimiterTypes getIntermediateDelimiter() throws KmgToolMsgException;

    /**
     * 中間ファイルに書き込む。
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    protected abstract boolean writeIntermediateFile() throws KmgToolMsgException;

}
