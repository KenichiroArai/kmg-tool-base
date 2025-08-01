package kmg.tool.is.application.service.impl;

import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    @InjectMocks
    private IsCreationServiceImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** IsFileCreationServiceのモック */
    @Mock
    private IsFileCreationService mockIsFileCreationService;

    /**
     * セットアップ
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        Mockito.mock(KmgMessageSource.class);

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
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testInitialize_normalInitialization() throws KmgReflectionException {

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
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testInitialize_semiNullParameters() throws KmgReflectionException {

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
     * outputInsertionSql メソッドのテスト - 異常系：初期化前の実行
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testOutputInsertionSql_errorNotInitialized() throws KmgReflectionException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN08000] 入力ファイルパスがnullです。";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN08000;
        final Class<?>           expectedCauseClass    = null;

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
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.outputInsertionSql();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * outputInsertionSql メソッドのテスト - 正常系：正常な実行
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testOutputInsertionSql_normalExecution() throws KmgToolMsgException {

        /* 期待値の定義 */
        final Path  expectedInputPath  = this.tempDir.resolve("input.xlsx");
        final Path  expectedOutputPath = this.tempDir.resolve("output.sql");
        final short expectedThreadNum  = 2;

        /* 準備 */
        this.testTarget.initialize(expectedInputPath, expectedOutputPath, expectedThreadNum);

        // IsFileCreationServiceのモック設定
        Mockito.doNothing().when(this.mockIsFileCreationService).initialize(ArgumentMatchers.eq(expectedInputPath),
            ArgumentMatchers.eq(expectedOutputPath), ArgumentMatchers.eq(expectedThreadNum));
        Mockito.doNothing().when(this.mockIsFileCreationService).outputInsertionSql();

        /* テスト対象の実行 */
        this.testTarget.outputInsertionSql();

        /* 検証の実施 */
        Mockito.verify(this.mockIsFileCreationService, Mockito.times(1)).initialize(
            ArgumentMatchers.eq(expectedInputPath), ArgumentMatchers.eq(expectedOutputPath),
            ArgumentMatchers.eq(expectedThreadNum));
        Mockito.verify(this.mockIsFileCreationService, Mockito.times(1)).outputInsertionSql();

    }

}
