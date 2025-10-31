package kmg.tool.base.jdts.application.model.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.base.jdts.application.model.JdtsTagConfigModel;
import kmg.tool.base.jdts.application.types.JdtsConfigKeyTypes;

/**
 * Javadocタグ設定の構成モデル実装のテスト<br>
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
public class JdtsConfigsModelImplTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.2.0
     */
    private JdtsConfigsModelImpl testTarget;

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
     * コンストラクタ メソッドのテスト - 異常系:全てのタグ設定でバリデーションエラーが発生
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorAllTagConfigValidationError() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object>       testYamlData = new HashMap<>();
        final List<Map<String, Object>> configs      = new ArrayList<>();

        // 無効なタグ設定1（タグ名が空）
        final Map<String, Object> invalidTagConfig1 = new HashMap<>();
        invalidTagConfig1.put(JdtsConfigKeyTypes.TAG_NAME.get(), "");
        invalidTagConfig1.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue1");
        invalidTagConfig1.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        invalidTagConfig1.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap1 = new HashMap<>();
        locationMap1.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap1.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        invalidTagConfig1.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap1);

        // 無効なタグ設定2（タグ値が空）
        final Map<String, Object> invalidTagConfig2 = new HashMap<>();
        invalidTagConfig2.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        invalidTagConfig2.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "");
        invalidTagConfig2.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        invalidTagConfig2.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap2 = new HashMap<>();
        locationMap2.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap2.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        invalidTagConfig2.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap2);

        configs.add(invalidTagConfig1);
        configs.add(invalidTagConfig2);
        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), configs);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "全てのタグ設定でバリデーションエラーが発生した場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:JdtsConfigsセクションが空
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorEmptyJdtsConfigs() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object>       testYamlData = new HashMap<>();
        final List<Map<String, Object>> emptyConfigs = new ArrayList<>();
        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), emptyConfigs);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "JdtsConfigsセクションが空の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:YAMLデータが空
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorEmptyYamlData() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testYamlData = new HashMap<>();

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "YAMLデータが空の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:JdtsConfigsセクションがnull
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorNullJdtsConfigs() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testYamlData = new HashMap<>();
        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), null);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "JdtsConfigsセクションがnullの場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:YAMLデータがnull
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorNullYamlData() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testYamlData = null;

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "YAMLデータがnullの場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:タグ設定でバリデーションエラーが発生
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorTagConfigValidationError() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object>       testYamlData = new HashMap<>();
        final List<Map<String, Object>> configs      = new ArrayList<>();

        // 無効なタグ設定（タグ名が空）
        final Map<String, Object> invalidTagConfig = new HashMap<>();
        invalidTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "");
        invalidTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "testValue");
        invalidTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        invalidTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        invalidTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        configs.add(invalidTagConfig);
        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), configs);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "タグ設定でバリデーションエラーが発生した場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系:複数の有効なタグ設定で正常に初期化
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_normalMultipleValidConfigs() throws Exception {

        /* 期待値の定義 */
        final int expectedConfigCount = 3;

        /* 準備 */
        final Map<String, Object>       testYamlData = new HashMap<>();
        final List<Map<String, Object>> configs      = new ArrayList<>();

        // 1つ目のタグ設定
        final Map<String, Object> tagConfig1 = new HashMap<>();
        tagConfig1.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        tagConfig1.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestAuthor1");
        tagConfig1.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        tagConfig1.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap1 = new HashMap<>();
        locationMap1.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap1.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        tagConfig1.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap1);

        // 2つ目のタグ設定
        final Map<String, Object> tagConfig2 = new HashMap<>();
        tagConfig2.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@version");
        tagConfig2.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "1.0.0");
        tagConfig2.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "end");
        tagConfig2.put(JdtsConfigKeyTypes.OVERWRITE.get(), "never");

        final Map<String, Object> locationMap2 = new HashMap<>();
        locationMap2.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap2.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");
        tagConfig2.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap2);

        // 3つ目のタグ設定
        final Map<String, Object> tagConfig3 = new HashMap<>();
        tagConfig3.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@since");
        tagConfig3.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "0.1.0");
        tagConfig3.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        tagConfig3.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ifLower");

        final Map<String, Object> locationMap3 = new HashMap<>();
        locationMap3.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap3.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        tagConfig3.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap3);

        configs.add(tagConfig1);
        configs.add(tagConfig2);
        configs.add(tagConfig3);
        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), configs);

        /* テスト対象の実行 */
        this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        /* 検証の準備 */
        final List<JdtsTagConfigModel> actualConfigs = this.testTarget.getJdaTagConfigModels();

        /* 検証の実施 */
        Assertions.assertEquals(expectedConfigCount, actualConfigs.size(), "複数の有効なタグ設定が正しく作成されること");
        Assertions.assertNotNull(actualConfigs.get(0), "1つ目のタグ設定が正しく作成されること");
        Assertions.assertNotNull(actualConfigs.get(1), "2つ目のタグ設定が正しく作成されること");
        Assertions.assertNotNull(actualConfigs.get(2), "3つ目のタグ設定が正しく作成されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系:正常に初期化
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_normalValidConfig() throws Exception {

        /* 期待値の定義 */
        final int expectedConfigCount = 2;

        /* 準備 */
        final Map<String, Object>       testYamlData = new HashMap<>();
        final List<Map<String, Object>> configs      = new ArrayList<>();

        // 1つ目のタグ設定
        final Map<String, Object> tagConfig1 = new HashMap<>();
        tagConfig1.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        tagConfig1.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestAuthor");
        tagConfig1.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        tagConfig1.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap1 = new HashMap<>();
        locationMap1.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap1.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        tagConfig1.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap1);

        // 2つ目のタグ設定
        final Map<String, Object> tagConfig2 = new HashMap<>();
        tagConfig2.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@version");
        tagConfig2.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "1.0.0");
        tagConfig2.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "end");
        tagConfig2.put(JdtsConfigKeyTypes.OVERWRITE.get(), "never");

        final Map<String, Object> locationMap2 = new HashMap<>();
        locationMap2.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap2.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");
        tagConfig2.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap2);

        configs.add(tagConfig1);
        configs.add(tagConfig2);
        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), configs);

        /* テスト対象の実行 */
        this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        /* 検証の準備 */
        final List<JdtsTagConfigModel> actualConfigs = this.testTarget.getJdaTagConfigModels();

        /* 検証の実施 */
        Assertions.assertEquals(expectedConfigCount, actualConfigs.size(), "タグ設定の数が正しく設定されること");
        Assertions.assertNotNull(actualConfigs.get(0), "1つ目のタグ設定が正しく作成されること");
        Assertions.assertNotNull(actualConfigs.get(1), "2つ目のタグ設定が正しく作成されること");

    }

    /**
     * getJdaTagConfigModels メソッドのテスト - 正常系:複数のタグ設定モデルのリストを返す
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testGetJdaTagConfigModels_normalReturnMultipleTagConfigModels() throws Exception {

        /* 期待値の定義 */
        final int expectedConfigCount = 3;

        /* 準備 */
        final Map<String, Object>       testYamlData = new HashMap<>();
        final List<Map<String, Object>> configs      = new ArrayList<>();

        // 1つ目のタグ設定
        final Map<String, Object> tagConfig1 = new HashMap<>();
        tagConfig1.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        tagConfig1.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestAuthor1");
        tagConfig1.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        tagConfig1.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap1 = new HashMap<>();
        locationMap1.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap1.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        tagConfig1.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap1);

        // 2つ目のタグ設定
        final Map<String, Object> tagConfig2 = new HashMap<>();
        tagConfig2.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@version");
        tagConfig2.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "1.0.0");
        tagConfig2.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "end");
        tagConfig2.put(JdtsConfigKeyTypes.OVERWRITE.get(), "never");

        final Map<String, Object> locationMap2 = new HashMap<>();
        locationMap2.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap2.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");
        tagConfig2.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap2);

        // 3つ目のタグ設定
        final Map<String, Object> tagConfig3 = new HashMap<>();
        tagConfig3.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@since");
        tagConfig3.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "0.1.0");
        tagConfig3.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        tagConfig3.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ifLower");

        final Map<String, Object> locationMap3 = new HashMap<>();
        locationMap3.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap3.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        tagConfig3.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap3);

        configs.add(tagConfig1);
        configs.add(tagConfig2);
        configs.add(tagConfig3);
        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), configs);

        this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        /* テスト対象の実行 */
        final List<JdtsTagConfigModel> testResult = this.testTarget.getJdaTagConfigModels();

        /* 検証の準備 */
        final int actualConfigCount = testResult.size();

        /* 検証の実施 */
        Assertions.assertEquals(expectedConfigCount, actualConfigCount, "複数のタグ設定モデルのリストが正しく返されること");
        Assertions.assertNotNull(testResult.get(0), "1つ目のタグ設定モデルが正しく作成されること");
        Assertions.assertNotNull(testResult.get(1), "2つ目のタグ設定モデルが正しく作成されること");
        Assertions.assertNotNull(testResult.get(2), "3つ目のタグ設定モデルが正しく作成されること");

    }

    /**
     * getJdaTagConfigModels メソッドのテスト - 正常系:タグ設定モデルのリストを返す
     *
     * @since 0.2.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testGetJdaTagConfigModels_normalReturnTagConfigModels() throws Exception {

        /* 期待値の定義 */
        final int expectedConfigCount = 1;

        /* 準備 */
        final Map<String, Object>       testYamlData = new HashMap<>();
        final List<Map<String, Object>> configs      = new ArrayList<>();

        final Map<String, Object> tagConfig = new HashMap<>();
        tagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        tagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestAuthor");
        tagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "beginning");
        tagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "always");

        final Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        locationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        tagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationMap);

        configs.add(tagConfig);
        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), configs);

        this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        /* テスト対象の実行 */
        final List<JdtsTagConfigModel> testResult = this.testTarget.getJdaTagConfigModels();

        /* 検証の準備 */
        final int actualConfigCount = testResult.size();

        /* 検証の実施 */
        Assertions.assertEquals(expectedConfigCount, actualConfigCount, "タグ設定モデルのリストが正しく返されること");
        Assertions.assertNotNull(testResult.get(0), "タグ設定モデルが正しく作成されること");

    }

}
