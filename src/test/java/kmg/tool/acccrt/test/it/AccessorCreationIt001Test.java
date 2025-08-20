package kmg.tool.acccrt.test.it;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.acccrt.presentation.ul.cli.AccessorCreationTool;

/**
 * アクセサ作成ツールの結合テスト001のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SpringBootTest(classes = AccessorCreationTool.class)
@TestPropertySource(properties = {
    "spring.main.web-application-type=none"
})
@SuppressWarnings({
    "nls", "static-method"
})
public class AccessorCreationIt001Test extends AbstractKmgTest {

    /** テストリソースディレクトリのルート */
    private static final Path TEST_RESOURCE_ROOT = Path.of("src", "test", "resources");

    /** テストクラスのリソースディレクトリ */
    private static final Path TEST_RESOURCE_DIR
        = AccessorCreationIt001Test.TEST_RESOURCE_ROOT.resolve(AccessorCreationIt001Test.class.getName());

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
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMain_normal() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String[] testArgs = {};

        // テストメソッド名
        final String testMethodName = "testMain_normal";
        // 各ファイルのパスを組み立て
        final Path testDir = AccessorCreationIt001Test.TEST_RESOURCE_DIR.resolve(testMethodName);

        final Path testInputPath        = testDir.resolve("TestInput.java");
        final Path testIntermediatePath = testDir.resolve("TestTemplate.yml");
        final Path testOutputPath       = null;

        // TODO KenichiroArai 2025/08/20 testInputPathの中身を確認する
        System.out.println("testInputPath: " + testInputPath.toAbsolutePath());

        if (Files.exists(testInputPath)) {

            final List<String> lines = Files.readAllLines(testInputPath);
            System.out.println("==== testInputPath の内容 ====");

            for (final String line : lines) {

                System.out.println(line);

            }
            System.out.println("==== ここまで ====");

        } else {

            System.out.println("testInputPathファイルが存在しません。");

        }

        // TODO KenichiroArai 2025/08/20 testIntermediatePathの中身を確認する
        System.out.println("testIntermediatePath: " + testIntermediatePath.toAbsolutePath());

        if (Files.exists(testIntermediatePath)) {

            final List<String> lines = Files.readAllLines(testIntermediatePath);
            System.out.println("==== testIntermediatePath の内容 ====");

            for (final String line : lines) {

                System.out.println(line);

            }
            System.out.println("==== ここまで ====");

        } else {

            System.out.println("testIntermediatePathファイルが存在しません。");

        }

        /* テスト対象の実行 */
        // TODO KenichiroArai 2025/08/20 実装中
        // AccessorCreationTool.main(testArgs);

        /* 検証の準備 */

        /* 検証の実施 */

    }

}
