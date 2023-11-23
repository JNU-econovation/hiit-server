package com.hiit.api.domain.dto.response;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class NoticeInfo implements ServiceResponse {

	private Long id;
	private Date date;
	private String type;
	private String title;
	private String content;
}
