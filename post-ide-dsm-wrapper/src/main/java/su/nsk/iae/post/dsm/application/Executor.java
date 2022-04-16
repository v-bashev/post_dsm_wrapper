package su.nsk.iae.post.dsm.application;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import com.google.inject.Injector;
import su.nsk.iae.post.IDsmExecutor;
import su.nsk.iae.post.PoSTStandaloneSetup;
import su.nsk.iae.post.dsm.domain.*;

public class Executor {

    public static String DSM_DIRECTORY;
    public static String DSM_EXECUTOR_CLASS_NAME;

    public static String execute(DsmRequestDomain requestBody) {
        try {
            Logger.info(Executor.class, "executing for request: " +
                    "id = " + requestBody.getId() +
                    ", root = " + requestBody.getRoot() +
                    ", fileName = " + requestBody.getFileName() +
                    ", ast = " + requestBody.getAst()
            );

            if (DSM_DIRECTORY == null || DSM_EXECUTOR_CLASS_NAME == null) {
                readProperties();
            }

            Logger.info(Executor.class, "all required properties are initialized");
            final Injector injector = PoSTStandaloneSetup.getInjector();
            final JavaIoFileSystemAccess fsa = injector.getInstance(JavaIoFileSystemAccess.class);
//            final Resource resource = ModelDeserializer.deserializeFromXMI(requestBody.getAst());
            final IGeneratorContext context = new NullGeneratorContext();
            final String generated = requestBody.getRoot() +
                    File.separator +
                    DSM_DIRECTORY +
                    File.separator +
                    requestBody.getFileName();
            fsa.setOutputPath(generated);
            Logger.info(Executor.class, "output path: " + generated);

            Logger.info(Executor.class, "creating executor...");
            Class<?> clazz = Class.forName(DSM_EXECUTOR_CLASS_NAME);
            final IDsmExecutor executor = (IDsmExecutor) clazz.getConstructor().newInstance();
            Logger.info(Executor.class, "successfully created executor of class " + executor.getClass().getName());
//            generator.beforeGenerate(resource, fsa, context);
//            generator.doGenerate(resource, fsa, context);
//            generator.afterGenerate(resource, fsa, context);

            Logger.info(Executor.class, "files generated");
            return "Files generated in " + generated;
        } catch (Exception e) {
            Logger.error(Executor.class, e.getMessage());
            return "Error occurred: " + e.getMessage();
        }
    }

    private static void readProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(Executor.class.getClassLoader().getResourceAsStream("dsm.properties"));
        DSM_DIRECTORY = properties.getProperty("dsm.directory");
        DSM_EXECUTOR_CLASS_NAME = properties.getProperty("dsm.executorClassName");

        Logger.info(Executor.class, "properties values:");
        Logger.info(Executor.class, "DSM_DIRECTORY = " + DSM_DIRECTORY);
        Logger.info(Executor.class, "DSM_EXECUTOR_CLASS_NAME = " + DSM_EXECUTOR_CLASS_NAME);
    }
}