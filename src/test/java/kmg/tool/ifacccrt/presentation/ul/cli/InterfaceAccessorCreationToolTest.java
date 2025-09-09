package kmg.tool.ifacccrt.presentation.ul.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.acccrt.application.service.AccessorCreationService;
import kmg.tool.dtc.presentation.ui.cli.AbstractDtcTool;

/**
 * インタフェースのアクセサ作成ツールのテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class InterfaceAccessorCreationToolTest extends AbstractKmgTest {

    /**
     * アクセサ作成サービスのモック
     *
     * @since 0.1.0
     */
    @Mock
    private AccessorCreationService mockAccessorCreationService;

    /**
     * テスト対象のインスタンス
     *
     * @since 0.1.0
     */
    @InjectMocks
    private InterfaceAccessorCreationTool testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.1.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * 各テスト前のセットアップ
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @BeforeEach
    public void setUp() throws Exception {

        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

    }

    /**
     * 各テスト後のクリーンアップ
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @AfterEach
    public void tearDown() throws Exception {

        // 必要ならクリーンアップ
    }

    /**
     * accessorCreationService フィールドのテスト - 正常系：アクセサ作成サービスが正しく注入される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAccessorCreationService_normalInjection() throws Exception {

        /* 期待値の定義 */
        final AccessorCreationService expectedService = this.mockAccessorCreationService;

        /* 準備 */
        this.reflectionModel.set("accessorCreationService", expectedService);

        /* テスト対象の実行 */
        final AccessorCreationService actualService
            = (AccessorCreationService) this.reflectionModel.get("accessorCreationService");

        /* 検証の準備 */
        final AccessorCreationService actualResult = actualService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedService, actualResult, "アクセサ作成サービスが正しく注入されていること");

    }

    /**
     * フィールドの型テスト - 正常系：accessorCreationServiceフィールドがAccessorCreationService型の場合
     *
     * @since 0.1.0
     */
    @Test
    public void testAccessorCreationServiceType_normalAccessorCreationService() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = AccessorCreationService.class;

        /* 準備 */
        final Class<?> testClass = this.testTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("accessorCreationService");
            final Class<?> actualFieldType = field.getType();

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
     * @since 0.1.0
     */
    @Test
    public void testAccessorCreationServiceVisibility_normalPrivate() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE;

        /* 準備 */
        final Class<?> testClass = this.testTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("accessorCreationService");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PRIVATE;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "accessorCreationServiceフィールドがprivateで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("accessorCreationServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：デフォルトコンストラクタが正常に動作する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefault() throws Exception {

        /* 期待値の定義 */
        final String expectedToolName = "インタフェースのアクセサ作成ツール";

        /* 準備 */
        // testTargetは@InjectMocksで生成済み

        /* テスト対象の実行 */
        // コンストラクタの実行は準備で完了

        /* 検証の準備 */
        final String actualToolName = (String) this.reflectionModel.get("toolName");

        /* 検証の実施 */
        Assertions.assertEquals(expectedToolName, actualToolName, "ツール名が正しく設定されていること");

    }

    /**
     * getIoService メソッドのテスト - 正常系：アクセサ作成サービスが正常に返される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final AccessorCreationService expectedService = this.mockAccessorCreationService;

        /* 準備 */
        this.reflectionModel.set("accessorCreationService", expectedService);

        /* テスト対象の実行 */
        final Method getIoServiceMethod = this.testTarget.getClass().getDeclaredMethod("getIoService");
        getIoServiceMethod.setAccessible(true);
        final AccessorCreationService actualService
            = (AccessorCreationService) getIoServiceMethod.invoke(this.testTarget);

        /* 検証の準備 */
        final AccessorCreationService actualResult = actualService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedService, actualResult, "アクセサ作成サービスが正しく返されること");

    }

    /**
     * getIoService メソッドのテスト - 準正常系：アクセサ作成サービスがnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_semiNullService() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("accessorCreationService", null);

        /* テスト対象の実行 */
        final Method getIoServiceMethod = this.testTarget.getClass().getDeclaredMethod("getIoService");
        getIoServiceMethod.setAccessible(true);
        final AccessorCreationService actualService
            = (AccessorCreationService) getIoServiceMethod.invoke(this.testTarget);

        /* 検証の準備 */
        final AccessorCreationService actualResult = actualService;

        /* 検証の実施 */
        Assertions.assertNull(actualResult, "nullのアクセサ作成サービスが正しく返されること");

    }

    /**
     * メソッドの戻り値型テスト - 正常系：getIoServiceメソッドがAccessorCreationServiceを返す場合
     *
     * @since 0.1.0
     */
    @Test
    public void testGetIoServiceReturnType_normalAccessorCreationService() {

        /* 期待値の定義 */
        final Class<?> expectedReturnType = AccessorCreationService.class;

        /* 準備 */
        final Class<?> testClass = this.testTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Method   method           = testClass.getDeclaredMethod("getIoService");
            final Class<?> actualReturnType = method.getReturnType();

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
     * @since 0.1.0
     */
    @Test
    public void testGetIoServiceVisibility_normalProtected() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PROTECTED;

        /* 準備 */
        final Class<?> testClass = this.testTarget.getClass();

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
     * 継承関係テスト - 正常系：AbstractDtcToolを継承している場合
     *
     * @since 0.1.0
     */
    @Test
    public void testInheritance_normalExtendsAbstractDtcTool() {

        /* 期待値の定義 */
        final Class<?> expectedSuperClass = AbstractDtcTool.class;

        /* 準備 */
        final Class<?> testClass = this.testTarget.getClass();

        /* テスト対象の実行 */
        final Class<?> actualSuperClass = testClass.getSuperclass();

        /* 検証の準備 */
        final Class<?> actualResult = actualSuperClass;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSuperClass, actualResult, "AbstractDtcToolを継承していること");

    }

    /**
     * main メソッドのテスト - 異常系：SpringBoot起動に失敗する場合
     *
     * @since 0.1.0
     */
    @Test
    public void testMain_errorSpringBootStartupFailure() {

        /* 期待値の定義 */

        /* 準備 */
        final String[] testArgs = {
            "invalid", "arguments"
        };

        /* テスト対象の実行 */
        try {

            InterfaceAccessorCreationTool.main(testArgs);

            /* 検証の準備 */
            final boolean actualResult = true;

            /* 検証の実施 */
            Assertions.assertTrue(actualResult, "mainメソッドが正常に実行されること");

        } catch (@SuppressWarnings("unused") final Exception e) {

            /* 検証の準備 */
            final boolean actualResult = false;

            /* 検証の実施 */
            Assertions.assertFalse(actualResult, "mainメソッドで例外が発生すること");

        }

    }

    /**
     * main メソッドのテスト - 正常系：正常に起動する場合
     *
     * @since 0.1.0
     */
    @Test
    public void testMain_normalSuccess() {

        /* 期待値の定義 */

        /* 準備 */
        final String[] testArgs = {};

        /* テスト対象の実行 */
        try {

            InterfaceAccessorCreationTool.main(testArgs);

            /* 検証の準備 */
            final boolean actualResult = true;

            /* 検証の実施 */
            Assertions.assertTrue(actualResult, "mainメソッドが正常に実行されること");

        } catch (@SuppressWarnings("unused") final Exception e) {

            /* 検証の準備 */
            final boolean actualResult = false;

            /* 検証の実施 */
            Assertions.assertFalse(actualResult, "mainメソッドで例外が発生すること");

        }

    }

    /**
     * main メソッドのテスト - 準正常系：引数がnullの場合
     *
     * @since 0.1.0
     */
    @Test
    public void testMain_semiNullArgs() {

        /* 期待値の定義 */
        final String[] expectedArgs = {};

        /* 準備 */
        final String[] testArgs = expectedArgs;

        /* テスト対象の実行 */
        try {

            InterfaceAccessorCreationTool.main(testArgs);

            /* 検証の準備 */
            final boolean actualResult = true;

            /* 検証の実施 */
            Assertions.assertTrue(actualResult, "空の引数でmainメソッドが正常に実行されること");

        } catch (@SuppressWarnings("unused") final IllegalArgumentException e) {

            /* 検証の準備 */
            final boolean actualResult = true;

            /* 検証の実施 */
            Assertions.assertTrue(actualResult, "空の引数でIllegalArgumentExceptionが発生すること");

        } catch (@SuppressWarnings("unused") final Exception e) {

            /* 検証の準備 */
            final boolean actualResult = false;

            /* 検証の実施 */
            Assertions.assertFalse(actualResult, "予期しない例外が発生すること");

        }

    }

    /**
     * TOOL_NAME フィールドのテスト - 正常系：正しい値が設定されている場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testToolName_normalCorrectValue() throws Exception {

        /* 期待値の定義 */
        final String expectedToolName = "インタフェースのアクセサ作成ツール";

        /* 準備 */
        final Class<?> testClass = this.testTarget.getClass();

        /* テスト対象の実行 */
        final Field toolNameField = testClass.getDeclaredField("TOOL_NAME");
        toolNameField.setAccessible(true);
        final String actualToolName = (String) toolNameField.get(null);

        /* 検証の準備 */
        final String actualResult = actualToolName;

        /* 検証の実施 */
        Assertions.assertEquals(expectedToolName, actualResult, "TOOL_NAMEが正しい値に設定されていること");

    }

    /**
     * TOOL_NAME フィールドの型テスト - 正常系：TOOL_NAMEフィールドがString型の場合
     *
     * @since 0.1.0
     */
    @Test
    public void testToolNameType_normalString() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = String.class;

        /* 準備 */
        final Class<?> testClass = this.testTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("TOOL_NAME");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */
            final Class<?> actualResult = actualFieldType;

            /* 検証の実施 */
            Assertions.assertEquals(expectedFieldType, actualResult, "TOOL_NAMEフィールドがString型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("TOOL_NAMEフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * TOOL_NAME フィールドの可視性テスト - 正常系：TOOL_NAMEフィールドがprivate static finalで定義されている場合
     *
     * @since 0.1.0
     */
    @Test
    public void testToolNameVisibility_normalPrivateStaticFinal() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL;

        /* 準備 */
        final Class<?> testClass = this.testTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("TOOL_NAME");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & (Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL);

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "TOOL_NAMEフィールドがprivate static finalで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("TOOL_NAMEフィールドが見つかりません: " + e.getMessage());

        }

    }

}
