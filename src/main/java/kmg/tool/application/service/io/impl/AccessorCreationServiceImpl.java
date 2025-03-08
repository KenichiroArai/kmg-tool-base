package kmg.tool.application.service.io.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.logic.io.AccessorCreationLogic;
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

    /** アクセサ作成ロジック */
    @Autowired
    private AccessorCreationLogic accessorCreationLogic;

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

        try (final BufferedReader brInput = Files.newBufferedReader(this.getInputPath());
            final BufferedWriter brOutput = Files.newBufferedWriter(this.getCsvPath());) {

            // 読み込んだ行データ
            String line = null;

            // 1行分のCSVを格納するリスト
            List<String> csvLine = new ArrayList<>();

            while ((line = brInput.readLine()) != null) {

                /* アクセサ作成ロジックの初期化 */
                this.accessorCreationLogic.initialize(line);

                /* カラム1：名称を追加する */

                // Javadocコメントに変換
                final boolean convertJavadocCommentFlg = this.accessorCreationLogic.convertJavadocComment();

                if (convertJavadocCommentFlg) {

                    // Javadocコメントから名称を取得
                    final String col1Name = this.accessorCreationLogic.getJavadocComment();

                    System.out.println(col1Name);

                    // カラム1：名称
                    csvLine.add(col1Name);

                }

                /* 残りのカラムを追加する */

                // 不要な修飾子を削除
                this.accessorCreationLogic.removeModifier();

                final boolean convertFieldsFlg = this.accessorCreationLogic.convertFields();

                System.out.println(convertFieldsFlg);

                if (!convertFieldsFlg) {

                    continue;

                }

                // フィールドの情報を取得
                final String col2Type            = this.accessorCreationLogic.getTyep();            // 型
                final String col3Item            = this.accessorCreationLogic.getItem();            // 項目名
                final String col3CapitalizedItem = this.accessorCreationLogic.getCapitalizedItem(); // 先頭大文字項目

                // テンプレートの各カラムに対応する値を設定
                // カラム2：型
                csvLine.add(col2Type);
                // カラム3：項目
                csvLine.add(col3Item);
                // カラム4：先頭大文字項目
                csvLine.add(col3CapitalizedItem);

                // CSVファイルに行を書き込む
                brOutput.write(KmgDelimiterTypes.COMMA.join(csvLine));
                brOutput.write(System.lineSeparator());

                csvLine = new ArrayList<>();

            }

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/06 メッセージ
            final KmgToolGenMessageTypes msgType = KmgToolGenMessageTypes.NONE;
            throw new KmgToolException(msgType, e);

        }

        return result;

    }
}
