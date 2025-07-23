package kmg.tool.is.presentation.controller;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;

/**
 * IsCreationControllerのテストクラス
 *
 * @author KenichiroArai
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class IsCreationControllerTest extends AbstractKmgTest {

    /** テンポラリディレクトリ */
    @TempDir
    private Path tempDir;

    /** テスト対象 */
    private IsCreationController testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** モックメッセージソース */
    @Mock
    private KmgMessageSource mockMessageSource;

    /** モックロガー */
    @Mock
    private Logger mockLogger;

    /** モックアクションイベント */
    @Mock
    private ActionEvent mockActionEvent;

    /** モック挿入SQL作成サービス */
    @Mock
    private kmg.tool.is.application.service.IsCreationService mockIsCreationService;

    /**
     * JavaFXの初期化
     */
    @BeforeAll
    public static void initializeJavaFX() {

        Platform.startup(() -> {

            // JavaFXの初期化処理
        });

    }

    /**
     * セットアップ
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        this.testTarget = new IsCreationController(this.mockLogger);
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // メッセージソースをリフレクションで設定
        this.reflectionModel.set("messageSource", this.mockMessageSource);

        // 挿入SQL作成サービスをリフレクションで設定
        this.reflectionModel.set("isCreationService", this.mockIsCreationService);

    }

    /**
     * クリーンアップ
     *
     * @throws Exception
     *                   例外
     */
    @AfterEach
    public void tearDown() throws Exception {

        if (this.testTarget != null) {

            // クリーンアップ処理
        }

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：カスタムロガーを使用した初期化
     */
    @Test
    public void testConstructor_normalCustomLogger() {

        /* 期待値の定義 */
        // 期待値なし（コンストラクタの動作確認のみ）

        /* 準備 */
        final Logger testLogger = LoggerFactory.getLogger("TestLogger");

        /* テスト対象の実行 */
        final IsCreationController actualResult = new IsCreationController(testLogger);

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        Assertions.assertNotNull(actualResult, "カスタムロガーを使用したコンストラクタが正常に動作すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：標準ロガーを使用した初期化
     */
    @Test
    public void testConstructor_normalStandardLogger() {

        /* 期待値の定義 */
        // 期待値なし（コンストラクタの動作確認のみ）

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        final IsCreationController actualResult = new IsCreationController();

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        Assertions.assertNotNull(actualResult, "標準ロガーを使用したコンストラクタが正常に動作すること");

    }

    /**
     * initialize メソッドのテスト - 正常系：正常な初期化
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalInitialization() throws Exception {

        /* 期待値の定義 */
        final String expectedThreadNum = String.valueOf(Runtime.getRuntime().availableProcessors());

        /* 準備 */
        final URL            testLocation  = URI.create("file:///test").toURL();
        final ResourceBundle testResources = Mockito.mock(ResourceBundle.class);

        // txtThreadNumをリフレクションで設定（JavaFXコンポーネントのnull問題を回避）
        final TextField actualTextField = new TextField();
        this.reflectionModel.set("txtThreadNum", actualTextField);

        /* テスト対象の実行 */
        this.testTarget.initialize(testLocation, testResources);

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        final String actualThreadNum = actualTextField.getText();
        Assertions.assertEquals(expectedThreadNum, actualThreadNum, "スレッド数が正しく設定されること");

    }

    /**
     * mainProc メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMainProc_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN08000] 入力ファイルパスがnullです。";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN08000;
        final Class<?>           expectedCauseClass    = null;

        /* 準備 */
        // 存在しないファイルパスを指定してKmgToolMsgExceptionを発生させる
        final Path nonExistentInputPath = this.tempDir.resolve("non_existent.xlsx");
        final Path expectedOutputPath   = this.tempDir.resolve("output.sql");

        // txtThreadNumをリフレクションで設定（JavaFXコンポーネントのnull問題を回避）
        final TextField actualTextField = new TextField();
        actualTextField.setText("4");
        this.reflectionModel.set("txtThreadNum", actualTextField);

        // IsCreationServiceのモック設定
        Mockito.doNothing().when(this.mockIsCreationService).initialize(ArgumentMatchers.any(), ArgumentMatchers.any(),
            ArgumentMatchers.anyShort());

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // KmgToolMsgExceptionの作成をMockedStaticのスコープ内で行う
            final KmgToolMsgException testException = new KmgToolMsgException(expectedMessageTypes, new Object[] {});
            Mockito.doThrow(testException).when(this.mockIsCreationService).outputInsertionSql();

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.mainProc(nonExistentInputPath, expectedOutputPath);

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * mainProc メソッドのテスト - 正常系：正常な処理
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMainProc_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final Path expectedInputPath  = this.tempDir.resolve("input.xlsx");
        final Path expectedOutputPath = this.tempDir.resolve("output.sql");

        /* 準備 */
        // テスト用のダミーファイルを作成
        expectedInputPath.toFile().createNewFile();

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            /* テスト対象の実行 */

            this.testTarget.mainProc(expectedInputPath, expectedOutputPath);

            /* 検証の準備 */
            // 検証の準備なし

            /* 検証の実施 */

        }

    }

}
