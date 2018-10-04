package com.apap.tutorial4.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tutorial4.model.DealerModel;
import com.apap.tutorial4.repository.DealerDb;

@Service
@Transactional
public class DealerServiceImpl implements DealerService{
	@Autowired
	private DealerDb dealerDb;
	
	@Override
	public Optional<DealerModel> getDealerDetailById(Long id){
		return dealerDb.findById(id);
	}
	
	@Override
	public void addDealer(DealerModel dealer) {
		dealerDb.save(dealer);
	}

	@Override
	public void deleteDealer(DealerModel dealer) {
		dealerDb.delete(dealer);
		
	}

	@Override
	public void updateDealer(DealerModel dealer, String alamat, String noTelp) {
		dealerDb.getOne(dealer.getId()).setAlamat(alamat);
		dealerDb.getOne(dealer.getId()).setNoTelp(alamat);
	}

	@Override
	public void sortCar(DealerModel dealer) {
		
		
	}

	@Override
	public List<DealerModel> getAllDealer() {
		return dealerDb.findAll();
		
	}
	

}
