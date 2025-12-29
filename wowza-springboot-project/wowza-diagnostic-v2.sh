#!/bin/bash

echo "=========================================="
echo "WOWZA COMPLETE DIAGNOSTIC"
echo "=========================================="

echo -e "\n1. Container Status:"
docker ps | grep wowza

echo -e "\n2. Wowza Startup Messages:"
docker logs wowza-server 2>&1 | grep -i "started\|running\|error\|failed\|license"

echo -e "\n3. Java Process:"
docker exec wowza-server ps aux 2>/dev/null | grep -i java | head -3

echo -e "\n4. Check if Wowza process exists:"
docker exec wowza-server ps aux 2>/dev/null | grep -i wowza

echo -e "\n5. Wowza Bin Directory:"
docker exec wowza-server ls -la /usr/local/WowzaStreamingEngine/bin/ 2>/dev/null | head -10

echo -e "\n6. Try Wowza Status:"
docker exec wowza-server /usr/local/WowzaStreamingEngine/bin/startup.sh status 2>&1

echo -e "\n7. Last 30 Lines of Access Log:"
docker exec wowza-server tail -30 /usr/local/WowzaStreamingEngine/logs/wowzastreamingengine_access.log 2>&1

echo -e "\n8. Last 30 Lines of Error Log:"
docker exec wowza-server tail -30 /usr/local/WowzaStreamingEngine/logs/wowzastreamingengine_error.log 2>&1

echo -e "\n9. REST API Test:"
curl -s http://localhost:8186/v2/servers/_defaultServer_/status 2>&1 | head -20

echo -e "\n10. Check Supervisor Status (if available):"
docker exec wowza-server supervisorctl status 2>&1

echo -e "\n11. Last 50 Docker Container Logs:"
docker logs --tail 50 wowza-server 2>&1

echo "=========================================="
