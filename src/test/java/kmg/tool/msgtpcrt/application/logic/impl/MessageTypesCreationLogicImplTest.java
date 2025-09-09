package kmg.tool.msgtpcrt.application.logic.impl;

import java.io.IOException;
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
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;

/**
 * <h2>メッセージの種類作成ロジック実装クラスのテスト</h2>
 * <p>
 * MessageTypesCreationLogicImplのテストクラスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls",
})
public class MessageTypesCreationLogicImplTest extends AbstractKmgTest {

    /**
     * テンポラリディレクトリ
     *
     * @since 0.1.0
     */
    @TempDir
    private Path tempDir;

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private MessageTypesCreationLogicImpl testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.1.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * モックKMGメッセージソース
     *
     * @since 0.1.0
     */
    private KmgMessageSource mockMessageSource;

    /**
     * セットアップ
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @SuppressWarnings("resource")
    @BeforeEach
    public void setUp() throws Exception {

        this.testTarget = new MessageTypesCreationLogicImpl();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

    }

    /**
     * クリーンアップ
     *
     * @since 0.1.0
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
     * addItemNameToRows メソッドのテスト - 異常系：項目名がnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testaddItemNameToRows_errorItemNameNull() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN14000] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN14000;

        /* 準備 */
        this.reflectionModel.set("itemName", null);

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
                = Assertions.assertThrows(KmgToolMsgException.class, () -> this.testTarget.addItemNameToRows());

            /* 検証の実施 */
            Assertions.assertTrue(actualException.getMessage().startsWith(expectedDomainMessage), "メッセージが正しいこと");
            Assertions.assertEquals(expectedMessageTypes, actualException.getMessageTypes(), "メッセージタイプが正しいこと");

        }

    }

    /**
     * addItemNameToRows メソッドのテスト - 正常系：項目名が設定されている場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testaddItemNameToRows_normalItemNameSet() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("itemName", "テスト項目名");
        this.testTarget.addOneLineOfDataToRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addItemNameToRows();

        /* 検証の準備 */
        final boolean            actualResult = testResult;
        final List<List<String>> actualRows   = this.testTarget.getRows();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(1, actualRows.size(), "中間の行数が正しいこと");
        Assertions.assertEquals(1, actualRows.get(0).size(), "中間の列数が正しいこと");
        Assertions.assertEquals("テスト項目名", actualRows.get(0).get(0), "項目名が正しく追加されていること");

    }

    /**
     * addItemToRows メソッドのテスト - 異常系：項目がnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testaddItemToRows_errorItemNull() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN14001] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN14001;

        /* 準備 */
        this.reflectionModel.set("item", null);

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
                = Assertions.assertThrows(KmgToolMsgException.class, () -> this.testTarget.addItemToRows());

            /* 検証の実施 */
            Assertions.assertTrue(actualException.getMessage().startsWith(expectedDomainMessage), "メッセージが正しいこと");
            Assertions.assertEquals(expectedMessageTypes, actualException.getMessageTypes(), "メッセージタイプが正しいこと");

        }

    }

    /**
     * addItemToRows メソッドのテスト - 正常系：項目が設定されている場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testaddItemToRows_normalItemSet() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("item", "TEST_ITEM");
        this.testTarget.addOneLineOfDataToRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addItemToRows();

        /* 検証の準備 */
        final boolean            actualResult = testResult;
        final List<List<String>> actualRows   = this.testTarget.getRows();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(1, actualRows.size(), "中間の行数が正しいこと");
        Assertions.assertEquals(1, actualRows.get(0).size(), "中間の列数が正しいこと");
        Assertions.assertEquals("TEST_ITEM", actualRows.get(0).get(0), "項目が正しく追加されていること");

    }

    /**
     * convertMessageTypesDefinition メソッドのテスト - 異常系：分割数が不正な場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertMessageTypesDefinition_errorInvalidSplitCount() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN14002] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN14002;

        /* 準備 */
        final String testLine = "INVALID_FORMAT";
        this.reflectionModel.set("convertedLine", testLine);
        this.reflectionModel.set("lineOfDataRead", testLine);
        this.reflectionModel.set("nowLineNumber", 1);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class,
                () -> this.testTarget.convertMessageTypesDefinition());

            /* 検証の実施 */
            Assertions.assertTrue(actualException.getMessage().startsWith(expectedDomainMessage), "メッセージが正しいこと");
            Assertions.assertEquals(expectedMessageTypes, actualException.getMessageTypes(), "メッセージタイプが正しいこと");

        }

    }

    /**
     * convertMessageTypesDefinition メソッドのテスト - 正常系：メッセージ定義の変換
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertMessageTypesDefinition_normalConverted() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult   = true;
        final String  expectedItem     = "KMGTOOL_GEN14000";
        final String  expectedItemName = "メッセージの種類が指定されていません。";

        /* 準備 */
        final String testLine = "KMGTOOL_GEN14000=メッセージの種類が指定されていません。";
        this.reflectionModel.set("convertedLine", testLine);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertMessageTypesDefinition();

        /* 検証の準備 */
        final boolean actualResult   = testResult;
        final String  actualItem     = this.testTarget.getItem();
        final String  actualItemName = this.testTarget.getItemName();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedItem, actualItem, "項目が正しく抽出されていること");
        Assertions.assertEquals(expectedItemName, actualItemName, "項目名が正しく抽出されていること");

    }

    /**
     * convertMessageTypesDefinition メソッドのテスト - 正常系：複数のイコールが含まれる場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertMessageTypesDefinition_normalMultipleEquals() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult   = true;
        final String  expectedItem     = "KMGTOOL_GEN14000";
        final String  expectedItemName = "メッセージの種類が指定されていません。値={0}";

        /* 準備 */
        final String testLine = "KMGTOOL_GEN14000=メッセージの種類が指定されていません。値={0}";
        this.reflectionModel.set("convertedLine", testLine);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertMessageTypesDefinition();

        /* 検証の準備 */
        final boolean actualResult   = testResult;
        final String  actualItem     = this.testTarget.getItem();
        final String  actualItemName = this.testTarget.getItemName();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedItem, actualItem, "項目が正しく抽出されていること");
        Assertions.assertEquals(expectedItemName, actualItemName, "項目名が正しく抽出されていること");

    }

    /**
     * getItem メソッドのテスト - 正常系：項目の取得
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetItem_normalGetItem() throws Exception {

        /* 期待値の定義 */
        final String expectedItem = "TEST_ITEM";

        /* 準備 */
        this.reflectionModel.set("item", expectedItem);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getItem();

        /* 検証の準備 */
        final String actualItem = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedItem, actualItem, "項目が正しく取得されていること");

    }

    /**
     * getItem メソッドのテスト - 正常系：項目がnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetItem_normalItemNull() throws Exception {

        /* 期待値の定義 */
        final String expectedItem = null;

        /* 準備 */
        this.reflectionModel.set("item", null);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getItem();

        /* 検証の準備 */
        final String actualItem = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedItem, actualItem, "nullが正しく取得されていること");

    }

    /**
     * getItemName メソッドのテスト - 正常系：項目名の取得
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetItemName_normalGetItemName() throws Exception {

        /* 期待値の定義 */
        final String expectedItemName = "テスト項目名";

        /* 準備 */
        this.reflectionModel.set("itemName", expectedItemName);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getItemName();

        /* 検証の準備 */
        final String actualItemName = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedItemName, actualItemName, "項目名が正しく取得されていること");

    }

    /**
     * getItemName メソッドのテスト - 正常系：項目名がnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetItemName_normalItemNameNull() throws Exception {

        /* 期待値の定義 */
        final String expectedItemName = null;

        /* 準備 */
        this.reflectionModel.set("itemName", null);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getItemName();

        /* 検証の準備 */
        final String actualItemName = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedItemName, actualItemName, "nullが正しく取得されていること");

    }

    /**
     * setConvertedLine メソッドのテスト - 正常系：変換後行データの設定
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetConvertedLine_normalSetConvertedLine() throws Exception {

        /* 期待値の定義 */
        final String expectedConvertedLine = "KMGTOOL_GEN14000=メッセージの種類が指定されていません。";

        /* テスト対象の実行 */
        this.reflectionModel.set("convertedLine", expectedConvertedLine);

        /* 検証の準備 */
        final String actualConvertedLine = this.testTarget.getConvertedLine();

        /* 検証の実施 */
        Assertions.assertEquals(expectedConvertedLine, actualConvertedLine, "変換後行データが正しく設定されていること");

    }

    /**
     * setConvertedLine メソッドのテスト - 正常系：変換後行データにnullを設定
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetConvertedLine_normalSetNull() throws Exception {

        /* 期待値の定義 */
        final String expectedConvertedLine = null;

        /* テスト対象の実行 */
        this.reflectionModel.set("convertedLine", null);

        /* 検証の準備 */
        final String actualConvertedLine = this.testTarget.getConvertedLine();

        /* 検証の実施 */
        Assertions.assertEquals(expectedConvertedLine, actualConvertedLine, "nullが正しく設定されていること");

    }

    /**
     * setItem メソッドのテスト - 正常系：項目の設定
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetItem_normalSetItem() throws Exception {

        /* 期待値の定義 */
        final String expectedItem = "TEST_ITEM";

        /* テスト対象の実行 */
        this.reflectionModel.set("item", expectedItem);

        /* 検証の準備 */
        final String actualItem = this.testTarget.getItem();

        /* 検証の実施 */
        Assertions.assertEquals(expectedItem, actualItem, "項目が正しく設定されていること");

    }

    /**
     * setItem メソッドのテスト - 正常系：項目にnullを設定
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetItem_normalSetNull() throws Exception {

        /* 期待値の定義 */
        final String expectedItem = null;

        /* テスト対象の実行 */
        this.reflectionModel.set("item", null);

        /* 検証の準備 */
        final String actualItem = this.testTarget.getItem();

        /* 検証の実施 */
        Assertions.assertEquals(expectedItem, actualItem, "nullが正しく設定されていること");

    }

    /**
     * setItemName メソッドのテスト - 正常系：項目名の設定
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetItemName_normalSetItemName() throws Exception {

        /* 期待値の定義 */
        final String expectedItemName = "テスト項目名";

        /* テスト対象の実行 */
        this.reflectionModel.set("itemName", expectedItemName);

        /* 検証の準備 */
        final String actualItemName = this.testTarget.getItemName();

        /* 検証の実施 */
        Assertions.assertEquals(expectedItemName, actualItemName, "項目名が正しく設定されていること");

    }

    /**
     * setItemName メソッドのテスト - 正常系：項目名にnullを設定
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetItemName_normalSetNull() throws Exception {

        /* 期待値の定義 */
        final String expectedItemName = null;

        /* テスト対象の実行 */
        this.reflectionModel.set("itemName", null);

        /* 検証の準備 */
        final String actualItemName = this.testTarget.getItemName();

        /* 検証の実施 */
        Assertions.assertEquals(expectedItemName, actualItemName, "nullが正しく設定されていること");

    }

}
