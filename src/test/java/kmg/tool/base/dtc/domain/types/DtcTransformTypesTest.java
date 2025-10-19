package kmg.tool.base.dtc.domain.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.type.KmgString;

/**
 * テンプレートの動的変換変換処理の種類のテスト<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
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
public class DtcTransformTypesTest {

    /**
     * デフォルトコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public DtcTransformTypesTest() {

        // 処理なし
    }

    /**
     * capitalize メソッドのテスト - 正常系:既に大文字の変換
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   リフレクション例外
     */
    @Test
    public void testCapitalize_normalAlreadyUpperCase() throws Exception {

        /* 期待値の定義 */
        final String expected = "Hello";

        /* 準備 */
        final KmgReflectionModelImpl reflectionModel = new KmgReflectionModelImpl(DtcTransformTypes.class);
        final String                 testValue       = "Hello";

        /* テスト対象の実行 */
        final String actual = (String) reflectionModel.getMethod("capitalize", testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "capitalizeメソッドで既に大文字の場合、そのまま返されること");

    }

    /**
     * capitalize メソッドのテスト - 正常系:空文字列の変換
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   リフレクション例外
     */
    @Test
    public void testCapitalize_normalEmptyString() throws Exception {

        /* 期待値の定義 */
        final String expected = "";

        /* 準備 */
        final KmgReflectionModelImpl reflectionModel = new KmgReflectionModelImpl(DtcTransformTypes.class);
        final String                 testValue       = "";

        /* テスト対象の実行 */
        final String actual = (String) reflectionModel.getMethod("capitalize", testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "capitalizeメソッドで空文字列の場合、空文字列がそのまま返されること");

    }

    /**
     * capitalize メソッドのテスト - 正常系:複数文字の変換
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   リフレクション例外
     */
    @Test
    public void testCapitalize_normalMultipleChars() throws Exception {

        /* 期待値の定義 */
        final String expected = "Hello";

        /* 準備 */
        final KmgReflectionModelImpl reflectionModel = new KmgReflectionModelImpl(DtcTransformTypes.class);
        final String                 testValue       = "hello";

        /* テスト対象の実行 */
        final String actual = (String) reflectionModel.getMethod("capitalize", testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "capitalizeメソッドで複数文字の場合、最初の文字が大文字に変換されること");

    }

    /**
     * capitalize メソッドのテスト - 正常系:1文字の変換
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   リフレクション例外
     */
    @Test
    public void testCapitalize_normalSingleChar() throws Exception {

        /* 期待値の定義 */
        final String expected = "A";

        /* 準備 */
        final KmgReflectionModelImpl reflectionModel = new KmgReflectionModelImpl(DtcTransformTypes.class);
        final String                 testValue       = "a";

        /* テスト対象の実行 */
        final String actual = (String) reflectionModel.getMethod("capitalize", testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "capitalizeメソッドで1文字の場合、大文字に変換されること");

    }

    /**
     * get メソッドのテスト - 正常系:基本的な値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGet_normalBasicValue() {

        /* 期待値の定義 */
        final String expected = "none";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "取得値が一致しません");

    }

    /**
     * get メソッドのテスト - 正常系:CAPITALIZEの値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGet_normalCapitalize() {

        /* 期待値の定義 */
        final String expected = "capitalize";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.CAPITALIZE;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "CAPITALIZEの取得値が一致しません");

    }

    /**
     * get メソッドのテスト - 正常系:TO_LOWER_CASEの値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGet_normalToLowerCase() {

        /* 期待値の定義 */
        final String expected = "toLowerCase";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.TO_LOWER_CASE;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_LOWER_CASEの取得値が一致しません");

    }

    /**
     * get メソッドのテスト - 正常系:TO_UPPER_CASEの値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGet_normalToUpperCase() {

        /* 期待値の定義 */
        final String expected = "toUpperCase";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.TO_UPPER_CASE;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_UPPER_CASEの取得値が一致しません");

    }

    /**
     * getDefault メソッドのテスト - 正常系:デフォルト値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDefault_normalDefaultValue() {

        /* 期待値の定義 */
        final DtcTransformTypes expected = DtcTransformTypes.NONE;

        /* テスト対象の実行 */
        final DtcTransformTypes actual = DtcTransformTypes.getDefault();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "デフォルト値が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:基本的な詳細情報の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDetail_normalBasicDetail() {

        /* 期待値の定義 */
        final String expected = "指定無し";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "詳細情報が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:CAPITALIZEの詳細情報の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDetail_normalCapitalizeDetail() {

        /* 期待値の定義 */
        final String expected = "文字列の最初の文字を大文字に変換する";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.CAPITALIZE;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "CAPITALIZEの詳細情報が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:TO_LOWER_CASEの詳細情報の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDetail_normalToLowerCaseDetail() {

        /* 期待値の定義 */
        final String expected = "文字列のすべての文字を小文字に変換する";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.TO_LOWER_CASE;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_LOWER_CASEの詳細情報が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:TO_UPPER_CASEの詳細情報の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDetail_normalToUpperCaseDetail() {

        /* 期待値の定義 */
        final String expected = "文字列のすべての文字を大文字に変換する";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.TO_UPPER_CASE;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_UPPER_CASEの詳細情報が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:基本的な表示名の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDisplayName_normalBasicDisplayName() {

        /* 期待値の定義 */
        final String expected = "指定無し";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "表示名が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:CAPITALIZEの表示名の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDisplayName_normalCapitalizeDisplayName() {

        /* 期待値の定義 */
        final String expected = "文字列の最初の文字を大文字に変換";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.CAPITALIZE;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "CAPITALIZEの表示名が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:TO_LOWER_CASEの表示名の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDisplayName_normalToLowerCaseDisplayName() {

        /* 期待値の定義 */
        final String expected = "すべて小文字に変換";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.TO_LOWER_CASE;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_LOWER_CASEの表示名が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:TO_UPPER_CASEの表示名の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDisplayName_normalToUpperCaseDisplayName() {

        /* 期待値の定義 */
        final String expected = "すべて大文字に変換";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.TO_UPPER_CASE;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_UPPER_CASEの表示名が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:CAPITALIZEの値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetEnum_normalCapitalizeValue() {

        /* 期待値の定義 */
        final DtcTransformTypes expected = DtcTransformTypes.CAPITALIZE;

        /* 準備 */
        final String testValue = "capitalize";

        /* テスト対象の実行 */
        final DtcTransformTypes actual = DtcTransformTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "CAPITALIZEの値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:存在する値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetEnum_normalExistingValue() {

        /* 期待値の定義 */
        final DtcTransformTypes expected = DtcTransformTypes.NONE;

        /* 準備 */
        final String testValue = "none";

        /* テスト対象の実行 */
        final DtcTransformTypes actual = DtcTransformTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:TO_LOWER_CASEの値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetEnum_normalToLowerCaseValue() {

        /* 期待値の定義 */
        final DtcTransformTypes expected = DtcTransformTypes.TO_LOWER_CASE;

        /* 準備 */
        final String testValue = "toLowerCase";

        /* テスト対象の実行 */
        final DtcTransformTypes actual = DtcTransformTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_LOWER_CASEの値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:TO_UPPER_CASEの値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetEnum_normalToUpperCaseValue() {

        /* 期待値の定義 */
        final DtcTransformTypes expected = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        final String testValue = "toUpperCase";

        /* テスト対象の実行 */
        final DtcTransformTypes actual = DtcTransformTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_UPPER_CASEの値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 準正常系:存在しない値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetEnum_semiNonExistingValue() {

        /* 期待値の定義 */
        final DtcTransformTypes expected = DtcTransformTypes.NONE;

        /* 準備 */
        final String testValue = "INVALID";

        /* テスト対象の実行 */
        final DtcTransformTypes actual = DtcTransformTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "存在しない値の場合、NONEが返されること");

    }

    /**
     * getEnum メソッドのテスト - 準正常系:null値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetEnum_semiNullValue() {

        /* 期待値の定義 */
        final DtcTransformTypes expected = DtcTransformTypes.NONE;

        /* 準備 */
        final String testValue = null;

        /* テスト対象の実行 */
        final DtcTransformTypes actual = DtcTransformTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "null値の場合、NONEが返されること");

    }

    /**
     * getInitValue メソッドのテスト - 正常系:初期値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetInitValue_normalInitialValue() {

        /* 期待値の定義 */
        final DtcTransformTypes expected = DtcTransformTypes.NONE;

        /* テスト対象の実行 */
        final DtcTransformTypes actual = DtcTransformTypes.getInitValue();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "初期値が一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:基本的なキーの取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetKey_normalBasicKey() {

        /* 期待値の定義 */
        final String expected = "none";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "キーが一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:CAPITALIZEのキーの取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetKey_normalCapitalizeKey() {

        /* 期待値の定義 */
        final String expected = "capitalize";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.CAPITALIZE;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "CAPITALIZEのキーが一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:TO_LOWER_CASEのキーの取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetKey_normalToLowerCaseKey() {

        /* 期待値の定義 */
        final String expected = "toLowerCase";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.TO_LOWER_CASE;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_LOWER_CASEのキーが一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:TO_UPPER_CASEのキーの取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetKey_normalToUpperCaseKey() {

        /* 期待値の定義 */
        final String expected = "toUpperCase";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.TO_UPPER_CASE;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_UPPER_CASEのキーが一致しません");

    }

    /**
     * toString メソッドのテスト - 正常系:CAPITALIZEの文字列表現
     *
     * @since 0.2.0
     */
    @Test
    public void testToString_normalCapitalize() {

        /* 期待値の定義 */
        final String expected = "capitalize";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.CAPITALIZE;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "CAPITALIZEの場合、\"capitalize\"が返されること");

    }

    /**
     * toString メソッドのテスト - 正常系:NONEの文字列表現
     *
     * @since 0.2.0
     */
    @Test
    public void testToString_normalNone() {

        /* 期待値の定義 */
        final String expected = "none";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "NONEの場合、\"none\"が返されること");

    }

    /**
     * toString メソッドのテスト - 正常系:TO_LOWER_CASEの文字列表現
     *
     * @since 0.2.0
     */
    @Test
    public void testToString_normalToLowerCase() {

        /* 期待値の定義 */
        final String expected = "toLowerCase";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.TO_LOWER_CASE;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_LOWER_CASEの場合、\"toLowerCase\"が返されること");

    }

    /**
     * toString メソッドのテスト - 正常系:TO_UPPER_CASEの文字列表現
     *
     * @since 0.2.0
     */
    @Test
    public void testToString_normalToUpperCase() {

        /* 期待値の定義 */
        final String expected = "toUpperCase";

        /* 準備 */
        final DtcTransformTypes testType = DtcTransformTypes.TO_UPPER_CASE;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_UPPER_CASEの場合、\"toUpperCase\"が返されること");

    }

    /**
     * transform メソッドのテスト - 正常系:CAPITALIZEの変換
     *
     * @since 0.2.0
     */
    @Test
    public void testTransform_normalCapitalize() {

        /* 期待値の定義 */
        final String expected = "Test";

        /* 準備 */
        final DtcTransformTypes testType  = DtcTransformTypes.CAPITALIZE;
        final String            testValue = "test";

        /* テスト対象の実行 */
        final String actual = testType.transform(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "CAPITALIZEの場合、最初の文字が大文字に変換されること");

    }

    /**
     * transform メソッドのテスト - 正常系:CAPITALIZEで既に大文字の変換
     *
     * @since 0.2.0
     */
    @Test
    public void testTransform_normalCapitalizeAlreadyUpperCase() {

        /* 期待値の定義 */
        final String expected = "Test";

        /* 準備 */
        final DtcTransformTypes testType  = DtcTransformTypes.CAPITALIZE;
        final String            testValue = "Test";

        /* テスト対象の実行 */
        final String actual = testType.transform(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "CAPITALIZEで既に大文字の場合、そのまま返されること");

    }

    /**
     * transform メソッドのテスト - 正常系:CAPITALIZEで複数文字の変換
     *
     * @since 0.2.0
     */
    @Test
    public void testTransform_normalCapitalizeMultipleChars() {

        /* 期待値の定義 */
        final String expected = "Hello";

        /* 準備 */
        final DtcTransformTypes testType  = DtcTransformTypes.CAPITALIZE;
        final String            testValue = "hello";

        /* テスト対象の実行 */
        final String actual = testType.transform(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "CAPITALIZEで複数文字の場合、最初の文字が大文字に変換されること");

    }

    /**
     * transform メソッドのテスト - 正常系:CAPITALIZEで1文字の変換
     *
     * @since 0.2.0
     */
    @Test
    public void testTransform_normalCapitalizeSingleChar() {

        /* 期待値の定義 */
        final String expected = "A";

        /* 準備 */
        final DtcTransformTypes testType  = DtcTransformTypes.CAPITALIZE;
        final String            testValue = "a";

        /* テスト対象の実行 */
        final String actual = testType.transform(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "CAPITALIZEで1文字の場合、大文字に変換されること");

    }

    /**
     * transform メソッドのテスト - 正常系:NONEの変換
     *
     * @since 0.2.0
     */
    @Test
    public void testTransform_normalNone() {

        /* 期待値の定義 */
        final String expected = "test";

        /* 準備 */
        final DtcTransformTypes testType  = DtcTransformTypes.NONE;
        final String            testValue = "test";

        /* テスト対象の実行 */
        final String actual = testType.transform(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "NONEの場合、元の値がそのまま返されること");

    }

    /**
     * transform メソッドのテスト - 正常系:TO_LOWER_CASEの変換
     *
     * @since 0.2.0
     */
    @Test
    public void testTransform_normalToLowerCase() {

        /* 期待値の定義 */
        final String expected = "test";

        /* 準備 */
        final DtcTransformTypes testType  = DtcTransformTypes.TO_LOWER_CASE;
        final String            testValue = "TEST";

        /* テスト対象の実行 */
        final String actual = testType.transform(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_LOWER_CASEの場合、すべての文字が小文字に変換されること");

    }

    /**
     * transform メソッドのテスト - 正常系:TO_LOWER_CASEで特殊文字の変換
     *
     * @since 0.2.0
     */
    @Test
    public void testTransform_normalToLowerCaseSpecialChars() {

        /* 期待値の定義 */
        final String expected = "test123!@#";

        /* 準備 */
        final DtcTransformTypes testType  = DtcTransformTypes.TO_LOWER_CASE;
        final String            testValue = "TEST123!@#";

        /* テスト対象の実行 */
        final String actual = testType.transform(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_LOWER_CASEで特殊文字の場合、アルファベットのみ小文字に変換されること");

    }

    /**
     * transform メソッドのテスト - 正常系:TO_UPPER_CASEの変換
     *
     * @since 0.2.0
     */
    @Test
    public void testTransform_normalToUpperCase() {

        /* 期待値の定義 */
        final String expected = "TEST";

        /* 準備 */
        final DtcTransformTypes testType  = DtcTransformTypes.TO_UPPER_CASE;
        final String            testValue = "test";

        /* テスト対象の実行 */
        final String actual = testType.transform(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_UPPER_CASEの場合、すべての文字が大文字に変換されること");

    }

    /**
     * transform メソッドのテスト - 正常系:TO_UPPER_CASEで特殊文字の変換
     *
     * @since 0.2.0
     */
    @Test
    public void testTransform_normalToUpperCaseSpecialChars() {

        /* 期待値の定義 */
        final String expected = "TEST123!@#";

        /* 準備 */
        final DtcTransformTypes testType  = DtcTransformTypes.TO_UPPER_CASE;
        final String            testValue = "test123!@#";

        /* テスト対象の実行 */
        final String actual = testType.transform(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TO_UPPER_CASEで特殊文字の場合、アルファベットのみ大文字に変換されること");

    }

    /**
     * transform メソッドのテスト - 準正常系:空文字列の変換
     *
     * @since 0.2.0
     */
    @Test
    public void testTransform_semiEmptyString() {

        /* 期待値の定義 */
        final String expected = KmgString.EMPTY;

        /* 準備 */
        final DtcTransformTypes testType  = DtcTransformTypes.TO_UPPER_CASE;
        final String            testValue = "";

        /* テスト対象の実行 */
        final String actual = testType.transform(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "空文字列の場合、空文字列がそのまま返されること");

    }

    /**
     * transform メソッドのテスト - 準正常系:null値の変換
     *
     * @since 0.2.0
     */
    @Test
    public void testTransform_semiNullValue() {

        /* 期待値の定義 */

        /* 準備 */
        final DtcTransformTypes testType  = DtcTransformTypes.CAPITALIZE;
        final String            testValue = null;

        /* テスト対象の実行 */
        final String actual = testType.transform(testValue);

        /* 検証の実施 */
        Assertions.assertNull(actual, "null値の場合、nullがそのまま返されること");

    }
}
