package kmg.tool.presentation.ui.gui.isc;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.infrastructure.type.msg.KmgToolLogMsgTypes;

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

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /** メッセージソース */
    @Autowired
    private KmgMessageSource messageSource;

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
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     */
    public InsertionSqlCreationTool() {

        this(LoggerFactory.getLogger(InsertionSqlCreationTool.class));

    }

    /**
     * カスタムロガーを使用して初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *               ロガー
     */
    protected InsertionSqlCreationTool(final Logger logger) {

        this.logger = logger;

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

            // ログの出力
            final KmgToolLogMsgTypes logType     = KmgToolLogMsgTypes.KMGTOOL_LOG43001;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getLogMessage(logType, messageArgs);
            this.logger.error(msg, e);
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
