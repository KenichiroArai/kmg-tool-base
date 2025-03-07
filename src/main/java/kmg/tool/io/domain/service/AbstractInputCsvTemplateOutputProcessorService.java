package kmg.tool.io.domain.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;

import kmg.core.infrastructure.utils.KmgPathUtils;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * 入力、CSV、テンプレート、出力の処理サービス抽象クラス
 */
public abstract class AbstractInputCsvTemplateOutputProcessorService implements Two2OneService {

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
    private DynamicTemplateConversionService dynamicTemplateConversionService;

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
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath)
        throws KmgToolException {

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
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean process() throws KmgToolException {

        boolean result = false;

        /* CSVファイルに書き込む */
        result = this.writeCsvFile();

        /* テンプレートの動的変換サービスで出力ファイルに出力する */
        result
            = this.dynamicTemplateConversionService.initialize(this.getCsvPath(), this.templatePath, this.outputPath);
        result = this.dynamicTemplateConversionService.process();

        return result;

    }

    /**
     * 一時的なCSVファイルを作成する。
     *
     * @return CSVファイルパス
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    protected Path createTempCsvFile() throws KmgToolException {

        Path result = null;

        try {

            final String fileNameOnly = KmgPathUtils.getFileNameOnly(this.getInputPath());
            result = Files.createTempFile(fileNameOnly, "Temp.csv");
            result.toFile().deleteOnExit();

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/06 メッセージ
            final KmgToolGenMessageTypes msgType = KmgToolGenMessageTypes.NONE;
            throw new KmgToolException(msgType, e);

        }

        return result;

    }

    /**
     * CSVファイルに書き込む。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    protected abstract boolean writeCsvFile() throws KmgToolException;

}
