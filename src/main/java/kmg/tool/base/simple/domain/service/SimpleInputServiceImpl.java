package kmg.tool.base.simple.domain.service;

import org.springframework.stereotype.Service;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.base.input.domain.service.AbstractInputService;

/**
 * シンプル入力サービス
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.0
 */
@Service
public class SimpleInputServiceImpl extends AbstractInputService implements SimpleInputService {

    /**
     * 処理する<br>
     *
     * @since 0.2.0
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
