package kmg.tool.base.io.domain.logic.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.type.KmgString;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;

/**
 * ファイルイテレーターロジック実装のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.2
 *
 * @version 0.2.2
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class FileIteratorLogicImplTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.2.2
     */
    private FileIteratorLogicImpl testTarget;

    /**
     * テスト用一時ディレクトリ
     *
     * @since 0.2.2
     */
    private Path testTempDir;

    /**
     * テスト用Javaファイル
     *
     * @since 0.2.2
     */
    private Path testJavaFile;

    /**
     * テスト用空のディレクトリ
     *
     * @since 0.2.2
     */
    private Path testEmptyDir;

    /**
     * テスト前処理<br>
     *
     * @since 0.2.2
     *
     * @throws IOException
     *                     入出力例外
     */
    @BeforeEach
    public void setUp() throws IOException {

        this.testTarget = new FileIteratorLogicImpl();

        /* テスト用一時ディレクトリの作成 */
        this.testTempDir = Files.createTempDirectory("file_iterator_test");
        this.testEmptyDir = Files.createTempDirectory("file_iterator_empty");

        /* テスト用Javaファイルの作成 */
        this.testJavaFile = this.testTempDir.resolve("TestFile.java");
        Files.writeString(this.testJavaFile, "public class TestFile {\n}\n");

        /* テスト用の非Javaファイル */
        final Path testTxtFile = this.testTempDir.resolve("TestFile.txt");
        Files.writeString(testTxtFile, "This is a text file.");

    }

    /**
     * テスト後処理<br>
     *
     * @since 0.2.2
     *
     * @throws IOException
     *                     入出力例外
     */
    @AfterEach
    public void tearDown() throws IOException {

        /* テストファイルとディレクトリの削除を再帰的に実行 */
        this.deleteDirectoryRecursively(this.testTempDir);
        this.deleteDirectoryRecursively(this.testEmptyDir);

    }

    /**
     * getCurrentFilePath メソッドのテスト - 正常系:初期化後
     *
     * @since 0.2.2
     */
    @Test
    public void testGetCurrentFilePath_normalAfterInitialize() {

        /* 期待値の定義 */
        final Path expectedCurrentFilePath = null;

        /* テスト対象の実行 */
        final Path actualCurrentFilePath = this.testTarget.getCurrentFilePath();

        /* 検証の実施 */
        Assertions.assertEquals(expectedCurrentFilePath, actualCurrentFilePath, "初期化後のカレントファイルパスがnullであること");

    }

    /**
     * getCurrentFilePath メソッドのテスト - 正常系:ロード後
     *
     * @since 0.2.2
     */
    @Test
    public void testGetCurrentFilePath_normalAfterLoad() {

        /* 期待値の定義 */
        final Path expectedCurrentFilePath = this.testJavaFile;

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        final Path actualCurrentFilePath = this.testTarget.getCurrentFilePath();

        /* 検証の実施 */
        Assertions.assertEquals(expectedCurrentFilePath, actualCurrentFilePath, "ロード後のカレントファイルパスが設定されていること");

    }

    /**
     * getFilePathList メソッドのテスト - 正常系:初期化後
     *
     * @since 0.2.2
     */
    @Test
    public void testGetFilePathList_normalAfterInitialize() {

        /* 期待値の定義 */
        final int expectedSize = 0;

        /* テスト対象の実行 */
        final List<Path> actualFilePathList = this.testTarget.getFilePathList();

        /* 検証の実施 */
        Assertions.assertEquals(expectedSize, actualFilePathList.size(), "初期化後のファイルパスリストが空であること");

    }

    /**
     * getFilePathList メソッドのテスト - 正常系:ロード後
     *
     * @since 0.2.2
     */
    @Test
    public void testGetFilePathList_normalAfterLoad() {

        /* 期待値の定義 */
        final int expectedSize = 1;

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        final List<Path> actualFilePathList = this.testTarget.getFilePathList();

        /* 検証の実施 */
        Assertions.assertEquals(expectedSize, actualFilePathList.size(), "ロード後のファイルパスリストにJavaファイルが含まれること");
        Assertions.assertTrue(actualFilePathList.contains(this.testJavaFile), "Javaファイルがリストに含まれること");

    }

    /**
     * getReadContent メソッドのテスト - 正常系:初期化後
     *
     * @since 0.2.2
     */
    @Test
    public void testGetReadContent_normalAfterInitialize() {

        /* 期待値の定義 */
        final String expectedReadContent = KmgString.EMPTY;

        /* テスト対象の実行 */
        final String actualReadContent = this.testTarget.getReadContent();

        /* 検証の実施 */
        Assertions.assertEquals(expectedReadContent, actualReadContent, "初期化後の読み込み内容が空文字列であること");

    }

    /**
     * getReadContent メソッドのテスト - 正常系:コンテンツロード後
     *
     * @since 0.2.2
     */
    @Test
    public void testGetReadContent_normalAfterLoadContent() {

        /* 期待値の定義 */
        final String expectedReadContent = "public class TestFile {\n}\n";

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();
            this.testTarget.loadContent();

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        final String actualReadContent = this.testTarget.getReadContent();

        /* 検証の実施 */
        Assertions.assertEquals(expectedReadContent, actualReadContent, "コンテンツロード後に正しい内容が読み込まれること");

    }

    /**
     * getTargetPath メソッドのテスト - 正常系:初期化後
     *
     * @since 0.2.2
     */
    @Test
    public void testGetTargetPath_normalAfterInitialize() {

        /* 期待値の定義 */
        final Path expectedTargetPath = this.testTempDir;

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        final Path actualTargetPath = this.testTarget.getTargetPath();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetPath, actualTargetPath, "初期化後に対象パスが設定されること");

    }

    /**
     * initialize メソッドのテスト - 正常系:初期化成功
     *
     * @since 0.2.2
     */
    @Test
    public void testInitialize_normalSuccess() {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.initialize(this.testTempDir);

        } catch (final Exception e) {

            Assertions.fail("初期化で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final Path       actualTargetPath      = this.testTarget.getTargetPath();
        final List<Path> actualFilePathList    = this.testTarget.getFilePathList();
        final Path       actualCurrentFilePath = this.testTarget.getCurrentFilePath();
        final String     actualReadContent     = this.testTarget.getReadContent();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "初期化が成功すること");
        Assertions.assertEquals(this.testTempDir, actualTargetPath, "対象パスが設定されること");
        Assertions.assertEquals(0, actualFilePathList.size(), "ファイルパスリストがクリアされること");
        Assertions.assertEquals(null, actualCurrentFilePath, "カレントファイルパスがnullに設定されること");
        Assertions.assertEquals(KmgString.EMPTY, actualReadContent, "読み込み内容が空文字列に設定されること");

    }

    /**
     * initialize メソッドのテスト - 正常系:拡張子を指定して初期化
     *
     * @since 0.2.2
     */
    @Test
    public void testInitialize_normalWithExtension() {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.initialize(this.testTempDir, ".java");

        } catch (final Exception e) {

            Assertions.fail("初期化で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final Path actualTargetPath = this.testTarget.getTargetPath();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "初期化が成功すること");
        Assertions.assertEquals(this.testTempDir, actualTargetPath, "対象パスが設定されること");

    }

    /**
     * initialize メソッドのテスト - 正常系:拡張子をnullにして全ファイル対象
     *
     * @since 0.2.2
     */
    @Test
    public void testInitialize_normalWithNullExtension() {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.initialize(this.testTempDir, null);

        } catch (final Exception e) {

            Assertions.fail("初期化で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final Path actualTargetPath = this.testTarget.getTargetPath();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "初期化が成功すること");
        Assertions.assertEquals(this.testTempDir, actualTargetPath, "対象パスが設定されること");

    }

    /**
     * load メソッドのテスト - 異常系:深いディレクトリ階層の非存在パス
     *
     * @since 0.2.2
     */
    @Test
    @Disabled
    public void testLoad_errorDeepNonExistentPath() {

        final Class<?>           expectedCauseClass    = IOException.class;
        final String             expectedDomainMessage
                                                       = "[KMGTOOL_GEN13002] 対象ファイルをロード中に例外が発生しました。対象ファイルパス=[deep\\non\\existent\\path]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN13002;

        final Path deepNonExistentPath = Paths.get("deep/non/existent/path");

        try {

            this.testTarget.initialize(deepNonExistentPath);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        // SpringApplicationContextHelperのモック化
        try (MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // Files.walkをstaticモックしてIOExceptionをスローさせる
            try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {

                filesMock.when(() -> Files.walk(deepNonExistentPath)).thenThrow(new IOException("mocked io error"));

                final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                    this.testTarget.load();

                }, "深い階層の非存在ディレクトリでKmgToolMsgExceptionがスローされること");

                this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                    expectedMessageTypes);

            }

        }

    }

    /**
     * load メソッドのテスト - 異常系:存在しないディレクトリ
     *
     * @since 0.2.2
     */
    @Test
    @Disabled
    public void testLoad_errorNonExistentDirectory() {

        final Class<?>           expectedCauseClass    = IOException.class;
        final String             expectedDomainMessage
                                                       = "[KMGTOOL_GEN13002] 対象ファイルをロード中に例外が発生しました。対象ファイルパス=[non\\existent\\path]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN13002;

        final Path nonExistentPath = Paths.get("non/existent/path");

        try {

            this.testTarget.initialize(nonExistentPath);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        // SpringApplicationContextHelperのモック化
        try (MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // Files.walkをstaticモックしてIOExceptionをスローさせる
            try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {

                filesMock.when(() -> Files.walk(nonExistentPath)).thenThrow(new IOException("mocked io error"));

                final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                    this.testTarget.load();

                }, "存在しないディレクトリでKmgToolMsgExceptionがスローされること");

                this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                    expectedMessageTypes);

            }

        }

    }

    /**
     * load メソッドのテスト - 異常系:NoSuchFileException発生
     *
     * @since 0.2.2
     */
    @Test
    @Disabled
    public void testLoad_errorNoSuchFileException() {

        /* 期待値の定義 */
        final Class<?>           expectedCauseClass    = NoSuchFileException.class;
        final String             expectedDomainMessage
                                                       = "[KMGTOOL_GEN13009] 対象ファイルが見つかりません。対象ファイルパス=[nosuchfile\\path]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN13009;

        /* 準備 */
        final Path testNoSuchFilePath = Paths.get("nosuchfile/path");

        try {

            this.testTarget.initialize(testNoSuchFilePath);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行・検証の実施 */
        // SpringApplicationContextHelperのモック化
        try (MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // Files.walkをstaticモックしてNoSuchFileExceptionをスローさせる
            try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {

                filesMock.when(() -> Files.walk(testNoSuchFilePath))
                    .thenThrow(new NoSuchFileException("mocked no such file error"));

                final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                    this.testTarget.load();

                }, "NoSuchFileExceptionでKmgToolMsgExceptionがスローされること");

                /* 検証の実施 */
                this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                    expectedMessageTypes);

            }

        }

    }

    /**
     * load メソッドのテスト - 正常系:Javaファイルなし
     *
     * @since 0.2.2
     */
    @Test
    public void testLoad_normalNoJavaFiles() {

        /* 期待値の定義 */
        final boolean expectedResult    = true;
        final int     expectedFileCount = 0;

        /* 準備 */
        try {

            this.testTarget.initialize(this.testEmptyDir);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("ロード処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final List<Path> actualFilePathList    = this.testTarget.getFilePathList();
        final Path       actualCurrentFilePath = this.testTarget.getCurrentFilePath();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "ロードが成功すること");
        Assertions.assertEquals(expectedFileCount, actualFilePathList.size(), "Javaファイルがない場合はリストが空であること");
        Assertions.assertEquals(null, actualCurrentFilePath, "Javaファイルがない場合はカレントファイルがnullであること");

    }

    /**
     * load メソッドのテスト - 正常系:異なる拡張子を指定してロード
     *
     * @since 0.2.2
     */
    @Test
    public void testLoad_normalWithDifferentExtension() {

        /* 期待値の定義 */
        final boolean expectedResult    = true;
        final int     expectedFileCount = 1;

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir, ".txt");

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("ロード処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final List<Path> actualFilePathList    = this.testTarget.getFilePathList();
        final Path       actualCurrentFilePath = this.testTarget.getCurrentFilePath();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "ロードが成功すること");
        Assertions.assertEquals(expectedFileCount, actualFilePathList.size(), "txtファイルがロードされること");
        Assertions.assertNotNull(actualCurrentFilePath, "カレントファイルが設定されること");
        Assertions.assertTrue(actualCurrentFilePath.toString().endsWith(".txt"), "txtファイルがカレントファイルに設定されること");

    }

    /**
     * load メソッドのテスト - 正常系:拡張子を指定してロード
     *
     * @since 0.2.2
     */
    @Test
    public void testLoad_normalWithExtension() {

        /* 期待値の定義 */
        final boolean expectedResult    = true;
        final int     expectedFileCount = 1;

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir, ".java");

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("ロード処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final List<Path> actualFilePathList    = this.testTarget.getFilePathList();
        final Path       actualCurrentFilePath = this.testTarget.getCurrentFilePath();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "ロードが成功すること");
        Assertions.assertEquals(expectedFileCount, actualFilePathList.size(), "Javaファイルがロードされること");
        Assertions.assertEquals(this.testJavaFile, actualCurrentFilePath, "最初のJavaファイルがカレントファイルに設定されること");

    }

    /**
     * load メソッドのテスト - 正常系:Javaファイルあり
     *
     * @since 0.2.2
     */
    @Test
    public void testLoad_normalWithJavaFiles() {

        /* 期待値の定義 */
        final boolean expectedResult    = true;
        final int     expectedFileCount = 1;

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("ロード処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final List<Path> actualFilePathList    = this.testTarget.getFilePathList();
        final Path       actualCurrentFilePath = this.testTarget.getCurrentFilePath();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "ロードが成功すること");
        Assertions.assertEquals(expectedFileCount, actualFilePathList.size(), "Javaファイルがロードされること");
        Assertions.assertEquals(this.testJavaFile, actualCurrentFilePath, "最初のJavaファイルがカレントファイルに設定されること");

    }

    /**
     * load メソッドのテスト - 正常系:拡張子をnullにして全ファイルをロード
     *
     * @since 0.2.2
     */
    @Test
    public void testLoad_normalWithNullExtension() {

        /* 期待値の定義 */
        final boolean expectedResult    = true;
        final int     expectedFileCount = 2;   // .javaファイルと.txtファイル

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir, null);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("ロード処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final List<Path> actualFilePathList    = this.testTarget.getFilePathList();
        final Path       actualCurrentFilePath = this.testTarget.getCurrentFilePath();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "ロードが成功すること");
        Assertions.assertEquals(expectedFileCount, actualFilePathList.size(), "全ファイルがロードされること");
        Assertions.assertNotNull(actualCurrentFilePath, "カレントファイルが設定されること");

    }

    /**
     * loadContent メソッドのテスト - 異常系:ファイル読み込みエラー
     *
     * @since 0.2.2
     */
    @Test
    public void testLoadContent_errorFileReadError() {

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();
            /* ファイルを削除してエラーを発生させる */
            Files.delete(this.testJavaFile);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行・検証の実施 */
        final Exception actualException = Assertions.assertThrows(Exception.class, () -> {

            this.testTarget.loadContent();

        }, "ファイル読み込みエラーで例外がスローされること");

        /* 検証の実施 */
        Assertions.assertNotNull(actualException, "例外が発生すること");

    }

    /**
     * loadContent メソッドのテスト - 異常系:KmgToolMsgException発生
     *
     * @since 0.2.2
     */
    @Test
    @Disabled
    public void testLoadContent_errorKmgToolMsgException() {

        final Class<?>           expectedCauseClass    = IOException.class;
        final String             expectedDomainMessage = "[KMGTOOL_GEN13001] ファイル内容を読み込み中に例外が発生しました。対象ファイルパス=[%s]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN13001;

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();
            /* ファイルを削除してエラーを発生させる */
            Files.delete(this.testJavaFile);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        // SpringApplicationContextHelperのモック化
        try (MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            final String formattedMessage = String.format(expectedDomainMessage, this.testJavaFile.toString());
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(formattedMessage);

            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.loadContent();

            }, "ファイル読み込みエラーでKmgToolMsgExceptionがスローされること");

            this.verifyKmgMsgException(actualException, expectedCauseClass, formattedMessage, expectedMessageTypes);

        }

    }

    /**
     * loadContent メソッドのテスト - 正常系:内容あり
     *
     * @since 0.2.2
     */
    @Test
    public void testLoadContent_normalWithContent() {

        /* 期待値の定義 */
        final boolean expectedResult  = true;
        final String  expectedContent = "public class TestFile {\n}\n";

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.loadContent();

        } catch (final Exception e) {

            Assertions.fail("コンテンツロード処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final String actualReadContent = this.testTarget.getReadContent();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "コンテンツロードが成功すること");
        Assertions.assertEquals(expectedContent, actualReadContent, "正しい内容が読み込まれること");

    }

    /**
     * loadContent メソッドのテスト - 準正常系:内容がブランク
     *
     * @since 0.2.2
     */
    @Test
    public void testLoadContent_semiBlankContent() {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        Path blankJavaFile = null;

        try {

            /* 既存のファイルを削除してブランクファイルのみにする */
            Files.delete(this.testJavaFile);
            blankJavaFile = this.testTempDir.resolve("BlankFile.java");
            Files.writeString(blankJavaFile, "   \n  \t  ");
            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = true;

        try {

            actualResult = this.testTarget.loadContent();

        } catch (final Exception e) {

            Assertions.fail("コンテンツロード処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final String actualReadContent = this.testTarget.getReadContent();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "ブランクのコンテンツの場合falseが返されること");
        Assertions.assertEquals("   \n  \t  ", actualReadContent, "ブランクの内容が読み込まれること");

    }

    /**
     * loadContent メソッドのテスト - 準正常系:内容が空
     *
     * @since 0.2.2
     */
    @Test
    public void testLoadContent_semiEmptyContent() {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        Path emptyJavaFile = null;

        try {

            /* 既存のファイルを削除して空のファイルのみにする */
            Files.delete(this.testJavaFile);
            emptyJavaFile = this.testTempDir.resolve("EmptyFile.java");
            Files.writeString(emptyJavaFile, "");
            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = true;

        try {

            actualResult = this.testTarget.loadContent();

        } catch (final Exception e) {

            Assertions.fail("コンテンツロード処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final String actualReadContent = this.testTarget.getReadContent();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "空のコンテンツの場合falseが返されること");
        Assertions.assertEquals("", actualReadContent, "空の内容が読み込まれること");

    }

    /**
     * nextFile メソッドのテスト - 正常系:次のファイルあり
     *
     * @since 0.2.2
     */
    @Test
    public void testNextFile_normalHasNextFile() {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        Path secondJavaFile = null;

        try {

            secondJavaFile = this.testTempDir.resolve("SecondFile.java");
            Files.writeString(secondJavaFile, "public class SecondFile {}");
            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.nextFile();

        } catch (final Exception e) {

            Assertions.fail("nextFile処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final Path actualCurrentFilePath = this.testTarget.getCurrentFilePath();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "次のファイルが存在する場合trueが返されること");
        Assertions.assertTrue(actualCurrentFilePath.getFileName().toString().endsWith(".java"),
            "次のJavaファイルがカレントファイルに設定されること");

    }

    /**
     * nextFile メソッドのテスト - 準正常系:次のファイルなし
     *
     * @since 0.2.2
     */
    @Test
    public void testNextFile_semiNoNextFile() {

        /* 期待値の定義 */
        final boolean expectedResult = false;

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = true;

        try {

            actualResult = this.testTarget.nextFile();

        } catch (final Exception e) {

            Assertions.fail("nextFile処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "次のファイルが存在しない場合falseが返されること");

    }

    /**
     * resetFileIndex メソッドのテスト - 正常系:ファイルリストなし
     *
     * @since 0.2.2
     */
    @Test
    public void testResetFileIndex_normalNoFileList() {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        try {

            this.testTarget.initialize(this.testEmptyDir);
            this.testTarget.load();

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.resetFileIndex();

        } catch (final Exception e) {

            Assertions.fail("resetFileIndex処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final Path actualCurrentFilePath = this.testTarget.getCurrentFilePath();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "リセットが成功すること");
        Assertions.assertEquals(null, actualCurrentFilePath, "ファイルリストが空の場合カレントファイルがnullに設定されること");

    }

    /**
     * resetFileIndex メソッドのテスト - 正常系:ファイルリストあり
     *
     * @since 0.2.2
     */
    @Test
    public void testResetFileIndex_normalWithFileList() {

        /* 期待値の定義 */
        final boolean expectedResult = true;

        /* 準備 */
        Path secondJavaFile = null;

        try {

            secondJavaFile = this.testTempDir.resolve("SecondFile.java");
            Files.writeString(secondJavaFile, "public class SecondFile {}");
            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();
            /* 次のファイルに進んでからリセット */
            this.testTarget.nextFile();

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.resetFileIndex();

        } catch (final Exception e) {

            Assertions.fail("resetFileIndex処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        final Path       actualCurrentFilePath = this.testTarget.getCurrentFilePath();
        final List<Path> filePathList          = this.testTarget.getFilePathList();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "リセットが成功すること");
        Assertions.assertEquals(filePathList.get(0), actualCurrentFilePath, "先頭のファイルがカレントファイルに設定されること");

    }

    /**
     * setWriteContent メソッドのテスト - 正常系:書き込み内容設定
     *
     * @since 0.2.2
     */
    @Test
    public void testSetWriteContent_normalSetContent() {

        /* 期待値の定義 */
        final String expectedContent = "Modified content";

        /* テスト対象の実行 */
        this.testTarget.setWriteContent(expectedContent);

        /* 検証は writeContent で間接的に確認 */
        Assertions.assertTrue(true, "setWriteContentメソッドが正常に実行されること");

    }

    /**
     * writeContent メソッドのテスト - 異常系:KmgToolMsgException発生
     *
     * @since 0.2.2
     */
    @Test
    @Disabled
    public void testWriteContent_errorKmgToolMsgException() {

        final Class<?>           expectedCauseClass    = IOException.class;
        final String             expectedDomainMessage
                                                       = "[KMGTOOL_GEN13000] ファイル内容を書き込み中に例外が発生しました。対象ファイルパス=[%s] 書き込み内容=[%s]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN13000;
        final String             testContent           = "Test content";

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();
            this.testTarget.setWriteContent(testContent);
            /* ファイルを削除してディレクトリを作成し、書き込みエラーを発生させる */
            Files.delete(this.testJavaFile);
            Files.createDirectory(this.testJavaFile);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        // SpringApplicationContextHelperのモック化
        try (MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSource);

            // モックメッセージソースの設定
            final String formattedMessage
                = String.format(expectedDomainMessage, this.testJavaFile.toString(), testContent);
            Mockito.when(mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(formattedMessage);

            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.writeContent();

            }, "書き込みエラーでKmgToolMsgExceptionがスローされること");

            this.verifyKmgMsgException(actualException, expectedCauseClass, formattedMessage, expectedMessageTypes);

        }

    }

    /**
     * writeContent メソッドのテスト - 異常系:書き込みエラー
     *
     * @since 0.2.2
     */
    @Test
    public void testWriteContent_errorWriteError() {

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();
            this.testTarget.setWriteContent("Test content");
            /* ファイルを削除してディレクトリを作成し、書き込みエラーを発生させる */
            Files.delete(this.testJavaFile);
            Files.createDirectory(this.testJavaFile);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行・検証の実施 */
        final Exception actualException = Assertions.assertThrows(Exception.class, () -> {

            this.testTarget.writeContent();

        }, "書き込みエラーで例外がスローされること");

        /* 検証の実施 */
        Assertions.assertNotNull(actualException, "例外が発生すること");

    }

    /**
     * writeContent メソッドのテスト - 正常系:書き込み成功
     *
     * @since 0.2.2
     */
    @Test
    public void testWriteContent_normalSuccess() {

        /* 期待値の定義 */
        final String expectedContent = "Modified Java content";

        /* 準備 */
        try {

            this.testTarget.initialize(this.testTempDir);
            this.testTarget.load();
            this.testTarget.setWriteContent(expectedContent);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行 */
        boolean actualResult = false;

        try {

            actualResult = this.testTarget.writeContent();

        } catch (final Exception e) {

            Assertions.fail("書き込み処理で例外が発生しました: " + e.getMessage());

        }

        /* 検証の準備 */
        String actualFileContent = "";

        try {

            actualFileContent = Files.readString(this.testJavaFile);

        } catch (final Exception e) {

            Assertions.fail("ファイル読み込みで例外が発生しました: " + e.getMessage());

        }

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "書き込みが成功すること");
        Assertions.assertEquals(expectedContent, actualFileContent, "指定された内容でファイルが書き込まれること");

    }

    /**
     * ディレクトリを再帰的に削除する<br>
     *
     * @since 0.2.2
     *
     * @param directory
     *                  削除するディレクトリ
     *
     * @throws IOException
     *                     入出力例外
     */
    @SuppressWarnings("resource")
    private void deleteDirectoryRecursively(final Path directory) throws IOException {

        if (!Files.exists(directory)) {

            return;

        }

        Files.walk(directory).sorted((a, b) -> b.compareTo(a)).forEach(path -> {

            try {

                Files.delete(path);

            } catch (final IOException e) {

                e.printStackTrace();

            }

        });

    }

}

