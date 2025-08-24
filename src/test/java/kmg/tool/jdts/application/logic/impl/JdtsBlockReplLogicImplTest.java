package kmg.tool.jdts.application.logic.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.jdoc.domain.model.JavadocModel;
import kmg.tool.jdoc.domain.model.JavadocTagModel;
import kmg.tool.jdoc.domain.model.JavadocTagsModel;
import kmg.tool.jdts.application.model.JdtsBlockModel;
import kmg.tool.jdts.application.model.JdtsConfigsModel;
import kmg.tool.jdts.application.model.JdtsLocationConfigModel;
import kmg.tool.jdts.application.model.JdtsTagConfigModel;
import kmg.tool.jdts.application.types.JdtsInsertPositionTypes;
import kmg.tool.jdts.application.types.JdtsOverwriteTypes;

/**
 * Javadocタグ設定のブロック置換ロジック実装のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({
    "nls",
})
public class JdtsBlockReplLogicImplTest extends AbstractKmgTest {

    /** テスト対象 */
    private JdtsBlockReplLogicImpl testTarget;

    /** モック: 構成モデル */
    @Mock
    private JdtsConfigsModel mockConfigsModel;

    /** モック: ブロックモデル */
    @Mock
    private JdtsBlockModel mockSrcBlockModel;

    /** モック: タグ構成モデル */
    @Mock
    private JdtsTagConfigModel mockTagConfigModel;

    /** モック: Javadocモデル */
    @Mock
    private JavadocModel mockJavadocModel;

    /** モック: Javadocタグ集合モデル */
    @Mock
    private JavadocTagsModel mockJavadocTagsModel;

    /** モック: Javadocタグモデル */
    @Mock
    private JavadocTagModel mockJavadocTagModel;

    /** モック: 配置設定モデル */
    @Mock
    private JdtsLocationConfigModel mockLocationConfigModel;

    /** リフレクションモデル */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * テスト前処理<br>
     *
     * @since 0.1.0
     */
    @BeforeEach
    public void setUp() {

        this.testTarget = new JdtsBlockReplLogicImpl();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:switch文のBEGINNINGケースの条件分岐を確実にカバー
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_beginningCaseCoverage() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @return 戻り値";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.BEGINNING);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.RETURN);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("戻り値");

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock",
            new StringBuilder("/**\n * Test javadoc\n * @author KenichiroArai\n */"));

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(actualReplacedBlock.toString().contains(expectedTagContent),
            "Javadocブロックに新しいタグが先頭に挿入されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:switch文のBEGINNINGケースの条件分岐を確実にカバー（追加テスト）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_beginningCaseCoverageAdditional() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @param param パラメータ";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.BEGINNING);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.PARAM);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("param");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("パラメータ");

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock",
            new StringBuilder("/**\n * Test javadoc\n * @version 1.0.0\n */"));

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock      = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent         = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtBeginning = actualReplacedBlock.toString().contains(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtBeginning, "Javadocブロックに新しいタグが先頭に挿入されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:BEGINNINGケース（無効なオフセット）（分割後）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalBeginningInvalidOffsetSplit() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @since 1.0.0";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.SINCE);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("1.0.0");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("");
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.BEGINNING);

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", -1);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInserted       = actualReplacedBlock.toString().contains(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInserted, "BEGINNINGケース（無効なオフセット）でタグが追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:BEGINNINGケース（THROWSタグ）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalBeginningThrows() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @throws Exception 例外";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.THROWS);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("Exception");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("例外");
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.BEGINNING);

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 0);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInserted       = actualReplacedBlock.toString().contains(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInserted, "BEGINNINGケースでタグが追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:BEGINNINGケース（有効なオフセット）（分割後）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalBeginningValidOffsetSplit() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @since 1.0.0";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.SINCE);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("1.0.0");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("");
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.BEGINNING);

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 3); // 文字列の長さ内の値に修正

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInserted       = actualReplacedBlock.toString().contains(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInserted, "BEGINNINGケースでタグが追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:BEGINNING位置でheadTagPosOffsetが有効
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalBeginningWithValidOffset() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @author Test Author";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.BEGINNING);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.AUTHOR);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("Test Author");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("");

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock",
            new StringBuilder("/**\n * Test javadoc\n * @author KenichiroArai\n */"));

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock      = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent         = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtBeginning = actualReplacedBlock.toString().contains(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtBeginning, "Javadocブロックに新しいタグが先頭に挿入されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:BEGINNING位置でheadTagPosOffsetが有効（追加テスト）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalBeginningWithValidOffsetAdditional() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @param testParam テストパラメータ";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.BEGINNING);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.PARAM);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("testParam");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("テストパラメータ");

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock",
            new StringBuilder("/**\n * Test javadoc\n * @author KenichiroArai\n */"));

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock      = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent         = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtBeginning = actualReplacedBlock.toString().contains(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtBeginning, "Javadocブロックに新しいタグが先頭に挿入されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:BEGINNING位置でheadTagPosOffsetが有効（独立テスト）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalBeginningWithValidOffsetIndependent() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @since 2.0.0";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.BEGINNING);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.SINCE);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("2.0.0");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("");

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock",
            new StringBuilder("/**\n * Test javadoc\n * @version 1.0.0\n */"));

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock      = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent         = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtBeginning = actualReplacedBlock.toString().contains(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtBeginning, "Javadocブロックに新しいタグが先頭に挿入されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:END位置
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalEnd() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @version 2.0.0";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.END);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.VERSION);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("2.0.0");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("");

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 0);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtEnd  = actualReplacedBlock.toString().endsWith(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtEnd, "Javadocブロックに新しいタグが末尾に追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:ENDケースの独立テスト
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalEndIndependent() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @version 3.0.0";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.END);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.VERSION);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("3.0.0");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("");

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 0);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtEnd  = actualReplacedBlock.toString().endsWith(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtEnd, "Javadocブロックに新しいタグが末尾に追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:ENDケース（分割後）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalEndSplit() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @since 1.0.0";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.SINCE);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("1.0.0");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("");
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.END);

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 0);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtEnd  = actualReplacedBlock.toString().endsWith(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtEnd, "ENDケースでタグが末尾に追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:ENDケース（THROWSタグ）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalEndThrows() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @throws Exception 例外";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.THROWS);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("Exception");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("例外");
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.END);

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 0);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtEnd  = actualReplacedBlock.toString().endsWith(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtEnd, "ENDケースでタグが末尾に追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:NONE位置
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalNone() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @param testParam テストパラメータ";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.NONE);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.PARAM);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("testParam");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("テストパラメータ");

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 10);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtEnd  = actualReplacedBlock.toString().endsWith(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtEnd, "Javadocブロックに新しいタグが末尾に追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:NONEケースの独立テスト
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalNoneIndependent() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @param param パラメータ";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.NONE);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.PARAM);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("param");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("パラメータ");

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 10);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtEnd  = actualReplacedBlock.toString().endsWith(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtEnd, "Javadocブロックに新しいタグが末尾に追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:NONEケース（分割後）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalNoneSplit() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @since 1.0.0";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.SINCE);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("1.0.0");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("");
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.NONE);

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 0);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtEnd  = actualReplacedBlock.toString().endsWith(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtEnd, "NONEケースでタグが末尾に追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:NONEケース（THROWSタグ）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalNoneThrows() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @throws Exception 例外";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.THROWS);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("Exception");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("例外");
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.NONE);

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 0);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtEnd  = actualReplacedBlock.toString().endsWith(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtEnd, "NONEケースでタグが末尾に追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:PRESERVE位置
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalPreserve() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @return 戻り値";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.PRESERVE);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.RETURN);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("戻り値");

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 5);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtEnd  = actualReplacedBlock.toString().endsWith(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtEnd, "Javadocブロックに新しいタグが末尾に追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:PRESERVEケースの独立テスト
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalPreserveIndependent() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @return 戻り値の説明";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.PRESERVE);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.RETURN);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("戻り値の説明");

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 5);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtEnd  = actualReplacedBlock.toString().endsWith(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtEnd, "Javadocブロックに新しいタグが末尾に追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:PRESERVEケース（分割後）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalPreserveSplit() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @since 1.0.0";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.SINCE);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("1.0.0");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("");
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.PRESERVE);

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 0);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtEnd  = actualReplacedBlock.toString().endsWith(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtEnd, "PRESERVEケースでタグが末尾に追加されること");

    }

    /**
     * addNewTagByPosition メソッドのテスト - 正常系:PRESERVEケース（THROWSタグ）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testAddNewTagByPosition_normalPreserveThrows() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @throws Exception 例外";

        /* 準備 */
        // モックの設定
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.THROWS);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("Exception");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("例外");
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.PRESERVE);

        // リフレクションでフィールドを設定
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc */"));
        this.reflectionModel.set("headTagPosOffset", 0);

        /* テスト対象の実行 */
        this.testTarget.addNewTagByPosition();

        /* 検証の準備 */
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final String        actualTagContent    = (String) this.reflectionModel.get("tagContentToApply");
        final boolean       isTagInsertedAtEnd  = actualReplacedBlock.toString().endsWith(expectedTagContent);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "タグ内容が正しく生成されること");
        Assertions.assertTrue(isTagInsertedAtEnd, "PRESERVEケースでタグが末尾に追加されること");

    }

    /**
     * createTagContent メソッドのテスト - 正常系:タグ内容の生成
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testCreateTagContent_normalCreateContent() throws Exception {

        /* 期待値の定義 */
        final String expectedContent = "* @author TestAuthor TestDescription";

        /* 準備 */
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.AUTHOR);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("TestAuthor");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("TestDescription");

        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);

        /* テスト対象の実行 */
        final String testResult = (String) this.reflectionModel.getMethod("createTagContent");

        /* 検証の準備 */
        final String actualContent = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedContent, actualContent, "タグ内容が正しく生成されること");

    }

    /**
     * getConfigsModel メソッドのテスト - 正常系:構成モデルを返す
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testGetConfigsModel_normalReturnConfigsModel() throws Exception {

        /* 期待値の定義 */
        final JdtsConfigsModel expectedConfigsModel = this.mockConfigsModel;

        /* 準備 */
        this.reflectionModel.set("configsModel", this.mockConfigsModel);

        /* テスト対象の実行 */
        final JdtsConfigsModel testResult = this.testTarget.getConfigsModel();

        /* 検証の準備 */
        final JdtsConfigsModel actualConfigsModel = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedConfigsModel, actualConfigsModel, "構成モデルが正しく返されること");

    }

    /**
     * getCurrentSrcJavadocTag メソッドのテスト - 正常系:現在のJavadocタグを返す
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testGetCurrentSrcJavadocTag_normalReturnCurrentTag() throws Exception {

        /* 期待値の定義 */
        final JavadocTagModel expectedJavadocTag = this.mockJavadocTagModel;

        /* 準備 */
        this.reflectionModel.set("currentSrcJavadocTag", this.mockJavadocTagModel);

        /* テスト対象の実行 */
        final JavadocTagModel testResult = this.testTarget.getCurrentSrcJavadocTag();

        /* 検証の準備 */
        final JavadocTagModel actualJavadocTag = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedJavadocTag, actualJavadocTag, "現在のJavadocタグが正しく返されること");

    }

    /**
     * getCurrentTagConfigModel メソッドのテスト - 正常系:現在のタグ構成モデルを返す
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testGetCurrentTagConfigModel_normalReturnCurrentTagConfig() throws Exception {

        /* 期待値の定義 */
        final JdtsTagConfigModel expectedTagConfigModel = this.mockTagConfigModel;

        /* 準備 */
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);

        /* テスト対象の実行 */
        final JdtsTagConfigModel testResult = this.testTarget.getCurrentTagConfigModel();

        /* 検証の準備 */
        final JdtsTagConfigModel actualTagConfigModel = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagConfigModel, actualTagConfigModel, "現在のタグ構成モデルが正しく返されること");

    }

    /**
     * getReplacedJavadocBlock メソッドのテスト - 正常系:置換後のJavadocブロックを返す
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testGetReplacedJavadocBlock_normalReturnReplacedBlock() throws Exception {

        /* 期待値の定義 */
        final String expectedJavadocBlock = "/** Test replaced javadoc */";

        /* 準備 */
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder(expectedJavadocBlock));

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getReplacedJavadocBlock();

        /* 検証の準備 */
        final String actualJavadocBlock = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedJavadocBlock, actualJavadocBlock, "置換後のJavadocブロックが正しく返されること");

    }

    /**
     * getTagContentToApply メソッドのテスト - 正常系:設定するタグの内容を返す
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testGetTagContentToApply_normalReturnTagContent() throws Exception {

        /* 期待値の定義 */
        final String expectedTagContent = "* @author TestAuthor TestDescription";

        /* 準備 */
        this.reflectionModel.set("tagContentToApply", expectedTagContent);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTagContentToApply();

        /* 検証の準備 */
        final String actualTagContent = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagContent, actualTagContent, "設定するタグの内容が正しく返されること");

    }

    /**
     * hasExistingTag メソッドのテスト - 正常系:既存タグが存在する場合
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testHasExistingTag_normalTagExists() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("currentSrcJavadocTag", this.mockJavadocTagModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.hasExistingTag();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "既存タグが存在する場合はtrueが返されること");

    }

    /**
     * hasExistingTag メソッドのテスト - 正常系:既存タグが存在しない場合
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testHasExistingTag_normalTagNotExists() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("currentSrcJavadocTag", null);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.hasExistingTag();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "既存タグが存在しない場合はfalseが返されること");

    }

    /**
     * initialize メソッドのテスト - 正常系:初期化が成功する場合
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testInitialize_normalSuccessful() throws Exception {

        /* 期待値の定義 */
        final int expectedHeadTagPosOffset = -1;

        /* 準備 */
        final List<JdtsTagConfigModel> testTagConfigModels = new ArrayList<>();
        testTagConfigModels.add(this.mockTagConfigModel);

        Mockito.when(this.mockConfigsModel.getJdaTagConfigModels()).thenReturn(testTagConfigModels);
        Mockito.when(this.mockSrcBlockModel.getJavadocModel()).thenReturn(this.mockJavadocModel);
        Mockito.when(this.mockJavadocModel.getSrcJavadoc()).thenReturn("/** Test javadoc */");
        Mockito.when(this.mockJavadocModel.getJavadocTagsModel()).thenReturn(this.mockJavadocTagsModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(this.mockConfigsModel, this.mockSrcBlockModel);

        /* 検証の準備 */
        final boolean       actualResult        = testResult;
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が成功すること");
        Assertions.assertNotNull(actualReplacedBlock, "置換用Javadocブロックが初期化されること");

    }

    /**
     * nextTag メソッドのテスト - 正常系:次のタグが存在する場合
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testNextTag_normalHasNextTag() throws Exception {

        /* 期待値の定義 */
        final JdtsTagConfigModel expectedCurrentTagConfig  = this.mockTagConfigModel;
        final JavadocTagModel    expectedCurrentJavadocTag = this.mockJavadocTagModel;

        /* 準備 */
        final Iterator<JdtsTagConfigModel> testIterator = Mockito.mock(Iterator.class);
        Mockito.when(testIterator.hasNext()).thenReturn(true);
        Mockito.when(testIterator.next()).thenReturn(this.mockTagConfigModel);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.AUTHOR);
        Mockito.when(this.mockJavadocTagsModel.findByTag(KmgJavadocTagTypes.AUTHOR))
            .thenReturn(this.mockJavadocTagModel);

        this.reflectionModel.set("tagConfigIterator", testIterator);
        this.reflectionModel.set("srcBlockModel", this.mockSrcBlockModel);
        Mockito.when(this.mockSrcBlockModel.getJavadocModel()).thenReturn(this.mockJavadocModel);
        Mockito.when(this.mockJavadocModel.getJavadocTagsModel()).thenReturn(this.mockJavadocTagsModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.nextTag();

        /* 検証の準備 */
        final boolean            actualResult            = testResult;
        final JdtsTagConfigModel actualCurrentTagConfig  = (JdtsTagConfigModel) this.reflectionModel
            .get("currentTagConfigModel");
        final JavadocTagModel    actualCurrentJavadocTag = (JavadocTagModel) this.reflectionModel
            .get("currentSrcJavadocTag");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "次のタグが存在する場合はtrueが返されること");
        Assertions.assertEquals(expectedCurrentTagConfig, actualCurrentTagConfig, "現在のタグ構成モデルが設定されること");
        Assertions.assertEquals(expectedCurrentJavadocTag, actualCurrentJavadocTag, "現在のJavadocタグが設定されること");

    }

    /**
     * nextTag メソッドのテスト - 正常系:次のタグが存在しない場合
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testNextTag_normalNoNextTag() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Iterator<JdtsTagConfigModel> testIterator = Mockito.mock(Iterator.class);
        Mockito.when(testIterator.hasNext()).thenReturn(false);

        this.reflectionModel.set("tagConfigIterator", testIterator);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.nextTag();

        /* 検証の準備 */
        final boolean            actualResult            = testResult;
        final JdtsTagConfigModel actualCurrentTagConfig  = (JdtsTagConfigModel) this.reflectionModel
            .get("currentTagConfigModel");
        final JavadocTagModel    actualCurrentJavadocTag = (JavadocTagModel) this.reflectionModel
            .get("currentSrcJavadocTag");

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "次のタグが存在しない場合はfalseが返されること");
        Assertions.assertNull(actualCurrentTagConfig, "現在のタグ構成モデルがクリアされること");
        Assertions.assertNull(actualCurrentJavadocTag, "現在のJavadocタグがクリアされること");

    }

    /**
     * removeCurrentTag メソッドのテスト - 正常系:現在のタグが存在する場合
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testRemoveCurrentTag_normalTagExists() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testTargetStr = "* @author TestAuthor";
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn(testTargetStr);

        this.reflectionModel.set("currentSrcJavadocTag", this.mockJavadocTagModel);
        this.reflectionModel.set("replacedJavadocBlock",
            new StringBuilder("/** Test javadoc\n" + testTargetStr + "\n */"));

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeCurrentTag();

        /* 検証の準備 */
        final boolean       actualResult        = testResult;
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final boolean       isTagRemoved        = !actualReplacedBlock.toString().contains(testTargetStr);

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "タグの削除が成功すること");
        Assertions.assertFalse(!isTagRemoved, "指定されたタグが削除されること");

    }

    /**
     * removeCurrentTag メソッドのテスト - 準正常系:現在のタグが存在しない場合
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testRemoveCurrentTag_semiTagNotExists() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.reflectionModel.set("currentSrcJavadocTag", null);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeCurrentTag();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "タグが存在しない場合はfalseが返されること");

    }

    /**
     * removeCurrentTagOnError メソッドのテスト - 正常系:誤配置時削除が設定されており配置が不適切
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testRemoveCurrentTagOnError_normalRemoveIfMisplaced() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testTargetStr = "* @author TestAuthor";
        Mockito.when(this.mockLocationConfigModel.isRemoveIfMisplaced()).thenReturn(true);
        Mockito.when(this.mockTagConfigModel.getLocation()).thenReturn(this.mockLocationConfigModel);
        Mockito.when(this.mockTagConfigModel.isProperlyPlaced(JavaClassificationTypes.CLASS)).thenReturn(false);
        Mockito.when(this.mockSrcBlockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn(testTargetStr);

        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("currentSrcJavadocTag", this.mockJavadocTagModel);
        this.reflectionModel.set("srcBlockModel", this.mockSrcBlockModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test\n" + testTargetStr + "\n */"));

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeCurrentTagOnError();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "誤配置時削除が設定されており配置が不適切な場合はタグが削除されること");

    }

    /**
     * removeCurrentTagOnError メソッドのテスト - 準正常系:配置が適切な場合
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testRemoveCurrentTagOnError_semiProperlyPlacedTrue() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockLocationConfigModel.isRemoveIfMisplaced()).thenReturn(true);
        Mockito.when(this.mockTagConfigModel.getLocation()).thenReturn(this.mockLocationConfigModel);
        Mockito.when(this.mockTagConfigModel.isProperlyPlaced(JavaClassificationTypes.CLASS)).thenReturn(true);
        Mockito.when(this.mockSrcBlockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);

        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("srcBlockModel", this.mockSrcBlockModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeCurrentTagOnError();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "配置が適切な場合はタグが削除されないこと");

    }

    /**
     * removeCurrentTagOnError メソッドのテスト - 準正常系:誤配置時削除が設定されていない場合
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testRemoveCurrentTagOnError_semiRemoveIfMisplacedFalse() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockLocationConfigModel.isRemoveIfMisplaced()).thenReturn(false);
        Mockito.when(this.mockTagConfigModel.getLocation()).thenReturn(this.mockLocationConfigModel);

        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.removeCurrentTagOnError();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "誤配置時削除が設定されていない場合はタグが削除されないこと");

    }

    /**
     * replaceExistingTag メソッドのテスト - 正常系:既存タグの置換が成功
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testReplaceExistingTag_normalSuccessful() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testTargetStr = "* @author OldAuthor";

        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn(testTargetStr);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.AUTHOR);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("NewAuthor");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("Description");

        this.reflectionModel.set("currentSrcJavadocTag", this.mockJavadocTagModel);
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test\n" + testTargetStr + "\n */"));

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.replaceExistingTag();

        /* 検証の準備 */
        final boolean       actualResult        = testResult;
        final StringBuilder actualReplacedBlock = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final boolean       isOldTagRemoved     = !actualReplacedBlock.toString().contains(testTargetStr);
        final boolean       isNewTagSet         = actualReplacedBlock.toString().contains("NewAuthor");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "既存タグの置換が成功すること");
        Assertions.assertTrue(isOldTagRemoved, "古いタグ内容が削除されること");
        Assertions.assertTrue(isNewTagSet, "新しいタグ内容が設定されること");

    }

    /**
     * replaceExistingTag メソッドのテスト - 準正常系:既存タグが見つからない場合
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testReplaceExistingTag_semiTagNotFound() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testTargetStr = "* @author NotFound";
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn(testTargetStr);

        this.reflectionModel.set("currentSrcJavadocTag", this.mockJavadocTagModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test javadoc without target */"));

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.replaceExistingTag();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "既存タグが見つからない場合はfalseが返されること");

    }

    /**
     * repositionTagIfNeeded メソッドのテスト - 正常系:BEGINNING位置への再配置
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testRepositionTagIfNeeded_normalBeginningReposition() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testTargetStr = "* @author TestAuthor";
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.BEGINNING);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.AUTHOR);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("TestAuthor");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("");
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn(testTargetStr);

        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("currentSrcJavadocTag", this.mockJavadocTagModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test\n" + testTargetStr + "\n */"));
        this.reflectionModel.set("headTagPosOffset", -1);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.repositionTagIfNeeded();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "BEGINNING位置への再配置が成功すること");

    }

    /**
     * repositionTagIfNeeded メソッドのテスト - 正常系:END位置への再配置
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testRepositionTagIfNeeded_normalEndReposition() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final String testTargetStr = "* @author TestAuthor";
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.END);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.AUTHOR);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("TestAuthor");
        Mockito.when(this.mockTagConfigModel.getTagDescription()).thenReturn("");
        Mockito.when(this.mockJavadocTagModel.getTargetStr()).thenReturn(testTargetStr);

        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("currentSrcJavadocTag", this.mockJavadocTagModel);
        this.reflectionModel.set("replacedJavadocBlock", new StringBuilder("/** Test\n" + testTargetStr + "\n */"));
        this.reflectionModel.set("headTagPosOffset", -1);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.repositionTagIfNeeded();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "END位置への再配置が成功すること");

    }

    /**
     * repositionTagIfNeeded メソッドのテスト - 準正常系:NONE位置で再配置不要
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testRepositionTagIfNeeded_semiNoneNoReposition() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.NONE);
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.repositionTagIfNeeded();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "NONE位置の場合は再配置不要でfalseが返されること");

    }

    /**
     * repositionTagIfNeeded メソッドのテスト - 準正常系:PRESERVE位置で再配置不要
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testRepositionTagIfNeeded_semiPreserveNoReposition() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.PRESERVE);
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.repositionTagIfNeeded();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "PRESERVE位置の場合は再配置不要でfalseが返されること");

    }

    /**
     * repositionTagIfNeeded メソッドのテスト - 準正常系:現在のタグ削除が失敗
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testRepositionTagIfNeeded_semiRemoveCurrentTagFailed() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockTagConfigModel.getInsertPosition()).thenReturn(JdtsInsertPositionTypes.BEGINNING);

        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("currentSrcJavadocTag", null); // タグが存在しない状態でremoveCurrentTagが失敗

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.repositionTagIfNeeded();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "現在のタグ削除が失敗した場合はfalseが返されること");

    }

    /**
     * shouldAddNewTag メソッドのテスト - 正常系:新しいタグを追加すべき場合
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testShouldAddNewTag_normalShouldAdd() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockTagConfigModel.isProperlyPlaced(JavaClassificationTypes.CLASS)).thenReturn(true);
        Mockito.when(this.mockSrcBlockModel.getClassification()).thenReturn(JavaClassificationTypes.CLASS);

        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("srcBlockModel", this.mockSrcBlockModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.shouldAddNewTag();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "適切な配置の場合は新しいタグを追加すべきでtrueが返されること");

    }

    /**
     * shouldOverwriteTag メソッドのテスト - 正常系:上書き設定がALWAYS
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testShouldOverwriteTag_normalAlways() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockTagConfigModel.getOverwrite()).thenReturn(JdtsOverwriteTypes.ALWAYS);
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.shouldOverwriteTag();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "上書き設定がALWAYSの場合はtrueが返されること");

    }

    /**
     * shouldOverwriteTag メソッドのテスト - 正常系:上書き設定がIF_LOWER
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testShouldOverwriteTag_normalIfLower() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockTagConfigModel.getOverwrite()).thenReturn(JdtsOverwriteTypes.IF_LOWER);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.AUTHOR);
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.shouldOverwriteTag();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "上書き設定がIF_LOWERでバージョンタグでない場合はtrueが返されること");

    }

    /**
     * shouldOverwriteTag メソッドのテスト - 正常系:上書き設定がIF_LOWERでバージョンタグ（既存バージョンが高い）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testShouldOverwriteTag_normalIfLowerVersionHigher() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockTagConfigModel.getOverwrite()).thenReturn(JdtsOverwriteTypes.IF_LOWER);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.VERSION);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("1.0.0");
        Mockito.when(this.mockJavadocTagModel.getValue()).thenReturn("2.0.0");
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("currentSrcJavadocTag", this.mockJavadocTagModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.shouldOverwriteTag();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "上書き設定がIF_LOWERでバージョンタグ（既存バージョンが高い）の場合はtrueが返されること");

    }

    /**
     * shouldOverwriteTag メソッドのテスト - 準正常系:上書き設定がIF_LOWERでバージョンタグ（既存バージョンが低いまたは同じ）
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testShouldOverwriteTag_semiIfLowerVersionLowerOrEqual() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockTagConfigModel.getOverwrite()).thenReturn(JdtsOverwriteTypes.IF_LOWER);
        Mockito.when(this.mockTagConfigModel.getTag()).thenReturn(KmgJavadocTagTypes.VERSION);
        Mockito.when(this.mockTagConfigModel.getTagValue()).thenReturn("2.0.0");
        Mockito.when(this.mockJavadocTagModel.getValue()).thenReturn("1.0.0");
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);
        this.reflectionModel.set("currentSrcJavadocTag", this.mockJavadocTagModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.shouldOverwriteTag();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "上書き設定がIF_LOWERでバージョンタグ（既存バージョンが低いまたは同じ）の場合はfalseが返されること");

    }

    /**
     * shouldOverwriteTag メソッドのテスト - 準正常系:上書き設定がNEVER
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testShouldOverwriteTag_semiNever() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockTagConfigModel.getOverwrite()).thenReturn(JdtsOverwriteTypes.NEVER);
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.shouldOverwriteTag();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "上書き設定がNEVERの場合はfalseが返されること");

    }

    /**
     * shouldOverwriteTag メソッドのテスト - 準正常系:上書き設定がNONE
     *
     * @throws Exception
     *                   リフレクション操作で発生する可能性のある例外
     */
    @Test
    public void testShouldOverwriteTag_semiNone() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        Mockito.when(this.mockTagConfigModel.getOverwrite()).thenReturn(JdtsOverwriteTypes.NONE);
        this.reflectionModel.set("currentTagConfigModel", this.mockTagConfigModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.shouldOverwriteTag();

        /* 検証の準備 */
        final boolean actualResult = testResult;

        /* 検証の実施 */
        Assertions.assertFalse(actualResult, "上書き設定がNONEの場合はfalseが返されること");

    }

}
