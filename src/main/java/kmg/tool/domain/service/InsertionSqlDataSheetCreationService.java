package kmg.tool.domain.service;

import java.nio.file.Path;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import kmg.core.infrastructure.types.KmgDbTypes;

/**
 * 挿入ＳＱＬデータシート作成サービスインタフェース<br>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
public interface InsertionSqlDataSheetCreationService extends Runnable {

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param KmgDbTypes
     *                   ＫＭＧＤＢの種類
     * @param inputSheet
     *                   入力シート
     * @param sqlIdMap
     *                   ＳＱＬＩＤマップ
     * @param outputPath
     *                   出力パス
     */
    void initialize(KmgDbTypes KmgDbTypes, Sheet inputSheet, Map<String, String> sqlIdMap, final Path outputPath);

    /**
     * 挿入ＳＱＬを出力する<br>
     */
    void outputInsertionSql();
}
