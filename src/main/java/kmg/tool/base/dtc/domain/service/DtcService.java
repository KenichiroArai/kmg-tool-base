package kmg.tool.base.dtc.domain.service;

import java.nio.file.Path;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.two2one.domain.service.Two2OneService;

/**
 * テンプレートの動的変換サービスインタフェース<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.2
 */
public interface DtcService extends Two2OneService {

    /**
     * 初期化する<br>
     * <p>
     * 中間ファイルの区切り文字を指定して初期化します。
     * </p>
     *
     * @since 0.2.2
     *
     * @param inputPath
     *                              入力ファイルパス（中間ファイルパス）
     * @param templatePath
     *                              テンプレートファイルパス
     * @param outputPath
     *                              出力ファイルパス
     * @param intermediateDelimiter
     *                              中間ファイルの区切り文字
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean initialize(Path inputPath, Path templatePath, Path outputPath, KmgDelimiterTypes intermediateDelimiter)
        throws KmgToolMsgException;

}
