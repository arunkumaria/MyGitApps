#!/bin/bash

echo "🚀 Building Wowza Spring Boot Module..."

# Navigate to Spring Boot project
#cd wowza-springboot-project

# Download Wowza JAR if needed (or copy from local installation)
if [ ! -f "wms-server.jar" ]; then
    echo "⚠️  Wowza JAR not found. Please copy it manually:"
    echo "   cp /Library/WowzaStreamingEngine/lib/wms-server.jar springboot-module-builder/"
    exit 1
fi

# Install Wowza JAR to local Maven
echo "📦 Installing Wowza JAR to Maven..."
mvn install:install-file \
  -Dfile=wms-server.jar \
  -DgroupId=com.wowza \
  -DartifactId=wms-server \
  -Dversion=4.8.0 \
  -Dpackaging=jar

# Build the project
echo "🔨 Building project..."
mvn clean package -DskipTests

# Copy module to wowza-modules directory
echo "📋 Copying module to Wowza..."
cp target/*-wowza-module.jar ../wowza-modules/wowza-custom-module.jar || \
   cp target/*.jar ../wowza-modules/wowza-custom-module.jar

echo "✅ Build complete!"
echo "   Module location: ../wowza-modules/wowza-custom-module.jar"

cd ..