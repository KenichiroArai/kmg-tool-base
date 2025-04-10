package kmg.tool.application.service.impl;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.application.logic.JavadocAppenderLogic;
import kmg.tool.application.model.jda.JdtsConfigurationsModel;
import kmg.tool.application.service.JavadocAppenderService;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加サービス<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@Service
public class JavadocAppenderServiceImpl implements JavadocAppenderService {

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
     * Javadoc追加ロジック
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    @Autowired
    private JavadocAppenderLogic javadocAppenderLogic;

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
    public JavadocAppenderServiceImpl() {

        this(LoggerFactory.getLogger(JavadocAppenderServiceImpl.class));

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
    protected JavadocAppenderServiceImpl(final Logger logger) {

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

        /* Javadoc追加ロジックの初期化 */
        this.javadocAppenderLogic.initialize(targetPath, templatePath);

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

        final boolean result = false;

        // TODO KenichiroArai 2025/03/29 処理の開始ログ

        /* Javadocタグモデルの作成 */
        this.javadocAppenderLogic.createJavadocTagsModel();

        // TODO KenichiroArai 2025/03/29 ログ
        final JdtsConfigurationsModel jdtsConfigurationsModel = this.javadocAppenderLogic.getJdtsConfigurationsModel();
        System.out.println(jdtsConfigurationsModel.toString());

        /* 対象のJavaファイルを作成する */
        this.javadocAppenderLogic.createJavaFileList();

        boolean nextFlg;

        do {

            /* 対象のJavaファイルのJavadocを設定する */
            this.javadocAppenderLogic.setJavadoc(true);

            /* 修正した内容をファイルに書き込む */
            this.javadocAppenderLogic.writeCurrentJavaFile();

            /* 次の対象のJavaファイルに進む */
            nextFlg = this.javadocAppenderLogic.nextJavaFile();

        } while (nextFlg);

        // TODO KenichiroArai 2025/03/29 処理の終了ログ
        System.out.println(String.format("読み込みファイル数: %d", this.javadocAppenderLogic.getJavaFilePathList().size()));
        System.out.println(String.format("最終合計行数: %d", this.javadocAppenderLogic.getTotalRows()));

        return result;

    }

}
