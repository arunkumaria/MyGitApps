package com.own.wowza;

import java.net.HttpURLConnection;
import java.net.URL;

import com.wowza.wms.application.*;
import com.wowza.wms.client.*;
import com.wowza.wms.stream.*;

public class SimpleWowzaModule extends ModuleBase {

	@Override
	public void onPublish(IClient client, IStream stream) {

		try {
			String token = client.getQueryStrMap().get("token");
			String streamName = stream.getName();

			if (!authorize(token, streamName)) {
				client.rejectConnection("Unauthorized");
			}

		} catch (Exception e) {
			client.rejectConnection("Authorization Error");
		}
	}

	private boolean authorize(String token, String streamName) throws Exception {

		String url = "http://localhost:8080/api/stream/validate" + "?token=" + token + "&streamName=" + streamName;

		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

		con.setRequestMethod("GET");
		return con.getResponseCode() == 200;
	}
}
