package kmg.tool.base.jdts.application.service.impl;

import java.nio.file.Path;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.exception.KmgFundMsgException;
import kmg.fund.infrastructure.utils.KmgYamlUtils;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.io.domain.logic.FileIteratorLogic;
import kmg.tool.base.jdts.application.model.JdtsCodeModel;
import kmg.tool.base.jdts.application.model.JdtsConfigsModel;
import kmg.tool.base.jdts.application.model.impl.JdtsCodeModelImpl;
import kmg.tool.base.jdts.application.model.impl.JdtsConfigsModelImpl;
import kmg.tool.base.jdts.application.service.JdtsReplService;
import kmg.tool.base.jdts.application.service.JdtsService;

/**
 * Javadocタグ設定サービス<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.2
 */
@Service
public class JdtsServiceImpl implements JdtsService {

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
     * Javadocタグ設定の構成モデル
     *
     * @since 0.2.0
     */
    private JdtsConfigsModel jdtsConfigsModel;

    /**
     * ファイルイテレーターロジック
     *
     * @since 0.2.0
     */
    @Autowired
    private FileIteratorLogic fileIteratorLogic;

    /**
     * Javadocタグ設定の入出力サービス
     *
     * @since 0.2.0
     */
    @Autowired
    private JdtsReplService jdtsReplService;

    /**
     * Springアプリケーションコンテキスト
     *
     * @since 0.2.0
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 対象ファイルパス
     *
     * @since 0.2.0
     */
    private Path targetPath;

    /**
     * 定義ファイルのパス
     *
     * @since 0.2.0
     */
    private Path definitionPath;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     */
    public JdtsServiceImpl() {

        this(LoggerFactory.getLogger(JdtsServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
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
     * @since 0.2.0
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
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    @Override
    public boolean process() throws KmgToolMsgException, KmgToolValException {

        boolean result = false;

        final KmgToolLogMsgTypes startLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG13005;
        final Object[]           startLogMsgArgs  = {};
        final String             startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes, startLogMsgArgs);
        this.logger.debug(startLogMsg);

        /* 準備 */

        // 構成モデルを作成する
        this.createJdtsConfigsModel();

        // Javaファイルのリストをロードする
        this.fileIteratorLogic.load();

        /* 次のJavaファイルがあるまでJavadocを置換する */

        // 合計置換数
        long totalReplaceCount = 0;

        do {

            totalReplaceCount += this.processFile();

        } while (this.fileIteratorLogic.nextFile());

        final KmgToolLogMsgTypes endLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG13006;
        final Object[]           endLogMsgArgs  = {
            this.fileIteratorLogic.getFilePathList().size(), totalReplaceCount,
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
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @return 解析済みのコードモデル
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    private JdtsCodeModel loadAndCreateCodeModel() throws KmgToolMsgException, KmgToolValException {

        JdtsCodeModel result;

        this.fileIteratorLogic.loadContent();

        final String readContent = this.fileIteratorLogic.getReadContent();
        result = this.applicationContext.getBean(JdtsCodeModelImpl.class, readContent);
        result.parse();

        return result;

    }

    /**
     * ファイル処理終了ログを出力する
     *
     * @since 0.2.0
     */
    private void logFileEnd() {

        final KmgToolLogMsgTypes fileEndLogMsgTypes  = KmgToolLogMsgTypes.KMGTOOL_LOG13008;
        final Object[]           fileStartEndMsgArgs = {
            this.fileIteratorLogic.getCurrentFilePath()
        };
        final String             fileEndLogMsg       = this.messageSource.getLogMessage(fileEndLogMsgTypes,
            fileStartEndMsgArgs);
        this.logger.debug(fileEndLogMsg);

    }

    /**
     * ファイル処理開始ログを出力する
     *
     * @since 0.2.0
     */
    private void logFileStart() {

        final KmgToolLogMsgTypes fileStartLogMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG13007;
        final Object[]           fileStartLogMsgArgs  = {
            this.fileIteratorLogic.getCurrentFilePath()
        };
        final String             fileStartLogMsg      = this.messageSource.getLogMessage(fileStartLogMsgTypes,
            fileStartLogMsgArgs);
        this.logger.debug(fileStartLogMsg);

    }

    /**
     * ファイルを処理する
     *
     * @since 0.2.0
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
     * @since 0.2.0
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

        this.fileIteratorLogic.setWriteContent(replaceContent);
        this.fileIteratorLogic.writeContent();

        return result;

    }

}
