package kmg.tool.jdts.test.it;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.jdts.application.service.impl.JdtsServiceImpl;

/**
 * Javadocタグ設定ツールの結合テスト001のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls",
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
    private JdtsServiceImpl testTarget;

    // /** リフレクションモデル */
    // private KmgReflectionModelImpl reflectionModel;
    //
    // /** モックKMGメッセージソース */
    // private KmgMessageSource mockMessageSource;

    /**
     * テスト用の一時ディレクトリ
     *
     * @since 0.1.0
     */
    @TempDir
    private Path tempDir;

    /**
     * セットアップ
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        final JdtsServiceImpl jdtsServiceImpl = new JdtsServiceImpl();
        this.testTarget = jdtsServiceImpl;
        // this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        //
        // /* モックの初期化 */
        // this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        //
        // /* モックの設定 */
        // this.reflectionModel.set("messageSource", this.mockMessageSource);

    }

    /**
     * クリーンアップ
     *
     * @throws Exception
     *                   例外
     */
    @AfterEach
    public void tearDown() throws Exception {

        if (this.testTarget != null) {

            // クリーンアップ処理
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

        // 各ファイルのパスを組み立て
        final Path testDir            = JavadocTagSetterIt001lTest.TEST_RESOURCE_DIR.resolve("testMain_normal");
        final Path testInputPath      = testDir.resolve("TestInput.java");
        final Path testDefinitionPath = testDir.resolve("TestDefinition.yml");

        /* テスト対象の実行 */
        this.testTarget.initialize(testInputPath, testDefinitionPath);
        this.testTarget.process();

        /* 検証の準備 */

        /* 検証の実施 */

        // 実際のファイル出力を確認
        final Path testOutputPath = testInputPath;
        System.out.println("OutputPath: " + testOutputPath.toAbsolutePath());

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

    }

}
