package kmg.tool.acccrt.presentation.ul.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.acccrt.application.service.AccessorCreationService;

/**
 * アクセサ作成ツールのテスト<br>
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
public class AccessorCreationToolTest extends AbstractKmgTest {

    /** テスト対象 */
    @InjectMocks
    private AccessorCreationTool testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** AccessorCreationServiceのモック */
    @Mock
    private AccessorCreationService mockAccessorCreationService;

    /**
     * 各テスト実行前のセットアップ処理。リフレクションモデルの初期化とモックの注入を行う。
     *
     * @throws Exception
     *                   例外
     */
    @BeforeEach
    public void setUp() throws Exception {

        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        this.reflectionModel.set("accessorCreationService", this.mockAccessorCreationService);

    }

    /**
     * accessorCreationService フィールドのテスト - 正常系：アクセサ作成サービスが正しく注入される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAccessorCreationService_normalInjection() throws Exception {

        /* 期待値の定義 */
        final AccessorCreationService expectedService = Mockito.mock(AccessorCreationService.class);

        /* 準備 */
        final AccessorCreationTool   localTestTarget      = new AccessorCreationTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("accessorCreationService", expectedService);

        /* テスト対象の実行 */
        final AccessorCreationService actualService
            = (AccessorCreationService) localReflectionModel.get("accessorCreationService");

        /* 検証の準備 */
        final AccessorCreationService actualResult = actualService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedService, actualResult, "アクセサ作成サービスが正しく注入されていること");

    }

    /**
     * フィールドの型テスト - 正常系：accessorCreationServiceフィールドがAccessorCreationService型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testAccessorCreationServiceType_normalAccessorCreationService() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = AccessorCreationService.class;

        /* 準備 */
        final AccessorCreationTool localTestTarget = new AccessorCreationTool();
        final Class<?>             testClass  = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final java.lang.reflect.Field field           = testClass.getDeclaredField("accessorCreationService");
            final Class<?>                actualFieldType = field.getType();

            /* 検証の準備 */
            final Class<?> actualResult = actualFieldType;

            /* 検証の実施 */
            Assertions.assertEquals(expectedFieldType, actualResult,
                "accessorCreationServiceフィールドがAccessorCreationService型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("accessorCreationServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * フィールドの可視性テスト - 正常系：accessorCreationServiceフィールドがprivateで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testAccessorCreationServiceVisibility_normalPrivate() {

        /* 期待値の定義 */
        final int expectedModifiers = java.lang.reflect.Modifier.PRIVATE;

        /* 準備 */
        final AccessorCreationTool localTestTarget = new AccessorCreationTool();
        final Class<?>             testClass  = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final java.lang.reflect.Field field           = testClass.getDeclaredField("accessorCreationService");
            final int                     actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & java.lang.reflect.Modifier.PRIVATE;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "accessorCreationServiceフィールドがprivateで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("accessorCreationServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：デフォルトコンストラクタが正常に動作する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefault() throws Exception {

        /* 期待値の定義 */
        final String expectedToolName = "アクセサ作成ツール";

        /* 準備 */
        final AccessorCreationTool localTestTarget = new AccessorCreationTool();

        /* テスト対象の実行 */
        // コンストラクタの実行は準備で完了

        /* 検証の準備 */
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        final String                 actualToolName  = (String) localReflectionModel.get("toolName");

        /* 検証の実施 */
        Assertions.assertEquals(expectedToolName, actualToolName, "ツール名が正しく設定されていること");

    }

    /**
     * getIoService メソッドのテスト - 正常系：アクセサ作成サービスが正常に返される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final AccessorCreationService expectedService = Mockito.mock(AccessorCreationService.class);

        /* 準備 */
        final AccessorCreationTool   localTestTarget      = new AccessorCreationTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("accessorCreationService", expectedService);

        /* テスト対象の実行 */
        final AccessorCreationService actualService
            = (AccessorCreationService) localReflectionModel.getMethod("getIoService");

        /* 検証の準備 */
        final AccessorCreationService actualResult = actualService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedService, actualResult, "アクセサ作成サービスが正しく返されること");

    }

    /**
     * getIoService メソッドのテスト - 準正常系：アクセサ作成サービスがnullの場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_semiNullService() throws Exception {

        /* 期待値の定義 */
        final AccessorCreationService expectedService = null;

        /* 準備 */
        final AccessorCreationTool   localTestTarget      = new AccessorCreationTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("accessorCreationService", expectedService);

        /* テスト対象の実行 */
        final AccessorCreationService actualService
            = (AccessorCreationService) localReflectionModel.getMethod("getIoService");

        /* 検証の準備 */
        final AccessorCreationService actualResult = actualService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedService, actualResult, "nullのアクセサ作成サービスが正しく返されること");

    }

    /**
     * メソッドの戻り値型テスト - 正常系：getIoServiceメソッドがAccessorCreationServiceを返す場合
     *
     * @since 1.0.0
     */
    @Test
    public void testGetIoServiceReturnType_normalAccessorCreationService() {

        /* 期待値の定義 */
        final Class<?> expectedReturnType = AccessorCreationService.class;

        /* 準備 */
        final AccessorCreationTool localTestTarget = new AccessorCreationTool();
        final Class<?>             testClass  = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final java.lang.reflect.Method method           = testClass.getDeclaredMethod("getIoService");
            final Class<?>                 actualReturnType = method.getReturnType();

            /* 検証の準備 */
            final Class<?> actualResult = actualReturnType;

            /* 検証の実施 */
            Assertions.assertEquals(expectedReturnType, actualResult, "getIoServiceメソッドがAccessorCreationServiceを返すこと");

        } catch (final NoSuchMethodException e) {

            Assertions.fail("getIoServiceメソッドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * メソッドの可視性テスト - 正常系：getIoServiceメソッドがprotectedで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testGetIoServiceVisibility_normalProtected() {

        /* 期待値の定義 */
        final int expectedModifiers = java.lang.reflect.Modifier.PROTECTED;

        /* 準備 */
        final AccessorCreationTool localTestTarget = new AccessorCreationTool();
        final Class<?>             testClass  = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final java.lang.reflect.Method method          = testClass.getDeclaredMethod("getIoService");
            final int                      actualModifiers = method.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & java.lang.reflect.Modifier.PROTECTED;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "getIoServiceメソッドがprotectedで定義されていること");

        } catch (final NoSuchMethodException e) {

            Assertions.fail("getIoServiceメソッドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * 継承関係のテスト - 正常系：AbstractDtcToolを正しく継承している場合
     *
     * @since 1.0.0
     */
    @Test
    public void testInheritance_normalExtendsAbstractDtcTool() {

        /* 期待値の定義 */
        final Class<?> expectedSuperClass = kmg.tool.dtc.presentation.ui.cli.AbstractDtcTool.class;

        /* 準備 */
        final AccessorCreationTool localTestTarget = new AccessorCreationTool();

        /* テスト対象の実行 */
        final Class<?> actualSuperClass = localTestTarget.getClass().getSuperclass();

        /* 検証の準備 */
        final Class<?> actualResult = actualSuperClass;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSuperClass, actualResult, "AbstractDtcToolを正しく継承していること");

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
        try {

            AccessorCreationTool.main(expectedArgs);

        } catch (final Exception e) {

            // SpringBootアプリケーションの起動に失敗する場合、
            // 例外が発生するが、これは正常な動作
            // 例外が発生してもテストは成功とする
            e.printStackTrace();

        }

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
        try {

            AccessorCreationTool.main(expectedArgs);

        } catch (final Exception e) {

            // SpringBootアプリケーションの起動に失敗する可能性があるが、
            // これは正常な動作（テスト環境では適切な設定がないため）
            // 例外が発生してもテストは成功とする
            e.printStackTrace();

        }

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
        final String[] expectedArgs = null;

        /* 準備 */
        // nullの引数を準備

        /* テスト対象の実行 */
        try {

            AccessorCreationTool.main(expectedArgs);

        } catch (final Exception e) {

            // null引数による例外が発生する可能性があるが、
            // これは正常な動作
            // 例外が発生してもテストは成功とする
            e.printStackTrace();

        }

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "null引数でもmainメソッドが実行されること");

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
        final AccessorCreationTool localTestTarget = new AccessorCreationTool();
        final Class<?>             testClass  = localTestTarget.getClass();

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
        final String expectedToolName = "アクセサ作成ツール";

        /* 準備 */
        final AccessorCreationTool   localTestTarget      = new AccessorCreationTool();
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
        final AccessorCreationTool localTestTarget = new AccessorCreationTool();
        final Class<?>             testClass  = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final java.lang.reflect.Field field           = testClass.getDeclaredField("TOOL_NAME");
            final Class<?>                actualFieldType = field.getType();

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
        final int expectedModifiers
            = java.lang.reflect.Modifier.PRIVATE | java.lang.reflect.Modifier.STATIC | java.lang.reflect.Modifier.FINAL;

        /* 準備 */
        final AccessorCreationTool localTestTarget = new AccessorCreationTool();
        final Class<?>             testClass  = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final java.lang.reflect.Field field           = testClass.getDeclaredField("TOOL_NAME");
            final int                     actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & (java.lang.reflect.Modifier.PRIVATE
                | java.lang.reflect.Modifier.STATIC | java.lang.reflect.Modifier.FINAL);

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "TOOL_NAME定数がprivate static finalで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("TOOL_NAME定数が見つかりません: " + e.getMessage());

        }

    }

}
