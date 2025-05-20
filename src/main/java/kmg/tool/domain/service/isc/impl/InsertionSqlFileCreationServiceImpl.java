package kmg.tool.domain.service.isc.impl;

import java.io.FileInputStream;
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
import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.tool.domain.logic.isc.InsertionSqlBasicInformationLogic;
import kmg.tool.domain.logic.isc.impl.InsertionSqlBasicInformationLogicImpl;
import kmg.tool.domain.service.isc.InsertionSqlDataSheetCreationService;
import kmg.tool.domain.service.isc.InsertionSqlFileCreationService;

/**
 * 挿入SQLファイル作成サービス<br>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
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
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
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

        /* ワークブック読み込み */
        try (final FileInputStream is = new FileInputStream(this.inputPath.toFile());
            final Workbook inputWb = WorkbookFactory.create(is);) {

            final InsertionSqlBasicInformationLogic insertionSqlFileCreationLogic
                = new InsertionSqlBasicInformationLogicImpl();
            insertionSqlFileCreationLogic.initialize(inputWb);

            /* KMG DBの種類を取得 */
            final KmgDbTypes kmgDbTypes = insertionSqlFileCreationLogic.getKmgDbTypes();

            /* SQLＩＤマップ */
            final Map<String, String> sqlIdMap = insertionSqlFileCreationLogic.getSqlIdMap();

            try (ExecutorService service = this.getExecutorService()) {

                for (int i = 0; i < inputWb.getNumberOfSheets(); i++) {

                    final Sheet wkSheet = inputWb.getSheetAt(i);

                    if (KmgString.equals(wkSheet.getSheetName(),
                        InsertionSqlBasicInformationLogic.SETTING_SHEET_NAME)) {

                        continue;

                    }

                    if (KmgString.equals(wkSheet.getSheetName(), InsertionSqlBasicInformationLogic.LIST_NAME)) {

                        continue;

                    }
                    final InsertionSqlDataSheetCreationService insertionSqlDataSheetCreationService
                        = new InsertionSqlDataSheetCreationServiceImpl();
                    insertionSqlDataSheetCreationService.initialize(kmgDbTypes, wkSheet, sqlIdMap, this.outputPath);
                    service.execute(insertionSqlDataSheetCreationService);

                }

            }

        } catch (final EncryptedDocumentException | IOException e) {

            // TODO KenichiroArai 2025/04/25 【挿入SQL作成】：エラー処理
            e.printStackTrace();
            return;

        }

    }

    /**
     * 執行者サービスを取得する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @return 執行者サービス
     */
    private ExecutorService getExecutorService() {

        ExecutorService result;

        if (this.threadNum > 0) {

            result = Executors.newFixedThreadPool(this.threadNum);

        } else {

            result = Executors.newCachedThreadPool();

        }

        return result;

    }

}
