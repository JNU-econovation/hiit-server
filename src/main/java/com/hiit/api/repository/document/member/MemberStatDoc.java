package com.hiit.api.repository.document.member;

import com.hiit.api.repository.document.StringIdBaseEntity;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@Entity(name = "member_stat_doc")
@Table(name = "member_stat_doc")
public class MemberStatDoc extends StringIdBaseEntity {

	public static final String ENTITY_PREFIX = "member_stat_";

	@Column(name = ENTITY_PREFIX + "resource", columnDefinition = "json")
	@Convert(converter = MemberStatConverter.class)
	private MemberStat resource;
}
