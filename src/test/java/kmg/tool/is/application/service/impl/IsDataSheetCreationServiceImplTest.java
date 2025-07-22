package kmg.tool.is.application.service.impl;

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

import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.is.application.logic.IsDataSheetCreationLogic;

/**
 * IsDataSheetCreationServiceImplのテストクラス
 *
 * @author KenichiroArai
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class IsDataSheetCreationServiceImplTest extends AbstractKmgTest {

    /** テンポラリディレクトリ */
    @TempDir
    private Path tempDir;

    /** テスト対象 */
    @InjectMocks
    private IsDataSheetCreationServiceImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** IsDataSheetCreationLogicのモック */
    @Mock
    private IsDataSheetCreationLogic mockIsDataSheetCreationLogic;

    /** KmgMessageSourceのモック */
    @Mock
    private KmgMessageSource mockMessageSource;

    /**
     * セットアップ
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // isDataSheetCreationLogicをリフレクションで設定
        this.reflectionModel.set("isDataSheetCreationLogic", this.mockIsDataSheetCreationLogic);

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
        final IsDataSheetCreationServiceImpl actualResult = new IsDataSheetCreationServiceImpl(testLogger);

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
        final IsDataSheetCreationServiceImpl actualResult = new IsDataSheetCreationServiceImpl();

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        Assertions.assertNotNull(actualResult, "標準ロガーを使用したコンストラクタが正常に動作すること");

    }

    /**
     * initialize メソッドのテスト - 正常系：正常な初期化
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
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testOutputInsertionSql_errorIOException() throws KmgToolMsgException {

        /* 期待値の定義 */
        final String             expectedDomainMessage
                                                       = "[KMGTOOL_GEN10003] 出力ファイルへの書き込みに失敗しました。出力ファイルパス=[test_insert_test_table.sql]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN10003;
        final Class<?>           expectedCauseClass    = IOException.class;

        /* 準備 */
        final KmgDbTypes          testKmgDbTypes     = KmgDbTypes.POSTGRE_SQL;
        final Sheet               testInputSheet     = this.createTestSheetWithData();
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
     * outputInsertionSql メソッドのテスト - 正常系：正常な実行
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
        final Sheet               expectedInputSheet     = this.createTestSheetWithData();
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
     * run メソッドのテスト - 正常系：正常な実行
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testRun_normalExecution() throws KmgToolMsgException {

        /* 期待値の定義 */

        /* 準備 */
        final KmgDbTypes          testKmgDbTypes = KmgDbTypes.POSTGRE_SQL;
        final Sheet               testInputSheet = this.createTestSheetWithData();
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

        /* テスト対象の実行 */
        this.testTarget.run();

        /* 検証の実施 */
        Mockito.verify(this.mockIsDataSheetCreationLogic, Mockito.times(1)).initialize(ArgumentMatchers.any(),
            ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());

    }

    /**
     * テスト用のシートを作成する<br>
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

    /**
     * データ付きテスト用シートを作成する<br>
     *
     * @return テスト用シート
     */
    private Sheet createTestSheetWithData() {

        final Sheet result;

        try (final Workbook workbook = new XSSFWorkbook()) {

            result = workbook.createSheet("テストシート");

            // 1行目にテーブル物理名を設定
            final Row                              row0    = result.createRow(0);
            final org.apache.poi.ss.usermodel.Cell cell0_0 = row0.createCell(0);
            cell0_0.setCellValue("test_table");

            // 3行目（インデックス2）にカラム物理名を設定
            final Row                              row2    = result.createRow(2);
            final org.apache.poi.ss.usermodel.Cell cell2_0 = row2.createCell(0);
            cell2_0.setCellValue("test_column");

            // 4行目（インデックス3）にデータ型を設定
            final Row                              row3    = result.createRow(3);
            final org.apache.poi.ss.usermodel.Cell cell3_0 = row3.createCell(0);
            cell3_0.setCellValue("STRING");

            // 5行目（インデックス4）にテストデータを設定
            final Row                              row4    = result.createRow(4);
            final org.apache.poi.ss.usermodel.Cell cell4_0 = row4.createCell(0);
            cell4_0.setCellValue("test_value");

        } catch (final Exception e) {

            throw new RuntimeException("テスト用シートの作成に失敗しました", e);

        }

        return result;

    }

}
