package kmg.tool.jdocr.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.jdocr.application.logic.JavadocLineRemoverLogic;

/**
 * Javadoc行削除サービス実装テスト<br>
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
    "nls", "static-method"
})
public class JavadocLineRemoverServiceImplTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private JavadocLineRemoverServiceImpl testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.1.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * モックKMGメッセージソース
     *
     * @since 0.1.0
     */
    private KmgMessageSource mockMessageSource;

    /**
     * モックJavadoc行削除ロジック
     *
     * @since 0.1.0
     */
    private JavadocLineRemoverLogic mockJavadocLineRemoverLogic;

    /**
     * モックロガー
     *
     * @since 0.1.0
     */
    private Logger mockLogger;

    /**
     * セットアップ
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @BeforeEach
    public void setUp() throws Exception {

        this.testTarget = new JavadocLineRemoverServiceImpl();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.mockJavadocLineRemoverLogic = Mockito.mock(JavadocLineRemoverLogic.class);
        this.mockLogger = Mockito.mock(Logger.class);

        /* モックをテスト対象に設定 */
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("javadocLineRemoverLogic", this.mockJavadocLineRemoverLogic);
        this.reflectionModel.set("logger", this.mockLogger);

    }

    /**
     * クリーンアップ
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @AfterEach
    public void tearDown() throws Exception {

        if (this.testTarget != null) {

            try {

                // 必要に応じてクリーンアップ処理を追加

            } catch (final Exception e) {

                e.printStackTrace();

            }

        }

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：カスタムロガーを使用するコンストラクタ
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCustomLogger() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Logger customLogger = LoggerFactory.getLogger("TestLogger");

        /* テスト対象の実行 */
        final JavadocLineRemoverServiceImpl testConstructor = new JavadocLineRemoverServiceImpl(customLogger);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが作成されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：デフォルトコンストラクタ
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefault() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final JavadocLineRemoverServiceImpl testConstructor = new JavadocLineRemoverServiceImpl();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "インスタンスが作成されること");

    }

    /**
     * initialize メソッドのテスト - 正常系：正常に初期化が完了する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final Path expectedInputPath = Paths.get("test.txt");

        /* 準備 */

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(expectedInputPath);

        /* 検証の準備 */
        final boolean actualResult    = testResult;
        final Path    actualInputPath = (Path) this.reflectionModel.get("inputPath");

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedInputPath, actualInputPath, "入力パスが正しく設定されること");

    }

    /**
     * initialize メソッドのテスト - 準正常系：nullパスが渡される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_semiNullPath() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(null);

        /* 検証の準備 */
        final boolean actualResult    = testResult;
        final Path    actualInputPath = (Path) this.reflectionModel.get("inputPath");

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");
        Assertions.assertNull(actualInputPath, "nullパスが正しく設定されること");

    }

    /**
     * inputPath フィールドのテスト - 正常系：入力パスが正しく設定される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInputPath_normalSet() throws Exception {

        /* 期待値の定義 */
        final Path expectedInputPath = Paths.get("test.txt");

        /* 準備 */
        final JavadocLineRemoverServiceImpl localTestTarget      = new JavadocLineRemoverServiceImpl();
        final KmgReflectionModelImpl        localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("inputPath", expectedInputPath);

        /* テスト対象の実行 */
        final Path actualInputPath = (Path) localReflectionModel.get("inputPath");

        /* 検証の準備 */
        final Path actualResult = actualInputPath;

        /* 検証の実施 */
        Assertions.assertEquals(expectedInputPath, actualResult, "入力パスが正しく設定されていること");

    }

    /**
     * javadocLineRemoverLogic フィールドのテスト - 正常系：Javadoc行削除ロジックが正しく注入される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testJavadocLineRemoverLogic_normalInjection() throws Exception {

        /* 期待値の定義 */
        final JavadocLineRemoverLogic expectedLogic = Mockito.mock(JavadocLineRemoverLogic.class);

        /* 準備 */
        final JavadocLineRemoverServiceImpl localTestTarget      = new JavadocLineRemoverServiceImpl();
        final KmgReflectionModelImpl        localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("javadocLineRemoverLogic", expectedLogic);

        /* テスト対象の実行 */
        final JavadocLineRemoverLogic actualLogic
            = (JavadocLineRemoverLogic) localReflectionModel.get("javadocLineRemoverLogic");

        /* 検証の準備 */
        final JavadocLineRemoverLogic actualResult = actualLogic;

        /* 検証の実施 */
        Assertions.assertEquals(expectedLogic, actualResult, "Javadoc行削除ロジックが正しく注入されていること");

    }

    /**
     * logger フィールドのテスト - 正常系：ロガーが正しく設定される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLogger_normalSet() throws Exception {

        /* 期待値の定義 */
        final Logger expectedLogger = LoggerFactory.getLogger("TestLogger");

        /* 準備 */
        final JavadocLineRemoverServiceImpl localTestTarget      = new JavadocLineRemoverServiceImpl(expectedLogger);
        final KmgReflectionModelImpl        localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        /* テスト対象の実行 */
        final Logger actualLogger = (Logger) localReflectionModel.get("logger");

        /* 検証の準備 */
        final Logger actualResult = actualLogger;

        /* 検証の実施 */
        Assertions.assertEquals(expectedLogger, actualResult, "ロガーが正しく設定されていること");

    }

    /**
     * messageSource フィールドのテスト - 正常系：メッセージソースが正しく注入される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMessageSource_normalInjection() throws Exception {

        /* 期待値の定義 */
        final KmgMessageSource expectedMessageSource = Mockito.mock(KmgMessageSource.class);

        /* 準備 */
        final JavadocLineRemoverServiceImpl localTestTarget      = new JavadocLineRemoverServiceImpl();
        final KmgReflectionModelImpl        localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", expectedMessageSource);

        /* テスト対象の実行 */
        final KmgMessageSource actualMessageSource = (KmgMessageSource) localReflectionModel.get("messageSource");

        /* 検証の準備 */
        final KmgMessageSource actualResult = actualMessageSource;

        /* 検証の実施 */
        Assertions.assertEquals(expectedMessageSource, actualResult, "メッセージソースが正しく注入されていること");

    }

    /**
     * process メソッドのテスト - 正常系：正常に処理が完了する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final int    expectedLineCount  = 3;
        final String expectedLogMessage = "テストメッセージ";

        /* 準備 */
        final Path                    testInputPath   = Paths.get("test.txt");
        final Map<Path, Set<Integer>> testInputMap    = new LinkedHashMap<>();
        final Set<Integer>            testLineNumbers = new LinkedHashSet<>();
        testLineNumbers.add(1);
        testLineNumbers.add(2);
        testLineNumbers.add(3);
        testInputMap.put(testInputPath, testLineNumbers);

        Mockito.when(this.mockJavadocLineRemoverLogic.getInputMap(ArgumentMatchers.any(Path.class)))
            .thenReturn(testInputMap);
        Mockito.when(this.mockJavadocLineRemoverLogic.deleteJavadocLines(ArgumentMatchers.any(Map.class)))
            .thenReturn(expectedLineCount);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.eq(KmgToolLogMsgTypes.KMGTOOL_LOG12000),
            ArgumentMatchers.any(Object[].class))).thenReturn(expectedLogMessage);

        // inputPathを設定
        this.reflectionModel.set("inputPath", testInputPath);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");

        // モックの呼び出しを検証
        Mockito.verify(this.mockJavadocLineRemoverLogic).getInputMap(testInputPath);
        Mockito.verify(this.mockJavadocLineRemoverLogic).deleteJavadocLines(testInputMap);
        Mockito.verify(this.mockMessageSource).getLogMessage(ArgumentMatchers.eq(KmgToolLogMsgTypes.KMGTOOL_LOG12000),
            ArgumentMatchers.any(Object[].class));
        Mockito.verify(this.mockLogger).debug(expectedLogMessage);

    }

    /**
     * process メソッドのテスト - 準正常系：空の入力マップが返される場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_semiEmptyInputMap() throws Exception {

        /* 期待値の定義 */
        final int    expectedLineCount  = 0;
        final String expectedLogMessage = "テストメッセージ";

        /* 準備 */
        final Path                    testInputPath = Paths.get("test.txt");
        final Map<Path, Set<Integer>> testInputMap  = new LinkedHashMap<>();

        Mockito.when(this.mockJavadocLineRemoverLogic.getInputMap(ArgumentMatchers.any(Path.class)))
            .thenReturn(testInputMap);
        Mockito.when(this.mockJavadocLineRemoverLogic.deleteJavadocLines(ArgumentMatchers.any(Map.class)))
            .thenReturn(expectedLineCount);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.eq(KmgToolLogMsgTypes.KMGTOOL_LOG12000),
            ArgumentMatchers.any(Object[].class))).thenReturn(expectedLogMessage);

        // inputPathを設定
        this.reflectionModel.set("inputPath", testInputPath);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");

        // モックの呼び出しを検証
        Mockito.verify(this.mockJavadocLineRemoverLogic).getInputMap(testInputPath);
        Mockito.verify(this.mockJavadocLineRemoverLogic).deleteJavadocLines(testInputMap);
        Mockito.verify(this.mockMessageSource).getLogMessage(ArgumentMatchers.eq(KmgToolLogMsgTypes.KMGTOOL_LOG12000),
            ArgumentMatchers.any(Object[].class));
        Mockito.verify(this.mockLogger).debug(expectedLogMessage);

    }

    /**
     * process メソッドのテスト - 準正常系：inputPathがnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_semiNullInputPath() throws Exception {

        /* 期待値の定義 */
        final int    expectedLineCount  = 0;
        final String expectedLogMessage = "テストメッセージ";

        /* 準備 */
        final Map<Path, Set<Integer>> testInputMap = new LinkedHashMap<>();

        Mockito.when(this.mockJavadocLineRemoverLogic.getInputMap(ArgumentMatchers.any(Path.class)))
            .thenReturn(testInputMap);
        Mockito.when(this.mockJavadocLineRemoverLogic.deleteJavadocLines(ArgumentMatchers.any(Map.class)))
            .thenReturn(expectedLineCount);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.eq(KmgToolLogMsgTypes.KMGTOOL_LOG12000),
            ArgumentMatchers.any(Object[].class))).thenReturn(expectedLogMessage);

        // inputPathをnullに設定
        this.reflectionModel.set("inputPath", null);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");

        // モックの呼び出しを検証
        Mockito.verify(this.mockJavadocLineRemoverLogic).getInputMap(null);
        Mockito.verify(this.mockJavadocLineRemoverLogic).deleteJavadocLines(testInputMap);
        Mockito.verify(this.mockMessageSource).getLogMessage(ArgumentMatchers.eq(KmgToolLogMsgTypes.KMGTOOL_LOG12000),
            ArgumentMatchers.any(Object[].class));
        Mockito.verify(this.mockLogger).debug(expectedLogMessage);

    }

    /**
     * process メソッドのテスト - 準正常系：削除行数が0の場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_semiZeroLineCount() throws Exception {

        /* 期待値の定義 */
        final int    expectedLineCount  = 0;
        final String expectedLogMessage = "テストメッセージ";

        /* 準備 */
        final Path                    testInputPath   = Paths.get("test.txt");
        final Map<Path, Set<Integer>> testInputMap    = new LinkedHashMap<>();
        final Set<Integer>            testLineNumbers = new LinkedHashSet<>();
        testInputMap.put(testInputPath, testLineNumbers);

        Mockito.when(this.mockJavadocLineRemoverLogic.getInputMap(ArgumentMatchers.any(Path.class)))
            .thenReturn(testInputMap);
        Mockito.when(this.mockJavadocLineRemoverLogic.deleteJavadocLines(ArgumentMatchers.any(Map.class)))
            .thenReturn(expectedLineCount);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.eq(KmgToolLogMsgTypes.KMGTOOL_LOG12000),
            ArgumentMatchers.any(Object[].class))).thenReturn(expectedLogMessage);

        // inputPathを設定
        this.reflectionModel.set("inputPath", testInputPath);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.process();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");

        // モックの呼び出しを検証
        Mockito.verify(this.mockJavadocLineRemoverLogic).getInputMap(testInputPath);
        Mockito.verify(this.mockJavadocLineRemoverLogic).deleteJavadocLines(testInputMap);
        Mockito.verify(this.mockMessageSource).getLogMessage(ArgumentMatchers.eq(KmgToolLogMsgTypes.KMGTOOL_LOG12000),
            ArgumentMatchers.any(Object[].class));
        Mockito.verify(this.mockLogger).debug(expectedLogMessage);

    }

}
