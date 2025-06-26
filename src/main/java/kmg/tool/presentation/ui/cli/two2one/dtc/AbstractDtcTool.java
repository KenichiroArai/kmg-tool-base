package kmg.tool.presentation.ui.cli.two2one.dtc;

import kmg.tool.domain.service.IitoProcessorService;
import kmg.tool.presentation.ui.cli.two2one.AbstractTwo2OneTool;

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
     * @param toolName
     *                 ツール名
     *
     * @since 0.1.0
     */
    public AbstractDtcTool(final String toolName) {

        super(toolName);

    }

    /**
     * 入力、中間、テンプレート、出力の処理サービスを返す。
     *
     * @return 入力、中間、テンプレート、出力の処理サービス
     */
    @Override
    protected abstract IitoProcessorService getIoService();

}
