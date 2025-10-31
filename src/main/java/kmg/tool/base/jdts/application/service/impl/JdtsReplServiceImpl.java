package kmg.tool.base.jdts.application.service.impl;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.cmn.infrastructure.types.KmgToolLogMsgTypes;
import kmg.tool.base.jdts.application.logic.JdtsBlockReplLogic;
import kmg.tool.base.jdts.application.model.JdtsBlockModel;
import kmg.tool.base.jdts.application.model.JdtsCodeModel;
import kmg.tool.base.jdts.application.model.JdtsConfigsModel;
import kmg.tool.base.jdts.application.service.JdtsReplService;

/**
 * Javadocタグ設定の入出力サービス<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * Replは、Replacementの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@Service
public class JdtsReplServiceImpl implements JdtsReplService {

    /**
     * ロガー
     *
     * @since 0.2.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @since 0.2.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * Javadocタグ設定のブロック置換ロジック
     *
     * @since 0.2.0
     */
    @Autowired
    private JdtsBlockReplLogic jdtsBlockReplLogic;

    /**
     * Javadocタグ設定の構成モデル
     *
     * @since 0.2.0
     */
    private JdtsConfigsModel jdtsConfigsModel;

    /**
     * Javadocタグ設定のコードモデル
     *
     * @since 0.2.0
     */
    private JdtsCodeModel jdtsCodeModel;

    /**
     * 置換後のコード
     *
     * @since 0.2.0
     */
    private String replaceCode;

    /**
     * 合計置換数
     *
     * @since 0.2.0
     */
    private long totalReplaceCount;

    /**
     * デフォルトコンストラクタ
     *
     * @since 0.2.0
     */
    public JdtsReplServiceImpl() {

        this(LoggerFactory.getLogger(JdtsServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.2.0
     *
     * @param logger
     *               ロガー
     */
    protected JdtsReplServiceImpl(final Logger logger) {

        this.logger = logger;
        this.replaceCode = KmgString.EMPTY;
        this.totalReplaceCount = 0;

    }

    /**
     * Javadocタグ設定の構成モデルを返す<br>
     *
     * @since 0.2.0
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
     * @since 0.2.0
     *
     * @return 置換後のコード
     */
    @Override
    public String getReplaceCode() {

        final String result = this.replaceCode;
        return result;

    }

    /**
     * 合計置換数を返す。
     *
     * @since 0.2.0
     *
     * @return 合計置換数
     */
    @Override
    public long getTotalReplaceCount() {

        final long result = this.totalReplaceCount;
        return result;

    }

    /**
     * 初期化する
     *
     * @since 0.2.0
     *
     * @param jdtsConfigsModel
     *                         Javadocタグ設定の構成モデル
     * @param jdtsCodeModel
     *                         Javadocタグ設定のコードモデル
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final JdtsConfigsModel jdtsConfigsModel, final JdtsCodeModel jdtsCodeModel)
        throws KmgToolMsgException {

        boolean result = false;

        /* パラメータがnullか */
        if (jdtsConfigsModel == null) {

            return result;

        }

        if (jdtsCodeModel == null) {

            return result;

        }

        this.jdtsConfigsModel = jdtsConfigsModel;
        this.jdtsCodeModel = jdtsCodeModel;

        this.replaceCode = this.jdtsCodeModel.getOrgCode();

        this.totalReplaceCount = 0;

        result = true;
        return result;

    }

    /**
     * Javadocを置換する。<br>
     *
     * @since 0.2.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean replace() throws KmgToolMsgException {

        boolean result = false;

        /* 事前処理 */

        // オリジナルコード内のJavadocブロック部分を一意の識別子に一時的に置換する
        // 同一内容のJavadocが複数存在する場合でも、異なる置換が行われないようにするため
        // 置換は前回の置換位置以降から検索して実施する

        int searchStartIdx = 0; // 次回検索開始位置

        for (final JdtsBlockModel jdtsBlockModel : this.jdtsCodeModel.getJdtsBlockModels()) {

            final String target     = jdtsBlockModel.getJavadocModel().getSrcJavadoc();
            final int    replaceIdx = this.replaceCode.indexOf(target, searchStartIdx);

            if (replaceIdx == -1) {

                continue;

            }

            // 置換対象の前後で分割し、Javadoc部分を識別子に置換
            final String before = this.replaceCode.substring(0, replaceIdx);
            final String after  = this.replaceCode.substring(replaceIdx).replaceFirst(Pattern.quote(target),
                jdtsBlockModel.getId().toString());

            this.replaceCode = KmgString.concat(before, after);

            // 次回は今回置換した識別子の直後から検索を再開
            searchStartIdx = replaceIdx + jdtsBlockModel.getId().toString().length();

        }

        /* Javadocを置換する */

        // ブロックごとにJavadocを置換する
        for (final JdtsBlockModel targetBlockModel : this.jdtsCodeModel.getJdtsBlockModels()) {

            this.processBlock(targetBlockModel);

        }

        result = true;
        return result;

    }

    /**
     * 新しいタグ追加時のログを出力する
     *
     * @since 0.2.0
     *
     * @param targetBlockModel
     *                         対象のブロックモデル
     */
    private void logAddNewTag(final JdtsBlockModel targetBlockModel) {

        /* 早期リターン：必要なオブジェクトがnullの場合 */
        if (this.jdtsBlockReplLogic.getCurrentTagConfigModel() == null) {

            return;

        }

        final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG13001;
        final Object[]           logMsgArgs  = {
            targetBlockModel.getClassification().getDisplayName(), targetBlockModel.getElementName(),
            this.jdtsBlockReplLogic.getCurrentTagConfigModel().getTag().getDisplayName(),
        };
        final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }

    /**
     * タグ削除時のログを出力する
     *
     * @since 0.2.0
     *
     * @param targetBlockModel
     *                         対象のブロックモデル
     */
    private void logRemoveTag(final JdtsBlockModel targetBlockModel) {

        final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG13002;
        final Object[]           logMsgArgs  = {
            targetBlockModel.getClassification().getDisplayName(), targetBlockModel.getElementName(),
            this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getTargetStr(),
            this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getTag().getDisplayName(),
            this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getValue(),
            this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getDescription(),
        };
        final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }

    /**
     * タグ置換時のログを出力する
     *
     * @since 0.2.0
     *
     * @param targetBlockModel
     *                         対象のブロックモデル
     */
    private void logReplaceTag(final JdtsBlockModel targetBlockModel) {

        /* 早期リターン：必要なオブジェクトがnullの場合 */
        if (this.jdtsBlockReplLogic.getCurrentSrcJavadocTag() == null) {

            return;

        }

        final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG13004;
        final Object[]           logMsgArgs  = {
            targetBlockModel.getClassification().getDisplayName(), targetBlockModel.getElementName(),
            this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getTargetStr(),
            this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getTag().getDisplayName(),
            this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getValue(),
            this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getDescription(),
            this.jdtsBlockReplLogic.getTagContentToApply(),
            this.jdtsBlockReplLogic.getCurrentTagConfigModel().getTag().getDisplayName(),
            this.jdtsBlockReplLogic.getCurrentTagConfigModel().getTagValue(),
            this.jdtsBlockReplLogic.getCurrentTagConfigModel().getTagDescription(),
        };
        final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }

    /**
     * タグ位置変更時のログを出力する
     *
     * @since 0.2.0
     *
     * @param targetBlockModel
     *                         対象のブロックモデル
     */
    private void logRepositionTag(final JdtsBlockModel targetBlockModel) {

        /* 早期リターン：必要なオブジェクトがnullの場合 */
        if (this.jdtsBlockReplLogic.getCurrentSrcJavadocTag() == null) {

            return;

        }

        final KmgToolLogMsgTypes logMsgTypes = KmgToolLogMsgTypes.KMGTOOL_LOG13003;
        final Object[]           logMsgArgs  = {
            targetBlockModel.getClassification().getDisplayName(), targetBlockModel.getElementName(),
            this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getTargetStr(),
            this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getTag().getDisplayName(),
            this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getValue(),
            this.jdtsBlockReplLogic.getCurrentSrcJavadocTag().getDescription(),
            this.jdtsBlockReplLogic.getTagContentToApply(),
            this.jdtsBlockReplLogic.getCurrentTagConfigModel().getTag().getDisplayName(),
            this.jdtsBlockReplLogic.getCurrentTagConfigModel().getTagValue(),
            this.jdtsBlockReplLogic.getCurrentTagConfigModel().getTagDescription(),
        };
        final String             logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }

    /**
     * ブロックごとのJavadoc置換処理を行う
     *
     * @since 0.2.0
     *
     * @param targetBlockModel
     *                         対象のブロックモデル
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void processBlock(final JdtsBlockModel targetBlockModel) throws KmgToolMsgException {

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

                    this.totalReplaceCount++;

                    this.logAddNewTag(targetBlockModel);

                }

                continue;

            }

            /* 誤配置時の削除処理を行う */

            // タグを削除したか
            if (this.jdtsBlockReplLogic.removeCurrentTagOnError()) {
                // 削除した場合

                this.totalReplaceCount++;

                this.logRemoveTag(targetBlockModel);

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

                this.totalReplaceCount++;

                this.logRepositionTag(targetBlockModel);

                continue;

            }

            /* 既存のタグを置換する */
            if (this.jdtsBlockReplLogic.replaceExistingTag()) {

                this.totalReplaceCount++;

                this.logReplaceTag(targetBlockModel);

                continue;

            }

            /* 次のタグを処理するか */
        } while (this.jdtsBlockReplLogic.nextTag());

        /* コード全体に反映する */

        // 置換後のJavadocブロックを取得する
        final String replaceJavadocBlock = this.jdtsBlockReplLogic.getReplacedJavadocBlock();

        // 置換後のJavadocブロックにコード全体に反映する
        this.replaceCode = this.replaceCode.replace(targetBlockModel.getId().toString(), replaceJavadocBlock);

    }
}
