package kmg.tool.domain.logic.impl;

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

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgCharsetTypes;
import kmg.core.infrastructure.types.KmgDbDataTypeTypes;
import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.core.infrastructure.utils.KmgListUtils;
import kmg.core.infrastructure.utils.KmgLocalDateTimeUtils;
import kmg.core.infrastructure.utils.KmgLocalDateUtils;
import kmg.tool.domain.logic.InsertionSqlDataSheetCreationLogic;
import kmg.tool.infrastructure.utils.PoiUtils;

/**
 * 挿入ＳＱＬデータシート作成ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class InsertionSqlDataSheetCreationLogicImpl implements InsertionSqlDataSheetCreationLogic {

    /** 削除ＳＱＬテンプレート */
    private static final String DELETE_SQL_TEMPLATE = "DELETE FROM %s;";

    /** 挿入ＳＱＬテンプレート */
    private static final String INSERT_SQL_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s);";

    /** ＫＭＧＤＢの種類 */
    private KmgDbTypes kmgDbTypes;

    /** 入力シート */
    private Sheet inputSheet;

    /** ＳＱＬＩＤマップ */
    private Map<String, String> sqlIdMap;

    /** 出力パス */
    private Path outputPath;

    /** テーブル論理名 */
    private String tableLogicName;

    /** テーブル物理名 */
    private String tablePhysicsName;

    /** ＳＱＬＩＤ */
    private String sqlId;

    /** 出力ファイルパス */
    private Path outputFilePath;

    /** 文字セット */
    private Charset charset;

    /** 削除コメント */
    private String deleteComment;

    /** 削除ＳＱＬ */
    private String deleteSql;

    /** カラム数 */
    private short columnNum;

    /** カラム物理名リスト */
    private List<String> columnPhysicsNameList;

    /** ＫＭＧＤＢデータ型リスト */
    private List<KmgDbDataTypeTypes> dbDataTypeList;

    /** 挿入コメント */
    private String insertComment;

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param kmgDbTypes
     *                   ＫＭＧＤＢの種類
     * @param inputSheet
     *                   入力シート
     * @param sqlIdMap
     *                   ＳＱＬＩＤマップ
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
     * テーブル論理名を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
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
     * @sine 1.0.0
     * @version 1.0.0
     * @return テーブル物理名
     */
    @Override
    public String getTablePhysicsName() {
        String result = null;
        if (KmgString.isNotEmpty(this.tablePhysicsName)) {
            result = this.tablePhysicsName;
            return result;
        }
        final Cell wkCell = PoiUtils.getCell(this.inputSheet, 0, 0);
        result = PoiUtils.getStringValue(wkCell);
        this.tablePhysicsName = result;
        return result;
    }

    /**
     * ＳＱＬＩＤを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return ＳＱＬＩＤ
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
     * 出力ファイルのディレクトリを作成する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @throws IOException
     *                     入出力例外
     */
    @Override
    public void createOutputFileDirectories() throws IOException {
        Files.createDirectories(this.outputPath);
    }

    /**
     * 出力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 出力ファイルパス
     */
    @Override
    public Path getOutputFilePath() {
        Path result = null;
        if (this.outputFilePath != null) {
            result = this.outputFilePath;
            return result;
        }
        result = Paths.get(this.outputPath.toAbsolutePath().toString(),
            String.format("%s_insert_%s.sql", this.getSqlId(), this.getTablePhysicsName()));
        this.outputFilePath = result;
        return result;
    }

    /**
     * 削除コメントを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 削除コメント
     */
    @Override
    public String getDeleteComment() {
        String result = null;
        if (KmgString.isNotEmpty(this.deleteComment)) {
            result = this.deleteComment;
            return result;
        }
        result = String.format("-- %sのレコード削除", this.getTableLogicName());
        this.deleteComment = result;
        return result;
    }

    /**
     * 削除ＳＱＬを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 削除ＳＱＬ
     */
    @Override
    public String getDeleteSql() {
        String result = null;
        if (KmgString.isNotEmpty(this.deleteSql)) {
            result = this.deleteSql;
            return result;
        }
        result = String.format(InsertionSqlDataSheetCreationLogicImpl.DELETE_SQL_TEMPLATE, this.getTablePhysicsName());
        this.deleteSql = result;
        return result;
    }

    /**
     * 文字セットを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
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
     * カラム物理名リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
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
            if (PoiUtils.isEmptyCell(physicsNameCell)) {
                break;
            }
            final String physicsName = PoiUtils.getStringValue(physicsNameCell);
            result.add(physicsName);
        }

        this.columnPhysicsNameList = result;
        return result;
    }

    /**
     * カラム数を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return カラム数
     */
    @Override
    public short getColumnNum() {
        final short result = (short) this.getColumnPhysicsNameList().size();
        this.columnNum = result;
        return result;
    }

    /**
     * ＫＭＧＤＢ型リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return ＫＭＧＤＢ型リスト
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

            final KmgDbDataTypeTypes type = KmgDbDataTypeTypes.getEnum(PoiUtils.getStringValue(typeCell));
            result.add(type);
        }

        this.dbDataTypeList = result;
        return result;
    }

    /**
     * 挿入コメントを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 挿入コメント
     */
    @Override
    public String getInsertComment() {
        String result = null;
        if (KmgString.isNotEmpty(this.insertComment)) {
            result = this.insertComment;
            return result;
        }
        result = String.format("-- %sのレコード挿入", this.getTableLogicName());
        this.insertComment = result;
        return result;
    }

    /**
     * 挿入ＳＱＬを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param datasRow
     *                 データ行
     * @return 挿入ＳＱＬ
     */
    @Override
    public String getInsertSql(final Row datasRow) {

        String result = null;

        final List<String> dataList = new ArrayList<>();

        for (short cellNum = datasRow.getFirstCellNum(); cellNum < this.getColumnNum(); cellNum++) {
            final Cell dataCell = datasRow.getCell(cellNum);
            final KmgDbDataTypeTypes kmgDbDataType = this.getKmgDbDataTypeList().get(cellNum);

            String outputData = null;
            final String dataStr = PoiUtils.getStringValue(dataCell);
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
            default:
                break;
            }
            dataList.add(outputData);
        }

        result = String.format(InsertionSqlDataSheetCreationLogicImpl.INSERT_SQL_TEMPLATE, this.getTablePhysicsName(),
            KmgDelimiterTypes.COMMA.joinAll(this.getColumnPhysicsNameList()),
            KmgDelimiterTypes.COMMA.joinAll(dataList));

        return result;
    }

    /**
     * ＰｏｓｔｇｒｅＳＱＬの出力データを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dataCell
     *                      データセル
     * @param kmgDbDataType
     *                      ＫＭＧＤＢ型の種類
     * @return 出力データ
     */
    @SuppressWarnings("static-method")
    private String getOutputDataForPostgreSql(final Cell dataCell, final KmgDbDataTypeTypes kmgDbDataType) {

        String result = null;

        String outputData = null;
        switch (kmgDbDataType) {
        case NONE:
            // 指定無し
            outputData = PoiUtils.getStringValue(dataCell);
            outputData = String.format("'%s'", outputData);
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
            final String dateStrTmp = PoiUtils.getStringValue(dataCell);
            // TODO KenichiroArai 2021/07/15 列挙型
            if (KmgString.equals("-infinity", dateStrTmp)) {
                outputData = dateStrTmp;
            } else if (KmgString.equals("infinity", dateStrTmp)) {
                outputData = dateStrTmp;
            } else {
                final Date date = dataCell.getDateCellValue();
                outputData = KmgLocalDateUtils.formatYyyyMmDd(date);
            }
            outputData = String.format("'%s'", outputData);
            break;
        case TIME:
            // 日時型
            final String dateTimeStrTmp = PoiUtils.getStringValue(dataCell);
            // TODO KenichiroArai 2021/07/15 列挙型
            if (KmgString.equals("-infinity", dateTimeStrTmp)) {
                outputData = dateTimeStrTmp;
            } else if (KmgString.equals("infinity", dateTimeStrTmp)) {
                outputData = dateTimeStrTmp;
            } else {
                final Date date = dataCell.getDateCellValue();
                outputData = KmgLocalDateTimeUtils.formatYyyyMmDdHhMmSsSss(date);
            }
            outputData = String.format("'%s'", outputData);
            break;
        case STRING:
            // 文字列型
            outputData = PoiUtils.getStringValue(dataCell);
            outputData = String.format("'%s'", outputData);
            break;
        }

        result = outputData;
        return result;
    }
}
