#!/bin/bash

# Customize these variables
RG_NAME="springboot-rg"
MYSQL_SERVER="springboot-mysql"
MYSQL_ADMIN="mysqladmin"
MYSQL_PASSWORD="YourStrongPassword!123"
MYSQL_DB="springboot_db"
APP_NAME="springboot-webapp"
PLAN_NAME="springboot-plan"
GITHUB_REPO="https://github.com/<your-username>/<your-springboot-repo>"
JAVA_VERSION="JAVA|17-java17"

# Step 0: Auto-detect a region with Burstable MySQL SKU (free tier)
echo "Searching for an Azure region supporting Burstable MySQL Flexible Server SKU..."
REGIONS=$(az account list-locations --query "[].name" -o tsv)
AVAILABLE_REGION=""
for region in $REGIONS; do
  SKU=$(az mysql flexible-server list-skus --location $region --query "[?tier=='Burstable'] | [0].name" -o tsv 2>/dev/null)
  if [ -n "$SKU" ]; then
    AVAILABLE_REGION=$region
    BURSTABLE_SKU=$SKU
    echo "Found Burstable SKU ($BURSTABLE_SKU) in region: $AVAILABLE_REGION"
    break
  fi
done

if [ -z "$AVAILABLE_REGION" ]; then
  echo "No Azure region with Burstable MySQL SKU found. Please check your subscription or choose another region manually."
  exit 1
fi

LOCATION=$AVAILABLE_REGION

# Step 1: Create resource group
echo "Creating resource group $RG_NAME in $LOCATION..."
az group create --name $RG_NAME --location $LOCATION

# Step 2: Create MySQL flexible server with discovered free-tier SKU
echo "Creating MySQL flexible server $MYSQL_SERVER with SKU $BURSTABLE_SKU..."
az mysql flexible-server create \
  --name $MYSQL_SERVER \
  --resource-group $RG_NAME \
  --location $LOCATION \
  --admin-user $MYSQL_ADMIN \
  --admin-password $MYSQL_PASSWORD \
  --sku-name $BURSTABLE_SKU \
  --tier Burstable

# Step 3: Configure firewall rule to allow all IPs (for demo purposes)
echo "Configuring firewall rule to allow access from any IP..."
az mysql flexible-server firewall-rule create \
  --resource-group $RG_NAME \
  --name "AllowAll" \
  --server $MYSQL_SERVER \
  --start-ip-address 0.0.0.0 \
  --end-ip-address 255.255.255.255

# Step 4: Create database
echo "Creating database $MYSQL_DB..."
az mysql flexible-server db create \
  --resource-group $RG_NAME \
  --server-name $MYSQL_SERVER \
  --database-name $MYSQL_DB

# Step 5: Create App Service plan (Free tier F1)
echo "Creating App Service plan $PLAN_NAME (Free tier)..."
az appservice plan create \
  --name $PLAN_NAME \
  --resource-group $RG_NAME \
  --sku F1 --is-linux

# Step 6: Create Web App and deploy from GitHub
echo "Creating Web App $APP_NAME and deploying from GitHub repo $GITHUB_REPO..."
az webapp create \
  --resource-group $RG_NAME \
  --plan $PLAN_NAME \
  --name $APP_NAME \
  --runtime $JAVA_VERSION \
  --deployment-source-url $GITHUB_REPO

# Step 7: Set MySQL connection settings for Web App using environment variables
echo "Setting app settings for MySQL connection..."
az webapp config appsettings set \
  --resource-group $RG_NAME \
  --name $APP_NAME \
  --settings \
    SPRING_DATASOURCE_URL="jdbc:mysql://$MYSQL_SERVER.mysql.database.azure.com:3306/$MYSQL_DB?useSSL=true" \
    SPRING_DATASOURCE_USERNAME="$MYSQL_ADMIN@$MYSQL_SERVER" \
    SPRING_DATASOURCE_PASSWORD="$MYSQL_PASSWORD"

# Step 8: Fetch application URL and print
APP_URL=$(az webapp show --resource-group $RG_NAME --name $APP_NAME --query defaultHostName -o tsv)

echo "Deployment complete! Your Spring Boot app is live at:"
echo "https://$APP_URL"
