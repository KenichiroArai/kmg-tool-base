package kmg.tool.application.logic.two2one.dtc.impl;

import java.io.IOException;
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
     * addItemNameToCsvRows メソッドのテスト - 異常系：項目名がnullの場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddItemNameToCsvRows_errorItemNameNull() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN32009] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN32009;

        /* 準備 */
        this.reflectionModel.set("itemName", null);

        /* テスト対象の実行 */
        final KmgToolMsgException actualException
            = Assertions.assertThrows(KmgToolMsgException.class, () -> this.testTarget.addItemNameToCsvRows());

        /* 検証の実施 */
        this.verifyKmgMsgException(actualException, KmgToolMsgException.class, expectedDomainMessage,
            expectedMessageTypes);

    }

    /**
     * addItemNameToCsvRows メソッドのテスト - 正常系：項目名が設定されている場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddItemNameToCsvRows_normalItemNameSet() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("itemName", "テスト項目名");
        this.testTarget.addOneLineOfDataToCsvRows();

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addItemNameToCsvRows();

        /* 検証の準備 */
        final boolean            actualResult  = testResult;
        final List<List<String>> actualCsvRows = this.testTarget.getCsvRows();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(1, actualCsvRows.size(), "CSVの行数が正しいこと");
        Assertions.assertEquals(1, actualCsvRows.get(0).size(), "CSVの列数が正しいこと");
        Assertions.assertEquals("テスト項目名", actualCsvRows.get(0).get(0), "項目名が正しく追加されていること");

    }

    /**
     * addItemToCsvRows メソッドのテスト - 異常系：項目がnullの場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddItemToCsvRows_errorItemNull() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN32010] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN32010;

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
     * addItemToCsvRows メソッドのテスト - 正常系：項目が設定されている場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddItemToCsvRows_normalItemSet() throws Exception {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        this.reflectionModel.set("item", "TEST_ITEM");
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
        Assertions.assertEquals("TEST_ITEM", actualCsvRows.get(0).get(0), "項目が正しく追加されていること");

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
