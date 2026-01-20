package kmg.tool.base.simple.domain.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseGenMsgTypes;
import kmg.tool.base.simple.application.service.SimpleTwo2OneService;

/**
 * シンプル2入力ファイルから1出力ファイルへの変換ツールサービス<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
@Service
public class SimpleTwo2OneServiceImpl implements SimpleTwo2OneService {

    /**
     * テンプレート置換用プレースホルダー
     *
     * @since 0.2.0
     */
    private static final String TEMPLATE_NAME_PLACEHOLDER = "{ name }"; //$NON-NLS-1$

    /**
     * 入力ファイルパス
     *
     * @since 0.2.0
     */
    private Path inputPath;

    /**
     * テンプレートファイルパス
     *
     * @since 0.2.0
     */
    private Path templatePath;

    /**
     * 出力ファイルパス
     *
     * @since 0.2.0
     */
    private Path outputPath;

    /**
     * デフォルトコンストラクタ
     *
     * @since 0.2.0
     */
    public SimpleTwo2OneServiceImpl() {

        // 処理なし
    }

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
     * テンプレートファイルパスを返す<br>
     *
     * @since 0.2.0
     *
     * @return テンプレートファイルパス
     */
    @Override
    public Path getTemplatePath() {

        final Path result = this.templatePath;
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
     *                     入力ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     * @param outputPath
     *                     出力ファイルパス
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath) {

        final boolean result = true;

        this.inputPath = inputPath;
        this.templatePath = templatePath;
        this.outputPath = outputPath;

        return result;

    }

    /**
     * 処理する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    @Override
    public boolean process() throws KmgToolBaseMsgException {

        boolean result = false;

        /* テンプレートの取得 */
        String template = null;

        try {

            template = Files.readAllLines(this.templatePath).stream()
                .collect(Collectors.joining(KmgDelimiterTypes.LINE_SEPARATOR.get()));

        } catch (final IOException e) {

            // 例外をスローする
            final KmgToolBaseGenMsgTypes msgType     = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN16001;
            final Object[]               messageArgs = {
                this.templatePath.toString()
            };
            throw new KmgToolBaseMsgException(msgType, messageArgs, e);

        }

        /* 入力から出力の処理 */
        try (final BufferedReader brInput = Files.newBufferedReader(this.inputPath);
            final BufferedWriter bw = Files.newBufferedWriter(this.outputPath);) {

            final StringBuilder output = new StringBuilder();

            String line = null;

            while ((line = brInput.readLine()) != null) {

                final String wk = template.replace(SimpleTwo2OneServiceImpl.TEMPLATE_NAME_PLACEHOLDER, line);
                output.append(wk);
                output.append(KmgDelimiterTypes.LINE_SEPARATOR.get());

            }

            bw.write(output.toString());

        } catch (final IOException e) {

            // 例外をスローする
            final KmgToolBaseGenMsgTypes msgType     = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN16000;
            final Object[]               messageArgs = {};
            throw new KmgToolBaseMsgException(msgType, messageArgs, e);

        }

        result = true;
        return result;

    }

}
