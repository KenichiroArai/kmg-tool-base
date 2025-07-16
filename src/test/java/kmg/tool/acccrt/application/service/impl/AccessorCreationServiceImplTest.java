package kmg.tool.acccrt.application.service.impl;

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

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.acccrt.application.logic.AccessorCreationLogic;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;

/**
 * アクセサ作成サービス実装テスト
 *
 * @author KenichiroArai
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class AccessorCreationServiceImplTest extends AbstractKmgTest {

    /** テンポラリディレクトリ */
    @TempDir
    private Path tempDir;

    /** テスト対象 */
    private AccessorCreationServiceImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** モックKMGメッセージソース */
    private KmgMessageSource mockMessageSource;

    /** モックアクセサ作成ロジック */
    private AccessorCreationLogic mockAccessorCreationLogic;

    /**
     * セットアップ
     *
     * @throws Exception
     *                   例外
     */
    @BeforeEach
    public void setUp() throws Exception {

        this.testTarget = new AccessorCreationServiceImpl();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.mockAccessorCreationLogic = Mockito.mock(AccessorCreationLogic.class);

        /* モックをテスト対象に設定 */
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("accessorCreationLogic", this.mockAccessorCreationLogic);

        // 初期化処理のモック設定
        Mockito.when(this.mockAccessorCreationLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockAccessorCreationLogic.addOneLineOfDataToRows()).thenReturn(true);

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

            try {

                // 必要に応じてクリーンアップ処理を追加

            } catch (final Exception e) {

                e.printStackTrace();

            }

        }

    }

    /**
     * addNameColumn メソッドのテスト - 正常系：Javadoc変換が成功する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddNameColumn_normalJavadocConvertSuccess() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        Mockito.when(this.mockAccessorCreationLogic.convertJavadoc()).thenReturn(true);
        Mockito.doNothing().when(this.mockAccessorCreationLogic).addJavadocCommentToRows();

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("addNameColumn");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * addNameColumn メソッドのテスト - 準正常系：Javadoc変換が失敗する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddNameColumn_semiJavadocConvertFailure() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        Mockito.when(this.mockAccessorCreationLogic.convertJavadoc()).thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("addNameColumn");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * addRemainingColumns メソッドのテスト - 正常系：フィールド変換が成功する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddRemainingColumns_normalFieldConvertSuccess() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        Mockito.when(this.mockAccessorCreationLogic.removeModifier()).thenReturn(true);
        Mockito.when(this.mockAccessorCreationLogic.convertFields()).thenReturn(true);
        Mockito.when(this.mockAccessorCreationLogic.addTypeToRows()).thenReturn(true);
        Mockito.when(this.mockAccessorCreationLogic.addItemToRows()).thenReturn(true);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("addRemainingColumns");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * addRemainingColumns メソッドのテスト - 準正常系：フィールド変換が失敗する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddRemainingColumns_semiFieldConvertFailure() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        Mockito.when(this.mockAccessorCreationLogic.removeModifier()).thenReturn(true);
        Mockito.when(this.mockAccessorCreationLogic.convertFields()).thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("addRemainingColumns");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * clearAndPrepareNextLine メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearAndPrepareNextLine_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        final String             expectedLogMessage  = "クリア処理中にエラーが発生しました。";
        final KmgToolLogMsgTypes expectedLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG01002;

        /* 準備 */
        Mockito.doNothing().when(this.mockAccessorCreationLogic).clearRows();
        Mockito.doThrow(new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN01001))
            .when(this.mockAccessorCreationLogic).clearProcessingData();

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedLogMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class,
                () -> this.reflectionModel.getMethod("clearAndPrepareNextLine"));

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(actualException, "例外が発生すること");

        }

    }

    /**
     * clearAndPrepareNextLine メソッドのテスト - 正常系：正常にクリア処理が完了する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearAndPrepareNextLine_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.doNothing().when(this.mockAccessorCreationLogic).clearRows();
        Mockito.doNothing().when(this.mockAccessorCreationLogic).clearProcessingData();
        Mockito.doNothing().when(this.mockAccessorCreationLogic).addOneLineOfDataToRows();

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("clearAndPrepareNextLine");

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("clearAndPrepareNextLine"),
            "clearAndPrepareNextLineメソッドが正常に実行されること");

    }

    /**
     * closeAccessorCreationLogic メソッドのテスト - 異常系：IOExceptionが発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseAccessorCreationLogic_errorIOException() throws Exception {

        /* 期待値の定義 */
        final Class<?>           expectedCauseClass    = IOException.class;
        final String             expectedDomainMessage = "[KMGTOOL_GEN01000] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN01000;

        /* 準備 */
        Mockito.doThrow(new IOException("テスト例外")).when(this.mockAccessorCreationLogic).close();

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class,
                () -> this.reflectionModel.getMethod("closeAccessorCreationLogic"));

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * closeAccessorCreationLogic メソッドのテスト - 正常系：正常にクローズ処理が完了する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseAccessorCreationLogic_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.doNothing().when(this.mockAccessorCreationLogic).close();

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeAccessorCreationLogic");

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("closeAccessorCreationLogic"),
            "closeAccessorCreationLogicメソッドが正常に実行されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：カスタムロガーを使用するコンストラクタ
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCustomLogger() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final org.slf4j.Logger customLogger = org.slf4j.LoggerFactory.getLogger("TestLogger");

        /* テスト対象の実行 */
        final AccessorCreationServiceImpl testConstructor = new AccessorCreationServiceImpl(customLogger);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが作成されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：デフォルトコンストラクタ
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefault() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final AccessorCreationServiceImpl testConstructor = new AccessorCreationServiceImpl();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが作成されること");

    }

    /**
     * processColumns メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcessColumns_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        final String             expectedLogMessage  = "カラムの追加中にエラーが発生しました。";
        final KmgToolLogMsgTypes expectedLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG01003;

        /* 準備 */
        Mockito.when(this.mockAccessorCreationLogic.convertJavadoc())
            .thenThrow(new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN01001));

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedLogMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class,
                () -> this.reflectionModel.getMethod("processColumns"));

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(actualException, "例外が発生すること");

        }

    }

    /**
     * processColumns メソッドのテスト - 正常系：正常に処理が完了する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcessColumns_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        Mockito.when(this.mockAccessorCreationLogic.convertJavadoc()).thenReturn(true);
        Mockito.doNothing().when(this.mockAccessorCreationLogic).addJavadocCommentToRows();
        Mockito.when(this.mockAccessorCreationLogic.getJavadocComment()).thenReturn("テストコメント");
        Mockito.when(this.mockAccessorCreationLogic.removeModifier()).thenReturn(true);
        Mockito.when(this.mockAccessorCreationLogic.convertFields()).thenReturn(true);
        Mockito.doNothing().when(this.mockAccessorCreationLogic).addTypeToRows();
        Mockito.doNothing().when(this.mockAccessorCreationLogic).addItemToRows();

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("processColumns");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * processColumns メソッドのテスト - 準正常系：残りのカラム追加が失敗する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcessColumns_semiAddRemainingColumnsFailure() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        Mockito.when(this.mockAccessorCreationLogic.convertJavadoc()).thenReturn(true);
        Mockito.doNothing().when(this.mockAccessorCreationLogic).addJavadocCommentToRows();
        Mockito.when(this.mockAccessorCreationLogic.getJavadocComment()).thenReturn("テストコメント");
        Mockito.when(this.mockAccessorCreationLogic.removeModifier()).thenReturn(true);
        Mockito.when(this.mockAccessorCreationLogic.convertFields()).thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("processColumns");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * processColumns メソッドのテスト - 準正常系：Javadocコメントがnullの場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcessColumns_semiJavadocCommentNull() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        Mockito.when(this.mockAccessorCreationLogic.convertJavadoc()).thenReturn(true);
        Mockito.doNothing().when(this.mockAccessorCreationLogic).addJavadocCommentToRows();
        Mockito.when(this.mockAccessorCreationLogic.getJavadocComment()).thenReturn(null);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("processColumns");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * readOneLineData メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReadOneLineData_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        final String             expectedLogMessage  = "1行データの読み込み中にエラーが発生しました。";
        final KmgToolLogMsgTypes expectedLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG01004;

        /* 準備 */
        Mockito.when(this.mockAccessorCreationLogic.readOneLineOfData())
            .thenThrow(new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN01001));

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedLogMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class,
                () -> this.reflectionModel.getMethod("readOneLineData"));

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(actualException, "例外が発生すること");

        }

    }

    /**
     * readOneLineData メソッドのテスト - 正常系：正常に読み込みが完了する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReadOneLineData_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        Mockito.when(this.mockAccessorCreationLogic.readOneLineOfData()).thenReturn(true);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("readOneLineData");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 異常系：アクセサ作成ロジックのクローズでIOExceptionが発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFile_errorCloseIOException() throws Exception {

        /* 期待値の定義 */
        final Class<?>           expectedCauseClass    = IOException.class;
        final String             expectedDomainMessage = "[KMGTOOL_GEN01000] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN01000;

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "test content".getBytes());

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // モックアクセサ作成ロジックの設定（クローズ時にIOExceptionを投げる）
            Mockito.doThrow(new IOException("テスト例外")).when(this.mockAccessorCreationLogic).close();

            /* テスト対象の実行 */
            final KmgToolMsgException actualException
                = Assertions.assertThrows(KmgToolMsgException.class, () -> this.testTarget.writeIntermediateFile());

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * writeIntermediateFile メソッドのテスト - 正常系：正常に処理が完了する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFile_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "/** テストコメント */\nprivate String testField;".getBytes());

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            // モックアクセサ作成ロジックの設定
            Mockito.when(this.mockAccessorCreationLogic.readOneLineOfData()).thenReturn(true, false);
            Mockito.when(this.mockAccessorCreationLogic.convertJavadoc()).thenReturn(true);
            Mockito.when(this.mockAccessorCreationLogic.getJavadocComment()).thenReturn("テストコメント");
            Mockito.when(this.mockAccessorCreationLogic.removeModifier()).thenReturn(true);
            Mockito.when(this.mockAccessorCreationLogic.convertFields()).thenReturn(true);
            Mockito.when(this.mockAccessorCreationLogic.getItem()).thenReturn("testField");

            /* テスト対象の実行 */
            final boolean testResult = this.testTarget.writeIntermediateFile();

            /* 検証の準備 */
            final boolean actualResult = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

        }

    }

    /**
     * writeIntermediateFileLine メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFileLine_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        final String             expectedLogMessage  = "中間ファイルに書き込み中にエラーが発生しました。";
        final KmgToolLogMsgTypes expectedLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG01000;

        /* 準備 */
        Mockito.doThrow(new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN01001))
            .when(this.mockAccessorCreationLogic).writeIntermediateFile();

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedLogMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class,
                () -> this.reflectionModel.getMethod("writeIntermediateFileLine"));

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(actualException, "例外が発生すること");

        }

    }

    /**
     * writeIntermediateFileLine メソッドのテスト - 正常系：正常に書き込みが完了する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFileLine_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.doNothing().when(this.mockAccessorCreationLogic).writeIntermediateFile();
        Mockito.when(this.mockAccessorCreationLogic.getJavadocComment()).thenReturn("テストコメント");
        Mockito.when(this.mockAccessorCreationLogic.getItem()).thenReturn("testField");

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            /* テスト対象の実行 */
            this.reflectionModel.getMethod("writeIntermediateFileLine");

            /* 検証の準備 */

            /* 検証の実施 */
            // 例外が発生しないことを確認
            Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("writeIntermediateFileLine"),
                "writeIntermediateFileLineメソッドが正常に実行されること");

        }

    }

}
