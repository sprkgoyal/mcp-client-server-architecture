# MCP Client-Server Architecture
MCP using Spring AI and secured using Keycloak and OAuth2


## How to Setup

### 1. Setup Keycloak

Download Keycloak and run command

```bash
kc start-dev
```
Open `http://localhost:8080`
(Setup Root Account if opening for the first time)

1. Create `Realm`
   1. Click `Manage Realms`
   2. Give any Realm name, e.g. `mcp-realm`
   3. Click `Create`

2. Goto `Clients`
   1. Click `Create Client`
   2. Give any Client ID, e.g. `mcp-test` and click Next
   3. If you want Client authentication, enable it else skip it
   4. Select `Direct access grants` and click Next
   5. Enter
      1. Root URL of you client application, e.g., `http://localhost:6274` for MCP Inspector
      2. Home URL: `http://localhost:6274/`
      3. Valid Redirect URIs: `http://localhost:6274/*`
      4. Web Origins: `http://localhost:6274/*`
      5. Click Save

3. Again, goto `Clients`
   1. Click client name which you created, `mcp-test` in my case
   2. Goto `Roles` tab
   3. Click `Create Role`
   4. Create a role with name `admin_role` and click Save
   5. Create another role with name `user_role`

4. Again, goto `Clients`
   1. Click `Client Registration` tab
   2. Click `Trusted Hosts`
   3. In Trusted Hosts, add your client host, e.g. `http://localhost:6274`

5. Open `Client Scopes`
   1. Click `Create Client Scope`
   2. Name the scope `mcp:tools`
   3. Type `Optional`
   4. Enable `Include in token scope` and click Save

6. Again open `Client Scopes`
   1. Select your scope
   2. Goto `Mappers` tab
   3. Click `Add mapper` -> `By Configuration` -> `Audience`
   4. Name the mapper, e.g. `MCP Tools Audience`
   5. Set `Include Custom Audience` to your server address e.g. `http://localhost:9090/mcp`
   6. Enable `Add to access token` and `Add to token introspection`
   7. Click Save

7. Again open `Client Scopes`
   1. Select you scope
   2. Goto `Scope` tab
   3. Click `Assign Role` -> `Client Roles`
   4. Select `admin_role` or the role you want to include that scope in

8. Goto `Users`
   1. Click `Add User`
   2. Enable `Email Verified`
   3. Set
      1. Username = `admin`
      2. Email = `admin@mail.com`
      3. First name = `Admin`
      4. Last name = `Admin`
   4. In Users, click `admin`
   5. Goto `Credentials` tab
   6. Click `Set Password`
   7. Set password and disable `Tempperory`
   8. Goto `Role Mapping` tab
   9. Click `Assign role` -> `Client roles`
   10. Select `admin_role` for the role you want to assign this user
   11. Similarly create one more user with Username `user` and assign one role to it.

### 2. Get access_token for Admin

```bash
curl --location 'http://localhost:8080/realms/mcp-realm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=mcp-test' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'username=admin' \
--data-urlencode 'password=admin' \
--data-urlencode 'scope=profile email mcp:tools'
```

It should return a json similar like this

```json
{
    "access_token": "",
    "expires_in": 300,
    "refresh_expires_in": 1800,
    "refresh_token": "",
    "token_type": "Bearer",
    "not-before-policy": 0,
    "session_state": "",
    "scope": ""
}
```

Copy the access_token. \
Remember this token will expire in 5 minutes.

### 3. Setup MCP Inspector for debugging

Download node.js first.

Run command

```bash
npx @modelcontextprotocol/inspector@0.17.2
```

NOTE: I am using v0.17.2 because Spring AI is using older MCP protocol at the time of this code development.

It will automatically open the browser with MCP Inspector running.

From the left pane

1. Select Transport Type `Streamable HTTP`
2. URL `http://localhost:9090/mcp`
3. Connection Type `Direct`
4. Expand Authentication
   1. Add Custom Headers
   2. Header Name `Authorization`
   3. Header Value `Bearer {access_token}`. Paste the access_token here.
5. Click `Connect`
6. After the connection is established
7. Goto `Tools` tab
8. Click `List Tools`
9. Select the tool annotated with `@PreAuthorise`, e.g. `get_weather` in out case.
10. Set the region with some random value and `Run Tool`
11. It will return the result.


If we change the curl command and use `user` username, it will return `Access Denied` 