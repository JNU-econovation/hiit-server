package com.hiit.api.web.dto.request.end.it;

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
public class DeleteEndItRequest {

	@DataId private Long id;
}
