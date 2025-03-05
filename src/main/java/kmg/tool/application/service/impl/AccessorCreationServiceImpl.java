package kmg.tool.application.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.service.AccessorCreationService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * アクセサ作成サービス実装クラス<br>
 *
 * @author KenichiroArai
 */
@Service
public class AccessorCreationServiceImpl implements AccessorCreationService {

    /** 入力ファイルパス */
    private Path inputPath;

    /** テンプレートファイルパス */
    private Path templatePath;

    /** 出力ファイルパス */
    private Path outputPath;

    /** テンプレート */
    private String template;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath) {

        boolean result = false;

        /* 入力ファイルパスの設定 */
        this.inputPath = inputPath;

        /* テンプレートファイルパスの設定 */
        this.templatePath = templatePath;

        /* 出力ファイルパスの設定 */
        this.outputPath = outputPath;

        /* テンプレートの取得 */
        try {

            this.template = Files.readAllLines(this.templatePath).stream()
                .collect(Collectors.joining(KmgDelimiterTypes.LINE_SEPARATOR.get()));
            result = true;

        } catch (final IOException e) {

            e.printStackTrace();
            return result;

        }

        return result;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean process() throws KmgToolException {

        boolean result = false;

        /* 入力から出力の処理 */
        try (final BufferedReader brInput = Files.newBufferedReader(this.inputPath);
            final BufferedWriter bw = Files.newBufferedWriter(this.outputPath);) {

            String output = this.template;
            String line   = null;

            while ((line = brInput.readLine()) != null) {

                line = line.replace("final", KmgString.EMPTY);
                line = line.replace("static", KmgString.EMPTY);
                final Pattern patternComment = Pattern.compile("/\\*\\* (\\S+)");
                final Matcher matcherComment = patternComment.matcher(line);

                if (matcherComment.find()) {

                    output = output.replace("$name", matcherComment.group(1));
                    continue;

                }

                final Pattern patternSrc = Pattern.compile("private\\s+((\\w|\\[\\]|<|>)+)\\s+(\\w+);");
                final Matcher matcherSrc = patternSrc.matcher(line);

                if (matcherSrc.find()) {

                    output = output.replace("$type", matcherSrc.group(1));
                    output = output.replace("$item", matcherSrc.group(3));
                    output = output.replace("$Item",
                        matcherSrc.group(3).substring(0, 1).toUpperCase() + matcherSrc.group(3).substring(1));
                    bw.write(output);
                    bw.write(System.lineSeparator());
                    output = this.template;

                }

            }

            result = true;

        } catch (final IOException e) {

            // KmgToolExceptionが適切なメッセージタイプと一緒に例外をスローします
            final KmgToolGenMessageTypes msgType = KmgToolGenMessageTypes.KMGTOOLGENI41002;
            throw new KmgToolException(msgType, e);

        }

        return result;

    }
}
