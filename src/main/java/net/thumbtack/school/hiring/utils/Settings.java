package net.thumbtack.school.hiring.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilder;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

public class Settings {
    private String databaseType;
    private static Settings ourInstance;

    private Settings() {
    }
    public static synchronized Settings getInstance() {
        if (ourInstance == null) {
            ourInstance = new Settings();
            ourInstance.readConfig();
        }
        return ourInstance;
    }

    public void readConfig(){
        try {
            Parameters params = new Parameters();
            File propertiesFile = new File("database.properties");

            ConfigurationBuilder<PropertiesConfiguration> builder =
                    new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                            .configure(params.fileBased().setFile(propertiesFile));

            Configuration config = builder.getConfiguration();
            String type = config.getString("database.type");
            setDatabaseType(type);
        } catch (ConfigurationException e) {
            System.out.println("Error reading properties file");
        }
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

}