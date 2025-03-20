package kmg.tool.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * KMG ツール設定クラス
 */
@Configuration
@PropertySource("classpath:kmg-tool-application.properties")
public class KmgToolConfig {
    // 処理なし
}
