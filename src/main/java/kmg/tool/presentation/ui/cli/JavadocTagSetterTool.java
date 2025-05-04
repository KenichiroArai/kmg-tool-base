package kmg.tool.presentation.ui.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.core.infrastructure.utils.KmgPathUtils;
import kmg.tool.application.service.jdts.JdtsService;
import kmg.tool.domain.service.InputService;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * Javadocタグ設定ツール<br>
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class JavadocTagSetterTool extends AbstractInputTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** 定義ファイルのパスのフォーマット */
    private static final String DEFINITION_FILE_PATH_FORMAT = "template/%s.yml";

    /**
     * <h3>ツール名</h3>
     * <p>
     * このツールの表示名を定義します。
     * </p>
     */
    private static final String TOOL_NAME = "Javadocタグ設定ツール";

    /** 入力サービス */
    @Autowired
    private InputService inputService;

    /**
     * Javadoc追加サービス
     */
    @Autowired
    private JdtsService jdtsService;

    /** 定義ファイルのパス */
    private final Path definitionPath;

    /**
     * メインメソッド
     *
     * @param args
     *             引数
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        final ConfigurableApplicationContext ctx = SpringApplication.run(JavadocTagSetterTool.class, args);

        final JavadocTagSetterTool tool = ctx.getBean(JavadocTagSetterTool.class);

        /* 実行 */
        tool.execute();

        ctx.close();

    }

    /**
     * デフォルトコンストラクタ
     */
    public JavadocTagSetterTool() {

        this(LoggerFactory.getLogger(JavadocTagSetterTool.class), JavadocTagSetterTool.TOOL_NAME);

    }

    /**
     * カスタムロガーを使用して初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *                 ロガー
     * @param toolName
     *                 ツール名
     */
    protected JavadocTagSetterTool(final Logger logger, final String toolName) {

        super(toolName);
        this.definitionPath = this.getDefaultDefinitionPath();

    }

    /**
     * 定義ファイルのパスを返す。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return 定義ファイルのパス
     */
    public Path getDefinitionPath() {

        final Path result = this.definitionPath;
        return result;

    }

    /**
     * ツールのメイン処理を実行する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    protected boolean executeMain() throws KmgToolMsgException {

        boolean result = true;

        /* Javadoc追加処理 */
        result &= this.jdtsService.initialize(this.getTargetPath(), this.definitionPath);
        result &= this.jdtsService.process();

        return result;

    }

    /**
     * 入力サービスを返す。
     *
     * @return 入力サービス
     */
    @Override
    protected InputService getInputService() {

        final InputService result = this.inputService;
        return result;

    }

    /**
     * デフォルト定義ファイルのパスを返す。
     *
     * @return デフォルト定義パス
     */
    private Path getDefaultDefinitionPath() {

        Path         result;
        final String className        = KmgPathUtils.getSimpleClassName(this.getClass());
        final String templateFileName = String.format(JavadocTagSetterTool.DEFINITION_FILE_PATH_FORMAT, className);

        result = Paths.get(JavadocTagSetterTool.BASE_PATH.toString(), templateFileName);

        return result;

    }

}
