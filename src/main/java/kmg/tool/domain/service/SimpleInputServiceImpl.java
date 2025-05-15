package kmg.tool.domain.service;

import org.springframework.stereotype.Service;

import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * シンプル入力サービス
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
@Service
public class SimpleInputServiceImpl extends AbstractInputService implements SimpleInputService {

    /**
     * 処理する<br>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean process() throws KmgToolMsgException {

        final boolean result = true;

        // 処理なし

        return result;

    }
}
