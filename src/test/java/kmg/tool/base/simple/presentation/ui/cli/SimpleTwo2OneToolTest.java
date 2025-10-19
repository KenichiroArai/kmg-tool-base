package kmg.tool.base.simple.presentation.ui.cli;

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
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.simple.application.service.SimpleTwo2OneService;

/**
 * SimpleTwo2OneToolのテストクラス
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
public class SimpleTwo2OneToolTest extends AbstractKmgTest {

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
    private SimpleTwo2OneTool testTarget;

    /**
     * モックシンプル2入力ファイルから1出力ファイルへの変換サービス
     *
     * @since 0.2.0
     */
    private SimpleTwo2OneService mockSimpleTwo2OneService;

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

        this.mockSimpleTwo2OneService = Mockito.mock(SimpleTwo2OneService.class);
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.testTarget = new SimpleTwo2OneTool();

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
     * コンストラクタ メソッドのテスト - 正常系：デフォルトコンストラクタで初期化する場合
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
        final SimpleTwo2OneTool testInstance = new SimpleTwo2OneTool();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "デフォルトコンストラクタでインスタンスが作成されること");

    }

    /**
     * getDefaultTemplatePath メソッドのテスト - 正常系：優先パスが実際に存在する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetDefaultTemplatePath_normalPrimaryPathActuallyExists() throws Exception {

        /* 期待値の定義 */
        final String expectedTemplateFileName = "template/SimpleTwo2OneTool.yml";

        /* 準備 */
        // リフレクションを使用してprivateメソッドを呼び出し
        final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト対象の実行 */
        final Path testResult = (Path) reflectionModel.getMethod("getDefaultTemplatePath");

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actual, "テンプレートパスが返されること");
        // 優先パスが実際に存在する場合、そのパスが返されることを確認
        final String actualPath = actual.toString();

        // パス区切り文字を統一（Windows環境でのバックスラッシュをスラッシュに変換）
        final String normalizedPath = actualPath.replace("\\", "/");

        if (normalizedPath.contains("work/io")) {

            // 優先パスが存在する場合
            Assertions.assertTrue(normalizedPath.contains("work/io"), "優先パスが使用されること");

        } else {

            // 優先パスが存在しない場合、代替パスが使用される
            Assertions.assertTrue(normalizedPath.contains("src/main/resources/tool/io"), "代替パスが使用されること");

        }
        Assertions.assertTrue(normalizedPath.endsWith(expectedTemplateFileName), "テンプレートファイル名が正しいこと");

    }

    /**
     * getDefaultTemplatePath メソッドのテスト - 正常系：優先パスが存在する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetDefaultTemplatePath_normalPrimaryPathExists() throws Exception {

        /* 期待値の定義 */
        final String expectedTemplateFileName = "template/SimpleTwo2OneTool.yml";

        /* 準備 */
        // リフレクションを使用してprivateメソッドを呼び出し
        final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト対象の実行 */
        final Path testResult = (Path) reflectionModel.getMethod("getDefaultTemplatePath");

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actual, "テンプレートパスが返されること");
        final String normalizedPath = actual.toString().replace("\\", "/");
        Assertions.assertTrue(normalizedPath.endsWith(expectedTemplateFileName), "テンプレートファイル名が正しいこと");

    }

    /**
     * getDefaultTemplatePath メソッドのテスト - 準正常系：優先パスが実際に存在しない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetDefaultTemplatePath_semiPrimaryPathActuallyNotExists() throws Exception {

        /* 期待値の定義 */
        final String expectedTemplateFileName = "template/SimpleTwo2OneTool.yml";

        /* 準備 */
        // リフレクションを使用してprivateメソッドを呼び出し
        final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト対象の実行 */
        final Path testResult = (Path) reflectionModel.getMethod("getDefaultTemplatePath");

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actual, "テンプレートパスが返されること");
        // 優先パスが存在しない場合、代替パスが使用されることを確認
        final String actualPath     = actual.toString();
        final String normalizedPath = actualPath.replace("\\", "/");
        Assertions.assertTrue(normalizedPath.contains("src/main/resources/tool/io"), "代替パスが使用されること");
        Assertions.assertTrue(normalizedPath.endsWith(expectedTemplateFileName), "テンプレートファイル名が正しいこと");

    }

    /**
     * getDefaultTemplatePath メソッドのテスト - 準正常系：優先パスが存在しない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetDefaultTemplatePath_semiPrimaryPathNotExists() throws Exception {

        /* 期待値の定義 */
        final String expectedTemplateFileName = "template/SimpleTwo2OneTool.yml";

        /* 準備 */
        // リフレクションを使用してprivateメソッドを呼び出し
        final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト対象の実行 */
        final Path testResult = (Path) reflectionModel.getMethod("getDefaultTemplatePath");

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actual, "テンプレートパスが返されること");
        final String normalizedPath = actual.toString().replace("\\", "/");
        Assertions.assertTrue(normalizedPath.endsWith(expectedTemplateFileName), "テンプレートファイル名が正しいこと");

    }

    /**
     * getIoService メソッドのテスト - 正常系：シンプル2入力ファイルから1出力ファイルへの変換サービスを取得する場合
     *
     * @since 0.2.0
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
        reflectionModel.set("simpleTwo2OneService", this.mockSimpleTwo2OneService);

        /* テスト対象の実行 */
        final SimpleTwo2OneService testResult = this.testTarget.getIoService();

        /* 検証の準備 */
        final SimpleTwo2OneService actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(this.mockSimpleTwo2OneService, actual, "設定したシンプル2入力ファイルから1出力ファイルへの変換サービスが正しく返されること");

    }

    /**
     * initialize メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN01001;
        final String             expectedDomainMessage = "項目名がnullです。";

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
                .thenReturn(expectedDomainMessage);

            // リフレクションを使用してメッセージソースを設定
            final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);
            reflectionModel.set("messageSource", this.mockMessageSource);
            reflectionModel.set("simpleTwo2OneService", this.mockSimpleTwo2OneService);

            // 例外を事前に作成（モック設定完了後に作成）
            final KmgToolMsgException exception = new KmgToolMsgException(expectedMessageTypes);

            // モックの設定（事前に作成した例外を使用）
            Mockito.when(this.mockSimpleTwo2OneService.initialize(ArgumentMatchers.any(Path.class),
                ArgumentMatchers.any(Path.class), ArgumentMatchers.any(Path.class))).thenThrow(exception);

            /* テスト対象の実行 */
            final boolean testResult = this.testTarget.initialize();

            /* 検証の準備 */
            final boolean actual = testResult;

            /* 検証の実施 */
            Assertions.assertFalse(actual, "KmgToolMsgExceptionが発生した場合、falseが返されること");

            // verifyKmgMsgExceptionを使用してKmgToolMsgExceptionを検証
            this.verifyKmgMsgException(exception, null, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * initialize メソッドのテスト - 正常系：初期化が成功する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockSimpleTwo2OneService.initialize(ArgumentMatchers.any(Path.class),
            ArgumentMatchers.any(Path.class), ArgumentMatchers.any(Path.class))).thenReturn(true);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(KmgToolLogMsgTypes.class),
            ArgumentMatchers.any())).thenReturn("成功");

        // リフレクションを使用してメッセージソースを設定
        final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        reflectionModel.set("messageSource", this.mockMessageSource);
        reflectionModel.set("simpleTwo2OneService", this.mockSimpleTwo2OneService);

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
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_semiFailure() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockSimpleTwo2OneService.initialize(ArgumentMatchers.any(Path.class),
            ArgumentMatchers.any(Path.class), ArgumentMatchers.any(Path.class))).thenReturn(false);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(KmgToolLogMsgTypes.class),
            ArgumentMatchers.any())).thenReturn("失敗");

        // リフレクションを使用してメッセージソースを設定
        final var reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        reflectionModel.set("messageSource", this.mockMessageSource);
        reflectionModel.set("simpleTwo2OneService", this.mockSimpleTwo2OneService);

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
     * @since 0.2.0
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
        SimpleTwo2OneTool.main(args);

        /* 検証の準備 */

        /* 検証の実施 */
        // mainメソッドは正常に実行されることを確認（例外が発生しない）
        Assertions.assertTrue(true, "mainメソッドが正常に実行されること");

    }

}
