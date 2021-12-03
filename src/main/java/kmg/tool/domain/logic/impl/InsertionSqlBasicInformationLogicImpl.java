package kmg.tool.domain.logic.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import kmg.core.infrastructure.types.DbTypes;
import kmg.tool.domain.logic.InsertionSqlBasicInformationLogic;
import kmg.tool.infrastructure.utils.PoiUtils;

/**
 * 挿入ＳＱＬ基本情報ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class InsertionSqlBasicInformationLogicImpl implements InsertionSqlBasicInformationLogic {

    /** 入力ワークブック */
    private Workbook inputWk;

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param inputWk
     *                入力ワークブック
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final Workbook inputWk) {
        this.inputWk = inputWk;
    }

    /**
     * ＤＢの種類を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return ＤＢの種類
     */
    @Override
    public DbTypes getDbTypes() {
        DbTypes result = null;

        final Sheet wkSheet = this.inputWk.getSheet(InsertionSqlBasicInformationLogic.SETTING_SHEET_NAME);
        final Cell wkCell = PoiUtils.getCell(wkSheet, 0, 1);

        result = DbTypes.getEnum(PoiUtils.getStringValue(wkCell));

        return result;
    }

    /**
     * SQLIDマップ返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return SQLIdマップ
     */
    @Override
    public Map<String, String> getSqlIdMap() {
        final Map<String, String> result = new HashMap<>();

        final Sheet wkSheet = this.inputWk.getSheet(InsertionSqlBasicInformationLogic.LIST_NAME);
        for (int rowIdx = 1; rowIdx <= wkSheet.getLastRowNum(); rowIdx++) {

            // テーブル物理名を取得
            final Cell tablePhysicsCell = PoiUtils.getCell(wkSheet, rowIdx, 2);
            final String tablePhysicsStr = PoiUtils.getStringValue(tablePhysicsCell);

            // SQLIDを取得
            final Cell sqlIdCell = PoiUtils.getCell(wkSheet, rowIdx, 3);
            final String sqlIdStr = PoiUtils.getStringValue(sqlIdCell);

            // マップに追加
            result.put(tablePhysicsStr, sqlIdStr);
        }

        return result;
    }

}
