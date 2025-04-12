package kmg.tool.application.model.jda.imp;

import java.util.List;

import kmg.tool.application.model.jda.JdtsBlockModel;
import kmg.tool.application.model.jda.JdtsCodeModel;

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

    // TODO KenichiroArai 2025/04/12 実装中

    /** Javadocタグ設定のブロックモデルリスト */
    private List<JdtsBlockModel> jdtsBlockModels;

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

}
