package kmg.tool.presentation.ui.cli.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kmg.tool.domain.service.One2OneService;

/**
 * 1入力ファイルから1出力ファイルへの変換ツールサービス抽象クラス
 */
public abstract class AbstractOne2OneTool extends AbstractIoTool {

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @param toolName
     *                 ツール名
     *
     * @since 0.1.0
     */
    public AbstractOne2OneTool(final String toolName) {

        this(LoggerFactory.getLogger(AbstractOne2OneTool.class), toolName);

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *                 ロガー
     * @param toolName
     *                 ツール名
     */
    protected AbstractOne2OneTool(final Logger logger, final String toolName) {

        super(toolName);
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
            = this.getIoService().initialize(AbstractIoTool.getInputPath(), AbstractIoTool.getOutputPath());

        if (!initializeResult) {

            this.logger.error("初期化の失敗");

        }

        return result;

    }

    /**
     * 1入力ファイルから1出力ファイルへの変換ツールサービスを返す。
     *
     * @return 1入力ファイルから1出力ファイルへの変換ツールサービス
     */
    @Override
    protected abstract One2OneService getIoService();

}
