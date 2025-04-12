package kmg.tool.application.service.impl;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.logic.JdtsReplLogic;
import kmg.tool.application.model.jda.JdtsBlockModel;
import kmg.tool.application.model.jda.JdtsCodeModel;
import kmg.tool.application.model.jda.JdtsConfigsModel;
import kmg.tool.application.service.JdtsReplService;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグ設定の入出力サービス<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * Replは、Replacementの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@Service
public class JdtsReplServiceImpl implements JdtsReplService {

    /** Javadocタグ設定の入出力ロジック */
    @Autowired
    private JdtsReplLogic jdtsReplLogic;

    /** Javadocタグ設定の構成モデル */
    private JdtsConfigsModel jdtsConfigsModel;

    /** Javadocタグ設定のコードモデル */
    private JdtsCodeModel jdtsCodeModel;

    /** 置換後のコード */
    private String replaceCode;

    /** 合計行数 */
    private long totalRows;

    /**
     * デフォルトコンストラクタ
     */
    public JdtsReplServiceImpl() {

        this.replaceCode = KmgString.EMPTY;
        this.totalRows = 0;

    }

    /**
     * Javadocタグ設定の構成モデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocタグ設定の構成モデル
     */
    @Override
    public JdtsConfigsModel getJdtsConfigsModel() {

        final JdtsConfigsModel result = this.jdtsConfigsModel;
        return result;

    }

    /**
     * 合計行数を返す。
     *
     * @return 合計行数
     */
    @Override
    public long getTotalRows() {

        final long result = this.totalRows;
        return result;

    }

    /**
     * 初期化する
     *
     * @param jdtsConfigsModel
     *                         Javadocタグ設定の構成モデル
     * @param jdtsCodeModel
     *                         Javadocタグ設定のコードモデル
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final JdtsConfigsModel jdtsConfigsModel, final JdtsCodeModel jdtsCodeModel)
        throws KmgToolException {

        boolean result = false;

        this.jdtsConfigsModel = jdtsConfigsModel;
        this.jdtsCodeModel = jdtsCodeModel;

        this.replaceCode = this.jdtsCodeModel.getOrgCode();

        this.totalRows = 0;

        result = true;
        return result;

    }

    /**
     * 内容を置換した値を返す。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public String replace() throws KmgToolException {

        String result = null;

        /* オリジナルコードのJavadocブロック部分を識別子に置換する */
        for (final JdtsBlockModel jdtsBlockModel : this.jdtsCodeModel.getJdtsBlockModels()) {

            this.replaceCode = this.replaceCode.replaceFirst(
                Pattern.quote(jdtsBlockModel.getJavadocModel().getSrcJavadoc()), jdtsBlockModel.getId().toString());

        }

        /* 識別子を置換後のJavadocブロックに置換する */
        for (final JdtsBlockModel jdtsBlockModel : this.jdtsCodeModel.getJdtsBlockModels()) {

            this.jdtsReplLogic.initialize(this.jdtsConfigsModel, jdtsBlockModel);
            final String replaceJavadocBlock = this.jdtsReplLogic.createReplacedJavadoc();
            this.replaceCode = this.replaceCode.replace(jdtsBlockModel.getId().toString(), replaceJavadocBlock);

        }

        this.totalRows += KmgDelimiterTypes.LINE_SEPARATOR.split(this.replaceCode).length;

        result = this.replaceCode;

        return result;

    }
}
