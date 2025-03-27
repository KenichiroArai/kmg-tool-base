package kmg.tool.presentation.ui.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.tool.application.service.JavadocAppenderService;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加ツール
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class JavadocAppenderTool extends AbstractTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** テンプレートファイルパス */
    private static final Path TEMPLATE_PATH
        = Paths.get(JavadocAppenderTool.BASE_PATH.toString(), "template/JavadocAppenderTool.txt");

    /** 対象パス */
    private static final Path TARGET_PATH = Paths.get("D:\\eclipse_git_wk\\DictOpeProj\\kmg-core");

    /**
     * Javadoc追加サービス
     */
    @Autowired
    private JavadocAppenderService javadocAppenderService;

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
     * 実行する
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean execute() {

        boolean result = true;

        try {

            result |= this.javadocAppenderService.initialize(JavadocAppenderTool.TARGET_PATH,
                JavadocAppenderTool.TEMPLATE_PATH);

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/07 例外処理
            e.printStackTrace();
            result = false;
            return result;

        }

        try {

            result |= this.javadocAppenderService.process();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/07 例外処理
            e.printStackTrace();
            result = false;

        }

        return result;

    }

}
