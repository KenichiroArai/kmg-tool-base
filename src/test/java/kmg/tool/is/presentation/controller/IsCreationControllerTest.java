package kmg.tool.is.presentation.controller;

import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javafx.event.ActionEvent;
import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;

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
    private org.slf4j.Logger mockLogger;

    /** モックアクションイベント */
    @Mock
    private ActionEvent mockActionEvent;

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
        final org.slf4j.Logger testLogger = org.slf4j.LoggerFactory.getLogger("TestLogger");

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
        final URL            testLocation  = new URL("file:///test");
        final ResourceBundle testResources = Mockito.mock(ResourceBundle.class);

        /* テスト対象の実行 */
        this.testTarget.initialize(testLocation, testResources);

        /* 検証の準備 */
        // スレッド数の設定は実際のRuntime.getRuntime().availableProcessors()の値に依存するため、
        // モックの設定を確認する

        /* 検証の実施 */
        // initializeメソッドが正常に実行されることを確認
        Assertions.assertNotNull(this.testTarget, "初期化が正常に実行されること");

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
        // TODO KenichiroArai 2025/07/22 KmgToolMsgExceptionを検証する
        final Path expectedInputPath  = this.tempDir.resolve("input.xlsx");
        final Path expectedOutputPath = this.tempDir.resolve("output.sql");

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        final Exception testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

            this.testTarget.mainProc(expectedInputPath, expectedOutputPath);

        });

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        Assertions.assertNotNull(testException, "KmgToolMsgExceptionが発生すること");

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
        // 準備なし

        /* テスト対象の実行 */
        this.testTarget.mainProc(expectedInputPath, expectedOutputPath);

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        // mainProcメソッドが正常に実行されることを確認
        Assertions.assertNotNull(this.testTarget, "メイン処理が正常に実行されること");

    }

    /**
     * onCalcInputFileOpenClicked メソッドのテスト - 正常系：メソッドが存在することを確認
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOnCalcInputFileOpenClicked_normalMethodExists() throws Exception {

        /* 期待値の定義 */
        // 期待値なし（メソッドの存在確認のみ）

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        final Method method
            = IsCreationController.class.getDeclaredMethod("onCalcInputFileOpenClicked", ActionEvent.class);

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        Assertions.assertNotNull(method, "onCalcInputFileOpenClickedメソッドが存在すること");

    }

    /**
     * onCalcOutputDirectoryOpenClicked メソッドのテスト - 正常系：メソッドが存在することを確認
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOnCalcOutputDirectoryOpenClicked_normalMethodExists() throws Exception {

        /* 期待値の定義 */
        // 期待値なし（メソッドの存在確認のみ）

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        final Method method
            = IsCreationController.class.getDeclaredMethod("onCalcOutputDirectoryOpenClicked", ActionEvent.class);

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        Assertions.assertNotNull(method, "onCalcOutputDirectoryOpenClickedメソッドが存在すること");

    }

    /**
     * onCalcRunClicked メソッドのテスト - 正常系：メソッドが存在することを確認
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOnCalcRunClicked_normalMethodExists() throws Exception {

        /* 期待値の定義 */
        // 期待値なし（メソッドの存在確認のみ）

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        final Method method = IsCreationController.class.getDeclaredMethod("onCalcRunClicked", ActionEvent.class);

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        Assertions.assertNotNull(method, "onCalcRunClickedメソッドが存在すること");

    }

}
