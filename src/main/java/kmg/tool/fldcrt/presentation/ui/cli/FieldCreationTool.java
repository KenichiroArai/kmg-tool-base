package kmg.tool.fldcrt.presentation.ui.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.tool.dtc.presentation.ui.cli.AbstractDtcTool;
import kmg.tool.fldcrt.application.service.FieldCreationService;

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
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
    "kmg"
})
public class FieldCreationTool extends AbstractDtcTool {

    /** ツール名 */
    private static final String TOOL_NAME = "フィールド作成ツール"; //$NON-NLS-1$

    /** フィールド作成サービス */
    @Autowired
    private FieldCreationService fieldCreationService;

    /**
     * エントリポイント
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
     */
    public FieldCreationTool() {

        super(FieldCreationTool.TOOL_NAME);

    }

    /**
     * フィールド作成サービスを返す
     *
     * @return フィールド作成サービス
     */
    @Override
    protected FieldCreationService getIoService() {

        final FieldCreationService result = this.fieldCreationService;
        return result;

    }
}
