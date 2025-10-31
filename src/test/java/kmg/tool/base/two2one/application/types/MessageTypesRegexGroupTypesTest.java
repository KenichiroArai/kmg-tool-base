package kmg.tool.base.two2one.application.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.test.AbstractKmgTest;

/**
 * メッセージの種類作成用正規表現グループの種類テスト
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@SuppressWarnings({
    "nls", "static-method"
})
public class MessageTypesRegexGroupTypesTest extends AbstractKmgTest {

    /**
     * コンストラクタのテスト - 正常系：全ての列挙型の値が正しく設定される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testConstructor_normalAllEnumValuesCorrectlySet() {

        /* 期待値の定義 */
        final MessageTypesRegexGroupTypes[] expectedValues = {
            MessageTypesRegexGroupTypes.NONE, MessageTypesRegexGroupTypes.MESSAGE_TYPE_FULL_MATCH,
            MessageTypesRegexGroupTypes.MESSAGE_TYPE_ITEM, MessageTypesRegexGroupTypes.MESSAGE_TYPE_ITEM_NAME
        };

        /* 準備 */

        /* テスト対象の実行 */
        final MessageTypesRegexGroupTypes[] testResult = MessageTypesRegexGroupTypes.values();

        /* 検証の準備 */
        final MessageTypesRegexGroupTypes[] actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedValues.length, actualResult.length, "列挙型の数が正しいこと");

        for (int i = 0; i < expectedValues.length; i++) {

            Assertions.assertEquals(expectedValues[i], actualResult[i], String.format("列挙型[%d]が正しいこと", i));

        }

    }

    /**
     * 各列挙型のget メソッドのテスト - 正常系：全ての列挙型のgetが正しい場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGet_normalAllEnumGetsCorrect() {

        /* 期待値の定義 */
        final String[] expectedGets = {
            "None", // NONE
            "messageTypeFullMatch", // MESSAGE_TYPE_FULL_MATCH
            "messageTypeItem", // MESSAGE_TYPE_ITEM
            "messageTypeItemName" // MESSAGE_TYPE_ITEM_NAME
        };

        /* 準備 */
        final MessageTypesRegexGroupTypes[] testTargets = MessageTypesRegexGroupTypes.values();

        /* テスト対象の実行 */
        final String[] testResults = new String[testTargets.length];

        for (int i = 0; i < testTargets.length; i++) {

            testResults[i] = testTargets[i].get();

        }

        /* 検証の準備 */
        final String[] actualResults = testResults;

        /* 検証の実施 */
        Assertions.assertEquals(expectedGets.length, actualResults.length, "getの数が正しいこと");

        for (int i = 0; i < expectedGets.length; i++) {

            Assertions.assertEquals(expectedGets[i], actualResults[i], String.format("列挙型[%d]のgetが正しいこと", i));

        }

    }

    /**
     * get メソッドのテスト - 正常系：キーが返される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGet_normalKeyReturned() {

        /* 期待値の定義 */
        final String expectedResult = "messageTypeFullMatch";

        /* 準備 */
        final MessageTypesRegexGroupTypes testTarget = MessageTypesRegexGroupTypes.MESSAGE_TYPE_FULL_MATCH;

        /* テスト対象の実行 */
        final String testResult = testTarget.get();

        /* 検証の準備 */
        final String actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "キーが正しく返されること");

    }

    /**
     * getDefault メソッドのテスト - 正常系：デフォルト値が返される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDefault_normalDefaultValueReturned() {

        /* 期待値の定義 */
        final MessageTypesRegexGroupTypes expectedResult = MessageTypesRegexGroupTypes.NONE;

        /* 準備 */

        /* テスト対象の実行 */
        final MessageTypesRegexGroupTypes testResult = MessageTypesRegexGroupTypes.getDefault();

        /* 検証の準備 */
        final MessageTypesRegexGroupTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "デフォルト値が正しく返されること");

    }

    /**
     * 各列挙型のgetDetail メソッドのテスト - 正常系：全ての列挙型の詳細情報が正しい場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDetail_normalAllEnumDetailsCorrect() {

        /* 期待値の定義 */
        final String[] expectedDetails = {
            "指定無し", // NONE
            "メッセージの種類パターンの全体マッチ", // MESSAGE_TYPE_FULL_MATCH
            "メッセージの種類パターンの項目部分", // MESSAGE_TYPE_ITEM
            "メッセージの種類パターンの項目名部分" // MESSAGE_TYPE_ITEM_NAME
        };

        /* 準備 */
        final MessageTypesRegexGroupTypes[] testTargets = MessageTypesRegexGroupTypes.values();

        /* テスト対象の実行 */
        final String[] testResults = new String[testTargets.length];

        for (int i = 0; i < testTargets.length; i++) {

            testResults[i] = testTargets[i].getDetail();

        }

        /* 検証の準備 */
        final String[] actualResults = testResults;

        /* 検証の実施 */
        Assertions.assertEquals(expectedDetails.length, actualResults.length, "詳細情報の数が正しいこと");

        for (int i = 0; i < expectedDetails.length; i++) {

            Assertions.assertEquals(expectedDetails[i], actualResults[i], String.format("列挙型[%d]の詳細情報が正しいこと", i));

        }

    }

    /**
     * getDetail メソッドのテスト - 正常系：詳細情報が返される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDetail_normalDetailReturned() {

        /* 期待値の定義 */
        final String expectedResult = "メッセージの種類パターンの全体マッチ";

        /* 準備 */
        final MessageTypesRegexGroupTypes testTarget = MessageTypesRegexGroupTypes.MESSAGE_TYPE_FULL_MATCH;

        /* テスト対象の実行 */
        final String testResult = testTarget.getDetail();

        /* 検証の準備 */
        final String actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "詳細情報が正しく返されること");

    }

    /**
     * 各列挙型のgetDisplayName メソッドのテスト - 正常系：全ての列挙型の表示名が正しい場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDisplayName_normalAllEnumDisplayNamesCorrect() {

        /* 期待値の定義 */
        final String[] expectedDisplayNames = {
            "指定無し", // NONE
            "メッセージの種類パターン：フルマッチ", // MESSAGE_TYPE_FULL_MATCH
            "メッセージの種類パターン：項目", // MESSAGE_TYPE_ITEM
            "メッセージの種類パターン：項目名" // MESSAGE_TYPE_ITEM_NAME
        };

        /* 準備 */
        final MessageTypesRegexGroupTypes[] testTargets = MessageTypesRegexGroupTypes.values();

        /* テスト対象の実行 */
        final String[] testResults = new String[testTargets.length];

        for (int i = 0; i < testTargets.length; i++) {

            testResults[i] = testTargets[i].getDisplayName();

        }

        /* 検証の準備 */
        final String[] actualResults = testResults;

        /* 検証の実施 */
        Assertions.assertEquals(expectedDisplayNames.length, actualResults.length, "表示名の数が正しいこと");

        for (int i = 0; i < expectedDisplayNames.length; i++) {

            Assertions.assertEquals(expectedDisplayNames[i], actualResults[i], String.format("列挙型[%d]の表示名が正しいこと", i));

        }

    }

    /**
     * getDisplayName メソッドのテスト - 正常系：表示名が返される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDisplayName_normalDisplayNameReturned() {

        /* 期待値の定義 */
        final String expectedResult = "メッセージの種類パターン：フルマッチ";

        /* 準備 */
        final MessageTypesRegexGroupTypes testTarget = MessageTypesRegexGroupTypes.MESSAGE_TYPE_FULL_MATCH;

        /* テスト対象の実行 */
        final String testResult = testTarget.getDisplayName();

        /* 検証の準備 */
        final String actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "表示名が正しく返されること");

    }

    /**
     * getEnum メソッドのテスト - 正常系：存在するキーが指定された場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetEnum_normalExistingKeySpecified() {

        /* 期待値の定義 */
        final MessageTypesRegexGroupTypes expectedResult = MessageTypesRegexGroupTypes.MESSAGE_TYPE_FULL_MATCH;

        /* 準備 */
        final String testKey = "messageTypeFullMatch";

        /* テスト対象の実行 */
        final MessageTypesRegexGroupTypes testResult = MessageTypesRegexGroupTypes.getEnum(testKey);

        /* 検証の準備 */
        final MessageTypesRegexGroupTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "存在するキーに対応する列挙型が返されること");

    }

    /**
     * getEnum メソッドのテスト - 準正常系：存在しないキーが指定された場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetEnum_semiNonExistingKeySpecified() {

        /* 期待値の定義 */
        final MessageTypesRegexGroupTypes expectedResult = MessageTypesRegexGroupTypes.NONE;

        /* 準備 */
        final String testKey = "nonExistingKey";

        /* テスト対象の実行 */
        final MessageTypesRegexGroupTypes testResult = MessageTypesRegexGroupTypes.getEnum(testKey);

        /* 検証の準備 */
        final MessageTypesRegexGroupTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "存在しないキーの場合NONEが返されること");

    }

    /**
     * getEnum メソッドのテスト - 準正常系：nullキーが指定された場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetEnum_semiNullKeySpecified() {

        /* 期待値の定義 */
        final MessageTypesRegexGroupTypes expectedResult = MessageTypesRegexGroupTypes.NONE;

        /* 準備 */
        final String testKey = null;

        /* テスト対象の実行 */
        final MessageTypesRegexGroupTypes testResult = MessageTypesRegexGroupTypes.getEnum(testKey);

        /* 検証の準備 */
        final MessageTypesRegexGroupTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "nullキーの場合NONEが返されること");

    }

    /**
     * 各列挙型のgetGroupIndex メソッドのテスト - 正常系：全ての列挙型のグループインデックスが正しい場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetGroupIndex_normalAllEnumGroupIndicesCorrect() {

        /* 期待値の定義 */
        final int[] expectedGroupIndices = {
            -1, // NONE
            0, // MESSAGE_TYPE_FULL_MATCH
            0, // MESSAGE_TYPE_ITEM
            1 // MESSAGE_TYPE_ITEM_NAME
        };

        /* 準備 */
        final MessageTypesRegexGroupTypes[] testTargets = MessageTypesRegexGroupTypes.values();

        /* テスト対象の実行 */
        final int[] testResults = new int[testTargets.length];

        for (int i = 0; i < testTargets.length; i++) {

            testResults[i] = testTargets[i].getGroupIndex();

        }

        /* 検証の準備 */
        final int[] actualResults = testResults;

        /* 検証の実施 */
        Assertions.assertEquals(expectedGroupIndices.length, actualResults.length, "グループインデックスの数が正しいこと");

        for (int i = 0; i < expectedGroupIndices.length; i++) {

            Assertions.assertEquals(expectedGroupIndices[i], actualResults[i],
                String.format("列挙型[%d]のグループインデックスが正しいこと", i));

        }

    }

    /**
     * getGroupIndex メソッドのテスト - 正常系：グループインデックスが返される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetGroupIndex_normalGroupIndexReturned() {

        /* 期待値の定義 */
        final int expectedResult = 0;

        /* 準備 */
        final MessageTypesRegexGroupTypes testTarget = MessageTypesRegexGroupTypes.MESSAGE_TYPE_FULL_MATCH;

        /* テスト対象の実行 */
        final int testResult = testTarget.getGroupIndex();

        /* 検証の準備 */
        final int actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "グループインデックスが正しく返されること");

    }

    /**
     * getInitValue メソッドのテスト - 正常系：初期値が返される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetInitValue_normalInitValueReturned() {

        /* 期待値の定義 */
        final MessageTypesRegexGroupTypes expectedResult = MessageTypesRegexGroupTypes.NONE;

        /* 準備 */

        /* テスト対象の実行 */
        final MessageTypesRegexGroupTypes testResult = MessageTypesRegexGroupTypes.getInitValue();

        /* 検証の準備 */
        final MessageTypesRegexGroupTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "初期値が正しく返されること");

    }

    /**
     * 各列挙型のgetKey メソッドのテスト - 正常系：全ての列挙型のキーが正しい場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetKey_normalAllEnumKeysCorrect() {

        /* 期待値の定義 */
        final String[] expectedKeys = {
            "None", // NONE
            "messageTypeFullMatch", // MESSAGE_TYPE_FULL_MATCH
            "messageTypeItem", // MESSAGE_TYPE_ITEM
            "messageTypeItemName" // MESSAGE_TYPE_ITEM_NAME
        };

        /* 準備 */
        final MessageTypesRegexGroupTypes[] testTargets = MessageTypesRegexGroupTypes.values();

        /* テスト対象の実行 */
        final String[] testResults = new String[testTargets.length];

        for (int i = 0; i < testTargets.length; i++) {

            testResults[i] = testTargets[i].getKey();

        }

        /* 検証の準備 */
        final String[] actualResults = testResults;

        /* 検証の実施 */
        Assertions.assertEquals(expectedKeys.length, actualResults.length, "キーの数が正しいこと");

        for (int i = 0; i < expectedKeys.length; i++) {

            Assertions.assertEquals(expectedKeys[i], actualResults[i], String.format("列挙型[%d]のキーが正しいこと", i));

        }

    }

    /**
     * getKey メソッドのテスト - 正常系：キーが返される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetKey_normalKeyReturned() {

        /* 期待値の定義 */
        final String expectedResult = "messageTypeFullMatch";

        /* 準備 */
        final MessageTypesRegexGroupTypes testTarget = MessageTypesRegexGroupTypes.MESSAGE_TYPE_FULL_MATCH;

        /* テスト対象の実行 */
        final String testResult = testTarget.getKey();

        /* 検証の準備 */
        final String actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "キーが正しく返されること");

    }

    /**
     * 各列挙型のtoString メソッドのテスト - 正常系：全ての列挙型のtoStringが正しい場合
     *
     * @since 0.2.0
     */
    @Test
    public void testToString_normalAllEnumToStringsCorrect() {

        /* 期待値の定義 */
        final String[] expectedToStrings = {
            "None", // NONE
            "messageTypeFullMatch", // MESSAGE_TYPE_FULL_MATCH
            "messageTypeItem", // MESSAGE_TYPE_ITEM
            "messageTypeItemName" // MESSAGE_TYPE_ITEM_NAME
        };

        /* 準備 */
        final MessageTypesRegexGroupTypes[] testTargets = MessageTypesRegexGroupTypes.values();

        /* テスト対象の実行 */
        final String[] testResults = new String[testTargets.length];

        for (int i = 0; i < testTargets.length; i++) {

            testResults[i] = testTargets[i].toString();

        }

        /* 検証の準備 */
        final String[] actualResults = testResults;

        /* 検証の実施 */
        Assertions.assertEquals(expectedToStrings.length, actualResults.length, "toStringの数が正しいこと");

        for (int i = 0; i < expectedToStrings.length; i++) {

            Assertions.assertEquals(expectedToStrings[i], actualResults[i], String.format("列挙型[%d]のtoStringが正しいこと", i));

        }

    }

    /**
     * toString メソッドのテスト - 正常系：キーが返される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testToString_normalKeyReturned() {

        /* 期待値の定義 */
        final String expectedResult = "messageTypeFullMatch";

        /* 準備 */
        final MessageTypesRegexGroupTypes testTarget = MessageTypesRegexGroupTypes.MESSAGE_TYPE_FULL_MATCH;

        /* テスト対象の実行 */
        final String testResult = testTarget.toString();

        /* 検証の準備 */
        final String actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "toStringメソッドがキーを正しく返すこと");

    }

    /**
     * VALUES_MAP のテスト - 正常系：マップに全ての列挙型が格納される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testValuesMap_normalAllEnumValuesInMap() {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final MessageTypesRegexGroupTypes testResult = MessageTypesRegexGroupTypes.getEnum("messageTypeFullMatch");

        /* 検証の準備 */
        final MessageTypesRegexGroupTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualResult, "マップから取得した値がnullでないこと");
        Assertions.assertEquals(MessageTypesRegexGroupTypes.MESSAGE_TYPE_FULL_MATCH, actualResult,
            "マップから正しい列挙型が取得されること");

    }

}
