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
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.jdts.application.types.JdtsConfigKeyTypes;
import kmg.tool.jdts.application.types.JdtsLocationModeTypes;

/**
 * Javadocタグ設定の配置場所設定実装のテスト<br>
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
public class JdtsLocationConfigModelImplTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private JdtsLocationConfigModelImpl testTarget;

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
     * コンストラクタ メソッドのテスト - 異常系:COMPLIANTモードでtargetElementsが指定されている場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorCompliantModeWithTargetElements() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        final List<String> targetElements = new ArrayList<>();
        targetElements.add("class");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), targetElements);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        }, "COMPLIANTモードでtargetElementsが指定されている場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:無効なtargetElementsが指定されている場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorInvalidTargetElements() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "manual");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        final List<String> targetElements = new ArrayList<>();
        targetElements.add("invalid_type");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), targetElements);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        }, "無効なtargetElementsが指定されている場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:MANUALモードでtargetElementsが指定されていない場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorManualModeWithoutTargetElements() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "manual");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        }, "MANUALモードでtargetElementsが指定されていない場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:locationMapがnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_errorNullLocationMap() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testLocationMap = null;

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(NullPointerException.class, () -> {

            this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        }, "locationMapがnullの場合はNullPointerExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系:COMPLIANTモードで正常に初期化
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_normalCompliantMode() throws Exception {

        /* 期待値の定義 */
        final JdtsLocationModeTypes expectedMode               = JdtsLocationModeTypes.COMPLIANT;
        final int                   expectedTargetElementsSize = 0;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");

        /* テスト対象の実行 */
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* 検証の準備 */
        final JdtsLocationModeTypes         actualMode              = this.testTarget.getMode();
        final boolean                       actualRemoveIfMisplaced = this.testTarget.isRemoveIfMisplaced();
        final List<JavaClassificationTypes> actualTargetElements    = this.testTarget.getTargetElements();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMode, actualMode, "配置方法が正しく設定されること");
        Assertions.assertTrue(actualRemoveIfMisplaced, "誤配置時に削除するかどうかが正しく設定されること");
        Assertions.assertEquals(expectedTargetElementsSize, actualTargetElements.size(), "対象要素の種類が空であること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系:MANUALモードで正常に初期化
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_normalManualMode() throws Exception {

        /* 期待値の定義 */
        final JdtsLocationModeTypes expectedMode               = JdtsLocationModeTypes.MANUAL;
        final int                   expectedTargetElementsSize = 2;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "manual");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");
        final List<String> targetElements = new ArrayList<>();
        targetElements.add("class");
        targetElements.add("interface");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), targetElements);

        /* テスト対象の実行 */
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* 検証の準備 */
        final JdtsLocationModeTypes         actualMode              = this.testTarget.getMode();
        final boolean                       actualRemoveIfMisplaced = this.testTarget.isRemoveIfMisplaced();
        final List<JavaClassificationTypes> actualTargetElements    = this.testTarget.getTargetElements();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMode, actualMode, "配置方法が正しく設定されること");
        Assertions.assertFalse(actualRemoveIfMisplaced, "誤配置時に削除するかどうかが正しく設定されること");
        Assertions.assertEquals(expectedTargetElementsSize, actualTargetElements.size(), "対象要素の種類の数が正しく設定されること");
        Assertions.assertEquals(JavaClassificationTypes.CLASS, actualTargetElements.get(0), "1つ目の対象要素が正しく設定されること");
        Assertions.assertEquals(JavaClassificationTypes.INTERFACE, actualTargetElements.get(1), "2つ目の対象要素が正しく設定されること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系:NONEモードで正常に初期化
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_normalNoneMode() throws Exception {

        /* 期待値の定義 */
        final JdtsLocationModeTypes expectedMode               = JdtsLocationModeTypes.NONE;
        final int                   expectedTargetElementsSize = 0;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "none");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");

        /* テスト対象の実行 */
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* 検証の準備 */
        final JdtsLocationModeTypes         actualMode              = this.testTarget.getMode();
        final boolean                       actualRemoveIfMisplaced = this.testTarget.isRemoveIfMisplaced();
        final List<JavaClassificationTypes> actualTargetElements    = this.testTarget.getTargetElements();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMode, actualMode, "配置方法が正しく設定されること");
        Assertions.assertFalse(actualRemoveIfMisplaced, "誤配置時に削除するかどうかが正しく設定されること");
        Assertions.assertEquals(expectedTargetElementsSize, actualTargetElements.size(), "対象要素の種類が空であること");

    }

    /**
     * コンストラクタ メソッドのテスト - 準正常系:locationMapが空の場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_semiEmptyLocationMap() throws Exception {

        /* 期待値の定義 */
        final JdtsLocationModeTypes expectedMode               = JdtsLocationModeTypes.NONE;
        final int                   expectedTargetElementsSize = 0;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();

        /* テスト対象の実行 */
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* 検証の準備 */
        final JdtsLocationModeTypes         actualMode              = this.testTarget.getMode();
        final boolean                       actualRemoveIfMisplaced = this.testTarget.isRemoveIfMisplaced();
        final List<JavaClassificationTypes> actualTargetElements    = this.testTarget.getTargetElements();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMode, actualMode, "空のマップの場合はNONEが設定されること");
        Assertions.assertFalse(actualRemoveIfMisplaced, "誤配置時に削除するかどうかがfalseに設定されること");
        Assertions.assertEquals(expectedTargetElementsSize, actualTargetElements.size(), "対象要素の種類が空であること");

    }

    /**
     * コンストラクタ メソッドのテスト - 準正常系:無効なモードが指定されている場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_semiInvalidMode() throws Exception {

        /* 期待値の定義 */
        final JdtsLocationModeTypes expectedMode               = JdtsLocationModeTypes.NONE;
        final int                   expectedTargetElementsSize = 0;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "invalid_mode");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");

        /* テスト対象の実行 */
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* 検証の準備 */
        final JdtsLocationModeTypes         actualMode              = this.testTarget.getMode();
        final boolean                       actualRemoveIfMisplaced = this.testTarget.isRemoveIfMisplaced();
        final List<JavaClassificationTypes> actualTargetElements    = this.testTarget.getTargetElements();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMode, actualMode, "無効なモードが指定された場合はNONEが設定されること");
        Assertions.assertFalse(actualRemoveIfMisplaced, "誤配置時に削除するかどうかが正しく設定されること");
        Assertions.assertEquals(expectedTargetElementsSize, actualTargetElements.size(), "対象要素の種類が空であること");

    }

    /**
     * コンストラクタ メソッドのテスト - 準正常系:removeIfMisplacedが無効な値の場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testConstructor_semiInvalidRemoveIfMisplaced() throws Exception {

        /* 期待値の定義 */
        final JdtsLocationModeTypes expectedMode               = JdtsLocationModeTypes.COMPLIANT;
        final int                   expectedTargetElementsSize = 0;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "invalid_boolean");

        /* テスト対象の実行 */
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* 検証の準備 */
        final JdtsLocationModeTypes         actualMode              = this.testTarget.getMode();
        final boolean                       actualRemoveIfMisplaced = this.testTarget.isRemoveIfMisplaced();
        final List<JavaClassificationTypes> actualTargetElements    = this.testTarget.getTargetElements();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMode, actualMode, "配置方法が正しく設定されること");
        Assertions.assertFalse(actualRemoveIfMisplaced, "無効な値の場合はfalseが設定されること");
        Assertions.assertEquals(expectedTargetElementsSize, actualTargetElements.size(), "対象要素の種類が空であること");

    }

    /**
     * getMode メソッドのテスト - 正常系:配置方法を返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testGetMode_normalReturnMode() throws Exception {

        /* 期待値の定義 */
        final JdtsLocationModeTypes expectedMode = JdtsLocationModeTypes.COMPLIANT;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* テスト対象の実行 */
        final JdtsLocationModeTypes actualMode = this.testTarget.getMode();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMode, actualMode, "配置方法が正しく返されること");

    }

    /**
     * getTargetElements メソッドのテスト - 正常系:空の対象要素の種類を返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testGetTargetElements_normalReturnEmptyTargetElements() throws Exception {

        /* 期待値の定義 */
        final int expectedTargetElementsSize = 0;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* テスト対象の実行 */
        final List<JavaClassificationTypes> actualTargetElements = this.testTarget.getTargetElements();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetElementsSize, actualTargetElements.size(), "対象要素の種類が空で返されること");

    }

    /**
     * getTargetElements メソッドのテスト - 正常系:対象要素の種類を返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testGetTargetElements_normalReturnTargetElements() throws Exception {

        /* 期待値の定義 */
        final int expectedTargetElementsSize = 2;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "manual");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");
        final List<String> targetElements = new ArrayList<>();
        targetElements.add("class");
        targetElements.add("interface");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), targetElements);
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* テスト対象の実行 */
        final List<JavaClassificationTypes> actualTargetElements = this.testTarget.getTargetElements();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetElementsSize, actualTargetElements.size(), "対象要素の種類の数が正しく返されること");
        Assertions.assertEquals(JavaClassificationTypes.CLASS, actualTargetElements.get(0), "1つ目の対象要素が正しく返されること");
        Assertions.assertEquals(JavaClassificationTypes.INTERFACE, actualTargetElements.get(1), "2つ目の対象要素が正しく返されること");

    }

    /**
     * isRemoveIfMisplaced メソッドのテスト - 正常系:誤配置時に削除しない場合を返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testIsRemoveIfMisplaced_normalReturnFalse() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* テスト対象の実行 */
        final boolean actualRemoveIfMisplaced = this.testTarget.isRemoveIfMisplaced();

        /* 検証の実施 */
        Assertions.assertFalse(actualRemoveIfMisplaced, "誤配置時に削除しない場合が正しく返されること");

    }

    /**
     * isRemoveIfMisplaced メソッドのテスト - 正常系:誤配置時に削除するかどうかを返す
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     */
    @Test
    public void testIsRemoveIfMisplaced_normalReturnRemoveIfMisplaced() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "compliant");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* テスト対象の実行 */
        final boolean actualRemoveIfMisplaced = this.testTarget.isRemoveIfMisplaced();

        /* 検証の実施 */
        Assertions.assertTrue(actualRemoveIfMisplaced, "誤配置時に削除するかどうかが正しく返されること");

    }

}
