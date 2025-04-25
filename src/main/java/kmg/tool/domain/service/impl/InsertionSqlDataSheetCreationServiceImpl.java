package kmg.tool.domain.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.tool.domain.logic.KmgTlInsertionSqlDataSheetCreationLogic;
import kmg.tool.domain.logic.impl.KmgTlInsertionSqlDataSheetCreationLogicImpl;
import kmg.tool.domain.service.InsertionSqlDataSheetCreationService;

/**
 * 挿入SQLデータシート作成サービス<br>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
public class InsertionSqlDataSheetCreationServiceImpl implements InsertionSqlDataSheetCreationService {

    /** ＫＭＧＤＢの種類 */
    private KmgDbTypes kmgDbTypes;

    /** 入力シート */
    private Sheet inputSheet;

    /** SQLＩＤマップ */
    private Map<String, String> sqlIdMap;

    /** 出力パス */
    private Path outputPath;

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param kmgDbTypes
     *                   ＫＭＧＤＢの種類
     * @param inputSheet
     *                   入力シート
     * @param sqlIdMap
     *                   SQLＩＤマップ
     * @param outputPath
     *                   出力パス
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final KmgDbTypes kmgDbTypes, final Sheet inputSheet, final Map<String, String> sqlIdMap,
        final Path outputPath) {

        this.kmgDbTypes = kmgDbTypes;
        this.inputSheet = inputSheet;
        this.sqlIdMap = sqlIdMap;
        this.outputPath = outputPath;

    }

    /**
     * 挿入SQLを出力する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     */
    @Override
    public void outputInsertionSql() {

        final KmgTlInsertionSqlDataSheetCreationLogic kmgTlInsertionSqlDataSheetCreationLogic
            = new KmgTlInsertionSqlDataSheetCreationLogicImpl();
        kmgTlInsertionSqlDataSheetCreationLogic.initialize(this.kmgDbTypes, this.inputSheet, this.sqlIdMap,
            this.outputPath);

        /* 出力ファイルのディレクトリの作成 */
        try {

            kmgTlInsertionSqlDataSheetCreationLogic.createOutputFileDirectories();

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/04/25 【優先度：低】：例外処理
            e.printStackTrace();
            return;

        }

        /* 出力ファイルパスの取得 */
        final Path outputFilePath = kmgTlInsertionSqlDataSheetCreationLogic.getOutputFilePath();

        /* 文字セットを取得 */
        final Charset charset = kmgTlInsertionSqlDataSheetCreationLogic.getCharset();

        try (BufferedWriter bw = Files.newBufferedWriter(outputFilePath, charset)) {

            /* 削除SQLの出力 */
            final String deleteComment = kmgTlInsertionSqlDataSheetCreationLogic.getDeleteComment();
            bw.write(deleteComment);
            bw.newLine();
            final String deleteSql = kmgTlInsertionSqlDataSheetCreationLogic.getDeleteSql();
            bw.write(deleteSql);
            bw.newLine();
            bw.newLine();

            /* 挿入SQLの出力 */
            final String insertComment = kmgTlInsertionSqlDataSheetCreationLogic.getInsertComment();
            bw.write(insertComment);
            bw.newLine();

            for (int rowIdx = 4; rowIdx <= this.inputSheet.getLastRowNum(); rowIdx++) {

                final Row datasRow = this.inputSheet.getRow(rowIdx);

                if (datasRow == null) {

                    break;

                }

                final String datas = kmgTlInsertionSqlDataSheetCreationLogic.getInsertSql(datasRow);
                bw.write(datas);
                bw.write(System.lineSeparator());

            }

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/04/25 【優先度：低】：例外処理
            e.printStackTrace();
            return;

        }

    }

    /**
     * 挿入SQL出力を実行する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     */
    @Override
    public void run() {

        this.outputInsertionSql();

    }

}
