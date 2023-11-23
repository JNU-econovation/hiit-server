package com.hiit.api.web.dto.request.with;

import com.hiit.api.web.dto.validator.DataId;
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
public class DeleteWithRequest {

	@DataId private Long id;
}
