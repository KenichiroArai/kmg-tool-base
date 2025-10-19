package kmg.tool.base.two2one.presentation.ui.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kmg.core.infrastructure.utils.KmgPathUtils;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.io.presentation.ui.cli.AbstractIoTool;
import kmg.tool.base.two2one.domain.service.Two2OneService;

/**
 * シンプル2入力ファイルから1出力ファイルへの変換ツールサービス抽象クラス
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public abstract class AbstractTwo2OneTool extends AbstractIoTool {

    /**
     * テンプレートファイルのパスのフォーマット
     *
     * @since 0.2.0
     */
    private static final String TEMPLATE_FILE_PATH_FORMAT = "template/%s.yml"; //$NON-NLS-1$

    /**
     * メッセージソース
     *
     * @since 0.2.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * ロガー
     *
     * @since 0.2.0
     */
    private final Logger logger;

    /**
     * テンプレートファイルパス
     *
     * @since 0.2.0
     */
    private final Path templatePath;

    /**
     * 標準ロガーを使用して初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param toolName
     *                 ツール名
     */
    public AbstractTwo2OneTool(final String toolName) {

        this(LoggerFactory.getLogger(AbstractTwo2OneTool.class), toolName);

    }

    /**
     * カスタムロガーを使用して初期化するコンストラクタ<br>
     *
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     */
    public boolean initialize() {

        boolean result;

        try {

            result = this.getIoService().initialize(AbstractIoTool.getInputPath(), this.getTemplatePath(),
                AbstractIoTool.getOutputPath());

        } catch (final KmgToolMsgException e) {

            // ログの出力
            final KmgToolLogMsgTypes logType     = KmgToolLogMsgTypes.KMGTOOL_LOG17000;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getLogMessage(logType, messageArgs);
            this.logger.error(msg, e);

            result = false;

        }

        return result;

    }

    /**
     * 2入力ファイルから1出力ファイルへの変換ツールサービスを返す。
     *
     * @since 0.2.0
     *
     * @return 2入力ファイルから1出力ファイルへの変換ツールサービス
     */
    @Override
    protected abstract Two2OneService getIoService();

    /**
     * デフォルトテンプレートパスを返す。
     *
     * @since 0.2.0
     *
     * @return デフォルトテンプレートパス
     */
    private Path getDefaultTemplatePath() {

        Path         result;
        final String className        = KmgPathUtils.getSimpleClassName(this.getClass());
        final String templateFileName = String.format(AbstractTwo2OneTool.TEMPLATE_FILE_PATH_FORMAT, className);

        // 優先パスがあれば採用する
        final Path primaryPath = Paths.get(AbstractIoTool.getPrimaryBasePath().toString(), templateFileName);

        if (primaryPath.toFile().exists()) {

            result = primaryPath;
            return result;

        }

        // 優先パスがないため、代替パスを採用する
        final Path secondaryPath = Paths.get(AbstractIoTool.getSecondaryBasePath().toString(), templateFileName);
        result = secondaryPath;

        return result;

    }

}
