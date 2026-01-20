package kmg.tool.base.is.application.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseLogMsgTypes;
import kmg.tool.base.is.application.logic.IsDataSheetCreationLogic;
import kmg.tool.base.is.application.service.IslDataSheetCreationService;

/**
 * 挿入SQLデータシート作成サービス<br>
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
public class IsDataSheetCreationServiceImpl implements IslDataSheetCreationService {

    /**
     * ロガー
     *
     * @since 0.2.0
     */
    private final Logger logger;

    /**
     * メッセージソース
     *
     * @since 0.2.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * 挿入SQLデータシート作成ロジック
     *
     * @since 0.2.0
     */
    @Autowired
    private IsDataSheetCreationLogic isDataSheetCreationLogic;

    /**
     * KMG DBの種類
     *
     * @since 0.2.0
     */
    private KmgDbTypes kmgDbTypes;

    /**
     * 入力シート
     *
     * @since 0.2.0
     */
    private Sheet inputSheet;

    /**
     * SQLＩＤマップ
     *
     * @since 0.2.0
     */
    private Map<String, String> sqlIdMap;

    /**
     * 出力パス
     *
     * @since 0.2.0
     */
    private Path outputPath;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public IsDataSheetCreationServiceImpl() {

        this(LoggerFactory.getLogger(IsDataSheetCreationServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param logger
     *               ロガー
     */
    protected IsDataSheetCreationServiceImpl(final Logger logger) {

        this.logger = logger;

    }

    /**
     * 初期化する<br>
     *
     * @since 0.2.0
     *
     * @param kmgDbTypes
     *                   KMG DBの種類
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
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    @Override
    public void outputInsertionSql() throws KmgToolBaseMsgException {

        /* 初期化チェック */
        if (this.inputSheet == null) {

            final KmgToolBaseGenMsgTypes genMsgTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN10006;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolBaseMsgException(genMsgTypes, genMsgArgs);

        }

        this.isDataSheetCreationLogic.initialize(this.kmgDbTypes, this.inputSheet, this.sqlIdMap, this.outputPath);

        /* 出力ファイルのディレクトリの作成 */
        this.isDataSheetCreationLogic.createOutputFileDirectories();

        /* 出力ファイルパスの取得 */
        final Path outputFilePath = this.isDataSheetCreationLogic.getOutputFilePath();

        /* 文字セットを取得 */
        final Charset charset = this.isDataSheetCreationLogic.getCharset();

        try (BufferedWriter bw = Files.newBufferedWriter(outputFilePath, charset)) {

            /* 削除SQLの出力 */
            final String deleteComment = this.isDataSheetCreationLogic.getDeleteComment();
            bw.write(deleteComment);
            bw.newLine();
            final String deleteSql = this.isDataSheetCreationLogic.getDeleteSql();
            bw.write(deleteSql);
            bw.newLine();
            bw.newLine();

            /* 挿入SQLの出力 */
            final String insertComment = this.isDataSheetCreationLogic.getInsertComment();
            bw.write(insertComment);
            bw.newLine();

            for (int rowIdx = 4; rowIdx <= this.inputSheet.getLastRowNum(); rowIdx++) {

                final Row datasRow = this.inputSheet.getRow(rowIdx);

                if (datasRow == null) {

                    break;

                }

                final String datas = this.isDataSheetCreationLogic.getInsertSql(datasRow);
                bw.write(datas);
                bw.write(System.lineSeparator());

            }

        } catch (final IOException e) {

            final KmgToolBaseGenMsgTypes genMsgTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN10003;
            final Object[]               genMsgArgs  = {
                outputFilePath,
            };
            throw new KmgToolBaseMsgException(genMsgTypes, genMsgArgs, e);

        }

    }

    /**
     * 挿入SQL出力を実行する<br>
     *
     * @since 0.2.4
     */
    @Override
    public void run() {

        try {

            this.outputInsertionSql();

        } catch (final KmgToolBaseMsgException e) {

            // ログの出力
            final KmgToolBaseLogMsgTypes logType     = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG10000;
            final Object[]               messageArgs = {};
            final String                 msg         = this.messageSource.getLogMessage(logType, messageArgs);
            this.logger.error(msg, e);

        }

    }

}
