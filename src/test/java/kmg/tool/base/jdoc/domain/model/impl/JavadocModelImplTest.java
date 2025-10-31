package kmg.tool.base.jdoc.domain.model.impl;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.jdoc.domain.model.JavadocTagModel;
import kmg.tool.base.jdoc.domain.model.JavadocTagsModel;

/**
 * Javadocモデル実装のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@SuppressWarnings({
    "nls",
})
public class JavadocModelImplTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.2.0
     */
    private JavadocModelImpl testTarget;

    /**
     * テスト前処理<br>
     *
     * @since 0.2.0
     */
    @BeforeEach
    public void setUp() {

        /* テスト対象のクリア */
        this.testTarget = null;

    }

    /**
     * constructor メソッドのテスト - 正常系:空文字列でのコンストラクタ
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testConstructor_normalEmptyJavadoc() throws KmgToolMsgException {

        /* 期待値の定義 */
        final String expectedSrcJavadoc = "";

        /* 準備 */
        final String testJavadoc = "";

        /* テスト対象の実行 */
        this.testTarget = new JavadocModelImpl(testJavadoc);

        /* 検証の準備 */
        final String actualSrcJavadoc = this.testTarget.getSrcJavadoc();

        /* 検証の実施 */
        Assertions.assertEquals(expectedSrcJavadoc, actualSrcJavadoc, "空文字列のJavadocが正しく設定されること");

    }

    /**
     * constructor メソッドのテスト - 正常系:通常のJavadocでのコンストラクタ
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testConstructor_normalJavadoc() throws KmgToolMsgException {

        /* 期待値の定義 */
        final String expectedSrcJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n */";

        /* 準備 */
        final String testJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n */";

        /* テスト対象の実行 */
        this.testTarget = new JavadocModelImpl(testJavadoc);

        /* 検証の準備 */
        final String actualSrcJavadoc = this.testTarget.getSrcJavadoc();

        /* 検証の実施 */
        Assertions.assertEquals(expectedSrcJavadoc, actualSrcJavadoc, "元のJavadocが正しく設定されること");

    }

    /**
     * getJavadocTagsModel メソッドのテスト - 正常系:空文字列のJavadocでのタグ一覧取得
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetJavadocTagsModel_normalEmptyJavadoc() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedTagCount = 0;

        /* 準備 */
        final String testJavadoc = "";
        this.testTarget = new JavadocModelImpl(testJavadoc);

        /* テスト対象の実行 */
        final JavadocTagsModel testResult = this.testTarget.getJavadocTagsModel();

        /* 検証の準備 */
        final JavadocTagsModel      actualJavadocTagsModel = testResult;
        final List<JavadocTagModel> actualTagList          = actualJavadocTagsModel.getJavadocTagModelList();

        /* 検証の実施 */
        Assertions.assertNotNull(actualJavadocTagsModel, "Javadocタグ一覧情報がnullでないこと");
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "空文字列の場合は空のリストが返されること");

    }

    /**
     * getJavadocTagsModel メソッドのテスト - 正常系:複数のタグを含むJavadocでのタグ一覧取得
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetJavadocTagsModel_normalJavadocWithMultipleTags() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedTagCount = 1;

        /* 準備 */
        final String testJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n * @version 0.2.0\n */";
        this.testTarget = new JavadocModelImpl(testJavadoc);

        /* テスト対象の実行 */
        final JavadocTagsModel testResult = this.testTarget.getJavadocTagsModel();

        /* 検証の準備 */
        final JavadocTagsModel      actualJavadocTagsModel = testResult;
        final List<JavadocTagModel> actualTagList          = actualJavadocTagsModel.getJavadocTagModelList();

        /* 検証の実施 */
        Assertions.assertNotNull(actualJavadocTagsModel, "Javadocタグ一覧情報がnullでないこと");
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "期待される数のタグが取得されること");

    }

    /**
     * getJavadocTagsModel メソッドのテスト - 正常系:タグなしのJavadocでのタグ一覧取得
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetJavadocTagsModel_normalJavadocWithoutTags() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedTagCount = 0;

        /* 準備 */
        final String testJavadoc = "/**\n * テストクラス\n */";
        this.testTarget = new JavadocModelImpl(testJavadoc);

        /* テスト対象の実行 */
        final JavadocTagsModel testResult = this.testTarget.getJavadocTagsModel();

        /* 検証の準備 */
        final JavadocTagsModel      actualJavadocTagsModel = testResult;
        final List<JavadocTagModel> actualTagList          = actualJavadocTagsModel.getJavadocTagModelList();

        /* 検証の実施 */
        Assertions.assertNotNull(actualJavadocTagsModel, "Javadocタグ一覧情報がnullでないこと");
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "タグなしの場合は空のリストが返されること");

    }

    /**
     * getJavadocTagsModel メソッドのテスト - 正常系:通常のJavadocでのタグ一覧取得
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetJavadocTagsModel_normalJavadocWithTags() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedTagCount = 1;

        /* 準備 */
        final String testJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n */";
        this.testTarget = new JavadocModelImpl(testJavadoc);

        /* テスト対象の実行 */
        final JavadocTagsModel testResult = this.testTarget.getJavadocTagsModel();

        /* 検証の準備 */
        final JavadocTagsModel      actualJavadocTagsModel = testResult;
        final List<JavadocTagModel> actualTagList          = actualJavadocTagsModel.getJavadocTagModelList();

        /* 検証の実施 */
        Assertions.assertNotNull(actualJavadocTagsModel, "Javadocタグ一覧情報がnullでないこと");
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "期待される数のタグが取得されること");

    }

    /**
     * getJavadocTagsModel メソッドのテスト - 正常系:正規表現パターンに合致する複数タグでのタグ一覧取得
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetJavadocTagsModel_normalJavadocWithValidMultipleTags() throws KmgToolMsgException {

        /* 期待値の定義 */
        final int expectedTagCount = 1;

        /* 準備 */
        final String testJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n * @version 0.2.0\n */";
        this.testTarget = new JavadocModelImpl(testJavadoc);

        /* テスト対象の実行 */
        final JavadocTagsModel testResult = this.testTarget.getJavadocTagsModel();

        /* 検証の準備 */
        final JavadocTagsModel      actualJavadocTagsModel = testResult;
        final List<JavadocTagModel> actualTagList          = actualJavadocTagsModel.getJavadocTagModelList();

        /* 検証の実施 */
        Assertions.assertNotNull(actualJavadocTagsModel, "Javadocタグ一覧情報がnullでないこと");
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "期待される数のタグが取得されること");

    }

    /**
     * getSrcJavadoc メソッドのテスト - 正常系:複雑なJavadocでの元のJavadoc取得
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetSrcJavadoc_normalComplexJavadoc() throws KmgToolMsgException {

        /* 期待値の定義 */
        final String expectedSrcJavadoc
            = "/**\n * 複雑なテストクラス\n * 詳細な説明\n *\n * @author KenichiroArai\n * @version 0.2.0\n * @since 0.2.0\n * @param param1 パラメータ1の説明\n * @return 戻り値の説明\n * @throws Exception 例外の説明\n */";

        /* 準備 */
        final String testJavadoc
            = "/**\n * 複雑なテストクラス\n * 詳細な説明\n *\n * @author KenichiroArai\n * @version 0.2.0\n * @since 0.2.0\n * @param param1 パラメータ1の説明\n * @return 戻り値の説明\n * @throws Exception 例外の説明\n */";
        this.testTarget = new JavadocModelImpl(testJavadoc);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getSrcJavadoc();

        /* 検証の準備 */
        final String actualSrcJavadoc = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSrcJavadoc, actualSrcJavadoc, "複雑なJavadocの元のJavadocが正しく返されること");

    }

    /**
     * getSrcJavadoc メソッドのテスト - 正常系:空文字列のJavadocでの元のJavadoc取得
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetSrcJavadoc_normalEmptyJavadoc() throws KmgToolMsgException {

        /* 期待値の定義 */
        final String expectedSrcJavadoc = "";

        /* 準備 */
        final String testJavadoc = "";
        this.testTarget = new JavadocModelImpl(testJavadoc);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getSrcJavadoc();

        /* 検証の準備 */
        final String actualSrcJavadoc = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSrcJavadoc, actualSrcJavadoc, "空文字列の元のJavadocが正しく返されること");

    }

    /**
     * getSrcJavadoc メソッドのテスト - 正常系:通常のJavadocでの元のJavadoc取得
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetSrcJavadoc_normalJavadoc() throws KmgToolMsgException {

        /* 期待値の定義 */
        final String expectedSrcJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n */";

        /* 準備 */
        final String testJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n */";
        this.testTarget = new JavadocModelImpl(testJavadoc);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getSrcJavadoc();

        /* 検証の準備 */
        final String actualSrcJavadoc = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSrcJavadoc, actualSrcJavadoc, "元のJavadocが正しく返されること");

    }

}
