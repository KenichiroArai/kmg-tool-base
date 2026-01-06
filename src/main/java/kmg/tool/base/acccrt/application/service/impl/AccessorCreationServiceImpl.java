package kmg.tool.base.acccrt.application.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.acccrt.application.logic.AccessorCreationLogic;
import kmg.tool.base.acccrt.application.service.AccessorCreationService;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.iito.domain.service.AbstractIitoProcessorService;

/**
 * アクセサ作成サービス<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.2
 */
@Service
public class AccessorCreationServiceImpl extends AbstractIitoProcessorService implements AccessorCreationService {

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
     * アクセサ作成ロジック
     *
     * @since 0.2.0
     */
    @Autowired
    private AccessorCreationLogic accessorCreationLogic;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public AccessorCreationServiceImpl() {

        this(LoggerFactory.getLogger(AccessorCreationServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param logger
     *               ロガー
     */
    protected AccessorCreationServiceImpl(final Logger logger) {

        this.logger = logger;

    }

    /**
     * 中間ファイルの区切り文字を返す。<br>
     * <p>
     * AbstractIitoProcessorServiceのgetIntermediateDelimiter()を実装します。
     * </p>
     *
     * @since 0.2.2
     *
     * @return 中間ファイルの区切り文字
     */
    @Override
    protected KmgDelimiterTypes getIntermediateDelimiter() {

        final KmgDelimiterTypes result = this.accessorCreationLogic.getOutputDelimiter();
        return result;

    }

    /**
     * 中間ファイルに書き込む。<br>
     * <p>
     * 入力ファイルから中間形式に変換して中間ファイルに出力する。
     * </p>
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    protected boolean writeIntermediateFile() throws KmgToolMsgException {

        boolean result = false;

        try {

            /* アクセサ作成ロジックの初期化 */
            this.accessorCreationLogic.initialize(this.getInputPath(), this.getIntermediatePath());

            /* 書き込み対象に行を追加する */
            this.accessorCreationLogic.addOneLineOfDataToRows();

            do {

                /* 1行データを読み込む */
                final boolean isRead = this.readOneLineData();

                if (!isRead) {

                    break;

                }

                /* カラムを追加する */
                final boolean isProcessed = this.processColumns();

                if (!isProcessed) {

                    continue;

                }

                /* 中間ファイルに行を書き込む */
                this.writeIntermediateFileLine();

                /* クリア処理 */
                this.clearAndPrepareNextLine();

            } while (true);

            result = true;

        } finally {

            /* アクセサ作成ロジックのクローズ処理 */
            this.closeAccessorCreationLogic();

        }

        return result;

    }

    /**
     * 1行分の中間を格納するリストにカラム1：名称を追加する。
     *
     * @since 0.2.0
     *
     * @return true：追加した、false：追加していない
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean addNameColumn() throws KmgToolMsgException {

        boolean result = false;

        // Javadocコメントに変換
        final boolean isConvertJavadocComment = this.accessorCreationLogic.convertJavadoc();

        if (!isConvertJavadocComment) {

            return result;

        }

        // カラム1：名称を書き込み対象に追加する。
        this.accessorCreationLogic.addJavadocCommentToRows();

        result = true;
        return result;

    }

    /**
     * 1行分の中間を格納するリストに残りのカラムを追加する。
     *
     * @since 0.2.0
     *
     * @return true：追加した、false：追加していない
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean addRemainingColumns() throws KmgToolMsgException {

        boolean result = false;

        /* 不要な修飾子を削除する */
        this.accessorCreationLogic.removeModifier();

        /* 型、項目名、先頭大文字項目に追加する */

        // フィールド宣言から型、項目名に変換する。
        final boolean isConvertFields = this.accessorCreationLogic.convertFields();

        if (!isConvertFields) {

            return result;

        }

        // テンプレートの各カラムに対応する値をを書き込み対象に追加する
        // カラム2：型
        this.accessorCreationLogic.addTypeToRows();
        // カラム3：項目
        this.accessorCreationLogic.addItemToRows();

        result = true;

        return result;

    }

    /**
     * データをクリアして次の行の準備をする。
     *
     * @since 0.2.0
     */
    private void clearAndPrepareNextLine() {

        // 書き込み対象の行データのリストをクリアする
        this.accessorCreationLogic.clearRows();

        // 処理中のデータをクリアする
        this.accessorCreationLogic.clearProcessingData();

        /* 書き込み対象に行を追加する */
        this.accessorCreationLogic.addOneLineOfDataToRows();

    }

    /**
     * アクセサ作成ロジックをクローズする。
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void closeAccessorCreationLogic() throws KmgToolMsgException {

        try {

            this.accessorCreationLogic.close();

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN01000;
            final Object[]           genMsgArgs  = {};
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

        }

    }

    /**
     * カラムを処理する。
     *
     * @since 0.2.0
     *
     * @return true：処理成功、false：処理スキップ
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean processColumns() throws KmgToolMsgException {

        boolean result = false;

        try {

            // カラム1：名称を追加する
            this.addNameColumn();

            final String javadocComment = this.accessorCreationLogic.getJavadocComment();

            // Javadocコメントが設定されていないか
            if (javadocComment == null) {
                // 設定されていない場合

                // Javadocコメントが先に設定されていないと残りのカラム情報は読み込めないため、処理をスキップさせる
                return result;

            }

            // 残りのカラムを追加する
            final boolean isAddRemainingColumns = this.addRemainingColumns();

            if (!isAddRemainingColumns) {

                return result;

            }

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG01003;
            final Object[]           logMsgArgs  = {};
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        result = true;
        return result;

    }

    /**
     * 1行データを読み込む。
     *
     * @since 0.2.0
     *
     * @return true：読み込み成功、false：読み込み終了
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean readOneLineData() throws KmgToolMsgException {

        boolean result = false;

        try {

            result = this.accessorCreationLogic.readOneLineOfData();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG01004;
            final Object[]           logMsgArgs  = {};
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        return result;

    }

    /**
     * 中間ファイルに行を書き込む。
     *
     * @since 0.2.0
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void writeIntermediateFileLine() throws KmgToolMsgException {

        try {

            this.accessorCreationLogic.writeIntermediateFile();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG01000;
            final Object[]           logMsgArgs  = {};
            final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);
            throw e;

        }

        final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG01001;
        final Object[]           logMsgArgs  = {
            this.accessorCreationLogic.getJavadocComment(), this.accessorCreationLogic.getItem(),
        };
        final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }
}
