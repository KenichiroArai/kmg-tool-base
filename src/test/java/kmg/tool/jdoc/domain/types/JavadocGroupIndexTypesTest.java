package kmg.tool.jdoc.domain.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.test.AbstractKmgTest;

/**
 * Javadocグループインデックスの種類テスト
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
public class JavadocGroupIndexTypesTest extends AbstractKmgTest {

    /**
     * コンストラクタのテスト - 正常系：全ての列挙型の値が正しく設定される場合
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalAllEnumValuesCorrectlySet() {

        /* 期待値の定義 */
        final JavadocGroupIndexTypes[] expectedValues = {
            JavadocGroupIndexTypes.WHOLE, JavadocGroupIndexTypes.TAG_NAME, JavadocGroupIndexTypes.VALUE,
            JavadocGroupIndexTypes.DESCRIPTION
        };

        /* 準備 */

        /* テスト対象の実行 */
        final JavadocGroupIndexTypes[] testResult = JavadocGroupIndexTypes.values();

        /* 検証の準備 */
        final JavadocGroupIndexTypes[] actualResult = testResult;

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
        final Integer[] expectedGets = {
            0, // WHOLE
            1, // TAG_NAME
            2, // VALUE
            3 // DESCRIPTION
        };

        /* 準備 */
        final JavadocGroupIndexTypes[] testTargets = JavadocGroupIndexTypes.values();

        /* テスト対象の実行 */
        final Integer[] testResults = new Integer[testTargets.length];

        for (int i = 0; i < testTargets.length; i++) {

            testResults[i] = testTargets[i].get();

        }

        /* 検証の準備 */
        final Integer[] actualResults = testResults;

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
        final Integer expectedResult = 0;

        /* 準備 */
        final JavadocGroupIndexTypes testTarget = JavadocGroupIndexTypes.WHOLE;

        /* テスト対象の実行 */
        final Integer testResult = testTarget.get();

        /* 検証の準備 */
        final Integer actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "キーが正しく返されること");

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
            "タグ全体のグループインデックス", // WHOLE
            "タグ名のグループインデックス", // TAG_NAME
            "タグ値のグループインデックス", // VALUE
            "タグ説明のグループインデックス" // DESCRIPTION
        };

        /* 準備 */
        final JavadocGroupIndexTypes[] testTargets = JavadocGroupIndexTypes.values();

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
        final String expectedResult = "タグ全体のグループインデックス";

        /* 準備 */
        final JavadocGroupIndexTypes testTarget = JavadocGroupIndexTypes.WHOLE;

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
            "タグ全体", // WHOLE
            "タグ名", // TAG_NAME
            "タグ値", // VALUE
            "タグ説明" // DESCRIPTION
        };

        /* 準備 */
        final JavadocGroupIndexTypes[] testTargets = JavadocGroupIndexTypes.values();

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
        final String expectedResult = "タグ全体";

        /* 準備 */
        final JavadocGroupIndexTypes testTarget = JavadocGroupIndexTypes.WHOLE;

        /* テスト対象の実行 */
        final String testResult = testTarget.getDisplayName();

        /* 検証の準備 */
        final String actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "表示名が正しく返されること");

    }

    /**
     * 各列挙型のgetKey メソッドのテスト - 正常系：全ての列挙型のキーが正しい場合
     *
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalAllEnumKeysCorrect() {

        /* 期待値の定義 */
        final Integer[] expectedKeys = {
            0, // WHOLE
            1, // TAG_NAME
            2, // VALUE
            3 // DESCRIPTION
        };

        /* 準備 */
        final JavadocGroupIndexTypes[] testTargets = JavadocGroupIndexTypes.values();

        /* テスト対象の実行 */
        final Integer[] testResults = new Integer[testTargets.length];

        for (int i = 0; i < testTargets.length; i++) {

            testResults[i] = testTargets[i].getKey();

        }

        /* 検証の準備 */
        final Integer[] actualResults = testResults;

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
        final Integer expectedResult = 0;

        /* 準備 */
        final JavadocGroupIndexTypes testTarget = JavadocGroupIndexTypes.WHOLE;

        /* テスト対象の実行 */
        final Integer testResult = testTarget.getKey();

        /* 検証の準備 */
        final Integer actualResult = testResult;

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
            "0", // WHOLE
            "1", // TAG_NAME
            "2", // VALUE
            "3" // DESCRIPTION
        };

        /* 準備 */
        final JavadocGroupIndexTypes[] testTargets = JavadocGroupIndexTypes.values();

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
        final String expectedResult = "0";

        /* 準備 */
        final JavadocGroupIndexTypes testTarget = JavadocGroupIndexTypes.WHOLE;

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
        final JavadocGroupIndexTypes testResult = JavadocGroupIndexTypes.WHOLE;

        /* 検証の準備 */
        final JavadocGroupIndexTypes actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualResult, "マップから取得した値がnullでないこと");
        Assertions.assertEquals(JavadocGroupIndexTypes.WHOLE, actualResult, "マップから正しい列挙型が取得されること");

    }

}
