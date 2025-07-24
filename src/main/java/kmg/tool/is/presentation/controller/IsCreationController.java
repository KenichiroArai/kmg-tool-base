package kmg.tool.is.presentation.controller;

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
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.is.application.service.IsCreationService;

/**
 * 挿入SQL作成画面コントローラ<br>
 * <p>
 * 「Is」は、InsertionSqlの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 */
@Controller
public class IsCreationController implements Initializable {

    /** ファイル選択ダイアログのタイトル */
    private static final String FILE_CHOOSER_TITLE = "ファイル選択"; //$NON-NLS-1$

    /** ディレクトリ選択ダイアログのタイトル */
    private static final String DIRECTORY_CHOOSER_TITLE = "ディレクトリ選択"; //$NON-NLS-1$

    /** デフォルトのディレクトリパス */
    private static final String DEFAULT_DIRECTORY_PATH = "c:/"; //$NON-NLS-1$

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /** メッセージソース */
    @Autowired
    private KmgMessageSource messageSource;

    /** 挿入SQL作成サービス */
    @Autowired
    private IsCreationService isCreationService;

    /** ファイル選択ダイアログのラッパー */
    private FileChooserWrapper fileChooserWrapper;

    /** ディレクトリ選択ダイアログのラッパー */
    private DirectoryChooserWrapper directoryChooserWrapper;

    /** 入力ファイルテキストボックス */
    @FXML
    private TextField txtInputFile;

    /** 入力ファイル読み込みボタン */
    @FXML
    private Button btnInputFileOpen;

    /** 出力ディレクトリテキストボックス */
    @FXML
    private TextField txtOutputDirectory;

    /** 出力ディレクトリ読み込みボタン */
    @FXML
    private Button btnOutputDirectoryOpen;

    /** スレッド数テキストボックス */
    @FXML
    private TextField txtThreadNum;

    /** 実行ボタン */
    @FXML
    private Button btnRun;

    /** 処理時間ラベル */
    @FXML
    private Label lblProcTime;

    /** 処理時間単位ラベル */
    @FXML
    private Label lblProcTimeUnit;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     */
    public IsCreationController() {

        this(LoggerFactory.getLogger(IsCreationController.class));

    }

    /**
     * カスタムロガーを使用して初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *               ロガー
     */
    protected IsCreationController(final Logger logger) {

        this.logger = logger;
        this.fileChooserWrapper = new FileChooserWrapper();
        this.directoryChooserWrapper = new DirectoryChooserWrapper();

    }

    /**
     * 初期化<br>
     *
     * @author KenichiroArai
     *
     * @since 1.0.0
     *
     * @version 1.0.0
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
     * ディレクトリ選択ダイアログのラッパーを設定する<br>
     *
     * @since 1.0.0
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
     * @since 1.0.0
     *
     * @param fileChooserWrapper
     *                           ファイル選択ダイアログのラッパー
     */
    public void setFileChooserWrapper(final FileChooserWrapper fileChooserWrapper) {

        this.fileChooserWrapper = fileChooserWrapper;

    }

    /**
     * メイン処理
     *
     * @author KenichiroArai
     *
     * @since 1.0.0
     *
     * @version 1.0.0
     *
     * @param inputPath
     *                   入力パス
     * @param outputPath
     *                   出力パス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    protected void mainProc(final Path inputPath, final Path outputPath) throws KmgToolMsgException {

        /* 挿入SQL作成サービス */
        final short threadNum = Short.parseShort(this.txtThreadNum.getText());
        this.isCreationService.initialize(inputPath, outputPath, threadNum);
        this.isCreationService.outputInsertionSql();

    }

    /**
     * 入力ファイル読み込みボタンクリックイベント
     *
     * @author KenichiroArai
     *
     * @since 1.0.0
     *
     * @version 1.0.0
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
     * @author KenichiroArai
     *
     * @since 1.0.0
     *
     * @version 1.0.0
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
     * @author KenichiroArai
     *
     * @since 1.0.0
     *
     * @version 1.0.0
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
