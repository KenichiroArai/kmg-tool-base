package kmg.tool.base.cmn.infrastructure.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import kmg.core.infrastructure.model.val.KmgValsModel;
import kmg.core.infrastructure.model.val.impl.KmgValsModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;

/**
 * KMGツールバリデーション例外テスト
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
public class KmgToolValExceptionTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.2.0
     */
    private KmgToolValException testTarget;

    /**
     * getCause メソッドのテスト - 正常系：原因がnullの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetCause_normalCauseNull() throws Exception {

        /* 期待値の定義 */
        final KmgValsModel expectedValidationsModel = new KmgValsModelImpl();

        /* 準備 */
        this.testTarget = new KmgToolValException(expectedValidationsModel);

        /* テスト対象の実行 */
        final Throwable testResult = this.testTarget.getCause();

        /* 検証の準備 */
        final Throwable actualCause = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualCause, "原因がnullで正しく取得されること");

    }

    /**
     * getCause メソッドのテスト - 正常系：原因が取得される場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetCause_normalGetCause() throws Exception {

        /* 期待値の定義 */
        final KmgValsModel expectedValidationsModel = new KmgValsModelImpl();
        final Throwable    expectedCause            = new RuntimeException("テスト例外");

        /* 準備 */
        this.testTarget = new KmgToolValException(expectedValidationsModel, expectedCause);

        /* テスト対象の実行 */
        final Throwable testResult = this.testTarget.getCause();

        /* 検証の準備 */
        final Throwable actualCause = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedCause, actualCause, "原因が正しく取得されること");

    }

    /**
     * getValidationsModel メソッドのテスト - 正常系：バリデーションモデルが取得される場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetValidationsModel_normalGetValidationsModel() throws Exception {

        /* 期待値の定義 */
        final KmgValsModel expectedValidationsModel = new KmgValsModelImpl();

        /* 準備 */
        this.testTarget = new KmgToolValException(expectedValidationsModel);

        /* テスト対象の実行 */
        final KmgValsModel testResult = this.testTarget.getValidationsModel();

        /* 検証の準備 */
        final KmgValsModel actualValidationsModel = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedValidationsModel, actualValidationsModel, "バリデーションモデルが正しく取得されること");

    }

    /**
     * getValidationsModel メソッドのテスト - 正常系：バリデーションモデルがnullの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetValidationsModel_normalValidationsModelNull() throws Exception {

        /* 期待値の定義 */
        final KmgValsModel expectedValidationsModel = null;

        /* 準備 */
        this.testTarget = new KmgToolValException(expectedValidationsModel);

        /* テスト対象の実行 */
        final KmgValsModel testResult = this.testTarget.getValidationsModel();

        /* 検証の準備 */
        final KmgValsModel actualValidationsModel = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedValidationsModel, actualValidationsModel, "バリデーションモデルがnullで正しく取得されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：原因がnullの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testKmgToolValException_normalCauseNull() throws Exception {

        /* 期待値の定義 */
        final KmgValsModel expectedValidationsModel = new KmgValsModelImpl();
        final Throwable    expectedCause            = null;

        /* 準備 */

        /* テスト対象の実行 */
        this.testTarget = new KmgToolValException(expectedValidationsModel, expectedCause);

        /* 検証の準備 */
        final KmgValsModel actualValidationsModel = this.testTarget.getValidationsModel();
        final Throwable    actualCause            = this.testTarget.getCause();

        /* 検証の実施 */
        Assertions.assertEquals(expectedValidationsModel, actualValidationsModel, "バリデーションモデルが正しく設定されていること");
        Assertions.assertEquals(expectedCause, actualCause, "原因がnullで正しく設定されていること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：モックバリデーションモデルの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testKmgToolValException_normalMockValidationsModel() throws Exception {

        /* 期待値の定義 */
        final KmgValsModel expectedValidationsModel = Mockito.mock(KmgValsModel.class);

        /* 準備 */

        /* テスト対象の実行 */
        this.testTarget = new KmgToolValException(expectedValidationsModel);

        /* 検証の準備 */
        final KmgValsModel actualValidationsModel = this.testTarget.getValidationsModel();

        /* 検証の実施 */
        Assertions.assertEquals(expectedValidationsModel, actualValidationsModel, "モックバリデーションモデルが正しく設定されていること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：モックバリデーションモデルと原因の場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testKmgToolValException_normalMockValidationsModelAndCause() throws Exception {

        /* 期待値の定義 */
        final KmgValsModel expectedValidationsModel = Mockito.mock(KmgValsModel.class);
        final Throwable    expectedCause            = new RuntimeException("テスト例外");

        /* 準備 */

        /* テスト対象の実行 */
        this.testTarget = new KmgToolValException(expectedValidationsModel, expectedCause);

        /* 検証の準備 */
        final KmgValsModel actualValidationsModel = this.testTarget.getValidationsModel();
        final Throwable    actualCause            = this.testTarget.getCause();

        /* 検証の実施 */
        Assertions.assertEquals(expectedValidationsModel, actualValidationsModel, "モックバリデーションモデルが正しく設定されていること");
        Assertions.assertEquals(expectedCause, actualCause, "原因が正しく設定されていること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：バリデーションモデルと原因の場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testKmgToolValException_normalValidationsModelAndCause() throws Exception {

        /* 期待値の定義 */
        final KmgValsModel expectedValidationsModel = new KmgValsModelImpl();
        final Throwable    expectedCause            = new RuntimeException("テスト例外");

        /* 準備 */

        /* テスト対象の実行 */
        this.testTarget = new KmgToolValException(expectedValidationsModel, expectedCause);

        /* 検証の準備 */
        final KmgValsModel actualValidationsModel = this.testTarget.getValidationsModel();
        final Throwable    actualCause            = this.testTarget.getCause();

        /* 検証の実施 */
        Assertions.assertEquals(expectedValidationsModel, actualValidationsModel, "バリデーションモデルが正しく設定されていること");
        Assertions.assertEquals(expectedCause, actualCause, "原因が正しく設定されていること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：バリデーションモデルと原因が両方nullの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testKmgToolValException_normalValidationsModelAndCauseBothNull() throws Exception {

        /* 期待値の定義 */
        final KmgValsModel expectedValidationsModel = null;
        final Throwable    expectedCause            = null;

        /* 準備 */

        /* テスト対象の実行 */
        this.testTarget = new KmgToolValException(expectedValidationsModel, expectedCause);

        /* 検証の準備 */
        final KmgValsModel actualValidationsModel = this.testTarget.getValidationsModel();
        final Throwable    actualCause            = this.testTarget.getCause();

        /* 検証の実施 */
        Assertions.assertEquals(expectedValidationsModel, actualValidationsModel, "バリデーションモデルがnullで正しく設定されていること");
        Assertions.assertEquals(expectedCause, actualCause, "原因がnullで正しく設定されていること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：バリデーションモデルがnullの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testKmgToolValException_normalValidationsModelNull() throws Exception {

        /* 期待値の定義 */
        final KmgValsModel expectedValidationsModel = null;

        /* 準備 */

        /* テスト対象の実行 */
        this.testTarget = new KmgToolValException(expectedValidationsModel);

        /* 検証の準備 */
        final KmgValsModel actualValidationsModel = this.testTarget.getValidationsModel();

        /* 検証の実施 */
        Assertions.assertEquals(expectedValidationsModel, actualValidationsModel, "バリデーションモデルがnullで正しく設定されていること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：バリデーションモデルがnullで原因が設定されている場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testKmgToolValException_normalValidationsModelNullAndCause() throws Exception {

        /* 期待値の定義 */
        final KmgValsModel expectedValidationsModel = null;
        final Throwable    expectedCause            = new RuntimeException("テスト例外");

        /* 準備 */

        /* テスト対象の実行 */
        this.testTarget = new KmgToolValException(expectedValidationsModel, expectedCause);

        /* 検証の準備 */
        final KmgValsModel actualValidationsModel = this.testTarget.getValidationsModel();
        final Throwable    actualCause            = this.testTarget.getCause();

        /* 検証の実施 */
        Assertions.assertEquals(expectedValidationsModel, actualValidationsModel, "バリデーションモデルがnullで正しく設定されていること");
        Assertions.assertEquals(expectedCause, actualCause, "原因が正しく設定されていること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：バリデーションモデルのみの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testKmgToolValException_normalValidationsModelOnly() throws Exception {

        /* 期待値の定義 */
        final KmgValsModel expectedValidationsModel = new KmgValsModelImpl();

        /* 準備 */

        /* テスト対象の実行 */
        this.testTarget = new KmgToolValException(expectedValidationsModel);

        /* 検証の準備 */
        final KmgValsModel actualValidationsModel = this.testTarget.getValidationsModel();

        /* 検証の実施 */
        Assertions.assertEquals(expectedValidationsModel, actualValidationsModel, "バリデーションモデルが正しく設定されていること");

    }

}
