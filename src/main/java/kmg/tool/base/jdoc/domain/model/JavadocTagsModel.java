package kmg.tool.base.jdoc.domain.model;

import java.util.List;
import java.util.regex.Pattern;

import kmg.core.infrastructure.types.KmgJavadocTagTypes;

/**
 * Javadocタグ一覧情報インターフェース<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
public interface JavadocTagsModel {

    /**
     * 行頭のアスタリスクのみの行を検出する正規表現パターン
     *
     * @since 0.2.0
     */
    String PATTERN_LINE_START_ASTERISK_ONLY = "^\\*$"; //$NON-NLS-1$

    /**
     * 行頭のアスタリスクを検出する正規表現パターン
     *
     * @since 0.2.0
     */
    String PATTERN_LINE_START_ASTERISK = "^\\*"; //$NON-NLS-1$

    /**
     * Javadocタグを抽出する正規表現パターン
     *
     * @since 0.2.0
     */
    String TAG_PATTERN = "\\s+\\*\\s+(@\\w+)\\s+([^\\s\\n]+)(?:\\s+([^@\\n]+))?"; //$NON-NLS-1$

    /**
     * コンパイル済みのJavadocタグパターン
     *
     * @since 0.2.0
     */
    Pattern COMPILED_TAG_PATTERN = Pattern.compile(JavadocTagsModel.TAG_PATTERN);

    /**
     * 指定されたタグに対応する既存のJavadocタグを検索する<br>
     *
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @return Javadocタグモデルのリスト
     */
    List<JavadocTagModel> getJavadocTagModelList();
}
