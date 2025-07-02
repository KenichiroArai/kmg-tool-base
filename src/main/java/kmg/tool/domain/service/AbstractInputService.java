package kmg.tool.domain.service;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.type.msg.KmgToolGenMsgTypes;

/**
 * 抽象入力サービス
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
@Service
public abstract class AbstractInputService implements InputService {

    /** 入力ファイルパス */
    private Path inputPath;

    /**
     * 入力ファイルパスを返す<br>
     *
     * @return 入力ファイルパス
     */
    @Override
    public Path getInputPath() {

        final Path result = this.inputPath;
        return result;

    }

    /**
     * 初期化する<br>
     *
     * @param inputPath
     *                  入力ファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath) throws KmgToolMsgException {

        boolean result = false;

        // 入力パスの検証
        if (inputPath == null) {

            final KmgToolGenMsgTypes genType     = KmgToolGenMsgTypes.KMGTOOL_GEN12004;
            final Object[]           messageArgs = {};

            throw new KmgToolMsgException(genType, messageArgs);

        }

        if (!Files.exists(inputPath)) {

            final KmgToolGenMsgTypes genType     = KmgToolGenMsgTypes.KMGTOOL_GEN12005;
            final Object[]           messageArgs = {
                inputPath.toString()
            };

            throw new KmgToolMsgException(genType, messageArgs);

        }

        // 入力パスの設定
        this.inputPath = inputPath;

        result = true;
        return result;

    }

    /**
     * 処理する<br>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public abstract boolean process() throws KmgToolMsgException;

}
