package kmg.tool.jdocr.presentation.ui.cli;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.core.domain.service.KmgPfaMeasService;
import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.input.presentation.ui.cli.AbstractInputTool;
import kmg.tool.jdocr.service.JavadocLineRemoverService;
import kmg.tool.simple.domain.service.SimpleInputService;

/**
 * Javadoc行削除ツール
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class JavadocLineRemoverTool extends AbstractInputTool {

    /**
     * <h3>ツール名</h3>
     * <p>
     * このツールの表示名を定義します。
     * </p>
     *
     * @since 0.1.0
     */
    private static final String TOOL_NAME = "Javadoc行削除ツール"; //$NON-NLS-1$

    /**
     * メッセージソース
     *
     * @since 0.1.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * シンプル入力サービス
     *
     * @since 0.1.0
     */
    @Autowired
    private SimpleInputService inputService;

    /**
     * Javadoc行削除サービス
     *
     * @since 0.1.0
     */
    @Autowired
    private JavadocLineRemoverService javadocLineRemoverService;

    /**
     * メインメソッド
     *
     * @since 0.1.0
     *
     * @param args
     *             引数
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        final ConfigurableApplicationContext ctx = SpringApplication.run(JavadocLineRemoverTool.class, args);

        final JavadocLineRemoverTool tool = ctx.getBean(JavadocLineRemoverTool.class);

        /* 実行 */
        tool.execute();

        ctx.close();

    }

    /**
     * 実行する
     *
     * @since 0.1.0
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean execute() {

        boolean result = true;

        final KmgPfaMeasService measService = new KmgPfaMeasServiceImpl(JavadocLineRemoverTool.TOOL_NAME);

        /* 開始 */
        measService.start();

        try {

            /* ファイルのパスの取得 */
            // 入力ファイルのパス
            final Path inputPath = this.inputService.getInputPath();

            /* Javadoc行削除の初期化 */
            result &= this.javadocLineRemoverService.initialize(inputPath);

            if (!result) {

                /* メッセージの出力 */
                final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN12003;
                final Object[]           messageArgs = {};
                final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
                measService.warn(msg);

                return result;

            }

            /* Javadoc行削除を処理する */
            result &= this.javadocLineRemoverService.process();

            /* 成功 */
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN12004;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.info(msg);

        } catch (final KmgToolMsgException e) {

            /* 例外 */
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN12005;
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
     * 入力サービスを返す。
     *
     * @since 0.1.0
     *
     * @return 入力サービス
     */
    @Override
    public SimpleInputService getInputService() {

        final SimpleInputService result = this.inputService;
        return result;

    }

}
