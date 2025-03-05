package kmg.tool.presentation.ui.cli.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.tool.application.service.SimpleOne2OneService;

/**
 * シンプル1入力ファイルから1出力ファイルへの変換ツール
 *
 * @author KenichiroArai
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class SimpleOne2OneTool extends AbstractOne2OneTool {

    /** ツール名 */
    private static final String TOOL_NAME = "シンプル1入力ファイルから1出力ファイルへの変換ツール";

    /** シンプル1入力ファイルから1出力ファイルへの変換サービス */
    @Autowired
    private SimpleOne2OneService simpleOne2OneService;

    /**
     * エントリポイント
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
     */
    public SimpleOne2OneTool() {

        super(SimpleOne2OneTool.TOOL_NAME);

    }

    /**
     * シンプル1入力ファイルから1出力ファイルへの変換サービスを返す。
     *
     * @return シンプル1入力ファイルから1出力ファイルへの変換サービス
     */
    @Override
    protected SimpleOne2OneService getIoService() {

        final SimpleOne2OneService result = this.simpleOne2OneService;

        return result;

    }

}
