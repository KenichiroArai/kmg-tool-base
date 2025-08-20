package kmg.tool.jdts.test.it;

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
import kmg.tool.io.presentation.ui.cli.AbstractIoTool;
import kmg.tool.jdts.presentation.ui.cli.JavadocTagSetterTool;

/**
 * アクセサ作成ツールの結合テスト001のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SpringBootTest(classes = JavadocTagSetterIt001lTest.class)
@TestPropertySource(properties = {
    "spring.main.web-application-type=none"
})
@SuppressWarnings({
    "nls", "static-method"
})
public class JavadocTagSetterIt001lTest extends AbstractKmgTest {

    /** テストリソースディレクトリのルート */
    private static final Path TEST_RESOURCE_ROOT = Path.of("src", "test", "resources");

    /** テストクラスのリソースディレクトリ */
    private static final Path TEST_RESOURCE_DIR
        = JavadocTagSetterIt001lTest.TEST_RESOURCE_ROOT.resolve(JavadocTagSetterIt001lTest.class.getName());

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    @Autowired
    private JavadocTagSetterTool testTarget;

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
        final Path testDir            = JavadocTagSetterIt001lTest.TEST_RESOURCE_DIR.resolve("testMain_normal");
        final Path testInputPath      = testDir.resolve("TestInput.java");
        final Path testDefinitionPath = testDir.resolve("TestDefinition.yml");
        final Path testOutputPath     = this.tempDir.resolve("TestOutput.java");

        // 実際の出力パスを保存
        Path actualOutputPath = null;

        // MockedStaticを使用してstaticメソッドをモック
        try (MockedStatic<AbstractIoTool> mockedStatic = Mockito.mockStatic(AbstractIoTool.class)) {

            // staticメソッドのモック設定
            mockedStatic.when(AbstractIoTool::getInputPath).thenReturn(testInputPath);
            mockedStatic.when(AbstractIoTool::getOutputPath).thenReturn(testOutputPath);

            // 定義パスを設定するために、AbstractTwo2OneToolのgetDefinitionPathメソッドをモック
            final JavadocTagSetterTool spyTool = Mockito.spy(this.testTarget);
            Mockito.doReturn(testDefinitionPath).when(spyTool).getDefinitionPath();

            /* テスト対象の実行 */
            spyTool.run(testArgs);

            // モックされた出力パスを保存
            actualOutputPath = AbstractIoTool.getOutputPath();

        }

        /* 検証の準備 */

        // 実際のファイル出力を確認
        System.out.println("testOutputPath: " + testOutputPath);

        if (Files.exists(testOutputPath)) {

            final List<String> lines = Files.readAllLines(testOutputPath);
            System.out.println("==== testOutputPath の内容 ====");

            for (final String line : lines) {

                System.out.println(line);

            }
            System.out.println("==== ここまで ====");

        } else {

            System.out.println("testOutputPathファイルが存在しません。");

            // デバッグ情報を出力
            System.out.println("tempDir: " + this.tempDir.toAbsolutePath());
            System.out.println("tempDir exists: " + Files.exists(this.tempDir));
            System.out.println("tempDir contents:");

            try {

                Files.list(this.tempDir).forEach(file -> System.out.println("  " + file.getFileName()));

            } catch (final Exception e) {

                System.out.println("tempDir listing failed: " + e.getMessage());

            }

        }

        /* 検証の実施 */

    }

}
