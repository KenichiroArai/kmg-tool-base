package kmg.tool.is.impl;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.tool.is.application.logic.IsBasicInformationLogic;
import kmg.tool.is.application.logic.impl.IsBasicInformationLogicImpl;

/**
 * 挿入SQL基本情報ロジック実装のテスト<br>
 *
 * @author KenichiroArai
 */
@SuppressWarnings({
    "nls", "static-method"
})
public class IsBasicInformationLogicImplTest {

    /**
     * デフォルトコンストラクタ<br>
     */
    public IsBasicInformationLogicImplTest() {

        // 処理なし
    }

    /**
     * getKmgDbTypes メソッドのテスト - 正常系:MySQLの設定値が正しく取得されることの確認
     * <p>
     * 設定シートの指定セルからMySQLの設定値が正しく取得されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外が発生した場合
     */
    @Test
    public void testGetKmgDbTypes_normalMysqlValue() throws Exception {

        /* 期待値の定義 */
        final KmgDbTypes expectedDbTypes = KmgDbTypes.MYSQL;

        /* 準備 */
        final IsBasicInformationLogicImpl testTarget = new IsBasicInformationLogicImpl();

        try (Workbook testWorkbook = WorkbookFactory.create(true)) {

            final Sheet testSheet = testWorkbook.createSheet(IsBasicInformationLogic.SETTING_SHEET_NAME);
            final Row   testRow   = testSheet.createRow(0);
            final Cell  testCell  = testRow.createCell(1);
            testCell.setCellValue("MySQL");

            /* テスト対象の実行 */
            testTarget.initialize(testWorkbook);
            final KmgDbTypes testResult = testTarget.getKmgDbTypes();

            /* 検証の準備 */
            final KmgDbTypes actualDbTypes = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedDbTypes, actualDbTypes, "MySQLの設定値が正しく取得されること");

        }

    }

    /**
     * getKmgDbTypes メソッドのテスト - 正常系:PostgreSQLの設定値が正しく取得されることの確認
     * <p>
     * 設定シートの指定セルからPostgreSQLの設定値が正しく取得されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外が発生した場合
     */
    @Test
    public void testGetKmgDbTypes_normalPostgreSqlValue() throws Exception {

        /* 期待値の定義 */
        final KmgDbTypes expectedDbTypes = KmgDbTypes.POSTGRE_SQL;

        /* 準備 */
        final IsBasicInformationLogicImpl testTarget = new IsBasicInformationLogicImpl();

        try (Workbook testWorkbook = WorkbookFactory.create(true)) {

            final Sheet testSheet = testWorkbook.createSheet(IsBasicInformationLogic.SETTING_SHEET_NAME);
            final Row   testRow   = testSheet.createRow(0);
            final Cell  testCell  = testRow.createCell(1);
            testCell.setCellValue("PostgreSQL");

            /* テスト対象の実行 */
            testTarget.initialize(testWorkbook);
            final KmgDbTypes testResult = testTarget.getKmgDbTypes();

            /* 検証の準備 */
            final KmgDbTypes actualDbTypes = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedDbTypes, actualDbTypes, "PostgreSQLの設定値が正しく取得されること");

        }

    }

    /**
     * getKmgDbTypes メソッドのテスト - 準正常系:セルがnullの場合NONEが返されることの確認
     * <p>
     * 設定シートの指定セルがnullの場合に、NONEが返されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外が発生した場合
     */
    @Test
    public void testGetKmgDbTypes_semiNullCellReturnsNone() throws Exception {

        /* 期待値の定義 */
        final KmgDbTypes expectedDbTypes = KmgDbTypes.NONE;

        /* 準備 */
        final IsBasicInformationLogicImpl testTarget = new IsBasicInformationLogicImpl();

        try (Workbook testWorkbook = WorkbookFactory.create(true)) {

            final Sheet testSheet = testWorkbook.createSheet(IsBasicInformationLogic.SETTING_SHEET_NAME);
            testSheet.createRow(0); // セルは作成しない（null）

            /* テスト対象の実行 */
            testTarget.initialize(testWorkbook);
            final KmgDbTypes testResult = testTarget.getKmgDbTypes();

            /* 検証の準備 */
            final KmgDbTypes actualDbTypes = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedDbTypes, actualDbTypes, "セルがnullの場合はNONEが返されること");

        }

    }

    /**
     * getKmgDbTypes メソッドのテスト - 準正常系:存在しないDB種類の場合NONEが返されることの確認
     * <p>
     * 設定シートに存在しないDB種類が記載されている場合に、NONEが返されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外が発生した場合
     */
    @Test
    public void testGetKmgDbTypes_semiUnknownDbTypeReturnsNone() throws Exception {

        /* 期待値の定義 */
        final KmgDbTypes expectedDbTypes = KmgDbTypes.NONE;

        /* 準備 */
        final IsBasicInformationLogicImpl testTarget = new IsBasicInformationLogicImpl();

        try (Workbook testWorkbook = WorkbookFactory.create(true)) {

            final Sheet testSheet = testWorkbook.createSheet(IsBasicInformationLogic.SETTING_SHEET_NAME);
            final Row   testRow   = testSheet.createRow(0);
            final Cell  testCell  = testRow.createCell(1);
            testCell.setCellValue("UnknownDB");

            /* テスト対象の実行 */
            testTarget.initialize(testWorkbook);
            final KmgDbTypes testResult = testTarget.getKmgDbTypes();

            /* 検証の準備 */
            final KmgDbTypes actualDbTypes = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedDbTypes, actualDbTypes, "存在しないDB種類の場合はNONEが返されること");

        }

    }

    /**
     * getSqlIdMap メソッドのテスト - 正常系:空のシートで空のマップが返されることの確認
     * <p>
     * データ行が存在しない一覧シートの場合に、空のマップが返されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外が発生した場合
     */
    @Test
    public void testGetSqlIdMap_normalEmptySheetReturnsEmptyMap() throws Exception {

        /* 期待値の定義 */
        final int expectedMapSize = 0;

        /* 準備 */
        final IsBasicInformationLogicImpl testTarget = new IsBasicInformationLogicImpl();

        try (Workbook testWorkbook = WorkbookFactory.create(true)) {

            testWorkbook.createSheet(IsBasicInformationLogic.LIST_NAME);
            // データ行は作成しない

            /* テスト対象の実行 */
            testTarget.initialize(testWorkbook);
            final Map<String, String> testResult = testTarget.getSqlIdMap();

            /* 検証の準備 */
            final int actualMapSize = testResult.size();

            /* 検証の実施 */
            Assertions.assertEquals(expectedMapSize, actualMapSize, "空のシートの場合は空のマップが返されること");

        }

    }

    /**
     * getSqlIdMap メソッドのテスト - 正常系:複数のテーブル情報が正しくマップされることの確認
     * <p>
     * 一覧シートから複数のテーブル物理名とSQLIDの組み合わせが正しくマップされることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外が発生した場合
     */
    @Test
    public void testGetSqlIdMap_normalMultipleTableMapping() throws Exception {

        /* 期待値の定義 */
        final int    expectedMapSize = 2;
        final String expectedSqlId1  = "SQL001";
        final String expectedSqlId2  = "SQL002";

        /* 準備 */
        final IsBasicInformationLogicImpl testTarget = new IsBasicInformationLogicImpl();

        try (Workbook testWorkbook = WorkbookFactory.create(true)) {

            final Sheet testSheet = testWorkbook.createSheet(IsBasicInformationLogic.LIST_NAME);

            // ヘッダー行（行番号0）
            testSheet.createRow(0);

            // データ行1（行番号1）
            final Row dataRow1 = testSheet.createRow(1);
            dataRow1.createCell(2).setCellValue("table1"); // テーブル物理名
            dataRow1.createCell(3).setCellValue("SQL001"); // SQLID

            // データ行2（行番号2）
            final Row dataRow2 = testSheet.createRow(2);
            dataRow2.createCell(2).setCellValue("table2"); // テーブル物理名
            dataRow2.createCell(3).setCellValue("SQL002"); // SQLID

            /* テスト対象の実行 */
            testTarget.initialize(testWorkbook);
            final Map<String, String> testResult = testTarget.getSqlIdMap();

            /* 検証の準備 */
            final int    actualMapSize = testResult.size();
            final String actualSqlId1  = testResult.get("table1");
            final String actualSqlId2  = testResult.get("table2");

            /* 検証の実施 */
            Assertions.assertEquals(expectedMapSize, actualMapSize, "マップのサイズが正しいこと");
            Assertions.assertEquals(expectedSqlId1, actualSqlId1, "table1のSQLIDが正しく取得されること");
            Assertions.assertEquals(expectedSqlId2, actualSqlId2, "table2のSQLIDが正しく取得されること");

        }

    }

    /**
     * getSqlIdMap メソッドのテスト - 正常系:nullセルが含まれる行が適切に処理されることの確認
     * <p>
     * テーブル物理名またはSQLIDセルがnullの行が存在する場合に、nullを含む情報が適切にマップされることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外が発生した場合
     */
    @Test
    public void testGetSqlIdMap_normalNullCellsHandledCorrectly() throws Exception {

        /* 期待値の定義 */
        final int expectedMapSize = 2;

        /* 準備 */
        final IsBasicInformationLogicImpl testTarget = new IsBasicInformationLogicImpl();

        try (Workbook testWorkbook = WorkbookFactory.create(true)) {

            final Sheet testSheet = testWorkbook.createSheet(IsBasicInformationLogic.LIST_NAME);

            // データ行1（行番号1）- テーブル物理名がnull
            final Row dataRow1 = testSheet.createRow(1);
            // セル2は作成しない（null）
            dataRow1.createCell(3).setCellValue("SQL001"); // SQLID

            // データ行2（行番号2）- SQLIDがnull
            final Row dataRow2 = testSheet.createRow(2);
            dataRow2.createCell(2).setCellValue("table2"); // テーブル物理名
            // セル3は作成しない（null）

            /* テスト対象の実行 */
            testTarget.initialize(testWorkbook);
            final Map<String, String> testResult = testTarget.getSqlIdMap();

            /* 検証の準備 */
            final int actualMapSize = testResult.size();

            /* 検証の実施 */
            Assertions.assertEquals(expectedMapSize, actualMapSize, "nullセルを含む行も適切に処理されること");
            Assertions.assertTrue(testResult.containsKey(null), "テーブル物理名がnullのエントリが存在すること");
            Assertions.assertTrue(testResult.containsValue(null), "SQLIDがnullのエントリが存在すること");

        }

    }

    /**
     * initialize メソッドのテスト - 正常系:nullワークブックでも例外が発生しないことの確認
     * <p>
     * nullワークブックで初期化した場合でも例外が発生しないことを確認します。 ただし、その後のメソッド呼び出しではNullPointerExceptionが発生することが期待されます。
     * </p>
     */
    @Test
    public void testInitialize_normalNullWorkbookNoException() {

        /* 期待値の定義 */
        // 例外が発生しないことを確認

        /* 準備 */
        final IsBasicInformationLogicImpl testTarget = new IsBasicInformationLogicImpl();

        /* テスト対象の実行 */
        testTarget.initialize(null);

        /* 検証の準備 */
        // 準備なし（例外が発生しないことを検証）

        /* 検証の実施 */
        // initialize自体は例外を投げないが、後続のメソッド呼び出しでNullPointerExceptionが発生することを確認
        Assertions.assertThrows(NullPointerException.class, () -> {

            testTarget.getKmgDbTypes();

        }, "nullワークブック使用時は後続メソッドでNullPointerExceptionが発生すること");

    }

    /**
     * initialize メソッドのテスト - 正常系:ワークブックが正しく設定されることの確認
     * <p>
     * 引数で指定されたワークブックが正しく内部フィールドに設定されることを確認します。
     * </p>
     *
     * @throws Exception
     *                   例外が発生した場合
     */
    @Test
    public void testInitialize_normalWorkbookSetCorrectly() throws Exception {

        /* 期待値の定義 */
        final KmgDbTypes expectedDbTypes = KmgDbTypes.ORACLE;

        /* 準備 */
        final IsBasicInformationLogicImpl testTarget = new IsBasicInformationLogicImpl();

        try (Workbook testWorkbook = WorkbookFactory.create(true)) {

            final Sheet testSheet = testWorkbook.createSheet(IsBasicInformationLogic.SETTING_SHEET_NAME);
            final Row   testRow   = testSheet.createRow(0);
            final Cell  testCell  = testRow.createCell(1);
            testCell.setCellValue("Oracle");

            /* テスト対象の実行 */
            testTarget.initialize(testWorkbook);

            // 初期化が正しく動作しているかを、他のメソッドの動作で検証
            final KmgDbTypes testResult = testTarget.getKmgDbTypes();

            /* 検証の準備 */
            final KmgDbTypes actualDbTypes = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedDbTypes, actualDbTypes, "ワークブックが正しく設定され、動作すること");

        }

    }

}
