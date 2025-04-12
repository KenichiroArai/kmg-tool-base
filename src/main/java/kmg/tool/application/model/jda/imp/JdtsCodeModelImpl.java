package kmg.tool.application.model.jda.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import kmg.tool.application.model.jda.JdtsBlockModel;
import kmg.tool.application.model.jda.JdtsCodeModel;
import kmg.tool.infrastructure.exception.KmgToolException;

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

    // TODO KenichiroArai 2025/04/13 メソッド名を見直す
    /**
     * 解析する
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */

    @Override
    public void parse() throws KmgToolException {

        // 「/**」でブロックに分ける
        // TODO KenichiroArai 2025/04/03 ハードコード
        final String[] blocks = this.orgCode.split(String.format("%s\\s+", Pattern.quote("/**")));

        // ブロックの0番目はJavadocではないので、1番目から進める
        for (int i = 1; i < blocks.length; i++) {

            final JdtsBlockModel jdtsBlockModel = new JdtsBlockModelImpl(blocks[i]);
            this.jdtsBlockModels.add(jdtsBlockModel);

            jdtsBlockModel.parse();

        }

    }

}
