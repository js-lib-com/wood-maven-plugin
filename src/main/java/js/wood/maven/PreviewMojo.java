package js.wood.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(defaultPhase = LifecyclePhase.PACKAGE, name = "preview")
public class PreviewMojo extends AbstractMojo {
	public void execute() throws MojoExecutionException {
	}
}
