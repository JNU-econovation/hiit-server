package com.hiit.api.domain.dto.response.together;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
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
public class RankTogetherResponse implements ServiceResponse {

	private Long id;
	private Long rank;
	private String memberName;
	private String memberPicture;
	private String memberComment;
	private Long hits;
}
