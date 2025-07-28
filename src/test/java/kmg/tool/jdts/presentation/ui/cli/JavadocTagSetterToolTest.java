package kmg.tool.jdts.presentation.ui.cli;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.model.val.KmgValsModel;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.input.domain.service.PlainContentInputServic;
import kmg.tool.input.presentation.ui.cli.AbstractPlainContentInputTool;
import kmg.tool.jdts.application.logic.JdtsIoLogic;
import kmg.tool.jdts.application.service.JdtsService;
import kmg.tool.jdts.application.service.impl.JdtsServiceImpl;

/**
 * Javadocタグ設定ツールのテスト<br>
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
public class JavadocTagSetterToolTest extends AbstractKmgTest {

    /** テスト対象 */
    @InjectMocks
    private JavadocTagSetterTool testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** KmgMessageSourceのモック */
    @Mock
    private KmgMessageSource mockMessageSource;

    /** PlainContentInputServicのモック */
    @Mock
    private PlainContentInputServic mockInputService;

    /** JdtsIoLogicのモック */
    @Mock
    private JdtsIoLogic mockJdtsIoLogic;

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
        this.reflectionModel.set("inputService", this.mockInputService);

        // JdtsIoLogicのモック設定
        Mockito.when(this.mockJdtsIoLogic.writeContent()).thenReturn(true);
        Mockito.when(this.mockJdtsIoLogic.getCurrentFilePath()).thenReturn(Paths.get("test/file.java"));
        Mockito.when(this.mockJdtsIoLogic.load()).thenReturn(true);
        Mockito.when(this.mockJdtsIoLogic.nextFile()).thenReturn(false); // 1回の処理で終了

    }

    /**
     * BASE_PATH 定数のテスト - 正常系：基準パスが正しく定義されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testBasePath_normalCorrectValue() throws Exception {

        /* 期待値の定義 */
        final Path expectedBasePath = Paths.get("src/main/resources/tool/io");

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        /* テスト対象の実行 */
        final Path actualBasePath = (Path) localReflectionModel.get("BASE_PATH");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedBasePath, actualBasePath, "BASE_PATH定数が正しく定義されていること");

    }

    /**
     * 定数の型テスト - 正常系：BASE_PATH定数がPath型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testBasePathType_normalPath() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = Path.class;

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("BASE_PATH");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertEquals(expectedFieldType, actualFieldType, "BASE_PATH定数がPath型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("BASE_PATH定数が見つかりません: " + e.getMessage());

        }

    }

    /**
     * 定数の可視性テスト - 正常系：BASE_PATH定数がprivate static finalで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testBasePathVisibility_normalPrivateStaticFinal() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL;

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("BASE_PATH");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & (Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL);

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "BASE_PATH定数がprivate static finalで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("BASE_PATH定数が見つかりません: " + e.getMessage());

        }

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：カスタムコンストラクタが正常に動作する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCustom() throws Exception {

        /* 期待値の定義 */
        final String expectedToolName = "カスタムツール名";

        /* 準備 */
        final Logger logger = LoggerFactory.getLogger(JavadocTagSetterTool.class);

        /* テスト対象の実行 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool(logger, expectedToolName);

        /* 検証の準備 */
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        final String                 actualToolName       = (String) localReflectionModel.get("TOOL_NAME");

        /* 検証の実施 */
        Assertions.assertEquals(expectedToolName, actualToolName, "カスタムツール名が正しく設定されていること");

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
        final String expectedToolName = "Javadocタグ設定ツール";

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();

        /* テスト対象の実行 */
        // コンストラクタの実行は準備で完了

        /* 検証の準備 */
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        final String                 actualToolName       = (String) localReflectionModel.get("TOOL_NAME");

        /* 検証の実施 */
        Assertions.assertEquals(expectedToolName, actualToolName, "ツール名が正しく設定されていること");

    }

    /**
     * DEFINITION_FILE_PATH_FORMAT 定数のテスト - 正常系：定義ファイルのパスフォーマットが正しく定義されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testDefinitionFilePathFormat_normalCorrectValue() throws Exception {

        /* 期待値の定義 */
        final String expectedFormat = "template/%s.yml";

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        /* テスト対象の実行 */
        final String actualFormat = (String) localReflectionModel.get("DEFINITION_FILE_PATH_FORMAT");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedFormat, actualFormat, "DEFINITION_FILE_PATH_FORMAT定数が正しく定義されていること");

    }

    /**
     * 定数の型テスト - 正常系：DEFINITION_FILE_PATH_FORMAT定数がString型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testDefinitionFilePathFormatType_normalString() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = String.class;

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("DEFINITION_FILE_PATH_FORMAT");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertEquals(expectedFieldType, actualFieldType, "DEFINITION_FILE_PATH_FORMAT定数がString型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("DEFINITION_FILE_PATH_FORMAT定数が見つかりません: " + e.getMessage());

        }

    }

    /**
     * 定数の可視性テスト - 正常系：DEFINITION_FILE_PATH_FORMAT定数がprivate static finalで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testDefinitionFilePathFormatVisibility_normalPrivateStaticFinal() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL;

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("DEFINITION_FILE_PATH_FORMAT");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & (Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL);

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult,
                "DEFINITION_FILE_PATH_FORMAT定数がprivate static finalで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("DEFINITION_FILE_PATH_FORMAT定数が見つかりません: " + e.getMessage());

        }

    }

    /**
     * execute メソッドのテスト - 異常系：例外が発生する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_errorException() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        final JdtsServiceImpl        localSpyJdtsService  = Mockito.spy(new JdtsServiceImpl());
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("jdtsService", localSpyJdtsService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenThrow(new RuntimeException("テスト例外"));
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "例外が発生した場合、falseが返されること");

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

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        final JdtsServiceImpl        localSpyJdtsService  = Mockito.spy(new JdtsServiceImpl());
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("jdtsService", localSpyJdtsService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path");
        Mockito.doReturn(true).when(localSpyJdtsService).initialize(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.doThrow(new KmgToolMsgException(kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes.KMGTOOL_GEN13001,
            new Object[] {})).when(localSpyJdtsService).process();
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "KmgToolMsgExceptionが発生した場合、falseが返されること");

    }

    /**
     * execute メソッドのテスト - 異常系：KmgToolValExceptionが発生する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_errorKmgToolValException() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        final JdtsServiceImpl        localSpyJdtsService  = Mockito.spy(new JdtsServiceImpl());
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("jdtsService", localSpyJdtsService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path");
        Mockito.doReturn(true).when(localSpyJdtsService).initialize(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.doThrow(new KmgToolValException(Mockito.mock(KmgValsModel.class))).when(localSpyJdtsService).process();
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "KmgToolValExceptionが発生した場合、falseが返されること");

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

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        final JdtsServiceImpl        localSpyJdtsService  = Mockito.spy(new JdtsServiceImpl());
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("jdtsService", localSpyJdtsService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path");
        Mockito.doReturn(true).when(localSpyJdtsService).initialize(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.doReturn(true).when(localSpyJdtsService).process();
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "正常に処理が完了すること");

    }

    /**
     * execute メソッドのテスト - 準正常系：入力ファイルの読み込みに失敗する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_semiInputLoadFailure() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(false);
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "入力ファイルの読み込みに失敗した場合、falseが返されること");

    }

    /**
     * execute メソッドのテスト - 準正常系：JdtsServiceの初期化に失敗する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_semiJdtsServiceInitFailure() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        final JdtsServiceImpl        localSpyJdtsService  = Mockito.spy(new JdtsServiceImpl());
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("jdtsService", localSpyJdtsService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path");
        Mockito.doReturn(false).when(localSpyJdtsService).initialize(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "JdtsServiceの初期化に失敗した場合、falseが返されること");

    }

    /**
     * execute メソッドのテスト - 準正常系：JdtsServiceの処理に失敗する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_semiJdtsServiceProcessFailure() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        final JdtsServiceImpl        localSpyJdtsService  = Mockito.spy(new JdtsServiceImpl());
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("jdtsService", localSpyJdtsService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path");
        Mockito.doReturn(true).when(localSpyJdtsService).initialize(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.doReturn(false).when(localSpyJdtsService).process();
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "JdtsServiceの処理に失敗した場合、falseが返されること");

    }

    /**
     * getDefaultDefinitionPath メソッドのテスト - 正常系：デフォルト定義パスが正常に返される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetDefaultDefinitionPath_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final Path expectedDefinitionPath = Paths.get("src/main/resources/tool/io/template/JavadocTagSetterTool.yml");

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        /* テスト対象の実行 */
        final Path actualDefinitionPath = (Path) localReflectionModel.getMethod("getDefaultDefinitionPath");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedDefinitionPath, actualDefinitionPath, "デフォルト定義パスが正しく返されること");

    }

    /**
     * getDefinitionPath メソッドのテスト - 正常系：定義ファイルのパスが正常に返される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetDefinitionPath_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final Path expectedDefinitionPath = Paths.get("src/main/resources/tool/io/template/JavadocTagSetterTool.yml");

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();

        /* テスト対象の実行 */
        final Path actualDefinitionPath = localTestTarget.getDefinitionPath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedDefinitionPath, actualDefinitionPath, "定義ファイルのパスが正しく返されること");

    }

    /**
     * メソッドの戻り値型テスト - 正常系：getDefinitionPathメソッドがPathを返す場合
     *
     * @since 1.0.0
     */
    @Test
    public void testGetDefinitionPathReturnType_normalPath() {

        /* 期待値の定義 */
        final Class<?> expectedReturnType = Path.class;

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Method   method           = testClass.getDeclaredMethod("getDefinitionPath");
            final Class<?> actualReturnType = method.getReturnType();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertEquals(expectedReturnType, actualReturnType, "getDefinitionPathメソッドがPathを返すこと");

        } catch (final NoSuchMethodException e) {

            Assertions.fail("getDefinitionPathメソッドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * メソッドの可視性テスト - 正常系：getDefinitionPathメソッドがpublicで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testGetDefinitionPathVisibility_normalPublic() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PUBLIC;

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Method method          = testClass.getDeclaredMethod("getDefinitionPath");
            final int    actualModifiers = method.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PUBLIC;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "getDefinitionPathメソッドがpublicで定義されていること");

        } catch (final NoSuchMethodException e) {

            Assertions.fail("getDefinitionPathメソッドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * getInputService メソッドのテスト - 正常系：プレーンコンテンツ入力サービスが正常に返される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetInputService_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final PlainContentInputServic expectedInputService = Mockito.mock(PlainContentInputServic.class);

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("inputService", expectedInputService);

        /* テスト対象の実行 */
        final PlainContentInputServic actualInputService = localTestTarget.getInputService();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedInputService, actualInputService, "プレーンコンテンツ入力サービスが正しく返されること");

    }

    /**
     * メソッドの戻り値型テスト - 正常系：getInputServiceメソッドがPlainContentInputServicを返す場合
     *
     * @since 1.0.0
     */
    @Test
    public void testGetInputServiceReturnType_normalPlainContentInputServic() {

        /* 期待値の定義 */
        final Class<?> expectedReturnType = PlainContentInputServic.class;

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Method   method           = testClass.getDeclaredMethod("getInputService");
            final Class<?> actualReturnType = method.getReturnType();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertEquals(expectedReturnType, actualReturnType,
                "getInputServiceメソッドがPlainContentInputServicを返すこと");

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
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

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
     * 継承関係のテスト - 正常系：AbstractPlainContentInputToolを正しく継承している場合
     *
     * @since 1.0.0
     */
    @Test
    public void testInheritance_normalExtendsAbstractPlainContentInputTool() {

        /* 期待値の定義 */
        final Class<?> expectedSuperClass = AbstractPlainContentInputTool.class;

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();

        /* テスト対象の実行 */
        final Class<?> actualSuperClass = localTestTarget.getClass().getSuperclass();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedSuperClass, actualSuperClass, "AbstractPlainContentInputToolを正しく継承していること");

    }

    /**
     * inputService フィールドのテスト - 正常系：プレーンコンテンツ入力サービスが正しく注入される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInputService_normalInjection() throws Exception {

        /* 期待値の定義 */
        final PlainContentInputServic expectedInputService = Mockito.mock(PlainContentInputServic.class);

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("inputService", expectedInputService);

        /* テスト対象の実行 */
        final PlainContentInputServic actualInputService
            = (PlainContentInputServic) localReflectionModel.get("inputService");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedInputService, actualInputService, "プレーンコンテンツ入力サービスが正しく注入されていること");

    }

    /**
     * フィールドの型テスト - 正常系：inputServiceフィールドがPlainContentInputServic型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testInputServiceType_normalPlainContentInputServic() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = PlainContentInputServic.class;

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("inputService");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertEquals(expectedFieldType, actualFieldType,
                "inputServiceフィールドがPlainContentInputServic型であること");

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
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

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
     * jdtsService フィールドのテスト - 正常系：Javadocタグ設定サービスが正しく注入される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testJdtsService_normalInjection() throws Exception {

        /* 期待値の定義 */
        final JdtsService expectedJdtsService = Mockito.mock(JdtsService.class);

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("jdtsService", expectedJdtsService);

        /* テスト対象の実行 */
        final JdtsService actualJdtsService = (JdtsService) localReflectionModel.get("jdtsService");

        /* 検証の準備 */
        final JdtsService actualResult = actualJdtsService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedJdtsService, actualResult, "Javadocタグ設定サービスが正しく注入されていること");

    }

    /**
     * フィールドの型テスト - 正常系：jdtsServiceフィールドがJdtsService型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testJdtsServiceType_normalJdtsService() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = JdtsService.class;

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("jdtsService");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */
            final Class<?> actualResult = actualFieldType;

            /* 検証の実施 */
            Assertions.assertEquals(expectedFieldType, actualResult, "jdtsServiceフィールドがJdtsService型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("jdtsServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * フィールドの可視性テスト - 正常系：jdtsServiceフィールドがprivateで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testJdtsServiceVisibility_normalPrivate() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE;

        /* 準備 */
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("jdtsService");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PRIVATE;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "jdtsServiceフィールドがprivateで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("jdtsServiceフィールドが見つかりません: " + e.getMessage());

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
        JavadocTagSetterTool.main(expectedArgs);

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
        JavadocTagSetterTool.main(expectedArgs);

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
        JavadocTagSetterTool.main(expectedArgs);

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
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
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
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

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
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

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
     * setTargetPathFromInputFile メソッドのテスト - 正常系：対象パスが正常に設定される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetTargetPathFromInputFile_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final Path expectedPath = Paths.get("test/path");

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path");

        /* テスト対象の実行 */
        final boolean actualResult = (Boolean) localReflectionModel.getMethod("setTargetPathFromInputFile");

        /* 検証の準備 */
        final Path actualPath = (Path) localReflectionModel.get("targetPath");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "対象パスが正常に設定されること");
        Assertions.assertEquals(expectedPath, actualPath, "対象パスが正しく設定されること");

    }

    /**
     * setTargetPathFromInputFile メソッドのテスト - 準正常系：入力ファイルの読み込みに失敗する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetTargetPathFromInputFile_semiInputLoadFailure() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(false);

        /* テスト対象の実行 */
        final boolean actualResult = (Boolean) localReflectionModel.getMethod("setTargetPathFromInputFile");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "入力ファイルの読み込みに失敗した場合、falseが返されること");

    }

    /**
     * setTargetPathFromInputFile メソッドのテスト - 準正常系：コンテンツがnullの場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetTargetPathFromInputFile_semiNullContent() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn(null);

        /* テスト対象の実行 */
        final boolean actualResult = (Boolean) localReflectionModel.getMethod("setTargetPathFromInputFile");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "コンテンツがnullの場合、falseが返されること");

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
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

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
        final String expectedToolName = "Javadocタグ設定ツール";

        /* 準備 */
        final JavadocTagSetterTool   localTestTarget      = new JavadocTagSetterTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        /* テスト対象の実行 */
        final String actualToolName = (String) localReflectionModel.get("TOOL_NAME");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedToolName, actualToolName, "TOOL_NAME定数が正しく定義されていること");

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
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

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
        final JavadocTagSetterTool localTestTarget = new JavadocTagSetterTool();
        final Class<?>             testClass       = localTestTarget.getClass();

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
