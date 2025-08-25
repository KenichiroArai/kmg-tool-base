package kmg.tool.input.presentation.ui.cli;

import java.nio.file.Path;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.input.domain.service.PlainContentInputServic;

/**
 * プレーンコンテンツ入力処理ツール抽象クラス
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public abstract class AbstractPlainContentInputTool extends AbstractInputTool {

    /**
     * 内容
     *
     * @since 0.1.0
     */
    private String content;

    /**
     * 内容を返す。
     *
     * @since 0.1.0
     *
     * @return 内容
     */
    public String getContent() {

        final String result = this.content;
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
    public abstract PlainContentInputServic getInputService();

    /**
     * 入力ファイルからプレーンコンテンツを読み込む
     *
     * @since 0.1.0
     *
     * @param inputPath
     *                  入力ファイルパス
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    public boolean loadPlainContent(final Path inputPath) throws KmgToolMsgException {

        boolean result = true;

        result &= this.getInputService().initialize(AbstractInputTool.getInputPath());

        result &= this.getInputService().process();

        this.content = this.getInputService().getContent();

        return result;

    }

}
