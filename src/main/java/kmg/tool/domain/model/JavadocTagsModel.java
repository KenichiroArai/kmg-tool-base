package kmg.tool.domain.model;

import java.util.List;
import java.util.regex.Pattern;

import kmg.core.infrastructure.types.KmgJavadocTagTypes;

/**
 * Javadocタグ一覧情報インターフェース<br>
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
     * 指定されたタグに対応する既存のJavadocタグを検索する<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @param tag
     *            Javadocタグの種類
     *
     * @return 既存のJavadocタグモデル。存在しない場合はnull
     */
    JavadocTagModel findByTag(KmgJavadocTagTypes tag);

    /**
     * Javadocタグモデルのリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return Javadocタグモデルのリスト
     */
    List<JavadocTagModel> getJavadocTagModelList();
}
