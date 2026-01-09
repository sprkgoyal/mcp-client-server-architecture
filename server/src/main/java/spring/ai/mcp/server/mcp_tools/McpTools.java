package spring.ai.mcp.server.mcp_tools;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

@Component
public class McpTools {

    @McpTool(name="get_weather", description="returns the temperature of current region in celcius")
    public float get_weather(String region) {
        return 17.0F;
    }

}
