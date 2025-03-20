package kmg.tool.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * KMG ツールプロパティローダー Spring Bootの起動時にプロパティファイルを読み込むためのクラス
 */
@Component
public class KmgToolPropertiesLoader implements EnvironmentPostProcessor {

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

        // kmg-tool-application.propertiesを読み込む
        final Resource resource = new ClassPathResource("kmg-tool-application.properties");

        if (!resource.exists()) {

            return;

        }

        final Properties properties = new Properties();

        try (InputStream inputStream = resource.getInputStream()) {

            properties.load(inputStream);

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/20 ログ
            e.printStackTrace();
            return;

        }

        // PropertiesをMap<String, Object>に変換
        final Map<String, Object> map = new HashMap<>();

        for (final Object key : properties.keySet()) {

            map.put(key.toString(), properties.get(key));

        }

        // プロパティを環境に追加
        final PropertySource<?> propertySource = new MapPropertySource("kmg-tool-properties", map);
        environment.getPropertySources().addFirst(propertySource);

    }
}
