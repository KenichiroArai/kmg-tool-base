package kmg.tool.presentation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import kmg.core.infrastructure.types.KmgDelimiterTypes;

/**
 * Javadoc追加ツール
 */
public class JavadocAppenderTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** テンプレートファイルパス */
    // TODO KenichiroArai 2025/02/23 自動設定
    private static final Path TEMPLATE_PATH
        = Paths.get(JavadocAppenderTool.BASE_PATH.toString(), "template/JavadocAppenderTool.txt");

    /** 入力パス */
    private static final Path INPUT_PATH = Paths.get("D:\\eclipse_git_wk\\DictOpeProj\\kmg-core");

    /**
     * 実行する<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @version 0.1.0
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

        /* タグマップの取得 */
        final Map<String, String> tagMap = this.getTagMap();
        System.out.println(tagMap.toString());

        /* 対象のJavaファイルを取得 */
        final List<Path> javaFiles = Files.walk(JavadocAppenderTool.INPUT_PATH).filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".java")).collect(Collectors.toList());

        return result;
    }

    /**
     * タグマップを取得する<br>
     * 
     * @return タグマップ
     * @throws IOException 入出力例外
     */
    private Map<String, String> getTagMap() throws IOException {
        final Map<String, String> result = new HashMap<>();

        /* テンプレートの読み込み */
        final List<String> lines = Files.readAllLines(JavadocAppenderTool.TEMPLATE_PATH);

        /* タグマップの作成 */
        for (final String line : lines) {
            final String trimmedLine = line.trim();

            if (!trimmedLine.startsWith(KmgDelimiterTypes.HALF_AT_SIGN.get())) {
                continue;
            }

            final String[] parts = KmgDelimiterTypes.SERIES_HALF_SPACE.split(trimmedLine, 2);
            final String tag = parts[0].trim();
            final String value = parts[1].trim();
            result.put(tag, value);
        }

        return result;
    }

    /**
     * メインメソッド
     *
     * @param args
     *             引数
     */
    public static void main(final String[] args) {

        final Class<JavadocAppenderTool> clasz = JavadocAppenderTool.class;

        try {

            final JavadocAppenderTool main = new JavadocAppenderTool();

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
