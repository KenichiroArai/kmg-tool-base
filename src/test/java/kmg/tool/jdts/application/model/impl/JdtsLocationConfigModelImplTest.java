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

    /** テスト対象 */
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
     * コンストラクタ メソッドのテスト - 異常系:COMPLIANTモードでTARGET_ELEMENTSが指定されている
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorCompliantModeWithTargetElements() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");

        final List<String> targetElements = new ArrayList<>();
        targetElements.add("CLASS");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), targetElements);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        }, "COMPLIANTモードでTARGET_ELEMENTSが指定されている場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:無効な対象要素が指定されている
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorInvalidTargetElement() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "MANUAL");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");

        final List<String> targetElements = new ArrayList<>();
        targetElements.add("INVALID_ELEMENT");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), targetElements);

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        }, "無効な対象要素が指定されている場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 異常系:MANUALモードでTARGET_ELEMENTSが指定されていない
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_errorManualModeWithoutTargetElements() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "MANUAL");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");
        // TARGET_ELEMENTSを指定しない

        /* テスト対象の実行と検証 */
        Assertions.assertThrows(KmgToolValException.class, () -> {

            this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        }, "MANUALモードでTARGET_ELEMENTSが指定されていない場合はKmgToolValExceptionが発生すること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系:COMPLIANTモードで正常に初期化
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalCompliantMode() throws Exception {

        /* 期待値の定義 */
        final JdtsLocationModeTypes expectedMode               = JdtsLocationModeTypes.COMPLIANT;
        final int                   expectedTargetElementsSize = 0;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());

        /* テスト対象の実行 */
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* 検証の実施 */
        Assertions.assertEquals(expectedMode, this.testTarget.getMode(), "配置方法が正しく設定されること");
        Assertions.assertTrue(this.testTarget.isRemoveIfMisplaced(), "誤配置時の削除設定が正しく設定されること");
        Assertions.assertEquals(expectedTargetElementsSize, this.testTarget.getTargetElements().size(),
            "対象要素リストが空であること");

    }

    /**
     * コンストラクタ メソッドのテスト - 正常系:MANUALモードで正常に初期化
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testConstructor_normalManualMode() throws Exception {

        /* 期待値の定義 */
        final JdtsLocationModeTypes expectedMode               = JdtsLocationModeTypes.MANUAL;
        final int                   expectedTargetElementsSize = 2;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "MANUAL");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");

        final List<String> targetElements = new ArrayList<>();
        targetElements.add("CLASS");
        targetElements.add("METHOD");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), targetElements);

        /* テスト対象の実行 */
        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* 検証の実施 */
        Assertions.assertEquals(expectedMode, this.testTarget.getMode(), "配置方法が正しく設定されること");
        Assertions.assertFalse(this.testTarget.isRemoveIfMisplaced(), "誤配置時の削除設定が正しく設定されること");
        Assertions.assertEquals(expectedTargetElementsSize, this.testTarget.getTargetElements().size(),
            "対象要素リストのサイズが正しいこと");
        Assertions.assertTrue(this.testTarget.getTargetElements().contains(JavaClassificationTypes.CLASS),
            "CLASS要素が含まれていること");
        Assertions.assertTrue(this.testTarget.getTargetElements().contains(JavaClassificationTypes.METHOD),
            "METHOD要素が含まれていること");

    }

    /**
     * getMode メソッドのテスト - 正常系:配置方法を返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetMode_normalReturnMode() throws Exception {

        /* 期待値の定義 */
        final JdtsLocationModeTypes expectedMode = JdtsLocationModeTypes.COMPLIANT;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());

        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* テスト対象の実行 */
        final JdtsLocationModeTypes testResult = this.testTarget.getMode();

        /* 検証の実施 */
        Assertions.assertEquals(expectedMode, testResult, "配置方法が正しく返されること");

    }

    /**
     * getTargetElements メソッドのテスト - 正常系:空の対象要素リストを返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTargetElements_normalReturnEmptyTargetElements() throws Exception {

        /* 期待値の定義 */
        final int expectedTargetElementsSize = 0;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());

        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* テスト対象の実行 */
        final List<JavaClassificationTypes> testResult = this.testTarget.getTargetElements();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetElementsSize, testResult.size(), "対象要素リストが空であること");

    }

    /**
     * getTargetElements メソッドのテスト - 正常系:nullの対象要素リストを返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTargetElements_normalReturnNullTargetElements() throws Exception {

        /* 期待値の定義 */
        final int expectedTargetElementsSize = 0;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), null);

        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* テスト対象の実行 */
        final List<JavaClassificationTypes> testResult = this.testTarget.getTargetElements();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetElementsSize, testResult.size(), "対象要素リストが空であること");

    }

    /**
     * getTargetElements メソッドのテスト - 正常系:対象要素リストを返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testGetTargetElements_normalReturnTargetElements() throws Exception {

        /* 期待値の定義 */
        final int expectedTargetElementsSize = 1;

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "MANUAL");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "true");

        final List<String> targetElements = new ArrayList<>();
        targetElements.add("CLASS");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), targetElements);

        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* テスト対象の実行 */
        final List<JavaClassificationTypes> testResult = this.testTarget.getTargetElements();

        /* 検証の実施 */
        Assertions.assertEquals(expectedTargetElementsSize, testResult.size(), "対象要素リストのサイズが正しいこと");
        Assertions.assertTrue(testResult.contains(JavaClassificationTypes.CLASS), "CLASS要素が含まれていること");

    }

    /**
     * isRemoveIfMisplaced メソッドのテスト - 正常系:誤配置時の削除設定を返す
     *
     * @throws Exception
     *                   テスト実行時に発生する可能性のある例外
     *
     * @since 0.1.0
     */
    @Test
    public void testIsRemoveIfMisplaced_normalReturnRemoveIfMisplaced() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        final Map<String, Object> testLocationMap = new HashMap<>();
        testLocationMap.put(JdtsConfigKeyTypes.MODE.get(), "COMPLIANT");
        testLocationMap.put(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get(), "false");
        testLocationMap.put(JdtsConfigKeyTypes.TARGET_ELEMENTS.get(), new ArrayList<>());

        this.testTarget = new JdtsLocationConfigModelImpl(testLocationMap);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.isRemoveIfMisplaced();

        /* 検証の実施 */
        Assertions.assertFalse(testResult, "誤配置時の削除設定が正しく返されること");

    }

}
