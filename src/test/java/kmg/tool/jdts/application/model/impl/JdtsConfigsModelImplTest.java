package kmg.tool.jdts.application.model.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kmg.core.infrastructure.test.AbstractKmgTest;
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.jdts.application.model.JdtsTagConfigModel;
import kmg.tool.jdts.application.types.JdtsConfigKeyTypes;

/**
 * Javadocタグ設定の構成モデル実装のテスト<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SuppressWarnings({
    "nls", "static-method"
})
public class JdtsConfigsModelImplTest extends AbstractKmgTest {

    /** テスト対象 */
    private JdtsConfigsModelImpl testTarget;

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
     * コンストラクタ メソッドのテスト - 正常系:有効なYAMLデータで正常に初期化
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalValidYamlData() throws Exception {

        /* 期待値の定義 */
        final int expectedTagConfigCount = 2;

        /* 準備 */
        final Map<String, Object> testYamlData = new HashMap<>();
        final List<Map<String, Object>> tagConfigs = new ArrayList<>();

        // 1つ目の有効なタグ設定
        final Map<String, Object> validTagConfig1 = new HashMap<>();
        validTagConfig1.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@author");
        validTagConfig1.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestAuthor1");
        validTagConfig1.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription1");
        validTagConfig1.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        validTagConfig1.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定1
        final Map<String, Object> locationConfig1 = new HashMap<>();
        locationConfig1.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig1.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig1.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<String>());
        validTagConfig1.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig1);

        // 2つ目の有効なタグ設定
        final Map<String, Object> validTagConfig2 = new HashMap<>();
        validTagConfig2.put(JdtsConfigKeyTypes.TAG_NAME.get(), "@version");
        validTagConfig2.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "1.0.0");
        validTagConfig2.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription2");
        validTagConfig2.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "END");
        validTagConfig2.put(JdtsConfigKeyTypes.OVERWRITE.get(), "NEVER");

        // 配置設定2
        final Map<String, Object> locationConfig2 = new HashMap<>();
        locationConfig2.put(JdtsConfigKeyTypes.MODE.get(), "MANUAL");
        locationConfig2.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");
        locationConfig2.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<String>());
        validTagConfig2.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig2);

        tagConfigs.add(validTagConfig1);
        tagConfigs.add(validTagConfig2);

        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), tagConfigs);

        /* テスト対象の実行 */
        this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        /* 検証の準備 */
        final List<JdtsTagConfigModel> actualTagConfigModels = this.testTarget.getJdaTagConfigModels();

        /* 検証の実施 */
        Assertions.assertNotNull(actualTagConfigModels, "タグ設定モデルリストがnullでないこと");
        Assertions.assertEquals(expectedTagConfigCount, actualTagConfigModels.size(), "タグ設定モデルの数が期待値と一致すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:YAMLデータがnull
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorYamlDataNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testYamlData = null;

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "YAMLデータがnullの場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:YAMLデータが空
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorYamlDataEmpty() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testYamlData = new HashMap<>();

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "YAMLデータが空の場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:JdtsConfigsセクションが存在しない
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorJdtsConfigsSectionMissing() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testYamlData = new HashMap<>();
        testYamlData.put("otherSection", "otherValue");

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "JdtsConfigsセクションが存在しない場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:JdtsConfigsセクションがnull
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorJdtsConfigsSectionNull() throws Exception {

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
     * コンストラクタ メソッドのテスト - 異常系:JdtsConfigsセクションが空リスト
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorJdtsConfigsSectionEmpty() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testYamlData = new HashMap<>();
        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), new ArrayList<>());

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "JdtsConfigsセクションが空リストの場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:タグ設定でバリデーションエラーが発生
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorTagConfigValidationError() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testYamlData = new HashMap<>();
        final List<Map<String, Object>> tagConfigs = new ArrayList<>();

        // 無効なタグ設定（必須項目が不足）
        final Map<String, Object> invalidTagConfig = new HashMap<>();
        invalidTagConfig.put("invalidKey", "invalidValue");
        tagConfigs.add(invalidTagConfig);

        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), tagConfigs);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "タグ設定でバリデーションエラーが発生する場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 準正常系:一部のタグ設定でバリデーションエラーが発生しても処理を継続
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_semiPartialTagConfigValidationError() throws Exception {

        /* 期待値の定義 */
        final int expectedTagConfigCount = 1; // 有効なタグ設定のみが含まれる

        /* 準備 */
        final Map<String, Object> testYamlData = new HashMap<>();
        final List<Map<String, Object>> tagConfigs = new ArrayList<>();

        // 有効なタグ設定
        final Map<String, Object> validTagConfig = new HashMap<>();
        validTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "author");
        validTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestAuthor");
        validTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        validTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        validTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<String>());
        validTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        // 無効なタグ設定（必須項目が不足）
        final Map<String, Object> invalidTagConfig = new HashMap<>();
        invalidTagConfig.put("invalidKey", "invalidValue");

        tagConfigs.add(validTagConfig);
        tagConfigs.add(invalidTagConfig);

        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), tagConfigs);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "一部のタグ設定でバリデーションエラーが発生する場合はKmgToolValExceptionが発生すること");

    }

    /**
     * getJdaTagConfigModels メソッドのテスト - 正常系:タグ設定モデルリストを返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetJdaTagConfigModels_normalReturnTagConfigModels() throws Exception {

        /* 期待値の定義 */
        final int expectedTagConfigCount = 1;

        /* 準備 */
        final Map<String, Object> testYamlData = new HashMap<>();
        final List<Map<String, Object>> tagConfigs = new ArrayList<>();

        // 有効なタグ設定
        final Map<String, Object> validTagConfig = new HashMap<>();
        validTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "author");
        validTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestAuthor");
        validTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        validTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        validTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<String>());
        validTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        tagConfigs.add(validTagConfig);

        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), tagConfigs);

        this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        /* テスト対象の実行 */
        final List<JdtsTagConfigModel> testResult = this.testTarget.getJdaTagConfigModels();

        /* 検証の準備 */
        final List<JdtsTagConfigModel> actualTagConfigModels = testResult;

        /* 検証の実施 */
        Assertions.assertNotNull(actualTagConfigModels, "タグ設定モデルリストがnullでないこと");
        Assertions.assertEquals(expectedTagConfigCount, actualTagConfigModels.size(), "タグ設定モデルの数が期待値と一致すること");

    }

    /**
     * getJdaTagConfigModels メソッドのテスト - 正常系:空のタグ設定モデルリストを返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetJdaTagConfigModels_normalReturnEmptyTagConfigModels() throws Exception {

        /* 期待値の定義 */
        final int expectedTagConfigCount = 0;

        /* 準備 */
        final Map<String, Object> testYamlData = new HashMap<>();
        final List<Map<String, Object>> tagConfigs = new ArrayList<>();

        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), tagConfigs);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "空のタグ設定リストの場合はKmgToolValExceptionが発生すること");

    }

    /**
     * getJdaTagConfigModels メソッドのテスト - 正常系:返されたリストが元のリストと同じ参照であることを確認
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetJdaTagConfigModels_normalReturnSameReference() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testYamlData = new HashMap<>();
        final List<Map<String, Object>> tagConfigs = new ArrayList<>();

        // 有効なタグ設定
        final Map<String, Object> validTagConfig = new HashMap<>();
        validTagConfig.put(JdtsConfigKeyTypes.TAG_NAME.get(), "author");
        validTagConfig.put(JdtsConfigKeyTypes.TAG_VALUE.get(), "TestAuthor");
        validTagConfig.put(JdtsConfigKeyTypes.TAG_DESCRIPTION.get(), "TestDescription");
        validTagConfig.put(JdtsConfigKeyTypes.INSERT_POSITION.get(), "BEGINNING");
        validTagConfig.put(JdtsConfigKeyTypes.OVERWRITE.get(), "ALWAYS");

        // 配置設定
        final Map<String, Object> locationConfig = new HashMap<>();
        locationConfig.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        locationConfig.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        locationConfig.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<String>());
        validTagConfig.put(JdtsConfigKeyTypes.LOCATION.get(), locationConfig);

        tagConfigs.add(validTagConfig);

        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), tagConfigs);

        this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        /* テスト対象の実行 */
        final List<JdtsTagConfigModel> result1 = this.testTarget.getJdaTagConfigModels();
        final List<JdtsTagConfigModel> result2 = this.testTarget.getJdaTagConfigModels();

        /* 検証の実施 */
        Assertions.assertSame(result1, result2, "返されたリストが同じ参照であること");

    }

    /**
     * getJdaTagConfigModels メソッドのテスト - 正常系:空のリストを返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetJdaTagConfigModels_normalReturnEmptyList() throws Exception {

        /* 期待値の定義 */
        final int expectedSize = 0;

        /* 準備 */
        final Map<String, Object> testYamlData = new HashMap<>();
        final List<Map<String, Object>> tagConfigs = new ArrayList<>();

        testYamlData.put(JdtsConfigKeyTypes.JDTS_CONFIGS.get(), tagConfigs);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsConfigsModelImpl(testYamlData);

        }, "空のタグ設定リストの場合はKmgToolValExceptionが発生すること");

    }

}
