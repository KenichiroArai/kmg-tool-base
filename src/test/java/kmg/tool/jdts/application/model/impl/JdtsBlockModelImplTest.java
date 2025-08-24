package kmg.tool.jdts.application.model.impl;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.jdoc.domain.model.JavadocModel;

/**
 * Javadocタグ設定のブロックモデル実装のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SuppressWarnings({
    "nls",
})
public class JdtsBlockModelImplTest extends AbstractKmgTest {

    /** テスト対象 */
    private JdtsBlockModelImpl testTarget;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * テスト前処理<br>
     *
     * @since 0.1.0
     */
    @BeforeEach
    public void setUp() {

        // 処理なし

    }

    /**
     * getAnnotations メソッドのテスト - 正常系:初期状態での空リスト取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetAnnotations_normalEmptyList() {

        /* 期待値の定義 */
        final int expectedSize = 0;

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final List<String> testResult = this.testTarget.getAnnotations();

        /* 検証の準備 */
        final List<String> actualAnnotations = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualAnnotations, "アノテーションリストがnullでないこと");
        Assertions.assertEquals(expectedSize, actualAnnotations.size(), "初期状態では空のアノテーションリストが返されること");

    }

    /**
     * getClassification メソッドのテスト - 正常系:初期状態でのNONE取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetClassification_normalInitialNone() {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification = JavaClassificationTypes.NONE;

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final JavaClassificationTypes testResult = this.testTarget.getClassification();

        /* 検証の準備 */
        final JavaClassificationTypes actualClassification = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedClassification, actualClassification, "初期状態では区分がNONEであること");

    }

    /**
     * getElementName メソッドのテスト - 正常系:初期状態でのnull取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetElementName_normalInitialNull() {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getElementName();

        /* 検証の準備 */
        final String actualElementName = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualElementName, "初期状態では要素名がnullであること");

    }

    /**
     * getId メソッドのテスト - 正常系:一意な識別子取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetId_normalUniqueId() {

        /* 期待値の定義 */
        // UUIDは動的に生成されるため、nullでないことと他のインスタンスと異なることを検証

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);
        final JdtsBlockModelImpl testTarget2 = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final UUID testResult  = this.testTarget.getId();
        final UUID testResult2 = testTarget2.getId();

        /* 検証の準備 */
        final UUID actualId  = testResult;
        final UUID actualId2 = testResult2;

        /* 検証の実施 */
        Assertions.assertNotNull(actualId, "識別子がnullでないこと");
        Assertions.assertNotEquals(actualId, actualId2, "異なるインスタンスでは異なる識別子が生成されること");

    }

    /**
     * getJavadocModel メソッドのテスト - 正常系:初期状態でのnull取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetJavadocModel_normalInitialNull() {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final JavadocModel testResult = this.testTarget.getJavadocModel();

        /* 検証の準備 */
        final JavadocModel actualJavadocModel = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualJavadocModel, "初期状態ではJavadocモデルがnullであること");

    }

    /**
     * getOrgBlock メソッドのテスト - 正常系:オリジナルブロック取得
     *
     * @since 0.1.0
     */
    @Test
    public void testGetOrgBlock_normalReturnOriginalBlock() {

        /* 期待値の定義 */
        final String expectedOrgBlock = "/** テストJavadoc */\npublic class TestClass {";

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getOrgBlock();

        /* 検証の準備 */
        final String actualOrgBlock = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedOrgBlock, actualOrgBlock, "オリジナルブロックが正しく返されること");

    }

    /**
     * parse メソッドのテスト - 異常系:アノテーションと空白のみのブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalAnnotationsAndWhitespaceOnly() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n@Component\n\n@Service\n\n";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "アノテーションと空白のみのブロックでも解析が成功すること");

    }

    /**
     * parse メソッドのテスト - 異常系:アノテーションのみでコードがないブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalAnnotationsOnly() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n@Component\n@Service";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "アノテーションのみでコードがないブロックでも解析が成功すること");

    }

    /**
     * parse メソッドのテスト - 異常系:コメントのみのブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalCommentsOnly() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n// コメント行\n/* ブロックコメント */";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "コメントのみのブロックでも解析が成功すること");

    }

    /**
     * parse メソッドのテスト - 異常系:空のブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalEmptyBlock() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "空のブロックでは解析が失敗すること");

    }

    /**
     * parse メソッドのテスト - 異常系:コードブロックが空のブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalEmptyCodeBlock() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "コードブロックが空のブロックでも解析が成功すること");

    }

    /**
     * parse メソッドのテスト - 異常系:不完全なメソッド定義のブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalIncompleteMethodDefinition() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\npublic String testMethod() { // 不完全なメソッド定義";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "不完全なメソッド定義のブロックでも解析が成功すること");

    }

    /**
     * parse メソッドのテスト - 異常系:無効なJavaコードのブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalInvalidJavaCode() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\npublic class { // 無効なクラス定義";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "無効なJavaコードのブロックでも解析が成功すること");

    }

    /**
     * parse メソッドのテスト - 異常系:Javadoc終了記号が先頭にあるブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalJavadocEndAtBeginning() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "*/ public class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "Javadoc終了記号が先頭にあるブロックでも解析が成功すること");

    }

    /**
     * parse メソッドのテスト - 異常系:Javadoc終了記号のみのブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalJavadocEndOnly() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "*/";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "Javadoc終了記号のみのブロックでは解析が失敗すること");

    }

    /**
     * parse メソッドのテスト - 異常系:Javadocブロックのみでコードブロックがない
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalJavadocOnly() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // Javadocブロックのみでコードブロックがない
        final String testBlock = "/** テストJavadoc */";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "Javadocブロックのみでは解析が失敗すること");

    }

    /**
     * parse メソッドのテスト - 異常系:Javadoc開始記号のみのブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalJavadocStartOnly() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/**";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "Javadoc開始記号のみのブロックでは解析が失敗すること");

    }

    /**
     * parse メソッドのテスト - 異常系: *&#47; が含まれていないブロック
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testParse_abnormalNoJavadocEndMarker() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        // 「*/」が含まれていないブロック
        final String testBlock = "/** テストJavadoc\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "「*/」が含まれていないブロックでは解析が失敗すること");

    }

    /**
     * parse メソッドのテスト - 異常系:nullブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalNullBlock() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = null;
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(NullPointerException.class, () -> {

            this.testTarget.parse();

        }, "nullブロックではNullPointerExceptionが発生すること");

    }

    /**
     * parse メソッドのテスト - 異常系:特殊文字を含むブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalSpecialCharactersInBlock() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\npublic class TestClass {\n\t// タブ文字を含む\n    // スペース文字を含む\n}";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "特殊文字を含むブロックでも解析が成功すること");

    }

    /**
     * parse メソッドのテスト - 異常系:特殊文字のみのブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalSpecialCharactersOnly() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "!@#$%^&*()";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "特殊文字のみのブロックでは解析が失敗すること");

    }

    /**
     * parse メソッドのテスト - 異常系:コードブロックが空白のみのブロック
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_abnormalWhitespaceOnlyCodeBlock() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n   \n\t\n";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "コードブロックが空白のみのブロックでも解析が成功すること");

    }

    /**
     * parse メソッドのテスト - 正常系:アノテーション使用の区分でも解析は成功する
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalAnnotationUsageButParseSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n@Test";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "アノテーション使用の区分でも解析が成功すること");

    }

    /**
     * parse メソッドのテスト - 正常系:クラス定義の解析
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalClassDefinition() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification = JavaClassificationTypes.CLASS;
        final String                  expectedElementName    = "TestClass";

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();
        final JavadocModel            actualJavadocModel   = this.testTarget.getJavadocModel();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がCLASSであること");
        Assertions.assertEquals(expectedElementName, actualElementName, "要素名が正しく取得されること");
        Assertions.assertNotNull(actualJavadocModel, "Javadocモデルが作成されること");

    }

    /**
     * parse メソッドのテスト - 正常系:空行を含むコードブロックのクラス定義解析
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalClassDefinitionWithBlankLines() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification = JavaClassificationTypes.CLASS;
        final String                  expectedElementName    = "TestClass";

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n\n\npublic class TestClass {\n\n    // メソッド\n\n}";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();
        final JavadocModel            actualJavadocModel   = this.testTarget.getJavadocModel();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がCLASSであること");
        Assertions.assertEquals(expectedElementName, actualElementName, "要素名が正しく取得されること");
        Assertions.assertNotNull(actualJavadocModel, "Javadocモデルが作成されること");

    }

    /**
     * parse メソッドのテスト - 正常系:アノテーション付きクラス定義の解析
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalClassWithAnnotations() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification  = JavaClassificationTypes.CLASS;
        final String                  expectedElementName     = "TestClass";
        final int                     expectedAnnotationCount = 2;
        final String                  expectedAnnotation1     = "@Component";
        final String                  expectedAnnotation2     = "@Service";

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n@Component\n@Service\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();
        final List<String>            actualAnnotations    = this.testTarget.getAnnotations();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がCLASSであること");
        Assertions.assertEquals(expectedElementName, actualElementName, "要素名が正しく取得されること");
        Assertions.assertEquals(expectedAnnotationCount, actualAnnotations.size(), "アノテーション数が正しいこと");
        Assertions.assertEquals(expectedAnnotation1, actualAnnotations.get(0), "1番目のアノテーションが正しく取得されること");
        Assertions.assertEquals(expectedAnnotation2, actualAnnotations.get(1), "2番目のアノテーションが正しく取得されること");

    }

    /**
     * parse メソッドのテスト - 正常系:空行を含むアノテーション付きクラス定義の解析
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalClassWithAnnotationsAndBlankLines() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification  = JavaClassificationTypes.CLASS;
        final String                  expectedElementName     = "TestClass";
        final int                     expectedAnnotationCount = 2;
        final String                  expectedAnnotation1     = "@Component";
        final String                  expectedAnnotation2     = "@Service";

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n@Component\n\n@Service\n\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();
        final List<String>            actualAnnotations    = this.testTarget.getAnnotations();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がCLASSであること");
        Assertions.assertEquals(expectedElementName, actualElementName, "要素名が正しく取得されること");
        Assertions.assertEquals(expectedAnnotationCount, actualAnnotations.size(), "アノテーション数が正しいこと");
        Assertions.assertEquals(expectedAnnotation1, actualAnnotations.get(0), "1番目のアノテーションが正しく取得されること");
        Assertions.assertEquals(expectedAnnotation2, actualAnnotations.get(1), "2番目のアノテーションが正しく取得されること");

    }

    /**
     * parse メソッドのテスト - 正常系:無効なクラス定義でもJavadoc対象外として正常処理される
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalInvalidClassDefinition() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification = JavaClassificationTypes.NONE;

        /* 準備 */
        // 無効なクラス定義（クラス名なし）
        final String testBlock = "/** テストJavadoc */\npublic class {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がNONEであること");
        Assertions.assertNull(actualElementName, "要素名がnullであること");

    }

    /**
     * parse メソッドのテスト - 正常系:メソッド定義の解析
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalMethodDefinition() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification = JavaClassificationTypes.METHOD;
        final String                  expectedElementName    = "testMethod";

        /* 準備 */
        final String testBlock = "/** テストメソッドのJavadoc */\npublic String testMethod() {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がMETHODであること");
        Assertions.assertEquals(expectedElementName, actualElementName, "要素名が正しく取得されること");

    }

    /**
     * parse メソッドのテスト - 正常系:メソッド定義で空行を含む解析
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalMethodDefinitionWithBlankLines() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification = JavaClassificationTypes.METHOD;
        final String                  expectedElementName    = "testMethod";

        /* 準備 */
        final String testBlock
            = "/** テストメソッドのJavadoc */\n\n\npublic String testMethod() {\n\n    return \"test\";\n\n}";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がMETHODであること");
        Assertions.assertEquals(expectedElementName, actualElementName, "要素名が正しく取得されること");

    }

    /**
     * parse メソッドのテスト - 正常系:アノテーションの複数行処理
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalMultilineAnnotation() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification  = JavaClassificationTypes.CLASS;
        final String                  expectedElementName     = "TestClass";
        final int                     expectedAnnotationCount = 3;
        final String                  expectedAnnotation1     = "@Component";
        final String                  expectedAnnotation2     = """
            @RequestMapping({
            "/api\",
            "/v1\"
            })""".replaceAll(KmgDelimiterTypes.REGEX_LINE_SEPARATOR.get(), KmgString.LINE_SEPARATOR);
        final String                  expectedAnnotation3     = "@Service";

        /* 準備 */
        final String testBlock = """
            /** テストJavadoc */
            @Component\n@RequestMapping({
                \"/api\",
                \"/v1\"
            })
            @Service
            public class TestClass {
                            """;

        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();
        final List<String>            actualAnnotations    = this.testTarget.getAnnotations();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がCLASSであること");
        Assertions.assertEquals(expectedElementName, actualElementName, "要素名が正しく取得されること");
        Assertions.assertEquals(expectedAnnotationCount, actualAnnotations.size(), "アノテーション数が正しいこと");
        Assertions.assertEquals(expectedAnnotation1, actualAnnotations.get(0), "1番目のアノテーションが正しく取得されること");
        Assertions.assertEquals(expectedAnnotation2, actualAnnotations.get(1), "複数行のアノテーションが正しく取得されること");
        Assertions.assertEquals(expectedAnnotation3, actualAnnotations.get(2), "複数行終了後のアノテーションが正しく取得されること");

    }

    /**
     * parse メソッドのテスト - 正常系:複数行アノテーションの処理でcontinue文のカバレッジ
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalMultilineAnnotationContinueCoverage() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification  = JavaClassificationTypes.CLASS;
        final String                  expectedElementName     = "TestClass";
        final int                     expectedAnnotationCount = 1;
        final String                  expectedAnnotation      = "@Component";

        /* 準備 */
        // 複数行アノテーションの開始後、終了前に通常のアノテーションが来るケース
        final String testBlock
            = "/** テストJavadoc */\n@RequestMapping({\n    \"/api\"\n@Component\n})\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();
        final List<String>            actualAnnotations    = this.testTarget.getAnnotations();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がCLASSであること");
        Assertions.assertEquals(expectedElementName, actualElementName, "要素名が正しく取得されること");
        Assertions.assertEquals(expectedAnnotationCount, actualAnnotations.size(), "アノテーション数が正しいこと");
        Assertions.assertEquals(expectedAnnotation, actualAnnotations.get(0), "通常のアノテーションが正しく取得されること");

    }

    /**
     * parse メソッドのテスト - 正常系:複数行アノテーションの終了記号が行の途中にある場合
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalMultilineAnnotationEndInMiddleOfLine() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification  = JavaClassificationTypes.ANNOTATION_USAGE;
        final int                     expectedAnnotationCount = 0;

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n@RequestMapping({\n    \"/api\"\n}) @Service";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final List<String>            actualAnnotations    = this.testTarget.getAnnotations();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がANNOTATION_USAGEであること");
        Assertions.assertEquals(expectedAnnotationCount, actualAnnotations.size(), "アノテーション数が正しいこと");

    }

    /**
     * parse メソッドのテスト - 正常系:複数行アノテーションの後に通常のアノテーションが続く場合
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalMultilineAnnotationFollowedByNormalAnnotation() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification  = JavaClassificationTypes.CLASS;
        final String                  expectedElementName     = "TestClass";
        final int                     expectedAnnotationCount = 1;
        final String                  expectedAnnotation      = "@Service";

        /* 準備 */
        final String testBlock
            = "/** テストJavadoc */\n@RequestMapping({\n    \"/api\"\n})\n@Service\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();
        final List<String>            actualAnnotations    = this.testTarget.getAnnotations();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がCLASSであること");
        Assertions.assertEquals(expectedElementName, actualElementName, "要素名が正しく取得されること");
        Assertions.assertEquals(expectedAnnotationCount, actualAnnotations.size(), "アノテーション数が正しいこと");
        Assertions.assertEquals(expectedAnnotation, actualAnnotations.get(0), "複数行終了後の通常アノテーションが正しく取得されること");

    }

    /**
     * parse メソッドのテスト - 正常系:複数行アノテーションの途中で終了する場合
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalMultilineAnnotationIncomplete() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification  = JavaClassificationTypes.ANNOTATION_USAGE;
        final int                     expectedAnnotationCount = 0;

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n@RequestMapping({\n    \"/api\"\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final List<String>            actualAnnotations    = this.testTarget.getAnnotations();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がANNOTATION_USAGEであること");
        Assertions.assertEquals(expectedAnnotationCount, actualAnnotations.size(), "アノテーション数が正しいこと");

    }

    /**
     * parse メソッドのテスト - 正常系:複数行アノテーションのみでコードがない場合
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalMultilineAnnotationOnly() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification  = JavaClassificationTypes.ANNOTATION_USAGE;
        final int                     expectedAnnotationCount = 0;

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n@RequestMapping({\n    \"/api\",\n    \"/v1\"\n})";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final List<String>            actualAnnotations    = this.testTarget.getAnnotations();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がANNOTATION_USAGEであること");
        Assertions.assertEquals(expectedAnnotationCount, actualAnnotations.size(), "アノテーション数が正しいこと");

    }

    /**
     * parse メソッドのテスト - 正常系:複数行アノテーションの処理でannotationMultilineに追加されるがannotationsには追加されない
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalMultilineAnnotationProcessing() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification  = JavaClassificationTypes.CLASS;
        final String                  expectedElementName     = "TestClass";
        final int                     expectedAnnotationCount = 0;

        /* 準備 */
        // 複数行アノテーションのみで、コードブロックに到達しないケース
        final String testBlock
            = "/** テストJavadoc */\n@RequestMapping({\n    \"/api\",\n    \"/v1\"\n})\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();
        final List<String>            actualAnnotations    = this.testTarget.getAnnotations();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がCLASSであること");
        Assertions.assertEquals(expectedElementName, actualElementName, "要素名が正しく取得されること");
        Assertions.assertEquals(expectedAnnotationCount, actualAnnotations.size(), "複数行アノテーションはannotationsに追加されないこと");

    }

    /**
     * parse メソッドのテスト - 正常系:複数行アノテーション内に空行が含まれる場合
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalMultilineAnnotationWithBlankLines() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification  = JavaClassificationTypes.CLASS;
        final String                  expectedElementName     = "TestClass";
        final int                     expectedAnnotationCount = 0;

        /* 準備 */
        final String testBlock
            = "/** テストJavadoc */\n@RequestMapping({\n\n    \"/api\",\n\n    \"/v1\"\n\n})\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();
        final List<String>            actualAnnotations    = this.testTarget.getAnnotations();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がCLASSであること");
        Assertions.assertEquals(expectedElementName, actualElementName, "要素名が正しく取得されること");
        Assertions.assertEquals(expectedAnnotationCount, actualAnnotations.size(), "アノテーション数が正しいこと");

    }

    /**
     * parse メソッドのテスト - 正常系:Javadoc対象外の区分
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalNonJavadocTarget() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification = JavaClassificationTypes.NONE;

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n// コメント行";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "解析が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がNONEであること");
        Assertions.assertNull(actualElementName, "要素名がnullであること");

    }

    /**
     * parse メソッドのテスト - 正常系:Javadoc対象外の区分（NONE）でも解析は成功する
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testParse_normalNonJavadocTargetButParseSuccess() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n// コメント行";
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.parse();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "Javadoc対象外の区分でも解析が成功すること");

    }

    /**
     * specifyClassification メソッドのテスト - 正常系:クラス定義の区分特定
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testSpecifyClassification_normalClassClassification() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification = JavaClassificationTypes.CLASS;
        final String                  expectedElementName    = "TestClass";

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\npublic class TestClass {";
        this.testTarget = new JdtsBlockModelImpl(testBlock);
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // codeBlockを直接設定
        this.reflectionModel.set("codeBlock", "public class TestClass {");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("specifyClassification");

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "区分の特定が成功すること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がCLASSであること");
        Assertions.assertEquals(expectedElementName, actualElementName, "要素名が正しく取得されること");

    }

    /**
     * specifyClassification メソッドのテスト - 正常系:無効なクラス定義でもNONE区分として正常処理される
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testSpecifyClassification_normalInvalidClassDefinition() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification = JavaClassificationTypes.NONE;

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\npublic class {"; // 無効なクラス定義
        this.testTarget = new JdtsBlockModelImpl(testBlock);
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // codeBlockを直接設定
        this.reflectionModel.set("codeBlock", "public class {");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("specifyClassification");

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();
        final String                  actualElementName    = this.testTarget.getElementName();

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "無効な定義の場合はfalseが返されること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がNONEであること");
        Assertions.assertNull(actualElementName, "要素名がnullであること");

    }

    /**
     * specifyClassification メソッドのテスト - 正常系:Javadoc対象外の区分
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testSpecifyClassification_normalNonJavadocTargetClassification() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification = JavaClassificationTypes.NONE;

        /* 準備 */
        final String testBlock = "/** テストJavadoc */\n// コメント行";
        this.testTarget = new JdtsBlockModelImpl(testBlock);
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // codeBlockを直接設定
        this.reflectionModel.set("codeBlock", "// コメント行");

        /* テスト対象の実行 */
        final boolean testResult = (Boolean) this.reflectionModel.getMethod("specifyClassification");

        /* 検証の準備 */
        final boolean                 actualResult         = testResult;
        final JavaClassificationTypes actualClassification = this.testTarget.getClassification();

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "Javadoc対象外の場合はfalseが返されること");
        Assertions.assertEquals(expectedClassification, actualClassification, "区分がNONEのままであること");

    }

}
