package com.company.sms;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("${sms.service.base.url}")
public interface SmsClient  {

}
