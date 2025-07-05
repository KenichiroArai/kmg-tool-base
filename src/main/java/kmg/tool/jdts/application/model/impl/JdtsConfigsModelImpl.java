package kmg.tool.jdts.application.model.impl;

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
import kmg.tool.cmn.infrastructure.exception.KmgToolValException;
import kmg.tool.cmn.infrastructure.types.KmgToolValMsgTypes;
import kmg.tool.jdts.application.model.JdtsConfigsModel;
import kmg.tool.jdts.application.model.JdtsTagConfigModel;
import kmg.tool.jdts.application.types.JdtsConfigKeyTypes;

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

            final KmgToolValMsgTypes valMsgTypes  = KmgToolValMsgTypes.KMGTOOL_VAL33000;
            final Object[]           valMsgArgs   = {};
            final KmgValDataModel    valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
            valsModel.addData(valDataModel);

            throw new KmgToolValException(valsModel);

        }

        /* YAMLデータからJdtsConfigsセクションを取得 */
        final ObjectMapper              mapper      = new ObjectMapper(new YAMLFactory());
        final List<Map<String, Object>> javadocTags = mapper
            .convertValue(yamlData.get(JdtsConfigKeyTypes.JDTS_CONFIGS.get()), List.class);

        if (KmgListUtils.isEmpty(javadocTags)) {

            final KmgToolValMsgTypes valMsgTypes  = KmgToolValMsgTypes.KMGTOOL_VAL33001;
            final Object[]           valMsgArgs   = {
                JdtsConfigKeyTypes.JDTS_CONFIGS.getDisplayName(), JdtsConfigKeyTypes.JDTS_CONFIGS.get(),
            };
            final KmgValDataModel    valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
            valsModel.addData(valDataModel);

            throw new KmgToolValException(valsModel);

        }

        /* 各タグ設定の処理 */
        for (final Map<String, Object> tagConfig : javadocTags) {

            /* モデルの作成 */
            final JdtsTagConfigModel model;

            try {

                model = new JdtsTagConfigModelImpl(tagConfig);

            } catch (final KmgToolValException e) {

                valsModel.merge(e.getValidationsModel());
                continue;

            }

            /* モデルの追加 */
            this.jdtsTagConfigModels.add(model);

        }

        /* バリデーションをマージする */
        if (valsModel.isNotEmpty()) {

            throw new KmgToolValException(valsModel);

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
