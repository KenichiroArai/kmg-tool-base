package kmg.tool.presentation.ui.cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    "kmg.tool"
})
public class SimpleOne2OneTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** 入力ファイルパス */
    private static final Path INPUT_PATH = Paths.get(SimpleOne2OneTool.BASE_PATH.toString(), "input.txt");

    /** 出力ファイルパス */
    private static final Path OUTPUT_PATH = Paths.get(SimpleOne2OneTool.BASE_PATH.toString(), "output.txt");

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

        final Class<SimpleOne2OneTool> clasz = SimpleOne2OneTool.class;

        try (ConfigurableApplicationContext ctx = SpringApplication.run(SimpleOne2OneTool.class, args);) {

            // SpringコンテキストからBeanを取得する
            final SimpleOne2OneTool main      = ctx.getBean(SimpleOne2OneTool.class);
            final Boolean           runResult = main.run();

            if (!runResult) {

                System.out.println(String.format("%s：失敗", clasz.toString()));
                return;

            }

            System.out.println(String.format("%s：成功", clasz.toString()));

        } catch (final Exception e) {

            e.printStackTrace();

        } finally {

            System.out.println(String.format("%s：終了", clasz.toString()));

        }

    }

    /**
     * 走る
     *
     * @return TRUE：成功、FLASE：失敗
     *
     * @throws FileNotFoundException
     *                               ファイルが存在しない例外
     * @throws IOException
     *                               入出力例外
     */
    public Boolean run() throws FileNotFoundException, IOException {

        final Boolean result = Boolean.TRUE;

        this.simpleOne2OneService.initialize(SimpleOne2OneTool.INPUT_PATH, SimpleOne2OneTool.OUTPUT_PATH);
        this.simpleOne2OneService.process();

        return result;

    }
}
