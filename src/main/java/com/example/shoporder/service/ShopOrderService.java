package com.example.shoporder.service;


import com.example.shoporder.dto.ShopOrderDTO;
import com.example.shoporder.persistence.model.ShopOrder;
import com.example.shoporder.persistence.repository.ShopOrderRepository;
import com.example.shoporder.utils.QueueUtils;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

import static com.example.shoporder.utils.QueueUtils.*;

@Service
public class ShopOrderService {

    @Autowired
    private ShopOrderRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void create(ShopOrderDTO orderDTO) {
        ShopOrder shopOrder = mapper.map(orderDTO, ShopOrder.class);
        repository.save(shopOrder);
        rabbitTemplate.convertAndSend(QUEUE_NAME, shopOrder.getId());
    }

    public Page<ShopOrderDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(s -> mapper.map(s, ShopOrderDTO.class));
    }

    @RabbitListener(queues = QUEUE_NAME)
    private void subscribe(Long id){
        Optional<ShopOrder> shopOrder = repository.findById(id);

        if(shopOrder.isPresent()){
            shopOrder.get().setStatus("DONE");
            repository.save(shopOrder.get());
        }
    }
}
