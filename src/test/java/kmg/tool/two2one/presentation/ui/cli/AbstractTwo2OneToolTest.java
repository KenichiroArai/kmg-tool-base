package kmg.tool.two2one.presentation.ui.cli;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.Logger;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.io.presentation.ui.cli.AbstractIoTool;
import kmg.tool.two2one.domain.service.Two2OneService;

/**
 * AbstractTwo2OneToolのテストクラス
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
public class AbstractTwo2OneToolTest extends AbstractKmgTest {

    /** テスト用のAbstractTwo2OneTool実装クラス */
    private static class TestAbstractTwo2OneTool extends AbstractTwo2OneTool {

        /**
         * 2入力ファイルから1出力ファイルへの変換ツールサービス
         *
         * @since 0.1.0
         */
        private final Two2OneService two2OneService;

        /**
         * デフォルトコンストラクタ
         *
         * @since 0.1.0
         */
        public TestAbstractTwo2OneTool() {

            super("テストツール");
            this.two2OneService = null;

        }

        /**
         * カスタムロガーを使用するコンストラクタ
         *
         * @since 0.1.0
         *
         * @param logger
         *                 ロガー
         * @param toolName
         *                 ツール名
         */
        public TestAbstractTwo2OneTool(final Logger logger, final String toolName) {

            super(logger, toolName);
            this.two2OneService = null;

        }

        /**
         * カスタム2入力ファイルから1出力ファイルへの変換ツールサービスを使用するコンストラクタ
         *
         * @since 0.1.0
         *
         * @param two2OneService
         *                       2入力ファイルから1出力ファイルへの変換ツールサービス
         */
        public TestAbstractTwo2OneTool(final Two2OneService two2OneService) {

            super("テストツール");
            this.two2OneService = two2OneService;

        }

        /**
         * 2入力ファイルから1出力ファイルへの変換ツールサービスを取得します。
         *
         * @since 0.1.0
         *
         * @return 2入力ファイルから1出力ファイルへの変換ツールサービス
         */
        @Override
        protected Two2OneService getIoService() {

            final Two2OneService result = this.two2OneService;
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
    private TestAbstractTwo2OneTool testTarget;

    /**
     * モック2入力ファイルから1出力ファイルへの変換ツールサービス
     *
     * @since 0.1.0
     */
    private Two2OneService mockTwo2OneService;

    /**
     * モックメッセージソース
     *
     * @since 0.1.0
     */
    private KmgMessageSource mockMessageSource;

    /**
     * セットアップ
     *
     * @since 0.1.0
     */
    @BeforeEach
    public void setUp() {

        this.mockTwo2OneService = Mockito.mock(Two2OneService.class);
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.testTarget = new TestAbstractTwo2OneTool(this.mockTwo2OneService);

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
     * コンストラクタのテスト - 正常系：カスタムロガーを使用するコンストラクタで初期化する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCustomLoggerConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Logger customLogger = Mockito.mock(Logger.class);

        /* テスト対象の実行 */
        final TestAbstractTwo2OneTool testInstance = new TestAbstractTwo2OneTool(customLogger, "カスタムツール");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "カスタムロガーを使用するコンストラクタでインスタンスが作成されること");

    }

    /**
     * コンストラクタのテスト - 正常系：カスタム2入力ファイルから1出力ファイルへの変換ツールサービスを使用するコンストラクタで初期化する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCustomTwo2OneServiceConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Two2OneService customTwo2OneService = Mockito.mock(Two2OneService.class);

        /* テスト対象の実行 */
        final TestAbstractTwo2OneTool testInstance = new TestAbstractTwo2OneTool(customTwo2OneService);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "カスタム2入力ファイルから1出力ファイルへの変換ツールサービスを使用するコンストラクタでインスタンスが作成されること");

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
        final TestAbstractTwo2OneTool testInstance = new TestAbstractTwo2OneTool();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "デフォルトコンストラクタでインスタンスが作成されること");

    }

    /**
     * getDefaultTemplatePath メソッドのテスト - 正常系：優先パスが存在する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetDefaultTemplatePath_normalPrimaryPathExists() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final TestAbstractTwo2OneTool testInstance = new TestAbstractTwo2OneTool();

        // 優先パスが存在するようにモック化
        try (final MockedStatic<AbstractIoTool> mockedStatic = Mockito.mockStatic(AbstractIoTool.class);
            final MockedStatic<Paths> mockedPaths = Mockito.mockStatic(Paths.class)) {

            // 優先パスが存在するように設定
            final Path mockPrimaryPath = Mockito.mock(Path.class);
            final File mockFile        = Mockito.mock(File.class);
            Mockito.when(mockPrimaryPath.toFile()).thenReturn(mockFile);
            Mockito.when(mockFile.exists()).thenReturn(true);

            mockedStatic.when(AbstractIoTool::getPrimaryBasePath).thenReturn(mockPrimaryPath);

            // Paths.get()のモック設定 - 優先パスのみをモック
            mockedPaths.when(() -> Paths.get(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(mockPrimaryPath);

            /* テスト対象の実行 */
            final var  reflectionModel = new KmgReflectionModelImpl(testInstance);
            final Path testResult      = (Path) reflectionModel.getMethod("getDefaultTemplatePath");

            /* 検証の準備 */
            final Path actual = testResult;

            /* 検証の実施 */
            Assertions.assertNotNull(actual, "優先パスが存在する場合、正しいパスが返されること");

        }

    }

    /**
     * getDefaultTemplatePath メソッドのテスト - 準正常系：優先パスが存在しない場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetDefaultTemplatePath_semiPrimaryPathNotExists() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final TestAbstractTwo2OneTool testInstance = new TestAbstractTwo2OneTool();

        // 優先パスが存在しないようにモック化
        try (final MockedStatic<AbstractIoTool> mockedStatic = Mockito.mockStatic(AbstractIoTool.class);
            final MockedStatic<Paths> mockedPaths = Mockito.mockStatic(Paths.class)) {

            // 優先パスが存在しないように設定
            final Path mockPrimaryPath   = Mockito.mock(Path.class);
            final Path mockSecondaryPath = Mockito.mock(Path.class);
            final File mockFile          = Mockito.mock(File.class);
            Mockito.when(mockPrimaryPath.toFile()).thenReturn(mockFile);
            Mockito.when(mockFile.exists()).thenReturn(false);

            mockedStatic.when(AbstractIoTool::getPrimaryBasePath).thenReturn(mockPrimaryPath);
            mockedStatic.when(AbstractIoTool::getSecondaryBasePath).thenReturn(mockSecondaryPath);

            // Paths.get()のモック設定 - 優先パスと代替パスの両方をモック
            mockedPaths.when(() -> Paths.get(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenAnswer(invocation -> {

                    final Path resultLocal = mockPrimaryPath;
                    return resultLocal;

                });

            // 代替パスのtoFile()メソッドもモック化
            Mockito.when(mockSecondaryPath.toFile()).thenReturn(mockFile);

            /* テスト対象の実行 */
            final var  reflectionModel = new KmgReflectionModelImpl(testInstance);
            final Path testResult      = (Path) reflectionModel.getMethod("getDefaultTemplatePath");

            /* 検証の準備 */
            final Path actual = testResult;

            /* 検証の実施 */
            Assertions.assertNotNull(actual, "優先パスが存在しない場合、代替パスが返されること");

        }

    }

    /**
     * getIoService メソッドのテスト - 正常系：デフォルトコンストラクタの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_normalDefaultConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final TestAbstractTwo2OneTool testInstance = new TestAbstractTwo2OneTool();

        /* テスト対象の実行 */
        final Two2OneService testResult = testInstance.getIoService();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNull(testResult, "デフォルトコンストラクタの場合、nullが返されること");

    }

    /**
     * getIoService メソッドのテスト - 正常系：カスタム2入力ファイルから1出力ファイルへの変換ツールサービスを設定した場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_normalGet() throws Exception {

        /* 期待値の定義 */
        final Two2OneService expected = this.mockTwo2OneService;

        /* 準備 */

        /* テスト対象の実行 */
        final Two2OneService testResult = this.testTarget.getIoService();

        /* 検証の準備 */
        final Two2OneService actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "設定した2入力ファイルから1出力ファイルへの変換ツールサービスが正しく返されること");

    }

    /**
     * getTemplatePath メソッドのテスト - 正常系：テンプレートパスが取得できる場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetTemplatePath_normalGet() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getTemplatePath();

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actual, "テンプレートパスが正しく取得されること");

    }

    /**
     * initialize メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        // TODO KenichiroArai 2025/07/31 KmgToolMsgExceptionの検証

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            // メッセージソースのモック設定
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // メッセージソースのメソッドのモック設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(KmgToolLogMsgTypes.class),
                ArgumentMatchers.any())).thenReturn("例外発生");
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("例外メッセージ");

            // リフレクションを使用してメッセージソースを設定
            final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);
            reflectionModel.set("messageSource", this.mockMessageSource);

            // 例外を事前に作成（モック設定完了後に作成）
            final KmgToolMsgException exception = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN01001);

            // モックの設定（事前に作成した例外を使用）
            Mockito.when(this.mockTwo2OneService.initialize(ArgumentMatchers.any(Path.class),
                ArgumentMatchers.any(Path.class), ArgumentMatchers.any(Path.class))).thenThrow(exception);

            /* テスト対象の実行 */
            final boolean testResult = this.testTarget.initialize();

            /* 検証の準備 */
            final boolean actual = testResult;

            /* 検証の実施 */
            Assertions.assertFalse(actual, "KmgToolMsgExceptionが発生した場合、falseが返されること");

        }

    }

    /**
     * initialize メソッドのテスト - 正常系：初期化が成功する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTwo2OneService.initialize(ArgumentMatchers.any(Path.class),
            ArgumentMatchers.any(Path.class), ArgumentMatchers.any(Path.class))).thenReturn(true);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(KmgToolLogMsgTypes.class),
            ArgumentMatchers.any())).thenReturn("成功");

        // リフレクションを使用してメッセージソースを設定
        final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        reflectionModel.set("messageSource", this.mockMessageSource);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize();

        /* 検証の準備 */
        final boolean actual = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actual, "初期化が成功した場合、trueが返されること");

    }

    /**
     * initialize メソッドのテスト - 準正常系：初期化が失敗する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_semiFailure() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTwo2OneService.initialize(ArgumentMatchers.any(Path.class),
            ArgumentMatchers.any(Path.class), ArgumentMatchers.any(Path.class))).thenReturn(false);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(KmgToolLogMsgTypes.class),
            ArgumentMatchers.any())).thenReturn("失敗");

        // リフレクションを使用してメッセージソースを設定
        final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        reflectionModel.set("messageSource", this.mockMessageSource);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize();

        /* 検証の準備 */
        final boolean actual = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actual, "初期化が失敗した場合、falseが返されること");

    }

}
