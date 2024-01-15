package com.hiit.api.repository.dao.document;

import com.hiit.api.repository.document.member.MemberStatDoc;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberStatDocRepository extends JpaRepository<MemberStatDoc, String> {

	Optional<MemberStatDoc> findByMemberId(Long memberId);
}
