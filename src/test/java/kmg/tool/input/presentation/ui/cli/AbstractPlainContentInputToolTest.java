package kmg.tool.input.presentation.ui.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.input.domain.service.PlainContentInputServic;

/**
 * AbstractPlainContentInputToolのテストクラス
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings({
    "nls", "static-method"
})
public class AbstractPlainContentInputToolTest extends AbstractKmgTest {

    /** テスト用のAbstractPlainContentInputTool実装クラス */
    private static class TestAbstractPlainContentInputTool extends AbstractPlainContentInputTool {

        /**
         * プレーンコンテンツ入力サービス
         *
         * @since 0.1.0
         */
        private final PlainContentInputServic plainContentInputService;

        /**
         * デフォルトコンストラクタ
         *
         * @since 0.1.0
         */
        public TestAbstractPlainContentInputTool() {

            this.plainContentInputService = null;

        }

        /**
         * カスタムプレーンコンテンツ入力サービスを使用するコンストラクタ
         *
         * @since 0.1.0
         *
         * @param plainContentInputService
         *                                 プレーンコンテンツ入力サービス
         */
        public TestAbstractPlainContentInputTool(final PlainContentInputServic plainContentInputService) {

            this.plainContentInputService = plainContentInputService;

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
         * プレーンコンテンツ入力サービスを取得します。
         *
         * @since 0.1.0
         *
         * @return プレーンコンテンツ入力サービス
         */
        @Override
        public PlainContentInputServic getInputService() {

            final PlainContentInputServic result = this.plainContentInputService;
            return result;

        }
    }

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private TestAbstractPlainContentInputTool testTarget;

    /**
     * モックプレーンコンテンツ入力サービス
     *
     * @since 0.1.0
     */
    private PlainContentInputServic mockPlainContentInputService;

    /**
     * セットアップ
     *
     * @since 0.1.0
     */
    @BeforeEach
    public void setUp() {

        this.mockPlainContentInputService = Mockito.mock(PlainContentInputServic.class);
        this.testTarget = new TestAbstractPlainContentInputTool(this.mockPlainContentInputService);

    }

    /**
     * クリーンアップ
     *
     * @since 0.1.0
     */
    @AfterEach
    public void tearDown() {

        if (this.testTarget != null) {

            Mockito.reset(this.mockPlainContentInputService);

        }

    }

    /**
     * コンストラクタのテスト - 正常系：カスタムプレーンコンテンツ入力サービスを使用するコンストラクタで初期化する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCustomPlainContentInputServiceConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final PlainContentInputServic customPlainContentInputService = Mockito.mock(PlainContentInputServic.class);

        /* テスト対象の実行 */
        final TestAbstractPlainContentInputTool testInstance
            = new TestAbstractPlainContentInputTool(customPlainContentInputService);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "カスタムプレーンコンテンツ入力サービスを使用するコンストラクタでインスタンスが作成されること");

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
        final TestAbstractPlainContentInputTool testInstance = new TestAbstractPlainContentInputTool();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "デフォルトコンストラクタでインスタンスが作成されること");

    }

    /**
     * getContent メソッドのテスト - 正常系：内容が正常に取得される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetContent_normalGet() throws Exception {

        /* 期待値の定義 */
        final String expected = "test content";

        /* 準備 */
        // リフレクションを使用してprivateフィールドに値を設定
        final java.lang.reflect.Field contentField = AbstractPlainContentInputTool.class.getDeclaredField("content");
        contentField.setAccessible(true);
        contentField.set(this.testTarget, expected);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getContent();

        /* 検証の準備 */
        final String actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "内容が正しく取得されること");

    }

    /**
     * getContent メソッドのテスト - 正常系：内容がnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetContent_normalNull() throws Exception {

        /* 期待値の定義 */
        final String expected = null;

        /* 準備 */
        // リフレクションを使用してprivateフィールドにnullを設定
        final java.lang.reflect.Field contentField = AbstractPlainContentInputTool.class.getDeclaredField("content");
        contentField.setAccessible(true);
        contentField.set(this.testTarget, expected);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getContent();

        /* 検証の準備 */
        final String actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "内容がnullの場合、nullが返されること");

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
        final TestAbstractPlainContentInputTool defaultTestTarget = new TestAbstractPlainContentInputTool();

        /* テスト対象の実行 */
        final PlainContentInputServic testResult = defaultTestTarget.getInputService();

        /* 検証の準備 */
        final PlainContentInputServic actual = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actual, "デフォルトコンストラクタで初期化した場合、nullが返されること");

    }

    /**
     * getInputService メソッドのテスト - 正常系：プレーンコンテンツ入力サービスが正常に取得される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetInputService_normalGet() throws Exception {

        /* 期待値の定義 */
        final PlainContentInputServic expected = this.mockPlainContentInputService;

        /* 準備 */

        /* テスト対象の実行 */
        final PlainContentInputServic testResult = this.testTarget.getInputService();

        /* 検証の準備 */
        final PlainContentInputServic actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "プレーンコンテンツ入力サービスが正しく取得されること");

    }

    /**
     * loadPlainContent メソッドのテスト - 異常系：getContentでKmgToolMsgExceptionが発生する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadPlainContent_errorGetContentException() throws Exception {

        /* 期待値の定義 */
        final Path inputPath = Paths.get("test/input.txt");

        /* 準備 */
        Mockito.reset(this.mockPlainContentInputService);
        Mockito.when(this.mockPlainContentInputService.initialize(ArgumentMatchers.any(Path.class))).thenReturn(true);
        Mockito.when(this.mockPlainContentInputService.process()).thenReturn(true);
        Mockito.when(this.mockPlainContentInputService.getContent()).thenThrow(new RuntimeException("test error"));

        /* テスト対象の実行 */

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertThrows(RuntimeException.class, () -> {

            this.testTarget.loadPlainContent(inputPath);

        }, "getContentでRuntimeExceptionが発生する場合、例外が投げられること");

    }

    /**
     * loadPlainContent メソッドのテスト - 異常系：initializeでKmgToolMsgExceptionが発生する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadPlainContent_errorInitializeException() throws Exception {

        /* 期待値の定義 */
        final Path inputPath = Paths.get("test/input.txt");

        /* 準備 */
        Mockito.reset(this.mockPlainContentInputService);
        Mockito.when(this.mockPlainContentInputService.initialize(ArgumentMatchers.any(Path.class)))
            .thenThrow(new RuntimeException("test error"));

        /* テスト対象の実行 */

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertThrows(RuntimeException.class, () -> {

            this.testTarget.loadPlainContent(inputPath);

        }, "initializeでRuntimeExceptionが発生する場合、例外が投げられること");

    }

    /**
     * loadPlainContent メソッドのテスト - 異常系：processでKmgToolMsgExceptionが発生する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadPlainContent_errorProcessException() throws Exception {

        /* 期待値の定義 */
        final Path inputPath = Paths.get("test/input.txt");

        /* 準備 */
        Mockito.reset(this.mockPlainContentInputService);
        Mockito.when(this.mockPlainContentInputService.initialize(ArgumentMatchers.any(Path.class))).thenReturn(true);
        Mockito.when(this.mockPlainContentInputService.process()).thenThrow(new RuntimeException("test error"));

        /* テスト対象の実行 */

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertThrows(RuntimeException.class, () -> {

            this.testTarget.loadPlainContent(inputPath);

        }, "processでRuntimeExceptionが発生する場合、例外が投げられること");

    }

    /**
     * loadPlainContent メソッドのテスト - 正常系：プレーンコンテンツが正常に読み込まれる場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadPlainContent_normalLoad() throws Exception {

        /* 期待値の定義 */
        final String expectedContent = "test content";
        final Path   inputPath       = Paths.get("test/input.txt");

        /* 準備 */
        Mockito.reset(this.mockPlainContentInputService);
        Mockito.when(this.mockPlainContentInputService.initialize(ArgumentMatchers.any(Path.class))).thenReturn(true);
        Mockito.when(this.mockPlainContentInputService.process()).thenReturn(true);
        Mockito.when(this.mockPlainContentInputService.getContent()).thenReturn(expectedContent);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.loadPlainContent(inputPath);

        /* 検証の準備 */
        final boolean actual        = testResult;
        final String  actualContent = this.testTarget.getContent();

        /* 検証の実施 */
        Assertions.assertTrue(actual, "プレーンコンテンツが正常に読み込まれる場合、trueが返されること");
        Assertions.assertEquals(expectedContent, actualContent, "内容が正しく設定されること");

    }

    /**
     * loadPlainContent メソッドのテスト - 準正常系：initializeがfalseを返す場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadPlainContent_semiInitializeFalse() throws Exception {

        /* 期待値の定義 */
        final Path inputPath = Paths.get("test/input.txt");

        /* 準備 */
        Mockito.reset(this.mockPlainContentInputService);
        Mockito.when(this.mockPlainContentInputService.initialize(ArgumentMatchers.any(Path.class))).thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.loadPlainContent(inputPath);

        /* 検証の準備 */
        final boolean actual = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actual, "initializeがfalseを返す場合、falseが返されること");

    }

    /**
     * loadPlainContent メソッドのテスト - 準正常系：processがfalseを返す場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadPlainContent_semiProcessFalse() throws Exception {

        /* 期待値の定義 */
        final Path inputPath = Paths.get("test/input.txt");

        /* 準備 */
        Mockito.reset(this.mockPlainContentInputService);
        Mockito.when(this.mockPlainContentInputService.initialize(ArgumentMatchers.any(Path.class))).thenReturn(true);
        Mockito.when(this.mockPlainContentInputService.process()).thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.loadPlainContent(inputPath);

        /* 検証の準備 */
        final boolean actual = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actual, "processがfalseを返す場合、falseが返されること");

    }

}
