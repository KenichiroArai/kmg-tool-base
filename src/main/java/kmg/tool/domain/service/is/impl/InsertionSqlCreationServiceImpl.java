package kmg.tool.domain.service.is.impl;

import java.nio.file.Path;

import kmg.tool.domain.service.is.InsertionSqlCreationService;
import kmg.tool.domain.service.is.InsertionSqlFileCreationService;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * 挿入SQL作成サービス<br>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
public class InsertionSqlCreationServiceImpl implements InsertionSqlCreationService {

    /** 入力パス */
    private Path inputPath;

    /** 出力パス */
    private Path outputPath;

    /** スレッド数 */
    private short threadNum;

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
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final Path inputPath, final Path outputPath, final short threadNum) {

        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.threadNum = threadNum;

    }

    /**
     * 挿入SQLを出力する<br>
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public void outputInsertionSql() throws KmgToolMsgException {

        final InsertionSqlFileCreationService insertionSqlFileCreationService
            = new InsertionSqlFileCreationServiceImpl();
        insertionSqlFileCreationService.initialize(this.inputPath, this.outputPath, this.threadNum);
        insertionSqlFileCreationService.outputInsertionSql();

    }

}
