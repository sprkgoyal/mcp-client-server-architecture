package spring.ai.mcp.server.mcp_tools;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class McpTools {

    @PreAuthorize("hasAuthority('SCOPE_mcp:tools')")
    @McpTool(description="returns the current temperature of the region in celsius")
    public float get_current_weather(String region) {
        return 17.0F;
    }

    @PreAuthorize("hasAuthority('SCOPE_mcp:tools')")
    @McpTool(description="returns the history of temperature of the region in celsius")
    public float[] get_weather_history(String region) {
        return new float[]{14.0F, 14.4F, 15.1F, 17.0F};
    }

}
