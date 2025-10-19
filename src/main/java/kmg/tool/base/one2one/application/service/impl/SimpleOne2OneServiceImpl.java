package kmg.tool.base.one2one.application.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.one2one.application.service.SimpleOne2OneService;

/**
 * シンプル1入力ファイルから1出力ファイルへの変換ツールサービス<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@Service
public class SimpleOne2OneServiceImpl implements SimpleOne2OneService {

    /**
     * 入力ファイルパス
     *
     * @since 0.2.0
     */
    private Path inputPath;

    /**
     * 出力ファイルパス
     *
     * @since 0.2.0
     */
    private Path outputPath;

    /**
     * 入力ファイルパスを返す<br>
     *
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @since 0.2.0
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
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN15000;
            final Object[]           messageArgs = {};
            throw new KmgToolMsgException(msgType, messageArgs, e);

        }

        result = true;
        return result;

    }

}
