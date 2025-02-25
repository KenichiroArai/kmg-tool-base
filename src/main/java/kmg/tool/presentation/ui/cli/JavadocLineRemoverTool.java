package kmg.tool.presentation.ui.cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.LinkedHashMap;
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
    @SuppressWarnings("static-method")
    public Boolean run() throws FileNotFoundException, IOException {

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
     * @return パスと行番号の降順のリストのマップ
     *
     * @throws IOException
     *                     入出力例外
     */
    private static Map<Path, List<Integer>> getInputMap() throws IOException {

        final Map<Path, List<Integer>> result;

        try (final Stream<String> stream = Files.lines(JavadocLineRemoverTool.INPUT_PATH)) {

            // Javaファイルの行を抽出するストリームを作成する
            final Stream<String> filteredLines = stream.filter(line -> line.contains(".java:"));

            // ストリームから行をパスと行番号のエントリに変換する
            final Stream<SimpleEntry<Path, Integer>> entries
                = filteredLines.map(JavadocLineRemoverTool::convertLineToPathLineEntry).filter(entry -> entry != null);

            // エントリからパスと行番号のリストのマップに変換する
            final Map<Path, List<Integer>> pathLineMap = entries.collect(
                Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

            // パスごとに行番号のリストを降順にソートする
            final Map<Path, List<Integer>> sortedLineMap = new LinkedHashMap<>();
            pathLineMap.forEach((path, lineNumbers) -> {

                lineNumbers.sort(Comparator.reverseOrder());
                sortedLineMap.put(path, lineNumbers);

            });

            result = sortedLineMap;

        }

        return result;

    }

    /**
     * 行文字列をパスと行番号のエントリに変換する
     *
     * @param line
     *             行文字列
     *
     * @return パスと行番号のエントリ、変換できない場合はnull
     */
    private static SimpleEntry<Path, Integer> convertLineToPathLineEntry(final String line) {

        SimpleEntry<Path, Integer> result = null;

        // ファイルパスと行番号を抽出
        final String[] parts = KmgDelimiterTypes.COLON.split(line);

        if (parts.length < 3) {

            return result;

        }

        final Path path = Paths.get(parts[1].trim());

        int lineNumber = 0;

        try {

            lineNumber = Integer.parseInt(parts[2].trim());

        } catch (@SuppressWarnings("unused") final NumberFormatException e) {

            // 処理なし
        }

        result = new SimpleEntry<>(path, lineNumber);

        return result;

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

            if (!main.run()) {

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
