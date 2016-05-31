package jeelab.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

/**
 * Abstract superclass for a deployable test containing
 * all desired resources.
 * @author Petr Kalivoda
 *
 */
@RunWith(Arquillian.class)
abstract public class AbstractDeployableTest {
	
	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "jee-test.jar")
				.addPackages(true, "jeelab")
				.addAsManifestResource("beans.test.xml", "beans.xml")
				.addAsResource("META-INF/persistence.test.xml", "META-INF/persistence.xml");

		return jar;
	}
}
