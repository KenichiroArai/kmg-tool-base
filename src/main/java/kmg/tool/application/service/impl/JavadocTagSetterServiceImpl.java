package kmg.tool.application.service.impl;

import java.nio.file.Path;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.fund.infrastructure.exception.KmgFundException;
import kmg.fund.infrastructure.utils.KmgYamlUtils;
import kmg.tool.application.logic.JdtsIoLogic;
import kmg.tool.application.model.jda.JdtsCodeModel;
import kmg.tool.application.model.jda.JdtsConfigsModel;
import kmg.tool.application.model.jda.imp.JdtsCodeModelImpl;
import kmg.tool.application.model.jda.imp.JdtsConfigsModelImpl;
import kmg.tool.application.service.JavadocTagSetterService;
import kmg.tool.application.service.JdtsReplService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグ設定サービス<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@Service
public class JavadocTagSetterServiceImpl implements JavadocTagSetterService {

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

    /**
     * テンプレートファイルパス
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    private Path templatePath;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    public JavadocTagSetterServiceImpl() {

        this(LoggerFactory.getLogger(JavadocTagSetterServiceImpl.class));

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
    protected JavadocTagSetterServiceImpl(final Logger logger) {

        this.logger = logger;

    }

    /**
     * 対象ファイルパス
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
     * テンプレートファイルパス
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return テンプレートファイルパス
     */
    @Override
    public Path getTemplatePath() {

        final Path result = this.templatePath;
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
     *                     対象ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path targetPath, final Path templatePath) throws KmgToolException {

        boolean result = false;

        this.targetPath = targetPath;
        this.templatePath = templatePath;

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
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean process() throws KmgToolException {

        boolean result = false;

        // TODO KenichiroArai 2025/03/29 処理の開始ログ

        /* Javadocタグ設定の構成モデルを作成 */
        this.createJdtsConfigsModel();

        /* Javaファイルのリストをロードする */
        this.jdtsIoLogic.load();

        /* 次のJavaファイルがあるまでJavadocを置換する */
        boolean hasNext;

        do {

            /* 内容を読み込む */
            this.jdtsIoLogic.loadContent();

            /* 内容を取得する */
            final String readContent = this.jdtsIoLogic.getReadContent();

            /* Javadocタグ設定のコードモデルを作成する */
            final JdtsCodeModel jdtsCodeModel = new JdtsCodeModelImpl(readContent);

            /* コードモデルを解析する。 */
            jdtsCodeModel.parse();

            /* Javadocタグ設定の入出力サービスを初期化する */
            this.jdtsReplService.initialize(this.jdtsConfigsModel, jdtsCodeModel);

            /* Javadocを置換する */
            final String replaceContent = this.jdtsReplService.replace();

            /* 書き込む内容を設定する */
            this.jdtsIoLogic.setWriteContent(replaceContent);

            /* 内容をファイルに書き込む */
            this.jdtsIoLogic.writeContent();

            /* 次のファイルに進む */
            hasNext = this.jdtsIoLogic.nextFile();

        } while (hasNext);

        // TODO KenichiroArai 2025/03/29 処理の終了ログ
        System.out.println(String.format("読み込みファイル数: %d", this.jdtsIoLogic.getFilePathList().size()));
        System.out.println(String.format("最終合計行数: %d", this.jdtsReplService.getTotalRows()));

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
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private boolean createJdtsConfigsModel() throws KmgToolException {

        final boolean result;

        Map<String, Object> yamlData;

        try {

            yamlData = KmgYamlUtils.load(this.templatePath);

        } catch (final KmgFundException e) {

            // TODO KenichiroArai 2025/04/11 例外処理
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

        this.jdtsConfigsModel = new JdtsConfigsModelImpl(yamlData);

        result = true;
        return result;

    }

}
