package kmg.tool.application.logic.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.logic.JavadocAppenderLogic;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加ロジック<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@Service
public class JavadocAppenderLogicImpl implements JavadocAppenderLogic {

    /**
     * 対象ファイルパス
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    private Path targetPath;

    /**
     * テンプレートファイルパス
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    private Path templatePath;

    /**
     * タグマップ
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    private final Map<String, String> tagMap;

    /**
     * 対象のJavaファイルリスト
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    private final List<Path> javaFileList;

    /**
     * デフォルトコンストラクタ
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    public JavadocAppenderLogicImpl() {

        this.tagMap = new HashMap<>();
        this.javaFileList = new ArrayList<>();

    }

    /**
     * 対象のJavaファイル
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean createJavaFileList() throws KmgToolException {

        boolean result = false;

        try (final Stream<Path> streamPath = Files.walk(this.targetPath)) {

            final List<Path> fileList = streamPath.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java")).collect(Collectors.toList());
            this.javaFileList.addAll(fileList);

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/29 メッセージ
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

        result = true;
        return result;

    }

    /**
     * タグマップを作成する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean createTagMap() throws KmgToolException {

        boolean result = false;

        /* テンプレートの読み込み */
        List<String> lines = null;

        try {

            lines = Files.readAllLines(this.templatePath);

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/29 メッセージ
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

        /* タグマップの作成 */
        for (final String line : lines) {

            final String trimmedLine = line.trim();

            if (!trimmedLine.startsWith(KmgDelimiterTypes.HALF_AT_SIGN.get())) {

                continue;

            }

            // TODO KenichiroArai 2025/03/29 ハードコード
            // タグと値を分離
            final String[] parts = KmgDelimiterTypes.SERIES_HALF_SPACE.split(trimmedLine, 2);
            final String   tag   = parts[0].trim();
            final String   value = parts[1].trim();
            this.tagMap.put(tag, value);

        }

        result = true;
        return result;

    }

    /**
     * 対象のJavaファイルリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @sine 0.1.0
     *
     * @return 対象のJavaファイルリスト
     */
    @Override
    public List<Path> getJavaFileList() {

        final List<Path> result = this.javaFileList;
        return result;

    }

    /**
     * 新しいJavaファイルを返す。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @param javaFile
     *                           Javaファイル
     * @param fileContentBuilder
     *                           ファイル内容ビルダー
     * @param insertAtTop
     *                           タグを先頭に挿入するかどうか
     *
     * @throws IOException
     *                     入出力例外
     *
     * @return ファイル内容
     */
    @Override
    public String getNewJavaFile(final Path javaFile, final StringBuilder fileContentBuilder, final boolean insertAtTop)
        throws IOException {

        try (BufferedReader br = Files.newBufferedReader(javaFile)) {

            /* 行ごとの読み込み */
            boolean             isInJavadoc    = false;
            String              line           = null;
            final StringBuilder javadocBuilder = new StringBuilder();
            final StringBuilder contentBuilder = new StringBuilder();
            boolean             foundFirstTag  = false;

            while ((line = br.readLine()) != null) {

                final String trimmedLine = line.trim();

                if (trimmedLine.startsWith("/**")) {

                    /* Javadocの開始 */

                    isInJavadoc = true;
                    javadocBuilder.setLength(0);
                    contentBuilder.setLength(0);
                    foundFirstTag = false;

                    if (!trimmedLine.endsWith("*/")) {

                        // Javadocの開始行の末尾に*/がない（複数行）場合
                        javadocBuilder.append(line).append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                        continue;

                    }

                    // 1行Javadocの場合の処理
                    fileContentBuilder.append("/**").append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                    // コメントを取得して出力
                    final String comment = trimmedLine.substring(3, trimmedLine.length() - 2).trim();

                    if (!comment.isEmpty()) {

                        fileContentBuilder.append(" * ").append(comment).append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                    }

                    // タグを出力
                    this.processJavadocTags(fileContentBuilder, comment);

                    fileContentBuilder.append(" */").append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                    isInJavadoc = false;
                    continue;

                }

                if (isInJavadoc && trimmedLine.endsWith("*/")) {

                    /* Javadocの終了 */

                    isInJavadoc = false;

                    /* 既存のJavadocの内容を解析してタグを置き換え */
                    final String[] javadocLines
                        = javadocBuilder.toString().split(KmgDelimiterTypes.LINE_SEPARATOR.get());

                    /* 各行を処理 */
                    for (final String javadocLine : javadocLines) {

                        final String trimmedJavadocLine = javadocLine.trim();

                        // 最初のタグを検出した位置でtagMapのタグを挿入（insertAtTopがtrueの場合）
                        if (insertAtTop && !foundFirstTag && trimmedJavadocLine.contains("@")) {

                            foundFirstTag = true;

                            /* tagMapのタグを出力 */
                            for (final Map.Entry<String, String> entry : this.tagMap.entrySet()) {

                                fileContentBuilder.append(" * ").append(entry.getKey()).append(" ")
                                    .append(entry.getValue()).append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                            }

                        }

                        if (!trimmedJavadocLine.contains("@")) {

                            // タグ行でない場合は保持
                            fileContentBuilder.append(javadocLine).append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                            continue;

                        }

                        // タグ行の場合、tagMapに存在するタグかチェック
                        boolean isTagInMap = false;

                        for (final String tag : this.tagMap.keySet()) {

                            if (trimmedJavadocLine.contains(tag)) {

                                isTagInMap = true;
                                break;

                            }

                        }

                        // tagMapにないタグ行は保持
                        if (!isTagInMap) {

                            fileContentBuilder.append(javadocLine).append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                        }

                    }

                    // タグが見つからなかった場合や、insertAtTopがfalseの場合は末尾にタグを挿入
                    if (!foundFirstTag || !insertAtTop) {

                        /* tagMapのタグを出力 */
                        for (final Map.Entry<String, String> entry : this.tagMap.entrySet()) {

                            fileContentBuilder.append(" * ").append(entry.getKey()).append(" ").append(entry.getValue())
                                .append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                        }

                    }

                    fileContentBuilder.append(" */").append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                    continue;

                }

                /* Javadoc内の行の処理 */
                if (!isInJavadoc) {

                    fileContentBuilder.append(line).append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                    continue;

                }

                // Javadoc内の場合
                javadocBuilder.append(line).append(KmgDelimiterTypes.LINE_SEPARATOR.get());

            }

        }

        final String result = fileContentBuilder.toString();
        return result;

    }

    /**
     * タグマップを取得する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return タグマップ
     */
    @Override
    public Map<String, String> getTagMap() {

        final Map<String, String> result = new HashMap<>(this.tagMap);
        return result;

    }

    /**
     * 対象ファイルパス
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return 対象ファイルパス
     */
    @Override
    public Path getTargetPath() {

        final Path result = this.targetPath;
        return result;

    }

    /**
     * テンプレートファイルパス
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return テンプレートファイルパス
     */
    @Override
    public Path getTemplatePath() {

        final Path result = this.templatePath;
        return result;

    }

    /**
     * 初期化する
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @param targetPath
     *                     対象ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path targetPath, final Path templatePath) throws KmgToolException {

        boolean result = false;

        this.targetPath = targetPath;
        this.templatePath = templatePath;

        this.tagMap.clear();
        this.javaFileList.clear();

        result = true;
        return result;

    }

    /**
     * Javadocのタグを処理する。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @param fileContentBuilder
     *                           ファイル内容ビルダー
     * @param content
     *                           内容
     */
    private void processJavadocTags(final StringBuilder fileContentBuilder, final String content) {

        // 既存のタグを確認
        final Map<String, Boolean> processedTags = new HashMap<>();

        // 既存のタグを処理
        for (final String tag : this.tagMap.keySet()) {

            if (content.contains(tag)) {

                fileContentBuilder.append(" * ").append(tag).append(" ").append(this.tagMap.get(tag))
                    .append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                processedTags.put(tag, true);

            }

        }

        // 未処理のタグを追加
        for (final Map.Entry<String, String> entry : this.tagMap.entrySet()) {

            if (!processedTags.containsKey(entry.getKey())) {

                fileContentBuilder.append(" * ").append(entry.getKey()).append(" ").append(entry.getValue())
                    .append(KmgDelimiterTypes.LINE_SEPARATOR.get());

            }

        }

    }
}
