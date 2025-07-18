package kmg.tool.dtc.domain.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * テンプレートの動的変換のキーの種類のテスト<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
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
public class DtcKeyTypesTest {

    /**
     * デフォルトコンストラクタ<br>
     *
     * @since 0.1.0
     */
    public DtcKeyTypesTest() {

        // 処理なし
    }

    /**
     * get メソッドのテスト - 正常系:基本的な値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGet_normalBasicValue() {

        /* 期待値の定義 */
        final String expected = "None";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "取得値が一致しません");

    }

    /**
     * get メソッドのテスト - 正常系:DERIVED_PLACEHOLDERSの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGet_normalDerivedPlaceholders() {

        /* 期待値の定義 */
        final String expected = "derivedPlaceholders";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.DERIVED_PLACEHOLDERS;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DERIVED_PLACEHOLDERSの取得値が一致しません");

    }

    /**
     * get メソッドのテスト - 正常系:DISPLAY_NAMEの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGet_normalDisplayName() {

        /* 期待値の定義 */
        final String expected = "displayName";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.DISPLAY_NAME;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DISPLAY_NAMEの取得値が一致しません");

    }

    /**
     * get メソッドのテスト - 正常系:INTERMEDIATE_PLACEHOLDERSの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGet_normalIntermediatePlaceholders() {

        /* 期待値の定義 */
        final String expected = "intermediatePlaceholders";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.INTERMEDIATE_PLACEHOLDERS;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "INTERMEDIATE_PLACEHOLDERSの取得値が一致しません");

    }

    /**
     * get メソッドのテスト - 正常系:REPLACEMENT_PATTERNの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGet_normalReplacementPattern() {

        /* 期待値の定義 */
        final String expected = "replacementPattern";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.REPLACEMENT_PATTERN;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "REPLACEMENT_PATTERNの取得値が一致しません");

    }

    /**
     * get メソッドのテスト - 正常系:SOURCE_KEYの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGet_normalSourceKey() {

        /* 期待値の定義 */
        final String expected = "sourceKey";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.SOURCE_KEY;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "SOURCE_KEYの取得値が一致しません");

    }

    /**
     * get メソッドのテスト - 正常系:TEMPLATE_CONTENTの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGet_normalTemplateContent() {

        /* 期待値の定義 */
        final String expected = "templateContent";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.TEMPLATE_CONTENT;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TEMPLATE_CONTENTの取得値が一致しません");

    }

    /**
     * get メソッドのテスト - 正常系:TRANSFORMATIONの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGet_normalTransformation() {

        /* 期待値の定義 */
        final String expected = "transformation";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.TRANSFORMATION;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TRANSFORMATIONの取得値が一致しません");

    }

    /**
     * getDefault メソッドのテスト - 正常系:デフォルト値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDefault_normalDefaultValue() {

        /* 期待値の定義 */
        final DtcKeyTypes expected = DtcKeyTypes.NONE;

        /* テスト対象の実行 */
        final DtcKeyTypes actual = DtcKeyTypes.getDefault();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "デフォルト値が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:基本的な詳細情報の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDetail_normalBasicDetail() {

        /* 期待値の定義 */
        final String expected = "指定無し";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "詳細情報が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:DERIVED_PLACEHOLDERSの詳細情報の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDetail_normalDerivedPlaceholdersDetail() {

        /* 期待値の定義 */
        final String expected = "他のプレースホルダーから派生するプレースホルダー定義のキー";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.DERIVED_PLACEHOLDERS;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DERIVED_PLACEHOLDERSの詳細情報が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:DISPLAY_NAMEの詳細情報の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDetail_normalDisplayNameDetail() {

        /* 期待値の定義 */
        final String expected = "表示名のキー";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.DISPLAY_NAME;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DISPLAY_NAMEの詳細情報が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:INTERMEDIATE_PLACEHOLDERSの詳細情報の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDetail_normalIntermediatePlaceholdersDetail() {

        /* 期待値の定義 */
        final String expected = "中間から直接取得するプレースホルダー定義のキー";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.INTERMEDIATE_PLACEHOLDERS;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "INTERMEDIATE_PLACEHOLDERSの詳細情報が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:REPLACEMENT_PATTERNの詳細情報の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDetail_normalReplacementPatternDetail() {

        /* 期待値の定義 */
        final String expected = "置換パターンのキー";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.REPLACEMENT_PATTERN;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "REPLACEMENT_PATTERNの詳細情報が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:SOURCE_KEYの詳細情報の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDetail_normalSourceKeyDetail() {

        /* 期待値の定義 */
        final String expected = "変換元となるプレースホルダーのキー";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.SOURCE_KEY;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "SOURCE_KEYの詳細情報が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:TEMPLATE_CONTENTの詳細情報の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDetail_normalTemplateContentDetail() {

        /* 期待値の定義 */
        final String expected = "テンプレート内容のキー";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.TEMPLATE_CONTENT;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TEMPLATE_CONTENTの詳細情報が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:TRANSFORMATIONの詳細情報の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDetail_normalTransformationDetail() {

        /* 期待値の定義 */
        final String expected = "変換処理のキー";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.TRANSFORMATION;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TRANSFORMATIONの詳細情報が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:基本的な表示名の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDisplayName_normalBasicDisplayName() {

        /* 期待値の定義 */
        final String expected = "指定無し";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "表示名が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:DERIVED_PLACEHOLDERSの表示名の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDisplayName_normalDerivedPlaceholdersDisplayName() {

        /* 期待値の定義 */
        final String expected = "派生プレースホルダー定義";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.DERIVED_PLACEHOLDERS;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DERIVED_PLACEHOLDERSの表示名が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:DISPLAY_NAMEの表示名の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDisplayName_normalDisplayNameDisplayName() {

        /* 期待値の定義 */
        final String expected = "表示名";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.DISPLAY_NAME;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DISPLAY_NAMEの表示名が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:INTERMEDIATE_PLACEHOLDERSの表示名の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDisplayName_normalIntermediatePlaceholdersDisplayName() {

        /* 期待値の定義 */
        final String expected = "中間プレースホルダー定義";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.INTERMEDIATE_PLACEHOLDERS;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "INTERMEDIATE_PLACEHOLDERSの表示名が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:REPLACEMENT_PATTERNの表示名の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDisplayName_normalReplacementPatternDisplayName() {

        /* 期待値の定義 */
        final String expected = "置換パターン";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.REPLACEMENT_PATTERN;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "REPLACEMENT_PATTERNの表示名が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:SOURCE_KEYの表示名の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDisplayName_normalSourceKeyDisplayName() {

        /* 期待値の定義 */
        final String expected = "ソースキー";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.SOURCE_KEY;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "SOURCE_KEYの表示名が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:TEMPLATE_CONTENTの表示名の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDisplayName_normalTemplateContentDisplayName() {

        /* 期待値の定義 */
        final String expected = "テンプレート内容";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.TEMPLATE_CONTENT;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TEMPLATE_CONTENTの表示名が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:TRANSFORMATIONの表示名の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDisplayName_normalTransformationDisplayName() {

        /* 期待値の定義 */
        final String expected = "変換処理";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.TRANSFORMATION;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TRANSFORMATIONの表示名が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:DERIVED_PLACEHOLDERSの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_normalDerivedPlaceholdersValue() {

        /* 期待値の定義 */
        final DtcKeyTypes expected = DtcKeyTypes.DERIVED_PLACEHOLDERS;

        /* 準備 */
        final String testValue = "derivedPlaceholders";

        /* テスト対象の実行 */
        final DtcKeyTypes actual = DtcKeyTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DERIVED_PLACEHOLDERSの値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:DISPLAY_NAMEの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_normalDisplayNameValue() {

        /* 期待値の定義 */
        final DtcKeyTypes expected = DtcKeyTypes.DISPLAY_NAME;

        /* 準備 */
        final String testValue = "displayName";

        /* テスト対象の実行 */
        final DtcKeyTypes actual = DtcKeyTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DISPLAY_NAMEの値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:存在する値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_normalExistingValue() {

        /* 期待値の定義 */
        final DtcKeyTypes expected = DtcKeyTypes.NONE;

        /* 準備 */
        final String testValue = "None";

        /* テスト対象の実行 */
        final DtcKeyTypes actual = DtcKeyTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:INTERMEDIATE_PLACEHOLDERSの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_normalIntermediatePlaceholdersValue() {

        /* 期待値の定義 */
        final DtcKeyTypes expected = DtcKeyTypes.INTERMEDIATE_PLACEHOLDERS;

        /* 準備 */
        final String testValue = "intermediatePlaceholders";

        /* テスト対象の実行 */
        final DtcKeyTypes actual = DtcKeyTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "INTERMEDIATE_PLACEHOLDERSの値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:REPLACEMENT_PATTERNの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_normalReplacementPatternValue() {

        /* 期待値の定義 */
        final DtcKeyTypes expected = DtcKeyTypes.REPLACEMENT_PATTERN;

        /* 準備 */
        final String testValue = "replacementPattern";

        /* テスト対象の実行 */
        final DtcKeyTypes actual = DtcKeyTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "REPLACEMENT_PATTERNの値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:SOURCE_KEYの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_normalSourceKeyValue() {

        /* 期待値の定義 */
        final DtcKeyTypes expected = DtcKeyTypes.SOURCE_KEY;

        /* 準備 */
        final String testValue = "sourceKey";

        /* テスト対象の実行 */
        final DtcKeyTypes actual = DtcKeyTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "SOURCE_KEYの値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:TEMPLATE_CONTENTの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_normalTemplateContentValue() {

        /* 期待値の定義 */
        final DtcKeyTypes expected = DtcKeyTypes.TEMPLATE_CONTENT;

        /* 準備 */
        final String testValue = "templateContent";

        /* テスト対象の実行 */
        final DtcKeyTypes actual = DtcKeyTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TEMPLATE_CONTENTの値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:TRANSFORMATIONの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_normalTransformationValue() {

        /* 期待値の定義 */
        final DtcKeyTypes expected = DtcKeyTypes.TRANSFORMATION;

        /* 準備 */
        final String testValue = "transformation";

        /* テスト対象の実行 */
        final DtcKeyTypes actual = DtcKeyTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TRANSFORMATIONの値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 準正常系:存在しない値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_semiNonExistingValue() {

        /* 期待値の定義 */
        final DtcKeyTypes expected = DtcKeyTypes.NONE;

        /* 準備 */
        final String testValue = "INVALID";

        /* テスト対象の実行 */
        final DtcKeyTypes actual = DtcKeyTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "存在しない値の場合、NONEが返されること");

    }

    /**
     * getEnum メソッドのテスト - 準正常系:null値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_semiNullValue() {

        /* 期待値の定義 */
        final DtcKeyTypes expected = DtcKeyTypes.NONE;

        /* 準備 */
        final String testValue = null;

        /* テスト対象の実行 */
        final DtcKeyTypes actual = DtcKeyTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "null値の場合、NONEが返されること");

    }

    /**
     * getInitValue メソッドのテスト - 正常系:初期値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetInitValue_normalInitialValue() {

        /* 期待値の定義 */
        final DtcKeyTypes expected = DtcKeyTypes.NONE;

        /* テスト対象の実行 */
        final DtcKeyTypes actual = DtcKeyTypes.getInitValue();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "初期値が一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:基本的なキーの取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalBasicKey() {

        /* 期待値の定義 */
        final String expected = "None";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "キーが一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:DERIVED_PLACEHOLDERSのキーの取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalDerivedPlaceholdersKey() {

        /* 期待値の定義 */
        final String expected = "derivedPlaceholders";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.DERIVED_PLACEHOLDERS;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DERIVED_PLACEHOLDERSのキーが一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:DISPLAY_NAMEのキーの取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalDisplayNameKey() {

        /* 期待値の定義 */
        final String expected = "displayName";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.DISPLAY_NAME;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DISPLAY_NAMEのキーが一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:INTERMEDIATE_PLACEHOLDERSのキーの取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalIntermediatePlaceholdersKey() {

        /* 期待値の定義 */
        final String expected = "intermediatePlaceholders";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.INTERMEDIATE_PLACEHOLDERS;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "INTERMEDIATE_PLACEHOLDERSのキーが一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:REPLACEMENT_PATTERNのキーの取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalReplacementPatternKey() {

        /* 期待値の定義 */
        final String expected = "replacementPattern";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.REPLACEMENT_PATTERN;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "REPLACEMENT_PATTERNのキーが一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:SOURCE_KEYのキーの取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalSourceKeyKey() {

        /* 期待値の定義 */
        final String expected = "sourceKey";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.SOURCE_KEY;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "SOURCE_KEYのキーが一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:TEMPLATE_CONTENTのキーの取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalTemplateContentKey() {

        /* 期待値の定義 */
        final String expected = "templateContent";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.TEMPLATE_CONTENT;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TEMPLATE_CONTENTのキーが一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:TRANSFORMATIONのキーの取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalTransformationKey() {

        /* 期待値の定義 */
        final String expected = "transformation";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.TRANSFORMATION;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TRANSFORMATIONのキーが一致しません");

    }

    /**
     * toString メソッドのテスト - 正常系:DERIVED_PLACEHOLDERSの文字列表現
     *
     * @since 0.1.0
     */
    @Test
    public void testToString_normalDerivedPlaceholders() {

        /* 期待値の定義 */
        final String expected = "derivedPlaceholders";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.DERIVED_PLACEHOLDERS;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DERIVED_PLACEHOLDERSの場合、\"derivedPlaceholders\"が返されること");

    }

    /**
     * toString メソッドのテスト - 正常系:DISPLAY_NAMEの文字列表現
     *
     * @since 0.1.0
     */
    @Test
    public void testToString_normalDisplayName() {

        /* 期待値の定義 */
        final String expected = "displayName";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.DISPLAY_NAME;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "DISPLAY_NAMEの場合、\"displayName\"が返されること");

    }

    /**
     * toString メソッドのテスト - 正常系:INTERMEDIATE_PLACEHOLDERSの文字列表現
     *
     * @since 0.1.0
     */
    @Test
    public void testToString_normalIntermediatePlaceholders() {

        /* 期待値の定義 */
        final String expected = "intermediatePlaceholders";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.INTERMEDIATE_PLACEHOLDERS;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "INTERMEDIATE_PLACEHOLDERSの場合、\"intermediatePlaceholders\"が返されること");

    }

    /**
     * toString メソッドのテスト - 正常系:NONEの文字列表現
     *
     * @since 0.1.0
     */
    @Test
    public void testToString_normalNone() {

        /* 期待値の定義 */
        final String expected = "None";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "NONEの場合、\"None\"が返されること");

    }

    /**
     * toString メソッドのテスト - 正常系:REPLACEMENT_PATTERNの文字列表現
     *
     * @since 0.1.0
     */
    @Test
    public void testToString_normalReplacementPattern() {

        /* 期待値の定義 */
        final String expected = "replacementPattern";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.REPLACEMENT_PATTERN;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "REPLACEMENT_PATTERNの場合、\"replacementPattern\"が返されること");

    }

    /**
     * toString メソッドのテスト - 正常系:SOURCE_KEYの文字列表現
     *
     * @since 0.1.0
     */
    @Test
    public void testToString_normalSourceKey() {

        /* 期待値の定義 */
        final String expected = "sourceKey";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.SOURCE_KEY;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "SOURCE_KEYの場合、\"sourceKey\"が返されること");

    }

    /**
     * toString メソッドのテスト - 正常系:TEMPLATE_CONTENTの文字列表現
     *
     * @since 0.1.0
     */
    @Test
    public void testToString_normalTemplateContent() {

        /* 期待値の定義 */
        final String expected = "templateContent";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.TEMPLATE_CONTENT;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TEMPLATE_CONTENTの場合、\"templateContent\"が返されること");

    }

    /**
     * toString メソッドのテスト - 正常系:TRANSFORMATIONの文字列表現
     *
     * @since 0.1.0
     */
    @Test
    public void testToString_normalTransformation() {

        /* 期待値の定義 */
        final String expected = "transformation";

        /* 準備 */
        final DtcKeyTypes testType = DtcKeyTypes.TRANSFORMATION;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "TRANSFORMATIONの場合、\"transformation\"が返されること");

    }
}
