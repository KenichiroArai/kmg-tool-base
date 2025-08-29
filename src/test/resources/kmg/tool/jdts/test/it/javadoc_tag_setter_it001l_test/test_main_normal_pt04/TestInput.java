package kmg.tool.jdts.application.model.impl;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.types.JavaClassificationTypes;
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
        final String testBlock = """            
            /** テストJavadoc */
            public class TestClass {
        """;
        this.testTarget = new JdtsBlockModelImpl(testBlock);

        /* テスト対象の実行 */
        final List<String> testResult = this.testTarget.getAnnotations();

        /* 検証の準備 */
        final List<String> actualAnnotations = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualAnnotations, "アノテーションリストがnullでないこと");
        Assertions.assertEquals(expectedSize, actualAnnotations.size(), "初期状態では空のアノテーションリストが返されること");

    }

}
