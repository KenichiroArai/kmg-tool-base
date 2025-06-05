package kmg.tool.presentation.ui.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

import kmg.core.domain.service.KmgPfaMeasService;
import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.domain.service.io.IoService;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;

/**
 * 入出力ツール抽象クラス
 * <p>
 * このクラスは入出力処理の基本機能を提供します。 入力ファイルと出力ファイルのパスは以下の優先順位で決定されます：
 * </p>
 * <ol>
 * <li>work/ioディレクトリが存在する場合：work/io/[input|output].txt</li>
 * <li>work/ioディレクトリが存在しない場合：src/main/resources/tool/io/[input|output].txt</li>
 * </ol>
 * <p>
 * 使用例：
 * </p>
 *
 * <pre>
 * public class CustomIoTool extends AbstractIoTool {
 *     private final IoService ioService;
 *
 *     public CustomIoTool(String toolName, IoService ioService) {
 *         super(toolName);
 *         this.ioService = ioService;
 *     }
 *
 *     {@literal @}Override
 *     protected IoService getIoService() {
 *         return this.ioService;
 *     }
 * }
 *
 * // ツールの使用
 * IoService ioService = new CustomIoService();
 * CustomIoTool tool = new CustomIoTool("カスタムツール", ioService);
 * boolean success = tool.execute();
 * </pre>
 */
public abstract class AbstractIoTool extends AbstractTool {

    /** 優先的に使用する基準パス */
    private static final Path PRIMARY_BASE_PATH = Paths.get("work/io"); //$NON-NLS-1$

    /** 代替の基準パス */
    private static final Path SECONDARY_BASE_PATH = Paths.get("src/main/resources/tool/io"); //$NON-NLS-1$

    /** 入力ファイル名 */
    private static final Path INPUT_FILE_NAME = Paths.get("input.txt"); //$NON-NLS-1$

    /** 出力ファイル名 */
    private static final Path OUTPUT_FILE_NAME = Paths.get("output.txt"); //$NON-NLS-1$

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

        Path result = null;

        if (AbstractIoTool.PRIMARY_BASE_PATH.toFile().exists()) {

            result = AbstractIoTool.PRIMARY_BASE_PATH;
            return result;

        }

        result = AbstractIoTool.SECONDARY_BASE_PATH;

        return result;

    }

    /**
     * 入力ファイルパスを返す。 優先パスに入力ファイルが存在すればそちらを使用し、なければ代替パスを使用する。
     *
     * @return 入力ファイルパス
     */
    public static Path getInputPath() {

        Path result = null;

        final Path primaryPath
            = Paths.get(AbstractIoTool.PRIMARY_BASE_PATH.toString(), AbstractIoTool.INPUT_FILE_NAME.toString());

        if (primaryPath.toFile().exists()) {

            result = primaryPath;
            return result;

        }

        final Path secondaryPath
            = Paths.get(AbstractIoTool.SECONDARY_BASE_PATH.toString(), AbstractIoTool.INPUT_FILE_NAME.toString());

        result = secondaryPath;
        return result;

    }

    /**
     * 出力ファイルパスを返す。 優先パスに出力ファイルが存在すればそちらを使用し、なければ代替パスを使用する。
     *
     * @return 出力ファイルパス
     */
    public static Path getOutputPath() {

        Path result = null;

        final Path primaryPath
            = Paths.get(AbstractIoTool.PRIMARY_BASE_PATH.toString(), AbstractIoTool.OUTPUT_FILE_NAME.toString());

        if (primaryPath.toFile().exists()) {

            result = primaryPath;
            return result;

        }

        final Path secondaryPath
            = Paths.get(AbstractIoTool.SECONDARY_BASE_PATH.toString(), AbstractIoTool.OUTPUT_FILE_NAME.toString());

        result = secondaryPath;
        return result;

    }

    /**
     * 優先的に使用する基準パスを取得します。
     *
     * @return 優先的に使用する基準パス
     *
     * @since 0.1.0
     */
    public static Path getPrimaryBasePath() {

        final Path result = AbstractIoTool.PRIMARY_BASE_PATH;
        return result;

    }

    /**
     * 代替の基準パスを取得します。
     *
     * @return 代替の基準パス
     *
     * @since 0.1.0
     */
    public static Path getSecondaryBasePath() {

        final Path result = AbstractIoTool.SECONDARY_BASE_PATH;
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

                /* メッセージの出力 */
                final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN41000;
                final Object[]           messageArgs = {};
                final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
                measService.warn(msg);

                return result;

            }

            /* 成功 */
            // メッセージの出力
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN41001;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.info(msg);

            result = true;

        } catch (final Exception e) {

            /* 失敗 */
            // メッセージの出力
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN41002;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
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
