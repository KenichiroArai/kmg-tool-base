package kmg.tool.application.service.jdts.impl;

import java.nio.file.Path;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.exception.KmgFundMsgException;
import kmg.fund.infrastructure.utils.KmgYamlUtils;
import kmg.tool.application.logic.jdts.JdtsIoLogic;
import kmg.tool.application.model.jdts.JdtsCodeModel;
import kmg.tool.application.model.jdts.JdtsConfigsModel;
import kmg.tool.application.model.jdts.imp.JdtsCodeModelImpl;
import kmg.tool.application.model.jdts.imp.JdtsConfigsModelImpl;
import kmg.tool.application.service.jdts.JdtsReplService;
import kmg.tool.application.service.jdts.JdtsService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * Javadocタグ設定サービス<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 */
@Service
public class JdtsServiceImpl implements JdtsService {

    /**
     * ロガー
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * Javadocタグ設定の構成モデル
     */
    private JdtsConfigsModel jdtsConfigsModel;

    /**
     * Javadocタグ設定の入出力ロジック
     */
    @Autowired
    private JdtsIoLogic jdtsIoLogic;

    /**
     * Javadocタグ設定の入出力サービス
     */
    @Autowired
    private JdtsReplService jdtsReplService;

    /**
     * 対象ファイルパス
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    private Path targetPath;

    /** 定義ファイルのパス */
    private Path definitionPath;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    public JdtsServiceImpl() {

        this(LoggerFactory.getLogger(JdtsServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @param logger
     *               ロガー
     */
    protected JdtsServiceImpl(final Logger logger) {

        this.logger = logger;

    }

    /**
     * 定義ファイルのパスを返す。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return 定義ファイルのパス
     */
    @Override
    public Path getDefinitionPath() {

        final Path result = this.definitionPath;
        return result;

    }

    /**
     * 対象ファイルパスを返す。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
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
     * @version 0.1.0
     *
     * @param targetPath
     *                       対象ファイルパス
     * @param definitionPath
     *                       定義ファイルのパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path targetPath, final Path definitionPath) throws KmgToolMsgException {

        boolean result = false;

        this.targetPath = targetPath;
        this.definitionPath = definitionPath;

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
     * @version 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    @Override
    public boolean process() throws KmgToolMsgException {

        boolean result = false;

        final KmgToolLogMessageTypes startLogMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31019;
        final Object[]               startLogMsgArgs  = {};
        final String                 startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes,
            startLogMsgArgs);
        this.logger.debug(startLogMsg);

        /* 準備 */

        // 構成モデルを作成する
        this.createJdtsConfigsModel();

        // Javaファイルのリストをロードする
        this.jdtsIoLogic.load();

        /* 次のJavaファイルがあるまでJavadocを置換する */

        // 合計置換数
        long totalReplaceCount = 0;

        do {

            /* 内容を取得する */

            // 内容を読み込む
            this.jdtsIoLogic.loadContent();

            // 内容を取得する
            final String readContent = this.jdtsIoLogic.getReadContent();

            /* コードモデルを作成する */
            // 内容から作成する
            final JdtsCodeModel jdtsCodeModel = new JdtsCodeModelImpl(readContent);

            // コードモデルを解析する
            jdtsCodeModel.parse();

            /* Javadocを置換する */

            // Javadocタグ設定の入出力サービスを初期化する
            this.jdtsReplService.initialize(this.jdtsConfigsModel, jdtsCodeModel);

            // Javadocを置換する
            this.jdtsReplService.replace();

            // 置換数を加算する
            totalReplaceCount += this.jdtsReplService.getTotalReplaceCount();

            // 置換後の内容を取得する
            final String replaceContent = this.jdtsReplService.getReplaceCode();

            /* 内容を書き込む */

            // 書き込む内容を設定する
            this.jdtsIoLogic.setWriteContent(replaceContent);

            // 内容をファイルに書き込む
            this.jdtsIoLogic.writeContent();

            /* 次のファイルに進む */
        } while (this.jdtsIoLogic.nextFile());

        // TODO KenichiroArai 2025/04/29 メッセージを修正する
        final KmgToolLogMessageTypes endLogMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31020;
        final Object[]               endLogMsgArgs  = {
            this.jdtsIoLogic.getFilePathList().size(), totalReplaceCount,
        };
        final String                 endLogMsg      = this.messageSource.getLogMessage(endLogMsgTypes, endLogMsgArgs);
        this.logger.debug(endLogMsg);

        result = true;
        return result;

    }

    /**
     * Javadocタグ設定の構成モデルを作成する。
     * <p>
     * YAMLファイルを読み込み、Javadocタグ設定の構成モデルを作成する。
     * </p>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                          KMGツールメッセージ例外
     */
    private boolean createJdtsConfigsModel() throws KmgToolMsgException {

        final boolean result;

        /* YAMLファイルを読み込む */
        Map<String, Object> yamlData;

        try {

            yamlData = KmgYamlUtils.load(this.definitionPath);

        } catch (final KmgFundMsgException e) {

            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.KMGTOOL_GEN31006;
            final Object[]               genMsgArgs  = {
                this.definitionPath.toString(),
            };
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

        }

        /* Javadocタグ設定の構成モデルを作成する */
        this.jdtsConfigsModel = new JdtsConfigsModelImpl(yamlData);

        result = true;
        return result;

    }

}
