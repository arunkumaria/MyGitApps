#!/bin/zsh

echo "🧹 Uninstalling Docker Desktop completely..."

# Stop Docker processes
killall Docker 2>/dev/null
killall "Docker Desktop" 2>/dev/null
killall dockerd 2>/dev/null

# Remove Docker Desktop application
sudo rm -rf /Applications/Docker.app

# Remove Docker data and config
sudo rm -rf ~/.docker
sudo rm -rf ~/Library/Containers/com.docker.docker
sudo rm -rf ~/Library/Group\ Containers/group.com.docker
sudo rm -rf ~/Library/Application\ Support/Docker\ Desktop
sudo rm -rf ~/Library/Logs/Docker\ Desktop

# Remove system-wide components
sudo rm -f /Library/PrivilegedHelperTools/com.docker.vmnetd
sudo rm -f /Library/LaunchDaemons/com.docker.vmnetd.plist

# Remove Docker CLI symlinks
sudo rm -f /usr/local/bin/docker
sudo rm -f /usr/local/bin/docker-compose
sudo rm -f /usr/local/bin/com.docker.cli

# Remove stale socket
sudo rm -f /var/run/docker.sock

echo "✅ Docker Desktop fully uninstalled!"
