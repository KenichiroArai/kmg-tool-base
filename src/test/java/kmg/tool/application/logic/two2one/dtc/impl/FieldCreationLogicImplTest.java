package kmg.tool.application.logic.two2one.dtc.impl;

import java.io.BufferedWriter;
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

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;

/**
 * フィールド作成ロジック実装クラスのテスト
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls",
})
public class FieldCreationLogicImplTest extends AbstractKmgTest {

    /** テスト対象クラス */
    private FieldCreationLogicImpl target;

    /** 一時ディレクトリ */
    @TempDir
    private Path tempDir;

    /** モックKMGメッセージソース */
    private KmgMessageSource mockMessageSource;

    /**
     * テスト前の準備
     */
    @SuppressWarnings("resource")
    @BeforeEach
    public void setUp() {

        this.target = new FieldCreationLogicImpl();

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

        if (this.target != null) {

            try {

                this.target.close();

            } catch (final IOException e) {

                e.printStackTrace();

            }

        }

    }

    /**
     * addCommentToCsvRows メソッドのテスト - 異常系：コメント未設定時の例外
     */
    @Test
    public void testAddCommentToCsvRows_errorCommentNotSet() {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN32006] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN32006;

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException
                = Assertions.assertThrows(KmgToolMsgException.class, () -> this.target.addCommentToCsvRows());

            /* 検証の実施 */
            Assertions.assertTrue(actualException.getMessage().startsWith(expectedDomainMessage), "例外メッセージが一致しません");
            Assertions.assertEquals(expectedMessageTypes, actualException.getMessageTypes(), "例外のメッセージタイプが一致しません");

        }

    }

    /**
     * addCommentToCsvRows メソッドのテスト - 正常系：コメントが設定されている場合
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testAddCommentToCsvRows_normalCommentSet() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult  = true;
        final String  expectedComment = "コメント";

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            writer.write("コメント フィールド名 String");

        }
        this.target.initialize(inputFile, outputFile);
        this.target.readOneLineOfData();
        this.target.convertFields();
        this.target.addOneLineOfDataToCsvRows();

        /* テスト対象の実行 */
        final boolean testResult = this.target.addCommentToCsvRows();

        /* 検証の準備 */
        final boolean actualResult  = testResult;
        final String  actualComment = this.target.getComment();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "コメント追加の戻り値が一致しません");
        Assertions.assertEquals(expectedComment, actualComment, "コメントが一致しません");

    }

    /**
     * addFieldToCsvRows メソッドのテスト - 異常系：フィールド名未設定時の例外
     */
    @Test
    public void testAddFieldToCsvRows_errorFieldNotSet() {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN32007] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN32007;

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException
                = Assertions.assertThrows(KmgToolMsgException.class, () -> this.target.addFieldToCsvRows());

            /* 検証の実施 */
            Assertions.assertTrue(actualException.getMessage().startsWith(expectedDomainMessage), "例外メッセージが一致しません");
            Assertions.assertEquals(expectedMessageTypes, actualException.getMessageTypes(), "例外のメッセージタイプが一致しません");

        }

    }

    /**
     * addFieldToCsvRows メソッドのテスト - 正常系：フィールド名が設定されている場合
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testAddFieldToCsvRows_normalFieldSet() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;
        final String  expectedField  = "fieldName";

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            writer.write("コメント field_name String");

        }
        this.target.initialize(inputFile, outputFile);
        this.target.readOneLineOfData();
        this.target.convertFields();
        this.target.addOneLineOfDataToCsvRows();

        /* テスト対象の実行 */
        final boolean testResult = this.target.addFieldToCsvRows();

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final String  actualField  = this.target.getField();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "フィールド追加の戻り値が一致しません");
        Assertions.assertEquals(expectedField, actualField, "フィールド名が一致しません");

    }

    /**
     * addTypeToCsvRows メソッドのテスト - 異常系：型情報未設定時の例外
     */
    @Test
    public void testAddTypeToCsvRows_errorTypeNotSet() {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN32008] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN32008;

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException
                = Assertions.assertThrows(KmgToolMsgException.class, () -> this.target.addTypeToCsvRows());

            /* 検証の実施 */
            Assertions.assertTrue(actualException.getMessage().startsWith(expectedDomainMessage), "例外メッセージが一致しません");
            Assertions.assertEquals(expectedMessageTypes, actualException.getMessageTypes(), "例外のメッセージタイプが一致しません");

        }

    }

    /**
     * addTypeToCsvRows メソッドのテスト - 正常系：型情報が設定されている場合
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testAddTypeToCsvRows_normalTypeSet() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;
        final String  expectedType   = "String";

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            writer.write("コメント フィールド名 String");

        }
        this.target.initialize(inputFile, outputFile);
        this.target.readOneLineOfData();
        this.target.convertFields();
        this.target.addOneLineOfDataToCsvRows();

        /* テスト対象の実行 */
        final boolean testResult = this.target.addTypeToCsvRows();

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final String  actualType   = this.target.getType();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "型追加の戻り値が一致しません");
        Assertions.assertEquals(expectedType, actualType, "型情報が一致しません");

    }

    /**
     * convertFields メソッドのテスト - 正常系：DBデータ型の変換
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testConvertFields_normalDbDataType() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult  = true;
        final String  expectedComment = "コメント";
        final String  expectedField   = "fieldName";
        final String  expectedType    = "VARCHAR";

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            writer.write("コメント field_name VARCHAR");

        }
        this.target.initialize(inputFile, outputFile);
        this.target.readOneLineOfData();

        /* テスト対象の実行 */
        final boolean testResult = this.target.convertFields();

        /* 検証の準備 */
        final boolean actualResult  = testResult;
        final String  actualComment = this.target.getComment();
        final String  actualField   = this.target.getField();
        final String  actualType    = this.target.getType();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "フィールド変換の戻り値が一致しません");
        Assertions.assertEquals(expectedComment, actualComment, "コメントが一致しません");
        Assertions.assertEquals(expectedField, actualField, "フィールド名が一致しません");
        Assertions.assertEquals(expectedType, actualType, "型情報が一致しません");

    }

    /**
     * convertFields メソッドのテスト - 正常系：パッケージ名を含む型情報の変換
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testConvertFields_normalPackageNameType() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult  = true;
        final String  expectedComment = "コメント";
        final String  expectedField   = "fieldName";
        final String  expectedType    = "Integer";

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            writer.write("コメント field_name Integer");

        }
        this.target.initialize(inputFile, outputFile);
        this.target.readOneLineOfData();

        /* テスト対象の実行 */
        final boolean testResult = this.target.convertFields();

        /* 検証の準備 */
        final boolean actualResult  = testResult;
        final String  actualComment = this.target.getComment();
        final String  actualField   = this.target.getField();
        final String  actualType    = this.target.getType();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "フィールド変換の戻り値が一致しません");
        Assertions.assertEquals(expectedComment, actualComment, "コメントが一致しません");
        Assertions.assertEquals(expectedField, actualField, "フィールド名が一致しません");
        Assertions.assertEquals(expectedType, actualType, "型情報が一致しません");

    }

    /**
     * convertFields メソッドのテスト - 正常系：標準データ型の場合
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testConvertFields_normalStandardType() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult  = true;
        final String  expectedComment = "コメント";
        final String  expectedField   = "fieldName";
        final String  expectedType    = "String";

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            writer.write("コメント field_name String");

        }
        this.target.initialize(inputFile, outputFile);
        this.target.readOneLineOfData();

        /* テスト対象の実行 */
        final boolean testResult = this.target.convertFields();

        /* 検証の準備 */
        final boolean actualResult  = testResult;
        final String  actualComment = this.target.getComment();
        final String  actualField   = this.target.getField();
        final String  actualType    = this.target.getType();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "フィールド変換の戻り値が一致しません");
        Assertions.assertEquals(expectedComment, actualComment, "コメントが一致しません");
        Assertions.assertEquals(expectedField, actualField, "フィールド名が一致しません");
        Assertions.assertEquals(expectedType, actualType, "型情報が一致しません");

    }

    /**
     * convertFields メソッドのテスト - 準正常系：入力データが不正な場合
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testConvertFields_semiInvalidInput() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            writer.write("コメント フィールド名");

        }
        this.target.initialize(inputFile, outputFile);
        this.target.readOneLineOfData();

        /* テスト対象の実行 */
        final boolean testResult = this.target.convertFields();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "フィールド変換の戻り値が一致しません");

    }

    /**
     * convertFields メソッドのテスト - 準正常系：入力データがnullの場合
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testConvertFields_semiNullInput() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        // 空のファイルを作成
        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            // 空のファイルを作成（何も書き込まない）
        }

        this.target.initialize(inputFile, outputFile);
        this.target.readOneLineOfData();

        /* テスト対象の実行 */
        final boolean testResult = this.target.convertFields();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "フィールド変換の戻り値が一致しません");

    }

    /**
     * getComment メソッドのテスト - 正常系：コメントが設定されている場合
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testGetComment_normalCommentSet() throws Exception {

        /* 期待値の定義 */
        final String expectedComment = "テストコメント";

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            writer.write("テストコメント test_field String");

        }
        this.target.initialize(inputFile, outputFile);
        this.target.readOneLineOfData();
        this.target.convertFields();

        /* テスト対象の実行 */
        final String testResult = this.target.getComment();

        /* 検証の準備 */
        final String actualComment = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedComment, actualComment, "コメントが一致しません");

    }

    /**
     * getComment メソッドのテスト - 準正常系：コメントが設定されていない場合
     */
    @Test
    public void testGetComment_semiCommentNotSet() {

        /* 期待値の定義 */
        final String expectedComment = null;

        /* 準備 */

        /* テスト対象の実行 */
        final String testResult = this.target.getComment();

        /* 検証の準備 */
        final String actualComment = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedComment, actualComment, "コメントが一致しません");

    }

    /**
     * getField メソッドのテスト - 正常系：フィールド名が設定されている場合
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testGetField_normalFieldSet() throws Exception {

        /* 期待値の定義 */
        final String expectedField = "testField";

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            writer.write("コメント test_field String");

        }
        this.target.initialize(inputFile, outputFile);
        this.target.readOneLineOfData();
        this.target.convertFields();

        /* テスト対象の実行 */
        final String testResult = this.target.getField();

        /* 検証の準備 */
        final String actualField = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedField, actualField, "フィールド名が一致しません");

    }

    /**
     * getField メソッドのテスト - 準正常系：フィールド名が設定されていない場合
     */
    @Test
    public void testGetField_semiFieldNotSet() {

        /* 期待値の定義 */
        final String expectedField = null;

        /* 準備 */

        /* テスト対象の実行 */
        final String testResult = this.target.getField();

        /* 検証の準備 */
        final String actualField = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedField, actualField, "フィールド名が一致しません");

    }

    /**
     * getType メソッドのテスト - 正常系：型情報が設定されている場合
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testGetType_normalTypeSet() throws Exception {

        /* 期待値の定義 */
        final String expectedType = "String";

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            writer.write("コメント field_name String");

        }
        this.target.initialize(inputFile, outputFile);
        this.target.readOneLineOfData();
        this.target.convertFields();

        /* テスト対象の実行 */
        final String testResult = this.target.getType();

        /* 検証の準備 */
        final String actualType = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedType, actualType, "型情報が一致しません");

    }

    /**
     * getType メソッドのテスト - 準正常系：型情報が設定されていない場合
     */
    @Test
    public void testGetType_semiTypeNotSet() {

        /* 期待値の定義 */
        final String expectedType = null;

        /* 準備 */

        /* テスト対象の実行 */
        final String testResult = this.target.getType();

        /* 検証の準備 */
        final String actualType = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedType, actualType, "型情報が一致しません");

    }
}
