package com.mdharr.livechat;

import com.mdharr.livechat.config.GlobalCorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GlobalCorsConfig.class)
public class LiveChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiveChatApplication.class, args);
	}

}
