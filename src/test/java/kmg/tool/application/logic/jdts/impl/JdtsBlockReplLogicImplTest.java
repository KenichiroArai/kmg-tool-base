package kmg.tool.application.logic.jdts.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kmg.tool.application.model.jdts.JdtsConfigsModel;

/**
 * Javadocタグ設定のブロック置換ロジック実装のテスト<br>
 *
 * @author KenichiroArai
 */
@SuppressWarnings({
    "nls", "static-method"
})
public class JdtsBlockReplLogicImplTest {

    /**
     * デフォルトコンストラクタ<br>
     */
    public JdtsBlockReplLogicImplTest() {

        // 処理なし
    }

    /**
     * getConfigsModel メソッドのテスト - 正常系:初期状態でnullが返されることの確認
     * <p>
     * 初期化前の状態で設定モデルを取得した場合にnullが返されることを確認します。
     * </p>
     */
    @Test
    public void testGetConfigsModel_normalInitialStateReturnsNull() {

        /* 期待値の定義 */
        final JdtsConfigsModel expectedConfigsModel = null;

        /* 準備 */
        final JdtsBlockReplLogicImpl testTarget = new JdtsBlockReplLogicImpl();

        /* テスト対象の実行 */
        final JdtsConfigsModel testResult = testTarget.getConfigsModel();

        /* 検証の準備 */
        final JdtsConfigsModel actualConfigsModel = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedConfigsModel, actualConfigsModel, "初期状態でnullが返されること");

    }

    /**
     * getReplacedJavadocBlock メソッドのテスト - 例外系:初期状態でNullPointerExceptionが発生することの確認
     * <p>
     * 初期化前の状態でgetReplacedJavadocBlockを呼び出した場合にNullPointerExceptionが発生することを確認します。
     * </p>
     */
    @Test
    public void testGetReplacedJavadocBlock_errorInitialStateThrowsNullPointerException() {

        /* 期待値の定義 */
        // NullPointerExceptionが発生すること

        /* 準備 */
        final JdtsBlockReplLogicImpl testTarget = new JdtsBlockReplLogicImpl();

        /* テスト対象の実行と検証の実施 */
        Assertions.assertThrows(NullPointerException.class, () -> testTarget.getReplacedJavadocBlock(),
            "初期状態でNullPointerExceptionが発生すること");

    }

    /**
     * getTagContentToApply メソッドのテスト - 正常系:初期状態でnullが返されることの確認
     * <p>
     * 初期化前の状態でgetTagContentToApplyを呼び出した場合にnullが返されることを確認します。
     * </p>
     */
    @Test
    public void testGetTagContentToApply_normalInitialStateReturnsNull() {

        /* 期待値の定義 */
        final String expectedTagContent = null;

        /* 準備 */
        final JdtsBlockReplLogicImpl testTarget = new JdtsBlockReplLogicImpl();

        /* テスト対象の実行 */
        final String testResult = testTarget.getTagContentToApply();

        /* 検証の準備 */
        final String actualTagContent = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "初期状態でnullが返されること");

    }

    /**
     * hasExistingTag メソッドのテスト - 正常系:初期状態でfalseが返されることの確認
     * <p>
     * 初期化前の状態でhasExistingTagを呼び出した場合にfalseが返されることを確認します。
     * </p>
     */
    @Test
    public void testHasExistingTag_normalInitialStateReturnsFalse() {

        /* 期待値の定義 */
        final boolean expectedHasExistingTag = false;

        /* 準備 */
        final JdtsBlockReplLogicImpl testTarget = new JdtsBlockReplLogicImpl();

        /* テスト対象の実行 */
        final boolean testResult = testTarget.hasExistingTag();

        /* 検証の準備 */
        final boolean actualHasExistingTag = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedHasExistingTag, actualHasExistingTag, "初期状態でfalseが返されること");

    }

    /**
     * JdtsBlockReplLogicImplの基本的なインスタンス生成テスト - 正常系:インスタンスが正しく生成されることの確認
     * <p>
     * JdtsBlockReplLogicImplクラスのインスタンスが正しく生成されることを確認します。
     * </p>
     */
    @Test
    public void testJdtsBlockReplLogicImpl_normalInstanceCreated() {

        /* 期待値の定義 */
        // インスタンスが正常に生成されること

        /* 準備 */
        // なし

        /* テスト対象の実行 */
        final JdtsBlockReplLogicImpl testResult = new JdtsBlockReplLogicImpl();

        /* 検証の準備 */
        final JdtsBlockReplLogicImpl actualInstance = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualInstance, "インスタンスが正しく生成されること");

    }

    /**
     * nextTag メソッドのテスト - 例外系:初期状態でNullPointerExceptionが発生することの確認
     * <p>
     * 初期化前の状態でnextTagを呼び出した場合にNullPointerExceptionが発生することを確認します。
     * </p>
     */
    @Test
    public void testNextTag_errorInitialStateThrowsNullPointerException() {

        /* 期待値の定義 */
        // NullPointerExceptionが発生すること

        /* 準備 */
        final JdtsBlockReplLogicImpl testTarget = new JdtsBlockReplLogicImpl();

        /* テスト対象の実行と検証の実施 */
        Assertions.assertThrows(NullPointerException.class, () -> testTarget.nextTag(),
            "初期状態でNullPointerExceptionが発生すること");

    }

    /**
     * removeCurrentTag メソッドのテスト - 準正常系:初期状態でfalseが返されることの確認
     * <p>
     * 初期化前の状態でremoveCurrentTagを呼び出した場合にfalseが返されることを確認します。
     * </p>
     */
    @Test
    public void testRemoveCurrentTag_semiInitialStateReturnsFalse() {

        /* 期待値の定義 */
        final boolean expectedRemoveResult = false;

        /* 準備 */
        final JdtsBlockReplLogicImpl testTarget = new JdtsBlockReplLogicImpl();

        /* テスト対象の実行 */
        final boolean testResult = testTarget.removeCurrentTag();

        /* 検証の準備 */
        final boolean actualRemoveResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedRemoveResult, actualRemoveResult, "初期状態でfalseが返されること");

    }

    /**
     * shouldAddNewTag メソッドのテスト - 例外系:初期状態でNullPointerExceptionが発生することの確認
     * <p>
     * 初期化前の状態でshouldAddNewTagを呼び出した場合にNullPointerExceptionが発生することを確認します。
     * </p>
     */
    @Test
    public void testShouldAddNewTag_errorInitialStateThrowsNullPointerException() {

        /* 期待値の定義 */
        // NullPointerExceptionが発生すること

        /* 準備 */
        final JdtsBlockReplLogicImpl testTarget = new JdtsBlockReplLogicImpl();

        /* テスト対象の実行と検証の実施 */
        Assertions.assertThrows(NullPointerException.class, () -> testTarget.shouldAddNewTag(),
            "初期状態でNullPointerExceptionが発生すること");

    }
}
