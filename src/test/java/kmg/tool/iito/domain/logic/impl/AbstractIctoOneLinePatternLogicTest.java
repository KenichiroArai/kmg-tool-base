package kmg.tool.iito.domain.logic.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.iito.domain.logic.AbstractIctoOneLinePatternLogic;

/**
 * AbstractIctoOneLinePatternLogicのテストクラス
 *
 * @author KenichiroArai
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class AbstractIctoOneLinePatternLogicTest extends AbstractKmgTest {

    /**
     * テスト用のAbstractIctoOneLinePatternLogic実装クラス
     */
    private static class TestAbstractIctoOneLinePatternLogic extends AbstractIctoOneLinePatternLogic {

        /**
         * デフォルトコンストラクタ
         */
        public TestAbstractIctoOneLinePatternLogic() {

        }

        /**
         * カスタムロガーを使用するコンストラクタ
         *
         * @param logger
         *               ロガー
         */
        public TestAbstractIctoOneLinePatternLogic(final Logger logger) {

            super(logger);

        }

        /**
         * replaceInLineメソッドをpublicでオーバーライド
         *
         * @param target
         *                    対象
         * @param replacement
         *                    置換値
         *
         * @return true：変更あり、false：変更なし
         */
        @Override
        public boolean replaceInLine(final String target, final String replacement) {

            final boolean result = super.replaceInLine(target, replacement);
            return result;

        }

    }

    /** テンポラリディレクトリ */
    @TempDir
    private Path tempDir;

    /** テスト対象 */
    private TestAbstractIctoOneLinePatternLogic testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** モックKMGメッセージソース */
    private KmgMessageSource mockMessageSource;

    /**
     * セットアップ
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        final TestAbstractIctoOneLinePatternLogic abstractIctoOneLinePatternLogic
            = new TestAbstractIctoOneLinePatternLogic();
        this.testTarget = abstractIctoOneLinePatternLogic;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

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

                this.testTarget.close();

            } catch (final IOException e) {

                e.printStackTrace();

            }

        }

    }

    /**
     * addOneLineOfDataToRows メソッドのテスト - 正常系：行を追加する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddOneLineOfDataToRows_normalAddRow() throws Exception {

        /* 期待値の定義 */
        final int expectedSize = 1;

        /* 準備 */
        this.reflectionModel.set("rows", new ArrayList<>());

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addOneLineOfDataToRows();

        /* 検証の準備 */
        final boolean            actualResult = testResult;
        @SuppressWarnings("unchecked")
        final List<List<String>> actualRows   = (List<List<String>>) this.reflectionModel.get("rows");
        final int                actualSize   = actualRows.size();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedSize, actualSize, "行が追加されていること");

    }

    /**
     * addRow メソッドのテスト - 異常系：行リストが空の場合（protectedメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddRow_errorEmptyRows() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN07005] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN07005;
        final Class<?>           expectedCauseClass    = NoSuchElementException.class;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            this.reflectionModel.set("rows", new ArrayList<>());

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("addRow", "test data");

            }, "行リストが空の場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * addRow メソッドのテスト - 正常系：行にデータを追加する場合（protectedメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddRow_normalAddData() throws Exception {

        /* 期待値の定義 */
        final String expectedData = "test data";

        /* 準備 */
        this.testTarget.addOneLineOfDataToRows();

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("addRow", expectedData);

        /* 検証の準備 */
        final boolean            actualResult = testResult;
        @SuppressWarnings("unchecked")
        final List<List<String>> rows         = (List<List<String>>) this.reflectionModel.get("rows");
        final String             actualData   = rows.get(0).get(0);

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedData, actualData, "データが正しく追加されていること");

    }

    /**
     * clearProcessingData メソッドのテスト - 正常系：処理中のデータをクリアする場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearProcessingData_normalClear() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("lineOfDataRead", "test data");
        this.reflectionModel.set("convertedLine", "converted data");
        this.reflectionModel.set("nowLineNumber", 5);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.clearProcessingData();

        /* 検証の準備 */
        final boolean actualResult         = testResult;
        final String  actualLineOfDataRead = (String) this.reflectionModel.get("lineOfDataRead");
        final String  actualConvertedLine  = (String) this.reflectionModel.get("convertedLine");
        final int     actualNowLineNumber  = (Integer) this.reflectionModel.get("nowLineNumber");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertNull(actualLineOfDataRead, "lineOfDataReadがnullであること");
        Assertions.assertNull(actualConvertedLine, "convertedLineがnullであること");
        Assertions.assertEquals(0, actualNowLineNumber, "nowLineNumberが0であること");

    }

    /**
     * clearRows メソッドのテスト - 正常系：行リストをクリアする場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearRows_normalClear() throws Exception {

        /* 期待値の定義 */
        final int expectedSize = 0;

        /* 準備 */
        final List<List<String>> rows = new ArrayList<>();
        rows.add(new ArrayList<>());
        rows.add(new ArrayList<>());
        this.reflectionModel.set("rows", rows);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.clearRows();

        /* 検証の準備 */
        final boolean            actualResult = testResult;
        @SuppressWarnings("unchecked")
        final List<List<String>> actualRows   = (List<List<String>>) this.reflectionModel.get("rows");
        final int                actualSize   = actualRows.size();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedSize, actualSize, "行リストがクリアされていること");

    }

    /**
     * close メソッドのテスト - 正常系：リソースをクローズする場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClose_normalCloseResources() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.txt");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);

        /* テスト対象の実行 */
        this.testTarget.close();

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.testTarget.close(), "closeメソッドが正常に実行されること");

    }

    /**
     * closeReader メソッドのテスト - 異常系：IOExceptionが発生した場合（privateメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseReader_errorIOException() throws Exception {

        /* 期待値の定義 */
        final Path               testInputFile         = this.tempDir.resolve("test_input.txt");
        final String             expectedDomainMessage = "[KMGTOOL_LOG07000] ";
        final Class<?>           expectedCauseClass    = IOException.class;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            this.reflectionModel.set("inputPath", testInputFile);
            this.reflectionModel.set("messageSource", this.mockMessageSource);

            // IOExceptionを発生させるモックリーダーを作成
            final BufferedReader mockReader = Mockito.mock(BufferedReader.class);
            Mockito.doThrow(new IOException("Test IOException")).when(mockReader).close();
            this.reflectionModel.set("reader", mockReader);

            /* テスト対象の実行 */
            final IOException actualException = Assertions.assertThrows(IOException.class, () -> {

                this.reflectionModel.getMethod("closeReader");

            }, "IOExceptionが発生した場合は例外が発生すること");

            /* 検証の実施 */
            Assertions.assertInstanceOf(expectedCauseClass, actualException, "例外の種類が正しいこと");

        }

    }

    /**
     * closeReader メソッドのテスト - 異常系：IOException発生時にログメッセージが生成される場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseReader_errorIOExceptionWithLogMessage() throws Exception {

        /* 期待値の定義 */
        final String expectedLogMessage = "[KMGTOOL_LOG07000] ";

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedLogMessage);

            /* 準備 */
            final Path testInputFile = this.tempDir.resolve("test_input.txt");
            Files.write(testInputFile, "test content".getBytes());
            this.reflectionModel.set("inputPath", testInputFile);
            this.reflectionModel.set("messageSource", this.mockMessageSource);
            this.reflectionModel.set("reader", Mockito.mock(BufferedReader.class));

            // BufferedReaderのcloseメソッドでIOExceptionを発生させる
            final BufferedReader mockReader = (BufferedReader) this.reflectionModel.get("reader");
            Mockito.doThrow(new IOException("Test exception")).when(mockReader).close();

            /* テスト対象の実行 */
            final IOException actualException = Assertions.assertThrows(IOException.class, () -> {

                this.reflectionModel.getMethod("closeReader");

            }, "IOExceptionが発生した場合は例外が発生すること");

            /* 検証の実施 */
            Assertions.assertNotNull(actualException, "例外が発生すること");
            Assertions.assertNull(this.reflectionModel.get("reader"), "readerがnullに設定されること");

        }

    }

    /**
     * closeReader メソッドのテスト - 正常系：リーダーをクローズする場合（privateメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseReader_normalCloseReader() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.txt");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeReader");

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("closeReader"), "リーダーが正常にクローズされること");

    }

    /**
     * closeReader メソッドのテスト - 正常系：リーダーがnullの場合（privateメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseReader_normalReaderNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("reader", null);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeReader");

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("closeReader"), "リーダーがnullの場合は正常に処理されること");

    }

    /**
     * closeWriter メソッドのテスト - 異常系：IOExceptionが発生した場合（privateメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseWriter_errorIOException() throws Exception {

        /* 期待値の定義 */
        final Path               testOutputFile        = this.tempDir.resolve("test_output.txt");
        final String             expectedDomainMessage = "[KMGTOOL_LOG07001] ";
        final Class<?>           expectedCauseClass    = IOException.class;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            this.reflectionModel.set("outputPath", testOutputFile);
            this.reflectionModel.set("messageSource", this.mockMessageSource);

            // IOExceptionを発生させるモックライターを作成
            final BufferedWriter mockWriter = Mockito.mock(BufferedWriter.class);
            Mockito.doThrow(new IOException("Test IOException")).when(mockWriter).close();
            this.reflectionModel.set("writer", mockWriter);

            /* テスト対象の実行 */
            final IOException actualException = Assertions.assertThrows(IOException.class, () -> {

                this.reflectionModel.getMethod("closeWriter");

            }, "IOExceptionが発生した場合は例外が発生すること");

            /* 検証の実施 */
            Assertions.assertInstanceOf(expectedCauseClass, actualException, "例外の種類が正しいこと");

        }

    }

    /**
     * closeWriter メソッドのテスト - 異常系：IOException発生時にログメッセージが生成される場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseWriter_errorIOExceptionWithLogMessage() throws Exception {

        /* 期待値の定義 */
        final String expectedLogMessage = "[KMGTOOL_LOG07001] ";

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedLogMessage);

            /* 準備 */
            final Path testOutputFile = this.tempDir.resolve("test_output.txt");
            this.reflectionModel.set("outputPath", testOutputFile);
            this.reflectionModel.set("messageSource", this.mockMessageSource);
            this.reflectionModel.set("writer", Mockito.mock(BufferedWriter.class));

            // BufferedWriterのcloseメソッドでIOExceptionを発生させる
            final BufferedWriter mockWriter = (BufferedWriter) this.reflectionModel.get("writer");
            Mockito.doThrow(new IOException("Test exception")).when(mockWriter).close();

            /* テスト対象の実行 */
            final IOException actualException = Assertions.assertThrows(IOException.class, () -> {

                this.reflectionModel.getMethod("closeWriter");

            }, "IOExceptionが発生した場合は例外が発生すること");

            /* 検証の実施 */
            Assertions.assertNotNull(actualException, "例外が発生すること");
            Assertions.assertNull(this.reflectionModel.get("writer"), "writerがnullに設定されること");

        }

    }

    /**
     * closeWriter メソッドのテスト - 正常系：ライターをクローズする場合（privateメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseWriter_normalCloseWriter() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.txt");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeWriter");

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("closeWriter"), "ライターが正常にクローズされること");

    }

    /**
     * closeWriter メソッドのテスト - 正常系：ライターがnullの場合（privateメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseWriter_normalWriterNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("writer", null);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeWriter");

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("closeWriter"), "ライターがnullの場合は正常に処理されること");

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
        final TestAbstractIctoOneLinePatternLogic testInstance = new TestAbstractIctoOneLinePatternLogic(customLogger);

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
        final TestAbstractIctoOneLinePatternLogic testInstance = new TestAbstractIctoOneLinePatternLogic();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testInstance, "インスタンスが作成されること");

    }

    /**
     * コンストラクタのテスト - 正常系：デフォルトコンストラクタでoutputDelimiterが正しく初期化される場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefaultConstructorWithOutputDelimiter() throws Exception {

        /* 期待値の定義 */
        final KmgDelimiterTypes expectedOutputDelimiter = KmgDelimiterTypes.COMMA;

        /* 準備 */

        /* テスト対象の実行 */
        final TestAbstractIctoOneLinePatternLogic testInstance = new TestAbstractIctoOneLinePatternLogic();

        /* 検証の準備 */
        final KmgReflectionModelImpl reflectionModel       = new KmgReflectionModelImpl(testInstance);
        final KmgDelimiterTypes      actualOutputDelimiter = (KmgDelimiterTypes) reflectionModel.get("outputDelimiter");

        /* 検証の実施 */
        Assertions.assertEquals(expectedOutputDelimiter, actualOutputDelimiter, "outputDelimiterが正しく初期化されること");

    }

    /**
     * getConvertedLine メソッドのテスト - 正常系：変換後の1行データを取得する場合
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetConvertedLine_normalGet() throws KmgReflectionException {

        /* 期待値の定義 */
        final String expected = "converted line data";

        /* 準備 */
        this.reflectionModel.set("convertedLine", expected);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getConvertedLine();

        /* 検証の準備 */
        final String actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "変換後の1行データが正しいこと");

    }

    /**
     * getLineOfDataRead メソッドのテスト - 正常系：読み込んだ1行データを取得する場合
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetLineOfDataRead_normalGet() throws KmgReflectionException {

        /* 期待値の定義 */
        final String expected = "read line data";

        /* 準備 */
        this.reflectionModel.set("lineOfDataRead", expected);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getLineOfDataRead();

        /* 検証の準備 */
        final String actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "読み込んだ1行データが正しいこと");

    }

    /**
     * getNowLineNumber メソッドのテスト - 正常系：現在の行番号を取得する場合
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetNowLineNumber_normalGet() throws KmgReflectionException {

        /* 期待値の定義 */
        final int expected = 10;

        /* 準備 */
        this.reflectionModel.set("nowLineNumber", expected);

        /* テスト対象の実行 */
        final int testResult = this.testTarget.getNowLineNumber();

        /* 検証の準備 */
        final int actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "現在の行番号が正しいこと");

    }

    /**
     * getRows メソッドのテスト - 正常系：書き込み対象の行データのリストを取得する場合
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetRows_normalGet() throws KmgReflectionException {

        /* 期待値の定義 */
        final List<List<String>> expected = new ArrayList<>();
        expected.add(new ArrayList<>());
        expected.add(new ArrayList<>());

        /* 準備 */
        this.reflectionModel.set("rows", expected);

        /* テスト対象の実行 */
        final List<List<String>> testResult = this.testTarget.getRows();

        /* 検証の準備 */
        final List<List<String>> actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "書き込み対象の行データのリストが正しいこと");

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
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.txt");
        Files.write(testInputFile, "test content".getBytes());

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(testInputFile, testOutputFile);

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が成功すること");

    }

    /**
     * initialize メソッドのテスト - 異常系：nullパラメータで初期化する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalWithNullParameters() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final NullPointerException actualException = Assertions.assertThrows(NullPointerException.class, () -> {

            this.testTarget.initialize(null, null);

        }, "nullパラメータの場合は例外が発生すること");

        /* 検証の準備 */
        final Path actualInputPath  = (Path) this.reflectionModel.get("inputPath");
        final Path actualOutputPath = (Path) this.reflectionModel.get("outputPath");

        /* 検証の実施 */
        Assertions.assertNotNull(actualException, "例外が発生すること");
        Assertions.assertNull(actualInputPath, "inputPathがnullに設定されること");
        Assertions.assertNull(actualOutputPath, "outputPathがnullに設定されること");

    }

    /**
     * openInputFile メソッドのテスト - 異常系：入力ファイルが存在しない場合（privateメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOpenInputFile_errorFileNotFound() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN07003] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN07003;
        final Class<?>           expectedCauseClass    = NoSuchFileException.class;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            final Path testInputFile = this.tempDir.resolve("nonexistent.txt");
            this.reflectionModel.set("inputPath", testInputFile);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("openInputFile");

            }, "入力ファイルが存在しない場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * openInputFile メソッドのテスト - 正常系：入力ファイルを開く場合（privateメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOpenInputFile_normalOpen() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile = this.tempDir.resolve("test_input.txt");
        Files.write(testInputFile, "test content".getBytes());
        this.reflectionModel.set("inputPath", testInputFile);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("openInputFile");

        /* 検証の準備 */
        final Object actualReader = this.reflectionModel.get("reader");

        /* 検証の実施 */
        Assertions.assertNotNull(actualReader, "リーダーが作成されていること");

    }

    /**
     * openOutputFile メソッドのテスト - 異常系：出力ファイルが作成できない場合（privateメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOpenOutputFile_errorCannotCreate() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN07004] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN07004;
        final Class<?>           expectedCauseClass    = NoSuchFileException.class;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            // 権限のないディレクトリを指定（Windowsの場合）
            final Path testOutputFile = Path.of("/invalid/path/test_output.txt");
            this.reflectionModel.set("outputPath", testOutputFile);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("openOutputFile");

            }, "出力ファイルが作成できない場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * openOutputFile メソッドのテスト - 正常系：出力ファイルを開く場合（privateメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOpenOutputFile_normalOpen() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testOutputFile = this.tempDir.resolve("test_output.txt");
        this.reflectionModel.set("outputPath", testOutputFile);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("openOutputFile");

        /* 検証の準備 */
        final Object actualWriter = this.reflectionModel.get("writer");

        /* 検証の実施 */
        Assertions.assertNotNull(actualWriter, "ライターが作成されていること");

    }

    /**
     * readOneLineOfData メソッドのテスト - 異常系：IOExceptionが発生した場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReadOneLineOfData_errorIOException() throws Exception {

        /* 期待値の定義 */
        final Path               testInputFile         = this.tempDir.resolve("test_input.txt");
        final Path               testOutputFile        = this.tempDir.resolve("test_output.txt");
        final String             expectedDomainMessage = "[KMGTOOL_GEN07000] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN07000;
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
            Files.write(testInputFile, "test content".getBytes());
            this.testTarget.initialize(testInputFile, testOutputFile);

            // IOExceptionを発生させるモックリーダーを作成
            try (final BufferedReader mockReader = Mockito.mock(BufferedReader.class);) {

                Mockito.when(mockReader.readLine()).thenThrow(new IOException("Test IOException"));
                this.reflectionModel.set("reader", mockReader);

            }

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.readOneLineOfData();

            }, "IOExceptionが発生した場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * readOneLineOfData メソッドのテスト - 正常系：データが読み込める場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReadOneLineOfData_normalReadData() throws Exception {

        /* 期待値の定義 */
        final String expectedLineOfDataRead = "test line 1";

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.txt");
        Files.write(testInputFile, "test line 1\ntest line 2".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.readOneLineOfData();

        /* 検証の準備 */
        final boolean actualResult         = testResult;
        final String  actualLineOfDataRead = (String) this.reflectionModel.get("lineOfDataRead");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "データが読み込めること");
        Assertions.assertEquals(expectedLineOfDataRead, actualLineOfDataRead, "読み込んだデータが正しいこと");

    }

    /**
     * readOneLineOfData メソッドのテスト - 正常系：空行を読み込む場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReadOneLineOfData_normalReadEmptyLine() throws Exception {

        /* 期待値の定義 */
        final String expectedLineOfDataRead = null;
        final String expectedConvertedLine  = null;

        /* 準備 */
        final Path testInputFile = this.tempDir.resolve("test_input.txt");
        Files.write(testInputFile, "".getBytes());
        this.reflectionModel.set("inputPath", testInputFile);
        this.reflectionModel.set("reader", Files.newBufferedReader(testInputFile));

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.readOneLineOfData();

        /* 検証の準備 */
        final boolean actualResult         = testResult;
        final String  actualLineOfDataRead = (String) this.reflectionModel.get("lineOfDataRead");
        final String  actualConvertedLine  = (String) this.reflectionModel.get("convertedLine");

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "空行の場合はfalseが返されること");
        Assertions.assertEquals(expectedLineOfDataRead, actualLineOfDataRead, "空行が正しく読み込まれること");
        Assertions.assertEquals(expectedConvertedLine, actualConvertedLine, "convertedLineが正しく設定されること");

    }

    /**
     * readOneLineOfData メソッドのテスト - 準正常系：ファイル終端に達した場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReadOneLineOfData_semiEndOfFile() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.txt");
        Files.write(testInputFile, "".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.readOneLineOfData();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "ファイル終端で正しく判定されること");

    }

    /**
     * replaceInLine メソッドのテスト - 異常系：convertedLineがnullの場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_errorConvertedLineNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("convertedLine", null);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.replaceInLine("target", "replacement");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "convertedLineがnullの場合はfalseが返されること");

    }

    /**
     * replaceInLine メソッドのテスト - 正常系：置換が発生しない場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_normalNoReplaceOccurs() throws Exception {

        /* 期待値の定義 */
        final String expectedConvertedLine = "original text";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "original text");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.replaceInLine("nonexistent", "new");

        /* 検証の準備 */
        final boolean actualResult        = testResult;
        final String  actualConvertedLine = (String) this.reflectionModel.get("convertedLine");

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "置換が発生しない場合はfalseが返されること");
        Assertions.assertEquals(expectedConvertedLine, actualConvertedLine, "convertedLineが変更されないこと");

    }

    /**
     * replaceInLine メソッドのテスト - 正常系：置換が行われる場合（protectedメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_normalReplace() throws Exception {

        /* 期待値の定義 */
        final String expectedLine = "Hello World";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "Hello Test");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("replaceInLine", "Test", "World");

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final String  actualLine   = (String) this.reflectionModel.get("convertedLine");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedLine, actualLine, "置換が正しく行われること");

    }

    /**
     * replaceInLine メソッドのテスト - 正常系：置換が発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_normalReplaceOccurs() throws Exception {

        /* 期待値の定義 */
        final String expectedConvertedLine = "new text";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "old text");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.replaceInLine("old", "new");

        /* 検証の準備 */
        final boolean actualResult        = testResult;
        final String  actualConvertedLine = (String) this.reflectionModel.get("convertedLine");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "置換が発生した場合はtrueが返されること");
        Assertions.assertEquals(expectedConvertedLine, actualConvertedLine, "convertedLineが正しく置換されること");

    }

    /**
     * replaceInLine メソッドのテスト - 準正常系：置換が行われない場合（protectedメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_semiNoReplace() throws Exception {

        /* 期待値の定義 */
        final String expectedLine = "Hello Test";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "Hello Test");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("replaceInLine", "World", "Test");

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final String  actualLine   = (String) this.reflectionModel.get("convertedLine");

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedLine, actualLine, "置換が行われないこと");

    }

    /**
     * replaceInLine メソッドのテスト - 準正常系：replacementがnullの場合（protectedメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_semiReplacementNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("convertedLine", "Hello Test");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("replaceInLine", "Test", null);

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");

    }

    /**
     * replaceInLine メソッドのテスト - 準正常系：targetがnullの場合（protectedメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_semiTargetNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("convertedLine", "Hello Test");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("replaceInLine", null, "World");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 異常系：flushでIOExceptionが発生した場合
     *
     * @throws Exception
     *                   例外
     */
    @SuppressWarnings("resource")
    @Test
    public void testWriteIntermediateFile_errorFlushIOException() throws Exception {

        /* 期待値の定義 */
        final Path               testInputFile         = this.tempDir.resolve("test_input.txt");
        final Path               testOutputFile        = this.tempDir.resolve("test_output.txt");
        final String             expectedDomainMessage = "[KMGTOOL_GEN07002] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN07002;
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
            Files.write(testInputFile, "test content".getBytes());
            this.testTarget.initialize(testInputFile, testOutputFile);

            // 行データを追加
            this.testTarget.addOneLineOfDataToRows();
            @SuppressWarnings("unchecked")
            final List<List<String>> rows = (List<List<String>>) this.reflectionModel.get("rows");
            rows.get(0).add("test data");

            // flushでIOExceptionを発生させるモックライターを作成
            try (final BufferedWriter mockWriter = Mockito.mock(BufferedWriter.class);) {

                Mockito.doThrow(new IOException("Test IOException")).when(mockWriter).flush();
                this.reflectionModel.set("writer", mockWriter);

            }
            this.reflectionModel.set("outputPath", testOutputFile);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.writeIntermediateFile();

            }, "flushでIOExceptionが発生した場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * writeIntermediateFile メソッドのテスト - 異常系：IOExceptionが発生した場合
     *
     * @throws Exception
     *                   例外
     */
    @SuppressWarnings("resource")
    @Test
    public void testWriteIntermediateFile_errorIOException() throws Exception {

        /* 期待値の定義 */
        final Path               testInputFile         = this.tempDir.resolve("test_input.txt");
        final Path               testOutputFile        = this.tempDir.resolve("test_output.txt");
        final String             expectedDomainMessage = "[KMGTOOL_GEN07001] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN07001;
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
            Files.write(testInputFile, "test content".getBytes());
            this.testTarget.initialize(testInputFile, testOutputFile);

            // 行データを追加
            this.testTarget.addOneLineOfDataToRows();
            @SuppressWarnings("unchecked")
            final List<List<String>> rows = (List<List<String>>) this.reflectionModel.get("rows");
            rows.get(0).add("test data");

            // IOExceptionを発生させるモックライターを作成
            try (final BufferedWriter mockWriter = Mockito.mock(BufferedWriter.class);) {

                Mockito.doThrow(new IOException("Test IOException")).when(mockWriter)
                    .write(ArgumentMatchers.anyString());
                this.reflectionModel.set("writer", mockWriter);

            }
            this.reflectionModel.set("outputPath", testOutputFile);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.writeIntermediateFile();

            }, "IOExceptionが発生した場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * writeIntermediateFile メソッドのテスト - 正常系：中間ファイルに書き込みが成功する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFile_normalWrite() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.txt");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);

        // 行データを追加
        this.testTarget.addOneLineOfDataToRows();
        @SuppressWarnings("unchecked")
        final List<List<String>> rows = (List<List<String>>) this.reflectionModel.get("rows");
        rows.get(0).add("test data");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeIntermediateFile();

        /* 検証の準備 */
        final boolean actualResult       = testResult;
        final boolean isOutputFileExists = Files.exists(testOutputFile);

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "中間ファイルへの書き込みが成功すること");
        Assertions.assertTrue(isOutputFileExists, "出力ファイルが作成されていること");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 正常系：空の行リストで書き込む場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFile_normalWriteEmptyRows() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testOutputFile = this.tempDir.resolve("test_output.txt");
        this.reflectionModel.set("outputPath", testOutputFile);
        this.reflectionModel.set("writer", Files.newBufferedWriter(testOutputFile));
        this.reflectionModel.set("rows", new ArrayList<>());

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeIntermediateFile();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 正常系：複数行のデータで書き込む場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFile_normalWriteMultipleRows() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testOutputFile = this.tempDir.resolve("test_output.txt");
        this.reflectionModel.set("outputPath", testOutputFile);
        this.reflectionModel.set("writer", Files.newBufferedWriter(testOutputFile));

        // 複数行のデータを準備
        final List<List<String>> rows = new ArrayList<>();
        final List<String>       row1 = new ArrayList<>();
        row1.add("data1");
        row1.add("data2");
        rows.add(row1);
        final List<String> row2 = new ArrayList<>();
        row2.add("data3");
        row2.add("data4");
        rows.add(row2);
        this.reflectionModel.set("rows", rows);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeIntermediateFile();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");

    }

}
