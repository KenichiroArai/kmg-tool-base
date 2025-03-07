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
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.service.io.AccessorCreationService;
import kmg.tool.domain.service.io.AbstractInputCsvTemplateOutputProcessorService;
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
     * CSVファイルに書き込む。<br>
     * <p>
     * 入力ファイルからCSV形式に変換してCSVファイルに出力する。
     * </p>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    protected boolean writeCsvFile() throws KmgToolException {

        final boolean result = false;

        // CSVデータを格納するリスト
        final List<List<String>> csvData = new ArrayList<>();

        try (final BufferedReader brInput = Files.newBufferedReader(this.getInputPath());
            final BufferedWriter brOutput = Files.newBufferedWriter(this.getCsvPath());) {

            // 読み込んだ行データ
            String line = null;

            while ((line = brInput.readLine()) != null) {

                // 不要な修飾子を削除
                line = line.replace("final", KmgString.EMPTY);
                line = line.replace("static", KmgString.EMPTY);

                // Javadocコメントの正規表現パターン
                final Pattern patternComment = Pattern.compile("/\\*\\* (\\S+)");
                final Matcher matcherComment = patternComment.matcher(line);

                // Javadocコメントか
                if (matcherComment.find()) {
                    // コメントの場合

                    // 新しいCSV行を作成
                    final List<String> csvLine = new ArrayList<>();
                    // Javadocコメントの内容を追加
                    csvLine.add(matcherComment.group(1));
                    // CSVデータに行を追加
                    csvData.add(csvLine);

                    continue;

                }

                // privateフィールド宣言の正規表現パターン
                final Pattern patternSrc = Pattern.compile("private\\s+((\\w|\\[\\]|<|>)+)\\s+(\\w+);");
                final Matcher matcherSrc = patternSrc.matcher(line);

                // privateフィールド宣言ではないか
                if (!matcherSrc.find()) {
                    // 宣言ではないか

                    continue;

                }

                // 最後に追加したCSV行を取得
                final List<String> csvLine = csvData.getLast();

                // フィールドの情報を取得
                final String fieldType            = matcherSrc.group(1);             // 型
                final String fieldName            = matcherSrc.group(3);             // 項目名
                final String capitalizedFieldName = KmgString.capitalize(fieldName); // 先頭大文字項目

                // テンプレートの各カラムに対応する値を設定
                // カラム1：名称（既にJavadocコメントから設定済み）
                // カラム2：型
                csvLine.add(fieldType);
                // カラム3：項目
                csvLine.add(fieldName);
                // カラム4：先頭大文字項目
                csvLine.add(capitalizedFieldName);

                // CSVファイルに行を書き込む
                brOutput.write(KmgDelimiterTypes.COMMA.join(csvLine));
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
