package kmg.tool.acccrt.application.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.test.AbstractKmgTest;

/**
 * アクセサ作成用正規表現グループの種類テスト
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SuppressWarnings({
    "nls", "static-method"
})
public class AccessorRegexGroupTypesTest extends AbstractKmgTest {

    /**
     * コンストラクタのテスト - 正常系：全ての列挙型の値が正しく設定される場合
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalAllEnumValuesCorrectlySet() {

        /* 期待値の定義 */
        final AccessorRegexGroupTypes[] expectedValues = {
            AccessorRegexGroupTypes.NONE, AccessorRegexGroupTypes.PRIVATE_FIELD_FULL_MATCH,
            AccessorRegexGroupTypes.PRIVATE_FIELD_TYPE, AccessorRegexGroupTypes.PRIVATE_FIELD_TYPE_INNER,
            AccessorRegexGroupTypes.PRIVATE_FIELD_ITEM_NAME, AccessorRegexGroupTypes.SINGLE_LINE_JAVADOC_FULL_MATCH,
            AccessorRegexGroupTypes.SINGLE_LINE_JAVADOC_COMMENT, AccessorRegexGroupTypes.MULTI_LINE_JAVADOC_FULL_MATCH,
            AccessorRegexGroupTypes.MULTI_LINE_JAVADOC_COMMENT
        };

        /* 準備 */

        /* テスト対象の実行 */
        final AccessorRegexGroupTypes[] testResult = AccessorRegexGroupTypes.values();

        /* 検証の準備 */
        final AccessorRegexGroupTypes[] actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedValues.length, actualResult.length, "列挙型の数が正しいこと");

        for (int i = 0; i < expectedValues.length; i++) {

            Assertions.assertEquals(expectedValues[i], actualResult[i], String.format("列挙型[%d]が正しいこと", i));

        }

    }

    /**
     * 各列挙型のget メソッドのテスト - 正常系：全ての列挙型のgetが正しい場合
     *
     * @since 0.1.0
     */
    @Test
    public void testGet_normalAllEnumGetsCorrect() {

        /* 期待値の定義 */
        final String[] expectedGets = {
            "None", // NONE
            "privateFieldFullMatch", // PRIVATE_FIELD_FULL_MATCH
            "privateFieldType", // PRIVATE_FIELD_TYPE
            "privateFieldTypeInner", // PRIVATE_FIELD_TYPE_INNER
            "privateFieldItemName", // PRIVATE_FIELD_ITEM_NAME
            "singleLineJavadocFullMatch", // SINGLE_LINE_JAVADOC_FULL_MATCH
            "singleLineJavadocComment", // SINGLE_LINE_JAVADOC_COMMENT
            "multiLineJavadocFullMatch", // MULTI_LINE_JAVADOC_FULL_MATCH
            "multiLineJavadocComment" // MULTI_LINE_JAVADOC_COMMENT
        };

        /* 準備 */
        final AccessorRegexGroupTypes[] testTargets = AccessorRegexGroupTypes.values();

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
     * @since 0.1.0
     */
    @Test
    public void testGet_normalKeyReturned() {

        /* 期待値の定義 */
        final String expectedResult = "privateFieldFullMatch";

        /* 準備 */
        final AccessorRegexGroupTypes testTarget = AccessorRegexGroupTypes.PRIVATE_FIELD_FULL_MATCH;

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
     * @since 0.1.0
     */
    @Test
    public void testGetDefault_normalDefaultValueReturned() {

        /* 期待値の定義 */
        final AccessorRegexGroupTypes expectedResult = AccessorRegexGroupTypes.NONE;

        /* 準備 */

        /* テスト対象の実行 */
        final AccessorRegexGroupTypes testResult = AccessorRegexGroupTypes.getDefault();

        /* 検証の準備 */
        final AccessorRegexGroupTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "デフォルト値が正しく返されること");

    }

    /**
     * 各列挙型のgetDetail メソッドのテスト - 正常系：全ての列挙型の詳細情報が正しい場合
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDetail_normalAllEnumDetailsCorrect() {

        /* 期待値の定義 */
        final String[] expectedDetails = {
            "指定無し", // NONE
            "privateフィールドパターンの全体マッチ", // PRIVATE_FIELD_FULL_MATCH
            "privateフィールドパターンの型部分", // PRIVATE_FIELD_TYPE
            "privateフィールドパターンの型部分の内部グループ", // PRIVATE_FIELD_TYPE_INNER
            "privateフィールドパターンの項目名部分", // PRIVATE_FIELD_ITEM_NAME
            "1行Javadocパターンの全体マッチ", // SINGLE_LINE_JAVADOC_FULL_MATCH
            "1行Javadocパターンのコメント内容部分", // SINGLE_LINE_JAVADOC_COMMENT
            "複数行Javadocパターンの全体マッチ", // MULTI_LINE_JAVADOC_FULL_MATCH
            "複数行Javadocパターンのコメント内容部分" // MULTI_LINE_JAVADOC_COMMENT
        };

        /* 準備 */
        final AccessorRegexGroupTypes[] testTargets = AccessorRegexGroupTypes.values();

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
     * @since 0.1.0
     */
    @Test
    public void testGetDetail_normalDetailReturned() {

        /* 期待値の定義 */
        final String expectedResult = "privateフィールドパターンの全体マッチ";

        /* 準備 */
        final AccessorRegexGroupTypes testTarget = AccessorRegexGroupTypes.PRIVATE_FIELD_FULL_MATCH;

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
     * @since 0.1.0
     */
    @Test
    public void testGetDisplayName_normalAllEnumDisplayNamesCorrect() {

        /* 期待値の定義 */
        final String[] expectedDisplayNames = {
            "指定無し", // NONE
            "privateフィールドパターン：フルマッチ", // PRIVATE_FIELD_FULL_MATCH
            "privateフィールドパターン：型", // PRIVATE_FIELD_TYPE
            "privateフィールドパターン：型（内部グループ）", // PRIVATE_FIELD_TYPE_INNER
            "privateフィールドパターン：項目名", // PRIVATE_FIELD_ITEM_NAME
            "1行Javadocパターン：フルマッチ", // SINGLE_LINE_JAVADOC_FULL_MATCH
            "1行Javadocパターン：コメント内容", // SINGLE_LINE_JAVADOC_COMMENT
            "複数行Javadocパターン：フルマッチ", // MULTI_LINE_JAVADOC_FULL_MATCH
            "複数行Javadocパターン：コメント内容" // MULTI_LINE_JAVADOC_COMMENT
        };

        /* 準備 */
        final AccessorRegexGroupTypes[] testTargets = AccessorRegexGroupTypes.values();

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
     * @since 0.1.0
     */
    @Test
    public void testGetDisplayName_normalDisplayNameReturned() {

        /* 期待値の定義 */
        final String expectedResult = "privateフィールドパターン：フルマッチ";

        /* 準備 */
        final AccessorRegexGroupTypes testTarget = AccessorRegexGroupTypes.PRIVATE_FIELD_FULL_MATCH;

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
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_normalExistingKeySpecified() {

        /* 期待値の定義 */
        final AccessorRegexGroupTypes expectedResult = AccessorRegexGroupTypes.PRIVATE_FIELD_FULL_MATCH;

        /* 準備 */
        final String testKey = "privateFieldFullMatch";

        /* テスト対象の実行 */
        final AccessorRegexGroupTypes testResult = AccessorRegexGroupTypes.getEnum(testKey);

        /* 検証の準備 */
        final AccessorRegexGroupTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "存在するキーに対応する列挙型が返されること");

    }

    /**
     * getEnum メソッドのテスト - 準正常系：存在しないキーが指定された場合
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_semiNonExistingKeySpecified() {

        /* 期待値の定義 */
        final AccessorRegexGroupTypes expectedResult = AccessorRegexGroupTypes.NONE;

        /* 準備 */
        final String testKey = "nonExistingKey";

        /* テスト対象の実行 */
        final AccessorRegexGroupTypes testResult = AccessorRegexGroupTypes.getEnum(testKey);

        /* 検証の準備 */
        final AccessorRegexGroupTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "存在しないキーの場合NONEが返されること");

    }

    /**
     * getEnum メソッドのテスト - 準正常系：nullキーが指定された場合
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_semiNullKeySpecified() {

        /* 期待値の定義 */
        final AccessorRegexGroupTypes expectedResult = AccessorRegexGroupTypes.NONE;

        /* 準備 */
        final String testKey = null;

        /* テスト対象の実行 */
        final AccessorRegexGroupTypes testResult = AccessorRegexGroupTypes.getEnum(testKey);

        /* 検証の準備 */
        final AccessorRegexGroupTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "nullキーの場合NONEが返されること");

    }

    /**
     * 各列挙型のgetGroupIndex メソッドのテスト - 正常系：全ての列挙型のグループインデックスが正しい場合
     *
     * @since 0.1.0
     */
    @Test
    public void testGetGroupIndex_normalAllEnumGroupIndicesCorrect() {

        /* 期待値の定義 */
        final int[] expectedGroupIndices = {
            -1, // NONE
            0, // PRIVATE_FIELD_FULL_MATCH
            1, // PRIVATE_FIELD_TYPE
            2, // PRIVATE_FIELD_TYPE_INNER
            3, // PRIVATE_FIELD_ITEM_NAME
            0, // SINGLE_LINE_JAVADOC_FULL_MATCH
            1, // SINGLE_LINE_JAVADOC_COMMENT
            0, // MULTI_LINE_JAVADOC_FULL_MATCH
            1 // MULTI_LINE_JAVADOC_COMMENT
        };

        /* 準備 */
        final AccessorRegexGroupTypes[] testTargets = AccessorRegexGroupTypes.values();

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
     * @since 0.1.0
     */
    @Test
    public void testGetGroupIndex_normalGroupIndexReturned() {

        /* 期待値の定義 */
        final int expectedResult = 0;

        /* 準備 */
        final AccessorRegexGroupTypes testTarget = AccessorRegexGroupTypes.PRIVATE_FIELD_FULL_MATCH;

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
     * @since 0.1.0
     */
    @Test
    public void testGetInitValue_normalInitValueReturned() {

        /* 期待値の定義 */
        final AccessorRegexGroupTypes expectedResult = AccessorRegexGroupTypes.NONE;

        /* 準備 */

        /* テスト対象の実行 */
        final AccessorRegexGroupTypes testResult = AccessorRegexGroupTypes.getInitValue();

        /* 検証の準備 */
        final AccessorRegexGroupTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "初期値が正しく返されること");

    }

    /**
     * 各列挙型のgetKey メソッドのテスト - 正常系：全ての列挙型のキーが正しい場合
     *
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalAllEnumKeysCorrect() {

        /* 期待値の定義 */
        final String[] expectedKeys = {
            "None", // NONE
            "privateFieldFullMatch", // PRIVATE_FIELD_FULL_MATCH
            "privateFieldType", // PRIVATE_FIELD_TYPE
            "privateFieldTypeInner", // PRIVATE_FIELD_TYPE_INNER
            "privateFieldItemName", // PRIVATE_FIELD_ITEM_NAME
            "singleLineJavadocFullMatch", // SINGLE_LINE_JAVADOC_FULL_MATCH
            "singleLineJavadocComment", // SINGLE_LINE_JAVADOC_COMMENT
            "multiLineJavadocFullMatch", // MULTI_LINE_JAVADOC_FULL_MATCH
            "multiLineJavadocComment" // MULTI_LINE_JAVADOC_COMMENT
        };

        /* 準備 */
        final AccessorRegexGroupTypes[] testTargets = AccessorRegexGroupTypes.values();

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
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalKeyReturned() {

        /* 期待値の定義 */
        final String expectedResult = "privateFieldFullMatch";

        /* 準備 */
        final AccessorRegexGroupTypes testTarget = AccessorRegexGroupTypes.PRIVATE_FIELD_FULL_MATCH;

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
     * @since 0.1.0
     */
    @Test
    public void testToString_normalAllEnumToStringsCorrect() {

        /* 期待値の定義 */
        final String[] expectedToStrings = {
            "None", // NONE
            "privateFieldFullMatch", // PRIVATE_FIELD_FULL_MATCH
            "privateFieldType", // PRIVATE_FIELD_TYPE
            "privateFieldTypeInner", // PRIVATE_FIELD_TYPE_INNER
            "privateFieldItemName", // PRIVATE_FIELD_ITEM_NAME
            "singleLineJavadocFullMatch", // SINGLE_LINE_JAVADOC_FULL_MATCH
            "singleLineJavadocComment", // SINGLE_LINE_JAVADOC_COMMENT
            "multiLineJavadocFullMatch", // MULTI_LINE_JAVADOC_FULL_MATCH
            "multiLineJavadocComment" // MULTI_LINE_JAVADOC_COMMENT
        };

        /* 準備 */
        final AccessorRegexGroupTypes[] testTargets = AccessorRegexGroupTypes.values();

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
     * @since 0.1.0
     */
    @Test
    public void testToString_normalKeyReturned() {

        /* 期待値の定義 */
        final String expectedResult = "privateFieldFullMatch";

        /* 準備 */
        final AccessorRegexGroupTypes testTarget = AccessorRegexGroupTypes.PRIVATE_FIELD_FULL_MATCH;

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
     * @since 0.1.0
     */
    @Test
    public void testValuesMap_normalAllEnumValuesInMap() {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final AccessorRegexGroupTypes testResult = AccessorRegexGroupTypes.getEnum("privateFieldFullMatch");

        /* 検証の準備 */
        final AccessorRegexGroupTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualResult, "マップから取得した値がnullでないこと");
        Assertions.assertEquals(AccessorRegexGroupTypes.PRIVATE_FIELD_FULL_MATCH, actualResult, "マップから正しい列挙型が取得されること");

    }

}
