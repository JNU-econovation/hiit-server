package com.hiit.api.domain.service.it;

import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.relation.TargetItTypeInfo;

public interface BrowseTargetItService {

	BasicIt browse(TargetItTypeInfo type, Long itId);

	TargetItTypeInfo getType();
}
