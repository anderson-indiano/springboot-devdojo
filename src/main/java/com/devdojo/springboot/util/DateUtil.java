package com.devdojo.springboot.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

//@Repository // especialização para classe dao
//@service // especialização  para classe de serviço
@Component //especialização para escanear a classe através do @ComponentScan
public class DateUtil {
	
	public String formatLocalDateTime(LocalDateTime localDateTime) {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
	}
}
