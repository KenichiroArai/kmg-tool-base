package kmg.tool.application.logic.impl;

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
import kmg.tool.application.logic.JdtsIoLogic;
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
     * 現在のJavaファイルパス
     */
    private Path currentJavaFilePath;

    // TODO KenichiroARai 2025/04/11 使用するか考える。
    /**
     * 現在のJavaファイルの中身
     */
    private String currentJavaFileContent;

    /**
     * デフォルトコンストラクタ
     */
    public JdtsIoLogicImpl() {

        this.javaFilePathList = new ArrayList<>();
        this.currentJavaFileIndex = 0;
        this.currentJavaFilePath = null;
        this.currentJavaFileContent = KmgString.EMPTY;

    }

    /**
     * 現在のJavaファイルの中身を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 現在のJavaファイルの中身
     */
    @Override
    public String getCurrentJavaFileContent() {

        final String result = this.currentJavaFileContent;
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

        this.javaFilePathList.clear();
        this.currentJavaFileIndex = 0;
        this.currentJavaFilePath = null;
        this.currentJavaFileContent = KmgString.EMPTY;

        result = true;
        return result;

    }

    /**
     * 対象のJavaファイルをロードする。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean loadJavaFileList() throws KmgToolException {

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
     * 内容を返す。
     *
     * @return 内容
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public String read() throws KmgToolException {

        String result;

        try {

            result = Files.readString(this.currentJavaFilePath);

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/29 メッセージ
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

        return result;

    }

    /**
     * 内容を書き込む
     *
     * @param contents
     *                 内容
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean write(final String contents) throws KmgToolException {

        boolean result = false;

        try {

            Files.writeString(this.currentJavaFilePath, contents);

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
