package kmg.tool.mptf.application.service.impl;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.jdts.application.logic.JdtsIoLogic;
import kmg.tool.mptf.application.service.MapTransformService;

/**
 * マッピング変換サービス<br>
 *
 * @author KenichiroArai
 */
@Service
public class MapTransformServiceImpl implements MapTransformService {

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

    /**
     * Javadocタグ設定の入出力ロジック
     */
    @Autowired
    private JdtsIoLogic jdtsIoLogic;

    /**
     * 対象ファイルパス
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    private Path targetPath;

    /** マッピング */
    private final Map<String, String> mapping;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    public MapTransformServiceImpl() {

        this(LoggerFactory.getLogger(MapTransformServiceImpl.class));

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
    protected MapTransformServiceImpl(final Logger logger) {

        this.logger = logger;
        this.mapping = new HashMap<>();

    }

    /**
     * 対象ファイルパスを返す。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
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
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param targetPath
     *                   対象ファイルパス
     * @param mapping
     *                   マッピング
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path targetPath, final Map<String, String> mapping) throws KmgToolMsgException {

        boolean result = false;

        this.targetPath = targetPath;
        this.mapping.putAll(mapping);

        /* Javadocタグ設定の入出力ロジックの初期化 */
        this.jdtsIoLogic.initialize(targetPath);

        result = true;
        return result;

    }

    /**
     * 処理する
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    @Override
    public boolean process() throws KmgToolMsgException, KmgToolValException {

        boolean result = false;

        // TODO KenichiroArai 2025/07/10 メッセージ
        final KmgToolLogMsgTypes startLogMsgTypes = KmgToolLogMsgTypes.NONE;
        final Object[]           startLogMsgArgs  = {};
        final String             startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes, startLogMsgArgs);
        this.logger.debug(startLogMsg);

        /* 準備 */

        // Javaファイルのリストをロードする
        this.jdtsIoLogic.load();

        /* 次のファイルがあるまで置換する */

        // 合計置換数
        long totalReplaceCount = 0;

        do {

            totalReplaceCount += this.processFile();

        } while (this.jdtsIoLogic.nextFile());

        // TODO KenichiroArai 2025/07/10 メッセージ
        final KmgToolLogMsgTypes endLogMsgTypes = KmgToolLogMsgTypes.NONE;
        final Object[]           endLogMsgArgs  = {
            this.jdtsIoLogic.getFilePathList().size(), totalReplaceCount,
        };
        final String             endLogMsg      = this.messageSource.getLogMessage(endLogMsgTypes, endLogMsgArgs);
        this.logger.debug(endLogMsg);

        result = true;
        return result;

    }

    /**
     * ファイルを処理する
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 置換数
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private long processFile() throws KmgToolMsgException {

        long result = 0;

        // ファイルの内容を読み込む
        if (!this.jdtsIoLogic.loadContent()) {

            return result;

        }

        String content = this.jdtsIoLogic.getReadContent();

        // 対象値からUUIDに置換する
        for (final Map.Entry<String, String> entry : this.mapping.entrySet()) {

            final String targetValue = entry.getKey();
            final String uuid        = java.util.UUID.randomUUID().toString();

            // 一時的にUUIDに置換
            content = content.replace(targetValue, uuid);
            result++;

        }

        // UUIDから置換値に置換する
        for (final Map.Entry<String, String> entry : this.mapping.entrySet()) {

            final String targetValue      = entry.getKey();
            final String replacementValue = entry.getValue();

            // UUIDを置換値に置換
            content = content.replace(targetValue, replacementValue);

        }

        // 置換後の内容を設定
        this.jdtsIoLogic.setWriteContent(content);

        // ファイルに書き込む
        this.jdtsIoLogic.writeContent();

        return result;

    }

}
