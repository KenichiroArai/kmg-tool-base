package kmg.tool.cmn.presentation.ui.gui.stage.wrapper;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * FileChooserのラッパークラス<br>
 * テスト時にモック化しやすくするためのラッパークラスです。
 *
 * @author KenichiroArai
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 */
public class FileChooserWrapper {

    /** FileChooser */
    private final FileChooser fileChooser;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @since 1.0.0
     */
    public FileChooserWrapper() {

        this.fileChooser = new FileChooser();

    }

    /**
     * 初期ディレクトリを設定する<br>
     *
     * @since 1.0.0
     *
     * @param initialDirectory
     *                         初期ディレクトリ
     */
    public void setInitialDirectory(final File initialDirectory) {

        this.fileChooser.setInitialDirectory(initialDirectory);

    }

    /**
     * タイトルを設定する<br>
     *
     * @since 1.0.0
     *
     * @param title
     *              タイトル
     */
    public void setTitle(final String title) {

        this.fileChooser.setTitle(title);

    }

    /**
     * ファイル選択ダイアログを表示する<br>
     *
     * @since 1.0.0
     *
     * @param ownerWindow
     *                    オーナーウィンドウ
     *
     * @return 選択されたファイル、キャンセルされた場合はnull
     */
    public File showOpenDialog(final Window ownerWindow) {

        final File result = this.fileChooser.showOpenDialog(ownerWindow);
        return result;

    }

}
