package com.apap.tutorial7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.rest.DealerDetail;
import com.apap.tutorial7.rest.Setting;
import com.apap.tutorial7.service.DealerService;

@RestController
@RequestMapping("/dealer")
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@PostMapping(value="/add")
	private DealerModel addDealerSubmit(@RequestBody DealerModel dealer) {
		return dealerService.addDealer(dealer);
	}
	
	@GetMapping(value="/{dealerId}")
	private DealerModel viewDealer(@PathVariable("dealerId") long dealerId, Model model) {
		return dealerService.getDealerDetailById(dealerId).get();
	}
	
	@DeleteMapping(value="/delete")
	private String deleteDealer(@RequestParam("dealerId") long id, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(id).get();
		dealerService.deleteDealer(dealer);
		return "Success";
	}
	
	@PutMapping(value="/{id}")
	private String updateDealerSubmit(@PathVariable(value="id") long id, @RequestParam("alamat") String alamat, @RequestParam("telp") String telp) {
		DealerModel dealer = (DealerModel) dealerService.getDealerDetailById(id).get();
		if(dealer.equals(null)) {
			return "Couldn't find your dealer";
		}
		dealer.setAlamat(alamat);
		dealer.setNoTelp(telp);
		dealerService.updateDealer(dealer, id);
		return "update success";
	}
	
	@GetMapping()
	private List<DealerModel> viewAllDealer(Model model){
		return dealerService.getAllDealer();
	}
	
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}
	
	@GetMapping(value="/status/{dealerId}")
	private String getStatus(@PathVariable ("dealerId") long dealerId) throws Exception {
		String path = Setting.dealerUrl + "/dealer?id=" + dealerId;
		return restTemplate.getForEntity(path, String.class).getBody();
	}
	
	@GetMapping(value="/full/{dealerId}")
	private DealerDetail postStatus(@PathVariable("dealerId") long dealerId) throws Exception{
		String path = Setting.dealerUrl + "/dealer";
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		DealerDetail detail = restTemplate.postForObject(path, dealer, DealerDetail.class);
		return detail;
	}
	
//	@Autowired
//	private CarService carService;
//	
//	@RequestMapping("/")
//	private String home(Model model) {
//		model.addAttribute("title", "Home");
//		return "home";
//	}
//	
//	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
//	private String add (Model model) {
//		model.addAttribute("dealer", new DealerModel());
//		model.addAttribute("title", "Add Dealer");
//		return "addDealer";
//	}
//	
//	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
//	private String addDealerSubmit(@ModelAttribute DealerModel dealer) {
//		dealerService.addDealer(dealer);
//		return "add";
//	}
//	
//	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
//	private String viewDealer(@RequestParam(value="dealerId") Long dealerId, Model model) {
//		DealerModel dealer = null;
//		List<CarModel> listCar = null;
//		if(dealerService.getDealerDetailById(dealerId).isPresent()) {
//			dealer = dealerService.getDealerDetailById(dealerId).get();
//			listCar = dealer.getListCar();
//			Collections.sort(listCar, comparePrice);
//		}
//		model.addAttribute("listCar", listCar);
//		model.addAttribute("deal", dealer);
//		model.addAttribute("dealId", dealerId);
//		model.addAttribute("title", "View Dealer");
//		return "view-dealer";
//	}
//	
//	@RequestMapping(value = "/dealer/delete", method =RequestMethod.GET)
//	private String deleteDealer(String dealerId, Model model) {
//		if(dealerService.getDealerDetailById(Long.parseLong(dealerId)).isPresent()) {
//			DealerModel dealer = dealerService.getDealerDetailById(Long.parseLong(dealerId)).get();
//			if (dealer.getListCar().isEmpty()) {
//				dealerService.deleteDealer(dealer);
//				return "delete";
//			}
//			else {
//				List<CarModel> listCar = dealer.getListCar();
//				for (CarModel car : listCar) {
//					System.out.println(car);
//					carService.deleteCar(car);
//					dealerService.deleteDealer(dealer);
//					return "delete";
//				}
//			}
//		}
//		return "error";
//	}
//	
//	@RequestMapping(value="/dealer/update/{dealerId}", method = RequestMethod.GET)
//	private String updateDealer(@PathVariable(value="dealerId") Long dealerId, Model model) {
//		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
//		model.addAttribute("deal", dealer);
//		model.addAttribute("title", "Update Dealer");
//		return "update-dealer";
//	}
//	
//	@RequestMapping(value="/dealer/update/{dealerId}", method = RequestMethod.POST)
//	private String update(@PathVariable(value="dealerId") Long dealerId, @ModelAttribute Optional<DealerModel> deal) {
//		if(deal.isPresent()) {
//			dealerService.updateDealer(deal, dealerId);
//			return "update";
//		}
//		return "error";
//	}
//	
//	@RequestMapping(value="/dealer/view-all", method= RequestMethod.GET)
//	private String viewAllCar(Model model) {
//		List<DealerModel> listDealer = dealerService.getAllDealer();
//		model.addAttribute("title", "View All Dealer");
//		model.addAttribute("listDealer", listDealer);
//		return "view-all-dealer";
//	}
//
//	CarModel car;
//	public static Comparator<CarModel> comparePrice = new Comparator<CarModel>() {
//		public int compare(CarModel o1, CarModel o2) {
//			Long price1 = o1.getPrice();
//			Long price2 = o2.getPrice();
//			return price1.compareTo(price2);
//		}
//	};
}
