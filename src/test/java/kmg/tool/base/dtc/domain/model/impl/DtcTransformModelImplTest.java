package kmg.tool.base.dtc.domain.model.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.base.dtc.domain.types.DtcTransformTypes;

/**
 * DtcTransformModelImplのテストクラス
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@SuppressWarnings({
    "nls",
})
public class DtcTransformModelImplTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.2.0
     */
    private DtcTransformModelImpl testTarget;

    /**
     * apply メソッドのテスト - 正常系：CAPITALIZE変換処理が適用される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testApply_normalCapitalizeTransformation() {

        /* 期待値の定義 */
        final String            expectedValue             = "testValue";
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.CAPITALIZE;
        final String            expectedTransformedValue  = "TestValue";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        this.testTarget.apply();

        /* 検証の準備 */
        final String actualTransformedValue = this.testTarget.getTransformedValue();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformedValue, actualTransformedValue, "CAPITALIZE変換処理が正しく適用されること");

    }

    /**
     * apply メソッドのテスト - 正常系：NONE変換処理が適用される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testApply_normalNoneTransformation() {

        /* 期待値の定義 */
        final String            expectedValue             = "testValue";
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.NONE;
        final String            expectedTransformedValue  = "testValue";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        this.testTarget.apply();

        /* 検証の準備 */
        final String actualTransformedValue = this.testTarget.getTransformedValue();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformedValue, actualTransformedValue, "NONE変換処理が正しく適用されること");

    }

    /**
     * apply メソッドのテスト - 正常系：TO_LOWER_CASE変換処理が適用される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testApply_normalToLowerCaseTransformation() {

        /* 期待値の定義 */
        final String            expectedValue             = "TESTVALUE";
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_LOWER_CASE;
        final String            expectedTransformedValue  = "testvalue";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        this.testTarget.apply();

        /* 検証の準備 */
        final String actualTransformedValue = this.testTarget.getTransformedValue();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformedValue, actualTransformedValue, "TO_LOWER_CASE変換処理が正しく適用されること");

    }

    /**
     * apply メソッドのテスト - 正常系：TO_UPPER_CASE変換処理が適用される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testApply_normalToUpperCaseTransformation() {

        /* 期待値の定義 */
        final String            expectedValue             = "testValue";
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;
        final String            expectedTransformedValue  = "TESTVALUE";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        this.testTarget.apply();

        /* 検証の準備 */
        final String actualTransformedValue = this.testTarget.getTransformedValue();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformedValue, actualTransformedValue, "TO_UPPER_CASE変換処理が正しく適用されること");

    }

    /**
     * apply メソッドのテスト - 準正常系：空文字の値で変換処理が適用される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testApply_semiEmptyValue() {

        /* 期待値の定義 */
        final String            expectedValue             = "";
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;
        final String            expectedTransformedValue  = "";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        this.testTarget.apply();

        /* 検証の準備 */
        final String actualTransformedValue = this.testTarget.getTransformedValue();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformedValue, actualTransformedValue, "空文字の値で変換処理が正しく適用されること");

    }

    /**
     * apply メソッドのテスト - 準正常系：nullの値で変換処理が適用される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testApply_semiNullValue() {

        /* 期待値の定義 */
        final String            expectedValue             = null;
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;
        final String            expectedTransformedValue  = "";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        this.testTarget.apply();

        /* 検証の準備 */
        final String actualTransformedValue = this.testTarget.getTransformedValue();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformedValue, actualTransformedValue, "nullの値で変換処理が正しく適用されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：正常にインスタンスが作成される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testConstructor_normalCreateInstance() {

        /* 期待値の定義 */
        final String            expectedValue             = "testValue";
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        // コンストラクタの実行は準備段階で完了

        /* 検証の準備 */
        final DtcTransformModelImpl actualTarget = this.testTarget;

        /* 検証の実施 */
        Assertions.assertNotNull(actualTarget, "インスタンスが正常に作成されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：空文字の値でインスタンスが作成される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testConstructor_normalEmptyValue() {

        /* 期待値の定義 */
        final String            expectedValue             = "";
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        // コンストラクタの実行は準備段階で完了

        /* 検証の準備 */
        final DtcTransformModelImpl actualTarget = this.testTarget;

        /* 検証の実施 */
        Assertions.assertNotNull(actualTarget, "空文字の値でインスタンスが正常に作成されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 準正常系：nullの値でインスタンスが作成される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testConstructor_semiNullValue() {

        /* 期待値の定義 */
        final String            expectedValue             = null;
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        // コンストラクタの実行は準備段階で完了

        /* 検証の準備 */
        final DtcTransformModelImpl actualTarget = this.testTarget;

        /* 検証の実施 */
        Assertions.assertNotNull(actualTarget, "nullの値でインスタンスが正常に作成されること");

    }

    /**
     * getOriginalValue メソッドのテスト - 正常系：正常な値が取得される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetOriginalValue_normalGetValue() {

        /* 期待値の定義 */
        final String            expectedValue             = "testValue";
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;
        final String            expectedOriginalValue     = "testValue";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getOriginalValue();

        /* 検証の準備 */
        final String actualOriginalValue = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedOriginalValue, actualOriginalValue, "正常な値が正しく取得されること");

    }

    /**
     * getOriginalValue メソッドのテスト - 準正常系：空文字の値が取得される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetOriginalValue_semiEmptyValue() {

        /* 期待値の定義 */
        final String            expectedValue             = "";
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;
        final String            expectedOriginalValue     = "";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getOriginalValue();

        /* 検証の準備 */
        final String actualOriginalValue = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedOriginalValue, actualOriginalValue, "空文字の値が正しく取得されること");

    }

    /**
     * getOriginalValue メソッドのテスト - 準正常系：nullの値が取得される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetOriginalValue_semiNullValue() {

        /* 期待値の定義 */
        final String            expectedValue             = null;
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;
        final String            expectedOriginalValue     = "";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getOriginalValue();

        /* 検証の準備 */
        final String actualOriginalValue = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedOriginalValue, actualOriginalValue, "nullの値が正しく取得されること");

    }

    /**
     * getTransformedValue メソッドのテスト - 正常系：変換後の値が取得される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetTransformedValue_normalGetValueAfterApply() {

        /* 期待値の定義 */
        final String            expectedValue             = "testValue";
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;
        final String            expectedTransformedValue  = "TESTVALUE";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        this.testTarget.apply();
        final String testResult = this.testTarget.getTransformedValue();

        /* 検証の準備 */
        final String actualTransformedValue = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformedValue, actualTransformedValue, "変換後の値が正しく取得されること");

    }

    /**
     * getTransformedValue メソッドのテスト - 正常系：変換前の値が取得される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetTransformedValue_normalGetValueBeforeApply() {

        /* 期待値の定義 */
        final String            expectedValue             = "testValue";
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;
        final String            expectedTransformedValue  = "testValue";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTransformedValue();

        /* 検証の準備 */
        final String actualTransformedValue = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformedValue, actualTransformedValue, "変換前の値が正しく取得されること");

    }

    /**
     * getTransformedValue メソッドのテスト - 準正常系：空文字の値が取得される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetTransformedValue_semiEmptyValue() {

        /* 期待値の定義 */
        final String            expectedValue             = "";
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;
        final String            expectedTransformedValue  = "";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTransformedValue();

        /* 検証の準備 */
        final String actualTransformedValue = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformedValue, actualTransformedValue, "空文字の値が正しく取得されること");

    }

    /**
     * getTransformedValue メソッドのテスト - 準正常系：nullの値が取得される場合
     *
     * @since 0.2.0
     */
    @Test
    public void testGetTransformedValue_semiNullValue() {

        /* 期待値の定義 */
        final String            expectedValue             = null;
        final DtcTransformTypes expectedDtcTransformTypes = DtcTransformTypes.TO_UPPER_CASE;
        final String            expectedTransformedValue  = "";

        /* 準備 */
        this.testTarget = new DtcTransformModelImpl(expectedValue, expectedDtcTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTransformedValue();

        /* 検証の準備 */
        final String actualTransformedValue = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformedValue, actualTransformedValue, "nullの値が正しく取得されること");

    }

}
