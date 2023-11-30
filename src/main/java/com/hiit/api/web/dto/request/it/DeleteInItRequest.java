package com.hiit.api.web.dto.request.it;

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
public class DeleteInItRequest {

	@DataId private Long id;
	@ItTitle @NotNull private String endTitle;
}
