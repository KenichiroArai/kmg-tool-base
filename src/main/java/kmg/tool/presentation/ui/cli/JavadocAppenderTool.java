package kmg.tool.presentation.ui.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.core.domain.service.KmgPfaMeasService;
import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
import kmg.tool.application.service.javadocappender.JavadocAppenderService;
import kmg.tool.domain.service.InputService;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加ツール
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class JavadocAppenderTool extends AbstractInputTool {

    // TODO KenichiroArai 2025/04/02 パスの自動設定

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** テンプレートファイルパス */
    private static final Path TEMPLATE_PATH
        = Paths.get(JavadocAppenderTool.BASE_PATH.toString(), "template/JavadocAppenderTool.yml");

    /**
     * <h3>ツール名</h3>
     * <p>
     * このツールの表示名を定義します。
     * </p>
     */
    private static final String TOOL_NAME = "Javadoc追加ツール";

    /** 入力サービス */
    @Autowired
    private InputService inputService;

    /**
     * Javadoc追加サービス
     */
    @Autowired
    private JavadocAppenderService javadocAppenderService;

    /** 対象パス */
    private Path targetPath;

    /**
     * メインメソッド
     *
     * @param args
     *             引数
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        final ConfigurableApplicationContext ctx = SpringApplication.run(JavadocAppenderTool.class, args);

        final JavadocAppenderTool tool = ctx.getBean(JavadocAppenderTool.class);

        /* 実行 */
        tool.execute();

        ctx.close();

    }

    /**
     * <h3>コンストラクタ</h3>
     * <p>
     * Javadoc追加ツールのインスタンスを生成します。
     * </p>
     * <p>
     * 親クラスのコンストラクタを呼び出し、ツール名を設定します。 このコンストラクタによって、デフォルトのテンプレートパスも設定されます。
     * </p>
     */
    public JavadocAppenderTool() {

        super(JavadocAppenderTool.TOOL_NAME);

    }

    /**
     * 実行する
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean execute() {

        boolean result = true;

        final KmgPfaMeasService kmgPfaMeasService = new KmgPfaMeasServiceImpl(JavadocAppenderTool.TOOL_NAME);
        kmgPfaMeasService.start();

        try {

            /* 入力ファイルから対象パスを設定 */
            result &= this.setTargetPathFromInputFile();

            /* Javadoc追加処理 */
            result &= this.appendJavadoc();

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
     */
    private boolean appendJavadoc() {

        boolean result = true;

        try {

            result &= this.javadocAppenderService.initialize(this.targetPath, JavadocAppenderTool.TEMPLATE_PATH);

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/07 例外処理
            e.printStackTrace();
            result = false;
            return result;

        }

        try {

            result &= this.javadocAppenderService.process();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/07 例外処理
            e.printStackTrace();
            result = false;

        }

        return result;

    }

    /**
     * 入力ファイルから対象パスを設定する
     *
     * @return true：成功、false：失敗
     */
    private boolean setTargetPathFromInputFile() {

        boolean result = true;

        try {

            result &= this.inputService.initialize(AbstractInputTool.getInputPath());

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/07 例外処理
            result = false;
            return result;

        }

        try {

            this.inputService.process();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/07 例外処理
            result = false;
            return result;

        }

        String content;

        try {

            content = this.inputService.getContent();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/07 例外処理
            result = false;
            return result;

        }

        this.targetPath = Paths.get(content);
        return result;

    }
}
