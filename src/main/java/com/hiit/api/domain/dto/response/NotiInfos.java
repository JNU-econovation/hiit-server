package com.hiit.api.domain.dto.response;

import com.hiit.api.common.marker.dto.AbstractResponse;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class NotiInfos implements AbstractResponse {

	private final List<NoticeInfo> notices;
}
