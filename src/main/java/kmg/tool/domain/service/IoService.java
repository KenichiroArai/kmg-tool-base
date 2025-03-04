package kmg.tool.domain.service;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * 入出力サービスインタフェース
 */
public interface IoService {

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean process() throws KmgToolException;

}
