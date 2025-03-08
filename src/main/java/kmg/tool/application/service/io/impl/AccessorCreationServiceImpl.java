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

            // 1行分のCSVを格納するリスト
            List<String> csvLine = new ArrayList<>();

            // 読み込んだ行データ
            String line = null;

            while ((line = brInput.readLine()) != null) {

                /* アクセサ作成ロジックの初期化 */
                this.accessorCreationLogic.initialize(line);

                /* カラム1：名称を追加する */
                final boolean addNameColumnFlg = this.addNameColumn(csvLine);

                if (addNameColumnFlg) {

                    continue;

                }

                /* 残りのカラムを追加する */
                final boolean addRemainingColumnsFlg = this.addRemainingColumns(csvLine);

                if (!addRemainingColumnsFlg) {

                    continue;

                }

                /* CSVファイルに行を書き込む */
                brOutput.write(KmgDelimiterTypes.COMMA.join(csvLine));
                brOutput.write(System.lineSeparator());

                csvLine = new ArrayList<>();

            }

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/08 メッセージ KMGTOOL_GEN31003=CSVファイル書き込み処理中にエラーが発生しました。入力ファイル=[{0}], 出力ファイル=[{1}]
            final KmgToolGenMessageTypes msgType = KmgToolGenMessageTypes.NONE;
            throw new KmgToolException(msgType, e);

        }

        return result;

    }

    /**
     * 1行分のCSVを格納するリストにカラム1：名称を追加する。
     *
     * @param csvLine
     *                1行分のCSVを格納するリスト
     *
     * @return true：追加した、false：追加していない
     */
    private boolean addNameColumn(final List<String> csvLine) {

        boolean result = false;

        // Javadocコメントに変換
        final boolean convertJavadocCommentFlg = this.accessorCreationLogic.convertJavadocComment();

        if (!convertJavadocCommentFlg) {

            return result;

        }

        // Javadocコメントから名称を取得
        final String col1Name = this.accessorCreationLogic.getJavadocComment();

        // カラム1：名称
        csvLine.add(col1Name);

        result = true;
        return result;

    }

    /**
     * 1行分のCSVを格納するリストに残りのカラムを追加する。
     *
     * @param csvLine
     *                1行分のCSVを格納するリスト
     *
     * @return true：追加した、false：追加していない
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private boolean addRemainingColumns(final List<String> csvLine) throws KmgToolException {

        boolean result = false;

        /* 不要な修飾子を削除する */
        this.accessorCreationLogic.removeModifier();

        /* 型、項目名、先頭大文字項目に追加する */

        final boolean convertFieldsFlg = this.accessorCreationLogic.convertFields();

        if (!convertFieldsFlg) {

            return result;

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

        result = true;

        return result;

    }
}
