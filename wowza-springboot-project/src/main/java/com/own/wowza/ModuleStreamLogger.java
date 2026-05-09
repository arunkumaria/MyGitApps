package com.own.wowza;

import com.wowza.wms.module.*;
import com.wowza.wms.stream.*;

public class ModuleStreamLogger extends ModuleBase {

	public void onStreamCreate(IMediaStream stream) {
		getLogger().info(">>> STREAM CREATED: " + stream.getName());
	}

	public void onStreamDestroy(IMediaStream stream) {
		getLogger().info(">>> STREAM DESTROYED: " + stream.getName());
	}
}