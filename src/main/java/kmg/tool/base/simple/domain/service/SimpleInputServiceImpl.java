package kmg.tool.base.simple.domain.service;

import org.springframework.stereotype.Service;

import kmg.tool.base.cmn.infrastructure.exception.KmgToolBaseMsgException;
import kmg.tool.base.input.domain.service.AbstractInputService;

/**
 * シンプル入力サービス
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.4
 */
@Service
public class SimpleInputServiceImpl extends AbstractInputService implements SimpleInputService {

    /**
     * デフォルトコンストラクタ
     *
     * @since 0.2.0
     */
    public SimpleInputServiceImpl() {

        // 処理なし
    }

    /**
     * 処理する<br>
     *
     * @since 0.2.4
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolBaseMsgException
     *                                 KMGツールメッセージ例外
     */
    @Override
    public boolean process() throws KmgToolBaseMsgException {

        final boolean result = true;

        // 処理なし

        return result;

    }
}
