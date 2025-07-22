package kmg.tool.is.application.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.poi.EmptyFileException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.is.application.logic.IsBasicInformationLogic;
import kmg.tool.is.application.service.IslDataSheetCreationService;

/**
 * IsFileCreationServiceImplのテストクラス
 *
 * @author KenichiroArai
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class IsFileCreationServiceImplTest extends AbstractKmgTest {

    /** テンポラリディレクトリ */
    @TempDir
    private Path tempDir;

    /** テスト対象 */
    @InjectMocks
    private IsFileCreationServiceImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** IslDataSheetCreationServiceのモック */
    @Mock
    private IslDataSheetCreationService mockIslDataSheetCreationService;

    /** IsBasicInformationLogicのモック */
    @Mock
    private IsBasicInformationLogic mockIsBasicInformationLogic;

    /** Sheetのモック */
    @Mock
    private Sheet mockSheet;

    /** KmgMessageSourceのモック */
    private KmgMessageSource mockMessageSource;

    /**
     * セットアップ
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // islDataSheetCreationServiceをリフレクションで設定
        this.reflectionModel.set("islDataSheetCreationService", this.mockIslDataSheetCreationService);

        // insertionSqlFileCreationLogicをリフレクションで設定
        this.reflectionModel.set("insertionSqlFileCreationLogic", this.mockIsBasicInformationLogic);

        // KmgMessageSourceのモックを作成
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

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
     * getExecutorService メソッドのテスト - 正常系：スレッド数が0より大きい場合
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                例外
     */
    @Test
    public void testGetExecutorService_normalThreadNumGreaterThanZero() throws KmgReflectionException, Exception {

        /* 期待値の定義 */
        final short expectedThreadNum = 4;

        /* 準備 */
        this.reflectionModel.set("threadNum", expectedThreadNum);

        /* テスト対象の実行 */
        final ExecutorService actualResult = (ExecutorService) this.reflectionModel.getMethod("getExecutorService");

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        Assertions.assertNotNull(actualResult, "FixedThreadPoolが作成されること");
        actualResult.shutdown();

    }

    /**
     * getExecutorService メソッドのテスト - 正常系：スレッド数が負の場合
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                例外
     */
    @Test
    public void testGetExecutorService_normalThreadNumNegative() throws KmgReflectionException, Exception {

        /* 期待値の定義 */
        final short expectedThreadNum = -1;

        /* 準備 */
        this.reflectionModel.set("threadNum", expectedThreadNum);

        /* テスト対象の実行 */
        final ExecutorService actualResult = (ExecutorService) this.reflectionModel.getMethod("getExecutorService");

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        Assertions.assertNotNull(actualResult, "CachedThreadPoolが作成されること");
        actualResult.shutdown();

    }

    /**
     * getExecutorService メソッドのテスト - 正常系：スレッド数が0の場合
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                例外
     */
    @Test
    public void testGetExecutorService_normalThreadNumZero() throws KmgReflectionException, Exception {

        /* 期待値の定義 */
        final short expectedThreadNum = 0;

        /* 準備 */
        this.reflectionModel.set("threadNum", expectedThreadNum);

        /* テスト対象の実行 */
        final ExecutorService actualResult = (ExecutorService) this.reflectionModel.getMethod("getExecutorService");

        /* 検証の準備 */
        // 検証の準備なし

        /* 検証の実施 */
        Assertions.assertNotNull(actualResult, "CachedThreadPoolが作成されること");
        actualResult.shutdown();

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
        final Path  expectedOutputPath = this.tempDir.resolve("output");
        final short expectedThreadNum  = 4;

        /* 準備 */
        // 準備なし

        /* テスト対象の実行 */
        this.testTarget.initialize(expectedInputPath, expectedOutputPath, expectedThreadNum);

        /* 検証の準備 */
        final Path  actualInputPath  = (Path) this.reflectionModel.get("inputPath");
        final Path  actualOutputPath = (Path) this.reflectionModel.get("outputPath");
        final short actualThreadNum  = (Short) this.reflectionModel.get("threadNum");

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

        /* 準備 */
        final Path  testInputPath  = null;
        final Path  testOutputPath = null;
        final short testThreadNum  = 0;

        /* テスト対象の実行 */
        this.testTarget.initialize(testInputPath, testOutputPath, testThreadNum);

        /* 検証の準備 */
        final Path  actualInputPath  = (Path) this.reflectionModel.get("inputPath");
        final Path  actualOutputPath = (Path) this.reflectionModel.get("outputPath");
        final short actualThreadNum  = (Short) this.reflectionModel.get("threadNum");

        /* 検証の実施 */
        Assertions.assertNull(actualInputPath, "nullの入力パスが正しく設定されること");
        Assertions.assertNull(actualOutputPath, "nullの出力パスが正しく設定されること");
        Assertions.assertEquals(testThreadNum, actualThreadNum, "0のスレッド数が正しく設定されること");

    }

    /**
     * outputInsertionSql メソッドのテスト - 異常系：EmptyFileException発生
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testOutputInsertionSql_errorEmptyFileException() throws KmgReflectionException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN10004] 入力ファイルが空です。入力ファイルパス=[test.xlsx]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN10004;
        final Class<?>           expectedCauseClass    = EmptyFileException.class;

        /* 準備 */
        final Path testInputPath = this.tempDir.resolve("test.xlsx");
        this.reflectionModel.set("inputPath", testInputPath);

        // 実際のファイルを作成（空のファイル）
        try {

            testInputPath.toFile().createNewFile();

        } catch (final IOException e) {

            throw new RuntimeException("テストファイルの作成に失敗しました", e);

        }

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // WorkbookFactory.createでEmptyFileExceptionを発生させる
            try (final MockedStatic<org.apache.poi.ss.usermodel.WorkbookFactory> mockedWorkbookFactory
                = Mockito.mockStatic(org.apache.poi.ss.usermodel.WorkbookFactory.class)) {

                mockedWorkbookFactory.when(() -> org.apache.poi.ss.usermodel.WorkbookFactory
                    .create(ArgumentMatchers.any(java.io.InputStream.class))).thenThrow(new EmptyFileException());

                /* テスト対象の実行 */
                final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                    this.testTarget.outputInsertionSql();

                });

                /* 検証の実施 */
                this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                    expectedMessageTypes);

            }

        }

    }

    /**
     * outputInsertionSql メソッドのテスト - 異常系：EncryptedDocumentException発生
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testOutputInsertionSql_errorEncryptedDocumentException() throws KmgReflectionException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN10001] 入力ファイルが暗号化されています。入力ファイルパス=[test.xlsx]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN10001;
        final Class<?>           expectedCauseClass    = EncryptedDocumentException.class;

        /* 準備 */
        final Path testInputPath = this.tempDir.resolve("test.xlsx");
        this.reflectionModel.set("inputPath", testInputPath);

        // 実際のファイルを作成（空のファイル）
        try {

            testInputPath.toFile().createNewFile();

        } catch (final IOException e) {

            throw new RuntimeException("テストファイルの作成に失敗しました", e);

        }

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // WorkbookFactory.createでEncryptedDocumentExceptionを発生させる
            try (final MockedStatic<org.apache.poi.ss.usermodel.WorkbookFactory> mockedWorkbookFactory
                = Mockito.mockStatic(org.apache.poi.ss.usermodel.WorkbookFactory.class)) {

                mockedWorkbookFactory
                    .when(() -> org.apache.poi.ss.usermodel.WorkbookFactory
                        .create(ArgumentMatchers.any(java.io.InputStream.class)))
                    .thenThrow(new EncryptedDocumentException("テスト用のEncryptedDocumentException"));

                /* テスト対象の実行 */
                final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                    this.testTarget.outputInsertionSql();

                });

                /* 検証の実施 */
                this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                    expectedMessageTypes);

            }

        }

    }

    /**
     * outputInsertionSql メソッドのテスト - 異常系：IOException発生（FileInputStream作成時）
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testOutputInsertionSql_errorIOExceptionInFileInputStream() throws KmgReflectionException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN10002] 入力ファイルが見つかりません。入力ファイルパス=[test.xlsx]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN10002;
        final Class<?>           expectedCauseClass    = java.io.FileNotFoundException.class;

        /* 準備 */
        final Path testInputPath = this.tempDir.resolve("test.xlsx");
        this.reflectionModel.set("inputPath", testInputPath);

        // ファイルは作成しない（存在しない状態にする）

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

                this.testTarget.outputInsertionSql();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * outputInsertionSql メソッドのテスト - 異常系：IOException発生（WorkbookFactory.create内）
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testOutputInsertionSql_errorIOExceptionInWorkbookFactory() throws KmgReflectionException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN10005] 入力ファイルの読み込みに失敗しました。入力ファイルパス=[test.xlsx]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN10005;
        final Class<?>           expectedCauseClass    = IOException.class;

        /* 準備 */
        final Path testInputPath = this.tempDir.resolve("test.xlsx");
        this.reflectionModel.set("inputPath", testInputPath);

        // 実際のファイルを作成（空のファイル）
        try {

            testInputPath.toFile().createNewFile();

        } catch (final IOException e) {

            throw new RuntimeException("テストファイルの作成に失敗しました", e);

        }

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // WorkbookFactory.createでIOExceptionを発生させる
            try (final MockedStatic<org.apache.poi.ss.usermodel.WorkbookFactory> mockedWorkbookFactory
                = Mockito.mockStatic(org.apache.poi.ss.usermodel.WorkbookFactory.class)) {

                mockedWorkbookFactory
                    .when(() -> org.apache.poi.ss.usermodel.WorkbookFactory
                        .create(ArgumentMatchers.any(java.io.InputStream.class)))
                    .thenThrow(new IOException("テスト用のIOException"));

                /* テスト対象の実行 */
                final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                    this.testTarget.outputInsertionSql();

                });

                /* 検証の実施 */
                this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                    expectedMessageTypes);

            }

        }

    }

    /**
     * outputInsertionSql メソッドのテスト - 正常系：正常な実行
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testOutputInsertionSql_normalExecution() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final Path  expectedInputPath  = this.tempDir.resolve("input.xlsx");
        final Path  expectedOutputPath = this.tempDir.resolve("output");
        final short expectedThreadNum  = 2;

        /* 準備 */
        this.reflectionModel.set("inputPath", expectedInputPath);
        this.reflectionModel.set("outputPath", expectedOutputPath);
        this.reflectionModel.set("threadNum", expectedThreadNum);

        // テスト用のExcelファイルを作成
        this.createTestExcelFile(expectedInputPath);

        // IsBasicInformationLogicのモック設定
        Mockito.doNothing().when(this.mockIsBasicInformationLogic).initialize(ArgumentMatchers.any(Workbook.class));
        Mockito.when(this.mockIsBasicInformationLogic.getKmgDbTypes()).thenReturn(KmgDbTypes.POSTGRE_SQL);
        Mockito.when(this.mockIsBasicInformationLogic.getSqlIdMap()).thenReturn(new HashMap<>());

        // IslDataSheetCreationServiceのモック設定
        Mockito.doNothing().when(this.mockIslDataSheetCreationService).initialize(ArgumentMatchers.any(),
            ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.doNothing().when(this.mockIslDataSheetCreationService).run();

        /* テスト対象の実行 */
        this.testTarget.outputInsertionSql();

        /* 検証の実施 */
        Mockito.verify(this.mockIsBasicInformationLogic, Mockito.times(1))
            .initialize(ArgumentMatchers.any(Workbook.class));
        Mockito.verify(this.mockIsBasicInformationLogic, Mockito.times(1)).getKmgDbTypes();
        Mockito.verify(this.mockIsBasicInformationLogic, Mockito.times(1)).getSqlIdMap();
        Mockito.verify(this.mockIslDataSheetCreationService, Mockito.times(1)).initialize(ArgumentMatchers.any(),
            ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verify(this.mockIslDataSheetCreationService, Mockito.times(1)).run();

    }

    /**
     * processWorkbook メソッドのテスト - 正常系：正常な処理
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                例外
     */
    @Test
    public void testProcessWorkbook_normalProcessing() throws KmgReflectionException, Exception {

        /* 期待値の定義 */
        final KmgDbTypes          expectedKmgDbTypes = KmgDbTypes.POSTGRE_SQL;
        final Map<String, String> expectedSqlIdMap   = new HashMap<>();
        final Path                expectedOutputPath = this.tempDir.resolve("output");

        /* 準備 */
        final Workbook testWorkbook = this.createTestWorkbook();
        this.reflectionModel.set("outputPath", expectedOutputPath);

        // IsBasicInformationLogicのモック設定
        Mockito.doNothing().when(this.mockIsBasicInformationLogic).initialize(ArgumentMatchers.any(Workbook.class));
        Mockito.when(this.mockIsBasicInformationLogic.getKmgDbTypes()).thenReturn(expectedKmgDbTypes);
        Mockito.when(this.mockIsBasicInformationLogic.getSqlIdMap()).thenReturn(expectedSqlIdMap);

        // IslDataSheetCreationServiceのモック設定
        Mockito.doNothing().when(this.mockIslDataSheetCreationService).initialize(ArgumentMatchers.any(),
            ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.doNothing().when(this.mockIslDataSheetCreationService).run();

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processWorkbook", testWorkbook);

        /* 検証の実施 */
        Mockito.verify(this.mockIsBasicInformationLogic, Mockito.times(1))
            .initialize(ArgumentMatchers.eq(testWorkbook));
        Mockito.verify(this.mockIsBasicInformationLogic, Mockito.times(1)).getKmgDbTypes();
        Mockito.verify(this.mockIsBasicInformationLogic, Mockito.times(1)).getSqlIdMap();
        Mockito.verify(this.mockIslDataSheetCreationService, Mockito.times(1)).initialize(
            ArgumentMatchers.eq(expectedKmgDbTypes), ArgumentMatchers.any(), ArgumentMatchers.eq(expectedSqlIdMap),
            ArgumentMatchers.eq(expectedOutputPath));
        Mockito.verify(this.mockIslDataSheetCreationService, Mockito.times(1)).run();

    }

    /**
     * processWorkbook メソッドのテスト - 正常系：設定シートと一覧シートをスキップ
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                例外
     */
    @Test
    public void testProcessWorkbook_normalSkipSettingAndListSheets() throws KmgReflectionException, Exception {

        /* 期待値の定義 */
        final KmgDbTypes          expectedKmgDbTypes = KmgDbTypes.POSTGRE_SQL;
        final Map<String, String> expectedSqlIdMap   = new HashMap<>();
        final Path                expectedOutputPath = this.tempDir.resolve("output");

        /* 準備 */
        final Workbook testWorkbook = this.createTestWorkbookWithSettingAndListSheets();
        this.reflectionModel.set("outputPath", expectedOutputPath);

        // IsBasicInformationLogicのモック設定
        Mockito.doNothing().when(this.mockIsBasicInformationLogic).initialize(ArgumentMatchers.any(Workbook.class));
        Mockito.when(this.mockIsBasicInformationLogic.getKmgDbTypes()).thenReturn(expectedKmgDbTypes);
        Mockito.when(this.mockIsBasicInformationLogic.getSqlIdMap()).thenReturn(expectedSqlIdMap);

        // IslDataSheetCreationServiceのモック設定
        Mockito.doNothing().when(this.mockIslDataSheetCreationService).initialize(ArgumentMatchers.any(),
            ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.doNothing().when(this.mockIslDataSheetCreationService).run();

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processWorkbook", testWorkbook);

        /* 検証の実施 */
        Mockito.verify(this.mockIsBasicInformationLogic, Mockito.times(1))
            .initialize(ArgumentMatchers.eq(testWorkbook));
        Mockito.verify(this.mockIsBasicInformationLogic, Mockito.times(1)).getKmgDbTypes();
        Mockito.verify(this.mockIsBasicInformationLogic, Mockito.times(1)).getSqlIdMap();
        // 設定シートと一覧シートはスキップされるため、データシート作成サービスは呼ばれない
        Mockito.verify(this.mockIslDataSheetCreationService, Mockito.never()).initialize(ArgumentMatchers.any(),
            ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verify(this.mockIslDataSheetCreationService, Mockito.never()).run();

    }

    /**
     * テスト用のExcelファイルを作成する<br>
     *
     * @param filePath
     *                 ファイルパス
     */
    private void createTestExcelFile(final Path filePath) {

        try (final Workbook workbook = new XSSFWorkbook()) {

            // データシートを作成
            final Sheet                            dataSheet = workbook.createSheet("テストシート");
            final org.apache.poi.ss.usermodel.Row  row0      = dataSheet.createRow(0);
            final org.apache.poi.ss.usermodel.Cell cell0_0   = row0.createCell(0);
            cell0_0.setCellValue("test_table");

            // ファイルに保存
            try (final java.io.FileOutputStream fos = new java.io.FileOutputStream(filePath.toFile())) {

                workbook.write(fos);

            }

        } catch (final Exception e) {

            throw new RuntimeException("テスト用Excelファイルの作成に失敗しました", e);

        }

    }

    /**
     * テスト用のワークブックを作成する<br>
     *
     * @return テスト用ワークブック
     */
    private Workbook createTestWorkbook() {

        final Workbook result;

        try (final Workbook workbook = new XSSFWorkbook()) {

            // データシートを作成
            final Sheet                            dataSheet = workbook.createSheet("テストシート");
            final org.apache.poi.ss.usermodel.Row  row0      = dataSheet.createRow(0);
            final org.apache.poi.ss.usermodel.Cell cell0_0   = row0.createCell(0);
            cell0_0.setCellValue("test_table");

            result = workbook;

        } catch (final Exception e) {

            throw new RuntimeException("テスト用ワークブックの作成に失敗しました", e);

        }

        return result;

    }

    /**
     * 設定シートと一覧シートを含むテスト用ワークブックを作成する<br>
     *
     * @return テスト用ワークブック
     */
    private Workbook createTestWorkbookWithSettingAndListSheets() {

        final Workbook result;

        try (final Workbook workbook = new XSSFWorkbook()) {

            // 設定シートを作成
            final Sheet                            settingSheet = workbook.createSheet("設定情報");
            final org.apache.poi.ss.usermodel.Row  settingRow   = settingSheet.createRow(0);
            final org.apache.poi.ss.usermodel.Cell settingCell  = settingRow.createCell(1);
            settingCell.setCellValue("PostgreSQL");

            // 一覧シートを作成
            final Sheet                            listSheet = workbook.createSheet("一覧");
            final org.apache.poi.ss.usermodel.Row  listRow   = listSheet.createRow(1);
            final org.apache.poi.ss.usermodel.Cell tableCell = listRow.createCell(2);
            tableCell.setCellValue("test_table");
            final org.apache.poi.ss.usermodel.Cell sqlIdCell = listRow.createCell(3);
            sqlIdCell.setCellValue("SQL001");

            result = workbook;

        } catch (final Exception e) {

            throw new RuntimeException("テスト用ワークブックの作成に失敗しました", e);

        }

        return result;

    }

}
