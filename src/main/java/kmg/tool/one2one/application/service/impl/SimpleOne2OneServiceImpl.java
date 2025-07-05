package kmg.tool.one2one.application.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.one2one.application.service.SimpleOne2OneService;

/**
 * シンプル1入力ファイルから1出力ファイルへの変換ツールサービス<br>
 */
@Service
public class SimpleOne2OneServiceImpl implements SimpleOne2OneService {

    /** 入力ファイルパス */
    private Path inputPath;

    /** 出力ファイルパス */
    private Path outputPath;

    /**
     * 入力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 入力ファイルパス
     */
    @Override
    public Path getInputPath() {

        final Path result = this.inputPath;
        return result;

    }

    /**
     * 出力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 出力ファイルパス
     */
    @Override
    public Path getOutputPath() {

        final Path result = this.outputPath;
        return result;

    }

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                   入力ファイルパス
     * @param outputPath
     *                   出力ファイルパス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path outputPath) throws KmgToolMsgException {

        final boolean result = true;

        this.inputPath = inputPath;
        this.outputPath = outputPath;

        return result;

    }

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean process() throws KmgToolMsgException {

        boolean result = false;

        /* 入力から出力の処理 */

        try (final BufferedReader brInput = Files.newBufferedReader(this.inputPath);
            final BufferedWriter bwOutput = Files.newBufferedWriter(this.outputPath);) {

            String line = null;

            while ((line = brInput.readLine()) != null) {

                bwOutput.write(line);

            }

        } catch (final IOException e) {

            // 例外をスローする
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN31000;
            final Object[]           messageArgs = {};
            throw new KmgToolMsgException(msgType, messageArgs, e);

        }

        result = true;
        return result;

    }

}
