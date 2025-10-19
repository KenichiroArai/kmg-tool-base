package kmg.tool.base.msgtpcrt.presentation.ul.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.base.dtc.presentation.ui.cli.AbstractDtcTool;
import kmg.tool.base.msgtpcrt.service.MessageTypesCreationService;

/**
 * <h2>メッセージの種類作成ツールのテスト</h2>
 * <p>
 * MessageTypesCreationToolのテストクラスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class MessageTypesCreationToolTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.2.0
     */
    @InjectMocks
    private MessageTypesCreationTool testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.2.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * MessageTypesCreationServiceのモック
     *
     * @since 0.2.0
     */
    @Mock
    private MessageTypesCreationService mockMessageTypesCreationService;

    /**
     * 各テスト実行前のセットアップ処理。リフレクションモデルの初期化とモックの注入を行う。
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @BeforeEach
    public void setUp() throws Exception {

        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        this.reflectionModel.set("messageTypesCreationService", this.mockMessageTypesCreationService);

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：デフォルトコンストラクタが正常に動作する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefault() throws Exception {

        /* 期待値の定義 */
        final String expectedToolName = "メッセージの種類作成ツール";

        /* 準備 */
        final MessageTypesCreationTool localTestTarget = new MessageTypesCreationTool();

        /* テスト対象の実行 */
        // コンストラクタの実行は準備で完了

        /* 検証の準備 */
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        final String                 actualToolName       = (String) localReflectionModel.get("TOOL_NAME");

        /* 検証の実施 */
        Assertions.assertEquals(expectedToolName, actualToolName, "ツール名が正しく設定されていること");

    }

    /**
     * getIoService メソッドのテスト - 正常系：メッセージの種類作成サービスが正常に返される場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final MessageTypesCreationService expectedMessageTypesCreationService
            = Mockito.mock(MessageTypesCreationService.class);

        /* 準備 */
        final MessageTypesCreationTool localTestTarget      = new MessageTypesCreationTool();
        final KmgReflectionModelImpl   localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageTypesCreationService", expectedMessageTypesCreationService);

        /* テスト対象の実行 */
        final MessageTypesCreationService actualMessageTypesCreationService = localTestTarget.getIoService();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedMessageTypesCreationService, actualMessageTypesCreationService,
            "メッセージの種類作成サービスが正しく返されること");

    }

    /**
     * メソッドの戻り値型テスト - 正常系：getIoServiceメソッドがMessageTypesCreationServiceを返す場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetIoServiceReturnType_normalMessageTypesCreationService() {

        /* 期待値の定義 */
        final Class<?> expectedReturnType = MessageTypesCreationService.class;

        /* 準備 */
        final MessageTypesCreationTool localTestTarget = new MessageTypesCreationTool();
        final Class<?>                 testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Method   method           = testClass.getDeclaredMethod("getIoService");
            final Class<?> actualReturnType = method.getReturnType();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertSame(expectedReturnType, actualReturnType,
                "getIoServiceメソッドがMessageTypesCreationServiceを返すこと");

        } catch (final NoSuchMethodException e) {

            Assertions.fail("getIoServiceメソッドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * メソッドの可視性テスト - 正常系：getIoServiceメソッドがprotectedで定義されている場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetIoServiceVisibility_normalProtected() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PROTECTED;

        /* 準備 */
        final MessageTypesCreationTool localTestTarget = new MessageTypesCreationTool();
        final Class<?>                 testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Method method          = testClass.getDeclaredMethod("getIoService");
            final int    actualModifiers = method.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PROTECTED;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "getIoServiceメソッドがprotectedで定義されていること");

        } catch (final NoSuchMethodException e) {

            Assertions.fail("getIoServiceメソッドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * 継承関係のテスト - 正常系：AbstractDtcToolを正しく継承している場合
     *
     * @since 0.2.0
     */
    @Test
    public void testInheritance_normalExtendsAbstractDtcTool() {

        /* 期待値の定義 */
        final Class<?> expectedSuperClass = AbstractDtcTool.class;

        /* 準備 */
        final MessageTypesCreationTool localTestTarget = new MessageTypesCreationTool();

        /* テスト対象の実行 */
        final Class<?> actualSuperClass = localTestTarget.getClass().getSuperclass();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertSame(expectedSuperClass, actualSuperClass, "AbstractDtcToolを正しく継承していること");

    }

    /**
     * main メソッドのテスト - 正常系：メインメソッドが正常に実行される場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @SuppressWarnings("resource")
    @Test
    public void testMain_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // SpringApplicationをモック化
        try (final MockedStatic<SpringApplication> mockedSpringApplication
            = Mockito.mockStatic(SpringApplication.class)) {

            // ConfigurableApplicationContextをモック化
            final ConfigurableApplicationContext mockContext = Mockito.mock(ConfigurableApplicationContext.class);
            final MessageTypesCreationTool       mockTool    = Mockito.mock(MessageTypesCreationTool.class);

            // SpringApplication.runの戻り値をモック化
            mockedSpringApplication.when(() -> SpringApplication.run(MessageTypesCreationTool.class))
                .thenReturn(mockContext);

            // ctx.getBeanの戻り値をモック化
            Mockito.when(mockContext.getBean(MessageTypesCreationTool.class)).thenReturn(mockTool);

            // initializeメソッドとexecuteメソッドの戻り値をモック化
            Mockito.when(mockTool.initialize()).thenReturn(true);
            Mockito.when(mockTool.execute()).thenReturn(true);

            /* テスト対象の実行 */
            // mainメソッドを実際に呼び出し
            MessageTypesCreationTool.main(new String[] {});

            /* 検証の準備 */

            /* 検証の実施 */
            // mainメソッドが例外を投げずに正常に実行されることを確認
            Assertions.assertTrue(true, "mainメソッドが正常に実行されること");

            // モックが正しく呼び出されたことを検証
            Mockito.verify(mockContext).getBean(MessageTypesCreationTool.class);
            Mockito.verify(mockTool).initialize();
            Mockito.verify(mockTool).execute();
            Mockito.verify(mockContext).close();

        }

    }

    /**
     * main メソッドのテスト - 準正常系：引数がnullの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @SuppressWarnings("resource")
    @Test
    public void testMain_semiNullArgs() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // SpringApplicationをモック化
        try (final MockedStatic<SpringApplication> mockedSpringApplication
            = Mockito.mockStatic(SpringApplication.class)) {

            // ConfigurableApplicationContextをモック化
            final ConfigurableApplicationContext mockContext = Mockito.mock(ConfigurableApplicationContext.class);
            final MessageTypesCreationTool       mockTool    = Mockito.mock(MessageTypesCreationTool.class);

            // SpringApplication.runの戻り値をモック化（null引数）
            mockedSpringApplication.when(() -> SpringApplication.run(MessageTypesCreationTool.class, (String[]) null))
                .thenReturn(mockContext);

            // ctx.getBeanの戻り値をモック化
            Mockito.when(mockContext.getBean(MessageTypesCreationTool.class)).thenReturn(mockTool);

            // initializeメソッドとexecuteメソッドの戻り値をモック化
            Mockito.when(mockTool.initialize()).thenReturn(true);
            Mockito.when(mockTool.execute()).thenReturn(true);

            /* テスト対象の実行 */
            // mainメソッドを実際に呼び出し（null引数）
            MessageTypesCreationTool.main(null);

            /* 検証の準備 */

            /* 検証の実施 */
            // mainメソッドが例外を投げずに正常に実行されることを確認
            Assertions.assertTrue(true, "null引数でもmainメソッドが実行されること");

            // モックが正しく呼び出されたことを検証
            Mockito.verify(mockContext).getBean(MessageTypesCreationTool.class);
            Mockito.verify(mockTool).initialize();
            Mockito.verify(mockTool).execute();
            Mockito.verify(mockContext).close();

        }

    }

    /**
     * messageTypesCreationService フィールドのテスト - 正常系：メッセージの種類作成サービスが正しく注入される場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMessageTypesCreationService_normalInjection() throws Exception {

        /* 期待値の定義 */
        final MessageTypesCreationService expectedMessageTypesCreationService
            = Mockito.mock(MessageTypesCreationService.class);

        /* 準備 */
        final MessageTypesCreationTool localTestTarget      = new MessageTypesCreationTool();
        final KmgReflectionModelImpl   localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageTypesCreationService", expectedMessageTypesCreationService);

        /* テスト対象の実行 */
        final MessageTypesCreationService actualMessageTypesCreationService
            = (MessageTypesCreationService) localReflectionModel.get("messageTypesCreationService");

        /* 検証の準備 */
        final MessageTypesCreationService actualResult = actualMessageTypesCreationService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedMessageTypesCreationService, actualResult, "メッセージの種類作成サービスが正しく注入されていること");

    }

    /**
     * フィールドの型テスト - 正常系：messageTypesCreationServiceフィールドがMessageTypesCreationService型の場合
     *
     * @since 0.2.0
     */
    @Test
    public void testMessageTypesCreationServiceType_normalMessageTypesCreationService() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = MessageTypesCreationService.class;

        /* 準備 */
        final MessageTypesCreationTool localTestTarget = new MessageTypesCreationTool();
        final Class<?>                 testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("messageTypesCreationService");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */
            final Class<?> actualResult = actualFieldType;

            /* 検証の実施 */
            Assertions.assertSame(expectedFieldType, actualResult,
                "messageTypesCreationServiceフィールドがMessageTypesCreationService型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("messageTypesCreationServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * フィールドの可視性テスト - 正常系：messageTypesCreationServiceフィールドがprivateで定義されている場合
     *
     * @since 0.2.0
     */
    @Test
    public void testMessageTypesCreationServiceVisibility_normalPrivate() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE;

        /* 準備 */
        final MessageTypesCreationTool localTestTarget = new MessageTypesCreationTool();
        final Class<?>                 testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("messageTypesCreationService");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PRIVATE;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult,
                "messageTypesCreationServiceフィールドがprivateで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("messageTypesCreationServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * SpringBootApplication アノテーションのテスト - 正常系：アノテーションが正しく設定されている場合
     *
     * @since 0.2.0
     */
    @Test
    public void testSpringBootApplicationAnnotation_normalCorrect() {

        /* 期待値の定義 */

        /* 準備 */
        final MessageTypesCreationTool localTestTarget = new MessageTypesCreationTool();
        final Class<?>                 testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        final SpringBootApplication annotation = testClass.getAnnotation(SpringBootApplication.class);

        /* 検証の準備 */
        final boolean actualResult = (annotation != null) && "kmg".equals(annotation.scanBasePackages()[0]);

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "SpringBootApplicationアノテーションが正しく設定されていること");

    }

    /**
     * TOOL_NAME 定数のテスト - 正常系：ツール名が正しく定義されている場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testToolName_normalCorrectValue() throws Exception {

        /* 期待値の定義 */
        final String expectedToolName = "メッセージの種類作成ツール";

        /* 準備 */
        final MessageTypesCreationTool localTestTarget      = new MessageTypesCreationTool();
        final KmgReflectionModelImpl   localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        /* テスト対象の実行 */
        final String actualToolName = (String) localReflectionModel.get("TOOL_NAME");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedToolName, actualToolName, "TOOL_NAME定数が正しく定義されていること");

    }

    /**
     * 定数の型テスト - 正常系：TOOL_NAME定数がString型の場合
     *
     * @since 0.2.0
     */
    @Test
    public void testToolNameType_normalString() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = String.class;

        /* 準備 */
        final MessageTypesCreationTool localTestTarget = new MessageTypesCreationTool();
        final Class<?>                 testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("TOOL_NAME");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertSame(expectedFieldType, actualFieldType, "TOOL_NAME定数がString型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("TOOL_NAME定数が見つかりません: " + e.getMessage());

        }

    }

    /**
     * 定数の可視性テスト - 正常系：TOOL_NAME定数がprivate static finalで定義されている場合
     *
     * @since 0.2.0
     */
    @Test
    public void testToolNameVisibility_normalPrivateStaticFinal() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL;

        /* 準備 */
        final MessageTypesCreationTool localTestTarget = new MessageTypesCreationTool();
        final Class<?>                 testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("TOOL_NAME");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & (Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL);

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "TOOL_NAME定数がprivate static finalで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("TOOL_NAME定数が見つかりません: " + e.getMessage());

        }

    }

}
