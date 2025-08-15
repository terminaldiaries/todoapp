# todoapp
## August 6th 2025
### Enable Tomcat Manger for Remote Access
a. Edit tomcat-users.xml
Path:
```
$CATALINA_HOME/conf/tomcat-users.xml
```
Add a user with manager-script role:
```
<role rolename="manager-script"/>
<user username="jenkins" password="yourStrongPassword" roles="manager-script"/>
```

## Security Note: Never use manager-gui or admin-gui roles for automated tools like Jenkins. Use manager-script only.

### Allow Remote IPs to Access the Manager
a. Edit web.xml (Optional for older versions)
Tomcat 9+ uses the RemoteAddrValve for access control.

b. Edit context.xml of the manager webapp
Path:
```
$CATALINA_HOME/webapps/manager/META-INF/context.xml
```

Comment out or remove this line to allow external access:
```
<Valve className="org.apache.catalina.valves.RemoteAddrValve"
       allow="127\.\d+\.\d+\.\d+|::1" />

3. Start or Restart Tomcat
```
$CATALINA_HOME/bin/shutdown.sh
$CATALINA_HOME/bin/startup.sh
```

4. Test Remote Deployment
You can test with curl or a browser:
```
curl -u jenkins:yourStrongPassword http://your-server-ip:8080/manager/text/list
```

If you see a list of deployed apps, it’s working.

## Notes:
   If /manager is missing from $CATALINA_HOME/webapps to the following:

 1. Determine Your Tomcat Version
 Run:
 ```
 $CATALINA_HOME/bin/version.sh
 ```
Take note of the version (e.g., 9.0.83 or 10.1.x)

2. Download the Manager App
You need to get the matching manager app .war from the Tomcat distribution site:

If you're using Tomcat 9.x:
Go to:
```
https://archive.apache.org/dist/tomcat/tomcat-9/v<your-version>/bin/
```

Example for 9.0.83

```
wget https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.83/bin/apache-tomcat-9.0.83.tar.gz
```

Then extract only the webapps/manager directory:
```
tar -xzf apache-tomcat-9.0.83.tar.gz
cp -r apache-tomcat-9.0.83/webapps/manager $CATALINA_HOME/webapps/
```

4. Restart Tomcat
```
$CATALINA_HOME/bin/shutdown.sh
$CATALINA_HOME/bin/startup.sh
```