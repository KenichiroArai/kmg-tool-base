package kmg.tool.base.cmn.infrastructure.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * KMGツール一般メッセージの種類のテスト<br>
 * <p>
 * Genは、Generalの略。<br>
 * Msgは、Messageの略。
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
public class KmgToolGenMsgTypesTest {

    /**
     * デフォルトコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public KmgToolGenMsgTypesTest() {

        // 処理なし
    }

    /**
     * get メソッドのテスト - 正常系:基本的な値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGet_normalBasicValue() {

        /* 期待値の定義 */
        final String expected = "KMGTOOL_GEN01000";

        /* 準備 */
        final KmgToolGenMsgTypes testType = KmgToolGenMsgTypes.KMGTOOL_GEN01000;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "取得値が一致しません");

    }

    /**
     * getCode メソッドのテスト - 正常系:コードの取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetCode_normalBasicCode() {

        /* 期待値の定義 */
        final String expected = "KMGTOOL_GEN01000";

        /* 準備 */
        final KmgToolGenMsgTypes testType = KmgToolGenMsgTypes.KMGTOOL_GEN01000;

        /* テスト対象の実行 */
        final String actual = testType.getCode();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "getCodeの返り値が一致しません");

    }

    /**
     * getDefault メソッドのテスト - 正常系:デフォルト値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDefault_normalDefaultValue() {

        /* 期待値の定義 */
        final KmgToolGenMsgTypes expected = KmgToolGenMsgTypes.NONE;

        /* テスト対象の実行 */
        final KmgToolGenMsgTypes actual = KmgToolGenMsgTypes.getDefault();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "デフォルト値が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:詳細情報の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDetail_normalBasicDetail() {

        /* 期待値の定義 */
        final String expected = "アクセサ作成ロジックをクローズ中にエラーが発生しました。";

        /* 準備 */
        final KmgToolGenMsgTypes testType = KmgToolGenMsgTypes.KMGTOOL_GEN01000;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "詳細情報が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:表示名の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetDisplayName_normalBasicDisplayName() {

        /* 期待値の定義 */
        final String expected = "アクセサ作成ロジックをクローズ中にエラーが発生しました。";

        /* 準備 */
        final KmgToolGenMsgTypes testType = KmgToolGenMsgTypes.KMGTOOL_GEN01000;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "表示名が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:存在する値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetEnum_normalExistingValue() {

        /* 期待値の定義 */
        final KmgToolGenMsgTypes expected = KmgToolGenMsgTypes.KMGTOOL_GEN01000;

        /* 準備 */
        final String testValue = "KMGTOOL_GEN01000";

        /* テスト対象の実行 */
        final KmgToolGenMsgTypes actual = KmgToolGenMsgTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 準正常系:存在しない値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetEnum_semiNonExistingValue() {

        /* 期待値の定義 */
        final KmgToolGenMsgTypes expected = KmgToolGenMsgTypes.NONE;

        /* 準備 */
        final String testValue = "INVALID";

        /* テスト対象の実行 */
        final KmgToolGenMsgTypes actual = KmgToolGenMsgTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "値が一致しません");

    }

    /**
     * getInitValue メソッドのテスト - 正常系:初期値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetInitValue_normalInitialValue() {

        /* 期待値の定義 */
        final KmgToolGenMsgTypes expected = KmgToolGenMsgTypes.NONE;

        /* テスト対象の実行 */
        final KmgToolGenMsgTypes actual = KmgToolGenMsgTypes.getInitValue();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "初期値が一致しません");

    }

    /**
     * getValue メソッドのテスト - 正常系:値の取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetValue_normalBasicValue() {

        /* 期待値の定義 */
        final String expected = "アクセサ作成ロジックをクローズ中にエラーが発生しました。";

        /* 準備 */
        final KmgToolGenMsgTypes testType = KmgToolGenMsgTypes.KMGTOOL_GEN01000;

        /* テスト対象の実行 */
        final String actual = testType.getValue();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "値が一致しません");

    }

    /**
     * toString メソッドのテスト - 正常系:KMGTOOL_GEN01000の文字列表現
     *
     * @since 0.2.0
     */
    @Test
    public void testToString_normalKmgtoolgen01000() {

        /* 期待値の定義 */
        final String expected = "KMGTOOL_GEN01000";

        /* 準備 */
        final KmgToolGenMsgTypes testType = KmgToolGenMsgTypes.KMGTOOL_GEN01000;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "KMGTOOL_GEN01000の場合、'KMGTOOL_GEN01000'が返されること");

    }

    /**
     * toString メソッドのテスト - 正常系:NONEの文字列表現
     *
     * @since 0.2.0
     */
    @Test
    public void testToString_normalNone() {

        /* 期待値の定義 */
        final String expected = "NONE";

        /* 準備 */
        final KmgToolGenMsgTypes testType = KmgToolGenMsgTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "NONEの場合、\"NONE\"が返されること");

    }
}
