package kmg.tool.dtc.domain.logic.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.dtc.domain.types.DtcKeyTypes;

/**
 * DtcLogicImplのテストクラス
 *
 * @author AI
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls",
})
public class DtcLogicImplTest extends AbstractKmgTest {

    /** テンポラリディレクトリ */
    @TempDir
    private Path tempDir;

    /** テスト対象 */
    private DtcLogicImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** モックKMGメッセージソース */
    private KmgMessageSource mockMessageSource;

    /**
     * セットアップ
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @SuppressWarnings("resource")
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        final DtcLogicImpl dtcLogicImpl = new DtcLogicImpl();
        this.testTarget = dtcLogicImpl;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

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

            try {

                this.testTarget.close();

            } catch (final IOException e) {

                e.printStackTrace();

            }

        }

    }

    /**
     * addOutputBufferContent メソッドのテスト - 正常系：1件分の内容をバッファに追加
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testAddOutputBufferContent_normalAdd() throws Exception {

        /* 期待値の定義 */
        final String expectedContent = "testContent";

        /* 準備 */
        this.reflectionModel.set("contentsOfOneItem", expectedContent);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.addOutputBufferContent();

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final String  actualBuffer = this.reflectionModel.get("outputBufferContent").toString();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedContent, actualBuffer, "バッファ内容が正しいこと");

    }

    /**
     * applyTemplateToInputFile メソッドのテスト - 正常系：テンプレート適用
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testApplyTemplateToInputFile_normal() throws Exception {

        /* 期待値の定義 */
        final String expectedTemplate = "A${B}C${D}";
        final String expectedResult   = "A1C2";

        /* 準備 */
        this.reflectionModel.set("templateContent", expectedTemplate);
        this.reflectionModel.set("convertedLine", "1,2");
        this.reflectionModel.set("intermediateDelimiter", KmgDelimiterTypes.COMMA);
        @SuppressWarnings("unchecked")
        final Map<String, String> placeholderMap
            = (Map<String, String>) this.reflectionModel.get("intermediatePlaceholderMap");
        placeholderMap.clear();
        placeholderMap.put("B", "${B}");
        placeholderMap.put("D", "${D}");

        /* テスト対象の実行 */
        this.testTarget.applyTemplateToInputFile();

        /* 検証の準備 */
        final String actual = this.testTarget.getContentsOfOneItem();

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actual, "テンプレート適用結果が正しいこと");

    }

    /**
     * clearOutputBufferContent メソッドのテスト - 正常系：バッファクリア
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearOutputBufferContent_normalClear() throws Exception {

        /* 期待値の定義 */
        final String expected = KmgString.EMPTY;

        /* 準備 */
        this.reflectionModel.set("outputBufferContent", new StringBuilder("abc"));

        /* テスト対象の実行 */
        this.testTarget.clearOutputBufferContent();

        /* 検証の準備 */
        final String actual = this.reflectionModel.get("outputBufferContent").toString();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "バッファがクリアされていること");

    }

    /**
     * clearReadingData メソッドのテスト - 正常系：全データクリア
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClearReadingData_normalClear() throws Exception {

        /* 期待値の定義 */
        final String expectedBuffer   = KmgString.EMPTY;
        final int    expectedMapSize  = 0;
        final int    expectedListSize = 0;

        /* 準備 */
        this.reflectionModel.set("lineOfDataRead", "abc");
        this.reflectionModel.set("convertedLine", "def");
        this.reflectionModel.set("templateContent", "ghi");
        this.reflectionModel.set("contentsOfOneItem", "jkl");
        this.reflectionModel.set("outputBufferContent", new StringBuilder("mno"));
        @SuppressWarnings("unchecked")
        final Map<String, String> map = (Map<String, String>) this.reflectionModel.get("intermediatePlaceholderMap");
        map.clear();
        map.put("X", "Y");
        @SuppressWarnings("unchecked")
        final List<Object> list = (List<Object>) this.reflectionModel.get("derivedPlaceholders");
        list.clear();
        list.add(null);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.clearReadingData();

        /* 検証の準備 */
        final boolean actualResult    = testResult;
        final String  actualLine      = (String) this.reflectionModel.get("lineOfDataRead");
        final String  actualConverted = (String) this.reflectionModel.get("convertedLine");
        final String  actualTemplate  = (String) this.reflectionModel.get("templateContent");
        final String  actualContents  = (String) this.reflectionModel.get("contentsOfOneItem");
        final String  actualBuffer    = this.reflectionModel.get("outputBufferContent").toString();
        final int     actualMapSize   = ((Map<?, ?>) this.reflectionModel.get("intermediatePlaceholderMap")).size();
        final int     actualListSize  = ((List<?>) this.reflectionModel.get("derivedPlaceholders")).size();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertNull(actualLine, "lineOfDataReadがnullであること");
        Assertions.assertNull(actualConverted, "convertedLineがnullであること");
        Assertions.assertNull(actualTemplate, "templateContentがnullであること");
        Assertions.assertNull(actualContents, "contentsOfOneItemがnullであること");
        Assertions.assertEquals(expectedBuffer, actualBuffer, "outputBufferContentが空であること");
        Assertions.assertEquals(expectedMapSize, actualMapSize, "intermediatePlaceholderMapが空であること");
        Assertions.assertEquals(expectedListSize, actualListSize, "derivedPlaceholdersが空であること");

    }

    /**
     * close メソッドのテスト - 正常系：リソースをクローズする
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testClose_normalCloseResources() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

        /* テスト対象の実行 */
        this.testTarget.close();

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.testTarget.close(), "closeメソッドが正常に実行されること");

    }

    /**
     * closeReader メソッドのテスト - 正常系：リーダーをクローズする場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseReader_normalCloseReader() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeReader");

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("closeReader"), "リーダーが正常にクローズされること");

    }

    /**
     * closeReader メソッドのテスト - 正常系：リーダーがnullの場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseReader_normalReaderNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("reader", null);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeReader");

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("closeReader"), "リーダーがnullの場合は正常に処理されること");

    }

    /**
     * closeWriter メソッドのテスト - 正常系：ライターをクローズする場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseWriter_normalCloseWriter() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeWriter");

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("closeWriter"), "ライターが正常にクローズされること");

    }

    /**
     * closeWriter メソッドのテスト - 正常系：ライターがnullの場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCloseWriter_normalWriterNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("writer", null);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("closeWriter");

        /* 検証の準備 */

        /* 検証の実施 */
        // 例外が発生しないことを確認
        Assertions.assertDoesNotThrow(() -> this.reflectionModel.getMethod("closeWriter"), "ライターがnullの場合は正常に処理されること");

    }

    /**
     * getContentsOfOneItem メソッドのテスト - 正常系：内容取得
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetContentsOfOneItem_normal() throws KmgReflectionException {

        /* 期待値の定義 */
        final String expected = "abc";

        /* 準備 */
        this.reflectionModel.set("contentsOfOneItem", expected);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getContentsOfOneItem();

        /* 検証の準備 */
        final String actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "内容が正しいこと");

    }

    /**
     * getInputPath メソッドのテスト - 正常系：パス取得
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetInputPath_normal() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expected = this.tempDir.resolve("input.txt");

        /* 準備 */
        this.reflectionModel.set("inputPath", expected);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getInputPath();

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "inputPathが正しいこと");

    }

    /**
     * getOutputPath メソッドのテスト - 正常系：パス取得
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetOutputPath_normal() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expected = this.tempDir.resolve("output.txt");

        /* 準備 */
        this.reflectionModel.set("outputPath", expected);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getOutputPath();

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "outputPathが正しいこと");

    }

    /**
     * getTemplatePath メソッドのテスト - 正常系：パス取得
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetTemplatePath_normal() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expected = this.tempDir.resolve("test_template.txt");

        /* 準備 */
        this.reflectionModel.set("templatePath", expected);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getTemplatePath();

        /* 検証の準備 */
        final Path actual = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expected, actual, "templatePathが正しいこと");

    }

    /**
     * initialize メソッドのテスト - 正常系：初期化が成功する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalInitialization() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "test content".getBytes());
        Files.write(testTemplateFile, "template content".getBytes());

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が成功すること");

    }

    /**
     * initialize メソッドのテスト - 正常系：区切り文字指定で初期化が成功する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalWithDelimiter() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "test content".getBytes());
        Files.write(testTemplateFile, "template content".getBytes());

        /* テスト対象の実行 */
        final boolean testResult
            = this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile, KmgDelimiterTypes.COMMA);

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が成功すること");

    }

    /**
     * loadDerivedPlaceholderDefinitions メソッドのテスト - 正常系：派生プレースホルダー定義がある場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadDerivedPlaceholderDefinitions_normalHasDefinitions() throws Exception {

        /* 期待値の定義 */
        final boolean expected = true;

        /* 準備 */
        final Map<String, Object>       yamlData            = new HashMap<>();
        final List<Map<String, String>> derivedPlaceholders = new ArrayList<>();
        final Map<String, String>       placeholder         = new HashMap<>();
        placeholder.put(DtcKeyTypes.DISPLAY_NAME.getKey(), "testName");
        placeholder.put(DtcKeyTypes.REPLACEMENT_PATTERN.getKey(), "${TEST}");
        placeholder.put(DtcKeyTypes.SOURCE_KEY.getKey(), "sourceKey");
        placeholder.put(DtcKeyTypes.TRANSFORMATION.getKey(), "UPPER_CASE");
        derivedPlaceholders.add(placeholder);
        yamlData.put(DtcKeyTypes.DERIVED_PLACEHOLDERS.getKey(), derivedPlaceholders);

        /* テスト対象の実行 */
        final boolean testResult
            = (Boolean) this.reflectionModel.getMethod("loadDerivedPlaceholderDefinitions", yamlData);

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final int     actualSize   = ((List<?>) this.reflectionModel.get("derivedPlaceholders")).size();

        /* 検証の実施 */
        Assertions.assertEquals(expected, actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(1, actualSize, "派生プレースホルダーが追加されていること");

    }

    /**
     * loadDerivedPlaceholderDefinitions メソッドのテスト - 準正常系：派生プレースホルダー定義がない場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadDerivedPlaceholderDefinitions_semiNoDefinitions() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> yamlData = new HashMap<>();
        yamlData.put(DtcKeyTypes.DERIVED_PLACEHOLDERS.getKey(), null);

        /* テスト対象の実行 */
        final boolean testResult
            = (Boolean) this.reflectionModel.getMethod("loadDerivedPlaceholderDefinitions", yamlData);

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");

    }

    /**
     * loadIntermediatePlaceholderDefinitions メソッドのテスト - 正常系：中間プレースホルダー定義がある場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadIntermediatePlaceholderDefinitions_normalHasDefinitions() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object>       yamlData                 = new HashMap<>();
        final List<Map<String, String>> intermediatePlaceholders = new ArrayList<>();
        final Map<String, String>       placeholder              = new HashMap<>();
        placeholder.put(DtcKeyTypes.DISPLAY_NAME.getKey(), "testName");
        placeholder.put(DtcKeyTypes.REPLACEMENT_PATTERN.getKey(), "${TEST}");
        intermediatePlaceholders.add(placeholder);
        yamlData.put(DtcKeyTypes.INTERMEDIATE_PLACEHOLDERS.getKey(), intermediatePlaceholders);

        /* テスト対象の実行 */
        final boolean testResult
            = (Boolean) this.reflectionModel.getMethod("loadIntermediatePlaceholderDefinitions", yamlData);

        /* 検証の準備 */
        final boolean actualResult = testResult;
        final int     actualSize   = ((Map<?, ?>) this.reflectionModel.get("intermediatePlaceholderMap")).size();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(1, actualSize, "中間プレースホルダーが追加されていること");

    }

    /**
     * loadIntermediatePlaceholderDefinitions メソッドのテスト - 準正常系：中間プレースホルダー定義がない場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadIntermediatePlaceholderDefinitions_semiNoDefinitions() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> yamlData = new HashMap<>();
        yamlData.put(DtcKeyTypes.INTERMEDIATE_PLACEHOLDERS.getKey(), null);

        /* テスト対象の実行 */
        final boolean testResult
            = (Boolean) this.reflectionModel.getMethod("loadIntermediatePlaceholderDefinitions", yamlData);

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "戻り値が正しいこと");

    }

    /**
     * loadTemplate メソッドのテスト - 異常系：YAML読み込みエラーの場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadTemplate_errorYamlLoad() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN03000] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN03000;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            final Path testTemplateFile = this.tempDir.resolve("test_template.yml");
            this.reflectionModel.set("templatePath", testTemplateFile);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.loadTemplate();

            }, "YAML読み込みエラーの場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * loadTemplate メソッドのテスト - 正常系：テンプレート読み込みが成功する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadTemplate_normalLoad() throws Exception {

        /* 期待値の定義 */
        final String expectedTemplateContent = "template content";

        /* 準備 */
        final Path   testTemplateFile = this.tempDir.resolve("test_template.yml");
        final String yamlContent      = "templateContent: " + expectedTemplateContent + "\n";
        Files.write(testTemplateFile, yamlContent.getBytes());
        this.reflectionModel.set("templatePath", testTemplateFile);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.loadTemplate();

        /* 検証の準備 */
        final boolean actualResult          = testResult;
        final String  actualTemplateContent = (String) this.reflectionModel.get("templateContent");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "テンプレート読み込みが成功すること");
        Assertions.assertEquals(expectedTemplateContent, actualTemplateContent, "テンプレート内容が正しいこと");

    }

    /**
     * loadTemplateContent メソッドのテスト - 正常系：テンプレートコンテンツを読み込む場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadTemplateContent_normalLoad() throws Exception {

        /* 期待値の定義 */
        final String expectedContent = "test template content";

        /* 準備 */
        final Map<String, Object> yamlData = new HashMap<>();
        yamlData.put(DtcKeyTypes.TEMPLATE_CONTENT.getKey(), expectedContent);

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("loadTemplateContent", yamlData);

        /* 検証の準備 */
        final boolean actualResult  = testResult;
        final String  actualContent = (String) this.reflectionModel.get("templateContent");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "戻り値が正しいこと");
        Assertions.assertEquals(expectedContent, actualContent, "テンプレートコンテンツが正しく設定されていること");

    }

    /**
     * openInputFile メソッドのテスト - 異常系：入力ファイルが存在しない場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOpenInputFile_errorFileNotFound() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN03003] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN03003;
        final Class<?>           expectedCauseClass    = NoSuchFileException.class;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            final Path testInputFile = this.tempDir.resolve("nonexistent.txt");
            this.reflectionModel.set("inputPath", testInputFile);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("openInputFile");

            }, "入力ファイルが存在しない場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedCauseClass, expectedDomainMessage,
                expectedMessageTypes);

        }

    }

    /**
     * openInputFile メソッドのテスト - 正常系：入力ファイルを開く場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOpenInputFile_normalOpen() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile = this.tempDir.resolve("test_input.txt");
        Files.write(testInputFile, "test content".getBytes());
        this.reflectionModel.set("inputPath", testInputFile);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("openInputFile");

        /* 検証の準備 */
        final Object actualReader = this.reflectionModel.get("reader");

        /* 検証の実施 */
        Assertions.assertNotNull(actualReader, "リーダーが作成されていること");

    }

    /**
     * openOutputFile メソッドのテスト - 異常系：出力ファイルが作成できない場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOpenOutputFile_errorCannotCreate() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN03004] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN03004;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            // 権限のないディレクトリを指定（Windowsの場合）
            final Path testOutputFile = Path.of("/invalid/path/test_output.txt");
            this.reflectionModel.set("outputPath", testOutputFile);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("openOutputFile");

            }, "出力ファイルが作成できない場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * openOutputFile メソッドのテスト - 正常系：出力ファイルを開く場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOpenOutputFile_normalOpen() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testOutputFile = this.tempDir.resolve("test_output.txt");
        this.reflectionModel.set("outputPath", testOutputFile);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("openOutputFile");

        /* 検証の準備 */
        final Object actualWriter = this.reflectionModel.get("writer");

        /* 検証の実施 */
        Assertions.assertNotNull(actualWriter, "ライターが作成されていること");

    }

    /**
     * processDerivedPlaceholders メソッドのテスト - 正常系：派生プレースホルダーを処理する場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcessDerivedPlaceholders_normalProcess() throws Exception {

        /* 期待値の定義 */
        final String expectedResult = "ATESTC";

        /* 準備 */
        this.reflectionModel.set("contentsOfOneItem", "A${B}C");
        final Map<String, String> intermediateValues = new HashMap<>();
        intermediateValues.put("sourceKey", "test");
        @SuppressWarnings("unchecked")
        final List<Object> derivedPlaceholders = (List<Object>) this.reflectionModel.get("derivedPlaceholders");
        derivedPlaceholders.clear();
        // 実際のDtcDerivedPlaceholderModelImplインスタンスを作成
        final Object placeholder = new kmg.tool.dtc.domain.model.impl.DtcDerivedPlaceholderModelImpl("testName", "${B}",
            "sourceKey", kmg.tool.dtc.domain.types.DtcTransformTypes.TO_UPPER_CASE);
        derivedPlaceholders.add(placeholder);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processDerivedPlaceholders", intermediateValues);

        /* 検証の準備 */
        final String actual = (String) this.reflectionModel.get("contentsOfOneItem");

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actual, "派生プレースホルダーが正しく処理されること");

    }

    /**
     * processPlaceholders メソッドのテスト - 異常系：中間行の列数が不足している場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcessPlaceholders_errorInsufficientColumns() throws Exception {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[KMGTOOL_GEN03005] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.KMGTOOL_GEN03005;

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            /* 準備 */
            this.reflectionModel.set("contentsOfOneItem", "A${B}C${D}");
            this.reflectionModel.set("convertedLine", "1"); // 1列のみ
            this.reflectionModel.set("intermediateDelimiter", KmgDelimiterTypes.COMMA);
            this.reflectionModel.set("inputPath", this.tempDir.resolve("input.txt"));
            @SuppressWarnings("unchecked")
            final Map<String, String> placeholderMap
                = (Map<String, String>) this.reflectionModel.get("intermediatePlaceholderMap");
            placeholderMap.clear();
            placeholderMap.put("B", "${B}");
            placeholderMap.put("D", "${D}"); // 2列目が必要だが1列しかない
            final Map<String, String> intermediateValues = new HashMap<>();

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("processPlaceholders", intermediateValues);

            }, "中間行の列数が不足している場合は例外が発生すること");

            /* 検証の実施 */
            this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

        }

    }

    /**
     * processPlaceholders メソッドのテスト - 正常系：中間プレースホルダーを処理する場合（プライベートメソッド）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcessPlaceholders_normalProcess() throws Exception {

        /* 期待値の定義 */
        final String expectedResult = "A1C2";

        /* 準備 */
        this.reflectionModel.set("contentsOfOneItem", "A${B}C${D}");
        this.reflectionModel.set("convertedLine", "1,2");
        this.reflectionModel.set("intermediateDelimiter", KmgDelimiterTypes.COMMA);
        @SuppressWarnings("unchecked")
        final Map<String, String> placeholderMap
            = (Map<String, String>) this.reflectionModel.get("intermediatePlaceholderMap");
        placeholderMap.clear();
        placeholderMap.put("B", "${B}");
        placeholderMap.put("D", "${D}");
        final Map<String, String> intermediateValues = new HashMap<>();

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processPlaceholders", intermediateValues);

        /* 検証の準備 */
        final String actual = (String) this.reflectionModel.get("contentsOfOneItem");

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actual, "中間プレースホルダーが正しく処理されること");

    }

    /**
     * readOneLineOfData メソッドのテスト - 正常系：データが読み込める場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReadOneLineOfData_normalReadData() throws Exception {

        /* 期待値の定義 */
        final String expectedLineOfDataRead = "test line 1";

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "test line 1\ntest line 2".getBytes());
        this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.readOneLineOfData();

        /* 検証の準備 */
        final boolean actualResult         = testResult;
        final String  actualLineOfDataRead = (String) this.reflectionModel.get("lineOfDataRead");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "データが読み込めること");
        Assertions.assertEquals(expectedLineOfDataRead, actualLineOfDataRead, "読み込んだデータが正しいこと");

    }

    /**
     * readOneLineOfData メソッドのテスト - 準正常系：ファイル終端に達した場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReadOneLineOfData_semiEndOfFile() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "".getBytes());
        this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.readOneLineOfData();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "ファイル終端で正しく判定されること");

    }

    /**
     * writeOutputBuffer メソッドのテスト - 正常系：バッファ書き込みが成功する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testWriteOutputBuffer_normalWrite() throws Exception {

        /* 期待値の定義 */
        final String expectedContent = "test output content";

        /* 準備 */
        final Path testInputFile    = this.tempDir.resolve("test_input.txt");
        final Path testTemplateFile = this.tempDir.resolve("test_template.txt");
        final Path testOutputFile   = this.tempDir.resolve("test_output.tmp");
        Files.write(testInputFile, "test content".getBytes());
        this.testTarget.initialize(testInputFile, testTemplateFile, testOutputFile);
        this.reflectionModel.set("outputBufferContent", new StringBuilder(expectedContent));

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.writeOutputBuffer();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "バッファ書き込みが成功すること");
        Assertions.assertTrue(Files.exists(testOutputFile), "出力ファイルが作成されていること");

    }

}
