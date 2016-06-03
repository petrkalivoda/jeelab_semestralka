package jeelab.test;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.runner.RunWith;

/**
 * Abstract superclass for a deployable test containing all desired resources.
 * 
 * @author Petr Kalivoda
 *
 */
@RunWith(Arquillian.class)
abstract public class AbstractDeployableTest {

	@Deployment
	public static WebArchive createDeployment() {
		File[] dependencies = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
				.withTransitivity().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, "jee-test.war").addPackages(true, "jeelab")
				.addAsWebInfResource("beans.test.xml", "beans.xml")
				.addAsResource("META-INF/persistence.test.xml", "META-INF/persistence.xml")
				.addAsLibraries(dependencies);

		return war;
	}
}
