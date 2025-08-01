package kmg.tool.is.application.service;

import java.nio.file.Path;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;

/**
 * 挿入SQLデータシート作成サービスインタフェース<br>
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
public interface IslDataSheetCreationService extends Runnable {

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     *
     * @since 1.0.0
     *
     * @version 1.0.0
     *
     * @param KmgDbTypes
     *                   KMG DBの種類
     * @param inputSheet
     *                   入力シート
     * @param sqlIdMap
     *                   SQLＩＤマップ
     * @param outputPath
     *                   出力パス
     */
    void initialize(KmgDbTypes KmgDbTypes, Sheet inputSheet, Map<String, String> sqlIdMap, final Path outputPath);

    /**
     * 挿入SQLを出力する<br>
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    void outputInsertionSql() throws KmgToolMsgException;
}
