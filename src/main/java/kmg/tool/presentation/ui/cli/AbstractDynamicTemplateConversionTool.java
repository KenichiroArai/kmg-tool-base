package kmg.tool.presentation.ui.cli;

import kmg.tool.domain.service.IctoProcessorService;
import kmg.tool.presentation.ui.cli.two2one.AbstractTwo2OneTool;

/**
 * テンプレートの動的変換ツール抽象クラス
 */
public abstract class AbstractDynamicTemplateConversionTool extends AbstractTwo2OneTool {

    /**
     * 標準ロガーを使用して初期化するコンストラクタ<br>
     *
     * @param toolName
     *                 ツール名
     *
     * @since 0.1.0
     */
    public AbstractDynamicTemplateConversionTool(final String toolName) {

        super(toolName);

    }

    /**
     * 入力、CSV、テンプレート、出力の処理サービスを返す。
     *
     * @return 入力、CSV、テンプレート、出力の処理サービス
     */
    @Override
    protected abstract IctoProcessorService getIoService();

}
