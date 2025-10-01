package kmg.tool.simple.domain.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.simple.domain.service.SimpleInputServiceImpl;

/**
 * SimpleInputServiceImplのテストクラス
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
    "nls", "static-method",
})
public class SimpleInputServiceImplTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private SimpleInputServiceImpl testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.1.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * テスト用入力パス
     *
     * @since 0.1.0
     */
    private Path testInputPath;

    /**
     * セットアップ
     *
     * @since 0.1.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        final SimpleInputServiceImpl simpleInputServiceImpl = new SimpleInputServiceImpl();
        this.testTarget = simpleInputServiceImpl;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト用パスの設定 */
        this.testInputPath = Paths.get("test/input.txt");

    }

    /**
     * クリーンアップ
     *
     * @since 0.1.0
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
     * コンストラクタ メソッドのテスト - 正常系：標準ロガーを使用した初期化
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalStandardLogger() {

        /* 期待値の定義 */

        /* 準備 */
        final SimpleInputServiceImpl testConstructor = new SimpleInputServiceImpl();

        /* テスト対象の実行 */

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "コンストラクタが正常に初期化されること");

    }

    /**
     * getInputPath メソッドのテスト - 正常系：入力ファイルパスの取得
     *
     * @since 0.1.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetInputPath_normalGetInputPath() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expectedResult = this.testInputPath;

        /* 準備 */
        this.reflectionModel.set("inputPath", this.testInputPath);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getInputPath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, testResult, "入力ファイルパスが正しく取得されること");

    }

    /**
     * getInputPath メソッドのテスト - 準正常系：nullの入力ファイルパスの取得
     *
     * @since 0.1.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetInputPath_semiNullInputPath() throws KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("inputPath", null);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getInputPath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNull(testResult, "nullの入力ファイルパスが正しく取得されること");

    }

    /**
     * initialize メソッドのテスト - 異常系：入力パスが存在しない場合
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    @Disabled
    public void testInitialize_errorInputPathNotExists() throws KmgToolMsgException {

        /* 期待値の定義 */
        final Path nonExistentPath = Paths.get("non/existent/path");

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class);
            final var mockFiles = Mockito.mockStatic(Files.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // Files.existsのモック化
            mockFiles.when(() -> Files.exists(ArgumentMatchers.any(Path.class))).thenReturn(false);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.initialize(nonExistentPath);

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(testException, "KmgToolMsgExceptionが正しく発生すること");

        }

    }

    /**
     * initialize メソッドのテスト - 異常系：入力パスがnullの場合
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    @Disabled
    public void testInitialize_errorNullInputPath() throws KmgToolMsgException {

        /* 期待値の定義 */

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.initialize(null);

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(testException, "KmgToolMsgExceptionが正しく発生すること");

        }

    }

    /**
     * initialize メソッドのテスト - 正常系：正常な初期化
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalInitialization() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class);
            final var mockFiles = Mockito.mockStatic(Files.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // Files.existsのモック化
            mockFiles.when(() -> Files.exists(ArgumentMatchers.any(Path.class))).thenReturn(true);

            /* テスト対象の実行 */
            final boolean testResult = this.testTarget.initialize(this.testInputPath);

            /* 検証の準備 */
            final boolean actualResult    = testResult;
            final Path    actualInputPath = (Path) this.reflectionModel.get("inputPath");

            /* 検証の実施 */
            Assertions.assertTrue(actualResult, "初期化が正常に完了すること");
            Assertions.assertEquals(this.testInputPath, actualInputPath, "入力ファイルパスが正しく設定されること");

        }

    }

    /**
     * process メソッドのテスト - 正常系：正常な処理
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testProcess_normalProcess() throws KmgToolMsgException {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "処理が正常に完了すること");

    }

}
