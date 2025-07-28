package kmg.tool.jdts.presentation.ui.cli;

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
import kmg.tool.input.domain.service.PlainContentInputServic;
import kmg.tool.input.presentation.ui.cli.AbstractPlainContentInputTool;
import kmg.tool.jdts.application.service.JdtsService;

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

    /** JdtsServiceのモック */
    @Mock
    private JdtsService mockJdtsService;

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
        this.reflectionModel.set("jdtsService", this.mockJdtsService);

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
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("jdtsService", this.mockJdtsService);
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
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("jdtsService", this.mockJdtsService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path");
        Mockito.when(this.mockJdtsService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockJdtsService.process()).thenReturn(true);
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

}
