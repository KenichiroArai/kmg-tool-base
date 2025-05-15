package kmg.tool.presentation.ui.cli;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.core.domain.service.KmgPfaMeasService;
import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.application.service.JavadocLineRemoverService;
import kmg.tool.domain.service.InputService;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;

/**
 * Javadoc行削除ツール
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
     */
    private static final String TOOL_NAME = "Javadoc行削除ツール";

    /** メッセージソース */
    @Autowired
    private KmgMessageSource messageSource;

    /** 入力サービス */
    @Autowired
    private InputService inputService;

    /** Javadoc行削除サービス */
    @Autowired
    private JavadocLineRemoverService javadocLineRemoverService;

    /**
     * メインメソッド
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
     * @return true：成功、false：失敗
     */
    @Override
    public boolean execute() {

        boolean result = true;

        final KmgPfaMeasService measService = new KmgPfaMeasServiceImpl(JavadocLineRemoverTool.TOOL_NAME);

        /* 開始 */
        measService.start();

        try {

            final Path inputPath = this.inputService.getInputPath();

            final Path definitionPath = null;

            /* 初期化 */
            result &= this.javadocLineRemoverService.initialize(inputPath, definitionPath);

            // TODO KenichiroArai 2025/05/14 未実装。
            // // 入力ファイルから対象パスを設定
            // result &= this.setTargetPathFromInputFile();

            if (!result) {

                /* メッセージの出力 */
                // TODO KenichiroArai 2025/05/14 メッセージ。
                final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.NONE;
                final Object[]           messageArgs = {};
                final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
                measService.warn(msg);

                return result;

            }

            /* Javadoc行削除処理 */
            // TODO KenichiroArai 2025/05/14 未実装。
            result &= this.javadocLineRemoverService.process();

            /* 成功 */
            // TODO KenichiroArai 2025/05/14 メッセージ。
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.NONE;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.info(msg);

        } catch (final KmgToolMsgException e) {

            /* 例外 */
            // TODO KenichiroArai 2025/05/14 メッセージ。
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.NONE;
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
     * @return 入力サービス
     */
    @Override
    protected InputService getInputService() {

        final InputService result = this.inputService;
        return result;

    }

}
