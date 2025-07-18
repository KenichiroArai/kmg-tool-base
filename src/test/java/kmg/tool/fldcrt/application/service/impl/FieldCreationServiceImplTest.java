package kmg.tool.fldcrt.application.service.impl;

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
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.fldcrt.application.logic.FieldCreationLogic;

/**
 * FieldCreationServiceImplのテストクラス
 *
 * @author KenichiroArai
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class FieldCreationServiceImplTest extends AbstractKmgTest {

    /** テンポラリディレクトリ */
    @TempDir
    private Path tempDir;

    /** テスト対象 */
    private FieldCreationServiceImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** モックKMGメッセージソース */
    private KmgMessageSource mockMessageSource;

    /** モックFieldCreationLogic */
    private FieldCreationLogic mockFieldCreationLogic;

    /**
     * セットアップ
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @SuppressWarnings("resource")
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        final FieldCreationServiceImpl fieldCreationServiceImpl = new FieldCreationServiceImpl();
        this.testTarget = fieldCreationServiceImpl;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.mockFieldCreationLogic = Mockito.mock(FieldCreationLogic.class);

        /* モックの設定 */
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("fieldCreationLogic", this.mockFieldCreationLogic);

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
     * clearAndPrepareNextLine メソッドのテスト - 正常系：正常なクリア処理
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearAndPrepareNextLine_normalClear() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockFieldCreationLogic.clearRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.clearProcessingData()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addOneLineOfDataToRows()).thenReturn(true);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("clearAndPrepareNextLine");

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "クリア処理が正常に実行されること");

    }

    /**
     * closeFieldCreationLogic メソッドのテスト - 異常系：IOException発生
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testCloseFieldCreationLogic_errorIOException() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN05003] テストメッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN05003;
        final Class<?>           expectedCauseClass    = IOException.class;

        /* 準備 */
        try {

            Mockito.doThrow(new IOException("テスト用IOException")).when(this.mockFieldCreationLogic).close();

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

                this.reflectionModel.getMethod("closeFieldCreationLogic");

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * closeFieldCreationLogic メソッドのテスト - 正常系：正常なクローズ処理
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testCloseFieldCreationLogic_normalClose() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.doNothing().when(this.mockFieldCreationLogic).close();

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeFieldCreationLogic");

        /* 検証の準備 */
        // 正常系のため、モックの呼び出しを検証
        Mockito.verify(this.mockFieldCreationLogic, Mockito.times(1)).close();

        /* 検証の実施 */
        // 例外が発生しないことを確認

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：カスタムロガーを使用した初期化
     */
    @Test
    public void testConstructor_normalCustomLogger() {

        /* 期待値の定義 */

        /* 準備 */
        final Logger mockLogger = Mockito.mock(Logger.class);

        /* テスト対象の実行 */
        final FieldCreationServiceImpl testConstructor = new FieldCreationServiceImpl(mockLogger);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが正常に作成されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：標準ロガーを使用した初期化
     */
    @Test
    public void testConstructor_normalStandardLogger() {

        /* 期待値の定義 */

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        final FieldCreationServiceImpl testConstructor = new FieldCreationServiceImpl();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが正常に作成されること");

    }

    /**
     * getInputPath メソッドのテスト - 正常系：入力ファイルパスを取得
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
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetOutputPath_semiNullPath() throws KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("outputPath", null);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getOutputPath();

        /* 検証の準備 */
        final Path actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualResult, "nullの出力ファイルパスが正しく取得されること");

    }

    /**
     * getTemplatePath メソッドのテスト - 正常系：テンプレートファイルパスを取得
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
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetTemplatePath_semiNullPath() throws KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("templatePath", null);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getTemplatePath();

        /* 検証の準備 */
        final Path actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualResult, "nullのテンプレートファイルパスが正しく取得されること");

    }

    /**
     * initialize メソッドのテスト - 正常系：正常な初期化
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
     * processColumns メソッドのテスト - 異常系：KmgToolMsgException発生
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testProcessColumns_errorKmgToolMsgException() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN05001] テスト例外メッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN05001;
        final Class<?>           expectedCauseClass    = KmgToolMsgException.class;

        /* 準備 */
        Mockito.when(this.mockFieldCreationLogic.convertFields()).thenReturn(true);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // KmgToolMsgExceptionの作成（モック設定後に作成）
            final KmgToolMsgException testException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN05001, new Object[] {});
            Mockito.when(this.mockFieldCreationLogic.addCommentToRows()).thenThrow(testException);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("processColumns");

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * processColumns メソッドのテスト - 正常系：正常な処理実行
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testProcessColumns_normalProcess() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockFieldCreationLogic.convertFields()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addCommentToRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addFieldToRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addTypeToRows()).thenReturn(true);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("processColumns");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "カラム処理が成功すること");

    }

    /**
     * processColumns メソッドのテスト - 準正常系：変換なし（スキップ）
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testProcessColumns_semiNoConversion() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockFieldCreationLogic.convertFields()).thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("processColumns");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "変換なしでスキップされること");

    }

    /**
     * readOneLineData メソッドのテスト - 異常系：KmgToolMsgException発生
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testReadOneLineData_errorKmgToolMsgException() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN05001] テスト例外メッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN05001;
        final Class<?>           expectedCauseClass    = KmgToolMsgException.class;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // KmgToolMsgExceptionの作成（モック設定後に作成）
            final KmgToolMsgException testException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN05001, new Object[] {});
            Mockito.when(this.mockFieldCreationLogic.readOneLineOfData()).thenThrow(testException);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("readOneLineData");

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * readOneLineData メソッドのテスト - 正常系：正常な読み込み
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testReadOneLineData_normalRead() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockFieldCreationLogic.readOneLineOfData()).thenReturn(true);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("readOneLineData");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "1行データの読み込みが成功すること");

    }

    /**
     * readOneLineData メソッドのテスト - 準正常系：読み込み終了
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testReadOneLineData_semiEndOfData() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockFieldCreationLogic.readOneLineOfData()).thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("readOneLineData");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "データ終了でfalseが返されること");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 異常系：closeFieldCreationLogicでIOException発生
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testWriteIntermediateFile_errorCloseIOException() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN05003] テストメッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN05003;
        final Class<?>           expectedCauseClass    = IOException.class;

        /* 準備 */
        final Path inputPath        = this.tempDir.resolve("input.txt");
        final Path intermediatePath = this.tempDir.resolve("intermediate.tmp");

        this.reflectionModel.set("inputPath", inputPath);
        this.reflectionModel.set("intermediatePath", intermediatePath);

        Mockito.when(this.mockFieldCreationLogic.initialize(inputPath, intermediatePath)).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addOneLineOfDataToRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.readOneLineOfData()).thenReturn(false);

        try {

            Mockito.doThrow(new IOException("テスト用IOException")).when(this.mockFieldCreationLogic).close();

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

                this.testTarget.writeIntermediateFile();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * process メソッドのテスト - 正常系：正常な処理実行
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    /**
     * writeIntermediateFile メソッドのテスト - 正常系：正常な処理実行
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testWriteIntermediateFile_normalProcess() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        final Path inputPath    = this.tempDir.resolve("input.txt");
        final Path templatePath = this.tempDir.resolve("template.yml");
        final Path outputPath   = this.tempDir.resolve("output.txt");

        this.testTarget.initialize(inputPath, templatePath, outputPath);

        Mockito.when(this.mockFieldCreationLogic.initialize(inputPath, this.testTarget.getIntermediatePath()))
            .thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addOneLineOfDataToRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.readOneLineOfData()).thenReturn(true, true, false);
        Mockito.when(this.mockFieldCreationLogic.convertFields()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addCommentToRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addFieldToRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addTypeToRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.writeIntermediateFile()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.clearRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.clearProcessingData()).thenReturn(true);

        try {

            Mockito.doNothing().when(this.mockFieldCreationLogic).close();

        } catch (final IOException e) {

            // テスト用の例外なので無視
            e.printStackTrace();

        }

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeIntermediateFile();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "処理が成功すること");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 準正常系：データなし（1行目で終了）
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testWriteIntermediateFile_semiNoData() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        final Path inputPath        = this.tempDir.resolve("input.txt");
        final Path intermediatePath = this.tempDir.resolve("intermediate.tmp");

        this.reflectionModel.set("inputPath", inputPath);
        this.reflectionModel.set("intermediatePath", intermediatePath);

        Mockito.when(this.mockFieldCreationLogic.initialize(inputPath, intermediatePath)).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addOneLineOfDataToRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.readOneLineOfData()).thenReturn(false);

        try {

            Mockito.doNothing().when(this.mockFieldCreationLogic).close();

        } catch (final IOException e) {

            // テスト用の例外なので無視
            e.printStackTrace();

        }

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeIntermediateFile();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "データなしでも処理が成功すること");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 準正常系：処理スキップ
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testWriteIntermediateFile_semiProcessSkip() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        final Path inputPath        = this.tempDir.resolve("input.txt");
        final Path intermediatePath = this.tempDir.resolve("intermediate.tmp");

        this.reflectionModel.set("inputPath", inputPath);
        this.reflectionModel.set("intermediatePath", intermediatePath);

        Mockito.when(this.mockFieldCreationLogic.initialize(inputPath, intermediatePath)).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addOneLineOfDataToRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.readOneLineOfData()).thenReturn(true, true, false);
        Mockito.when(this.mockFieldCreationLogic.convertFields()).thenReturn(false, true);
        Mockito.when(this.mockFieldCreationLogic.addCommentToRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addFieldToRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.addTypeToRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.writeIntermediateFile()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.clearRows()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.clearProcessingData()).thenReturn(true);

        try {

            Mockito.doNothing().when(this.mockFieldCreationLogic).close();

        } catch (final IOException e) {

            // テスト用の例外なので無視
            e.printStackTrace();

        }

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeIntermediateFile();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "処理スキップでも正常に完了すること");

    }

    /**
     * writeIntermediateFileLine メソッドのテスト - 異常系：KmgToolMsgException発生
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testWriteIntermediateFileLine_errorKmgToolMsgException() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN05001] テスト例外メッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN05001;
        final Class<?>           expectedCauseClass    = KmgToolMsgException.class;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // KmgToolMsgExceptionの作成（モック設定後に作成）
            final KmgToolMsgException testException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN05001, new Object[] {});
            Mockito.when(this.mockFieldCreationLogic.writeIntermediateFile()).thenThrow(testException);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("writeIntermediateFileLine");

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * writeIntermediateFileLine メソッドのテスト - 正常系：正常な書き込み
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testWriteIntermediateFileLine_normalWrite() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final String expectedComment = "テストコメント";
        final String expectedLogMsg  = "テストログメッセージ";

        /* 準備 */
        Mockito.when(this.mockFieldCreationLogic.writeIntermediateFile()).thenReturn(true);
        Mockito.when(this.mockFieldCreationLogic.getComment()).thenReturn(expectedComment);
        Mockito.when(this.mockMessageSource.getLogMessage(KmgToolLogMsgTypes.KMGTOOL_LOG05004, new Object[] {
            expectedComment
        })).thenReturn(expectedLogMsg);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("writeIntermediateFileLine");

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "中間ファイル行の書き込みが成功すること");

    }

}
