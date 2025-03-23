package kmg.tool.domain.logic;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import kmg.core.infrastructure.types.KmgDbDataTypeTypes;
import kmg.core.infrastructure.types.KmgDbTypes;

/**
 * ＫＭＧツール挿入ＳＱＬデータシート作成ロジックインタフェース<br>
 *
 * @author KenichiroArai
 * 
 * @sine 1.0.0
 * 
 * @version 1.0.0
 */
public interface KmgTlInsertionSqlDataSheetCreationLogic {

    /**
     * 出力ファイルのディレクトリを作成する<br>
     *
     * @author KenichiroArai
     * 
     * @sine 1.0.0
     * 
     * @version 1.0.0
     * 
     * @throws IOException
     *                     入出力例外
     */
    void createOutputFileDirectories() throws IOException;

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
    Charset getCharset();

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
    short getColumnNum();

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
    List<String> getColumnPhysicsNameList();

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
    String getDeleteComment();

    /**
     * 削除ＳＱＬを返す<br>
     *
     * @author KenichiroArai
     * 
     * @sine 1.0.0
     * 
     * @version 1.0.0
     * 
     * @return 削除ＳＱＬ
     */
    String getDeleteSql();

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
    String getInsertComment();

    /**
     * 挿入ＳＱＬを返す<br>
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
     * @return 挿入ＳＱＬ
     */
    String getInsertSql(final Row datasRow);

    /**
     * ＫＭＧＤＢデータ型リストを返す<br>
     *
     * @author KenichiroArai
     * 
     * @sine 1.0.0
     * 
     * @version 1.0.0
     * 
     * @return ＤＢデータ型リスト
     */
    List<KmgDbDataTypeTypes> getKmgDbDataTypeList();

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
    Path getOutputFilePath();

    /**
     * ＳＱＬＩＤを返す<br>
     *
     * @author KenichiroArai
     * 
     * @sine 1.0.0
     * 
     * @version 1.0.0
     * 
     * @return ＳＱＬＩＤ
     */
    String getSqlId();

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
    String getTableLogicName();

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
    String getTablePhysicsName();

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
     *                   ＫＭＧＤＢの種類
     * @param inputSheet
     *                   入力シート
     * @param sqlIdMap
     *                   ＳＱＬＩＤマップ
     * @param outputPath
     *                   出力パス
     */
    void initialize(KmgDbTypes kmgDbTypes, Sheet inputSheet, Map<String, String> sqlIdMap, final Path outputPath);
}
