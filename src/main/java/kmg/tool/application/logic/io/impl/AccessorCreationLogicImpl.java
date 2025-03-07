package kmg.tool.application.logic.io.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import kmg.tool.application.logic.io.AccessorCreationLogic;

/**
 * アクセサ作成ロジック<br>
 *
 * @author KenichiroArai
 */
@Service
public class AccessorCreationLogicImpl implements AccessorCreationLogic {

    /**
     * Javadocコメントを返す。
     *
     * @param line
     *             1行データ
     *
     * @return Javadocコメント。取得できない場合は、null
     */
    @Override
    public String getJavadocComment(final String line) {

        String result = null;

        // Javadocコメントの正規表現パターン
        final Pattern patternComment = Pattern.compile("/\\*\\* (\\S+)");
        final Matcher matcherComment = patternComment.matcher(line);

        // Javadocコメントではないか
        if (!matcherComment.find()) {
            // コメントではないの場合

            return result;

        }

        // Javadocコメントを取得
        result = matcherComment.group(1);

        return result;

    }

}
