package su.nsk.iae.post.dsm;

import java.io.File;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import com.google.inject.Injector;
import su.nsk.iae.post.PoSTStandaloneSetup;
import su.nsk.iae.post.deserialization.ModelDeserializer;
import su.nsk.iae.post.dsm.data.DsmRequestBody;
import su.nsk.iae.post.dsm.data.NullGeneratorContext;
import su.nsk.iae.post.generator.st.STGenerator;

public class Generator {

    // TODO: rename to name of your dsm
    public static final String DSM_DIRECTORY = "st";

    public static String generate(DsmRequestBody requestBody) {
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

        //TODO Change to your generation class
        final STGenerator generator = new STGenerator();
        generator.beforeGenerate(resource, fsa, context);
        generator.doGenerate(resource, fsa, context);
        generator.afterGenerate(resource, fsa, context);

        return "Files generated in " + generated;
    }
}