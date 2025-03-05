package kmg.tool.presentation.ui.cli.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.tool.application.service.AccessorCreationService;

/**
 * アクセサ作成ツール
 *
 * @author KenichiroArai
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class AccessorCreationlTool extends AbstractTwo2OneTool {

    /** ツール名 */
    private static final String TOOL_NAME = "アクセサ作成ツール";

    /** アクセサ作成サービス */
    @Autowired
    private AccessorCreationService accessorCreationService;

    /**
     * エントリポイント
     *
     * @param args
     *             オプション
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        final ConfigurableApplicationContext ctx = SpringApplication.run(AccessorCreationlTool.class, args);

        final AccessorCreationlTool tool = ctx.getBean(AccessorCreationlTool.class);

        /* 初期化 */
        tool.initialize();

        /* 実行 */
        tool.execute();

        ctx.close();

    }

    /**
     * コンストラクタ
     */
    public AccessorCreationlTool() {

        super(AccessorCreationlTool.TOOL_NAME);

    }

    /**
     * アクセサ作成サービスを返す。
     *
     * @return アクセサ作成サービス
     */
    @Override
    protected AccessorCreationService getIoService() {

        final AccessorCreationService result = this.accessorCreationService;

        return result;

    }
}
