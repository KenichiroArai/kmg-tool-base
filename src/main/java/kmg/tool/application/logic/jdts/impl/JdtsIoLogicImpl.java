package kmg.tool.application.logic.jdts.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.utils.KmgListUtils;
import kmg.tool.application.logic.jdts.JdtsIoLogic;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグ設定の入出力ロジック<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@Service
public class JdtsIoLogicImpl implements JdtsIoLogic {

    /** 対象ファイルの拡張子 */
    private static final String TARGET_FILE_EXTENSION = ".java";

    /**
     * 対象ファイルパス
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    private Path targetPath;

    /**
     * ファイルパスのリスト
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    private final List<Path> filePathList;

    /**
     * 現在のファイルインデックス
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    private int currentFileIndex;

    /**
     * 現在のファイルパス
     */
    private Path currentFilePath;

    /** 読込んだ内容 */
    private String readContent;

    /** 書き込む内容 */
    private String writeContent;

    /**
     * デフォルトコンストラクタ
     */
    public JdtsIoLogicImpl() {

        this.filePathList = new ArrayList<>();
        this.currentFileIndex = 0;
        this.currentFilePath = null;
        this.readContent = KmgString.EMPTY;

    }

    /**
     * 現在のファイルパスを返す。
     *
     * @return 現在のファイルパス
     */
    @Override
    public Path getCurrentFilePath() {

        final Path result = this.currentFilePath;
        return result;

    }

    /**
     * ファイルパスのリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return ファイルのパス
     */
    @Override
    public List<Path> getFilePathList() {

        final List<Path> result = this.filePathList;
        return result;

    }

    /**
     * 読込んだ内容を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 読込んだ内容
     */
    @Override
    public String getReadContent() {

        final String result = this.readContent;
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
     * 初期化する
     *
     * @param targetPath
     *                   対象ファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path targetPath) throws KmgToolException {

        boolean result = false;

        this.targetPath = targetPath;

        this.filePathList.clear();
        this.currentFileIndex = 0;
        this.currentFilePath = null;
        this.readContent = KmgString.EMPTY;

        result = true;
        return result;

    }

    /**
     * ロードする。
     * <p>
     * 対象ファイルパスから対象となるJavaファイルをリストにロードする。
     * </p>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean load() throws KmgToolException {

        boolean result = false;

        List<Path> fileList;

        try (final Stream<Path> streamPath = Files.walk(this.targetPath)) {

            fileList = streamPath.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(JdtsIoLogicImpl.TARGET_FILE_EXTENSION))
                .collect(Collectors.toList());

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/04/25 メッセージ。Javadocタグ設定で対象ファイルをロード中に例外が発生しました。対象ファイルパス=[{0}]
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {
                this.targetPath.toString()
            };
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

        this.filePathList.addAll(fileList);

        if (KmgListUtils.isNotEmpty(this.filePathList)) {

            this.currentFilePath = this.filePathList.get(this.currentFileIndex);

        }

        result = true;
        return result;

    }

    /**
     * 内容を読み込む。
     * </p>
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean loadContent() throws KmgToolException {

        boolean result = false;

        try {

            this.readContent = Files.readString(this.currentFilePath);

        } catch (
            final IOException e) {

            // TODO KenichiroArai 2025/04/25 メッセージ。Javadocタグ設定で現在のファイルをロード中に例外が発生しました。現在のファイルパス=[{0}]
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {
                this.currentFilePath.toString()
            };
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

        if (KmgString.isBlank(this.readContent)) {

            return result;

        }

        result = true;
        return result;

    }

    /**
     * 次のファイルに進む。
     *
     * @return true：ファイルあり、false:ファイルなし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean nextFile() throws KmgToolException {

        boolean result = false;

        this.currentFileIndex++;

        if (this.currentFileIndex >= this.filePathList.size()) {

            return result;

        }

        this.currentFilePath = this.filePathList.get(this.currentFileIndex);

        result = true;
        return result;

    }

    /**
     * 書き込む内容を設定する。
     *
     * @param content
     *                内容
     */
    @Override
    public void setWriteContent(final String content) {

        this.writeContent = content;

    }

    /**
     * 内容を書き込む
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean writeContent() throws KmgToolException {

        boolean result = false;

        try {

            Files.writeString(this.currentFilePath, this.writeContent);

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/04/25 メッセージ。Javadocタグ設定で現在のファイルに内容を書き込み中に例外が発生しました。現在のファイルパス=[{0}]、書き込む内容=[{1}]
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {
                this.currentFilePath.toString(), this.writeContent
            };
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

        result = true;
        return result;

    }
}
