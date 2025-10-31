package kmg.tool.base.acccrt.application.logic.impl;

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
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;

/**
 * アクセサ作成ロジック実装テスト
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
    "nls",
})
public class AccessorCreationLogicImplTest extends AbstractKmgTest {

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
    private AccessorCreationLogicImpl testTarget;

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
     * セットアップ
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @SuppressWarnings("resource")
    @BeforeEach
    public void setUp() throws Exception {

        this.testTarget = new AccessorCreationLogicImpl();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

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

            try {

                this.testTarget.close();

            } catch (final IOException e) {

                e.printStackTrace();

            }

        }

    }

    /**
     * addItemToRows メソッドのテスト - 異常系：項目名がnullの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddItemToRows_abnormalItemNull() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN01001] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN01001;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            this.reflectionModel.set("item", null);
            this.testTarget.addOneLineOfDataToRows();

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.addItemToRows();

            }, "項目名がnullの場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * addItemToRows メソッドのテスト - 正常系：項目名が設定されている場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddItemToRows_normalItemSet() throws Exception {

        /* 期待値の定義 */
        final int    expectedRowSize    = 1;
        final int    expectedColumnSize = 1;
        final String expectedItem       = "testItem";

        /* 準備 */
        this.reflectionModel.set("item", "testItem");
        this.testTarget.addOneLineOfDataToRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addItemToRows();

        /* 検証の準備 */
        final boolean            actualResult = testResult;
        final List<List<String>> actualRows   = this.testTarget.getRows();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedRowSize, actualRows.size(), "中間の行数が正しいこと");
        Assertions.assertEquals(expectedColumnSize, actualRows.get(0).size(), "中間の列数が正しいこと");
        Assertions.assertEquals(expectedItem, actualRows.get(0).get(0), "項目名が正しく追加されていること");

    }

    /**
     * addJavadocCommentToRows メソッドのテスト - 異常系：Javadocコメントがnullの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddJavadocCommentToRows_abnormalJavadocNull() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN01002] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN01002;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            this.reflectionModel.set("javadocComment", null);
            this.testTarget.addOneLineOfDataToRows();

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.addJavadocCommentToRows();

            }, "Javadocコメントがnullの場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * addJavadocCommentToRows メソッドのテスト - 正常系：Javadocコメントが設定されている場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddJavadocCommentToRows_normalJavadocSet() throws Exception {

        /* 期待値の定義 */
        final int    expectedRowSize    = 1;
        final int    expectedColumnSize = 1;
        final String expectedComment    = "テストコメント";

        /* 準備 */
        this.reflectionModel.set("javadocComment", "テストコメント");
        this.testTarget.addOneLineOfDataToRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addJavadocCommentToRows();

        /* 検証の準備 */
        final boolean            actualResult = testResult;
        final List<List<String>> actualRows   = this.testTarget.getRows();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedRowSize, actualRows.size(), "中間の行数が正しいこと");
        Assertions.assertEquals(expectedColumnSize, actualRows.get(0).size(), "中間の列数が正しいこと");
        Assertions.assertEquals(expectedComment, actualRows.get(0).get(0), "Javadocコメントが正しく追加されていること");

    }

    /**
     * addOneLineOfDataToRows メソッドのテスト - 正常系：中間に新しい行を追加する
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddOneLineOfDataToRows_normalAddNewRow() throws Exception {

        /* 期待値の定義 */
        final int expectedRowSize    = 1;
        final int expectedColumnSize = 0;

        /* 準備 */

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addOneLineOfDataToRows();

        /* 検証の準備 */
        final boolean            actualResult = testResult;
        final List<List<String>> actualRows   = this.testTarget.getRows();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedRowSize, actualRows.size(), "新しい行が追加されていること");
        Assertions.assertEquals(expectedColumnSize, actualRows.get(0).size(), "新しい行は空であること");

    }

    /**
     * addTypeToRows メソッドのテスト - 異常系：型がnullの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddTypeToRows_abnormalTypeNull() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN01003] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN01003;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            this.reflectionModel.set("type", null);
            this.testTarget.addOneLineOfDataToRows();

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.addTypeToRows();

            }, "型がnullの場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * addTypeToRows メソッドのテスト - 正常系：型が設定されている場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddTypeToRows_normalTypeSet() throws Exception {

        /* 期待値の定義 */
        final int    expectedRowSize    = 1;
        final int    expectedColumnSize = 1;
        final String expectedType       = "String";

        /* 準備 */
        this.reflectionModel.set("type", "String");
        this.testTarget.addOneLineOfDataToRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addTypeToRows();

        /* 検証の準備 */
        final boolean            actualResult = testResult;
        final List<List<String>> actualRows   = this.testTarget.getRows();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedRowSize, actualRows.size(), "中間の行数が正しいこと");
        Assertions.assertEquals(expectedColumnSize, actualRows.get(0).size(), "中間の列数が正しいこと");
        Assertions.assertEquals(expectedType, actualRows.get(0).get(0), "型が正しく追加されていること");

    }

    /**
     * clearProcessingData メソッドのテスト - 正常系：データをクリアする
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearProcessingData_normalClearData() throws Exception {

        /* 期待値の定義 */
        final String expectedJavadocComment = null;
        final String expectedType           = null;
        final String expectedItem           = null;

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
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedJavadocComment, actualJavadocComment, "Javadocコメントがクリアされていること");
        Assertions.assertEquals(expectedType, actualType, "型がクリアされていること");
        Assertions.assertEquals(expectedItem, actualItem, "項目名がクリアされていること");

    }

    /**
     * clearRows メソッドのテスト - 正常系：行データをクリアする
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearRows_normalClearRows() throws Exception {

        /* 期待値の定義 */
        final int expectedRowSize = 0;

        /* 準備 */
        this.testTarget.addOneLineOfDataToRows();
        this.reflectionModel.set("item", "testItem");
        this.testTarget.addItemToRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.clearRows();

        /* 検証の準備 */
        final boolean            actualResult = testResult;
        final List<List<String>> actualRows   = this.testTarget.getRows();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedRowSize, actualRows.size(), "行データがクリアされていること");

    }

    /**
     * close メソッドのテスト - 正常系：リソースをクローズする
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClose_normalCloseResources() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.tmp");
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
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertFields_normalPrivateArrayField() throws Exception {

        /* 期待値の定義 */
        final String expectedType = "String[]";
        final String expectedItem = "testArrayField";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String[] testArrayField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertFields();

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final String  actualType   = this.testTarget.getTyep();
        final String  actualItem   = this.testTarget.getItem();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedType, actualType, "配列型が正しく抽出されていること");
        Assertions.assertEquals(expectedItem, actualItem, "項目名が正しく抽出されていること");

    }

    /**
     * convertFields メソッドのテスト - 正常系：privateフィールド宣言の場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertFields_normalPrivateField() throws Exception {

        /* 期待値の定義 */
        final String expectedType = "String";
        final String expectedItem = "testField";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String testField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertFields();

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final String  actualType   = this.testTarget.getTyep();
        final String  actualItem   = this.testTarget.getItem();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedType, actualType, "型が正しく抽出されていること");
        Assertions.assertEquals(expectedItem, actualItem, "項目名が正しく抽出されていること");

    }

    /**
     * convertFields メソッドのテスト - 正常系：ジェネリクス型のprivateフィールド宣言の場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertFields_normalPrivateGenericField() throws Exception {

        /* 期待値の定義 */
        final String expectedType = "List<String>";
        final String expectedItem = "testGenericField";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private List<String> testGenericField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertFields();

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final String  actualType   = this.testTarget.getTyep();
        final String  actualItem   = this.testTarget.getItem();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedType, actualType, "ジェネリクス型が正しく抽出されていること");
        Assertions.assertEquals(expectedItem, actualItem, "項目名が正しく抽出されていること");

    }

    /**
     * convertFields メソッドのテスト - 準正常系：privateフィールド宣言ではない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertFields_semiNotPrivateField() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("convertedLine", "public void testMethod() {");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertFields();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");

    }

    /**
     * convertJavadoc メソッドのテスト - 正常系：Javadoc解析中にJavadoc開始がある場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertJavadoc_normalInJavadocParsingWithStart() throws Exception {

        /* 期待値の定義 */
        final String expectedJavadocComment = "テストコメント";

        /* 準備 */
        this.reflectionModel.set("inJavadocParsing", true);
        this.reflectionModel.set("convertedLine", "/**テストコメント");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertJavadoc();

        /* 検証の準備 */
        final boolean actualResult         = testResult;
        final String  actualJavadocComment = this.testTarget.getJavadocComment();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedJavadocComment, actualJavadocComment, "Javadocコメントが正しく抽出されていること");

    }

    /**
     * convertJavadoc メソッドのテスト - 正常系：複数行Javadocコメント開始の場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertJavadoc_normalMultiLineJavadocStart() throws Exception {

        /* 期待値の定義 */
        final String expectedJavadocComment = "テストコメント";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "/**テストコメント");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertJavadoc();

        /* 検証の準備 */
        final boolean actualResult           = testResult;
        final String  actualJavadocComment   = this.testTarget.getJavadocComment();
        final boolean actualInJavadocParsing = this.testTarget.isInJavadocParsing();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedJavadocComment, actualJavadocComment, "Javadocコメントが正しく抽出されていること");
        Assertions.assertFalse(actualInJavadocParsing, "Javadoc解析が終了していること");

    }

    /**
     * convertJavadoc メソッドのテスト - 正常系：1行Javadocコメントの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertJavadoc_normalSingleLineJavadoc() throws Exception {

        /* 期待値の定義 */
        final String expectedJavadocComment = "テストコメント";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "/** テストコメント */");

        /*
         * テスト対象の実行
         * @since 0.2.0
         */
        final boolean testResult = this.testTarget.convertJavadoc();

        /* 検証の準備 */
        final boolean actualResult           = testResult;
        final String  actualJavadocComment   = this.testTarget.getJavadocComment();
        final boolean actualInJavadocParsing = this.testTarget.isInJavadocParsing();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedJavadocComment, actualJavadocComment, "Javadocコメントが正しく抽出されていること");
        Assertions.assertFalse(actualInJavadocParsing, "Javadoc解析が終了していること");

    }

    /**
     * convertJavadoc メソッドのテスト - 準正常系：Javadoc解析中で複数行パターンではない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertJavadoc_semiInJavadocParsingNotMultiLinePattern() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("inJavadocParsing", true);
        this.reflectionModel.set("convertedLine", " * テストコメント");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertJavadoc();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");

    }

    /**
     * convertJavadoc メソッドのテスト - 準正常系：Javadocコメント開始ではない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertJavadoc_semiNotJavadocStart() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("convertedLine", "// 通常のコメント");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertJavadoc();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");

    }

    /**
     * getConvertedLine メソッドのテスト - 正常系：変換後の行データを取得する
     *
     * @since 0.2.0
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
     * getItem メソッドのテスト - 正常系：項目名を取得する
     *
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.0
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
        final Path testOutputFile = this.tempDir.resolve("test_output.tmp");
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
     * @since 0.2.0
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
        final Path testOutputFile = this.tempDir.resolve("test_output.tmp");
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
     * getRows メソッドのテスト - 正常系：中間データを取得する
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetRows_normalGetRows() throws Exception {

        /* 期待値の定義 */
        final int    expectedRowSize    = 1;
        final int    expectedColumnSize = 1;
        final String expectedItem       = "testItem";

        /* 準備 */
        this.testTarget.addOneLineOfDataToRows();
        this.reflectionModel.set("item", "testItem");
        this.testTarget.addItemToRows();

        /* テスト対象の実行 */
        final List<List<String>> testResult = this.testTarget.getRows();

        /* 検証の準備 */
        final List<List<String>> actualRows = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualRows, "中間データが取得できること");
        Assertions.assertEquals(expectedRowSize, actualRows.size(), "中間の行数が正しいこと");
        Assertions.assertEquals(expectedColumnSize, actualRows.get(0).size(), "中間の列数が正しいこと");
        Assertions.assertEquals(expectedItem, actualRows.get(0).get(0), "データが正しく取得できること");

    }

    /**
     * getTyep メソッドのテスト - 正常系：型を取得する
     *
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalInitialization() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "test content".getBytes());

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(testInputFile, testOutputFile);

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が成功すること");

    }

    /**
     * isInJavadocParsing メソッドのテスト - 正常系：Javadoc解析中フラグがfalseの場合
     *
     * @since 0.2.0
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
        Assertions.assertFalse(actualInJavadocParsing, "Javadoc解析中フラグが正しく取得できること");

    }

    /**
     * isInJavadocParsing メソッドのテスト - 正常系：Javadoc解析中フラグがtrueの場合
     *
     * @since 0.2.0
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
        Assertions.assertTrue(actualInJavadocParsing, "Javadoc解析中フラグが正しく取得できること");

    }

    /**
     * readOneLineOfData メソッドのテスト - 正常系：データが読み込める場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReadOneLineOfData_normalReadData() throws Exception {

        /* 期待値の定義 */
        final String expectedLineOfDataRead = "test line 1";
        final int    expectedNowLineNumber  = 1;

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "test line 1\ntest line 2".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.readOneLineOfData();

        /* 検証の準備 */
        final boolean actualResult         = testResult;
        final String  actualLineOfDataRead = this.testTarget.getLineOfDataRead();
        final int     actualNowLineNumber  = this.testTarget.getNowLineNumber();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "データが読み込めること");
        Assertions.assertEquals(expectedLineOfDataRead, actualLineOfDataRead, "読み込んだデータが正しいこと");
        Assertions.assertEquals(expectedNowLineNumber, actualNowLineNumber, "行番号が正しいこと");

    }

    /**
     * readOneLineOfData メソッドのテスト - 準正常系：ファイル終端に達した場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReadOneLineOfData_semiEndOfFile() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.tmp");
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
     * removeModifier メソッドのテスト - 正常系：削除対象の修飾子がない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testRemoveModifier_normalNoModifierToRemove() throws Exception {

        /* 期待値の定義 */
        final String expectedConvertedLine = "private String testField;";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String testField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeModifier();

        /* 検証の準備 */
        final boolean actualResult        = testResult;
        final String  actualConvertedLine = this.testTarget.getConvertedLine();

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedConvertedLine, actualConvertedLine, "元の文字列のまま変化がないこと");

    }

    /**
     * removeModifier メソッドのテスト - 正常系：finalとstaticの修飾子を削除する
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testRemoveModifier_normalRemoveFinalAndStatic() throws Exception {

        /* 期待値の定義 */
        final String expectedConvertedLine = "private   String testField;";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private static final String testField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeModifier();

        /* 検証の準備 */
        final boolean actualResult        = testResult;
        final String  actualConvertedLine = this.testTarget.getConvertedLine();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedConvertedLine, actualConvertedLine, "finalとstaticが削除されていること");

    }

    /**
     * removeModifier メソッドのテスト - 正常系：finalのみの修飾子を削除する
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testRemoveModifier_normalRemoveFinalOnly() throws Exception {

        /* 期待値の定義 */
        final String expectedConvertedLine = "private  String testField;";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private final String testField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeModifier();

        /* 検証の準備 */
        final boolean actualResult        = testResult;
        final String  actualConvertedLine = this.testTarget.getConvertedLine();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedConvertedLine, actualConvertedLine, "finalが削除されていること");

    }

    /**
     * removeModifier メソッドのテスト - 正常系：staticのみの修飾子を削除する
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testRemoveModifier_normalRemoveStaticOnly() throws Exception {

        /* 期待値の定義 */
        final String expectedConvertedLine = "private  String testField;";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private static String testField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeModifier();

        /* 検証の準備 */
        final boolean actualResult        = testResult;
        final String  actualConvertedLine = this.testTarget.getConvertedLine();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedConvertedLine, actualConvertedLine, "staticが削除されていること");

    }

    /**
     * replaceInLine メソッドのテスト - 正常系：行内の文字列を置換する（プライベートメソッド）
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_normalReplaceString() throws Exception {

        /* 期待値の定義 */
        final String expectedConvertedLine = "private  static String testField;";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private final static String testField;");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("replaceInLine", "final", "");

        /* 検証の準備 */
        final boolean actualResult        = testResult;
        final String  actualConvertedLine = this.testTarget.getConvertedLine();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "置換が成功していること");
        Assertions.assertEquals(expectedConvertedLine, actualConvertedLine, "文字列が置換されていること");

    }

    /**
     * replaceInLine メソッドのテスト - 準正常系：置換文字列がnullの場合（プライベートメソッド）
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_semiReplacementNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String testField;");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("replaceInLine", "String", null);

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "nullの場合は置換されないこと");

    }

    /**
     * replaceInLine メソッドのテスト - 準正常系：対象文字列が見つからない場合（プライベートメソッド）
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_semiTargetNotFound() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String testField;");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("replaceInLine", "notfound", "");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "対象文字列が見つからない場合は置換されないこと");

    }

    /**
     * replaceInLine メソッドのテスト - 準正常系：対象文字列がnullの場合（プライベートメソッド）
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceInLine_semiTargetNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String testField;");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("replaceInLine", null, "");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "nullの場合は置換されないこと");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 正常系：中間ファイルに書き込む
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void writeIntermediateFile_normalWriteIntermediateFile() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile  = this.tempDir.resolve("test_input.txt");
        final Path testOutputFile = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testOutputFile);

        // テストデータを追加
        this.testTarget.addOneLineOfDataToRows();
        this.reflectionModel.set("item", "testItem1");
        this.testTarget.addItemToRows();
        this.reflectionModel.set("javadocComment", "テストコメント1");
        this.testTarget.addJavadocCommentToRows();

        this.testTarget.addOneLineOfDataToRows();
        this.reflectionModel.set("item", "testItem2");
        this.testTarget.addItemToRows();
        this.reflectionModel.set("javadocComment", "テストコメント2");
        this.testTarget.addJavadocCommentToRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeIntermediateFile();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "中間ファイルへの書き込みが成功すること");
        Assertions.assertTrue(Files.exists(testOutputFile), "出力ファイルが作成されていること");

    }

}
