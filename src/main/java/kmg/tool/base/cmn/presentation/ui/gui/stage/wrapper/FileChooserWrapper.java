package kmg.tool.base.cmn.presentation.ui.gui.stage.wrapper;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * FileChooserのラッパークラス<br>
 * テスト時にモック化しやすくするためのラッパークラスです。
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public class FileChooserWrapper {

    /**
     * FileChooser
     *
     * @since 0.2.0
     */
    private final FileChooser fileChooser;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public FileChooserWrapper() {

        this.fileChooser = new FileChooser();

    }

    /**
     * 初期ディレクトリを設定する<br>
     *
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.0
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
