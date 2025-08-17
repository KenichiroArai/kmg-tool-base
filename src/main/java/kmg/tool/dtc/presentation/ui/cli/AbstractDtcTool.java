package kmg.tool.dtc.presentation.ui.cli;

import kmg.tool.iito.domain.service.IitoProcessorService;
import kmg.tool.two2one.presentation.ui.cli.AbstractTwo2OneTool;

/**
 * テンプレートの動的変換ツール抽象クラス
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 */
public abstract class AbstractDtcTool extends AbstractTwo2OneTool {

    /**
     * 標準ロガーを使用して初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param toolName
     *                 ツール名
     */
    public AbstractDtcTool(final String toolName) {

        super(toolName);

    }

    /**
     * 入力、中間、テンプレート、出力の処理サービスを返す。
     *
     * @since 0.1.0
     *
     * @return 入力、中間、テンプレート、出力の処理サービス
     */
    @Override
    protected abstract IitoProcessorService getIoService();

}
