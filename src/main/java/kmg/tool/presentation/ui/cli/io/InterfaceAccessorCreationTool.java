package kmg.tool.presentation.ui.cli.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.tool.application.service.io.AccessorCreationService;

/**
 * <h2>インタフェースのアクセサ作成ツール</h2>
 * <p>
 * Javaクラスのフィールドに対するインタフェース用のアクセサメソッド（getterおよびsetter）を自動生成するためのツールです。
 * </p>
 * <p>
 * このツールは入力ファイルとテンプレートファイルを使用して、アクセサメソッドを含む出力ファイルを生成します。
 * </p>
 * <p>
 * AbstractTwo2OneToolを継承しており、2つの入力ファイルから1つの出力ファイルを生成する処理を実装しています。
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
// @SpringBootApplication
public class InterfaceAccessorCreationTool extends AbstractDynamicTemplateConversionTool {

    /**
     * <h3>ツール名</h3>
     * <p>
     * このツールの表示名を定義します。
     * </p>
     */
    private static final String TOOL_NAME = "インタフェースのアクセサ作成ツール";

    /**
     * <h3>アクセサ作成サービス</h3>
     * <p>
     * フィールド定義からアクセサメソッドを生成するためのサービスです。
     * </p>
     */
    @Autowired
    private AccessorCreationService accessorCreationService;

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
        final ConfigurableApplicationContext ctx = SpringApplication.run(InterfaceAccessorCreationTool.class, args);

        final InterfaceAccessorCreationTool tool = ctx.getBean(InterfaceAccessorCreationTool.class);

        /* 初期化 */
        tool.initialize();

        /* 実行 */
        tool.execute();

        ctx.close();

    }

    /**
     * <h3>コンストラクタ</h3>
     * <p>
     * インタフェースのアクセサ作成ツールのインスタンスを生成します。
     * </p>
     * <p>
     * 親クラスのコンストラクタを呼び出し、ツール名を設定します。 このコンストラクタによって、デフォルトのテンプレートパスも設定されます。
     * </p>
     */
    public InterfaceAccessorCreationTool() {

        super(InterfaceAccessorCreationTool.TOOL_NAME);

    }

    /**
     * <h3>アクセサ作成サービスを返す</h3>
     * <p>
     * AbstractTwo2OneToolの抽象メソッドを実装し、DI（依存性注入）された アクセサ作成サービスのインスタンスを返します。
     * </p>
     * <p>
     * このメソッドは親クラスの処理から呼び出され、実際のアクセサ生成処理を担当する サービスを提供します。
     * </p>
     *
     * @return アクセサ作成サービス このツールが使用するアクセサ作成サービスのインスタンス
     */
    @Override
    protected AccessorCreationService getIoService() {

        final AccessorCreationService result = this.accessorCreationService;

        return result;

    }
}
