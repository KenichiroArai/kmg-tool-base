package kmg.tool.base.msgtpcrt.presentation.ul.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.tool.base.dtc.presentation.ui.cli.AbstractDtcTool;
import kmg.tool.base.msgtpcrt.service.MessageTypesCreationService;

/**
 * <h2>メッセージの種類作成ツール</h2>
 * <p>
 * メッセージの種類を定義するYMLファイルを自動生成するためのツールです。
 * </p>
 * <p>
 * このツールは入力ファイルとテンプレートファイルを使用して、メッセージ種類の定義を含む出力ファイルを生成します。
 * </p>
 * <p>
 * AbstractDynamicTemplateConversionToolを継承しており、動的テンプレート変換処理を実装しています。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class MessageTypesCreationTool extends AbstractDtcTool {

    /**
     * <h3>ツール名</h3>
     * <p>
     * このツールの表示名を定義します。
     * </p>
     *
     * @since 0.2.0
     */
    private static final String TOOL_NAME = "メッセージの種類作成ツール"; //$NON-NLS-1$

    /**
     * <h3>メッセージの種類作成サービス</h3>
     * <p>
     * メッセージ種類の定義を生成するためのサービスです。
     * </p>
     *
     * @since 0.2.0
     */
    @Autowired
    private MessageTypesCreationService messageTypesCreationService;

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
     * @since 0.2.0
     *
     * @param args
     *             コマンドライン引数。入力ファイルパス、テンプレートファイルパス、出力ファイルパスなどを指定できます。 <br>
     *             ※引数の詳細は親クラスのドキュメントを参照してください。
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        final ConfigurableApplicationContext ctx = SpringApplication.run(MessageTypesCreationTool.class, args);

        final MessageTypesCreationTool tool = ctx.getBean(MessageTypesCreationTool.class);

        /* 初期化 */
        tool.initialize();

        /* 実行 */
        tool.execute();

        ctx.close();

    }

    /**
     * <h3>コンストラクタ</h3>
     * <p>
     * メッセージの種類作成ツールのインスタンスを生成します。
     * </p>
     * <p>
     * 親クラスのコンストラクタを呼び出し、ツール名を設定します。 このコンストラクタによって、デフォルトのテンプレートパスも設定されます。
     * </p>
     *
     * @since 0.2.0
     */
    public MessageTypesCreationTool() {

        super(MessageTypesCreationTool.TOOL_NAME);

    }

    /**
     * <h3>メッセージの種類作成サービスを返す</h3>
     * <p>
     * AbstractDynamicTemplateConversionToolの抽象メソッドを実装し、DI（依存性注入）された メッセージの種類作成サービスのインスタンスを返します。
     * </p>
     * <p>
     * このメソッドは親クラスの処理から呼び出され、実際のメッセージ種類生成処理を担当する サービスを提供します。
     * </p>
     *
     * @since 0.2.0
     *
     * @return メッセージの種類作成サービス このツールが使用するメッセージの種類作成サービスのインスタンス
     */
    @Override
    protected MessageTypesCreationService getIoService() {

        final MessageTypesCreationService result = this.messageTypesCreationService;

        return result;

    }
}
