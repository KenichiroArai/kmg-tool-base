package kmg.tool.application.logic.two2one.dtc.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import kmg.tool.application.logic.two2one.dtc.Enum2SwitchCaseCreationLogic;
import kmg.tool.application.types.two2one.ConvertEnumDefinitionTypes;
import kmg.tool.domain.logic.two2one.AbstractIctoOneLinePatternLogic;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.infrastructure.type.msg.KmgToolGenMessageTypes;

/**
 * <h2>列挙型からcase文作成ロジック実装クラス</h2>
 * <p>
 * 列挙型の定義からswitch-case文を自動生成するためのロジック実装クラスです。
 * </p>
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Service
public class Enum2SwitchCaseCreationLogicImpl extends AbstractIctoOneLinePatternLogic
    implements Enum2SwitchCaseCreationLogic {

    /** 列挙型定義の正規表現パターン */
    private static final String ENUM_DEFINITION_PATTERN = "(\\w+)\\(\"(\\S+)\",";

    /** 項目 */
    private String item;

    /** 項目名 */
    private String itemName;

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean addItemNameToCsvRows() throws KmgToolMsgException {

        boolean result = false;

        if (this.itemName == null) {

            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.KMGTOOL_GEN32009;
            final Object[]               messageArgs  = {};
            throw new KmgToolMsgException(messageTypes, messageArgs);

        }

        super.addCsvRow(this.itemName);
        result = true;

        return result;

    }

    /**
     * 項目を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean addItemToCsvRows() throws KmgToolMsgException {

        boolean result = false;

        if (this.item == null) {

            final KmgToolGenMessageTypes messageTypes = KmgToolGenMessageTypes.KMGTOOL_GEN32010;
            final Object[]               messageArgs  = {};
            throw new KmgToolMsgException(messageTypes, messageArgs);

        }

        super.addCsvRow(this.item);
        result = true;

        return result;

    }

    /**
     * 列挙型定義から項目と項目名に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean convertEnumDefinition() throws KmgToolMsgException {

        boolean result = false;

        // 列挙型定義を正規表現でグループ化する
        final Pattern patternSrc = Pattern.compile(Enum2SwitchCaseCreationLogicImpl.ENUM_DEFINITION_PATTERN);
        final Matcher matcherSrc = patternSrc.matcher(this.getConvertedLine());

        // 列挙型定義ではないか
        if (!matcherSrc.find()) {
            // 定義ではない場合

            return result;

        }

        // 列挙型の情報を取得
        this.item = matcherSrc.group(ConvertEnumDefinitionTypes.ENUM_DEFINITION_CONSTANT_NAME.getGroupIndex()); // 項目
        this.itemName = matcherSrc.group(ConvertEnumDefinitionTypes.ENUM_DEFINITION_DISPLAY_NAME.getGroupIndex()); // 項目名

        result = true;
        return result;

    }

    /**
     * 項目を返す。
     *
     * @return 項目
     */
    @Override
    public String getItem() {

        final String result = this.item;
        return result;

    }

    /**
     * 項目名を返す。
     *
     * @return 項目名
     */
    @Override
    public String getItemName() {

        final String result = this.itemName;
        return result;

    }

}
