package kmg.tool.base.mptf.application.service.impl;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.domain.logic.FileIteratorLogic;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.exception.KmgFundMsgException;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.mptf.application.service.MapTransformService;

/**
 * マッピング変換サービス<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.2
 */
@Service
public class MapTransformServiceImpl implements MapTransformService {

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
     * ファイルイテレーターロジック
     *
     * @since 0.2.0
     */
    @Autowired
    private FileIteratorLogic fileIteratorLogic;

    /**
     * 対象ファイルパス
     *
     * @since 0.2.0
     */
    private Path targetPath;

    /**
     * 対象値と置換値のマッピング
     *
     * @since 0.2.0
     */
    private final Map<String, String> targetValueToReplacementValueMapping;

    /**
     * UUIDと置換値のマッピング
     *
     * @since 0.2.0
     */
    private final Map<String, String> uuidToReplacementValueMapping;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public MapTransformServiceImpl() {

        this(LoggerFactory.getLogger(MapTransformServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param logger
     *               ロガー
     */
    protected MapTransformServiceImpl(final Logger logger) {

        this.logger = logger;
        this.targetValueToReplacementValueMapping = new HashMap<>();
        this.uuidToReplacementValueMapping = new HashMap<>();

    }

    /**
     * 対象ファイルパスを返す。
     *
     * @since 0.2.0
     *
     * @return 対象ファイルパス
     */
    @Override
    public Path getTargetPath() {

        final Path result = this.targetPath;
        return result;

    }

    /**
     * 初期化する
     *
     * @since 0.2.0
     *
     * @param targetPath
     *                                             対象ファイルパス
     * @param targetValueToReplacementValueMapping
     *                                             対象値と置換値のマッピング
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgFundMsgException
     *                             KMG基盤メッセージ例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path targetPath, final Map<String, String> targetValueToReplacementValueMapping)
        throws KmgFundMsgException, KmgToolMsgException {

        boolean result;

        this.targetPath = targetPath;
        this.targetValueToReplacementValueMapping.putAll(targetValueToReplacementValueMapping);

        /* ファイルイテレーターロジックの初期化 */
        this.fileIteratorLogic.initialize(targetPath);

        result = true;
        return result;

    }

    /**
     * 処理する
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgFundMsgException
     *                             KMG基盤メッセージ例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    @Override
    public boolean process() throws KmgFundMsgException, KmgToolMsgException, KmgToolValException {

        boolean result;

        final KmgToolLogMsgTypes startLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG19000;
        final Object[]           startLogMsgArgs  = {};
        final String             startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes, startLogMsgArgs);
        this.logger.debug(startLogMsg);

        /* 準備 */

        // Javaファイルのリストをロードする
        this.fileIteratorLogic.load();

        /* 対象値からUUIDに置き換える */

        // UUIDの合計置換数
        long uuidReplaceCount = 0;

        do {

            uuidReplaceCount += this.replaceTargetValuesWithUuid();

        } while (this.fileIteratorLogic.nextFile());

        // Javaファイルのカレントをリセットする
        this.fileIteratorLogic.resetFileIndex();

        /* UUIDから置換値に置き換える */

        // 置換値の合計置換数
        long replaceValueReplaceCount = 0;

        do {

            replaceValueReplaceCount += this.replaceUuidWithReplacementValues();

        } while (this.fileIteratorLogic.nextFile());

        /* 置換数の確認 */
        if (uuidReplaceCount != replaceValueReplaceCount) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN19004;
            final Object[]           genMsgArgs  = {
                uuidReplaceCount, replaceValueReplaceCount
            };
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs);

        }

        final KmgToolLogMsgTypes endLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG19001;
        final Object[]           endLogMsgArgs  = {
            this.fileIteratorLogic.getFilePathList().size(), uuidReplaceCount,
        };
        final String             endLogMsg      = this.messageSource.getLogMessage(endLogMsgTypes, endLogMsgArgs);
        this.logger.debug(endLogMsg);

        result = true;
        return result;

    }

    /**
     * 対象値をUUIDに一時置換する
     *
     * @since 0.2.0
     *
     * @return 置換数
     *
     * @throws KmgFundMsgException
     *                             KMG基盤メッセージ例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private long replaceTargetValuesWithUuid() throws KmgFundMsgException, KmgToolMsgException {

        long result = 0;

        // ファイルの内容を読み込む
        if (!this.fileIteratorLogic.loadContent()) {

            return result;

        }

        String content = this.fileIteratorLogic.getReadContent();

        // 対象値からUUIDに置換する
        for (final Map.Entry<String, String> entry : this.targetValueToReplacementValueMapping.entrySet()) {

            final String targetValue = entry.getKey();
            final String uuid        = java.util.UUID.randomUUID().toString();

            // 一時的にUUIDに置換
            content = content.replace(targetValue, uuid);

            // UUIDと置換値のマッピングに追加
            this.uuidToReplacementValueMapping.put(uuid, entry.getValue());

            result++;

        }

        // 置換後の内容を設定
        this.fileIteratorLogic.setWriteContent(content);

        // ファイルに書き込む
        this.fileIteratorLogic.writeContent();

        return result;

    }

    /**
     * UUIDを置換値に置換する
     *
     * @since 0.2.0
     *
     * @return 置換数
     *
     * @throws KmgFundMsgException
     *                             KMG基盤メッセージ例外
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private long replaceUuidWithReplacementValues() throws KmgFundMsgException, KmgToolMsgException {

        long result = 0;

        // ファイルの内容を読み込む
        if (!this.fileIteratorLogic.loadContent()) {

            return result;

        }

        String content = this.fileIteratorLogic.getReadContent();

        // UUIDから置換値に置換する
        for (final Map.Entry<String, String> entry : this.uuidToReplacementValueMapping.entrySet()) {

            final String uuid             = entry.getKey();
            final String replacementValue = entry.getValue();

            // UUIDを置換値に置換
            final String originalContent = content;
            content = content.replace(uuid, replacementValue);

            // 置換が発生した場合のみカウント
            if (!originalContent.equals(content)) {

                result++;

            }

        }

        // 置換後の内容を設定
        this.fileIteratorLogic.setWriteContent(content);

        // ファイルに書き込む
        this.fileIteratorLogic.writeContent();

        return result;

    }

}
