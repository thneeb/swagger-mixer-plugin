package de.neebs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Goal which touches a timestamp file.
 */
@Mojo( name = "mixer", defaultPhase = LifecyclePhase.GENERATE_SOURCES )
public class MyMojo extends AbstractMojo {
    /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputFile", required = true )
    private String outputFile;

    @Parameter( property = "inputFile", required = true)
    private String inputFile;

    public void execute() throws MojoExecutionException {
        File file = new File(outputFile);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        try {
            SwaggerMixer swaggerMixer = new SwaggerMixer();
            swaggerMixer.run(new ObjectMapper(), inputFile, outputFile);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
