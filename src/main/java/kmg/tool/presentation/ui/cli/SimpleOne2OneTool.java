package kmg.tool.presentation.ui.cli;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.tool.application.service.SimpleOne2OneService;
import kmg.tool.domain.service.One2OneService;

/**
 * シンプル1入力ファイルから1出力ファイルへの変換ツール
 *
 * @author KenichiroArai
 */
@SpringBootApplication(scanBasePackages = {
    "kmg.tool"
})
public class SimpleOne2OneTool extends AbstractIoTool {

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

        try {

            /* 初期化 */
            tool.initialize();

            /* 処理 */
            tool.process();

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/01 例外処理
            e.printStackTrace();

        }

        ctx.close();

    }

    /**
     * 1入力ファイルから1出力ファイルへの変換サービスを返す。
     *
     * @return 1入力ファイルから1出力ファイルへの変換サービス
     */
    @Override
    protected One2OneService getOne2OneService() {

        final One2OneService result = this.simpleOne2OneService;

        return result;

    }

}
