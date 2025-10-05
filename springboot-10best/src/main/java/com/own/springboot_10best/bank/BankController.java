package com.own.springboot_10best.bank;

import org.springframework.web.bind.annotation.*;

record TransferRequest(String from,String to,double amount){}

@RestController
@RequestMapping("/bank")
public class BankController {
	private final BankService service;
	private final AccountRepository repo;

	public BankController(BankService service, AccountRepository repo) {
		this.service = service;
		this.repo = repo;
	}

	@PostMapping("/transfer")
	public String transfer(@RequestBody TransferRequest req) {
		service.transfer(req.from(), req.to(), req.amount());
		return "Transferred " + req.amount() + " from " + req.from() + " to " + req.to();
	}

	@GetMapping("/accounts")
	public java.util.List<Account> accounts() {
		return repo.findAll();
	}
}