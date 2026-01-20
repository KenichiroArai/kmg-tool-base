package kmg.tool.base.is.application.service.impl;

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
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseGenMsgTypes;
import kmg.tool.base.is.application.logic.IsBasicInformationLogic;
import kmg.tool.base.is.application.service.IsFileCreationService;
import kmg.tool.base.is.application.service.IslDataSheetCreationService;

/**
 * 挿入SQLファイル作成サービス<br>
 * <p>
 * 「Is」は、InsertionSqlの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
@Service
public class IsFileCreationServiceImpl implements IsFileCreationService {

    /**
     * 挿入SQLデータシート作成サービス
     *
     * @since 0.2.0
     */
    @Autowired
    private IslDataSheetCreationService islDataSheetCreationService;

    /**
     * 挿入SQL基本情報ロジック
     *
     * @since 0.2.0
     */
    @Autowired
    private IsBasicInformationLogic insertionSqlFileCreationLogic;

    /**
     * 入力パス
     *
     * @since 0.2.0
     */
    private Path inputPath;

    /**
     * 出力パス
     *
     * @since 0.2.0
     */
    private Path outputPath;

    /**
     * スレッド数
     *
     * @since 0.2.0
     */
    private short threadNum;

    /**
     * デフォルトコンストラクタ
     *
     * @since 0.2.0
     */
    public IsFileCreationServiceImpl() {

        // 処理なし
    }

    /**
     * 初期化する<br>
     *
     * @since 0.2.0
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
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    @Override
    public void outputInsertionSql() throws KmgToolBaseMsgException {

        /* ワークブック読み込み */
        try (final FileInputStream is = new FileInputStream(this.inputPath.toFile());) {

            try (final Workbook inputWb = WorkbookFactory.create(is);) {

                this.processWorkbook(inputWb);

            } catch (final EmptyFileException e) {

                final KmgToolBaseGenMsgTypes genMsgTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN10004;
                final Object[]               genMsgArgs  = {
                    this.inputPath,
                };
                throw new KmgToolBaseMsgException(genMsgTypes, genMsgArgs, e);

            } catch (final EncryptedDocumentException e) {

                final KmgToolBaseGenMsgTypes genMsgTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN10001;
                final Object[]               genMsgArgs  = {
                    this.inputPath,
                };
                throw new KmgToolBaseMsgException(genMsgTypes, genMsgArgs, e);

            } catch (final IOException e) {

                final KmgToolBaseGenMsgTypes genMsgTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN10005;
                final Object[]               genMsgArgs  = {
                    this.inputPath,
                };
                throw new KmgToolBaseMsgException(genMsgTypes, genMsgArgs, e);

            }

        } catch (final IOException e) {

            final KmgToolBaseGenMsgTypes genMsgTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN10002;
            final Object[]               genMsgArgs  = {
                this.inputPath,
            };
            throw new KmgToolBaseMsgException(genMsgTypes, genMsgArgs, e);

        }

    }

    /**
     * 執行者サービスを取得する<br>
     *
     * @since 0.2.0
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
     * @since 0.2.0
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
