@echo off
chcp 65001 >NUL
set HTTP_PROXY=
set HTTPS_PROXY=
set NO_PROXY=
set "JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -Dlog4j.skipJansi=false"
.\jdk-21.0.4+7\bin\java.exe ^
  -Dlog4j.configurationFile=.\log4j2.xml ^
  -Xms3G -Xmx3G -jar mohist-1.20.1-859-server.jar nogui
pause
