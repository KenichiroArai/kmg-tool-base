package kmg.tool.application.service.io.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.tool.application.service.io.AccessorCreationService;
import kmg.tool.domain.service.AbstractInputCsvTemplateOutputProcessorService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * アクセサ作成サービス<br>
 *
 * @author KenichiroArai
 */
@Service
public class AccessorCreationServiceImpl extends AbstractInputCsvTemplateOutputProcessorService
    implements AccessorCreationService {

    /**
     * CSVファイルに書き込む。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    protected boolean writeCsvFile() throws KmgToolException {

        final boolean result = false;

        final List<List<String>> csvData = new ArrayList<>();

        /* 入力ファイルからCSV形式に変換してCSVファイルに出力する */
        try (final BufferedReader brInput = Files.newBufferedReader(this.getInputPath());
            final BufferedWriter brOutput = Files.newBufferedWriter(this.getCsvPath());) {

            String line = null;

            while ((line = brInput.readLine()) != null) {

                line = line.replace("final", KmgString.EMPTY);
                line = line.replace("static", KmgString.EMPTY);
                final Pattern patternComment = Pattern.compile("/\\*\\* (\\S+)");
                final Matcher matcherComment = patternComment.matcher(line);

                if (matcherComment.find()) {

                    final List<String> csvLine = new ArrayList<>();
                    csvLine.add(matcherComment.group(1));
                    csvData.add(csvLine);

                    continue;

                }

                final Pattern patternSrc = Pattern.compile("private\\s+((\\w|\\[\\]|<|>)+)\\s+(\\w+);");
                final Matcher matcherSrc = patternSrc.matcher(line);

                if (!matcherSrc.find()) {

                    continue;

                }

                final List<String> csvLine = csvData.getLast();
                csvLine.add(matcherSrc.group(1));
                csvLine.add(matcherSrc.group(3));
                csvLine.add(matcherSrc.group(3).substring(0, 1).toUpperCase() + matcherSrc.group(3).substring(1));

                brOutput.write(String.join(",", csvLine));
                brOutput.write(System.lineSeparator());

            }

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/06 メッセージ
            final KmgToolGenMessageTypes msgType = KmgToolGenMessageTypes.NONE;
            throw new KmgToolException(msgType, e);

        }

        return result;

    }
}
