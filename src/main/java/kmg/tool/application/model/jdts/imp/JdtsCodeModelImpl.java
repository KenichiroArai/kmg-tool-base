package kmg.tool.application.model.jdts.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.application.model.jdts.JdtsBlockModel;
import kmg.tool.application.model.jdts.JdtsCodeModel;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolLogMsgTypes;

/**
 * Javadocタグ設定のコードモデル<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JdtsCodeModelImpl implements JdtsCodeModel {

    /** Javadocコメント開始文字列 */
    private static final String JAVADOC_START = "/**"; //$NON-NLS-1$

    /** Javadocブロック分割用の正規表現フォーマット */
    private static final String JAVADOC_BLOCK_SPLIT_FORMAT = "%s\\s+"; //$NON-NLS-1$

    /**
     * ロガー
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /** オリジナルコード */
    private final String orgCode;

    /** Javadocタグ設定のブロックモデルリスト */
    private final List<JdtsBlockModel> jdtsBlockModels;

    /**
     * コンストラクタ
     *
     * @param code
     *             コード
     */
    public JdtsCodeModelImpl(final String code) {

        this(LoggerFactory.getLogger(JdtsCodeModelImpl.class), code);

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @param code
     *             コード
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param logger
     *               ロガー
     */
    protected JdtsCodeModelImpl(final Logger logger, final String code) {

        this.logger = logger;

        this.orgCode = code;

        this.jdtsBlockModels = new ArrayList<>();

    }

    /**
     * Javadocタグ設定のブロックモデルリストを返す<br>
     * *
     *
     * @Override
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocタグ設定のブロックモデルリスト
     */
    @Override
    public List<JdtsBlockModel> getJdtsBlockModels() {

        final List<JdtsBlockModel> result = this.jdtsBlockModels;
        return result;

    }

    /**
     * オリジナルコードを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return オリジナルコード
     */
    @Override
    public String getOrgCode() {

        final String result = this.orgCode;
        return result;

    }

    /**
     * 解析する
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */

    @Override
    public void parse() throws KmgToolMsgException {

        // 「/**」でブロックに分ける
        final String[] blocks = this.orgCode.split(String.format(JdtsCodeModelImpl.JAVADOC_BLOCK_SPLIT_FORMAT,
            Pattern.quote(JdtsCodeModelImpl.JAVADOC_START)));

        // ブロックの0番目はJavadocではないので、1番目から進める
        for (int i = 1; i < blocks.length; i++) {

            final JdtsBlockModel jdtsBlockModel = new JdtsBlockModelImpl(blocks[i]);
            this.jdtsBlockModels.add(jdtsBlockModel);

            // ブロックモデルを解析する
            final boolean blockParseResult = jdtsBlockModel.parse();

            // 解析に失敗したか
            if (!blockParseResult) {
                // 解析に失敗した場合

                /* ログの出力 */
                final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG33000;
                final Object[]           logMsgArgs  = {
                    jdtsBlockModel.getId(), jdtsBlockModel.getOrgBlock(),
                };
                final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
                this.logger.warn(logMsg);

            }

        }

    }

}
