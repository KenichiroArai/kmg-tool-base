package kmg.tool.base.cmn.infrastructure.io.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.utils.KmgListUtils;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.io.FileIteratorLogic;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;

/**
 * ファイルイテレーターロジック実装<br>
 * <p>
 * ディレクトリ内のファイルを順次処理するためのイテレーター機能を実装します。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.2
 *
 * @version 0.2.2
 */
@Service
public class FileIteratorLogicImpl implements FileIteratorLogic {

    /**
     * 対象ファイルの拡張子
     * <p>
     * nullの場合は全ファイルを対象とする。
     * </p>
     *
     * @since 0.2.2
     */
    private String fileExtension;

    /**
     * 対象ファイルパス
     *
     * @since 0.2.2
     */
    private Path targetPath;

    /**
     * ファイルパスのリスト
     *
     * @since 0.2.2
     */
    private final List<Path> filePathList;

    /**
     * 現在のファイルインデックス
     *
     * @since 0.2.2
     */
    private int currentFileIndex;

    /**
     * 現在のファイルパス
     *
     * @since 0.2.2
     */
    private Path currentFilePath;

    /**
     * 読込んだ内容
     *
     * @since 0.2.2
     */
    private String readContent;

    /**
     * 書き込む内容
     *
     * @since 0.2.2
     */
    private String writeContent;

    /**
     * デフォルトコンストラクタ
     *
     * @since 0.2.2
     */
    public FileIteratorLogicImpl() {

        this.filePathList = new ArrayList<>();
        this.currentFileIndex = 0;
        this.currentFilePath = null;
        this.readContent = KmgString.EMPTY;
        this.fileExtension = null;

    }

    /**
     * 現在のファイルパスを返す。
     *
     * @since 0.2.2
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
     * @since 0.2.2
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
     * @since 0.2.2
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
     * @since 0.2.2
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
     * <p>
     * 拡張子が指定されない場合は、デフォルトで".java"を対象とする。
     * </p>
     *
     * @since 0.2.2
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

        return this.initialize(targetPath, ".java"); //$NON-NLS-1$

    }

    /**
     * 初期化する
     * <p>
     * 拡張子を指定して初期化する。fileExtensionがnullの場合は全ファイルを対象とする。
     * </p>
     *
     * @since 0.2.2
     *
     * @param targetPath
     *                      対象ファイルパス
     * @param fileExtension
     *                      対象ファイルの拡張子（nullの場合は全ファイルを対象）
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path targetPath, final String fileExtension) throws KmgToolMsgException {

        boolean result = false;

        this.targetPath = targetPath;
        this.fileExtension = fileExtension;

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
     * 対象ファイルパスから対象となるファイルをリストにロードする。 拡張子が指定されている場合は、その拡張子のファイルのみを対象とする。 拡張子がnullの場合は、全ファイルを対象とする。
     * </p>
     *
     * @since 0.2.2
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

            Stream<Path> filteredStream = streamPath.filter(Files::isRegularFile);

            /* 拡張子が指定されている場合はフィルタリング */
            if (this.fileExtension != null) {

                filteredStream = filteredStream.filter(path -> path.toString().endsWith(this.fileExtension));

            }

            fileList = filteredStream.collect(Collectors.toList());

        } catch (final NoSuchFileException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN13009;
            final Object[]           genMsgArgs  = {
                this.targetPath.toString(),
            };
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

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
     *
     * @since 0.2.2
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
     * @since 0.2.2
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
     * @since 0.2.2
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
     * @since 0.2.2
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
     * @since 0.2.2
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
