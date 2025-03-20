package kmg.tool.infrastructure.config;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import kmg.foundation.infrastructure.config.KmgFundPropertiesLoader;

/**
 * KMG ツールプロパティローダー Spring Bootの起動時にプロパティファイルを読み込むためのクラス
 */
@Component
public class KmgToolPropertiesLoader extends KmgFundPropertiesLoader {

    /**
     * 環境後処理
     *
     * @param environment
     *                    環境
     * @param application
     *                    アプリケーション
     */
    @Override
    public void postProcessEnvironment(final ConfigurableEnvironment environment, final SpringApplication application) {

        super.postProcessEnvironment(environment, application);

    }

}
