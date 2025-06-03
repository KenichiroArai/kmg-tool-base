package kmg.tool.application.logic.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;

import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;
import kmg.tool.presentation.ui.cli.JavadocLineRemoverTool;

/**
 * Javadoc行削除ロジック実装のテスト<br>
 *
 * @author KenichiroArai
 */
@SpringBootTest(classes = JavadocLineRemoverTool.class)
@SuppressWarnings({
    "nls", "static-method"
})
public class JavadocLineRemoverLogicImplTest {

    /**
     * デフォルトコンストラクタ<br>
     */
    public JavadocLineRemoverLogicImplTest() {

        // 処理なし
    }

    /**
     * deleteJavadocLines メソッドのテスト - 正常系:指定したJavadoc行が正しく削除されることの確認
     * <p>
     * 存在するJavaファイルから指定した行番号の行が正しく削除されることを確認します。
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
    public void testDeleteJavadocLines_normalDeleteSpecifiedLines(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final int      expectedDeletedLinesCount = 3;
        final String[] expectedRemainingLines    = {
            "package test;", "public class Test {", "}",
        };

        /* 準備 */
        final Path     testJavaFile  = tempDir.resolve("Test.java");
        final String[] originalLines = {
            "package test;", "/**", " * Javadoc comment", " */", "public class Test {", "}",
        };
        Files.write(testJavaFile, java.util.Arrays.asList(originalLines));

        final Map<Path, Set<Integer>> testInputMap    = new LinkedHashMap<>();
        final Set<Integer>            testLineNumbers = new LinkedHashSet<>();
        testLineNumbers.add(4); // " */" (降順で削除されるため最初に削除)
        testLineNumbers.add(3); // " * Javadoc comment"
        testLineNumbers.add(2); // "/**"
        testInputMap.put(testJavaFile, testLineNumbers);

        final JavadocLineRemoverLogicImpl testTarget = new JavadocLineRemoverLogicImpl();

        /* テスト対象の実行 */
        final int testResult = testTarget.deleteJavadocLines(testInputMap);

        /* 検証の準備 */
        final java.util.List<String> actualRemainingLines = Files.readAllLines(testJavaFile);
        final String[]               actualLinesArray     = actualRemainingLines.toArray(new String[0]);

        /* 検証の実施 */
        Assertions.assertEquals(expectedDeletedLinesCount, testResult, "削除された行数が正しいこと");
        Assertions.assertArrayEquals(expectedRemainingLines, actualLinesArray, "残った行の内容が正しいこと");

    }

    /**
     * deleteJavadocLines メソッドのテスト - 準正常系:存在しないファイルでIOExceptionが発生することの確認
     * <p>
     * 存在しないファイルが指定された場合に、適切な例外が発生することを確認します。
     * </p>
     */
    @Test
    public void testDeleteJavadocLines_semiNonExistentFileThrowsException() {

        /* 期待値の定義 */
        final Path               nonExistentFile = Paths.get("nonexistent.java");
        final KmgToolGenMsgTypes expectedMsgType = KmgToolGenMsgTypes.KMGTOOL_GEN32015;

        /* 準備 */
        final Map<Path, Set<Integer>> testInputMap    = new LinkedHashMap<>();
        final Set<Integer>            testLineNumbers = new LinkedHashSet<>();
        testLineNumbers.add(1);
        testInputMap.put(nonExistentFile, testLineNumbers);

        final JavadocLineRemoverLogicImpl testTarget = new JavadocLineRemoverLogicImpl();

        /* テスト対象の実行と検証の実施 */
        final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

            testTarget.deleteJavadocLines(testInputMap);

        }, "存在しないファイルの場合は例外が発生すること");

        /* 検証の準備 */
        final KmgToolGenMsgTypes actualMsgType = (KmgToolGenMsgTypes) testException.getMessageTypes();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMsgType, actualMsgType, "期待されるメッセージタイプの例外が発生すること");

    }

    /**
     * deleteJavadocLines メソッドのテスト - 正常系:無効な行番号が指定された場合にスキップされることの確認
     * <p>
     * 0以下や範囲外の行番号が指定された場合に、その行番号がスキップされることを確認します。
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
    public void testDeleteJavadocLines_normalInvalidLineNumbersSkipped(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final int      expectedDeletedLinesCount = 1;
        final String[] expectedRemainingLines    = {
            "package test;", "public class Test {",
        };

        /* 準備 */
        final Path     testJavaFile  = tempDir.resolve("Test.java");
        final String[] originalLines = {
            "package test;", "// comment to delete", "public class Test {",
        };
        Files.write(testJavaFile, java.util.Arrays.asList(originalLines));

        final Map<Path, Set<Integer>> testInputMap    = new LinkedHashMap<>();
        final Set<Integer>            testLineNumbers = new LinkedHashSet<>();
        testLineNumbers.add(0); // 無効（0以下）
        testLineNumbers.add(2); // 有効
        testLineNumbers.add(10); // 無効（範囲外）
        testInputMap.put(testJavaFile, testLineNumbers);

        final JavadocLineRemoverLogicImpl testTarget = new JavadocLineRemoverLogicImpl();

        /* テスト対象の実行 */
        final int testResult = testTarget.deleteJavadocLines(testInputMap);

        /* 検証の準備 */
        final java.util.List<String> actualRemainingLines = Files.readAllLines(testJavaFile);
        final String[]               actualLinesArray     = actualRemainingLines.toArray(new String[0]);

        /* 検証の実施 */
        Assertions.assertEquals(expectedDeletedLinesCount, testResult, "有効な行番号のみが削除されること");
        Assertions.assertArrayEquals(expectedRemainingLines, actualLinesArray, "残った行の内容が正しいこと");

    }

    /**
     * getInputMap メソッドのテスト - 正常系:入力ファイルから正しくパスと行番号のマップが取得されることの確認
     * <p>
     * Javaファイルの行を含む入力ファイルから、正しくパスと行番号のマップが生成されることを確認します。
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
    public void testGetInputMap_normalValidInputFileProcessing(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final Path expectedPath1 = Paths.get("D:\\test\\Sample1.java");
        final Path expectedPath2 = Paths.get("D:\\test\\Sample2.java");

        /* 準備 */
        final Path     testInputFile = tempDir.resolve("input.txt");
        final String[] inputLines    = {
            "D:\\test\\Sample1.java:123: @SuppressWarnings", "D:\\test\\Sample1.java:456: @Override",
            "D:\\test\\Sample2.java:789: @Deprecated", "not a java file line",
            "D:\\test\\Sample1.java:123: @SuppressWarnings",                                         // 重複
        };
        Files.write(testInputFile, java.util.Arrays.asList(inputLines));

        final JavadocLineRemoverLogicImpl testTarget = new JavadocLineRemoverLogicImpl();

        /* テスト対象の実行 */
        final Map<Path, Set<Integer>> testResult = testTarget.getInputMap(testInputFile);

        /* 検証の準備 */
        // 準備なし（結果を直接検証）

        /* 検証の実施 */
        Assertions.assertEquals(2, testResult.size(), "2つのファイルパスが含まれること");
        Assertions.assertTrue(testResult.containsKey(expectedPath1), "Sample1.javaのパスが含まれること");
        Assertions.assertTrue(testResult.containsKey(expectedPath2), "Sample2.javaのパスが含まれること");

        final Set<Integer> actualLineNumbers1 = testResult.get(expectedPath1);
        Assertions.assertEquals(2, actualLineNumbers1.size(), "Sample1.javaに2つの行番号が含まれること");
        Assertions.assertTrue(actualLineNumbers1.contains(123), "行番号123が含まれること");
        Assertions.assertTrue(actualLineNumbers1.contains(456), "行番号456が含まれること");

        final Set<Integer> actualLineNumbers2 = testResult.get(expectedPath2);
        Assertions.assertEquals(1, actualLineNumbers2.size(), "Sample2.javaに1つの行番号が含まれること");
        Assertions.assertTrue(actualLineNumbers2.contains(789), "行番号789が含まれること");

    }

    /**
     * getInputMap メソッドのテスト - 準正常系:存在しない入力ファイルでIOExceptionが発生することの確認
     * <p>
     * 存在しない入力ファイルが指定された場合に、適切な例外が発生することを確認します。
     * </p>
     */
    @Test
    public void testGetInputMap_semiNonExistentInputFileThrowsException() {

        /* 期待値の定義 */
        final Path               nonExistentInputFile = Paths.get("nonexistent_input.txt");
        final KmgToolGenMsgTypes expectedMsgType      = KmgToolGenMsgTypes.KMGTOOL_GEN32016;

        /* 準備 */
        final JavadocLineRemoverLogicImpl testTarget = new JavadocLineRemoverLogicImpl();

        /* テスト対象の実行と検証の実施 */
        final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

            testTarget.getInputMap(nonExistentInputFile);

        }, "存在しない入力ファイルの場合は例外が発生すること");

        /* 検証の準備 */
        final KmgToolGenMsgTypes actualMsgType = (KmgToolGenMsgTypes) testException.getMessageTypes();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMsgType, actualMsgType, "期待されるメッセージタイプの例外が発生すること");

    }

    /**
     * getInputMap メソッドのテスト - 正常系:空の入力ファイルで空のマップが返されることの確認
     * <p>
     * 空の入力ファイルが指定された場合に、空のマップが返されることを確認します。
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
    public void testGetInputMap_normalEmptyInputFileReturnsEmptyMap(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedMapSize = 0;

        /* 準備 */
        final Path testInputFile = tempDir.resolve("empty_input.txt");
        Files.write(testInputFile, java.util.Arrays.asList()); // 空ファイル

        final JavadocLineRemoverLogicImpl testTarget = new JavadocLineRemoverLogicImpl();

        /* テスト対象の実行 */
        final Map<Path, Set<Integer>> testResult = testTarget.getInputMap(testInputFile);

        /* 検証の準備 */
        final int actualMapSize = testResult.size();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMapSize, actualMapSize, "空の入力ファイルの場合は空のマップが返されること");

    }

}
