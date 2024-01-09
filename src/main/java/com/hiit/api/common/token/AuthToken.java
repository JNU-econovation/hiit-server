package com.hiit.api.common.token;

import com.hiit.api.common.marker.dto.AbstractResponse;

public interface AuthToken extends AbstractResponse {

	String getAccessToken();

	String getRefreshToken();
}
