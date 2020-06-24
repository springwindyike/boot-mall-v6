/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: snadakJtMM0MA5ItJJcOPbTm3IaQvHPq
 */
package com.igomall.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.dao.CouponCodeDao;
import com.igomall.entity.Coupon;
import com.igomall.entity.CouponCode;
import com.igomall.entity.Member;
import com.igomall.entity.PointLog;
import com.igomall.service.CouponCodeService;
import com.igomall.service.MemberService;

/**
 * Service - 优惠码
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class CouponCodeServiceImpl extends BaseServiceImpl<CouponCode, Long> implements CouponCodeService {

	@Inject
	private CouponCodeDao couponCodeDao;
	@Inject
	private MemberService memberService;

	@Override
	@Transactional(readOnly = true)
	public boolean codeExists(String code) {
		return couponCodeDao.exists("code", StringUtils.lowerCase(code));
	}

	@Override
	@Transactional(readOnly = true)
	public CouponCode findByCode(String code) {
		return couponCodeDao.find("code", StringUtils.lowerCase(code));
	}

	@Override
	public CouponCode generate(Coupon coupon, Member member) {
		Assert.notNull(coupon, "[Assertion failed] - coupon is required; it must not be null");

		CouponCode couponCode = new CouponCode();
		couponCode.setCode(coupon.getPrefix() + DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30)).toUpperCase());
		couponCode.setIsUsed(false);
		couponCode.setCoupon(coupon);
		couponCode.setMember(member);
		return super.save(couponCode);
	}

	@Override
	public List<CouponCode> generate(Coupon coupon, Member member, Integer count) {
		Assert.notNull(coupon, "[Assertion failed] - coupon is required; it must not be null");
		Assert.notNull(count, "[Assertion failed] - count is required; it must not be null");

		List<CouponCode> couponCodes = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			CouponCode couponCode = generate(coupon, member);
			couponCodes.add(couponCode);
			if (i % 50 == 0) {
				couponCodeDao.flush();
				couponCodeDao.clear();
			}
		}
		return couponCodes;
	}

	@Override
	public CouponCode exchange(Coupon coupon, Member member) {
		Assert.notNull(coupon, "[Assertion failed] - coupon is required; it must not be null");
		Assert.notNull(coupon.getPoint(), "[Assertion failed] - coupon point is required; it must not be null");
		Assert.state(coupon.getIsEnabled() && coupon.getIsExchange() && !coupon.hasExpired(), "[Assertion failed] - coupon must be enabled, can exchange and is not expired");
		Assert.notNull(member, "[Assertion failed] - member is required; it must not be null");
		Assert.notNull(member.getPoint(), "[Assertion failed] - member point is required; it must not be null");
		Assert.state(member.getPoint() >= coupon.getPoint(), "[Assertion failed] - member point must be equal or greater than coupon point");

		if (coupon.getPoint() > 0) {
			memberService.addPoint(member, -coupon.getPoint(), PointLog.Type.EXCHANGE, null);
		}

		return generate(coupon, member);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CouponCode> findPage(Member member, Pageable pageable) {
		return couponCodeDao.findPage(member, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed) {
		return couponCodeDao.count(coupon, member, hasBegun, hasExpired, isUsed);
	}

}