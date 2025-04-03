package kmg.tool.domain.model.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kmg.tool.domain.model.JavadocTagsModel;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグモデル<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JavadocTagModelImpl implements JavadocTagsModel {

    /** 元のJavadoc */
    private final String sourceJavadoc;

    /** タグ */
    private final String tag;

    /** 指定値 */
    private final String value;

    /** 説明 */
    private final String description;

    /**
     * コンストラクタ<br>
     *
     * @param sourceJavadoc
     *                      Javadoc
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    public JavadocTagModelImpl(final String sourceJavadoc) throws KmgToolException {

        this.sourceJavadoc = sourceJavadoc;

        // @タグを抽出する正規表現パターン
        // グループ1: タグ名
        // グループ2: 値
        // グループ3: 説明（オプション）
        // TODO KenichiroArai 2025/04/03 ハードコード
        final String  pattern = "@(\\w+)\\s+([^\\s\\n]+)(?:\\s+([^@\\n]+))?";
        final Pattern p       = java.util.regex.Pattern.compile(pattern);
        final Matcher m       = p.matcher(sourceJavadoc);

        if (!m.find()) {

            // TODO KenichiroArai 2025/04/03 メッセージ
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs);

        }

        // TODO KenichiroArai 2025/04/03 ハードコード
        this.tag = m.group(1);
        this.value = m.group(2);
        this.description = (m.group(3) != null) ? m.group(3).trim() : "";

    }

    /**
     * 説明を返す<br>
     *
     * @return 説明
     */
    @Override
    public String getDescription() {

        final String result = this.description;
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
     * タグを返す<br>
     *
     * @return タグ
     */
    @Override
    public String getTag() {

        final String result = this.tag;
        return result;

    }

    /**
     * 指定値を返す<br>
     *
     * @return 指定値
     */
    @Override
    public String getValue() {

        final String result = this.value;
        return result;

    }
}
