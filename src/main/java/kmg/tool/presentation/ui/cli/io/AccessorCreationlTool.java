package kmg.tool.presentation.ui.cli.io;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.tool.application.service.AccessorCreationService;
import kmg.tool.application.service.SimpleTwo2OneService;

/**
 * アクセサ作成ツール
 *
 * @author KenichiroArai
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class AccessorCreationlTool extends SimpleTwo2OneTool {

    /** ツール名 */
    private static final String TOOL_NAME = "アクセサ作成ツール";

    /** テンプレートファイルパス */
    // TODO KenichiroArai 2025/02/27 外部文字列化
    private static final Path TEMPLATE_PATH
        = Paths.get(AbstractIoTool.getBasePath().toString(), "template/kmgTlAccessorCreationlTool.txt");

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

        // 親クラスのコンストラクタを呼び出す
        // SimpleTwo2OneToolは自身のTOOL_NAMEを親クラスに渡しているため、
        // ここでは親クラスのコンストラクタを直接呼び出します
        

    }

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean initialize() {

        // 親クラスの初期化を呼び出す
        final boolean parentResult = super.initialize();

        // アクセサ作成サービスの初期化
        final boolean result = this.accessorCreationService.initialize(AbstractIoTool.getInputPath(),
            AccessorCreationlTool.TEMPLATE_PATH, AbstractIoTool.getOutputPath());

        return result && parentResult;

    }

    /**
     * アクセサ作成サービスを返す。
     *
     * @return アクセサ作成サービス
     */
    protected AccessorCreationService getAccessorCreationService() {

        final AccessorCreationService result = this.accessorCreationService;
        return result;

    }

    /**
     * 入出力サービスを返す。 親クラスのサービスを使用します。
     *
     * @return シンプル2入力ファイルから1出力ファイルへの変換サービス
     */
    @Override
    protected SimpleTwo2OneService getIoService() {

        // 親クラスのサービスを使用する
        return super.getIoService();

    }
}
