package com.own.wowza;

import com.wowza.wms.application.*;
import com.wowza.wms.amf.*;
import com.wowza.wms.client.*;
import com.wowza.wms.module.*;
import com.wowza.wms.request.*;

public class ModulePlayLogger extends ModuleBase {

	public void onPlay(IClient client, RequestFunction function, AMFDataList params) {
		String streamName = params.getString(PARAM1);
		getLogger().info(">>> PLAYBACK STARTED: " + streamName + " by client " + client.getClientId());

		invokePrevious(client, function, params);
	}

	public void onStop(IClient client, RequestFunction function, AMFDataList params) {
		String streamName = params.getString(PARAM1);
		getLogger().info(">>> PLAYBACK STOPPED: " + streamName);

		invokePrevious(client, function, params);
	}

	public void onPause(IClient client, RequestFunction function, AMFDataList params) {
		invokePrevious(client, function, params);
	}

	public void onSeek(IClient client, RequestFunction function, AMFDataList params) {
		invokePrevious(client, function, params);
	}
}