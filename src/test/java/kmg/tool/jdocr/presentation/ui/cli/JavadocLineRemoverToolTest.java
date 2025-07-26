package kmg.tool.jdocr.presentation.ui.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.input.presentation.ui.cli.AbstractInputTool;
import kmg.tool.jdocr.service.JavadocLineRemoverService;
import kmg.tool.simple.domain.service.SimpleInputService;

/**
 * Javadoc行削除ツールのテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class JavadocLineRemoverToolTest extends AbstractKmgTest {

    /** テスト対象 */
    @InjectMocks
    private JavadocLineRemoverTool testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** KmgMessageSourceのモック */
    @Mock
    private KmgMessageSource mockMessageSource;

    /** SimpleInputServiceのモック */
    @Mock
    private SimpleInputService mockSimpleInputService;

    /** JavadocLineRemoverServiceのモック */
    @Mock
    private JavadocLineRemoverService mockJavadocLineRemoverService;

    /**
     * 各テスト実行前のセットアップ処理。リフレクションモデルの初期化とモックの注入を行う。
     *
     * @throws Exception
     *                   例外
     */
    @BeforeEach
    public void setUp() throws Exception {

        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("inputService", this.mockSimpleInputService);
        this.reflectionModel.set("javadocLineRemoverService", this.mockJavadocLineRemoverService);

    }

    /**
     * execute メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        final Path expectedPath = Paths.get("nonexistent.txt");

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget      = new JavadocLineRemoverTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        final SimpleInputService mockInputService = Mockito.mock(SimpleInputService.class);
        Mockito.when(mockInputService.getInputPath()).thenReturn(expectedPath);

        final JavadocLineRemoverService mockService = Mockito.mock(JavadocLineRemoverService.class);
        Mockito.when(mockService.initialize(ArgumentMatchers.any(Path.class))).thenReturn(true);

        // SpringApplicationContextHelperのモック化
        try (final org.mockito.MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource localMockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(localMockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(localMockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("[KMGTOOL_GEN01001] テストメッセージ");

            // KmgToolMsgExceptionを作成（モックのスコープ内で）
            final KmgToolMsgException testException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN01001, new Object[] {});
            Mockito.when(mockService.process()).thenThrow(testException);

            localReflectionModel.set("inputService", mockInputService);
            localReflectionModel.set("javadocLineRemoverService", mockService);
            localReflectionModel.set("messageSource", localMockMessageSource);

            /* テスト対象の実行 */
            final boolean actualResult = (Boolean) localReflectionModel.getMethod("execute");

            /* 検証の準備 */
            final boolean actualResultForVerify = actualResult;

            /* 検証の実施 */
            Assertions.assertFalse(actualResultForVerify, "例外が発生した場合、falseが返されること");

        }

    }

    /**
     * execute メソッドのテスト - 異常系：処理に失敗する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_errorProcessFailure() throws Exception {

        /* 期待値の定義 */
        final Path expectedPath = Paths.get("test.txt");

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget      = new JavadocLineRemoverTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        final SimpleInputService mockInputService = Mockito.mock(SimpleInputService.class);
        Mockito.when(mockInputService.getInputPath()).thenReturn(expectedPath);

        final JavadocLineRemoverService mockService = Mockito.mock(JavadocLineRemoverService.class);
        Mockito.when(mockService.initialize(ArgumentMatchers.any(Path.class))).thenReturn(true);
        Mockito.when(mockService.process()).thenReturn(false);

        final KmgMessageSource localMockMessageSource = Mockito.mock(KmgMessageSource.class);
        Mockito.when(localMockMessageSource.getGenMessage(ArgumentMatchers.any(KmgToolGenMsgTypes.class),
            ArgumentMatchers.any(Object[].class))).thenReturn("テストメッセージ");

        localReflectionModel.set("inputService", mockInputService);
        localReflectionModel.set("javadocLineRemoverService", mockService);
        localReflectionModel.set("messageSource", localMockMessageSource);

        /* テスト対象の実行 */
        final boolean actualResult = (Boolean) localReflectionModel.getMethod("execute");

        /* 検証の準備 */
        final boolean actualResultForVerify = actualResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResultForVerify, "処理に失敗した場合、falseが返されること");

    }

    /**
     * execute メソッドのテスト - 正常系：正常に処理が完了する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final Path expectedPath = Paths.get("test.txt");

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget      = new JavadocLineRemoverTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        final SimpleInputService mockInputService = Mockito.mock(SimpleInputService.class);
        Mockito.when(mockInputService.getInputPath()).thenReturn(expectedPath);

        final JavadocLineRemoverService mockService = Mockito.mock(JavadocLineRemoverService.class);
        Mockito.when(mockService.initialize(ArgumentMatchers.any(Path.class))).thenReturn(true);
        Mockito.when(mockService.process()).thenReturn(true);

        final KmgMessageSource localMockMessageSource = Mockito.mock(KmgMessageSource.class);
        Mockito.when(localMockMessageSource.getGenMessage(ArgumentMatchers.any(KmgToolGenMsgTypes.class),
            ArgumentMatchers.any(Object[].class))).thenReturn("テストメッセージ");

        localReflectionModel.set("inputService", mockInputService);
        localReflectionModel.set("javadocLineRemoverService", mockService);
        localReflectionModel.set("messageSource", localMockMessageSource);

        /* テスト対象の実行 */
        final boolean actualResult = (Boolean) localReflectionModel.getMethod("execute");

        /* 検証の準備 */
        final boolean actualResultForVerify = actualResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResultForVerify, "正常に処理が完了すること");

    }

    /**
     * execute メソッドのテスト - 準正常系：初期化に失敗する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_semiInitializeFailure() throws Exception {

        /* 期待値の定義 */
        final Path expectedPath = Paths.get("test.txt");

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget      = new JavadocLineRemoverTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        final SimpleInputService mockInputService = Mockito.mock(SimpleInputService.class);
        Mockito.when(mockInputService.getInputPath()).thenReturn(expectedPath);

        final JavadocLineRemoverService mockService = Mockito.mock(JavadocLineRemoverService.class);
        Mockito.when(mockService.initialize(ArgumentMatchers.any(Path.class))).thenReturn(false);

        final KmgMessageSource localMockMessageSource = Mockito.mock(KmgMessageSource.class);
        Mockito.when(localMockMessageSource.getGenMessage(ArgumentMatchers.any(KmgToolGenMsgTypes.class),
            ArgumentMatchers.any(Object[].class))).thenReturn("テストメッセージ");

        localReflectionModel.set("inputService", mockInputService);
        localReflectionModel.set("javadocLineRemoverService", mockService);
        localReflectionModel.set("messageSource", localMockMessageSource);

        /* テスト対象の実行 */
        final boolean actualResult = (Boolean) localReflectionModel.getMethod("execute");

        /* 検証の準備 */
        final boolean actualResultForVerify = actualResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResultForVerify, "初期化に失敗した場合、falseが返されること");

    }

    /**
     * getInputService メソッドのテスト - 正常系：入力サービスが正常に返される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetInputService_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final SimpleInputService expectedService = Mockito.mock(SimpleInputService.class);

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget      = new JavadocLineRemoverTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("inputService", expectedService);

        /* テスト対象の実行 */
        final SimpleInputService actualService = (SimpleInputService) localReflectionModel.getMethod("getInputService");

        /* 検証の準備 */
        final SimpleInputService actualResult = actualService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedService, actualResult, "入力サービスが正しく返されること");

    }

    /**
     * getInputService メソッドのテスト - 準正常系：入力サービスがnullの場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetInputService_semiNullService() throws Exception {

        /* 期待値の定義 */
        final SimpleInputService expectedService = null;

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget      = new JavadocLineRemoverTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("inputService", expectedService);

        /* テスト対象の実行 */
        final SimpleInputService actualService = (SimpleInputService) localReflectionModel.getMethod("getInputService");

        /* 検証の準備 */
        final SimpleInputService actualResult = actualService;

        /* 検証の実施 */
        Assertions.assertNull(actualResult, "nullの入力サービスが正しく返されること");

    }

    /**
     * メソッドの戻り値型テスト - 正常系：getInputServiceメソッドがSimpleInputServiceを返す場合
     *
     * @since 1.0.0
     */
    @Test
    public void testGetInputServiceReturnType_normalSimpleInputService() {

        /* 期待値の定義 */
        final Class<?> expectedReturnType = SimpleInputService.class;

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget = new JavadocLineRemoverTool();
        final Class<?>               testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Method   method           = testClass.getDeclaredMethod("getInputService");
            final Class<?> actualReturnType = method.getReturnType();

            /* 検証の準備 */
            final Class<?> actualResult = actualReturnType;

            /* 検証の実施 */
            Assertions.assertEquals(expectedReturnType, actualResult, "getInputServiceメソッドがSimpleInputServiceを返すこと");

        } catch (final NoSuchMethodException e) {

            Assertions.fail("getInputServiceメソッドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * メソッドの可視性テスト - 正常系：getInputServiceメソッドがpublicで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testGetInputServiceVisibility_normalPublic() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PUBLIC;

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget = new JavadocLineRemoverTool();
        final Class<?>               testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Method method          = testClass.getDeclaredMethod("getInputService");
            final int    actualModifiers = method.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PUBLIC;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "getInputServiceメソッドがpublicで定義されていること");

        } catch (final NoSuchMethodException e) {

            Assertions.fail("getInputServiceメソッドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * 継承関係のテスト - 正常系：AbstractInputToolを正しく継承している場合
     *
     * @since 1.0.0
     */
    @Test
    public void testInheritance_normalExtendsAbstractInputTool() {

        /* 期待値の定義 */
        final Class<?> expectedSuperClass = AbstractInputTool.class;

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget = new JavadocLineRemoverTool();

        /* テスト対象の実行 */
        final Class<?> actualSuperClass = localTestTarget.getClass().getSuperclass();

        /* 検証の準備 */
        final Class<?> actualResult = actualSuperClass;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSuperClass, actualResult, "AbstractInputToolを正しく継承していること");

    }

    /**
     * inputService フィールドのテスト - 正常系：シンプル入力サービスが正しく注入される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInputService_normalInjection() throws Exception {

        /* 期待値の定義 */
        final SimpleInputService expectedInputService = Mockito.mock(SimpleInputService.class);

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget      = new JavadocLineRemoverTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("inputService", expectedInputService);

        /* テスト対象の実行 */
        final SimpleInputService actualInputService = (SimpleInputService) localReflectionModel.get("inputService");

        /* 検証の準備 */
        final SimpleInputService actualResult = actualInputService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInputService, actualResult, "シンプル入力サービスが正しく注入されていること");

    }

    /**
     * フィールドの型テスト - 正常系：inputServiceフィールドがSimpleInputService型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testInputServiceType_normalSimpleInputService() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = SimpleInputService.class;

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget = new JavadocLineRemoverTool();
        final Class<?>               testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("inputService");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */
            final Class<?> actualResult = actualFieldType;

            /* 検証の実施 */
            Assertions.assertEquals(expectedFieldType, actualResult, "inputServiceフィールドがSimpleInputService型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("inputServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * フィールドの可視性テスト - 正常系：inputServiceフィールドがprivateで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testInputServiceVisibility_normalPrivate() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE;

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget = new JavadocLineRemoverTool();
        final Class<?>               testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("inputService");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PRIVATE;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "inputServiceフィールドがprivateで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("inputServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * javadocLineRemoverService フィールドのテスト - 正常系：Javadoc行削除サービスが正しく注入される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testJavadocLineRemoverService_normalInjection() throws Exception {

        /* 期待値の定義 */
        final JavadocLineRemoverService expectedService = Mockito.mock(JavadocLineRemoverService.class);

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget      = new JavadocLineRemoverTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("javadocLineRemoverService", expectedService);

        /* テスト対象の実行 */
        final JavadocLineRemoverService actualService
            = (JavadocLineRemoverService) localReflectionModel.get("javadocLineRemoverService");

        /* 検証の準備 */
        final JavadocLineRemoverService actualResult = actualService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedService, actualResult, "Javadoc行削除サービスが正しく注入されていること");

    }

    /**
     * フィールドの型テスト - 正常系：javadocLineRemoverServiceフィールドがJavadocLineRemoverService型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testJavadocLineRemoverServiceType_normalJavadocLineRemoverService() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = JavadocLineRemoverService.class;

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget = new JavadocLineRemoverTool();
        final Class<?>               testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("javadocLineRemoverService");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */
            final Class<?> actualResult = actualFieldType;

            /* 検証の実施 */
            Assertions.assertEquals(expectedFieldType, actualResult,
                "javadocLineRemoverServiceフィールドがJavadocLineRemoverService型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("javadocLineRemoverServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * フィールドの可視性テスト - 正常系：javadocLineRemoverServiceフィールドがprivateで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testJavadocLineRemoverServiceVisibility_normalPrivate() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE;

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget = new JavadocLineRemoverTool();
        final Class<?>               testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("javadocLineRemoverService");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PRIVATE;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult,
                "javadocLineRemoverServiceフィールドがprivateで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("javadocLineRemoverServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * main メソッドのテスト - 異常系：SpringBootアプリケーションの起動に失敗する場合
     *
     * @since 1.0.0
     */
    @Test
    public void testMain_errorSpringBootStartupFailure() {

        /* 期待値の定義 */
        final String[] expectedArgs = {
            "invalid", "arguments"
        };

        /* 準備 */
        // 無効な引数を準備

        /* テスト対象の実行 */
        JavadocLineRemoverTool.main(expectedArgs);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "無効な引数でもmainメソッドが実行されること");

    }

    /**
     * main メソッドのテスト - 正常系：メインメソッドが正常に実行される場合
     *
     * @since 1.0.0
     */
    @Test
    public void testMain_normalSuccess() {

        /* 期待値の定義 */
        final String[] expectedArgs = {};

        /* 準備 */
        // テスト用の引数を準備

        /* テスト対象の実行 */
        JavadocLineRemoverTool.main(expectedArgs);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "mainメソッドが正常に実行されること");

    }

    /**
     * main メソッドのテスト - 準正常系：引数がnullの場合
     *
     * @since 1.0.0
     */
    @Test
    public void testMain_semiNullArgs() {

        /* 期待値の定義 */
        final String[] expectedArgs = {};

        /* 準備 */
        // 空の引数を準備

        /* テスト対象の実行 */
        JavadocLineRemoverTool.main(expectedArgs);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "空の引数でもmainメソッドが実行されること");

    }

    /**
     * messageSource フィールドのテスト - 正常系：メッセージソースが正しく注入される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMessageSource_normalInjection() throws Exception {

        /* 期待値の定義 */
        final KmgMessageSource expectedMessageSource = Mockito.mock(KmgMessageSource.class);

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget      = new JavadocLineRemoverTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", expectedMessageSource);

        /* テスト対象の実行 */
        final KmgMessageSource actualMessageSource = (KmgMessageSource) localReflectionModel.get("messageSource");

        /* 検証の準備 */
        final KmgMessageSource actualResult = actualMessageSource;

        /* 検証の実施 */
        Assertions.assertEquals(expectedMessageSource, actualResult, "メッセージソースが正しく注入されていること");

    }

    /**
     * フィールドの型テスト - 正常系：messageSourceフィールドがKmgMessageSource型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testMessageSourceType_normalKmgMessageSource() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = KmgMessageSource.class;

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget = new JavadocLineRemoverTool();
        final Class<?>               testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("messageSource");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */
            final Class<?> actualResult = actualFieldType;

            /* 検証の実施 */
            Assertions.assertEquals(expectedFieldType, actualResult, "messageSourceフィールドがKmgMessageSource型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("messageSourceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * フィールドの可視性テスト - 正常系：messageSourceフィールドがprivateで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testMessageSourceVisibility_normalPrivate() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE;

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget = new JavadocLineRemoverTool();
        final Class<?>               testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("messageSource");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PRIVATE;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "messageSourceフィールドがprivateで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("messageSourceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * SpringBootApplication アノテーションのテスト - 正常系：アノテーションが正しく設定されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testSpringBootApplicationAnnotation_normalCorrect() {

        /* 期待値の定義 */

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget = new JavadocLineRemoverTool();
        final Class<?>               testClass       = localTestTarget.getClass();

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
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testToolName_normalCorrectValue() throws Exception {

        /* 期待値の定義 */
        final String expectedToolName = "Javadoc行削除ツール";

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget      = new JavadocLineRemoverTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        /* テスト対象の実行 */
        final String actualToolName = (String) localReflectionModel.get("TOOL_NAME");

        /* 検証の準備 */
        final String actualResult = actualToolName;

        /* 検証の実施 */
        Assertions.assertEquals(expectedToolName, actualResult, "TOOL_NAME定数が正しく定義されていること");

    }

    /**
     * 定数の型テスト - 正常系：TOOL_NAME定数がString型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testToolNameType_normalString() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = String.class;

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget = new JavadocLineRemoverTool();
        final Class<?>               testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("TOOL_NAME");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */
            final Class<?> actualResult = actualFieldType;

            /* 検証の実施 */
            Assertions.assertEquals(expectedFieldType, actualResult, "TOOL_NAME定数がString型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("TOOL_NAME定数が見つかりません: " + e.getMessage());

        }

    }

    /**
     * 定数の可視性テスト - 正常系：TOOL_NAME定数がprivate static finalで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testToolNameVisibility_normalPrivateStaticFinal() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL;

        /* 準備 */
        final JavadocLineRemoverTool localTestTarget = new JavadocLineRemoverTool();
        final Class<?>               testClass       = localTestTarget.getClass();

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
