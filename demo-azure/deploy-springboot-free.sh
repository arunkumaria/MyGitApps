#!/bin/bash
set -e

# Variables
RESOURCE_GROUP="springboot-free-rg"
APP_SERVICE_PLAN="springboot-free-plan"
WEBAPP_NAME="springboot-free-app-$RANDOM"
LOCATION="centralindia"   # ✅ eastus supports free plan
JAR_PATH="target/demo-azure-0.0.1-SNAPSHOT.jar"

echo "Creating resource group..."
az group create --name $RESOURCE_GROUP --location $LOCATION

echo "Creating App Service Plan (FREE Tier)..."
az appservice plan create \
  --name $APP_SERVICE_PLAN \
  --resource-group $RESOURCE_GROUP \
  --sku F1 \
  --is-linux

echo "Creating Web App..."
az webapp create \
  --resource-group $RESOURCE_GROUP \
  --plan $APP_SERVICE_PLAN \
  --name $WEBAPP_NAME \
  --runtime "JAVA:17-java17"

echo "Deploying Spring Boot JAR..."
az webapp deploy \
  --resource-group $RESOURCE_GROUP \
  --name $WEBAPP_NAME \
  --src-path $JAR_PATH \
  --type jar \
  --async false

echo "Setting JAVA_OPTS..."
az webapp config set \
  --resource-group $RESOURCE_GROUP \
  --name $WEBAPP_NAME \
  --generic-configurations '{"linuxFxVersion": "JAVA|17-java17"}'

echo "Deployment complete!"
echo "✅ Access your app at: https://$WEBAPP_NAME.azurewebsites.net"

