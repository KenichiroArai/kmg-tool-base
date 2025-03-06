package kmg.tool.presentation.ui.cli.io;

import java.nio.file.Path;
import java.nio.file.Paths;

import kmg.core.infrastructure.utils.KmgPathUtils;

/**
 * テンプレートの動的変換ツール抽象クラス
 */
public abstract class AbstractDynamicTemplateConversionTool extends AbstractTwo2OneTool {

    /** テンプレートファイルパス */
    private final Path templatePath;

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
        this.templatePath = this.getDefaultTemplatePath();

    }

    /**
     * テンプレートファイルパス
     *
     * @return テンプレートファイルパス
     */
    @Override
    public Path getTemplatePath() {

        final Path result = this.templatePath;
        return result;

    }

    /**
     * デフォルトテンプレートパスを返す。
     *
     * @return デフォルトテンプレートパス
     */
    private Path getDefaultTemplatePath() {

        Path         result    = null;
        final String className = KmgPathUtils.getSimpleClassName(this.getClass());

        final String templateFileName = String.format("template/%s.yml", className);
        result = Paths.get(AbstractIoTool.getBasePath().toString(), templateFileName);
        return result;

    }
}
