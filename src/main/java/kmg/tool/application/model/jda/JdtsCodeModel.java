package kmg.tool.application.model.jda;

import java.util.List;

/**
 * Javadocタグ設定のコードモデルインタフェース<br>
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
public interface JdtsCodeModel {

    // TODO KenichiroArai 2025/04/12 実装中

    /**
     * Javadocタグ設定のブロックモデルリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocタグ設定のブロックモデルリスト
     */
    List<JdtsBlockModel> getJdtsBlockModels();

}
