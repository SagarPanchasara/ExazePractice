package com.example.practical.service;

import com.example.practical.entity.ClientEntity;
import com.example.practical.exception.MyException;
import com.example.practical.json.ClientJson;
import com.example.practical.repository.ClientRepository;
import com.example.practical.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private ClientEntity toEntity(ClientJson clientJson) {
        return ClientEntity.builder()
                .id(clientJson.getId())
                .firstName(clientJson.getFirstName())
                .idNumber(clientJson.getIdNumber())
                .lastName(clientJson.getLastName())
                .mobileNumber(clientJson.getMobileNumber())
                .physicalAddress(clientJson.getPhysicalAddress())
                .build();
    }

    private ClientJson toJson(ClientEntity clientEntity) {
        return ClientJson.builder()
                .id(clientEntity.getId())
                .firstName(clientEntity.getFirstName())
                .idNumber(clientEntity.getIdNumber())
                .lastName(clientEntity.getLastName())
                .mobileNumber(clientEntity.getMobileNumber())
                .physicalAddress(clientEntity.getPhysicalAddress())
                .build();
    }

    public ClientJson insert(ClientJson clientJson) {
        Optional<ClientEntity> optional = clientRepository.findOne((Specification<ClientEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("idNumber"), clientJson.getIdNumber());
            if (null != clientJson.getMobileNumber()) {
                predicate = criteriaBuilder.or(
                        predicate,
                        criteriaBuilder.equal(root.get("mobileNumber"), clientJson.getMobileNumber())
                );
            }
            return predicate;
        });
        if (optional.isPresent()) {
            ClientEntity existing = optional.get();
            if (existing.getIdNumber().equals(clientJson.getIdNumber())) {
                throw new MyException(HttpStatus.BAD_REQUEST, Constant.ValidationMessage.DUPLICATE_ID_NUMBER);
            } else {
                throw new MyException(HttpStatus.BAD_REQUEST, Constant.ValidationMessage.DUPLICATE_MOBILE_NUMBER);
            }
        }
        ClientEntity entity = clientRepository.save(toEntity(clientJson));
        clientJson.setId(entity.getId());
        return clientJson;
    }

    public void update(long id, ClientJson clientJson) {
        clientRepository.findById(id).orElseThrow(MyException.getSupplier(HttpStatus.BAD_REQUEST, Constant.ValidationMessage.INVALID_ID));
        Optional<ClientEntity> optional = clientRepository.findOne((Specification<ClientEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("idNumber"), clientJson.getIdNumber());
            if (null != clientJson.getMobileNumber()) {
                predicate = criteriaBuilder.or(
                        predicate,
                        criteriaBuilder.equal(root.get("mobileNumber"), clientJson.getMobileNumber())
                );
            }
            return criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.notEqual(root.get("id"), id)
            );
        });
        if (optional.isPresent()) {
            ClientEntity existing = optional.get();
            if (existing.getIdNumber().equals(clientJson.getIdNumber())) {
                throw new MyException(HttpStatus.BAD_REQUEST, Constant.ValidationMessage.DUPLICATE_ID_NUMBER);
            } else {
                throw new MyException(HttpStatus.BAD_REQUEST, Constant.ValidationMessage.DUPLICATE_MOBILE_NUMBER);
            }
        }
        ClientEntity clientEntity = toEntity(clientJson);
        clientEntity.setId(id);
        clientRepository.save(clientEntity);
    }

    public Page<ClientJson> getList(String query, Pageable pageable) {
        Page<ClientEntity> page;
        if (null == query || query.isEmpty()) {
            page = clientRepository.findAll(pageable);
        } else {
            query = query.toLowerCase();
            String finalQuery = query;
            page = clientRepository.findAll((Specification<ClientEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), finalQuery),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("idNumber")), finalQuery),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("mobileNumber")), finalQuery)
            ), pageable);
        }
        return page.map(this::toJson);
    }
}
