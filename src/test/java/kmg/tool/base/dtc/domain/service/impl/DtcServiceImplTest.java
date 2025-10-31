package kmg.tool.base.dtc.domain.service.impl;

import java.io.IOException;
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
import org.slf4j.Logger;

import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.dtc.domain.logic.DtcLogic;

/**
 * DtcServiceImplのテストクラス
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class DtcServiceImplTest extends AbstractKmgTest {

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
    private DtcServiceImpl testTarget;

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
     * モックDtcLogic
     *
     * @since 0.2.0
     */
    private DtcLogic mockDtcLogic;

    /**
     * セットアップ
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @SuppressWarnings("resource")
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        final DtcServiceImpl dtcServiceImpl = new DtcServiceImpl();
        this.testTarget = dtcServiceImpl;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.mockDtcLogic = Mockito.mock(DtcLogic.class);

        /* モックの設定 */
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("dtcLogic", this.mockDtcLogic);

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

            // クリーンアップ処理
        }

    }

    /**
     * closeDtcLogic メソッドのテスト - 異常系：IOException発生
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testCloseDtcLogic_errorIOException() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN03006] テストメッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN03006;
        final Class<?>           expectedCauseClass    = IOException.class;

        /* 準備 */
        try {

            Mockito.doThrow(new IOException("テスト用IOException")).when(this.mockDtcLogic).close();

        } catch (final IOException e) {

            // テスト用の例外なので無視
            e.printStackTrace();

        }

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("closeDtcLogic");

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * closeDtcLogic メソッドのテスト - 正常系：正常なクローズ処理
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testCloseDtcLogic_normalClose() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        Mockito.doNothing().when(this.mockDtcLogic).close();

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeDtcLogic");

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "クローズ処理が正常に実行されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：カスタムロガーを使用した初期化
     *
     * @since 0.2.0
     */
    @Test
    public void testConstructor_normalCustomLogger() {

        /* 期待値の定義 */

        /* 準備 */
        final Logger mockLogger = Mockito.mock(Logger.class);

        /* テスト対象の実行 */
        final DtcServiceImpl testConstructor = new DtcServiceImpl(mockLogger);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが正常に作成されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：標準ロガーを使用した初期化
     *
     * @since 0.2.0
     */
    @Test
    public void testConstructor_normalStandardLogger() {

        /* 期待値の定義 */

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        final DtcServiceImpl testConstructor = new DtcServiceImpl();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが正常に作成されること");

    }

    /**
     * getInputPath メソッドのテスト - 正常系：入力ファイルパスを取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetInputPath_normalGetPath() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expectedPath = this.tempDir.resolve("input.txt");

        /* 準備 */
        this.reflectionModel.set("inputPath", expectedPath);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getInputPath();

        /* 検証の準備 */
        final Path actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedPath, actualResult, "入力ファイルパスが正しく取得されること");

    }

    /**
     * getInputPath メソッドのテスト - 準正常系：nullの入力ファイルパスを取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetInputPath_semiNullPath() throws KmgReflectionException {

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
     * getOutputPath メソッドのテスト - 正常系：出力ファイルパスを取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetOutputPath_normalGetPath() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expectedPath = this.tempDir.resolve("output.txt");

        /* 準備 */
        this.reflectionModel.set("outputPath", expectedPath);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getOutputPath();

        /* 検証の準備 */
        final Path actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedPath, actualResult, "出力ファイルパスが正しく取得されること");

    }

    /**
     * getOutputPath メソッドのテスト - 準正常系：nullの出力ファイルパスを取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetOutputPath_semiNullPath() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expectedPath = null;

        /* 準備 */
        this.reflectionModel.set("outputPath", null);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getOutputPath();

        /* 検証の準備 */
        final Path actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedPath, actualResult, "nullの出力ファイルパスが正しく取得されること");

    }

    /**
     * getTemplatePath メソッドのテスト - 正常系：テンプレートファイルパスを取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetTemplatePath_normalGetPath() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expectedPath = this.tempDir.resolve("template.yml");

        /* 準備 */
        this.reflectionModel.set("templatePath", expectedPath);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getTemplatePath();

        /* 検証の準備 */
        final Path actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedPath, actualResult, "テンプレートファイルパスが正しく取得されること");

    }

    /**
     * getTemplatePath メソッドのテスト - 準正常系：nullのテンプレートファイルパスを取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetTemplatePath_semiNullPath() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expectedPath = null;

        /* 準備 */
        this.reflectionModel.set("templatePath", null);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getTemplatePath();

        /* 検証の準備 */
        final Path actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedPath, actualResult, "nullのテンプレートファイルパスが正しく取得されること");

    }

    /**
     * initialize メソッドのテスト - 正常系：正常な初期化
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testInitialize_normalInitialization() throws KmgToolMsgException {

        /* 期待値の定義 */
        final Path expectedInputPath    = this.tempDir.resolve("input.txt");
        final Path expectedTemplatePath = this.tempDir.resolve("template.yml");
        final Path expectedOutputPath   = this.tempDir.resolve("output.txt");

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        final boolean testResult
            = this.testTarget.initialize(expectedInputPath, expectedTemplatePath, expectedOutputPath);

        /* 検証の準備 */
        final boolean actualResult       = testResult;
        final Path    actualInputPath    = this.testTarget.getInputPath();
        final Path    actualTemplatePath = this.testTarget.getTemplatePath();
        final Path    actualOutputPath   = this.testTarget.getOutputPath();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が成功すること");
        Assertions.assertEquals(expectedInputPath, actualInputPath, "入力ファイルパスが正しく設定されること");
        Assertions.assertEquals(expectedTemplatePath, actualTemplatePath, "テンプレートファイルパスが正しく設定されること");
        Assertions.assertEquals(expectedOutputPath, actualOutputPath, "出力ファイルパスが正しく設定されること");

    }

    /**
     * process メソッドのテスト - 異常系：DtcLogicのcloseでIOException発生
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testProcess_errorCloseIOException() throws KmgToolMsgException {

        /* 期待値の定義 */
        final String             expectedStartLogMsg   = "処理開始ログメッセージ";
        final String             expectedEndLogMsg     = "処理終了ログメッセージ";
        final String             expectedDomainMessage = "[KMGTOOL_GEN03006] テストメッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN03006;
        final Class<?>           expectedCauseClass    = IOException.class;

        /* 準備 */
        final Path inputPath    = this.tempDir.resolve("input.txt");
        final Path templatePath = this.tempDir.resolve("template.yml");
        final Path outputPath   = this.tempDir.resolve("output.txt");

        this.testTarget.initialize(inputPath, templatePath, outputPath);

        Mockito.when(this.mockMessageSource.getLogMessage(KmgToolLogMsgTypes.KMGTOOL_LOG03000, new Object[] {}))
            .thenReturn(expectedStartLogMsg);
        Mockito.when(this.mockMessageSource.getLogMessage(KmgToolLogMsgTypes.KMGTOOL_LOG03001, new Object[] {}))
            .thenReturn(expectedEndLogMsg);

        Mockito.when(this.mockDtcLogic.readOneLineOfData()).thenReturn(false);

        try {

            Mockito.doThrow(new IOException("テスト用IOException")).when(this.mockDtcLogic).close();

        } catch (final IOException e) {

            // テスト用の例外なので無視
            e.printStackTrace();

        }

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMothod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMothod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMothod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * process メソッドのテスト - 正常系：正常な処理実行
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("resource")
    @Test
    public void testProcess_normalProcess() throws KmgToolMsgException {

        /* 期待値の定義 */
        final boolean expectedResult      = true;
        final String  expectedStartLogMsg = "処理開始ログメッセージ";
        final String  expectedEndLogMsg   = "処理終了ログメッセージ";

        /* 準備 */
        final Path inputPath    = this.tempDir.resolve("input.txt");
        final Path templatePath = this.tempDir.resolve("template.yml");
        final Path outputPath   = this.tempDir.resolve("output.txt");

        this.testTarget.initialize(inputPath, templatePath, outputPath);

        Mockito.when(this.mockMessageSource.getLogMessage(KmgToolLogMsgTypes.KMGTOOL_LOG03000, new Object[] {}))
            .thenReturn(expectedStartLogMsg);
        Mockito.when(this.mockMessageSource.getLogMessage(KmgToolLogMsgTypes.KMGTOOL_LOG03001, new Object[] {}))
            .thenReturn(expectedEndLogMsg);

        Mockito.when(this.mockDtcLogic.readOneLineOfData()).thenReturn(true, true, false);
        Mockito.doNothing().when(this.mockDtcLogic).applyTemplateToInputFile();
        Mockito.when(this.mockDtcLogic.addOutputBufferContent()).thenReturn(true);
        Mockito.when(this.mockDtcLogic.writeOutputBuffer()).thenReturn(true);
        Mockito.doNothing().when(this.mockDtcLogic).clearOutputBufferContent();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "処理が成功すること");

    }

    /**
     * process メソッドのテスト - 準正常系：データなし（1行目で終了）
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testProcess_semiNoData() throws KmgToolMsgException {

        /* 期待値の定義 */
        final boolean expectedResult      = true;
        final String  expectedStartLogMsg = "処理開始ログメッセージ";
        final String  expectedEndLogMsg   = "処理終了ログメッセージ";

        /* 準備 */
        final Path inputPath    = this.tempDir.resolve("input.txt");
        final Path templatePath = this.tempDir.resolve("template.yml");
        final Path outputPath   = this.tempDir.resolve("output.txt");

        this.testTarget.initialize(inputPath, templatePath, outputPath);

        Mockito.when(this.mockMessageSource.getLogMessage(KmgToolLogMsgTypes.KMGTOOL_LOG03000, new Object[] {}))
            .thenReturn(expectedStartLogMsg);
        Mockito.when(this.mockMessageSource.getLogMessage(KmgToolLogMsgTypes.KMGTOOL_LOG03001, new Object[] {}))
            .thenReturn(expectedEndLogMsg);

        Mockito.when(this.mockDtcLogic.readOneLineOfData()).thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "処理が成功すること");

    }

}
