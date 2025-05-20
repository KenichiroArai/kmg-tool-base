package kmg.tool.presentation.ui.gui;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 挿入SQL作成ツール<br>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
public class InsertionSqlCreationScreenTool extends Application {

    /** ステージタイトル */
    private static final String STAGE_TITLE = "挿入SQL作成画面";

    /** FXMLファイルパス */
    private static final String FXML_PATH = "/kmg/tool/application/ui/gui/KmgTlInsertionSqlCreationScreenGui.fxml";

    /**
     * エントリポイント<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
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
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param stage
     *              ステージ
     */
    @Override
    public void start(final Stage stage) {

        stage.setTitle(STAGE_TITLE);

        try {

            final URL        url   = this.getClass().getResource(FXML_PATH);
            final FXMLLoader fxml  = new FXMLLoader(url);
            final AnchorPane root  = fxml.load();
            final Scene      scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (final IOException e) {

            e.printStackTrace();

        }

    }
}
