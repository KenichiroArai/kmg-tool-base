package kmg.tool.jdts.test.it;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.jdts.application.service.impl.JdtsServiceImpl;
import kmg.tool.jdts.presentation.ui.cli.JavadocTagSetterTool;

/**
 * Javadocタグ設定ツールの結合テスト001のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SpringBootTest(classes = JavadocTagSetterTool.class)
@ActiveProfiles("test")
@SuppressWarnings({
    "nls",
})
public class JavadocTagSetterIt001lTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    @Autowired
    private JdtsServiceImpl testTarget;

    /**
     * テスト用の一時ディレクトリ
     *
     * @since 0.1.0
     */
    @TempDir
    private Path tempDir;

    /**
     * main メソッドのテスト - 正常系：パターン01<br>
     * <p>
     * クラスのJavadocで、タグが一つもない場合に、タグを追加する。
     * </p>
     *
     * @since 0.1.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMain_normalPt01(final TestInfo testInfo) throws Exception {

        /* 期待値の定義 */

        // テストメソッドパス
        final Path testMethodPath = this.getCurrentTestMethodPath(testInfo);

        // 期待値対象
        final Path   expectedTargetPath = testMethodPath.resolve("ExpectedTarget.java");
        final String expectedContent    = Files.readString(expectedTargetPath);

        /* 準備 */

        // テスト入力ファイルパス
        final Path testInputPath = testMethodPath.resolve("TestInput.java");

        // テスト定義ファイルパス
        final Path testDefinitionPath = testMethodPath.resolve("TestTemplate.yml");

        // テスト作業用入力ファイルパス（tempDirにコピーして使用）
        final Path testWorkInputPath = this.tempDir.resolve(testInputPath.getFileName());
        Files.copy(testInputPath, testWorkInputPath, StandardCopyOption.REPLACE_EXISTING);

        /* テスト対象の実行 */
        this.testTarget.initialize(testWorkInputPath, testDefinitionPath);
        this.testTarget.process();

        /* 検証の準備 */

        // 実際の対象ファイル
        final Path   actualTargetPath = this.testTarget.getTargetPath();
        final String actualContent    = Files.readString(actualTargetPath);

        /* 検証の実施 */

        // 期待値ファイルと実際のファイルの内容を比較
        Assertions.assertEquals(expectedContent, actualContent, "ファイル内容が一致すること");

    }

    /**
     * main メソッドのテスト - 正常系：パターン02<br>
     * <p>
     * クラスのJavadocで、タグが一つもない場合に、タグを追加する。
     * </p>
     *
     * @since 0.1.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMain_normalPt02(final TestInfo testInfo) throws Exception {

        /* 期待値の定義 */

        // テストメソッドパス
        final Path testMethodPath = this.getCurrentTestMethodPath(testInfo);

        // 期待値対象
        final Path   expectedTargetPath = testMethodPath.resolve("ExpectedTarget.java");
        final String expectedContent    = Files.readString(expectedTargetPath);

        /* 準備 */

        // テスト入力ファイルパス
        final Path testInputPath = testMethodPath.resolve("TestInput.java");

        // テスト定義ファイルパス
        final Path testDefinitionPath = testMethodPath.resolve("TestTemplate.yml");

        // テスト作業用入力ファイルパス（tempDirにコピーして使用）
        final Path testWorkInputPath = this.tempDir.resolve(testInputPath.getFileName());
        Files.copy(testInputPath, testWorkInputPath, StandardCopyOption.REPLACE_EXISTING);

        /* テスト対象の実行 */
        this.testTarget.initialize(testWorkInputPath, testDefinitionPath);
        this.testTarget.process();

        /* 検証の準備 */

        // 実際の対象ファイル
        final Path   actualTargetPath = this.testTarget.getTargetPath();
        final String actualContent    = Files.readString(actualTargetPath);

        /* 検証の実施 */

        // 期待値ファイルと実際のファイルの内容を比較
        Assertions.assertEquals(expectedContent, actualContent, "ファイル内容が一致すること");

    }

}
