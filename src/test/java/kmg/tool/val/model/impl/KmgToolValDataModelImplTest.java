package kmg.tool.val.model.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kmg.core.infrastructure.cmn.msg.KmgCmnValMsgTypes;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.types.KmgToolValMsgTypes;

/**
 * KMGツールバリデーションデータモデル実装クラスのテスト
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls",
})
public class KmgToolValDataModelImplTest extends AbstractKmgTest {

    /** テスト対象クラス */
    private KmgToolValDataModelImpl target;

    /** モックKMGメッセージソース */
    private KmgMessageSource mockMessageSource;

    /**
     * コンストラクタ メソッドのテスト - 正常系：null引数でインスタンス作成
     */
    @Test
    public void testConstructor_normalNullArgs() {

        /* 期待値の定義 */

        /* 準備 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

        final KmgToolValMsgTypes testMessageTypes = null;
        final Object[]           testMessageArgs  = null;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            /* テスト対象の実行 */
            this.target = new KmgToolValDataModelImpl(testMessageTypes, testMessageArgs);

            /* 検証の準備 */
            final KmgCmnValMsgTypes actualMessageTypes = this.target.getMessageTypes();
            final Object[]          actualMessageArgs  = this.target.getMessageArgs();

            /* 検証の実施 */
            Assertions.assertNull(actualMessageTypes, "メッセージタイプが一致しません");
            Assertions.assertNull(actualMessageArgs, "メッセージ引数が一致しません");

        }

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：正常な引数でインスタンス作成
     */
    @Test
    public void testConstructor_normalValidArgs() {

        /* 期待値の定義 */
        // TODO KenichiroArai 2025/07/31 KmgToolValMsgTypesの検証
        final KmgToolValMsgTypes expectedMessageTypes = KmgToolValMsgTypes.KMGTOOL_VAL13000;
        final Object[]           expectedMessageArgs  = {
            "test"
        };

        /* 準備 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            /* テスト対象の実行 */
            this.target = new KmgToolValDataModelImpl(expectedMessageTypes, expectedMessageArgs);

            /* 検証の準備 */
            final KmgCmnValMsgTypes actualMessageTypes = this.target.getMessageTypes();
            final Object[]          actualMessageArgs  = this.target.getMessageArgs();

            /* 検証の実施 */
            Assertions.assertEquals(expectedMessageTypes, actualMessageTypes, "メッセージタイプが一致しません");
            Assertions.assertEquals(expectedMessageArgs, actualMessageArgs, "メッセージ引数が一致しません");

        }

    }

    /**
     * createMessage メソッドのテスト - 正常系：空文字メッセージ作成
     */
    @Test
    public void testCreateMessage_normalEmptyMessage() {

        /* 期待値の定義 */
        // TODO KenichiroArai 2025/07/31 KmgToolValMsgTypesの検証
        final String             expectedMessage = "";
        final KmgToolValMsgTypes messageTypes    = KmgToolValMsgTypes.KMGTOOL_VAL13000;
        final Object[]           messageArgs     = {
            "test"
        };

        /* 準備 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(messageTypes, messageArgs)).thenReturn(expectedMessage);

            this.target = new KmgToolValDataModelImpl(messageTypes, messageArgs);

            /* テスト対象の実行 */
            final String testResult = this.target.createMessage();

            /* 検証の準備 */
            final String actualMessage = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedMessage, actualMessage, "メッセージが一致しません");

        }

    }

    /**
     * createMessage メソッドのテスト - 正常系：nullメッセージ作成
     */
    @Test
    public void testCreateMessage_normalNullMessage() {

        /* 期待値の定義 */
        // TODO KenichiroArai 2025/07/31 KmgToolValMsgTypesの検証
        final KmgToolValMsgTypes messageTypes = KmgToolValMsgTypes.KMGTOOL_VAL13000;
        final Object[]           messageArgs  = {
            "test"
        };

        /* 準備 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(messageTypes, messageArgs)).thenReturn(expectedMessage);

            this.target = new KmgToolValDataModelImpl(messageTypes, messageArgs);

            /* テスト対象の実行 */
            final String testResult = this.target.createMessage();

            /* 検証の準備 */
            final String actualMessage = testResult;

            /* 検証の実施 */
            Assertions.assertNull(actualMessage, "メッセージが一致しません");

        }

    }

    /**
     * createMessage メソッドのテスト - 正常系：正常なメッセージ作成
     */
    @Test
    public void testCreateMessage_normalValidMessage() {

        /* 期待値の定義 */
        // TODO KenichiroArai 2025/07/31 KmgToolValMsgTypesの検証
        final String             expectedMessage = "テストメッセージ";
        final KmgToolValMsgTypes messageTypes    = KmgToolValMsgTypes.KMGTOOL_VAL13000;
        final Object[]           messageArgs     = {
            "test"
        };

        /* 準備 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(messageTypes, messageArgs)).thenReturn(expectedMessage);

            this.target = new KmgToolValDataModelImpl(messageTypes, messageArgs);

            /* テスト対象の実行 */
            final String testResult = this.target.createMessage();

            /* 検証の準備 */
            final String actualMessage = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedMessage, actualMessage, "メッセージが一致しません");

        }

    }

    /**
     * createMessageSource メソッドのテスト - 正常系：メッセージソース作成
     */
    @Test
    public void testCreateMessageSource_normalCreateMessageSource() {

        /* 期待値の定義 */
        // TODO KenichiroArai 2025/07/31 KmgToolValMsgTypesの検証
        final KmgToolValMsgTypes messageTypes = KmgToolValMsgTypes.KMGTOOL_VAL13000;
        final Object[]           messageArgs  = {
            "test"
        };

        /* 準備 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            this.target = new KmgToolValDataModelImpl(messageTypes, messageArgs);

            /* テスト対象の実行 */
            this.target.createMessageSource();

            /* 検証の準備 */
            // メッセージソースが正しく設定されていることを確認するため、
            // createMessageメソッドを呼び出してメッセージソースが使用されることを検証
            final String testResult = this.target.createMessage();

            /* 検証の実施 */
            Assertions.assertNotNull(testResult, "メッセージソースが正しく作成されていません");

        }

    }

    /**
     * getMessage メソッドのテスト - 正常系：メッセージ取得
     */
    @Test
    public void testGetMessage_normalGetMessage() {

        /* 期待値の定義 */
        // TODO KenichiroArai 2025/07/31 KmgToolValMsgTypesの検証
        final String             expectedMessage = "テストメッセージ";
        final KmgToolValMsgTypes messageTypes    = KmgToolValMsgTypes.KMGTOOL_VAL13000;
        final Object[]           messageArgs     = {
            "test"
        };

        /* 準備 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(messageTypes, messageArgs)).thenReturn(expectedMessage);

            this.target = new KmgToolValDataModelImpl(messageTypes, messageArgs);

            /* テスト対象の実行 */
            final String testResult = this.target.getMessage();

            /* 検証の準備 */
            final String actualMessage = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedMessage, actualMessage, "メッセージが一致しません");

        }

    }

    /**
     * getMessageArgs メソッドのテスト - 正常系：メッセージ引数取得
     */
    @Test
    public void testGetMessageArgs_normalGetMessageArgs() {

        /* 期待値の定義 */
        // TODO KenichiroArai 2025/07/31 KmgToolValMsgTypesの検証
        final Object[]           expectedMessageArgs = {
            "test", "arg2"
        };
        final KmgToolValMsgTypes messageTypes        = KmgToolValMsgTypes.KMGTOOL_VAL13000;

        /* 準備 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            this.target = new KmgToolValDataModelImpl(messageTypes, expectedMessageArgs);

            /* テスト対象の実行 */
            final Object[] testResult = this.target.getMessageArgs();

            /* 検証の準備 */
            final Object[] actualMessageArgs = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedMessageArgs, actualMessageArgs, "メッセージ引数が一致しません");

        }

    }

    /**
     * getMessageTypes メソッドのテスト - 正常系：メッセージタイプ取得
     */
    @Test
    public void testGetMessageTypes_normalGetMessageTypes() {

        /* 期待値の定義 */
        // TODO KenichiroArai 2025/07/31 KmgToolValMsgTypesの検証
        final KmgToolValMsgTypes expectedMessageTypes = KmgToolValMsgTypes.KMGTOOL_VAL13001;
        final Object[]           messageArgs          = {
            "test"
        };

        /* 準備 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            this.target = new KmgToolValDataModelImpl(expectedMessageTypes, messageArgs);

            /* テスト対象の実行 */
            final KmgCmnValMsgTypes testResult = this.target.getMessageTypes();

            /* 検証の準備 */
            final KmgCmnValMsgTypes actualMessageTypes = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedMessageTypes, actualMessageTypes, "メッセージタイプが一致しません");

        }

    }

}
