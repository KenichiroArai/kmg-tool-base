package kmg.tool.presentation.ui.cli.io;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

import kmg.core.domain.service.KmgPfaMeasService;
import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
import kmg.foundation.infrastructure.context.KmgMessageSource;
import kmg.tool.domain.service.io.IoService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.presentation.ui.cli.AbstractTool;

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
    private static final Path PRIMARY_BASE_PATH = Paths.get("work/io");

    /** 代替の基準パス */
    private static final Path SECONDARY_BASE_PATH = Paths.get("src/main/resources/tool/io");

    /** 入力ファイル名 */
    private static final Path INPUT_FILE_NAME = Paths.get("input.txt");

    /** 出力ファイル名 */
    private static final Path OUTPUT_FILE_NAME = Paths.get("output.txt");

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

        if (PRIMARY_BASE_PATH.toFile().exists()) {

            return PRIMARY_BASE_PATH;

        }
        return SECONDARY_BASE_PATH;

    }

    /**
     * 入力ファイルパスを返す。
     *
     * @return 入力ファイルパス
     */
    public static Path getInputPath() {

        return Paths.get(getBasePath().toString(), INPUT_FILE_NAME.toString());

    }

    /**
     * 出力ファイルパスを返す。
     *
     * @return 出力ファイルパス
     */
    public static Path getOutputPath() {

        return Paths.get(getBasePath().toString(), OUTPUT_FILE_NAME.toString());

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
                final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.KMGTOOL_GEN41000;
                final Object[]               messageArgs = {};
                final String                 msg         = this.messageSource.getGenMessage(msgType, messageArgs);
                measService.warn(msg);

                return result;

            }

            /* 成功 */
            // メッセージの出力
            final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.KMGTOOL_GEN41001;
            final Object[]               messageArgs = {};
            final String                 msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.info(msg);

            result = true;

        } catch (final Exception e) {

            /* 失敗 */
            // メッセージの出力
            final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.KMGTOOL_GEN41002;
            final Object[]               messageArgs = {};
            final String                 msg         = this.messageSource.getGenMessage(msgType, messageArgs);
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
