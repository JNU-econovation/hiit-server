package com.hiit.api.web.dto.request.end.it;

import com.hiit.api.web.dto.validator.DataId;
import com.hiit.api.web.dto.validator.ItTitle;
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
public class EditEndItRequest {

	@DataId private Long id;
	@ItTitle @NotNull private String title;
}
