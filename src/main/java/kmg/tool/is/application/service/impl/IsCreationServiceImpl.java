package kmg.tool.is.application.service.impl;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@Component
public class IsCreationServiceImpl implements IsCreationService {

    /**
     * 入力パス
     *
     * @since 0.1.0
     */
    private Path inputPath;

    /**
     * 出力パス
     *
     * @since 0.1.0
     */
    private Path outputPath;

    /**
     * スレッド数
     *
     * @since 0.1.0
     */
    private short threadNum;

    /**
     * 挿入SQLファイル作成サービス
     *
     * @since 0.1.0
     */
    @Autowired
    private IsFileCreationService isFileCreationService;

    /**
     * 初期化する<br>
     *
     * @since 0.1.0
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
     * @since 0.1.0
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

        this.isFileCreationService.initialize(this.inputPath, this.outputPath, this.threadNum);
        this.isFileCreationService.outputInsertionSql();

    }

}
