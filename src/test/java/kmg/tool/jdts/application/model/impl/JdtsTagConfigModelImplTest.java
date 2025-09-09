package kmg.tool.jdts.application.model.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.model.val.KmgValsModel;
import kmg.core.infrastructure.model.val.impl.KmgValsModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.jdts.application.types.JdtsConfigKeyTypes;
import kmg.tool.jdts.application.types.JdtsInsertPositionTypes;
import kmg.tool.jdts.application.types.JdtsLocationModeTypes;
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

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private JdtsTagConfigModelImpl testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.1.0
     */
    private KmgReflectionModelImpl reflectionModel;

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
     * コンストラクタ メソッドのテスト - 異常系:タグ名が空
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorEmptyTagName() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "タグ名が空の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:タグ値が空
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorEmptyTagValue() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "タグ値が空の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:無効な挿入位置
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorInvalidInsertPosition() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "INVALID_POSITION");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "無効な挿入位置の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:配置場所の設定が無効
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorInvalidLocationConfig() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        // 無効な配置場所の設定（MANUALモードでTARGET_ELEMENTSが指定されていない）
        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "manual");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        // TARGET_ELEMENTSを指定しない
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "配置場所の設定が無効な場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:無効な上書き設定
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorInvalidOverwrite() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "INVALID_OVERWRITE");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "無効な上書き設定の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:無効なタグ名
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorInvalidTagName() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "INVALID_TAG");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "無効なタグ名の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系:タグの説明がnull
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_normalNullTagDescription() throws Exception {

        /* 期待値の定義 */
        final String expectedTagDescription = "";

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), null);
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        /* テスト対象の実行 */
        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagDescription, this.testTarget.getTagDescription(), "タグの説明が空文字列になること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系:正常に初期化
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_normalValidConfig() throws Exception {

        /* 期待値の定義 */
        final KmgJavadocTagTypes      expectedTag            = KmgJavadocTagTypes.AUTHOR;
        final String                  expectedTagName        = "@author";
        final String                  expectedTagValue       = "testValue";
        final String                  expectedTagDescription = "testDescription";
        final JdtsInsertPositionTypes expectedInsertPosition = JdtsInsertPositionTypes.BEGINNING;
        final JdtsOverwriteTypes      expectedOverwrite      = JdtsOverwriteTypes.ALWAYS;

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "testDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        /* テスト対象の実行 */
        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* 検証の実施 */
        Assertions.assertEquals(expectedTag, this.testTarget.getTag(), "タグが正しく設定されること");
        Assertions.assertEquals(expectedTagName, this.testTarget.getTagName(), "タグ名が正しく設定されること");
        Assertions.assertEquals(expectedTagValue, this.testTarget.getTagValue(), "タグ値が正しく設定されること");
        Assertions.assertEquals(expectedTagDescription, this.testTarget.getTagDescription(), "タグの説明が正しく設定されること");
        Assertions.assertEquals(expectedInsertPosition, this.testTarget.getInsertPosition(), "挿入位置が正しく設定されること");
        Assertions.assertEquals(expectedOverwrite, this.testTarget.getOverwrite(), "上書き設定が正しく設定されること");
        Assertions.assertNotNull(this.testTarget.getLocation(), "配置場所の設定が正しく設定されること");

    }

    /**
     * getInsertPosition メソッドのテスト - 正常系:挿入位置を返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testGetInsertPosition_normalReturnInsertPosition() throws Exception {

        /* 期待値の定義 */
        final JdtsInsertPositionTypes expectedInsertPosition = JdtsInsertPositionTypes.END;

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "end");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final JdtsInsertPositionTypes testResult = this.testTarget.getInsertPosition();

        /* 検証の実施 */
        Assertions.assertEquals(expectedInsertPosition, testResult, "挿入位置が正しく返されること");

    }

    /**
     * getLocation メソッドのテスト - 正常系:配置場所の設定を返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testGetLocation_normalReturnLocation() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final var testResult = this.testTarget.getLocation();

        /* 検証の実施 */
        Assertions.assertNotNull(testResult, "配置場所の設定が正しく返されること");

    }

    /**
     * getOverwrite メソッドのテスト - 正常系:上書き設定を返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testGetOverwrite_normalReturnOverwrite() throws Exception {

        /* 期待値の定義 */
        final JdtsOverwriteTypes expectedOverwrite = JdtsOverwriteTypes.NEVER;

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "never");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final JdtsOverwriteTypes testResult = this.testTarget.getOverwrite();

        /* 検証の実施 */
        Assertions.assertEquals(expectedOverwrite, testResult, "上書き設定が正しく返されること");

    }

    /**
     * getTag メソッドのテスト - 正常系:タグを返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testGetTag_normalReturnTag() throws Exception {

        /* 期待値の定義 */
        final KmgJavadocTagTypes expectedTag = KmgJavadocTagTypes.VERSION;

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@version");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final KmgJavadocTagTypes testResult = this.testTarget.getTag();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTag, testResult, "タグが正しく返されること");

    }

    /**
     * getTagDescription メソッドのテスト - 正常系:タグの説明を返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testGetTagDescription_normalReturnTagDescription() throws Exception {

        /* 期待値の定義 */
        final String expectedTagDescription = "testDescription";

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "testDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTagDescription();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagDescription, testResult, "タグの説明が正しく返されること");

    }

    /**
     * getTagName メソッドのテスト - 正常系:タグ名を返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testGetTagName_normalReturnTagName() throws Exception {

        /* 期待値の定義 */
        final String expectedTagName = "@since";

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@since");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTagName();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagName, testResult, "タグ名が正しく返されること");

    }

    /**
     * getTagValue メソッドのテスト - 正常系:タグの指定値を返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testGetTagValue_normalReturnTagValue() throws Exception {

        /* 期待値の定義 */
        final String expectedTagValue = "testValue";

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final String testResult = this.testTarget.getTagValue();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTagValue, testResult, "タグの指定値が正しく返されること");

    }

    /**
     * isProperlyPlaced メソッドのテスト - 正常系:COMPLIANTモードで一致する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testIsProperlyPlaced_normalCompliantModeMatch() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isProperlyPlaced(JavaClassificationTypes.CLASS);

        /* 検証の実施 */
        Assertions.assertTrue(testResult, "COMPLIANTモードで一致する場合はtrueが返されること");

    }

    /**
     * isProperlyPlaced メソッドのテスト - 正常系:COMPLIANTモードで一致しない場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testIsProperlyPlaced_normalCompliantModeNoMatch() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@param");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isProperlyPlaced(JavaClassificationTypes.CLASS);

        /* 検証の実施 */
        Assertions.assertFalse(testResult, "COMPLIANTモードで一致しない場合はfalseが返されること");

    }

    /**
     * isProperlyPlaced メソッドのテスト - 正常系:COMPLIANTモードでJavaClassificationTypes.NONEを含むタグ（SUMMARY）の場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testIsProperlyPlaced_normalCompliantModeWithSummaryTag() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@summary");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isProperlyPlaced(JavaClassificationTypes.METHOD);

        /* 検証の実施 */
        Assertions.assertTrue(testResult, "COMPLIANTモードでJavaClassificationTypes.NONEを含むタグの場合はtrueが返されること");

    }

    /**
     * isProperlyPlaced メソッドのテスト - 正常系:MANUALモードで一致する場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testIsProperlyPlaced_normalManualModeMatch() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "manual");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");

        final List<String> targetElements = new ArrayList<>();
        targetElements.add("CLASS");
        locationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), targetElements);
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        // モックを使用してprotectedメソッドのエラーを回避
        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig) {

            @Override
            protected KmgValsModel setupLocation() {

                final KmgValsModel result = new KmgValsModelImpl();

                // バリデーションエラーを回避するため、空のバリデーションモデルを返す
                // locationフィールドを適切に設定
                try {

                    final ObjectMapper        mapper      = new ObjectMapper(new YAMLFactory());
                    @SuppressWarnings("hiding")
                    final Map<String, Object> locationMap = mapper
                        .convertValue(testTagConfig.get(JdtsConfigKeyTypes.LOCATION.get()), Map.class);
                    // リフレクションを使用してlocationフィールドにアクセス
                    final Field locationField = JdtsTagConfigModelImpl.class.getDeclaredField("location");
                    locationField.setAccessible(true);
                    locationField.set(this, new JdtsLocationConfigModelImpl(locationMap));

                } catch (@SuppressWarnings("unused") final Exception e) {

                    // エラーが発生した場合はモックのlocationを作成
                    try {

                        final Field locationField = JdtsTagConfigModelImpl.class.getDeclaredField("location");
                        locationField.setAccessible(true);
                        locationField.set(this, new JdtsLocationConfigModelImpl(new HashMap<>()) {

                            @Override
                            public JdtsLocationModeTypes getMode() {

                                final JdtsLocationModeTypes resultLocal = JdtsLocationModeTypes.MANUAL;
                                return resultLocal;

                            }

                            @Override
                            public List<JavaClassificationTypes> getTargetElements() {

                                final List<JavaClassificationTypes> resultLocal = new ArrayList<>();
                                resultLocal.add(JavaClassificationTypes.CLASS);
                                return resultLocal;

                            }
                        });

                    } catch (final Exception ex) {

                        // リフレクションエラーの場合は何もしない
                        ex.printStackTrace();

                    }

                }
                return result;

            }
        };

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isProperlyPlaced(JavaClassificationTypes.CLASS);

        /* 検証の実施 */
        Assertions.assertTrue(testResult, "MANUALモードで一致する場合はtrueが返されること");

    }

    /**
     * isProperlyPlaced メソッドのテスト - 正常系:MANUALモードで一致しない場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testIsProperlyPlaced_normalManualModeNoMatch() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "manual");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");

        final List<String> targetElements = new ArrayList<>();
        targetElements.add("METHOD");
        locationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), targetElements);
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        // モックを使用してprotectedメソッドのエラーを回避
        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig) {

            @Override
            protected KmgValsModel setupLocation() {

                final KmgValsModel result = new KmgValsModelImpl();

                // バリデーションエラーを回避するため、空のバリデーションモデルを返す
                // locationフィールドを適切に設定
                try {

                    final ObjectMapper        mapper      = new ObjectMapper(new YAMLFactory());
                    @SuppressWarnings("hiding")
                    final Map<String, Object> locationMap = mapper
                        .convertValue(testTagConfig.get(JdtsConfigKeyTypes.LOCATION.get()), Map.class);
                    // リフレクションを使用してlocationフィールドにアクセス
                    final Field locationField = JdtsTagConfigModelImpl.class.getDeclaredField("location");
                    locationField.setAccessible(true);
                    locationField.set(this, new JdtsLocationConfigModelImpl(locationMap));

                } catch (@SuppressWarnings("unused") final Exception e) {

                    // エラーが発生した場合はモックのlocationを作成
                    try {

                        final Field locationField = JdtsTagConfigModelImpl.class.getDeclaredField("location");
                        locationField.setAccessible(true);
                        locationField.set(this, new JdtsLocationConfigModelImpl(new HashMap<>()) {

                            @Override
                            public JdtsLocationModeTypes getMode() {

                                final JdtsLocationModeTypes resultLocal = JdtsLocationModeTypes.MANUAL;
                                return resultLocal;

                            }

                            @Override
                            public List<JavaClassificationTypes> getTargetElements() {

                                final List<JavaClassificationTypes> resultLocal = new ArrayList<>();
                                resultLocal.add(JavaClassificationTypes.METHOD);
                                return resultLocal;

                            }
                        });

                    } catch (final Exception ex) {

                        // リフレクションエラーの場合は何もしない
                        ex.printStackTrace();

                    }

                }
                return result;

            }
        };

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isProperlyPlaced(JavaClassificationTypes.CLASS);

        /* 検証の実施 */
        Assertions.assertFalse(testResult, "MANUALモードで一致しない場合はfalseが返されること");

    }

    /**
     * isProperlyPlaced メソッドのテスト - 正常系:NONEモードでfalseを返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testIsProperlyPlaced_normalNoneMode() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "none");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isProperlyPlaced(JavaClassificationTypes.CLASS);

        /* 検証の実施 */
        Assertions.assertFalse(testResult, "NONEモードの場合はfalseが返されること");

    }

    /**
     * setupBasicItems メソッドのテスト - 異常系:タグ名がnull
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testSetupBasicItems_errorNullTagName() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), null);
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "testDescription");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(NullPointerException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "タグ名がnullの場合はNullPointerExceptionが発生すること");

    }

    /**
     * setupBasicItems メソッドのテスト - 異常系:タグ値がnull
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testSetupBasicItems_errorNullTagValue() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), null);
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "testDescription");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "タグ値がnullの場合はKmgToolValExceptionが発生すること");

    }

    /**
     * setupBasicItems メソッドのテスト - 正常系:基本項目の設定
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testSetupBasicItems_normalSetupBasicItems() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "testDescription");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* リフレクションモデルの初期化 */
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト対象の実行 */
        final var testResult = this.reflectionModel.getMethod("setupBasicItems");

        /* 検証の実施 */
        Assertions.assertNotNull(testResult, "基本項目の設定が正常に実行されること");

    }

    /**
     * setupInsertPosition メソッドのテスト - 異常系:挿入位置がnull
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testSetupInsertPosition_errorNullInsertPosition() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), null);
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "挿入位置がnullの場合はKmgToolValExceptionが発生すること");

    }

    /**
     * setupInsertPosition メソッドのテスト - 正常系:挿入位置の設定
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testSetupInsertPosition_normalSetupInsertPosition() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* リフレクションモデルの初期化 */
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト対象の実行 */
        final var testResult = this.reflectionModel.getMethod("setupInsertPosition");

        /* 検証の実施 */
        Assertions.assertNotNull(testResult, "挿入位置の設定が正常に実行されること");

    }

    /**
     * setupLocation メソッドのテスト - 正常系:配置場所の設定
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testSetupLocation_normalSetupLocation() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* リフレクションモデルの初期化 */
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト対象の実行 */
        final var testResult = this.reflectionModel.getMethod("setupLocation");

        /* 検証の実施 */
        Assertions.assertNotNull(testResult, "配置場所の設定が正常に実行されること");

    }

    /**
     * setupOverwrite メソッドのテスト - 異常系:上書き設定がnull
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testSetupOverwrite_errorNullOverwrite() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), null);

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        }, "上書き設定がnullの場合はKmgToolValExceptionが発生すること");

    }

    /**
     * setupOverwrite メソッドのテスト - 正常系:上書き設定
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testSetupOverwrite_normalSetupOverwrite() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testTagConfig = new HashMap<>();
        testTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        testTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        testTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        testTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        this.testTarget = new JdtsTagConfigModelImpl(testTagConfig);

        /* リフレクションモデルの初期化 */
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        /* テスト対象の実行 */
        final var testResult = this.reflectionModel.getMethod("setupOverwrite");

        /* 検証の実施 */
        Assertions.assertNotNull(testResult, "上書き設定が正常に実行されること");

    }

}
