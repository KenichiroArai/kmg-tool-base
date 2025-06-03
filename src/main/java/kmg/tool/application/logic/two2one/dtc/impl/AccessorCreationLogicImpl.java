package kmg.tool.application.logic.two2one.dtc.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgJavaKeywordTypes;
import kmg.tool.application.logic.two2one.dtc.AccessorCreationLogic;
import kmg.tool.application.types.two2one.AccessorRegexGroupTypes;
import kmg.tool.domain.logic.two2one.AbstractIctoOneLinePatternLogic;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMsgTypes;

/**
 * アクセサ作成ロジック<br>
 *
 * @author KenichiroArai
 */
@Service
public class AccessorCreationLogicImpl extends AbstractIctoOneLinePatternLogic implements AccessorCreationLogic {

    /** Javadocコメントの開始の正規表現パターン */
    private static final String JAVADOC_COMMENT_START_PATTERN = "/\\*\\*"; //$NON-NLS-1$

    /** 1行Javadocコメントの正規表現パターン */
    private static final String SINGLE_LINE_JAVADOC_PATTERN = "/\\*\\*\\s+(\\S+)\\s+\\*/"; //$NON-NLS-1$

    /** 複数行Javadocコメント開始の正規表現パターン */
    private static final String MULTI_LINE_JAVADOC_START_PATTERN = "/\\*\\*(\\S+)"; //$NON-NLS-1$

    /** privateフィールド宣言の正規表現パターン */
    private static final String PRIVATE_FIELD_PATTERN = "private\\s+((\\w|\\[\\]|<|>)+)\\s+(\\w+);"; //$NON-NLS-1$

    /** Javadocの解析中かを管理する */
    private boolean inJavadocParsing;

    /** Javadocコメント */
    private String javadocComment;

    /** 型 */
    private String tyep;

    /** 項目名 */
    private String item;

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean addItemToCsvRows() throws KmgToolMsgException {

        boolean result = false;

        if (this.item == null) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN32001;
            final Object[]           messageArgs  = {};
            throw new KmgToolMsgException(messageTypes, messageArgs);

        }

        super.addCsvRow(this.item);
        result = true;

        return result;

    }

    /**
     * Javadocコメントを書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean addJavadocCommentToCsvRows() throws KmgToolMsgException {

        boolean result = false;

        if (this.javadocComment == null) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN32002;
            final Object[]           messageArgs  = {};
            throw new KmgToolMsgException(messageTypes, messageArgs);

        }

        super.addCsvRow(this.javadocComment);
        result = true;

        return result;

    }

    /**
     * 型情報を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean addTypeToCsvRows() throws KmgToolMsgException {

        boolean result = false;

        if (this.tyep == null) {

            final KmgToolGenMsgTypes messageTypes = KmgToolGenMsgTypes.KMGTOOL_GEN32003;
            final Object[]           messageArgs  = {};
            throw new KmgToolMsgException(messageTypes, messageArgs);

        }

        super.addCsvRow(this.tyep);
        result = true;

        return result;

    }

    /**
     * 処理中のデータをクリアする。
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean clearProcessingData() {

        boolean result = super.clearProcessingData();

        this.javadocComment = null;
        this.tyep = null;
        this.item = null;

        result = true;
        return result;

    }

    /**
     * フィールド宣言から型、項目名、先頭大文字項目に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean convertFields() throws KmgToolMsgException {

        boolean result = false;

        // privateフィールド宣言を正規表現でグループ化する
        final Pattern patternSrc = Pattern.compile(AccessorCreationLogicImpl.PRIVATE_FIELD_PATTERN);
        final Matcher matcherSrc = patternSrc.matcher(this.getConvertedLine());

        // privateフィールド宣言ではないか
        if (!matcherSrc.find()) {
            // 宣言ではないか

            return result;

        }

        // フィールドの情報を取得
        this.tyep = matcherSrc.group(AccessorRegexGroupTypes.PRIVATE_FIELD_TYPE.getGroupIndex()); // 型
        this.item = matcherSrc.group(AccessorRegexGroupTypes.PRIVATE_FIELD_ITEM_NAME.getGroupIndex()); // 項目名

        result = true;
        return result;

    }

    /**
     * Javadocコメントに変換する。
     *
     * @return true：変換あり、false：変換なし
     */
    @Override
    public boolean convertJavadoc() {

        boolean result = false;

        /* Javadocの解析中を開始する */

        // Javadocの解析中でないか
        if (!this.inJavadocParsing) {
            // 解析中ではない場合

            // Javadocの開始判定
            final Pattern javadocStartPattern = Pattern
                .compile(AccessorCreationLogicImpl.JAVADOC_COMMENT_START_PATTERN);
            final Matcher javadocStartMatcher = javadocStartPattern.matcher(this.getConvertedLine());

            // Javadocの開始ではないか
            if (!javadocStartMatcher.find()) {
                // 開始でない場合

                return result;

            }

            // Javadoc解析中に設定
            this.inJavadocParsing = true;

        }

        /* 1行完結型のJavadocの処理 */

        final Pattern singleLinePattern = Pattern.compile(AccessorCreationLogicImpl.SINGLE_LINE_JAVADOC_PATTERN);
        final Matcher singleLineMatcher = singleLinePattern.matcher(this.getConvertedLine());
        final boolean isSingleLineMatch = singleLineMatcher.find();

        // 1行完結型のJavadocでないか
        if (isSingleLineMatch) {
            // 1行完結型のJavadocでない場合

            // コメント部分を抽出して設定
            this.javadocComment
                = singleLineMatcher.group(AccessorRegexGroupTypes.SINGLE_LINE_JAVADOC_COMMENT.getGroupIndex());

            // Javadoc解析終了
            this.inJavadocParsing = false;

            result = true;

            return result;

        }

        /* 複数行Javadocの処理 */

        // 複数行Javadocの開始行を正規表現でグループ化する
        final Pattern multiLineStartPattern = Pattern
            .compile(AccessorCreationLogicImpl.MULTI_LINE_JAVADOC_START_PATTERN);
        final Matcher multiLineStartMatcher = multiLineStartPattern.matcher(this.getConvertedLine());
        final boolean isMultiLineStartMatch = multiLineStartMatcher.find();

        // 複数行Javadocの開始行でないか
        if (!isMultiLineStartMatch) {
            // 開始行でない場合

            return result;

        }

        // コメント部分を抽出して設定
        this.javadocComment
            = multiLineStartMatcher.group(AccessorRegexGroupTypes.MULTI_LINE_JAVADOC_COMMENT.getGroupIndex());

        // Javadoc解析終了
        this.inJavadocParsing = false;

        result = true;

        return result;

    }

    /**
     * 項目名返す。
     *
     * @return 項目名
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
     * 型を返す。
     *
     * @return 型
     */
    @Override
    public String getTyep() {

        final String result = this.tyep;
        return result;

    }

    /**
     * Javadocの解析中かを返す。
     *
     * @return true：Javadoc解析中、false：Javadoc解析外
     */
    @Override
    public boolean isInJavadocParsing() {

        final boolean result = this.inJavadocParsing;
        return result;

    }

    /**
     * 不要な修飾子を削除する。
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean removeModifier() {

        boolean result = true;

        result &= this.replaceInLine(KmgJavaKeywordTypes.FINAL.getKey(), KmgString.EMPTY);
        result &= this.replaceInLine(KmgJavaKeywordTypes.STATIC.getKey(), KmgString.EMPTY);

        return result;

    }

}
