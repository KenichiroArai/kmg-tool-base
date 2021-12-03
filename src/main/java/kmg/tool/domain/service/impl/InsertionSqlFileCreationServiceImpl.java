package kmg.tool.domain.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.DbTypes;
import kmg.tool.domain.logic.InsertionSqlBasicInformationLogic;
import kmg.tool.domain.logic.impl.InsertionSqlBasicInformationLogicImpl;
import kmg.tool.domain.service.InsertionSqlDataSheetCreationService;
import kmg.tool.domain.service.InsertionSqlFileCreationService;

/**
 * 挿入ＳＱＬファイル作成サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class InsertionSqlFileCreationServiceImpl implements InsertionSqlFileCreationService {

    /** 入力パス */
    private Path inputPath;

    /** 出力パス */
    private Path outputPath;

    /** スレッド数 */
    private short threadNum;

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param inputPath
     *                   入力パス
     * @param outputPath
     *                   出力パス
     * @param threadNum
     *                   スレッド数
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final Path inputPath, final Path outputPath, final short threadNum) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.threadNum = threadNum;
    }

    /**
     * 挿入ＳＱＬを出力する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void outputInsertionSql() {

        /* ワークブック読み込み */
        try (final FileInputStream is = new FileInputStream(this.inputPath.toFile());
            final Workbook inputWb = WorkbookFactory.create(is);) {

            final InsertionSqlBasicInformationLogic insertionSqlFileCreationLogic = new InsertionSqlBasicInformationLogicImpl();
            insertionSqlFileCreationLogic.initialize(inputWb);

            /* ＤＢの種類を取得 */
            final DbTypes dbTypes = insertionSqlFileCreationLogic.getDbTypes();

            /* ＳＱＬＩＤマップ */
            final Map<String, String> sqlIdMap = insertionSqlFileCreationLogic.getSqlIdMap();

            ExecutorService service = null;
            try {
                if (this.threadNum > 0) {
                    service = Executors.newFixedThreadPool(this.threadNum);
                } else {
                    service = Executors.newCachedThreadPool();
                }
                for (int i = 0; i < inputWb.getNumberOfSheets(); i++) {
                    final Sheet wkSheet = inputWb.getSheetAt(i);

                    if (KmgString.equals(wkSheet.getSheetName(),
                        InsertionSqlBasicInformationLogic.SETTING_SHEET_NAME)) {
                        continue;
                    }
                    if (KmgString.equals(wkSheet.getSheetName(), InsertionSqlBasicInformationLogic.LIST_NAME)) {
                        continue;
                    }
                    final InsertionSqlDataSheetCreationService insertionSqlDataSheetCreationService = new InsertionSqlDataSheetCreationServiceImpl();
                    insertionSqlDataSheetCreationService.initialize(dbTypes, wkSheet, sqlIdMap, this.outputPath);
                    service.execute(insertionSqlDataSheetCreationService);
                }
            } finally {
                if (service != null) {
                    service.shutdown();
                }
            }

        } catch (final EncryptedDocumentException e) {
            e.printStackTrace();
            return;
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        }
    }

}
