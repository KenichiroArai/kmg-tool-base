package kmg.tool.jdocr.application.logic;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;

/**
 * Javadoc行削除ロジックインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface JavadocLineRemoverLogic {

    /**
     * Javadoc行を削除する
     *
     * @param inputMap
     *                 パスと行番号のマップ
     *
     * @return 削除した行数
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    int deleteJavadocLines(final Map<Path, Set<Integer>> inputMap) throws KmgToolMsgException;

    /**
     * 入力ファイルからパスと行番号のマップを取得する
     *
     * @param inputPath
     *                  入力ファイルのパス
     *
     * @return パスと行番号の降順のセットのマップ
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    Map<Path, Set<Integer>> getInputMap(final Path inputPath) throws KmgToolMsgException;

}
