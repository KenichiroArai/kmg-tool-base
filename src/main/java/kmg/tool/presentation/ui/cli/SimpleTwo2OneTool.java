package kmg.tool.presentation.ui.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import kmg.core.infrastructure.types.KmgDelimiterTypes;

/**
 * シンプル入力2ファイルから出力1ファイルへの変換ツール
 *
 * @author KenichiroArai
 */
public class SimpleTwo2OneTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** テンプレートファイルパス */
    private static final Path TEMPLATE_PATH
        = Paths.get(SimpleTwo2OneTool.BASE_PATH.toString(), "template/SimpleTemplate.txt");

    /** 入力ファイルパス */
    private static final Path INPUT_PATH = Paths.get(SimpleTwo2OneTool.BASE_PATH.toString(), "input.txt");

    /** 出力ファイルパス */
    private static final Path OUTPUT_PATH = Paths.get(SimpleTwo2OneTool.BASE_PATH.toString(), "output.txt");

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
    @SuppressWarnings("static-method")
    public Boolean run() throws FileNotFoundException, IOException {

        final Boolean result = Boolean.FALSE;

        /* テンプレートの取得 */
        String template = null;

        try {

            template = Files.readAllLines(SimpleTwo2OneTool.TEMPLATE_PATH).stream()
                .collect(Collectors.joining(KmgDelimiterTypes.LINE_SEPARATOR.get()));

        } catch (final IOException e) {

            throw e;

        }

        /* 入力から出力の処理 */
        try (final BufferedReader brInput = Files.newBufferedReader(SimpleTwo2OneTool.INPUT_PATH);
            final BufferedWriter bw = Files.newBufferedWriter(SimpleTwo2OneTool.OUTPUT_PATH);) {

            final StringBuilder output = new StringBuilder();

            String line = null;

            while ((line = brInput.readLine()) != null) {

                final String wk = template.replace("{ name }", line);
                output.append(wk);

            }

            bw.write(output.toString());

        } catch (final IOException e) {

            throw e;

        }

        return result;

    }

    /**
     * エントリポイント
     *
     * @param args
     *             オプション
     */
    public static void main(final String[] args) {

        final Class<SimpleTwo2OneTool> clasz = SimpleTwo2OneTool.class;

        try {

            final SimpleTwo2OneTool main = new SimpleTwo2OneTool();

            if (main.run()) {

                System.out.println(String.format("%s：失敗", clasz.toString()));

            }

        } catch (final Exception e) {

            e.printStackTrace();

        } finally {

            System.out.println(String.format("%s：成功", clasz.toString()));
            System.out.println(String.format("%s：終了", clasz.toString()));

        }

    }

}
