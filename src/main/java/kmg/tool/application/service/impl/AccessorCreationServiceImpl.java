package kmg.tool.application.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.tool.application.service.AccessorCreationService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * アクセサ作成サービス<br>
 *
 * @author KenichiroArai
 */
@Service
public class AccessorCreationServiceImpl extends DynamicTemplateConversionServiceImpl
    implements AccessorCreationService {

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean process() throws KmgToolException {

        boolean result = false;

        /* 入力から出力の処理 */
        final List<List<String>> csv = new ArrayList<>();

        final Path csvPath = Path.of("");

        try (final BufferedReader brInput = Files.newBufferedReader(this.getInputPath());
            final BufferedWriter bw = Files.newBufferedWriter(csvPath);) {

            String line = null;

            while ((line = brInput.readLine()) != null) {

                // final String[] keyArrays = columnMappings.values().toArray(new String[0]);

                line = line.replace("final", KmgString.EMPTY);
                line = line.replace("static", KmgString.EMPTY);
                final Pattern patternComment = Pattern.compile("/\\*\\* (\\S+)");
                final Matcher matcherComment = patternComment.matcher(line);

                if (matcherComment.find()) {

                    final List<String> csvLine = new ArrayList<>();
                    csvLine.add(matcherComment.group(1));
                    csv.add(csvLine);

                    continue;

                }

                final Pattern patternSrc = Pattern.compile("private\\s+((\\w|\\[\\]|<|>)+)\\s+(\\w+);");
                final Matcher matcherSrc = patternSrc.matcher(line);

                if (!matcherSrc.find()) {

                    continue;

                }

                final List<String> csvLine = csv.getLast();
                csvLine.add(matcherSrc.group(1));
                csvLine.add(matcherSrc.group(3));
                csvLine.add(matcherSrc.group(3).substring(0, 1).toUpperCase() + matcherSrc.group(3).substring(1));

                bw.write(String.join(",", csvLine));
                bw.write(System.lineSeparator());

            }

            result = true;

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/06 メッセージ
            final KmgToolGenMessageTypes msgType = KmgToolGenMessageTypes.NONE;
            throw new KmgToolException(msgType, e);

        }

        result = super.process();

        return result;

    }
}
