package kmg.tool.base.input.domain.service;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseGenMsgTypes;

/**
 * AbstractInputServiceのテストクラス
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class AbstractInputServiceTest extends AbstractKmgTest {

    /**
     * テスト用のAbstractInputService実装クラス
     */
    private static class TestAbstractInputService extends AbstractInputService {

        /**
         * デフォルトコンストラクタ
         *
         * @since 0.2.0
         */
        public TestAbstractInputService() {

        }

        /**
         * processメソッドをpublicでオーバーライド
         *
         * @since 0.2.4
         *
         * @return true：成功、false：失敗
         *
         * @throws KmgToolBaseMsgException
         *                                 KMGツールメッセージ例外
         */
        @Override
        public boolean process() throws KmgToolBaseMsgException {

            final boolean result = true;
            return result;

        }

    }

    /**
     * テンポラリディレクトリ
     *
     * @since 0.2.0
     */
    @TempDir
    private Path tempDir;

    /**
     * テスト対象
     *
     * @since 0.2.0
     */
    private TestAbstractInputService testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.2.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * モックKMGメッセージソース
     *
     * @since 0.2.0
     */
    private KmgMessageSource mockMessageSource;

    /**
     * セットアップ
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        final TestAbstractInputService abstractInputService = new TestAbstractInputService();
        this.testTarget = abstractInputService;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

    }

    /**
     * クリーンアップ
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @AfterEach
    public void tearDown() throws Exception {

        if (this.testTarget != null) {

            // クリーンアップ処理は特に必要ない

        }

    }

    /**
     * コンストラクタのテスト - 正常系：デフォルトコンストラクタで初期化する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefaultConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final TestAbstractInputService testInstance = new TestAbstractInputService();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "インスタンスが作成されること");

    }

    /**
     * getInputPath メソッドのテスト - 正常系：入力ファイルパスを取得する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetInputPath_normalGet() throws Exception {

        /* 期待値の定義 */
        final Path expected = this.tempDir.resolve("test_input.txt");

        /* 準備 */
        this.reflectionModel.set("inputPath", expected);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getInputPath();

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "入力ファイルパスが正しいこと");

    }

    /**
     * initialize メソッドのテスト - 異常系：inputPathが存在しない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_errorInputPathNotExists() throws Exception {

        /* 期待値の定義 */
        final String                 expectedDomainMessage = "[KMGTOOLBASE_GEN08001] ";
        final KmgToolBaseGenMsgTypes expectedMessageTypes  = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN08001;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            final Path testInputPath = this.tempDir.resolve("non_existent_file.txt");

            /* テスト対象の実行 */
            final KmgToolBaseMsgException actualException
                = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                    this.testTarget.initialize(testInputPath);

                }, "inputPathが存在しない場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * initialize メソッドのテスト - 異常系：inputPathがnullの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_errorInputPathNull() throws Exception {

        /* 期待値の定義 */
        final String                 expectedDomainMessage = "[KMGTOOLBASE_GEN08000] ";
        final KmgToolBaseGenMsgTypes expectedMessageTypes  = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN08000;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            final Path testInputPath = null;

            /* テスト対象の実行 */
            final KmgToolBaseMsgException actualException
                = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                    this.testTarget.initialize(testInputPath);

                }, "inputPathがnullの場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * initialize メソッドのテスト - 正常系：初期化が成功する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalInitialization() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile = this.tempDir.resolve("test_input.txt");
        Files.write(testInputFile, "test content".getBytes());

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(testInputFile);

        /* 検証の準備 */
        final boolean actualResult    = testResult;
        final Path    actualInputPath = (Path) this.reflectionModel.get("inputPath");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が成功すること");
        Assertions.assertEquals(testInputFile, actualInputPath, "入力ファイルパスが正しく設定されること");

    }

    /**
     * process メソッドのテスト - 正常系：抽象メソッドが正常に実行される場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_normalExecute() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "抽象メソッドが正常に実行されること");

    }

}
