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
import js.wood.BuilderConfig;

@Mojo(name = "build", defaultPhase = LifecyclePhase.PREPARE_PACKAGE, requiresDependencyResolution = ResolutionScope.RUNTIME, threadSafe = true)
public class PreparePackageMojo extends AbstractMojo {
	@Parameter(defaultValue = "${project}", readonly = true)
	private MavenProject project;

	@Parameter(property = "outputDirectory", required = false, defaultValue = "target/site")
	private String outputDirectory;

	@Parameter(property = "buildNumber", required = false, defaultValue = "0")
	private int buildNumber;

	@Parameter(property = "disabled", required = false, defaultValue = "false")
	private boolean disabled;

	public void execute() throws MojoExecutionException {
		try {
			BuilderConfig config = new BuilderConfig();
			config.setProjectDir(project.getBasedir());
			// build directory from BuilderConfig is absolute but outputDirectory field is relative to project root 
			config.setBuildDir(new File(project.getBasedir(), outputDirectory));
			config.setBuildNumber(buildNumber);

			if (disabled) {
				// ensure build directory is created even if processing is disabled
				config.getBuildDir().mkdirs();
				return;
			}

			Builder builder = new Builder(config);
			builder.build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MojoExecutionException(e.getMessage());
		}
	}
}
