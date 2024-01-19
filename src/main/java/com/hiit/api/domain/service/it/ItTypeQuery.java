package com.hiit.api.domain.service.it;

import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;

public interface ItTypeQuery {

	BasicIt query(ItTypeDetails type, GetItId inItId);

	BasicIt query(ItTypeDetails type, GetInItId inItId);

	ItTypeDetails getType();
}
