package kmg.tool.is.presentation.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.is.application.service.IsCreationService;

/**
 * 挿入SQL作成画面コントローラのテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 */
@ExtendWith({
    MockitoExtension.class, ApplicationExtension.class
})
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class IsCreationControllerTest extends ApplicationTest {

    /** テスト対象 */
    private IsCreationController testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** メッセージソースのモック */
    @Mock
    private KmgMessageSource mockMessageSource;

    /** 挿入SQL作成サービスのモック */
    @Mock
    private IsCreationService mockIsCreationService;

    /** Springアプリケーションコンテキストのモック */
    @Mock
    private ApplicationContext mockApplicationContext;

    /** テスト用一時ディレクトリ */
    private Path testTempDir;

    /** テスト用入力ファイル */
    private Path testInputFile;

    /** テスト用出力ディレクトリ */
    private Path testOutputDir;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @since 1.0.0
     */
    public IsCreationControllerTest() {

        // 処理なし
    }

    /**
     * テスト前処理<br>
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @BeforeEach
    public void setUp() throws Exception {

        /* テスト用一時ディレクトリの作成 */
        this.testTempDir = Files.createTempDirectory("is_controller_test");
        this.testInputFile = this.testTempDir.resolve("test_input.xlsx");
        this.testOutputDir = this.testTempDir.resolve("test_output");

        /* テスト用ファイルの作成 */
        Files.createFile(this.testInputFile);
        Files.createDirectories(this.testOutputDir);

        /* テスト対象の作成 */
        this.testTarget = new IsCreationController();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの注入 */
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("isCreationService", this.mockIsCreationService);

        /* FXMLフィールドのモック化 */
        final TextField mockTxtInputFile           = Mockito.mock(TextField.class);
        final TextField mockTxtOutputDirectory     = Mockito.mock(TextField.class);
        final TextField mockTxtThreadNum           = Mockito.mock(TextField.class);
        final Button    mockBtnInputFileOpen       = Mockito.mock(Button.class);
        final Button    mockBtnOutputDirectoryOpen = Mockito.mock(Button.class);
        final Button    mockBtnRun                 = Mockito.mock(Button.class);
        final Label     mockLblProcTime            = Mockito.mock(Label.class);
        final Label     mockLblProcTimeUnit        = Mockito.mock(Label.class);

        this.reflectionModel.set("txtInputFile", mockTxtInputFile);
        this.reflectionModel.set("btnInputFileOpen", mockBtnInputFileOpen);
        this.reflectionModel.set("txtOutputDirectory", mockTxtOutputDirectory);
        this.reflectionModel.set("btnOutputDirectoryOpen", mockBtnOutputDirectoryOpen);
        this.reflectionModel.set("txtThreadNum", mockTxtThreadNum);
        this.reflectionModel.set("btnRun", mockBtnRun);
        this.reflectionModel.set("lblProcTime", mockLblProcTime);
        this.reflectionModel.set("lblProcTimeUnit", mockLblProcTimeUnit);

        // SpringApplicationContextHelperのモック化は各テストメソッド内で行う
        // 静的フィールドのモック化のため、各テストメソッドでMockedStaticを使用する

    }

    /**
     * テスト後処理<br>
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @AfterEach
    public void tearDown() throws Exception {

        /* テストファイルとディレクトリの削除 */
        if ((this.testTempDir != null) && Files.exists(this.testTempDir)) {

            this.deleteDirectoryRecursively(this.testTempDir);

        }

    }

    /**
     * btnInputFileOpen フィールドのテスト - 正常系：フィールドが正しく設定されている場合
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     *
     * @since 1.0.0
     */
    @Test
    public void testBtnInputFileOpen_normalBasic() throws KmgReflectionException {

        /* 期待値の定義 */
        // フィールドが存在することを期待

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final Button actualBtnInputFileOpen = (Button) this.reflectionModel.get("btnInputFileOpen");

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertNotNull(actualBtnInputFileOpen, "btnInputFileOpenフィールドが存在すること");

    }

    /**
     * btnOutputDirectoryOpen フィールドのテスト - 正常系：フィールドが正しく設定されている場合
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     *
     * @since 1.0.0
     */
    @Test
    public void testBtnOutputDirectoryOpen_normalBasic() throws KmgReflectionException {

        /* 期待値の定義 */
        // フィールドが存在することを期待

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final Button actualBtnOutputDirectoryOpen = (Button) this.reflectionModel.get("btnOutputDirectoryOpen");

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertNotNull(actualBtnOutputDirectoryOpen, "btnOutputDirectoryOpenフィールドが存在すること");

    }

    /**
     * btnRun フィールドのテスト - 正常系：フィールドが正しく設定されている場合
     *
     * @since 1.0.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testBtnRun_normalBasic() throws KmgReflectionException {

        /* 期待値の定義 */
        // フィールドが存在することを期待

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final Button actualBtnRun = (Button) this.reflectionModel.get("btnRun");

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertNotNull(actualBtnRun, "btnRunフィールドが存在すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：カスタムロガーを使用したコンストラクタが正常に動作する場合
     *
     * @since 1.0.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testConstructor_normalCustomLogger() throws KmgReflectionException {

        /* 期待値の定義 */
        final Logger expectedLogger = LoggerFactory.getLogger("TestLogger");

        /* 準備 */
        final IsCreationController testConstructor = new IsCreationController(expectedLogger);

        /* テスト対象の実行 */
        // コンストラクタの実行は準備で完了

        /* 検証の準備 */
        final KmgReflectionModelImpl actualReflectionModel = new KmgReflectionModelImpl(testConstructor);
        final Object                 actualLogger          = actualReflectionModel.get("logger");

        /* 検証の実施 */
        Assertions.assertEquals(expectedLogger, actualLogger, "カスタムロガーが正しく設定されていること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：デフォルトコンストラクタが正常に動作する場合
     *
     * @since 1.0.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testConstructor_normalDefault() throws KmgReflectionException {

        /* 期待値の定義 */
        // コンストラクタが正常に動作することを期待

        /* 準備 */
        final IsCreationController testConstructor = new IsCreationController();

        /* テスト対象の実行 */
        // コンストラクタの実行は準備で完了

        /* 検証の準備 */
        final KmgReflectionModelImpl actualReflectionModel = new KmgReflectionModelImpl(testConstructor);
        final Object                 actualLogger          = actualReflectionModel.get("logger");

        /* 検証の実施 */
        Assertions.assertNotNull(actualLogger, "ロガーが正しく設定されていること");

    }

    /**
     * DEFAULT_DIRECTORY_PATH 定数のテスト - 正常系：定数が正しく定義されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testDEFAULT_DIRECTORY_PATH_normalBasic() throws Exception {

        /* 期待値の定義 */
        final String expected = "c:/";

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final Field field = IsCreationController.class.getDeclaredField("DEFAULT_DIRECTORY_PATH");
        field.setAccessible(true);
        final String actual = (String) field.get(null);

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DEFAULT_DIRECTORY_PATH定数が正しく定義されていること");

    }

    /**
     * DIRECTORY_CHOOSER_TITLE 定数のテスト - 正常系：定数が正しく定義されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testDIRECTORY_CHOOSER_TITLE_normalBasic() throws Exception {

        /* 期待値の定義 */
        final String expected = "ディレクトリ選択";

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final Field field = IsCreationController.class.getDeclaredField("DIRECTORY_CHOOSER_TITLE");
        field.setAccessible(true);
        final String actual = (String) field.get(null);

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DIRECTORY_CHOOSER_TITLE定数が正しく定義されていること");

    }

    /**
     * FILE_CHOOSER_TITLE 定数のテスト - 正常系：定数が正しく定義されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testFILE_CHOOSER_TITLE_normalBasic() throws Exception {

        /* 期待値の定義 */
        final String expected = "ファイル選択";

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final Field field = IsCreationController.class.getDeclaredField("FILE_CHOOSER_TITLE");
        field.setAccessible(true);
        final String actual = (String) field.get(null);

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "FILE_CHOOSER_TITLE定数が正しく定義されていること");

    }

    /**
     * initialize メソッドのテスト - 正常系：正常な初期化
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalBasic() throws Exception {

        /* 期待値の定義 */
        final String expectedThreadNum = String.valueOf(Runtime.getRuntime().availableProcessors());

        /* 準備 */
        final URL            location  = null;
        final ResourceBundle resources = null;

        /* テスト対象の実行 */
        this.testTarget.initialize(location, resources);

        /* 検証の準備 */
        final TextField actualTxtThreadNum = (TextField) this.reflectionModel.get("txtThreadNum");

        /* 検証の実施 */
        Mockito.verify(actualTxtThreadNum, Mockito.times(1)).setText(expectedThreadNum);

    }

    /**
     * isCreationService フィールドのテスト - 正常系：フィールドが正しく設定されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testIsCreationService_normalBasic() throws Exception {

        /* 期待値の定義 */
        // フィールドが存在することを期待

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final Object actualIsCreationService = this.reflectionModel.get("isCreationService");

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertNotNull(actualIsCreationService, "isCreationServiceフィールドが存在すること");

    }

    /**
     * lblProcTime フィールドのテスト - 正常系：フィールドが正しく設定されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLblProcTime_normalBasic() throws Exception {

        /* 期待値の定義 */
        // フィールドが存在することを期待

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final Label actualLblProcTime = (Label) this.reflectionModel.get("lblProcTime");

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertNotNull(actualLblProcTime, "lblProcTimeフィールドが存在すること");

    }

    /**
     * lblProcTimeUnit フィールドのテスト - 正常系：フィールドが正しく設定されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLblProcTimeUnit_normalBasic() throws Exception {

        /* 期待値の定義 */
        // フィールドが存在することを期待

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final Label actualLblProcTimeUnit = (Label) this.reflectionModel.get("lblProcTimeUnit");

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertNotNull(actualLblProcTimeUnit, "lblProcTimeUnitフィールドが存在すること");

    }

    /**
     * logger フィールドのテスト - 正常系：フィールドが正しく設定されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLogger_normalBasic() throws Exception {

        /* 期待値の定義 */
        // フィールドが存在することを期待

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final Object actualLogger = this.reflectionModel.get("logger");

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertNotNull(actualLogger, "loggerフィールドが存在すること");

    }

    /**
     * mainProc メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMainProc_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        // TODO KenichiroArai 2025/07/24 KmgToolMsgExceptionを検証する

        /* 準備 */
        final Path inputPath  = this.testInputFile;
        final Path outputPath = this.testOutputDir;

        final TextField txtThreadNum = (TextField) this.reflectionModel.get("txtThreadNum");
        Mockito.when(txtThreadNum.getText()).thenReturn("2");

        Mockito.doNothing().when(this.mockIsCreationService).initialize(ArgumentMatchers.any(Path.class),
            ArgumentMatchers.any(Path.class), ArgumentMatchers.anyShort());

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            Mockito.doThrow(new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN08000, new Object[] {}))
                .when(this.mockIsCreationService).outputInsertionSql();

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.mainProc(inputPath, outputPath);

            });

            /* 検証の準備 */
            // 検証の準備は不要

            /* 検証の実施 */
            Assertions.assertNotNull(actualException, "KmgToolMsgExceptionが発生すること");

        }

    }

    /**
     * mainProc メソッドのテスト - 正常系：正常なメイン処理
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMainProc_normalBasic() throws Exception {

        /* 期待値の定義 */
        // 例外が発生しないことを期待

        /* 準備 */
        final Path inputPath  = this.testInputFile;
        final Path outputPath = this.testOutputDir;

        final TextField txtThreadNum = (TextField) this.reflectionModel.get("txtThreadNum");
        Mockito.when(txtThreadNum.getText()).thenReturn("2");

        Mockito.doNothing().when(this.mockIsCreationService).initialize(ArgumentMatchers.any(Path.class),
            ArgumentMatchers.any(Path.class), ArgumentMatchers.anyShort());
        Mockito.doNothing().when(this.mockIsCreationService).outputInsertionSql();

        /* テスト対象の実行 */
        this.testTarget.mainProc(inputPath, outputPath);

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Mockito.verify(this.mockIsCreationService, Mockito.times(1)).initialize(inputPath, outputPath, (short) 2);
        Mockito.verify(this.mockIsCreationService, Mockito.times(1)).outputInsertionSql();

    }

    /**
     * messageSource フィールドのテスト - 正常系：フィールドが正しく設定されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMessageSource_normalBasic() throws Exception {

        /* 期待値の定義 */
        // フィールドが存在することを期待

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final Object actualMessageSource = this.reflectionModel.get("messageSource");

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertNotNull(actualMessageSource, "messageSourceフィールドが存在すること");

    }

    /**
     * onCalcInputFileOpenClicked メソッドのテスト - 正常系：既存のファイルパスがある場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOnCalcInputFileOpenClicked_normalExistingPath() throws Exception {

        /* 期待値の定義 */
        final String expectedFilePath = this.testInputFile.toAbsolutePath().toString();

        /* 準備 */
        final TextField txtInputFile = (TextField) this.reflectionModel.get("txtInputFile");
        Mockito.when(txtInputFile.getText()).thenReturn(this.testInputFile.toAbsolutePath().toString());

        try (MockedStatic<FileChooser> mockedFileChooser = Mockito.mockStatic(FileChooser.class)) {

            final FileChooser mockFileChooser = Mockito.mock(FileChooser.class);
            final File        mockFile        = this.testInputFile.toFile();

            mockedFileChooser.when(FileChooser::new).thenReturn(mockFileChooser);
            Mockito.when(mockFileChooser.showOpenDialog(ArgumentMatchers.any())).thenReturn(mockFile);

            /* テスト対象の実行 */
            final ActionEvent mockEvent = Mockito.mock(ActionEvent.class);
            final Method      method    = this.testTarget.getClass().getDeclaredMethod("onCalcInputFileOpenClicked",
                ActionEvent.class);
            method.setAccessible(true);
            method.invoke(this.testTarget, mockEvent);

            /* 検証の準備 */
            // 検証の準備は不要

            /* 検証の実施 */
            Mockito.verify(txtInputFile, Mockito.times(1)).setText(expectedFilePath);

        }

    }

    /**
     * onCalcInputFileOpenClicked メソッドのテスト - 正常系：ファイルが選択された場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOnCalcInputFileOpenClicked_normalFileSelected() throws Exception {

        /* 期待値の定義 */
        final String expectedFilePath = this.testInputFile.toAbsolutePath().toString();

        final TextField txtInputFile = (TextField) this.reflectionModel.get("txtInputFile");
        Mockito.when(txtInputFile.getText()).thenReturn("");

        try (MockedStatic<FileChooser> mockedFileChooser = Mockito.mockStatic(FileChooser.class)) {

            final FileChooser mockFileChooser = Mockito.mock(FileChooser.class);
            final File        mockFile        = this.testInputFile.toFile();

            mockedFileChooser.when(FileChooser::new).thenReturn(mockFileChooser);
            Mockito.when(mockFileChooser.showOpenDialog(ArgumentMatchers.any())).thenReturn(mockFile);

            /* テスト対象の実行 */
            final ActionEvent mockEvent = Mockito.mock(ActionEvent.class);
            final Method      method    = this.testTarget.getClass().getDeclaredMethod("onCalcInputFileOpenClicked",
                ActionEvent.class);
            method.setAccessible(true);
            method.invoke(this.testTarget, mockEvent);

            /* 検証の準備 */
            // 検証の準備は不要

            /* 検証の実施 */
            Mockito.verify(txtInputFile, Mockito.times(1)).setText(expectedFilePath);

        }

    }

    /**
     * onCalcInputFileOpenClicked メソッドのテスト - 準正常系：ファイルが選択されなかった場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOnCalcInputFileOpenClicked_semiNoFileSelected() throws Exception {

        /* 期待値の定義 */
        final String expectedFilePath = "test_path";

        /* 準備 */
        final TextField txtInputFile = (TextField) this.reflectionModel.get("txtInputFile");
        Mockito.when(txtInputFile.getText()).thenReturn(expectedFilePath);

        try (MockedStatic<FileChooser> mockedFileChooser = Mockito.mockStatic(FileChooser.class)) {

            final FileChooser mockFileChooser = Mockito.mock(FileChooser.class);

            mockedFileChooser.when(FileChooser::new).thenReturn(mockFileChooser);
            Mockito.when(mockFileChooser.showOpenDialog(ArgumentMatchers.any())).thenReturn(null);

            /* テスト対象の実行 */
            final ActionEvent mockEvent = Mockito.mock(ActionEvent.class);
            final Method      method    = this.testTarget.getClass().getDeclaredMethod("onCalcInputFileOpenClicked",
                ActionEvent.class);
            method.setAccessible(true);
            method.invoke(this.testTarget, mockEvent);

            /* 検証の準備 */
            // 検証の準備は不要

            /* 検証の実施 */
            Mockito.verify(txtInputFile, Mockito.never()).setText(ArgumentMatchers.anyString());

        }

    }

    /**
     * onCalcOutputDirectoryOpenClicked メソッドのテスト - 正常系：ディレクトリが選択された場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOnCalcOutputDirectoryOpenClicked_normalDirectorySelected() throws Exception {

        /* 期待値の定義 */
        final String expectedDirPath = this.testOutputDir.toAbsolutePath().toString();

        /* 準備 */
        final TextField txtOutputDirectory = (TextField) this.reflectionModel.get("txtOutputDirectory");
        Mockito.when(txtOutputDirectory.getText()).thenReturn("");

        try (MockedStatic<DirectoryChooser> mockedDirectoryChooser = Mockito.mockStatic(DirectoryChooser.class)) {

            final DirectoryChooser mockDirectoryChooser = Mockito.mock(DirectoryChooser.class);
            final File             mockDir              = this.testOutputDir.toFile();

            mockedDirectoryChooser.when(DirectoryChooser::new).thenReturn(mockDirectoryChooser);
            Mockito.when(mockDirectoryChooser.showDialog(ArgumentMatchers.any())).thenReturn(mockDir);

            /* テスト対象の実行 */
            final ActionEvent mockEvent = Mockito.mock(ActionEvent.class);
            final Method      method    = this.testTarget.getClass()
                .getDeclaredMethod("onCalcOutputDirectoryOpenClicked", ActionEvent.class);
            method.setAccessible(true);
            method.invoke(this.testTarget, mockEvent);

            /* 検証の準備 */
            // 検証の準備は不要

            /* 検証の実施 */
            Mockito.verify(txtOutputDirectory, Mockito.times(1)).setText(expectedDirPath);

        }

    }

    /**
     * onCalcOutputDirectoryOpenClicked メソッドのテスト - 準正常系：ディレクトリが選択されなかった場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOnCalcOutputDirectoryOpenClicked_semiNoDirectorySelected() throws Exception {

        /* 期待値の定義 */
        final String expectedDirPath = "test_dir_path";

        /* 準備 */
        final TextField txtOutputDirectory = (TextField) this.reflectionModel.get("txtOutputDirectory");
        Mockito.when(txtOutputDirectory.getText()).thenReturn(expectedDirPath);

        try (MockedStatic<DirectoryChooser> mockedDirectoryChooser = Mockito.mockStatic(DirectoryChooser.class)) {

            final DirectoryChooser mockDirectoryChooser = Mockito.mock(DirectoryChooser.class);

            mockedDirectoryChooser.when(DirectoryChooser::new).thenReturn(mockDirectoryChooser);
            Mockito.when(mockDirectoryChooser.showDialog(ArgumentMatchers.any())).thenReturn(null);

            /* テスト対象の実行 */
            final ActionEvent mockEvent = Mockito.mock(ActionEvent.class);
            final Method      method    = this.testTarget.getClass()
                .getDeclaredMethod("onCalcOutputDirectoryOpenClicked", ActionEvent.class);
            method.setAccessible(true);
            method.invoke(this.testTarget, mockEvent);

            /* 検証の準備 */
            // 検証の準備は不要

            /* 検証の実施 */
            Mockito.verify(txtOutputDirectory, Mockito.never()).setText(ArgumentMatchers.anyString());

        }

    }

    /**
     * onCalcRunClicked メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOnCalcRunClicked_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        // 例外が発生しても処理が完了することを期待

        /* 準備 */
        final TextField txtInputFile       = (TextField) this.reflectionModel.get("txtInputFile");
        final TextField txtOutputDirectory = (TextField) this.reflectionModel.get("txtOutputDirectory");
        final TextField txtThreadNum       = (TextField) this.reflectionModel.get("txtThreadNum");
        final Label     lblProcTime        = (Label) this.reflectionModel.get("lblProcTime");
        final Label     lblProcTimeUnit    = (Label) this.reflectionModel.get("lblProcTimeUnit");

        Mockito.when(txtInputFile.getText()).thenReturn(this.testInputFile.toAbsolutePath().toString());
        Mockito.when(txtOutputDirectory.getText()).thenReturn(this.testOutputDir.toAbsolutePath().toString());
        Mockito.when(txtThreadNum.getText()).thenReturn("2");

        Mockito.doNothing().when(this.mockIsCreationService).initialize(ArgumentMatchers.any(Path.class),
            ArgumentMatchers.any(Path.class), ArgumentMatchers.anyShort());

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(KmgToolLogMsgTypes.class),
                ArgumentMatchers.any(Object[].class))).thenReturn("テストエラーメッセージ");

            Mockito.doThrow(new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN08000, new Object[] {}))
                .when(this.mockIsCreationService).outputInsertionSql();

            /* テスト対象の実行 */
            final ActionEvent mockEvent = Mockito.mock(ActionEvent.class);
            final Method      method    = this.testTarget.getClass().getDeclaredMethod("onCalcRunClicked",
                ActionEvent.class);
            method.setAccessible(true);
            method.invoke(this.testTarget, mockEvent);

            /* 検証の準備 */
            // 検証の準備は不要

            /* 検証の実施 */
            Mockito.verify(lblProcTime, Mockito.times(1)).setText(ArgumentMatchers.anyString());
            Mockito.verify(lblProcTimeUnit, Mockito.times(1)).setText(ArgumentMatchers.anyString());

        }

    }

    /**
     * onCalcRunClicked メソッドのテスト - 正常系：正常な実行
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOnCalcRunClicked_normalBasic() throws Exception {

        /* 期待値の定義 */
        // 例外が発生しないことを期待

        /* 準備 */

        final TextField txtInputFile       = (TextField) this.reflectionModel.get("txtInputFile");
        final TextField txtOutputDirectory = (TextField) this.reflectionModel.get("txtOutputDirectory");
        final TextField txtThreadNum       = (TextField) this.reflectionModel.get("txtThreadNum");
        final Label     lblProcTime        = (Label) this.reflectionModel.get("lblProcTime");
        final Label     lblProcTimeUnit    = (Label) this.reflectionModel.get("lblProcTimeUnit");

        Mockito.when(txtInputFile.getText()).thenReturn(this.testInputFile.toAbsolutePath().toString());
        Mockito.when(txtOutputDirectory.getText()).thenReturn(this.testOutputDir.toAbsolutePath().toString());
        Mockito.when(txtThreadNum.getText()).thenReturn("2");

        Mockito.doNothing().when(this.mockIsCreationService).initialize(ArgumentMatchers.any(Path.class),
            ArgumentMatchers.any(Path.class), ArgumentMatchers.anyShort());
        Mockito.doNothing().when(this.mockIsCreationService).outputInsertionSql();

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            /* テスト対象の実行 */
            final ActionEvent mockEvent = Mockito.mock(ActionEvent.class);
            final Method      method    = this.testTarget.getClass().getDeclaredMethod("onCalcRunClicked",
                ActionEvent.class);
            method.setAccessible(true);
            method.invoke(this.testTarget, mockEvent);

            /* 検証の準備 */
            // 検証の準備は不要

            /* 検証の実施 */
            Mockito.verify(this.mockIsCreationService, Mockito.times(1)).initialize(ArgumentMatchers.any(Path.class),
                ArgumentMatchers.any(Path.class), ArgumentMatchers.anyShort());
            Mockito.verify(this.mockIsCreationService, Mockito.times(1)).outputInsertionSql();
            Mockito.verify(lblProcTime, Mockito.times(1)).setText(ArgumentMatchers.anyString());
            Mockito.verify(lblProcTimeUnit, Mockito.times(1)).setText(ArgumentMatchers.anyString());

        }

    }

    /**
     * txtInputFile フィールドのテスト - 正常系：フィールドが正しく設定されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testTxtInputFile_normalBasic() throws Exception {

        /* 期待値の定義 */
        // フィールドが存在することを期待

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final TextField actualTxtInputFile = (TextField) this.reflectionModel.get("txtInputFile");

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertNotNull(actualTxtInputFile, "txtInputFileフィールドが存在すること");

    }

    /**
     * txtOutputDirectory フィールドのテスト - 正常系：フィールドが正しく設定されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testTxtOutputDirectory_normalBasic() throws Exception {

        /* 期待値の定義 */
        // フィールドが存在することを期待

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final TextField actualTxtOutputDirectory = (TextField) this.reflectionModel.get("txtOutputDirectory");

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertNotNull(actualTxtOutputDirectory, "txtOutputDirectoryフィールドが存在すること");

    }

    /**
     * txtThreadNum フィールドのテスト - 正常系：フィールドが正しく設定されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testTxtThreadNum_normalBasic() throws Exception {

        /* 期待値の定義 */
        // フィールドが存在することを期待

        /* 準備 */
        // 準備は不要

        /* テスト対象の実行 */
        final TextField actualTxtThreadNum = (TextField) this.reflectionModel.get("txtThreadNum");

        /* 検証の準備 */
        // 検証の準備は不要

        /* 検証の実施 */
        Assertions.assertNotNull(actualTxtThreadNum, "txtThreadNumフィールドが存在すること");

    }

    /**
     * ディレクトリを再帰的に削除する<br>
     *
     * @since 1.0.0
     *
     * @param directory
     *                  削除対象ディレクトリ
     *
     * @throws IOException
     *                     入出力例外
     */
    @SuppressWarnings("resource")
    private void deleteDirectoryRecursively(final Path directory) throws IOException {

        if (!Files.exists(directory)) {

            return;

        }

        Files.walk(directory).sorted((a, b) -> b.compareTo(a)).forEach(path -> {

            try {

                Files.delete(path);

            } catch (final IOException e) {

                e.printStackTrace();

            }

        });

    }

}
