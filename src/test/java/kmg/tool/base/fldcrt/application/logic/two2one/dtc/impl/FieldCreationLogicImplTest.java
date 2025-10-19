package kmg.tool.base.fldcrt.application.logic.two2one.dtc.impl;

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
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.fldcrt.application.logic.impl.FieldCreationLogicImpl;

/**
 * フィールド作成ロジック実装クラスのテスト
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
public class FieldCreationLogicImplTest extends AbstractKmgTest {

    /**
     * テスト対象クラス
     *
     * @since 0.2.0
     */
    private FieldCreationLogicImpl target;

    /**
     * 一時ディレクトリ
     *
     * @since 0.2.0
     */
    @TempDir
    private Path tempDir;

    /**
     * モックKMGメッセージソース
     *
     * @since 0.2.0
     */
    private KmgMessageSource mockMessageSource;

    /**
     * テスト前の準備
     *
     * @since 0.2.0
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
     * @since 0.2.0
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
     * addCommentToRows メソッドのテスト - 異常系：コメント未設定時の例外
     *
     * @since 0.2.0
     */
    @Test
    public void testAddCommentToRows_errorCommentNotSet() {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN05000] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN05000;

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
                = Assertions.assertThrows(KmgToolMsgException.class, () -> this.target.addCommentToRows());

            /* 検証の実施 */
            Assertions.assertTrue(actualException.getMessage().startsWith(expectedDomainMessage), "例外メッセージが一致しません");
            Assertions.assertEquals(expectedMessageTypes, actualException.getMessageTypes(), "例外のメッセージタイプが一致しません");

        }

    }

    /**
     * addCommentToRows メソッドのテスト - 正常系：コメントが設定されている場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testAddCommentToRows_normalCommentSet() throws Exception {

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
        this.target.addOneLineOfDataToRows();

        /* テスト対象の実行 */
        final boolean testResult = this.target.addCommentToRows();

        /* 検証の準備 */
        final boolean actualResult  = testResult;
        final String  actualComment = this.target.getComment();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "コメント追加の戻り値が一致しません");
        Assertions.assertEquals(expectedComment, actualComment, "コメントが一致しません");

    }

    /**
     * addFieldToRows メソッドのテスト - 異常系：フィールド名未設定時の例外
     *
     * @since 0.2.0
     */
    @Test
    public void testAddFieldToRows_errorFieldNotSet() {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN05001] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN05001;

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
                = Assertions.assertThrows(KmgToolMsgException.class, () -> this.target.addFieldToRows());

            /* 検証の実施 */
            Assertions.assertTrue(actualException.getMessage().startsWith(expectedDomainMessage), "例外メッセージが一致しません");
            Assertions.assertEquals(expectedMessageTypes, actualException.getMessageTypes(), "例外のメッセージタイプが一致しません");

        }

    }

    /**
     * addFieldToRows メソッドのテスト - 正常系：フィールド名が設定されている場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testAddFieldToRows_normalFieldSet() throws Exception {

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
        this.target.addOneLineOfDataToRows();

        /* テスト対象の実行 */
        final boolean testResult = this.target.addFieldToRows();

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final String  actualField  = this.target.getField();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "フィールド追加の戻り値が一致しません");
        Assertions.assertEquals(expectedField, actualField, "フィールド名が一致しません");

    }

    /**
     * addTypeToRows メソッドのテスト - 異常系：型情報未設定時の例外
     *
     * @since 0.2.0
     */
    @Test
    public void testAddTypeToRows_errorTypeNotSet() {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN05002] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN05002;

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
                = Assertions.assertThrows(KmgToolMsgException.class, () -> this.target.addTypeToRows());

            /* 検証の実施 */
            Assertions.assertTrue(actualException.getMessage().startsWith(expectedDomainMessage), "例外メッセージが一致しません");
            Assertions.assertEquals(expectedMessageTypes, actualException.getMessageTypes(), "例外のメッセージタイプが一致しません");

        }

    }

    /**
     * addTypeToRows メソッドのテスト - 正常系：型情報が設定されている場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testAddTypeToRows_normalTypeSet() throws Exception {

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
        this.target.addOneLineOfDataToRows();

        /* テスト対象の実行 */
        final boolean testResult = this.target.addTypeToRows();

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
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testConvertFields_normalDbDataType() throws Exception {

        /* 期待値の定義 */
        final String expectedComment = "コメント";
        final String expectedField   = "fieldName";
        final String expectedType    = "String";

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            writer.write("コメント field_name 文字列型");

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
        Assertions.assertTrue(actualResult, "フィールド変換の戻り値が一致しません");
        Assertions.assertEquals(expectedComment, actualComment, "コメントが一致しません");
        Assertions.assertEquals(expectedField, actualField, "フィールド名が一致しません");
        Assertions.assertEquals(expectedType, actualType, "型情報が一致しません");

    }

    /**
     * convertFields メソッドのテスト - 正常系：未知の型情報の場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時にエラーが発生した場合
     */
    @Test
    public void testConvertFields_normalUnknownType() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult  = true;
        final String  expectedComment = "コメント";
        final String  expectedField   = "fieldName";
        final String  expectedType    = "UNKNOWN_TYPE";

        /* 準備 */
        final Path inputFile  = this.tempDir.resolve("input.txt");
        final Path outputFile = this.tempDir.resolve("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {

            writer.write("コメント field_name UNKNOWN_TYPE");

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
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.0
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
     *
     * @since 0.2.0
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
     * @since 0.2.0
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
     *
     * @since 0.2.0
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
     * @since 0.2.0
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
     *
     * @since 0.2.0
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
