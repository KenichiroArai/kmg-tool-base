package kmg.tool.base.jdocr.application.logic;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;

/**
 * Javadoc行削除ロジックインタフェース<br>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
public interface JavadocLineRemoverLogic {

    /**
     * Javadoc行を削除する
     *
     * @since 0.2.4
     *
     * @param inputMap
     *                 パスと行番号のマップ
     *
     * @return 削除した行数
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    int deleteJavadocLines(final Map<Path, Set<Integer>> inputMap) throws KmgToolBaseMsgException;

    /**
     * 入力ファイルからパスと行番号のマップを取得する
     *
     * @since 0.2.4
     *
     * @param inputPath
     *                  入力ファイルのパス
     *
     * @return パスと行番号の降順のセットのマップ
     *
     * @throws KmgToolBaseMsgException
     *                             KMGツールメッセージ例外
     */
    Map<Path, Set<Integer>> getInputMap(final Path inputPath) throws KmgToolBaseMsgException;

}
