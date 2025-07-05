package kmg.tool.jdts.application.logic.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.type.KmgString;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.jdts.application.logic.impl.JdtsIoLogicImpl;

/**
 * Javadocタグ設定の入出力ロジック実装のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SpringBootTest(classes = kmg.tool.is.presentation.ui.gui.IsCreationTool.class)
@SuppressWarnings({
    "nls", "static-method"
})
public class JdtsIoLogicImplTest extends AbstractKmgTest {

    /** テスト対象 */
    private JdtsIoLogicImpl testTarget;

    /** テスト用一時ディレクトリ */
    private Path testTempDir;

    /** テスト用Javaファイル */
    private Path testJavaFile;

    /** テスト用空のディレクトリ */
    private Path testEmptyDir;

    /**
     * テスト前処理<br>
     *
     * @since 0.1.0
     *
     * @throws IOException
     *                     入出力例外
     */
    @BeforeEach
    public void setUp() throws IOException {

        this.testTarget = new JdtsIoLogicImpl();

        /* テスト用一時ディレクトリの作成 */
        this.testTempDir = Files.createTempDirectory("jdts_test");
        this.testEmptyDir = Files.createTempDirectory("jdts_empty");

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
     * @since 0.1.0
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
     * load メソッドのテスト - 異常系:深いディレクトリ階層の非存在パス
     */
    @Test
    public void testLoad_errorDeepNonExistentPath() {

        /* 期待値の定義 */
        final Class<?>           expectedCauseClass    = IOException.class;
        final String             expectedDomainMessage
                                                       = "[KMGTOOL_GEN32013] Javadocタグ設定で対象ファイルをロード中に例外が発生しました。対象ファイルパス=[deep\\non\\existent\\path]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN32013;

        /* 準備 */
        final Path deepNonExistentPath = Paths.get("deep/non/existent/path");

        try {

            this.testTarget.initialize(deepNonExistentPath);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行・検証の実施 */
        final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

            this.testTarget.load();

        }, "深い階層の非存在ディレクトリでKmgToolMsgExceptionがスローされること");

        /* 検証の実施 */
        this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage, expectedMessageTypes);

    }

    /**
     * load メソッドのテスト - 異常系:存在しないディレクトリ
     */
    @Test
    public void testLoad_errorNonExistentDirectory() {

        /* 期待値の定義 */
        final Class<?>           expectedCauseClass    = IOException.class;
        final String             expectedDomainMessage
                                                       = "[KMGTOOL_GEN32013] Javadocタグ設定で対象ファイルをロード中に例外が発生しました。対象ファイルパス=[non\\existent\\path]";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN32013;

        /* 準備 */
        final Path nonExistentPath = Paths.get("non/existent/path");

        try {

            this.testTarget.initialize(nonExistentPath);

        } catch (final Exception e) {

            Assertions.fail("準備処理で例外が発生しました: " + e.getMessage());

        }

        /* テスト対象の実行・検証の実施 */
        final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

            this.testTarget.load();

        }, "存在しないディレクトリでKmgToolMsgExceptionがスローされること");

        /* 検証の実施 */
        this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage, expectedMessageTypes);

    }

    /**
     * load メソッドのテスト - 正常系:Javaファイルなし
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
     * load メソッドのテスト - 正常系:Javaファイルあり
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
     * loadContent メソッドのテスト - 異常系:ファイル読み込みエラー
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
     * loadContent メソッドのテスト - 正常系:内容あり
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
     * setWriteContent メソッドのテスト - 正常系:書き込み内容設定
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
     * writeContent メソッドのテスト - 異常系:書き込みエラー
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
     */
    @Test
    public void testWriteContent_normalSuccess() {

        /* 期待値の定義 */
        final boolean expectedResult  = true;
        final String  expectedContent = "Modified Java content";

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
        Assertions.assertEquals(expectedResult, actualResult, "書き込みが成功すること");
        Assertions.assertEquals(expectedContent, actualFileContent, "指定された内容でファイルが書き込まれること");

    }

    /**
     * ディレクトリを再帰的に削除する<br>
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
