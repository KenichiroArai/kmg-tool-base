package kmg.tool.base.jdts.application.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.jdts.application.model.JdtsBlockModel;
import kmg.tool.base.jdts.application.model.JdtsCodeModel;

/**
 * Javadocタグ設定のコードモデル<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@Component
@Scope("prototype")
public class JdtsCodeModelImpl implements JdtsCodeModel {

    /**
     * Javadocコメント開始文字列
     *
     * @since 0.2.0
     */
    private static final String JAVADOC_START = "/**"; //$NON-NLS-1$

    /**
     * Javadocブロック分割用の正規表現フォーマット
     *
     * @since 0.2.0
     */
    private static final String JAVADOC_BLOCK_SPLIT_FORMAT = "(^|\\s+)%s\\s+"; //$NON-NLS-1$

    /**
     * Javadocブロック分割用の正規表現パターン
     *
     * @since 0.2.0
     */
    private static final String JAVADOC_BLOCK_SPLIT_PATTERN
        = String.format(JdtsCodeModelImpl.JAVADOC_BLOCK_SPLIT_FORMAT, Pattern.quote(JdtsCodeModelImpl.JAVADOC_START));

    /**
     * ロガー
     *
     * @since 0.2.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @since 0.2.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * オリジナルコード
     *
     * @since 0.2.0
     */
    private final String orgCode;

    /**
     * Javadocタグ設定のブロックモデルリスト
     *
     * @since 0.2.0
     */
    private final List<JdtsBlockModel> jdtsBlockModels;

    /**
     * コンストラクタ
     *
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @param code
     *               コード
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
     * @since 0.2.0
     *
     * @Override
     *
     * @author KenichiroArai
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
     * @since 0.2.0
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
     * @since 0.2.0
     */

    @Override
    public void parse() {

        // 「/**」でブロックに分ける
        final String[] blocks = this.orgCode.split(JdtsCodeModelImpl.JAVADOC_BLOCK_SPLIT_PATTERN);

        // ブロックの0番目はJavadocではないので、1番目から進める
        for (int i = 1; i < blocks.length; i++) {

            final JdtsBlockModel jdtsBlockModel = new JdtsBlockModelImpl(blocks[i]);

            // ブロックモデルを解析する
            final boolean blockParseResult = jdtsBlockModel.parse();

            // 解析が対象外か
            if (!blockParseResult) {
                // 対象外の場合

                /* ログの出力 */
                final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG13000;
                final Object[]           logMsgArgs  = {
                    jdtsBlockModel.getId(), jdtsBlockModel.getOrgBlock(),
                };
                final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
                this.logger.warn(logMsg);

                continue;

            }

            this.jdtsBlockModels.add(jdtsBlockModel);

        }

    }

}
