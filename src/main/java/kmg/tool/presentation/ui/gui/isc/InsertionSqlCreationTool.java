package kmg.tool.presentation.ui.gui.isc;

import java.io.IOException;
import java.net.URL;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

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
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class InsertionSqlCreationTool extends Application {

    /** ステージタイトル */
    private static final String STAGE_TITLE = "挿入SQL作成画面";

    /** FXMLファイルパス */
    private static final String FXML_PATH = "/kmg/tool/application/ui/gui/KmgTlInsertionSqlCreationScreenGui.fxml";

    // TODO KenichiroArai 2025/05/22 作業中
    // /** ロガー */
    // private Logger logger;
    //
    // /** メッセージソース */
    // private KmgMessageSource messageSource;

    /** Springアプリケーションコンテキスト */
    private ConfigurableApplicationContext springContext;

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

        Application.launch(InsertionSqlCreationTool.class, args);

    }

    /**
     * 初期化<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     */
    @SuppressWarnings("resource")
    @Override
    public void init() {

        // this.springContext = SpringApplication.run(InsertionSqlCreationTool.class);
        this.springContext = new SpringApplicationBuilder(InsertionSqlCreationTool.class).run();

        // TODO KenichiroArai 2025/05/22 作業中
        // this.logger = this.springContext.getBean(Logger.class);
        // this.messageSource = this.springContext.getBean(KmgMessageSource.class);

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

        stage.setTitle(InsertionSqlCreationTool.STAGE_TITLE);

        final URL        url  = this.getClass().getResource(InsertionSqlCreationTool.FXML_PATH);
        final FXMLLoader fxml = new FXMLLoader(url);
        fxml.setControllerFactory(this.springContext::getBean);
        AnchorPane root;

        try {

            root = fxml.load();

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/04/25 【挿入SQL作成】：ログ。挿入SQL作成ツールの開始に失敗しました。
            // ログの出力
            // final KmgToolLogMsgTypes logType = KmgToolLogMsgTypes.NONE;
            // final Object[] messageArgs = {};
            // final String msg = this.messageSource.getLogMessage(logType, messageArgs);
            // this.logger.error(msg, e);
            return;

        }
        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * 停止<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     */
    @Override
    public void stop() {

        this.springContext.close();

    }
}
