package com.own.wowza;

import com.wowza.wms.application.*;
import com.wowza.wms.amf.*;
import com.wowza.wms.client.*;
import com.wowza.wms.module.*;
import com.wowza.wms.request.*;

public class ModulePublishLogger extends ModuleBase {

	public void onPublish(IClient client, RequestFunction function, AMFDataList params) {
		String streamName = params.getString(PARAM1);
		getLogger().info("╔════════════════════════════════════════╗");
		getLogger().info("║      STREAM PUBLISHED                  ║");
		getLogger().info("╠════════════════════════════════════════╣");
		getLogger().info("║ Stream: " + streamName);
		getLogger().info("║ Publisher: " + client.getClientId());
		getLogger().info("║ IP: " + client.getIp());
		getLogger().info("╚════════════════════════════════════════╝");

		invokePrevious(client, function, params);
	}

	public void onUnPublish(IClient client, RequestFunction function, AMFDataList params) {
		String streamName = params.getString(PARAM1);
		getLogger().info(">>> STREAM UNPUBLISHED: " + streamName);

		invokePrevious(client, function, params);
	}
}