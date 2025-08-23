package kmg.tool.jdts.test.it;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.utils.KmgPathUtils;
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
@ExtendWith(SpringExtension.class)
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
     * ファイルの内容を確認し、デバッグ情報を出力する
     *
     * @param filePath
     *                 確認対象のファイルパス
     *
     * @since 0.1.0
     */
    private static void verifyFileContent(final Path filePath) {

        System.out.println(String.format("FilePath: %s", filePath.toAbsolutePath()));

        if (!Files.exists(filePath)) {

            System.out.println("ファイルが存在しません。");
            return;

        }

        List<String> lines;

        try {

            lines = Files.readAllLines(filePath);

        } catch (final IOException e) {

            e.printStackTrace();
            return;

        }
        System.out.println("==== ファイルの内容 ====");

        for (final String line : lines) {

            System.out.println(line);

        }
        System.out.println("==== ここまで ====");

    }

    /**
     * main メソッドのテスト - 正常系
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMain_normal() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        final Path testResources = Path.of("src/test/resources");
        System.out.println("===== パス：開始 =====");
        System.out.println(testResources.toAbsolutePath().toString());
        final Path fqcnPath = KmgPathUtils.getFqcnPath(JavadocTagSetterIt001lTest.class);
        System.out.println(fqcnPath.toString());
        final Path testPath = testResources.resolve(fqcnPath);
        System.out.println(testPath.toAbsolutePath().toString());
        System.out.println("===== パス：終了 =====");

        final Path testInputPath      = testPath.resolve("test_main_normal/TestInput.java");
        final Path testDefinitionPath = testPath.resolve("test_main_normal/TestTemplate.yml");

        /* テスト対象の実行 */
        this.testTarget.initialize(testInputPath, testDefinitionPath);
        this.testTarget.process();

        /* 検証の準備 */

        /* 検証の実施 */

        // 実際のファイル内容を確認
        final Path testOutputPath = testInputPath;
        JavadocTagSetterIt001lTest.verifyFileContent(testOutputPath);

    }

}
