/**
 * ＫＭＧ．ツール
 */
package kmg.tool.presentation.ui.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDelimiterTypes;

/**
 * ＫＭＧツール名称・メッセージ作成ツール
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls") // TODO KenichiroArai 2021/05/12 外部文字列化
public class KmgTlNameMsgCreationlTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** テンプレートファイルパス */
    private static final Path TEMPLATE_PATH = Paths.get(KmgTlNameMsgCreationlTool.BASE_PATH.toString(),
            "template/kmgTlNameMsgCreationlTool.txt"); // TODO KenichiroArai 2021/05/28 自動設定

    /** 入力ファイルパス */
    private static final Path INPUT_PATH = Paths.get(KmgTlNameMsgCreationlTool.BASE_PATH.toString(), "input.txt");

    /** 出力ファイルパス */
    private static final Path OUTPUT_PATH = Paths.get(KmgTlNameMsgCreationlTool.BASE_PATH.toString(), "output.txt");

    /** パラメータ：コメント */
    private static final String PARAM_COMMENT = "$comment";

    /** パラメータ：キー */
    private static final String PARAM_KEY = "$key";

    /** パラメータ：名称 */
    private static final String PARAM_NAME = "$name";

    /** パラメータ：値 */
    private static final String PARAM_VALUE = "$value";

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

            template = Files.readAllLines(KmgTlNameMsgCreationlTool.TEMPLATE_PATH).stream()
                    .collect(Collectors.joining(KmgDelimiterTypes.LINE_SEPARATOR.get()));

        } catch (final IOException e) {

            throw e;

        }

        /* 入力から出力の処理 */
        try (final BufferedReader brInput = Files.newBufferedReader(KmgTlNameMsgCreationlTool.INPUT_PATH);
                final BufferedWriter bw = Files.newBufferedWriter(KmgTlNameMsgCreationlTool.OUTPUT_PATH);) {

            String line;

            while ((line = brInput.readLine()) != null) {

                /* データ取得 */
                final String[]  inputDatas = KmgDelimiterTypes.HALF_EQUAL.split(line, 2);
                int             dataIdx    = 0;
                final KmgString idData     = new KmgString(inputDatas[dataIdx]); // ID
                dataIdx++;
                final KmgString nameData = new KmgString(inputDatas[dataIdx]);
                dataIdx++; // 名称

                /* 変換処理 */
                String output = template;
                output = output.replace(KmgTlNameMsgCreationlTool.PARAM_COMMENT, nameData.toString()); // コメント
                output = output.replace(KmgTlNameMsgCreationlTool.PARAM_KEY, idData.toString()); // キー
                output = output.replace(KmgTlNameMsgCreationlTool.PARAM_NAME, nameData.toString()); // 名称
                output = output.replace(KmgTlNameMsgCreationlTool.PARAM_VALUE, idData.toString()); // 値

                /* 出力 */
                bw.write(output);
                bw.write(System.lineSeparator());

            }

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

        final Class<KmgTlNameMsgCreationlTool> clasz = KmgTlNameMsgCreationlTool.class;

        try {

            final KmgTlNameMsgCreationlTool main = new KmgTlNameMsgCreationlTool();

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
