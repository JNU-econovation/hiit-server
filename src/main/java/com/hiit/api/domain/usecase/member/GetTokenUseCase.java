package com.hiit.api.domain.usecase.member;

import com.hiit.api.common.token.AuthToken;
import com.hiit.api.common.token.TokenResolver;
import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.request.member.GetTokenUseCaseRequest;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.service.token.UserTokenGenerator;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetTokenUseCase implements AbstractUseCase<GetTokenUseCaseRequest> {

	private final MemberDao memberDao;
	private final MemberEntityConverter memberEntityConverter;

	private final TokenResolver tokenResolver;
	private final UserTokenGenerator tokenGenerator;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	public AuthToken execute(GetTokenUseCaseRequest request) {
		final String refreshToken = request.getToken();

		Long source = getSource(refreshToken);

		log.debug("read member : m - {}", source);
		Member member = readMember(source);

		return tokenGenerator.generateAuthToken(member.getId());
	}

	private Long getSource(String token) {
		Optional<Long> source = tokenResolver.resolveId(token);
		if (source.isEmpty()) {
			log.warn("Failed to get memberId. token: {}", token);
			throw new IllegalArgumentException();
		}
		return source.get();
	}

	private Member readMember(Long memberId) {
		Optional<HiitMemberEntity> source = memberDao.findById(memberId);
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate("memberId", memberId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new IllegalArgumentException(exceptionData);
		}
		return memberEntityConverter.from(source.get());
	}
}
