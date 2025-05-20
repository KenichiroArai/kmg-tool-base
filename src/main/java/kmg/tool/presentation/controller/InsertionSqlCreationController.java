package kmg.tool.presentation.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import kmg.core.infrastructure.model.KmgPfaMeasModel;
import kmg.core.infrastructure.model.impl.KmgPfaMeasModelImpl;
import kmg.core.infrastructure.type.KmgString;
import kmg.tool.domain.service.InsertionSqlCreationService;
import kmg.tool.domain.service.impl.InsertionSqlCreationServiceImpl;

/**
 * 挿入SQL作成画面画面コントローラ<br>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
public class InsertionSqlCreationController implements Initializable {

    /** ファイル選択ダイアログのタイトル */
    private static final String FILE_CHOOSER_TITLE = "ファイル選択";

    /** ディレクトリ選択ダイアログのタイトル */
    private static final String DIRECTORY_CHOOSER_TITLE = "ディレクトリ選択";

    /** デフォルトのディレクトリパス */
    private static final String DEFAULT_DIRECTORY_PATH = "c:/";

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
     * 初期化<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
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
        final Runtime runtime   = Runtime.getRuntime();
        final int     threadNum = runtime.availableProcessors();
        // テキストボックスに設定
        this.txtThreadNum.setText(String.valueOf(threadNum));

    }

    /**
     * メイン処理
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param inputPath
     *                   入力パス
     * @param outputPath
     *                   出力パス
     */
    protected void mainProc(final Path inputPath, final Path outputPath) {

        /* 挿入SQL作成サービス */
        final InsertionSqlCreationService insertSqlCreationService = new InsertionSqlCreationServiceImpl();
        final short                       threadNum                = Short.parseShort(this.txtThreadNum.getText());
        insertSqlCreationService.initialize(inputPath, outputPath, threadNum);
        insertSqlCreationService.outputInsertionSql();

    }

    /**
     * 入力ファイル読み込みボタンクリックイベント
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param event
     *              アクションイベント
     */
    @FXML
    private void onCalcInputFileOpenClicked(final ActionEvent event) {

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(InsertionSqlCreationController.FILE_CHOOSER_TITLE);
        String defaultFilePath = this.txtInputFile.getText();

        if (KmgString.isEmpty(defaultFilePath)) {

            defaultFilePath = InsertionSqlCreationController.DEFAULT_DIRECTORY_PATH;

        }
        File defaultFile = new File(defaultFilePath);

        if (defaultFile.isFile()) {

            defaultFile = defaultFile.getParentFile();

        }
        fileChooser.setInitialDirectory(defaultFile);
        final File file = fileChooser.showOpenDialog(null);
        this.txtInputFile.setText(file.getAbsolutePath());

    }

    /**
     * 出力ディレクトリ読み込みボタンクリックイベント
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param event
     *              アクションイベント
     */
    @FXML
    private void onCalcOutputDirectoryOpenClicked(final ActionEvent event) {

        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(InsertionSqlCreationController.DIRECTORY_CHOOSER_TITLE);
        String defaultFilePath = this.txtOutputDirectory.getText();

        if (KmgString.isEmpty(defaultFilePath)) {

            defaultFilePath = InsertionSqlCreationController.DEFAULT_DIRECTORY_PATH;

        }
        File defaultFile = new File(defaultFilePath);

        if (defaultFile.isFile()) {

            defaultFile = defaultFile.getParentFile();

        }
        directoryChooser.setInitialDirectory(defaultFile);
        final File file = directoryChooser.showDialog(null);
        this.txtOutputDirectory.setText(file.getAbsolutePath());

    }

    /**
     * 実行ボタンクリックイベント
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
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

        try {

            // メイン処理
            final Path inputPath  = Paths.get(this.txtInputFile.getText());
            final Path outputPath = Paths.get(this.txtOutputDirectory.getText());
            this.mainProc(inputPath, outputPath);

        } finally {

            pfaMeas.end();
            this.lblProcTime.setText(String.valueOf(pfaMeas.getElapsedTime()));
            this.lblProcTimeUnit.setText(pfaMeas.getTimeUnit().getUnitName());

        }

    }
}
