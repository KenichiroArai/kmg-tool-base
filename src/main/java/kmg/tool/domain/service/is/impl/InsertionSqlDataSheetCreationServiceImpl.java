package kmg.tool.domain.service.is.impl;

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
import kmg.tool.domain.logic.is.InsertionSqlDataSheetCreationLogic;
import kmg.tool.domain.service.is.InsertionSqlDataSheetCreationService;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;
import kmg.tool.infrastructure.type.msg.KmgToolLogMsgTypes;

/**
 * 挿入SQLデータシート作成サービス<br>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
@Service
public class InsertionSqlDataSheetCreationServiceImpl implements InsertionSqlDataSheetCreationService {

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /** メッセージソース */
    @Autowired
    private KmgMessageSource messageSource;

    /** 挿入SQLデータシート作成ロジック */
    @Autowired
    private InsertionSqlDataSheetCreationLogic insertionSqlDataSheetCreationLogic;

    /** KMG DBの種類 */
    private KmgDbTypes kmgDbTypes;

    /** 入力シート */
    private Sheet inputSheet;

    /** SQLＩＤマップ */
    private Map<String, String> sqlIdMap;

    /** 出力パス */
    private Path outputPath;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     */
    public InsertionSqlDataSheetCreationServiceImpl() {

        this(LoggerFactory.getLogger(InsertionSqlDataSheetCreationServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *               ロガー
     */
    protected InsertionSqlDataSheetCreationServiceImpl(final Logger logger) {

        this.logger = logger;

    }

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
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public void outputInsertionSql() throws KmgToolMsgException {

        this.insertionSqlDataSheetCreationLogic.initialize(this.kmgDbTypes, this.inputSheet, this.sqlIdMap,
            this.outputPath);

        /* 出力ファイルのディレクトリの作成 */
        this.insertionSqlDataSheetCreationLogic.createOutputFileDirectories();

        /* 出力ファイルパスの取得 */
        final Path outputFilePath = this.insertionSqlDataSheetCreationLogic.getOutputFilePath();

        /* 文字セットを取得 */
        final Charset charset = this.insertionSqlDataSheetCreationLogic.getCharset();

        try (BufferedWriter bw = Files.newBufferedWriter(outputFilePath, charset)) {

            /* 削除SQLの出力 */
            final String deleteComment = this.insertionSqlDataSheetCreationLogic.getDeleteComment();
            bw.write(deleteComment);
            bw.newLine();
            final String deleteSql = this.insertionSqlDataSheetCreationLogic.getDeleteSql();
            bw.write(deleteSql);
            bw.newLine();
            bw.newLine();

            /* 挿入SQLの出力 */
            final String insertComment = this.insertionSqlDataSheetCreationLogic.getInsertComment();
            bw.write(insertComment);
            bw.newLine();

            for (int rowIdx = 4; rowIdx <= this.inputSheet.getLastRowNum(); rowIdx++) {

                final Row datasRow = this.inputSheet.getRow(rowIdx);

                if (datasRow == null) {

                    break;

                }

                final String datas = this.insertionSqlDataSheetCreationLogic.getInsertSql(datasRow);
                bw.write(datas);
                bw.write(System.lineSeparator());

            }

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN12010;
            final Object[]           genMsgArgs  = {
                outputFilePath,
            };
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

        }

    }

    /**
     * 挿入SQL出力を実行する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     */
    @Override
    public void run() {

        try {

            this.outputInsertionSql();

        } catch (final KmgToolMsgException e) {

            // ログの出力
            final KmgToolLogMsgTypes logType     = KmgToolLogMsgTypes.KMGTOOL_LOG12007;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getLogMessage(logType, messageArgs);
            this.logger.error(msg, e);

        }

    }

}
