package kmg.tool.base.jdts.application.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.jdoc.domain.model.JavadocModel;
import kmg.tool.base.jdoc.domain.model.JavadocTagModel;
import kmg.tool.base.jdts.application.logic.JdtsBlockReplLogic;
import kmg.tool.base.jdts.application.model.JdtsBlockModel;
import kmg.tool.base.jdts.application.model.JdtsCodeModel;
import kmg.tool.base.jdts.application.model.JdtsConfigsModel;
import kmg.tool.base.jdts.application.model.JdtsTagConfigModel;

/**
 * JdtsReplServiceImplのテストクラス
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.3
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls", "static-method"
})
public class JdtsReplServiceImplTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.2.0
     */
    private JdtsReplServiceImpl testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.2.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * モックKMGメッセージソース
     *
     * @since 0.2.0
     */
    private KmgMessageSource mockMessageSource;

    /**
     * モックJdtsBlockReplLogic
     *
     * @since 0.2.0
     */
    private JdtsBlockReplLogic mockJdtsBlockReplLogic;

    /**
     * モックJdtsConfigsModel
     *
     * @since 0.2.0
     */
    private JdtsConfigsModel mockJdtsConfigsModel;

    /**
     * モックJdtsCodeModel
     *
     * @since 0.2.0
     */
    private JdtsCodeModel mockJdtsCodeModel;

    /**
     * モックJavadocModel
     *
     * @since 0.2.0
     */
    private JavadocModel mockJavadocModel;

    /**
     * モックJavadocTagModel
     *
     * @since 0.2.0
     */
    private JavadocTagModel mockJavadocTagModel;

    /**
     * モックJdtsTagConfigModel
     *
     * @since 0.2.0
     */
    private JdtsTagConfigModel mockJdtsTagConfigModel;

    /**
     * セットアップ
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @BeforeEach
    public void setUp() throws KmgReflectionException {

        final JdtsReplServiceImpl jdtsReplServiceImpl = new JdtsReplServiceImpl();
        this.testTarget = jdtsReplServiceImpl;
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);
        this.mockJdtsBlockReplLogic = Mockito.mock(JdtsBlockReplLogic.class);
        this.mockJdtsConfigsModel = Mockito.mock(JdtsConfigsModel.class);
        this.mockJdtsCodeModel = Mockito.mock(JdtsCodeModel.class);
        this.mockJavadocModel = Mockito.mock(JavadocModel.class);
        this.mockJavadocTagModel = Mockito.mock(JavadocTagModel.class);
        this.mockJdtsTagConfigModel = Mockito.mock(JdtsTagConfigModel.class);

        /* モックの設定 */
        this.reflectionModel.set("messageSource", this.mockMessageSource);
        this.reflectionModel.set("jdtsBlockReplLogic", this.mockJdtsBlockReplLogic);

        /* JavadocTagModelのモック設定 */
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn("test target");
        Mockito.when(this.mockJavadocTagModel.getTag()).thenReturn(KmgJavadocTagTypes.AUTHOR);
        Mockito.when(this.mockJavadocTagModel.getValue()).thenReturn("test value");
        Mockito.when(this.mockJavadocTagModel.getDescription()).thenReturn("test description");

        /* JdtsTagConfigModelのモック設定 */
        Mockito.when(this.mockJdtsTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.AUTHOR);
        Mockito.when(this.mockJdtsTagConfigModel.getTagValue()).thenReturn("test tag value");
        Mockito.when(this.mockJdtsTagConfigModel.getTagDescription()).thenReturn("test tag description");

    }

    /**
     * クリーンアップ
     *
     * @since 0.2.0
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
     * コンストラクタ メソッドのテスト - 正常系：カスタムロガーを使用した初期化
     *
     * @since 0.2.0
     */
    @Test
    public void testConstructor_normalCustomLogger() {

        /* 期待値の定義 */

        /* 準備 */
        final Logger              mockLogger      = Mockito.mock(Logger.class);
        final JdtsReplServiceImpl testConstructor = new JdtsReplServiceImpl(mockLogger);

        /* テスト対象の実行 */

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "カスタムロガーを使用したコンストラクタが正常に初期化されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系：標準ロガーを使用した初期化
     *
     * @since 0.2.0
     */
    @Test
    public void testConstructor_normalStandardLogger() {

        /* 期待値の定義 */

        /* 準備 */
        final JdtsReplServiceImpl testConstructor = new JdtsReplServiceImpl();

        /* テスト対象の実行 */

        /* 検証の準備 */

        /* 検証の実施 */
        Assertions.assertNotNull(testConstructor, "コンストラクタが正常に初期化されること");

    }

    /**
     * getJdtsConfigsModel メソッドのテスト - 正常系：構成モデルの取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetJdtsConfigsModel_normalGetConfigsModel() throws KmgReflectionException {

        /* 期待値の定義 */
        final JdtsConfigsModel expectedResult = this.mockJdtsConfigsModel;

        /* 準備 */
        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);

        /* テスト対象の実行 */
        final JdtsConfigsModel testResult = this.testTarget.getJdtsConfigsModel();

        /* 検証の準備 */
        final JdtsConfigsModel actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "構成モデルが正しく取得されること");

    }

    /**
     * getJdtsConfigsModel メソッドのテスト - 準正常系：nullの構成モデルの取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetJdtsConfigsModel_semiNullConfigsModel() throws KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("jdtsConfigsModel", null);

        /* テスト対象の実行 */
        final JdtsConfigsModel testResult = this.testTarget.getJdtsConfigsModel();

        /* 検証の準備 */
        final JdtsConfigsModel actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualResult, "nullの構成モデルが正しく取得されること");

    }

    /**
     * getReplaceCode メソッドのテスト - 正常系：置換後のコードの取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetReplaceCode_normalGetReplaceCode() throws KmgReflectionException {

        /* 期待値の定義 */
        final String expectedResult = "test replace code";

        /* 準備 */
        this.reflectionModel.set("replaceCode", expectedResult);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getReplaceCode();

        /* 検証の準備 */
        final String actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "置換後のコードが正しく取得されること");

    }

    /**
     * getReplaceCode メソッドのテスト - 準正常系：空文字の置換後のコードの取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetReplaceCode_semiEmptyReplaceCode() throws KmgReflectionException {

        /* 期待値の定義 */
        final String expectedResult = "";

        /* 準備 */
        this.reflectionModel.set("replaceCode", expectedResult);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getReplaceCode();

        /* 検証の準備 */
        final String actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "空文字の置換後のコードが正しく取得されること");

    }

    /**
     * getTotalReplaceCount メソッドのテスト - 正常系：合計置換数の取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetTotalReplaceCount_normalGetTotalReplaceCount() throws KmgReflectionException {

        /* 期待値の定義 */
        final long expectedResult = 5L;

        /* 準備 */
        this.reflectionModel.set("totalReplaceCount", expectedResult);

        /* テスト対象の実行 */
        final long testResult = this.testTarget.getTotalReplaceCount();

        /* 検証の準備 */
        final long actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "合計置換数が正しく取得されること");

    }

    /**
     * getTotalReplaceCount メソッドのテスト - 準正常系：ゼロの合計置換数の取得
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testGetTotalReplaceCount_semiZeroTotalReplaceCount() throws KmgReflectionException {

        /* 期待値の定義 */
        final long expectedResult = 0L;

        /* 準備 */
        this.reflectionModel.set("totalReplaceCount", expectedResult);

        /* テスト対象の実行 */
        final long testResult = this.testTarget.getTotalReplaceCount();

        /* 検証の準備 */
        final long actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedResult, actualResult, "ゼロの合計置換数が正しく取得されること");

    }

    /**
     * initialize メソッドのテスト - 正常系：正常な初期化
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testInitialize_normalInitialization() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final String expectedOrgCode           = "test original code";
        final long   expectedTotalReplaceCount = 0L;

        /* 準備 */
        Mockito.when(this.mockJdtsCodeModel.getOrgCode()).thenReturn(expectedOrgCode);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(this.mockJdtsConfigsModel, this.mockJdtsCodeModel);

        /* 検証の準備 */
        final boolean          actualResult            = testResult;
        final JdtsConfigsModel actualConfigsModel      = (JdtsConfigsModel) this.reflectionModel
            .get("jdtsConfigsModel");
        final JdtsCodeModel    actualCodeModel         = (JdtsCodeModel) this.reflectionModel.get("jdtsCodeModel");
        final String           actualReplaceCode       = (String) this.reflectionModel.get("replaceCode");
        final Long             actualTotalReplaceCount = (Long) this.reflectionModel.get("totalReplaceCount");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が正常に完了すること");
        Assertions.assertEquals(this.mockJdtsConfigsModel, actualConfigsModel, "構成モデルが正しく設定されること");
        Assertions.assertEquals(this.mockJdtsCodeModel, actualCodeModel, "コードモデルが正しく設定されること");
        Assertions.assertEquals(expectedOrgCode, actualReplaceCode, "置換後のコードが正しく設定されること");
        Assertions.assertEquals(expectedTotalReplaceCount, actualTotalReplaceCount, "合計置換数がゼロに初期化されること");

    }

    /**
     * initialize メソッドのテスト - 準正常系：jdtsCodeModelがnullの場合
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testInitialize_semiNullJdtsCodeModel() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        // jdtsCodeModelがnullの場合は初期化をスキップするため、モックは不要

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(this.mockJdtsConfigsModel, null);

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "jdtsCodeModelがnullの場合は初期化が失敗すること");

    }

    /**
     * initialize メソッドのテスト - 準正常系：nullパラメータの場合
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testInitialize_semiNullParameters() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */

        /* 準備 */
        // nullパラメータの場合は初期化をスキップするため、モックは不要

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(null, null);

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "nullパラメータの場合は初期化が失敗すること");

    }

    /**
     * logAddNewTag メソッドのテスト - 正常系：新しいタグ追加時のログ出力
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testLogAddNewTag_normalLogOutput() throws KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String expectedLogMessage = "test log message";

        /* 準備 */
        final JdtsBlockModel     blockModel           = Mockito.mock(JdtsBlockModel.class);
        final KmgJavadocTagTypes mockTagConfigTagType = Mockito.mock(KmgJavadocTagTypes.class);

        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");
        Mockito.when(this.mockJdtsTagConfigModel.getTag()).thenReturn(mockTagConfigTagType);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentTagConfigModel()).thenReturn(this.mockJdtsTagConfigModel);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(expectedLogMessage);

        // タグタイプのモック設定
        Mockito.when(mockTagConfigTagType.getDisplayName()).thenReturn("ConfigTag");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("logAddNewTag", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "新しいタグ追加時のログが正常に出力されること");

    }

    /**
     * logAddNewTag メソッドのテスト - 準正常系：CurrentTagConfigModelがnullの場合
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testLogAddNewTag_semiNullCurrentTagConfigModel() throws KmgReflectionException, Exception {

        /* 期待値の定義 */

        /* 準備 */
        final JdtsBlockModel blockModel = Mockito.mock(JdtsBlockModel.class);

        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentTagConfigModel()).thenReturn(null);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("logAddNewTag", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "CurrentTagConfigModelがnullの場合でも例外が発生しないこと");

    }

    /**
     * logRemoveTag メソッドのテスト - 正常系：タグ削除時のログ出力
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testLogRemoveTag_normalLogOutput() throws KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String expectedLogMessage = "test log message";

        /* 準備 */
        final JdtsBlockModel     blockModel         = Mockito.mock(JdtsBlockModel.class);
        final KmgJavadocTagTypes mockJavadocTagType = Mockito.mock(KmgJavadocTagTypes.class);

        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn("test target");
        Mockito.when(this.mockJavadocTagModel.getTag()).thenReturn(mockJavadocTagType);
        Mockito.when(this.mockJavadocTagModel.getValue()).thenReturn("test value");
        Mockito.when(this.mockJavadocTagModel.getDescription()).thenReturn("test description");
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentSrcJavadocTag()).thenReturn(this.mockJavadocTagModel);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(expectedLogMessage);

        // タグタイプのモック設定
        Mockito.when(mockJavadocTagType.getDisplayName()).thenReturn("TestTag");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("logRemoveTag", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "タグ削除時のログが正常に出力されること");

    }

    /**
     * logReplaceTag メソッドのテスト - 正常系：タグ置換時のログ出力
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testLogReplaceTag_normalLogOutput() throws KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String expectedLogMessage = "test log message";

        /* 準備 */
        final JdtsBlockModel     blockModel           = Mockito.mock(JdtsBlockModel.class);
        final KmgJavadocTagTypes mockJavadocTagType   = Mockito.mock(KmgJavadocTagTypes.class);
        final KmgJavadocTagTypes mockTagConfigTagType = Mockito.mock(KmgJavadocTagTypes.class);

        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn("test target");
        Mockito.when(this.mockJavadocTagModel.getTag()).thenReturn(mockJavadocTagType);
        Mockito.when(this.mockJavadocTagModel.getValue()).thenReturn("test value");
        Mockito.when(this.mockJavadocTagModel.getDescription()).thenReturn("test description");
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentSrcJavadocTag()).thenReturn(this.mockJavadocTagModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getTagContentToApply()).thenReturn("test content");
        Mockito.when(this.mockJdtsTagConfigModel.getTag()).thenReturn(mockTagConfigTagType);
        Mockito.when(this.mockJdtsTagConfigModel.getTagValue()).thenReturn("test tag value");
        Mockito.when(this.mockJdtsTagConfigModel.getTagDescription()).thenReturn("test tag description");
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentTagConfigModel()).thenReturn(this.mockJdtsTagConfigModel);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(expectedLogMessage);

        // タグタイプのモック設定
        Mockito.when(mockJavadocTagType.getDisplayName()).thenReturn("TestTag");
        Mockito.when(mockTagConfigTagType.getDisplayName()).thenReturn("ConfigTag");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("logReplaceTag", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "タグ置換時のログが正常に出力されること");

    }

    /**
     * logReplaceTag メソッドのテスト - 準正常系：CurrentSrcJavadocTagがnullの場合
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testLogReplaceTag_semiNullCurrentSrcJavadocTag() throws KmgReflectionException, Exception {

        /* 期待値の定義 */

        /* 準備 */
        final JdtsBlockModel blockModel = Mockito.mock(JdtsBlockModel.class);

        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentSrcJavadocTag()).thenReturn(null);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("logReplaceTag", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "CurrentSrcJavadocTagがnullの場合でも例外が発生しないこと");

    }

    /**
     * logRepositionTag メソッドのテスト - 正常系：タグ位置変更時のログ出力
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testLogRepositionTag_normalLogOutput() throws KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String expectedLogMessage = "test log message";

        /* 準備 */
        final JdtsBlockModel     blockModel           = Mockito.mock(JdtsBlockModel.class);
        final KmgJavadocTagTypes mockJavadocTagType   = Mockito.mock(KmgJavadocTagTypes.class);
        final KmgJavadocTagTypes mockTagConfigTagType = Mockito.mock(KmgJavadocTagTypes.class);

        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn("test target");
        Mockito.when(this.mockJavadocTagModel.getTag()).thenReturn(mockJavadocTagType);
        Mockito.when(this.mockJavadocTagModel.getValue()).thenReturn("test value");
        Mockito.when(this.mockJavadocTagModel.getDescription()).thenReturn("test description");
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentSrcJavadocTag()).thenReturn(this.mockJavadocTagModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getTagContentToApply()).thenReturn("test content");
        Mockito.when(this.mockJdtsTagConfigModel.getTag()).thenReturn(mockTagConfigTagType);
        Mockito.when(this.mockJdtsTagConfigModel.getTagValue()).thenReturn("test tag value");
        Mockito.when(this.mockJdtsTagConfigModel.getTagDescription()).thenReturn("test tag description");
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentTagConfigModel()).thenReturn(this.mockJdtsTagConfigModel);
        Mockito.when(this.mockMessageSource.getLogMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(expectedLogMessage);

        // タグタイプのモック設定
        Mockito.when(mockJavadocTagType.getDisplayName()).thenReturn("TestTag");
        Mockito.when(mockTagConfigTagType.getDisplayName()).thenReturn("ConfigTag");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("logRepositionTag", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "タグ位置変更時のログが正常に出力されること");

    }

    /**
     * logRepositionTag メソッドのテスト - 準正常系：CurrentSrcJavadocTagがnullの場合
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testLogRepositionTag_semiNullCurrentSrcJavadocTag() throws KmgReflectionException, Exception {

        /* 期待値の定義 */

        /* 準備 */
        final JdtsBlockModel blockModel = Mockito.mock(JdtsBlockModel.class);

        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentSrcJavadocTag()).thenReturn(null);

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("logRepositionTag", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "CurrentSrcJavadocTagがnullの場合でも例外が発生しないこと");

    }

    /**
     * processBlock メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     */
    @Test
    public void testProcessBlock_errorKmgToolMsgException() throws KmgReflectionException, KmgToolMsgException {

        /* 期待値の定義 */

        /* 準備 */
        final JdtsBlockModel blockModel = Mockito.mock(JdtsBlockModel.class);
        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);

        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");
        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenThrow(new RuntimeException("Test exception"));

        /* テスト対象の実行 */
        final RuntimeException testException = Assertions.assertThrows(RuntimeException.class, () -> {

            this.reflectionModel.getMethod("processBlock", blockModel);

        });

        /* 検証の準備 */
        final boolean actualResult = testException != null;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "RuntimeExceptionが正しく発生すること");

    }

    /**
     * processBlock メソッドのテスト - 正常系：nextTag()がfalseを返す場合の処理（do-whileループが終了するケース）
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testProcessBlock_normalNextTagFalse() throws KmgToolMsgException, KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String replacedJavadocBlock      = "/** replaced javadoc */";
        final UUID   blockId                   = UUID.randomUUID();
        final long   expectedTotalReplaceCount = 0L;

        /* 準備 */
        final JdtsBlockModel     blockModel           = Mockito.mock(JdtsBlockModel.class);
        final KmgJavadocTagTypes mockJavadocTagType   = Mockito.mock(KmgJavadocTagTypes.class);
        final KmgJavadocTagTypes mockTagConfigTagType = Mockito.mock(KmgJavadocTagTypes.class);

        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);
        this.reflectionModel.set("replaceCode", "test original code");
        this.reflectionModel.set("totalReplaceCount", 0L);

        // モックの設定 - nextTag()がfalseを返すように設定（ループが終了する）
        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.hasExistingTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.shouldAddNewTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.nextTag()).thenReturn(false); // falseを返す
        Mockito.when(this.mockJdtsBlockReplLogic.getReplacedJavadocBlock()).thenReturn(replacedJavadocBlock);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentSrcJavadocTag()).thenReturn(this.mockJavadocTagModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentTagConfigModel()).thenReturn(this.mockJdtsTagConfigModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getTagContentToApply()).thenReturn("test content");
        Mockito.when(blockModel.getId()).thenReturn(blockId);
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");

        // logReplaceTagメソッドで使用されるモック設定
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn("test target");
        Mockito.when(this.mockJavadocTagModel.getTag()).thenReturn(mockJavadocTagType);
        Mockito.when(this.mockJavadocTagModel.getValue()).thenReturn("test value");
        Mockito.when(this.mockJavadocTagModel.getDescription()).thenReturn("test description");
        Mockito.when(mockJavadocTagType.getDisplayName()).thenReturn("TestTag");
        Mockito.when(this.mockJdtsTagConfigModel.getTag()).thenReturn(mockTagConfigTagType);
        Mockito.when(this.mockJdtsTagConfigModel.getTagValue()).thenReturn("test tag value");
        Mockito.when(this.mockJdtsTagConfigModel.getTagDescription()).thenReturn("test tag description");
        Mockito.when(mockTagConfigTagType.getDisplayName()).thenReturn("ConfigTag");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processBlock", blockModel);

        /* 検証の準備 */
        final long actualTotalReplaceCount = (Long) this.reflectionModel.get("totalReplaceCount");

        /* 検証の実施 */
        Assertions.assertEquals(expectedTotalReplaceCount, actualTotalReplaceCount,
            "nextTag()がfalseを返す場合、totalReplaceCountが正しく維持されること");

        // nextTag()が1回呼び出されることを確認（falseを返してループが終了）
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(1)).nextTag();

        // ループが終了するため、hasExistingTag()が1回呼び出されることを確認
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(1)).hasExistingTag();

        // getReplacedJavadocBlock()が呼び出されることを確認（ループ終了後の処理）
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(1)).getReplacedJavadocBlock();

    }

    /**
     * processBlock メソッドのテスト - 正常系：nextTag()がtrueを返す場合の処理（do-whileループが継続するケース）
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testProcessBlock_normalNextTagTrue() throws KmgToolMsgException, KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String replacedJavadocBlock      = "/** replaced javadoc */";
        final UUID   blockId                   = UUID.randomUUID();
        final long   expectedTotalReplaceCount = 0L;

        /* 準備 */
        final JdtsBlockModel     blockModel           = Mockito.mock(JdtsBlockModel.class);
        final KmgJavadocTagTypes mockJavadocTagType   = Mockito.mock(KmgJavadocTagTypes.class);
        final KmgJavadocTagTypes mockTagConfigTagType = Mockito.mock(KmgJavadocTagTypes.class);

        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);
        this.reflectionModel.set("replaceCode", "test original code");
        this.reflectionModel.set("totalReplaceCount", 0L);

        // モックの設定 - nextTag()がtrueを返すように設定（ループが継続する）
        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.hasExistingTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.shouldAddNewTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.nextTag()).thenReturn(true, false); // 1回目はtrue、2回目はfalse
        Mockito.when(this.mockJdtsBlockReplLogic.getReplacedJavadocBlock()).thenReturn(replacedJavadocBlock);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentSrcJavadocTag()).thenReturn(this.mockJavadocTagModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentTagConfigModel()).thenReturn(this.mockJdtsTagConfigModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getTagContentToApply()).thenReturn("test content");
        Mockito.when(blockModel.getId()).thenReturn(blockId);
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");

        // logReplaceTagメソッドで使用されるモック設定
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn("test target");
        Mockito.when(this.mockJavadocTagModel.getTag()).thenReturn(mockJavadocTagType);
        Mockito.when(this.mockJavadocTagModel.getValue()).thenReturn("test value");
        Mockito.when(this.mockJavadocTagModel.getDescription()).thenReturn("test description");
        Mockito.when(mockJavadocTagType.getDisplayName()).thenReturn("TestTag");
        Mockito.when(this.mockJdtsTagConfigModel.getTag()).thenReturn(mockTagConfigTagType);
        Mockito.when(this.mockJdtsTagConfigModel.getTagValue()).thenReturn("test tag value");
        Mockito.when(this.mockJdtsTagConfigModel.getTagDescription()).thenReturn("test tag description");
        Mockito.when(mockTagConfigTagType.getDisplayName()).thenReturn("ConfigTag");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processBlock", blockModel);

        /* 検証の準備 */
        final long actualTotalReplaceCount = (Long) this.reflectionModel.get("totalReplaceCount");

        /* 検証の実施 */
        Assertions.assertEquals(expectedTotalReplaceCount, actualTotalReplaceCount,
            "nextTag()がtrueを返す場合、totalReplaceCountが正しく維持されること");

        // nextTag()が2回呼び出されることを確認（1回目はtrue、2回目はfalse）
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(2)).nextTag();

        // ループが継続されるため、hasExistingTag()が複数回呼び出されることを確認
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(2)).hasExistingTag();

    }

    /**
     * processBlock メソッドのテスト - 正常系：正常なブロック処理
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testProcessBlock_normalProcessBlock() throws KmgToolMsgException, KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String replacedJavadocBlock = "/** replaced javadoc */";
        final UUID   blockId              = UUID.randomUUID();

        /* 準備 */
        final JdtsBlockModel blockModel = Mockito.mock(JdtsBlockModel.class);
        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);
        this.reflectionModel.set("replaceCode", "test original code");
        this.reflectionModel.set("totalReplaceCount", 0L);

        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.hasExistingTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.shouldAddNewTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.nextTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.getReplacedJavadocBlock()).thenReturn(replacedJavadocBlock);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentTagConfigModel()).thenReturn(this.mockJdtsTagConfigModel);
        Mockito.when(blockModel.getId()).thenReturn(blockId);
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processBlock", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "ブロック処理が正常に完了すること");

    }

    /**
     * processBlock メソッドのテスト - 正常系：既存タグの置換が発生する場合の処理（replaceExistingTag()がtrueを返すケース）
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testProcessBlock_normalReplaceExistingTag()
        throws KmgToolMsgException, KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String replacedJavadocBlock      = "/** replaced javadoc */";
        final UUID   blockId                   = UUID.randomUUID();
        final long   expectedTotalReplaceCount = 1L;

        /* 準備 */
        final JdtsBlockModel     blockModel           = Mockito.mock(JdtsBlockModel.class);
        final KmgJavadocTagTypes mockJavadocTagType   = Mockito.mock(KmgJavadocTagTypes.class);
        final KmgJavadocTagTypes mockTagConfigTagType = Mockito.mock(KmgJavadocTagTypes.class);

        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);
        this.reflectionModel.set("replaceCode", "test original code");
        this.reflectionModel.set("totalReplaceCount", 0L);

        // モックの設定 - replaceExistingTag()がtrueを返すように設定
        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.hasExistingTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.removeCurrentTagOnError()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.shouldOverwriteTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.repositionTagIfNeeded()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.replaceExistingTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.nextTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.getReplacedJavadocBlock()).thenReturn(replacedJavadocBlock);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentSrcJavadocTag()).thenReturn(this.mockJavadocTagModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentTagConfigModel()).thenReturn(this.mockJdtsTagConfigModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getTagContentToApply()).thenReturn("test content");
        Mockito.when(blockModel.getId()).thenReturn(blockId);
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");

        // logReplaceTagメソッドで使用されるモック設定
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn("test target");
        Mockito.when(this.mockJavadocTagModel.getTag()).thenReturn(mockJavadocTagType);
        Mockito.when(this.mockJavadocTagModel.getValue()).thenReturn("test value");
        Mockito.when(this.mockJavadocTagModel.getDescription()).thenReturn("test description");
        Mockito.when(mockJavadocTagType.getDisplayName()).thenReturn("TestTag");
        Mockito.when(this.mockJdtsTagConfigModel.getTag()).thenReturn(mockTagConfigTagType);
        Mockito.when(this.mockJdtsTagConfigModel.getTagValue()).thenReturn("test tag value");
        Mockito.when(this.mockJdtsTagConfigModel.getTagDescription()).thenReturn("test tag description");
        Mockito.when(mockTagConfigTagType.getDisplayName()).thenReturn("ConfigTag");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processBlock", blockModel);

        /* 検証の準備 */
        final long actualTotalReplaceCount = (Long) this.reflectionModel.get("totalReplaceCount");

        /* 検証の実施 */
        Assertions.assertEquals(expectedTotalReplaceCount, actualTotalReplaceCount,
            "既存タグの置換時にtotalReplaceCountが正しくインクリメントされること");

        // replaceExistingTag()が呼び出されたことを確認
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(1)).replaceExistingTag();

        // logReplaceTagが呼び出されたことを検証（logReplaceTagメソッド内で使用されるメソッドが呼び出されることを確認）
        // logReplaceTagメソッド内でgetCurrentSrcJavadocTag()が5回呼び出される（早期リターンチェック1回 + ログ出力用4回）
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(5)).getCurrentSrcJavadocTag();
        // logReplaceTagメソッド内でgetCurrentTagConfigModel()が3回呼び出される（ログ出力用3回）
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(3)).getCurrentTagConfigModel();
        // logReplaceTagメソッド内でgetTagContentToApply()が1回呼び出される（ログ出力用1回）
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(1)).getTagContentToApply();

        // continue文が実行された後、do-whileループの条件チェックでnextTag()が呼び出されることを確認
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(1)).nextTag();

    }

    /**
     * processBlock メソッドのテスト - 正常系：既存タグの置換が発生しない場合の処理（replaceExistingTag()がfalseを返すケース）
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testProcessBlock_normalReplaceExistingTagFalse()
        throws KmgToolMsgException, KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String replacedJavadocBlock      = "/** replaced javadoc */";
        final UUID   blockId                   = UUID.randomUUID();
        final long   expectedTotalReplaceCount = 0L;

        /* 準備 */
        final JdtsBlockModel     blockModel           = Mockito.mock(JdtsBlockModel.class);
        final KmgJavadocTagTypes mockJavadocTagType   = Mockito.mock(KmgJavadocTagTypes.class);
        final KmgJavadocTagTypes mockTagConfigTagType = Mockito.mock(KmgJavadocTagTypes.class);

        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);
        this.reflectionModel.set("replaceCode", "test original code");
        this.reflectionModel.set("totalReplaceCount", 0L);

        // モックの設定 - replaceExistingTag()がfalseを返すように設定
        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.hasExistingTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.removeCurrentTagOnError()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.shouldOverwriteTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.repositionTagIfNeeded()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.replaceExistingTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.nextTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.getReplacedJavadocBlock()).thenReturn(replacedJavadocBlock);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentSrcJavadocTag()).thenReturn(this.mockJavadocTagModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentTagConfigModel()).thenReturn(this.mockJdtsTagConfigModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getTagContentToApply()).thenReturn("test content");
        Mockito.when(blockModel.getId()).thenReturn(blockId);
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");

        // logReplaceTagメソッドで使用されるモック設定（replaceExistingTag()がfalseの場合は呼び出されないが、念のため設定）
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn("test target");
        Mockito.when(this.mockJavadocTagModel.getTag()).thenReturn(mockJavadocTagType);
        Mockito.when(this.mockJavadocTagModel.getValue()).thenReturn("test value");
        Mockito.when(this.mockJavadocTagModel.getDescription()).thenReturn("test description");
        Mockito.when(mockJavadocTagType.getDisplayName()).thenReturn("TestTag");
        Mockito.when(this.mockJdtsTagConfigModel.getTag()).thenReturn(mockTagConfigTagType);
        Mockito.when(this.mockJdtsTagConfigModel.getTagValue()).thenReturn("test tag value");
        Mockito.when(this.mockJdtsTagConfigModel.getTagDescription()).thenReturn("test tag description");
        Mockito.when(mockTagConfigTagType.getDisplayName()).thenReturn("ConfigTag");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processBlock", blockModel);

        /* 検証の準備 */
        final long actualTotalReplaceCount = (Long) this.reflectionModel.get("totalReplaceCount");

        /* 検証の実施 */
        Assertions.assertEquals(expectedTotalReplaceCount, actualTotalReplaceCount,
            "既存タグの置換が発生しない場合、totalReplaceCountがインクリメントされないこと");

        // replaceExistingTag()が呼び出されたことを確認
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(1)).replaceExistingTag();

        // logReplaceTagが呼び出されないことを検証（replaceExistingTag()がfalseを返すため）
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(0)).getCurrentSrcJavadocTag();
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(0)).getCurrentTagConfigModel();
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(0)).getTagContentToApply();

        // continue文が実行されないため、do-whileループの条件チェックでnextTag()が呼び出されることを確認
        Mockito.verify(this.mockJdtsBlockReplLogic, Mockito.times(1)).nextTag();

    }

    /**
     * processBlock メソッドのテスト - 準正常系：既存タグがある場合の処理
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testProcessBlock_semiExistingTag() throws KmgToolMsgException, KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String replacedJavadocBlock = "/** replaced javadoc */";
        final UUID   blockId              = UUID.randomUUID();

        /* 準備 */
        final JdtsBlockModel blockModel = Mockito.mock(JdtsBlockModel.class);
        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);
        this.reflectionModel.set("replaceCode", "test original code");
        this.reflectionModel.set("totalReplaceCount", 0L);

        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.hasExistingTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.removeCurrentTagOnError()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.shouldOverwriteTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.repositionTagIfNeeded()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.replaceExistingTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.nextTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.getReplacedJavadocBlock()).thenReturn(replacedJavadocBlock);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentSrcJavadocTag()).thenReturn(this.mockJavadocTagModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentTagConfigModel()).thenReturn(this.mockJdtsTagConfigModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getTagContentToApply()).thenReturn("test content");
        Mockito.when(blockModel.getId()).thenReturn(blockId);
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processBlock", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "既存タグがある場合のブロック処理が正常に完了すること");

    }

    /**
     * processBlock メソッドのテスト - 準正常系：新しいタグを追加しない場合の処理
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testProcessBlock_semiNoAddNewTag() throws KmgToolMsgException, KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String replacedJavadocBlock = "/** replaced javadoc */";
        final UUID   blockId              = UUID.randomUUID();

        /* 準備 */
        final JdtsBlockModel blockModel = Mockito.mock(JdtsBlockModel.class);
        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);
        this.reflectionModel.set("replaceCode", "test original code");
        this.reflectionModel.set("totalReplaceCount", 0L);

        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.hasExistingTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.shouldAddNewTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.nextTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.getReplacedJavadocBlock()).thenReturn(replacedJavadocBlock);
        Mockito.when(blockModel.getId()).thenReturn(blockId);
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processBlock", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "新しいタグを追加しない場合のブロック処理が正常に完了すること");

    }

    /**
     * processBlock メソッドのテスト - 準正常系：上書きしない場合の処理
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testProcessBlock_semiNoOverwrite() throws KmgToolMsgException, KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String replacedJavadocBlock = "/** replaced javadoc */";
        final UUID   blockId              = UUID.randomUUID();

        /* 準備 */
        final JdtsBlockModel blockModel = Mockito.mock(JdtsBlockModel.class);
        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);
        this.reflectionModel.set("replaceCode", "test original code");
        this.reflectionModel.set("totalReplaceCount", 0L);

        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.hasExistingTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.removeCurrentTagOnError()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.shouldOverwriteTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.nextTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.getReplacedJavadocBlock()).thenReturn(replacedJavadocBlock);
        Mockito.when(blockModel.getId()).thenReturn(blockId);
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processBlock", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "上書きしない場合のブロック処理が正常に完了すること");

    }

    /**
     * processBlock メソッドのテスト - 準正常系：タグ削除が発生する場合の処理
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testProcessBlock_semiTagRemoval() throws KmgToolMsgException, KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String replacedJavadocBlock = "/** replaced javadoc */";
        final UUID   blockId              = UUID.randomUUID();

        /* 準備 */
        final JdtsBlockModel blockModel = Mockito.mock(JdtsBlockModel.class);
        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);
        this.reflectionModel.set("replaceCode", "test original code");
        this.reflectionModel.set("totalReplaceCount", 0L);

        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.hasExistingTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.removeCurrentTagOnError()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.nextTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.getReplacedJavadocBlock()).thenReturn(replacedJavadocBlock);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentSrcJavadocTag()).thenReturn(this.mockJavadocTagModel);
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn("test target");
        Mockito.when(this.mockJavadocTagModel.getTag()).thenReturn(KmgJavadocTagTypes.AUTHOR);
        Mockito.when(this.mockJavadocTagModel.getValue()).thenReturn("test value");
        Mockito.when(this.mockJavadocTagModel.getDescription()).thenReturn("test description");
        Mockito.when(blockModel.getId()).thenReturn(blockId);
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processBlock", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "タグ削除が発生する場合のブロック処理が正常に完了すること");

    }

    /**
     * processBlock メソッドのテスト - 準正常系：タグ位置変更が発生する場合の処理
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws Exception
     *                                メソッド実行例外
     */
    @Test
    public void testProcessBlock_semiTagReposition() throws KmgToolMsgException, KmgReflectionException, Exception {

        /* 期待値の定義 */
        final String replacedJavadocBlock = "/** replaced javadoc */";
        final UUID   blockId              = UUID.randomUUID();

        /* 準備 */
        final JdtsBlockModel blockModel = Mockito.mock(JdtsBlockModel.class);
        this.reflectionModel.set("jdtsConfigsModel", this.mockJdtsConfigsModel);
        this.reflectionModel.set("replaceCode", "test original code");
        this.reflectionModel.set("totalReplaceCount", 0L);

        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.hasExistingTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.removeCurrentTagOnError()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.shouldOverwriteTag()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.repositionTagIfNeeded()).thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.nextTag()).thenReturn(false);
        Mockito.when(this.mockJdtsBlockReplLogic.getReplacedJavadocBlock()).thenReturn(replacedJavadocBlock);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentSrcJavadocTag()).thenReturn(this.mockJavadocTagModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getCurrentTagConfigModel()).thenReturn(this.mockJdtsTagConfigModel);
        Mockito.when(this.mockJdtsBlockReplLogic.getTagContentToApply()).thenReturn("test content");
        Mockito.when(blockModel.getId()).thenReturn(blockId);
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");

        /* テスト対象の実行 */
        this.reflectionModel.getMethod("processBlock", blockModel);

        /* 検証の準備 */
        final boolean actualResult = true;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "タグ位置変更が発生する場合のブロック処理が正常に完了すること");

    }

    /**
     * replace メソッドのテスト - 異常系：KmgToolMsgExceptionが発生する場合
     *
     * @since 0.2.0
     *
     * @throws KmgReflectionException
     *                                リフレクション例外
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     */
    @Test
    public void testReplace_errorKmgToolMsgException() throws KmgReflectionException, KmgToolMsgException {

        /* 期待値の定義 */

        /* 準備 */
        final List<JdtsBlockModel> blockModels = new ArrayList<>();
        final JdtsBlockModel       blockModel  = Mockito.mock(JdtsBlockModel.class);
        blockModels.add(blockModel);

        this.reflectionModel.set("jdtsCodeModel", this.mockJdtsCodeModel);
        this.reflectionModel.set("replaceCode", "test original code");
        this.reflectionModel.set("totalReplaceCount", 0L);

        Mockito.when(this.mockJdtsCodeModel.getJdtsBlockModels()).thenReturn(blockModels);
        Mockito.when(this.mockJavadocModel.getSrcJavadoc()).thenReturn("/** test javadoc */");
        Mockito.when(blockModel.getJavadocModel()).thenReturn(this.mockJavadocModel);
        Mockito.when(blockModel.getId()).thenReturn(UUID.randomUUID());
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");
        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenThrow(new RuntimeException("Test exception"));

        /* テスト対象の実行 */
        final RuntimeException testException = Assertions.assertThrows(RuntimeException.class, () -> {

            this.testTarget.replace();

        });

        /* 検証の準備 */
        final boolean actualResult = testException != null;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "RuntimeExceptionが正しく発生すること");

    }

    /**
     * replace メソッドのテスト - 正常系：正常な置換処理
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testReplace_normalReplace() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final String originalCode         = "test original code";
        final String javadocContent       = "/** test javadoc */";
        final UUID   blockId              = UUID.randomUUID();
        final String replacedJavadocBlock = "/** replaced javadoc */";
        // replaceCodeにはjavadocContentが含まれている（見つかる状態）
        final String replaceCodeWithJavadoc = originalCode + javadocContent;

        /* 準備 */
        final List<JdtsBlockModel> blockModels = new ArrayList<>();
        final JdtsBlockModel       blockModel  = Mockito.mock(JdtsBlockModel.class);
        blockModels.add(blockModel);

        this.reflectionModel.set("jdtsCodeModel", this.mockJdtsCodeModel);
        this.reflectionModel.set("replaceCode", replaceCodeWithJavadoc);
        this.reflectionModel.set("totalReplaceCount", 0L);

        Mockito.when(this.mockJdtsCodeModel.getJdtsBlockModels()).thenReturn(blockModels);
        Mockito.when(this.mockJavadocModel.getSrcJavadoc()).thenReturn(javadocContent);
        Mockito.when(blockModel.getJavadocModel()).thenReturn(this.mockJavadocModel);
        Mockito.when(blockModel.getId()).thenReturn(blockId);
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");
        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.getReplacedJavadocBlock()).thenReturn(replacedJavadocBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.replace();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "置換処理が正常に完了すること");

    }

    /**
     * replace メソッドのテスト - 準正常系：空のブロックモデルリストでの置換処理
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testReplace_semiEmptyBlockModels() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final String originalCode = "test original code";

        /* 準備 */
        final List<JdtsBlockModel> blockModels = new ArrayList<>();

        this.reflectionModel.set("jdtsCodeModel", this.mockJdtsCodeModel);
        this.reflectionModel.set("replaceCode", originalCode);
        this.reflectionModel.set("totalReplaceCount", 0L);

        Mockito.when(this.mockJdtsCodeModel.getJdtsBlockModels()).thenReturn(blockModels);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.replace();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "空のブロックモデルリストでも置換処理が正常に完了すること");

    }

    /**
     * replace メソッドのテスト - 準正常系：Javadocが見つからない場合の処理（replaceIdx == -1）
     *
     * @since 0.2.3
     *
     * @throws KmgToolMsgException
     *                                KMGツールメッセージ例外
     * @throws KmgReflectionException
     *                                リフレクション例外
     */
    @Test
    public void testReplace_semiJavadocNotFound() throws KmgToolMsgException, KmgReflectionException {

        /* 期待値の定義 */
        final String originalCode   = "test original code";
        final String javadocContent = "/** test javadoc */";
        final UUID   blockId        = UUID.randomUUID();
        final String replacedJavadocBlock = "/** replaced javadoc */";

        /* 準備 */
        final List<JdtsBlockModel> blockModels = new ArrayList<>();
        final JdtsBlockModel       blockModel  = Mockito.mock(JdtsBlockModel.class);
        blockModels.add(blockModel);

        this.reflectionModel.set("jdtsCodeModel", this.mockJdtsCodeModel);
        // replaceCodeにはjavadocContentが含まれていない（見つからない状態）
        this.reflectionModel.set("replaceCode", originalCode);
        this.reflectionModel.set("totalReplaceCount", 0L);

        Mockito.when(this.mockJdtsCodeModel.getJdtsBlockModels()).thenReturn(blockModels);
        Mockito.when(this.mockJavadocModel.getSrcJavadoc()).thenReturn(javadocContent);
        Mockito.when(blockModel.getJavadocModel()).thenReturn(this.mockJavadocModel);
        Mockito.when(blockModel.getId()).thenReturn(blockId);
        Mockito.when(blockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(blockModel.getElementName()).thenReturn("TestClass");
        Mockito.when(this.mockJdtsBlockReplLogic.initialize(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(true);
        Mockito.when(this.mockJdtsBlockReplLogic.getReplacedJavadocBlock()).thenReturn(replacedJavadocBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.replace();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "Javadocが見つからない場合でも置換処理が正常に完了すること");

    }

}
