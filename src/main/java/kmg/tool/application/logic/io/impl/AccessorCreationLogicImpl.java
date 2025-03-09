package kmg.tool.application.logic.io.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.logic.io.AccessorCreationLogic;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
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

    /** 読み込んだ１行データ */
    private String lineOfDataRead;

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

    /** 入力ファイルパス */
    private Path inputPath;

    /** 出力ファイルパス */
    private Path outputPath;

    /** 書き込み対象のCSVデータのリスト */
    private final List<List<String>> csvRows;

    /** 入力ファイルのBufferedReader */
    private BufferedReader reader;

    /** 出力ファイルのBufferedReader */
    private BufferedWriter writer;

    /**
     * デフォルトコンストラクタ
     */
    public AccessorCreationLogicImpl() {

        this.csvRows = new ArrayList<>();

    }

    /**
     * 先頭大文字項目を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean addCapitalizedItemToCsvRows() {

        boolean result = false;

        if (this.capitalizedItem == null) {

            return result;

        }

        final List<String> row = this.csvRows.getLast();
        row.add(this.capitalizedItem);
        this.csvRows.add(row);
        result = true;

        return result;

    }

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean addItemToCsvRows() {

        boolean result = false;

        if (this.item == null) {

            return result;

        }

        final List<String> row = this.csvRows.getLast();
        row.add(this.item);
        this.csvRows.add(row);
        result = true;

        return result;

    }

    /**
     * Javadocコメントを書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean addJavadocCommentToCsvRows() {

        boolean result = false;

        if (this.javadocComment == null) {

            return result;

        }

        final List<String> row = this.csvRows.getLast();
        row.add(this.javadocComment);
        this.csvRows.add(row);
        result = true;

        return result;

    }

    /**
     * 書き込み対象に行を追加する。
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean addOneLineOfDataToCsvRows() {

        boolean result = false;

        final List<String> newRow = new ArrayList<>();
        this.csvRows.add(newRow);

        result = true;
        return result;

    }

    /**
     * 型情報を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean addTypeToCsvRows() {

        boolean result = false;

        if (this.tyep == null) {

            return result;

        }

        final List<String> row = this.csvRows.getLast();
        row.add(this.tyep);
        this.csvRows.add(row);
        result = true;

        return result;

    }

    /**
     * 書き込み対象のCSVデータのリストをクリアする。
     *
     * @return true：成功、false：失敗
     */
    @Override
    public boolean clearCsvRows() {

        boolean result = false;

        this.csvRows.clear();

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

        boolean result = false;

        this.lineOfDataRead = null;
        this.convertedLine = null;
        this.javadocComment = null;
        this.tyep = null;
        this.item = null;
        this.capitalizedItem = null;

        result = true;
        return result;

    }

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
        final Matcher matcherSrc = patternSrc.matcher(this.convertedLine);

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
        final Matcher matcher = pattern.matcher(this.convertedLine);

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
     * 書き込み対象のCSVデータのリストを返す。
     *
     * @return 書き込み対象のCSVデータのリスト
     */
    @Override
    public List<List<String>> getCsvRows() {

        final List<List<String>> result = this.csvRows;
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
     * 読み込んだ１行データを返す。
     *
     * @return 読み込んだ１行データ
     */
    @Override
    public String getLineOfDataRead() {

        final String result = this.lineOfDataRead;
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
     * 初期化する。
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                   入力ファイルパス
     * @param outputPath
     *                   出力ファイルパス
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path outputPath) throws KmgToolException {

        boolean result = false;

        this.inputPath = inputPath;
        this.outputPath = outputPath;

        /* 読み込みと書き込みのインスタンス変数の初期化 */
        this.clearProcessingData();

        this.clearCsvRows();

        /* ファイルを開く */
        try {

            // 入力ファイルを開く
            this.reader = Files.newBufferedReader(this.inputPath);

            // 出力ファイルを開く
            this.writer = Files.newBufferedWriter(this.outputPath);

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/08 例外
            final KmgToolGenMessageTypes msgType = KmgToolGenMessageTypes.NONE;
            throw new KmgToolException(msgType, e);

        }

        result = true;
        return result;

    }

    /**
     * 1行データを読み込む。
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean readOneLineOfData() throws KmgToolException {

        boolean result = false;

        try {

            // 1行読み込み
            this.lineOfDataRead = this.reader.readLine();
            this.convertedLine = this.lineOfDataRead;

            // ファイルの終わりに達した場合
            if (this.lineOfDataRead == null) {

                return result;

            }

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/08 例外
            final KmgToolGenMessageTypes msgType = KmgToolGenMessageTypes.NONE;
            throw new KmgToolException(msgType, e);

        }

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

    /**
     * CSVファイルに書き込む。<br>
     * <p>
     * 入力ファイルからCSV形式に変換してCSVファイルに出力する。
     * </p>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean writeCsvFile() throws KmgToolException {

        boolean result = false;

        for (final List<String> csvRow : this.csvRows) {

            try {

                this.writer.write(KmgDelimiterTypes.COMMA.join(csvRow));
                this.writer.write(System.lineSeparator());
                this.writer.flush();

            } catch (final IOException e) {

                // TODO KenichiroArai 2025/03/08 例外
                final KmgToolGenMessageTypes msgType = KmgToolGenMessageTypes.NONE;
                throw new KmgToolException(msgType, e);

            }

        }

        result = true;
        return result;

    }

}
