package com.hiit.api.domain.dto.response;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class NotiInfos implements ServiceResponse {

	private final List<NoticeInfo> notices;
}
