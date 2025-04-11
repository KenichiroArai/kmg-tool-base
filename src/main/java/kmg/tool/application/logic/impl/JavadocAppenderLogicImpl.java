package kmg.tool.application.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.logic.JavadocAppenderLogic;
import kmg.tool.application.model.jda.JdaReplacementModel;
import kmg.tool.application.model.jda.JdtsConfigsModel;
import kmg.tool.application.model.jda.imp.JdaReplacementModelImpl;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加ロジック<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@Service
public class JavadocAppenderLogicImpl implements JavadocAppenderLogic {

    /**
     * 合計行数
     */
    private long totalRows;

    /**
     * デフォルトコンストラクタ
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    public JavadocAppenderLogicImpl() {

        this.totalRows = 0;

    }

    /**
     * 合計行数を返す。
     *
     * @return 合計行数
     */
    @Override
    public long getTotalRows() {

        final long result = this.totalRows;
        return result;

    }

    /**
     * 内容を置換した値を返す。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @param contents
     *                         内容
     * @param jdtsConfigsModel
     *                         Javadocタグ設定の構成モデル
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public String replace(final String contents, final JdtsConfigsModel jdtsConfigsModel) throws KmgToolException {

        String result;

        String replaceContents = contents;

        /* Javadocを設定する */

        final List<JdaReplacementModel> javadocReplacementModelList = new ArrayList<>();

        // 「/**」でブロックに分ける
        // TODO KenichiroArai 2025/04/03 ハードコード
        final String[] blocks = contents.split(String.format("%s\\s+", Pattern.quote("/**")));

        // ブロックの0番目はJavadocではないので、1番目から進める
        for (int i = 1; i < blocks.length; i++) {

            // 「*/」でJavadocとCodeのブラックに分ける
            // TODO KenichiroArai 2025/04/03 ハードコード
            final String[] javadocCodeBlock = blocks[i].split(String.format("%s\\s+", Pattern.quote("*/")), 2);

            // 元のJavadoc
            final String srcJavadoc = javadocCodeBlock[0];

            // 元のコード
            final String srcCodeBlock = javadocCodeBlock[1];

            // TODO KenichiroArai 2025/04/09 コードを精査する
            // アノテーションと元のコードを分割
            final String[]      codeLines      = srcCodeBlock.split("\\R");
            final StringBuilder codeBuilder    = new StringBuilder();
            final List<String>  annotationList = new ArrayList<>();

            boolean isCodeSection = false;

            for (final String line : codeLines) {

                final String trimmedLine = line.trim();

                if (trimmedLine.isEmpty()) {

                    continue;

                }

                if (!isCodeSection) {

                    if (trimmedLine.startsWith("@")) {

                        annotationList.add(trimmedLine);

                    } else {

                        isCodeSection = true;
                        codeBuilder.append(line).append(System.lineSeparator());

                    }

                } else {

                    codeBuilder.append(line).append(System.lineSeparator());

                }

            }

            final String actualSrcCodeBlock = codeBuilder.toString().trim();

            // 元のJavadoc

            final JdaReplacementModel jdaReplacementModel
                = new JdaReplacementModelImpl(srcJavadoc, actualSrcCodeBlock, jdtsConfigsModel);
            javadocReplacementModelList.add(jdaReplacementModel);

            // 元のJavadoc部分を置換用識別子に置換する
            replaceContents = replaceContents.replaceFirst(Pattern.quote(srcJavadoc),
                jdaReplacementModel.getIdentifier().toString());

            // Java区分を特定する
            jdaReplacementModel.specifyJavaClassification();

            // 置換後のJavadocを作成する
            jdaReplacementModel.createReplacedJavadoc();

        }

        // 置換用識別子を置換後のJavadocに置換する
        for (final JdaReplacementModel jdaReplacementModel : javadocReplacementModelList) {

            replaceContents = replaceContents.replace(jdaReplacementModel.getIdentifier().toString(),
                jdaReplacementModel.getReplacedJavadoc());

        }

        this.totalRows += KmgDelimiterTypes.LINE_SEPARATOR.split(replaceContents).length;

        result = replaceContents;
        return result;

    }

}
