package kmg.tool.cmn.infrastructure.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * KMGツールログメッセージの種類のテスト<br>
 * <p>
 * Logは、Logの略。<br>
 * Msgは、Messageの略。
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
public class KmgToolLogMsgTypesTest {

    /**
     * デフォルトコンストラクタ<br>
     *
     * @since 0.1.0
     */
    public KmgToolLogMsgTypesTest() {

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
        final String expected = "KMGTOOL_LOG01000";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.KMGTOOL_LOG01000;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "取得値が一致しません");

    }

    /**
     * getCode メソッドのテスト - 正常系:コードの取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetCode_normalBasicCode() {

        /* 期待値の定義 */
        final String expected = "KMGTOOL_LOG01000";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.KMGTOOL_LOG01000;

        /* テスト対象の実行 */
        final String actual = testType.getCode();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "getCodeの返り値が一致しません");

    }

    /**
     * getDefault メソッドのテスト - 正常系:デフォルト値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDefault_normalDefaultValue() {

        /* 期待値の定義 */
        final KmgToolLogMsgTypes expected = KmgToolLogMsgTypes.NONE;

        /* テスト対象の実行 */
        final KmgToolLogMsgTypes actual = KmgToolLogMsgTypes.getDefault();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "デフォルト値が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:詳細情報の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDetail_normalBasicDetail() {

        /* 期待値の定義 */
        final String expected = "中間ファイルに書き込み中にエラーが発生しました。";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.KMGTOOL_LOG01000;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "詳細情報が一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:表示名の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDisplayName_normalBasicDisplayName() {

        /* 期待値の定義 */
        final String expected = "中間ファイルに書き込み中にエラーが発生しました。";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.KMGTOOL_LOG01000;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "表示名が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:存在する値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_normalExistingValue() {

        /* 期待値の定義 */
        final KmgToolLogMsgTypes expected = KmgToolLogMsgTypes.KMGTOOL_LOG01000;

        /* 準備 */
        final String testValue = "KMGTOOL_LOG01000";

        /* テスト対象の実行 */
        final KmgToolLogMsgTypes actual = KmgToolLogMsgTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 準正常系:存在しない値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_semiNonExistingValue() {

        /* 期待値の定義 */
        final KmgToolLogMsgTypes expected = KmgToolLogMsgTypes.NONE;

        /* 準備 */
        final String testValue = "INVALID";

        /* テスト対象の実行 */
        final KmgToolLogMsgTypes actual = KmgToolLogMsgTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "値が一致しません");

    }

    /**
     * getInitValue メソッドのテスト - 正常系:初期値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetInitValue_normalInitialValue() {

        /* 期待値の定義 */
        final KmgToolLogMsgTypes expected = KmgToolLogMsgTypes.NONE;

        /* テスト対象の実行 */
        final KmgToolLogMsgTypes actual = KmgToolLogMsgTypes.getInitValue();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "初期値が一致しません");

    }

    /**
     * getValue メソッドのテスト - 正常系:値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetValue_normalBasicValue() {

        /* 期待値の定義 */
        final String expected = "中間ファイルに書き込み中にエラーが発生しました。";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.KMGTOOL_LOG01000;

        /* テスト対象の実行 */
        final String actual = testType.getValue();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "値が一致しません");

    }

    /**
     * toString メソッドのテスト - 正常系:KMGTOOL_LOG01000の文字列表現
     *
     * @since 0.1.0
     */
    @Test
    public void testToString_normalKmgtoolLog01000() {

        /* 期待値の定義 */
        final String expected = "KMGTOOL_LOG01000";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.KMGTOOL_LOG01000;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "KMGTOOL_LOG01000の場合、'KMGTOOL_LOG01000'が返されること");

    }

    /**
     * toString メソッドのテスト - 正常系:NONEの文字列表現
     *
     * @since 0.1.0
     */
    @Test
    public void testToString_normalNone() {

        /* 期待値の定義 */
        final String expected = "NONE";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "NONEの場合、\"NONE\"が返されること");

    }

    /**
     * getKey メソッドのテスト - 正常系:キーの取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalBasicKey() {

        /* 期待値の定義 */
        final String expected = "KMGTOOL_LOG01000";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.KMGTOOL_LOG01000;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "キーが一致しません");

    }

    /**
     * getKey メソッドのテスト - 正常系:NONEのキーの取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetKey_normalNoneKey() {

        /* 期待値の定義 */
        final String expected = "NONE";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.getKey();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "NONEのキーが一致しません");

    }

    /**
     * getDisplayName メソッドのテスト - 正常系:NONEの表示名の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDisplayName_normalNoneDisplayName() {

        /* 期待値の定義 */
        final String expected = "指定無し";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.getDisplayName();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "NONEの表示名が一致しません");

    }

    /**
     * getValue メソッドのテスト - 正常系:NONEの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetValue_normalNoneValue() {

        /* 期待値の定義 */
        final String expected = "指定無し";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.getValue();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "NONEの値が一致しません");

    }

    /**
     * getDetail メソッドのテスト - 正常系:NONEの詳細情報の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetDetail_normalNoneDetail() {

        /* 期待値の定義 */
        final String expected = "指定無し";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.getDetail();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "NONEの詳細情報が一致しません");

    }

    /**
     * get メソッドのテスト - 正常系:NONEの値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGet_normalNoneValue() {

        /* 期待値の定義 */
        final String expected = "NONE";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.get();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "NONEの取得値が一致しません");

    }

    /**
     * getCode メソッドのテスト - 正常系:NONEのコードの取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetCode_normalNoneCode() {

        /* 期待値の定義 */
        final String expected = "NONE";

        /* 準備 */
        final KmgToolLogMsgTypes testType = KmgToolLogMsgTypes.NONE;

        /* テスト対象の実行 */
        final String actual = testType.getCode();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "NONEのコードが一致しません");

    }

    /**
     * getEnum メソッドのテスト - 正常系:別の存在する値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_normalAnotherExistingValue() {

        /* 期待値の定義 */
        final KmgToolLogMsgTypes expected = KmgToolLogMsgTypes.KMGTOOL_LOG01001;

        /* 準備 */
        final String testValue = "KMGTOOL_LOG01001";

        /* テスト対象の実行 */
        final KmgToolLogMsgTypes actual = KmgToolLogMsgTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "別の存在する値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 準正常系:null値の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_semiNullValue() {

        /* 期待値の定義 */
        final KmgToolLogMsgTypes expected = KmgToolLogMsgTypes.NONE;

        /* 準備 */
        final String testValue = null;

        /* テスト対象の実行 */
        final KmgToolLogMsgTypes actual = KmgToolLogMsgTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "null値の場合の値が一致しません");

    }

    /**
     * getEnum メソッドのテスト - 準正常系:空文字列の取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetEnum_semiEmptyStringValue() {

        /* 期待値の定義 */
        final KmgToolLogMsgTypes expected = KmgToolLogMsgTypes.NONE;

        /* 準備 */
        final String testValue = "";

        /* テスト対象の実行 */
        final KmgToolLogMsgTypes actual = KmgToolLogMsgTypes.getEnum(testValue);

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "空文字列の場合の値が一致しません");

    }

}
