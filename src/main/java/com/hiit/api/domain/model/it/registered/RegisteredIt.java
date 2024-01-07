package com.hiit.api.domain.model.it.registered;

import com.hiit.api.domain.model.it.BasicIt;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class RegisteredIt extends BasicIt {}
