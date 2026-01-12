package spring.ai.mcp.server.mcp_tools;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class McpTools {

    @PreAuthorize("hasAuthority('SCOPE_mcp:tools')")
    @McpTool(name="get_weather", description="returns the temperature of current region in celsius")
    public float get_weather(String region) {
        return 17.0F;
    }

}
