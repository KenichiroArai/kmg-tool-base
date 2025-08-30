import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.tool.jdts.application.model.impl.JdtsBlockModelImpl;

public class JdtsBlockModelImplTest extends AbstractKmgTest {

    /**
     * parse メソッドのテスト - 正常系:アノテーションの複数行処理
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testParse_normalMultilineAnnotation() throws Exception {

        /* 期待値の定義 */
        final JavaClassificationTypes expectedClassification  = JavaClassificationTypes.CLASS;
        final String                  expectedElementName     = "TestClass";
        final int                     expectedAnnotationCount = 3;
        final String                  expectedAnnotation1     = "@Component";
        final String                  expectedAnnotation2     = AbstractKmgTest.normalizeLineSeparators("""
            @RequestMapping({
            "/api\",
            "/v1\"
            })""");
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

}
