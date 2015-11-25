package com.monash.mymonashmate.client;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public abstract class AbstractClient {

	// To access your PC localhost from Android emulator, use 10.0.2.2 instead
	// of 127.0.0.1.
	// localhost or 127.0.0.1 refers to the emulated device itself, not the host
	// the emulator is running on.
	// Reference:
	// http://developer.android.com/tools/devices/emulator.html#networkaddresses
	protected static final String BASE_URL = "http://10.0.2.2:13520/MyMonashMateServer/webresources";

	protected HttpEntity<?> getRequestEntity() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		return new HttpEntity<Object>(requestHeaders);
	}
}
