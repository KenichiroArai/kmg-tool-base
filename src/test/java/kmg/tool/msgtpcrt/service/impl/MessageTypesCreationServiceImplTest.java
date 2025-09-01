package kmg.tool.msgtpcrt.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.msgtpcrt.application.logic.MessageTypesCreationLogic;

/**
 * メッセージの種類作成サービス実装テスト
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
    "nls", "static-method"
})
public class MessageTypesCreationServiceImplTest extends AbstractKmgTest {

    /**
     * テンポラリディレクトリ
     *
     * @since 0.1.0
     */
    @TempDir
    private Path tempDir;

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private MessageTypesCreationServiceImpl testTarget;

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
     * モックメッセージの種類作成ロジック
     *
     * @since 0.1.0
     */
    private MessageTypesCreationLogic mockMessageTypesCreationLogic;

    /**
     * セットアップ
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @SuppressWarnings("resource")
    @BeforeEach
    public void setUp() throws Exception {

        this.testTarget = new MessageTypesCreationServiceImpl();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.mockMessageTypesCreationLogic = Mockito.mock(MessageTypesCreationLogic.class);

        /* モックをテスト対象に設定 */
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("messageTypesCreationLogic", this.mockMessageTypesCreationLogic);

        // 初期化処理のモック設定
        Mockito.when(this.mockMessageTypesCreationLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockMessageTypesCreationLogic.addOneLineOfDataToRows()).thenReturn(true);

    }

    /**
     * クリーンアップ
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @AfterEach
    public void tearDown() throws Exception {

        if (this.testTarget != null) {

            try {

                // 必要に応じてクリーンアップ処理を追加

            } catch (final Exception e) {

                e.printStackTrace();

            }

        }

    }

    /**
     * clearAndPrepareNextLine メソッドのテスト - 正常系：正常にクリア処理が完了する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearAndPrepareNextLine_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockMessageTypesCreationLogic.clearRows()).thenReturn(true);
        Mockito.when(this.mockMessageTypesCreationLogic.clearProcessingData()).thenReturn(true);
        Mockito.when(this.mockMessageTypesCreationLogic.addOneLineOfDataToRows()).thenReturn(true);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("clearAndPrepareNextLine");

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("clearAndPrepareNextLine"),
            "clearAndPrepareNextLineメソッドが正常に実行されること");

    }

    /**
     * closeMessageTypesCreationLogic メソッドのテスト - 異常系：IOExceptionが発生する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseMessageTypesCreationLogic_errorIOException() throws Exception {

        /* 期待値の定義 */
        final Class<?>           expectedCauseClass    = IOException.class;
        final String             expectedDomainMessage = "[KMGTOOL_GEN14003] メッセージの種類作成ロジックをクローズ中にエラーが発生しました。";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN14003;

        /* 準備 */
        Mockito.doThrow(new IOException("テスト例外")).when(this.mockMessageTypesCreationLogic).close();

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                try {

                    this.reflectionModel.getMethod("closeMessageTypesCreationLogic");

                } catch (final KmgReflectionException e) {

                    // KmgReflectionExceptionの原因となった例外を再投げする
                    final Throwable cause = e.getCause();

                    if (cause instanceof KmgToolMsgException) {

                        throw (KmgToolMsgException) cause;

                    }
                    throw e;

                }

            });

            /* 検証の準備 */

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * closeMessageTypesCreationLogic メソッドのテスト - 正常系：正常にクローズ処理が完了する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseMessageTypesCreationLogic_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.doNothing().when(this.mockMessageTypesCreationLogic).close();

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeMessageTypesCreationLogic");

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("closeMessageTypesCreationLogic"),
            "closeMessageTypesCreationLogicメソッドが正常に実行されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：カスタムロガーを使用するコンストラクタ
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCustomLogger() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Logger customLogger = LoggerFactory.getLogger("TestLogger");

        /* テスト対象の実行 */
        final MessageTypesCreationServiceImpl testConstructor = new MessageTypesCreationServiceImpl(customLogger);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが作成されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：デフォルトコンストラクタ
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefault() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final MessageTypesCreationServiceImpl testConstructor = new MessageTypesCreationServiceImpl();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが作成されること");

    }

    /**
     * processColumns メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcessColumns_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN14001] 項目がnullです。";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN14001;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            /* 準備 */
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストログメッセージ");
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // convertMessageTypesDefinitionは例外を投げないメソッドなので、addItemToRowsで例外を投げる
            Mockito.when(this.mockMessageTypesCreationLogic.convertMessageTypesDefinition()).thenReturn(true);

            // 例外を事前に作成して、モック設定を完了させる
            final KmgToolMsgException testException = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN14001);

            Mockito.when(this.mockMessageTypesCreationLogic.addItemToRows()).thenThrow(testException);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                try {

                    this.reflectionModel.getMethod("processColumns");

                } catch (final KmgReflectionException e) {

                    // KmgReflectionExceptionの原因となった例外を再投げする
                    final Throwable cause = e.getCause();

                    if (cause instanceof KmgToolMsgException) {

                        throw (KmgToolMsgException) cause;

                    }
                    throw e;

                }

            });

            /* 検証の準備 */

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * processColumns メソッドのテスト - 正常系：正常に処理が完了する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcessColumns_normalSuccess() throws Exception {

        /* 準備 */
        Mockito.when(this.mockMessageTypesCreationLogic.convertMessageTypesDefinition()).thenReturn(true);
        Mockito.when(this.mockMessageTypesCreationLogic.addItemToRows()).thenReturn(true);
        Mockito.when(this.mockMessageTypesCreationLogic.addItemNameToRows()).thenReturn(true);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("processColumns");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");

    }

    /**
     * processColumns メソッドのテスト - 準正常系：convertMessageTypesDefinitionがfalseを返す場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcessColumns_semiConvertMessageTypesDefinitionFalse() throws Exception {

        /* 準備 */
        Mockito.when(this.mockMessageTypesCreationLogic.convertMessageTypesDefinition()).thenReturn(false);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("processColumns");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");

    }

    /**
     * readOneLineData メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReadOneLineData_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage
                                                       = "[KMGTOOL_GEN14002] 項目と項目名に分かれていません。「項目=項目名」の設定にしてください。行番号=[{0}]、行データ=[{1}]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN14002;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストログメッセージ");
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            // 例外を事前に作成して、モック設定を完了させる
            final Object[]            messageArgs   = {
                1, "test data"
            };
            final KmgToolMsgException testException = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN14002,
                messageArgs);

            Mockito.when(this.mockMessageTypesCreationLogic.readOneLineOfData()).thenThrow(testException);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                try {

                    this.reflectionModel.getMethod("readOneLineData");

                } catch (final KmgReflectionException e) {

                    // KmgReflectionExceptionの原因となった例外を再投げする
                    final Throwable cause = e.getCause();

                    if (cause instanceof KmgToolMsgException) {

                        throw (KmgToolMsgException) cause;

                    }
                    throw e;

                }

            });

            /* 検証の準備 */

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * readOneLineData メソッドのテスト - 正常系：正常に読み込みが完了する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReadOneLineData_normalSuccess() throws Exception {

        /* 準備 */
        Mockito.when(this.mockMessageTypesCreationLogic.readOneLineOfData()).thenReturn(true);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("readOneLineData");

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");

    }

    /**
     * writeIntermediateFile メソッドのテスト - 異常系：メッセージの種類作成ロジックのクローズでIOExceptionが発生する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFile_errorCloseIOException() throws Exception {

        /* 期待値の定義 */
        final Class<?>           expectedCauseClass    = IOException.class;
        final String             expectedDomainMessage = "[KMGTOOL_GEN14003] メッセージの種類作成ロジックをクローズ中にエラーが発生しました。";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN14003;

        /* 準備 */
        final Path testInputFile = this.tempDir.resolve("test_input.txt");
        this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "test content".getBytes());

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // モックメッセージの種類作成ロジックの設定（クローズ時にIOExceptionを投げる）
            Mockito.doThrow(new IOException("テスト例外")).when(this.mockMessageTypesCreationLogic).close();

            /* テスト対象の実行 */
            final KmgToolMsgException actualException
                = Assertions.assertThrows(KmgToolMsgException.class, () -> this.testTarget.writeIntermediateFile());

            /* 検証の準備 */

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * writeIntermediateFile メソッドのテスト - 正常系：正常に処理が完了する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFile_normalSuccess() throws Exception {

        /* 準備 */
        final Path testInputFile = this.tempDir.resolve("test_input.txt");
        this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "KMGTOOL_GEN14000=メッセージの種類が指定されていません。".getBytes());

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            // モックメッセージの種類作成ロジックの設定
            Mockito.when(this.mockMessageTypesCreationLogic.readOneLineOfData()).thenReturn(true, false);
            Mockito.when(this.mockMessageTypesCreationLogic.convertMessageTypesDefinition()).thenReturn(true);
            Mockito.when(this.mockMessageTypesCreationLogic.addItemToRows()).thenReturn(true);
            Mockito.when(this.mockMessageTypesCreationLogic.addItemNameToRows()).thenReturn(true);
            Mockito.when(this.mockMessageTypesCreationLogic.getItem()).thenReturn("KMGTOOL_GEN14000");
            Mockito.when(this.mockMessageTypesCreationLogic.getItemName()).thenReturn("メッセージの種類が指定されていません。");

            /* テスト対象の実行 */
            final boolean testResult = this.testTarget.writeIntermediateFile();

            /* 検証の準備 */
            final boolean actualResult = testResult;

            /* 検証の実施 */
            Assertions.assertTrue(actualResult, "戻り値が正しいこと");

        }

    }

    /**
     * writeIntermediateFile メソッドのテスト - 準正常系：processColumnsがfalseを返す場合（continue文のカバレッジ）
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFile_semiProcessColumnsFalse() throws Exception {

        /* 準備 */
        final Path testInputFile = this.tempDir.resolve("test_input.txt");
        this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "KMGTOOL_GEN14000=メッセージの種類が指定されていません。".getBytes());

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            // モックメッセージの種類作成ロジックの設定
            // 1回目のreadOneLineOfDataはtrueを返し、processColumnsがfalseを返すようにする
            // 2回目のreadOneLineOfDataはfalseを返してループを終了する
            Mockito.when(this.mockMessageTypesCreationLogic.readOneLineOfData()).thenReturn(true, false);

            // processColumnsがfalseを返すようにするため、convertMessageTypesDefinitionをfalseに設定
            Mockito.when(this.mockMessageTypesCreationLogic.convertMessageTypesDefinition()).thenReturn(false);

            /* テスト対象の実行 */
            final boolean testResult = this.testTarget.writeIntermediateFile();

            /* 検証の準備 */
            final boolean actualResult = testResult;

            /* 検証の実施 */
            Assertions.assertTrue(actualResult, "戻り値が正しいこと");

        }

    }

    /**
     * writeIntermediateFileLine メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @SuppressWarnings("resource")
    @Test
    public void testWriteIntermediateFileLine_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN14003] メッセージの種類作成ロジックをクローズ中にエラーが発生しました。";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN14003;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストログメッセージ");
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            // 例外を事前に作成して、モック設定を完了させる
            final KmgToolMsgException testException = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN14003);

            Mockito.doThrow(testException).when(this.mockMessageTypesCreationLogic).writeIntermediateFile();

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                try {

                    this.reflectionModel.getMethod("writeIntermediateFileLine");

                } catch (final KmgReflectionException e) {

                    // KmgReflectionExceptionの原因となった例外を再投げする
                    final Throwable cause = e.getCause();

                    if (cause instanceof KmgToolMsgException) {

                        throw (KmgToolMsgException) cause;

                    }
                    throw e;

                }

            });

            /* 検証の準備 */

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * writeIntermediateFileLine メソッドのテスト - 正常系：正常に書き込みが完了する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteIntermediateFileLine_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockMessageTypesCreationLogic.writeIntermediateFile()).thenReturn(true);
        Mockito.when(this.mockMessageTypesCreationLogic.getItem()).thenReturn("KMGTOOL_GEN14000");
        Mockito.when(this.mockMessageTypesCreationLogic.getItemName()).thenReturn("メッセージの種類が指定されていません。");

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            /* テスト対象の実行 */
            this.reflectionModel.getMethod("writeIntermediateFileLine");

            /* 検証の準備 */

            /* 検証の実施 */
            // 例外が発生しないことを確認
            Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("writeIntermediateFileLine"),
                "writeIntermediateFileLineメソッドが正常に実行されること");

        }

    }

}
