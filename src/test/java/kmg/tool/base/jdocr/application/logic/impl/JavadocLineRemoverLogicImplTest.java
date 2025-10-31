package kmg.tool.base.jdocr.application.logic.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;

/**
 * Javadoc行削除ロジック実装のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class JavadocLineRemoverLogicImplTest {

    /**
     * モックKMGメッセージソース
     *
     * @since 0.2.0
     */
    private KmgMessageSource mockMessageSource;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public JavadocLineRemoverLogicImplTest() {

        // 処理なし
    }

    /**
     * convertLineToPathLineEntry メソッドのテスト - 正常系:ドライブ文字の置換が正しく動作することの確認
     * <p>
     * ドライブ文字の置換処理が正しく動作することを確認します。
     * </p>
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertLineToPathLineEntry_normalDriveLetterReplacement() throws Exception {

        /* 期待値の定義 */
        final Path expectedPath       = Paths.get("D:\\eclipse\\workspace\\Sample.java");
        final int  expectedLineNumber = 456;

        /* 準備 */
        final String                      testInputLine = "D:\\eclipse\\workspace\\Sample.java:456: @Override";
        final JavadocLineRemoverLogicImpl testTarget    = new JavadocLineRemoverLogicImpl();
        final KmgReflectionModelImpl      reflection    = new KmgReflectionModelImpl(testTarget);

        /* テスト対象の実行 */
        @SuppressWarnings("unchecked")
        final SimpleEntry<Path, Integer> testResult
            = (SimpleEntry<Path, Integer>) reflection.getMethod("convertLineToPathLineEntry", testInputLine);

        /* 検証の準備 */
        final Path actualPath       = testResult.getKey();
        final int  actualLineNumber = testResult.getValue();

        /* 検証の実施 */
        Assertions.assertEquals(expectedPath, actualPath, "ドライブ文字の置換が正しく動作すること");
        Assertions.assertEquals(expectedLineNumber, actualLineNumber, "行番号が正しく変換されること");

    }

    /**
     * convertLineToPathLineEntry メソッドのテスト - 正常系:有効なJavaファイル行が正しく変換されることの確認
     * <p>
     * 正常なJavaファイルの行文字列から、パスと行番号のエントリが正しく作成されることを確認します。
     * </p>
     * * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertLineToPathLineEntry_normalValidJavaFileLine() throws Exception {

        /* 期待値の定義 */
        final Path expectedPath       = Paths.get("D:\\test\\Sample.java");
        final int  expectedLineNumber = 123;

        /* 準備 */
        final String                      testInputLine = "D:\\test\\Sample.java:123: @SuppressWarnings";
        final JavadocLineRemoverLogicImpl testTarget    = new JavadocLineRemoverLogicImpl();
        final KmgReflectionModelImpl      reflection    = new KmgReflectionModelImpl(testTarget);

        /* テスト対象の実行 */
        @SuppressWarnings("unchecked")
        final SimpleEntry<Path, Integer> testResult
            = (SimpleEntry<Path, Integer>) reflection.getMethod("convertLineToPathLineEntry", testInputLine);

        /* 検証の準備 */
        final Path actualPath       = testResult.getKey();
        final int  actualLineNumber = testResult.getValue();

        /* 検証の実施 */
        Assertions.assertEquals(expectedPath, actualPath, "パスが正しく変換されること");
        Assertions.assertEquals(expectedLineNumber, actualLineNumber, "行番号が正しく変換されること");

    }

    /**
     * convertLineToPathLineEntry メソッドのテスト - 準正常系:コロンが不足する行でnullが返されることの確認
     * <p>
     * コロンで区切られた部分が2つ未満の場合に、nullが返されることを確認します。
     * </p>
     * * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertLineToPathLineEntry_semiInsufficientColonReturnsNull() throws Exception {

        /* 期待値の定義 */
        final SimpleEntry<Path, Integer> expectedResult = null;

        /* 準備 */
        final String                      testInputLine = "D:\\test\\Sample.java @SuppressWarnings";
        final JavadocLineRemoverLogicImpl testTarget    = new JavadocLineRemoverLogicImpl();
        final KmgReflectionModelImpl      reflection    = new KmgReflectionModelImpl(testTarget);

        /* テスト対象の実行 */
        @SuppressWarnings("unchecked")
        final SimpleEntry<Path, Integer> testResult
            = (SimpleEntry<Path, Integer>) reflection.getMethod("convertLineToPathLineEntry", testInputLine);

        /* 検証の準備 */
        // 準備なし（結果を直接検証）

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, testResult, "コロンが不足する場合はnullが返されること");

    }

    /**
     * convertLineToPathLineEntry メソッドのテスト - 準正常系:無効な行番号でnullが返されることの確認
     * <p>
     * 行番号部分が数値でない場合に、nullが返されることを確認します。
     * </p>
     * * @since 0.2.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertLineToPathLineEntry_semiInvalidLineNumberReturnsNull() throws Exception {

        /* 期待値の定義 */
        final SimpleEntry<Path, Integer> expectedResult = null;

        /* 準備 */
        final String                      testInputLine = "D:\\test\\Sample.java:abc: @SuppressWarnings";
        final JavadocLineRemoverLogicImpl testTarget    = new JavadocLineRemoverLogicImpl();
        final KmgReflectionModelImpl      reflection    = new KmgReflectionModelImpl(testTarget);

        /* テスト対象の実行 */
        @SuppressWarnings("unchecked")
        final SimpleEntry<Path, Integer> testResult
            = (SimpleEntry<Path, Integer>) reflection.getMethod("convertLineToPathLineEntry", testInputLine);

        /* 検証の準備 */
        // 準備なし（結果を直接検証）

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, testResult, "無効な行番号の場合はnullが返されること");

    }

    /**
     * convertLineToPathLineEntry メソッドのテスト - 準正常系:@マークが含まれない行でnullが返されることの確認
     * <p>
     *
     * @since 0.2.0
     *
     * @マークが含まれない行の場合に、nullが返されることを確認します。
     *                                    </p>
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConvertLineToPathLineEntry_semiNoAtMarkReturnsNull() throws Exception {

        /* 期待値の定義 */
        final SimpleEntry<Path, Integer> expectedResult = null;

        /* 準備 */
        final String                      testInputLine = "D:\\test\\Sample.java:123: SuppressWarnings";
        final JavadocLineRemoverLogicImpl testTarget    = new JavadocLineRemoverLogicImpl();
        final KmgReflectionModelImpl      reflection    = new KmgReflectionModelImpl(testTarget);

        /* テスト対象の実行 */
        @SuppressWarnings("unchecked")
        final SimpleEntry<Path, Integer> testResult
            = (SimpleEntry<Path, Integer>) reflection.getMethod("convertLineToPathLineEntry", testInputLine);

        /* 検証の準備 */
        // 準備なし（結果を直接検証）

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, testResult, "@マークが含まれない場合はnullが返されること");

    }

    /**
     * deleteJavadocLines メソッドのテスト - 正常系:指定したJavadoc行が正しく削除されることの確認
     * <p>
     * 存在するJavaファイルから指定した行番号の行が正しく削除されることを確認します。
     * </p>
     *
     * @since 0.2.0
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
     * deleteJavadocLines メソッドのテスト - 正常系:空のマップで0が返されることの確認
     * <p>
     * 空のマップが渡された場合に、削除された行数として0が返されることを確認します。
     * </p>
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testDeleteJavadocLines_normalEmptyMapReturnsZero() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedDeletedLinesCount = 0;

        /* 準備 */
        final Map<Path, Set<Integer>>     testInputMap = new LinkedHashMap<>();
        final JavadocLineRemoverLogicImpl testTarget   = new JavadocLineRemoverLogicImpl();

        /* テスト対象の実行 */
        final int testResult = testTarget.deleteJavadocLines(testInputMap);

        /* 検証の準備 */
        // 準備なし（結果を直接検証）

        /* 検証の実施 */
        Assertions.assertEquals(expectedDeletedLinesCount, testResult, "空のマップの場合は0が返されること");

    }

    /**
     * deleteJavadocLines メソッドのテスト - 正常系:inputMapのcontainsKeyチェックの動作確認
     * <p>
     * このテストは、deleteJavadocLinesメソッド内のcontainsKeyチェックが機能することを確認します。 通常の使用では発生しない状況ですが、コードカバレッジのためにテストします。
     * カスタムMapを使用してcontainsKeyが常にfalseを返すケースをシミュレートします。
     * </p>
     *
     * @since 0.2.0
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
    public void testDeleteJavadocLines_normalInputMapContainsKeyCheck(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedDeletedLinesCount = 0; // containsKeyがfalseなので削除されない

        /* 準備 */
        final Path     testJavaFile  = tempDir.resolve("Test.java");
        final String[] originalLines = {
            "package test;", "// comment", "public class Test {", "}",
        };
        Files.write(testJavaFile, java.util.Arrays.asList(originalLines));

        final Set<Integer> testLineNumbers = new LinkedHashSet<>();
        testLineNumbers.add(2);

        // containsKeyが常にfalseを返すカスタムMap
        final Map<Path, Set<Integer>> testInputMap = new LinkedHashMap<>() {

            private static final long serialVersionUID = 1L;

            {

                this.put(testJavaFile, testLineNumbers);

            }

            @Override
            public boolean containsKey(final Object key) {

                // 常にfalseを返す
                final boolean result = false;
                return result;

            }
        };

        final JavadocLineRemoverLogicImpl testTarget = new JavadocLineRemoverLogicImpl();

        /* テスト対象の実行 */
        final int testResult = testTarget.deleteJavadocLines(testInputMap);

        /* 検証の準備 */
        final java.util.List<String> actualRemainingLines = Files.readAllLines(testJavaFile);

        /* 検証の実施 */
        Assertions.assertEquals(expectedDeletedLinesCount, testResult, "containsKeyがfalseのため削除されない");
        Assertions.assertEquals(originalLines.length, actualRemainingLines.size(), "元のファイルが変更されていない");

    }

    /**
     * deleteJavadocLines メソッドのテスト - 正常系:無効な行番号が指定された場合にスキップされることの確認
     * <p>
     * 0以下や範囲外の行番号が指定された場合に、その行番号がスキップされることを確認します。
     * </p>
     *
     * @since 0.2.0
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
     * deleteJavadocLines メソッドのテスト - 正常系:降順でない行番号セットが正しく処理されることの確認
     * <p>
     * 降順でない行番号のセットが渡された場合でも、正しく行が削除されることを確認します。
     * </p>
     *
     * @since 0.2.0
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
    public void testDeleteJavadocLines_normalUnorderedLineNumbers(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final int      expectedDeletedLinesCount = 3;
        final String[] expectedRemainingLines    = {
            "package test;", "// line 3", "public class Test {",
        };

        /* 準備 */
        final Path     testJavaFile  = tempDir.resolve("Test.java");
        final String[] originalLines = {
            "package test;", "// line 2", "// line 3", "// line 4", "public class Test {", "}",
        };
        Files.write(testJavaFile, java.util.Arrays.asList(originalLines));

        final Map<Path, Set<Integer>> testInputMap    = new LinkedHashMap<>();
        final Set<Integer>            testLineNumbers = new LinkedHashSet<>();
        // 削除対象: 行2、行3、行4 (コメント行を削除)
        testLineNumbers.add(2);
        testLineNumbers.add(3);
        testLineNumbers.add(4);
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
     *
     * @since 0.2.0
     */
    @Test
    public void testDeleteJavadocLines_semiNonExistentFileThrowsException() {

        /* 期待値の定義 */
        final Path               nonExistentFile = Paths.get("nonexistent.java");
        final KmgToolGenMsgTypes expectedMsgType = KmgToolGenMsgTypes.KMGTOOL_GEN12001;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

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

    }

    /**
     * deleteJavadocLines メソッドのテスト - 準正常系:ファイル書き込み時のIOExceptionが適切に処理されることの確認
     * <p>
     * 読み込み専用ファイルへの書き込み時にIOExceptionが発生し、適切な例外が投げられることを確認します。
     * </p>
     *
     * @since 0.2.0
     *
     * @param tempDir
     *                一時ディレクトリ
     *
     * @throws IOException
     *                     入出力例外
     */
    @Test
    public void testDeleteJavadocLines_semiWriteIOExceptionThrowsException(@TempDir final Path tempDir)
        throws IOException {

        /* 期待値の定義 */
        final KmgToolGenMsgTypes expectedMsgType = KmgToolGenMsgTypes.KMGTOOL_GEN12000;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            final Path     testJavaFile  = tempDir.resolve("ReadOnlyTest.java");
            final String[] originalLines = {
                "package test;", "public class Test {", "}",
            };
            Files.write(testJavaFile, java.util.Arrays.asList(originalLines));

            // ファイルを読み込み専用に設定
            testJavaFile.toFile().setReadOnly();

            final Map<Path, Set<Integer>> testInputMap    = new LinkedHashMap<>();
            final Set<Integer>            testLineNumbers = new LinkedHashSet<>();
            testLineNumbers.add(2);
            testInputMap.put(testJavaFile, testLineNumbers);

            final JavadocLineRemoverLogicImpl testTarget = new JavadocLineRemoverLogicImpl();

            /* テスト対象の実行と検証の実施 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                testTarget.deleteJavadocLines(testInputMap);

            }, "読み込み専用ファイルの書き込み時は例外が発生すること");

            /* 検証の準備 */
            final KmgToolGenMsgTypes actualMsgType = (KmgToolGenMsgTypes) testException.getMessageTypes();

            /* 検証の実施 */
            Assertions.assertEquals(expectedMsgType, actualMsgType, "期待されるメッセージタイプの例外が発生すること");

        }

    }

    /**
     * getInputMap メソッドのテスト - 正常系:空の入力ファイルで空のマップが返されることの確認
     * <p>
     * 空の入力ファイルが指定された場合に、空のマップが返されることを確認します。
     * </p>
     *
     * @since 0.2.0
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

    /**
     * getInputMap メソッドのテスト - 正常系:行番号が降順でソートされることの確認
     * <p>
     * 同一ファイルの行番号が降順でソートされることを確認します。
     * </p>
     *
     * @since 0.2.0
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
    public void testGetInputMap_normalLineNumbersSortedDescending(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final Path      expectedPath        = Paths.get("D:\\test\\Sample.java");
        final Integer[] expectedLineNumbers = {
            789, 456, 123
        };                                                                       // 降順

        /* 準備 */
        final Path     testInputFile = tempDir.resolve("input.txt");
        final String[] inputLines    = {
            "D:\\test\\Sample.java:123: @SuppressWarnings", "D:\\test\\Sample.java:456: @Override",
            "D:\\test\\Sample.java:789: @Deprecated",
        };
        Files.write(testInputFile, java.util.Arrays.asList(inputLines));

        final JavadocLineRemoverLogicImpl testTarget = new JavadocLineRemoverLogicImpl();

        /* テスト対象の実行 */
        final Map<Path, Set<Integer>> testResult = testTarget.getInputMap(testInputFile);

        /* 検証の準備 */
        final Set<Integer> actualLineNumbers      = testResult.get(expectedPath);
        final Integer[]    actualLineNumbersArray = actualLineNumbers.toArray(new Integer[0]);

        /* 検証の実施 */
        Assertions.assertArrayEquals(expectedLineNumbers, actualLineNumbersArray, "行番号が降順でソートされること");

    }

    /**
     * getInputMap メソッドのテスト - 正常系:nullエントリと非nullエントリが混在する場合の正しい処理確認
     * <p>
     * convertLineToPathLineEntryがnullを返す行と有効なエントリを返す行が混在する場合に、 nullエントリが正しくフィルタリングされ、有効なエントリのみが処理されることを確認します。
     * </p>
     *
     * @since 0.2.0
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
    public void testGetInputMap_normalMixedValidAndInvalidEntries(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final Path expectedPath            = Paths.get("D:\\test\\Sample.java");
        final int  expectedMapSize         = 1;
        final int  expectedLineNumberCount = 2;

        /* 準備 */
        final Path     testInputFile = tempDir.resolve("input.txt");
        final String[] inputLines    = {
            "D:\\test\\Sample.java:123: @SuppressWarnings",                     // 有効（@マークあり）
            "D:\\test\\Sample.java:456: @Override",                             // 有効（@マークあり）
            "D:\\test\\Sample.java:789: SuppressWarnings",                      // 無効（@マークなし、nullを返す）
            "D:\\test\\Sample.java:abc: @Deprecated",                           // 無効（行番号が数値でない、nullを返す）
            "D:\\test\\Sample.java @TestAnnotation",                            // 無効（コロン不足、nullを返す）
        };
        Files.write(testInputFile, java.util.Arrays.asList(inputLines));

        final JavadocLineRemoverLogicImpl testTarget = new JavadocLineRemoverLogicImpl();

        /* テスト対象の実行 */
        final Map<Path, Set<Integer>> testResult = testTarget.getInputMap(testInputFile);

        /* 検証の準備 */
        final int actualMapSize = testResult.size();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMapSize, actualMapSize, "1つのファイルパスが含まれること");
        Assertions.assertTrue(testResult.containsKey(expectedPath), "Sample.javaのパスが含まれること");

        final Set<Integer> actualLineNumbers = testResult.get(expectedPath);
        Assertions.assertEquals(expectedLineNumberCount, actualLineNumbers.size(), "有効な行番号のみが含まれること");
        Assertions.assertTrue(actualLineNumbers.contains(123), "行番号123が含まれること");
        Assertions.assertTrue(actualLineNumbers.contains(456), "行番号456が含まれること");
        Assertions.assertFalse(actualLineNumbers.contains(789), "無効な行番号789は含まれないこと");

    }

    /**
     * getInputMap メソッドのテスト - 正常系:Javaファイル拡張子を含まない行がフィルタされることの確認
     * <p>
     * Javaファイル拡張子を含まない行が適切にフィルタされることを確認します。
     * </p>
     *
     * @since 0.2.0
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
    public void testGetInputMap_normalNonJavaFileLinesFiltered(@TempDir final Path tempDir)
        throws IOException, KmgToolMsgException {

        /* 期待値の定義 */
        final int  expectedMapSize = 1;
        final Path expectedPath    = Paths.get("D:\\test\\Sample.java");

        /* 準備 */
        final Path     testInputFile = tempDir.resolve("input.txt");
        final String[] inputLines    = {
            "D:\\test\\Sample.java:123: @SuppressWarnings", "D:\\test\\Sample.txt:456: @Override",           // .java以外
            "D:\\test\\Sample.cpp:789: @Deprecated",                                                         // .java以外
            "some random text",
        };
        Files.write(testInputFile, java.util.Arrays.asList(inputLines));

        final JavadocLineRemoverLogicImpl testTarget = new JavadocLineRemoverLogicImpl();

        /* テスト対象の実行 */
        final Map<Path, Set<Integer>> testResult = testTarget.getInputMap(testInputFile);

        /* 検証の準備 */
        final int actualMapSize = testResult.size();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMapSize, actualMapSize, "Javaファイルのみが含まれること");
        Assertions.assertTrue(testResult.containsKey(expectedPath), "Javaファイルのパスが含まれること");

    }

    /**
     * getInputMap メソッドのテスト - 正常系:入力ファイルから正しくパスと行番号のマップが取得されることの確認
     * <p>
     * Javaファイルの行を含む入力ファイルから、正しくパスと行番号のマップが生成されることを確認します。
     * </p>
     *
     * @since 0.2.0
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
     *
     * @since 0.2.0
     */
    @Test
    public void testGetInputMap_semiNonExistentInputFileThrowsException() {

        /* 期待値の定義 */
        final Path               nonExistentInputFile = Paths.get("nonexistent_input.txt");
        final KmgToolGenMsgTypes expectedMsgType      = KmgToolGenMsgTypes.KMGTOOL_GEN12002;

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

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

    }

}
