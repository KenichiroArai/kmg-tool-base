package kmg.tool.jdts.application.logic.impl;

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
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.jdts.application.logic.JdtsIoLogic;

// TODO KenichiroArai 2025/07/11 汎用化する。対象ファイルパスを読み込むようにする。拡張子の指定はオプション扱いにする。
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

    /**
     * 対象ファイルの拡張子
     *
     * @since 0.1.0
     */
    private static final String TARGET_FILE_EXTENSION = ".java"; //$NON-NLS-1$

    /**
     * 対象ファイルパス
     *
     * @since 0.1.0
     */
    private Path targetPath;

    /**
     * ファイルパスのリスト
     *
     * @since 0.1.0
     */
    private final List<Path> filePathList;

    /**
     * 現在のファイルインデックス
     *
     * @since 0.1.0
     */
    private int currentFileIndex;

    /**
     * 現在のファイルパス
     *
     * @since 0.1.0
     */
    private Path currentFilePath;

    /**
     * 読込んだ内容
     *
     * @since 0.1.0
     */
    private String readContent;

    /**
     * 書き込む内容
     *
     * @since 0.1.0
     */
    private String writeContent;

    /**
     * デフォルトコンストラクタ
     *
     * @since 0.1.0
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
     * @since 0.1.0
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
     * @since 0.1.0
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
     * @since 0.1.0
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
     * @since 0.1.0
     *
     * @param targetPath
     *                   対象ファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path targetPath) throws KmgToolMsgException {

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
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean load() throws KmgToolMsgException {

        boolean result = false;

        List<Path> fileList;

        try (final Stream<Path> streamPath = Files.walk(this.targetPath)) {

            fileList = streamPath.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(JdtsIoLogicImpl.TARGET_FILE_EXTENSION))
                .collect(Collectors.toList());

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN13002;
            final Object[]           genMsgArgs  = {
                this.targetPath.toString(),
            };
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

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
     * @since 0.1.0
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean loadContent() throws KmgToolMsgException {

        boolean result = false;

        try {

            this.readContent = Files.readString(this.currentFilePath);

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN13001;
            final Object[]           genMsgArgs  = {
                this.currentFilePath.toString(),
            };
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

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
     * @since 0.1.0
     *
     * @return true：ファイルあり、false:ファイルなし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean nextFile() throws KmgToolMsgException {

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
     * ファイルインデックスを初期化する。
     * <p>
     * currentFileIndexを0に設定し、currentFilePathを先頭のファイルに設定する。
     * </p>
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean resetFileIndex() throws KmgToolMsgException {

        boolean result;

        this.currentFileIndex = 0;

        if (KmgListUtils.isNotEmpty(this.filePathList)) {

            this.currentFilePath = this.filePathList.get(this.currentFileIndex);

        } else {

            this.currentFilePath = null;

        }

        result = true;
        return result;

    }

    /**
     * 書き込む内容を設定する。
     *
     * @since 0.1.0
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
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean writeContent() throws KmgToolMsgException {

        boolean result = false;

        try {

            Files.writeString(this.currentFilePath, this.writeContent);

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN13000;
            final Object[]           genMsgArgs  = {
                this.currentFilePath.toString(), this.writeContent,
            };
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

        }

        result = true;
        return result;

    }

}
