package com.hiit.api.domain.dto.response.member.list;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dto.response.member.FriendResponse;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class FriendResponses implements ServiceResponse {

	private final List<FriendResponse> members;
}
