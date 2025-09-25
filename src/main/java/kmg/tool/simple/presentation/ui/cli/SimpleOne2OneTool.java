package kmg.tool.simple.presentation.ui.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.tool.one2one.application.service.SimpleOne2OneService;
import kmg.tool.one2one.presentation.ui.cli.AbstractOne2OneTool;

/**
 * シンプル1入力ファイルから1出力ファイルへの変換ツール
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class SimpleOne2OneTool extends AbstractOne2OneTool {

    /**
     * ツール名
     *
     * @since 0.1.0
     */
    private static final String TOOL_NAME = "シンプル1入力ファイルから1出力ファイルへの変換ツール"; //$NON-NLS-1$

    /**
     * シンプル1入力ファイルから1出力ファイルへの変換サービス
     *
     * @since 0.1.0
     */
    @Autowired
    private SimpleOne2OneService simpleOne2OneService;

    /**
     * エントリポイント
     *
     * @since 0.1.0
     *
     * @param args
     *             オプション
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        final ConfigurableApplicationContext ctx = SpringApplication.run(SimpleOne2OneTool.class, args);

        final SimpleOne2OneTool tool = ctx.getBean(SimpleOne2OneTool.class);

        /* 初期化 */
        tool.initialize();

        /* 実行 */
        tool.execute();

        ctx.close();

    }

    /**
     * コンストラクタ
     *
     * @since 0.1.0
     */
    public SimpleOne2OneTool() {

        super(SimpleOne2OneTool.TOOL_NAME);

    }

    /**
     * シンプル1入力ファイルから1出力ファイルへの変換サービスを返す。
     *
     * @since 0.1.0
     *
     * @return シンプル1入力ファイルから1出力ファイルへの変換サービス
     */
    @Override
    protected SimpleOne2OneService getIoService() {

        final SimpleOne2OneService result = this.simpleOne2OneService;

        return result;

    }

}
