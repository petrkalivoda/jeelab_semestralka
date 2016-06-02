package jeelab.setup;

import java.util.LinkedHashMap;
import java.util.Map;

import org.wildfly.swarm.config.security.Flag;
import org.wildfly.swarm.config.security.SecurityDomain;
import org.wildfly.swarm.config.security.security_domain.ClassicAuthentication;
import org.wildfly.swarm.config.security.security_domain.authentication.LoginModule;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jpa.JPAFraction;
import org.wildfly.swarm.security.SecurityFraction;
import org.wildfly.swarm.transactions.TransactionsFraction;

/**
 * Main class for Swarm run
 * @author Petr Kalivoda
 *
 */
public class SwarmRun {
	public static void main(String[] args) throws Exception {
		//1. create container
		Container container = new Container();

		//2. configure datasource fraction
		container.fraction(new DatasourcesFraction()
                .jdbcDriver("h2", (d) -> {
                    d.driverClassName("org.h2.Driver");
                    d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
                    d.driverModuleName("com.h2database.h2");
                    
                }).dataSource("JeelabDS", (ds) -> {
                    ds.driverName("h2");
                    ds.connectionUrl("jdbc:h2:mem:jeelabSwarm;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
                    ds.jndiName("java:/JeelabDS");
                    ds.jta(Boolean.TRUE);
                    ds.userName("sa");
                    ds.password("sa");
                }));
		
		//3. get rid of default DS
		container.fraction(new JPAFraction()
                .inhibitDefaultDatasource()
                .defaultDatasource("jboss/datasources/JeelabDS"));
		
		//4. create transactions fraction
		container.fraction(TransactionsFraction.createDefaultFraction());
		
		//5. create auth module options
		Map<String, String> moduleOptions = new LinkedHashMap<>();
		moduleOptions.put("dsJndiName", "java:/JeelabDS");
		moduleOptions.put("principalsQuery", "SELECT password AS 'Password' FROM user_table WHERE email = ? AND active = 1");
		moduleOptions.put("rolesQuery", "SELECT name, 'Roles' FROM user_table_role_table JOIN role_table ON (role_table.id = user_table_role_table.roles_id) JOIN user_table ON (user_table.id = user_table_role_table.User_id) WHERE email = ?");
		moduleOptions.put("hashAlgorithm", "SHA-256");
		moduleOptions.put("hashEncoding", "hex");
        
		//6. create security fraction
		container.fraction(SecurityFraction.defaultSecurityFraction()
                .securityDomain(new SecurityDomain<>("jeedb")
                    .classicAuthentication(new ClassicAuthentication<>()
                        .loginModule(new LoginModule<>("Database")
                            .code("Database")
                            .flag(Flag.REQUIRED).moduleOptions(moduleOptions)))));
		
		//7. start container
		container.start();
		
        //8. deploy app
		container.deploy();
	}
}
