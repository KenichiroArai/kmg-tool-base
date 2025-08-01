package kmg.tool.input.domain.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;

/**
 * PlainContentInputServiceImplのテストクラス
 *
 * @author KenichiroArai
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class PlainContentInputServiceImplTest extends AbstractKmgTest {

    /** テンポラリディレクトリ */
    @TempDir
    private Path tempDir;

    /** テスト対象 */
    private PlainContentInputServiceImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * セットアップ
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        final PlainContentInputServiceImpl plainContentInputServiceImpl = new PlainContentInputServiceImpl();
        this.testTarget = plainContentInputServiceImpl;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

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
     * コンストラクタ メソッドのテスト - 正常系：デフォルトコンストラクタで初期化
     */
    @Test
    public void testConstructor_normalDefaultConstructor() {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final PlainContentInputServiceImpl testConstructor = new PlainContentInputServiceImpl();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが正常に作成されること");

    }

    /**
     * getContent メソッドのテスト - 正常系：入力内容を取得
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetContent_normalGetContent() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final String expectedContent = "テストコンテンツ";

        /* 準備 */
        this.reflectionModel.set("content", expectedContent);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getContent();

        /* 検証の準備 */
        final String actualContent = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedContent, actualContent, "入力内容が正しく取得されること");

    }

    /**
     * getContent メソッドのテスト - 準正常系：nullの入力内容を取得
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetContent_semiNullContent() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final String expectedContent = null;

        /* 準備 */
        this.reflectionModel.set("content", null);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getContent();

        /* 検証の準備 */
        final String actualContent = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedContent, actualContent, "nullの入力内容が正しく取得されること");

    }

    /**
     * process メソッドのテスト - 異常系：IOException発生
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testProcess_errorIOException() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN08002] テストメッセージ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN08002;
        final Class<?>           expectedCauseClass    = IOException.class;

        /* 準備 */
        final Path inputPath = this.tempDir.resolve("non_existent_file.txt");

        // リフレクションを使用してinputPathを直接設定
        this.reflectionModel.set("inputPath", inputPath);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceTestMethod = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceTestMethod);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceTestMethod.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * process メソッドのテスト - 正常系：空ファイルの読み込み
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws IOException
     *                             入出力例外
     */
    @Test
    public void testProcess_normalEmptyFileRead() throws KmgToolMsgException, IOException {

        /* 期待値の定義 */
        final boolean expectedResult  = true;
        final String  expectedContent = "";

        /* 準備 */
        final Path inputPath = this.tempDir.resolve("empty_input.txt");
        Files.writeString(inputPath, expectedContent);

        this.testTarget.initialize(inputPath);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult  = testResult;
        final String  actualContent = this.testTarget.getContent();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "処理が成功すること");
        Assertions.assertEquals(expectedContent, actualContent, "空ファイルの内容が正しく読み込まれること");

    }

    /**
     * process メソッドのテスト - 正常系：正常なファイル読み込み処理
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws IOException
     *                             入出力例外
     */
    @Test
    public void testProcess_normalFileRead() throws KmgToolMsgException, IOException {

        /* 期待値の定義 */
        final boolean expectedResult  = true;
        final String  expectedContent = "テストファイルの内容";

        /* 準備 */
        final Path inputPath = this.tempDir.resolve("test_input.txt");
        Files.writeString(inputPath, expectedContent);

        this.testTarget.initialize(inputPath);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult  = testResult;
        final String  actualContent = this.testTarget.getContent();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "処理が成功すること");
        Assertions.assertEquals(expectedContent, actualContent, "ファイルの内容が正しく読み込まれること");

    }

    /**
     * process メソッドのテスト - 正常系：大きなファイルの読み込み
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws IOException
     *                             入出力例外
     */
    @Test
    public void testProcess_normalLargeFileRead() throws KmgToolMsgException, IOException {

        /* 期待値の定義 */
        final boolean       expectedResult         = true;
        final StringBuilder expectedContentBuilder = new StringBuilder();

        for (int i = 0; i < 1000; i++) {

            expectedContentBuilder.append("行").append(i).append("の内容\n");

        }
        final String expectedContent = expectedContentBuilder.toString();

        /* 準備 */
        final Path inputPath = this.tempDir.resolve("large_input.txt");
        Files.writeString(inputPath, expectedContent);

        this.testTarget.initialize(inputPath);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult  = testResult;
        final String  actualContent = this.testTarget.getContent();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "処理が成功すること");
        Assertions.assertEquals(expectedContent, actualContent, "大きなファイルの内容が正しく読み込まれること");

    }

}
