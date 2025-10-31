package kmg.tool.base.is.application.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.is.application.logic.IsDataSheetCreationLogic;

/**
 * IsDataSheetCreationServiceImplのテストクラス
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
public class IsDataSheetCreationServiceImplTest extends AbstractKmgTest {

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
    @InjectMocks
    private IsDataSheetCreationServiceImpl testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.2.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * IsDataSheetCreationLogicのモック
     *
     * @since 0.2.0
     */
    @Mock
    private IsDataSheetCreationLogic mockIsDataSheetCreationLogic;

    /**
     * KmgMessageSourceのモック
     *
     * @since 0.2.0
     */
    @Mock
    private KmgMessageSource mockMessageSource;

    /**
     * Sheetのモック
     *
     * @since 0.2.0
     */
    @Mock
    private Sheet mockInputSheet;

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

        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // isDataSheetCreationLogicをリフレクションで設定
        this.reflectionModel.set("isDataSheetCreationLogic", this.mockIsDataSheetCreationLogic);

        // messageSourceをリフレクションで設定
        this.reflectionModel.set("messageSource", this.mockMessageSource);

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
     * コンストラクタ メソッドのテスト - 正常系：カスタムロガーを使用した初期化
     *
     * @since 0.2.0
     */
    @Test
    public void testConstructor_normalCustomLogger() {

        /* 期待値の定義 */
        // 期待値なし（コンストラクタの動作確認のみ）

        /* 準備 */
        final Logger testLogger = LoggerFactory.getLogger("TestLogger");

        /* テスト対象の実行 */
        final IsDataSheetCreationServiceImpl actualResult = new IsDataSheetCreationServiceImpl(testLogger);

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        Assertions.assertNotNull(actualResult, "カスタムロガーを使用したコンストラクタが正常に動作すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：標準ロガーを使用した初期化
     *
     * @since 0.2.0
     */
    @Test
    public void testConstructor_normalStandardLogger() {

        /* 期待値の定義 */
        // 期待値なし（コンストラクタの動作確認のみ）

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        final IsDataSheetCreationServiceImpl actualResult = new IsDataSheetCreationServiceImpl();

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        Assertions.assertNotNull(actualResult, "標準ロガーを使用したコンストラクタが正常に動作すること");

    }

    /**
     * initialize メソッドのテスト - 正常系：正常な初期化
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testInitialize_normalInitialization() throws KmgReflectionException {

        /* 期待値の定義 */
        final KmgDbTypes          expectedKmgDbTypes = KmgDbTypes.POSTGRE_SQL;
        final Sheet               expectedInputSheet = this.createTestSheet();
        final Map<String, String> expectedSqlIdMap   = new HashMap<>();
        final Path                expectedOutputPath = this.tempDir.resolve("output.sql");

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        this.testTarget.initialize(expectedKmgDbTypes, expectedInputSheet, expectedSqlIdMap, expectedOutputPath);

        /* 検証の準備 */
        final KmgDbTypes          actualKmgDbTypes = (KmgDbTypes) this.reflectionModel.get("kmgDbTypes");
        final Sheet               actualInputSheet = (Sheet) this.reflectionModel.get("inputSheet");
        @SuppressWarnings("unchecked")
        final Map<String, String> actualSqlIdMap   = (Map<String, String>) this.reflectionModel.get("sqlIdMap");
        final Path                actualOutputPath = (Path) this.reflectionModel.get("outputPath");

        /* 検証の実施 */
        Assertions.assertEquals(expectedKmgDbTypes, actualKmgDbTypes, "KMG DBの種類が正しく設定されること");
        Assertions.assertEquals(expectedInputSheet, actualInputSheet, "入力シートが正しく設定されること");
        Assertions.assertEquals(expectedSqlIdMap, actualSqlIdMap, "SQL IDマップが正しく設定されること");
        Assertions.assertEquals(expectedOutputPath, actualOutputPath, "出力パスが正しく設定されること");

    }

    /**
     * initialize メソッドのテスト - 準正常系：nullパラメータでの初期化
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testInitialize_semiNullParameters() throws KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        final KmgDbTypes          testKmgDbTypes = null;
        final Sheet               testInputSheet = null;
        final Map<String, String> testSqlIdMap   = null;
        final Path                testOutputPath = null;

        /* テスト対象の実行 */
        this.testTarget.initialize(testKmgDbTypes, testInputSheet, testSqlIdMap, testOutputPath);

        /* 検証の準備 */
        final KmgDbTypes          actualKmgDbTypes = (KmgDbTypes) this.reflectionModel.get("kmgDbTypes");
        final Sheet               actualInputSheet = (Sheet) this.reflectionModel.get("inputSheet");
        @SuppressWarnings("unchecked")
        final Map<String, String> actualSqlIdMap   = (Map<String, String>) this.reflectionModel.get("sqlIdMap");
        final Path                actualOutputPath = (Path) this.reflectionModel.get("outputPath");

        /* 検証の実施 */
        Assertions.assertNull(actualKmgDbTypes, "nullのKMG DBの種類が正しく設定されること");
        Assertions.assertNull(actualInputSheet, "nullの入力シートが正しく設定されること");
        Assertions.assertNull(actualSqlIdMap, "nullのSQL IDマップが正しく設定されること");
        Assertions.assertNull(actualOutputPath, "nullの出力パスが正しく設定されること");

    }

    /**
     * outputInsertionSql メソッドのテスト - 異常系：IOException発生
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    @Disabled
    public void testOutputInsertionSql_errorIOException() throws KmgToolMsgException {

        /* 期待値の定義 */
        final String             expectedDomainMessage
                                                       = "[KMGTOOL_GEN10003] 出力ファイルへの書き込みに失敗しました。出力ファイルパス=[test_insert_test_table.sql]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN10003;
        final Class<?>           expectedCauseClass    = IOException.class;

        /* 準備 */
        final KmgDbTypes          testKmgDbTypes     = KmgDbTypes.POSTGRE_SQL;
        final Sheet               testInputSheet     = this.mockInputSheet;
        final Map<String, String> testSqlIdMap       = new HashMap<>();
        final Path                testOutputPath     = this.tempDir.resolve("output.sql");
        final Path                testOutputFilePath = this.tempDir.resolve("test_insert_test_table.sql");

        this.testTarget.initialize(testKmgDbTypes, testInputSheet, testSqlIdMap, testOutputPath);

        // IsDataSheetCreationLogicのモック設定（IOExceptionを発生させる）
        Mockito.doNothing().when(this.mockIsDataSheetCreationLogic).initialize(ArgumentMatchers.any(),
            ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.doNothing().when(this.mockIsDataSheetCreationLogic).createOutputFileDirectories();
        Mockito.when(this.mockIsDataSheetCreationLogic.getOutputFilePath()).thenReturn(testOutputFilePath);
        Mockito.when(this.mockIsDataSheetCreationLogic.getCharset())
            .thenReturn(java.nio.charset.StandardCharsets.UTF_8);
        Mockito.when(this.mockIsDataSheetCreationLogic.getDeleteComment()).thenReturn("-- テスト");
        Mockito.when(this.mockIsDataSheetCreationLogic.getDeleteSql()).thenReturn("DELETE FROM test;");
        Mockito.when(this.mockIsDataSheetCreationLogic.getInsertComment()).thenReturn("-- テスト");
        Mockito.when(this.mockIsDataSheetCreationLogic.getInsertSql(ArgumentMatchers.any(Row.class)))
            .thenReturn("INSERT INTO test VALUES ('test');");

        // inputSheetのモック設定
        final Row mockRow = Mockito.mock(Row.class);
        Mockito.when(testInputSheet.getLastRowNum()).thenReturn(4);
        Mockito.when(testInputSheet.getRow(4)).thenReturn(mockRow);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // ファイル書き込みでIOExceptionを発生させる
            try (final MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {

                mockedFiles.when(() -> Files.newBufferedWriter(ArgumentMatchers.any(Path.class),
                    ArgumentMatchers.any(Charset.class))).thenThrow(new IOException("テスト用のIOException"));

                /* テスト対象の実行 */
                final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                    this.testTarget.outputInsertionSql();

                });

                /* 検証の実施 */
                this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                    expectedMessageTypes);

            }

        }

    }

    /**
     * outputInsertionSql メソッドのテスト - 異常系：初期化前の実行
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    @Disabled
    public void testOutputInsertionSql_errorNotInitialized() throws KmgReflectionException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN10006] 入力ファイルパスがnullです。";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN10006;
        final Class<?>           expectedCauseClass    = null;

        /* 準備 */
        // 初期化しない

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

                this.testTarget.outputInsertionSql();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * outputInsertionSql メソッドのテスト - 正常系：正常な実行
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws IOException
     *                             入出力例外
     */
    @Test
    public void testOutputInsertionSql_normalExecution() throws KmgToolMsgException, IOException {

        /* 期待値の定義 */
        final KmgDbTypes          expectedKmgDbTypes     = KmgDbTypes.POSTGRE_SQL;
        final Sheet               expectedInputSheet     = this.mockInputSheet;
        final Map<String, String> expectedSqlIdMap       = new HashMap<>();
        final Path                expectedOutputPath     = this.tempDir.resolve("output.sql");
        final Path                expectedOutputFilePath = this.tempDir.resolve("test_insert_test_table.sql");
        final Charset             expectedCharset        = java.nio.charset.StandardCharsets.UTF_8;
        final String              expectedDeleteComment  = "-- テストシートのレコード削除";
        final String              expectedDeleteSql      = "DELETE FROM test_table;";
        final String              expectedInsertComment  = "-- テストシートのレコード挿入";
        final String              expectedInsertSql
                                                         = "INSERT INTO test_table (test_column) VALUES ('test_value');";

        /* 準備 */
        this.testTarget.initialize(expectedKmgDbTypes, expectedInputSheet, expectedSqlIdMap, expectedOutputPath);

        // IsDataSheetCreationLogicのモック設定
        Mockito.doNothing().when(this.mockIsDataSheetCreationLogic).initialize(ArgumentMatchers.eq(expectedKmgDbTypes),
            ArgumentMatchers.eq(expectedInputSheet), ArgumentMatchers.eq(expectedSqlIdMap),
            ArgumentMatchers.eq(expectedOutputPath));
        Mockito.doNothing().when(this.mockIsDataSheetCreationLogic).createOutputFileDirectories();
        Mockito.when(this.mockIsDataSheetCreationLogic.getOutputFilePath()).thenReturn(expectedOutputFilePath);
        Mockito.when(this.mockIsDataSheetCreationLogic.getCharset()).thenReturn(expectedCharset);
        Mockito.when(this.mockIsDataSheetCreationLogic.getDeleteComment()).thenReturn(expectedDeleteComment);
        Mockito.when(this.mockIsDataSheetCreationLogic.getDeleteSql()).thenReturn(expectedDeleteSql);
        Mockito.when(this.mockIsDataSheetCreationLogic.getInsertComment()).thenReturn(expectedInsertComment);
        Mockito.when(this.mockIsDataSheetCreationLogic.getInsertSql(ArgumentMatchers.any(Row.class)))
            .thenReturn(expectedInsertSql);

        // inputSheetのモック設定
        final Row mockRow = Mockito.mock(Row.class);
        Mockito.when(expectedInputSheet.getLastRowNum()).thenReturn(4);
        Mockito.when(expectedInputSheet.getRow(4)).thenReturn(mockRow);

        /* テスト対象の実行 */
        this.testTarget.outputInsertionSql();

        /* 検証の実施 */
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).initialize(
            ArgumentMatchers.eq(expectedKmgDbTypes), ArgumentMatchers.eq(expectedInputSheet),
            ArgumentMatchers.eq(expectedSqlIdMap), ArgumentMatchers.eq(expectedOutputPath));
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).createOutputFileDirectories();
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).getOutputFilePath();
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).getCharset();
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).getDeleteComment();
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).getDeleteSql();
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).getInsertComment();
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1))
            .getInsertSql(ArgumentMatchers.any(Row.class));

        // 出力ファイルの内容を検証
        Assertions.assertTrue(Files.exists(expectedOutputFilePath), "出力ファイルが作成されること");
        final String actualContent = Files.readString(expectedOutputFilePath);
        Assertions.assertTrue(actualContent.contains(expectedDeleteComment), "削除コメントが出力されること");
        Assertions.assertTrue(actualContent.contains(expectedDeleteSql), "削除SQLが出力されること");
        Assertions.assertTrue(actualContent.contains(expectedInsertComment), "挿入コメントが出力されること");
        Assertions.assertTrue(actualContent.contains(expectedInsertSql), "挿入SQLが出力されること");

    }

    /**
     * outputInsertionSql メソッドのテスト - 正常系：null行を含むシートでの実行
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws IOException
     *                             入出力例外
     */
    @Test
    public void testOutputInsertionSql_normalExecutionWithNullRows() throws KmgToolMsgException, IOException {

        /* 期待値の定義 */
        final KmgDbTypes          expectedKmgDbTypes     = KmgDbTypes.POSTGRE_SQL;
        final Sheet               expectedInputSheet     = this.mockInputSheet;
        final Map<String, String> expectedSqlIdMap       = new HashMap<>();
        final Path                expectedOutputPath     = this.tempDir.resolve("output.sql");
        final Path                expectedOutputFilePath = this.tempDir.resolve("test_insert_test_table.sql");
        final Charset             expectedCharset        = java.nio.charset.StandardCharsets.UTF_8;
        final String              expectedDeleteComment  = "-- テストシートのレコード削除";
        final String              expectedDeleteSql      = "DELETE FROM test_table;";
        final String              expectedInsertComment  = "-- テストシートのレコード挿入";
        final String              expectedInsertSql
                                                         = "INSERT INTO test_table (test_column) VALUES ('test_value');";

        /* 準備 */
        this.testTarget.initialize(expectedKmgDbTypes, expectedInputSheet, expectedSqlIdMap, expectedOutputPath);

        // IsDataSheetCreationLogicのモック設定
        Mockito.doNothing().when(this.mockIsDataSheetCreationLogic).initialize(ArgumentMatchers.eq(expectedKmgDbTypes),
            ArgumentMatchers.eq(expectedInputSheet), ArgumentMatchers.eq(expectedSqlIdMap),
            ArgumentMatchers.eq(expectedOutputPath));
        Mockito.doNothing().when(this.mockIsDataSheetCreationLogic).createOutputFileDirectories();
        Mockito.when(this.mockIsDataSheetCreationLogic.getOutputFilePath()).thenReturn(expectedOutputFilePath);
        Mockito.when(this.mockIsDataSheetCreationLogic.getCharset()).thenReturn(expectedCharset);
        Mockito.when(this.mockIsDataSheetCreationLogic.getDeleteComment()).thenReturn(expectedDeleteComment);
        Mockito.when(this.mockIsDataSheetCreationLogic.getDeleteSql()).thenReturn(expectedDeleteSql);
        Mockito.when(this.mockIsDataSheetCreationLogic.getInsertComment()).thenReturn(expectedInsertComment);
        Mockito.when(this.mockIsDataSheetCreationLogic.getInsertSql(ArgumentMatchers.any(Row.class)))
            .thenReturn(expectedInsertSql);

        // inputSheetのモック設定（行5以降でnullを返すように）
        final Row mockRow4 = Mockito.mock(Row.class);
        Mockito.when(expectedInputSheet.getLastRowNum()).thenReturn(6);
        Mockito.when(expectedInputSheet.getRow(4)).thenReturn(mockRow4); // 行4は存在
        Mockito.when(expectedInputSheet.getRow(5)).thenReturn(null); // 行5はnull
        Mockito.when(expectedInputSheet.getRow(6)).thenReturn(null); // 行6はnull

        /* テスト対象の実行 */
        this.testTarget.outputInsertionSql();

        /* 検証の実施 */
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).initialize(
            ArgumentMatchers.eq(expectedKmgDbTypes), ArgumentMatchers.eq(expectedInputSheet),
            ArgumentMatchers.eq(expectedSqlIdMap), ArgumentMatchers.eq(expectedOutputPath));
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).createOutputFileDirectories();
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).getOutputFilePath();
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).getCharset();
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).getDeleteComment();
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).getDeleteSql();
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).getInsertComment();
        // null行をスキップするため、getInsertSqlは1回のみ呼ばれる
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1))
            .getInsertSql(ArgumentMatchers.any(Row.class));

        // 出力ファイルの内容を検証
        Assertions.assertTrue(Files.exists(expectedOutputFilePath), "出力ファイルが作成されること");
        final String actualContent = Files.readString(expectedOutputFilePath);
        Assertions.assertTrue(actualContent.contains(expectedDeleteComment), "削除コメントが出力されること");
        Assertions.assertTrue(actualContent.contains(expectedDeleteSql), "削除SQLが出力されること");
        Assertions.assertTrue(actualContent.contains(expectedInsertComment), "挿入コメントが出力されること");
        Assertions.assertTrue(actualContent.contains(expectedInsertSql), "挿入SQLが出力されること");

    }

    /**
     * run メソッドのテスト - 異常系：KmgToolMsgException発生時の処理
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    @Disabled
    public void testRun_errorKmgToolMsgException() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final String             expectedLogMessage  = "テスト用のログメッセージ";
        final KmgToolLogMsgTypes expectedLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG10000;

        /* 準備 */
        final KmgDbTypes          testKmgDbTypes = KmgDbTypes.POSTGRE_SQL;
        final Sheet               testInputSheet = this.mockInputSheet;
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = this.tempDir.resolve("output.sql");

        this.testTarget.initialize(testKmgDbTypes, testInputSheet, testSqlIdMap, testOutputPath);

        // messageSourceのモック設定
        Mockito
            .when(
                this.mockMessageSource.getLogMessage(ArgumentMatchers.eq(expectedLogMsgTypes), ArgumentMatchers.any()))
            .thenReturn(expectedLogMessage);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // testTargetをスパイしてoutputInsertionSqlメソッドでKmgToolMsgExceptionを発生させる
            final IsDataSheetCreationServiceImpl spyTarget = Mockito.spy(this.testTarget);

            // loggerフィールドをリフレクションで設定
            final KmgReflectionModelImpl spyReflectionModel = new KmgReflectionModelImpl(spyTarget);
            final Logger                 testLogger         = LoggerFactory.getLogger("TestLogger");
            spyReflectionModel.set("logger", testLogger);

            // KmgToolMsgExceptionを発生させる
            final KmgToolGenMsgTypes  genMsgTypes   = KmgToolGenMsgTypes.KMGTOOL_GEN10003;
            final Object[]            genMsgArgs    = {
                "test_file.sql"
            };
            final KmgToolMsgException testException = new KmgToolMsgException(genMsgTypes, genMsgArgs);
            Mockito.doThrow(testException).when(spyTarget).outputInsertionSql();

            /* テスト対象の実行 */
            spyTarget.run();

            /* 検証の実施 */
            // messageSource.getLogMessageが呼ばれることを確認
            Mockito.verify(this.mockMessageSource, Mockito.times(1))
                .getLogMessage(ArgumentMatchers.eq(expectedLogMsgTypes), ArgumentMatchers.any());

            // outputInsertionSqlメソッドが呼ばれることを確認
            Mockito.verify(spyTarget, Mockito.times(1)).outputInsertionSql();

        }

    }

    /**
     * run メソッドのテスト - 正常系：正常な実行
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testRun_normalExecution() throws KmgToolMsgException {

        /* 期待値の定義 */

        /* 準備 */
        final KmgDbTypes          testKmgDbTypes = KmgDbTypes.POSTGRE_SQL;
        final Sheet               testInputSheet = this.mockInputSheet;
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = this.tempDir.resolve("output.sql");

        this.testTarget.initialize(testKmgDbTypes, testInputSheet, testSqlIdMap, testOutputPath);

        // IsDataSheetCreationLogicのモック設定
        Mockito.doNothing().when(this.mockIsDataSheetCreationLogic).initialize(ArgumentMatchers.any(),
            ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.doNothing().when(this.mockIsDataSheetCreationLogic).createOutputFileDirectories();
        Mockito.when(this.mockIsDataSheetCreationLogic.getOutputFilePath())
            .thenReturn(this.tempDir.resolve("test.sql"));
        Mockito.when(this.mockIsDataSheetCreationLogic.getCharset())
            .thenReturn(java.nio.charset.StandardCharsets.UTF_8);
        Mockito.when(this.mockIsDataSheetCreationLogic.getDeleteComment()).thenReturn("-- テスト");
        Mockito.when(this.mockIsDataSheetCreationLogic.getDeleteSql()).thenReturn("DELETE FROM test;");
        Mockito.when(this.mockIsDataSheetCreationLogic.getInsertComment()).thenReturn("-- テスト");
        Mockito.when(this.mockIsDataSheetCreationLogic.getInsertSql(ArgumentMatchers.any(Row.class)))
            .thenReturn("INSERT INTO test VALUES ('test');");

        // inputSheetのモック設定
        final Row mockRow = Mockito.mock(Row.class);
        Mockito.when(testInputSheet.getLastRowNum()).thenReturn(4);
        Mockito.when(testInputSheet.getRow(4)).thenReturn(mockRow);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用のログメッセージ");

            /* テスト対象の実行 */
            this.testTarget.run();

            /* 検証の実施 */
            Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).initialize(ArgumentMatchers.any(),
                ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());

        }

    }

    /**
     * テスト用のシートを作成する<br>
     *
     * @since 0.2.0
     *
     * @return テスト用シート
     */
    private Sheet createTestSheet() {

        final Sheet result;

        try (final Workbook workbook = new XSSFWorkbook()) {

            result = workbook.createSheet("テストシート");

            // 1行目にテーブル物理名を設定
            final Row                              row0    = result.createRow(0);
            final org.apache.poi.ss.usermodel.Cell cell0_0 = row0.createCell(0);
            cell0_0.setCellValue("test_table");

        } catch (final Exception e) {

            throw new RuntimeException("テスト用シートの作成に失敗しました", e);

        }

        return result;

    }

}
