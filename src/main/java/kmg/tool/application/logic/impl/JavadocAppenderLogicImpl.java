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
import kmg.core.infrastructure.utils.KmgListUtils;
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
     * 合計行数
     */
    private long totalRows;

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
     * タグのマップ
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    private final Map<String, String> tagMap;

    /**
     * 対象のJavaファイルパスのリスト
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    private final List<Path> javaFilePathList;

    /**
     * 現在のJavaファイルパス
     */
    private Path currentJavaFilePath;

    /**
     * 現在のJavaファイルの中身
     */
    private String currentJavaFileContent;

    /**
     * 現在のJavaファイルインデックス
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    private int currentJavaFileIndex;

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
        this.javaFilePathList = new ArrayList<>();
        this.currentJavaFileIndex = 0;
        this.currentJavaFilePath = null;
        this.totalRows = 0;

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

        List<Path> fileList;

        try (final Stream<Path> streamPath = Files.walk(this.targetPath)) {

            // TODO KenichiroArai 2025/03/29 ハードコード
            fileList = streamPath.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java"))
                .collect(Collectors.toList());

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/29 メッセージ
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

        this.javaFilePathList.addAll(fileList);

        if (KmgListUtils.isNotEmpty(this.javaFilePathList)) {

            this.currentJavaFilePath = this.javaFilePathList.get(this.currentJavaFileIndex);

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
     * 現在のJavaファイルパスを返す。
     *
     * @return 現在のJavaファイルパス
     */
    @Override
    public Path getCurrentJavaFilePath() {

        final Path result = this.currentJavaFilePath;
        return result;

    }

    /**
     * 対象のJavaファイルパスのリストを返す<br>
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
    public List<Path> getJavaFilePathList() {

        final List<Path> result = this.javaFilePathList;
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
     * 合計行数を返す。
     *
     * @return 合計行数
     */
    @Override
    public long getTotalRows() {

        final long result = this.totalRows;
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
        this.javaFilePathList.clear();
        this.totalRows = 0;

        result = true;
        return result;

    }

    /**
     * 次のJavaファイルに進む。
     *
     * @return true：ファイルあり、false:ファイルなし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean nextJavaFile() throws KmgToolException {

        boolean result = false;

        this.currentJavaFileIndex++;

        if (this.currentJavaFileIndex >= this.javaFilePathList.size()) {

            return result;

        }

        this.currentJavaFilePath = this.javaFilePathList.get(this.currentJavaFileIndex);

        result = true;
        return result;

    }

    /**
     * 現在のJavaファイルにJavadocを設定する。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @param insertAtTop
     *                    タグを先頭に挿入するかどうか
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean setJavadoc(final boolean insertAtTop) throws KmgToolException {

        boolean result;

        final StringBuilder fileContentBuilder = new StringBuilder();

        try (BufferedReader br = Files.newBufferedReader(this.currentJavaFilePath)) {

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

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/29 メッセージ
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

        this.currentJavaFileContent = fileContentBuilder.toString();

        this.totalRows += KmgDelimiterTypes.LINE_SEPARATOR.split(this.currentJavaFileContent).length;

        result = true;
        return result;

    }

    /**
     * 現在のJavaファイルに書き込む
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean writeCurrentJavaFile() throws KmgToolException {

        boolean result = false;

        try {

            Files.writeString(this.currentJavaFilePath, this.currentJavaFileContent);

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

            if (!content.contains(tag)) {

                continue;

            }

            fileContentBuilder.append(" * ").append(tag).append(" ").append(this.tagMap.get(tag))
                .append(KmgDelimiterTypes.LINE_SEPARATOR.get());
            processedTags.put(tag, true);

        }

        // 未処理のタグを追加
        for (final Map.Entry<String, String> entry : this.tagMap.entrySet()) {

            if (processedTags.containsKey(entry.getKey())) {

                continue;

            }

            fileContentBuilder.append(" * ").append(entry.getKey()).append(" ").append(entry.getValue())
                .append(KmgDelimiterTypes.LINE_SEPARATOR.get());

        }

    }
}
