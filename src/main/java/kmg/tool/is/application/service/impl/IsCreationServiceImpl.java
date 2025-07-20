package kmg.tool.is.application.service.impl;

import java.nio.file.Path;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.is.application.service.IsCreationService;
import kmg.tool.is.application.service.IsFileCreationService;

/**
 * 挿入SQL作成サービス<br>
 * <p>
 * 「Is」は、InsertionSqlの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
public class IsCreationServiceImpl implements IsCreationService {

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

        // 初期化チェック
        if (this.inputPath == null) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN08000;
            final Object[]           genMsgArgs  = {};
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs);

        }

        final IsFileCreationService isFileCreationService = new IsFileCreationServiceImpl();
        isFileCreationService.initialize(this.inputPath, this.outputPath, this.threadNum);
        isFileCreationService.outputInsertionSql();

    }

}
