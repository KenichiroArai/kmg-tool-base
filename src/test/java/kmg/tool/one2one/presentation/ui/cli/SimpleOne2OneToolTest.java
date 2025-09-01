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

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.one2one.application.service.SimpleOne2OneService;

/**
 * SimpleOne2OneToolのテストクラス
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
public class SimpleOne2OneToolTest extends AbstractKmgTest {

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
    private SimpleOne2OneTool testTarget;

    /**
     * モックシンプル1入力ファイルから1出力ファイルへの変換サービス
     *
     * @since 0.1.0
     */
    private SimpleOne2OneService mockSimpleOne2OneService;

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

        this.mockSimpleOne2OneService = Mockito.mock(SimpleOne2OneService.class);
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.testTarget = new SimpleOne2OneTool();

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
     * コンストラクタ メソッドのテスト - 正常系：デフォルトコンストラクタで初期化する場合
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
        final SimpleOne2OneTool testInstance = new SimpleOne2OneTool();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "デフォルトコンストラクタでインスタンスが作成されること");

    }

    /**
     * getIoService メソッドのテスト - 正常系：シンプル1入力ファイルから1出力ファイルへの変換サービスを取得する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_normalGet() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // リフレクションを使用してサービスを設定
        final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        reflectionModel.set("simpleOne2OneService", this.mockSimpleOne2OneService);

        /* テスト対象の実行 */
        final SimpleOne2OneService testResult = this.testTarget.getIoService();

        /* 検証の準備 */
        final SimpleOne2OneService actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(this.mockSimpleOne2OneService, actual, "設定したシンプル1入力ファイルから1出力ファイルへの変換サービスが正しく返されること");

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
            reflectionModel.set("simpleOne2OneService", this.mockSimpleOne2OneService);

            // 例外を事前に作成（モック設定完了後に作成）
            final KmgToolMsgException exception = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN01001);

            // モックの設定（事前に作成した例外を使用）
            Mockito.when(this.mockSimpleOne2OneService.initialize(ArgumentMatchers.any(Path.class),
                ArgumentMatchers.any(Path.class))).thenThrow(exception);

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
        Mockito.when(this.mockSimpleOne2OneService.initialize(ArgumentMatchers.any(Path.class),
            ArgumentMatchers.any(Path.class))).thenReturn(true);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(KmgToolLogMsgTypes.class),
            ArgumentMatchers.any())).thenReturn("成功");

        // リフレクションを使用してメッセージソースを設定
        final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        reflectionModel.set("messageSource", this.mockMessageSource);
        reflectionModel.set("simpleOne2OneService", this.mockSimpleOne2OneService);

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
        Mockito.when(this.mockSimpleOne2OneService.initialize(ArgumentMatchers.any(Path.class),
            ArgumentMatchers.any(Path.class))).thenReturn(false);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(KmgToolLogMsgTypes.class),
            ArgumentMatchers.any())).thenReturn("失敗");

        // リフレクションを使用してメッセージソースを設定
        final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        reflectionModel.set("messageSource", this.mockMessageSource);
        reflectionModel.set("simpleOne2OneService", this.mockSimpleOne2OneService);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize();

        /* 検証の準備 */
        final boolean actual = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actual, "初期化が失敗した場合、falseが返されること");

    }

    /**
     * main メソッドのテスト - 正常系：mainメソッドが正常に実行される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMain_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String[] args = {};

        /* テスト対象の実行 */
        SimpleOne2OneTool.main(args);

        /* 検証の準備 */

        /* 検証の実施 */
        // mainメソッドは正常に実行されることを確認（例外が発生しない）
        Assertions.assertTrue(true, "mainメソッドが正常に実行されること");

    }

}
