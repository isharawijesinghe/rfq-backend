package com.rfq.quote;

import com.rfq.enclave.autoconfigure.EnableNitroEnclavesHostSide;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableNitroEnclavesHostSide
public class QuoteAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuoteAggregatorApplication.class, args);
	}

}
