package kmg.tool.presentation.ui.cli.io;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

import kmg.core.domain.service.KmgPfaMeasService;
import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
import kmg.tool.domain.service.IoService;
import kmg.tool.domain.types.KmgToolMsgMessageTypes;
import kmg.tool.infrastructure.context.KmgMessageSource;
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

    /** メッセージソース */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * ツール名
     *
     * @since 0.1.0
     */
    private final String toolName;

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
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @param toolName
     *                 ツール名
     *
     * @since 0.1.0
     */
    public AbstractIoTool(final String toolName) {

        this.toolName = toolName;

    }

    /**
     * 実行する
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean execute() {

        boolean result = false;

        final KmgPfaMeasService measService = new KmgPfaMeasServiceImpl(this.toolName);

        try {

            /* 開始 */
            measService.start();

            /* 処理 */
            final boolean processResult = this.getIoService().process();

            if (!processResult) {

                /* ログの出力 */
                final KmgToolMsgMessageTypes logType     = KmgToolMsgMessageTypes.KMGTOOLMSGW41000;
                final Object[]               messageArgs = {};
                final String                 msg         = this.messageSource.getMessage(logType, messageArgs);
                measService.warn(msg);

                return result;

            }

            /* 成功 */
            // ログの出力
            final KmgToolMsgMessageTypes logType     = KmgToolMsgMessageTypes.KMGTOOLMSGI41001;
            final Object[]               messageArgs = {};
            final String                 msg         = this.messageSource.getMessage(logType, messageArgs);
            measService.info(msg);

            result = true;

        } catch (final Exception e) {

            /* 失敗 */
            // ログの出力
            final KmgToolMsgMessageTypes logType     = KmgToolMsgMessageTypes.KMGTOOLMSGE41002;
            final Object[]               messageArgs = {};
            final String                 msg         = this.messageSource.getMessage(logType, messageArgs);
            measService.error(msg, e);

        } finally {

            /* 終了 */

            measService.end();

        }

        return result;

    }

    /**
     * 入出力サービスを返す。
     *
     * @return 入出力サービス
     */
    protected abstract IoService getIoService();

}
