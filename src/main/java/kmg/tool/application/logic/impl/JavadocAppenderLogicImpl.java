package kmg.tool.application.logic.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.logic.JavadocAppenderLogic;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加ロジック<br>
 *
 * @author KenichiroArai
 */
@Service
public class JavadocAppenderLogicImpl implements JavadocAppenderLogic {

    /** 対象ファイルパス */
    private Path targetPath;

    /** テンプレートファイルパス */
    private Path templatePath;

    /**
     * タグマップを取得する<br>
     *
     * @return タグマップ
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public Map<String, String> getTagMap() throws KmgToolException {

        final Map<String, String> result = new HashMap<>();

        /* テンプレートの読み込み */
        List<String> lines = null;

        try {

            lines = Files.readAllLines(this.templatePath);

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/29 メッセージ
            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.NONE;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

        }

        /* タグマップの作成 */
        for (final String line : lines) {

            final String trimmedLine = line.trim();

            if (!trimmedLine.startsWith(KmgDelimiterTypes.HALF_AT_SIGN.get())) {

                continue;

            }

            // TODO KenichiroArai 2025/03/29 ハードコード
            // タグと値を分離
            final String[] parts = KmgDelimiterTypes.SERIES_HALF_SPACE.split(trimmedLine, 2);
            final String   tag   = parts[0].trim();
            final String   value = parts[1].trim();
            result.put(tag, value);

        }

        return result;

    }

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param targetPath
     *                     対象ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path targetPath, final Path templatePath) throws KmgToolException {

        boolean result = false;

        this.targetPath = targetPath;
        this.templatePath = templatePath;

        result = true;
        return result;

    }

}
