package kmg.tool.base.cmn.presentation.ui.cli;

/**
 * ツール抽象クラス
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public abstract class AbstractTool {

    /**
     * 実行する
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     */
    public abstract boolean execute();

}
