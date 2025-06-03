package kmg.tool.application.logic.is.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import kmg.core.infrastructure.types.KmgCharsetTypes;
import kmg.core.infrastructure.types.KmgDbDataTypeTypes;
import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;

/**
 * 挿入SQLデータシート作成ロジック実装のテスト<br>
 *
 * @author KenichiroArai
 */
@SuppressWarnings({
    "nls", "static-method"
})
public class IsDataSheetCreationLogicImplTest {

    /**
     * デフォルトコンストラクタ<br>
     */
    public IsDataSheetCreationLogicImplTest() {

        // 処理なし
    }

    /**
     * createOutputFileDirectories メソッドのテスト - 正常系:出力ディレクトリが正しく作成されることの確認
     * <p>
     * 指定されたパスに出力ディレクトリが正しく作成されることを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testCreateOutputFileDirectories_normalDirectoryCreationSuccess(@TempDir final Path tempDir)
        throws KmgToolMsgException {

        /* 期待値の定義 */
        final boolean expectedDirectoryExists = true;

        /* 準備 */
        final Path                         outputPath = tempDir.resolve("output");
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // 初期化
        final Sheet               testSheet    = this.createTestSheet();
        final Map<String, String> testSqlIdMap = new HashMap<>();
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, outputPath);

        /* テスト対象の実行 */
        testTarget.createOutputFileDirectories();

        /* 検証の準備 */
        final boolean actualDirectoryExists = Files.exists(outputPath);

        /* 検証の実施 */
        Assertions.assertEquals(expectedDirectoryExists, actualDirectoryExists, "出力ディレクトリが正しく作成されること");

    }

    /**
     * createOutputFileDirectories メソッドのテスト - 準正常系:既存ディレクトリが存在する場合でも成功することの確認
     * <p>
     * 既に存在するディレクトリに対しても処理が正常に完了することを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws IOException
     *                             入出力例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testCreateOutputFileDirectories_normalExistingDirectoryHandled(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final boolean expectedDirectoryExists = true;

        /* 準備 */
        final Path outputPath = tempDir.resolve("existing");
        Files.createDirectories(outputPath); // 事前にディレクトリを作成

        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // 初期化
        final Sheet               testSheet    = this.createTestSheet();
        final Map<String, String> testSqlIdMap = new HashMap<>();
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, outputPath);

        /* テスト対象の実行 */
        testTarget.createOutputFileDirectories();

        /* 検証の準備 */
        final boolean actualDirectoryExists = Files.exists(outputPath);

        /* 検証の実施 */
        Assertions.assertEquals(expectedDirectoryExists, actualDirectoryExists, "既存ディレクトリが存在する場合でも正常に処理されること");

    }

    /**
     * getCharset メソッドのテスト - 正常系:PostgreSQLでMS932が返されることの確認
     * <p>
     * PostgreSQLの場合にMS932文字セットが返されることを確認します。
     * </p>
     */
    @Test
    public void testGetCharset_normalPostgreSqlReturnsMs932() {

        /* 期待値の定義 */
        final Charset expectedCharset = KmgCharsetTypes.MS932.toCharset();

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // 初期化
        final Sheet               testSheet      = this.createTestSheet();
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final Charset testResult = testTarget.getCharset();

        /* 検証の準備 */
        final Charset actualCharset = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedCharset, actualCharset, "PostgreSQLの場合はMS932文字セットが返されること");

    }

    /**
     * getCharset メソッドのテスト - 正常系:MySQLでUTF8が返されることの確認
     * <p>
     * MySQLの場合にUTF8文字セットが返されることを確認します。
     * </p>
     */
    @Test
    public void testGetCharset_normalMySqlReturnsUtf8() {

        /* 期待値の定義 */
        final Charset expectedCharset = KmgCharsetTypes.UTF8.toCharset();

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // 初期化
        final Sheet               testSheet      = this.createTestSheet();
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.MYSQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final Charset testResult = testTarget.getCharset();

        /* 検証の準備 */
        final Charset actualCharset = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedCharset, actualCharset, "MySQLの場合はUTF8文字セットが返されること");

    }

    /**
     * getColumnNum メソッドのテスト - 正常系:正しいカラム数が返されることの確認
     * <p>
     * シートから正しいカラム数が取得されることを確認します。
     * </p>
     */
    @Test
    public void testGetColumnNum_normalCorrectColumnCount() {

        /* 期待値の定義 */
        final short expectedColumnNum = 3;

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithColumns();
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final short testResult = testTarget.getColumnNum();

        /* 検証の準備 */
        final short actualColumnNum = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedColumnNum, actualColumnNum, "正しいカラム数が返されること");

    }

    /**
     * getColumnPhysicsNameList メソッドのテスト - 正常系:カラム物理名リストが正しく取得されることの確認
     * <p>
     * シートからカラム物理名のリストが正しく取得されることを確認します。
     * </p>
     */
    @Test
    public void testGetColumnPhysicsNameList_normalCorrectPhysicsNameList() {

        /* 期待値の定義 */
        final String[] expectedPhysicsNames = {
            "id", "name", "value"
        };

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithColumns();
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final List<String> testResult = testTarget.getColumnPhysicsNameList();

        /* 検証の準備 */
        final String[] actualPhysicsNames = testResult.toArray(new String[0]);

        /* 検証の実施 */
        Assertions.assertArrayEquals(expectedPhysicsNames, actualPhysicsNames, "カラム物理名リストが正しく取得されること");

    }

    /**
     * getDeleteComment メソッドのテスト - 正常系:削除コメントが正しく生成されることの確認
     * <p>
     * テーブル論理名から削除コメントが正しく生成されることを確認します。
     * </p>
     */
    @Test
    public void testGetDeleteComment_normalCorrectDeleteComment() {

        /* 期待値の定義 */
        final String expectedDeleteComment = "-- テストテーブルのレコード削除";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithName("テストテーブル");
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final String testResult = testTarget.getDeleteComment();

        /* 検証の準備 */
        final String actualDeleteComment = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedDeleteComment, actualDeleteComment, "削除コメントが正しく生成されること");

    }

    /**
     * getDeleteSql メソッドのテスト - 正常系:削除SQLが正しく生成されることの確認
     * <p>
     * テーブル物理名から削除SQLが正しく生成されることを確認します。
     * </p>
     */
    @Test
    public void testGetDeleteSql_normalCorrectDeleteSql() {

        /* 期待値の定義 */
        final String expectedDeleteSql = "DELETE FROM test_table;";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithPhysicsName("test_table");
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final String testResult = testTarget.getDeleteSql();

        /* 検証の準備 */
        final String actualDeleteSql = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedDeleteSql, actualDeleteSql, "削除SQLが正しく生成されること");

    }

    /**
     * getInsertComment メソッドのテスト - 正常系:挿入コメントが正しく生成されることの確認
     * <p>
     * テーブル論理名から挿入コメントが正しく生成されることを確認します。
     * </p>
     */
    @Test
    public void testGetInsertComment_normalCorrectInsertComment() {

        /* 期待値の定義 */
        final String expectedInsertComment = "-- テストテーブルのレコード挿入";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithName("テストテーブル");
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final String testResult = testTarget.getInsertComment();

        /* 検証の準備 */
        final String actualInsertComment = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInsertComment, actualInsertComment, "挿入コメントが正しく生成されること");

    }

    /**
     * getKmgDbDataTypeList メソッドのテスト - 正常系:データ型リストが正しく取得されることの確認
     * <p>
     * シートからデータ型のリストが正しく取得されることを確認します。 セルの値がKmgDbDataTypeTypesのキーと一致しない場合はNONEが返されます。
     * </p>
     */
    @Test
    public void testGetKmgDbDataTypeList_normalCorrectDataTypeList() {

        /* 期待値の定義 */
        // セルの値「INTEGER」「STRING」「DOUBLE」は実際のキーと一致しないため、NONEが返される
        final KmgDbDataTypeTypes[] expectedDataTypes = {
            KmgDbDataTypeTypes.NONE, KmgDbDataTypeTypes.NONE, KmgDbDataTypeTypes.NONE
        };

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithDataTypes();
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final List<KmgDbDataTypeTypes> testResult = testTarget.getKmgDbDataTypeList();

        /* 検証の準備 */
        final KmgDbDataTypeTypes[] actualDataTypes = testResult.toArray(new KmgDbDataTypeTypes[0]);

        /* 検証の実施 */
        Assertions.assertArrayEquals(expectedDataTypes, actualDataTypes, "データ型リストが正しく取得されること");

    }

    /**
     * getOutputFilePath メソッドのテスト - 正常系:出力ファイルパスが正しく生成されることの確認
     * <p>
     * SQLIDとテーブル物理名から出力ファイルパスが正しく生成されることを確認します。
     * </p>
     */
    @Test
    public void testGetOutputFilePath_normalCorrectOutputFilePath() {

        /* 期待値の定義 */
        final String expectedFileName = "SQL001_insert_test_table.sql";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet    = this.createTestSheetWithPhysicsName("test_table");
        final Map<String, String> testSqlIdMap = new HashMap<>();
        testSqlIdMap.put("test_table", "SQL001");
        final Path testOutputPath = Paths.get("output");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final Path testResult = testTarget.getOutputFilePath();

        /* 検証の準備 */
        final String actualFileName = testResult.getFileName().toString();

        /* 検証の実施 */
        Assertions.assertEquals(expectedFileName, actualFileName, "出力ファイルパスが正しく生成されること");

    }

    /**
     * getSqlId メソッドのテスト - 正常系:SQLIDが正しく取得されることの確認
     * <p>
     * SQLIDマップからSQLIDが正しく取得されることを確認します。
     * </p>
     */
    @Test
    public void testGetSqlId_normalCorrectSqlId() {

        /* 期待値の定義 */
        final String expectedSqlId = "SQL001";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet    = this.createTestSheetWithPhysicsName("test_table");
        final Map<String, String> testSqlIdMap = new HashMap<>();
        testSqlIdMap.put("test_table", "SQL001");
        final Path testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final String testResult = testTarget.getSqlId();

        /* 検証の準備 */
        final String actualSqlId = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSqlId, actualSqlId, "SQLIDが正しく取得されること");

    }

    /**
     * getTableLogicName メソッドのテスト - 正常系:テーブル論理名が正しく取得されることの確認
     * <p>
     * シート名からテーブル論理名が正しく取得されることを確認します。
     * </p>
     */
    @Test
    public void testGetTableLogicName_normalCorrectTableLogicName() {

        /* 期待値の定義 */
        final String expectedTableLogicName = "テストテーブル";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithName("テストテーブル");
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final String testResult = testTarget.getTableLogicName();

        /* 検証の準備 */
        final String actualTableLogicName = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTableLogicName, actualTableLogicName, "テーブル論理名が正しく取得されること");

    }

    /**
     * getTablePhysicsName メソッドのテスト - 正常系:テーブル物理名が正しく取得されることの確認
     * <p>
     * セルからテーブル物理名が正しく取得されることを確認します。
     * </p>
     */
    @Test
    public void testGetTablePhysicsName_normalCorrectTablePhysicsName() {

        /* 期待値の定義 */
        final String expectedTablePhysicsName = "test_table";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithPhysicsName("test_table");
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final String testResult = testTarget.getTablePhysicsName();

        /* 検証の準備 */
        final String actualTablePhysicsName = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTablePhysicsName, actualTablePhysicsName, "テーブル物理名が正しく取得されること");

    }

    /**
     * initialize メソッドのテスト - 正常系:初期化が正しく実行されることの確認
     * <p>
     * 各パラメータが正しく設定されることを確認します。
     * </p>
     */
    @Test
    public void testInitialize_normalInitializationSuccess() {

        /* 期待値の定義 */
        final KmgDbTypes expectedDbTypes = KmgDbTypes.POSTGRE_SQL;

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget     = new IsDataSheetCreationLogicImpl();
        final Sheet                        testSheet      = this.createTestSheet();
        final Map<String, String>          testSqlIdMap   = new HashMap<>();
        final Path                         testOutputPath = Paths.get("test");

        /* テスト対象の実行 */
        testTarget.initialize(expectedDbTypes, testSheet, testSqlIdMap, testOutputPath);

        /* 検証の準備 */
        // 初期化の確認は、後続のgetterメソッドでパラメータが正しく設定されていることで確認
        final Path actualOutputPath = testTarget.getOutputFilePath();

        /* 検証の実施 */
        Assertions.assertNotNull(actualOutputPath, "初期化が正しく実行されること");

    }

    /**
     * テスト用のシートを作成する<br>
     *
     * @return テスト用シート
     */
    private Sheet createTestSheet() {

        final Workbook workbook = new XSSFWorkbook();
        final Sheet    sheet    = workbook.createSheet("テストシート");

        // 1行目にテーブル物理名を設定
        final Row  row0    = sheet.createRow(0);
        final Cell cell0_0 = row0.createCell(0);
        cell0_0.setCellValue("test_table");

        return sheet;

    }

    /**
     * 指定した名前のテスト用シートを作成する<br>
     *
     * @param sheetName
     *                  シート名
     *
     * @return テスト用シート
     */
    private Sheet createTestSheetWithName(final String sheetName) {

        final Workbook workbook = new XSSFWorkbook();
        final Sheet    sheet    = workbook.createSheet(sheetName);

        // 1行目にテーブル物理名を設定
        final Row  row0    = sheet.createRow(0);
        final Cell cell0_0 = row0.createCell(0);
        cell0_0.setCellValue("test_table");

        return sheet;

    }

    /**
     * テーブル物理名を持つテスト用シートを作成する<br>
     *
     * @param physicsName
     *                    テーブル物理名
     *
     * @return テスト用シート
     */
    private Sheet createTestSheetWithPhysicsName(final String physicsName) {

        final Workbook workbook = new XSSFWorkbook();
        final Sheet    sheet    = workbook.createSheet("テストシート");

        // 1行目にテーブル物理名を設定
        final Row  row0    = sheet.createRow(0);
        final Cell cell0_0 = row0.createCell(0);
        cell0_0.setCellValue(physicsName);

        return sheet;

    }

    /**
     * カラム情報を持つテスト用シートを作成する<br>
     *
     * @return テスト用シート
     */
    private Sheet createTestSheetWithColumns() {

        final Workbook workbook = new XSSFWorkbook();
        final Sheet    sheet    = workbook.createSheet("テストシート");

        // 1行目にテーブル物理名を設定
        final Row  row0    = sheet.createRow(0);
        final Cell cell0_0 = row0.createCell(0);
        cell0_0.setCellValue("test_table");

        // 3行目（インデックス2）にカラム物理名を設定
        final Row  row2    = sheet.createRow(2);
        final Cell cell2_0 = row2.createCell(0);
        cell2_0.setCellValue("id");
        final Cell cell2_1 = row2.createCell(1);
        cell2_1.setCellValue("name");
        final Cell cell2_2 = row2.createCell(2);
        cell2_2.setCellValue("value");

        return sheet;

    }

    /**
     * データ型情報を持つテスト用シートを作成する<br>
     *
     * @return テスト用シート
     */
    private Sheet createTestSheetWithDataTypes() {

        final Workbook workbook = new XSSFWorkbook();
        final Sheet    sheet    = workbook.createSheet("テストシート");

        // 1行目にテーブル物理名を設定
        final Row  row0    = sheet.createRow(0);
        final Cell cell0_0 = row0.createCell(0);
        cell0_0.setCellValue("test_table");

        // 3行目（インデックス2）にカラム物理名を設定
        final Row  row2    = sheet.createRow(2);
        final Cell cell2_0 = row2.createCell(0);
        cell2_0.setCellValue("id");
        final Cell cell2_1 = row2.createCell(1);
        cell2_1.setCellValue("name");
        final Cell cell2_2 = row2.createCell(2);
        cell2_2.setCellValue("value");

        // 4行目（インデックス3）にデータ型を設定
        final Row  row3    = sheet.createRow(3);
        final Cell cell3_0 = row3.createCell(0);
        cell3_0.setCellValue("INTEGER");
        final Cell cell3_1 = row3.createCell(1);
        cell3_1.setCellValue("STRING");
        final Cell cell3_2 = row3.createCell(2);
        cell3_2.setCellValue("DOUBLE");

        return sheet;

    }
}
