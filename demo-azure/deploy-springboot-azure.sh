#!/bin/bash

# ======== CONFIGURATION ========
RG="springboot-free-rg"
LOCATION="centralindia"
PLAN="springboot-free-plan"
APP_NAME="springboot-app-$(date +%s)"
JAR_PATH="target/*.jar"       # adjust if your JAR name is different
MYSQL_SERVER="springboot-mysql-$(date +%s)"
MYSQL_ADMIN="mysqladmin"
MYSQL_PWD="P@ssw0rd123!"      # change to strong password
DB_NAME="springdb"
SKU_NAME="Standard_B1ms"       # Free tier compatible SKU

# ======== STEP 1: Create Resource Group ========
echo "Creating resource group..."
az group create --name $RG --location $LOCATION

# ======== STEP 2: Create App Service Plan (Free Tier) ========
echo "Creating App Service Plan..."
az appservice plan create \
    --name $PLAN \
    --resource-group $RG \
    --sku F1 \
    --is-linux

# ======== STEP 3: Create Web App ========
echo "Creating Web App..."
az webapp create \
    --resource-group $RG \
    --plan $PLAN \
    --name $APP_NAME \
    --runtime "JAVA|17-java17"

# ======== STEP 4: Deploy Spring Boot JAR ========
echo "Deploying Spring Boot App..."
az webapp deploy \
    --resource-group $RG \
    --name $APP_NAME \
    --src-path $JAR_PATH

# ======== STEP 5: Optional MySQL Flexible Server ========
echo "Creating MySQL Flexible Server..."
az mysql flexible-server create \
    --resource-group $RG \
    --name $MYSQL_SERVER \
    --location $LOCATION \
    --admin-user $MYSQL_ADMIN \
    --admin-password $MYSQL_PWD \
    --sku-name $SKU_NAME \
    --version 8.0

# Wait until server is ready
echo "Waiting 1 minute for MySQL server to be ready..."
sleep 60

# Create database
echo "Creating database $DB_NAME..."
az mysql flexible-server db create \
    --resource-group $RG \
    --server-name $MYSQL_SERVER \
    --database-name $DB_NAME

# ======== STEP 6: Configure Web App to connect MySQL ========
DB_HOST="${MYSQL_SERVER}.mysql.database.azure.com"
SPRING_DATASOURCE_URL="jdbc:mysql://${DB_HOST}:3306/${DB_NAME}?useSSL=true&requireSSL=true"
az webapp config appsettings set \
    --resource-group $RG \
    --name $APP_NAME \
    --settings SPRING_DATASOURCE_URL="$SPRING_DATASOURCE_URL" \
               SPRING_DATASOURCE_USERNAME="$MYSQL_ADMIN" \
               SPRING_DATASOURCE_PASSWORD="$MYSQL_PWD"

# ======== DONE ========
echo "Deployment finished!"
echo "Web App URL: https://${APP_NAME}.azurewebsites.net"
echo "MySQL Server: $MYSQL_SERVER, Database: $DB_NAME"

