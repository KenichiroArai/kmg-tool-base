package kmg.tool.input.domain.service.impl;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;

import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.input.domain.service.AbstractInputService;
import kmg.tool.input.domain.service.PlainContentInputServic;

/**
 * プレーンコンテンツ入力サービス<br>
 * <p>
 * 入力ファイルパスを読み込み、プレーンコンテンツを返すサービスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @sine 1.0.0
 *
 * @version 1.0.0
 */
@Service
public class PlainContentInputServiceImpl extends AbstractInputService implements PlainContentInputServic {

    /** 入力内容 */
    private String content;

    /**
     * 入力内容を返す<br>
     *
     * @return 入力内容
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public String getContent() throws KmgToolMsgException {

        final String result = this.content;
        return result;

    }

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

        boolean result = false;

        try {

            // ファイルの内容を読み込む
            this.content = Files.readString(this.getInputPath());

        } catch (final IOException e) {

            final KmgToolGenMsgTypes genType     = KmgToolGenMsgTypes.KMGTOOL_GEN08002;
            final Object[]           messageArgs = {
                this.getInputPath(),
            };

            throw new KmgToolMsgException(genType, messageArgs, e);

        }

        result = true;
        return result;

    }
}
