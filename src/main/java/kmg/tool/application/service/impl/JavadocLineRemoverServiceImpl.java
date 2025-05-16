package kmg.tool.application.service.impl;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.tool.application.logic.JavadocLineRemoverLogic;
import kmg.tool.application.service.JavadocLineRemoverService;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * Javadoc行削除サービス<br>
 *
 * @author KenichiroArai
 */
@Service
public class JavadocLineRemoverServiceImpl implements JavadocLineRemoverService {

    /** 入力ファイルのパス */
    private Path inputPath;

    /** Javadoc行削除ロジック */
    @Autowired
    private JavadocLineRemoverLogic javadocLineRemoverLogic;

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                  入力ファイルのパス
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath) throws KmgToolMsgException {

        final boolean result = false;

        this.inputPath = inputPath;

        return result;

    }

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean process() throws KmgToolMsgException {

        final boolean result = true;

        /* 入力からパスと行番号のマップ（ファイルパスと行番号のリスト、リストは行番号の降順）を取得 */
        final var inputMap = this.javadocLineRemoverLogic.getInputMap(this.inputPath);
        System.out.println(inputMap.toString());

        /* Javadoc行を削除する */
        final int lineCount = this.javadocLineRemoverLogic.deleteJavadocLines(inputMap);

        /* 情報の出力 */
        System.out.println(String.format("lineCount: %d", lineCount));

        return result;

    }
}
