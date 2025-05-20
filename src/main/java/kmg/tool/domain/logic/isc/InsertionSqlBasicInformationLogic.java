package kmg.tool.domain.logic.isc;

import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import kmg.core.infrastructure.types.KmgDbTypes;

/**
 * 挿入SQL基本情報ロジックインタフェース<br>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
public interface InsertionSqlBasicInformationLogic {

    /** 設定シート名 */
    String SETTING_SHEET_NAME = "設定情報";

    /** 一覧シート名 */
    String LIST_NAME = "一覧";

    /**
     * KMG DBの種類を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return KMG DBの種類
     */
    KmgDbTypes getKmgDbTypes();

    /**
     * SQLIDマップ返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return SQLIdマップ
     */
    Map<String, String> getSqlIdMap();

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param inputWk
     *                入力ワークブック
     */
    void initialize(final Workbook inputWk);
}
