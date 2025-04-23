package kmg.tool.presentation.ui.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.core.domain.service.KmgPfaMeasService;
import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
import kmg.core.infrastructure.utils.KmgPathUtils;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.application.service.jdts.JdtsService;
import kmg.tool.domain.service.InputService;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

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

    /** メッセージソース */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /** 入力サービス */
    @Autowired
    private InputService inputService;

    /**
     * Javadoc追加サービス
     */
    @Autowired
    private JdtsService jdtsService;

    /** 対象パス */
    private Path targetPath;

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
        this.logger = logger;
        this.definitionPath = this.getDefaultDefinitionPath();

    }

    /**
     * 実行する
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean execute() {

        boolean result = true;

        final KmgPfaMeasService kmgPfaMeasService = new KmgPfaMeasServiceImpl(JavadocTagSetterTool.TOOL_NAME);
        kmgPfaMeasService.start();

        try {

            /* 入力ファイルから対象パスを設定 */
            result &= this.setTargetPathFromInputFile();

            /* Javadoc追加処理 */
            result &= this.appendJavadoc();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/04/23 例外処理

            // ログの出力
            final KmgToolLogMessageTypes logType     = KmgToolLogMessageTypes.NONE;
            final Object[]               messageArgs = {};
            final String                 msg         = this.messageSource.getLogMessage(logType, messageArgs);
            this.logger.error(msg, e);

            result = false;

        } finally {

            kmgPfaMeasService.end();

        }

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
     * Javadocを追加する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private boolean appendJavadoc() throws KmgToolException {

        boolean result = true;

        result &= this.jdtsService.initialize(this.targetPath, this.definitionPath);
        result &= this.jdtsService.process();

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

    /**
     * 入力ファイルから対象パスを設定する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private boolean setTargetPathFromInputFile() throws KmgToolException {

        boolean result = true;

        result &= this.inputService.initialize(AbstractInputTool.getInputPath());

        result &= this.inputService.process();

        final String content = this.inputService.getContent();
        this.targetPath = Paths.get(content);

        return result;

    }
}
