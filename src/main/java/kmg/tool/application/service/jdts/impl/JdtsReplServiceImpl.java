package kmg.tool.application.service.jdts.impl;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.logic.jdts.JdtsBlockReplLogic;
import kmg.tool.application.model.jdts.JdtsBlockModel;
import kmg.tool.application.model.jdts.JdtsCodeModel;
import kmg.tool.application.model.jdts.JdtsConfigsModel;
import kmg.tool.application.service.jdts.JdtsReplService;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグ設定の入出力サービス<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * Replは、Replacementの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
@Service
public class JdtsReplServiceImpl implements JdtsReplService {

    /** Javadocタグ設定のブロック置換ロジック */
    @Autowired
    private JdtsBlockReplLogic jdtsBlockReplLogic;

    /** Javadocタグ設定の構成モデル */
    private JdtsConfigsModel jdtsConfigsModel;

    /** Javadocタグ設定のコードモデル */
    private JdtsCodeModel jdtsCodeModel;

    /** 置換後のコード */
    private String replaceCode;

    /** 合計行数 */
    private long totalRows;

    /**
     * デフォルトコンストラクタ
     */
    public JdtsReplServiceImpl() {

        this.replaceCode = KmgString.EMPTY;
        this.totalRows = 0;

    }

    /**
     * Javadocタグ設定の構成モデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocタグ設定の構成モデル
     */
    @Override
    public JdtsConfigsModel getJdtsConfigsModel() {

        final JdtsConfigsModel result = this.jdtsConfigsModel;
        return result;

    }

    /**
     * 置換後のコードを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 置換後のコード
     */
    @Override
    public String getReplaceCode() {

        final String result = this.replaceCode;
        return result;

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
     * 初期化する
     *
     * @param jdtsConfigsModel
     *                         Javadocタグ設定の構成モデル
     * @param jdtsCodeModel
     *                         Javadocタグ設定のコードモデル
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final JdtsConfigsModel jdtsConfigsModel, final JdtsCodeModel jdtsCodeModel)
        throws KmgToolException {

        boolean result = false;

        this.jdtsConfigsModel = jdtsConfigsModel;
        this.jdtsCodeModel = jdtsCodeModel;

        this.replaceCode = this.jdtsCodeModel.getOrgCode();

        this.totalRows = 0;

        result = true;
        return result;

    }

    /**
     * Javadocを置換する。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean replace() throws KmgToolException {

        boolean result = false;

        /* 事前処理 */

        // オリジナルコードのJavadocブロック部分を識別子に置換する
        // 同じJavadocがある場合に異なる置換をさせないため、事前に一意となる識別子に置き換える
        for (final JdtsBlockModel jdtsBlockModel : this.jdtsCodeModel.getJdtsBlockModels()) {

            // 複数該当する場合もあるため、最初の部分のみを置換する
            this.replaceCode = this.replaceCode.replaceFirst(
                Pattern.quote(jdtsBlockModel.getJavadocModel().getSrcJavadoc()), jdtsBlockModel.getId().toString());

        }

        /* Javadocを置換する */

        // ブロックごとにJavadocを置換する
        // 対象ブロックモデルを取得する
        for (final JdtsBlockModel targetBlockModel : this.jdtsCodeModel.getJdtsBlockModels()) {

            /* ブロックごとの置換の処理の準備 */

            // ブロック置換ロジックの初期化
            this.jdtsBlockReplLogic.initialize(this.jdtsConfigsModel, targetBlockModel);

            /* タグ構成の順番に処理を行う */
            // タグ構成が存在するまで続ける
            do {

                /* 対象のブロックにタグ構成のタグが存在しない場合の処理 */

                // 対象のブロックに構成タグのタグが存在しないか
                if (!this.jdtsBlockReplLogic.hasExistingTag()) {
                    // 存在しない場合

                    // 新しいタグを追加するか
                    if (this.jdtsBlockReplLogic.shouldAddNewTag()) {
                        // 追加する場合

                        // 新しいタグを作成し配置する
                        this.jdtsBlockReplLogic.addNewTagByPosition();

                        // TODO KenichiroArai 2025/04/03 デバッグ
                        System.out.println(String.format("【タグ存在しない場合】Javadocタグ：[%s], Java区分：[%s], オリジナルコード：[%s]",
                            this.jdtsBlockReplLogic.getCurrentTagConfigModel().getTag().getDisplayName(),
                            targetBlockModel.getJavaClassification().getDisplayName(), targetBlockModel.getOrgBlock()));

                    }

                    continue;

                }

                // TODO KenichiroArai 2025/04/03 デバッグ
                System.out.println(String.format("【タグ存在する場合】対象文字列: [%s], タグ: [%s], 指定値: [%s], 説明: [%s]",
                    this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getTargetStr(),
                    this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getTag().getDisplayName(),
                    this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getValue(),
                    this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getDescription()));

                /* 誤配置時の削除処理を行う */

                // タグを削除したか
                if (this.jdtsBlockReplLogic.removeCurrentTagOnError()) {
                    // 削除した場合

                    // タグを削除したため、後続の処理は行わず、次のタグを処理する
                    continue;

                }

                /* タグを更新処理する */

                // タグを上書きしないか
                if (!this.jdtsBlockReplLogic.shouldOverwriteTag()) {
                    // 上書きしない場合

                    continue;

                }

                /* タグの位置が指定されている場合は、指定値に置換する */
                if (this.jdtsBlockReplLogic.repositionTagIfNeeded()) {

                    continue;

                }

                // 既存のタグを置換する
                this.jdtsBlockReplLogic.replaceExistingTag();

                /* 次のタグを処理するか */
            } while (this.jdtsBlockReplLogic.nextTag());

            /* コード全体に反映する */

            // 置換後のJavadocブロックを取得する
            final String replaceJavadocBlock = this.jdtsBlockReplLogic.getReplacedJavadocBlock();

            // 置換後のJavadocブロックにコード全体に反映する
            this.replaceCode = this.replaceCode.replace(targetBlockModel.getId().toString(), replaceJavadocBlock);

        }

        this.totalRows += KmgDelimiterTypes.LINE_SEPARATOR.split(this.replaceCode).length;

        result = true;
        return result;

    }
}
