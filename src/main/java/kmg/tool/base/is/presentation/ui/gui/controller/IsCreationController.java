package kmg.tool.base.is.presentation.ui.gui.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kmg.core.infrastructure.model.KmgPfaMeasModel;
import kmg.core.infrastructure.model.impl.KmgPfaMeasModelImpl;
import kmg.core.infrastructure.type.KmgString;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.cmn.presentation.ui.gui.stage.wrapper.DirectoryChooserWrapper;
import kmg.tool.base.cmn.presentation.ui.gui.stage.wrapper.FileChooserWrapper;
import kmg.tool.base.is.application.service.IsCreationService;

/**
 * 挿入SQL作成画面コントローラ<br>
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
@Controller
public class IsCreationController implements Initializable {

    /**
     * ファイル選択ダイアログのタイトル
     *
     * @since 0.2.0
     */
    private static final String FILE_CHOOSER_TITLE = "ファイル選択"; //$NON-NLS-1$

    /**
     * ディレクトリ選択ダイアログのタイトル
     *
     * @since 0.2.0
     */
    private static final String DIRECTORY_CHOOSER_TITLE = "ディレクトリ選択"; //$NON-NLS-1$

    /**
     * デフォルトのディレクトリパス
     *
     * @since 0.2.0
     */
    private static final String DEFAULT_DIRECTORY_PATH = "c:/"; //$NON-NLS-1$

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
     * 挿入SQL作成サービス
     *
     * @since 0.2.0
     */
    @Autowired
    private IsCreationService isCreationService;

    /**
     * ファイル選択ダイアログのラッパー
     *
     * @since 0.2.0
     */
    private FileChooserWrapper fileChooserWrapper;

    /**
     * ディレクトリ選択ダイアログのラッパー
     *
     * @since 0.2.0
     */
    private DirectoryChooserWrapper directoryChooserWrapper;

    /**
     * 入力ファイルテキストボックス
     *
     * @since 0.2.0
     */
    @FXML
    private TextField txtInputFile;

    /**
     * 入力ファイル読み込みボタン
     *
     * @since 0.2.0
     */
    @FXML
    private Button btnInputFileOpen;

    /**
     * 出力ディレクトリテキストボックス
     *
     * @since 0.2.0
     */
    @FXML
    private TextField txtOutputDirectory;

    /**
     * 出力ディレクトリ読み込みボタン
     *
     * @since 0.2.0
     */
    @FXML
    private Button btnOutputDirectoryOpen;

    /**
     * スレッド数テキストボックス
     *
     * @since 0.2.0
     */
    @FXML
    private TextField txtThreadNum;

    /**
     * 実行ボタン
     *
     * @since 0.2.0
     */
    @FXML
    private Button btnRun;

    /**
     * 処理時間ラベル
     *
     * @since 0.2.0
     */
    @FXML
    private Label lblProcTime;

    /**
     * 処理時間単位ラベル
     *
     * @since 0.2.0
     */
    @FXML
    private Label lblProcTimeUnit;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public IsCreationController() {

        this(LoggerFactory.getLogger(IsCreationController.class));

    }

    /**
     * カスタムロガーを使用して初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param logger
     *               ロガー
     */
    public IsCreationController(final Logger logger) {

        this.logger = logger;
        this.fileChooserWrapper = new FileChooserWrapper();
        this.directoryChooserWrapper = new DirectoryChooserWrapper();

    }

    /**
     * 初期化<br>
     *
     * @since 0.2.0
     *
     * @param location
     *                  ロケーション
     * @param resources
     *                  リソース
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        /* スレッド数の初期値を設定する */
        // CPUの論理プロセッサ数を取得
        final Runtime runtime          = Runtime.getRuntime();
        final int     defaultThreadNum = runtime.availableProcessors();
        // テキストボックスに設定
        this.txtThreadNum.setText(String.valueOf(defaultThreadNum));

    }

    /**
     * メイン処理
     *
     * @since 0.2.0
     *
     * @param inputPath
     *                   入力パス
     * @param outputPath
     *                   出力パス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    public void mainProc(final Path inputPath, final Path outputPath) throws KmgToolMsgException {

        /* 挿入SQL作成サービス */
        final short threadNum = Short.parseShort(this.txtThreadNum.getText());
        this.isCreationService.initialize(inputPath, outputPath, threadNum);
        this.isCreationService.outputInsertionSql();

    }

    /**
     * ディレクトリ選択ダイアログのラッパーを設定する<br>
     *
     * @since 0.2.0
     *
     * @param directoryChooserWrapper
     *                                ディレクトリ選択ダイアログのラッパー
     */
    public void setDirectoryChooserWrapper(final DirectoryChooserWrapper directoryChooserWrapper) {

        this.directoryChooserWrapper = directoryChooserWrapper;

    }

    /**
     * ファイル選択ダイアログのラッパーを設定する<br>
     *
     * @since 0.2.0
     *
     * @param fileChooserWrapper
     *                           ファイル選択ダイアログのラッパー
     */
    public void setFileChooserWrapper(final FileChooserWrapper fileChooserWrapper) {

        this.fileChooserWrapper = fileChooserWrapper;

    }

    /**
     * 入力ファイル読み込みボタンクリックイベント
     *
     * @since 0.2.0
     *
     * @param event
     *              アクションイベント
     */
    @FXML
    private void onCalcInputFileOpenClicked(final ActionEvent event) {

        this.fileChooserWrapper.setTitle(IsCreationController.FILE_CHOOSER_TITLE);
        String defaultFilePath = this.txtInputFile.getText();

        if (KmgString.isEmpty(defaultFilePath)) {

            defaultFilePath = IsCreationController.DEFAULT_DIRECTORY_PATH;

        }
        File defaultFile = new File(defaultFilePath);

        if (defaultFile.isFile()) {

            defaultFile = defaultFile.getParentFile();

        }
        this.fileChooserWrapper.setInitialDirectory(defaultFile);
        final File file = this.fileChooserWrapper.showOpenDialog(null);

        if (file != null) {

            this.txtInputFile.setText(file.getAbsolutePath());

        }

    }

    /**
     * 出力ディレクトリ読み込みボタンクリックイベント
     *
     * @since 0.2.0
     *
     * @param event
     *              アクションイベント
     */
    @FXML
    private void onCalcOutputDirectoryOpenClicked(final ActionEvent event) {

        this.directoryChooserWrapper.setTitle(IsCreationController.DIRECTORY_CHOOSER_TITLE);
        String defaultFilePath = this.txtOutputDirectory.getText();

        if (KmgString.isEmpty(defaultFilePath)) {

            defaultFilePath = IsCreationController.DEFAULT_DIRECTORY_PATH;

        }
        File defaultFile = new File(defaultFilePath);

        if (defaultFile.isFile()) {

            defaultFile = defaultFile.getParentFile();

        }
        this.directoryChooserWrapper.setInitialDirectory(defaultFile);
        final File file = this.directoryChooserWrapper.showDialog(null);

        if (file != null) {

            this.txtOutputDirectory.setText(file.getAbsolutePath());

        }

    }

    /**
     * 実行ボタンクリックイベント
     *
     * @since 0.2.0
     *
     * @param event
     *              アクションイベント
     */
    @FXML
    private void onCalcRunClicked(final ActionEvent event) {

        final KmgPfaMeasModel pfaMeas = new KmgPfaMeasModelImpl();
        pfaMeas.start();

        final Path inputPath  = Paths.get(this.txtInputFile.getText());
        final Path outputPath = Paths.get(this.txtOutputDirectory.getText());

        try {

            // メイン処理
            this.mainProc(inputPath, outputPath);

        } catch (final KmgToolMsgException e) {

            // ログの出力
            final KmgToolLogMsgTypes logType     = KmgToolLogMsgTypes.KMGTOOL_LOG10001;
            final Object[]           messageArgs = {
                inputPath, outputPath,
            };
            final String             msg         = this.messageSource.getLogMessage(logType, messageArgs);
            this.logger.error(msg, e);

        } finally {

            pfaMeas.end();
            this.lblProcTime.setText(String.valueOf(pfaMeas.getElapsedTime()));
            this.lblProcTimeUnit.setText(pfaMeas.getTimeUnit().getUnitName());

        }

    }
}
