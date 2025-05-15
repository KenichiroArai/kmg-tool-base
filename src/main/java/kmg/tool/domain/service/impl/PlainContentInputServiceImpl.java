package kmg.tool.domain.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import kmg.tool.domain.service.PlainContentInputServic;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;

/**
 * プレーンコンテンツ入力サービス<br>
 * <p>
 * 入力ファイルパスを読み込み、プレーンコンテンツを返すサービスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
@Service
public class PlainContentInputServiceImpl implements PlainContentInputServic {

    /** 入力ファイルパス */
    private Path inputPath;

    /** 入力内容 */
    private String content;

    /**
     * 入力内容を返す<br>
     *
     * @return 入力内容
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public String getContent() throws KmgToolMsgException {

        final String result = this.content;
        return result;

    }

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
    public boolean process() throws KmgToolMsgException {

        boolean result = false;

        try {

            // ファイルの内容を読み込む
            this.content = Files.readString(this.inputPath);

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genType     = KmgToolGenMsgTypes.KMGTOOL_GEN12006;
            final Object[]           messageArgs = {
                this.inputPath.toString()
            };

            throw new KmgToolMsgException(genType, messageArgs, e);

        }

        result = true;
        return result;

    }
}
