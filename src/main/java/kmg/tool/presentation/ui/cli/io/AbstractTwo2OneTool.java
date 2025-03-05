package kmg.tool.presentation.ui.cli.io;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kmg.foundation.infrastructure.context.KmgMessageSource;
import kmg.tool.domain.service.Two2OneService;
import kmg.tool.domain.types.KmgToolLogMessageTypes;

/**
 * シンプル2入力ファイルから1出力ファイルへの変換ツールサービス抽象クラス
 */
public abstract class AbstractTwo2OneTool extends AbstractIoTool {

    /** メッセージソース */
    @Autowired
    private KmgMessageSource messageSource;

    /** テンプレートファイルパス */
    private final Path templatePath;

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
    public AbstractTwo2OneTool(final String toolName) {

        this(LoggerFactory.getLogger(AbstractTwo2OneTool.class), toolName);

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
    protected AbstractTwo2OneTool(final Logger logger, final String toolName) {

        super(toolName);
        this.logger = logger;
        this.templatePath = this.getDefaultTemplatePath();

    }

    /**
     * テンプレートファイルパス
     *
     * @return テンプレートファイルパス
     */
    public Path getTemplatePath() {

        final Path result = this.templatePath;
        return result;

    }

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     */
    public boolean initialize() {

        final boolean result = false;

        final boolean initializeResult = this.getIoService().initialize(AbstractIoTool.getInputPath(),
            this.getTemplatePath(), AbstractIoTool.getOutputPath());

        if (!initializeResult) {

            // ログの出力
            final KmgToolLogMessageTypes logType     = KmgToolLogMessageTypes.KMGTOOLLOGE41001;
            final Object[]               messageArgs = {};
            final String                 msg         = this.messageSource.getLogMessage(logType, messageArgs);
            this.logger.error(msg);

        }

        return result;

    }

    /**
     * 2入力ファイルから1出力ファイルへの変換ツールサービスを返す。
     *
     * @return 2入力ファイルから1出力ファイルへの変換ツールサービス
     */
    @Override
    protected abstract Two2OneService getIoService();

    /**
     * デフォルトテンプレートパスを返す。
     *
     * @return デフォルトテンプレートパス
     */
    private Path getDefaultTemplatePath() {

        Path         result           = null;
        final String templateFileName = String.format("template/%s.txt", this.getClass().getSimpleName());
        result = Paths.get(AbstractIoTool.getBasePath().toString(), templateFileName);
        return result;

    }

}
