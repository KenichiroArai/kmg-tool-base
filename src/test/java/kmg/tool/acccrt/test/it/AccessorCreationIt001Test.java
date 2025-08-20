package kmg.tool.acccrt.test.it;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
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
     * テスト対象
     *
     * @since 0.1.0
     */
    private AccessorCreationTool testTarget;

    /**
     * テスト用の一時ディレクトリ
     *
     * @since 0.1.0
     */
    @TempDir
    private Path tempDir;

    /**
     * 各テストメソッドの実行前に呼び出される初期化処理
     *
     * @since 0.1.0
     */
    @BeforeEach
    public void setUp() {

        // テスト対象のインスタンスを作成
        this.testTarget = new AccessorCreationTool();

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
        final String[] testArgs = {};

        // テスト用のパスを設定
        final AccessorCreationTool spyTool = this.setupTestPaths("testMain_normal");

        // TODO KenichiroArai 2025/08/20 testInputPathの中身を確認する
        final Path testInputPath = spyTool.getTestInputPath();
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

        // TODO KenichiroArai 2025/08/20 testTemplatePathの中身を確認する
        final Path testTemplatePath = spyTool.getTestTemplatePath();
        System.out.println("testTemplatePath: " + testTemplatePath.toAbsolutePath());

        if (Files.exists(testTemplatePath)) {

            final List<String> lines = Files.readAllLines(testTemplatePath);
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
        // AccessorCreationTool.main(testArgs);

        /* 検証の準備 */

        /* 検証の実施 */

    }

    /**
     * テスト用のパスを設定する
     *
     * @param testMethodName
     *                       テストメソッド名
     *
     * @return 設定されたテスト対象のインスタンス
     */
    private AccessorCreationTool setupTestPaths(final String testMethodName) {

        final AccessorCreationTool result;

        // 各ファイルのパスを組み立て
        final Path testDir = AccessorCreationIt001Test.TEST_RESOURCE_DIR.resolve(testMethodName);

        // テスト対象のファイルを設定
        final Path testInputPath    = testDir.resolve("TestInput.java");
        final Path testTemplatePath = testDir.resolve("TestTemplate.yml");
        final Path testOutputPath   = this.tempDir.resolve("TestOutput.java");

        // AccessorCreationToolをスパイしてテスト用のパスを設定
        result = Mockito.spy(this.testTarget);

        // getTestInputPathメソッドをスパイしてテスト用の入力パスを返すように設定
        Mockito.doReturn(testInputPath).when(result).getTestInputPath();

        // getTestOutputPathメソッドをスパイしてテスト用の出力パスを返すように設定
        Mockito.doReturn(testOutputPath).when(result).getTestOutputPath();

        // getTestTemplatePathメソッドをスパイしてテスト用のテンプレートパスを返すように設定
        Mockito.doReturn(testTemplatePath).when(result).getTestTemplatePath();

        return result;

    }

}
