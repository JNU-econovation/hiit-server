package com.hiit.api.domain.service.it;

import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;

public interface ItQuery {

	BasicIt query(ItTypeDetails type, GetItId itId);

	ItTypeDetails getType();
}
