package kmg.tool.jdts.presentation.ui.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.core.domain.service.KmgPfaMeasService;
import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
import kmg.core.infrastructure.utils.KmgPathUtils;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.input.domain.service.PlainContentInputServic;
import kmg.tool.input.presentation.ui.cli.AbstractInputTool;
import kmg.tool.input.presentation.ui.cli.AbstractPlainContentInputTool;
import kmg.tool.jdts.application.service.JdtsService;

/**
 * Javadocタグ設定ツール<br>
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class JavadocTagSetterTool extends AbstractPlainContentInputTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io")); //$NON-NLS-1$

    /** 定義ファイルのパスのフォーマット */
    private static final String DEFINITION_FILE_PATH_FORMAT = "template/%s.yml"; //$NON-NLS-1$

    /**
     * <h3>ツール名</h3>
     * <p>
     * このツールの表示名を定義します。
     * </p>
     */
    private static final String TOOL_NAME = "Javadocタグ設定ツール"; //$NON-NLS-1$

    /** メッセージソース */
    @Autowired
    private KmgMessageSource messageSource;

    /** プレーンコンテンツ入力サービス */
    @Autowired
    private PlainContentInputServic inputService;

    /**
     * Javadocタグ設定サービス
     */
    @Autowired
    private JdtsService jdtsService;

    /** 対象ファイルのパス */
    private Path targetPath;

    /** 定義ファイルのパス */
    private final Path definitionPath;

    /**
     * メインメソッド
     *
     * @param args
     *             引数
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        final ConfigurableApplicationContext ctx = SpringApplication.run(JavadocTagSetterTool.class, args);

        final JavadocTagSetterTool tool = ctx.getBean(JavadocTagSetterTool.class);

        /* 実行 */
        tool.execute();

        ctx.close();

    }

    /**
     * デフォルトコンストラクタ
     */
    public JavadocTagSetterTool() {

        this(LoggerFactory.getLogger(JavadocTagSetterTool.class), JavadocTagSetterTool.TOOL_NAME);

    }

    /**
     * カスタムロガーを使用して初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *                 ロガー
     * @param toolName
     *                 ツール名
     */
    protected JavadocTagSetterTool(final Logger logger, final String toolName) {

        this.definitionPath = this.getDefaultDefinitionPath();

    }

    /**
     * 実行する
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean execute() {

        boolean result = true;

        final KmgPfaMeasService measService = new KmgPfaMeasServiceImpl(JavadocTagSetterTool.TOOL_NAME);

        /* 開始 */
        measService.start();

        try {

            /* 処理 */

            // 入力ファイルから対象パスを設定
            result &= this.setTargetPathFromInputFile();

            if (!result) {

                /* メッセージの出力 */
                final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN41003;
                final Object[]           messageArgs = {};
                final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
                measService.warn(msg);

                return result;

            }

            /* Javadoc追加処理 */
            result &= this.jdtsService.initialize(this.targetPath, this.definitionPath);
            result &= this.jdtsService.process();

            /* 成功 */
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN41004;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.info(msg);

        } catch (final KmgToolMsgException e) {

            /* 例外 */
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN41005;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.error(msg, e);

            result = false;

        } catch (final KmgToolValException e) {

            /* 例外 */
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN12007;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.error(msg, e);

            // バリデーションエラーを全てログに出力する
            e.getValidationsModel().getDatas().forEach(data -> measService.error(data.getMessage()));

            result = false;

        } finally {

            /* 終了 */

            measService.end();

        }

        return result;

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
    public Path getDefinitionPath() {

        final Path result = this.definitionPath;
        return result;

    }

    /**
     * プレーンコンテンツ入力サービスを返す。
     *
     * @return プレーンコンテンツ入力サービス
     */
    @Override
    public PlainContentInputServic getInputService() {

        final PlainContentInputServic result = this.inputService;
        return result;

    }

    /**
     * デフォルト定義ファイルのパスを返す。
     *
     * @return デフォルト定義パス
     */
    private Path getDefaultDefinitionPath() {

        Path         result;
        final String className        = KmgPathUtils.getSimpleClassName(this.getClass());
        final String templateFileName = String.format(JavadocTagSetterTool.DEFINITION_FILE_PATH_FORMAT, className);

        result = Paths.get(JavadocTagSetterTool.BASE_PATH.toString(), templateFileName);

        return result;

    }

    /**
     * 入力ファイルから対象パスを設定する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean setTargetPathFromInputFile() throws KmgToolMsgException {

        boolean result = true;

        result &= this.loadPlainContent(AbstractInputTool.getInputPath());

        final String content = this.getContent();

        this.targetPath = Paths.get(content);

        return result;

    }

}
