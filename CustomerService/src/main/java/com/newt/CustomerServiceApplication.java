package com.newt;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ImplicitGrant;
import springfox.documentation.service.LoginEndpoint;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication 
@EnableDiscoveryClient 
@EnableSwagger2
public class CustomerServiceApplication{
	private static final Logger logger = Logger.getLogger(CustomerServiceApplication.class);
	public static void main(String[] args) {
		logger.info("CustomerServices invokation...");
		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	
	@Value("${swagger.oauth.url}")
	private String swaggerOAuthUrl;
	
	public static final String securitySchemaOAuth2 = "oauth2schema";
    public static final String authorizationScopeGlobal = "global";
    public static final String authorizationScopeGlobalDesc ="accessEverything";

	@Bean
	public Docket swaggerSpringMvcPlugin() {
		List<SecurityScheme> list = new ArrayList<SecurityScheme>();
		list.add(oauth2());
		List<SecurityContext> list2 = new ArrayList<SecurityContext>();
		list2.add(securityContext());

	      return new Docket(DocumentationType.SWAGGER_2)
	              .groupName("api")
	              .select()
	              .paths(paths())
	              .build()
	              .apiInfo(apiInfo())
	              .securitySchemes(list)
	              .securityContexts(list2);
	  }

	  //Here is an example where we select any api that matches one of these paths
		private Predicate<String> paths() {
	      return or(regex("/customer/.*"),
	              regex("/customer.*"));
	  }

	  private ApiInfo apiInfo() {
	      return new ApiInfoBuilder()
	              .title("Customer Microservice")
	              .description("Provides services to add customer and query customer data")
	              .version("2.0")
	              .termsOfServiceUrl("devopsinabox@newtglobal.com")
	              .build();
	  }


	  @Bean
	  SecurityScheme oauth2() {
	      return new OAuthBuilder()
	              .name(securitySchemaOAuth2)
	              .grantTypes(grantTypes())
	              .scopes(scopes())
	              .build();
	  }

	  List<AuthorizationScope> scopes() {
		  List<AuthorizationScope> authScope = new ArrayList<AuthorizationScope>();
		  authScope.add(new AuthorizationScope(authorizationScopeGlobal,authorizationScopeGlobal));
	      return authScope;
	  }

	  List<GrantType> grantTypes() {
	      List<GrantType> grantType = new ArrayList<GrantType>();
	      grantType.add(new ImplicitGrant(new LoginEndpoint(swaggerOAuthUrl),"access_token"));
	      return grantType;
	  }
	  
	  private SecurityContext securityContext() {
	        return SecurityContext.builder()
	                .securityReferences(defaultAuth())
	                .forPaths(paths())
	                .build();
	    }

	    private List<SecurityReference> defaultAuth() {
	    	List<SecurityReference> secReference  = new ArrayList<>();
	        AuthorizationScope authorizationScope
	                = new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobalDesc);
	        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	        authorizationScopes[0] = authorizationScope;
	        secReference.add(new SecurityReference(securitySchemaOAuth2, authorizationScopes));
	        return secReference;
	    }
	    
	    @Bean
	    SecurityConfiguration security() {
	      return new SecurityConfiguration(
	          "my-trusted-client",
	          "secret",
	          "MY_OAUTH_REALM",
	          "MY_OAUTH",
	          "apiKey",
	          ApiKeyVehicle.HEADER, 
	          "my_rest_api", 
	          "," /*scope separator*/);
	    }

	    @Bean
	    UiConfiguration uiConfig() {
	      return new  UiConfiguration(
	          "validatorUrl",// url
	          "none",       // docExpansion          => none | list
	          "alpha",      // apiSorter             => alpha
	          "schema",     // defaultModelRendering => schema
	          UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
	          false,        // enableJsonEditor      => true | false
	          true       // showRequestHeaders    => true | false
	 );      // requestTimeout => in milliseconds, defaults to null (uses jquery xh timeout)
	    }

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private static final String REALM="MY_OAUTH_REALM";
	
	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UserApprovalHandler userApprovalHandler;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory()
	        .withClient("my-trusted-client")
            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
            .scopes("read", "write", "trust","global")
            .secret("secret")
            .accessTokenValiditySeconds(120).//Access token is only valid for 2 minutes.
            refreshTokenValiditySeconds(600);//Refresh token is only valid for 10 minutes.
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
				.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.realm(REALM+"/client");
	}
}

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "my_rest_api";
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.
		anonymous().disable()
		.requestMatchers().antMatchers("/customer/**")
		.and().authorizeRequests()
		.antMatchers("/customer/**").access("hasRole('ADMIN')")
		.antMatchers("/customer/**").access("hasRole('USER')")
        .antMatchers("/customer/**").access("#oauth2.hasScope('global')")
		.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

}

@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private ClientDetailsService clientDetailsService;
	
	@Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .withUser("newtadmin").password("newt@123").roles("ADMIN").and()
        .withUser("newtuser").password("newt@123").roles("USER");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	@Bean
	@Autowired
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		return handler;
	}
	
	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}
}
}


