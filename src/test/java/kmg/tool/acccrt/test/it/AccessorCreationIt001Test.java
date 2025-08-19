package kmg.tool.acccrt.test.it;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
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

    /**
     * テスト用の一時ディレクトリ
     *
     * @since 0.1.0
     */
    @TempDir
    private Path tempDir;

    /**
     * 入力ファイルパス
     *
     * @since 0.1.0
     */
    private Path inputFilePath;

    /**
     * テンプレートファイルパス
     *
     * @since 0.1.0
     */
    private Path templateFilePath;

    /**
     * 各テスト実行前のセットアップ処理。テスト用ファイルの準備を行う。
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @BeforeEach
    public void setUp() throws Exception {

        /* テスト用ファイルの準備 */
        this.inputFilePath = this.tempDir.resolve("DtcDerivedPlaceholderModelImpl.java");
        this.templateFilePath = this.tempDir.resolve("template.yml");

    }

    /**
     * main メソッドの結合テスト - 正常系：実際のファイルを使用してツールが正常に動作する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMain_integrationSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String[] testArgs = {};

        /* テスト対象の実行 */
        AccessorCreationTool.main(testArgs);

        /* 検証の準備 */

        /* 検証の実施 */

    }

}
