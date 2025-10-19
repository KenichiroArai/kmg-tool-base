package kmg.tool.base.is.presentation.ui.gui;

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
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;

/**
 * 挿入SQL作成ツール<br>
 * <p>
 * 「Is」は、InsertionSqlの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class IsCreationTool extends Application {

    /**
     * ステージタイトル
     *
     * @since 0.2.0
     */
    private static final String STAGE_TITLE = "挿入SQL作成画面"; //$NON-NLS-1$

    /**
     * FXMLファイルパス
     *
     * @since 0.2.0
     */
    private static final String FXML_PATH = "/kmg/tool/application/ui/gui/IsCreationScreenGui.fxml"; //$NON-NLS-1$

    /**
     * ロガー
     *
     * @since 0.2.0
     */
    private final Logger logger;

    /**
     * メッセージソース
     *
     * @since 0.2.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * Springアプリケーションコンテキスト
     *
     * @since 0.2.0
     */
    private ConfigurableApplicationContext springContext;

    /**
     * エントリポイント<br>
     *
     * @since 0.2.0
     *
     * @param args
     *             オプション
     */
    public static void main(final String[] args) {

        Application.launch(IsCreationTool.class, args);

    }

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public IsCreationTool() {

        this(LoggerFactory.getLogger(IsCreationTool.class));

    }

    /**
     * カスタムロガーを使用して初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param logger
     *               ロガー
     */
    protected IsCreationTool(final Logger logger) {

        this.logger = logger;

    }

    /**
     * 初期化<br>
     *
     * @since 0.2.0
     */
    @SuppressWarnings("resource")
    @Override
    public void init() {

        this.springContext = new SpringApplicationBuilder(IsCreationTool.class).run();

    }

    /**
     * 開始<br>
     *
     * @since 0.2.0
     *
     * @param stage
     *              ステージ
     */
    @Override
    public void start(final Stage stage) {

        stage.setTitle(IsCreationTool.STAGE_TITLE);

        final URL        url  = this.getClass().getResource(IsCreationTool.FXML_PATH);
        final FXMLLoader fxml = new FXMLLoader(url);
        fxml.setControllerFactory(this.springContext::getBean);
        AnchorPane root;

        try {

            root = fxml.load();

        } catch (final IOException e) {

            // ログの出力
            final KmgToolLogMsgTypes logType     = KmgToolLogMsgTypes.KMGTOOL_LOG10002;
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
     * @since 0.2.0
     */
    @Override
    public void stop() {

        this.springContext.close();

    }
}
