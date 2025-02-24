package kmg.tool.presentation.ui.gui;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * ＫＭＧツール挿入ＳＱＬ作成画面ＧＵＩ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class KmgTlInsertionSqlCreationScreenGui extends Application {

    /**
     * エントリポイント<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param args
     *             オプション
     */
    public static void main(final String[] args) {

        Application.launch(args);
    }

    /**
     * 開始<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stage
     *              ステージ
     */
    @Override
    public void start(final Stage stage) {

        stage.setTitle("挿入ＳＱＬ作成画面");
        try {
            final URL url = this.getClass()
                    .getResource("/kmg/tool/application/ui/gui/KmgTlInsertionSqlCreationScreenGui.fxml");
            final FXMLLoader fxml = new FXMLLoader(url);
            final AnchorPane root = fxml.load();
            final Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }
}
