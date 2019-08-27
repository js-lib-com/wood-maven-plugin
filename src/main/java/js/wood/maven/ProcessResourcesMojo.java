package js.wood.maven;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import js.wood.Builder;

@Mojo(defaultPhase = LifecyclePhase.PROCESS_RESOURCES, name = "process-resources")
public class ProcessResourcesMojo extends AbstractMojo {
	@Parameter(defaultValue = "${project}", readonly = true)
	private MavenProject project;

	@Parameter(property = "outputDirectory", required = false, defaultValue = "target/site")
	private File outputDirectory;

	@Parameter(property = "buildNumber", required = false, defaultValue = "0")
	private int buildNumber;

	public void execute() throws MojoExecutionException {
		try {
			Builder builder = new Builder(project.getBasedir().getAbsolutePath());
			builder.setSiteDir(outputDirectory);
			if (buildNumber > 0) {
				builder.setBuildNumber(buildNumber);
			}
			builder.build();
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}
}
