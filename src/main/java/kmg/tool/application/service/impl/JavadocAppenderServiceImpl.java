package kmg.tool.application.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.application.logic.JavadocAppenderLogic;
import kmg.tool.application.service.JavadocAppenderService;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加サービス<br>
 *
 * @author KenichiroArai
 */
@Service
public class JavadocAppenderServiceImpl implements JavadocAppenderService {

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @since 0.1.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /** Javadoc追加ロジック */
    @Autowired
    private JavadocAppenderLogic javadocAppenderLogic;

    /** 対象ファイルパス */
    private Path targetPath;

    /** テンプレートファイルパス */
    private Path templatePath;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     */
    public JavadocAppenderServiceImpl() {

        this(LoggerFactory.getLogger(JavadocAppenderServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
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
     * @return true：成功、false：失敗
     *
     * @param targetPath
     *                     対象ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
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
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean process() throws KmgToolException {

        boolean result = false;

        /* タグマップの取得 */
        try {

            this.javadocAppenderLogic.createTagMap();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/29 メッセージ
            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.NONE;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }
        final Map<String, String> tagMap = this.javadocAppenderLogic.getTagMap();
        System.out.println(tagMap.toString());

        /* 対象のJavaファイルを取得 */
        final List<Path> javaFileList;

        int fileCount = 0;

        try (final Stream<Path> streamPath = Files.walk(this.targetPath)) {

            javaFileList = streamPath.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java"))
                .collect(Collectors.toList());

            fileCount += javaFileList.size();

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/29 メッセージ
            e.printStackTrace();
            return result;

        }

        /* 対象のJavaファイルをすべて読み込む */

        int lineCount = 0;

        for (final Path javaFile : javaFileList) {

            final StringBuilder fileContentBuilder = new StringBuilder();
            String              fileContent;

            try {

                fileContent = this.javadocAppenderLogic.getNewJavaFile(javaFile, fileContentBuilder, true);

            } catch (final IOException e) {

                // TODO KenichiroArai 2025/03/29 メッセージ
                e.printStackTrace();
                return result;

            }

            lineCount += KmgDelimiterTypes.LINE_SEPARATOR.split(fileContent).length;

            /* 修正した内容をファイルに書き込む */
            try {

                Files.writeString(javaFile, fileContent);

            } catch (final IOException e) {

                System.err.println("Error writing to file: " + javaFile);
                e.printStackTrace();
                result = false;

            }

        }

        System.out.println(String.format("fileCount: %d", fileCount));
        System.out.println(String.format("lineCount: %d", lineCount));

        return result;

    }

}
