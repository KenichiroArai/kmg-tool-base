package kmg.tool.presentation.ui.cli.io;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
import kmg.tool.domain.service.One2OneService;
import kmg.tool.presentation.ui.cli.AbstractTool;

/**
 * 入出力ツール抽象クラス
 */
public abstract class AbstractIoTool extends AbstractTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** 入力ファイルパス */
    private static final Path INPUT_PATH = Paths.get(AbstractIoTool.BASE_PATH.toString(), "input.txt");

    /** 出力ファイルパス */
    private static final Path OUTPUT_PATH = Paths.get(AbstractIoTool.BASE_PATH.toString(), "output.txt");

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /**
     * 基準パスを返す。
     *
     * @return 基準パス
     */
    public static Path getBasePath() {

        final Path result = AbstractIoTool.BASE_PATH;
        return result;

    }

    /**
     * 入力ファイルパスを返す。
     *
     * @return 入力ファイルパス
     */
    public static Path getInputPath() {

        final Path result = AbstractIoTool.INPUT_PATH;
        return result;

    }

    /**
     * 出力ファイルパスを返す。
     *
     * @return 出力ファイルパス
     */
    public static Path getOutputPath() {

        final Path result = AbstractIoTool.OUTPUT_PATH;
        return result;

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.1.0
     */
    public AbstractIoTool() {

        this(LoggerFactory.getLogger(KmgPfaMeasServiceImpl.class));

    }

    /**
     * テスト用コンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *               ロガー
     */
    protected AbstractIoTool(final Logger logger) {

        this.logger = logger;

    }

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     */
    public boolean initialize() {

        final boolean result = false;

        final boolean initializeResult
            = this.getOne2OneService().initialize(AbstractIoTool.getInputPath(), AbstractIoTool.getOutputPath());

        if (!initializeResult) {

            this.logger.error("初期化の失敗");

        }

        return result;

    }

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     */
    public boolean process() {

        boolean result = false;

        /* 開始 */
        this.logger.info("開始");

        /* 処理 */
        final boolean processResult = this.getOne2OneService().process();

        if (!processResult) {

            this.logger.error("処理の失敗");
            return result;

        }

        /* 成功 */
        this.logger.info("成功");

        result = true;

        /* 終了 */

        this.logger.info("終了");

        return result;

    }

    /**
     * 1入力ファイルから1出力ファイルへの変換サービスを返す。
     *
     * @return 1入力ファイルから1出力ファイルへの変換サービス
     */
    protected abstract One2OneService getOne2OneService();

}
