package kmg.tool.application.logic.jdts.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * Javadocタグ設定の入出力ロジック実装のテスト<br>
 *
 * @author KenichiroArai
 */
@SuppressWarnings({
    "nls", "static-method"
})
public class JdtsIoLogicImplTest {

    /**
     * デフォルトコンストラクタ<br>
     */
    public JdtsIoLogicImplTest() {

        // 処理なし
    }

    /**
     * getCurrentFilePath メソッドのテスト - 正常系:現在のファイルパスが正しく返されることの確認
     * <p>
     * ファイルを読み込んだ後に現在のファイルパスが正しく返されることを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws IOException
     *                             入出力例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetCurrentFilePath_normalCurrentFilePathReturned(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final String expectedFileName = "TestClass.java";

        /* 準備 */
        final Path testFile = tempDir.resolve(expectedFileName);
        Files.writeString(testFile, "public class TestClass {}");

        final JdtsIoLogicImpl testTarget = new JdtsIoLogicImpl();

        // 初期化とロード
        testTarget.initialize(tempDir);
        testTarget.load();

        /* テスト対象の実行 */
        final Path testResult = testTarget.getCurrentFilePath();

        /* 検証の準備 */
        final String actualFileName = testResult.getFileName().toString();

        /* 検証の実施 */
        Assertions.assertEquals(expectedFileName, actualFileName, "現在のファイルパスが正しく返されること");

    }

    /**
     * getFilePathList メソッドのテスト - 正常系:ファイルパスリストが正しく取得されることの確認
     * <p>
     * 指定ディレクトリ内のJavaファイルのパスリストが正しく取得されることを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws IOException
     *                             入出力例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetFilePathList_normalFilePathListReturned(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedFileCount = 2;

        /* 準備 */
        final Path testFile1 = tempDir.resolve("TestClass1.java");
        final Path testFile2 = tempDir.resolve("TestClass2.java");
        Files.writeString(testFile1, "public class TestClass1 {}");
        Files.writeString(testFile2, "public class TestClass2 {}");

        final JdtsIoLogicImpl testTarget = new JdtsIoLogicImpl();

        // 初期化とロード
        testTarget.initialize(tempDir);
        testTarget.load();

        /* テスト対象の実行 */
        final List<Path> testResult = testTarget.getFilePathList();

        /* 検証の準備 */
        final int actualFileCount = testResult.size();

        /* 検証の実施 */
        Assertions.assertEquals(expectedFileCount, actualFileCount, "ファイルパスリストが正しく取得されること");

    }

    /**
     * getReadContent メソッドのテスト - 正常系:読み込んだ内容が正しく返されることの確認
     * <p>
     * ファイルの内容が正しく読み込まれることを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws IOException
     *                             入出力例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetReadContent_normalContentReturned(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final String expectedContent = "public class TestClass {}";

        /* 準備 */
        final Path testFile = tempDir.resolve("TestClass.java");
        Files.writeString(testFile, expectedContent);

        final JdtsIoLogicImpl testTarget = new JdtsIoLogicImpl();

        // 初期化とロード
        testTarget.initialize(tempDir);
        testTarget.load();
        testTarget.loadContent();

        /* テスト対象の実行 */
        final String testResult = testTarget.getReadContent();

        /* 検証の準備 */
        final String actualContent = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedContent, actualContent, "読み込んだ内容が正しく返されること");

    }

    /**
     * getTargetPath メソッドのテスト - 正常系:対象パスが正しく返されることの確認
     * <p>
     * 初期化で設定した対象パスが正しく返されることを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetTargetPath_normalTargetPathReturned(@TempDir final Path tempDir) throws KmgToolMsgException {

        /* 期待値の定義 */
        final Path expectedTargetPath = tempDir;

        /* 準備 */
        final JdtsIoLogicImpl testTarget = new JdtsIoLogicImpl();

        // 初期化
        testTarget.initialize(tempDir);

        /* テスト対象の実行 */
        final Path testResult = testTarget.getTargetPath();

        /* 検証の準備 */
        final Path actualTargetPath = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetPath, actualTargetPath, "対象パスが正しく返されること");

    }

    /**
     * initialize メソッドのテスト - 正常系:初期化が正しく実行されることの確認
     * <p>
     * 対象パスが正しく設定されることを確認します。
     * </p>
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testInitialize_normalInitializationSuccess() throws KmgToolMsgException {

        /* 期待値の定義 */
        final boolean expectedInitResult = true;

        /* 準備 */
        final JdtsIoLogicImpl testTarget = new JdtsIoLogicImpl();
        final Path            testPath   = Paths.get("test");

        /* テスト対象の実行 */
        final boolean testResult = testTarget.initialize(testPath);

        /* 検証の準備 */
        final boolean actualInitResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInitResult, actualInitResult, "初期化が正しく実行されること");

    }

    /**
     * load メソッドのテスト - 正常系:Javaファイルが正しくロードされることの確認
     * <p>
     * 指定ディレクトリ内のJavaファイルが正しくロードされることを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws IOException
     *                             入出力例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testLoad_normalJavaFilesLoaded(@TempDir final Path tempDir) throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final boolean expectedLoadResult = true;

        /* 準備 */
        final Path testFile = tempDir.resolve("TestClass.java");
        Files.writeString(testFile, "public class TestClass {}");

        final JdtsIoLogicImpl testTarget = new JdtsIoLogicImpl();

        // 初期化
        testTarget.initialize(tempDir);

        /* テスト対象の実行 */
        final boolean testResult = testTarget.load();

        /* 検証の準備 */
        final boolean actualLoadResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedLoadResult, actualLoadResult, "Javaファイルが正しくロードされること");

    }

    /**
     * load メソッドのテスト - 正常系:非Javaファイルが除外されることの確認
     * <p>
     * .java拡張子を持たないファイルが除外されることを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws IOException
     *                             入出力例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testLoad_normalNonJavaFilesExcluded(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedFileCount = 1;

        /* 準備 */
        final Path javaFile = tempDir.resolve("TestClass.java");
        final Path txtFile  = tempDir.resolve("readme.txt");
        Files.writeString(javaFile, "public class TestClass {}");
        Files.writeString(txtFile, "This is a text file");

        final JdtsIoLogicImpl testTarget = new JdtsIoLogicImpl();

        // 初期化とロード
        testTarget.initialize(tempDir);
        testTarget.load();

        /* テスト対象の実行 */
        final List<Path> testResult = testTarget.getFilePathList();

        /* 検証の準備 */
        final int actualFileCount = testResult.size();

        /* 検証の実施 */
        Assertions.assertEquals(expectedFileCount, actualFileCount, "非Javaファイルが除外されること");

    }

    /**
     * loadContent メソッドのテスト - 正常系:ファイル内容が正しく読み込まれることの確認
     * <p>
     * 現在のファイルの内容が正しく読み込まれることを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws IOException
     *                             入出力例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testLoadContent_normalContentLoaded(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final boolean expectedLoadResult = true;

        /* 準備 */
        final Path testFile = tempDir.resolve("TestClass.java");
        Files.writeString(testFile, "public class TestClass {}");

        final JdtsIoLogicImpl testTarget = new JdtsIoLogicImpl();

        // 初期化とロード
        testTarget.initialize(tempDir);
        testTarget.load();

        /* テスト対象の実行 */
        final boolean testResult = testTarget.loadContent();

        /* 検証の準備 */
        final boolean actualLoadResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedLoadResult, actualLoadResult, "ファイル内容が正しく読み込まれること");

    }

    /**
     * loadContent メソッドのテスト - 準正常系:空ファイルの場合にfalseが返されることの確認
     * <p>
     * 空のファイルの場合にfalseが返されることを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws IOException
     *                             入出力例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testLoadContent_semiEmptyFileReturnsFalse(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final boolean expectedLoadResult = false;

        /* 準備 */
        final Path testFile = tempDir.resolve("EmptyClass.java");
        Files.writeString(testFile, ""); // 空ファイル

        final JdtsIoLogicImpl testTarget = new JdtsIoLogicImpl();

        // 初期化とロード
        testTarget.initialize(tempDir);
        testTarget.load();

        /* テスト対象の実行 */
        final boolean testResult = testTarget.loadContent();

        /* 検証の準備 */
        final boolean actualLoadResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedLoadResult, actualLoadResult, "空ファイルの場合にfalseが返されること");

    }

    /**
     * nextFile メソッドのテスト - 正常系:次のファイルに正しく移動することの確認
     * <p>
     * 複数のファイルがある場合に次のファイルに正しく移動できることを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws IOException
     *                             入出力例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testNextFile_normalNextFileExists(@TempDir final Path tempDir) throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final boolean expectedNextFileResult = true;

        /* 準備 */
        final Path testFile1 = tempDir.resolve("TestClass1.java");
        final Path testFile2 = tempDir.resolve("TestClass2.java");
        Files.writeString(testFile1, "public class TestClass1 {}");
        Files.writeString(testFile2, "public class TestClass2 {}");

        final JdtsIoLogicImpl testTarget = new JdtsIoLogicImpl();

        // 初期化とロード
        testTarget.initialize(tempDir);
        testTarget.load();

        /* テスト対象の実行 */
        final boolean testResult = testTarget.nextFile();

        /* 検証の準備 */
        final boolean actualNextFileResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedNextFileResult, actualNextFileResult, "次のファイルに正しく移動できること");

    }

    /**
     * nextFile メソッドのテスト - 正常系:次のファイルが存在しない場合にfalseが返されることの確認
     * <p>
     * すべてのファイルを処理し終えた場合にfalseが返されることを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws IOException
     *                             入出力例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testNextFile_normalNoNextFileReturnsFalse(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final boolean expectedNextFileResult = false;

        /* 準備 */
        final Path testFile = tempDir.resolve("TestClass.java");
        Files.writeString(testFile, "public class TestClass {}");

        final JdtsIoLogicImpl testTarget = new JdtsIoLogicImpl();

        // 初期化とロード
        testTarget.initialize(tempDir);
        testTarget.load();

        // すべてのファイルを処理し終える
        while (testTarget.nextFile()) {

            // 何もしない
        }

        /* テスト対象の実行 */
        final boolean testResult = testTarget.nextFile();

        /* 検証の準備 */
        final boolean actualNextFileResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedNextFileResult, actualNextFileResult, "次のファイルが存在しない場合にfalseが返されること");

    }

    /**
     * setWriteContent メソッドのテスト - 正常系:書き込み内容が正しく設定されることの確認
     * <p>
     * 書き込み内容が正しく設定されることを確認します。
     * </p>
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testSetWriteContent_normalWriteContentSet() throws KmgToolMsgException {

        /* 期待値の定義 */
        // setWriteContentは void なので、後続のwriteContentで検証

        /* 準備 */
        final String          testContent = "public class ModifiedClass {}";
        final JdtsIoLogicImpl testTarget  = new JdtsIoLogicImpl();
        final Path            testPath    = Paths.get("test");

        // 初期化
        testTarget.initialize(testPath);

        /* テスト対象の実行 */
        testTarget.setWriteContent(testContent);

        /* 検証の準備 */
        // setWriteContentの結果は内部状態として保存されるため、
        // 実際の検証はwriteContentメソッドのテストで行う

        /* 検証の実施 */
        Assertions.assertDoesNotThrow(() -> testTarget.setWriteContent(testContent), "書き込み内容が正しく設定されること");

    }

    /**
     * writeContent メソッドのテスト - 正常系:ファイルが正しく書き込まれることの確認
     * <p>
     * 設定した内容がファイルに正しく書き込まれることを確認します。
     * </p>
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws IOException
     *                             入出力例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testWriteContent_normalContentWritten(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final boolean expectedWriteResult = true;
        final String  expectedContent     = "public class ModifiedClass {}";

        /* 準備 */
        final Path testFile = tempDir.resolve("TestClass.java");
        Files.writeString(testFile, "public class TestClass {}");

        final JdtsIoLogicImpl testTarget = new JdtsIoLogicImpl();

        // 初期化とロード
        testTarget.initialize(tempDir);
        testTarget.load();
        testTarget.setWriteContent(expectedContent);

        /* テスト対象の実行 */
        final boolean testResult = testTarget.writeContent();

        /* 検証の準備 */
        final boolean actualWriteResult = testResult;
        final String  actualContent     = Files.readString(testFile);

        /* 検証の実施 */
        Assertions.assertEquals(expectedWriteResult, actualWriteResult, "ファイルが正しく書き込まれること");
        Assertions.assertEquals(expectedContent, actualContent, "書き込まれた内容が正しいこと");

    }
}
