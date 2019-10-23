package js.wood.maven;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

@Mojo(name = "clean", defaultPhase = LifecyclePhase.CLEAN, requiresDependencyResolution = ResolutionScope.RUNTIME, threadSafe = true)
public class CleanMojo extends AbstractMojo {
	@Parameter(defaultValue = "${project}", readonly = true)
	private MavenProject project;

	@Parameter(property = "outputDirectory", required = false, defaultValue = "target/site")
	private String outputDirectory;

	public void execute() throws MojoExecutionException {
		try {
			delete(new File(project.getBasedir(), outputDirectory));
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}

	private void delete(File file) throws IOException {
		if (file.isDirectory()) {
			if (file.getName().equals("WEB-INF")) {
				return;
			}

			File[] entries = file.listFiles();
			if (entries != null) {
				for (File entry : entries) {
					delete(entry);
				}
			}
		}

		getLog().info("Deleting file " + file);
		if (!file.delete()) {
			// throw new IOException("Failed to delete " + file);
		}
	}
}
