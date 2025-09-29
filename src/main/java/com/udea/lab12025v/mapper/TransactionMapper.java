package com.udea.lab12025v.mapper;


import com.udea.lab12025v.DTO.TransactionDTO;
import com.udea.lab12025v.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {
TransactionMapper   INSTANCE = Mappers.getMapper(TransactionMapper.class);
TransactionDTO toDTO(Transaction transaction);
}
