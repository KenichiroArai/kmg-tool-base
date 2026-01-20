package kmg.tool.base.is.application.service;

import java.nio.file.Path;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import kmg.core.infrastructure.types.KmgDbTypes;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;

/**
 * 挿入SQLデータシート作成サービスインタフェース<br>
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
public interface IslDataSheetCreationService extends Runnable {

    /**
     * 初期化する<br>
     *
     * @since 0.2.0
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
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    void outputInsertionSql() throws KmgToolBaseMsgException;
}
