package com.hiit.api.web.dto.request.it;

import com.hiit.api.web.dto.validator.DataId;
import com.hiit.api.web.dto.validator.DayCode;
import com.hiit.api.web.dto.validator.ItResolution;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddInItRequest {

	@DataId private Long id;
	@DayCode @NotNull private String dayCode;
	@ItResolution @NotNull private String resolution;
	@NotNull private RequestItType type;
}
