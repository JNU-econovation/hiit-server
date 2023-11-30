package com.hiit.api.web.dto.request.with;

import com.hiit.api.web.dto.validator.DataId;
import com.hiit.api.web.dto.validator.With;
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
public class AddWithRequest {

	@DataId private Long id;
	@With @NotNull private String content;
}
