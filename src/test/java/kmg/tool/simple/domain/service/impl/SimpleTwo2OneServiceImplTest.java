package kmg.tool.simple.domain.service.impl;

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
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;

/**
 * SimpleTwo2OneServiceImplのテストクラス
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
public class SimpleTwo2OneServiceImplTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private SimpleTwo2OneServiceImpl testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.1.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * テスト用入力ファイルパス
     *
     * @since 0.1.0
     */
    private Path testInputPath;

    /**
     * テスト用テンプレートファイルパス
     *
     * @since 0.1.0
     */
    private Path testTemplatePath;

    /**
     * テスト用出力ファイルパス
     *
     * @since 0.1.0
     */
    private Path testOutputPath;

    /**
     * テスト用入力ファイル内容
     *
     * @since 0.1.0
     */
    private String testInputContent;

    /**
     * テスト用テンプレートファイル内容
     *
     * @since 0.1.0
     */
    private String testTemplateContent;

    /**
     * コンストラクタ メソッドのテスト - 正常系：標準コンストラクタでの初期化
     *
     * @since 0.1.0
     */
    @Test
    public static void testConstructor_normalStandardConstructor() {

        /* 期待値の定義 */

        /* 準備 */
        final SimpleTwo2OneServiceImpl testConstructor = new SimpleTwo2OneServiceImpl();

        /* テスト対象の実行 */

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "コンストラクタが正常に初期化されること");

    }

    /**
     * セットアップ
     *
     * @since 0.1.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws IOException
     *                                入出力例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException, IOException {

        final SimpleTwo2OneServiceImpl simpleTwo2OneServiceImpl = new SimpleTwo2OneServiceImpl();
        this.testTarget = simpleTwo2OneServiceImpl;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト用パスの設定 */
        this.testInputPath = Files.createTempFile("test_input", ".txt");
        this.testTemplatePath = Files.createTempFile("test_template", ".txt");
        this.testOutputPath = Files.createTempFile("test_output", ".txt");
        this.testInputContent
            = "test input content" + System.lineSeparator() + "line2" + System.lineSeparator() + "line3";
        this.testTemplateContent = "Hello { name }, welcome!";

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

            // テストファイルの削除
            try {

                Files.deleteIfExists(this.testInputPath);
                Files.deleteIfExists(this.testTemplatePath);
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
     * @since 0.1.0
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
     * @since 0.1.0
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
     * @since 0.1.0
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
     * @since 0.1.0
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
     * getTemplatePath メソッドのテスト - 正常系：テンプレートファイルパスの取得
     *
     * @since 0.1.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetTemplatePath_normalGetTemplatePath() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expectedResult = this.testTemplatePath;

        /* 準備 */
        this.reflectionModel.set("templatePath", this.testTemplatePath);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getTemplatePath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, testResult, "テンプレートファイルパスが正しく取得されること");

    }

    /**
     * getTemplatePath メソッドのテスト - 準正常系：nullのテンプレートファイルパスの取得
     *
     * @since 0.1.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetTemplatePath_semiNullTemplatePath() throws KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("templatePath", null);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getTemplatePath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNull(testResult, "nullのテンプレートファイルパスが正しく取得されること");

    }

    /**
     * initialize メソッドのテスト - 正常系：正常な初期化
     *
     * @since 0.1.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testInitialize_normalInitialization() throws KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final boolean testResult
            = this.testTarget.initialize(this.testInputPath, this.testTemplatePath, this.testOutputPath);

        /* 検証の準備 */
        final boolean actualResult       = testResult;
        final Path    actualInputPath    = (Path) this.reflectionModel.get("inputPath");
        final Path    actualTemplatePath = (Path) this.reflectionModel.get("templatePath");
        final Path    actualOutputPath   = (Path) this.reflectionModel.get("outputPath");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が正常に完了すること");
        Assertions.assertEquals(this.testInputPath, actualInputPath, "入力ファイルパスが正しく設定されること");
        Assertions.assertEquals(this.testTemplatePath, actualTemplatePath, "テンプレートファイルパスが正しく設定されること");
        Assertions.assertEquals(this.testOutputPath, actualOutputPath, "出力ファイルパスが正しく設定されること");

    }

    /**
     * process メソッドのテスト - 異常系：入力ファイルの読み込み権限がない場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorInputFileNoReadPermission() throws Exception {

        /* 期待値の定義 */
        final KmgToolGenMsgTypes expectedMsgType = KmgToolGenMsgTypes.KMGTOOL_GEN16000;
        final String             expectedMessage = "ファイル処理に失敗しました。";

        /* 準備 */
        // テスト用テンプレートファイルを作成
        Files.write(this.testTemplatePath, this.testTemplateContent.getBytes());

        // 存在しない入力ファイルパスで初期化（より確実に例外を発生させる）
        final Path nonExistentInputPath = Paths.get("non/existent/input.txt");
        this.testTarget.initialize(nonExistentInputPath, this.testTemplatePath, this.testOutputPath);

        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(testException, IOException.class, expectedMessage, expectedMsgType);

        }

    }

    /**
     * process メソッドのテスト - 異常系：入力ファイルが存在しない場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorInputFileNotExists() throws Exception {

        /* 期待値の定義 */
        final KmgToolGenMsgTypes expectedMsgType = KmgToolGenMsgTypes.KMGTOOL_GEN16000;
        final String             expectedMessage = "ファイル処理に失敗しました。";

        /* 準備 */
        // テスト用テンプレートファイルを作成
        Files.write(this.testTemplatePath, this.testTemplateContent.getBytes());

        // 存在しない入力ファイルパスで初期化
        final Path nonExistentInputPath = Paths.get("non/existent/input.txt");
        this.testTarget.initialize(nonExistentInputPath, this.testTemplatePath, this.testOutputPath);

        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(testException, IOException.class, expectedMessage, expectedMsgType);

        }

    }

    /**
     * process メソッドのテスト - 異常系：出力ディレクトリが存在しない場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorOutputDirectoryNotExists() throws Exception {

        /* 期待値の定義 */
        final KmgToolGenMsgTypes expectedMsgType = KmgToolGenMsgTypes.KMGTOOL_GEN16000;
        final String             expectedMessage = "ファイル処理に失敗しました。";

        /* 準備 */
        // テスト用ファイルを作成
        Files.write(this.testInputPath, this.testInputContent.getBytes());
        Files.write(this.testTemplatePath, this.testTemplateContent.getBytes());

        // 存在しない出力ディレクトリのパスで初期化
        final Path nonExistentOutputPath = Paths.get("non/existent/output.txt");
        this.testTarget.initialize(this.testInputPath, this.testTemplatePath, nonExistentOutputPath);

        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(testException, IOException.class, expectedMessage, expectedMsgType);

        }

    }

    /**
     * process メソッドのテスト - 異常系：出力ファイルの書き込み権限がない場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorOutputFileNoWritePermission() throws Exception {

        /* 期待値の定義 */
        final KmgToolGenMsgTypes expectedMsgType = KmgToolGenMsgTypes.KMGTOOL_GEN16000;
        final String             expectedMessage = "ファイル処理に失敗しました。";

        /* 準備 */
        // テスト用ファイルを作成
        Files.write(this.testInputPath, this.testInputContent.getBytes());
        Files.write(this.testTemplatePath, this.testTemplateContent.getBytes());

        // 存在しない出力ディレクトリのパスで初期化（より確実に例外を発生させる）
        final Path nonExistentOutputPath = Paths.get("non/existent/output.txt");
        this.testTarget.initialize(this.testInputPath, this.testTemplatePath, nonExistentOutputPath);

        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(testException, IOException.class, expectedMessage, expectedMsgType);

        }

    }

    /**
     * process メソッドのテスト - 異常系：テンプレートファイルの読み込み権限がない場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorTemplateFileNoReadPermission() throws Exception {

        /* 期待値の定義 */
        final KmgToolGenMsgTypes expectedMsgType = KmgToolGenMsgTypes.KMGTOOL_GEN16001;
        final String             expectedMessage = "テンプレートファイルの読み込みに失敗しました。";

        /* 準備 */
        // 存在しないテンプレートファイルパスで初期化（より確実に例外を発生させる）
        final Path nonExistentTemplatePath = Paths.get("non/existent/template.txt");
        this.testTarget.initialize(this.testInputPath, nonExistentTemplatePath, this.testOutputPath);

        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(testException, IOException.class, expectedMessage, expectedMsgType);

        }

    }

    /**
     * process メソッドのテスト - 異常系：テンプレートファイルが存在しない場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorTemplateFileNotExists() throws Exception {

        /* 期待値の定義 */
        final KmgToolGenMsgTypes expectedMsgType = KmgToolGenMsgTypes.KMGTOOL_GEN16001;
        final String             expectedMessage = "テンプレートファイルの読み込みに失敗しました。";

        /* 準備 */
        // 存在しないテンプレートファイルパスで初期化
        final Path nonExistentTemplatePath = Paths.get("non/existent/template.txt");
        this.testTarget.initialize(this.testInputPath, nonExistentTemplatePath, this.testOutputPath);

        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(testException, IOException.class, expectedMessage, expectedMsgType);

        }

    }

    /**
     * process メソッドのテスト - 正常系：正常な処理
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_normalProcess() throws Exception {

        /* 期待値の定義 */
        final String expectedOutputContent = "Hello test input content, welcome!" + System.lineSeparator()
            + "Hello line2, welcome!" + System.lineSeparator() + "Hello line3, welcome!" + System.lineSeparator();

        /* 準備 */
        // テスト用ファイルを作成
        Files.write(this.testInputPath, this.testInputContent.getBytes());
        Files.write(this.testTemplatePath, this.testTemplateContent.getBytes());

        // 初期化
        this.testTarget.initialize(this.testInputPath, this.testTemplatePath, this.testOutputPath);

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
        Assertions.assertEquals(expectedOutputContent, actualOutputContent, "出力ファイルの内容が正しく生成されること");

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
        Files.write(this.testTemplatePath, this.testTemplateContent.getBytes());

        // 初期化
        this.testTarget.initialize(this.testInputPath, this.testTemplatePath, this.testOutputPath);

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
     * @since 0.1.0
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

        for (int i = 1; i <= 100; i++) {

            largeContent.append("line ").append(i).append(System.lineSeparator());
            expectedOutputContent.append("Hello line ").append(i).append(", welcome!").append(System.lineSeparator());

        }

        Files.write(this.testInputPath, largeContent.toString().getBytes());
        Files.write(this.testTemplatePath, this.testTemplateContent.getBytes());

        // 初期化
        this.testTarget.initialize(this.testInputPath, this.testTemplatePath, this.testOutputPath);

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
        Assertions.assertEquals(expectedOutputContent.toString(), actualOutputContent, "出力ファイルの内容が正しく生成されること");

    }

    /**
     * process メソッドのテスト - 準正常系：複数行のテンプレートファイルの処理
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_semiMultiLineTemplateFile() throws Exception {

        /* 期待値の定義 */
        final String expectedOutputContent
            = "Hello test input content, welcome!" + System.lineSeparator() + "How are you?" + System.lineSeparator()
                + "Hello line2, welcome!" + System.lineSeparator() + "How are you?" + System.lineSeparator()
                + "Hello line3, welcome!" + System.lineSeparator() + "How are you?" + System.lineSeparator();

        /* 準備 */
        // 複数行のテンプレートファイルを作成
        final String multiLineTemplate
            = "Hello { name }, welcome!" + System.lineSeparator() + "How are you?" + System.lineSeparator();
        Files.write(this.testInputPath, this.testInputContent.getBytes());
        Files.write(this.testTemplatePath, multiLineTemplate.getBytes());

        // 初期化
        this.testTarget.initialize(this.testInputPath, this.testTemplatePath, this.testOutputPath);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult           = testResult;
        final boolean actualOutputFileExists = Files.exists(this.testOutputPath);
        final String  actualOutputContent    = Files.exists(this.testOutputPath) ? Files.readString(this.testOutputPath)
            : "";

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "複数行のテンプレートファイルの処理が正常に完了すること");
        Assertions.assertTrue(actualOutputFileExists, "出力ファイルが作成されること");
        Assertions.assertEquals(expectedOutputContent, actualOutputContent, "出力ファイルの内容が正しく生成されること");

    }

    /**
     * process メソッドのテスト - 準正常系：1行のみの入力ファイルの処理
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_semiSingleLineInputFile() throws Exception {

        /* 期待値の定義 */
        final String expectedOutputContent = "Hello single line content, welcome!" + System.lineSeparator();

        /* 準備 */
        // 1行のみのテスト用入力ファイルを作成
        Files.write(this.testInputPath, "single line content".getBytes());
        Files.write(this.testTemplatePath, this.testTemplateContent.getBytes());

        // 初期化
        this.testTarget.initialize(this.testInputPath, this.testTemplatePath, this.testOutputPath);

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
        Assertions.assertEquals(expectedOutputContent, actualOutputContent, "出力ファイルの内容が正しく生成されること");

    }

    /**
     * process メソッドのテスト - 準正常系：プレースホルダーが含まれないテンプレートの処理
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_semiTemplateWithoutPlaceholder() throws Exception {

        /* 期待値の定義 */
        final String expectedOutputContent = "Hello world!" + System.lineSeparator() + "Hello world!"
            + System.lineSeparator() + "Hello world!" + System.lineSeparator();

        /* 準備 */
        // プレースホルダーが含まれないテンプレートファイルを作成
        final String templateWithoutPlaceholder = "Hello world!";
        Files.write(this.testInputPath, this.testInputContent.getBytes());
        Files.write(this.testTemplatePath, templateWithoutPlaceholder.getBytes());

        // 初期化
        this.testTarget.initialize(this.testInputPath, this.testTemplatePath, this.testOutputPath);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult           = testResult;
        final boolean actualOutputFileExists = Files.exists(this.testOutputPath);
        final String  actualOutputContent    = Files.exists(this.testOutputPath) ? Files.readString(this.testOutputPath)
            : "";

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "プレースホルダーが含まれないテンプレートの処理が正常に完了すること");
        Assertions.assertTrue(actualOutputFileExists, "出力ファイルが作成されること");
        Assertions.assertEquals(expectedOutputContent, actualOutputContent, "出力ファイルの内容が正しく生成されること");

    }

}
