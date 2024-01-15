package com.hiit.api.domain.client.member.unlink;

import com.hiit.api.domain.client.response.member.UnlinkInfo;

public interface SocialUnlinkClient {

	UnlinkInfo execute(String targetId);
}
