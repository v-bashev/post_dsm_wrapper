package su.nsk.iae.post.dsm;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import com.google.inject.Injector;
import su.nsk.iae.post.PoSTStandaloneSetup;
import su.nsk.iae.post.deserialization.ModelDeserializer;
import su.nsk.iae.post.dsm.data.DsmRequestBody;
import su.nsk.iae.post.dsm.data.NullGeneratorContext;
import su.nsk.iae.post.generator.IPoSTGenerator;

public class Executor {

    public static String DSM_DIRECTORY;
    public static String DSM_GENERATOR_CLASS_NAME;

    public static String execute(DsmRequestBody requestBody) {
        try {
            if (DSM_DIRECTORY == null || DSM_GENERATOR_CLASS_NAME == null) {
                readProperties();
            }

            final Injector injector = PoSTStandaloneSetup.getInjector();
            final JavaIoFileSystemAccess fsa = injector.getInstance(JavaIoFileSystemAccess.class);
            final Resource resource = ModelDeserializer.deserializeFromXMI(requestBody.getAst());
            final IGeneratorContext context = new NullGeneratorContext();
            final String generated = requestBody.getRoot() +
                    File.separator +
                    DSM_DIRECTORY +
                    File.separator +
                    requestBody.getFileName();
            fsa.setOutputPath(generated);

            Class<?> clazz = Class.forName(DSM_GENERATOR_CLASS_NAME);
            final IPoSTGenerator generator = (IPoSTGenerator) clazz.getConstructor().newInstance();
            generator.beforeGenerate(resource, fsa, context);
            generator.doGenerate(resource, fsa, context);
            generator.afterGenerate(resource, fsa, context);

            return "Files generated in " + generated;
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    private static void readProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(App.class.getClassLoader().getResourceAsStream("dsm.properties"));
        DSM_DIRECTORY = properties.getProperty("dsm.directory");
        DSM_GENERATOR_CLASS_NAME = properties.getProperty("dsm.generatorClassName");
    }
}