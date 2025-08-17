package kmg.tool.cmn.infrastructure.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.msg.KmgToolCmnExcMsg;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;

/**
 * KMGツールメッセージ例外テスト
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls",
})
public class KmgToolMsgExceptionTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private KmgToolMsgException testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.1.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * モックKMGメッセージソース
     *
     * @since 0.1.0
     */
    private KmgMessageSource mockMessageSource;

    /**
     * createMessage メソッドのテスト - 正常系：メッセージが作成される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCreateMessage_normalCreateMessage() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN01001] 項目名がnullです。";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN01001;

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            this.testTarget = new KmgToolMsgException(expectedMessageTypes);

            /* テスト対象の実行 */
            this.testTarget.getMessage();

            /* 検証の準備 */

            /* 検証の実施 */
            this.verifyKmgMsgException(this.testTarget, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * createMessageSource メソッドのテスト - 正常系：メッセージソースが作成される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCreateMessageSource_normalCreateMessageSource() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN01001] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN01001;

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            this.testTarget = new KmgToolMsgException(expectedMessageTypes);
            this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

            /* テスト対象の実行 */
            this.reflectionModel.getMethod("createMessageSource");

            /* 検証の準備 */
            final KmgMessageSource actualMessageSource = (KmgMessageSource) this.reflectionModel.get("messageSource");

            /* 検証の実施 */
            Assertions.assertEquals(this.mockMessageSource, actualMessageSource, "メッセージソースが正しく設定されていること");
            this.verifyKmgMsgException(this.testTarget, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * getCause メソッドのテスト - 正常系：原因がnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetCause_normalCauseNull() throws Exception {

        /* 期待値の定義 */
        final KmgToolCmnExcMsg messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN01001;

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("[KMGTOOL_GEN01001] テストメッセージ");

            this.testTarget = new KmgToolMsgException(messageTypes);

            /* テスト対象の実行 */
            final Throwable testResult = this.testTarget.getCause();

            /* 検証の準備 */
            final Throwable actualCause = testResult;

            /* 検証の実施 */
            Assertions.assertNull(actualCause, "原因がnullで正しく取得されること");

        }

    }

    /**
     * getCause メソッドのテスト - 正常系：原因が取得される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetCause_normalGetCause() throws Exception {

        /* 期待値の定義 */
        final KmgToolCmnExcMsg messageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN01001;
        final Throwable        expectedCause = new RuntimeException("テスト例外");

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("[KMGTOOL_GEN01001] テストメッセージ");

            this.testTarget = new KmgToolMsgException(messageTypes, expectedCause);

            /* テスト対象の実行 */
            final Throwable testResult = this.testTarget.getCause();

            /* 検証の準備 */
            final Throwable actualCause = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedCause, actualCause, "原因が正しく取得されること");

        }

    }

    /**
     * getMessageArgs メソッドのテスト - 正常系：メッセージ引数が取得される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetMessageArgs_normalGetMessageArgs() throws Exception {

        /* 期待値の定義 */
        final KmgToolCmnExcMsg messageTypes        = KmgToolGenMsgTypes.KMGTOOL_GEN01001;
        final Object[]         expectedMessageArgs = {
            "arg1", "arg2"
        };

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("[KMGTOOL_GEN01001] テストメッセージ");

            this.testTarget = new KmgToolMsgException(messageTypes, expectedMessageArgs);

            /* テスト対象の実行 */
            final Object[] testResult = this.testTarget.getMessageArgs();

            /* 検証の準備 */
            final Object[] actualMessageArgs = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedMessageArgs, actualMessageArgs, "メッセージ引数が正しく取得されること");

        }

    }

    /**
     * getMessageArgs メソッドのテスト - 正常系：メッセージ引数がnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetMessageArgs_normalMessageArgsNull() throws Exception {

        /* 期待値の定義 */
        final KmgToolCmnExcMsg messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN01001;

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("[KMGTOOL_GEN01001] テストメッセージ");

            this.testTarget = new KmgToolMsgException(messageTypes);

            /* テスト対象の実行 */
            final Object[] testResult = this.testTarget.getMessageArgs();

            /* 検証の準備 */
            final Object[] actualMessageArgs = testResult;

            /* 検証の実施 */
            Assertions.assertNull(actualMessageArgs, "メッセージ引数がnullで正しく取得されること");

        }

    }

    /**
     * getMessageTypes メソッドのテスト - 正常系：メッセージタイプが取得される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetMessageTypes_normalGetMessageTypes() throws Exception {

        /* 期待値の定義 */
        final KmgToolCmnExcMsg expectedMessageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN01001;

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("[KMGTOOL_GEN01001] テストメッセージ");

            this.testTarget = new KmgToolMsgException(expectedMessageTypes);

            /* テスト対象の実行 */
            final KmgToolCmnExcMsg testResult = (KmgToolCmnExcMsg) this.testTarget.getMessageTypes();

            /* 検証の準備 */
            final KmgToolCmnExcMsg actualMessageTypes = testResult;

            /* 検証の実施 */
            Assertions.assertEquals(expectedMessageTypes, actualMessageTypes, "メッセージタイプが正しく取得されること");

        }

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：メッセージタイプとメッセージ引数の場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testKmgToolMsgException_normalMessageTypesAndArgs() throws Exception {

        /* 期待値の定義 */
        final KmgToolCmnExcMsg expectedMessageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN01001;
        final Object[]         expectedMessageArgs  = {
            "arg1", "arg2"
        };

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("[KMGTOOL_GEN01001] テストメッセージ");

            /* テスト対象の実行 */
            this.testTarget = new KmgToolMsgException(expectedMessageTypes, expectedMessageArgs);

            /* 検証の準備 */
            final KmgToolCmnExcMsg actualMessageTypes = (KmgToolCmnExcMsg) this.testTarget.getMessageTypes();
            final Object[]         actualMessageArgs  = this.testTarget.getMessageArgs();

            /* 検証の実施 */
            Assertions.assertEquals(expectedMessageTypes, actualMessageTypes, "メッセージタイプが正しく設定されていること");
            Assertions.assertEquals(expectedMessageArgs, actualMessageArgs, "メッセージ引数が正しく設定されていること");

        }

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：メッセージタイプとメッセージ引数と原因の場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testKmgToolMsgException_normalMessageTypesAndArgsAndCause() throws Exception {

        /* 期待値の定義 */
        final KmgToolCmnExcMsg expectedMessageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN01001;
        final Object[]         expectedMessageArgs  = {
            "arg1", "arg2"
        };
        final Throwable        expectedCause        = new RuntimeException("テスト例外");

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("[KMGTOOL_GEN01001] テストメッセージ");

            /* テスト対象の実行 */
            this.testTarget = new KmgToolMsgException(expectedMessageTypes, expectedMessageArgs, expectedCause);

            /* 検証の準備 */
            final KmgToolCmnExcMsg actualMessageTypes = (KmgToolCmnExcMsg) this.testTarget.getMessageTypes();
            final Object[]         actualMessageArgs  = this.testTarget.getMessageArgs();
            final Throwable        actualCause        = this.testTarget.getCause();

            /* 検証の実施 */
            Assertions.assertEquals(expectedMessageTypes, actualMessageTypes, "メッセージタイプが正しく設定されていること");
            Assertions.assertEquals(expectedMessageArgs, actualMessageArgs, "メッセージ引数が正しく設定されていること");
            Assertions.assertEquals(expectedCause, actualCause, "原因が正しく設定されていること");

        }

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：メッセージタイプと原因の場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testKmgToolMsgException_normalMessageTypesAndCause() throws Exception {

        /* 期待値の定義 */
        final KmgToolCmnExcMsg expectedMessageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN01001;
        final Throwable        expectedCause        = new RuntimeException("テスト例外");

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("[KMGTOOL_GEN01001] テストメッセージ");

            /* テスト対象の実行 */
            this.testTarget = new KmgToolMsgException(expectedMessageTypes, expectedCause);

            /* 検証の準備 */
            final KmgToolCmnExcMsg actualMessageTypes = (KmgToolCmnExcMsg) this.testTarget.getMessageTypes();
            final Throwable        actualCause        = this.testTarget.getCause();

            /* 検証の実施 */
            Assertions.assertEquals(expectedMessageTypes, actualMessageTypes, "メッセージタイプが正しく設定されていること");
            Assertions.assertEquals(expectedCause, actualCause, "原因が正しく設定されていること");

        }

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：メッセージタイプのみの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testKmgToolMsgException_normalMessageTypesOnly() throws Exception {

        /* 期待値の定義 */
        final KmgToolCmnExcMsg expectedMessageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN01001;

        /* 準備 */

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("[KMGTOOL_GEN01001] テストメッセージ");

            /* テスト対象の実行 */
            this.testTarget = new KmgToolMsgException(expectedMessageTypes);

            /* 検証の準備 */
            final KmgToolCmnExcMsg actualMessageTypes = (KmgToolCmnExcMsg) this.testTarget.getMessageTypes();

            /* 検証の実施 */
            Assertions.assertEquals(expectedMessageTypes, actualMessageTypes, "メッセージタイプが正しく設定されていること");

        }

    }
}
