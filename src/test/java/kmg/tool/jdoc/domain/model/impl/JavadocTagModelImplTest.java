package kmg.tool.jdoc.domain.model.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.types.KmgJavadocTagTypes;

/**
 * Javadocタグモデル実装のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SuppressWarnings({
    "nls",
})
public class JavadocTagModelImplTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private JavadocTagModelImpl testTarget;

    /**
     * テスト前処理<br>
     *
     * @since 0.1.0
     */
    @BeforeEach
    public void setUp() {

        /* テスト対象のクリア */
        this.testTarget = null;

    }

    /**
     * constructor メソッドのテスト - 正常系:全てのパラメータが正常な値でのコンストラクタ
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalAllParameters() {

        /* 期待値の定義 */
        final String             expectedTargetStr   = "@author KenichiroArai";
        final KmgJavadocTagTypes expectedTag         = KmgJavadocTagTypes.AUTHOR;
        final String             expectedValue       = "KenichiroArai";
        final String             expectedDescription = "クラスやインタフェースの作成者を示す";

        /* 準備 */
        final String             testTargetStr   = "@author KenichiroArai";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.AUTHOR;
        final String             testValue       = "KenichiroArai";
        final String             testDescription = "クラスやインタフェースの作成者を示す";

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* 検証の準備 */
        final String             actualTargetStr   = this.testTarget.getTargetStr();
        final KmgJavadocTagTypes actualTag         = this.testTarget.getTag();
        final String             actualValue       = this.testTarget.getValue();
        final String             actualDescription = this.testTarget.getDescription();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetStr, actualTargetStr, "対象文字列が正しく設定されること");
        Assertions.assertEquals(expectedTag, actualTag, "タグが正しく設定されること");
        Assertions.assertEquals(expectedValue, actualValue, "指定値が正しく設定されること");
        Assertions.assertEquals(expectedDescription, actualDescription, "説明が正しく設定されること");

    }

    /**
     * constructor メソッドのテスト - 正常系:複雑な文字列でのコンストラクタ
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalComplexStrings() {

        /* 期待値の定義 */
        final String             expectedTargetStr   = "@param param1 パラメータ1の説明";
        final KmgJavadocTagTypes expectedTag         = KmgJavadocTagTypes.PARAM;
        final String             expectedValue       = "param1";
        final String             expectedDescription = "パラメータ1の説明";

        /* 準備 */
        final String             testTargetStr   = "@param param1 パラメータ1の説明";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.PARAM;
        final String             testValue       = "param1";
        final String             testDescription = "パラメータ1の説明";

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* 検証の準備 */
        final String             actualTargetStr   = this.testTarget.getTargetStr();
        final KmgJavadocTagTypes actualTag         = this.testTarget.getTag();
        final String             actualValue       = this.testTarget.getValue();
        final String             actualDescription = this.testTarget.getDescription();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetStr, actualTargetStr, "複雑な文字列の対象文字列が正しく設定されること");
        Assertions.assertEquals(expectedTag, actualTag, "複雑な文字列のタグが正しく設定されること");
        Assertions.assertEquals(expectedValue, actualValue, "複雑な文字列の指定値が正しく設定されること");
        Assertions.assertEquals(expectedDescription, actualDescription, "複雑な文字列の説明が正しく設定されること");

    }

    /**
     * constructor メソッドのテスト - 正常系:空文字列でのコンストラクタ
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalEmptyStrings() {

        /* 期待値の定義 */
        final String             expectedTargetStr   = "";
        final KmgJavadocTagTypes expectedTag         = KmgJavadocTagTypes.NONE;
        final String             expectedValue       = "";
        final String             expectedDescription = "";

        /* 準備 */
        final String             testTargetStr   = "";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.NONE;
        final String             testValue       = "";
        final String             testDescription = "";

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* 検証の準備 */
        final String             actualTargetStr   = this.testTarget.getTargetStr();
        final KmgJavadocTagTypes actualTag         = this.testTarget.getTag();
        final String             actualValue       = this.testTarget.getValue();
        final String             actualDescription = this.testTarget.getDescription();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetStr, actualTargetStr, "空文字列の対象文字列が正しく設定されること");
        Assertions.assertEquals(expectedTag, actualTag, "空文字列のタグが正しく設定されること");
        Assertions.assertEquals(expectedValue, actualValue, "空文字列の指定値が正しく設定されること");
        Assertions.assertEquals(expectedDescription, actualDescription, "空文字列の説明が正しく設定されること");

    }

    /**
     * constructor メソッドのテスト - 正常系:null値でのコンストラクタ
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalNullValues() {

        /* 期待値の定義 */

        /* 準備 */
        final String             testTargetStr   = null;
        final KmgJavadocTagTypes testTag         = null;
        final String             testValue       = null;
        final String             testDescription = null;

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* 検証の準備 */
        final String             actualTargetStr   = this.testTarget.getTargetStr();
        final KmgJavadocTagTypes actualTag         = this.testTarget.getTag();
        final String             actualValue       = this.testTarget.getValue();
        final String             actualDescription = this.testTarget.getDescription();

        /* 検証の実施 */
        Assertions.assertNull(actualTargetStr, "nullの対象文字列が正しく設定されること");
        Assertions.assertNull(actualTag, "nullのタグが正しく設定されること");
        Assertions.assertNull(actualValue, "nullの指定値が正しく設定されること");
        Assertions.assertNull(actualDescription, "nullの説明が正しく設定されること");

    }

    /**
     * getDescription メソッドのテスト - 正常系:通常の説明での説明取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDescription_normalDescription() {

        /* 期待値の定義 */
        final String expectedDescription = "クラスやインタフェースの作成者を示す";

        /* 準備 */
        final String             testTargetStr   = "@author KenichiroArai";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.AUTHOR;
        final String             testValue       = "KenichiroArai";
        final String             testDescription = "クラスやインタフェースの作成者を示す";
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getDescription();

        /* 検証の準備 */
        final String actualDescription = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedDescription, actualDescription, "説明が正しく返されること");

    }

    /**
     * getDescription メソッドのテスト - 正常系:空文字列の説明での説明取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDescription_normalEmptyDescription() {

        /* 期待値の定義 */
        final String expectedDescription = "";

        /* 準備 */
        final String             testTargetStr   = "@author KenichiroArai";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.AUTHOR;
        final String             testValue       = "KenichiroArai";
        final String             testDescription = "";
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getDescription();

        /* 検証の準備 */
        final String actualDescription = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedDescription, actualDescription, "空文字列の説明が正しく返されること");

    }

    /**
     * getDescription メソッドのテスト - 正常系:nullの説明での説明取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDescription_normalNullDescription() {

        /* 期待値の定義 */

        /* 準備 */
        final String             testTargetStr   = "@author KenichiroArai";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.AUTHOR;
        final String             testValue       = "KenichiroArai";
        final String             testDescription = null;
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getDescription();

        /* 検証の準備 */
        final String actualDescription = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualDescription, "nullの説明が正しく返されること");

    }

    /**
     * getTag メソッドのテスト - 正常系:異なるタグでのタグ取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTag_normalDifferentTag() {

        /* 期待値の定義 */
        final KmgJavadocTagTypes expectedTag = KmgJavadocTagTypes.VERSION;

        /* 準備 */
        final String             testTargetStr   = "@version 0.1.0";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.VERSION;
        final String             testValue       = "0.1.0";
        final String             testDescription = "クラスやインタフェースのバージョンを示す";
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final KmgJavadocTagTypes testResult = this.testTarget.getTag();

        /* 検証の準備 */
        final KmgJavadocTagTypes actualTag = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTag, actualTag, "異なるタグが正しく返されること");

    }

    /**
     * getTag メソッドのテスト - 正常系:nullのタグでのタグ取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTag_normalNullTag() {

        /* 期待値の定義 */

        /* 準備 */
        final String             testTargetStr   = "@author KenichiroArai";
        final KmgJavadocTagTypes testTag         = null;
        final String             testValue       = "KenichiroArai";
        final String             testDescription = "クラスやインタフェースの作成者を示す";
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final KmgJavadocTagTypes testResult = this.testTarget.getTag();

        /* 検証の準備 */
        final KmgJavadocTagTypes actualTag = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualTag, "nullのタグが正しく返されること");

    }

    /**
     * getTag メソッドのテスト - 正常系:通常のタグでのタグ取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTag_normalTag() {

        /* 期待値の定義 */
        final KmgJavadocTagTypes expectedTag = KmgJavadocTagTypes.AUTHOR;

        /* 準備 */
        final String             testTargetStr   = "@author KenichiroArai";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.AUTHOR;
        final String             testValue       = "KenichiroArai";
        final String             testDescription = "クラスやインタフェースの作成者を示す";
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final KmgJavadocTagTypes testResult = this.testTarget.getTag();

        /* 検証の準備 */
        final KmgJavadocTagTypes actualTag = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTag, actualTag, "タグが正しく返されること");

    }

    /**
     * getTargetStr メソッドのテスト - 正常系:空文字列の対象文字列での対象文字列取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTargetStr_normalEmptyTargetStr() {

        /* 期待値の定義 */
        final String expectedTargetStr = "";

        /* 準備 */
        final String             testTargetStr   = "";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.AUTHOR;
        final String             testValue       = "KenichiroArai";
        final String             testDescription = "クラスやインタフェースの作成者を示す";
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTargetStr();

        /* 検証の準備 */
        final String actualTargetStr = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetStr, actualTargetStr, "空文字列の対象文字列が正しく返されること");

    }

    /**
     * getTargetStr メソッドのテスト - 正常系:nullの対象文字列での対象文字列取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTargetStr_normalNullTargetStr() {

        /* 期待値の定義 */

        /* 準備 */
        final String             testTargetStr   = null;
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.AUTHOR;
        final String             testValue       = "KenichiroArai";
        final String             testDescription = "クラスやインタフェースの作成者を示す";
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTargetStr();

        /* 検証の準備 */
        final String actualTargetStr = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualTargetStr, "nullの対象文字列が正しく返されること");

    }

    /**
     * getTargetStr メソッドのテスト - 正常系:通常の対象文字列での対象文字列取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTargetStr_normalTargetStr() {

        /* 期待値の定義 */
        final String expectedTargetStr = "@author KenichiroArai";

        /* 準備 */
        final String             testTargetStr   = "@author KenichiroArai";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.AUTHOR;
        final String             testValue       = "KenichiroArai";
        final String             testDescription = "クラスやインタフェースの作成者を示す";
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTargetStr();

        /* 検証の準備 */
        final String actualTargetStr = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetStr, actualTargetStr, "対象文字列が正しく返されること");

    }

    /**
     * getValue メソッドのテスト - 正常系:複雑な指定値での指定値取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetValue_normalComplexValue() {

        /* 期待値の定義 */
        final String expectedValue = "param1 パラメータ1の説明";

        /* 準備 */
        final String             testTargetStr   = "@param param1 パラメータ1の説明";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.PARAM;
        final String             testValue       = "param1 パラメータ1の説明";
        final String             testDescription = "メソッドや構築子のパラメータについて説明する";
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getValue();

        /* 検証の準備 */
        final String actualValue = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedValue, actualValue, "複雑な指定値が正しく返されること");

    }

    /**
     * getValue メソッドのテスト - 正常系:空文字列の指定値での指定値取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetValue_normalEmptyValue() {

        /* 期待値の定義 */
        final String expectedValue = "";

        /* 準備 */
        final String             testTargetStr   = "@author KenichiroArai";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.AUTHOR;
        final String             testValue       = "";
        final String             testDescription = "クラスやインタフェースの作成者を示す";
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getValue();

        /* 検証の準備 */
        final String actualValue = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedValue, actualValue, "空文字列の指定値が正しく返されること");

    }

    /**
     * getValue メソッドのテスト - 正常系:nullの指定値での指定値取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetValue_normalNullValue() {

        /* 期待値の定義 */

        /* 準備 */
        final String             testTargetStr   = "@author KenichiroArai";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.AUTHOR;
        final String             testValue       = null;
        final String             testDescription = "クラスやインタフェースの作成者を示す";
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getValue();

        /* 検証の準備 */
        final String actualValue = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualValue, "nullの指定値が正しく返されること");

    }

    /**
     * getValue メソッドのテスト - 正常系:通常の指定値での指定値取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetValue_normalValue() {

        /* 期待値の定義 */
        final String expectedValue = "KenichiroArai";

        /* 準備 */
        final String             testTargetStr   = "@author KenichiroArai";
        final KmgJavadocTagTypes testTag         = KmgJavadocTagTypes.AUTHOR;
        final String             testValue       = "KenichiroArai";
        final String             testDescription = "クラスやインタフェースの作成者を示す";
        this.testTarget = new JavadocTagModelImpl(testTargetStr, testTag, testValue, testDescription);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getValue();

        /* 検証の準備 */
        final String actualValue = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedValue, actualValue, "指定値が正しく返されること");

    }

}
