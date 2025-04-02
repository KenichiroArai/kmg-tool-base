package kmg.tool.domain.model.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.domain.model.JavadocReplacementModel;
import kmg.tool.domain.model.JavadocTagConfigModel;
import kmg.tool.domain.model.JavadocTagsModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc置換モデル<br>
 *
 * @author KenichiroArai
 */
public class JavadocReplacementModelImpl implements JavadocReplacementModel {

    /** 元のJavadoc */
    private final String sourceJavadoc;

    /** 元のコード */
    private final String sourceCode;

    /** 置換用識別子 */
    private final UUID identifier;

    /** Java区分 */
    private JavaClassificationTypes javaClassification;

    /** 置換後のJavadoc */
    private String replacedJavadoc;

    /** Javadocタグモデル */
    private final JavadocTagsModel javadocTagsModel;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @param sourceJavadoc
     *                         元のJavadoc
     * @param sourceCode
     *                         元のコード
     * @param javadocTagsModel
     *                         Javadocタグモデル
     */
    public JavadocReplacementModelImpl(final String sourceJavadoc, final String sourceCode,
        final JavadocTagsModel javadocTagsModel) {

        this.sourceJavadoc = sourceJavadoc;
        this.sourceCode = sourceCode;
        this.identifier = UUID.randomUUID();
        this.javadocTagsModel = javadocTagsModel;

    }

    /**
     * 置換後のJavadocを作成する。<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean createReplacedJavadoc() throws KmgToolException {

        boolean result = false;

        /* 既存のJavadocの解析 */
        final Map<String, StringBuilder> existingTags = new HashMap<>();
        final StringBuilder              description  = new StringBuilder();
        final String[]                   lines        = this.sourceJavadoc.split("\\r?\\n");
        boolean                          isFirstLine  = true;
        String                           currentTag   = null;

        for (String line : lines) {

            line = line.trim();

            if (isFirstLine) {

                description.append(line).append("\n");
                isFirstLine = false;
                continue;

            }

            if ("*/".equals(line)) {

                continue;

            }

            if (line.startsWith("*")) {

                line = line.substring(1).trim();

                if (line.startsWith("@")) {

                    // 新しいタグの開始
                    final String[] parts = line.substring(1).split("\\s+", 2);
                    currentTag = parts[0];
                    final String tagValue = parts.length > 1 ? parts[1] : "";

                    // 同じタグが複数回出現する可能性があるため、既存の値があれば保持
                    if (existingTags.containsKey(currentTag)) {

                        existingTags.get(currentTag).append("\n * @").append(currentTag).append(" ").append(tagValue);

                    } else {

                        existingTags.put(currentTag, new StringBuilder(tagValue));

                    }

                } else if (currentTag != null && !line.isEmpty()) {

                    // 現在のタグの説明文の続き
                    existingTags.get(currentTag).append("\n * ").append(line);

                } else {

                    // 説明部分の処理（空行も含む）
                    description.append(" *");

                    if (!line.isEmpty()) {

                        description.append(" ").append(line);

                    }
                    description.append("\n");

                }

            }

        }

        /* 新しいJavadocの生成 */
        final StringBuilder newJavadoc = new StringBuilder();
        newJavadoc.append(description);

        /* 既存のタグをすべて保持 */
        final Set<String> processedTags = new HashSet<>();

        /* YAMLで定義されたタグの処理 */
        for (final JavadocTagConfigModel tagConfig : this.javadocTagsModel.getJavadocTagConfigModels()) {

            final String        tagName       = tagConfig.getName();
            final String        tagValue      = tagConfig.getText();
            final StringBuilder existingValue = existingTags.get(tagName);

            // タグの追加判断
            if ("never".equals(tagConfig.getOverwrite()) && (existingValue != null)) {

                // 既存のタグを保持
                newJavadoc.append(" * @").append(tagName).append(" ").append(existingValue).append("\n");

            } else {

                if ("ifLower".equals(tagConfig.getOverwrite())) {

                    // TODO KenichiroArai 2025/04/02 未実装
                }
                // バージョン比較ロジックが必要な場合は実装
                newJavadoc.append(" * @").append(tagName).append(" ").append(tagValue).append("\n");

            }

            processedTags.add(tagName);

        }

        /* 既存のタグで、YAMLに定義されていないものを保持 */
        for (final Map.Entry<String, StringBuilder> entry : existingTags.entrySet()) {

            if (!processedTags.contains(entry.getKey())) {

                newJavadoc.append(" * @").append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");

            }

        }

        // 最後の行を追加
        this.replacedJavadoc = newJavadoc.toString();
        result = true;
        return result;

    }

    /**
     * 置換用識別子を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 置換用識別子
     */
    @Override
    public UUID getIdentifier() {

        final UUID result = this.identifier;
        return result;

    }

    /**
     * Java区分を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Java区分
     */
    @Override
    public JavaClassificationTypes getJavaClassification() {

        final JavaClassificationTypes result = this.javaClassification;
        return result;

    }

    /**
     * 置換後のJavadocを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 置換後のJavadoc
     */
    @Override
    public String getReplacedJavadoc() {

        final String result = this.replacedJavadoc;
        return result;

    }

    /**
     * 元のコードを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 元のコード
     */
    @Override
    public String getSourceCode() {

        final String result = this.sourceCode;
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

    /**
     * Java区分を特定する<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return true：区分が特定できた、false：区分がNONE
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean specifyJavaClassification() throws KmgToolException {

        boolean result = false;

        /* Java区分の初期化 */
        this.javaClassification = JavaClassificationTypes.NONE;

        /* コードを行ごとに確認する */

        // コードを行ごとに取得する。空行は除外する。
        final String[] codeLines = Arrays.stream(KmgDelimiterTypes.LINE_SEPARATOR.split(this.sourceCode))
            .filter(KmgString::isNotBlank).toArray(String[]::new);

        for (final String codeLine : codeLines) {

            // Java区分を判別
            this.javaClassification = JavaClassificationTypes.identify(codeLine);

            // Javadoc対象外か
            if (this.javaClassification.isNotJavadocTarget()) {
                // 対象外の場合

                continue;

            }

            result = true;
            break;

        }

        return result;

    }

}
