package kmg.tool.is.application.logic.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.fund.infrastructure.utils.KmgPoiUtils;
import kmg.tool.is.application.logic.IsBasicInformationLogic;

/**
 * 挿入SQL基本情報ロジック<br>
 * <p>
 * 「Is」は、InsertionSqlの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 */
@Service
public class IsBasicInformationLogicImpl implements IsBasicInformationLogic {

    /** 入力ワークブック */
    private Workbook inputWk;

    /**
     * KMG DBの種類を返す<br>
     *
     * @author KenichiroArai
     *
     * @since 1.0.0
     *
     * @version 1.0.0
     *
     * @return KMG DBの種類
     */
    @Override
    public KmgDbTypes getKmgDbTypes() {

        KmgDbTypes result = null;

        final Sheet wkSheet = this.inputWk.getSheet(IsBasicInformationLogic.SETTING_SHEET_NAME);
        final Cell  wkCell  = KmgPoiUtils.getCell(wkSheet, 0, 1);

        result = KmgDbTypes.getEnum(KmgPoiUtils.getStringValue(wkCell));

        return result;

    }

    /**
     * SQLIDマップ返す<br>
     *
     * @author KenichiroArai
     *
     * @since 1.0.0
     *
     * @version 1.0.0
     *
     * @return SQLIdマップ
     */
    @Override
    public Map<String, String> getSqlIdMap() {

        final Map<String, String> result = new HashMap<>();

        final Sheet wkSheet = this.inputWk.getSheet(IsBasicInformationLogic.LIST_NAME);

        for (int rowIdx = 1; rowIdx <= wkSheet.getLastRowNum(); rowIdx++) {

            // テーブル物理名を取得
            final Cell   tablePhysicsCell = KmgPoiUtils.getCell(wkSheet, rowIdx, 2);
            final String tablePhysicsStr  = KmgPoiUtils.getStringValue(tablePhysicsCell);

            // SQLIDを取得
            final Cell   sqlIdCell = KmgPoiUtils.getCell(wkSheet, rowIdx, 3);
            final String sqlIdStr  = KmgPoiUtils.getStringValue(sqlIdCell);

            // マップに追加
            result.put(tablePhysicsStr, sqlIdStr);

        }

        return result;

    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     *
     * @since 1.0.0
     *
     * @version 1.0.0
     *
     * @param inputWk
     *                入力ワークブック
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final Workbook inputWk) {

        this.inputWk = inputWk;

    }

}
