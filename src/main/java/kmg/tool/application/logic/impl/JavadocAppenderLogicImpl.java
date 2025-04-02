package kmg.tool.application.logic.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.core.infrastructure.utils.KmgListUtils;
import kmg.fund.infrastructure.exception.KmgFundException;
import kmg.fund.infrastructure.utils.KmgYamlUtils;
import kmg.tool.application.logic.JavadocAppenderLogic;
import kmg.tool.domain.model.JavadocReplacementModel;
import kmg.tool.domain.model.JavadocTagConfigModel;
import kmg.tool.domain.model.JavadocTagsModel;
import kmg.tool.domain.model.impl.JavadocReplacementModelImpl;
import kmg.tool.domain.model.impl.JavadocTagsModelImpl;
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
     * Javadocタグ設定モデルのリスト
     */
    private final List<JavadocTagConfigModel> javadocTagConfigModels;

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
     * 現在の書き込みするファイルの中身
     */
    private String currentContentsOfFileToWrite;

    /** Javadocタグモデル */
    private JavadocTagsModel javadocTagsModel;

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

        this.javadocTagConfigModels = new ArrayList<>();
        this.javaFilePathList = new ArrayList<>();
        this.currentJavaFileIndex = 0;
        this.currentJavaFilePath = null;
        this.totalRows = 0;

    }

    /**
     * Javadocタグモデルを作成する<br>
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
    public boolean createJavadocTagsModel() throws KmgToolException {

        boolean result = false;

        try {

            /* YAMLファイルを読み込み、モデルを作成 */
            final Map<String, Object> yamlData = KmgYamlUtils.load(this.templatePath);
            this.javadocTagsModel = new JavadocTagsModelImpl(yamlData);

        } catch (final KmgFundException e) {

            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

        result = true;
        return result;

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
     * 現在の書き込みするファイルの中身を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 現在の書き込みするファイルの中身
     */
    @Override
    public String getCurrentContentsOfFileToWrite() {

        final String result = this.currentContentsOfFileToWrite;
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
     * Javadocタグモデルを取得する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return Javadocタグモデル
     */
    @Override
    public JavadocTagsModel getJavadocTagsModel() {

        final JavadocTagsModel result = this.javadocTagsModel;
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

        final boolean result;

        try {

            /* Javaファイルを読み込み、現在の中身と書き込み用の中身に設定する */
            this.currentJavaFileContent = Files.readString(this.currentJavaFilePath);
            this.currentContentsOfFileToWrite = this.currentJavaFileContent;

            /* Javadocを設定する */

            final List<JavadocReplacementModel> javadocReplacementModelList = new ArrayList<>();

            // 「/**」でブロックに分ける
            final String[] blocks = this.currentContentsOfFileToWrite.split(Pattern.quote("/**"));

            // ブロックの0番目はJavadocではないので、1番目から進める
            for (int i = 1; i < blocks.length; i++) {

                // 「*/」でJavadocとCodeのブラックに分ける
                final String[] javadocCodeBlock = blocks[i].split(Pattern.quote("*/"), 2);

                // 元のJavadoc
                final String sourceJavadoc = javadocCodeBlock[0];

                // 元のコード
                final String sourceCode = javadocCodeBlock[1];

                // 元のコードの分析

                // 元のJavadoc

                final JavadocReplacementModel javadocReplacementModel
                    = new JavadocReplacementModelImpl(sourceJavadoc, sourceCode, this.javadocTagsModel);
                javadocReplacementModelList.add(javadocReplacementModel);

                // TODO KenichiroArai 2025/03/29 実装中

                // 元のJavadoc部分を置換用識別子に置換する
                this.currentContentsOfFileToWrite = this.currentContentsOfFileToWrite
                    .replaceFirst(Pattern.quote(sourceJavadoc), javadocReplacementModel.getIdentifier().toString());

                // Java区分を特定する
                javadocReplacementModel.specifyJavaClassification();

                // 置換後のJavadocを作成する
                javadocReplacementModel.createReplacedJavadoc();

            }

            // 置換用識別子を置換後のJavadocに置換する
            for (final JavadocReplacementModel javadocReplacementModel : javadocReplacementModelList) {

                this.currentContentsOfFileToWrite = this.currentContentsOfFileToWrite.replace(
                    javadocReplacementModel.getIdentifier().toString(), javadocReplacementModel.getReplacedJavadoc());

            }

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/29 メッセージ
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

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

            Files.writeString(this.currentJavaFilePath, this.currentContentsOfFileToWrite);

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
        for (final JavadocTagConfigModel tagConfig : this.javadocTagsModel.getJavadocTagConfigModels()) {

            final String tag = "@" + tagConfig.getName();

            if (!content.contains(tag)) {

                continue;

            }

            fileContentBuilder.append(" * ").append(tag).append(" ").append(tagConfig.getText())
                .append(KmgDelimiterTypes.LINE_SEPARATOR.get());
            processedTags.put(tag, true);

        }

        // 未処理のタグを追加
        for (final JavadocTagConfigModel tagConfig : this.javadocTagsModel.getJavadocTagConfigModels()) {

            final String tag = "@" + tagConfig.getName();

            if (processedTags.containsKey(tag)) {

                continue;

            }

            fileContentBuilder.append(" * ").append(tag).append(" ").append(tagConfig.getText())
                .append(KmgDelimiterTypes.LINE_SEPARATOR.get());

        }

    }
}
