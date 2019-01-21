package com.video;

import com.video.dao.ITVipCodesMapper;
import com.video.dao.ITWholesaleOrderMapper;
import com.video.model.TVipCodes;
import com.video.model.TWholesaleOrder;
import com.video.service.WholesalePriceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	WholesalePriceService wholesalePriceService;
	@Autowired
	private ITWholesaleOrderMapper wholesaleOrderMapper;
	@Test
	public void contextLoads() {

		TWholesaleOrder order = wholesaleOrderMapper.selectByClassElement(new TWholesaleOrder());
		wholesalePriceService.handOut(order);
	}

}
