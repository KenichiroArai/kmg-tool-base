package kmg.tool.one2one.presentation.ui.cli;

import java.nio.file.Path;

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
import kmg.tool.one2one.domain.service.One2OneService;

/**
 * AbstractOne2OneToolのテストクラス
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
public class AbstractOne2OneToolTest extends AbstractKmgTest {

    /** テスト用のAbstractOne2OneTool実装クラス */
    private static class TestAbstractOne2OneTool extends AbstractOne2OneTool {

        /**
         * 1入力ファイルから1出力ファイルへの変換ツールサービス
         *
         * @since 0.1.0
         */
        private final One2OneService one2OneService;

        /**
         * デフォルトコンストラクタ
         *
         * @since 0.1.0
         */
        public TestAbstractOne2OneTool() {

            super("テストツール");
            this.one2OneService = null;

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
        public TestAbstractOne2OneTool(final Logger logger, final String toolName) {

            super(logger, toolName);
            this.one2OneService = null;

        }

        /**
         * カスタム1入力ファイルから1出力ファイルへの変換ツールサービスを使用するコンストラクタ
         *
         * @since 0.1.0
         *
         * @param one2OneService
         *                       1入力ファイルから1出力ファイルへの変換ツールサービス
         */
        public TestAbstractOne2OneTool(final One2OneService one2OneService) {

            super("テストツール");
            this.one2OneService = one2OneService;

        }

        /**
         * 1入力ファイルから1出力ファイルへの変換ツールサービスを取得します。
         *
         * @since 0.1.0
         *
         * @return 1入力ファイルから1出力ファイルへの変換ツールサービス
         */
        @Override
        protected One2OneService getIoService() {

            final One2OneService result = this.one2OneService;
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
    private TestAbstractOne2OneTool testTarget;

    /**
     * モック1入力ファイルから1出力ファイルへの変換ツールサービス
     *
     * @since 0.1.0
     */
    private One2OneService mockOne2OneService;

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

        this.mockOne2OneService = Mockito.mock(One2OneService.class);
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.testTarget = new TestAbstractOne2OneTool(this.mockOne2OneService);

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
        final TestAbstractOne2OneTool testInstance = new TestAbstractOne2OneTool(customLogger, "カスタムツール");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "カスタムロガーを使用するコンストラクタでインスタンスが作成されること");

    }

    /**
     * コンストラクタのテスト - 正常系：カスタム1入力ファイルから1出力ファイルへの変換ツールサービスを使用するコンストラクタで初期化する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCustomOne2OneServiceConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final One2OneService customOne2OneService = Mockito.mock(One2OneService.class);

        /* テスト対象の実行 */
        final TestAbstractOne2OneTool testInstance = new TestAbstractOne2OneTool(customOne2OneService);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "カスタム1入力ファイルから1出力ファイルへの変換ツールサービスを使用するコンストラクタでインスタンスが作成されること");

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
        final TestAbstractOne2OneTool testInstance = new TestAbstractOne2OneTool();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "デフォルトコンストラクタでインスタンスが作成されること");

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
        final TestAbstractOne2OneTool testInstance = new TestAbstractOne2OneTool();

        /* テスト対象の実行 */
        final One2OneService testResult = testInstance.getIoService();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNull(testResult, "デフォルトコンストラクタの場合、nullが返されること");

    }

    /**
     * getIoService メソッドのテスト - 正常系：カスタム1入力ファイルから1出力ファイルへの変換ツールサービスを設定した場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_normalGet() throws Exception {

        /* 期待値の定義 */
        final One2OneService expected = this.mockOne2OneService;

        /* 準備 */

        /* テスト対象の実行 */
        final One2OneService testResult = this.testTarget.getIoService();

        /* 検証の準備 */
        final One2OneService actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "設定した1入力ファイルから1出力ファイルへの変換ツールサービスが正しく返されること");

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
            Mockito.when(
                this.mockOne2OneService.initialize(ArgumentMatchers.any(Path.class), ArgumentMatchers.any(Path.class)))
                .thenThrow(exception);

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
        Mockito
            .when(
                this.mockOne2OneService.initialize(ArgumentMatchers.any(Path.class), ArgumentMatchers.any(Path.class)))
            .thenReturn(true);
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
        Assertions.assertFalse(actual, "初期化が成功した場合、falseが返されること");

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
        Mockito
            .when(
                this.mockOne2OneService.initialize(ArgumentMatchers.any(Path.class), ArgumentMatchers.any(Path.class)))
            .thenReturn(false);
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
