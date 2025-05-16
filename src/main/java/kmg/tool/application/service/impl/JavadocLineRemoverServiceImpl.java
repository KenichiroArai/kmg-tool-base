package kmg.tool.application.service.impl;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.application.logic.JavadocLineRemoverLogic;
import kmg.tool.application.service.JavadocLineRemoverService;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolLogMsgTypes;

/**
 * Javadoc行削除サービス<br>
 *
 * @author KenichiroArai
 */
@Service
public class JavadocLineRemoverServiceImpl implements JavadocLineRemoverService {

    /**
     * ロガー
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /** Javadoc行削除ロジック */
    @Autowired
    private JavadocLineRemoverLogic javadocLineRemoverLogic;

    /** 入力ファイルのパス */
    private Path inputPath;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    public JavadocLineRemoverServiceImpl() {

        this(LoggerFactory.getLogger(JavadocLineRemoverServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
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
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                  入力ファイルのパス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath) throws KmgToolMsgException {

        final boolean result = false;

        this.inputPath = inputPath;

        return result;

    }

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean process() throws KmgToolMsgException {

        final boolean result = true;

        /* 入力からパスと行番号のマップ（ファイルパスと行番号のリスト、リストは行番号の降順）を取得 */
        final var inputMap = this.javadocLineRemoverLogic.getInputMap(this.inputPath);
        System.out.println(inputMap.toString());

        /* Javadoc行を削除する */
        final int lineCount = this.javadocLineRemoverLogic.deleteJavadocLines(inputMap);

        /* 情報の出力 */
        // TODO KenichiroArai 2025/05/17 メッセージ。削除した行数=[{0}]
        final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.NONE;
        final Object[]           logMsgArgs  = {
            lineCount,
        };
        final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

        return result;

    }
}
