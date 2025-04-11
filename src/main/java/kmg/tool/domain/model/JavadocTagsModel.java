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

    /** 行頭のアスタリスクのみの行を検出する正規表現パターン */
    String PATTERN_LINE_START_ASTERISK_ONLY = "^\\*$";

    /** 行頭のアスタリスクを検出する正規表現パターン */
    String PATTERN_LINE_START_ASTERISK = "^\\*";

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
     * Javadocタグモデルのリストを返す<br>
     *
     * @return Javadocタグモデルのリスト
     */
    List<JavadocTagModel> getJavadocTagModelList();

}
