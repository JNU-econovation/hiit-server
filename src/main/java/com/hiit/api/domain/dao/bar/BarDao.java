package com.hiit.api.domain.dao.bar;

import com.hiit.api.domain.dao.JpaDao;

/** 연관관계를 가지고 있는 엔티티의 경우 신경쓸 것이 더 많다. */
public interface BarDao extends JpaDao<BarData, Long> {}
