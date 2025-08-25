package kmg.tool.input.presentation.ui.cli;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.input.domain.service.InputService;

/**
 * AbstractInputToolのテストクラス
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SuppressWarnings({
    "nls", "static-method"
})
public class AbstractInputToolTest extends AbstractKmgTest {

    /** テスト用のAbstractInputTool実装クラス */
    private static class TestAbstractInputTool extends AbstractInputTool {

        /**
         * 入力サービス
         *
         * @since 0.1.0
         */
        private final InputService inputService;

        /**
         * デフォルトコンストラクタ
         *
         * @since 0.1.0
         */
        public TestAbstractInputTool() {

            this.inputService = null;

        }

        /**
         * カスタム入力サービスを使用するコンストラクタ
         *
         * @since 0.1.0
         *
         * @param inputService
         *                     入力サービス
         */
        public TestAbstractInputTool(final InputService inputService) {

            this.inputService = inputService;

        }

        /**
         * 実行します。
         *
         * @since 0.1.0
         *
         * @return true：成功、false：失敗
         */
        @Override
        public boolean execute() {

            final boolean result = true;
            return result;

        }

        /**
         * 入力サービスを取得します。
         *
         * @since 0.1.0
         *
         * @return 入力サービス
         */
        @Override
        public InputService getInputService() {

            final InputService result = this.inputService;
            return result;

        }
    }

    /**
     * テンポラリディレクトリ
     *
     * @since 0.1.0
     */
    @TempDir
    private Path tempDir;

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private TestAbstractInputTool testTarget;

    /**
     * モック入力サービス
     *
     * @since 0.1.0
     */
    private InputService mockInputService;

    /**
     * セットアップ
     *
     * @since 0.1.0
     */
    @BeforeEach
    public void setUp() {

        this.mockInputService = Mockito.mock(InputService.class);
        this.testTarget = new TestAbstractInputTool(this.mockInputService);

    }

    /**
     * クリーンアップ
     *
     * @since 0.1.0
     */
    @AfterEach
    public void tearDown() {

        if (this.testTarget != null) {

            // クリーンアップ処理は特に必要ない
        }

    }

    /**
     * コンストラクタのテスト - 正常系：カスタム入力サービスを使用するコンストラクタで初期化する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCustomInputServiceConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final InputService customInputService = Mockito.mock(InputService.class);

        /* テスト対象の実行 */
        final TestAbstractInputTool testInstance = new TestAbstractInputTool(customInputService);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "カスタム入力サービスを使用するコンストラクタでインスタンスが作成されること");

    }

    /**
     * コンストラクタのテスト - 正常系：デフォルトコンストラクタで初期化する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefaultConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final TestAbstractInputTool testInstance = new TestAbstractInputTool();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "デフォルトコンストラクタでインスタンスが作成されること");

    }

    /**
     * getBasePath メソッドのテスト - 正常系：優先パスが存在する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetBasePath_normalPrimaryPathExists() throws Exception {

        /* 期待値の定義 */
        final Path expected = Paths.get("work/io");

        /* 準備 */
        // 実際のディレクトリを作成
        final Path workIoDir = this.tempDir.resolve("work/io");
        Files.createDirectories(workIoDir);

        // 一時的にカレントディレクトリを変更
        final Path originalDir = Paths.get("").toAbsolutePath();

        try {

            System.setProperty("user.dir", this.tempDir.toString());

            /* テスト対象の実行 */
            final Path testResult = AbstractInputTool.getBasePath();

            /* 検証の準備 */
            final Path actual = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expected, actual, "優先パスが存在する場合、優先パスが返されること");

        } finally {

            System.setProperty("user.dir", originalDir.toString());

        }

    }

    /**
     * getBasePath メソッドのテスト - 正常系：優先パスが存在しない場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetBasePath_normalPrimaryPathNotExists() throws Exception {

        /* 期待値の定義 */
        final Path expected = Paths.get("src/main/resources/tool/io");

        /* 準備 */
        // work/ioディレクトリを一時的に名前変更して、存在しない状態を作成
        final Path    workDir        = Paths.get("work");
        final Path    workBackupDir  = Paths.get("work_backup");
        final boolean workDirExists  = Files.exists(workDir);
        boolean       workDirRenamed = false;

        try {

            if (workDirExists) {

                // workディレクトリを一時的に名前変更
                Files.move(workDir, workBackupDir);
                workDirRenamed = true;

            }

            /* テスト対象の実行 */
            final Path testResult = AbstractInputTool.getBasePath();

            /* 検証の準備 */
            final Path actual = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expected, actual, "優先パスが存在しない場合でも、代替パスが返されること");

        } finally {

            if (workDirRenamed) {

                // workディレクトリを元に戻す
                Files.move(workBackupDir, workDir);

            }

        }

    }

    /**
     * getInputPath メソッドのテスト - 正常系：優先パスに入力ファイルが存在する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetInputPath_normalPrimaryInputFileExists() throws Exception {

        /* 期待値の定義 */
        final Path expected = Paths.get("work/io/input.txt");

        /* 準備 */
        // 実際のディレクトリとファイルを作成
        final Path workIoDir = this.tempDir.resolve("work/io");
        final Path inputFile = workIoDir.resolve("input.txt");
        Files.createDirectories(workIoDir);
        Files.write(inputFile, "test content".getBytes());

        // 一時的にカレントディレクトリを変更
        final Path originalDir = Paths.get("").toAbsolutePath();

        try {

            System.setProperty("user.dir", this.tempDir.toString());

            /* テスト対象の実行 */
            final Path testResult = AbstractInputTool.getInputPath();

            /* 検証の準備 */
            final Path actual = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expected, actual, "優先パスに入力ファイルが存在する場合、優先パスの入力ファイルが返されること");

        } finally {

            System.setProperty("user.dir", originalDir.toString());

        }

    }

    /**
     * getInputPath メソッドのテスト - 正常系：優先パスに入力ファイルが存在しない場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetInputPath_normalPrimaryInputFileNotExists() throws Exception {

        /* 期待値の定義 */
        final Path expected = Paths.get("src/main/resources/tool/io/input.txt");

        /* 準備 */
        // work/ioディレクトリを一時的に名前変更して、存在しない状態を作成
        final Path    workDir        = Paths.get("work");
        final Path    workBackupDir  = Paths.get("work_backup");
        final boolean workDirExists  = Files.exists(workDir);
        boolean       workDirRenamed = false;

        try {

            if (workDirExists) {

                // workディレクトリを一時的に名前変更
                Files.move(workDir, workBackupDir);
                workDirRenamed = true;

            }

            /* テスト対象の実行 */
            final Path testResult = AbstractInputTool.getInputPath();

            /* 検証の準備 */
            final Path actual = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expected, actual, "優先パスに入力ファイルが存在しない場合でも、代替パスの入力ファイルが返されること");

        } finally {

            if (workDirRenamed) {

                // workディレクトリを元に戻す
                Files.move(workBackupDir, workDir);

            }

        }

    }

    /**
     * getInputService メソッドのテスト - 正常系：デフォルトコンストラクタで初期化した場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetInputService_normalDefaultConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final TestAbstractInputTool defaultTestTarget = new TestAbstractInputTool();

        /* テスト対象の実行 */
        final InputService testResult = defaultTestTarget.getInputService();

        /* 検証の準備 */
        final InputService actual = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actual, "デフォルトコンストラクタで初期化した場合、nullが返されること");

    }

    /**
     * getInputService メソッドのテスト - 正常系：入力サービスが正常に取得される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetInputService_normalGet() throws Exception {

        /* 期待値の定義 */
        final InputService expected = this.mockInputService;

        /* 準備 */

        /* テスト対象の実行 */
        final InputService testResult = this.testTarget.getInputService();

        /* 検証の準備 */
        final InputService actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "入力サービスが正しく取得されること");

    }

    /**
     * getPrimaryBasePath メソッドのテスト - 正常系：優先的に使用する基準パスを取得する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetPrimaryBasePath_normalGet() throws Exception {

        /* 期待値の定義 */
        final Path expected = Paths.get("work/io");

        /* 準備 */

        /* テスト対象の実行 */
        final Path testResult = AbstractInputTool.getPrimaryBasePath();

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "優先的に使用する基準パスが正しく取得されること");

    }

    /**
     * getSecondaryBasePath メソッドのテスト - 正常系：代替の基準パスを取得する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetSecondaryBasePath_normalGet() throws Exception {

        /* 期待値の定義 */
        final Path expected = Paths.get("src/main/resources/tool/io");

        /* 準備 */

        /* テスト対象の実行 */
        final Path testResult = AbstractInputTool.getSecondaryBasePath();

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "代替の基準パスが正しく取得されること");

    }

}
