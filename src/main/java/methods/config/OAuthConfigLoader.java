package methods.config;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class OAuthConfigLoader {
    private static final String PATH_CONFIG = "src/main/resources/oauth_config.yml";
    private static OAuthConfig instance;

    public static OAuthConfig loadConfig(){
        return loadConfig(PATH_CONFIG);
    }

    public static OAuthConfig loadConfig(String configPath){
        if (instance == null){
            try {
                InputStream inputStream = new FileInputStream(configPath);
                LoaderOptions options = new LoaderOptions();
                Constructor constructor = new Constructor(OAuthConfig.class, options);
                Yaml yaml = new Yaml(constructor);
                instance = yaml.load(inputStream);

//                Yaml yaml = new Yaml();
//                Map<String, Object> config = yaml.load(inputStream);
//
//                // создаем и заполняем объект oauthConfig
//                instance = new OAuthConfig();
//                instance.setApiKey((String) config.get("apiKey"));
//                instance.setApiSecret((String) config.get("apiSecret"));
            } catch (FileNotFoundException e){
                throw new RuntimeException("Oauth config файл не найден: " + configPath, e);
            }
        }
        return instance;
    }
}
