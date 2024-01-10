package com.hiit.api.domain.client.member.token;

import com.hiit.api.domain.client.response.token.Token;

public interface SocialTokenClient {

	Token execute(String code);
}
