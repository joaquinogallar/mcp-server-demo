# MCP Server Demo

A Model Context Protocol (MCP) server implementation for weather services.

## Prerequisites

- Java Runtime Environment (JRE) 8 or higher
- Claude Desktop application

## Deployment

1. **Build the JAR file**:
   ```bash
   ./mvnw clean package
   ```
   The JAR file will be created in the `target/` directory.

2. **Configure Claude Desktop**:

   Add the following configuration to your `claude_desktop_config.json` file:
   ```json
   {
     "mcpServers": {
       "weather-server": {
         "command": "java",
         "args": [
           "-jar",
           "<JAR-DIRECTORY>/mcp-server-demo.jar"
         ]
       }
     }
   }
   ```

   Replace `<JAR-DIRECTORY>` with the full path to your JAR file in the `target/` directory.

3. **Restart Claude Desktop** to load the new MCP server configuration.

## Usage

Once configured, the weather server will be available in Claude Desktop. You can ask Claude to:
- Get current weather conditions for any city
- Retrieve weather forecasts
- Access weather-related data through the MCP protocol

## Configuration File Location

The `claude_desktop_config.json` file is typically located at:
- **macOS**: `~/Library/Application Support/Claude/claude_desktop_config.json`
- **Windows**: `%APPDATA%\Claude\claude_desktop_config.json`
- **Linux**: `~/.config/Claude/claude_desktop_config.json`

## Troubleshooting

- Ensure the JAR file path in the configuration is absolute and correct
- Check that Java is installed and accessible from the command line
- Restart Claude Desktop after making configuration changes
- Check Claude Desktop logs for any connection issues