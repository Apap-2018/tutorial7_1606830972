package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.DealerModel;

public interface DealerService {
	Optional<DealerModel> getDealerDetailById(Long id);
	
	DealerModel addDealer(DealerModel dealer);
	
	void deleteDealer(DealerModel dealer);
	
	void updateDealer(DealerModel dealer, Long dealerId);

	void sortCar(DealerModel dealer);
	
	List<DealerModel> getAllDealer();
}