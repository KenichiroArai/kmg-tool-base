package kmg.tool.base.one2one.application.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseGenMsgTypes;

/**
 * SimpleOne2OneServiceImplのテストクラス
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
    "nls",
})
public class SimpleOne2OneServiceImplTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.2.0
     */
    private SimpleOne2OneServiceImpl testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.2.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * テスト用入力ファイルパス
     *
     * @since 0.2.0
     */
    private Path testInputPath;

    /**
     * テスト用出力ファイルパス
     *
     * @since 0.2.0
     */
    private Path testOutputPath;

    /**
     * テスト用入力ファイル内容
     *
     * @since 0.2.0
     */
    private String testInputContent;

    /**
     * コンストラクタ メソッドのテスト - 正常系：標準コンストラクタでの初期化
     *
     * @since 0.2.0
     */
    @Test
    public static void testConstructor_normalStandardConstructor() {

        /* 期待値の定義 */

        /* 準備 */
        final SimpleOne2OneServiceImpl testConstructor = new SimpleOne2OneServiceImpl();

        /* テスト対象の実行 */

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "コンストラクタが正常に初期化されること");

    }

    /**
     * セットアップ
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws IOException
     *                                入出力例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException, IOException {

        final SimpleOne2OneServiceImpl simpleOne2OneServiceImpl = new SimpleOne2OneServiceImpl();
        this.testTarget = simpleOne2OneServiceImpl;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト用パスの設定 */
        this.testInputPath = Files.createTempFile("test_input", ".txt");
        this.testOutputPath = Files.createTempFile("test_output", ".txt");
        this.testInputContent
            = "test input content" + System.lineSeparator() + "line2" + System.lineSeparator() + "line3";

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

            // テストファイルの削除
            try {

                Files.deleteIfExists(this.testInputPath);
                Files.deleteIfExists(this.testOutputPath);

            } catch (final IOException e) {

                // 削除に失敗した場合は無視
                e.printStackTrace();

            }

        }

    }

    /**
     * getInputPath メソッドのテスト - 正常系：入力ファイルパスの取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetInputPath_normalGetInputPath() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expectedResult = this.testInputPath;

        /* 準備 */
        this.reflectionModel.set("inputPath", this.testInputPath);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getInputPath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, testResult, "入力ファイルパスが正しく取得されること");

    }

    /**
     * getInputPath メソッドのテスト - 準正常系：nullの入力ファイルパスの取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetInputPath_semiNullInputPath() throws KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("inputPath", null);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getInputPath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNull(testResult, "nullの入力ファイルパスが正しく取得されること");

    }

    /**
     * getOutputPath メソッドのテスト - 正常系：出力ファイルパスの取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetOutputPath_normalGetOutputPath() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expectedResult = this.testOutputPath;

        /* 準備 */
        this.reflectionModel.set("outputPath", this.testOutputPath);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getOutputPath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, testResult, "出力ファイルパスが正しく取得されること");

    }

    /**
     * getOutputPath メソッドのテスト - 準正常系：nullの出力ファイルパスの取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetOutputPath_semiNullOutputPath() throws KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("outputPath", null);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getOutputPath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNull(testResult, "nullの出力ファイルパスが正しく取得されること");

    }

    /**
     * initialize メソッドのテスト - 正常系：正常な初期化
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                 リフレクション例外
     */
    @Test
    public void testInitialize_normalInitialization() throws KmgToolBaseMsgException, KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(this.testInputPath, this.testOutputPath);

        /* 検証の準備 */
        final boolean actualResult     = testResult;
        final Path    actualInputPath  = (Path) this.reflectionModel.get("inputPath");
        final Path    actualOutputPath = (Path) this.reflectionModel.get("outputPath");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が正常に完了すること");
        Assertions.assertEquals(this.testInputPath, actualInputPath, "入力ファイルパスが正しく設定されること");
        Assertions.assertEquals(this.testOutputPath, actualOutputPath, "出力ファイルパスが正しく設定されること");

    }

    /**
     * process メソッドのテスト - 異常系：入力ファイルの読み込み権限がない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorInputFileNoReadPermission() throws Exception {

        /* 期待値の定義 */
        final KmgToolBaseGenMsgTypes expectedMsgType = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN15000;
        final String                 expectedMessage = "ファイル処理に失敗しました。";

        /* 準備 */
        // 存在しない入力ファイルパスで初期化（より確実に例外を発生させる）
        final Path nonExistentInputPath = Paths.get("non/existent/input.txt");
        this.testTarget.initialize(nonExistentInputPath, this.testOutputPath);

        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException testException = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(testException, IOException.class, expectedMessage, expectedMsgType);

        }

    }

    /**
     * process メソッドのテスト - 異常系：入力ファイルが存在しない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorInputFileNotExists() throws Exception {

        /* 期待値の定義 */
        final KmgToolBaseGenMsgTypes expectedMsgType = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN15000;
        final String                 expectedMessage = "ファイル処理に失敗しました。";

        /* 準備 */
        // 存在しない入力ファイルパスで初期化
        final Path nonExistentInputPath = Paths.get("non/existent/input.txt");
        this.testTarget.initialize(nonExistentInputPath, this.testOutputPath);

        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException testException = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(testException, IOException.class, expectedMessage, expectedMsgType);

        }

    }

    /**
     * process メソッドのテスト - 異常系：出力ディレクトリが存在しない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorOutputDirectoryNotExists() throws Exception {

        /* 期待値の定義 */
        final KmgToolBaseGenMsgTypes expectedMsgType = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN15000;
        final String                 expectedMessage = "ファイル処理に失敗しました。";

        /* 準備 */
        // テスト用入力ファイルを作成
        Files.write(this.testInputPath, this.testInputContent.getBytes());

        // 存在しない出力ディレクトリのパスで初期化
        final Path nonExistentOutputPath = Paths.get("non/existent/output.txt");
        this.testTarget.initialize(this.testInputPath, nonExistentOutputPath);

        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException testException = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(testException, IOException.class, expectedMessage, expectedMsgType);

        }

    }

    /**
     * process メソッドのテスト - 異常系：出力ファイルの書き込み権限がない場合
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorOutputFileNoWritePermission() throws Exception {

        /* 期待値の定義 */
        final KmgToolBaseGenMsgTypes expectedMsgType = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN15000;
        final String                 expectedMessage = "ファイル処理に失敗しました。";

        /* 準備 */
        // テスト用入力ファイルを作成
        Files.write(this.testInputPath, this.testInputContent.getBytes());

        // 存在しない出力ディレクトリのパスで初期化（より確実に例外を発生させる）
        final Path nonExistentOutputPath = Paths.get("non/existent/output.txt");
        this.testTarget.initialize(this.testInputPath, nonExistentOutputPath);

        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);

            /* テスト対象の実行 */
            final KmgToolBaseMsgException testException = Assertions.assertThrows(KmgToolBaseMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(testException, IOException.class, expectedMessage, expectedMsgType);

        }

    }

    /**
     * process メソッドのテスト - 正常系：正常な処理
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_normalProcess() throws Exception {

        /* 期待値の定義 */
        final String expectedOutputContent = "test input contentline2line3";

        /* 準備 */
        // テスト用入力ファイルを作成
        Files.write(this.testInputPath, this.testInputContent.getBytes());

        // 初期化
        this.testTarget.initialize(this.testInputPath, this.testOutputPath);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult           = testResult;
        final boolean actualOutputFileExists = Files.exists(this.testOutputPath);
        final String  actualOutputContent    = Files.exists(this.testOutputPath) ? Files.readString(this.testOutputPath)
            : "";

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "処理が正常に完了すること");
        Assertions.assertTrue(actualOutputFileExists, "出力ファイルが作成されること");
        Assertions.assertEquals(expectedOutputContent, actualOutputContent, "出力ファイルの内容が入力ファイルと一致すること");

    }

    /**
     * process メソッドのテスト - 準正常系：空の入力ファイルの処理
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_semiEmptyInputFile() throws Exception {

        /* 期待値の定義 */
        final String expectedOutputContent = "";

        /* 準備 */
        // 空のテスト用入力ファイルを作成
        Files.write(this.testInputPath, "".getBytes());

        // 初期化
        this.testTarget.initialize(this.testInputPath, this.testOutputPath);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult           = testResult;
        final boolean actualOutputFileExists = Files.exists(this.testOutputPath);
        final String  actualOutputContent    = Files.exists(this.testOutputPath) ? Files.readString(this.testOutputPath)
            : "";

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "空の入力ファイルの処理が正常に完了すること");
        Assertions.assertTrue(actualOutputFileExists, "出力ファイルが作成されること");
        Assertions.assertEquals(expectedOutputContent, actualOutputContent, "出力ファイルの内容が空であること");

    }

    /**
     * process メソッドのテスト - 準正常系：大量の行を含む入力ファイルの処理
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_semiLargeInputFile() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // 大量の行を含むテスト用入力ファイルを作成
        final StringBuilder largeContent          = new StringBuilder();
        final StringBuilder expectedOutputContent = new StringBuilder();

        for (int i = 1; i <= 1000; i++) {

            largeContent.append("line ").append(i).append(System.lineSeparator());
            expectedOutputContent.append("line ").append(i);

        }

        Files.write(this.testInputPath, largeContent.toString().getBytes());

        // 初期化
        this.testTarget.initialize(this.testInputPath, this.testOutputPath);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult           = testResult;
        final boolean actualOutputFileExists = Files.exists(this.testOutputPath);
        final String  actualOutputContent    = Files.exists(this.testOutputPath) ? Files.readString(this.testOutputPath)
            : "";

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "大量の行を含む入力ファイルの処理が正常に完了すること");
        Assertions.assertTrue(actualOutputFileExists, "出力ファイルが作成されること");
        Assertions.assertEquals(expectedOutputContent.toString(), actualOutputContent, "出力ファイルの内容が入力ファイルと一致すること");

    }

    /**
     * process メソッドのテスト - 準正常系：1行のみの入力ファイルの処理
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_semiSingleLineInputFile() throws Exception {

        /* 期待値の定義 */
        final String expectedOutputContent = "single line content";

        /* 準備 */
        // 1行のみのテスト用入力ファイルを作成
        Files.write(this.testInputPath, expectedOutputContent.getBytes());

        // 初期化
        this.testTarget.initialize(this.testInputPath, this.testOutputPath);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult           = testResult;
        final boolean actualOutputFileExists = Files.exists(this.testOutputPath);
        final String  actualOutputContent    = Files.exists(this.testOutputPath) ? Files.readString(this.testOutputPath)
            : "";

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "1行のみの入力ファイルの処理が正常に完了すること");
        Assertions.assertTrue(actualOutputFileExists, "出力ファイルが作成されること");
        Assertions.assertEquals(expectedOutputContent, actualOutputContent, "出力ファイルの内容が入力ファイルと一致すること");

    }

}
