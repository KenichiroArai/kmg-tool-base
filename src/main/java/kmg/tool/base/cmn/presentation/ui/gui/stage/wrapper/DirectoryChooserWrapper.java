package kmg.tool.base.cmn.presentation.ui.gui.stage.wrapper;

import java.io.File;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

/**
 * DirectoryChooserのラッパークラス<br>
 * テスト時にモック化しやすくするためのラッパークラスです。
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public class DirectoryChooserWrapper {

    /**
     * DirectoryChooser
     *
     * @since 0.2.0
     */
    private final DirectoryChooser directoryChooser;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public DirectoryChooserWrapper() {

        this.directoryChooser = new DirectoryChooser();

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

        this.directoryChooser.setInitialDirectory(initialDirectory);

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

        this.directoryChooser.setTitle(title);

    }

    /**
     * ディレクトリ選択ダイアログを表示する<br>
     *
     * @since 0.2.0
     *
     * @param ownerWindow
     *                    オーナーウィンドウ
     *
     * @return 選択されたディレクトリ、キャンセルされた場合はnull
     */
    public File showDialog(final Window ownerWindow) {

        final File result = this.directoryChooser.showDialog(ownerWindow);
        return result;

    }

}
