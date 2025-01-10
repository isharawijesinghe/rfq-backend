package com.rfq.asset;

import com.rfq.enclave.autoconfigure.EnableNitroEnclavesHostSide;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableNitroEnclavesHostSide
public class AssetManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetManagementApplication.class, args);
	}

}
