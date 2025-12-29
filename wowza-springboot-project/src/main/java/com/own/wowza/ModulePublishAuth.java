package com.own.wowza;

import com.wowza.wms.application.*;
import com.wowza.wms.amf.*;
import com.wowza.wms.client.*;
import com.wowza.wms.module.*;
import com.wowza.wms.request.*;

public class ModulePublishAuth extends ModuleBase {

	private static final String VALID_TOKEN = "secret123";

	public void onPublish(IClient client, RequestFunction function, AMFDataList params) {
		String streamName = params.getString(PARAM1);
		String queryString = client.getQueryStr();

		getLogger().info("Authentication check for stream: " + streamName);

		boolean authenticated = false;
		if (queryString != null && queryString.contains("token=" + VALID_TOKEN)) {
			authenticated = true;
		}

		if (!authenticated) {
			getLogger().warn("UNAUTHORIZED: Publish rejected for stream: " + streamName);
			getLogger().warn("   Client IP: " + client.getIp());
			getLogger().warn("   Query: " + queryString);

			sendClientOnStatusError(client, "NetStream.Publish.Rejected", "Authentication failed. Token required.");
			client.setShutdownClient(true);
		} else {
			getLogger().info("AUTHORIZED: Publishing allowed for stream: " + streamName);
			invokePrevious(client, function, params);
		}
	}

	public void onUnPublish(IClient client, RequestFunction function, AMFDataList params) {
		invokePrevious(client, function, params);
	}
}