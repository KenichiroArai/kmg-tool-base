package kmg.tool.application.model.jdts.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import kmg.core.infrastructure.model.val.KmgValDataModel;
import kmg.core.infrastructure.model.val.KmgValsModel;
import kmg.core.infrastructure.model.val.impl.KmgValDataModelImpl;
import kmg.core.infrastructure.model.val.impl.KmgValsModelImpl;
import kmg.core.infrastructure.utils.KmgListUtils;
import kmg.core.infrastructure.utils.KmgMapUtils;
import kmg.tool.application.model.jdts.JdtsConfigsModel;
import kmg.tool.application.model.jdts.JdtsTagConfigModel;
import kmg.tool.application.types.jdts.JdtsConfigKeyTypes;
import kmg.tool.infrastructure.exception.KmgToolValException;
import kmg.tool.infrastructure.type.msg.KmgToolValMessageTypes;

/**
 * Javadocタグ設定の構成モデル<br>
 * YAMLデータのJdtsConfigurationsセクションを表現するモデル
 * <p>
 * Jdtsは、JavadocTagSetterの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JdtsConfigsModelImpl implements JdtsConfigsModel {

    /** Javadoc追加のタグ設定モデルのリスト */
    private final List<JdtsTagConfigModel> jdtsTagConfigModels;

    /**
     * コンストラクタ<br>
     *
     * @param yamlData
     *                 YAMLデータ
     *
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    public JdtsConfigsModelImpl(final Map<String, Object> yamlData) throws KmgToolValException {

        this.jdtsTagConfigModels = new ArrayList<>();

        final KmgValsModel valsModel = new KmgValsModelImpl();

        if (KmgMapUtils.isEmpty(yamlData)) {

            // TODO KenichiroArai 2025/05/08 例外処理
            final KmgToolValMessageTypes valMsgTypes  = KmgToolValMessageTypes.NONE;
            final Object[]               valMsgArgs   = {};
            final KmgValDataModel        valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
            valsModel.addData(valDataModel);

            throw new KmgToolValException(valsModel);

        }

        /* YAMLデータからJdtsConfigsセクションを取得 */
        final ObjectMapper              mapper      = new ObjectMapper(new YAMLFactory());
        final List<Map<String, Object>> javadocTags = mapper
            .convertValue(yamlData.get(JdtsConfigKeyTypes.JDTS_CONFIGS.get()), List.class);

        if (KmgListUtils.isEmpty(javadocTags)) {

            // TODO KenichiroArai 2025/05/08 例外処理
            final KmgToolValMessageTypes valMsgTypes  = KmgToolValMessageTypes.NONE;
            final Object[]               valMsgArgs   = {};
            final KmgValDataModel        valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
            valsModel.addData(valDataModel);

            throw new KmgToolValException(valsModel);

        }

        /* 各タグ設定の処理 */
        for (final Map<String, Object> tagConfig : javadocTags) {

            /* モデルの作成と追加 */
            final JdtsTagConfigModel model = new JdtsTagConfigModelImpl(tagConfig);
            this.jdtsTagConfigModels.add(model);

        }

    }

    /**
     * Javadocタグ設定のタグ構成モデルのリストを返す<br>
     *
     * @return Javadocタグ設定のタグ構成モデルのリスト
     */
    @Override
    public List<JdtsTagConfigModel> getJdaTagConfigModels() {

        final List<JdtsTagConfigModel> result = this.jdtsTagConfigModels;
        return result;

    }

}
