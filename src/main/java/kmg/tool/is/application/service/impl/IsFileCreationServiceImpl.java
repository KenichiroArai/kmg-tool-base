package kmg.tool.is.application.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.EmptyFileException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.is.application.logic.IsBasicInformationLogic;
import kmg.tool.is.application.service.IsFileCreationService;
import kmg.tool.is.application.service.IslDataSheetCreationService;

/**
 * 挿入SQLファイル作成サービス<br>
 * <p>
 * 「Is」は、InsertionSqlの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
public class IsFileCreationServiceImpl implements IsFileCreationService {

    /** 挿入SQLデータシート作成サービス */
    @Autowired
    private IslDataSheetCreationService islDataSheetCreationService;

    /** 挿入SQL基本情報ロジック */
    @Autowired
    private IsBasicInformationLogic insertionSqlFileCreationLogic;

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
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public void outputInsertionSql() throws KmgToolMsgException {

        /* ワークブック読み込み */
        try (final FileInputStream is = new FileInputStream(this.inputPath.toFile());) {

            try (final Workbook inputWb = WorkbookFactory.create(is);) {

                this.processWorkbook(inputWb);

            } catch (final EmptyFileException e) {

                // TODO KenichiroArai 2025/07/20 空ファイルの場合
                final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.NONE;
                final Object[]           genMsgArgs  = {
                    this.inputPath,
                };
                throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

            } catch (final EncryptedDocumentException e) {

                // TODO KenichiroArai 2025/07/20 暗号化失敗
                final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.NONE;
                final Object[]           genMsgArgs  = {
                    this.inputPath,
                };
                throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

            } catch (final IOException e) {

                final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN10001;
                final Object[]           genMsgArgs  = {
                    this.inputPath,
                };
                throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

            }

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN10002;
            final Object[]           genMsgArgs  = {
                this.inputPath,
            };
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

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
            return result;

        }

        result = Executors.newCachedThreadPool();

        return result;

    }

    /**
     * ワークブックを処理する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param inputWb
     *                入力ワークブック
     */
    private void processWorkbook(final Workbook inputWb) {

        this.insertionSqlFileCreationLogic.initialize(inputWb);

        /* KMG DBの種類を取得 */
        final KmgDbTypes kmgDbTypes = this.insertionSqlFileCreationLogic.getKmgDbTypes();

        /* SQL IDマップ */
        final Map<String, String> sqlIdMap = this.insertionSqlFileCreationLogic.getSqlIdMap();

        try (ExecutorService service = this.getExecutorService()) {

            for (int i = 0; i < inputWb.getNumberOfSheets(); i++) {

                final Sheet wkSheet = inputWb.getSheetAt(i);

                if (KmgString.equals(wkSheet.getSheetName(), IsBasicInformationLogic.SETTING_SHEET_NAME)) {

                    continue;

                }

                if (KmgString.equals(wkSheet.getSheetName(), IsBasicInformationLogic.LIST_NAME)) {

                    continue;

                }
                this.islDataSheetCreationService.initialize(kmgDbTypes, wkSheet, sqlIdMap, this.outputPath);
                service.execute(this.islDataSheetCreationService);

            }

        }

    }

}
