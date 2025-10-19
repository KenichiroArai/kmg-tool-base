package kmg.tool.base.jdts.presentation.ui.cli;

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
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.base.input.domain.service.PlainContentInputServic;
import kmg.tool.base.input.presentation.ui.cli.AbstractInputTool;
import kmg.tool.base.input.presentation.ui.cli.AbstractPlainContentInputTool;
import kmg.tool.base.jdts.application.service.JdtsService;

/**
 * Javadocタグ設定ツール<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class JavadocTagSetterTool extends AbstractPlainContentInputTool {

    /**
     * 基準パス
     *
     * @since 0.2.0
     */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io")); //$NON-NLS-1$

    /**
     * 定義ファイルのパスのフォーマット
     *
     * @since 0.2.0
     */
    private static final String DEFINITION_FILE_PATH_FORMAT = "template/%s.yml"; //$NON-NLS-1$

    /**
     * <h3>ツール名</h3>
     * <p>
     * このツールの表示名を定義します。
     * </p>
     *
     * @since 0.2.0
     */
    private static final String TOOL_NAME = "Javadocタグ設定ツール"; //$NON-NLS-1$

    /**
     * メッセージソース
     *
     * @since 0.2.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * プレーンコンテンツ入力サービス
     *
     * @since 0.2.0
     */
    @Autowired
    private PlainContentInputServic inputService;

    /**
     * Javadocタグ設定サービス
     *
     * @since 0.2.0
     */
    @Autowired
    private JdtsService jdtsService;

    /**
     * 対象ファイルのパス
     *
     * @since 0.2.0
     */
    private Path targetPath;

    /**
     * 定義ファイルのパス
     *
     * @since 0.2.0
     */
    private final Path definitionPath;

    /**
     * メインメソッド
     *
     * @since 0.2.0
     *
     * @param args
     *             引数
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        final ConfigurableApplicationContext ctx = SpringApplication.run(JavadocTagSetterTool.class, args);

        final JavadocTagSetterTool tool = ctx.getBean(JavadocTagSetterTool.class);

        tool.run(args);

        ctx.close();

    }

    /**
     * デフォルトコンストラクタ
     *
     * @since 0.2.0
     */
    public JavadocTagSetterTool() {

        this(LoggerFactory.getLogger(JavadocTagSetterTool.class), JavadocTagSetterTool.TOOL_NAME);

    }

    /**
     * カスタムロガーを使用して初期化するコンストラクタ<br>
     *
     * @since 0.2.0
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
     * @since 0.2.0
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
                final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN13004;
                final Object[]           messageArgs = {};
                final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
                measService.warn(msg);

                return result;

            }

            /* Javadoc追加処理 */
            result &= this.jdtsService.initialize(this.targetPath, this.definitionPath);
            result &= this.jdtsService.process();

            /* 成功 */
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN13005;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.info(msg);

        } catch (final KmgToolMsgException e) {

            /* 例外 */
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN13006;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.error(msg, e);

            result = false;

        } catch (final KmgToolValException e) {

            /* 例外 */
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN13007;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.error(msg, e);

            // バリデーションエラーを全てログに出力する
            e.getValidationsModel().getDatas().forEach(data -> measService.error(data.getMessage()));

            result = false;

        } catch (final RuntimeException e) {

            /* 例外 */

            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN13008;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.error(msg, e);

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
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @return プレーンコンテンツ入力サービス
     */
    @Override
    public PlainContentInputServic getInputService() {

        final PlainContentInputServic result = this.inputService;
        return result;

    }

    /**
     * <h3>ツール実行メソッド</h3>
     * <p>
     * ツールの初期化と実行を行います。
     * </p>
     *
     * @since 0.2.0
     *
     * @param args
     *             コマンドライン引数
     */
    public void run(final String[] args) {

        /* 実行 */
        this.execute();

    }

    /**
     * デフォルト定義ファイルのパスを返す。
     *
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean setTargetPathFromInputFile() throws KmgToolMsgException {

        boolean result = true;

        result &= this.loadPlainContent(AbstractInputTool.getInputPath());

        if (!result) {

            return result;

        }

        final String content = this.getContent();

        if (content == null) {

            result = false;
            return result;

        }

        this.targetPath = Paths.get(content);

        return result;

    }

}
