package kmg.tool.application.model.jdts.imp;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.context.SpringApplicationContextHelper;
import kmg.tool.application.model.jdts.JdtsBlockModel;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;

/**
 * Javadocタグ設定のコードモデル実装のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SuppressWarnings({
    "nls", "static-method",
})
public class JdtsCodeModelImplTest extends AbstractKmgTest {

    /** テスト対象 */
    private JdtsCodeModelImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /** モックKMGメッセージソース */
    private KmgMessageSource mockMessageSource;

    /**
     * テスト前処理<br>
     *
     * @since 0.1.0
     */
    @BeforeEach
    public void setUp() {

        /* モックの初期化 */
        this.mockMessageSource = Mockito.mock(KmgMessageSource.class);

        /* テスト対象のクリア */
        this.testTarget = null;

    }

    /**
     * constructor メソッドのテスト - 正常系:通常のコードでのコンストラクタ
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalCode() {

        /* 期待値の定義 */
        final String expectedOrgCode             = "/** テストJavadoc */\npublic class TestClass {}";
        final int    expectedJdtsBlockModelsSize = 0;

        /* 準備 */
        final String testCode = "/** テストJavadoc */\npublic class TestClass {}";

        /* テスト対象の実行 */
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* 検証の準備 */
        final String               actualOrgCode         = this.testTarget.getOrgCode();
        final List<JdtsBlockModel> actualJdtsBlockModels = this.testTarget.getJdtsBlockModels();

        /* 検証の実施 */
        Assertions.assertEquals(expectedOrgCode, actualOrgCode, "オリジナルコードが正しく設定されること");
        Assertions.assertNotNull(actualJdtsBlockModels, "Javadocブロックモデルリストがnullでないこと");
        Assertions.assertEquals(expectedJdtsBlockModelsSize, actualJdtsBlockModels.size(),
            "初期状態では空のJavadocブロックモデルリストが設定されること");

    }

    /**
     * constructor メソッドのテスト - 正常系:空文字列でのコンストラクタ
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalEmptyCode() {

        /* 期待値の定義 */
        final String expectedOrgCode             = "";
        final int    expectedJdtsBlockModelsSize = 0;

        /* 準備 */
        final String testCode = "";

        /* テスト対象の実行 */
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* 検証の準備 */
        final String               actualOrgCode         = this.testTarget.getOrgCode();
        final List<JdtsBlockModel> actualJdtsBlockModels = this.testTarget.getJdtsBlockModels();

        /* 検証の実施 */
        Assertions.assertEquals(expectedOrgCode, actualOrgCode, "空文字列のオリジナルコードが正しく設定されること");
        Assertions.assertNotNull(actualJdtsBlockModels, "Javadocブロックモデルリストがnullでないこと");
        Assertions.assertEquals(expectedJdtsBlockModelsSize, actualJdtsBlockModels.size(),
            "初期状態では空のJavadocブロックモデルリストが設定されること");

    }

    /**
     * constructor メソッドのテスト - 正常系:nullでのコンストラクタ
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalNullCode() {

        /* 期待値の定義 */
        final String expectedOrgCode             = null;
        final int    expectedJdtsBlockModelsSize = 0;

        /* 準備 */
        final String testCode = null;

        /* テスト対象の実行 */
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* 検証の準備 */
        final String               actualOrgCode         = this.testTarget.getOrgCode();
        final List<JdtsBlockModel> actualJdtsBlockModels = this.testTarget.getJdtsBlockModels();

        /* 検証の実施 */
        Assertions.assertEquals(expectedOrgCode, actualOrgCode, "nullのオリジナルコードが正しく設定されること");
        Assertions.assertNotNull(actualJdtsBlockModels, "Javadocブロックモデルリストがnullでないこと");
        Assertions.assertEquals(expectedJdtsBlockModelsSize, actualJdtsBlockModels.size(),
            "初期状態では空のJavadocブロックモデルリストが設定されること");

    }

    /**
     * getJdtsBlockModels メソッドのテスト - 正常系:parse後の状態でのリスト取得
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetJdtsBlockModels_normalAfterParse() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedSize = 2;

        /* 準備 */
        final String testCode
            = "/**\n * テストクラス\n */\npublic class TestClass {\n\n    /**\n     * テストメソッド\n     */\n    public void testMethod() {\n    }\n}";
        this.testTarget = new JdtsCodeModelImpl(testCode);
        this.testTarget.parse();

        /* テスト対象の実行 */
        final List<JdtsBlockModel> testResult = this.testTarget.getJdtsBlockModels();

        /* 検証の準備 */
        final List<JdtsBlockModel> actualJdtsBlockModels = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualJdtsBlockModels, "Javadocブロックモデルリストがnullでないこと");
        Assertions.assertEquals(expectedSize, actualJdtsBlockModels.size(), "parse後は期待される数のJavadocブロックモデルリストが返されること");

    }

    /**
     * getJdtsBlockModels メソッドのテスト - 正常系:初期状態での空リスト取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetJdtsBlockModels_normalEmptyList() {

        /* 期待値の定義 */
        final int expectedSize = 0;

        /* 準備 */
        final String testCode = "/** テストJavadoc */\npublic class TestClass {}";
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* テスト対象の実行 */
        final List<JdtsBlockModel> testResult = this.testTarget.getJdtsBlockModels();

        /* 検証の準備 */
        final List<JdtsBlockModel> actualJdtsBlockModels = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualJdtsBlockModels, "Javadocブロックモデルリストがnullでないこと");
        Assertions.assertEquals(expectedSize, actualJdtsBlockModels.size(), "初期状態では空のJavadocブロックモデルリストが返されること");

    }

    /**
     * getOrgCode メソッドのテスト - 正常系:nullオリジナルコード取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetOrgCode_normalReturnNullOriginalCode() {

        /* 期待値の定義 */
        final String expectedOrgCode = null;

        /* 準備 */
        final String testCode = null;
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getOrgCode();

        /* 検証の準備 */
        final String actualOrgCode = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedOrgCode, actualOrgCode, "nullのオリジナルコードが正しく返されること");

    }

    /**
     * getOrgCode メソッドのテスト - 正常系:オリジナルコード取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetOrgCode_normalReturnOriginalCode() {

        /* 期待値の定義 */
        final String expectedOrgCode = "/** テストJavadoc */\npublic class TestClass {}";

        /* 準備 */
        final String testCode = "/** テストJavadoc */\npublic class TestClass {}";
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getOrgCode();

        /* 検証の準備 */
        final String actualOrgCode = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedOrgCode, actualOrgCode, "オリジナルコードが正しく返されること");

    }

    /**
     * jdtsBlockModels フィールドのテスト - privateフィールドの値確認
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testJdtsBlockModelsField_normalPrivateFieldAccess() throws Exception {

        /* 期待値の定義 */
        final int expectedSize = 0;

        /* 準備 */
        final String testCode = "/** テストJavadoc */\npublic class TestClass {}";
        this.testTarget = new JdtsCodeModelImpl(testCode);
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト対象の実行 */
        final Object testResult = this.reflectionModel.get("jdtsBlockModels");

        /* 検証の準備 */
        @SuppressWarnings("unchecked")
        final List<JdtsBlockModel> actualJdtsBlockModels = (List<JdtsBlockModel>) testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualJdtsBlockModels, "privateフィールドjdtsBlockModelsがnullでないこと");
        Assertions.assertEquals(expectedSize, actualJdtsBlockModels.size(),
            "初期状態ではprivateフィールドjdtsBlockModelsは空のリストであること");

    }

    /**
     * orgCode フィールドのテスト - privateフィールドの値確認
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testOrgCodeField_normalPrivateFieldAccess() throws Exception {

        /* 期待値の定義 */
        final String expectedOrgCode = "/** テストJavadoc */\npublic class TestClass {}";

        /* 準備 */
        final String testCode = "/** テストJavadoc */\npublic class TestClass {}";
        this.testTarget = new JdtsCodeModelImpl(testCode);
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト対象の実行 */
        final Object testResult = this.reflectionModel.get("orgCode");

        /* 検証の準備 */
        final String actualOrgCode = (String) testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedOrgCode, actualOrgCode, "privateフィールドorgCodeに正しい値が設定されていること");

    }

    /**
     * parse メソッドのテスト - 異常系:parse実行時にJdtsBlockModelImplのparseが例外を投げる場合
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testParse_errorJdtsBlockModelImplParseException() throws KmgToolMsgException {

        /* 期待値の定義 */
        final String             expectedDomainMessage = "[NONE] ";
        final KmgToolGenMsgTypes expectedMessageTypes  = KmgToolGenMsgTypes.NONE;

        /* 準備 */
        final String testCode = "/**\n * テストクラス\n */\npublic class TestClass {}";
        this.testTarget = new JdtsCodeModelImpl(testCode);

        // SpringApplicationContextHelperを先にモック化
        try (final MockedStatic<SpringApplicationContextHelper> mockedStatic
            = Mockito.mockStatic(SpringApplicationContextHelper.class)) {

            mockedStatic.when(() -> SpringApplicationContextHelper.getBean(KmgMessageSource.class))
                .thenReturn(this.mockMessageSource);

            // モックメッセージソースの設定
            Mockito.when(this.mockMessageSource.getExcMessage(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedDomainMessage);

            // 例外インスタンスを先に作成
            final KmgToolMsgException testException = new KmgToolMsgException(KmgToolGenMsgTypes.NONE, new Object[] {});

            // JdtsBlockModelImplのコンストラクタをモック化
            try (final MockedConstruction<JdtsBlockModelImpl> mockedConstruction
                = Mockito.mockConstruction(JdtsBlockModelImpl.class, (mock, context) -> {

                    // parseメソッドが例外を投げるように設定（先に作成した例外を使用）
                    Mockito.doThrow(testException).when(mock).parse();

                })) {

                /* テスト対象の実行 */
                final KmgToolMsgException actualException
                    = Assertions.assertThrows(KmgToolMsgException.class, () -> this.testTarget.parse());

                /* 検証の準備 */
                /* 検証の実施 */
                // TODO KenichiroArai 2025/06/21 Exceptionを期待値として定義する。他も同様に対応する。
                this.verifyKmgMsgException(actualException, expectedDomainMessage, expectedMessageTypes);

            }

        }

    }

    /**
     * parse メソッドのテスト - 正常系:連続するJavadocブロックの解析
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testParse_normalConsecutiveJavadocBlocks() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedJdtsBlockModelsSize = 3;

        /* 準備 */
        final String testCode
            = "/** テスト1 */\nclass Test1 {}\n\n/** テスト2 */\nclass Test2 {}\n\n/** テスト3 */\nclass Test3 {}";
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* テスト対象の実行 */
        this.testTarget.parse();

        /* 検証の準備 */
        final List<JdtsBlockModel> actualJdtsBlockModels = this.testTarget.getJdtsBlockModels();

        /* 検証の実施 */
        Assertions.assertEquals(expectedJdtsBlockModelsSize, actualJdtsBlockModels.size(), "連続するJavadocブロックが全て解析されること");

    }

    /**
     * parse メソッドのテスト - 正常系:空文字列の解析
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testParse_normalEmptyString() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedJdtsBlockModelsSize = 0;

        /* 準備 */
        final String testCode = "";
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* テスト対象の実行 */
        this.testTarget.parse();

        /* 検証の準備 */
        final List<JdtsBlockModel> actualJdtsBlockModels = this.testTarget.getJdtsBlockModels();

        /* 検証の実施 */
        Assertions.assertEquals(expectedJdtsBlockModelsSize, actualJdtsBlockModels.size(), "空文字列の場合は空のリストが保持されること");

    }

    /**
     * parse メソッドのテスト - 正常系:Javadocブロック開始記号のみの解析
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testParse_normalJavadocStartOnly() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedJdtsBlockModelsSize = 0;

        /* 準備 */
        final String testCode = "/**";
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* テスト対象の実行 */
        this.testTarget.parse();

        /* 検証の準備 */
        final List<JdtsBlockModel> actualJdtsBlockModels = this.testTarget.getJdtsBlockModels();

        /* 検証の実施 */
        Assertions.assertEquals(expectedJdtsBlockModelsSize, actualJdtsBlockModels.size(),
            "Javadocブロック開始記号のみの場合は空のリストが保持されること");

    }

    /**
     * parse メソッドのテスト - 正常系:Javadocブロック開始タグが含まれるが、空白文字での分割結果が1つの場合
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testParse_normalJavadocStartWithoutWhitespace() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedJdtsBlockModelsSize = 0;

        /* 準備 */
        final String testCode = "/**テストコメント";
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* テスト対象の実行 */
        this.testTarget.parse();

        /* 検証の準備 */
        final List<JdtsBlockModel> actualJdtsBlockModels = this.testTarget.getJdtsBlockModels();

        /* 検証の実施 */
        Assertions.assertEquals(expectedJdtsBlockModelsSize, actualJdtsBlockModels.size(),
            "空白文字を含まないJavadocブロック開始の場合は空のリストが保持されること");

    }

    /**
     * parse メソッドのテスト - 正常系:複数Javadocブロックの解析
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testParse_normalMultipleJavadocBlocks() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedJdtsBlockModelsSize = 2;

        /* 準備 */
        final String testCode
            = "/**\n * テストクラス\n */\npublic class TestClass {\n\n    /**\n     * テストメソッド\n     */\n    public void testMethod() {\n    }\n}";
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* テスト対象の実行 */
        this.testTarget.parse();

        /* 検証の準備 */
        final List<JdtsBlockModel> actualJdtsBlockModels = this.testTarget.getJdtsBlockModels();

        /* 検証の実施 */
        Assertions.assertEquals(expectedJdtsBlockModelsSize, actualJdtsBlockModels.size(), "2つのJavadocブロックが解析されること");
        Assertions.assertNotNull(actualJdtsBlockModels.get(0), "1つ目のJavadocブロックモデルが生成されること");
        Assertions.assertNotNull(actualJdtsBlockModels.get(1), "2つ目のJavadocブロックモデルが生成されること");

    }

    /**
     * parse メソッドのテスト - 正常系:Javadocブロックなしの解析
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testParse_normalNoJavadocBlocks() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedJdtsBlockModelsSize = 0;

        /* 準備 */
        final String testCode = "public class TestClass {}";
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* テスト対象の実行 */
        this.testTarget.parse();

        /* 検証の準備 */
        final List<JdtsBlockModel> actualJdtsBlockModels = this.testTarget.getJdtsBlockModels();

        /* 検証の実施 */
        Assertions.assertEquals(expectedJdtsBlockModelsSize, actualJdtsBlockModels.size(),
            "Javadocブロックがない場合は空のリストが保持されること");

    }

    /**
     * parse メソッドのテスト - 正常系:parseメソッド実行後のJdtsBlockModelsリストの状態確認
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testParse_normalParseAfterGetJdtsBlockModels() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedJdtsBlockModelsSize = 1;
        final int expectedBeforeParseSize     = 0;

        /* 準備 */
        final String            testCode        = "/**\n * テストクラス\n */\npublic class TestClass {}";
        final JdtsCodeModelImpl localTestTarget = new JdtsCodeModelImpl(testCode);

        /* テスト対象の実行 */
        // parse実行前の状態を確認（この時点ではリストは空）
        final int                  beforeParseSize    = localTestTarget.getJdtsBlockModels().size();
        final List<JdtsBlockModel> beforeParseListRef = localTestTarget.getJdtsBlockModels();

        localTestTarget.parse();

        final List<JdtsBlockModel> afterParseListRef = localTestTarget.getJdtsBlockModels();
        final int                  afterParseSize    = afterParseListRef.size();

        /* 検証の実施 */
        Assertions.assertEquals(expectedBeforeParseSize, beforeParseSize, "parse実行前は空のリストであること");
        Assertions.assertEquals(expectedJdtsBlockModelsSize, afterParseSize, "parse実行後は1つのブロックが追加されること");
        Assertions.assertSame(beforeParseListRef, afterParseListRef, "同じリストインスタンスが返されること");

        // parse実行後は、以前に取得したリスト参照も変更されていることを確認
        // （getJdtsBlockModelsは同じリストインスタンスの参照を返すため）
        Assertions.assertEquals(expectedJdtsBlockModelsSize, beforeParseListRef.size(),
            "parse実行後は、以前に取得したリスト参照も変更されていること");

    }

    /**
     * parse メソッドのテスト - 正常系:単一Javadocブロックの解析
     *
     * @since 0.1.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testParse_normalSingleJavadocBlock() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedJdtsBlockModelsSize = 1;

        /* 準備 */
        final String testCode = "/**\n * テストクラス\n */\npublic class TestClass {}";
        this.testTarget = new JdtsCodeModelImpl(testCode);

        /* テスト対象の実行 */
        this.testTarget.parse();

        /* 検証の準備 */
        final List<JdtsBlockModel> actualJdtsBlockModels = this.testTarget.getJdtsBlockModels();

        /* 検証の実施 */
        Assertions.assertEquals(expectedJdtsBlockModelsSize, actualJdtsBlockModels.size(), "1つのJavadocブロックが解析されること");
        Assertions.assertNotNull(actualJdtsBlockModels.get(0), "Javadocブロックモデルが生成されること");

    }

}
