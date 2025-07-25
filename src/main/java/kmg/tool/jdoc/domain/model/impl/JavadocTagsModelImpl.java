package kmg.tool.jdoc.domain.model.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.jdoc.domain.model.JavadocTagModel;
import kmg.tool.jdoc.domain.model.JavadocTagsModel;
import kmg.tool.jdoc.domain.types.JavadocGroupIndexTypes;

/**
 * Javadocタグ一覧情報<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JavadocTagsModelImpl implements JavadocTagsModel {

    /** Javadocタグモデルのリスト */
    private final List<JavadocTagModel> javadocTagModelList;

    /**
     * デフォルトコンストラクタ<br>
     */
    public JavadocTagsModelImpl() {

        this.javadocTagModelList = new ArrayList<>();

    }

    /**
     * コンストラクタ<br>
     *
     * @param sourceJavadoc
     *                      Javadoc
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    public JavadocTagsModelImpl(final String sourceJavadoc) throws KmgToolMsgException {

        this.javadocTagModelList = new ArrayList<>();

        /* 引数チェック */
        if (sourceJavadoc == null) {

            return;

        }

        // @タグを抽出する正規表現パターン
        // グループ1: タグ名
        // グループ2: 値
        // グループ3: 説明（オプション）
        final Pattern compiledTagPattern = JavadocTagsModel.COMPILED_TAG_PATTERN;
        final Matcher compiledTagMatcher = compiledTagPattern.matcher(sourceJavadoc);

        while (compiledTagMatcher.find()) {

            // Javadocタグの対象文字列
            final String srcPatternStr = compiledTagMatcher.group(JavadocGroupIndexTypes.WHOLE.get());
            // 改行で分割して2行目の処理を行う
            final String targetStr = Arrays.stream(KmgDelimiterTypes.REGEX_LINE_SEPARATOR.split(srcPatternStr))
                .map(line -> line.trim().replaceAll(JavadocTagsModel.PATTERN_LINE_START_ASTERISK_ONLY, KmgString.EMPTY))
                .filter(KmgString::isNotBlank).collect(Collectors.joining(KmgDelimiterTypes.LINE_SEPARATOR.get()));

            // タグ
            final KmgJavadocTagTypes tag
                = KmgJavadocTagTypes.getEnum(compiledTagMatcher.group(JavadocGroupIndexTypes.TAG_NAME.get()));

            // 指定値
            final String value = compiledTagMatcher.group(JavadocGroupIndexTypes.VALUE.get());

            // 説明取得
            final String description
                = Optional.ofNullable(compiledTagMatcher.group(JavadocGroupIndexTypes.DESCRIPTION.get()))
                    .map(s -> s.trim().replaceFirst(JavadocTagsModel.PATTERN_LINE_START_ASTERISK, KmgString.EMPTY))
                    .orElse(KmgString.EMPTY).trim();

            final JavadocTagModel javadocTagMode = new JavadocTagModelImpl(targetStr, tag, value, description);
            this.javadocTagModelList.add(javadocTagMode);

        }

    }

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
    @Override
    public JavadocTagModel findByTag(final KmgJavadocTagTypes tag) {

        JavadocTagModel result = null;

        /* 引数チェック */
        if (tag == null) {

            return result;

        }

        /* タグの検索 */
        result = this.javadocTagModelList.stream().filter(model -> model.getTag() == tag).findFirst().orElse(null);

        return result;

    }

    /**
     * Javadocタグモデルのリストを返す<br>
     *
     * @return Javadocタグモデルのリスト
     */
    @Override
    public List<JavadocTagModel> getJavadocTagModelList() {

        final List<JavadocTagModel> result = this.javadocTagModelList;
        return result;

    }

}
