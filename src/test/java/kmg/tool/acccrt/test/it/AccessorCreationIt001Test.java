package kmg.tool.acccrt.test.it;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.acccrt.presentation.ul.cli.AccessorCreationTool;
import kmg.tool.io.presentation.ui.cli.AbstractIoTool;

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
     * テスト対象
     *
     * @since 0.1.0
     */
    @Autowired
    private AccessorCreationTool testTarget;

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

        // 各ファイルのパスを組み立て
        final Path testDir          = AccessorCreationIt001Test.TEST_RESOURCE_DIR.resolve("testMain_normal");
        final Path testInputPath    = testDir.resolve("TestInput.java");
        final Path testTemplatePath = testDir.resolve("TestTemplate.yml");
        final Path testOutputPath   = this.tempDir.resolve("TestOutput.java");

        // MockedStaticを使用してstaticメソッドをモック
        try (MockedStatic<AbstractIoTool> mockedStatic = Mockito.mockStatic(AbstractIoTool.class)) {

            // staticメソッドのモック設定
            mockedStatic.when(AbstractIoTool::getInputPath).thenReturn(testInputPath);
            mockedStatic.when(AbstractIoTool::getOutputPath).thenReturn(testOutputPath);

            // テンプレートパスを設定するために、AbstractTwo2OneToolのgetTemplatePathメソッドをモック
            final AccessorCreationTool spyTool = Mockito.spy(this.testTarget);
            Mockito.doReturn(testTemplatePath).when(spyTool).getTemplatePath();

            // TODO KenichiroArai 2025/08/20 testInputPathの中身を確認する
            final Path actualInputPath = AbstractIoTool.getInputPath();
            System.out.println("testInputPath: " + actualInputPath.toAbsolutePath());

            if (Files.exists(actualInputPath)) {

                final List<String> lines = Files.readAllLines(actualInputPath);
                System.out.println("==== testInputPath の内容 ====");

                for (final String line : lines) {

                    System.out.println(line);

                }
                System.out.println("==== ここまで ====");

            } else {

                System.out.println("testInputPathファイルが存在しません。");

            }

            // TODO KenichiroArai 2025/08/20 testTemplatePathの中身を確認する
            final Path actualTemplatePath = spyTool.getTemplatePath();
            System.out.println("testTemplatePath: " + actualTemplatePath.toAbsolutePath());

            if (Files.exists(actualTemplatePath)) {

                final List<String> lines = Files.readAllLines(actualTemplatePath);
                System.out.println("==== testTemplatePath の内容 ====");

                for (final String line : lines) {

                    System.out.println(line);

                }
                System.out.println("==== ここまで ====");

            } else {

                System.out.println("testTemplatePathファイルが存在しません。");

            }

            /* テスト対象の実行 */
            // TODO KenichiroArai 2025/08/20 実装中
            spyTool.run(testArgs);

        }

        /* 検証の準備 */

        /* 検証の実施 */

    }

}
