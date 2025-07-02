package kmg.tool.e2scc.application.logic.impl;

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
import kmg.tool.cmn.infrastructure.type.msg.KmgToolGenMsgTypes;
import kmg.tool.e2scc.application.logic.impl.Enum2SwitchCaseCreationLogicImpl;

/**
 * 列挙型からcase文作成ロジック実装テスト
 *
 * @author KenichiroArai
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls",
})
public class Enum2SwitchCaseCreationLogicImplTest extends AbstractKmgTest {

    /** テンポラリディレクトリ */
    @TempDir
    private Path tempDir;

    /** テスト対象 */
    private Enum2SwitchCaseCreationLogicImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** モックKMGメッセージソース */
    private KmgMessageSource mockMessageSource;

    /**
     * セットアップ
     *
     * @throws Exception
     *                   例外
     */
    @SuppressWarnings("resource")
    @BeforeEach
    public void setUp() throws Exception {

        this.testTarget = new Enum2SwitchCaseCreationLogicImpl();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

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
     * @throws Exception
     *                   例外
     */
    @Test
    public void testaddItemNameToRows_errorItemNameNull() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN32009] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN32009;

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
     * @throws Exception
     *                   例外
     */
    @Test
    public void testaddItemToRows_errorItemNull() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN32010] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN32010;

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
     * convertEnumDefinition メソッドのテスト - 正常系：列挙型定義が正しく変換される場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertEnumDefinition_normalConverted() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult   = true;
        final String  expectedItem     = "TEST_ITEM";
        final String  expectedItemName = "テスト項目";

        /* 準備 */
        this.reflectionModel.set("convertedLine", "TEST_ITEM(\"テスト項目\",");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertEnumDefinition();

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
     * convertEnumDefinition メソッドのテスト - 準正常系：列挙型定義ではない場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertEnumDefinition_semiNotEnumDefinition() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        this.reflectionModel.set("convertedLine", "private String testField;");

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.convertEnumDefinition();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");

    }

    /**
     * getItem メソッドのテスト - 正常系：項目を取得する
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
        Assertions.assertEquals(expectedItem, actualItem, "項目が正しく取得できること");

    }

    /**
     * getItemName メソッドのテスト - 正常系：項目名を取得する
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetItemName_normalGetItemName() throws Exception {

        /* 期待値の定義 */
        final String expectedItemName = "テスト項目";

        /* 準備 */
        this.reflectionModel.set("itemName", expectedItemName);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getItemName();

        /* 検証の準備 */
        final String actualItemName = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedItemName, actualItemName, "項目名が正しく取得できること");

    }
}
