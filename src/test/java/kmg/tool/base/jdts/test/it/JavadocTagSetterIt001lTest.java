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

/*
 * TODO KenichiroArai 2025/09/04 v0.1.1対応予定。
 * 結合テスト001の役割を明確にする。テンプレートごとにクラスを分ける。001は、パターン01～06のテンプレートは同じにする。002は、パターン06のテンプレートに関連したテストにする。
 */

/**
 * Javadocタグ設定ツールの結合テスト001のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@SpringBootTest(classes = JdtsServiceImpl.class)
@ActiveProfiles("test")
@SuppressWarnings({
    "nls",
})
public class JavadocTagSetterIt001lTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.2.0
     */
    @Autowired
    private JdtsServiceImpl testTarget;

    /**
     * テスト用の一時ディレクトリ
     *
     * @since 0.2.0
     */
    @TempDir
    private Path tempDir;

    // TODO KenichiroArai 2025/09/04 v0.1.1対応予定。テンプレートはクラスの共通にする。

    /**
     * main メソッドのテスト - 正常系：パターン01<br>
     * <p>
     * クラスのJavadocで、タグが一つもない場合に、タグを追加する。
     * </p>
     *
     * @since 0.2.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testMain_normalPt01(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

    /**
     * main メソッドのテスト - 正常系：パターン02<br>
     * <p>
     * テストメソッド内で文字列として「\/** XXXXX *\/」がある場合に、タグを追加しないことを確認する。
     * </p>
     *
     * @since 0.2.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMain_normalPt02(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

    /**
     * main メソッドのテスト - 正常系：パターン03<br>
     * <p>
     * 文字列として「\/** XXXXX *\/\\n～」がある場合に、タグを追加しないことを確認する。<br>
     * 「*\/」の後に改行がり続きの処理がある。
     * </p>
     *
     * @since 0.2.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testMain_normalPt03(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

    /**
     * main メソッドのテスト - 正常系：パターン04<br>
     * <p>
     * 文字列がテキストブロックで「"""<br>
     * \/** XXXXX *\/\<br>
     * ～<br>
     * """」がある場合に、タグを追加しないことを確認する。<br>
     * </p>
     *
     * @since 0.2.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testMain_normalPt04(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

    /**
     * main メソッドのテスト - 正常系：パターン05<br>
     * <p>
     * 文字列がテキストブロックで「"""<br>
     * \/** XXXXX *\/\<br>
     * ～<br>
     * """」がある場合に、タグを追加しないことを確認する。<br>
     * </p>
     *
     * @since 0.2.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testMain_normalPt05(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

    // TODO KenichiroArai 2025/09/04 v0.1.1対応予定。executeJavadocTagSetterTestメソッドを共通化する。

    /**
     * main メソッドのテスト - 正常系：パターン06<br>
     * <p>
     * 文字列がテキストブロックで「"""<br>
     * \/** XXXXX *\/\<br>
     * ～<br>
     * """」でかつ、Javadocタグもある場合に、タグを追加しないことを確認する。<br>
     * </p>
     *
     * @since 0.2.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testMain_normalPt06(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

    /**
     * main メソッドのテスト - 正常系：パターン07<br>
     * <p>
     * 内部クラスのJavadocに「author」、「since」、「version」が設定された状態で、「author」は削除し、「since」と「version」はそのままであること。
     * </p>
     *
     * @since 0.2.0
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

    // TODO KenichiroArai 2025/09/04 v0.1.1対応予定。executeJavadocTagSetterTestメソッドを共通化する。
    /**
     * Javadocタグ設定ツールのテスト実行共通処理<br>
     *
     * @since 0.2.0
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

    // TODO KenichiroArai 2025/09/04 v0.1.1対応予定。executeJavadocTagSetterTestWithDefaultFilesメソッドを共通化する。
    /**
     * Javadocタグ設定ツールのテスト実行共通処理（デフォルトファイル使用）<br>
     *
     * @since 0.2.0
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
