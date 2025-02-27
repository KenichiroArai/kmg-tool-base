package kmg.tool.presentation.ui.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * シンプル入力1ファイルと出力1ファイル変換ツール
 *
 * @author KenichiroArai
 */
public class SimpleInput1Output1ConversionTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** 入力ファイルパス */
    private static final Path INPUT_PATH
        = Paths.get(SimpleInput1Output1ConversionTool.BASE_PATH.toString(), "input.txt");

    /** 出力ファイルパス */
    private static final Path OUTPUT_PATH
        = Paths.get(SimpleInput1Output1ConversionTool.BASE_PATH.toString(), "output.txt");

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

        /* 入力から出力の処理 */
        try (final BufferedReader brInput = Files.newBufferedReader(SimpleInput1Output1ConversionTool.INPUT_PATH);
            final BufferedWriter bw = Files.newBufferedWriter(SimpleInput1Output1ConversionTool.OUTPUT_PATH);) {

            String line = null;

            while ((line = brInput.readLine()) != null) {

                bw.write(line);

            }

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

        final Class<SimpleInput1Output1ConversionTool> clasz = SimpleInput1Output1ConversionTool.class;

        try {

            final SimpleInput1Output1ConversionTool main = new SimpleInput1Output1ConversionTool();

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
