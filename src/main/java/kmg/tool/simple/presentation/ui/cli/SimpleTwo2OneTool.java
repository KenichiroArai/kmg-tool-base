package kmg.tool.simple.presentation.ui.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.tool.simple.application.service.SimpleTwo2OneService;
import kmg.tool.two2one.presentation.ui.cli.AbstractTwo2OneTool;

/**
 * シンプル2入力ファイルから1出力ファイルへの変換ツール
 *
 * @author KenichiroArai
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class SimpleTwo2OneTool extends AbstractTwo2OneTool {

    /** ツール名 */
    private static final String TOOL_NAME = "シンプル2入力ファイルから1出力ファイルへの変換ツール"; //$NON-NLS-1$

    /** シンプル2入力ファイルから1出力ファイルへの変換サービス */
    @Autowired
    private SimpleTwo2OneService simpleTwo2OneService;

    /**
     * エントリポイント
     *
     * @param args
     *             オプション
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        final ConfigurableApplicationContext ctx = SpringApplication.run(SimpleTwo2OneTool.class, args);

        final SimpleTwo2OneTool tool = ctx.getBean(SimpleTwo2OneTool.class);

        /* 初期化 */
        tool.initialize();

        /* 実行 */
        tool.execute();

        ctx.close();

    }

    /**
     * コンストラクタ
     */
    public SimpleTwo2OneTool() {

        super(SimpleTwo2OneTool.TOOL_NAME);

    }

    /**
     * シンプル2入力ファイルから1出力ファイルへの変換サービスを返す。
     *
     * @return シンプル2入力ファイルから1出力ファイルへの変換サービス
     */
    @Override
    protected SimpleTwo2OneService getIoService() {

        final SimpleTwo2OneService result = this.simpleTwo2OneService;

        return result;

    }

}
