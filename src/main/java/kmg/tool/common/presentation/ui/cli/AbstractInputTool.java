package kmg.tool.common.presentation.ui.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

import kmg.tool.domain.service.InputService;

/**
 * 入力処理ツールの抽象基底クラス
 * <p>
 * このクラスは入力処理の基本機能を提供する抽象クラスです。 入力ファイルのパスは以下の優先順位で決定されます：
 * </p>
 * <ol>
 * <li>work/ioディレクトリが存在する場合：work/io/input.txt</li>
 * <li>work/ioディレクトリが存在しない場合：src/main/resources/tool/io/input.txt</li>
 * </ol>
 * <p>
 * このクラスを継承することで、カスタム入力処理ツールを実装できます。 継承クラスでは{@link #getInputService()}メソッドを実装する必要があります。
 * </p>
 * <h3>使用例：</h3>
 *
 * <pre>
 * // カスタム入力サービスの実装
 * public class CustomInputService implements InputService {
 *     {@literal @}Override
 *     public void processInput(Path inputPath) {
 *         // 入力ファイルの処理ロジックを実装
 *     }
 * }
 *
 * // カスタム入力ツールの実装
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
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 1.0.0
 */
public abstract class AbstractInputTool extends AbstractTool {

    /** 優先的に使用する基準パス */
    private static final Path PRIMARY_BASE_PATH = Paths.get("work/io"); //$NON-NLS-1$

    /** 代替の基準パス */
    private static final Path SECONDARY_BASE_PATH = Paths.get("src/main/resources/tool/io"); //$NON-NLS-1$

    /** 入力ファイル名 */
    private static final Path INPUT_FILE_NAME = Paths.get("input.txt"); //$NON-NLS-1$

    /**
     * 基準パスを取得します。
     * <p>
     * 優先パス（work/io）が存在する場合はそちらを返し、 存在しない場合は代替パス（src/main/resources/tool/io）を返します。
     * </p>
     *
     * @return 基準パス
     *
     * @since 0.1.0
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
     * 入力ファイルのパスを取得します。
     * <p>
     * 優先パス（work/io/input.txt）に入力ファイルが存在すればそちらを使用し、 存在しない場合は代替パス（src/main/resources/tool/io/input.txt）を使用します。
     * </p>
     *
     * @return 入力ファイルパス
     *
     * @since 0.1.0
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
     * <p>
     * このメソッドは常にwork/ioディレクトリのパスを返します。
     * </p>
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
     * <p>
     * このメソッドは常にsrc/main/resources/tool/ioディレクトリのパスを返します。
     * </p>
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
     * 入力サービスを取得します。
     * <p>
     * このメソッドは継承クラスで実装する必要があります。 入力処理の具体的な実装を提供するサービスを返してください。
     * </p>
     *
     * @return 入力サービス
     *
     * @since 0.1.0
     */
    public abstract InputService getInputService();

}
