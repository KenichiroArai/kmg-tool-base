package kmg.tool.base.jdocr.service.impl;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolBaseLogMsgTypes;
import kmg.tool.base.jdocr.application.logic.JavadocLineRemoverLogic;
import kmg.tool.base.jdocr.service.JavadocLineRemoverService;

/**
 * Javadoc行削除サービス<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
@Service
public class JavadocLineRemoverServiceImpl implements JavadocLineRemoverService {

    /**
     * ロガー
     *
     * @since 0.2.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @since 0.2.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * Javadoc行削除ロジック
     *
     * @since 0.2.0
     */
    @Autowired
    private JavadocLineRemoverLogic javadocLineRemoverLogic;

    /**
     * 入力ファイルのパス
     *
     * @since 0.2.0
     */
    private Path inputPath;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public JavadocLineRemoverServiceImpl() {

        this(LoggerFactory.getLogger(JavadocLineRemoverServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param logger
     *               ロガー
     */
    protected JavadocLineRemoverServiceImpl(final Logger logger) {

        this.logger = logger;

    }

    /**
     * 初期化する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                  入力ファイルのパス
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath) throws KmgToolBaseMsgException {

        final boolean result = false;

        this.inputPath = inputPath;

        return result;

    }

    /**
     * 処理する
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean process() throws KmgToolBaseMsgException {

        final boolean result = true;

        /* 入力からパスと行番号のマップ（ファイルパスと行番号のリスト、リストは行番号の降順）を取得 */
        final var inputMap = this.javadocLineRemoverLogic.getInputMap(this.inputPath);
        System.out.println(inputMap.toString());

        /* Javadoc行を削除する */
        final int lineCount = this.javadocLineRemoverLogic.deleteJavadocLines(inputMap);

        /* ログの出力 */
        final KmgToolBaseLogMsgTypes logMsgTypes = KmgToolBaseLogMsgTypes.KMGTOOLBASE_LOG12000;
        final Object[]           logMsgArgs  = {
            lineCount,
        };
        final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

        return result;

    }
}
