package kmg.tool.presentation.ui.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

import kmg.tool.domain.service.InputService;

/**
 * 入力処理ツール抽象クラス
 * <p>
 * このクラスは入力処理の基本機能を提供します。 入力ファイルのパスは以下の優先順位で決定されます：
 * </p>
 * <ol>
 * <li>work/ioディレクトリが存在する場合：work/io/input.txt</li>
 * <li>work/ioディレクトリが存在しない場合：src/main/resources/tool/io/input.txt</li>
 * </ol>
 * <p>
 * 使用例：
 * </p>
 *
 * <pre>
 * public class CustomInputTool extends AbstractInputTool {
 *     private final InputService inputService;
 *
 *     public CustomInputTool(String toolName, InputService inputService) {
 *         super(toolName);
 *         this.inputService = inputService;
 *     }
 *
 *     {@literal @}Override
 *     protected InputService getInputService() {
 *         return this.inputService;
 *     }
 * }
 *
 * // ツールの使用
 * InputService inputService = new CustomInputService();
 * CustomInputTool tool = new CustomInputTool("カスタム入力ツール", inputService);
 * boolean success = tool.execute();
 * </pre>
 */
// TODO KenichiroArai 2025/05/14 Javadocの再設定が必要。
public abstract class AbstractInputTool extends AbstractTool {

    /** 優先的に使用する基準パス */
    private static final Path PRIMARY_BASE_PATH = Paths.get("work/io");

    /** 代替の基準パス */
    private static final Path SECONDARY_BASE_PATH = Paths.get("src/main/resources/tool/io");

    /** 入力ファイル名 */
    private static final Path INPUT_FILE_NAME = Paths.get("input.txt");

    /**
     * 基準パスを返す。
     *
     * @return 基準パス
     */
    public static Path getBasePath() {

        Path result = null;

        if (AbstractInputTool.PRIMARY_BASE_PATH.toFile().exists()) {

            result = AbstractInputTool.PRIMARY_BASE_PATH;
            return result;

        }

        result = AbstractInputTool.SECONDARY_BASE_PATH;

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
            = Paths.get(AbstractInputTool.PRIMARY_BASE_PATH.toString(), AbstractInputTool.INPUT_FILE_NAME.toString());

        if (primaryPath.toFile().exists()) {

            result = primaryPath;
            return result;

        }

        final Path secondaryPath
            = Paths.get(AbstractInputTool.SECONDARY_BASE_PATH.toString(), AbstractInputTool.INPUT_FILE_NAME.toString());

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

        final Path result = AbstractInputTool.PRIMARY_BASE_PATH;
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

        final Path result = AbstractInputTool.SECONDARY_BASE_PATH;
        return result;

    }

    /**
     * 入力サービスを返す。
     *
     * @return 入力サービス
     */
    protected abstract InputService getInputService();

}
