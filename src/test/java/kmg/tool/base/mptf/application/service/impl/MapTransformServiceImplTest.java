package kmg.tool.base.mptf.application.service.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import kmg.fund.domain.logic.FileIteratorLogic;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseGenMsgTypes;

/**
 * マッピング変換サービス実装テスト
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class MapTransformServiceImplTest extends AbstractKmgTest {

    /**
     * テンポラリディレクトリ
     *
     * @since 0.2.0
     */
    @TempDir
    private Path tempDir;

    /**
     * テスト対象
     *
     * @since 0.2.0
     */
    private MapTransformServiceImpl testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.2.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * モックKMGメッセージソース
     *
     * @since 0.2.0
     */
    private KmgMessageSource mockMessageSource;

    /**
     * モックFileIteratorLogic
     *
     * @since 0.2.0
     */
    private FileIteratorLogic mockFileIteratorLogic;

    /**
     * セットアップ
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @BeforeEach
    public void setUp() throws Exception {

        this.testTarget = new MapTransformServiceImpl();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.mockFileIteratorLogic = Mockito.mock(FileIteratorLogic.class);

        /* モックをテスト対象に設定 */
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("fileIteratorLogic", this.mockFileIteratorLogic);

        // 初期化処理のモック設定
        Mockito.when(this.mockFileIteratorLogic.initialize(ArgumentMatchers.any())).thenReturn(true);

    }

    /**
     * クリーンアップ
     *
     * @since 0.2.0
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
     * コンストラクタ メソッドのテスト - 正常系：カスタムロガーを使用するコンストラクタ
     *
     * @since 0.2.0
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
        final MapTransformServiceImpl testConstructor = new MapTransformServiceImpl(customLogger);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが作成されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：デフォルトコンストラクタ
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefault() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final MapTransformServiceImpl testConstructor = new MapTransformServiceImpl();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが作成されること");

    }

    /**
     * getTargetPath メソッドのテスト - 正常系：対象ファイルパスが設定されている場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetTargetPath_normalPathSet() throws Exception {

        /* 期待値の定義 */
        final Path expectedPath = this.tempDir.resolve("test.txt");

        /* 準備 */
        this.reflectionModel.set("targetPath", expectedPath);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getTargetPath();

        /* 検証の準備 */
        final Path actualPath = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedPath, actualPath, "対象ファイルパスが正しく返されること");

    }

    /**
     * getTargetPath メソッドのテスト - 準正常系：対象ファイルパスがnullの場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetTargetPath_semiPathNull() throws Exception {

        /* 期待値の定義 */
        final Path expectedPath = null;

        /* 準備 */
        this.reflectionModel.set("targetPath", null);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getTargetPath();

        /* 検証の準備 */
        final Path actualPath = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedPath, actualPath, "nullが正しく返されること");

    }

    /**
     * initialize メソッドのテスト - 異常系：JdtsIoLogicの初期化で例外が発生する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_errorJdtsIoLogicException() throws Exception {

        /* 期待値の定義 */
        final Path                expectedPath    = this.tempDir.resolve("test.txt");
        final Map<String, String> expectedMapping = new HashMap<>();
        expectedMapping.put("oldValue", "newValue");

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // 例外を事前に作成して、モック設定を完了させる
            final Object[]                genMsgArgs    = {
                0L, 0L
            };
            final KmgToolBaseMsgException testException = new KmgToolBaseMsgException(
                KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004, genMsgArgs);

            Mockito.when(this.mockFileIteratorLogic.initialize(ArgumentMatchers.any())).thenThrow(testException);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException actualException
                = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                    this.testTarget.initialize(expectedPath, expectedMapping);

                });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(actualException, "例外が発生すること");

        }

    }

    /**
     * initialize メソッドのテスト - 正常系：正常に初期化が完了する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final Path                expectedPath    = this.tempDir.resolve("test.txt");
        final Map<String, String> expectedMapping = new HashMap<>();
        expectedMapping.put("oldValue", "newValue");

        /* 準備 */
        Mockito.when(this.mockFileIteratorLogic.initialize(ArgumentMatchers.any())).thenReturn(true);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(expectedPath, expectedMapping);

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final Path    actualPath   = (Path) this.reflectionModel.get("targetPath");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedPath, actualPath, "対象ファイルパスが正しく設定されること");

    }

    /**
     * process メソッドのテスト - 異常系：JdtsIoLogicのloadで例外が発生する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorJdtsIoLogicLoadException() throws Exception {

        /* 期待値の定義 */
        final String                 expectedDomainMessage = "[KMGTOOLBASE_GEN19004] ";
        final KmgToolBaseGenMsgTypes expectedMessageTypes  = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // 例外を事前に作成して、モック設定を完了させる
            final Object[]                genMsgArgs    = {
                0L, 0L
            };
            final KmgToolBaseMsgException testException = new KmgToolBaseMsgException(
                KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004, genMsgArgs);

            Mockito.when(this.mockFileIteratorLogic.load()).thenThrow(testException);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException actualException
                = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                    this.testTarget.process();

                });

            /* 検証の準備 */

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * process メソッドのテスト - 異常系：JdtsIoLogicのnextFileで例外が発生する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorJdtsIoLogicNextFileException() throws Exception {

        /* 期待値の定義 */
        final String                 expectedDomainMessage = "[KMGTOOLBASE_GEN19004] ";
        final KmgToolBaseGenMsgTypes expectedMessageTypes  = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004;

        /* 準備 */
        final List<Path> filePathList = new ArrayList<>();
        filePathList.add(this.tempDir.resolve("Test1.java"));

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // 例外を事前に作成して、モック設定を完了させる
            final Object[]                genMsgArgs    = {
                0L, 0L
            };
            final KmgToolBaseMsgException testException = new KmgToolBaseMsgException(
                KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004, genMsgArgs);

            Mockito.when(this.mockFileIteratorLogic.load()).thenReturn(true);
            Mockito.when(this.mockFileIteratorLogic.getFilePathList()).thenReturn(filePathList);
            Mockito.when(this.mockFileIteratorLogic.nextFile()).thenThrow(testException);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException actualException
                = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                    this.testTarget.process();

                });

            /* 検証の準備 */

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * process メソッドのテスト - 異常系：JdtsIoLogicのresetFileIndexで例外が発生する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorJdtsIoLogicResetFileIndexException() throws Exception {

        /* 期待値の定義 */
        final String                 expectedDomainMessage = "[KMGTOOLBASE_GEN19004] ";
        final KmgToolBaseGenMsgTypes expectedMessageTypes  = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004;

        /* 準備 */
        final List<Path> filePathList = new ArrayList<>();
        filePathList.add(this.tempDir.resolve("Test1.java"));

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // 例外を事前に作成して、モック設定を完了させる
            final Object[]                genMsgArgs    = {
                0L, 0L
            };
            final KmgToolBaseMsgException testException = new KmgToolBaseMsgException(
                KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004, genMsgArgs);

            Mockito.when(this.mockFileIteratorLogic.load()).thenReturn(true);
            Mockito.when(this.mockFileIteratorLogic.getFilePathList()).thenReturn(filePathList);
            Mockito.when(this.mockFileIteratorLogic.nextFile()).thenReturn(false);
            Mockito.when(this.mockFileIteratorLogic.resetFileIndex()).thenThrow(testException);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException actualException
                = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                    this.testTarget.process();

                });

            /* 検証の準備 */

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * process メソッドのテスト - 異常系：置換数が一致しない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorReplaceCountMismatch() throws Exception {

        /* 期待値の定義 */
        final String                 expectedDomainMessage = "[KMGTOOLBASE_GEN19004] ";
        final KmgToolBaseGenMsgTypes expectedMessageTypes  = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004;

        /* 準備 */
        final List<Path> filePathList = new ArrayList<>();
        filePathList.add(this.tempDir.resolve("Test1.java"));

        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");
        Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(expectedDomainMessage);
        Mockito.when(this.mockFileIteratorLogic.load()).thenReturn(true);
        Mockito.when(this.mockFileIteratorLogic.getFilePathList()).thenReturn(filePathList);
        Mockito.when(this.mockFileIteratorLogic.nextFile()).thenReturn(false, false);
        Mockito.when(this.mockFileIteratorLogic.loadContent()).thenReturn(true);
        Mockito.when(this.mockFileIteratorLogic.getReadContent()).thenReturn("test content");
        Mockito.when(this.mockFileIteratorLogic.writeContent()).thenReturn(true);
        Mockito.when(this.mockFileIteratorLogic.resetFileIndex()).thenReturn(true);

        // マッピングを設定（置換が発生するように）
        final Map<String, String> mapping = new HashMap<>();
        mapping.put("test", "replacement");
        this.reflectionModel.set("targetValueToReplacementValueMapping", mapping);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException actualException
                = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                    this.testTarget.process();

                });

            /* 検証の準備 */

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * process メソッドのテスト - 正常系：正常に処理が完了する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final List<Path> filePathList = new ArrayList<>();
        filePathList.add(this.tempDir.resolve("Test1.java"));
        filePathList.add(this.tempDir.resolve("Test2.java"));

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");
            Mockito.when(this.mockFileIteratorLogic.load()).thenReturn(true);
            Mockito.when(this.mockFileIteratorLogic.getFilePathList()).thenReturn(filePathList);
            Mockito.when(this.mockFileIteratorLogic.nextFile()).thenReturn(true, false, true, false);
            Mockito.when(this.mockFileIteratorLogic.loadContent()).thenReturn(true);
            Mockito.when(this.mockFileIteratorLogic.getReadContent()).thenReturn("oldValue test content",
                "test-uuid-1 test content");
            Mockito.when(this.mockFileIteratorLogic.writeContent()).thenReturn(true);
            Mockito.doNothing().when(this.mockFileIteratorLogic).setWriteContent(ArgumentMatchers.anyString());
            Mockito.when(this.mockFileIteratorLogic.resetFileIndex()).thenReturn(true);

            // マッピングを設定
            final Map<String, String> mapping = new HashMap<>();
            mapping.put("oldValue", "newValue");
            this.reflectionModel.set("targetValueToReplacementValueMapping", mapping);

            // UUIDマッピングも事前に設定（replaceTargetValuesWithUuidで設定されるはずだが、テストでは事前に設定）
            final Map<String, String> uuidMapping = new HashMap<>();
            uuidMapping.put("test-uuid-1", "newValue");
            this.reflectionModel.set("uuidToReplacementValueMapping", uuidMapping);

            /* テスト対象の実行 */
            final boolean testResult = this.testTarget.process();

            /* 検証の準備 */
            final boolean actualResult = testResult;

            /* 検証の実施 */
            Assertions.assertTrue(actualResult, "戻り値が正しいこと");

        }

    }

    /**
     * replaceTargetValuesWithUuid メソッドのテスト - 異常系：loadContentで例外が発生する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceTargetValuesWithUuid_errorLoadContentException() throws Exception {

        /* 期待値の定義 */
        final String                 expectedDomainMessage = "[KMGTOOLBASE_GEN19004] ";
        final KmgToolBaseGenMsgTypes expectedMessageTypes  = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // 例外を事前に作成して、モック設定を完了させる
            final Object[]                genMsgArgs    = {
                0L, 0L
            };
            final KmgToolBaseMsgException testException = new KmgToolBaseMsgException(
                KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004, genMsgArgs);

            Mockito.when(this.mockFileIteratorLogic.loadContent()).thenThrow(testException);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException actualException
                = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                    try {

                        this.reflectionModel.getMethod("replaceTargetValuesWithUuid");

                    } catch (final KmgReflectionException e) {

                        // KmgReflectionExceptionの原因となった例外を再投げする
                        final Throwable cause = e.getCause();

                        if (cause instanceof KmgToolBaseMsgException) {

                            throw (KmgToolBaseMsgException) cause;

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
     * replaceTargetValuesWithUuid メソッドのテスト - 異常系：writeContentで例外が発生する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceTargetValuesWithUuid_errorWriteContentException() throws Exception {

        /* 期待値の定義 */
        final String                 expectedDomainMessage = "[KMGTOOLBASE_GEN19004] ";
        final KmgToolBaseGenMsgTypes expectedMessageTypes  = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // 例外を事前に作成して、モック設定を完了させる
            final Object[]                genMsgArgs    = {
                0L, 0L
            };
            final KmgToolBaseMsgException testException = new KmgToolBaseMsgException(
                KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004, genMsgArgs);

            Mockito.when(this.mockFileIteratorLogic.loadContent()).thenReturn(true);
            Mockito.when(this.mockFileIteratorLogic.getReadContent()).thenReturn("oldValue");
            Mockito.when(this.mockFileIteratorLogic.writeContent()).thenThrow(testException);

            // マッピングを設定
            final Map<String, String> mapping = new HashMap<>();
            mapping.put("oldValue", "newValue");
            this.reflectionModel.set("targetValueToReplacementValueMapping", mapping);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException actualException
                = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                    try {

                        this.reflectionModel.getMethod("replaceTargetValuesWithUuid");

                    } catch (final KmgReflectionException e) {

                        // KmgReflectionExceptionの原因となった例外を再投げする
                        final Throwable cause = e.getCause();

                        if (cause instanceof KmgToolBaseMsgException) {

                            throw (KmgToolBaseMsgException) cause;

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
     * replaceTargetValuesWithUuid メソッドのテスト - 正常系：正常に置換が完了する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceTargetValuesWithUuid_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final long expectedResult = 2L;

        /* 準備 */
        Mockito.when(this.mockFileIteratorLogic.loadContent()).thenReturn(true);
        Mockito.when(this.mockFileIteratorLogic.getReadContent()).thenReturn("oldValue1 oldValue2");
        Mockito.when(this.mockFileIteratorLogic.writeContent()).thenReturn(true);

        // マッピングを設定
        final Map<String, String> mapping = new HashMap<>();
        mapping.put("oldValue1", "newValue1");
        mapping.put("oldValue2", "newValue2");
        this.reflectionModel.set("targetValueToReplacementValueMapping", mapping);

        /* テスト対象の実行 */
        final long testResult = (Long) this.reflectionModel.getMethod("replaceTargetValuesWithUuid");

        /* 検証の準備 */
        final long actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "置換数が正しいこと");

    }

    /**
     * replaceTargetValuesWithUuid メソッドのテスト - 準正常系：loadContentがfalseを返す場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceTargetValuesWithUuid_semiLoadContentFalse() throws Exception {

        /* 期待値の定義 */
        final long expectedResult = 0L;

        /* 準備 */
        Mockito.when(this.mockFileIteratorLogic.loadContent()).thenReturn(false);

        /* テスト対象の実行 */
        final long testResult = (Long) this.reflectionModel.getMethod("replaceTargetValuesWithUuid");

        /* 検証の準備 */
        final long actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "置換数が0であること");

    }

    /**
     * replaceUuidWithReplacementValues メソッドのテスト - 異常系：loadContentで例外が発生する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceUuidWithReplacementValues_errorLoadContentException() throws Exception {

        /* 期待値の定義 */
        final String                 expectedDomainMessage = "[KMGTOOLBASE_GEN19004] ";
        final KmgToolBaseGenMsgTypes expectedMessageTypes  = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // 例外を事前に作成して、モック設定を完了させる
            final Object[]                genMsgArgs    = {
                0L, 0L
            };
            final KmgToolBaseMsgException testException = new KmgToolBaseMsgException(
                KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004, genMsgArgs);

            Mockito.when(this.mockFileIteratorLogic.loadContent()).thenThrow(testException);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException actualException
                = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                    try {

                        this.reflectionModel.getMethod("replaceUuidWithReplacementValues");

                    } catch (final KmgReflectionException e) {

                        // KmgReflectionExceptionの原因となった例外を再投げする
                        final Throwable cause = e.getCause();

                        if (cause instanceof KmgToolBaseMsgException) {

                            throw (KmgToolBaseMsgException) cause;

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
     * replaceUuidWithReplacementValues メソッドのテスト - 異常系：writeContentで例外が発生する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceUuidWithReplacementValues_errorWriteContentException() throws Exception {

        /* 期待値の定義 */
        final String                 expectedDomainMessage = "[KMGTOOLBASE_GEN19004] ";
        final KmgToolBaseGenMsgTypes expectedMessageTypes  = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // 例外を事前に作成して、モック設定を完了させる
            final Object[]                genMsgArgs    = {
                0L, 0L
            };
            final KmgToolBaseMsgException testException = new KmgToolBaseMsgException(
                KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN19004, genMsgArgs);

            Mockito.when(this.mockFileIteratorLogic.loadContent()).thenReturn(true);
            Mockito.when(this.mockFileIteratorLogic.getReadContent()).thenReturn("uuid1");
            Mockito.when(this.mockFileIteratorLogic.writeContent()).thenThrow(testException);

            // UUIDマッピングを設定
            final Map<String, String> uuidMapping = new HashMap<>();
            uuidMapping.put("uuid1", "newValue1");
            this.reflectionModel.set("uuidToReplacementValueMapping", uuidMapping);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException actualException
                = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                    try {

                        this.reflectionModel.getMethod("replaceUuidWithReplacementValues");

                    } catch (final KmgReflectionException e) {

                        // KmgReflectionExceptionの原因となった例外を再投げする
                        final Throwable cause = e.getCause();

                        if (cause instanceof KmgToolBaseMsgException) {

                            throw (KmgToolBaseMsgException) cause;

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
     * replaceUuidWithReplacementValues メソッドのテスト - 正常系：正常に置換が完了する場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceUuidWithReplacementValues_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final long expectedResult = 2L;

        /* 準備 */
        Mockito.when(this.mockFileIteratorLogic.loadContent()).thenReturn(true);
        Mockito.when(this.mockFileIteratorLogic.getReadContent()).thenReturn("uuid1 uuid2");
        Mockito.when(this.mockFileIteratorLogic.writeContent()).thenReturn(true);

        // UUIDマッピングを設定
        final Map<String, String> uuidMapping = new HashMap<>();
        uuidMapping.put("uuid1", "newValue1");
        uuidMapping.put("uuid2", "newValue2");
        this.reflectionModel.set("uuidToReplacementValueMapping", uuidMapping);

        /* テスト対象の実行 */
        final long testResult = (Long) this.reflectionModel.getMethod("replaceUuidWithReplacementValues");

        /* 検証の準備 */
        final long actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "置換数が正しいこと");

    }

    /**
     * replaceUuidWithReplacementValues メソッドのテスト - 準正常系：loadContentがfalseを返す場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceUuidWithReplacementValues_semiLoadContentFalse() throws Exception {

        /* 期待値の定義 */
        final long expectedResult = 0L;

        /* 準備 */
        Mockito.when(this.mockFileIteratorLogic.loadContent()).thenReturn(false);

        /* テスト対象の実行 */
        final long testResult = (Long) this.reflectionModel.getMethod("replaceUuidWithReplacementValues");

        /* 検証の準備 */
        final long actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "置換数が0であること");

    }

}
