/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: kp+fbdlPaEYfQ1CvJZ/HZxuwgVKySf0C
 */
package com.igomall.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Security - 当前店铺注解
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.PARAMETER)
public @interface CurrentStore {

}