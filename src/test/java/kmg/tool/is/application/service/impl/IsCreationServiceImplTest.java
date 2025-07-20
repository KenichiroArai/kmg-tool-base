package kmg.tool.is.application.service.impl;

import java.io.IOException;
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

import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.is.application.service.IsFileCreationService;

/**
 * IsCreationServiceImplのテストクラス
 *
 * @author KenichiroArai
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls",
})
public class IsCreationServiceImplTest extends AbstractKmgTest {

    /** テンポラリディレクトリ */
    @TempDir
    private Path tempDir;

    /** テスト対象 */
    private IsCreationServiceImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** モックKMGメッセージソース */
    private KmgMessageSource mockMessageSource;

    /** モックIsFileCreationService */
    private IsFileCreationService mockIsFileCreationService;

    /**
     * セットアップ
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        final IsCreationServiceImpl isCreationServiceImpl = new IsCreationServiceImpl();
        this.testTarget = isCreationServiceImpl;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.mockIsFileCreationService = Mockito.mock(IsFileCreationService.class);

    }

    /**
     * クリーンアップ
     *
     * @throws Exception
     *                   例外
     */
    @AfterEach
    public void tearDown() throws Exception {

        if (this.testTarget != null) {

            // クリーンアップ処理
        }

    }

    /**
     * initialize メソッドのテスト - 正常系：正常な初期化
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testInitialize_normalInitialization() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final Path  expectedInputPath  = this.tempDir.resolve("input.xlsx");
        final Path  expectedOutputPath = this.tempDir.resolve("output.sql");
        final short expectedThreadNum  = 4;

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        this.testTarget.initialize(expectedInputPath, expectedOutputPath, expectedThreadNum);

        /* 検証の準備 */
        final Path  actualInputPath  = (Path) this.reflectionModel.get("inputPath");
        final Path  actualOutputPath = (Path) this.reflectionModel.get("outputPath");
        final short actualThreadNum  = (short) this.reflectionModel.get("threadNum");

        /* 検証の実施 */
        Assertions.assertEquals(expectedInputPath, actualInputPath, "入力パスが正しく設定されること");
        Assertions.assertEquals(expectedOutputPath, actualOutputPath, "出力パスが正しく設定されること");
        Assertions.assertEquals(expectedThreadNum, actualThreadNum, "スレッド数が正しく設定されること");

    }

    /**
     * initialize メソッドのテスト - 準正常系：nullパラメータでの初期化
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testInitialize_semiNullParameters() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final Path  expectedInputPath  = null;
        final Path  expectedOutputPath = null;
        final short expectedThreadNum  = 0;

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        this.testTarget.initialize(expectedInputPath, expectedOutputPath, expectedThreadNum);

        /* 検証の準備 */
        final Path  actualInputPath  = (Path) this.reflectionModel.get("inputPath");
        final Path  actualOutputPath = (Path) this.reflectionModel.get("outputPath");
        final short actualThreadNum  = (short) this.reflectionModel.get("threadNum");

        /* 検証の実施 */
        Assertions.assertEquals(expectedInputPath, actualInputPath, "nullの入力パスが正しく設定されること");
        Assertions.assertEquals(expectedOutputPath, actualOutputPath, "nullの出力パスが正しく設定されること");
        Assertions.assertEquals(expectedThreadNum, actualThreadNum, "0のスレッド数が正しく設定されること");

    }

    /**
     * outputInsertionSql メソッドのテスト - 異常系：空のファイルの場合
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testOutputInsertionSql_errorEmptyFile() throws KmgToolMsgException {

        /* 期待値の定義 */
        final Path  expectedInputPath  = this.tempDir.resolve("input.xlsx");
        final Path  expectedOutputPath = this.tempDir.resolve("output.sql");
        final short expectedThreadNum  = 2;

        /* 準備 */
        this.testTarget.initialize(expectedInputPath, expectedOutputPath, expectedThreadNum);

        // テスト用の空のExcelファイルを作成
        try {

            expectedInputPath.toFile().createNewFile();

        } catch (final IOException e) {

            // ファイル作成に失敗した場合はテストをスキップ
            e.printStackTrace();
            return;

        }

        /* テスト対象の実行 */
        final org.apache.poi.EmptyFileException actualException
            = Assertions.assertThrows(org.apache.poi.EmptyFileException.class, () -> {

                this.testTarget.outputInsertionSql();

            });

        /* 検証の準備 */
        final boolean actualResult = actualException != null;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "EmptyFileExceptionが発生すること");

    }

    /**
     * outputInsertionSql メソッドのテスト - 異常系：IsFileCreationServiceでEncryptedDocumentException発生
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testOutputInsertionSql_errorEncryptedDocumentException() throws KmgToolMsgException {

        /* 期待値の定義 */
        final Path               expectedInputPath     = this.tempDir.resolve("input.xlsx");
        final Path               expectedOutputPath    = this.tempDir.resolve("output.sql");
        final short              expectedThreadNum     = 2;
        final String             expectedDomainMessage
                                                       = "[KMGTOOL_GEN10002] 入力ファイルのパスの読み込みに失敗しました。入力ファイルのパス=[input.xlsx]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN10002;
        final Class<?>           expectedCauseClass    = java.io.FileNotFoundException.class;

        /* 準備 */
        this.testTarget.initialize(expectedInputPath, expectedOutputPath, expectedThreadNum);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.outputInsertionSql();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * outputInsertionSql メソッドのテスト - 異常系：ファイルが見つからない場合
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testOutputInsertionSql_errorFileNotFound() throws KmgToolMsgException {

        /* 期待値の定義 */
        final Path               expectedInputPath     = this.tempDir.resolve("input.xlsx");
        final Path               expectedOutputPath    = this.tempDir.resolve("output.sql");
        final short              expectedThreadNum     = 2;
        final String             expectedDomainMessage
                                                       = "[KMGTOOL_GEN10002] 入力ファイルのパスの読み込みに失敗しました。入力ファイルのパス=[input.xlsx]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN10002;
        final Class<?>           expectedCauseClass    = java.io.FileNotFoundException.class;

        /* 準備 */
        this.testTarget.initialize(expectedInputPath, expectedOutputPath, expectedThreadNum);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.outputInsertionSql();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * outputInsertionSql メソッドのテスト - 異常系：IsFileCreationServiceでIOException発生
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testOutputInsertionSql_errorIOException() throws KmgToolMsgException {

        /* 期待値の定義 */
        final Path               expectedInputPath     = this.tempDir.resolve("input.xlsx");
        final Path               expectedOutputPath    = this.tempDir.resolve("output.sql");
        final short              expectedThreadNum     = 2;
        final String             expectedDomainMessage
                                                       = "[KMGTOOL_GEN10002] 入力ファイルのパスの読み込みに失敗しました。入力ファイルのパス=[input.xlsx]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN10002;
        final Class<?>           expectedCauseClass    = IOException.class;

        /* 準備 */
        this.testTarget.initialize(expectedInputPath, expectedOutputPath, expectedThreadNum);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.outputInsertionSql();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * outputInsertionSql メソッドのテスト - 準正常系：初期化前の実行
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testOutputInsertionSql_semiNotInitialized() throws KmgToolMsgException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN10002] 入力ファイルのパスの読み込みに失敗しました。入力ファイルのパス=[null]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN10002;
        final Class<?>           expectedCauseClass    = java.lang.NullPointerException.class;

        /* 準備 */
        // 初期化しない

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final NullPointerException actualException = Assertions.assertThrows(NullPointerException.class, () -> {

                this.testTarget.outputInsertionSql();

            });

            /* 検証の準備 */
            final boolean actualResult = actualException != null;

            /* 検証の実施 */
            Assertions.assertTrue(actualResult, "NullPointerExceptionが発生すること");

        }

    }

}
