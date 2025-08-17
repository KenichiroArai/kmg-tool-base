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

import javafx.stage.FileChooser;
import javafx.stage.Window;
import kmg.core.infrastructure.model.impl.KmgReflectionModelImpl;
import kmg.core.infrastructure.test.AbstractKmgTest;

/**
 * FileChooserWrapperのテスト
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
public class FileChooserWrapperTest extends AbstractKmgTest {

    /**
     * テスト対象
     *
     * @since 0.1.0
     */
    private FileChooserWrapper testTarget;

    /**
     * リフレクションモデル
     *
     * @since 0.1.0
     */
    private KmgReflectionModelImpl reflectionModel;

    /**
     * モックFileChooser
     *
     * @since 0.1.0
     */
    @Mock
    private FileChooser mockFileChooser;

    /**
     * モックWindow
     *
     * @since 0.1.0
     */
    @Mock
    private Window mockWindow;

    /**
     * コンストラクタのテスト - 正常系：FileChooserが正常に作成される
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testConstructor_normalCreateFileChooser() throws Exception {

        /* 期待値の定義 */

        /* 準備 */

        /* テスト対象の実行 */
        this.testTarget = new FileChooserWrapper();

        /* 検証の準備 */
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);
        final FileChooser actualFileChooser = (FileChooser) this.reflectionModel.get("fileChooser");

        /* 検証の実施 */
        Assertions.assertNotNull(actualFileChooser, "FileChooserが正常に作成されていること");

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
        this.testTarget = new FileChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // FileChooserをモックに置き換え
        this.reflectionModel.set("fileChooser", this.mockFileChooser);

        /* テスト対象の実行 */
        this.testTarget.setInitialDirectory(expectedInitialDirectory);

        /* 検証の準備 */

        /* 検証の実施 */
        Mockito.verify(this.mockFileChooser, Mockito.times(1)).setInitialDirectory(expectedInitialDirectory);

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
        this.testTarget = new FileChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // FileChooserをモックに置き換え
        this.reflectionModel.set("fileChooser", this.mockFileChooser);

        /* テスト対象の実行 */
        this.testTarget.setInitialDirectory(null);

        /* 検証の準備 */

        /* 検証の実施 */
        Mockito.verify(this.mockFileChooser, Mockito.times(1)).setInitialDirectory(null);

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
        this.testTarget = new FileChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // FileChooserをモックに置き換え
        this.reflectionModel.set("fileChooser", this.mockFileChooser);

        /* テスト対象の実行 */
        this.testTarget.setTitle(expectedTitle);

        /* 検証の準備 */

        /* 検証の実施 */
        Mockito.verify(this.mockFileChooser, Mockito.times(1)).setTitle(expectedTitle);

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
        this.testTarget = new FileChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // FileChooserをモックに置き換え
        this.reflectionModel.set("fileChooser", this.mockFileChooser);

        /* テスト対象の実行 */
        this.testTarget.setTitle(expectedTitle);

        /* 検証の準備 */

        /* 検証の実施 */
        Mockito.verify(this.mockFileChooser, Mockito.times(1)).setTitle(expectedTitle);

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
        this.testTarget = new FileChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // FileChooserをモックに置き換え
        this.reflectionModel.set("fileChooser", this.mockFileChooser);

        /* テスト対象の実行 */
        this.testTarget.setTitle(null);

        /* 検証の準備 */

        /* 検証の実施 */
        Mockito.verify(this.mockFileChooser, Mockito.times(1)).setTitle(null);

    }

    /**
     * showOpenDialog メソッドのテスト - 正常系：ファイルが選択される
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testShowOpenDialog_normalFileSelected() throws Exception {

        /* 期待値の定義 */
        final File expectedSelectedFile = new File("selected/file.txt");

        /* 準備 */
        this.testTarget = new FileChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // FileChooserをモックに置き換え
        this.reflectionModel.set("fileChooser", this.mockFileChooser);

        // モックの動作を設定
        Mockito.when(this.mockFileChooser.showOpenDialog(this.mockWindow)).thenReturn(expectedSelectedFile);

        /* テスト対象の実行 */
        final File testResult = this.testTarget.showOpenDialog(this.mockWindow);

        /* 検証の準備 */
        final File actualSelectedFile = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSelectedFile, actualSelectedFile, "選択されたファイルが正しく返されること");
        Mockito.verify(this.mockFileChooser, Mockito.times(1)).showOpenDialog(this.mockWindow);

    }

    /**
     * showOpenDialog メソッドのテスト - 準正常系：キャンセルされた場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testShowOpenDialog_semiCancelled() throws Exception {

        /* 期待値の定義 */

        /* 準備 */
        this.testTarget = new FileChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // FileChooserをモックに置き換え
        this.reflectionModel.set("fileChooser", this.mockFileChooser);

        // モックの動作を設定（キャンセル時はnullを返す）
        Mockito.when(this.mockFileChooser.showOpenDialog(this.mockWindow)).thenReturn(null);

        /* テスト対象の実行 */
        final File testResult = this.testTarget.showOpenDialog(this.mockWindow);

        /* 検証の準備 */
        final File actualSelectedFile = testResult;

        /* 検証の実施 */
        Assertions.assertNull(actualSelectedFile, "キャンセル時はnullが返されること");
        Mockito.verify(this.mockFileChooser, Mockito.times(1)).showOpenDialog(this.mockWindow);

    }

    /**
     * showOpenDialog メソッドのテスト - 準正常系：オーナーウィンドウがnullの場合
     *
     * @since 0.1.0
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testShowOpenDialog_semiOwnerWindowNull() throws Exception {

        /* 期待値の定義 */
        final File expectedSelectedFile = new File("selected/file.txt");

        /* 準備 */
        this.testTarget = new FileChooserWrapper();
        this.reflectionModel = new KmgReflectionModelImpl(this.testTarget);

        // FileChooserをモックに置き換え
        this.reflectionModel.set("fileChooser", this.mockFileChooser);

        // モックの動作を設定
        Mockito.when(this.mockFileChooser.showOpenDialog(null)).thenReturn(expectedSelectedFile);

        /* テスト対象の実行 */
        final File testResult = this.testTarget.showOpenDialog(null);

        /* 検証の準備 */
        final File actualSelectedFile = testResult;

        /* 検証の実施 */
        Assertions.assertEquals(expectedSelectedFile, actualSelectedFile, "nullのオーナーウィンドウでも正常に動作すること");
        Mockito.verify(this.mockFileChooser, Mockito.times(1)).showOpenDialog(null);

    }

}
