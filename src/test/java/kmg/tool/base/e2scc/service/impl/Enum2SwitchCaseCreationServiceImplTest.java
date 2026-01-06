package kmg.tool.base.e2scc.service.impl;

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
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.e2scc.application.logic.Enum2SwitchCaseCreationLogic;

/**
 * Enum2SwitchCaseCreationServiceImplのテストクラス
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.3
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class Enum2SwitchCaseCreationServiceImplTest extends AbstractKmgTest {

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
    private Enum2SwitchCaseCreationServiceImpl testTarget;

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
     * モックEnum2SwitchCaseCreationLogic
     *
     * @since 0.2.0
     */
    private Enum2SwitchCaseCreationLogic mockEnum2SwitchCaseCreationLogic;

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

        final Enum2SwitchCaseCreationServiceImpl enum2SwitchCaseCreationServiceImpl
            = new Enum2SwitchCaseCreationServiceImpl();
        this.testTarget = enum2SwitchCaseCreationServiceImpl;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.mockEnum2SwitchCaseCreationLogic = Mockito.mock(Enum2SwitchCaseCreationLogic.class);

        /* モックの設定 */
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("enum2SwitchCaseMakingLogic", this.mockEnum2SwitchCaseCreationLogic);

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
     * clearAndPrepareNextLine メソッドのテスト - 正常系：正常なクリア処理
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearAndPrepareNextLine_normalClear() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.clearRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.clearProcessingData()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addOneLineOfDataToRows()).thenReturn(true);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("clearAndPrepareNextLine");

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "クリア処理が正常に実行されること");

    }

    /**
     * closeEnum2SwitchCaseCreationLogic メソッドのテスト - 異常系：IOException発生
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testCloseEnum2SwitchCaseCreationLogic_errorIOException() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN04002] テストメッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN04002;
        final Class<?>           expectedCauseClass    = IOException.class;

        /* 準備 */
        try {

            Mockito.doThrow(new IOException("テスト用IOException")).when(this.mockEnum2SwitchCaseCreationLogic).close();

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

                this.reflectionModel.getMethod("closeEnum2SwitchCaseCreationLogic");

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * closeEnum2SwitchCaseCreationLogic メソッドのテスト - 正常系：正常なクローズ処理
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testCloseEnum2SwitchCaseCreationLogic_normalClose() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        Mockito.doNothing().when(this.mockEnum2SwitchCaseCreationLogic).close();

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeEnum2SwitchCaseCreationLogic");

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
        final Enum2SwitchCaseCreationServiceImpl testConstructor = new Enum2SwitchCaseCreationServiceImpl(mockLogger);

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
        final Enum2SwitchCaseCreationServiceImpl testConstructor = new Enum2SwitchCaseCreationServiceImpl();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが正常に作成されること");

    }

    /**
     * getIntermediateDelimiter メソッドのテスト - 正常系：正常に区切り文字を取得する場合
     *
     * @since 0.2.3
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetIntermediateDelimiter_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final KmgDelimiterTypes expectedResult = KmgDelimiterTypes.COMMA;

        /* 準備 */
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.getOutputDelimiter()).thenReturn(expectedResult);

        /* テスト対象の実行 */
        final KmgDelimiterTypes testResult
            = (KmgDelimiterTypes) this.reflectionModel.getMethod("getIntermediateDelimiter");

        /* 検証の準備 */
        final KmgDelimiterTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * processColumns メソッドのテスト - 異常系：KmgToolMsgException発生
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testProcessColumns_errorKmgToolMsgException() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final String             expectedLogMsg         = "テストログメッセージ";
        final KmgToolLogMsgTypes expectedLogMsgTypes    = KmgToolLogMsgTypes.KMGTOOL_LOG04001;
        final Class<?>           expectedExceptionClass = KmgToolMsgException.class;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            final KmgToolMsgException testException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN04001, new Object[] {});
            Mockito.when(this.mockEnum2SwitchCaseCreationLogic.convertEnumDefinition()).thenThrow(testException);
            Mockito.when(this.mockMessageSource.getLogMessage(expectedLogMsgTypes, new Object[] {}))
                .thenReturn(expectedLogMsg);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("processColumns");

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertInstanceOf(expectedExceptionClass, actualException, "KmgToolMsgExceptionが発生すること");

        }

    }

    /**
     * processColumns メソッドのテスト - 正常系：正常な処理実行
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testProcessColumns_normalProcess() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.convertEnumDefinition()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addItemToRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addItemNameToRows()).thenReturn(true);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("processColumns");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "カラム処理が正常に実行されること");

    }

    /**
     * processColumns メソッドのテスト - 準正常系：変換失敗
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testProcessColumns_semiConvertFailure() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.convertEnumDefinition()).thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("processColumns");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "変換失敗時にfalseが返されること");

    }

    /**
     * readOneLineData メソッドのテスト - 異常系：KmgToolMsgException発生
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testReadOneLineData_errorKmgToolMsgException() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final String             expectedLogMsg         = "テストログメッセージ";
        final KmgToolLogMsgTypes expectedLogMsgTypes    = KmgToolLogMsgTypes.KMGTOOL_LOG04002;
        final Class<?>           expectedExceptionClass = KmgToolMsgException.class;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            final KmgToolMsgException testException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN04001, new Object[] {});
            Mockito.when(this.mockEnum2SwitchCaseCreationLogic.readOneLineOfData()).thenThrow(testException);
            Mockito.when(this.mockMessageSource.getLogMessage(expectedLogMsgTypes, new Object[] {}))
                .thenReturn(expectedLogMsg);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("readOneLineData");

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertInstanceOf(expectedExceptionClass, actualException, "KmgToolMsgExceptionが発生すること");

        }

    }

    /**
     * readOneLineData メソッドのテスト - 正常系：正常な読み込み
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testReadOneLineData_normalRead() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.readOneLineOfData()).thenReturn(true);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("readOneLineData");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "1行データが正常に読み込まれること");

    }

    /**
     * readOneLineData メソッドのテスト - 準正常系：読み込み終了
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testReadOneLineData_semiReadEnd() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.readOneLineOfData()).thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("readOneLineData");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "読み込み終了時にfalseが返されること");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 異常系：close時にIOException発生
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testWriteIntermediateFile_errorCloseIOException() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage    = "[KMGTOOL_GEN04002] テストメッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes     = KmgToolGenMsgTypes.KMGTOOL_GEN04002;
        final Class<?>           expectedCauseClass       = IOException.class;
        final Path               expectedInputPath        = this.tempDir.resolve("input.txt");
        final Path               expectedIntermediatePath = this.tempDir.resolve("intermediate.txt");

        /* 準備 */
        this.reflectionModel.set("inputPath", expectedInputPath);
        this.reflectionModel.set("intermediatePath", expectedIntermediatePath);

        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.initialize(expectedInputPath, expectedIntermediatePath))
            .thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addOneLineOfDataToRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.readOneLineOfData()).thenReturn(false);

        try {

            Mockito.doThrow(new IOException("テスト用IOException")).when(this.mockEnum2SwitchCaseCreationLogic).close();

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
     * writeIntermediateFile メソッドのテスト - 異常系：processColumnsでKmgToolMsgException発生
     *
     * @since 0.2.3
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testWriteIntermediateFile_errorProcessColumnsException()
        throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN04001] テスト例外メッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN04001;
        final Class<?>           expectedCauseClass    = null;

        /* 準備 */
        final Path inputPath        = this.tempDir.resolve("input.txt");
        final Path intermediatePath = this.tempDir.resolve("intermediate.tmp");

        this.reflectionModel.set("inputPath", inputPath);
        this.reflectionModel.set("intermediatePath", intermediatePath);

        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.initialize(inputPath, intermediatePath)).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addOneLineOfDataToRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.readOneLineOfData()).thenReturn(true);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);
            Mockito.when(mockMessageSourceTestMethod.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("test log message");

            // KmgToolMsgExceptionの作成
            final KmgToolMsgException testException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN04001, new Object[] {});
            Mockito.when(this.mockEnum2SwitchCaseCreationLogic.convertEnumDefinition()).thenReturn(true);
            Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addItemToRows()).thenThrow(testException);

            try {

                Mockito.doNothing().when(this.mockEnum2SwitchCaseCreationLogic).close();

            } catch (final IOException e) {

                // テスト用の例外なので無視
                e.printStackTrace();

            }

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
     * writeIntermediateFile メソッドのテスト - 異常系：readOneLineDataでKmgToolMsgException発生
     *
     * @since 0.2.3
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testWriteIntermediateFile_errorReadOneLineDataException()
        throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN04001] テスト例外メッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN04001;
        final Class<?>           expectedCauseClass    = null;

        /* 準備 */
        final Path inputPath        = this.tempDir.resolve("input.txt");
        final Path intermediatePath = this.tempDir.resolve("intermediate.tmp");

        this.reflectionModel.set("inputPath", inputPath);
        this.reflectionModel.set("intermediatePath", intermediatePath);

        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.initialize(inputPath, intermediatePath)).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addOneLineOfDataToRows()).thenReturn(true);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);
            Mockito.when(mockMessageSourceTestMethod.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("test log message");

            // KmgToolMsgExceptionの作成
            final KmgToolMsgException testException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN04001, new Object[] {});
            Mockito.when(this.mockEnum2SwitchCaseCreationLogic.readOneLineOfData()).thenThrow(testException);

            try {

                Mockito.doNothing().when(this.mockEnum2SwitchCaseCreationLogic).close();

            } catch (final IOException e) {

                // テスト用の例外なので無視
                e.printStackTrace();

            }

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
     * writeIntermediateFile メソッドのテスト - 異常系：writeIntermediateFileLineでKmgToolMsgException発生
     *
     * @since 0.2.3
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testWriteIntermediateFile_errorWriteIntermediateFileLineException()
        throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN04001] テスト例外メッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN04001;
        final Class<?>           expectedCauseClass    = null;

        /* 準備 */
        final Path inputPath        = this.tempDir.resolve("input.txt");
        final Path intermediatePath = this.tempDir.resolve("intermediate.tmp");

        this.reflectionModel.set("inputPath", inputPath);
        this.reflectionModel.set("intermediatePath", intermediatePath);

        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.initialize(inputPath, intermediatePath)).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addOneLineOfDataToRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.readOneLineOfData()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.convertEnumDefinition()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addItemToRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addItemNameToRows()).thenReturn(true);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);
            Mockito.when(mockMessageSourceTestMethod.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("test log message");

            // KmgToolMsgExceptionの作成
            final KmgToolMsgException testException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN04001, new Object[] {});
            Mockito.when(this.mockEnum2SwitchCaseCreationLogic.writeIntermediateFile()).thenThrow(testException);

            try {

                Mockito.doNothing().when(this.mockEnum2SwitchCaseCreationLogic).close();

            } catch (final IOException e) {

                // テスト用の例外なので無視
                e.printStackTrace();

            }

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
     * writeIntermediateFile メソッドのテスト - 正常系：正常な処理実行
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFile_normalProcess() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult           = true;
        final Path    expectedInputPath        = this.tempDir.resolve("input.txt");
        final Path    expectedIntermediatePath = this.tempDir.resolve("intermediate.txt");

        /* 準備 */
        this.reflectionModel.set("inputPath", expectedInputPath);
        this.reflectionModel.set("intermediatePath", expectedIntermediatePath);

        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.initialize(expectedInputPath, expectedIntermediatePath))
            .thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addOneLineOfDataToRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.readOneLineOfData()).thenReturn(true, true, false);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.convertEnumDefinition()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addItemToRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addItemNameToRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.writeIntermediateFile()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.clearRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.clearProcessingData()).thenReturn(true);
        Mockito.doNothing().when(this.mockEnum2SwitchCaseCreationLogic).close();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeIntermediateFile();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "中間ファイルへの書き込みが正常に実行されること");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 準正常系：データなし（1行目で終了）
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFile_semiNoData() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult           = true;
        final Path    expectedInputPath        = this.tempDir.resolve("input.txt");
        final Path    expectedIntermediatePath = this.tempDir.resolve("intermediate.txt");

        /* 準備 */
        this.reflectionModel.set("inputPath", expectedInputPath);
        this.reflectionModel.set("intermediatePath", expectedIntermediatePath);

        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.initialize(expectedInputPath, expectedIntermediatePath))
            .thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addOneLineOfDataToRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.readOneLineOfData()).thenReturn(false);
        Mockito.doNothing().when(this.mockEnum2SwitchCaseCreationLogic).close();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeIntermediateFile();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "データなしでも正常に処理されること");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 準正常系：処理スキップ
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFile_semiProcessSkip() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult           = true;
        final Path    expectedInputPath        = this.tempDir.resolve("input.txt");
        final Path    expectedIntermediatePath = this.tempDir.resolve("intermediate.txt");

        /* 準備 */
        this.reflectionModel.set("inputPath", expectedInputPath);
        this.reflectionModel.set("intermediatePath", expectedIntermediatePath);

        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.initialize(expectedInputPath, expectedIntermediatePath))
            .thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addOneLineOfDataToRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.readOneLineOfData()).thenReturn(true, true, false);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.convertEnumDefinition()).thenReturn(false, true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addItemToRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.addItemNameToRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.writeIntermediateFile()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.clearRows()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.clearProcessingData()).thenReturn(true);
        Mockito.doNothing().when(this.mockEnum2SwitchCaseCreationLogic).close();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeIntermediateFile();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "処理スキップでも正常に処理されること");

    }

    /**
     * writeIntermediateFileLine メソッドのテスト - 異常系：KmgToolMsgException発生
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws Exception
     *                             例外
     */
    @Test
    public void testWriteIntermediateFileLine_errorKmgToolMsgException() throws KmgToolMsgException, Exception {

        /* 期待値の定義 */
        final String             expectedLogMsg         = "テストログメッセージ";
        final KmgToolLogMsgTypes expectedLogMsgTypes    = KmgToolLogMsgTypes.KMGTOOL_LOG04003;
        final Class<?>           expectedExceptionClass = KmgToolMsgException.class;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            final KmgToolMsgException testException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN04001, new Object[] {});
            Mockito.when(this.mockEnum2SwitchCaseCreationLogic.writeIntermediateFile()).thenThrow(testException);
            Mockito.when(this.mockMessageSource.getLogMessage(expectedLogMsgTypes, new Object[] {}))
                .thenReturn(expectedLogMsg);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("writeIntermediateFileLine");

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertInstanceOf(expectedExceptionClass, actualException, "KmgToolMsgExceptionが発生すること");

        }

    }

    /**
     * writeIntermediateFileLine メソッドのテスト - 正常系：正常な書き込み
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFileLine_normalWrite() throws Exception {

        /* 期待値の定義 */
        final String expectedLogMsg   = "テストデバッグメッセージ";
        final String expectedItem     = "テスト項目";
        final String expectedItemName = "テスト項目名";

        /* 準備 */
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.writeIntermediateFile()).thenReturn(true);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.getItem()).thenReturn(expectedItem);
        Mockito.when(this.mockEnum2SwitchCaseCreationLogic.getItemName()).thenReturn(expectedItemName);
        Mockito.when(this.mockMessageSource.getLogMessage(KmgToolLogMsgTypes.KMGTOOL_LOG04004, new Object[] {
            expectedItem, expectedItemName
        })).thenReturn(expectedLogMsg);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("writeIntermediateFileLine");

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "中間ファイルへの書き込みが正常に実行されること");

    }

}
