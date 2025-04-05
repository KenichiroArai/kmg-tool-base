package kmg.tool.domain.model.impl;

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
import kmg.tool.domain.model.JavadocModel;
import kmg.tool.domain.model.JavadocTagModel;
import kmg.tool.domain.model.JavadocTagsModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocモデル<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JavadocModelImpl implements JavadocModel {

    /** 元のJavadoc */
    private final String sourceJavadoc;

    /** Javadocタグモデルのリスト */
    private final List<JavadocTagModel> javadocTagModelList;

    /**
     * コンストラクタ<br>
     *
     * @param sourceJavadoc
     *                      Javadoc
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    public JavadocModelImpl(final String sourceJavadoc) throws KmgToolException {

        this.javadocTagModelList = new ArrayList<>();
        this.sourceJavadoc = sourceJavadoc;

        // @タグを抽出する正規表現パターン
        // グループ1: タグ名
        // グループ2: 値
        // グループ3: 説明（オプション）
        final Pattern compiledTagPattern = JavadocTagsModel.COMPILED_TAG_PATTERN;
        final Matcher compiledTagMatcher = compiledTagPattern.matcher(sourceJavadoc);

        // TODO KenichiroArai 2025/04/03 デバッグ
        System.out.println("----- 元のJavadoc -----");

        while (compiledTagMatcher.find()) {

            // TODO KenichiroArai 2025/04/03 ハードコード

            // Javadocタグの対象文字列
            final String srcPatternStr = compiledTagMatcher.group(0); // 元のパターンに一致する文字列
            // 改行で分割して2行目の処理を行う
            final String targetStr = Arrays.stream(KmgDelimiterTypes.REGEX_LINE_SEPARATOR.split(srcPatternStr))
                .map(line -> line.trim().replaceAll("^\\*$", KmgString.EMPTY)).filter(KmgString::isNotBlank)
                .collect(Collectors.joining(KmgDelimiterTypes.LINE_SEPARATOR.get()));

            // タグ
            final KmgJavadocTagTypes tag = KmgJavadocTagTypes.getEnum(compiledTagMatcher.group(1));

            // 指定値
            final String value = compiledTagMatcher.group(2);

            // 説明取得
            final String description = Optional.ofNullable(compiledTagMatcher.group(3))
                .map(s -> s.trim().replaceFirst("^\\*", KmgString.EMPTY)).orElse(KmgString.EMPTY).trim();

            final JavadocTagModel javadocTagMode = new JavadocTagModelImpl(targetStr, tag, value, description);
            this.javadocTagModelList.add(javadocTagMode);

            // TODO KenichiroArai 2025/04/03 デバッグ
            System.out.println(String.format("対象文字列: %s, タグ: %s, 指定値: %s, 説明: %s", javadocTagMode.getTargetStr(),
                javadocTagMode.getTag(), javadocTagMode.getValue(), javadocTagMode.getDescription()));

        }

    }

    /**
     * Javadocタグモデルのリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocタグモデルのリスト
     */
    @Override
    public List<JavadocTagModel> getJavadocTagModelList() {

        final List<JavadocTagModel> result = this.javadocTagModelList;
        return result;

    }

    /**
     * 元のJavadocを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 元のJavadoc
     */
    @Override
    public String getSourceJavadoc() {

        final String result = this.sourceJavadoc;
        return result;

    }

}
