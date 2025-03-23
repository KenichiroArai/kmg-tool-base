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
import kmg.core.infrastructure.types.KmgDbDataTypeTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;

/**
 * フィールド作成ツール
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
@SuppressWarnings("nls") // TODO KenichiroArai 2021/05/11 外部文字列化
public class FieldCreationTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** テンプレートファイルパス */
    private static final Path TEMPLATE_PATH
        = Paths.get(FieldCreationTool.BASE_PATH.toString(), "template/kmgTlFieldCreationTool.txt"); // TODO
                                                                                                    // KenichiroArai
                                                                                                    // 2021/05/28 自動設定

    /** 入力ファイルパス */
    private static final Path INPUT_PATH = Paths.get(FieldCreationTool.BASE_PATH.toString(), "input.txt");

    /** 出力ファイルパス */
    private static final Path OUTPUT_PATH = Paths.get(FieldCreationTool.BASE_PATH.toString(), "output.txt");

    /** パラメータ：コメント */
    private static final String PARAM_COMMENT = "$comment";

    /** パラメータ：フィールド */
    private static final String PARAM_FIELD = "$field";

    /** パラメータ：型 */
    private static final String PARAM_TYPE = "$type";

    /**
     * エントリポイント<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param args
     *             オプション
     */
    public static void main(final String[] args) {

        final Class<FieldCreationTool> clasz = FieldCreationTool.class;

        try {

            final FieldCreationTool main = new FieldCreationTool();

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

    /**
     * 実行する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
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

            template = Files.readAllLines(FieldCreationTool.TEMPLATE_PATH).stream()
                .collect(Collectors.joining(KmgDelimiterTypes.LINE_SEPARATOR.get()));

        } catch (final IOException e) {

            throw e;

        }

        /* 入力から出力の処理 */
        try (final BufferedReader brInput = Files.newBufferedReader(FieldCreationTool.INPUT_PATH);
            final BufferedWriter bw = Files.newBufferedWriter(FieldCreationTool.OUTPUT_PATH);) {

            String line;

            while ((line = brInput.readLine()) != null) {

                /* データ取得 */
                final String[] inputDatas  = KmgDelimiterTypes.SERIES_HALF_SPACE.split(line);
                int            dataIdx     = 0;
                final String   commentData = inputDatas[dataIdx];                            // コメント
                dataIdx++;
                final String fieldData = inputDatas[dataIdx]; // フィールド名
                dataIdx++;
                final String typeData = inputDatas[dataIdx++]; // 型

                /* 変換処理 */

                final String             changeFieldData = new KmgString(fieldData).toCamelCase();
                String                   changeTypeData  = null;
                final KmgDbDataTypeTypes type            = KmgDbDataTypeTypes.getEnum(typeData);

                if (type == null) {

                    changeTypeData = typeData;

                } else {

                    changeTypeData = type.getType().getTypeName().replaceAll("(\\w+\\.)+", KmgString.EMPTY);

                }

                String output = template;
                output = output.replace(FieldCreationTool.PARAM_COMMENT, commentData);
                output = output.replace(FieldCreationTool.PARAM_FIELD, changeFieldData);
                output = output.replace(FieldCreationTool.PARAM_TYPE, changeTypeData);

                /* 出力 */
                bw.write(output);
                bw.write(System.lineSeparator());

            }

        } catch (final IOException e) {

            throw e;

        }

        return result;

    }

}
