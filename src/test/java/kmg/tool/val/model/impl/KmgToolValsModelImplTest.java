package kmg.tool.val.model.impl;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.model.val.KmgValDataModel;
import kmg.tool.cmn.infrastructure.types.KmgToolValMsgTypes;

/**
 * KMGツールバリデーション集合モデルのテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 */
@SuppressWarnings({
    "nls", "static-method"
})
public class KmgToolValsModelImplTest {

    /**
     * 基本的な動作確認のテスト - 正常系：データの追加と取得が正常に動作する場合
     */
    @Test
    public void testBasicOperation_normalAddAndGetData() {

        /* 期待値の定義 */
        final int expectedSize = 1;

        /* 準備 */
        final KmgToolValsModelImpl testTarget = new KmgToolValsModelImpl();
        final KmgValDataModel      testData   = new KmgToolValDataModelImpl(KmgToolValMsgTypes.NONE, new Object[] {
            "テストデータ"
        });

        /* テスト対象の実行 */
        testTarget.addData(testData);

        /* 検証の準備 */
        final List<KmgValDataModel> actualDatas = testTarget.getDatas();
        final int                   actualSize  = actualDatas.size();

        /* 検証の実施 */
        Assertions.assertEquals(expectedSize, actualSize, "データサイズが一致しません");
        Assertions.assertEquals(testData, actualDatas.get(0), "追加されたデータが一致しません");

    }

    /**
     * コンストラクタのテスト - 正常系：正常にインスタンスが作成される場合
     */
    @Test
    public void testConstructor_normal() {

        /* 期待値の定義 */

        /* テスト対象の実行 */
        final KmgToolValsModelImpl testTarget = new KmgToolValsModelImpl();

        /* 検証の準備 */
        final boolean               actualIsEmpty = testTarget.isEmpty();
        final List<KmgValDataModel> actualDatas   = testTarget.getDatas();

        /* 検証の実施 */
        Assertions.assertNotNull(testTarget, "インスタンスが作成されていません");
        Assertions.assertTrue(actualIsEmpty, "初期状態で空でありません");
        Assertions.assertNotNull(actualDatas, "データリストがnullです");

    }

}
