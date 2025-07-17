package kmg.tool.dtc.domain.model.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.dtc.domain.types.DtcTransformTypes;

/**
 * DtcDerivedPlaceholderModelImplのテストクラス
 *
 * @author KenichiroArai
 */
@SuppressWarnings({
    "nls",
})
public class DtcDerivedPlaceholderModelImplTest extends AbstractKmgTest {

    /** テスト対象 */
    private DtcDerivedPlaceholderModelImpl testTarget;

    /**
     * コンストラクタ メソッドのテスト - 正常系：正常にインスタンスが作成される場合
     */
    @Test
    public void testConstructor_normalCreateInstance() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "テスト表示名";
        final String            expectedReplacementPattern = "${TEST}";
        final String            expectedSourceKey          = "testKey";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        // コンストラクタの実行は準備段階で完了

        /* 検証の準備 */
        final DtcDerivedPlaceholderModelImpl actualTarget = this.testTarget;

        /* 検証の実施 */
        Assertions.assertNotNull(actualTarget, "インスタンスが正常に作成されること");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系：空文字の表示名が正しく取得される場合
     */
    @Test
    public void testGetDisplayName_normalEmptyDisplayName() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "";
        final String            expectedReplacementPattern = "${TEST}";
        final String            expectedSourceKey          = "testKey";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getDisplayName();

        /* 検証の準備 */
        final String actualDisplayName = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedDisplayName, actualDisplayName, "空文字の表示名が正しく取得されること");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系：表示名が正しく取得される場合
     */
    @Test
    public void testGetDisplayName_normalGetDisplayName() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "テスト表示名";
        final String            expectedReplacementPattern = "${TEST}";
        final String            expectedSourceKey          = "testKey";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getDisplayName();

        /* 検証の準備 */
        final String actualDisplayName = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedDisplayName, actualDisplayName, "表示名が正しく取得されること");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系：nullの表示名が正しく取得される場合
     */
    @Test
    public void testGetDisplayName_normalNullDisplayName() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = null;
        final String            expectedReplacementPattern = "${TEST}";
        final String            expectedSourceKey          = "testKey";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getDisplayName();

        /* 検証の準備 */
        final String actualDisplayName = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedDisplayName, actualDisplayName, "nullの表示名が正しく取得されること");

    }

    /**
     * getReplacementPattern メソッドのテスト - 正常系：空文字の置換パターンが正しく取得される場合
     */
    @Test
    public void testGetReplacementPattern_normalEmptyReplacementPattern() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "テスト表示名";
        final String            expectedReplacementPattern = "";
        final String            expectedSourceKey          = "testKey";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getReplacementPattern();

        /* 検証の準備 */
        final String actualReplacementPattern = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedReplacementPattern, actualReplacementPattern, "空文字の置換パターンが正しく取得されること");

    }

    /**
     * getReplacementPattern メソッドのテスト - 正常系：置換パターンが正しく取得される場合
     */
    @Test
    public void testGetReplacementPattern_normalGetReplacementPattern() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "テスト表示名";
        final String            expectedReplacementPattern = "${TEST}";
        final String            expectedSourceKey          = "testKey";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getReplacementPattern();

        /* 検証の準備 */
        final String actualReplacementPattern = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedReplacementPattern, actualReplacementPattern, "置換パターンが正しく取得されること");

    }

    /**
     * getReplacementPattern メソッドのテスト - 正常系：nullの置換パターンが正しく取得される場合
     */
    @Test
    public void testGetReplacementPattern_normalNullReplacementPattern() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "テスト表示名";
        final String            expectedReplacementPattern = null;
        final String            expectedSourceKey          = "testKey";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getReplacementPattern();

        /* 検証の準備 */
        final String actualReplacementPattern = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedReplacementPattern, actualReplacementPattern, "nullの置換パターンが正しく取得されること");

    }

    /**
     * getSourceKey メソッドのテスト - 正常系：空文字のソースキーが正しく取得される場合
     */
    @Test
    public void testGetSourceKey_normalEmptySourceKey() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "テスト表示名";
        final String            expectedReplacementPattern = "${TEST}";
        final String            expectedSourceKey          = "";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getSourceKey();

        /* 検証の準備 */
        final String actualSourceKey = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSourceKey, actualSourceKey, "空文字のソースキーが正しく取得されること");

    }

    /**
     * getSourceKey メソッドのテスト - 正常系：ソースキーが正しく取得される場合
     */
    @Test
    public void testGetSourceKey_normalGetSourceKey() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "テスト表示名";
        final String            expectedReplacementPattern = "${TEST}";
        final String            expectedSourceKey          = "testKey";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getSourceKey();

        /* 検証の準備 */
        final String actualSourceKey = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSourceKey, actualSourceKey, "ソースキーが正しく取得されること");

    }

    /**
     * getSourceKey メソッドのテスト - 正常系：nullのソースキーが正しく取得される場合
     */
    @Test
    public void testGetSourceKey_normalNullSourceKey() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "テスト表示名";
        final String            expectedReplacementPattern = "${TEST}";
        final String            expectedSourceKey          = null;
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getSourceKey();

        /* 検証の準備 */
        final String actualSourceKey = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSourceKey, actualSourceKey, "nullのソースキーが正しく取得されること");

    }

    /**
     * getTransformationTypes メソッドのテスト - 正常系：CAPITALIZEの変換処理の種類が正しく取得される場合
     */
    @Test
    public void testGetTransformationTypes_normalCapitalizeTransformationTypes() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "テスト表示名";
        final String            expectedReplacementPattern = "${TEST}";
        final String            expectedSourceKey          = "testKey";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.CAPITALIZE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final DtcTransformTypes testResult = this.testTarget.getTransformationTypes();

        /* 検証の準備 */
        final DtcTransformTypes actualTransformTypes = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformTypes, actualTransformTypes, "CAPITALIZEの変換処理の種類が正しく取得されること");

    }

    /**
     * getTransformationTypes メソッドのテスト - 正常系：変換処理の種類が正しく取得される場合
     */
    @Test
    public void testGetTransformationTypes_normalGetTransformationTypes() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "テスト表示名";
        final String            expectedReplacementPattern = "${TEST}";
        final String            expectedSourceKey          = "testKey";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.TO_UPPER_CASE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final DtcTransformTypes testResult = this.testTarget.getTransformationTypes();

        /* 検証の準備 */
        final DtcTransformTypes actualTransformTypes = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformTypes, actualTransformTypes, "変換処理の種類が正しく取得されること");

    }

    /**
     * getTransformationTypes メソッドのテスト - 正常系：NONEの変換処理の種類が正しく取得される場合
     */
    @Test
    public void testGetTransformationTypes_normalNoneTransformationTypes() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "テスト表示名";
        final String            expectedReplacementPattern = "${TEST}";
        final String            expectedSourceKey          = "testKey";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.NONE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final DtcTransformTypes testResult = this.testTarget.getTransformationTypes();

        /* 検証の準備 */
        final DtcTransformTypes actualTransformTypes = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformTypes, actualTransformTypes, "NONEの変換処理の種類が正しく取得されること");

    }

    /**
     * getTransformationTypes メソッドのテスト - 正常系：TO_LOWER_CASEの変換処理の種類が正しく取得される場合
     */
    @Test
    public void testGetTransformationTypes_normalToLowerCaseTransformationTypes() {

        /* 期待値の定義 */
        final String            expectedDisplayName        = "テスト表示名";
        final String            expectedReplacementPattern = "${TEST}";
        final String            expectedSourceKey          = "testKey";
        final DtcTransformTypes expectedTransformTypes     = DtcTransformTypes.TO_LOWER_CASE;

        /* 準備 */
        this.testTarget = new DtcDerivedPlaceholderModelImpl(expectedDisplayName, expectedReplacementPattern,
            expectedSourceKey, expectedTransformTypes);

        /* テスト対象の実行 */
        final DtcTransformTypes testResult = this.testTarget.getTransformationTypes();

        /* 検証の準備 */
        final DtcTransformTypes actualTransformTypes = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTransformTypes, actualTransformTypes, "TO_LOWER_CASEの変換処理の種類が正しく取得されること");

    }

}
