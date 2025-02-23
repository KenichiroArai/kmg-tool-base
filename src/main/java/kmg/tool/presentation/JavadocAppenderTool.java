package kmg.tool.presentation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import kmg.core.domain.service.KmgPfaMeasService;
import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
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
    public Boolean run() throws FileNotFoundException, IOException {

        boolean result = true;

        /* タグマップの取得 */
        final Map<String, String> tagMap = this.getTagMap();
        System.out.println(tagMap.toString());

        /* 対象のJavaファイルを取得 */
        final List<Path> javaFileList;

        int fileCount = 0;

        try (final Stream<Path> streamPath = Files.walk(JavadocAppenderTool.INPUT_PATH)) {

            javaFileList = streamPath.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java"))
                .collect(Collectors.toList());

            fileCount += javaFileList.size();

        }

        /* 対象のJavaファイルをすべて読み込む */

        int lineCount = 0;

        for (final Path javaFile : javaFileList) {

            final StringBuilder fileContentBuilder = new StringBuilder();

            try (BufferedReader br = Files.newBufferedReader(javaFile)) {

                /* 行ごとの読み込み */
                boolean isInJavadoc = false;
                String  line        = null;

                while ((line = br.readLine()) != null) {

                    lineCount++;

                    final String trimmedLine = line.trim();

                    /* Javadocの開始判定 */
                    if (trimmedLine.startsWith("/**")) {

                        isInJavadoc = true;

                        if (!trimmedLine.endsWith("*/")) {

                            // Javadocの開始行の末尾に*/がない（1行）場合

                            fileContentBuilder.append(line).append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                            continue;

                        }

                    }

                    /* Javadocの終了判定 */
                    if (isInJavadoc && trimmedLine.endsWith("*/")) {

                        isInJavadoc = false;

                        if (trimmedLine.startsWith("/**")) {

                            // Javadocの開始行と終了行が同じ場合

                            fileContentBuilder.append("/**").append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                            // lineからコメントを取得する
                            final String comment = trimmedLine.substring(3, trimmedLine.length() - 2);

                            fileContentBuilder.append(comment).append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                        }

                        /* tagMapの内容を挿入 */
                        for (final Map.Entry<String, String> entry : tagMap.entrySet()) {

                            fileContentBuilder.append(" * ").append(entry.getKey()).append(" ").append(entry.getValue())
                                .append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                        }

                        fileContentBuilder.append("*/").append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                        continue;

                    }

                    /* Javadoc内の行の処理 */
                    if (!isInJavadoc) {

                        fileContentBuilder.append(line).append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                        continue;

                    }

                    // Javadoc内の場合

                    // tagMapのキーに該当する行は追加しない
                    boolean shouldSkip = false;

                    for (final String tag : tagMap.keySet()) {

                        if (!trimmedLine.contains(tag)) {

                            continue;

                        }

                        shouldSkip = true;
                        break;

                    }

                    if (shouldSkip) {

                        continue;

                    }

                    fileContentBuilder.append(line).append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                }

            }

            /* 修正した内容をファイルに書き込む */
            try {

                Files.writeString(javaFile, fileContentBuilder.toString());

            } catch (final IOException e) {

                System.err.println("Error writing to file: " + javaFile);
                e.printStackTrace();
                result = false;

            }

        }

        System.out.println(String.format("fileCount: %d", fileCount));
        System.out.println(String.format("lineCount: %d", lineCount));

        return result;

    }

    /**
     * タグマップを取得する<br>
     *
     * @return タグマップ
     *
     * @throws IOException
     *                     入出力例外
     */
    @SuppressWarnings("static-method")
    private Map<String, String> getTagMap() throws IOException {

        final Map<String, String> result = new HashMap<>();

        /* テンプレートの読み込み */
        List<String> lines = null;

        try {

            lines = Files.readAllLines(JavadocAppenderTool.TEMPLATE_PATH);

        } catch (final IOException e) {

            throw e;

        }

        /* タグマップの作成 */
        for (final String line : lines) {

            final String trimmedLine = line.trim();

            if (!trimmedLine.startsWith(KmgDelimiterTypes.HALF_AT_SIGN.get())) {

                continue;

            }

            final String[] parts = KmgDelimiterTypes.SERIES_HALF_SPACE.split(trimmedLine, 2);
            final String   tag   = parts[0].trim();
            final String   value = parts[1].trim();
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

        final Class<JavadocAppenderTool> clasz       = JavadocAppenderTool.class;
        final KmgPfaMeasService          measService = new KmgPfaMeasServiceImpl(clasz.toString());
        measService.start();

        final JavadocAppenderTool main = new JavadocAppenderTool();

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
