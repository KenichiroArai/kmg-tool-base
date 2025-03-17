package kmg.tool.presentation.ui.cli.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.tool.application.service.io.Enum2SwitchCaseMakingService;

/**
 * <h2>列挙型からcase文作成ツール</h2>
 * <p>
 * 列挙型の定義からswitch-case文を自動生成するためのツールです。
 * </p>
 * <p>
 * このツールは入力ファイルとテンプレートファイルを使用して、switch-case文を含む出力ファイルを生成します。
 * </p>
 * <p>
 * AbstractDynamicTemplateConversionToolを継承しており、動的テンプレート変換処理を実装しています。
 * </p>
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class Enum2SwitchCaseMakingTool extends AbstractDynamicTemplateConversionTool {

    /**
     * <h3>ツール名</h3>
     * <p>
     * このツールの表示名を定義します。
     * </p>
     */
    private static final String TOOL_NAME = "列挙型からcase文作成ツール";

    /**
     * <h3>列挙型からcase文作成サービス</h3>
     * <p>
     * 列挙型定義からswitch-case文を生成するためのサービスです。
     * </p>
     */
    @Autowired
    private Enum2SwitchCaseMakingService enum2SwitchCaseMakingService;

    /**
     * <h3>エントリポイント</h3>
     * <p>
     * アプリケーションの起動とツールの実行を行います。
     * </p>
     * <p>
     * このメソッドはSpringBootアプリケーションとしてツールを起動し、 初期化処理と実行処理を順に行います。
     * </p>
     * <p>
     * 処理終了後はSpringのコンテキストを閉じて、リソースを解放します。
     * </p>
     * <p>
     * <strong>処理の流れ：</strong>
     * </p>
     * <ol>
     * <li>SpringBootアプリケーションの起動</li>
     * <li>ツールインスタンスの取得</li>
     * <li>初期化処理の実行</li>
     * <li>メイン処理の実行</li>
     * <li>コンテキストのクローズ</li>
     * </ol>
     *
     * @param args
     *             コマンドライン引数。入力ファイルパス、テンプレートファイルパス、出力ファイルパスなどを指定できます。 <br>
     *             ※引数の詳細は親クラスのドキュメントを参照してください。
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        final ConfigurableApplicationContext ctx = SpringApplication.run(Enum2SwitchCaseMakingTool.class, args);

        final Enum2SwitchCaseMakingTool tool = ctx.getBean(Enum2SwitchCaseMakingTool.class);

        /* 初期化 */
        tool.initialize();

        /* 実行 */
        tool.execute();

        ctx.close();

    }

    /**
     * <h3>コンストラクタ</h3>
     * <p>
     * 列挙型からcase文作成ツールのインスタンスを生成します。
     * </p>
     * <p>
     * 親クラスのコンストラクタを呼び出し、ツール名を設定します。 このコンストラクタによって、デフォルトのテンプレートパスも設定されます。
     * </p>
     */
    public Enum2SwitchCaseMakingTool() {

        super(Enum2SwitchCaseMakingTool.TOOL_NAME);

    }

    /**
     * <h3>列挙型からcase文作成サービスを返す</h3>
     * <p>
     * AbstractDynamicTemplateConversionToolの抽象メソッドを実装し、DI（依存性注入）された 列挙型からcase文作成サービスのインスタンスを返します。
     * </p>
     * <p>
     * このメソッドは親クラスの処理から呼び出され、実際のcase文生成処理を担当する サービスを提供します。
     * </p>
     *
     * @return 列挙型からcase文作成サービス このツールが使用する列挙型からcase文作成サービスのインスタンス
     */
    @Override
    protected Enum2SwitchCaseMakingService getIoService() {

        final Enum2SwitchCaseMakingService result = this.enum2SwitchCaseMakingService;

        return result;

    }
}
