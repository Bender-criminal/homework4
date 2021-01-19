package ru.digitalhabbits.homework4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class PropertyPostProcessor implements EnvironmentPostProcessor {

    private static final String PROPERTIES_STORE = "classpath:config/*.properties";
    private final PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Resource path = new ClassPathResource(PROPERTIES_STORE);
        PropertySource<?> propertySource = loadProperties(path);
        environment.getPropertySources().addLast(propertySource);
    }

    private PropertySource<?> loadProperties(Resource path) {
        if (!path.exists()) {
            throw new IllegalArgumentException("Resource " + path + " does not exist");
        }
        try {
            return this.loader.load(path.getFilename(), path).get(0);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Failed to load configuration from " + path, ex);
        }
    }
}
