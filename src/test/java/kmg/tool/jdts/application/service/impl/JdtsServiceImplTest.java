package kmg.tool.jdts.application.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import kmg.core.infrastructure.exception.KmgReflectionException;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.fund.infrastructure.exception.KmgFundMsgException;
import kmg.fund.infrastructure.utils.KmgYamlUtils;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.jdts.application.logic.JdtsIoLogic;
import kmg.tool.jdts.application.model.JdtsCodeModel;
import kmg.tool.jdts.application.model.JdtsConfigsModel;
import kmg.tool.jdts.application.service.JdtsReplService;
import kmg.tool.jdts.application.types.JdtsConfigKeyTypes;

/**
 * JdtsServiceImplのテストクラス
 *
 * @author KenichiroArai
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls",
})
public class JdtsServiceImplTest extends AbstractKmgTest {

    /** テスト対象 */
    private JdtsServiceImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** モックKMGメッセージソース */
    private KmgMessageSource mockMessageSource;

    /** モックJdtsIoLogic */
    private JdtsIoLogic mockJdtsIoLogic;

    /** モックJdtsReplService */
    private JdtsReplService mockJdtsReplService;

    /** モックJdtsCodeModel */
    private JdtsCodeModel mockJdtsCodeModel;

    /** モックJdtsConfigsModel */
    private JdtsConfigsModel mockJdtsConfigsModel;

    /** テスト用対象パス */
    private Path testTargetPath;

    /** テスト用定義パス */
    private Path testDefinitionPath;

    /**
     * コンストラクタ メソッドのテスト - 正常系：カスタムロガーを使用した初期化
     */
    @Test
    public static void testConstructor_normalCustomLogger() {

        /* 期待値の定義 */

        /* 準備 */
        final Logger          mockLogger      = Mockito.mock(Logger.class);
        final JdtsServiceImpl testConstructor = new JdtsServiceImpl(mockLogger);

        /* テスト対象の実行 */

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "カスタムロガーを使用したコンストラクタが正常に初期化されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：標準ロガーを使用した初期化
     */
    @Test
    public static void testConstructor_normalStandardLogger() {

        /* 期待値の定義 */

        /* 準備 */
        final JdtsServiceImpl testConstructor = new JdtsServiceImpl();

        /* テスト対象の実行 */

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "コンストラクタが正常に初期化されること");

    }

    /**
     * 有効なYAMLデータを作成する
     *
     * @return 有効なYAMLデータ
     */
    private static Map<String, Object> createValidYamlData() {

        final Map<String, Object>       result  = new HashMap<>();
        final List<Map<String, Object>> configs = new ArrayList<>();

        // 有効なタグ設定を作成
        final Map<String, Object> tagConfig = new HashMap<>();
        tagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        tagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestAuthor");
        tagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        tagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        tagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        configs.add(tagConfig);
        result.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), configs);

        return result;

    }

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
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.mockJdtsIoLogic = Mockito.mock(JdtsIoLogic.class);
        this.mockJdtsReplService = Mockito.mock(JdtsReplService.class);
        this.mockJdtsCodeModel = Mockito.mock(JdtsCodeModel.class);
        this.mockJdtsConfigsModel = Mockito.mock(JdtsConfigsModel.class);

        /* モックの設定 */
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("jdtsIoLogic", this.mockJdtsIoLogic);
        this.reflectionModel.set("jdtsReplService", this.mockJdtsReplService);

        /* テスト用パスの設定 */
        this.testTargetPath = Paths.get("test/target");
        this.testDefinitionPath = Paths.get("test/definition.yml");

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
     * createJdtsConfigsModel メソッドのテスト - 異常系：YAMLファイル読み込みで例外が発生する場合
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgToolValException
     *                                KMGツールバリデーション例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testCreateJdtsConfigsModel_errorYamlLoadException()
        throws KmgToolMsgException, KmgToolValException, KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("definitionPath", this.testDefinitionPath);

        // SpringApplicationContextHelperのモック化
        try (final var mockStatic = Mockito.mockStatic(KmgYamlUtils.class);
            final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // 例外を事前に作成
            final KmgFundMsgException expectedException
                = new KmgFundMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN13003, new Object[] {
                    "test"
                });

            mockStatic.when(() -> KmgYamlUtils.load(ArgumentMatchers.any(Path.class))).thenThrow(expectedException);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("createJdtsConfigsModel");

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(testException, "KmgToolMsgExceptionが正しく発生すること");

        }

    }

    /**
     * createJdtsConfigsModel メソッドのテスト - 正常系：正常な構成モデル作成
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testCreateJdtsConfigsModel_normalCreateConfigsModel() throws Exception {

        /* 期待値の定義 */
        final Map<String, Object> yamlData = JdtsServiceImplTest.createValidYamlData();

        /* 準備 */
        this.reflectionModel.set("definitionPath", this.testDefinitionPath);

        try (final var mockStatic = Mockito.mockStatic(KmgYamlUtils.class)) {

            mockStatic.when(() -> KmgYamlUtils.load(ArgumentMatchers.any(Path.class))).thenReturn(yamlData);

            /* テスト対象の実行 */
            final boolean testResult = (Boolean) this.reflectionModel.getMethod("createJdtsConfigsModel");

            /* 検証の準備 */
            final JdtsConfigsModel actualConfigsModel = (JdtsConfigsModel) this.reflectionModel.get("jdtsConfigsModel");

            /* 検証の実施 */
            Assertions.assertTrue(testResult, "構成モデル作成が正常に完了すること");
            Assertions.assertNotNull(actualConfigsModel, "構成モデルが正しく作成されること");

        }

    }

    /**
     * getDefinitionPath メソッドのテスト - 正常系：定義ファイルパスの取得
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetDefinitionPath_normalGetDefinitionPath() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expectedResult = this.testDefinitionPath;

        /* 準備 */
        this.reflectionModel.set("definitionPath", this.testDefinitionPath);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getDefinitionPath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, testResult, "定義ファイルパスが正しく取得されること");

    }

    /**
     * getDefinitionPath メソッドのテスト - 準正常系：nullの定義ファイルパスの取得
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetDefinitionPath_semiNullDefinitionPath() throws KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("definitionPath", null);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getDefinitionPath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNull(testResult, "nullの定義ファイルパスが正しく取得されること");

    }

    /**
     * getTargetPath メソッドのテスト - 正常系：対象ファイルパスの取得
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetTargetPath_normalGetTargetPath() throws KmgReflectionException {

        /* 期待値の定義 */
        final Path expectedResult = this.testTargetPath;

        /* 準備 */
        this.reflectionModel.set("targetPath", this.testTargetPath);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getTargetPath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, testResult, "対象ファイルパスが正しく取得されること");

    }

    /**
     * getTargetPath メソッドのテスト - 準正常系：nullの対象ファイルパスの取得
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetTargetPath_semiNullTargetPath() throws KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("targetPath", null);

        /* テスト対象の実行 */
        final Path testResult = this.testTarget.getTargetPath();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNull(testResult, "nullの対象ファイルパスが正しく取得されること");

    }

    /**
     * initialize メソッドのテスト - 異常系：JdtsIoLogicの初期化で例外が発生する場合
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testInitialize_errorJdtsIoLogicException() throws KmgToolMsgException {

        /* 期待値の定義 */

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // 例外を事前に作成
            final KmgToolMsgException expectedException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN13001, new Object[] {
                    "test"
                });

            Mockito.when(this.mockJdtsIoLogic.initialize(ArgumentMatchers.any(Path.class)))
                .thenThrow(expectedException);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.initialize(this.testTargetPath, this.testDefinitionPath);

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(testException, "KmgToolMsgExceptionが正しく発生すること");

        }

    }

    /**
     * initialize メソッドのテスト - 正常系：正常な初期化
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInitialize_normalInitialization() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockJdtsIoLogic.initialize(ArgumentMatchers.any(Path.class))).thenReturn(true);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(this.testTargetPath, this.testDefinitionPath);

        /* 検証の準備 */
        final boolean actualResult         = testResult;
        final Path    actualTargetPath     = (Path) this.reflectionModel.get("targetPath");
        final Path    actualDefinitionPath = (Path) this.reflectionModel.get("definitionPath");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が正常に完了すること");
        Assertions.assertEquals(this.testTargetPath, actualTargetPath, "対象ファイルパスが正しく設定されること");
        Assertions.assertEquals(this.testDefinitionPath, actualDefinitionPath, "定義ファイルパスが正しく設定されること");
        Mockito.verify(this.mockJdtsIoLogic).initialize(this.testTargetPath);

    }

    /**
     * loadAndCreateCodeModel メソッドのテスト - 異常系：loadContentで例外が発生する場合
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgToolValException
     *                                KMGツールバリデーション例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testLoadAndCreateCodeModel_errorLoadContentException()
        throws KmgToolMsgException, KmgToolValException, KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // 例外を事前に作成
            final KmgToolMsgException expectedException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN13001, new Object[] {
                    "test"
                });

            Mockito.when(this.mockJdtsIoLogic.loadContent()).thenThrow(expectedException);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("loadAndCreateCodeModel");

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(testException, "KmgToolMsgExceptionが正しく発生すること");

        }

    }

    /**
     * loadAndCreateCodeModel メソッドのテスト - 正常系：正常なコードモデル作成
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testLoadAndCreateCodeModel_normalCreateCodeModel() throws Exception {

        /* 期待値の定義 */
        final String expectedContent = "public class TestClass {\n}";

        /* 準備 */
        Mockito.when(this.mockJdtsIoLogic.getReadContent()).thenReturn(expectedContent);

        /* テスト対象の実行 */
        final JdtsCodeModel testResult = (JdtsCodeModel) this.reflectionModel.getMethod("loadAndCreateCodeModel");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testResult, "コードモデルが正しく作成されること");
        Mockito.verify(this.mockJdtsIoLogic).loadContent();

    }

    /**
     * logFileEnd メソッドのテスト - 正常系：正常なファイル処理終了ログ出力
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testLogFileEnd_normalLogOutput() throws KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String expectedLogMessage = "test log message";

        /* 準備 */
        Mockito.when(this.mockJdtsIoLogic.getCurrentFilePath()).thenReturn(this.testTargetPath);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(expectedLogMessage);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("logFileEnd");

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "ファイル処理終了ログが正常に出力されること");

    }

    /**
     * logFileStart メソッドのテスト - 正常系：正常なファイル処理開始ログ出力
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testLogFileStart_normalLogOutput() throws KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String expectedLogMessage = "test log message";

        /* 準備 */
        Mockito.when(this.mockJdtsIoLogic.getCurrentFilePath()).thenReturn(this.testTargetPath);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(expectedLogMessage);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("logFileStart");

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "ファイル処理開始ログが正常に出力されること");

    }

    /**
     * process メソッドのテスト - 異常系：createJdtsConfigsModelで例外が発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorCreateJdtsConfigsModelException() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("definitionPath", this.testDefinitionPath);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");

        // SpringApplicationContextHelperのモック化
        try (final var mockStatic = Mockito.mockStatic(KmgYamlUtils.class);
            final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // 例外を事前に作成
            final KmgFundMsgException expectedException
                = new KmgFundMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN13003, new Object[] {
                    "test"
                });

            mockStatic.when(() -> KmgYamlUtils.load(ArgumentMatchers.any(Path.class))).thenThrow(expectedException);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(testException, "KmgToolMsgExceptionが正しく発生すること");

        }

    }

    /**
     * process メソッドのテスト - 異常系：loadで例外が発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorLoadException() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("definitionPath", this.testDefinitionPath);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");

        // SpringApplicationContextHelperのモック化
        try (final var mockStatic = Mockito.mockStatic(KmgYamlUtils.class);
            final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // 例外を事前に作成
            final KmgToolMsgException expectedException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN13002, new Object[] {
                    "test"
                });

            Mockito.when(this.mockJdtsIoLogic.load()).thenThrow(expectedException);

            final Map<String, Object> yamlData = JdtsServiceImplTest.createValidYamlData();
            mockStatic.when(() -> KmgYamlUtils.load(ArgumentMatchers.any(Path.class))).thenReturn(yamlData);

            /* テスト対象の実行 */
            final KmgToolMsgException actualException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(actualException, "KmgToolMsgExceptionが正しく発生すること");

        }

    }

    /**
     * process メソッドのテスト - 異常系：nextFileで例外が発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorNextFileException() throws Exception {

        /* 期待値の定義 */
        final List<Path> filePathList = new ArrayList<>();
        filePathList.add(this.testTargetPath);

        /* 準備 */
        this.reflectionModel.set("definitionPath", this.testDefinitionPath);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");
        Mockito.when(this.mockJdtsIoLogic.getFilePathList()).thenReturn(filePathList);
        Mockito.when(this.mockJdtsIoLogic.getCurrentFilePath()).thenReturn(this.testTargetPath);
        Mockito.when(this.mockJdtsIoLogic.getReadContent()).thenReturn("public class TestClass {\n}");
        Mockito.when(this.mockJdtsReplService.getTotalReplaceCount()).thenReturn(1L);
        Mockito.when(this.mockJdtsReplService.getReplaceCode()).thenReturn("replaced code");
        Mockito.when(this.mockJdtsReplService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);

        // SpringApplicationContextHelperのモック化
        try (final var mockStatic = Mockito.mockStatic(KmgYamlUtils.class);
            final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // 例外を事前に作成
            final KmgToolMsgException expectedException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN13001, new Object[] {
                    "test"
                });

            // nextFile()で例外を発生させる
            Mockito.when(this.mockJdtsIoLogic.nextFile()).thenThrow(expectedException);

            final Map<String, Object> yamlData = JdtsServiceImplTest.createValidYamlData();
            mockStatic.when(() -> KmgYamlUtils.load(ArgumentMatchers.any(Path.class))).thenReturn(yamlData);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(testException, "KmgToolMsgExceptionが正しく発生すること");

        }

    }

    /**
     * process メソッドのテスト - 異常系：processFileで例外が発生する場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_errorProcessFileException() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("definitionPath", this.testDefinitionPath);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");
        Mockito.when(this.mockJdtsIoLogic.getCurrentFilePath()).thenReturn(this.testTargetPath);

        // SpringApplicationContextHelperのモック化
        try (final var mockStatic = Mockito.mockStatic(KmgYamlUtils.class);
            final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // 例外を事前に作成
            final KmgToolMsgException expectedException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN13001, new Object[] {
                    "test"
                });

            Mockito.when(this.mockJdtsIoLogic.loadContent()).thenThrow(expectedException);

            final Map<String, Object> yamlData = JdtsServiceImplTest.createValidYamlData();
            mockStatic.when(() -> KmgYamlUtils.load(ArgumentMatchers.any(Path.class))).thenReturn(yamlData);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.testTarget.process();

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(testException, "KmgToolMsgExceptionが正しく発生すること");

        }

    }

    /**
     * process メソッドのテスト - 正常系：複数ファイルの処理（do-whileループ）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_normalMultipleFiles() throws Exception {

        /* 期待値の定義 */
        final List<Path> filePathList = new ArrayList<>();
        filePathList.add(this.testTargetPath);
        filePathList.add(Paths.get("test/target2"));
        filePathList.add(Paths.get("test/target3"));

        /* 準備 */
        this.reflectionModel.set("definitionPath", this.testDefinitionPath);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");
        Mockito.when(this.mockJdtsIoLogic.getFilePathList()).thenReturn(filePathList);
        Mockito.when(this.mockJdtsIoLogic.getCurrentFilePath()).thenReturn(this.testTargetPath);
        Mockito.when(this.mockJdtsIoLogic.getReadContent()).thenReturn("public class TestClass {\n}");
        Mockito.when(this.mockJdtsReplService.getTotalReplaceCount()).thenReturn(2L);
        Mockito.when(this.mockJdtsReplService.getReplaceCode()).thenReturn("replaced code");
        Mockito.when(this.mockJdtsReplService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);

        // nextFile()の呼び出し回数を制御するためのカウンター
        final int[] nextFileCallCount = {
            0
        };
        Mockito.when(this.mockJdtsIoLogic.nextFile()).thenAnswer(invocation -> {

            nextFileCallCount[0]++;
            // 2回目までtrueを返し、3回目でfalseを返す（3つのファイルを処理）
            return nextFileCallCount[0] < 3;

        });

        try (final var mockStatic = Mockito.mockStatic(KmgYamlUtils.class)) {

            final Map<String, Object> yamlData = JdtsServiceImplTest.createValidYamlData();
            mockStatic.when(() -> KmgYamlUtils.load(ArgumentMatchers.any(Path.class))).thenReturn(yamlData);

            /* テスト対象の実行 */
            final boolean testResult = this.testTarget.process();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertTrue(testResult, "複数ファイルの処理が正常に完了すること");
            // nextFile()が3回呼ばれることを確認（3つのファイルを処理するため）
            Mockito.verify(this.mockJdtsIoLogic, Mockito.times(3)).nextFile();
            // processFile()が3回呼ばれることを確認（3つのファイルを処理するため）
            Mockito.verify(this.mockJdtsIoLogic, Mockito.times(3)).loadContent();

        }

    }

    /**
     * process メソッドのテスト - 正常系：単一ファイルの処理（do-whileループ）
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_normalSingleFile() throws Exception {

        /* 期待値の定義 */
        final List<Path> filePathList = new ArrayList<>();
        filePathList.add(this.testTargetPath);

        /* 準備 */
        this.reflectionModel.set("definitionPath", this.testDefinitionPath);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");
        Mockito.when(this.mockJdtsIoLogic.getFilePathList()).thenReturn(filePathList);
        Mockito.when(this.mockJdtsIoLogic.getCurrentFilePath()).thenReturn(this.testTargetPath);
        Mockito.when(this.mockJdtsIoLogic.getReadContent()).thenReturn("public class TestClass {\n}");
        Mockito.when(this.mockJdtsReplService.getTotalReplaceCount()).thenReturn(1L);
        Mockito.when(this.mockJdtsReplService.getReplaceCode()).thenReturn("replaced code");
        Mockito.when(this.mockJdtsReplService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);

        // nextFile()は1回目でfalseを返す（単一ファイルのため）
        Mockito.when(this.mockJdtsIoLogic.nextFile()).thenReturn(false);

        try (final var mockStatic = Mockito.mockStatic(KmgYamlUtils.class)) {

            final Map<String, Object> yamlData = JdtsServiceImplTest.createValidYamlData();
            mockStatic.when(() -> KmgYamlUtils.load(ArgumentMatchers.any(Path.class))).thenReturn(yamlData);

            /* テスト対象の実行 */
            final boolean testResult = this.testTarget.process();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertTrue(testResult, "単一ファイルの処理が正常に完了すること");
            // nextFile()が1回呼ばれることを確認
            Mockito.verify(this.mockJdtsIoLogic, Mockito.times(1)).nextFile();
            // processFile()が1回呼ばれることを確認
            Mockito.verify(this.mockJdtsIoLogic, Mockito.times(1)).loadContent();

        }

    }

    /**
     * process メソッドのテスト - 準正常系：ファイルが存在しない場合
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcess_semiNoFiles() throws Exception {

        /* 期待値の定義 */
        final List<Path> emptyFilePathList = new ArrayList<>();

        /* 準備 */
        this.reflectionModel.set("definitionPath", this.testDefinitionPath);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");
        Mockito.when(this.mockJdtsIoLogic.getFilePathList()).thenReturn(emptyFilePathList);
        Mockito.when(this.mockJdtsIoLogic.getReadContent()).thenReturn("public class TestClass {\n}");

        try (final var mockStatic = Mockito.mockStatic(KmgYamlUtils.class)) {

            final Map<String, Object> yamlData = JdtsServiceImplTest.createValidYamlData();
            mockStatic.when(() -> KmgYamlUtils.load(ArgumentMatchers.any(Path.class))).thenReturn(yamlData);

            /* テスト対象の実行 */
            final boolean testResult = this.testTarget.process();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertTrue(testResult, "ファイルが存在しない場合でも処理が正常に完了すること");

        }

    }

    /**
     * processFile メソッドのテスト - 異常系：loadAndCreateCodeModelで例外が発生する場合
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgToolValException
     *                                KMGツールバリデーション例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testProcessFile_errorLoadAndCreateCodeModelException()
        throws KmgToolMsgException, KmgToolValException, KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockJdtsIoLogic.getCurrentFilePath()).thenReturn(this.testTargetPath);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");

        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // 例外を事前に作成
            final KmgToolMsgException expectedException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN13001, new Object[] {
                    "test"
                });

            Mockito.when(this.mockJdtsIoLogic.loadContent()).thenThrow(expectedException);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("processFile");

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(testException, "KmgToolMsgExceptionが正しく発生すること");

        }

    }

    /**
     * processFile メソッドのテスト - 正常系：正常なファイル処理
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testProcessFile_normalProcessFile() throws Exception {

        /* 期待値の定義 */
        final long expectedResult = 5L;

        /* 準備 */
        Mockito.when(this.mockJdtsIoLogic.getCurrentFilePath()).thenReturn(this.testTargetPath);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("test log message");
        Mockito.when(this.mockJdtsIoLogic.getReadContent()).thenReturn("public class TestClass {\n}");
        Mockito.when(this.mockJdtsReplService.getTotalReplaceCount()).thenReturn(expectedResult);
        Mockito.when(this.mockJdtsReplService.getReplaceCode()).thenReturn("replaced code");

        /* テスト対象の実行 */
        final long testResult = (Long) this.reflectionModel.getMethod("processFile");

        /* 検証の準備 */
        final long actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "ファイル処理が正常に完了すること");

    }

    /**
     * replaceJavadoc メソッドのテスト - 異常系：initializeで例外が発生する場合
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgToolValException
     *                                KMGツールバリデーション例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testReplaceJavadoc_errorInitializeException()
        throws KmgToolMsgException, KmgToolValException, KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);

        // SpringApplicationContextHelperのモック化
        try (final var mockSpringHelper = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource mockMessageSourceForException = Mockito.mock(KmgMessageSource.class);
            mockSpringHelper.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(mockMessageSourceForException);

            // モックメッセージソースの設定
            Mockito.when(mockMessageSourceForException.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テスト用の例外メッセージ");

            // 例外を事前に作成
            final KmgToolMsgException expectedException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN13001, new Object[] {
                    "test"
                });

            Mockito.when(this.mockJdtsReplService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenThrow(expectedException);

            /* テスト対象の実行 */
            final KmgToolMsgException testException = Assertions.assertThrows(KmgToolMsgException.class, () -> {

                this.reflectionModel.getMethod("replaceJavadoc", this.mockJdtsCodeModel);

            });

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertNotNull(testException, "KmgToolMsgExceptionが正しく発生すること");

        }

    }

    /**
     * replaceJavadoc メソッドのテスト - 正常系：正常なJavadoc置換
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testReplaceJavadoc_normalReplaceJavadoc() throws Exception {

        /* 期待値の定義 */
        final long   expectedResult         = 3L;
        final String expectedReplaceContent = "replaced javadoc content";

        /* 準備 */
        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);
        Mockito.when(this.mockJdtsReplService.getTotalReplaceCount()).thenReturn(expectedResult);
        Mockito.when(this.mockJdtsReplService.getReplaceCode()).thenReturn(expectedReplaceContent);

        /* テスト対象の実行 */
        final long testResult = (Long) this.reflectionModel.getMethod("replaceJavadoc", this.mockJdtsCodeModel);

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, testResult, "Javadoc置換が正常に完了すること");
        Mockito.verify(this.mockJdtsReplService).initialize(this.mockJdtsConfigsModel, this.mockJdtsCodeModel);
        Mockito.verify(this.mockJdtsReplService).replace();
        Mockito.verify(this.mockJdtsIoLogic).setWriteContent(expectedReplaceContent);
        Mockito.verify(this.mockJdtsIoLogic).writeContent();

    }

}
