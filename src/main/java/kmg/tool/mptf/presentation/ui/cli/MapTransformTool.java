package kmg.tool.mptf.presentation.ui.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.core.domain.service.KmgPfaMeasService;
import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.input.domain.service.PlainContentInputServic;
import kmg.tool.input.presentation.ui.cli.AbstractInputTool;
import kmg.tool.input.presentation.ui.cli.AbstractPlainContentInputTool;
import kmg.tool.mptf.application.service.MapTransformService;

/**
 * マッピング変換ツール<br>
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class MapTransformTool extends AbstractPlainContentInputTool {

    /**
     * <h3>ツール名</h3>
     * <p>
     * このツールの表示名を定義します。
     * </p>
     */
    private static final String TOOL_NAME = "マッピング変換ツール"; //$NON-NLS-1$

    /** メッセージソース */
    @Autowired
    private KmgMessageSource messageSource;

    /** プレーンコンテンツ入力サービス */
    @Autowired
    private PlainContentInputServic inputService;

    /**
     * マッピング変換サービス
     */
    @Autowired
    private MapTransformService mapTransformService;

    /** 対象ファイルのパス */
    private Path targetPath;

    /**
     * メインメソッド
     *
     * @param args
     *             引数
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        final ConfigurableApplicationContext ctx = SpringApplication.run(MapTransformTool.class, args);

        final MapTransformTool tool = ctx.getBean(MapTransformTool.class);

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

        final KmgPfaMeasService measService = new KmgPfaMeasServiceImpl(MapTransformTool.TOOL_NAME);

        /* 開始 */
        measService.start();

        try {

            /* 処理 */

            // 入力ファイルから設定する
            result &= this.fromInputFile();

            if (!result) {

                /* メッセージの出力 */
                // TODO KenichiroArai 2025/07/10 メッセージ
                final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.NONE;
                final Object[]           messageArgs = {};
                final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
                measService.warn(msg);

                return result;

            }

            /* Javadoc追加処理 */
            result &= this.mapTransformService.initialize(this.targetPath);
            result &= this.mapTransformService.process();

            /* 成功 */
            // TODO KenichiroArai 2025/07/10 メッセージ
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.NONE;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.info(msg);

        } catch (final KmgToolMsgException e) {

            /* 例外 */
            // TODO KenichiroArai 2025/07/10 メッセージ
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.NONE;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.error(msg, e);

            result = false;

        } catch (final KmgToolValException e) {

            /* 例外 */
            // TODO KenichiroArai 2025/07/10 メッセージ
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.NONE;
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
     * 入力ファイルから設定する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean fromInputFile() throws KmgToolMsgException {

        boolean result = true;

        result &= this.loadPlainContent(AbstractInputTool.getInputPath());

        final String content = this.getContent();

        this.targetPath = Paths.get(content);

        return result;

    }

}
