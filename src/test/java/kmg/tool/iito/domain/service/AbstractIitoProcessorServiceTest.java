package kmg.tool.iito.domain.service;

import java.io.IOException;
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
import org.slf4j.Logger;

import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.dtc.domain.service.DtcService;

/**
 * AbstractIitoProcessorServiceのテストクラス
 *
 * @author KenichiroArai
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class AbstractIitoProcessorServiceTest extends AbstractKmgTest {

    /**
     * テスト用のAbstractIitoProcessorService実装クラス
     */
    private static class TestAbstractIitoProcessorService extends AbstractIitoProcessorService {

        /**
         * デフォルトコンストラクタ
         */
        public TestAbstractIitoProcessorService() {

        }

        /**
         * カスタムロガーを使用するコンストラクタ
         *
         * @param logger
         *               ロガー
         */
        public TestAbstractIitoProcessorService(final Logger logger) {

            super(logger);

        }

        /**
         * writeIntermediateFileメソッドをpublicでオーバーライド
         *
         * @return true：成功、false：失敗
         *
         * @throws KmgToolMsgException
         *                             KMGツールメッセージ例外
         */
        @Override
        public boolean writeIntermediateFile() throws KmgToolMsgException {

            final boolean result = true;
            return result;

        }

    }

    /** テンポラリディレクトリ */
    @TempDir
    private Path tempDir;

    /** テスト対象 */
    private TestAbstractIitoProcessorService testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** モックKMGメッセージソース */
    private KmgMessageSource mockMessageSource;

    /** モックDTCサービス */
    private DtcService mockDtcService;

    /**
     * セットアップ
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        final TestAbstractIitoProcessorService abstractIitoProcessorService = new TestAbstractIitoProcessorService();
        this.testTarget = abstractIitoProcessorService;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.mockDtcService = Mockito.mock(DtcService.class);

        // messageSourceをリフレクションモデルに設定
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("dtcService", this.mockDtcService);

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

            // クリーンアップ処理は特に必要ない

        }

    }

    /**
     * コンストラクタのテスト - 正常系：カスタムロガーを使用するコンストラクタで初期化する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCustomLoggerConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Logger customLogger = Mockito.mock(Logger.class);

        /* テスト対象の実行 */
        final TestAbstractIitoProcessorService testInstance = new TestAbstractIitoProcessorService(customLogger);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "インスタンスが作成されること");

    }

    /**
     * コンストラクタのテスト - 正常系：デフォルトコンストラクタで初期化する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefaultConstructor() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final TestAbstractIitoProcessorService testInstance = new TestAbstractIitoProcessorService();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "インスタンスが作成されること");

    }

    /**
     * createTempntermediateFile メソッドのテスト - 異常系：Files.createTempFileでIOExceptionが発生する場合（protectedメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCreateTempntermediateFile_errorIOException() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN07006] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN07006;
        final Class<?>           expectedCauseClass    = IOException.class;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            final Path testInputFile = this.tempDir.resolve("test_input.txt");
            Files.write(testInputFile, "test content".getBytes());
            this.reflectionModel.set("inputPath", testInputFile);

            // Files.createTempFileでIOExceptionを発生させるモック
            try (final MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {

                mockedFiles.when(() -> Files.createTempFile(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                    .thenThrow(new IOException("Test IOException"));

                /* テスト対象の実行 */
                final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                    this.reflectionModel.getMethod("createTempntermediateFile");

                }, "Files.createTempFileでIOExceptionが発生した場合は例外が発生すること");

                /* 検証の実施 */
                this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                    expectedMessageTypes);

            }

        }

    }

    /**
     * createTempntermediateFile メソッドのテスト - 正常系：一時ファイルが正常に作成される場合（protectedメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCreateTempntermediateFile_normalCreate() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile = this.tempDir.resolve("test_input.txt");
        Files.write(testInputFile, "test content".getBytes());
        this.reflectionModel.set("inputPath", testInputFile);

        /* テスト対象の実行 */
        final Path testResult = (Path) this.reflectionModel.getMethod("createTempntermediateFile");

        /* 検証の準備 */
        final Path    actualResult           = testResult;
        final boolean actualExists           = Files.exists(actualResult);
        final String  actualFileName         = actualResult.getFileName().toString();
        final boolean actualHasCorrectSuffix = actualFileName.endsWith("Temp.tmp");

        /* 検証の実施 */
        Assertions.assertNotNull(actualResult, "一時ファイルが作成されること");
        Assertions.assertTrue(actualExists, "作成されたファイルが存在すること");
        Assertions.assertTrue(actualHasCorrectSuffix, "ファイル名が正しいサフィックスを持つこと");

    }

    /**
     * getInputPath メソッドのテスト - 正常系：入力ファイルパスを取得する場合
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
     * getIntermediatePath メソッドのテスト - 正常系：中間ファイルパスを取得する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIntermediatePath_normalGet() throws Exception {

        /* 期待値の定義 */
        final Path expected = this.tempDir.resolve("test_intermediate.tmp");

        /* 準備 */
        this.reflectionModel.set("intermediatePath", expected);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getIntermediatePath();

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "中間ファイルパスが正しいこと");

    }

    /**
     * getOutputPath メソッドのテスト - 正常系：出力ファイルパスを取得する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetOutputPath_normalGet() throws Exception {

        /* 期待値の定義 */
        final Path expected = this.tempDir.resolve("test_output.txt");

        /* 準備 */
        this.reflectionModel.set("outputPath", expected);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getOutputPath();

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "出力ファイルパスが正しいこと");

    }

    /**
     * getTemplatePath メソッドのテスト - 正常系：テンプレートファイルパスを取得する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetTemplatePath_normalGet() throws Exception {

        /* 期待値の定義 */
        final Path expected = this.tempDir.resolve("test_template.txt");

        /* 準備 */
        this.reflectionModel.set("templatePath", expected);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getTemplatePath();

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "テンプレートファイルパスが正しいこと");

    }

    /**
     * initialize メソッドのテスト - 異常系：createTempntermediateFileでIOExceptionが発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_errorCreateTempFileIOException() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN07006] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN07006;
        final Class<?>           expectedCauseClass    = IOException.class;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            final Path testInputFile    = this.tempDir.resolve("test_input.txt");
            final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
            final Path testOutputFile   = this.tempDir.resolve("test_output.txt");
            Files.write(testInputFile, "test content".getBytes());

            // Files.createTempFileでIOExceptionを発生させるモック
            try (final MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {

                mockedFiles.when(() -> Files.createTempFile(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                    .thenThrow(new IOException("Test IOException"));

                /* テスト対象の実行 */
                final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                    this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

                }, "createTempntermediateFileでIOExceptionが発生した場合は例外が発生すること");

                /* 検証の実施 */
                this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                    expectedMessageTypes);

            }

        }

    }

    /**
     * createTempntermediateFile メソッドのテスト - 異常系：initializeメソッド内でcreateTempntermediateFileでIOExceptionが発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_errorCreateTempntermediateFileIOException() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN07006] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN07006;
        final Class<?>           expectedCauseClass    = IOException.class;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            final Path testInputFile    = this.tempDir.resolve("test_input.txt");
            final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
            final Path testOutputFile   = this.tempDir.resolve("test_output.txt");
            Files.write(testInputFile, "test content".getBytes());

            // Files.createTempFileでIOExceptionを発生させるモック
            try (final MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {

                mockedFiles.when(() -> Files.createTempFile(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                    .thenThrow(new IOException("Test IOException"));

                /* テスト対象の実行 */
                final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                    this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

                }, "initializeメソッド内でcreateTempntermediateFileでIOExceptionが発生した場合は例外が発生すること");

                /* 検証の実施 */
                this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                    expectedMessageTypes);

            }

        }

    }

    /**
     * initialize メソッドのテスト - 正常系：初期化が成功する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalInitialization() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.txt");
        Files.write(testInputFile, "test content".getBytes());

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

        /* 検証の準備 */
        final boolean actualResult           = testResult;
        final Path    actualInputPath        = (Path) this.reflectionModel.get("inputPath");
        final Path    actualTemplatePath     = (Path) this.reflectionModel.get("templatePath");
        final Path    actualOutputPath       = (Path) this.reflectionModel.get("outputPath");
        final Path    actualIntermediatePath = (Path) this.reflectionModel.get("intermediatePath");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が成功すること");
        Assertions.assertEquals(testInputFile, actualInputPath, "入力ファイルパスが正しく設定されること");
        Assertions.assertEquals(testTemplateFile, actualTemplatePath, "テンプレートファイルパスが正しく設定されること");
        Assertions.assertEquals(testOutputFile, actualOutputPath, "出力ファイルパスが正しく設定されること");
        Assertions.assertNotNull(actualIntermediatePath, "中間ファイルパスが作成されること");

    }

    /**
     * process メソッドのテスト - 異常系：dtcService.initializeでfalseが返される場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorDtcServiceInitializeFalse() throws Exception {

        /* 期待値の定義 */

        // モックメッセージソースの設定
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.txt");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

        // DTCサービスのモック設定（initializeでfalseを返す）
        Mockito
            .when(
                this.mockDtcService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "dtcService.initializeでfalseが返される場合の結果が正しいこと");

    }

    /**
     * process メソッドのテスト - 異常系：dtcService.processでfalseが返される場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorDtcServiceProcessFalse() throws Exception {

        /* 期待値の定義 */

        // モックメッセージソースの設定
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.txt");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

        // DTCサービスのモック設定（processでfalseを返す）
        Mockito
            .when(
                this.mockDtcService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockDtcService.process()).thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "dtcService.processでfalseが返される場合の結果が正しいこと");

    }

    /**
     * process メソッドのテスト - 異常系：writeIntermediateFileでKmgToolMsgExceptionが発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorWriteIntermediateFileException() throws Exception {

        /* 期待値の定義 */
        final Class<?> expectedCauseClass = Exception.class;

        // モックメッセージソースの設定
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.txt");
        Files.write(testInputFile, "test content".getBytes());

        // writeIntermediateFileで例外を発生させるテスト用クラス
        final TestAbstractIitoProcessorService exceptionTestTarget = new TestAbstractIitoProcessorService() {

            @Override
            public boolean writeIntermediateFile() throws KmgToolMsgException {

                throw new RuntimeException("Test exception");

            }
        };
        exceptionTestTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

        // messageSourceを設定
        final KmgReflectionModelImpl exceptionReflectionModel = new KmgReflectionModelImpl(exceptionTestTarget);
        exceptionReflectionModel.set("messageSource", this.mockMessageSource);

        /* テスト対象の実行 */
        final Exception actualException = Assertions.assertThrows(Exception.class, () -> {

            exceptionTestTarget.process();

        }, "writeIntermediateFileで例外が発生した場合は例外が発生すること");

        /* 検証の実施 */
        Assertions.assertInstanceOf(expectedCauseClass, actualException, "期待した例外の種類の例外が発生したこと");

    }

    /**
     * process メソッドのテスト - 正常系：処理が成功する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_normalProcess() throws Exception {

        /* 期待値の定義 */

        // モックメッセージソースの設定
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.txt");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

        // DTCサービスのモック設定
        Mockito
            .when(
                this.mockDtcService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockDtcService.process()).thenReturn(true);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "処理が成功すること");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 正常系：抽象メソッドが正常に実行される場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFile_normalExecute() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeIntermediateFile();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "抽象メソッドが正常に実行されること");

    }

}
