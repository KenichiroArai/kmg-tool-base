package kmg.tool.is.application.logic;

import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import kmg.core.infrastructure.types.KmgDbTypes;

/**
 * 挿入SQL基本情報ロジックインタフェース<br>
 * <p>
 * 「Is」は、InsertionSqlの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface IsBasicInformationLogic {

    /**
     * 設定シート名
     *
     * @since 0.1.0
     */
    String SETTING_SHEET_NAME = "設定情報"; //$NON-NLS-1$

    /**
     * 一覧シート名
     *
     * @since 0.1.0
     */
    String LIST_NAME = "一覧"; //$NON-NLS-1$

    /**
     * KMG DBの種類を返す<br>
     *
     * @since 0.1.0
     *
     * @return KMG DBの種類
     */
    KmgDbTypes getKmgDbTypes();

    /**
     * SQLIDマップ返す<br>
     *
     * @return SQLIdマップ
     */
    Map<String, String> getSqlIdMap();

    /**
     * 初期化する<br>
     *
     * @since 0.1.0
     *
     * @param inputWk
     *                入力ワークブック
     */
    void initialize(final Workbook inputWk);
}
