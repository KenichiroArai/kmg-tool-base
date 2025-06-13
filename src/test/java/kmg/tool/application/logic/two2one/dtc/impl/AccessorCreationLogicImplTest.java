package kmg.tool.application.logic.two2one.dtc.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;

/**
 * アクセサ作成ロジック実装テスト
 *
 * @author KenichiroArai
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls",
})
public class AccessorCreationLogicImplTest extends AbstractKmgTest {

    /** テンポラリディレクトリ */
    @TempDir
    Path tempDir;

    /** テスト対象 */
    private AccessorCreationLogicImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * セットアップ
     *
     * @throws Exception
     *                   例外
     */
    @SuppressWarnings("resource")
    @BeforeEach
    public void setUp() throws Exception {

        this.testTarget = new AccessorCreationLogicImpl();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

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
     * addItemToCsvRows メソッドのテスト - 異常系：項目名がnullの場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddItemToCsvRows_errorItemNull() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN32001] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN32001;

        /* 準備 */
        this.reflectionModel.set("item", null);

        /* テスト対象の実行 */
        final KmgToolMsgException actualException
            = Assertions.assertThrows(KmgToolMsgException.class, () -> this.testTarget.addItemToCsvRows());

        /* 検証の実施 */
        this.verifyKmgMsgException(actualException, KmgToolMsgException.class, expectedDomainMessage,
            expectedMessageTypes);

    }

    /**
     * addItemToCsvRows メソッドのテスト - 正常系：項目名が設定されている場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddItemToCsvRows_normalItemSet() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("item", "testItem");
        this.testTarget.addOneLineOfDataToCsvRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addItemToCsvRows();

        /* 検証の準備 */
        final boolean            actualResult  = testResult;
        final List<List<String>> actualCsvRows = this.testTarget.getCsvRows();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(1, actualCsvRows.size(), "CSVの行数が正しいこと");
        Assertions.assertEquals(1, actualCsvRows.get(0).size(), "CSVの列数が正しいこと");
        Assertions.assertEquals("testItem", actualCsvRows.get(0).get(0), "項目名が正しく追加されていること");

    }

    /**
     * addJavadocCommentToCsvRows メソッドのテスト - 異常系：Javadocコメントがnullの場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddJavadocCommentToCsvRows_errorJavadocNull() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN32002] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN32002;

        /* 準備 */
        this.reflectionModel.set("javadocComment", null);

        /* テスト対象の実行 */
        final KmgToolMsgException actualException
            = Assertions.assertThrows(KmgToolMsgException.class, () -> this.testTarget.addJavadocCommentToCsvRows());

        /* 検証の実施 */
        this.verifyKmgMsgException(actualException, KmgToolMsgException.class, expectedDomainMessage,
            expectedMessageTypes);

    }

    /**
     * addJavadocCommentToCsvRows メソッドのテスト - 正常系：Javadocコメントが設定されている場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddJavadocCommentToCsvRows_normalJavadocSet() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("javadocComment", "テストコメント");
        this.testTarget.addOneLineOfDataToCsvRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addJavadocCommentToCsvRows();

        /* 検証の準備 */
        final boolean            actualResult  = testResult;
        final List<List<String>> actualCsvRows = this.testTarget.getCsvRows();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(1, actualCsvRows.size(), "CSVの行数が正しいこと");
        Assertions.assertEquals(1, actualCsvRows.get(0).size(), "CSVの列数が正しいこと");
        Assertions.assertEquals("テストコメント", actualCsvRows.get(0).get(0), "Javadocコメントが正しく追加されていること");

    }

    /**
     * addOneLineOfDataToCsvRows メソッドのテスト - 正常系：CSVに新しい行を追加する
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddOneLineOfDataToCsvRows_normalAddNewRow() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addOneLineOfDataToCsvRows();

        /* 検証の準備 */
        final boolean            actualResult  = testResult;
        final List<List<String>> actualCsvRows = this.testTarget.getCsvRows();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(1, actualCsvRows.size(), "新しい行が追加されていること");
        Assertions.assertEquals(0, actualCsvRows.get(0).size(), "新しい行は空であること");

    }

    /**
     * addTypeToCsvRows メソッドのテスト - 異常系：型がnullの場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddTypeToCsvRows_errorTypeNull() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN32003] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN32003;

        /* 準備 */
        this.reflectionModel.set("type", null);

        /* テスト対象の実行 */
        final KmgToolMsgException actualException
            = Assertions.assertThrows(KmgToolMsgException.class, () -> this.testTarget.addTypeToCsvRows());

        /* 検証の実施 */
        this.verifyKmgMsgException(actualException, KmgToolMsgException.class, expectedDomainMessage,
            expectedMessageTypes);

    }

    /**
     * addTypeToCsvRows メソッドのテスト - 正常系：型が設定されている場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddTypeToCsvRows_normalTypeSet() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("type", "String");
        this.testTarget.addOneLineOfDataToCsvRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addTypeToCsvRows();

        /* 検証の準備 */
        final boolean            actualResult  = testResult;
        final List<List<String>> actualCsvRows = this.testTarget.getCsvRows();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(1, actualCsvRows.size(), "CSVの行数が正しいこと");
        Assertions.assertEquals(1, actualCsvRows.get(0).size(), "CSVの列数が正しいこと");
        Assertions.assertEquals("String", actualCsvRows.get(0).get(0), "型が正しく追加されていること");

    }

    /**
     * clearCsvRows メソッドのテスト - 正常系：CSVデータをクリアする
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearCsvRows_normalClearCsvRows() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.testTarget.addOneLineOfDataToCsvRows();
        this.reflectionModel.set("item", "testItem");
        this.testTarget.addItemToCsvRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.clearCsvRows();

        /* 検証の準備 */
        final boolean            actualResult  = testResult;
        final List<List<String>> actualCsvRows = this.testTarget.getCsvRows();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(0, actualCsvRows.size(), "CSVデータがクリアされていること");

    }

    /**
     * clearProcessingData メソッドのテスト - 正常系：データをクリアする
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearProcessingData_normalClearData() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("javadocComment", "テストコメント");
        this.reflectionModel.set("type", "String");
        this.reflectionModel.set("item", "testItem");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.clearProcessingData();

        /* 検証の準備 */
        final boolean actualResult         = testResult;
        final String  actualJavadocComment = this.testTarget.getJavadocComment();
        final String  actualType           = this.testTarget.getTyep();
        final String  actualItem           = this.testTarget.getItem();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertNull(actualJavadocComment, "Javadocコメントがクリアされていること");
        Assertions.assertNull(actualType, "型がクリアされていること");
        Assertions.assertNull(actualItem, "項目名がクリアされていること");

    }

    /**
     * close メソッドのテスト - 正常系：リソースをクローズする
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClose_normalCloseResources() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.csv");
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
     * convertFields メソッドのテスト - 正常系：配列型のprivateフィールド宣言の場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertFields_normalPrivateArrayField() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String[] testArrayField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertFields();

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final String  actualType   = this.testTarget.getTyep();
        final String  actualItem   = this.testTarget.getItem();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals("String[]", actualType, "配列型が正しく抽出されていること");
        Assertions.assertEquals("testArrayField", actualItem, "項目名が正しく抽出されていること");

    }

    /**
     * convertFields メソッドのテスト - 正常系：privateフィールド宣言の場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertFields_normalPrivateField() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String testField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertFields();

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final String  actualType   = this.testTarget.getTyep();
        final String  actualItem   = this.testTarget.getItem();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals("String", actualType, "型が正しく抽出されていること");
        Assertions.assertEquals("testField", actualItem, "項目名が正しく抽出されていること");

    }

    /**
     * convertFields メソッドのテスト - 正常系：ジェネリクス型のprivateフィールド宣言の場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertFields_normalPrivateGenericField() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private List<String> testGenericField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertFields();

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final String  actualType   = this.testTarget.getTyep();
        final String  actualItem   = this.testTarget.getItem();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals("List<String>", actualType, "ジェネリクス型が正しく抽出されていること");
        Assertions.assertEquals("testGenericField", actualItem, "項目名が正しく抽出されていること");

    }

    /**
     * convertFields メソッドのテスト - 準正常系：privateフィールド宣言ではない場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertFields_semiNotPrivateField() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "public void testMethod() {");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertFields();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * convertJavadoc メソッドのテスト - 正常系：Javadoc解析中にJavadoc開始がある場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertJavadoc_normalInJavadocParsingWithStart() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("inJavadocParsing", true);
        this.reflectionModel.set("convertedLine", "/**テストコメント");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertJavadoc();

        /* 検証の準備 */
        final boolean actualResult         = testResult;
        final String  actualJavadocComment = this.testTarget.getJavadocComment();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals("テストコメント", actualJavadocComment, "Javadocコメントが正しく抽出されていること");

    }

    /**
     * convertJavadoc メソッドのテスト - 正常系：複数行Javadocコメント開始の場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertJavadoc_normalMultiLineJavadocStart() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "/**テストコメント");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertJavadoc();

        /* 検証の準備 */
        final boolean actualResult           = testResult;
        final String  actualJavadocComment   = this.testTarget.getJavadocComment();
        final boolean actualInJavadocParsing = this.testTarget.isInJavadocParsing();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals("テストコメント", actualJavadocComment, "Javadocコメントが正しく抽出されていること");
        Assertions.assertEquals(false, actualInJavadocParsing, "Javadoc解析が終了していること");

    }

    /**
     * convertJavadoc メソッドのテスト - 正常系：1行Javadocコメントの場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertJavadoc_normalSingleLineJavadoc() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "/** テストコメント */");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertJavadoc();

        /* 検証の準備 */
        final boolean actualResult           = testResult;
        final String  actualJavadocComment   = this.testTarget.getJavadocComment();
        final boolean actualInJavadocParsing = this.testTarget.isInJavadocParsing();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals("テストコメント", actualJavadocComment, "Javadocコメントが正しく抽出されていること");
        Assertions.assertEquals(false, actualInJavadocParsing, "Javadoc解析が終了していること");

    }

    /**
     * convertJavadoc メソッドのテスト - 準正常系：Javadoc解析中で複数行パターンではない場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertJavadoc_semiInJavadocParsingNotMultiLinePattern() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        this.reflectionModel.set("inJavadocParsing", true);
        this.reflectionModel.set("convertedLine", " * テストコメント");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertJavadoc();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * convertJavadoc メソッドのテスト - 準正常系：Javadocコメント開始ではない場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertJavadoc_semiNotJavadocStart() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "// 通常のコメント");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertJavadoc();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * getConvertedLine メソッドのテスト - 正常系：変換後の行データを取得する
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetConvertedLine_normalGetConvertedLine() throws Exception {

        /* 期待値の定義 */
        final String expectedConvertedLine = "test line";

        /* 準備 */
        this.reflectionModel.set("convertedLine", expectedConvertedLine);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getConvertedLine();

        /* 検証の準備 */
        final String actualConvertedLine = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedConvertedLine, actualConvertedLine, "変換後の行データが正しく取得できること");

    }

    /**
     * getCsvRows メソッドのテスト - 正常系：CSVデータを取得する
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetCsvRows_normalGetCsvRows() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.testTarget.addOneLineOfDataToCsvRows();
        this.reflectionModel.set("item", "testItem");
        this.testTarget.addItemToCsvRows();

        /* テスト対象の実行 */
        final List<List<String>> testResult = this.testTarget.getCsvRows();

        /* 検証の準備 */
        final List<List<String>> actualCsvRows = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualCsvRows, "CSVデータが取得できること");
        Assertions.assertEquals(1, actualCsvRows.size(), "CSVの行数が正しいこと");
        Assertions.assertEquals(1, actualCsvRows.get(0).size(), "CSVの列数が正しいこと");
        Assertions.assertEquals("testItem", actualCsvRows.get(0).get(0), "データが正しく取得できること");

    }

    /**
     * getItem メソッドのテスト - 正常系：項目名を取得する
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetItem_normalGetItem() throws Exception {

        /* 期待値の定義 */
        final String expectedItem = "testItem";

        /* 準備 */
        this.reflectionModel.set("item", expectedItem);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getItem();

        /* 検証の準備 */
        final String actualItem = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedItem, actualItem, "項目名が正しく取得できること");

    }

    /**
     * getJavadocComment メソッドのテスト - 正常系：Javadocコメントを取得する
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetJavadocComment_normalGetJavadocComment() throws Exception {

        /* 期待値の定義 */
        final String expectedJavadocComment = "テストコメント";

        /* 準備 */
        this.reflectionModel.set("javadocComment", expectedJavadocComment);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getJavadocComment();

        /* 検証の準備 */
        final String actualJavadocComment = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedJavadocComment, actualJavadocComment, "Javadocコメントが正しく取得できること");

    }

    /**
     * getLineOfDataRead メソッドのテスト - 正常系：読み込んだ行データを取得する
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetLineOfDataRead_normalGetLineOfDataRead() throws Exception {

        /* 期待値の定義 */
        final String expectedLineOfDataRead = "test line";

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.csv");
        Files.write(testInputFile, "test line".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);
        this.testTarget.readOneLineOfData();

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getLineOfDataRead();

        /* 検証の準備 */
        final String actualLineOfDataRead = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedLineOfDataRead, actualLineOfDataRead, "読み込んだ行データが正しく取得できること");

    }

    /**
     * getNowLineNumber メソッドのテスト - 正常系：現在の行番号を取得する
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetNowLineNumber_normalGetNowLineNumber() throws Exception {

        /* 期待値の定義 */
        final int expectedNowLineNumber = 1;

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.csv");
        Files.write(testInputFile, "test line".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);
        this.testTarget.readOneLineOfData();

        /* テスト対象の実行 */
        final int testResult = this.testTarget.getNowLineNumber();

        /* 検証の準備 */
        final int actualNowLineNumber = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedNowLineNumber, actualNowLineNumber, "現在の行番号が正しく取得できること");

    }

    /**
     * getTyep メソッドのテスト - 正常系：型を取得する
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetTyep_normalGetType() throws Exception {

        /* 期待値の定義 */
        final String expectedType = "String";

        /* 準備 */
        this.reflectionModel.set("type", expectedType);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTyep();

        /* 検証の準備 */
        final String actualType = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedType, actualType, "型が正しく取得できること");

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
        final boolean expectedResult = true;

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.csv");
        Files.write(testInputFile, "test content".getBytes());

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(testInputFile, testOutputFile);

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "初期化が成功すること");

    }

    /**
     * isInJavadocParsing メソッドのテスト - 正常系：Javadoc解析中フラグがfalseの場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testIsInJavadocParsing_normalInJavadocParsingFalse() throws Exception {

        /* 期待値の定義 */
        final boolean expectedInJavadocParsing = false;

        /* 準備 */
        this.reflectionModel.set("inJavadocParsing", expectedInJavadocParsing);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isInJavadocParsing();

        /* 検証の準備 */
        final boolean actualInJavadocParsing = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInJavadocParsing, actualInJavadocParsing, "Javadoc解析中フラグが正しく取得できること");

    }

    /**
     * isInJavadocParsing メソッドのテスト - 正常系：Javadoc解析中フラグがtrueの場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testIsInJavadocParsing_normalInJavadocParsingTrue() throws Exception {

        /* 期待値の定義 */
        final boolean expectedInJavadocParsing = true;

        /* 準備 */
        this.reflectionModel.set("inJavadocParsing", expectedInJavadocParsing);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isInJavadocParsing();

        /* 検証の準備 */
        final boolean actualInJavadocParsing = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInJavadocParsing, actualInJavadocParsing, "Javadoc解析中フラグが正しく取得できること");

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
        final boolean expectedResult = true;

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.csv");
        Files.write(testInputFile, "test line 1\ntest line 2".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.readOneLineOfData();

        /* 検証の準備 */
        final boolean actualResult         = testResult;
        final String  actualLineOfDataRead = this.testTarget.getLineOfDataRead();
        final int     actualNowLineNumber  = this.testTarget.getNowLineNumber();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "データが読み込めること");
        Assertions.assertEquals("test line 1", actualLineOfDataRead, "読み込んだデータが正しいこと");
        Assertions.assertEquals(1, actualNowLineNumber, "行番号が正しいこと");

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
        final boolean expectedResult = false;

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.csv");
        Files.write(testInputFile, "".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.readOneLineOfData();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "ファイル終端で正しく判定されること");

    }

    /**
     * removeModifier メソッドのテスト - 正常系：削除対象の修飾子がない場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testRemoveModifier_normalNoModifierToRemove() throws Exception {

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String testField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeModifier();

        /* 検証の準備 */
        final boolean actualResult        = testResult;
        final String  actualConvertedLine = this.testTarget.getConvertedLine();

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals("private String testField;", actualConvertedLine, "元の文字列のまま変化がないこと");

    }

    /**
     * removeModifier メソッドのテスト - 正常系：finalとstaticの修飾子を削除する
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testRemoveModifier_normalRemoveFinalAndStatic() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private static final String testField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeModifier();

        /* 検証の準備 */
        final boolean actualResult        = testResult;
        final String  actualConvertedLine = this.testTarget.getConvertedLine();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals("private   String testField;", actualConvertedLine, "finalとstaticが削除されていること");

    }

    /**
     * removeModifier メソッドのテスト - 正常系：finalのみの修飾子を削除する
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testRemoveModifier_normalRemoveFinalOnly() throws Exception {

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private final String testField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeModifier();

        /* 検証の準備 */
        final boolean actualResult        = testResult;
        final String  actualConvertedLine = this.testTarget.getConvertedLine();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals("private  String testField;", actualConvertedLine, "finalが削除されていること");

    }

    /**
     * removeModifier メソッドのテスト - 正常系：staticのみの修飾子を削除する
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testRemoveModifier_normalRemoveStaticOnly() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private static String testField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeModifier();

        /* 検証の準備 */
        final boolean actualResult        = testResult;
        final String  actualConvertedLine = this.testTarget.getConvertedLine();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals("private  String testField;", actualConvertedLine, "staticが削除されていること");

    }

    /**
     * replaceInLine メソッドのテスト - 正常系：行内の文字列を置換する（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_normalReplaceString() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private final static String testField;");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("replaceInLine", "final", "");

        /* 検証の準備 */
        final boolean actualResult        = testResult;
        final String  actualConvertedLine = this.testTarget.getConvertedLine();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "置換が成功していること");
        Assertions.assertEquals("private  static String testField;", actualConvertedLine, "文字列が置換されていること");

    }

    /**
     * replaceInLine メソッドのテスト - 準正常系：置換文字列がnullの場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_semiReplacementNull() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String testField;");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("replaceInLine", "String", null);

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "nullの場合は置換されないこと");

    }

    /**
     * replaceInLine メソッドのテスト - 準正常系：対象文字列が見つからない場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_semiTargetNotFound() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String testField;");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("replaceInLine", "notfound", "");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "対象文字列が見つからない場合は置換されないこと");

    }

    /**
     * replaceInLine メソッドのテスト - 準正常系：対象文字列がnullの場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_semiTargetNull() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String testField;");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("replaceInLine", null, "");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "nullの場合は置換されないこと");

    }

    /**
     * writeCsvFile メソッドのテスト - 正常系：CSVファイルに書き込む
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteCsvFile_normalWriteCsv() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.csv");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);

        // テストデータを追加
        this.testTarget.addOneLineOfDataToCsvRows();
        this.reflectionModel.set("item", "testItem1");
        this.testTarget.addItemToCsvRows();
        this.reflectionModel.set("javadocComment", "テストコメント1");
        this.testTarget.addJavadocCommentToCsvRows();

        this.testTarget.addOneLineOfDataToCsvRows();
        this.reflectionModel.set("item", "testItem2");
        this.testTarget.addItemToCsvRows();
        this.reflectionModel.set("javadocComment", "テストコメント2");
        this.testTarget.addJavadocCommentToCsvRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeCsvFile();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "CSVファイルへの書き込みが成功すること");
        Assertions.assertTrue(Files.exists(testOutputFile), "出力ファイルが作成されていること");

    }

}
