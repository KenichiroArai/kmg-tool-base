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
     * ファイル出力の確認とデバッグ情報の出力を行う
     *
     * @param testOutputPath
     *                       確認対象の出力ファイルパス
     * @param tempDir
     *                       一時ディレクトリのパス
     *
     * @since 0.1.0
     */
    private static void verifyFileOutput(final Path testOutputPath, final Path tempDir) {

        System.out.println("OutputPath: " + testOutputPath.toAbsolutePath());

        if (Files.exists(testOutputPath)) {

            List<String> lines;

            try {

                lines = Files.readAllLines(testOutputPath);

            } catch (final IOException e) {

                e.printStackTrace();
                return;

            }
            System.out.println("==== testOutputPath の内容 ====");

            for (final String line : lines) {

                System.out.println(line);

            }
            System.out.println("==== ここまで ====");

        } else {

            System.out.println("testOutputPathファイルが存在しません。");

            // デバッグ情報を出力
            System.out.println("tempDir: " + tempDir.toAbsolutePath());
            System.out.println("tempDir exists: " + Files.exists(tempDir));
            System.out.println("tempDir contents:");

            try {

                Files.list(tempDir).forEach(file -> System.out.println("  " + file.getFileName()));

            } catch (final Exception e) {

                System.out.println("tempDir listing failed: " + e.getMessage());

            }

        }

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

        final Path testInputPath      = KmgPathUtils.getClassFullPath(JavadocTagSetterIt001lTest.class,
            "test_main_normal/TestInput.java");
        final Path testDefinitionPath = KmgPathUtils.getClassFullPath(JavadocTagSetterIt001lTest.class,
            "test_main_normal/TestTemplate.yml");

        /* テスト対象の実行 */
        this.testTarget.initialize(testInputPath, testDefinitionPath);
        this.testTarget.process();

        /* 検証の準備 */

        /* 検証の実施 */

        // 実際のファイル出力を確認
        final Path testOutputPath = testInputPath;
        JavadocTagSetterIt001lTest.verifyFileOutput(testOutputPath, this.tempDir);

    }

}
