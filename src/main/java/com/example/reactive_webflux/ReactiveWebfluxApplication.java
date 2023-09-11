package com.example.reactive_webflux;

import com.example.reactive_webflux.service.FluxAndMonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWebfluxApplication.class, args);
		FluxAndMonoService fluxAndMonoService=new FluxAndMonoService();
		fluxAndMonoService.fruitsFlux().subscribe(s->{
			System.out.println("S= "+s);
		});
		fluxAndMonoService.fruitMono().subscribe(s->{
			System.out.println("S Mono= "+s);
		});
	}

}
