package com.hiit.api.domain.model.it.relation;

import com.hiit.api.common.marker.model.AbstractDomain;
import com.hiit.api.domain.model.it.GetItId;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class It_Relation implements GetItId, AbstractDomain {

	private Long id;
	private Long itId;
	private Long inItId;

	private ItTypeDetails type;

	private LocalDateTime createAt;
	private LocalDateTime updateAt;

	public boolean isType(ItTypeDetails itType) {
		return Objects.equals(this.type, itType);
	}

	public boolean isIt(GetItId itId) {
		return Objects.equals(this.itId, itId.getId());
	}

	public boolean isInIt(Long inItId) {
		return Objects.equals(this.inItId, inItId);
	}
}
