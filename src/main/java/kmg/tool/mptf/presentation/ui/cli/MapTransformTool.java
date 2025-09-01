package kmg.tool.mptf.presentation.ui.cli;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.core.domain.service.KmgPfaMeasService;
import kmg.core.domain.service.impl.KmgPfaMeasServiceImpl;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
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
public class MapTransformTool extends AbstractPlainContentInputTool {

    /**
     * <h3>ツール名</h3>
     * <p>
     * このツールの表示名を定義します。
     * </p>
     *
     * @since 0.1.0
     */
    private static final String TOOL_NAME = "マッピング変換ツール"; //$NON-NLS-1$

    /**
     * メッセージソース
     *
     * @since 0.1.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * プレーンコンテンツ入力サービス
     *
     * @since 0.1.0
     */
    @Autowired
    private PlainContentInputServic inputService;

    /**
     * マッピング変換サービス
     *
     * @since 0.1.0
     */
    @Autowired
    private MapTransformService mapTransformService;

    /**
     * 対象ファイルのパス
     *
     * @since 0.1.0
     */
    private Path targetPath;

    /**
     * マッピング
     *
     * @since 0.1.0
     */
    private final Map<String, String> mapping;

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
        final ConfigurableApplicationContext ctx = SpringApplication.run(MapTransformTool.class, args);

        final MapTransformTool tool = ctx.getBean(MapTransformTool.class);

        /* 実行 */
        tool.execute();

        ctx.close();

    }

    /**
     * コンストラクタ
     *
     * @since 0.1.0
     */
    public MapTransformTool() {

        this.mapping = new HashMap<>();

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

        final KmgPfaMeasService measService = new KmgPfaMeasServiceImpl(MapTransformTool.TOOL_NAME);

        /* 開始 */
        measService.start();

        try {

            /* 処理 */

            // 入力ファイルから設定する
            result &= this.fromInputFile();

            if (!result) {

                /* メッセージの出力 */
                final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN19000;
                final Object[]           messageArgs = {};
                final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
                measService.warn(msg);

                return result;

            }

            /* マッピング変換処理 */
            result &= this.mapTransformService.initialize(this.targetPath, this.mapping);
            result &= this.mapTransformService.process();

            /* 成功 */
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN19001;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.info(msg);

        } catch (final KmgToolMsgException e) {

            /* 例外 */
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN19002;
            final Object[]           messageArgs = {};
            final String             msg         = this.messageSource.getGenMessage(msgType, messageArgs);
            measService.error(msg, e);

            result = false;

        } catch (final KmgToolValException e) {

            /* 例外 */
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN19003;
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
     * @since 0.1.0
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
     * @since 0.1.0
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

        // コンテンツを行に分割
        final String[] lines = KmgDelimiterTypes.REGEX_LINE_SEPARATOR.split(content);

        if (lines.length < 2) {

            result = false;
            return result;

        }

        // 1行目はパス
        this.targetPath = Paths.get(lines[0].trim());

        // 2行目以降をマッピングデータとして処理
        for (int i = 1; i < lines.length; i++) {

            final String line = lines[i].trim();

            if (line.isEmpty()) {

                continue;

            }

            final String[] parts = KmgDelimiterTypes.COMMA.split(line);

            if (parts.length < 2) {

                continue;

            }

            final String targetValue      = parts[0].trim();
            final String replacementValue = parts[1].trim();

            this.mapping.put(targetValue, replacementValue);

        }

        return result;

    }

}
