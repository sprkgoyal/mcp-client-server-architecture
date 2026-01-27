package spring.ai.mcp.client.configuration;

import io.modelcontextprotocol.client.transport.customizer.McpSyncHttpClientRequestCustomizer;
import org.springaicommunity.mcp.security.client.sync.AuthenticationMcpTransportContextProvider;
import org.springaicommunity.mcp.security.client.sync.oauth2.http.client.OAuth2AuthorizationCodeSyncHttpRequestCustomizer;
import org.springframework.ai.mcp.customizer.McpSyncClientCustomizer;
import org.springframework.ai.tool.resolution.StaticToolCallbackResolver;
import org.springframework.ai.tool.resolution.ToolCallbackResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.util.List;

@Configuration
class McpConfiguration {

    @Bean
    McpSyncClientCustomizer syncClientCustomizer() {
        return (name, syncSpec) ->
                syncSpec.transportContextProvider(
                        new AuthenticationMcpTransportContextProvider()
                );
    }

    @Bean
    McpSyncHttpClientRequestCustomizer requestCustomizer(
            OAuth2AuthorizedClientManager clientManager
    ) {
        return new OAuth2AuthorizationCodeSyncHttpRequestCustomizer(
                clientManager,
                "authserver"
        );
    }

    @Bean
    ToolCallbackResolver resolver() {
        return new StaticToolCallbackResolver(List.of());
    }

}
