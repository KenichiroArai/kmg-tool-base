package kmg.tool.presentation.ui.cli;

import kmg.tool.domain.service.PlainContentInputServic;

/**
 * 入力処理ツール抽象クラス
 */
// TODO KenichiroArai 2025/05/14 Javadocの再設定が必要。
public abstract class AbstractPlainContentInputTool extends AbstractInputTool {

    /**
     * プレーンコンテンツ入力サービスを返す。
     *
     * @return プレーンコンテンツ入力サービス
     */
    @Override
    protected abstract PlainContentInputServic getInputService();

}
