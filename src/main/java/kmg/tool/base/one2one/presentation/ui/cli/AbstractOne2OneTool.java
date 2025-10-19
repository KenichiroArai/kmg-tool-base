package kmg.tool.base.one2one.presentation.ui.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.io.presentation.ui.cli.AbstractIoTool;
import kmg.tool.base.one2one.domain.service.One2OneService;

/**
 * 1入力ファイルから1出力ファイルへの変換ツールサービス抽象クラス
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public abstract class AbstractOne2OneTool extends AbstractIoTool {

    /**
     * ロガー
     *
     * @since 0.2.0
     */
    private final Logger logger;

    /**
     * メッセージソース
     *
     * @since 0.2.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param toolName
     *                 ツール名
     */
    public AbstractOne2OneTool(final String toolName) {

        this(LoggerFactory.getLogger(AbstractOne2OneTool.class), toolName);

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     */
    public boolean initialize() {

        final boolean result = false;

        boolean initializeResult = false;

        try {

            initializeResult
                = this.getIoService().initialize(AbstractIoTool.getInputPath(), AbstractIoTool.getOutputPath());

        } catch (final KmgToolMsgException e) {

            // ログの出力
            final KmgToolLogMsgTypes logType     = KmgToolLogMsgTypes.KMGTOOL_LOG15001;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getLogMessage(logType, messageArgs);
            this.logger.error(msg, e);

        }

        if (!initializeResult) {

            // ログの出力
            final KmgToolLogMsgTypes logType     = KmgToolLogMsgTypes.KMGTOOL_LOG15000;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getLogMessage(logType, messageArgs);
            this.logger.error(msg);

        }

        return result;

    }

    /**
     * 1入力ファイルから1出力ファイルへの変換ツールサービスを返す。
     *
     * @since 0.2.0
     *
     * @return 1入力ファイルから1出力ファイルへの変換ツールサービス
     */
    @Override
    protected abstract One2OneService getIoService();

}
