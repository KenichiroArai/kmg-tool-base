package kmg.tool.base.jdoc.domain.model.impl;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.jdoc.domain.model.JavadocTagModel;

/**
 * Javadocタグ一覧情報実装のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
@SuppressWarnings({
    "nls",
})
public class JavadocTagsModelImplTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.2.0
     */
    private JavadocTagsModelImpl testTarget;

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
     * constructor メソッドのテスト - 正常系:デフォルトコンストラクタでの初期化
     *
     * @since 0.2.0
     */
    @Test
    public void testConstructor_normalDefaultConstructor() {

        /* 期待値の定義 */
        final int expectedTagCount = 0;

        /* 準備 */

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagsModelImpl();

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = this.testTarget.getJavadocTagModelList();

        /* 検証の実施 */
        Assertions.assertNotNull(actualTagList, "タグリストがnullでないこと");
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "初期状態でタグリストが空であること");

    }

    /**
     * constructor メソッドのテスト - 正常系:空文字列のJavadocでのコンストラクタ
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testConstructor_normalEmptyJavadoc() throws KmgToolBaseMsgException {

        /* 期待値の定義 */
        final int expectedTagCount = 0;

        /* 準備 */
        final String testSourceJavadoc = "";

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = this.testTarget.getJavadocTagModelList();

        /* 検証の実施 */
        Assertions.assertNotNull(actualTagList, "タグリストがnullでないこと");
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "空文字列のJavadocでタグリストが空であること");

    }

    /**
     * constructor メソッドのテスト - 正常系:無効なタグのJavadocでのコンストラクタ
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testConstructor_normalInvalidTagJavadoc() throws KmgToolBaseMsgException {

        /* 期待値の定義 */
        final int                expectedTagCount = 1;
        final KmgJavadocTagTypes expectedTag      = KmgJavadocTagTypes.NONE;
        final String             expectedValue    = "invalidTag";

        /* 準備 */
        final String testSourceJavadoc = "/**\n * テストクラス\n * @invalidTag invalidTag\n */";

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = this.testTarget.getJavadocTagModelList();
        final JavadocTagModel       actualTag     = actualTagList.get(0);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "無効なタグが正しく解析されること");
        Assertions.assertEquals(expectedTag, actualTag.getTag(), "無効なタグがNONEとして設定されること");
        Assertions.assertEquals(expectedValue, actualTag.getValue(), "指定値が正しく設定されること");

    }

    /**
     * constructor メソッドのテスト - 正常系:複数行の説明付きタグのJavadocでのコンストラクタ
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testConstructor_normalMultiLineDescriptionJavadoc() throws KmgToolBaseMsgException {

        /* 期待値の定義 */
        final int                expectedTagCount    = 1;
        final KmgJavadocTagTypes expectedTag         = KmgJavadocTagTypes.PARAM;
        final String             expectedValue       = "param1";
        final String             expectedDescription = "パラメータ1の説明";

        /* 準備 */
        final String testSourceJavadoc = "/**\n * テストメソッド\n * @param param1 パラメータ1の説明\n * 複数行の説明\n */";

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = this.testTarget.getJavadocTagModelList();
        final JavadocTagModel       actualTag     = actualTagList.get(0);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "複数行説明付きタグが正しく解析されること");
        Assertions.assertEquals(expectedTag, actualTag.getTag(), "タグが正しく設定されること");
        Assertions.assertEquals(expectedValue, actualTag.getValue(), "指定値が正しく設定されること");
        Assertions.assertEquals(expectedDescription, actualTag.getDescription(), "複数行の説明が正しく設定されること");

    }

    /**
     * constructor メソッドのテスト - 正常系:複数タグのJavadocでのコンストラクタ
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testConstructor_normalMultipleTagsJavadoc() throws KmgToolBaseMsgException {

        /* 期待値の定義 */
        final int expectedTagCount = 1;

        /* 準備 */
        final String testSourceJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n * @version 0.2.0\n */";

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = this.testTarget.getJavadocTagModelList();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "複数タグが正しく解析されること");

    }

    /**
     * constructor メソッドのテスト - 正常系:nullのJavadocでのコンストラクタ
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testConstructor_normalNullJavadoc() throws KmgToolBaseMsgException {

        /* 期待値の定義 */
        final int expectedTagCount = 0;

        /* 準備 */
        final String testSourceJavadoc = null;

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = this.testTarget.getJavadocTagModelList();

        /* 検証の実施 */
        Assertions.assertNotNull(actualTagList, "タグリストがnullでないこと");
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "nullのJavadocでタグリストが空であること");

    }

    /**
     * constructor メソッドのテスト - 正常系:単一タグのJavadocでのコンストラクタ
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testConstructor_normalSingleTagJavadoc() throws KmgToolBaseMsgException {

        /* 期待値の定義 */
        final int                expectedTagCount = 1;
        final KmgJavadocTagTypes expectedTag      = KmgJavadocTagTypes.AUTHOR;
        final String             expectedValue    = "KenichiroArai";

        /* 準備 */
        final String testSourceJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n */";

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = this.testTarget.getJavadocTagModelList();
        final JavadocTagModel       actualTag     = actualTagList.get(0);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "単一タグが正しく解析されること");
        Assertions.assertEquals(expectedTag, actualTag.getTag(), "タグが正しく設定されること");
        Assertions.assertEquals(expectedValue, actualTag.getValue(), "指定値が正しく設定されること");

    }

    /**
     * constructor メソッドのテスト - 正常系:タグのみのJavadocでのコンストラクタ
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testConstructor_normalTagOnlyJavadoc() throws KmgToolBaseMsgException {

        /* 期待値の定義 */
        final int                expectedTagCount = 1;
        final KmgJavadocTagTypes expectedTag      = KmgJavadocTagTypes.AUTHOR;
        final String             expectedValue    = "*/";

        /* 準備 */
        final String testSourceJavadoc = "/**\n * テストクラス\n * @author\n */";

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = this.testTarget.getJavadocTagModelList();
        final JavadocTagModel       actualTag     = actualTagList.get(0);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "タグのみが正しく解析されること");
        Assertions.assertEquals(expectedTag, actualTag.getTag(), "タグが正しく設定されること");
        Assertions.assertEquals(expectedValue, actualTag.getValue(), "指定値が正しく設定されること");

    }

    /**
     * constructor メソッドのテスト - 正常系:説明付きタグのJavadocでのコンストラクタ
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testConstructor_normalTagWithDescriptionJavadoc() throws KmgToolBaseMsgException {

        /* 期待値の定義 */
        final int                expectedTagCount    = 1;
        final KmgJavadocTagTypes expectedTag         = KmgJavadocTagTypes.PARAM;
        final String             expectedValue       = "param1";
        final String             expectedDescription = "パラメータ1の説明";

        /* 準備 */
        final String testSourceJavadoc = "/**\n * テストメソッド\n * @param param1 パラメータ1の説明\n */";

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = this.testTarget.getJavadocTagModelList();
        final JavadocTagModel       actualTag     = actualTagList.get(0);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "説明付きタグが正しく解析されること");
        Assertions.assertEquals(expectedTag, actualTag.getTag(), "タグが正しく設定されること");
        Assertions.assertEquals(expectedValue, actualTag.getValue(), "指定値が正しく設定されること");
        Assertions.assertEquals(expectedDescription, actualTag.getDescription(), "説明が正しく設定されること");

    }

    /**
     * constructor メソッドのテスト - 正常系:説明なしタグのJavadocでのコンストラクタ
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testConstructor_normalTagWithoutDescriptionJavadoc() throws KmgToolBaseMsgException {

        /* 期待値の定義 */
        final int                expectedTagCount = 1;
        final KmgJavadocTagTypes expectedTag      = KmgJavadocTagTypes.AUTHOR;
        final String             expectedValue    = "KenichiroArai";

        /* 準備 */
        final String testSourceJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n */";

        /* テスト対象の実行 */
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = this.testTarget.getJavadocTagModelList();
        final JavadocTagModel       actualTag     = actualTagList.get(0);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "説明なしタグが正しく解析されること");
        Assertions.assertEquals(expectedTag, actualTag.getTag(), "タグが正しく設定されること");
        Assertions.assertEquals(expectedValue, actualTag.getValue(), "指定値が正しく設定されること");

    }

    /**
     * findByTag メソッドのテスト - 正常系:空のタグリストでの検索
     *
     * @since 0.2.0
     */
    @Test
    public void testFindByTag_normalEmptyTagList() {

        /* 期待値の定義 */

        /* 準備 */
        this.testTarget = new JavadocTagsModelImpl();
        final KmgJavadocTagTypes testTag = KmgJavadocTagTypes.AUTHOR;

        /* テスト対象の実行 */
        final JavadocTagModel testResult = this.testTarget.findByTag(testTag);

        /* 検証の準備 */
        final JavadocTagModel actualTag = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualTag, "空のタグリストでnullが返されること");

    }

    /**
     * findByTag メソッドのテスト - 正常系:存在するタグでの検索
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testFindByTag_normalExistingTag() throws KmgToolBaseMsgException {

        /* 期待値の定義 */
        final KmgJavadocTagTypes expectedTag   = KmgJavadocTagTypes.AUTHOR;
        final String             expectedValue = "KenichiroArai";

        /* 準備 */
        final String testSourceJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n * @version 0.2.0\n */";
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);
        final KmgJavadocTagTypes testTag = KmgJavadocTagTypes.AUTHOR;

        /* テスト対象の実行 */
        final JavadocTagModel testResult = this.testTarget.findByTag(testTag);

        /* 検証の準備 */
        final JavadocTagModel actualTag = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualTag, "存在するタグが正しく検索されること");
        Assertions.assertEquals(expectedTag, actualTag.getTag(), "タグが正しく設定されること");
        Assertions.assertEquals(expectedValue, actualTag.getValue(), "指定値が正しく設定されること");

    }

    /**
     * findByTag メソッドのテスト - 正常系:存在しないタグでの検索
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testFindByTag_normalNonExistingTag() throws KmgToolBaseMsgException {

        /* 期待値の定義 */

        /* 準備 */
        final String testSourceJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n */";
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);
        final KmgJavadocTagTypes testTag = KmgJavadocTagTypes.VERSION;

        /* テスト対象の実行 */
        final JavadocTagModel testResult = this.testTarget.findByTag(testTag);

        /* 検証の準備 */
        final JavadocTagModel actualTag = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualTag, "存在しないタグでnullが返されること");

    }

    /**
     * findByTag メソッドのテスト - 準正常系:nullのタグでの検索
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testFindByTag_semiNullTag() throws KmgToolBaseMsgException {

        /* 期待値の定義 */

        /* 準備 */
        final String testSourceJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n */";
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);
        final KmgJavadocTagTypes testTag = null;

        /* テスト対象の実行 */
        final JavadocTagModel testResult = this.testTarget.findByTag(testTag);

        /* 検証の準備 */
        final JavadocTagModel actualTag = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualTag, "nullのタグでnullが返されること");

    }

    /**
     * getJavadocTagModelList メソッドのテスト - 正常系:複雑なJavadocでのリスト取得
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetJavadocTagModelList_normalComplexJavadoc() throws KmgToolBaseMsgException {

        /* 期待値の定義 */
        final int expectedTagCount = 2;

        /* 準備 */
        final String testSourceJavadoc
            = "/**\n * テストクラス\n * @author KenichiroArai\n * @version 0.2.0\n * @since 0.2.0\n * @param param1 パラメータ1の説明\n */";
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);

        /* テスト対象の実行 */
        final List<JavadocTagModel> testResult = this.testTarget.getJavadocTagModelList();

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualTagList, "タグリストがnullでないこと");
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "複雑なJavadocのタグリストが正しく返されること");

    }

    /**
     * getJavadocTagModelList メソッドのテスト - 正常系:空のタグリストでのリスト取得
     *
     * @since 0.2.0
     */
    @Test
    public void testGetJavadocTagModelList_normalEmptyTagList() {

        /* 期待値の定義 */
        final int expectedTagCount = 0;

        /* 準備 */
        this.testTarget = new JavadocTagsModelImpl();

        /* テスト対象の実行 */
        final List<JavadocTagModel> testResult = this.testTarget.getJavadocTagModelList();

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualTagList, "タグリストがnullでないこと");
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "空のタグリストが正しく返されること");

    }

    /**
     * getJavadocTagModelList メソッドのテスト - 正常系:複数タグのリストでのリスト取得
     *
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Test
    public void testGetJavadocTagModelList_normalMultipleTagsList() throws KmgToolBaseMsgException {

        /* 期待値の定義 */
        final int expectedTagCount = 1;

        /* 準備 */
        final String testSourceJavadoc = "/**\n * テストクラス\n * @author KenichiroArai\n * @version 0.2.0\n */";
        this.testTarget = new JavadocTagsModelImpl(testSourceJavadoc);

        /* テスト対象の実行 */
        final List<JavadocTagModel> testResult = this.testTarget.getJavadocTagModelList();

        /* 検証の準備 */
        final List<JavadocTagModel> actualTagList = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualTagList, "タグリストがnullでないこと");
        Assertions.assertEquals(expectedTagCount, actualTagList.size(), "複数タグのリストが正しく返されること");

    }

}
