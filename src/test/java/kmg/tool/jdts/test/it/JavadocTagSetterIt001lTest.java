package kmg.tool.jdts.test.it;

import java.nio.file.Path;

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
     * main メソッドのテスト - 正常系
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
    public void testMain_normal(final TestInfo testInfo) throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        // テストメソッドパス
        final Path testMethodPath = this.getCurrentTestMethodPath(testInfo);

        // テスト入力ファイルパス
        final Path testInputPath = testMethodPath.resolve("TestInput.java");

        // テスト定義ファイルパス
        final Path testDefinitionPath = testMethodPath.resolve("TestTemplate.yml");

        /* テスト対象の実行 */
        this.testTarget.initialize(testInputPath, testDefinitionPath);
        this.testTarget.process();

        /* 検証の準備 */

        /* 検証の実施 */

        // 実際のファイル内容を確認
        final Path testOutputPath = testInputPath;
        AbstractKmgTest.verifyFileContent(testOutputPath);

    }

}
