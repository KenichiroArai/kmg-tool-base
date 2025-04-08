package kmg.tool.application.logic.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
import kmg.tool.application.model.jda.JdaReplacementModel;
import kmg.tool.application.model.jda.JdaTagConfigModel;
import kmg.tool.application.model.jda.JdaTagsModel;
import kmg.tool.application.model.jda.imp.JdaReplacementModelImpl;
import kmg.tool.application.model.jda.imp.JdaTagsModelImpl;
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
     * Javadoc追加のタグ設定モデルのリスト
     */
    private final List<JdaTagConfigModel> jdaTagConfigModels;

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
    private JdaTagsModel jdaTagsModel;

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

        this.jdaTagConfigModels = new ArrayList<>();
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
            this.jdaTagsModel = new JdaTagsModelImpl(yamlData);

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
     * Javadoc追加のタグモデルを取得する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return Javadoc追加のタグモデル
     */
    @Override
    public JdaTagsModel getJavadocAppenderTagsModel() {

        final JdaTagsModel result = this.jdaTagsModel;
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

            final List<JdaReplacementModel> javadocReplacementModelList = new ArrayList<>();

            // 「/**」でブロックに分ける
            // TODO KenichiroArai 2025/04/03 ハードコード
            final String[] blocks
                = this.currentContentsOfFileToWrite.split(String.format("%s\\s+", Pattern.quote("/**")));

            // ブロックの0番目はJavadocではないので、1番目から進める
            for (int i = 1; i < blocks.length; i++) {

                // 「*/」でJavadocとCodeのブラックに分ける
                // TODO KenichiroArai 2025/04/03 ハードコード
                final String[] javadocCodeBlock = blocks[i].split(String.format("%s\\s+", Pattern.quote("*/")), 2);

                // 元のJavadoc
                final String sourceJavadoc = javadocCodeBlock[0];

                // 元のコード
                final String sourceCode = javadocCodeBlock[1];

                // TODO KenichiroArai 2025/04/09 コードを精査する
                // アノテーションと元のコードを分割
                final String[]      codeLines      = sourceCode.split("\\R");
                final StringBuilder codeBuilder    = new StringBuilder();
                final List<String>  annotationList = new ArrayList<>();

                boolean isCodeSection = false;

                for (final String line : codeLines) {

                    final String trimmedLine = line.trim();

                    if (trimmedLine.isEmpty()) {

                        continue;

                    }

                    if (!isCodeSection) {

                        if (trimmedLine.startsWith("@")) {

                            annotationList.add(trimmedLine);

                        } else {

                            isCodeSection = true;
                            codeBuilder.append(line).append(System.lineSeparator());

                        }

                    } else {

                        codeBuilder.append(line).append(System.lineSeparator());

                    }

                }

                final String actualSourceCode = codeBuilder.toString().trim();

                // 元のJavadoc

                final JdaReplacementModel jdaReplacementModel
                    = new JdaReplacementModelImpl(sourceJavadoc, actualSourceCode, this.jdaTagsModel);
                javadocReplacementModelList.add(jdaReplacementModel);

                // 元のJavadoc部分を置換用識別子に置換する
                this.currentContentsOfFileToWrite = this.currentContentsOfFileToWrite
                    .replaceFirst(Pattern.quote(sourceJavadoc), jdaReplacementModel.getIdentifier().toString());

                // Java区分を特定する
                jdaReplacementModel.specifyJavaClassification();

                // 置換後のJavadocを作成する
                jdaReplacementModel.createReplacedJavadoc();

            }

            // 置換用識別子を置換後のJavadocに置換する
            for (final JdaReplacementModel jdaReplacementModel : javadocReplacementModelList) {

                this.currentContentsOfFileToWrite = this.currentContentsOfFileToWrite
                    .replace(jdaReplacementModel.getIdentifier().toString(), jdaReplacementModel.getReplacedJavadoc());

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

}
