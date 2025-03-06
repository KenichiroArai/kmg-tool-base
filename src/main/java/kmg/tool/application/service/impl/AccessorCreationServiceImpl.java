package kmg.tool.application.service.impl;

import org.springframework.stereotype.Service;

import kmg.tool.application.service.AccessorCreationService;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * アクセサ作成サービス<br>
 *
 * @author KenichiroArai
 */
@Service
public class AccessorCreationServiceImpl extends DynamicTemplateConversionServiceImpl
    implements AccessorCreationService {

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean process() throws KmgToolException {

        boolean result = false;

        result = super.process();

        return result;

    }
}
