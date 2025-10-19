package kmg.tool.base.io.presentation.ui.cli;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.io.domain.service.IoService;

/**
 * AbstractIoToolのテストクラス
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@SuppressWarnings({
    "nls", "static-method"
})
public class AbstractIoToolTest extends AbstractKmgTest {

    /** テスト用のAbstractIoTool実装クラス */
    private static class TestAbstractIoTool extends AbstractIoTool {

        /**
         * 入出力サービス
         *
         * @since 0.2.0
         */
        private final IoService ioService;

        /**
         * デフォルトコンストラクタ
         *
         * @since 0.2.0
         */
        public TestAbstractIoTool() {

            super("テストツール");
            this.ioService = null;

        }

        /**
         * カスタム入出力サービスを使用するコンストラクタ
         *
         * @since 0.2.0
         *
         * @param ioService
         *                  入出力サービス
         */
        public TestAbstractIoTool(final IoService ioService) {

            super("テストツール");
            this.ioService = ioService;

        }

        /**
         * 入出力サービスを取得します。
         *
         * @since 0.2.0
         *
         * @return 入出力サービス
         */
        @Override
        protected IoService getIoService() {

            final IoService result = this.ioService;
            return result;

        }
    }

    /**
     * テンポラリディレクトリ
     *
     * @since 0.2.0
     */
    @TempDir
    private Path tempDir;

    /**
     * テスト対象
     *
     * @since 0.2.0
     */
    private TestAbstractIoTool testTarget;

    /**
     * モック入出力サービス
     *
     * @since 0.2.0
     */
    private IoService mockIoService;

    /**
     * モックメッセージソース
     *
     * @since 0.2.0
     */
    private KmgMessageSource mockMessageSource;

    /**
     * セットアップ
     *
     * @since 0.2.0
     */
    @BeforeEach
    public void setUp() {

        this.mockIoService = Mockito.mock(IoService.class);
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.testTarget = new TestAbstractIoTool(this.mockIoService);

    }

    /**
     * クリーンアップ
     *
     * @since 0.2.0
     */
    @AfterEach
    public void tearDown() {

        if (this.testTarget != null) {

            // クリーンアップ処理は特に必要ない
        }

    }

    /**
     * コンストラクタのテスト - 正常系：カスタム入出力サービスを使用するコンストラクタで初期化する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCustomIoServiceConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final IoService customIoService = Mockito.mock(IoService.class);

        /* テスト対象の実行 */
        final TestAbstractIoTool testInstance = new TestAbstractIoTool(customIoService);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "カスタム入出力サービスを使用するコンストラクタでインスタンスが作成されること");

    }

    /**
     * コンストラクタのテスト - 正常系：デフォルトコンストラクタで初期化する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefaultConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final TestAbstractIoTool testInstance = new TestAbstractIoTool();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "デフォルトコンストラクタでインスタンスが作成されること");

    }

    /**
     * execute メソッドのテスト - 異常系：例外が発生する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_errorException() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockIoService.process()).thenThrow(new RuntimeException("テスト例外"));
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("例外発生");

        // リフレクションを使用してメッセージソースを設定
        final var reflectionModel = new kmg.core.infrastructure.model.impl.KmgReflectionModelImpl(this.testTarget);
        reflectionModel.set("messageSource", this.mockMessageSource);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.execute();

        /* 検証の準備 */
        final boolean actual = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actual, "例外が発生した場合、falseが返されること");

    }

    /**
     * execute メソッドのテスト - 異常系：RuntimeExceptionが発生する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_errorRuntimeException() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // モックの設定
        final RuntimeException exception = new RuntimeException("テスト例外");
        Mockito.when(this.mockIoService.process()).thenThrow(exception);
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("例外発生");

        // リフレクションを使用してメッセージソースを設定
        final var reflectionModel = new kmg.core.infrastructure.model.impl.KmgReflectionModelImpl(this.testTarget);
        reflectionModel.set("messageSource", this.mockMessageSource);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.execute();

        /* 検証の準備 */
        final boolean actual = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actual, "RuntimeExceptionが発生した場合、falseが返されること");

    }

    /**
     * execute メソッドのテスト - 正常系：処理が成功する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockIoService.process()).thenReturn(true);
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("成功");

        // リフレクションを使用してメッセージソースを設定
        final var reflectionModel = new kmg.core.infrastructure.model.impl.KmgReflectionModelImpl(this.testTarget);
        reflectionModel.set("messageSource", this.mockMessageSource);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.execute();

        /* 検証の準備 */
        final boolean actual = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actual, "処理が成功した場合、trueが返されること");

    }

    /**
     * execute メソッドのテスト - 準正常系：処理が失敗する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_semiFailure() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockIoService.process()).thenReturn(false);
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("失敗");

        // リフレクションを使用してメッセージソースを設定
        final var reflectionModel = new kmg.core.infrastructure.model.impl.KmgReflectionModelImpl(this.testTarget);
        reflectionModel.set("messageSource", this.mockMessageSource);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.execute();

        /* 検証の準備 */
        final boolean actual = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actual, "処理が失敗した場合、falseが返されること");

    }

    /**
     * getBasePath メソッドのテスト - 正常系：優先パスが存在する場合
     *
     * @since 0.2.0
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
            final Path testResult = AbstractIoTool.getBasePath();

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
     * @since 0.2.0
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
            final Path testResult = AbstractIoTool.getBasePath();

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
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testGetInputPath_normalPrimaryInputFileExists() throws Exception {

        /* 期待値の定義 */
        final Path expected = Paths.get("work/io/input.txt");

        /* 準備 */
        // 実際のディレクトリとファイルを作成
        final Path workIoDir = this.tempDir.resolve("work/io");
        Files.createDirectories(workIoDir);
        final Path inputFile = workIoDir.resolve("input.txt");
        Files.createFile(inputFile);

        // 一時的にカレントディレクトリを変更
        final Path originalDir = Paths.get("").toAbsolutePath();

        try {

            System.setProperty("user.dir", this.tempDir.toString());

            /* テスト対象の実行 */
            final Path testResult = AbstractIoTool.getInputPath();

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
     * @since 0.2.0
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
            final Path testResult = AbstractIoTool.getInputPath();

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
     * getIoService メソッドのテスト - 正常系：デフォルトコンストラクタの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_normalDefaultConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final TestAbstractIoTool testInstance = new TestAbstractIoTool();

        /* テスト対象の実行 */
        final IoService testResult = testInstance.getIoService();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNull(testResult, "デフォルトコンストラクタの場合、nullが返されること");

    }

    /**
     * getIoService メソッドのテスト - 正常系：カスタム入出力サービスを設定した場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_normalGet() throws Exception {

        /* 期待値の定義 */
        final IoService expected = this.mockIoService;

        /* 準備 */

        /* テスト対象の実行 */
        final IoService testResult = this.testTarget.getIoService();

        /* 検証の準備 */
        final IoService actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "設定した入出力サービスが正しく返されること");

    }

    /**
     * getOutputPath メソッドのテスト - 正常系：優先パスに出力ファイルが存在する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testGetOutputPath_normalPrimaryOutputFileExists() throws Exception {

        /* 期待値の定義 */
        final Path expected = Paths.get("work/io/output.txt");

        /* 準備 */
        // 実際のディレクトリとファイルを作成
        final Path workIoDir = this.tempDir.resolve("work/io");
        Files.createDirectories(workIoDir);
        final Path outputFile = workIoDir.resolve("output.txt");
        Files.createFile(outputFile);

        // 一時的にカレントディレクトリを変更
        final Path originalDir = Paths.get("").toAbsolutePath();

        try {

            System.setProperty("user.dir", this.tempDir.toString());

            /* テスト対象の実行 */
            final Path testResult = AbstractIoTool.getOutputPath();

            /* 検証の準備 */
            final Path actual = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expected, actual, "優先パスに出力ファイルが存在する場合、優先パスの出力ファイルが返されること");

        } finally {

            System.setProperty("user.dir", originalDir.toString());

        }

    }

    /**
     * getOutputPath メソッドのテスト - 正常系：優先パスに出力ファイルが存在しない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetOutputPath_normalPrimaryOutputFileNotExists() throws Exception {

        /* 期待値の定義 */
        final Path expected = Paths.get("src/main/resources/tool/io/output.txt");

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
            final Path testResult = AbstractIoTool.getOutputPath();

            /* 検証の準備 */
            final Path actual = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expected, actual, "優先パスに出力ファイルが存在しない場合でも、代替パスの出力ファイルが返されること");

        } finally {

            if (workDirRenamed) {

                // workディレクトリを元に戻す
                Files.move(workBackupDir, workDir);

            }

        }

    }

    /**
     * getPrimaryBasePath メソッドのテスト - 正常系：優先パスの取得
     *
     * @since 0.2.0
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
        final Path testResult = AbstractIoTool.getPrimaryBasePath();

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "優先パスが正しく返されること");

    }

    /**
     * getSecondaryBasePath メソッドのテスト - 正常系：代替パスの取得
     *
     * @since 0.2.0
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
        final Path testResult = AbstractIoTool.getSecondaryBasePath();

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "代替パスが正しく返されること");

    }

}
