package kmg.tool.base.fldcrt.presentation.ui.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.tool.base.dtc.presentation.ui.cli.AbstractDtcTool;
import kmg.tool.base.fldcrt.application.service.FieldCreationService;

/**
 * <h2>フィールド作成ツール</h2>
 * <p>
 * Javaクラスのフィールドを自動生成するためのツールです。
 * </p>
 * <p>
 * このツールは入力ファイルとテンプレートファイルを使用して、フィールド定義を含む出力ファイルを生成します。
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
public class FieldCreationTool extends AbstractDtcTool {

    /**
     * ツール名
     *
     * @since 0.2.0
     */
    private static final String TOOL_NAME = "フィールド作成ツール"; //$NON-NLS-1$

    /**
     * フィールド作成サービス
     *
     * @since 0.2.0
     */
    @Autowired
    private FieldCreationService fieldCreationService;

    /**
     * エントリポイント
     *
     * @since 0.2.0
     *
     * @param args
     *             コマンドライン引数
     */
    public static void main(final String[] args) {

        @SuppressWarnings("resource")
        final ConfigurableApplicationContext ctx = SpringApplication.run(FieldCreationTool.class, args);

        final FieldCreationTool tool = ctx.getBean(FieldCreationTool.class);

        /* 初期化 */
        tool.initialize();

        /* 実行 */
        tool.execute();

        ctx.close();

    }

    /**
     * コンストラクタ
     *
     * @since 0.2.0
     */
    public FieldCreationTool() {

        super(FieldCreationTool.TOOL_NAME);

    }

    /**
     * フィールド作成サービスを返す
     *
     * @since 0.2.0
     *
     * @return フィールド作成サービス
     */
    @Override
    protected FieldCreationService getIoService() {

        final FieldCreationService result = this.fieldCreationService;
        return result;

    }
}
