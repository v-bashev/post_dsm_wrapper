package su.nsk.iae.post.dsm.application;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import su.nsk.iae.post.IDsmExecutor;

import org.eclipse.emf.ecore.resource.Resource;

import su.nsk.iae.post.deserialization.ModelDeserializer;

public class Executor {
    public static String DSM_EXECUTOR_CLASS_NAME;

    public static String execute(LinkedHashMap<String, Object> request) {
        try {
            if (DSM_EXECUTOR_CLASS_NAME == null) {
                readProperties();
            }
            Logger.info(Executor.class, "all required properties are initialized");
            Logger.info(Executor.class, "creating executor...");
            final String root = (String) request.get("root");
            final String fileName = (String) request.get("fileName");
            final Resource resource = ModelDeserializer.deserializeFromXMI((String) request.get("ast"));
            Class<?> clazz = Class.forName(DSM_EXECUTOR_CLASS_NAME);
            final IDsmExecutor executor = (IDsmExecutor) clazz.getConstructor().newInstance();
            Logger.info(Executor.class, "successfully created executor of class " + executor.getClass().getName());
            return executor.execute(root, fileName, resource);
        } catch (Exception e) {
            Logger.error(Executor.class, e.getMessage());
            return "Error occurred: " + e.getMessage();
        }
    }

    private static void readProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(Executor.class.getClassLoader().getResourceAsStream("dsm.properties"));
        DSM_EXECUTOR_CLASS_NAME = properties.getProperty("dsm.executorClassName");
        Logger.info(Executor.class, "properties values:");
        Logger.info(Executor.class, "DSM_EXECUTOR_CLASS_NAME = " + DSM_EXECUTOR_CLASS_NAME);
    }
}