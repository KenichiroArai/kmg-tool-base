package kmg.tool.presentation.ui.cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import kmg.core.domain.service.KmgPfaMeasService;
import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
import kmg.core.infrastructure.types.KmgDelimiterTypes;

/**
 * Javadoc行削除ツール
 */
public class JavadocLineRemoverTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** 入力ファイルパス */
    private static final Path INPUT_PATH = Paths.get(JavadocLineRemoverTool.BASE_PATH.toString(), "input.txt");

    /** Javaパス */
    private static final Path JAVA_PATH = Paths.get("D:\\eclipse_git_wk\\DictOpeProj\\kmg-core");

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
    public static Boolean run() throws FileNotFoundException, IOException {

        final boolean result = true;

        /* 入力からパスと行番号のマップ（ファイルパスと行番号のリスト、リストは行番号の降順）を取得 */
        final Map<Path, List<Integer>> inputMap = JavadocLineRemoverTool.getInputMap();
        System.out.println(inputMap.toString());

        /* 対象のJavaファイルを取得 */
        final List<Path> javaFileList;

        int fileCount = 0;

        try (final Stream<Path> streamPath = Files.walk(JavadocLineRemoverTool.JAVA_PATH)) {

            javaFileList = streamPath.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java"))
                .collect(Collectors.toList());

            fileCount += javaFileList.size();

        }

        /* 対象のJavaファイルを書き換える */

        final int lineCount = 0;

        /* 情報の出力 */

        System.out.println(String.format("fileCount: %d", fileCount));
        System.out.println(String.format("lineCount: %d", lineCount));

        return result;

    }

    /**
     * 入力ファイルからパスと行番号のマップを取得する
     *
     * @return パスと行番号のマップ
     *
     * @throws IOException
     *                     入出力例外
     */
    private static Map<Path, List<Integer>> getInputMap() throws IOException {

        try (final Stream<String> stream = Files.lines(JavadocLineRemoverTool.INPUT_PATH)) {

            final Map<Path, List<Integer>> result = stream.filter(line -> line.contains(".java:")).map(line -> {

                // ファイルパスと行番号を抽出
                final String[] parts = KmgDelimiterTypes.COLON.split(line);

                if (parts.length < 3) {

                    return null;

                }

                try {

                    final Path path       = Paths.get(parts[1].trim());
                    final int  lineNumber = Integer.parseInt(parts[2].trim());
                    return new SimpleEntry<>(path, lineNumber);

                } catch (@SuppressWarnings("unused") final Exception e) {

                    // 処理なし
                }

                return null;

            }).filter(entry -> entry != null).collect(
                Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

            return result;

        }

    }

    /**
     * メインメソッド
     *
     * @param args
     *             引数
     */
    public static void main(final String[] args) {

        final Class<JavadocLineRemoverTool> clasz       = JavadocLineRemoverTool.class;
        final KmgPfaMeasService             measService = new KmgPfaMeasServiceImpl(clasz.toString());
        measService.start();

        final JavadocLineRemoverTool main = new JavadocLineRemoverTool();

        try {

            if (!JavadocLineRemoverTool.run()) {

                System.out.println(String.format("%s：失敗", clasz.toString()));

            }
            System.out.println(String.format("%s：成功", clasz.toString()));

        } catch (final Exception e) {

            System.out.println(String.format("%s：例外発生", clasz.toString()));

            e.printStackTrace();

        } finally {

            measService.end();

        }

    }

}
