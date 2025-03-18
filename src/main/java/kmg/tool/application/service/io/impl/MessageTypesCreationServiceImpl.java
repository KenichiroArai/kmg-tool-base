package kmg.tool.application.service.io.impl;

import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.foundation.infrastructure.context.KmgMessageSource;
import kmg.tool.application.logic.io.MessageTypesCreationLogic;
import kmg.tool.application.service.io.MessageTypesCreationService;
import kmg.tool.domain.service.io.AbstractIctoProcessorService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * <h2>メッセージの種類作成サービス実装クラス</h2>
 * <p>
 * メッセージの種類を定義するYMLファイルを自動生成するためのサービス実装クラスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Service
public class MessageTypesCreationServiceImpl extends AbstractIctoProcessorService
    implements MessageTypesCreationService {

    /**
     * ロガー
     *
     * @since 1.0.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @since 1.0.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /** メッセージの種類作成ロジック */
    @Autowired
    private MessageTypesCreationLogic messageTypesCreationLogic;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 1.0.0
     */
    public MessageTypesCreationServiceImpl() {

        this(LoggerFactory.getLogger(MessageTypesCreationServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 1.0.0
     *
     * @param logger
     *               ロガー
     */
    protected MessageTypesCreationServiceImpl(final Logger logger) {

        this.logger = logger;

    }

    /**
     * CSVファイルに書き込む。<br>
     * <p>
     * 入力ファイルからCSV形式に変換してCSVファイルに出力する。
     * </p>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    protected boolean writeCsvFile() throws KmgToolException {

        boolean result = false;

        try {

            // 一時CSVファイルを作成
            final Path csvPath = this.createTempCsvFile();

            // メッセージの種類作成ロジックを初期化
            result = this.messageTypesCreationLogic.initialize(this.getInputPath(), csvPath);

            if (!result) {

                this.closeMessageTypesCreationLogic();
                return result;

            }

            // 最初の行を作成
            this.messageTypesCreationLogic.addOneLineOfDataToCsvRows();

            // 入力ファイルを読み込み処理
            while (this.messageTypesCreationLogic.readOneLineOfData()) {

                // 読み込んだデータの処理
                this.processItem();

                // 処理中のデータをクリア
                this.messageTypesCreationLogic.clearProcessingData();

            }

            // CSVファイルに書き込み
            result = this.messageTypesCreationLogic.writeCsvFile();
            this.closeMessageTypesCreationLogic();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/18 ログ
            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.NONE;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            try {

                this.closeMessageTypesCreationLogic();

            } catch (final KmgToolException e1) {

                // ロジッククローズエラーはスローしない
                e1.printStackTrace();

            }

            throw e;

        }

        return result;

    }

    /**
     * メッセージの種類作成ロジックをクローズする。
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private void closeMessageTypesCreationLogic() throws KmgToolException {

        try {

            this.messageTypesCreationLogic.close();

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/18 ログ
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

    }

    /**
     * 項目を処理する。
     *
     * @return true：処理成功、false：処理スキップ
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private boolean processItem() throws KmgToolException {

        boolean result = false;

        try {

            // メッセージの種類定義から項目と項目名に変換する
            final boolean isConvertMessageTypesDefinition
                = this.messageTypesCreationLogic.convertMessageTypesDefinition();

            if (!isConvertMessageTypesDefinition) {

                return result;

            }

            // 新しい行を作成
            this.messageTypesCreationLogic.addOneLineOfDataToCsvRows();

            // 項目を書き込み対象に追加する
            this.messageTypesCreationLogic.addItemToCsvRows();

            // 項目名を書き込み対象に追加する
            this.messageTypesCreationLogic.addItemNameToCsvRows();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/18 ログ
            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.NONE;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        result = true;
        return result;

    }

}
