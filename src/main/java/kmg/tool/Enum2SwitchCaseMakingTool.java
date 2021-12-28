/**
 * ＫＭＧ．ツール
 */
package kmg.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import kmg.core.infrastructure.types.KmgDelimiterTypes;

/**
 * 列挙型からcase作成ツール
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls") // TODO KenichiroArai 2021/07/14 外部文字列化
public class Enum2SwitchCaseMakingTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** テンプレートファイルパス */
    private static final Path TEMPLATE_PATH = Paths.get(Enum2SwitchCaseMakingTool.BASE_PATH.toString(),
        "template/enum2SwitchCaseMakingTool.txt"); // TODO KenichiroArai 2021/07/14 自動設定

    /** 入力ファイルパス */
    private static final Path INPUT_PATH = Paths.get(Enum2SwitchCaseMakingTool.BASE_PATH.toString(), "input.txt");

    /** 出力ファイルパス */
    private static final Path OUTPUT_PATH = Paths.get(Enum2SwitchCaseMakingTool.BASE_PATH.toString(), "output.txt");

    /** パラメータ：項目 */
    private static final String PARAM_ITEM = "%item%";

    /** パラメータ：項目名 */
    private static final String PARAM_ITEM_NAME = "%itemName%";

    /**
     * 実行する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return TRUE：成功、FLASE：失敗
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

            template = Files.readAllLines(Enum2SwitchCaseMakingTool.TEMPLATE_PATH).stream()
                .collect(Collectors.joining(KmgDelimiterTypes.LINE_SEPARATOR.get()));

        } catch (final FileNotFoundException e) {
            throw e;
        } catch (final IOException e) {
            throw e;
        }

        /* 入力から出力の処理 */
        try (final BufferedReader brInput = Files.newBufferedReader(Enum2SwitchCaseMakingTool.INPUT_PATH);
            final BufferedWriter bw = Files.newBufferedWriter(Enum2SwitchCaseMakingTool.OUTPUT_PATH);) {
            String line;
            while ((line = brInput.readLine()) != null) {
                String output = template;

                final Pattern patternComment = Pattern.compile("(\\w+)\\(\"(\\S+)\",");
                final Matcher matcherComment = patternComment.matcher(line);
                if (matcherComment.find()) {
                    output = output.replace(Enum2SwitchCaseMakingTool.PARAM_ITEM, matcherComment.group(1));
                    output = output.replace(Enum2SwitchCaseMakingTool.PARAM_ITEM_NAME, matcherComment.group(2));
                    bw.write(output);
                    bw.newLine();
                    continue;
                }
            }
        } catch (final FileNotFoundException e) {
            throw e;
        } catch (final IOException e) {
            throw e;
        }

        return result;
    }

    /**
     * エントリポイント<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param args
     *             オプション
     */
    public static void main(final String[] args) {

        final Class<Enum2SwitchCaseMakingTool> clasz = Enum2SwitchCaseMakingTool.class;
        try {
            final Enum2SwitchCaseMakingTool main = new Enum2SwitchCaseMakingTool();
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
