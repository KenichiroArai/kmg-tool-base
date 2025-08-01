package kmg.tool.e2scc.presentation.ui.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.e2scc.service.Enum2SwitchCaseCreationService;

/**
 * 列挙型からcase文作成ツールのテスト<br>
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
public class Enum2SwitchCaseCreationToolTest extends AbstractKmgTest {

    /**
     * デフォルトコンストラクタ<br>
     *
     * @since 1.0.0
     */
    public Enum2SwitchCaseCreationToolTest() {

        // 処理なし
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
        final String expectedToolName = "列挙型からcase文作成ツール";

        /* 準備 */
        final Enum2SwitchCaseCreationTool testTarget = new Enum2SwitchCaseCreationTool();

        /* テスト対象の実行 */
        // コンストラクタの実行は準備で完了

        /* 検証の準備 */
        final KmgReflectionModelImpl reflectionModel = new KmgReflectionModelImpl(testTarget);
        final String                 actualToolName  = (String) reflectionModel.get("toolName");

        /* 検証の実施 */
        Assertions.assertEquals(expectedToolName, actualToolName, "ツール名が正しく設定されていること");

    }

    /**
     * enum2SwitchCaseMakingService フィールドのテスト - 正常系：列挙型からcase文作成サービスが正しく注入される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testEnum2SwitchCaseMakingService_normalInjection() throws Exception {

        /* 期待値の定義 */
        final Enum2SwitchCaseCreationService expectedService = Mockito.mock(Enum2SwitchCaseCreationService.class);

        /* 準備 */
        final Enum2SwitchCaseCreationTool testTarget      = new Enum2SwitchCaseCreationTool();
        final KmgReflectionModelImpl      reflectionModel = new KmgReflectionModelImpl(testTarget);
        reflectionModel.set("enum2SwitchCaseMakingService", expectedService);

        /* テスト対象の実行 */
        final Enum2SwitchCaseCreationService actualService
            = (Enum2SwitchCaseCreationService) reflectionModel.get("enum2SwitchCaseMakingService");

        /* 検証の準備 */
        final Enum2SwitchCaseCreationService actualResult = actualService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedService, actualResult, "列挙型からcase文作成サービスが正しく注入されていること");

    }

    /**
     * フィールドの型テスト - 正常系：enum2SwitchCaseMakingServiceフィールドがEnum2SwitchCaseCreationService型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testEnum2SwitchCaseMakingServiceType_normalEnum2SwitchCaseCreationService() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = Enum2SwitchCaseCreationService.class;

        /* 準備 */
        final Enum2SwitchCaseCreationTool testTarget = new Enum2SwitchCaseCreationTool();
        final Class<?>                    testClass  = testTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("enum2SwitchCaseMakingService");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */
            final Class<?> actualResult = actualFieldType;

            /* 検証の実施 */
            Assertions.assertEquals(expectedFieldType, actualResult,
                "enum2SwitchCaseMakingServiceフィールドがEnum2SwitchCaseCreationService型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("enum2SwitchCaseMakingServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * フィールドの可視性テスト - 正常系：enum2SwitchCaseMakingServiceフィールドがprivateで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testEnum2SwitchCaseMakingServiceVisibility_normalPrivate() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE;

        /* 準備 */
        final Enum2SwitchCaseCreationTool testTarget = new Enum2SwitchCaseCreationTool();
        final Class<?>                    testClass  = testTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("enum2SwitchCaseMakingService");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PRIVATE;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult,
                "enum2SwitchCaseMakingServiceフィールドがprivateで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("enum2SwitchCaseMakingServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * getIoService メソッドのテスト - 正常系：列挙型からcase文作成サービスが正常に返される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final Enum2SwitchCaseCreationService expectedService = Mockito.mock(Enum2SwitchCaseCreationService.class);

        /* 準備 */
        final Enum2SwitchCaseCreationTool testTarget      = new Enum2SwitchCaseCreationTool();
        final KmgReflectionModelImpl      reflectionModel = new KmgReflectionModelImpl(testTarget);
        reflectionModel.set("enum2SwitchCaseMakingService", expectedService);

        /* テスト対象の実行 */
        final Enum2SwitchCaseCreationService actualService
            = (Enum2SwitchCaseCreationService) reflectionModel.getMethod("getIoService");

        /* 検証の準備 */
        final Enum2SwitchCaseCreationService actualResult = actualService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedService, actualResult, "列挙型からcase文作成サービスが正しく返されること");

    }

    /**
     * getIoService メソッドのテスト - 準正常系：列挙型からcase文作成サービスがnullの場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIoService_semiNullService() throws Exception {

        /* 期待値の定義 */
        final Enum2SwitchCaseCreationService expectedService = null;

        /* 準備 */
        final Enum2SwitchCaseCreationTool testTarget      = new Enum2SwitchCaseCreationTool();
        final KmgReflectionModelImpl      reflectionModel = new KmgReflectionModelImpl(testTarget);
        reflectionModel.set("enum2SwitchCaseMakingService", expectedService);

        /* テスト対象の実行 */
        final Enum2SwitchCaseCreationService actualService
            = (Enum2SwitchCaseCreationService) reflectionModel.getMethod("getIoService");

        /* 検証の準備 */
        final Enum2SwitchCaseCreationService actualResult = actualService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedService, actualResult, "nullの列挙型からcase文作成サービスが正しく返されること");

    }

    /**
     * メソッドの戻り値型テスト - 正常系：getIoServiceメソッドがEnum2SwitchCaseCreationServiceを返す場合
     *
     * @since 1.0.0
     */
    @Test
    public void testGetIoServiceReturnType_normalEnum2SwitchCaseCreationService() {

        /* 期待値の定義 */
        final Class<?> expectedReturnType = Enum2SwitchCaseCreationService.class;

        /* 準備 */
        final Enum2SwitchCaseCreationTool testTarget = new Enum2SwitchCaseCreationTool();
        final Class<?>                    testClass  = testTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Method   method           = testClass.getDeclaredMethod("getIoService");
            final Class<?> actualReturnType = method.getReturnType();

            /* 検証の準備 */
            final Class<?> actualResult = actualReturnType;

            /* 検証の実施 */
            Assertions.assertEquals(expectedReturnType, actualResult,
                "getIoServiceメソッドがEnum2SwitchCaseCreationServiceを返すこと");

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
        final int expectedModifiers = Modifier.PROTECTED;

        /* 準備 */
        final Enum2SwitchCaseCreationTool testTarget = new Enum2SwitchCaseCreationTool();
        final Class<?>                    testClass  = testTarget.getClass();

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
     * @since 1.0.0
     */
    @Test
    public void testInheritance_normalExtendsAbstractDtcTool() {

        /* 期待値の定義 */
        final Class<?> expectedSuperClass = kmg.tool.dtc.presentation.ui.cli.AbstractDtcTool.class;

        /* 準備 */
        final Enum2SwitchCaseCreationTool testTarget = new Enum2SwitchCaseCreationTool();

        /* テスト対象の実行 */
        final Class<?> actualSuperClass = testTarget.getClass().getSuperclass();

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
        Enum2SwitchCaseCreationTool.main(expectedArgs);

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
        Enum2SwitchCaseCreationTool.main(expectedArgs);

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
        final String[] expectedArgs = new String[0];

        /* 準備 */
        // 空の引数を準備

        /* テスト対象の実行 */
        Enum2SwitchCaseCreationTool.main(expectedArgs);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "空の引数でもmainメソッドが実行されること");

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
        final Enum2SwitchCaseCreationTool testTarget = new Enum2SwitchCaseCreationTool();
        final Class<?>                    testClass  = testTarget.getClass();

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
        final String expectedToolName = "列挙型からcase文作成ツール";

        /* 準備 */
        final Enum2SwitchCaseCreationTool testTarget      = new Enum2SwitchCaseCreationTool();
        final KmgReflectionModelImpl      reflectionModel = new KmgReflectionModelImpl(testTarget);

        /* テスト対象の実行 */
        final String actualToolName = (String) reflectionModel.get("TOOL_NAME");

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
        final Enum2SwitchCaseCreationTool testTarget = new Enum2SwitchCaseCreationTool();
        final Class<?>                    testClass  = testTarget.getClass();

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
        final Enum2SwitchCaseCreationTool testTarget = new Enum2SwitchCaseCreationTool();
        final Class<?>                    testClass  = testTarget.getClass();

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
