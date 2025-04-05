package kmg.tool.domain.model;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Javadocタグ一覧情報<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JavadocTagsModel {

    /**
     * Javadocタグを抽出する正規表現パターン
     *
     * @since 0.1.0
     */
    String TAG_PATTERN = "\\s+\\*\\s+(@\\w+)\\s+([^\\s\\n]+)(?:\\s+([^@\\n]+))?";

    /**
     * コンパイル済みのJavadocタグパターン
     *
     * @since 0.1.0
     */
    Pattern COMPILED_TAG_PATTERN = Pattern.compile(JavadocTagsModel.TAG_PATTERN);

    /**
     * コンパイル済みのJavadocタグパターンを返す<br>
     *
     * @return コンパイル済みのJavadocタグパターン
     */
    default Pattern getCompiledTagPattern() {

        final Pattern result = JavadocTagsModel.COMPILED_TAG_PATTERN;
        return result;

    }

    /**
     * Javadocタグモデルのリストを返す<br>
     *
     * @return Javadocタグモデルのリスト
     */
    List<JavadocTagModel> getJavadocTagModelList();

    /**
     * Javadocタグを抽出する正規表現パターンを返す<br>
     *
     * @return Javadocタグを抽出する正規表現パターン
     */
    default String getTagPattern() {

        final String result = JavadocTagsModel.TAG_PATTERN;
        return result;

    }

    /**
     * Javadocタグモデルのリストを設定する<br>
     *
     * @param javadocTagModelList
     *                            Javadocタグモデルのリスト
     */
    void setJavadocTagModelList(List<JavadocTagModel> javadocTagModelList);

}
