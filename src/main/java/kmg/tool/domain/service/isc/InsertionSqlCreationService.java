package kmg.tool.domain.service.isc;

import java.nio.file.Path;

/**
 * 挿入SQL作成サービスインタフェース<br>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
public interface InsertionSqlCreationService {

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @param inputPath
     *                   入力パス
     * @param outputPath
     *                   出力パス
     * @param threadNum
     *                   スレッド数
     */
    void initialize(final Path inputPath, final Path outputPath, short threadNum);

    /**
     * 挿入SQLを出力する<br>
     */
    void outputInsertionSql();
}
