package com.own.wowza;

import com.wowza.wms.application.*;
import com.wowza.wms.amf.*;
import com.wowza.wms.client.*;
import com.wowza.wms.module.*;
import com.wowza.wms.request.*;

public class ModuleConnectionLogger extends ModuleBase implements IModuleOnApp, IModuleOnConnect {

	public void onAppStart(IApplicationInstance appInstance) {
		String fullname = appInstance.getApplication().getName() + "/" + appInstance.getName();
		getLogger().info("===========================================");
		getLogger().info("*** SPRING BOOT WOWZA MODULE STARTED ***");
		getLogger().info("Application: " + fullname);
		getLogger().info("===========================================");
	}

	public void onAppStop(IApplicationInstance appInstance) {
		String fullname = appInstance.getApplication().getName() + "/" + appInstance.getName();
		getLogger().info("*** SPRING BOOT WOWZA MODULE STOPPED ***");
		getLogger().info("Application: " + fullname);
	}

	public void onConnect(IClient client, RequestFunction function, AMFDataList params) {
		getLogger().info("╔════════════════════════════════════════╗");
		getLogger().info("║      CLIENT CONNECTED                  ║");
		getLogger().info("╠════════════════════════════════════════╣");
		getLogger().info("║ Client ID: " + client.getClientId());
		getLogger().info("║ IP Address: " + client.getIp());
		getLogger().info("║ Protocol: " + client.getProtocol());
		getLogger().info("║ Query: " + client.getQueryStr());
		getLogger().info("╚════════════════════════════════════════╝");
	}

	public void onConnectAccept(IClient client) {
		// Optional: can be left empty
	}

	public void onConnectReject(IClient client) {
		// Optional: can be left empty
	}

	public void onDisconnect(IClient client) {
		getLogger().info(">>> CLIENT DISCONNECTED: " + client.getClientId());
	}
}