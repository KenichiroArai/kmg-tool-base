package kmg.tool.application.logic.io.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.tool.application.logic.io.AccessorCreationLogic;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * アクセサ作成ロジック<br>
 *
 * @author KenichiroArai
 */
@Service
public class AccessorCreationLogicImpl implements AccessorCreationLogic {

    /** Javadocコメントの正規表現パターン */
    private static final String JAVADOC_COMMENT_PATTERN = "/\\*\\* (\\S+)";

    /** privateフィールド宣言の正規表現パターン */
    private static final String PRIVATE_FIELD_PATTERN = "private\\s+((\\w|\\[\\]|<|>)+)\\s+(\\w+);";

    /** 1行データ */
    private String line;

    /** 変換後の1行データ */
    private String convertedLine;

    /** Javadocコメント */
    private String javadocComment;

    /** 型 */
    private String tyep;

    /** 項目名 */
    private String item;

    /** 先頭大文字項目 */
    private String capitalizedItem;

    /**
     * フィールド宣言から型、項目名、先頭大文字項目に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean convertFields() throws KmgToolException {

        boolean result = false;

        // privateフィールド宣言を正規表現でグループ化する
        final Pattern patternSrc = Pattern.compile(AccessorCreationLogicImpl.PRIVATE_FIELD_PATTERN);
        final Matcher matcherSrc = patternSrc.matcher(this.line);

        // privateフィールド宣言ではないか
        if (!matcherSrc.find()) {
            // 宣言ではないか

            return result;

        }

        // フィールドの情報を取得
        this.tyep = matcherSrc.group(1); // 型
        this.item = matcherSrc.group(3); // 項目名
        this.capitalizedItem = KmgString.capitalize(this.item); // 先頭大文字項目

        result = true;
        return result;

    }

    /**
     * Javadocコメントに変換する。
     *
     * @return true：変換あり、false：変換なし
     */
    @Override
    public boolean convertJavadocComment() {

        boolean result = false;

        // Javadocコメントかを正規表現で判断する
        final Pattern pattern = Pattern.compile(AccessorCreationLogicImpl.JAVADOC_COMMENT_PATTERN);
        final Matcher matcher = pattern.matcher(this.line);

        // Javadocコメントはないか
        if (!matcher.find()) {
            // コメントはないの場合

            return result;

        }

        // Javadocコメントを設定
        this.javadocComment = matcher.group(1);

        result = true;
        return result;

    }

    /**
     * 先頭大文字項目返す。
     *
     * @return capitalizedItem 先頭大文字項目
     */
    @Override
    public String getCapitalizedItem() {

        final String result = this.capitalizedItem;
        return result;

    }

    /**
     * 変換後の1行データを返す。
     */
    @Override
    public String getConvertedLine() {

        final String result = this.convertedLine;
        return result;

    }

    /**
     * 項目名返す。
     *
     * @return item 項目名
     */
    @Override
    public String getItem() {

        final String result = this.item;
        return result;

    }

    /**
     * Javadocコメントを返す。
     *
     * @return Javadocコメント。取得できない場合は、null
     */
    @Override
    public String getJavadocComment() {

        final String result = this.javadocComment;
        return result;

    }

    /**
     * 1行データを返す。
     *
     * @return 1行データ
     */
    @Override
    public String getLine() {

        final String result = this.line;
        return result;

    }

    /**
     * 型を返す。
     *
     * @return tyep 型
     */
    @Override
    public String getTyep() {

        final String result = this.tyep;
        return result;

    }

    /**
     * 初期化する。
     *
     * @return true：成功、false：失敗
     *
     * @param line
     *             1行データ
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final String line) throws KmgToolException {

        boolean result = false;

        this.line = line;
        this.convertedLine = line;

        result = true;
        return result;

    }

    /**
     * 不要な修飾子を削除する。
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean removeModifier() {

        boolean result = false;

        this.convertedLine = this.convertedLine.replace("final", KmgString.EMPTY);
        this.convertedLine = this.convertedLine.replace("static", KmgString.EMPTY);

        result = true;
        return result;

    }

}
