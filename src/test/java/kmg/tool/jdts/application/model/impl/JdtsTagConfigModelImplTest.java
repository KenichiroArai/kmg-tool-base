package kmg.tool.jdts.application.model.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.jdts.application.model.JdtsLocationConfigModel;
import kmg.tool.jdts.application.types.JdtsConfigKeyTypes;
import kmg.tool.jdts.application.types.JdtsInsertPositionTypes;
import kmg.tool.jdts.application.types.JdtsOverwriteTypes;

/**
 * Javadocタグ設定のタグ構成モデル実装のテスト<br>
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
public class JdtsTagConfigModelImplTest extends AbstractKmgTest {

    /** テスト対象 */
    private JdtsTagConfigModelImpl testTarget;

    /**
     * テスト前処理<br>
     *
     * @since 0.1.0
     */
    @BeforeEach
    public void setUp() {

        /* テスト対象のクリア */
        this.testTarget = null;

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:無効な挿入位置
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorInvalidInsertPosition() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "INVALID");

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "無効な挿入位置の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:無効な上書き設定
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorInvalidOverwrite() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "INVALID");

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "無効な上書き設定の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:無効なタグ名
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorInvalidTagName() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@invalid");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "無効なタグ名の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:タグ名が空文字
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorTagNameEmpty() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "タグ名が空文字の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:タグ名がnull
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorTagNameNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), null);
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "タグ名がnullの場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:タグ値が空文字
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorTagValueEmpty() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "タグ値が空文字の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:タグ値がnull
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorTagValueNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), null);
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "タグ値がnullの場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系:有効なタグ設定で正常に初期化
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalValidTagConfig() throws Exception {

        /* 期待値の定義 */
        final String                  expectedTagName        = "@author";
        final String                  expectedTagValue       = "TestAuthor";
        final String                  expectedTagDescription = "TestDescription";
        final JdtsInsertPositionTypes expectedInsertPosition = JdtsInsertPositionTypes.BEGINNING;
        final JdtsOverwriteTypes      expectedOverwrite      = JdtsOverwriteTypes.ALWAYS;

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), expectedTagName);
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), expectedTagValue);
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), expectedTagDescription);
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        /* テスト対象の実行 */
        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagName, this.testTarget.getTagName(), "タグ名が正しく設定されること");
        Assertions.assertEquals(expectedTagValue, this.testTarget.getTagValue(), "タグ値が正しく設定されること");
        Assertions.assertEquals(expectedTagDescription, this.testTarget.getTagDescription(), "タグ説明が正しく設定されること");
        Assertions.assertEquals(expectedInsertPosition, this.testTarget.getInsertPosition(), "挿入位置が正しく設定されること");
        Assertions.assertEquals(expectedOverwrite, this.testTarget.getOverwrite(), "上書き設定が正しく設定されること");
        Assertions.assertNotNull(this.testTarget.getLocation(), "配置設定がnullでないこと");

    }

    /**
     * getInsertPosition メソッドのテスト - 正常系:挿入位置を返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetInsertPosition_normalReturnInsertPosition() throws Exception {

        /* 期待値の定義 */
        final JdtsInsertPositionTypes expectedInsertPosition = JdtsInsertPositionTypes.END;

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "END");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final JdtsInsertPositionTypes testResult = this.testTarget.getInsertPosition();

        /* 検証の実施 */
        Assertions.assertEquals(expectedInsertPosition, testResult, "挿入位置が正しく返されること");

    }

    /**
     * getLocation メソッドのテスト - 正常系:配置設定を返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetLocation_normalReturnLocation() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final JdtsLocationConfigModel testResult = this.testTarget.getLocation();

        /* 検証の実施 */
        Assertions.assertNotNull(testResult, "配置設定がnullでないこと");

    }

    /**
     * getOverwrite メソッドのテスト - 正常系:上書き設定を返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetOverwrite_normalReturnOverwrite() throws Exception {

        /* 期待値の定義 */
        final JdtsOverwriteTypes expectedOverwrite = JdtsOverwriteTypes.NEVER;

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "NEVER");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final JdtsOverwriteTypes testResult = this.testTarget.getOverwrite();

        /* 検証の実施 */
        Assertions.assertEquals(expectedOverwrite, testResult, "上書き設定が正しく返されること");

    }

    /**
     * getTag メソッドのテスト - 正常系:タグを返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTag_normalReturnTag() throws Exception {

        /* 期待値の定義 */
        final KmgJavadocTagTypes expectedTag = KmgJavadocTagTypes.AUTHOR;

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final KmgJavadocTagTypes testResult = this.testTarget.getTag();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTag, testResult, "タグが正しく返されること");

    }

    /**
     * getTagDescription メソッドのテスト - 正常系:タグ説明を返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTagDescription_normalReturnTagDescription() throws Exception {

        /* 期待値の定義 */
        final String expectedTagDescription = "TestDescription";

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), expectedTagDescription);
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTagDescription();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagDescription, testResult, "タグ説明が正しく返されること");

    }

    /**
     * getTagName メソッドのテスト - 正常系:タグ名を返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTagName_normalReturnTagName() throws Exception {

        /* 期待値の定義 */
        final String expectedTagName = "@author";

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), expectedTagName);
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTagName();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagName, testResult, "タグ名が正しく返されること");

    }

    /**
     * getTagValue メソッドのテスト - 正常系:タグ値を返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTagValue_normalReturnTagValue() throws Exception {

        /* 期待値の定義 */
        final String expectedTagValue = "TestValue";

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), expectedTagValue);
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTagValue();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagValue, testResult, "タグ値が正しく返されること");

    }

    /**
     * isProperlyPlaced メソッドのテスト - 正常系:MANUALモードで適切に配置されていない場合
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testIsProperlyPlaced_normalManualModeNotProperlyPlaced() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定（MANUALモード）
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "MANUAL");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");

        final List<String> targetElements = new ArrayList<>();
        targetElements.add("METHOD");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), targetElements);
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isProperlyPlaced(JavaClassificationTypes.CLASS);

        /* 検証の実施 */
        Assertions.assertFalse(testResult, "MANUALモードで適切に配置されていない場合にfalseが返されること");

    }

    /**
     * isProperlyPlaced メソッドのテスト - 正常系:MANUALモードで適切に配置されている場合
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testIsProperlyPlaced_normalManualModeProperlyPlaced() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定（MANUALモード）
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "MANUAL");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");

        final List<String> targetElements = new ArrayList<>();
        targetElements.add("CLASS");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), targetElements);
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isProperlyPlaced(JavaClassificationTypes.CLASS);

        /* 検証の実施 */
        Assertions.assertTrue(testResult, "MANUALモードで適切に配置されている場合にtrueが返されること");

    }

    /**
     * isProperlyPlaced メソッドのテスト - 正常系:NONEモードの場合
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testIsProperlyPlaced_normalNoneMode() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定（NONEモード）
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "NONE");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isProperlyPlaced(JavaClassificationTypes.CLASS);

        /* 検証の実施 */
        Assertions.assertFalse(testResult, "NONEモードの場合はfalseが返されること");

    }

    /**
     * isProperlyPlaced メソッドのテスト - 正常系:適切に配置されていない場合
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testIsProperlyPlaced_normalNotProperlyPlaced() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isProperlyPlaced(JavaClassificationTypes.NONE);

        /* 検証の実施 */
        Assertions.assertFalse(testResult, "適切に配置されていない場合にfalseが返されること");

    }

    /**
     * isProperlyPlaced メソッドのテスト - 正常系:適切に配置されている場合
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testIsProperlyPlaced_normalProperlyPlaced() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isProperlyPlaced(JavaClassificationTypes.CLASS);

        /* 検証の実施 */
        Assertions.assertTrue(testResult, "適切に配置されている場合にtrueが返されること");

    }

}
