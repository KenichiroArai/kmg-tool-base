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
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.types.KmgCharsetTypes;
import kmg.core.infrastructure.types.KmgDbDataTypeTypes;
import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * 挿入SQLデータシート作成ロジック実装のテスト<br>
 *
 * @author KenichiroArai
 */
@SpringBootTest(classes = kmg.tool.presentation.ui.gui.is.IsCreationTool.class)
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
     * createOutputFileDirectories メソッドのテスト - 異常系:IOException発生時のテスト
     * <p>
     * ディレクトリ作成時にIOExceptionが発生した場合、適切にKmgToolMsgExceptionがスローされることを確認します。
     * Mockitoを使用してFiles.createDirectoriesをモックし、IOExceptionを発生させてテストします。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     */
    @Test
    public void testCreateOutputFileDirectories_errorIOException(@TempDir final Path tempDir) {

        /* 準備 */
        final Path                         outputPath = tempDir.resolve("output").toAbsolutePath();
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // 初期化
        final Sheet               testSheet    = this.createTestSheet();
        final Map<String, String> testSqlIdMap = new HashMap<>();
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, outputPath);

        /* テスト実行 */
        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {

            // Files.createDirectoriesがIOExceptionをスローするように設定
            final IOException testException = new IOException("Disk full");
            mockedFiles.when(() -> Files.createDirectories(outputPath)).thenThrow(testException);

            /* 検証の実施 */
            final KmgToolMsgException exception = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                testTarget.createOutputFileDirectories();

            }, "IOExceptionが発生した場合、KmgToolMsgExceptionがスローされること");

            // TODO KenichiroArai 2025/06/07 例外処理の書き方を共通方式にする。
            // 例外の詳細検証
            Assertions.assertTrue(exception.getMessage().contains("KMGTOOL_GEN13009"),
                "例外メッセージにKMGTOOL_GEN13009が含まれること");
            Assertions.assertInstanceOf(IOException.class, exception.getCause(), "例外の原因がIOExceptionであること");
            Assertions.assertEquals("Disk full", exception.getCause().getMessage(), "原因となったIOExceptionのメッセージが正しいこと");

            // モックの呼び出し確認
            mockedFiles.verify(() -> Files.createDirectories(outputPath), Mockito.times(1));

        }

    }

    /**
     * createOutputFileDirectories メソッドのテスト - 異常系:権限不足によるIOException発生時のテスト
     * <p>
     * ディレクトリ作成時に権限不足によるIOExceptionが発生した場合の例外処理を確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     */
    @Test
    public void testCreateOutputFileDirectories_errorPermissionDenied(@TempDir final Path tempDir) {

        /* 準備 */
        final Path                         outputPath = tempDir.resolve("restricted").toAbsolutePath();
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // 初期化
        final Sheet               testSheet    = this.createTestSheet();
        final Map<String, String> testSqlIdMap = new HashMap<>();
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, outputPath);

        /* テスト実行 */
        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {

            // 権限不足のIOExceptionをスロー
            final IOException testException = new IOException("Access denied");
            mockedFiles.when(() -> Files.createDirectories(outputPath)).thenThrow(testException);

            /* 検証の実施 */
            final KmgToolMsgException exception = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                testTarget.createOutputFileDirectories();

            }, "権限不足のIOExceptionが発生した場合、KmgToolMsgExceptionがスローされること");

            // 例外の詳細検証
            Assertions.assertTrue(exception.getMessage().contains("KMGTOOL_GEN13009"),
                "例外メッセージにKMGTOOL_GEN13009が含まれること");
            Assertions.assertEquals("Access denied", exception.getCause().getMessage(), "権限不足のメッセージが保持されること");

            // モックの呼び出し確認
            mockedFiles.verify(() -> Files.createDirectories(outputPath), Mockito.times(1));

        }

    }

    /**
     * createOutputFileDirectories メソッドのテスト - 正常系:出力ディレクトリが正しく作成されることの確認
     * <p>
     * 指定されたパスに出力ディレクトリが正しく作成されることを確認します。 Mockitoを使用してFiles.createDirectoriesの呼び出しを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     */
    @Test
    public void testCreateOutputFileDirectories_normalDirectoryCreationSuccess(@TempDir final Path tempDir) {

        /* 準備 */
        final Path                         outputPath = tempDir.resolve("output").toAbsolutePath();
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // 初期化
        final Sheet               testSheet    = this.createTestSheet();
        final Map<String, String> testSqlIdMap = new HashMap<>();
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, outputPath);

        /* テスト実行 */
        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {

            // Files.createDirectoriesが正常に実行されるように設定
            mockedFiles.when(() -> Files.createDirectories(outputPath)).thenReturn(outputPath);

            /* 検証の実施 */
            Assertions.assertDoesNotThrow(() -> testTarget.createOutputFileDirectories(), "正常系では例外がスローされないこと");

            // モックの呼び出し確認
            mockedFiles.verify(() -> Files.createDirectories(outputPath), Mockito.times(1));

        }

    }

    /**
     * createOutputFileDirectories メソッドのテスト - 準正常系:既存ディレクトリが存在する場合でも成功することの確認
     * <p>
     * 既に存在するディレクトリに対しても処理が正常に完了することを確認します。 Mockitoを使用してFiles.createDirectoriesの動作をシミュレートします。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     */
    @Test
    public void testCreateOutputFileDirectories_normalExistingDirectoryHandled(@TempDir final Path tempDir) {

        /* 準備 */
        final Path                         outputPath = tempDir.resolve("existing").toAbsolutePath();
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // 初期化
        final Sheet               testSheet    = this.createTestSheet();
        final Map<String, String> testSqlIdMap = new HashMap<>();
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, outputPath);

        /* テスト実行 */
        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {

            // 既存ディレクトリの場合、Files.createDirectoriesは正常に完了
            mockedFiles.when(() -> Files.createDirectories(outputPath)).thenReturn(outputPath);

            /* 検証の実施 */
            Assertions.assertDoesNotThrow(() -> testTarget.createOutputFileDirectories(),
                "既存ディレクトリが存在する場合でも正常に処理されること");

            // モックの呼び出し確認
            mockedFiles.verify(() -> Files.createDirectories(outputPath), Mockito.times(1));

        }

    }

    /**
     * getCharset メソッドのテスト - 正常系:キャッシュされた文字セットが返されることの確認
     * <p>
     * 既にキャッシュされた文字セットが返されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetCharset_normalCachedCharsetReturned() throws Exception {

        /* 期待値の定義 */
        final Charset expectedCharset = KmgCharsetTypes.MS932.toCharset();

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // 初期化
        final Sheet               testSheet      = this.createTestSheet();
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        // リフレクションを使ってcharsetフィールドに直接値を設定
        final KmgReflectionModelImpl testReflection = new KmgReflectionModelImpl(testTarget);
        testReflection.set("charset", expectedCharset);

        /* テスト対象の実行 */
        final Charset testResult = testTarget.getCharset();

        /* 検証の準備 */
        final Charset actualCharset = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedCharset, actualCharset, "キャッシュされた文字セットが返されること");

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
     * getCharset メソッドのテスト - 正常系:OracleでUTF8が返されることの確認
     * <p>
     * Oracleの場合にUTF8文字セットが返されることを確認します。
     * </p>
     */
    @Test
    public void testGetCharset_normalOracleReturnsUtf8() {

        /* 期待値の定義 */
        final Charset expectedCharset = KmgCharsetTypes.UTF8.toCharset();

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // 初期化
        final Sheet               testSheet      = this.createTestSheet();
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.ORACLE, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final Charset testResult = testTarget.getCharset();

        /* 検証の準備 */
        final Charset actualCharset = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedCharset, actualCharset, "Oracleの場合はUTF8文字セットが返されること");

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
     * getCharset メソッドのテスト - 正常系:SQLServerでUTF8が返されることの確認
     * <p>
     * SQLServerの場合にUTF8文字セットが返されることを確認します。
     * </p>
     */
    @Test
    public void testGetCharset_normalSqlServerReturnsUtf8() {

        /* 期待値の定義 */
        final Charset expectedCharset = KmgCharsetTypes.UTF8.toCharset();

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // 初期化
        final Sheet               testSheet      = this.createTestSheet();
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.SQL_SERVER, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final Charset testResult = testTarget.getCharset();

        /* 検証の準備 */
        final Charset actualCharset = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedCharset, actualCharset, "SQLServerの場合はUTF8文字セットが返されること");

    }

    /**
     * getCharset メソッドのテスト - 準正常系:NONEでnullが返されることの確認
     * <p>
     * NONEの場合にnullが返されることを確認します。
     * </p>
     */
    @Test
    public void testGetCharset_semiNoneReturnsNull() {

        /* 期待値の定義 */
        final Charset expectedCharset = null;

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // 初期化
        final Sheet               testSheet      = this.createTestSheet();
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.NONE, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final Charset testResult = testTarget.getCharset();

        /* 検証の準備 */
        final Charset actualCharset = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedCharset, actualCharset, "NONEの場合はnullが返されること");

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
     * getDeleteComment メソッドのテスト - 正常系:キャッシュされた削除コメントが返されることの確認
     * <p>
     * 既にキャッシュされた削除コメントが返されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetDeleteComment_normalCachedDeleteCommentReturned() throws Exception {

        /* 期待値の定義 */
        final String expectedDeleteComment = "-- キャッシュされたコメント";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithName("テストテーブル");
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        // リフレクションを使ってdeleteCommentフィールドに直接値を設定
        final KmgReflectionModelImpl testReflection = new KmgReflectionModelImpl(testTarget);
        testReflection.set("deleteComment", expectedDeleteComment);

        /* テスト対象の実行 */
        final String testResult = testTarget.getDeleteComment();

        /* 検証の準備 */
        final String actualDeleteComment = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedDeleteComment, actualDeleteComment, "キャッシュされた削除コメントが返されること");

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
     * getDeleteSql メソッドのテスト - 正常系:キャッシュされた削除SQLが返されることの確認
     * <p>
     * 既にキャッシュされた削除SQLが返されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetDeleteSql_normalCachedDeleteSqlReturned() throws Exception {

        /* 期待値の定義 */
        final String expectedDeleteSql = "DELETE FROM cached_table;";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithPhysicsName("test_table");
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        // リフレクションを使ってdeleteSqlフィールドに直接値を設定
        final KmgReflectionModelImpl testReflection = new KmgReflectionModelImpl(testTarget);
        testReflection.set("deleteSql", expectedDeleteSql);

        /* テスト対象の実行 */
        final String testResult = testTarget.getDeleteSql();

        /* 検証の準備 */
        final String actualDeleteSql = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedDeleteSql, actualDeleteSql, "キャッシュされた削除SQLが返されること");

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
     * getInsertComment メソッドのテスト - 正常系:キャッシュされた挿入コメントが返されることの確認
     * <p>
     * 既にキャッシュされた挿入コメントが返されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetInsertComment_normalCachedInsertCommentReturned() throws Exception {

        /* 期待値の定義 */
        final String expectedInsertComment = "-- キャッシュされた挿入コメント";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithName("テストテーブル");
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        // リフレクションを使ってinsertCommentフィールドに直接値を設定
        final KmgReflectionModelImpl testReflection = new KmgReflectionModelImpl(testTarget);
        testReflection.set("insertComment", expectedInsertComment);

        /* テスト対象の実行 */
        final String testResult = testTarget.getInsertComment();

        /* 検証の準備 */
        final String actualInsertComment = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInsertComment, actualInsertComment, "キャッシュされた挿入コメントが返されること");

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
     * getInsertSql メソッドのテスト - 正常系:空データを含む挿入SQLが正しく生成されることの確認
     * <p>
     * 空データを含むデータ行から挿入SQLが正しく生成されることを確認します。
     * </p>
     */
    @Test
    public void testGetInsertSql_normalInsertSqlWithEmptyData() {

        /* 期待値の定義 */
        final String expectedInsertSql = "INSERT INTO test_table (id,name,value) VALUES ('1.0',null,null);";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成（データ型と空データを含む）
        final Sheet testSheet = this.createTestSheetWithEmptyData();

        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        // テストデータ行を作成
        final Row testDataRow = testSheet.getRow(4); // 5行目（インデックス4）にデータが設定されている

        /* テスト対象の実行 */
        final String testResult = testTarget.getInsertSql(testDataRow);

        /* 検証の準備 */
        final String actualInsertSql = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInsertSql, actualInsertSql, "空データを含む挿入SQLが正しく生成されること");

    }

    /**
     * getInsertSql メソッドのテスト - 正常系:PostgreSQL向けの挿入SQLが正しく生成されることの確認
     * <p>
     * データ行から挿入SQLが正しく生成されることを確認します。
     * </p>
     */
    @Test
    public void testGetInsertSql_normalPostgreSqlInsertSqlGenerated() {

        /* 期待値の定義 */
        final String expectedInsertSql = "INSERT INTO test_table (id,name,value) VALUES ('1.0','test','123.45');";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成（データ型と実際のデータを含む）
        final Sheet testSheet = this.createTestSheetWithData();

        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        // テストデータ行を作成
        final Row testDataRow = testSheet.getRow(4); // 5行目（インデックス4）にデータが設定されている

        /* テスト対象の実行 */
        final String testResult = testTarget.getInsertSql(testDataRow);

        /* 検証の準備 */
        final String actualInsertSql = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInsertSql, actualInsertSql, "PostgreSQL向けの挿入SQLが正しく生成されること");

    }

    /**
     * getInsertSql メソッドのテスト - 準正常系:NONEデータベース種別で処理されることの確認
     * <p>
     * NONE DBタイプの場合の処理を確認します。
     * </p>
     */
    @Test
    public void testGetInsertSql_semiNoneDbTypeProcessed() {

        /* 期待値の定義 */
        final String expectedInsertSql = "INSERT INTO test_table (id,name,value) VALUES (null,null,null);";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet testSheet = this.createTestSheetWithData();

        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.NONE, testSheet, testSqlIdMap, testOutputPath);

        // テストデータ行を作成
        final Row testDataRow = testSheet.getRow(4); // 5行目（インデックス4）にデータが設定されている

        /* テスト対象の実行 */
        final String testResult = testTarget.getInsertSql(testDataRow);

        /* 検証の準備 */
        final String actualInsertSql = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInsertSql, actualInsertSql, "NONE DBタイプの場合は処理されずnullで埋められること");

    }

    /**
     * getInsertSql メソッドのテスト - 準正常系:MySQLデータベース種別で処理されることの確認
     * <p>
     * MySQL DBタイプの場合の処理を確認します。
     * </p>
     */
    @Test
    public void testGetInsertSql_semiMySqlDbTypeProcessed() {

        /* 期待値の定義 */
        final String expectedInsertSql = "INSERT INTO test_table (id,name,value) VALUES (null,null,null);";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet testSheet = this.createTestSheetWithData();

        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.MYSQL, testSheet, testSqlIdMap, testOutputPath);

        // テストデータ行を作成
        final Row testDataRow = testSheet.getRow(4); // 5行目（インデックス4）にデータが設定されている

        /* テスト対象の実行 */
        final String testResult = testTarget.getInsertSql(testDataRow);

        /* 検証の準備 */
        final String actualInsertSql = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInsertSql, actualInsertSql, "MySQL DBタイプの場合は処理されずnullで埋められること");

    }

    /**
     * getInsertSql メソッドのテスト - 準正常系:Oracleデータベース種別で処理されることの確認
     * <p>
     * Oracle DBタイプの場合の処理を確認します。
     * </p>
     */
    @Test
    public void testGetInsertSql_semiOracleDbTypeProcessed() {

        /* 期待値の定義 */
        final String expectedInsertSql = "INSERT INTO test_table (id,name,value) VALUES (null,null,null);";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet testSheet = this.createTestSheetWithData();

        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.ORACLE, testSheet, testSqlIdMap, testOutputPath);

        // テストデータ行を作成
        final Row testDataRow = testSheet.getRow(4); // 5行目（インデックス4）にデータが設定されている

        /* テスト対象の実行 */
        final String testResult = testTarget.getInsertSql(testDataRow);

        /* 検証の準備 */
        final String actualInsertSql = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInsertSql, actualInsertSql, "Oracle DBタイプの場合は処理されずnullで埋められること");

    }

    /**
     * getInsertSql メソッドのテスト - 準正常系:SQL Serverデータベース種別で処理されることの確認
     * <p>
     * SQL Server DBタイプの場合の処理を確認します。
     * </p>
     */
    @Test
    public void testGetInsertSql_semiSqlServerDbTypeProcessed() {

        /* 期待値の定義 */
        final String expectedInsertSql = "INSERT INTO test_table (id,name,value) VALUES (null,null,null);";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet testSheet = this.createTestSheetWithData();

        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.SQL_SERVER, testSheet, testSqlIdMap, testOutputPath);

        // テストデータ行を作成
        final Row testDataRow = testSheet.getRow(4); // 5行目（インデックス4）にデータが設定されている

        /* テスト対象の実行 */
        final String testResult = testTarget.getInsertSql(testDataRow);

        /* 検証の準備 */
        final String actualInsertSql = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInsertSql, actualInsertSql, "SQL Server DBタイプの場合は処理されずnullで埋められること");

    }

    /**
     * getKmgDbDataTypeList メソッドのテスト - 正常系:キャッシュされたデータ型リストが返されることの確認
     * <p>
     * 既にキャッシュされたデータ型リストが返されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetKmgDbDataTypeList_normalCachedDataTypeListReturned() throws Exception {

        /* 期待値の定義 */
        final List<KmgDbDataTypeTypes> expectedDataTypeList
            = List.of(KmgDbDataTypeTypes.INTEGER, KmgDbDataTypeTypes.STRING);

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithDataTypes();
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        // リフレクションを使ってdbDataTypeListフィールドに直接値を設定
        final KmgReflectionModelImpl testReflection = new KmgReflectionModelImpl(testTarget);
        testReflection.set("dbDataTypeList", expectedDataTypeList);

        /* テスト対象の実行 */
        final List<KmgDbDataTypeTypes> testResult = testTarget.getKmgDbDataTypeList();

        /* 検証の準備 */
        final List<KmgDbDataTypeTypes> actualDataTypeList = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedDataTypeList, actualDataTypeList, "キャッシュされたデータ型リストが返されること");

    }

    /**
     * getKmgDbDataTypeList メソッドのテスト - 正常系:データ型リストが正しく取得されることの確認
     * <p>
     * シートからデータ型のリストが正しく取得されることを確認します。適切なキーを使用して実際のデータ型を確認します。
     * </p>
     */
    @Test
    public void testGetKmgDbDataTypeList_normalCorrectDataTypeList() {

        /* 期待値の定義 */
        // 適切なキーを使用して実際のデータ型が取得されることを確認
        final KmgDbDataTypeTypes[] expectedDataTypes = {
            KmgDbDataTypeTypes.INTEGER, KmgDbDataTypeTypes.STRING, KmgDbDataTypeTypes.DOUBLE
        };

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成（正しいキーを使用）
        final Sheet testSheet = this.createTestSheetWithCorrectDataTypes();

        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        /* テスト対象の実行 */
        final List<KmgDbDataTypeTypes> testResult = testTarget.getKmgDbDataTypeList();

        /* 検証の準備 */
        final KmgDbDataTypeTypes[] actualDataTypes = testResult.toArray(new KmgDbDataTypeTypes[0]);

        // enumのインスタンス比較ではなくgetKey()で比較
        final String[] expectedKeys = new String[expectedDataTypes.length];
        final String[] actualKeys   = new String[actualDataTypes.length];

        for (int i = 0; i < expectedDataTypes.length; i++) {

            expectedKeys[i] = expectedDataTypes[i].getKey();
            actualKeys[i] = actualDataTypes[i].getKey();

        }
        Assertions.assertArrayEquals(expectedKeys, actualKeys, "データ型リストが正しく取得されること");

    }

    /**
     * getOutputFilePath メソッドのテスト - 正常系:キャッシュされた出力ファイルパスが返されることの確認
     * <p>
     * 既にキャッシュされた出力ファイルパスが返されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetOutputFilePath_normalCachedOutputFilePathReturned() throws Exception {

        /* 期待値の定義 */
        final Path expectedOutputFilePath = Paths.get("cached_output_file.sql");

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet    = this.createTestSheetWithPhysicsName("test_table");
        final Map<String, String> testSqlIdMap = new HashMap<>();
        testSqlIdMap.put("test_table", "SQL001");
        final Path testOutputPath = Paths.get("output");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        // リフレクションを使ってoutputFilePathフィールドに直接値を設定
        final KmgReflectionModelImpl testReflection = new KmgReflectionModelImpl(testTarget);
        testReflection.set("outputFilePath", expectedOutputFilePath);

        /* テスト対象の実行 */
        final Path testResult = testTarget.getOutputFilePath();

        /* 検証の準備 */
        final Path actualOutputFilePath = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedOutputFilePath, actualOutputFilePath, "キャッシュされた出力ファイルパスが返されること");

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
     * getSqlId メソッドのテスト - 正常系:キャッシュされたSQLIDが返されることの確認
     * <p>
     * 既にキャッシュされたSQLIDが返されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetSqlId_normalCachedSqlIdReturned() throws Exception {

        /* 期待値の定義 */
        final String expectedSqlId = "CACHED_SQL_ID";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet    = this.createTestSheetWithPhysicsName("test_table");
        final Map<String, String> testSqlIdMap = new HashMap<>();
        testSqlIdMap.put("test_table", "SQL001");
        final Path testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        // リフレクションを使ってsqlIdフィールドに直接値を設定
        final KmgReflectionModelImpl testReflection = new KmgReflectionModelImpl(testTarget);
        testReflection.set("sqlId", expectedSqlId);

        /* テスト対象の実行 */
        final String testResult = testTarget.getSqlId();

        /* 検証の準備 */
        final String actualSqlId = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSqlId, actualSqlId, "キャッシュされたSQLIDが返されること");

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
     * getTableLogicName メソッドのテスト - 正常系:キャッシュされたテーブル論理名が返されることの確認
     * <p>
     * 既にキャッシュされたテーブル論理名が返されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetTableLogicName_normalCachedTableLogicNameReturned() throws Exception {

        /* 期待値の定義 */
        final String expectedTableLogicName = "キャッシュされたテーブル論理名";

        /* 準備 */
        final IsDataSheetCreationLogicImpl testTarget = new IsDataSheetCreationLogicImpl();

        // テストシートの作成
        final Sheet               testSheet      = this.createTestSheetWithName("テストテーブル");
        final Map<String, String> testSqlIdMap   = new HashMap<>();
        final Path                testOutputPath = Paths.get("test");
        testTarget.initialize(KmgDbTypes.POSTGRE_SQL, testSheet, testSqlIdMap, testOutputPath);

        // リフレクションを使ってtableLogicNameフィールドに直接値を設定
        final KmgReflectionModelImpl testReflection = new KmgReflectionModelImpl(testTarget);
        testReflection.set("tableLogicName", expectedTableLogicName);

        /* テスト対象の実行 */
        final String testResult = testTarget.getTableLogicName();

        /* 検証の準備 */
        final String actualTableLogicName = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTableLogicName, actualTableLogicName, "キャッシュされたテーブル論理名が返されること");

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

        final Sheet result;

        try (final Workbook workbook = new XSSFWorkbook()) {

            result = workbook.createSheet("テストシート");

            // 1行目にテーブル物理名を設定
            final Row  row0    = result.createRow(0);
            final Cell cell0_0 = row0.createCell(0);
            cell0_0.setCellValue("test_table");

            return result;

        } catch (final Exception e) {

            throw new RuntimeException("テスト用シートの作成に失敗しました", e);

        }

    }

    /**
     * カラム情報を持つテスト用シートを作成する<br>
     *
     * @return テスト用シート
     */
    private Sheet createTestSheetWithColumns() {

        final Sheet result;

        try (final Workbook workbook = new XSSFWorkbook()) {

            result = workbook.createSheet("テストシート");

            // 1行目にテーブル物理名を設定
            final Row  row0    = result.createRow(0);
            final Cell cell0_0 = row0.createCell(0);
            cell0_0.setCellValue("test_table");

            // 3行目（インデックス2）にカラム物理名を設定
            final Row  row2    = result.createRow(2);
            final Cell cell2_0 = row2.createCell(0);
            cell2_0.setCellValue("id");
            final Cell cell2_1 = row2.createCell(1);
            cell2_1.setCellValue("name");
            final Cell cell2_2 = row2.createCell(2);
            cell2_2.setCellValue("value");

            return result;

        } catch (final Exception e) {

            throw new RuntimeException("テスト用シートの作成に失敗しました", e);

        }

    }

    /**
     * データ型行にKmgDbDataTypeTypesの正しいキーを設定したテスト用シートを作成する<br>
     *
     * @return テスト用シート
     */
    private Sheet createTestSheetWithCorrectDataTypes() {

        final Sheet result;

        try (final Workbook workbook = new XSSFWorkbook()) {

            result = workbook.createSheet("テストシート");

            // 1行目にテーブル物理名を設定
            final Row  row0    = result.createRow(0);
            final Cell cell0_0 = row0.createCell(0);
            cell0_0.setCellValue("test_table");

            // 3行目（インデックス2）にカラム物理名を設定
            final Row  row2    = result.createRow(2);
            final Cell cell2_0 = row2.createCell(0);
            cell2_0.setCellValue("id");
            final Cell cell2_1 = row2.createCell(1);
            cell2_1.setCellValue("name");
            final Cell cell2_2 = row2.createCell(2);
            cell2_2.setCellValue("value");

            // 4行目（インデックス3）にKmgDbDataTypeTypesの正しいキーを設定
            final Row  row3    = result.createRow(3);
            final Cell cell3_0 = row3.createCell(0);
            cell3_0.setCellValue(KmgDbDataTypeTypes.INTEGER.getKey()); // 例: "4バイト整数"
            final Cell cell3_1 = row3.createCell(1);
            cell3_1.setCellValue(KmgDbDataTypeTypes.STRING.getKey()); // 例: "文字列型"
            final Cell cell3_2 = row3.createCell(2);
            cell3_2.setCellValue(KmgDbDataTypeTypes.DOUBLE.getKey()); // 例: "8バイト実数"

            // 5行目（インデックス4）にテストデータを設定
            final Row  row4    = result.createRow(4);
            final Cell cell4_0 = row4.createCell(0);
            cell4_0.setCellValue(1);
            final Cell cell4_1 = row4.createCell(1);
            cell4_1.setCellValue("test");
            final Cell cell4_2 = row4.createCell(2);
            cell4_2.setCellValue(123.45);

            return result;

        } catch (final Exception e) {

            throw new RuntimeException("テスト用シートの作成に失敗しました", e);

        }

    }

    /**
     * データを含むテスト用シートを作成する<br>
     *
     * @return テスト用シート
     */
    private Sheet createTestSheetWithData() {

        final Sheet result;

        try (final Workbook workbook = new XSSFWorkbook()) {

            result = workbook.createSheet("テストシート");

            // 1行目にテーブル物理名を設定
            final Row  row0    = result.createRow(0);
            final Cell cell0_0 = row0.createCell(0);
            cell0_0.setCellValue("test_table");

            // 3行目（インデックス2）にカラム物理名を設定
            final Row  row2    = result.createRow(2);
            final Cell cell2_0 = row2.createCell(0);
            cell2_0.setCellValue("id");
            final Cell cell2_1 = row2.createCell(1);
            cell2_1.setCellValue("name");
            final Cell cell2_2 = row2.createCell(2);
            cell2_2.setCellValue("value");

            // 4行目（インデックス3）にデータ型を設定
            final Row  row3    = result.createRow(3);
            final Cell cell3_0 = row3.createCell(0);
            cell3_0.setCellValue("INTEGER");
            final Cell cell3_1 = row3.createCell(1);
            cell3_1.setCellValue("STRING");
            final Cell cell3_2 = row3.createCell(2);
            cell3_2.setCellValue("DOUBLE");

            // 5行目（インデックス4）にテストデータを設定
            final Row  row4    = result.createRow(4);
            final Cell cell4_0 = row4.createCell(0);
            cell4_0.setCellValue(1);
            final Cell cell4_1 = row4.createCell(1);
            cell4_1.setCellValue("test");
            final Cell cell4_2 = row4.createCell(2);
            cell4_2.setCellValue(123.45);

            return result;

        } catch (final Exception e) {

            throw new RuntimeException("テスト用シートの作成に失敗しました", e);

        }

    }

    /**
     * データ型情報を持つテスト用シートを作成する<br>
     *
     * @return テスト用シート
     */
    private Sheet createTestSheetWithDataTypes() {

        final Sheet result;

        try (final Workbook workbook = new XSSFWorkbook()) {

            result = workbook.createSheet("テストシート");

            // 1行目にテーブル物理名を設定
            final Row  row0    = result.createRow(0);
            final Cell cell0_0 = row0.createCell(0);
            cell0_0.setCellValue("test_table");

            // 3行目（インデックス2）にカラム物理名を設定
            final Row  row2    = result.createRow(2);
            final Cell cell2_0 = row2.createCell(0);
            cell2_0.setCellValue("id");
            final Cell cell2_1 = row2.createCell(1);
            cell2_1.setCellValue("name");
            final Cell cell2_2 = row2.createCell(2);
            cell2_2.setCellValue("value");

            // 4行目（インデックス3）にデータ型を設定
            final Row  row3    = result.createRow(3);
            final Cell cell3_0 = row3.createCell(0);
            cell3_0.setCellValue("INTEGER");
            final Cell cell3_1 = row3.createCell(1);
            cell3_1.setCellValue("STRING");
            final Cell cell3_2 = row3.createCell(2);
            cell3_2.setCellValue("DOUBLE");

            return result;

        } catch (final Exception e) {

            throw new RuntimeException("テスト用シートの作成に失敗しました", e);

        }

    }

    // TODO KenichiroArai 2025/06/07 コードを整理する
    /**
     * 空データを含むテスト用シートを作成する<br>
     *
     * @return テスト用シート
     */
    private Sheet createTestSheetWithEmptyData() {

        final Sheet result;

        try (final Workbook workbook = new XSSFWorkbook()) {

            result = workbook.createSheet("テストシート");

            // 1行目にテーブル物理名を設定
            final Row  row0    = result.createRow(0);
            final Cell cell0_0 = row0.createCell(0);
            cell0_0.setCellValue("test_table");

            // 3行目（インデックス2）にカラム物理名を設定
            final Row  row2    = result.createRow(2);
            final Cell cell2_0 = row2.createCell(0);
            cell2_0.setCellValue("id");
            final Cell cell2_1 = row2.createCell(1);
            cell2_1.setCellValue("name");
            final Cell cell2_2 = row2.createCell(2);
            cell2_2.setCellValue("value");

            // 4行目（インデックス3）にデータ型を設定
            final Row  row3    = result.createRow(3);
            final Cell cell3_0 = row3.createCell(0);
            cell3_0.setCellValue("INTEGER");
            final Cell cell3_1 = row3.createCell(1);
            cell3_1.setCellValue("STRING");
            final Cell cell3_2 = row3.createCell(2);
            cell3_2.setCellValue("DOUBLE");

            // 5行目（インデックス4）にテストデータを設定（一部のみ）
            final Row  row4    = result.createRow(4);
            final Cell cell4_0 = row4.createCell(0);
            cell4_0.setCellValue(1);
            // cell4_1とcell4_2は空のまま

            return result;

        } catch (final Exception e) {

            throw new RuntimeException("テスト用シートの作成に失敗しました", e);

        }

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

        final Sheet result;

        try (final Workbook workbook = new XSSFWorkbook()) {

            result = workbook.createSheet(sheetName);

            // 1行目にテーブル物理名を設定
            final Row  row0    = result.createRow(0);
            final Cell cell0_0 = row0.createCell(0);
            cell0_0.setCellValue("test_table");

            return result;

        } catch (final Exception e) {

            throw new RuntimeException("テスト用シートの作成に失敗しました", e);

        }

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

        final Sheet result;

        try (final Workbook workbook = new XSSFWorkbook()) {

            result = workbook.createSheet("テストシート");

            // 1行目にテーブル物理名を設定
            final Row  row0    = result.createRow(0);
            final Cell cell0_0 = row0.createCell(0);
            cell0_0.setCellValue(physicsName);

            return result;

        } catch (final Exception e) {

            throw new RuntimeException("テスト用シートの作成に失敗しました", e);

        }

    }
}
