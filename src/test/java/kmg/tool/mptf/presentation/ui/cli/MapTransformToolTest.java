package kmg.tool.mptf.presentation.ui.cli;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.model.val.impl.KmgValDataModelImpl;
import kmg.core.infrastructure.model.val.impl.KmgValsModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.types.msg.KmgCoreValMsgTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.input.domain.service.PlainContentInputServic;
import kmg.tool.input.presentation.ui.cli.AbstractPlainContentInputTool;
import kmg.tool.mptf.application.service.MapTransformService;

/**
 * マッピング変換ツールのテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class MapTransformToolTest extends AbstractKmgTest {

    /** テスト対象 */
    @InjectMocks
    private MapTransformTool testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** KmgMessageSourceのモック */
    @Mock
    private KmgMessageSource mockMessageSource;

    /** PlainContentInputServicのモック */
    @Mock
    private PlainContentInputServic mockInputService;

    /** MapTransformServiceのモック */
    @Mock
    private MapTransformService mockMapTransformService;

    /**
     * 各テスト実行前のセットアップ処理。リフレクションモデルの初期化とモックの注入を行う。
     *
     * @throws Exception
     *                   例外
     */
    @BeforeEach
    public void setUp() throws Exception {

        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("inputService", this.mockInputService);
        this.reflectionModel.set("mapTransformService", this.mockMapTransformService);

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：デフォルトコンストラクタが正常に動作する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalDefault() throws Exception {

        /* 期待値の定義 */
        final String expectedToolName = "マッピング変換ツール";

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();

        /* テスト対象の実行 */
        // コンストラクタの実行は準備で完了

        /* 検証の準備 */
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        final String                 actualToolName       = (String) localReflectionModel.get("TOOL_NAME");
        @SuppressWarnings("unchecked")
        final Map<String, String>    actualMapping        = (Map<String, String>) localReflectionModel.get("mapping");

        /* 検証の実施 */
        Assertions.assertEquals(expectedToolName, actualToolName, "ツール名が正しく設定されていること");
        Assertions.assertNotNull(actualMapping, "マッピングが初期化されていること");
        Assertions.assertTrue(actualMapping instanceof HashMap, "マッピングがHashMap型であること");

    }

    /**
     * execute メソッドのテスト - 異常系：一般例外が発生する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_errorException() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final MapTransformTool       localTestTarget      = Mockito.spy(new MapTransformTool());
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("mapTransformService", this.mockMapTransformService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        // KmgToolMsgExceptionの作成はMockedStaticのスコープ内で行う
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource localMockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(localMockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(localMockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("[KMGTOOL_GEN19002] テストメッセージ");

            // KmgToolMsgExceptionを作成（モックのスコープ内で）
            final KmgToolMsgException testException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN19002, new Object[] {});
            Mockito.when(localTestTarget.getInputService().getContent()).thenThrow(testException);

            /* テスト対象の実行 */
            final boolean actualResult = localTestTarget.execute();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertFalse(actualResult, "例外が発生した場合、falseが返されること");

        }

    }

    /**
     * execute メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_errorKmgToolMsgException() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("mapTransformService", this.mockMapTransformService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path\noldValue,newValue");
        Mockito.when(this.mockMapTransformService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);

        // SpringApplicationContextHelperのモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            final KmgMessageSource localMockMessageSource = Mockito.mock(KmgMessageSource.class);
            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(localMockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(localMockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("[KMGTOOL_GEN19002] テストメッセージ");

            // KmgToolMsgExceptionを作成（モックのスコープ内で）
            final KmgToolMsgException testException
                = new KmgToolMsgException(KmgToolGenMsgTypes.KMGTOOL_GEN19002, new Object[] {});
            Mockito.when(this.mockMapTransformService.process()).thenThrow(testException);
            Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn("テストメッセージ");

            /* テスト対象の実行 */
            final boolean actualResult = localTestTarget.execute();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertFalse(actualResult, "KmgToolMsgExceptionが発生した場合、falseが返されること");

        }

    }

    /**
     * execute メソッドのテスト - 異常系：KmgToolValExceptionが発生する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_errorKmgToolValException() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("mapTransformService", this.mockMapTransformService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path\noldValue,newValue");
        Mockito.when(this.mockMapTransformService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);

        // 例外を事前に作成
        final KmgToolValException testException = new KmgToolValException(new KmgValsModelImpl());
        Mockito.when(this.mockMapTransformService.process()).thenThrow(testException);
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "KmgToolValExceptionが発生した場合、falseが返されること");

    }

    /**
     * execute メソッドのテスト - 異常系：KmgToolValExceptionのバリデーションリストが1件以上の場合
     *
     * @since 1.0.1
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_errorKmgToolValException_withValidationData() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("mapTransformService", this.mockMapTransformService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path\noldValue,newValue");
        Mockito.when(this.mockMapTransformService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);

        // バリデーションモデルを作成
        final KmgValsModelImpl    validationModel = new KmgValsModelImpl();
        final KmgValDataModelImpl validationData  = new KmgValDataModelImpl(KmgCoreValMsgTypes.NONE, new Object[] {});
        validationModel.addData(validationData);

        // 例外を事前に作成
        final KmgToolValException testException = new KmgToolValException(validationModel);
        Mockito.when(this.mockMapTransformService.process()).thenThrow(testException);
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "KmgToolValExceptionが発生した場合、falseが返されること");

    }

    /**
     * execute メソッドのテスト - 正常系：正常に処理が完了する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("mapTransformService", this.mockMapTransformService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path\noldValue,newValue");
        Mockito.when(this.mockMapTransformService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockMapTransformService.process()).thenReturn(true);
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "正常に処理が完了すること");

    }

    /**
     * execute メソッドのテスト - 準正常系：fromInputFileがfalseを返す場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_semiFromInputFileFailure() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path"); // 1行のみ
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "fromInputFileがfalseを返す場合、falseが返されること");

    }

    /**
     * execute メソッドのテスト - 準正常系：MapTransformServiceの初期化に失敗する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_semiMapTransformServiceInitFailure() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("mapTransformService", this.mockMapTransformService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path\noldValue,newValue");
        Mockito.when(this.mockMapTransformService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(false);
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "MapTransformServiceの初期化に失敗した場合、falseが返されること");

    }

    /**
     * execute メソッドのテスト - 準正常系：MapTransformServiceの処理に失敗する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testExecute_semiMapTransformServiceProcessFailure() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        localReflectionModel.set("mapTransformService", this.mockMapTransformService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path\noldValue,newValue");
        Mockito.when(this.mockMapTransformService.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockMapTransformService.process()).thenReturn(false);
        Mockito.when(this.mockMessageSource.getGenMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn("テストメッセージ");

        /* テスト対象の実行 */
        final boolean actualResult = localTestTarget.execute();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "MapTransformServiceの処理に失敗した場合、falseが返されること");

    }

    /**
     * fromInputFile メソッドのテスト - 正常系：入力ファイルから設定が正常に完了する場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testFromInputFile_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final Path expectedTargetPath = Paths.get("test/path");

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path\noldValue,newValue");

        /* テスト対象の実行 */
        final boolean actualResult = (Boolean) localReflectionModel.getMethod("fromInputFile");

        /* 検証の準備 */
        final Path                actualTargetPath = (Path) localReflectionModel.get("targetPath");
        @SuppressWarnings("unchecked")
        final Map<String, String> actualMapping    = (Map<String, String>) localReflectionModel.get("mapping");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "入力ファイルから設定が正常に完了すること");
        Assertions.assertEquals(expectedTargetPath, actualTargetPath, "対象パスが正しく設定されること");
        Assertions.assertEquals("newValue", actualMapping.get("oldValue"), "マッピングが正しく設定されること");

    }

    /**
     * fromInputFile メソッドのテスト - 準正常系：空行が含まれる場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testFromInputFile_semiEmptyLine() throws Exception {

        /* 期待値の定義 */
        final Path expectedTargetPath = Paths.get("test/path");

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path\n\noldValue,newValue");

        /* テスト対象の実行 */
        final boolean actualResult = (Boolean) localReflectionModel.getMethod("fromInputFile");

        /* 検証の準備 */
        final Path                actualTargetPath = (Path) localReflectionModel.get("targetPath");
        @SuppressWarnings("unchecked")
        final Map<String, String> actualMapping    = (Map<String, String>) localReflectionModel.get("mapping");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "空行が含まれても正常に処理されること");
        Assertions.assertEquals(expectedTargetPath, actualTargetPath, "対象パスが正しく設定されること");
        Assertions.assertEquals("newValue", actualMapping.get("oldValue"), "マッピングが正しく設定されること");

    }

    /**
     * fromInputFile メソッドのテスト - 準正常系：カンマ区切りが2つ未満の場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testFromInputFile_semiLessThanTwoCommas() throws Exception {

        /* 期待値の定義 */
        final Path expectedTargetPath = Paths.get("test/path");

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path\noldValue"); // カンマなし

        /* テスト対象の実行 */
        final boolean actualResult = (Boolean) localReflectionModel.getMethod("fromInputFile");

        /* 検証の準備 */
        final Path                actualTargetPath = (Path) localReflectionModel.get("targetPath");
        @SuppressWarnings("unchecked")
        final Map<String, String> actualMapping    = (Map<String, String>) localReflectionModel.get("mapping");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "カンマ区切りが2つ未満でも正常に処理されること");
        Assertions.assertEquals(expectedTargetPath, actualTargetPath, "対象パスが正しく設定されること");
        Assertions.assertTrue(actualMapping.isEmpty(), "マッピングが空のままであること");

    }

    /**
     * fromInputFile メソッドのテスト - 準正常系：コンテンツが2行未満の場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testFromInputFile_semiLessThanTwoLines() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", this.mockMessageSource);
        localReflectionModel.set("inputService", this.mockInputService);
        Mockito.when(this.mockInputService.initialize(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(this.mockInputService.process()).thenReturn(true);
        Mockito.when(this.mockInputService.getContent()).thenReturn("test/path"); // 1行のみ

        /* テスト対象の実行 */
        final boolean actualResult = (Boolean) localReflectionModel.getMethod("fromInputFile");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "コンテンツが2行未満の場合、falseが返されること");

    }

    /**
     * getInputService メソッドのテスト - 正常系：プレーンコンテンツ入力サービスが正常に返される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testGetInputService_normalSuccess() throws Exception {

        /* 期待値の定義 */
        final PlainContentInputServic expectedInputService = Mockito.mock(PlainContentInputServic.class);

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("inputService", expectedInputService);

        /* テスト対象の実行 */
        final PlainContentInputServic actualInputService = localTestTarget.getInputService();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedInputService, actualInputService, "プレーンコンテンツ入力サービスが正しく返されること");

    }

    /**
     * メソッドの戻り値型テスト - 正常系：getInputServiceメソッドがPlainContentInputServicを返す場合
     *
     * @since 1.0.0
     */
    @Test
    public void testGetInputServiceReturnType_normalPlainContentInputServic() {

        /* 期待値の定義 */
        final Class<?> expectedReturnType = PlainContentInputServic.class;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Method   method           = testClass.getDeclaredMethod("getInputService");
            final Class<?> actualReturnType = method.getReturnType();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertSame(expectedReturnType, actualReturnType,
                "getInputServiceメソッドがPlainContentInputServicを返すこと");

        } catch (final NoSuchMethodException e) {

            Assertions.fail("getInputServiceメソッドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * メソッドの可視性テスト - 正常系：getInputServiceメソッドがpublicで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testGetInputServiceVisibility_normalPublic() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PUBLIC;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Method method          = testClass.getDeclaredMethod("getInputService");
            final int    actualModifiers = method.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PUBLIC;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "getInputServiceメソッドがpublicで定義されていること");

        } catch (final NoSuchMethodException e) {

            Assertions.fail("getInputServiceメソッドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * 継承関係のテスト - 正常系：AbstractPlainContentInputToolを正しく継承している場合
     *
     * @since 1.0.0
     */
    @Test
    public void testInheritance_normalExtendsAbstractPlainContentInputTool() {

        /* 期待値の定義 */
        final Class<?> expectedSuperClass = AbstractPlainContentInputTool.class;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();

        /* テスト対象の実行 */
        final Class<?> actualSuperClass = localTestTarget.getClass().getSuperclass();

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertSame(expectedSuperClass, actualSuperClass, "AbstractPlainContentInputToolを正しく継承していること");

    }

    /**
     * inputService フィールドのテスト - 正常系：プレーンコンテンツ入力サービスが正しく注入される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testInputService_normalInjection() throws Exception {

        /* 期待値の定義 */
        final PlainContentInputServic expectedInputService = Mockito.mock(PlainContentInputServic.class);

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("inputService", expectedInputService);

        /* テスト対象の実行 */
        final PlainContentInputServic actualInputService
            = (PlainContentInputServic) localReflectionModel.get("inputService");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedInputService, actualInputService, "プレーンコンテンツ入力サービスが正しく注入されていること");

    }

    /**
     * フィールドの型テスト - 正常系：inputServiceフィールドがPlainContentInputServic型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testInputServiceType_normalPlainContentInputServic() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = PlainContentInputServic.class;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("inputService");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertSame(expectedFieldType, actualFieldType,
                "inputServiceフィールドがPlainContentInputServic型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("inputServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * フィールドの可視性テスト - 正常系：inputServiceフィールドがprivateで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testInputServiceVisibility_normalPrivate() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("inputService");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PRIVATE;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "inputServiceフィールドがprivateで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("inputServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * main メソッドのテスト - 正常系：メインメソッドが正常に実行される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @SuppressWarnings("resource")
    @Test
    public void testMain_normalSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // SpringApplicationをモック化
        try (final MockedStatic<SpringApplication> mockedSpringApplication
            = Mockito.mockStatic(SpringApplication.class)) {

            // ConfigurableApplicationContextをモック化
            final ConfigurableApplicationContext mockContext = Mockito.mock(ConfigurableApplicationContext.class);
            final MapTransformTool               mockTool    = Mockito.mock(MapTransformTool.class);

            // SpringApplication.runの戻り値をモック化
            mockedSpringApplication.when(() -> SpringApplication.run(MapTransformTool.class)).thenReturn(mockContext);

            // ctx.getBeanの戻り値をモック化
            Mockito.when(mockContext.getBean(MapTransformTool.class)).thenReturn(mockTool);

            // executeメソッドの戻り値をモック化
            Mockito.when(mockTool.execute()).thenReturn(true);

            /* テスト対象の実行 */
            // mainメソッドを実際に呼び出し
            MapTransformTool.main(new String[] {});

            /* 検証の準備 */

            /* 検証の実施 */
            // mainメソッドが例外を投げずに正常に実行されることを確認
            Assertions.assertTrue(true, "mainメソッドが正常に実行されること");

            // モックが正しく呼び出されたことを検証
            Mockito.verify(mockContext).getBean(MapTransformTool.class);
            Mockito.verify(mockTool).execute();
            Mockito.verify(mockContext).close();

        }

    }

    /**
     * main メソッドのテスト - 準正常系：引数がnullの場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @SuppressWarnings("resource")
    @Test
    public void testMain_semiNullArgs() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // SpringApplicationをモック化
        try (final MockedStatic<SpringApplication> mockedSpringApplication
            = Mockito.mockStatic(SpringApplication.class)) {

            // ConfigurableApplicationContextをモック化
            final ConfigurableApplicationContext mockContext = Mockito.mock(ConfigurableApplicationContext.class);
            final MapTransformTool               mockTool    = Mockito.mock(MapTransformTool.class);

            // SpringApplication.runの戻り値をモック化（null引数）
            mockedSpringApplication.when(() -> SpringApplication.run(MapTransformTool.class, (String[]) null))
                .thenReturn(mockContext);

            // ctx.getBeanの戻り値をモック化
            Mockito.when(mockContext.getBean(MapTransformTool.class)).thenReturn(mockTool);

            // executeメソッドの戻り値をモック化
            Mockito.when(mockTool.execute()).thenReturn(true);

            /* テスト対象の実行 */
            // mainメソッドを実際に呼び出し（null引数）
            MapTransformTool.main(null);

            /* 検証の準備 */

            /* 検証の実施 */
            // mainメソッドが例外を投げずに正常に実行されることを確認
            Assertions.assertTrue(true, "null引数でもmainメソッドが実行されること");

            // モックが正しく呼び出されたことを検証
            Mockito.verify(mockContext).getBean(MapTransformTool.class);
            Mockito.verify(mockTool).execute();
            Mockito.verify(mockContext).close();

        }

    }

    /**
     * mapping フィールドのテスト - 正常系：マッピングが正しく初期化される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMapping_normalInitialization() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        /* テスト対象の実行 */
        @SuppressWarnings("unchecked")
        final Map<String, String> actualMapping = (Map<String, String>) localReflectionModel.get("mapping");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(actualMapping, "マッピングが初期化されていること");
        Assertions.assertTrue(actualMapping instanceof HashMap, "マッピングがHashMap型であること");
        Assertions.assertTrue(actualMapping.isEmpty(), "マッピングが空で初期化されていること");

    }

    /**
     * フィールドの型テスト - 正常系：mappingフィールドがMap<String, String>型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testMappingType_normalMapStringString() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = Map.class;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("mapping");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertSame(expectedFieldType, actualFieldType, "mappingフィールドがMap型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("mappingフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * フィールドの可視性テスト - 正常系：mappingフィールドがfinalで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testMappingVisibility_normalFinal() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.FINAL;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("mapping");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.FINAL;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "mappingフィールドがfinalで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("mappingフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * mapTransformService フィールドのテスト - 正常系：マッピング変換サービスが正しく注入される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMapTransformService_normalInjection() throws Exception {

        /* 期待値の定義 */
        final MapTransformService expectedMapTransformService = Mockito.mock(MapTransformService.class);

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("mapTransformService", expectedMapTransformService);

        /* テスト対象の実行 */
        final MapTransformService actualMapTransformService
            = (MapTransformService) localReflectionModel.get("mapTransformService");

        /* 検証の準備 */
        final MapTransformService actualResult = actualMapTransformService;

        /* 検証の実施 */
        Assertions.assertEquals(expectedMapTransformService, actualResult, "マッピング変換サービスが正しく注入されていること");

    }

    /**
     * フィールドの型テスト - 正常系：mapTransformServiceフィールドがMapTransformService型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testMapTransformServiceType_normalMapTransformService() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = MapTransformService.class;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("mapTransformService");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */
            final Class<?> actualResult = actualFieldType;

            /* 検証の実施 */
            Assertions.assertSame(expectedFieldType, actualResult,
                "mapTransformServiceフィールドがMapTransformService型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("mapTransformServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * フィールドの可視性テスト - 正常系：mapTransformServiceフィールドがprivateで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testMapTransformServiceVisibility_normalPrivate() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("mapTransformService");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PRIVATE;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "mapTransformServiceフィールドがprivateで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("mapTransformServiceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * messageSource フィールドのテスト - 正常系：メッセージソースが正しく注入される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMessageSource_normalInjection() throws Exception {

        /* 期待値の定義 */
        final KmgMessageSource expectedMessageSource = Mockito.mock(KmgMessageSource.class);

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);
        localReflectionModel.set("messageSource", expectedMessageSource);

        /* テスト対象の実行 */
        final KmgMessageSource actualMessageSource = (KmgMessageSource) localReflectionModel.get("messageSource");

        /* 検証の準備 */
        final KmgMessageSource actualResult = actualMessageSource;

        /* 検証の実施 */
        Assertions.assertEquals(expectedMessageSource, actualResult, "メッセージソースが正しく注入されていること");

    }

    /**
     * フィールドの型テスト - 正常系：messageSourceフィールドがKmgMessageSource型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testMessageSourceType_normalKmgMessageSource() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = KmgMessageSource.class;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("messageSource");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */
            final Class<?> actualResult = actualFieldType;

            /* 検証の実施 */
            Assertions.assertSame(expectedFieldType, actualResult, "messageSourceフィールドがKmgMessageSource型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("messageSourceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * フィールドの可視性テスト - 正常系：messageSourceフィールドがprivateで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testMessageSourceVisibility_normalPrivate() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("messageSource");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PRIVATE;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "messageSourceフィールドがprivateで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("messageSourceフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * SpringBootApplication アノテーションのテスト - 正常系：アノテーションが正しく設定されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testSpringBootApplicationAnnotation_normalCorrect() {

        /* 期待値の定義 */

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        final SpringBootApplication annotation = testClass.getAnnotation(SpringBootApplication.class);

        /* 検証の準備 */
        final boolean actualResult = (annotation != null) && "kmg".equals(annotation.scanBasePackages()[0]);

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "SpringBootApplicationアノテーションが正しく設定されていること");

    }

    /**
     * targetPath フィールドのテスト - 正常系：対象ファイルのパスが正しく設定される場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testTargetPath_normalSet() throws Exception {

        /* 期待値の定義 */
        final Path expectedTargetPath = Paths.get("test/path");

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        /* テスト対象の実行 */
        localReflectionModel.set("targetPath", expectedTargetPath);
        final Path actualTargetPath = (Path) localReflectionModel.get("targetPath");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetPath, actualTargetPath, "対象ファイルのパスが正しく設定されること");

    }

    /**
     * フィールドの型テスト - 正常系：targetPathフィールドがPath型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testTargetPathType_normalPath() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = Path.class;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("targetPath");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertSame(expectedFieldType, actualFieldType, "targetPathフィールドがPath型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("targetPathフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * フィールドの可視性テスト - 正常系：targetPathフィールドがprivateで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testTargetPathVisibility_normalPrivate() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("targetPath");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & Modifier.PRIVATE;

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "targetPathフィールドがprivateで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("targetPathフィールドが見つかりません: " + e.getMessage());

        }

    }

    /**
     * TOOL_NAME 定数のテスト - 正常系：ツール名が正しく定義されている場合
     *
     * @since 1.0.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testToolName_normalCorrectValue() throws Exception {

        /* 期待値の定義 */
        final String expectedToolName = "マッピング変換ツール";

        /* 準備 */
        final MapTransformTool       localTestTarget      = new MapTransformTool();
        final KmgReflectionModelImpl localReflectionModel = new KmgReflectionModelImpl(localTestTarget);

        /* テスト対象の実行 */
        final String actualToolName = (String) localReflectionModel.get("TOOL_NAME");

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertEquals(expectedToolName, actualToolName, "TOOL_NAME定数が正しく定義されていること");

    }

    /**
     * 定数の型テスト - 正常系：TOOL_NAME定数がString型の場合
     *
     * @since 1.0.0
     */
    @Test
    public void testToolNameType_normalString() {

        /* 期待値の定義 */
        final Class<?> expectedFieldType = String.class;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field    field           = testClass.getDeclaredField("TOOL_NAME");
            final Class<?> actualFieldType = field.getType();

            /* 検証の準備 */

            /* 検証の実施 */
            Assertions.assertSame(expectedFieldType, actualFieldType, "TOOL_NAME定数がString型であること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("TOOL_NAME定数が見つかりません: " + e.getMessage());

        }

    }

    /**
     * 定数の可視性テスト - 正常系：TOOL_NAME定数がprivate static finalで定義されている場合
     *
     * @since 1.0.0
     */
    @Test
    public void testToolNameVisibility_normalPrivateStaticFinal() {

        /* 期待値の定義 */
        final int expectedModifiers = Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL;

        /* 準備 */
        final MapTransformTool localTestTarget = new MapTransformTool();
        final Class<?>         testClass       = localTestTarget.getClass();

        /* テスト対象の実行 */
        try {

            final Field field           = testClass.getDeclaredField("TOOL_NAME");
            final int   actualModifiers = field.getModifiers();

            /* 検証の準備 */
            final int actualResult = actualModifiers & (Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL);

            /* 検証の実施 */
            Assertions.assertEquals(expectedModifiers, actualResult, "TOOL_NAME定数がprivate static finalで定義されていること");

        } catch (final NoSuchFieldException e) {

            Assertions.fail("TOOL_NAME定数が見つかりません: " + e.getMessage());

        }

    }

}
