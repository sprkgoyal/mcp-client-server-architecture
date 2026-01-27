package spring.ai.mcp.client.controller;

import io.modelcontextprotocol.client.McpSyncClient;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class BaseController {

    private final List<McpSyncClient> clients;

    @GetMapping("/")
    Map<String, String> healthStatus() {
        return Map.of("status", "healthy");
    }

    @GetMapping("/health")
    Map<String, String> healthStatus2() {
        return Map.of("status", "healthy");
    }

    @GetMapping("/api/mcp-list-tools")
    String listMcpTools() {
        SyncMcpToolCallbackProvider mcpToolCallback = SyncMcpToolCallbackProvider.builder().mcpClients(clients).build();
        return Arrays.stream(mcpToolCallback.getToolCallbacks()).map(ToolCallback::getToolDefinition).toList().toString();
    }

}
