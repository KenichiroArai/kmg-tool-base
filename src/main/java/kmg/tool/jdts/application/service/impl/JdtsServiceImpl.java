package kmg.tool.jdts.application.service.impl;

import java.nio.file.Path;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.exception.KmgFundMsgException;
import kmg.fund.infrastructure.utils.KmgYamlUtils;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.jdts.application.logic.JdtsIoLogic;
import kmg.tool.jdts.application.model.JdtsCodeModel;
import kmg.tool.jdts.application.model.JdtsConfigsModel;
import kmg.tool.jdts.application.model.impl.JdtsCodeModelImpl;
import kmg.tool.jdts.application.model.impl.JdtsConfigsModelImpl;
import kmg.tool.jdts.application.service.JdtsReplService;
import kmg.tool.jdts.application.service.JdtsService;

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
     *                       対象ファイルパス
     * @param definitionPath
     *                       定義ファイルのパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
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

        final KmgToolLogMsgTypes startLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG31019;
        final Object[]           startLogMsgArgs  = {};
        final String             startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes, startLogMsgArgs);
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

            totalReplaceCount += this.processFile();

        } while (this.jdtsIoLogic.nextFile());

        final KmgToolLogMsgTypes endLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG31020;
        final Object[]           endLogMsgArgs  = {
            this.jdtsIoLogic.getFilePathList().size(), totalReplaceCount,
        };
        final String             endLogMsg      = this.messageSource.getLogMessage(endLogMsgTypes, endLogMsgArgs);
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
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    private boolean createJdtsConfigsModel() throws KmgToolMsgException, KmgToolValException {

        final boolean result;

        /* YAMLファイルを読み込む */
        Map<String, Object> yamlData;

        try {

            yamlData = KmgYamlUtils.load(this.definitionPath);

        } catch (final KmgFundMsgException e) {

            final KmgToolGenMsgTypes genMsgTypes = KmgToolGenMsgTypes.KMGTOOL_GEN13003;
            final Object[]           genMsgArgs  = {
                this.definitionPath.toString(),
            };
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

        }

        /* Javadocタグ設定の構成モデルを作成する */
        this.jdtsConfigsModel = new JdtsConfigsModelImpl(yamlData);

        result = true;
        return result;

    }

    /**
     * 内容を読み込み、コードモデルを作成・解析する
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return 解析済みのコードモデル
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    private JdtsCodeModel loadAndCreateCodeModel() throws KmgToolMsgException, KmgToolValException {

        this.jdtsIoLogic.loadContent();
        final String        readContent   = this.jdtsIoLogic.getReadContent();
        final JdtsCodeModel jdtsCodeModel = new JdtsCodeModelImpl(readContent);
        jdtsCodeModel.parse();
        return jdtsCodeModel;

    }

    /**
     * ファイル処理終了ログを出力する
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    private void logFileEnd() {

        final KmgToolLogMsgTypes fileEndLogMsgTypes  = KmgToolLogMsgTypes.KMGTOOL_LOG31026;
        final Object[]           fileStartEndMsgArgs = {
            this.jdtsIoLogic.getCurrentFilePath()
        };
        final String             fileEndLogMsg       = this.messageSource.getLogMessage(fileEndLogMsgTypes,
            fileStartEndMsgArgs);
        this.logger.debug(fileEndLogMsg);

    }

    /**
     * ファイル処理開始ログを出力する
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    private void logFileStart() {

        final KmgToolLogMsgTypes fileStartLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG31025;
        final Object[]           fileStartLogMsgArgs  = {
            this.jdtsIoLogic.getCurrentFilePath()
        };
        final String             fileStartLogMsg      = this.messageSource.getLogMessage(fileStartLogMsgTypes,
            fileStartLogMsgArgs);
        this.logger.debug(fileStartLogMsg);

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
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    private long processFile() throws KmgToolMsgException, KmgToolValException {

        this.logFileStart();
        final JdtsCodeModel jdtsCodeModel = this.loadAndCreateCodeModel();
        final long          result        = this.replaceJavadoc(jdtsCodeModel);
        this.logFileEnd();

        return result;

    }

    /**
     * Javadocを置換し、結果をファイルに書き込む
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param jdtsCodeModel
     *                      コードモデル
     *
     * @return 置換数
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    private long replaceJavadoc(final JdtsCodeModel jdtsCodeModel) throws KmgToolMsgException, KmgToolValException {

        this.jdtsReplService.initialize(this.jdtsConfigsModel, jdtsCodeModel);
        this.jdtsReplService.replace();

        final long   result         = this.jdtsReplService.getTotalReplaceCount();
        final String replaceContent = this.jdtsReplService.getReplaceCode();

        this.jdtsIoLogic.setWriteContent(replaceContent);
        this.jdtsIoLogic.writeContent();

        return result;

    }

}
