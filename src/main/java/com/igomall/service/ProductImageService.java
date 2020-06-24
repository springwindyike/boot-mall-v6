
package com.igomall.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.igomall.entity.ProductImage;

/**
 * Service - 商品图片
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface ProductImageService {

	/**
	 * 商品图片过滤
	 * 
	 * @param productImages
	 *            商品图片
	 */
	void filter(List<ProductImage> productImages);

	/**
	 * 生成商品图片
	 * 
	 * @param multipartFile
	 *            文件
	 * @return 商品图片
	 */
	ProductImage generate(MultipartFile multipartFile);

}