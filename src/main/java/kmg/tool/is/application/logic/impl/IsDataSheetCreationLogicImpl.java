package kmg.tool.is.application.logic.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgCharsetTypes;
import kmg.core.infrastructure.types.KmgDbDataTypeTypes;
import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.core.infrastructure.utils.KmgListUtils;
import kmg.core.infrastructure.utils.KmgLocalDateTimeUtils;
import kmg.core.infrastructure.utils.KmgLocalDateUtils;
import kmg.fund.infrastructure.utils.KmgPoiUtils;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.is.application.logic.IsDataSheetCreationLogic;

/**
 * 挿入SQLデータシート作成ロジック<br>
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
@Service
public class IsDataSheetCreationLogicImpl implements IsDataSheetCreationLogic {

    /** 削除SQLテンプレート */
    private static final String DELETE_SQL_TEMPLATE = "DELETE FROM %s;"; //$NON-NLS-1$

    /** 挿入SQLテンプレート */
    private static final String INSERT_SQL_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s);"; //$NON-NLS-1$

    /** 無限小 */
    private static final String NEGATIVE_INFINITY = "-infinity"; //$NON-NLS-1$

    /** 無限大 */
    private static final String POSITIVE_INFINITY = "infinity"; //$NON-NLS-1$

    /** シングルクォート付き文字列フォーマット */
    private static final String SINGLE_QUOTED_STRING_FORMAT = "'%s'"; //$NON-NLS-1$

    /** レコード削除コメントフォーマット */
    private static final String DELETE_COMMENT_FORMAT = "-- %sのレコード削除"; //$NON-NLS-1$

    /** レコード挿入コメントフォーマット */
    private static final String INSERT_COMMENT_FORMAT = "-- %sのレコード挿入"; //$NON-NLS-1$

    /** 出力ファイル名フォーマット */
    private static final String OUTPUT_FILENAME_FORMAT = "%s_insert_%s.sql"; //$NON-NLS-1$

    /** KMG DBの種類 */
    private KmgDbTypes kmgDbTypes;

    /** 入力シート */
    private Sheet inputSheet;

    /** SQLＩＤマップ */
    private Map<String, String> sqlIdMap;

    /** 出力パス */
    private Path outputPath;

    /** テーブル論理名 */
    private String tableLogicName;

    /** テーブル物理名 */
    private String tablePhysicsName;

    /** SQLＩＤ */
    private String sqlId;

    /** 出力ファイルパス */
    private Path outputFilePath;

    /** 文字セット */
    private Charset charset;

    /** 削除コメント */
    private String deleteComment;

    /** 削除SQL */
    private String deleteSql;

    /** カラム物理名リスト */
    private List<String> columnPhysicsNameList;

    /** KMG DBデータ型リスト */
    private List<KmgDbDataTypeTypes> dbDataTypeList;

    /** 挿入コメント */
    private String insertComment;

    /**
     * 出力ファイルのディレクトリを作成する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public void createOutputFileDirectories() throws KmgToolMsgException {

        try {

            Files.createDirectories(this.outputPath);

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN10000;
            final Object[]           genMsgArgs  = {
                this.outputPath,
            };
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

        }

    }

    /**
     * 文字セットを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 文字セット
     */
    @Override
    public Charset getCharset() {

        Charset result = null;

        if (this.charset != null) {

            result = this.charset;
            return result;

        }

        switch (this.kmgDbTypes) {

            case NONE:
                break;

            case MYSQL:
            case ORACLE:
            case SQL_SERVER:
                result = KmgCharsetTypes.UTF8.toCharset();
                break;

            case POSTGRE_SQL:
                result = KmgCharsetTypes.MS932.toCharset();
                break;

            default:
                break;

        }

        this.charset = result;
        return result;

    }

    /**
     * カラム数を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return カラム数
     */
    @Override
    public short getColumnNum() {

        final short result = (short) this.getColumnPhysicsNameList().size();
        return result;

    }

    /**
     * カラム物理名リストを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return カラム物理名リスト
     */
    @Override
    public List<String> getColumnPhysicsNameList() {

        List<String> result = null;

        if (KmgListUtils.isNotEmpty(this.columnPhysicsNameList)) {

            result = this.columnPhysicsNameList;
            return result;

        }
        result = new ArrayList<>();

        final Row physicsNameRow = this.inputSheet.getRow(2);

        for (short j = physicsNameRow.getFirstCellNum(); j <= physicsNameRow.getLastCellNum(); j++) {

            final Cell physicsNameCell = physicsNameRow.getCell(j);

            if (KmgPoiUtils.isEmptyCell(physicsNameCell)) {

                break;

            }
            final String physicsName = KmgPoiUtils.getStringValue(physicsNameCell);
            result.add(physicsName);

        }

        this.columnPhysicsNameList = result;
        return result;

    }

    /**
     * 削除コメントを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 削除コメント
     */
    @Override
    public String getDeleteComment() {

        String result = null;

        if (KmgString.isNotEmpty(this.deleteComment)) {

            result = this.deleteComment;
            return result;

        }
        result = String.format(IsDataSheetCreationLogicImpl.DELETE_COMMENT_FORMAT, this.getTableLogicName());
        this.deleteComment = result;
        return result;

    }

    /**
     * 削除SQLを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 削除SQL
     */
    @Override
    public String getDeleteSql() {

        String result = null;

        if (KmgString.isNotEmpty(this.deleteSql)) {

            result = this.deleteSql;
            return result;

        }
        result = String.format(IsDataSheetCreationLogicImpl.DELETE_SQL_TEMPLATE, this.getTablePhysicsName());
        this.deleteSql = result;
        return result;

    }

    /**
     * 挿入コメントを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 挿入コメント
     */
    @Override
    public String getInsertComment() {

        String result = null;

        if (KmgString.isNotEmpty(this.insertComment)) {

            result = this.insertComment;
            return result;

        }
        result = String.format(IsDataSheetCreationLogicImpl.INSERT_COMMENT_FORMAT, this.getTableLogicName());
        this.insertComment = result;
        return result;

    }

    /**
     * 挿入SQLを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param datasRow
     *                 データ行
     *
     * @return 挿入SQL
     */
    @Override
    public String getInsertSql(final Row datasRow) {

        String result = null;

        final List<String> dataList = new ArrayList<>();

        for (short cellNum = datasRow.getFirstCellNum(); cellNum < this.getColumnNum(); cellNum++) {

            final Cell               dataCell      = datasRow.getCell(cellNum);
            final KmgDbDataTypeTypes kmgDbDataType = this.getKmgDbDataTypeList().get(cellNum);

            String       outputData = null;
            final String dataStr    = KmgPoiUtils.getStringValue(dataCell);

            if (KmgString.isEmpty(dataStr)) {

                dataList.add(outputData);
                continue;

            }

            switch (this.kmgDbTypes) {

                case NONE:
                    break;

                case POSTGRE_SQL:
                    outputData = this.getOutputDataForPostgreSql(dataCell, kmgDbDataType);
                    break;

                case MYSQL:
                    break;

                case ORACLE:
                    break;

                case SQL_SERVER:
                    break;

            }

            dataList.add(outputData);

        }

        result = String.format(IsDataSheetCreationLogicImpl.INSERT_SQL_TEMPLATE, this.getTablePhysicsName(),
            KmgDelimiterTypes.COMMA.joinAll(this.getColumnPhysicsNameList()),
            KmgDelimiterTypes.COMMA.joinAll(dataList));

        return result;

    }

    /**
     * KMG DB型リストを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return KMG DB型リスト
     */
    @Override
    public List<KmgDbDataTypeTypes> getKmgDbDataTypeList() {

        List<KmgDbDataTypeTypes> result = null;

        if (this.dbDataTypeList != null) {

            result = this.dbDataTypeList;
            return result;

        }
        result = new ArrayList<>();

        final Row typeRow = this.inputSheet.getRow(3);

        for (short cellNum = typeRow.getFirstCellNum(); cellNum < this.getColumnNum(); cellNum++) {

            final Cell typeCell = typeRow.getCell(cellNum);

            final KmgDbDataTypeTypes type = KmgDbDataTypeTypes.getEnum(KmgPoiUtils.getStringValue(typeCell));
            result.add(type);

        }

        this.dbDataTypeList = result;
        return result;

    }

    /**
     * 出力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 出力ファイルパス
     */
    @Override
    public Path getOutputFilePath() {

        Path result = null;

        if (this.outputFilePath != null) {

            result = this.outputFilePath;
            return result;

        }
        result = Paths.get(this.outputPath.toAbsolutePath().toString(), String
            .format(IsDataSheetCreationLogicImpl.OUTPUT_FILENAME_FORMAT, this.getSqlId(), this.getTablePhysicsName()));
        this.outputFilePath = result;
        return result;

    }

    /**
     * SQLＩＤを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return SQLＩＤ
     */
    @Override
    public String getSqlId() {

        String result = null;

        if (KmgString.isNotEmpty(this.sqlId)) {

            result = this.sqlId;
            return result;

        }
        result = this.sqlIdMap.get(this.getTablePhysicsName());
        this.sqlId = result;
        return result;

    }

    /**
     * テーブル論理名を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return テーブル論理名
     */
    @Override
    public String getTableLogicName() {

        String result = null;

        if (KmgString.isNotEmpty(this.tableLogicName)) {

            result = this.tableLogicName;
            return result;

        }
        result = this.inputSheet.getSheetName();
        this.tableLogicName = result;
        return result;

    }

    /**
     * テーブル物理名を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return テーブル物理名
     */
    @Override
    public String getTablePhysicsName() {

        String result = null;

        if (KmgString.isNotEmpty(this.tablePhysicsName)) {

            result = this.tablePhysicsName;
            return result;

        }
        final Cell wkCell = KmgPoiUtils.getCell(this.inputSheet, 0, 0);
        result = KmgPoiUtils.getStringValue(wkCell);
        this.tablePhysicsName = result;
        return result;

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
     * ＰｏｓｔｇｒｅSQLの出力データを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param dataCell
     *                      データセル
     * @param kmgDbDataType
     *                      KMG DB型の種類
     *
     * @return 出力データ
     */
    @SuppressWarnings("static-method")
    private String getOutputDataForPostgreSql(final Cell dataCell, final KmgDbDataTypeTypes kmgDbDataType) {

        String result = null;

        String outputData = null;

        switch (kmgDbDataType) {

            case NONE:
                // 指定無し
                outputData = KmgPoiUtils.getStringValue(dataCell);
                outputData = String.format(IsDataSheetCreationLogicImpl.SINGLE_QUOTED_STRING_FORMAT, outputData);
                break;

            case INTEGER:
                // 4バイト整数
            case LONG:
                // 8バイト整数
            case SMALLSERIAL:
                // 自動4バイト
            case SERIAL:
                // 自動8バイト
                outputData = String.valueOf((int) dataCell.getNumericCellValue());
                break;

            case FLOAT:
                // 4バイト実数
            case DOUBLE:
                // 8バイト実数
            case BIG_DECIMAL:
                // 8バイト実数
                outputData = String.valueOf(dataCell.getNumericCellValue());
                break;

            case DATE:
                // 日付型
                final String dateStrTmp = KmgPoiUtils.getStringValue(dataCell);
                if (KmgString.equals(IsDataSheetCreationLogicImpl.NEGATIVE_INFINITY, dateStrTmp)) {

                    outputData = dateStrTmp;

                } else if (KmgString.equals(IsDataSheetCreationLogicImpl.POSITIVE_INFINITY, dateStrTmp)) {

                    outputData = dateStrTmp;

                } else {

                    final Date date = dataCell.getDateCellValue();
                    outputData = KmgLocalDateUtils.formatYyyyMmDd(date);

                }
                outputData = String.format(IsDataSheetCreationLogicImpl.SINGLE_QUOTED_STRING_FORMAT, outputData);
                break;

            case TIME:
                // 日時型
                final String dateTimeStrTmp = KmgPoiUtils.getStringValue(dataCell);
                if (KmgString.equals(IsDataSheetCreationLogicImpl.NEGATIVE_INFINITY, dateTimeStrTmp)) {

                    outputData = dateTimeStrTmp;

                } else if (KmgString.equals(IsDataSheetCreationLogicImpl.POSITIVE_INFINITY, dateTimeStrTmp)) {

                    outputData = dateTimeStrTmp;

                } else {

                    final Date date = dataCell.getDateCellValue();
                    outputData = KmgLocalDateTimeUtils.formatYyyyMmDdHhMmSsSss(date);

                }
                outputData = String.format(IsDataSheetCreationLogicImpl.SINGLE_QUOTED_STRING_FORMAT, outputData);
                break;

            case STRING:
                // 文字列型
                outputData = KmgPoiUtils.getStringValue(dataCell);
                outputData = String.format(IsDataSheetCreationLogicImpl.SINGLE_QUOTED_STRING_FORMAT, outputData);
                break;

        }

        result = outputData;
        return result;

    }
}
