package kmg.tool.cmn.presentation.ui.gui.stage.wrapper;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;

/**
 * DirectoryChooserWrapperのテスト
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
public class DirectoryChooserWrapperTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private DirectoryChooserWrapper testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.1.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * モックDirectoryChooser
     *
     * @since 0.1.0
     */
    @Mock
    private DirectoryChooser mockDirectoryChooser;

    /**
     * モックWindow
     *
     * @since 0.1.0
     */
    @Mock
    private Window mockWindow;

    /**
     * コンストラクタのテスト - 正常系：DirectoryChooserが正常に作成される
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCreateDirectoryChooser() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        this.testTarget = new DirectoryChooserWrapper();

        /* 検証の準備 */
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        final DirectoryChooser actualDirectoryChooser = (DirectoryChooser) this.reflectionModel.get("directoryChooser");

        /* 検証の実施 */
        Assertions.assertNotNull(actualDirectoryChooser, "DirectoryChooserが正常に作成されていること");

    }

    /**
     * setInitialDirectory メソッドのテスト - 正常系：初期ディレクトリが設定される
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetInitialDirectory_normalSetInitialDirectory() throws Exception {

        /* 期待値の定義 */
        final File expectedInitialDirectory = new File("test/directory");

        /* 準備 */
        this.testTarget = new DirectoryChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // DirectoryChooserをモックに置き換え
        this.reflectionModel.set("directoryChooser", this.mockDirectoryChooser);

        /* テスト対象の実行 */
        this.testTarget.setInitialDirectory(expectedInitialDirectory);

        /* 検証の準備 */

        /* 検証の実施 */
        Mockito.verify(this.mockDirectoryChooser, Mockito.times(1)).setInitialDirectory(expectedInitialDirectory);

    }

    /**
     * setInitialDirectory メソッドのテスト - 準正常系：初期ディレクトリがnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetInitialDirectory_semiInitialDirectoryNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.testTarget = new DirectoryChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // DirectoryChooserをモックに置き換え
        this.reflectionModel.set("directoryChooser", this.mockDirectoryChooser);

        /* テスト対象の実行 */
        this.testTarget.setInitialDirectory(null);

        /* 検証の準備 */

        /* 検証の実施 */
        Mockito.verify(this.mockDirectoryChooser, Mockito.times(1)).setInitialDirectory(null);

    }

    /**
     * setTitle メソッドのテスト - 正常系：タイトルが設定される
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetTitle_normalSetTitle() throws Exception {

        /* 期待値の定義 */
        final String expectedTitle = "テストタイトル";

        /* 準備 */
        this.testTarget = new DirectoryChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // DirectoryChooserをモックに置き換え
        this.reflectionModel.set("directoryChooser", this.mockDirectoryChooser);

        /* テスト対象の実行 */
        this.testTarget.setTitle(expectedTitle);

        /* 検証の準備 */

        /* 検証の実施 */
        Mockito.verify(this.mockDirectoryChooser, Mockito.times(1)).setTitle(expectedTitle);

    }

    /**
     * setTitle メソッドのテスト - 準正常系：タイトルが空文字の場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetTitle_semiTitleEmpty() throws Exception {

        /* 期待値の定義 */
        final String expectedTitle = "";

        /* 準備 */
        this.testTarget = new DirectoryChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // DirectoryChooserをモックに置き換え
        this.reflectionModel.set("directoryChooser", this.mockDirectoryChooser);

        /* テスト対象の実行 */
        this.testTarget.setTitle(expectedTitle);

        /* 検証の準備 */

        /* 検証の実施 */
        Mockito.verify(this.mockDirectoryChooser, Mockito.times(1)).setTitle(expectedTitle);

    }

    /**
     * setTitle メソッドのテスト - 準正常系：タイトルがnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testSetTitle_semiTitleNull() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.testTarget = new DirectoryChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // DirectoryChooserをモックに置き換え
        this.reflectionModel.set("directoryChooser", this.mockDirectoryChooser);

        /* テスト対象の実行 */
        this.testTarget.setTitle(null);

        /* 検証の準備 */

        /* 検証の実施 */
        Mockito.verify(this.mockDirectoryChooser, Mockito.times(1)).setTitle(null);

    }

    /**
     * showDialog メソッドのテスト - 正常系：ディレクトリが選択される
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testShowDialog_normalDirectorySelected() throws Exception {

        /* 期待値の定義 */
        final File expectedSelectedDirectory = new File("selected/directory");

        /* 準備 */
        this.testTarget = new DirectoryChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // DirectoryChooserをモックに置き換え
        this.reflectionModel.set("directoryChooser", this.mockDirectoryChooser);

        // モックの動作を設定
        Mockito.when(this.mockDirectoryChooser.showDialog(this.mockWindow)).thenReturn(expectedSelectedDirectory);

        /* テスト対象の実行 */
        final File testResult = this.testTarget.showDialog(this.mockWindow);

        /* 検証の準備 */
        final File actualSelectedDirectory = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSelectedDirectory, actualSelectedDirectory, "選択されたディレクトリが正しく返されること");
        Mockito.verify(this.mockDirectoryChooser, Mockito.times(1)).showDialog(this.mockWindow);

    }

    /**
     * showDialog メソッドのテスト - 準正常系：キャンセルされた場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testShowDialog_semiCancelled() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.testTarget = new DirectoryChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // DirectoryChooserをモックに置き換え
        this.reflectionModel.set("directoryChooser", this.mockDirectoryChooser);

        // モックの動作を設定（キャンセル時はnullを返す）
        Mockito.when(this.mockDirectoryChooser.showDialog(this.mockWindow)).thenReturn(null);

        /* テスト対象の実行 */
        final File testResult = this.testTarget.showDialog(this.mockWindow);

        /* 検証の準備 */
        final File actualSelectedDirectory = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualSelectedDirectory, "キャンセル時はnullが返されること");
        Mockito.verify(this.mockDirectoryChooser, Mockito.times(1)).showDialog(this.mockWindow);

    }

    /**
     * showDialog メソッドのテスト - 準正常系：オーナーウィンドウがnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testShowDialog_semiOwnerWindowNull() throws Exception {

        /* 期待値の定義 */
        final File expectedSelectedDirectory = new File("selected/directory");

        /* 準備 */
        this.testTarget = new DirectoryChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // DirectoryChooserをモックに置き換え
        this.reflectionModel.set("directoryChooser", this.mockDirectoryChooser);

        // モックの動作を設定
        Mockito.when(this.mockDirectoryChooser.showDialog(null)).thenReturn(expectedSelectedDirectory);

        /* テスト対象の実行 */
        final File testResult = this.testTarget.showDialog(null);

        /* 検証の準備 */
        final File actualSelectedDirectory = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSelectedDirectory, actualSelectedDirectory, "nullのオーナーウィンドウでも正常に動作すること");
        Mockito.verify(this.mockDirectoryChooser, Mockito.times(1)).showDialog(null);

    }

}
