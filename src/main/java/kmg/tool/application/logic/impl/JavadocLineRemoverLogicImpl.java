package kmg.tool.application.logic.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.logic.JavadocLineRemoverLogic;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;

/**
 * Javadoc行削除ロジック<br>
 *
 * @author KenichiroArai
 */
@Service
public class JavadocLineRemoverLogicImpl implements JavadocLineRemoverLogic {

    /** アットマーク */
    private static final String AT_MARK = "@";

    /** ドライブ文字列 */
    private static final String DRIVE_LETTER = "D:";

    /** ドライブ文字列（エスケープ） */
    private static final String DRIVE_LETTER_ESCAPED = "D\\";

    /** Javaファイル拡張子 */
    private static final String JAVA_FILE_EXTENSION = ".java:";

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

        String wkLine = line;

        if (!wkLine.contains(JavadocLineRemoverLogicImpl.AT_MARK)) {

            return result;

        }

        wkLine = wkLine.replace(JavadocLineRemoverLogicImpl.DRIVE_LETTER,
            JavadocLineRemoverLogicImpl.DRIVE_LETTER_ESCAPED);

        // ファイルパスと行番号を抽出
        final String[] parts = KmgDelimiterTypes.COLON.split(wkLine);

        if (parts.length < 2) {

            return result;

        }

        // 実際のファイルパスに変換
        String filePath = parts[0].trim();

        filePath = filePath.replace(JavadocLineRemoverLogicImpl.DRIVE_LETTER_ESCAPED,
            JavadocLineRemoverLogicImpl.DRIVE_LETTER);

        final Path path = Paths.get(filePath);

        int lineNumber = 0;

        try {

            lineNumber = Integer.parseInt(parts[1].trim());

        } catch (@SuppressWarnings("unused") final NumberFormatException e) {

            return result;

        }

        result = new SimpleEntry<>(path, lineNumber);

        return result;

    }

    /**
     * Javadoc行を削除する
     *
     * @param inputMap
     *                 パスと行番号のマップ
     *
     * @return 削除した行数
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public int deleteJavadocLines(final Map<Path, Set<Integer>> inputMap) throws KmgToolMsgException {

        int result = 0;

        /* 対象のJavaファイルごとに処理 */
        for (final Path javaFile : inputMap.keySet()) {

            /* 入力マップに含まれるファイルのみ処理 */
            if (!inputMap.containsKey(javaFile)) {

                continue;

            }

            /* ファイルの内容を読み込む */
            List<String> lines;

            try {

                lines = Files.readAllLines(javaFile);

            } catch (final IOException e) {

                // TODO KenichiroArai 2025/05/14 メッセージ。ファイルの読み込み中にエラーが発生しました。ファイル=[{0}]
                final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.NONE;
                final Object[]           genMsgArgs  = {
                    javaFile.toString(),
                };
                throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

            }

            /* 削除対象の行番号リスト（降順）を取得 */
            final Set<Integer> lineNumbers = inputMap.get(javaFile);

            /* 行番号ごとに行を削除（降順なので、インデックスの調整は不要） */
            for (final Integer lineNumber : lineNumbers) {

                /* 行番号は1から始まるが、リストのインデックスは0から始まるため調整 */
                final int index = lineNumber - 1;

                /* インデックスが有効範囲内かチェック */
                if (index < 0) {

                    continue;

                }

                if (index >= lines.size()) {

                    continue;

                }

                lines.remove(index);
                result++;

            }

            /* 変更した内容をファイルに書き戻す */
            try {

                Files.write(javaFile, lines);

            } catch (final IOException e) {

                // TODO KenichiroArai 2025/05/14 メッセージ。ファイルの書き込み中にエラーが発生しました。ファイル=[{0}]
                final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.NONE;
                final Object[]           genMsgArgs  = {
                    javaFile.toString(),
                };
                throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

            }

        }

        return result;

    }

    /**
     * 入力ファイルからパスと行番号のマップを取得する
     *
     * @param inputPath
     *                  入力ファイルのパス
     *
     * @return パスと行番号の降順のセットのマップ
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public Map<Path, Set<Integer>> getInputMap(final Path inputPath) throws KmgToolMsgException {

        final Map<Path, Set<Integer>> result;

        try (final Stream<String> stream = Files.lines(inputPath)) {

            // Javaファイルの行を抽出するストリームを作成する
            final Stream<String> filteredLines
                = stream.filter(line -> line.contains(JavadocLineRemoverLogicImpl.JAVA_FILE_EXTENSION));

            // ストリームから行をパスと行番号のエントリに変換する
            final Stream<SimpleEntry<Path, Integer>> entries = filteredLines
                .map(JavadocLineRemoverLogicImpl::convertLineToPathLineEntry).filter(entry -> entry != null);

            // エントリからパスと行番号のリストのマップに変換する
            final Map<Path, Set<Integer>> pathLineMap = entries.collect(
                Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));

            // パスごとに行番号のリストを降順にソートする
            final Map<Path, Set<Integer>> sortedLineMap = new LinkedHashMap<>();
            pathLineMap.forEach((path, lineNumbers) -> {

                // Setをソート可能なListに変換
                final List<Integer> sortedLineNumbers = new ArrayList<>(lineNumbers);
                sortedLineNumbers.sort(Comparator.reverseOrder());
                sortedLineMap.put(path, new LinkedHashSet<>(sortedLineNumbers));

            });

            result = sortedLineMap;

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/05/14 メッセージ。入力ファイルの読み込み中にエラーが発生しました。ファイル=[{0}]
            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.NONE;
            final Object[]           genMsgArgs  = {
                inputPath.toString(),
            };
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

        }

        return result;

    }
}
