package kmg.tool.base.is.application.service.impl;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseGenMsgTypes;
import kmg.tool.base.is.application.service.IsCreationService;
import kmg.tool.base.is.application.service.IsFileCreationService;

/**
 * 挿入SQL作成サービス<br>
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
@Component
public class IsCreationServiceImpl implements IsCreationService {

    /**
     * 入力パス
     *
     * @since 0.2.0
     */
    private Path inputPath;

    /**
     * 出力パス
     *
     * @since 0.2.0
     */
    private Path outputPath;

    /**
     * スレッド数
     *
     * @since 0.2.0
     */
    private short threadNum;

    /**
     * 挿入SQLファイル作成サービス
     *
     * @since 0.2.0
     */
    @Autowired
    private IsFileCreationService isFileCreationService;

    /**
     * デフォルトコンストラクタ
     *
     * @since 0.2.0
     */
    public IsCreationServiceImpl() {

        // 処理なし
    }

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
     * @since 0.2.4
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public void outputInsertionSql() throws KmgToolBaseMsgException {

        // 初期化チェック
        if (this.inputPath == null) {

            final KmgToolBaseGenMsgTypes genMsgTypes = KmgToolBaseGenMsgTypes.KMGTOOLBASE_GEN08000;
            final Object[]           genMsgArgs  = {};
            throw new KmgToolBaseMsgException(genMsgTypes, genMsgArgs);

        }

        this.isFileCreationService.initialize(this.inputPath, this.outputPath, this.threadNum);
        this.isFileCreationService.outputInsertionSql();

    }

}
