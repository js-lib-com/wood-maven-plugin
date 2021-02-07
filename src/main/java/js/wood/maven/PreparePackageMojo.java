package js.wood.maven;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import js.wood.Builder;

@Mojo(name = "build", defaultPhase = LifecyclePhase.PREPARE_PACKAGE, requiresDependencyResolution = ResolutionScope.RUNTIME, threadSafe = true)
public class PreparePackageMojo extends AbstractMojo {
	@Parameter(defaultValue = "${project}", readonly = true)
	private MavenProject project;

	@Parameter(property = "outputDirectory", required = false, defaultValue = "target/site")
	private File outputDirectory;

	@Parameter(property = "buildNumber", required = false, defaultValue = "0")
	private int buildNumber;

	@Parameter(property = "disabled", required = false, defaultValue = "false")
	private boolean disabled;

	public void execute() throws MojoExecutionException {
		if (disabled) {
			// takes care to create empty directory if processing is disabled
			outputDirectory.mkdirs();
			return;
		}

		try {
			Builder builder = new Builder(project.getBasedir().getAbsolutePath());
			builder.setSiteDir(outputDirectory);
			if (buildNumber > 0) {
				builder.setBuildNumber(buildNumber);
			}
			builder.build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MojoExecutionException(e.getMessage());
		}
	}
}
