package kmg.tool.base.jdts.test.it;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.base.jdts.application.service.impl.JdtsServiceImpl;

/**
 * Javadocタグ設定ツールの結合テスト002のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.2
 *
 * @version 0.2.2
 */
@SpringBootTest(classes = JdtsServiceImpl.class)
@ActiveProfiles("test")
@SuppressWarnings({
    "nls",
})
@Disabled
public class JavadocTagSetterIt002lTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.2.2
     */
    @Autowired
    private JdtsServiceImpl testTarget;

    /**
     * テスト用の一時ディレクトリ
     *
     * @since 0.2.2
     */
    @TempDir
    private Path tempDir;

    /**
     * main メソッドのテスト - 正常系：パターン07<br>
     * <p>
     * 内部クラスのJavadocに「author」、「since」、「version」が設定された状態で、「author」は削除し、「since」と「version」はそのままであること。
     * </p>
     *
     * @since 0.2.2
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testMain_normalPt07(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

    /**
     * Javadocタグ設定ツールのテスト実行共通処理<br>
     *
     * @since 0.2.2
     *
     * @param testInfo
     *                         テスト情報
     * @param inputFileName
     *                         入力ファイル名
     * @param templateFileName
     *                         テンプレートファイル名
     * @param expectedFileName
     *                         期待値ファイル名
     *
     * @throws Exception
     *                   例外
     */
    private void executeJavadocTagSetterTest(final TestInfo testInfo, final String inputFileName,
        final String templateFileName, final String expectedFileName) throws Exception {

        /* 期待値の定義 */

        // テストメソッドパス
        final Path testMethodPath = this.getCurrentTestMethodPath(testInfo);

        // 期待値対象
        final Path   expectedTargetPath = testMethodPath.resolve(expectedFileName);
        final String expectedContent    = Files.readString(expectedTargetPath);

        /* 準備 */

        // テスト入力ファイルパス
        final Path testInputPath = testMethodPath.resolve(inputFileName);

        // テスト定義ファイルパス
        final Path testDefinitionPath = testMethodPath.resolve(templateFileName);

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
     * Javadocタグ設定ツールのテスト実行共通処理（デフォルトファイル使用）<br>
     *
     * @since 0.2.2
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    private void executeJavadocTagSetterTestWithDefaultFiles(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTest(testInfo, "TestInput.java", "TestTemplate.yml", "ExpectedTarget.java");

    }

}
