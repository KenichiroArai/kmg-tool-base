package kmg.tool.base.is.application.service;

import java.nio.file.Path;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;

/**
 * 挿入SQLファイル作成サービスインタフェース<br>
 * <p>
 * 「Is」は、InsertionSqlの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface IsFileCreationService {

    /**
     * 初期化する<br>
     *
     * @since 0.2.0
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
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    void outputInsertionSql() throws KmgToolMsgException;
}
