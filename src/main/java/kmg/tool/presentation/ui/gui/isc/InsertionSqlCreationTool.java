package kmg.tool.presentation.ui.gui.isc;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

    /** メッセージソース */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

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
        AnchorPane       root;

        try {

            root = fxml.load();

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/04/25 【挿入SQL作成】：ログ。挿入SQL作成ツールの開始に失敗しました。
            // ログの出力
            final KmgToolLogMsgTypes logType     = KmgToolLogMsgTypes.NONE;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getLogMessage(logType, messageArgs);
            this.logger.error(msg, e);

            return;

        }
        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
